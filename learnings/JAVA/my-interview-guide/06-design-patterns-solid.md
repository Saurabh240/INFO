# Design Patterns & SOLID Principles — Interview Guide (7+ YOE)

---

## 1. SOLID Principles (Production-Level Examples)

> At 7+ YOE, interviewers expect you to apply SOLID to real code — not just recite definitions.

### S — Single Responsibility Principle
*One class = one reason to change.*

```java
// VIOLATION: TransactionService handles too many concerns
@Service
public class TransactionService {
    public void process(Transaction tx) {
        // Validation logic
        if (tx.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException();
        // Persistence
        transactionRepo.save(tx);
        // Notification
        emailService.send(tx.getCustomerEmail(), "Transaction complete");
        // Audit
        auditLog.write("TX processed: " + tx.getId());
    }
}

// CORRECT: Each class has one reason to change
@Component class TransactionValidator {
    void validate(Transaction tx) { ... }
}
@Repository class TransactionRepository {
    void save(Transaction tx) { ... }
}
@Component class TransactionNotifier {
    void notify(Transaction tx) { ... }
}
@Component class TransactionAuditor {
    void audit(Transaction tx) { ... }
}
@Service class TransactionService {
    void process(Transaction tx) {
        validator.validate(tx);
        repository.save(tx);
        notifier.notify(tx);
        auditor.audit(tx);
    }
}
```

---

### O — Open/Closed Principle
*Open for extension, closed for modification.*

```java
// VIOLATION: Adding a new payment type requires modifying existing code
class PaymentProcessor {
    void process(Payment payment) {
        if ("UPI".equals(payment.getType()))      { processUPI(payment); }
        else if ("CARD".equals(payment.getType())) { processCard(payment); }
        // Every new type = edit this class = risky, test-breaking
    }
}

// CORRECT: Extend via new class, never touch existing ones
interface PaymentStrategy {
    boolean supports(String paymentType);
    PaymentResult process(Payment payment);
}

@Component class UPIStrategy implements PaymentStrategy {
    public boolean supports(String type) { return "UPI".equals(type); }
    public PaymentResult process(Payment p) { ... }
}
@Component class CardStrategy implements PaymentStrategy {
    public boolean supports(String type) { return "CARD".equals(type); }
    public PaymentResult process(Payment p) { ... }
}

// Adding NEFT? Just add new class — existing code untouched
@Component class NEFTStrategy implements PaymentStrategy {
    public boolean supports(String type) { return "NEFT".equals(type); }
    public PaymentResult process(Payment p) { ... }
}

@Service @RequiredArgsConstructor
class PaymentProcessor {
    private final List<PaymentStrategy> strategies; // Spring injects all implementations

    PaymentResult process(Payment payment) {
        return strategies.stream()
            .filter(s -> s.supports(payment.getType()))
            .findFirst()
            .orElseThrow(() -> new UnsupportedPaymentTypeException(payment.getType()))
            .process(payment);
    }
}
```

---

### L — Liskov Substitution Principle
*Subclasses must honour the contract of their parent — no surprises.*

```java
// VIOLATION: PremiumAccount changes the contract of Account
class Account {
    virtual void withdraw(BigDecimal amount) {
        if (balance < amount) throw new InsufficientFundsException();
        balance -= amount;
    }
}

class OverdraftAccount extends Account {
    @Override
    void withdraw(BigDecimal amount) {
        // No exception — overdraft allowed
        // Code that expects InsufficientFundsException will break with OverdraftAccount
        balance -= amount;
    }
}

// CORRECT: Model the hierarchy properly
interface Withdrawable {
    WithdrawalResult withdraw(BigDecimal amount); // returns result, no exception contract
}

class StandardAccount implements Withdrawable {
    public WithdrawalResult withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            return WithdrawalResult.insufficientFunds();
        balance = balance.subtract(amount);
        return WithdrawalResult.success();
    }
}

class OverdraftAccount implements Withdrawable {
    public WithdrawalResult withdraw(BigDecimal amount) {
        balance = balance.subtract(amount); // overdraft ok — still returns result
        return WithdrawalResult.success();
    }
}
// Both are substitutable — code using Withdrawable works with either
```

---

### I — Interface Segregation Principle
*Don't force clients to depend on methods they don't use.*

```java
// VIOLATION: Fat interface
interface TransactionOperations {
    void create(Transaction tx);
    Transaction find(String id);
    void delete(String id);
    void generateReport();
    void sendAlerts();
    void archiveOldTransactions();
}
// A reporting service implementing this must stub create/delete/alerts

// CORRECT: Focused, role-based interfaces
interface TransactionWriter { void create(Transaction tx); }
interface TransactionReader { Transaction find(String id); }
interface TransactionReporter { Report generateReport(); }
interface TransactionArchiver { void archiveOldTransactions(); }

// Each service implements only what it needs
@Service class TransactionReportService implements TransactionReader, TransactionReporter {
    public Transaction find(String id) { ... }
    public Report generateReport() { ... }
    // No forced stubs for write or delete operations
}
```

---

### D — Dependency Inversion Principle
*Depend on abstractions, not concretions.*

```java
// VIOLATION: High-level service hardwired to low-level detail
@Service
class RiskAnalysisService {
    private final PostgresRiskRepository repo = new PostgresRiskRepository(); // concrete
    private final AwsEmailSender emailSender = new AwsEmailSender(); // concrete
}
// Changing DB or email provider requires changing RiskAnalysisService

// CORRECT: Both depend on interfaces, Spring wires the implementation
interface RiskRepository { List<RiskFactor> findByAccountId(String id); }
interface AlertSender    { void sendAlert(RiskAlert alert); }

@Repository class PostgresRiskRepository implements RiskRepository { ... }
@Component class AwsEmailSender implements AlertSender { ... }

@Service @RequiredArgsConstructor
class RiskAnalysisService {
    private final RiskRepository repository; // abstraction
    private final AlertSender alertSender;   // abstraction
    // Can swap PostgreSQL → MongoDB, AWS SES → SendGrid — zero changes here
}
```

---

## 2. Design Patterns in Microservices

### Q1. Which patterns have you used in production? Explain with code.

**Strategy Pattern — payment routing:**
*(Already shown in OCP above — this is the real-world implementation)*

---

**Builder Pattern — complex object construction:**
```java
// Without Builder: constructor with 8+ parameters — unreadable
new Transaction("id", "ACC-001", "ACC-002", BigDecimal.TEN, "INR",
                "NEFT", "COMPLETED", Instant.now()); // which param is which?

// With Builder: readable, safe
Transaction transaction = Transaction.builder()
    .id(UUID.randomUUID().toString())
    .fromAccount("ACC-001")
    .toAccount("ACC-002")
    .amount(BigDecimal.TEN)
    .currency("INR")
    .type(TransactionType.NEFT)
    .status(TransactionStatus.COMPLETED)
    .createdAt(Instant.now())
    .build();

// Lombok makes this trivial:
@Builder @Value // Value = immutable record-like class
public class Transaction {
    String id, fromAccount, toAccount;
    BigDecimal amount;
    String currency;
    TransactionType type;
    TransactionStatus status;
    Instant createdAt;
}
```

---

**Observer Pattern — event-driven with Kafka:**
```java
// Publisher (Subject)
@Component @RequiredArgsConstructor
public class TransactionEventPublisher {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void publishEvent(Transaction tx) {
        kafkaTemplate.send("transactions", tx.getId(),
            new TransactionEvent(tx.getId(), tx.getStatus(), Instant.now()));
    }
}

// Multiple independent observers/consumers
@Component class AuditConsumer    { @KafkaListener(...) void onEvent(TransactionEvent e) {...} }
@Component class NotifyConsumer   { @KafkaListener(...) void onEvent(TransactionEvent e) {...} }
@Component class AnalyticsConsumer{ @KafkaListener(...) void onEvent(TransactionEvent e) {...} }
// Each decoupled — adding a new consumer doesn't change the publisher
```

---

**Decorator Pattern — adding behavior without modifying:**
```java
// Core repository
interface AccountRepository {
    Account findById(String id);
    void save(Account account);
}

// Decorator: adds caching without touching the original
@Primary @RequiredArgsConstructor
class CachingAccountRepository implements AccountRepository {
    private final AccountRepository delegate; // wraps the real one
    private final RedisTemplate<String, Account> cache;

    @Override
    public Account findById(String id) {
        String cacheKey = "account:" + id;
        Account cached = cache.opsForValue().get(cacheKey);
        if (cached != null) return cached;

        Account account = delegate.findById(id);
        cache.opsForValue().set(cacheKey, account, Duration.ofMinutes(5));
        return account;
    }
}

// Decorator: adds audit logging
class AuditingAccountRepository implements AccountRepository {
    private final AccountRepository delegate;

    @Override
    public void save(Account account) {
        auditLogger.log("PRE_SAVE", account.getId());
        delegate.save(account);
        auditLogger.log("POST_SAVE", account.getId());
    }
}
```

---

**Saga Pattern — distributed transactions across microservices:**
```java
// Choreography-based Saga — each service reacts to events and emits its own

// Step 1: OrderService creates order and publishes event
@Service class OrderService {
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = orderRepo.save(new Order(request, "PENDING"));
        eventPublisher.publish(new OrderCreatedEvent(order.getId(), request.getItems()));
        return order;
    }
}

// Step 2: InventoryService reserves stock
@Component class InventoryEventHandler {
    @KafkaListener(topics = "order.created")
    public void onOrderCreated(OrderCreatedEvent event) {
        try {
            inventoryService.reserve(event.getOrderId(), event.getItems());
            eventPublisher.publish(new InventoryReservedEvent(event.getOrderId()));
        } catch (InsufficientStockException e) {
            // Publish compensating event
            eventPublisher.publish(new InventoryReservationFailedEvent(event.getOrderId()));
        }
    }
}

// Step 3: PaymentService charges customer
@Component class PaymentEventHandler {
    @KafkaListener(topics = "inventory.reserved")
    public void onInventoryReserved(InventoryReservedEvent event) {
        try {
            paymentService.charge(event.getOrderId());
            eventPublisher.publish(new PaymentCompletedEvent(event.getOrderId()));
        } catch (PaymentFailedException e) {
            // Compensating transaction — release inventory
            eventPublisher.publish(new PaymentFailedEvent(event.getOrderId()));
        }
    }
}

// Compensation handler — InventoryService listens for PaymentFailed
@Component class CompensatingHandler {
    @KafkaListener(topics = "payment.failed")
    public void onPaymentFailed(PaymentFailedEvent event) {
        inventoryService.release(event.getOrderId()); // undo reservation
        orderService.cancel(event.getOrderId());
    }
}
```

---

**Strangler Fig Pattern — monolith migration:**
```
Monolith                  API Gateway
   │                           │
   │◄──── /old-endpoint ───────┤
   │                           │
   │                           │
New Service                    │
   │◄──── /new-endpoint ───────┤
```

Strategy used at IBM:
1. Identify bounded contexts in the monolith
2. Build new Spring Boot service alongside monolith
3. Configure API Gateway to route new traffic to new service
4. Gradually shift traffic (feature flags, canary routing)
5. Decommission the old module once traffic = 0%
