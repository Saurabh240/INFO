# JPA, Hibernate & Databases — Interview Guide (7+ YOE)

---

## 1. JPA Entity Relationships

### Q1. Explain JPA relationships and their fetch strategies. What is the N+1 problem?

```java
@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // OneToMany — default LAZY (correct — don't eagerly load thousands of orders)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // ManyToOne — default EAGER (usually fine — loading one parent)
    // But consider LAZY for performance in large graphs
}

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // recommended — avoid auto-join
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}
```

**N+1 Problem — the most common JPA performance killer:**
```java
// PROBLEM: This executes 1 + N queries
List<Customer> customers = customerRepo.findAll(); // 1 query: SELECT * FROM customers
for (Customer c : customers) {
    // Each access triggers: SELECT * FROM orders WHERE customer_id = ?
    System.out.println(c.getOrders().size()); // N queries!
}
// For 100 customers = 101 queries. For 1000 = 1001 queries.

// SOLUTION 1: JOIN FETCH in JPQL
@Query("SELECT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllWithOrders();
// Executes 1 query with JOIN — loads all data at once

// SOLUTION 2: @EntityGraph — declarative, cleaner
@EntityGraph(attributePaths = {"orders", "orders.items"})
@Query("SELECT c FROM Customer c")
List<Customer> findAllWithOrdersAndItems();

// SOLUTION 3: @BatchSize — loads in batches, not one-by-one
@OneToMany(mappedBy = "customer")
@BatchSize(size = 20) // loads 20 customers' orders in one query
private List<Order> orders;
```

> **Senior gotcha:** `JOIN FETCH` with `DISTINCT` and pagination don't mix well — Hibernate fetches all rows first, then paginates in memory (HibernateJpaDialect warning). Use `@EntityGraph` with `countQuery` for paginated results with associations.

---

## 2. Transactions in JPA

* ### Q2. How do `@Transactional` and JPA interact? What is the persistence context?

The **Persistence Context** is Hibernate's first-level cache — it tracks all entities loaded within a transaction. Changes are **automatically flushed** to the DB at the end of the transaction (dirty checking).

```java
@Service
@Transactional
public class AccountService {

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        // Both loaded into same persistence context
        Account from = accountRepo.findById(fromId).orElseThrow();
        Account to   = accountRepo.findById(toId).orElseThrow();

        from.debit(amount);   // just modifies the object in memory
        to.credit(amount);    // same

        // NO explicit save() needed!
        // Hibernate detects changes via dirty checking
        // and generates UPDATE statements on transaction commit
    }
}

// But — fetch inside @Transactional vs outside matters:
@Service
public class ReportService {

    @Transactional(readOnly = true) // hint: skip dirty checking, optimize reads
    public CustomerReport getReport(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        // LAZY collections can be accessed here — transaction still open
        int orderCount = customer.getOrders().size(); // safe
        return new CustomerReport(customer, orderCount);
    }

    public CustomerReport getReportBroken(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        // Transaction closed after findById returns!
        customer.getOrders().size(); // LazyInitializationException!
        return null;
    }
}
```

---

## 3. Optimistic vs Pessimistic Locking

### Q3. When do you use optimistic vs pessimistic locking in a banking system?

**Optimistic Locking — assume no conflict, verify before commit:**
```java
@Entity
public class Account {
    @Id
    private Long id;

    private BigDecimal balance;

    @Version // Hibernate manages this — increments on every update
    private Integer version;
}

// If two transactions read version=5, both try to update:
// First commit succeeds → version becomes 6
// Second commit fails → OptimisticLockException (version 5 != 6)

// Handle the exception at service level
@Service
public class AccountService {

    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    @Transactional
    public void updateBalance(Long id, BigDecimal amount) {
        Account account = accountRepo.findById(id).orElseThrow();
        account.credit(amount);
        // save() triggers version check
    }
}
```

**Pessimistic Locking — lock row immediately, block others:**
```java
// SELECT ... FOR UPDATE — row locked until transaction ends
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdWithLock(@Param("id") Long id);
}

// Use pessimistic locking when:
// - High contention expected (many concurrent updates to same row)
// - Can't tolerate retries (ATM withdrawal — must succeed or fail, not retry)
// Use optimistic locking when:
// - Low contention (most updates succeed)
// - Read-heavy, infrequent writes
// - Distributed systems where holding DB locks is expensive
```

---

## 4. Spring Data JPA — Advanced Queries

### Q4. What are projections and how do they improve performance?

Fetching full entities when you only need a few fields wastes memory and DB bandwidth.

```java
// Interface-based projection — Spring generates proxy
public interface CustomerSummary {
    String getName();
    String getEmail();
    // derived expression
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<CustomerSummary> findByStatus(String status); // only fetches name, email
}

// DTO projection — class-based, more explicit
public record CustomerDTO(String name, String email, long orderCount) {}

@Query("""
    SELECT new com.example.dto.CustomerDTO(c.name, c.email, COUNT(o))
    FROM Customer c LEFT JOIN c.orders o
    WHERE c.status = :status
    GROUP BY c.id
    """)
List<CustomerDTO> findCustomerSummaries(@Param("status") String status);
```

**Native queries for complex SQL:**
```java
@Query(
    value = """
        SELECT c.name, c.email, SUM(o.amount) as total_spend
        FROM customers c
        INNER JOIN orders o ON c.id = o.customer_id
        WHERE o.created_at >= :since
        GROUP BY c.id
        HAVING SUM(o.amount) > :minSpend
        ORDER BY total_spend DESC
        LIMIT :limit
        """,
    nativeQuery = true
)
List<Object[]> findTopSpenders(
    @Param("since") LocalDate since,
    @Param("minSpend") BigDecimal minSpend,
    @Param("limit") int limit
);
```

---

## 5. Database Indexing & Query Optimization

### Q5. How do you approach SQL query optimization? What indexes have you used?

**EXPLAIN ANALYZE — your best debugging tool:**
```sql
-- See execution plan
EXPLAIN ANALYZE
SELECT * FROM transactions
WHERE account_id = 'ACC-001' AND status = 'COMPLETED'
ORDER BY created_at DESC
LIMIT 20;

-- Look for:
-- "Seq Scan" on large tables = missing index
-- High "rows removed by filter" = poor selectivity
-- "Hash Join" vs "Nested Loop" — context-dependent
-- Actual rows vs estimated rows — stale statistics if very different
-- Run: ANALYZE transactions; to update statistics
```

**Index types and when to use each:**
```sql
-- B-tree index (default) — equality and range queries
CREATE INDEX idx_tx_account_status ON transactions(account_id, status);
-- Composite index — column order matters!
-- Queries on (account_id) or (account_id, status) use this
-- Query on (status) alone does NOT use this index effectively

-- Partial index — index only subset of rows (much smaller, faster)
CREATE INDEX idx_tx_pending ON transactions(created_at)
WHERE status = 'PENDING';
-- Great for: querying only pending transactions frequently

-- Covering index — includes all needed columns (avoids table lookup)
CREATE INDEX idx_tx_covering ON transactions(account_id, status)
INCLUDE (amount, created_at);
-- Query: SELECT amount, created_at FROM transactions WHERE account_id=? AND status=?
-- Uses index-only scan — never touches the table

-- GIN index — for JSONB or array fields
CREATE INDEX idx_tx_metadata ON transactions USING GIN(metadata);
-- Supports: metadata @> '{"channel": "mobile"}'
```

**Common query optimization techniques:**
```sql
-- PROBLEM: N+1 at SQL level
-- Don't do this in a loop:
SELECT * FROM orders WHERE customer_id = 1;
SELECT * FROM orders WHERE customer_id = 2; -- repeated

-- DO this instead:
SELECT * FROM orders WHERE customer_id IN (1, 2, 3, ...);

-- Window functions instead of subqueries
-- BAD: correlated subquery — O(n²)
SELECT id, amount,
    (SELECT AVG(amount) FROM transactions WHERE account_id = t.account_id) as avg
FROM transactions t;

-- GOOD: window function — single pass
SELECT id, amount,
    AVG(amount) OVER (PARTITION BY account_id) as avg
FROM transactions;
```

---

## 6. Database Transactions & Isolation

### Q6. Explain ACID, isolation levels, and when you've applied them.

**ACID:**
- **Atomicity:** All operations succeed or all roll back — no partial commits
- **Consistency:** DB constraints always satisfied before and after transaction
- **Isolation:** Concurrent transactions don't interfere with each other
- **Durability:** Committed data survives crashes (WAL in PostgreSQL)

**Isolation levels and their problems:**

| Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| `READ UNCOMMITTED` | ✅ possible | ✅ possible | ✅ possible |
| `READ COMMITTED` | ❌ prevented | ✅ possible | ✅ possible |
| `REPEATABLE READ` | ❌ | ❌ prevented | ✅ possible |
| `SERIALIZABLE` | ❌ | ❌ | ❌ prevented |

```java
// READ_COMMITTED (PostgreSQL default) — good for most banking operations
@Transactional(isolation = Isolation.READ_COMMITTED)
public BigDecimal getBalance(String accountId) {
    return accountRepo.getBalance(accountId);
    // Won't read another transaction's uncommitted debit/credit
}

// SERIALIZABLE — for critical financial operations (fund transfer)
@Transactional(
    isolation = Isolation.SERIALIZABLE,
    rollbackFor = InsufficientFundsException.class
)
public TransactionResult transfer(String fromId, String toId, BigDecimal amount) {
    // Phantom read prevention matters here:
    // Without SERIALIZABLE, two concurrent "check balance → debit" transactions
    // could both read sufficient balance and both proceed — double spend
    Account from = accountRepo.findByIdWithLock(fromId).orElseThrow();
    if (from.getBalance().compareTo(amount) < 0)
        throw new InsufficientFundsException();
    from.debit(amount);
    accountRepo.findById(toId).orElseThrow().credit(amount);
    return new TransactionResult("SUCCESS");
}
```

---

## 7. Schema Migration

### Q7. How do you handle database migrations in production with Flyway?

```java
// Flyway auto-runs on application startup
// File: src/main/resources/db/migration/V1__create_transactions.sql
```

```sql
-- V1__create_transactions.sql
CREATE TABLE transactions (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id  VARCHAR(50) NOT NULL,
    amount      DECIMAL(19,4) NOT NULL,
    currency    VARCHAR(3) NOT NULL DEFAULT 'INR',
    status      VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_tx_account_id ON transactions(account_id);
CREATE INDEX idx_tx_status_created ON transactions(status, created_at DESC);

-- V2__add_reference_number.sql
-- ZERO-DOWNTIME migration: add nullable column first, backfill, then add constraint
ALTER TABLE transactions ADD COLUMN reference_number VARCHAR(100);
-- V3__backfill_reference.sql — backfill in batches
-- V4__add_reference_constraint.sql — add NOT NULL after backfill
```

> **Zero-downtime migration rules:**
> 1. Never add a NOT NULL column without a default in a single migration
> 2. Never rename a column in one migration (add new → dual-write → remove old)
> 3. Never drop a column without first deploying code that doesn't use it
> 4. Index creation: use `CREATE INDEX CONCURRENTLY` to avoid table lock
