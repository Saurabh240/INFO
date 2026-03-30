# System Design — Interview Guide (7+ YOE)

---

## Approach Framework

For every system design question, follow this structure:

1. **Clarify requirements** (2–3 mins) — functional + non-functional
2. **Capacity estimation** — requests/sec, storage, bandwidth
3. **High-level design** — services, databases, queues
4. **Deep dive** — bottlenecks, scaling, failure handling
5. **Trade-offs** — explain what you chose and why you didn't choose the alternative

---

## Q1. Design a Payment Processing System (High Transaction Volume)

### Requirements clarification
- 10,000 TPS at peak, 99.99% availability
- Exactly-once payment processing (no double charges)
- Audit trail for all transactions
- Sub-200ms P99 latency for payment initiation

### High-Level Architecture

```
Client
  │
  ▼
API Gateway (rate limiting, auth, routing)
  │
  ▼
Payment API Service (stateless, horizontally scaled)
  │
  ├──► Idempotency Store (Redis) ──► check duplicate requests
  │
  ├──► Account Service (sync) ──► validate & debit account
  │
  ▼
Kafka Topic: payments.initiated
  │
  ├──► Payment Processor (async) ──► core business logic
  │         │
  │         ▼
  │    PostgreSQL (source of truth)
  │
  ├──► Notification Service ──► SMS / Email to customer
  ├──► Audit Service ──► immutable audit log
  └──► Analytics Service ──► real-time dashboard
```

### Key Design Decisions

**Idempotency — prevent double charges:**
```
Client sends: POST /payments with Idempotency-Key: "client-uuid-12345"

Payment Service:
1. Check Redis: GET "idem:client-uuid-12345"
2. If exists → return cached response (don't reprocess)
3. If absent → process payment → SET "idem:client-uuid-12345" result EX 86400
```

**Kafka partitioning strategy:**
```
Partition by accountId → ensures all events for same account are ordered
Partitions = 50 → 50 consumers can process in parallel
Replication factor = 3 → survives 2 broker failures
```

**Database schema:**
```sql
CREATE TABLE transactions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key VARCHAR(100) UNIQUE NOT NULL,  -- prevents duplicates
    from_account_id VARCHAR(50) NOT NULL,
    to_account_id   VARCHAR(50) NOT NULL,
    amount          DECIMAL(19,4) NOT NULL,
    currency        VARCHAR(3) NOT NULL,
    status          VARCHAR(20) NOT NULL,           -- INITIATED, PROCESSING, COMPLETED, FAILED
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Optimistic locking on accounts
CREATE TABLE accounts (
    id      VARCHAR(50) PRIMARY KEY,
    balance DECIMAL(19,4) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0  -- for @Version optimistic locking
);
```

---

## Q2. Design a URL Shortener

### Core requirements
- Encode: `POST /encode` → `https://short.ly/abc123`
- Decode: `GET /abc123` → redirect to original URL
- 100M URLs/day write, 1B reads/day (read-heavy)
- URLs expire after 1 year

### Short code generation — Base62
```java
public class Base62Encoder {
    private static final String CHARSET =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Convert auto-increment DB ID to short code
    public String encode(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.insert(0, CHARSET.charAt((int)(id % 62)));
            id /= 62;
        }
        return sb.toString();
    }
    // 62^6 = ~56 billion combinations for 6-char code
}
```

**Architecture:**
```
Write path:
POST /encode
  → Validate URL
  → Get next ID from distributed counter (Redis INCR or Snowflake ID)
  → Base62 encode
  → Store (shortCode, originalUrl, expiresAt) in PostgreSQL
  → Cache in Redis (shortCode → originalUrl, TTL = 1 year)
  → Return short URL

Read path (hot path, must be <10ms):
GET /{shortCode}
  → Redis lookup (cache hit ~99% for popular URLs)
  → Cache miss → PostgreSQL → update cache → 301/302 redirect
  → Log click analytics to Kafka (async, non-blocking)
```

**Caching strategy:**
- Redis with LRU eviction — 20% of URLs get 80% of traffic (power law)
- TTL in Redis = same as URL expiry
- 301 (permanent) redirect allows browser caching — reduces server load further

---

## Q3. Design a Rate Limiter

### Algorithms comparison

| Algorithm | Pros | Cons |
|---|---|---|
| Fixed Window | Simple | Burst at window boundary |
| Sliding Window Log | Exact | High memory (store all timestamps) |
| Sliding Window Counter | Approximate, memory efficient | Slightly inaccurate |
| Token Bucket | Smooth bursts allowed | Complex implementation |
| Leaky Bucket | Consistent output rate | Drops requests, no burst |

### Token Bucket with Redis (production approach)
```java
@Component @RequiredArgsConstructor
public class RateLimiter {
    private final RedisTemplate<String, String> redis;

    // 100 requests/minute per user
    private static final int RATE = 100;
    private static final int WINDOW_SECONDS = 60;

    public boolean isAllowed(String userId) {
        String key = "ratelimit:" + userId + ":" + (System.currentTimeMillis() / 1000 / WINDOW_SECONDS);

        // Lua script — atomic increment + expire in single Redis call
        String script = """
            local current = redis.call('INCR', KEYS[1])
            if current == 1 then
                redis.call('EXPIRE', KEYS[1], ARGV[1])
            end
            return current
            """;

        Long count = redis.execute(
            new DefaultRedisScript<>(script, Long.class),
            List.of(key),
            String.valueOf(WINDOW_SECONDS)
        );

        return count != null && count <= RATE;
    }
}

// Spring filter — apply before reaching controller
@Component @RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private final RateLimiter rateLimiter;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String userId = req.getHeader("X-User-Id");
        if (!rateLimiter.isAllowed(userId)) {
            res.setStatus(429); // Too Many Requests
            res.setHeader("Retry-After", "60");
            res.getWriter().write("{\"error\": \"Rate limit exceeded\"}");
            return;
        }
        chain.doFilter(req, res);
    }
}
```

---

## Q4. CAP Theorem Applied to Real Systems

**CAP Theorem:** In a distributed system, you can only guarantee 2 of:
- **C**onsistency — all nodes see the same data at the same time
- **A**vailability — every request gets a response (not necessarily the latest data)
- **P**artition tolerance — system continues despite network partitions

Network partitions are **unavoidable** in distributed systems → always choose P, then trade off C vs A:

| System | Choice | Why |
|---|---|---|
| Banking transactions | **CP** | Cannot have stale balance reads — strong consistency mandatory |
| Product catalog | **AP** | Slightly stale price (few seconds) is acceptable vs unavailability |
| Shopping cart | **AP** | Cart items can be eventually consistent |
| DNS | **AP** | Availability critical, slight staleness OK |
| Zookeeper / etcd | **CP** | Coordination service must be consistent |

```
Real example from IBM banking platform:
- Transaction processing: PostgreSQL with synchronous replication (CP)
  Accepts writes only to primary, reads from primary for financial data
- Notification preferences: Cassandra (AP)
  Eventually consistent — if a user misses one notification, acceptable
- Session cache: Redis with master-replica (AP by default, configurable)
```

---

## Q5. Caching Strategies

### Cache patterns

**Cache-Aside (Lazy Loading) — most common:**
```java
public Account getAccount(String id) {
    // 1. Check cache
    Account cached = cache.get("account:" + id);
    if (cached != null) return cached;

    // 2. Cache miss — load from DB
    Account account = accountRepo.findById(id).orElseThrow();

    // 3. Store in cache
    cache.set("account:" + id, account, Duration.ofMinutes(10));
    return account;
}
// Problem: cache stampede on first load or after expiry
// Fix: use probabilistic early expiration or locking
```

**Write-Through — write to cache and DB together:**
```java
public void updateAccount(Account account) {
    accountRepo.save(account);                    // write to DB
    cache.set("account:" + account.getId(), account); // immediately update cache
}
// Pros: cache always consistent with DB
// Cons: write latency includes cache write
```

**Write-Behind (Write-Back) — write to cache, async flush to DB:**
```
Write → Cache → Return success
                    │ (async, batched)
                    ▼
                  Database
```
- Risk of data loss if cache node fails before flush
- Use case: high-write analytics, non-critical counters

**Cache eviction strategies:**
- **LRU (Least Recently Used):** Evict items not accessed recently — good for temporal locality
- **LFU (Least Frequently Used):** Evict least accessed items — good for stable "hot" data
- **TTL:** Time-based expiry — simplest, always correct (stale for at most TTL duration)

**Cache invalidation — the hard problem:**
```java
// Option 1: TTL-based — accept eventual staleness
cache.set(key, value, Duration.ofSeconds(30)); // stale for up to 30s

// Option 2: Event-driven invalidation
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
public void onAccountUpdated(AccountUpdatedEvent event) {
    cache.delete("account:" + event.getAccountId()); // invalidate on change
}

// Option 3: Write-through + version tag
cache.set("account:" + id + ":v" + version, value); // new version = different key
```
