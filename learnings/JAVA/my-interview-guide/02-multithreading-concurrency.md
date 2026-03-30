# Multithreading & Concurrency — Interview Guide (7+ YOE)

---

## 1. Thread Fundamentals

### Q1. What's the difference between `Runnable`, `Callable`, and `Future`?

| | `Runnable` | `Callable<V>` | `Future<V>` |
|---|---|---|---|
| Return value | None (`void`) | Returns `V` | Holds result of `Callable` |
| Throws checked exception | No | Yes | — |
| Use with | Thread, ExecutorService | ExecutorService | ExecutorService.submit() |

```java
// Runnable — fire and forget
Runnable task = () -> System.out.println("Running in thread: " + Thread.currentThread().getName());
new Thread(task).start();

// Callable — returns a result
Callable<BigDecimal> balanceTask = () -> accountService.getBalance(accountId);

ExecutorService executor = Executors.newFixedThreadPool(5);
Future<BigDecimal> future = executor.submit(balanceTask);

// future.get() blocks — use timeout in production
try {
    BigDecimal balance = future.get(3, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    future.cancel(true); // interrupt the task
    throw new ServiceUnavailableException("Balance service timed out");
} finally {
    executor.shutdown();
}
```

---

## 2. Executor Framework

### Q2. Explain the different ExecutorService types and when to use each.

```java
// Fixed pool — bounded, predictable resource usage
// Use for: CPU-bound tasks, known concurrency level
ExecutorService fixed = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
);

// Cached pool — unbounded, short-lived threads
// Use for: many short IO-bound tasks (danger: can create too many threads)
ExecutorService cached = Executors.newCachedThreadPool();

// Single thread — sequential execution, never parallel
// Use for: ordered processing, background single-threaded jobs
ExecutorService single = Executors.newSingleThreadExecutor();

// Scheduled — delayed or periodic tasks
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
scheduler.scheduleAtFixedRate(() -> metricsCollector.collect(), 0, 30, TimeUnit.SECONDS);
```

**Production pattern — custom ThreadPoolExecutor:**
```java
// In banking, always use explicit ThreadPoolExecutor over Executors shortcuts
// So you control queue capacity and rejection policy
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    10,                                    // corePoolSize
    50,                                    // maximumPoolSize
    60L, TimeUnit.SECONDS,                 // keepAliveTime
    new ArrayBlockingQueue<>(1000),        // bounded queue — prevents OOM
    new ThreadFactoryBuilder()             // named threads for debugging
        .setNameFormat("payment-worker-%d")
        .build(),
    new ThreadPoolExecutor.CallerRunsPolicy() // backpressure on caller
);
```

> **Why not use `Executors.newFixedThreadPool()` in production?** It uses an unbounded `LinkedBlockingQueue` — under load, the queue grows infinitely, causing OOM. Always use bounded queues in production.

---

## 3. Synchronization

### Q3. Explain `synchronized`, `volatile`, and `ReentrantLock`. When do you use each?

**`volatile` — visibility guarantee, no atomicity:**
```java
public class StatusMonitor {
    // volatile ensures writes are visible to all threads immediately
    // but does NOT make compound operations atomic
    private volatile boolean running = true;

    public void stop() { running = false; } // write visible to all threads

    public void run() {
        while (running) { // reads latest value, not cached in CPU register
            checkHealth();
        }
    }
}
// Use volatile for: simple boolean flags, status fields read by many threads
// Do NOT use for: counter++, read-modify-write — not atomic
```

**`synchronized` — mutual exclusion + visibility:**
```java
public class BankAccount {
    private BigDecimal balance;

    // Method-level lock — lock is the 'this' reference
    public synchronized void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    // Block-level lock — finer control
    public void transfer(BankAccount target, BigDecimal amount) {
        synchronized (this) {
            this.balance = this.balance.subtract(amount);
        }
        synchronized (target) {
            target.balance = target.balance.add(amount);
        }
        // WARNING: Above can cause deadlock — see Q5 below
    }
}
```

**`ReentrantLock` — advanced features:**
```java
public class PaymentGateway {
    private final ReentrantLock lock = new ReentrantLock(true); // fair lock
    private final Condition sufficientFunds = lock.newCondition();
    private BigDecimal balance;

    public boolean tryProcessPayment(BigDecimal amount) {
        // tryLock avoids blocking — critical for responsive banking APIs
        if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
            try {
                if (balance.compareTo(amount) < 0) {
                    return false; // insufficient funds
                }
                balance = balance.subtract(amount);
                return true;
            } finally {
                lock.unlock(); // always in finally
            }
        }
        throw new PaymentTimeoutException("Could not acquire lock");
    }
}
```

| Feature | `synchronized` | `ReentrantLock` |
|---|---|---|
| Fairness | No | Yes (optional) |
| `tryLock()` | No | Yes |
| Interruptible wait | No | Yes |
| Multiple conditions | No | Yes |
| Automatic release | Yes (implicit) | No — must `unlock()` in `finally` |

> **Rule of thumb:** Use `synchronized` for simple mutual exclusion. Use `ReentrantLock` when you need `tryLock`, timed lock, fair ordering, or multiple `Condition` variables.

---

### Q4. What is the difference between `AtomicInteger` and `synchronized int`?

`AtomicInteger` uses **CAS (Compare-And-Swap)** — a CPU-level instruction. It's lock-free, which means threads don't block each other — they retry.

```java
// synchronized version — thread blocks
public class SynchronizedCounter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public synchronized int get() { return count; }
}

// AtomicInteger — lock-free, better throughput under low/medium contention
public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    public void increment() { count.incrementAndGet(); }
    public int get() { return count.get(); }
}

// AtomicReference — for more complex atomic state transitions
public class StatusManager {
    private final AtomicReference<String> status = new AtomicReference<>("ACTIVE");

    public boolean deactivate() {
        // CAS: only update if current value is "ACTIVE"
        return status.compareAndSet("ACTIVE", "INACTIVE");
    }
}
```

> **When `synchronized` beats `AtomicInteger`:** Under very high contention (thousands of threads), CAS retries become expensive. `LongAdder` (Java 8) performs better than `AtomicLong` for high-contention counters like metrics — it uses striped counters internally.

---

## 4. CompletableFuture (Advanced)

### Q5. How do you handle errors and combine multiple CompletableFutures?

```java
// Combining independent futures — run in parallel, combine results
CompletableFuture<User> userFuture    = CompletableFuture.supplyAsync(() -> userService.fetch(id));
CompletableFuture<Account> acctFuture = CompletableFuture.supplyAsync(() -> accountService.fetch(id));

CompletableFuture<DashboardData> dashboard = userFuture
    .thenCombine(acctFuture, (user, account) -> new DashboardData(user, account));

// Run all and wait — allOf returns CompletableFuture<Void>
List<CompletableFuture<Report>> futures = reportIds.stream()
    .map(id -> CompletableFuture.supplyAsync(() -> reportService.generate(id)))
    .collect(Collectors.toList());

CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
    .thenRun(() -> log.info("All reports generated"));

// Error handling
CompletableFuture<Balance> balance = CompletableFuture
    .supplyAsync(() -> balanceService.fetch(accountId))
    .exceptionally(ex -> {                           // recover from error
        log.error("Balance fetch failed", ex);
        return Balance.ZERO;                         // fallback
    })
    .thenApply(b -> applyExchangeRate(b));           // continues even after recovery
```

---

## 5. Deadlock

### Q6. What is a deadlock? How do you detect and prevent it?

A deadlock occurs when two or more threads are waiting for each other's locks — creating a circular wait.

```java
// Classic deadlock scenario — fund transfer
class DeadlockExample {
    void transfer(Account from, Account to, BigDecimal amount) {
        synchronized (from) {                    // Thread A locks Account 1
            synchronized (to) {                  // Thread A waits for Account 2
                from.debit(amount);
                to.credit(amount);
            }
        }
        // Thread B doing transfer(Account2 → Account1) simultaneously deadlocks here
    }
}

// Prevention strategy 1: Consistent lock ordering
void safeTransfer(Account from, Account to, BigDecimal amount) {
    // Always lock lower-id account first — regardless of transfer direction
    Account first  = from.getId() < to.getId() ? from : to;
    Account second = from.getId() < to.getId() ? to : from;

    synchronized (first) {
        synchronized (second) {
            from.debit(amount);
            to.credit(amount);
        }
    }
}

// Prevention strategy 2: tryLock with timeout (ReentrantLock)
boolean safeTransferWithTryLock(Account from, Account to, BigDecimal amount)
        throws InterruptedException {
    while (true) {
        if (from.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                if (to.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        from.debit(amount);
                        to.credit(amount);
                        return true;
                    } finally {
                        to.lock.unlock();
                    }
                }
            } finally {
                from.lock.unlock();
            }
        }
        Thread.sleep(50); // back off and retry
    }
}
```

**Detection in production:** Thread dumps (`kill -3 <pid>` or `jstack <pid>`) show "DEADLOCK FOUND" sections. We used this at GlobalLogic to diagnose a production deadlock caused by ELK stack connection pool exhaustion combined with synchronized logging.

---

## 6. Virtual Threads (Java 21)

### Q7. What are Virtual Threads and when should you use them?

Virtual Threads (Project Loom) are lightweight threads managed by the JVM, not the OS. A single OS thread can carry thousands of virtual threads.

**Traditional threads — problem:**
```java
// OS thread per request — expensive at scale
// 10,000 concurrent requests = 10,000 OS threads = ~80GB RAM
ExecutorService executor = Executors.newFixedThreadPool(200); // artificially limited
```

**Virtual Threads — solution for IO-bound workloads:**
```java
// Java 21 — each task gets its own virtual thread, JVM manages mounting/unmounting
try (ExecutorService vExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i ->
        vExecutor.submit(() -> {
            // While this virtual thread blocks on IO (DB call, HTTP call),
            // the carrier OS thread is freed to run other virtual threads
            String result = httpClient.call("https://api.service.com/data/" + i);
            return process(result);
        })
    );
}
```

**When to use Virtual Threads:**
- ✅ High-concurrency IO-bound workloads (REST APIs, DB calls, file IO)
- ✅ Replacing large thread pools for per-request processing
- ❌ CPU-bound workloads — use parallel streams or ForkJoinPool instead
- ❌ When code uses `synchronized` on shared state heavily — can cause "pinning" (virtual thread pinned to OS thread)

> **Production note:** Spring Boot 3.2+ supports virtual threads via `spring.threads.virtual.enabled=true`. For a banking API service making many downstream calls, this can dramatically increase throughput without changing business logic.

---

## 7. ForkJoinPool

### Q8. What is ForkJoinPool and how does `parallelStream()` relate to it?

ForkJoinPool implements the **work-stealing** algorithm — idle threads steal tasks from busy threads' queues. It's designed for divide-and-conquer recursive tasks.

```java
// Custom RecursiveTask — sum a large array in parallel
class SumTask extends RecursiveTask<Long> {
    private final long[] array;
    private final int start, end;
    private static final int THRESHOLD = 1000;

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // Base case — compute directly
            long sum = 0;
            for (int i = start; i < end; i++) sum += array[i];
            return sum;
        }
        // Split into two halves
        int mid = (start + end) / 2;
        SumTask left  = new SumTask(array, start, mid);
        SumTask right = new SumTask(array, mid, end);
        left.fork();                            // schedule left async
        return right.compute() + left.join();   // compute right, wait for left
    }
}

ForkJoinPool pool = new ForkJoinPool(4);
long total = pool.invoke(new SumTask(largeArray, 0, largeArray.length));
```

**`parallelStream()` uses the common ForkJoinPool:**
```java
// parallelStream() uses ForkJoinPool.commonPool() — shared across the JVM
// In production, this can cause contention with other parallel operations
List<Report> reports = reportIds.parallelStream()
    .map(reportService::generate)
    .collect(Collectors.toList());

// Better: use a dedicated pool for parallel operations
ForkJoinPool customPool = new ForkJoinPool(4);
List<Report> reports = customPool.submit(() ->
    reportIds.parallelStream()
             .map(reportService::generate)
             .collect(Collectors.toList())
).get();
```

> **Senior caution:** Never use `parallelStream()` blindly. For IO-bound tasks (DB, HTTP), it can actually be slower than sequential — IO blocks the ForkJoin thread, starving other tasks. Use `parallelStream()` for CPU-bound in-memory operations only.
