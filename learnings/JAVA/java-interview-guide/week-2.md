## 🧠 **DAY 1–2: Spring Boot Core**

---

### ✅ 1. **Autowiring & Bean Lifecycle**

#### 🔹 a. **Autowiring in Spring**

* Dependency injection framework.
* `@Autowired`: constructor, field, or setter.
* `@Qualifier` for disambiguation.
* `@Primary` for default bean resolution.

**🧪 Practice:**

```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```

**💡 Task:** Inject a `PaymentService` using constructor injection. Add two implementations and use `@Qualifier` to pick one.

---

#### 🔹 b. **Bean Lifecycle Hooks**

* `@PostConstruct` → after bean initialization.
* `@PreDestroy` → before bean destruction.
* `InitializingBean`, `DisposableBean` interfaces.

**🧪 Practice:**

```java
@Component
public class MyBean {
    @PostConstruct
    public void init() { System.out.println("Initialized"); }

    @PreDestroy
    public void destroy() { System.out.println("Destroyed"); }
}
```

**💡 Task:** Implement a bean that logs init/destroy phases. Observe logs during app start/stop.

---

### ✅ 2. **Profiles & Actuator**

#### 🔹 a. **Profiles**

* `@Profile("dev")`: activates bean only for `dev` profile.
* `application-dev.properties` for env-specific config.

**🧪 Practice:**

```java
@Profile("dev")
@Service
public class DevEmailService implements EmailService {}
```

**💡 Task:** Create `EmailService` beans for `dev` and `prod` profiles.

---

#### 🔹 b. **Spring Boot Actuator**

* Production-ready features like `/actuator/health`.
* Can expose metrics, info, env properties.

**🧪 Practice:**

```yaml
management.endpoints.web.exposure.include=*
```

**💡 Task:** Add a custom health check that verifies DB connection.

---

### ✅ 3. **REST Controller Design Best Practices**

* Use `@RestController`.
* Return `ResponseEntity`.
* Validation with `@Valid`.
* Proper HTTP status codes.
* Pagination support.

**🧪 Practice:**

```java
@GetMapping("/products")
public ResponseEntity<List<Product>> getProducts() {
    return ResponseEntity.ok(service.getAll());
}
```

**💡 Task:** Design REST API for `/api/customers` supporting `GET`, `POST` with validation and pagination params.

---

## 🧠 **DAY 3–4: Microservices Architecture**

---

### ✅ 1. **Inter-service Communication**

#### 🔹 a. **Feign Client**

* Declarative REST client.
* Auto-handles fallback integration.

**🧪 Practice:**

```java
@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/orders/{id}")
    Order getOrder(@PathVariable Long id);
}
```

**💡 Task:** Define a Feign client to call `product-service`.

---

#### 🔹 b. **Kafka Communication**

* Async communication.
* Durable and decoupled.

**🧪 Practice:**

```java
@KafkaListener(topics = "orders")
public void consume(Order order) {
    // process
}
```

**💡 Task:** Publish an `OrderCreated` event from one service and consume it in another.

---

### ✅ 2. **Service Discovery**

* Eureka or Consul.
* `@EnableEurekaClient`.

**💡 Task:** Register 2 services with Eureka and call one from another using service name.

---

### ✅ 3. **Circuit Breaker with Resilience4j**

**🧪 Practice:**

```java
@CircuitBreaker(name = "orderService", fallbackMethod = "fallback")
public Order getOrder() { ... }
```

**💡 Task:** Add a fallback for failed `FeignClient` call.

---

### ✅ 4. **Config Server**

* Centralized config for multiple services.

**💡 Task:** Set up Spring Cloud Config Server and externalize `application.yml`.

---

### ✅ 5. **API Gateway**

* Routing, filtering, load balancing.

**🧪 Example:**

```yaml
spring.cloud.gateway.routes:
  - id: customer
    uri: lb://CUSTOMER-SERVICE
    predicates:
      - Path=/customers/**
```

**💡 Task:** Route `/customers/**` and `/orders/**` through Spring Cloud Gateway.

---

## 🧠 **DAY 5: JPA + Hibernate**

---

### ✅ 1. **Entity Relationships**

* `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`.

**🧪 Example:**

```java
@OneToMany(mappedBy = "customer")
private List<Order> orders;
```

**💡 Task:** Model `Customer` → `Order` (OneToMany) relationship.

---

### ✅ 2. **Lazy vs Eager Loading**

* `FetchType.LAZY` (default for collections).
* Avoid N+1 issues.

**💡 Task:** Load a customer and explain behavior for `LAZY` and `EAGER`.

---

### ✅ 3. **N+1 Problem Mitigation**

* Use `join fetch`.

**🧪 Example:**

```java
@Query("select c from Customer c join fetch c.orders")
```

**💡 Task:** Write JPQL to fetch customers with their orders.

---

### ✅ 4. **Projections**

* Interface or DTO-based.

**🧪 Example:**

```java
public interface CustomerView {
    String getName();
    String getEmail();
}
```

**💡 Task:** Create a projection for `Customer(name, email)`.

---

### ✅ 5. **Native Queries**

**🧪 Example:**

```java
@Query(value = "SELECT * FROM customer WHERE name = :name", nativeQuery = true)
```

**💡 Task:** Write native query to fetch customers with `created_at > today`.

---

## 🧠 **DAY 6–7: Integration and Testing**

---

### ✅ 1. **Exception Handling**

* `@ControllerAdvice` + `@ExceptionHandler`.

**🧪 Example:**

```java
@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<ErrorResponse> handleNotFound() { ... }
```

**💡 Task:** Global handler for `NotFoundException` and `ValidationException`.

---

### ✅ 2. **Validation**

* Bean validation: `@NotNull`, `@Size`, `@Pattern`.

**💡 Task:** Validate nested DTO: `Customer` with `Address`.

---

### ✅ 3. **DTO Mapping (MapStruct)**

**🧪 Example:**

```java
@Mapper
public interface CustomerMapper {
    CustomerDTO toDTO(Customer entity);
}
```

**💡 Task:** Configure a mapper for `Order` ↔ `OrderDTO`.

---

### ✅ 4. **MockMvc + Test Annotations**

#### 🔹 a. `@WebMvcTest`

```java
@WebMvcTest(CustomerController.class)
class CustomerControllerTest { }
```

#### 🔹 b. `@DataJpaTest`

```java
@DataJpaTest
class CustomerRepositoryTest { }
```

**💡 Task:** Write unit test for `/api/customers/{id}`.

---

## 🧩 **Practical Coding Set (Week 2)**

1️⃣ Build a REST API for `/api/products` with CRUD, validation, pagination.
2️⃣ Model `Customer` ↔ `Order` using JPA annotations, test `LAZY` loading behavior.
3️⃣ Implement `FeignClient` + fallback with Resilience4j circuit breaker.
4️⃣ Configure `Spring Cloud Gateway` to route `/customers` and `/orders`.
5️⃣ Write `@WebMvcTest` for `CustomerController`.
6️⃣ Create a `MapStruct` mapper for `Order` and test mapping logic.

---

✅ **Mock Interview Prep:**

| **Topic**         | **Question**                                           |
| ----------------- | ------------------------------------------------------ |
| Spring Boot       | What is bean lifecycle? How do you customize it?       |
| Microservices     | Difference between Feign and RestTemplate? Pros/Cons?  |
| Service Discovery | How does Eureka client register and deregister?        |
| Circuit Breaker   | Explain circuit breaker pattern and fallback handling. |
| JPA               | Lazy vs Eager fetch impact on performance.             |
| DTO Mapping       | Why use MapStruct? How is it better than ModelMapper?  |
| Validation        | How to validate nested DTOs?                           |
| Testing           | How do `@WebMvcTest` and `@DataJpaTest` differ?        |
