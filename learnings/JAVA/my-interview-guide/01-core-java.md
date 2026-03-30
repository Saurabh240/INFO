# Core Java — Interview Guide (7+ YOE)

---

## 1. Java 8–17 Feature Deep Dive

### Q1. What are the key Java 8 features you actively use in production?

The most impactful Java 8 features in production work are:

- **Streams API** — declarative collection processing
- **Optional** — explicit null handling
- **CompletableFuture** — async/non-blocking programming
- **Lambda & Functional Interfaces** — cleaner, composable code
- **Default methods on interfaces** — backward-compatible API evolution

**Streams — Senior-level example:**

```java
// Group transactions by status, then get total amount per group
Map<String, BigDecimal> totals = transactions.stream()
    .collect(Collectors.groupingBy(
        Transaction::getStatus,
        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
    ));
```

**CompletableFuture — chaining async tasks (real-world pattern):**

```java
CompletableFuture<Void> pipeline = CompletableFuture
    .supplyAsync(() -> userService.fetchUser(userId))          // async fetch
    .thenCompose(user -> orderService.getOrders(user.getId())) // non-blocking chain
    .thenCompose(orders -> emailService.sendSummary(orders))   // another async step
    .exceptionally(ex -> {
        log.error("Pipeline failed", ex);
        return null;
    });
```

> **Key point for 7+ YOE:** You should know the difference between `thenApply` (sync transform), `thenCompose` (async chain — avoids `CompletableFuture<CompletableFuture<T>>`), and `thenCombine` (merge two independent futures).

---

### Q2. What are the key Java 17 features and where have you used them?

Java 17 is an LTS release — critical for enterprise projects. Key features:

**Records (Java 16) — immutable DTOs:**
```java
// Before: 40 lines of POJO boilerplate
// After:
public record TransactionDTO(String id, BigDecimal amount, String status) {}

// Usage — canonical constructor, equals, hashCode, toString auto-generated
TransactionDTO tx = new TransactionDTO("TXN-001", new BigDecimal("5000"), "COMPLETED");
System.out.println(tx.amount()); // accessor, not getter
```

**Sealed Classes — controlled polymorphism:**
```java
// Model payment outcomes as a closed hierarchy
public sealed interface PaymentResult permits Success, Failure, Pending {}
public record Success(String txnId) implements PaymentResult {}
public record Failure(String reason, int errorCode) implements PaymentResult {}
public record Pending(String referenceId) implements PaymentResult {}

// Compiler forces exhaustive handling — no default needed
String message = switch (result) {
    case Success s    -> "Payment confirmed: " + s.txnId();
    case Failure f    -> "Payment failed: " + f.reason();
    case Pending p    -> "Awaiting confirmation: " + p.referenceId();
};
```

**Pattern Matching instanceof:**
```java
// Before Java 17
if (obj instanceof String) {
    String s = (String) obj; // redundant cast
    process(s);
}

// Java 17
if (obj instanceof String s) {
    process(s); // s is already String, scoped to this block
}
```

> **Interview tip:** Mention that you used Records for DTOs and Sealed classes for domain modeling in your banking project. Sealed classes + switch expressions give compile-time exhaustiveness — the compiler tells you if you've missed a case.

---

### Q3. What is the contract between `equals()` and `hashCode()`? Why does it matter?

The contract:
1. If `a.equals(b)` is `true`, then `a.hashCode() == b.hashCode()` **must** be true.
2. The reverse is NOT required — same hashCode doesn't mean equal (hash collision is fine).

**Why it matters in practice:**

If you override `equals()` but not `hashCode()`, objects that are logically equal will behave incorrectly in `HashMap`, `HashSet`, and `Hashtable` — because these collections use `hashCode()` for bucket placement first.

```java
public class Account {
    private final String accountNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account a)) return false;
        return Objects.equals(accountNumber, a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber); // consistent with equals
    }
}

// Without hashCode override:
Set<Account> set = new HashSet<>();
Account a1 = new Account("ACC-001");
Account a2 = new Account("ACC-001");
set.add(a1);
set.contains(a2); // returns FALSE — wrong! Different hashCodes → different buckets
```

> **Senior angle:** Records automatically generate correct `equals()` and `hashCode()` based on all components — one more reason to use them for value objects.

---

### Q4. How do you build an immutable class in Java? What are the edge cases?

**Rules:**
1. Declare class `final` (prevent subclassing)
2. All fields `private final`
3. No setters
4. Initialize via constructor
5. **Defensive copies** for mutable fields — this is the most commonly missed rule

```java
public final class BankTransaction {
    private final String id;
    private final BigDecimal amount;
    private final LocalDate transactionDate; // LocalDate is immutable — safe
    private final List<String> tags;         // List is mutable — needs defensive copy

    public BankTransaction(String id, BigDecimal amount,
                           LocalDate date, List<String> tags) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = date;
        // Defensive copy on the way IN
        this.tags = List.copyOf(tags); // Java 10+ — returns unmodifiable copy
    }

    public String getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getTransactionDate() { return transactionDate; }

    // Return unmodifiable view — no defensive copy needed since List.copyOf already did it
    public List<String> getTags() { return tags; }
}
```

**Why BigDecimal is safe without copy:** It's already immutable.
**Why Date is NOT safe without copy:** `java.util.Date` is mutable — always defensive copy it. Use `LocalDate`/`LocalDateTime` instead (immutable by design).

**Thread safety benefit:**
```java
// Immutable objects can be freely shared across threads — no synchronization needed
private static final BankTransaction ZERO_TX =
    new BankTransaction("ZERO", BigDecimal.ZERO, LocalDate.now(), List.of());
```

---

## 2. OOP & SOLID Principles

### Q5. Explain SOLID principles with real examples from your project.

**S — Single Responsibility:**
Each class has exactly one reason to change.

```java
// BAD: TransactionService doing too much
class TransactionService {
    void processTransaction(Transaction tx) {
        validate(tx);       // validation concern
        saveToDb(tx);       // persistence concern
        sendEmail(tx);      // notification concern
        logAudit(tx);       // audit concern
    }
}

// GOOD: Separated concerns
@Service class TransactionValidator  { void validate(Transaction tx) {...} }
@Repository class TransactionRepository { void save(Transaction tx) {...} }
@Service class NotificationService   { void notifyUser(Transaction tx) {...} }
@Component class AuditLogger         { void log(Transaction tx) {...} }

@Service class TransactionService {
    void processTransaction(Transaction tx) {
        validator.validate(tx);
        repository.save(tx);
        notificationService.notifyUser(tx);
        auditLogger.log(tx);
    }
}
```

**O — Open/Closed:**
```java
// Open for extension (new payment types), closed for modification
interface PaymentProcessor { PaymentResult process(Payment payment); }

class UPIProcessor    implements PaymentProcessor { ... }
class CardProcessor   implements PaymentProcessor { ... }
class NEFTProcessor   implements PaymentProcessor { ... }

// Adding RTGS? Add a new class — don't touch existing code.
class RTGSProcessor   implements PaymentProcessor { ... }
```

**L — Liskov Substitution:**
Subclasses must honour the contract of their parent. Classic violation:

```java
// VIOLATION: Square breaks Rectangle's contract
class Rectangle {
    void setWidth(int w)  { this.width = w; }
    void setHeight(int h) { this.height = h; }
    int area() { return width * height; }
}
class Square extends Rectangle {
    void setWidth(int w)  { this.width = w; this.height = w; } // surprise!
}

// Code that works for Rectangle silently breaks for Square:
void assertArea(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.area() == 20; // fails for Square — LSP violated
}
```

**I — Interface Segregation:**
```java
// BAD: Forces all implementors to implement everything
interface Worker { void code(); void design(); void manage(); void test(); }

// GOOD: Role-specific interfaces
interface Coder    { void code(); }
interface Designer { void design(); }
interface Manager  { void manage(); }

class Developer implements Coder, Designer { ... } // only what's relevant
```

**D — Dependency Inversion:**
```java
// BAD: High-level module depends on concrete low-level module
class ReportService {
    private final PostgresReportDao dao = new PostgresReportDao(); // hard dependency
}

// GOOD: Both depend on abstraction
interface ReportRepository { void save(Report r); }

@Service class ReportService {
    private final ReportRepository repository;
    ReportService(ReportRepository repository) { // injected — Spring handles this
        this.repository = repository;
    }
}

@Repository class PostgresReportRepository implements ReportRepository { ... }
```

---

## 3. Java Memory Model

### Q6. Explain Java heap, stack, and GC — how do you tune GC in production?

**Memory areas:**

| Area | Stores | Lifecycle |
|---|---|---|
| **Stack** | Method call frames, local primitives, object references | Per-thread, auto-freed on method return |
| **Heap** | All objects (`new X()`) | Managed by GC |
| **Metaspace** | Class metadata (replaced PermGen in Java 8) | Freed when classloaders are collected |

**Generational GC model:**
- **Young Generation** (Eden + Survivor spaces): New objects. Minor GC is frequent but fast.
- **Old Generation (Tenured)**: Long-lived objects. Major GC is infrequent but longer pause.
- **G1GC** (default since Java 9): Splits heap into regions, predictable pause targets.
- **ZGC** (Java 15+ production): Sub-millisecond pauses — ideal for latency-sensitive banking APIs.

**Production tuning example (banking service):**
```bash
# G1GC with explicit heap sizing and GC logging
java -Xms2g -Xmx4g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -Xlog:gc*:file=/logs/gc.log:time,uptime:filecount=5,filesize=20m \
     -jar banking-service.jar
```

**How to diagnose memory issues:**
1. Enable GC logs → look for frequent Full GC or promotion failures
2. Use `-XX:+HeapDumpOnOutOfMemoryError` → analyze with VisualVM or Eclipse MAT
3. Thread dumps → identify memory leaks from thread-local state

> **Senior angle:** In production at IBM, we profiled the banking service and found that using `String.format()` in a tight loop was creating excessive short-lived String objects. Replacing with `StringBuilder` + template caching reduced Young GC frequency by 40%.

---

## 4. String Pool & Performance

### Q7. How does String interning work? Why is `String` immutable?

**String Pool (String Interning):**
```java
String a = "hello";            // goes to String pool
String b = "hello";            // returns same reference from pool
String c = new String("hello"); // bypasses pool — always new heap object

System.out.println(a == b);    // true  — same pool reference
System.out.println(a == c);    // false — c is on heap
System.out.println(a.equals(c)); // true — value equality

// Force c into pool:
String d = c.intern();
System.out.println(a == d);    // true
```

**Why String is immutable — design reasons:**
1. **Thread safety** — immutable objects need no synchronization
2. **Security** — class loading uses String class names; mutable class names would be a security hole
3. **Caching** — hashCode computed once and cached (String stores it in a field)
4. **String pool** — only possible because value never changes

**Performance — `StringBuilder` vs `String` concatenation:**
```java
// BAD: Creates N intermediate String objects — O(n²) copies
String result = "";
for (String word : words) {
    result += word; // new String object on every iteration
}

// GOOD: Single buffer — O(n)
StringBuilder sb = new StringBuilder();
for (String word : words) {
    sb.append(word);
}
String result = sb.toString();
```

---

## 5. `Comparable` vs `Comparator`

### Q8. When do you use `Comparable` vs `Comparator`?

| | `Comparable` | `Comparator` |
|---|---|---|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Location | Inside the class | External / lambda |
| Use case | Natural ordering | Custom/multiple orderings |

```java
// Comparable — natural order (by id)
public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private BigDecimal salary;

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id); // natural order by id
    }
}

// Usage
List<Employee> employees = ...;
Collections.sort(employees); // uses compareTo

// Comparator — custom orders without modifying the class
Comparator<Employee> bySalaryDesc = Comparator
    .comparing(Employee::getSalary).reversed();

Comparator<Employee> byDeptThenSalary = Comparator
    .comparing(Employee::getDepartment)
    .thenComparing(Employee::getSalary, Comparator.reverseOrder());

employees.sort(byDeptThenSalary);

// Java 8 chaining — sort by multiple criteria
employees.stream()
    .sorted(Comparator.comparing(Employee::getDepartment)
                      .thenComparing(Employee::getName))
    .collect(Collectors.toList());
```

> **When to choose which:** Use `Comparable` when the class has one obvious natural order (e.g., `LocalDate` by date). Use `Comparator` when you need multiple sort strategies or the class isn't yours to modify.
