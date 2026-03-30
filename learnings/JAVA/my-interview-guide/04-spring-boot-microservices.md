# Spring Boot & Microservices — Interview Guide (7+ YOE)

---

## 1. Spring Core — DI & Bean Lifecycle

### Q1. How does Spring's dependency injection work? What are the injection types?

Spring manages a container of beans (`ApplicationContext`). When your application starts, Spring:
1. Scans for components (`@Component`, `@Service`, `@Repository`, `@Controller`)
2. Resolves dependencies
3. Injects them in the correct order

**Three injection types:**
```java
// 1. Constructor Injection — RECOMMENDED
// Immutable, testable, dependencies are explicit, no circular dependency at runtime
@Service
public class PaymentService {
    private final AccountRepository accountRepo;
    private final KafkaProducer kafkaProducer;

    // Spring auto-injects if single constructor (no @Autowired needed in Spring 4.3+)
    public PaymentService(AccountRepository accountRepo, KafkaProducer kafkaProducer) {
        this.accountRepo = accountRepo;
        this.kafkaProducer = kafkaProducer;
    }
}

// 2. Field Injection — convenient but avoid in production code
// Can't be final, requires reflection (harder to test without Spring context)
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService; // bad for testing, hides dependencies
}

// 3. Setter Injection — for optional dependencies
@Service
public class NotificationService {
    private AuditService auditService;

    @Autowired(required = false) // optional — gracefully absent
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
}
```

**Disambiguation with `@Qualifier` and `@Primary`:**
```java
public interface PaymentProcessor { PaymentResult process(Payment p); }

@Component("upiProcessor")
public class UPIProcessor implements PaymentProcessor { ... }

@Component("cardProcessor")
@Primary // default when no @Qualifier specified
public class CardProcessor implements PaymentProcessor { ... }

@Service
public class CheckoutService {
    // Injects CardProcessor (primary)
    @Autowired
    private PaymentProcessor defaultProcessor;

    // Injects UPIProcessor specifically
    @Autowired
    @Qualifier("upiProcessor")
    private PaymentProcessor upiProcessor;
}
```

---

### Q2. Explain the Spring Bean lifecycle.

```
Instantiation → Property Injection → @PostConstruct → 
ApplicationContext ready → Business Use → @PreDestroy → Destroy
```

```java
@Component
@Slf4j
public class DatabaseConnectionPool {

    @Value("${db.pool.size:10}")
    private int poolSize;

    private ConnectionPool pool;

    // Called AFTER properties are injected — safe to use @Value fields here
    @PostConstruct
    public void initPool() {
        log.info("Initializing connection pool with size: {}", poolSize);
        this.pool = new ConnectionPool(poolSize);
        pool.warmUp(); // pre-create connections
    }

    // Called BEFORE bean is destroyed — clean up resources
    @PreDestroy
    public void closePool() {
        log.info("Closing connection pool");
        if (pool != null) pool.closeAll();
    }
}
```

**Bean scopes:**
```java
@Scope("singleton")    // Default — one instance per ApplicationContext
@Scope("prototype")    // New instance on every injection/getBean()
@Scope("request")      // One per HTTP request (web apps)
@Scope("session")      // One per HTTP session (web apps)

// Prototype in a Singleton — common gotcha
@Component
public class ReportService {
    @Autowired
    private PrototypeBean bean; // WRONG — injected once, always same instance!

    // Fix: use ObjectProvider or ApplicationContext.getBean()
    @Autowired
    private ObjectProvider<PrototypeBean> beanProvider;

    public void generateReport() {
        PrototypeBean bean = beanProvider.getObject(); // fresh instance each time
    }
}
```

---

## 2. Spring Boot Auto-Configuration

### Q3. How does Spring Boot auto-configuration work?

`@SpringBootApplication` = `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`

`@EnableAutoConfiguration` loads `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` — a list of auto-configuration classes.

Each auto-config class uses conditionals:
```java
// Example: DataSource auto-configuration (simplified)
@Configuration
@ConditionalOnClass(DataSource.class)           // only if driver jar on classpath
@ConditionalOnMissingBean(DataSource.class)     // only if no custom DataSource defined
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "url")
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }
}
```

**Override auto-configuration:**
```java
// Option 1: Define your own bean — auto-config backs off (@ConditionalOnMissingBean)
@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://...");
        config.setMaximumPoolSize(50);           // custom pool settings
        config.setConnectionTimeout(3000);
        return new HikariDataSource(config);
    }
}

// Option 2: Exclude auto-configuration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```

> **Debug tip:** Run with `--debug` flag → Spring prints "CONDITIONS EVALUATION REPORT" showing which auto-configs were applied and why others were skipped.

---

## 3. REST API Design

### Q4. What are Spring Boot REST best practices for a senior developer?

```java
@RestController
@RequestMapping("/api/v1/transactions")
@Validated
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 1. Return ResponseEntity for full control over status codes
    // 2. Use DTOs, never expose entities directly
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable String id) {
        return transactionService.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    }

    // 3. Pagination — never return unbounded lists
    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(transactionService.findAll(pageable));
    }

    // 4. Validate input with Bean Validation
    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody CreateTransactionRequest request) {
        TransactionResponse response = transactionService.create(request);
        URI location = URI.create("/api/v1/transactions/" + response.getId());
        return ResponseEntity.created(location).body(response); // 201 Created
    }

    // 5. PATCH for partial update, PUT for full replace
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest request) {
        transactionService.updateStatus(id, request.getStatus());
        return ResponseEntity.noContent().build(); // 204
    }
}
```

**Global exception handling:**
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .collect(Collectors.toList());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("VALIDATION_FAILED", errors.toString(), Instant.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception for request {}", req.getRequestURI(), ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred", Instant.now()));
    }
}
```

---

## 4. Microservices Patterns

### Q5. How do microservices communicate? Sync vs async — when to use each?

**Synchronous (REST / gRPC):**
```java
// Feign Client — declarative REST client (sync, blocking)
@FeignClient(name = "account-service", fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @GetMapping("/api/v1/accounts/{id}")
    AccountDTO getAccount(@PathVariable String id);

    @PostMapping("/api/v1/accounts/{id}/debit")
    void debit(@PathVariable String id, @RequestBody DebitRequest request);
}

// Fallback for when account-service is down
@Component
class AccountClientFallback implements FallbackFactory<AccountClient> {
    @Override
    public AccountClient create(Throwable cause) {
        return new AccountClient() {
            @Override
            public AccountDTO getAccount(String id) {
                throw new ServiceUnavailableException("Account service unavailable: " + cause.getMessage());
            }
            @Override
            public void debit(String id, DebitRequest request) {
                throw new ServiceUnavailableException("Account service unavailable");
            }
        };
    }
}
```

**Asynchronous (Kafka):**
```java
// Producer — publish event
@Service
@RequiredArgsConstructor
public class TransactionEventPublisher {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void publishTransactionCompleted(Transaction tx) {
        TransactionEvent event = TransactionEvent.builder()
            .transactionId(tx.getId())
            .accountId(tx.getAccountId())
            .amount(tx.getAmount())
            .status("COMPLETED")
            .timestamp(Instant.now())
            .build();

        kafkaTemplate.send("transactions.completed", tx.getAccountId(), event)
            .addCallback(
                result  -> log.debug("Event published: {}", tx.getId()),
                failure -> log.error("Failed to publish event: {}", tx.getId(), failure)
            );
    }
}

// Consumer — with error handling and DLT
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "transactions.completed", groupId = "notification-group")
    public void onTransactionCompleted(TransactionEvent event,
                                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                       @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Processing event from partition {} offset {}", partition, offset);
        notificationService.sendTransactionAlert(event);
    }

    // Dead Letter Topic handler — for events that failed after all retries
    @KafkaListener(topics = "transactions.completed.DLT", groupId = "notification-dlt-group")
    public void handleDlt(TransactionEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String error) {
        log.error("Event in DLT: {} reason: {}", event.getTransactionId(), error);
        alertOpsTeam(event, error);
    }
}
```

**When to use which:**
| | Synchronous (REST/gRPC) | Asynchronous (Kafka) |
|---|---|---|
| Response needed immediately | ✅ | ❌ |
| Caller can proceed without response | ❌ | ✅ |
| Decoupling services | Poor | Excellent |
| High throughput | Limited by slowest service | Independent scaling |
| Ordering guarantee | Not built-in | Per partition |
| Use case | Query, real-time validation | Notifications, audit, analytics |

---

## 5. Circuit Breaker — Resilience4j

### Q6. How does the Resilience4j circuit breaker work? Explain all states.

```
CLOSED (normal) → OPEN (failure threshold exceeded) → HALF_OPEN (testing) → CLOSED/OPEN
```

```java
// Configuration
@Bean
public Customizer<Resilience4JCircuitBreakerFactory> globalCircuitBreakerConfig() {
    CircuitBreakerConfig config = CircuitBreakerConfig.custom()
        .slidingWindowType(SlidingWindowType.COUNT_BASED)
        .slidingWindowSize(10)                    // evaluate last 10 calls
        .failureRateThreshold(50)                 // open if ≥50% fail
        .waitDurationInOpenState(Duration.ofSeconds(30)) // wait before half-open
        .permittedNumberOfCallsInHalfOpenState(5) // test with 5 calls
        .automaticTransitionFromOpenToHalfOpenEnabled(true)
        .build();

    return factory -> factory.configureDefault(id -> 
        new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(config)
            .build());
}

// Usage with fallback
@Service
public class InventoryService {

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "getInventoryFallback")
    @Retry(name = "inventoryService")
    @Bulkhead(name = "inventoryService")
    public InventoryStatus checkInventory(String productId) {
        return inventoryClient.check(productId); // downstream call
    }

    // Fallback — must have same signature + Throwable parameter
    private InventoryStatus getInventoryFallback(String productId, Throwable ex) {
        log.warn("Inventory service unavailable for product {}, using cached", productId);
        return cache.getOrDefault(productId, InventoryStatus.UNKNOWN);
    }
}
```

**Retry with exponential backoff:**
```java
RetryConfig retryConfig = RetryConfig.custom()
    .maxAttempts(3)
    .waitDuration(Duration.ofMillis(500))
    .retryOnException(ex -> ex instanceof ConnectException)    // retry on network errors
    .ignoreException(ex -> ex instanceof BusinessException)    // don't retry business errors
    .build();
```

---

## 6. API Gateway & Service Discovery

### Q7. What does an API Gateway do? How does service discovery work?

**API Gateway (Spring Cloud Gateway):**
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: account-service
          uri: lb://ACCOUNT-SERVICE   # lb:// = load-balanced via Eureka
          predicates:
            - Path=/api/v1/accounts/**
          filters:
            - StripPrefix=0
            - name: CircuitBreaker
              args:
                name: accountCircuitBreaker
                fallbackUri: forward:/fallback/accounts
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
```

**Service Discovery (Eureka):**
```java
// Service registers itself on startup
@SpringBootApplication
@EnableEurekaClient
public class AccountServiceApplication { ... }

// application.yml
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server:8761/eureka/
  instance:
    preferIpAddress: true
    lease-renewal-interval-in-seconds: 10    // heartbeat every 10s
    lease-expiration-duration-in-seconds: 30 // deregister if no heartbeat in 30s
```

---

## 7. Spring Profiles & Config Management

### Q8. How do you manage configuration across environments?

```java
// Profile-specific beans
@Configuration
public class SecurityConfig {

    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurity(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // dev: open
            .csrf(csrf -> csrf.disable())
            .build();
    }

    @Bean
    @Profile({"staging", "prod"})
    public SecurityFilterChain prodSecurity(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
    }
}
```

**Externalized config hierarchy (highest to lowest priority):**
1. Command-line arguments: `--spring.datasource.url=jdbc:...`
2. `SPRING_DATASOURCE_URL` environment variable
3. `application-{profile}.yml`
4. `application.yml`
5. Default values in `@Value`

```java
// Type-safe configuration properties
@ConfigurationProperties(prefix = "banking")
@Validated
public record BankingProperties(
    @NotNull Duration transactionTimeout,
    @Min(1) @Max(100) int maxRetries,
    @NotEmpty String defaultCurrency
) {}
```
