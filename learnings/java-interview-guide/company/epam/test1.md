### **Streams API**
#### **Concept**:
Streams process collections of data in a declarative, functional style. Operations can be **intermediate** (e.g., `filter`, `map`) or **terminal** (e.g., `collect`, `reduce`).

#### **Key Operations**:
1. **Filtering**: Filter elements based on a condition.
   ```java
   List<Integer> numbers = List.of(1, 2, 3, 4, 5);
   List<Integer> evenNumbers = numbers.stream()
       .filter(n -> n % 2 == 0)
       .collect(Collectors.toList());
   System.out.println(evenNumbers); // Output: [2, 4]
   ```

2. **Mapping**: Transform each element.
   ```java
   List<String> names = List.of("Alice", "Bob", "Charlie");
   List<Integer> lengths = names.stream()
       .map(String::length)
       .collect(Collectors.toList());
   System.out.println(lengths); // Output: [5, 3, 7]
   ```

3. **Sorting**: Sort elements in natural or custom order.
   ```java
   List<String> fruits = List.of("apple", "banana", "cherry");
   List<String> sortedFruits = fruits.stream()
       .sorted()
       .collect(Collectors.toList());
   System.out.println(sortedFruits); // Output: [apple, banana, cherry]
   ```

4. **Reduction**: Aggregate data (e.g., sum, max).
   ```java
   List<Integer> numbers = List.of(1, 2, 3, 4);
   int sum = numbers.stream()
       .reduce(0, Integer::sum); // Start with 0, add each number
   System.out.println(sum); // Output: 10
   ```

---

### **Optional**
#### **Concept**:
Optional is a container object that avoids `null` pointer exceptions by explicitly handling the absence of a value.

#### **Example**:
```java
Optional<String> optionalName = Optional.ofNullable(null);

// Safe way to handle null
String name = optionalName.orElse("Default Name");
System.out.println(name); // Output: Default Name

// Using ifPresent
optionalName.ifPresent(System.out::println); // No output
```

---

### **Functional Interfaces**
#### **Concept**:
Functional interfaces are interfaces with a single abstract method. Java 8 provides common ones:

1. **Predicate**: Takes a value, returns `boolean`.
   ```java
   Predicate<Integer> isEven = n -> n % 2 == 0;
   System.out.println(isEven.test(4)); // Output: true
   ```

2. **Consumer**: Takes a value, performs an action.
   ```java
   Consumer<String> print = System.out::println;
   print.accept("Hello, World!"); // Output: Hello, World!
   ```

3. **Supplier**: Returns a value, no input.
   ```java
   Supplier<Double> randomValue = Math::random;
   System.out.println(randomValue.get()); // Output: random number
   ```

4. **Function**: Takes a value, returns a result.
   ```java
   Function<String, Integer> length = String::length;
   System.out.println(length.apply("Java")); // Output: 4
   ```

---

### **Lambda Expressions**
#### **Concept**:
Simplifies the creation of anonymous functions by replacing boilerplate code.

#### **Example**:
```java
// Without Lambda
Comparator<Integer> comparator = new Comparator<Integer>() {
   public int compare(Integer a, Integer b) {
       return a - b;
   }
};

// With Lambda
Comparator<Integer> comparatorLambda = (a, b) -> a - b;
```

---

### **Method References**
#### **Concept**:
Shorthand for calling a method in a lambda. Types:
1. **Static Method**:
   ```java
   Function<Integer, String> toString = String::valueOf;
   System.out.println(toString.apply(123)); // Output: "123"
   ```

2. **Instance Method**:
   ```java
   Consumer<String> printer = System.out::println;
   printer.accept("Hello!"); // Output: Hello!
   ```

3. **Constructor**:
   ```java
   Supplier<List<String>> listCreator = ArrayList::new;
   List<String> list = listCreator.get();
   ```

---

### **Collectors**
#### **Concept**:
Collectors are used to aggregate stream elements into a collection or summary.

#### **Examples**:
1. **Grouping**:
   ```java
   List<String> items = List.of("apple", "banana", "apple", "orange");
   Map<String, Long> count = items.stream()
       .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
   System.out.println(count); // Output: {apple=2, banana=1, orange=1}
   ```

2. **Partitioning**:
   ```java
   List<Integer> numbers = List.of(1, 2, 3, 4, 5);
   Map<Boolean, List<Integer>> partitioned = numbers.stream()
       .collect(Collectors.partitioningBy(n -> n % 2 == 0));
   System.out.println(partitioned); // Output: {false=[1, 3, 5], true=[2, 4]}
   ```

3. **Joining**:
   ```java
   List<String> names = List.of("Alice", "Bob", "Charlie");
   String result = names.stream()
       .collect(Collectors.joining(", "));
   System.out.println(result); // Output: Alice, Bob, Charlie
   ```

---

### **Default and Static Methods in Interfaces**
#### **Concept**:
Allows interfaces to have default implementations or utility methods.

#### **Example**:
```java
interface Calculator {
   default int add(int a, int b) {
       return a + b;
   }

   static int multiply(int a, int b) {
       return a * b;
   }
}

class MyCalculator implements Calculator {}

MyCalculator calc = new MyCalculator();
System.out.println(calc.add(2, 3)); // Output: 5
System.out.println(Calculator.multiply(2, 3)); // Output: 6
```

---

### **New Date/Time API (`java.time`)**
#### **Concept**:
Provides a modern, thread-safe API for date and time handling.

#### **Examples**:
1. **LocalDate**:
   ```java
   LocalDate today = LocalDate.now();
   System.out.println(today); // Output: Current date
   ```

2. **LocalTime**:
   ```java
   LocalTime now = LocalTime.now();
   System.out.println(now); // Output: Current time
   ```

3. **LocalDateTime**:
   ```java
   LocalDateTime now = LocalDateTime.now();
   System.out.println(now); // Output: Current date and time
   ```

4. **Formatting/Parsing**:
   ```java
   LocalDate date = LocalDate.parse("2023-01-01");
   String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
   System.out.println(formattedDate); // Output: 01/01/2023
   ```

5. **Duration**:
   ```java
   Duration duration = Duration.between(LocalTime.NOON, LocalTime.now());
   System.out.println(duration.toMinutes()); // Output: Minutes between
   ```

--- 

### Low-Level Design for a Hotel Booking System

---

#### **Core Components**
1. **Entities**:
   - **Hotel**
   - **Room**
   - **Booking**
   - **Customer**

2. **Services**:
   - **RoomService** (Add rooms, check availability)
   - **BookingService** (Make, cancel bookings)

3. **Enums**:
   - RoomType (SINGLE, DOUBLE, SUITE)
   - BookingStatus (CONFIRMED, CANCELLED, PENDING)

---

### Code Implementation

#### **Entities**

1. **Room**
```java
import java.util.UUID;

class Room {
    private String roomId;
    private RoomType roomType;
    private boolean isAvailable;

    public Room(RoomType roomType) {
        this.roomId = UUID.randomUUID().toString();
        this.roomType = roomType;
        this.isAvailable = true;
    }

    public String getRoomId() { return roomId; }
    public RoomType getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
```

2. **Booking**
```java
import java.time.LocalDate;

class Booking {
    private String bookingId;
    private String customerId;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;

    public Booking(String customerId, String roomId, LocalDate startDate, LocalDate endDate) {
        this.bookingId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.CONFIRMED;
    }

    public String getBookingId() { return bookingId; }
    public String getRoomId() { return roomId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
```

3. **Enums**
```java
enum RoomType {
    SINGLE, DOUBLE, SUITE
}

enum BookingStatus {
    CONFIRMED, CANCELLED, PENDING
}
```

---

#### **Service Classes**

1. **RoomService**
```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(RoomType roomType) {
        Room room = new Room(roomType);
        rooms.add(room);
        System.out.println("Room added: " + room.getRoomId());
    }

    public List<Room> checkAvailability(RoomType roomType) {
        return rooms.stream()
                .filter(room -> room.isAvailable() && room.getRoomType() == roomType)
                .collect(Collectors.toList());
    }

    public void markRoomAsBooked(String roomId) {
        rooms.stream()
                .filter(room -> room.getRoomId().equals(roomId))
                .findFirst()
                .ifPresent(room -> room.setAvailable(false));
    }

    public void markRoomAsAvailable(String roomId) {
        rooms.stream()
                .filter(room -> room.getRoomId().equals(roomId))
                .findFirst()
                .ifPresent(room -> room.setAvailable(true));
    }
}
```

2. **BookingService**
```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private RoomService roomService;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    public Booking createBooking(String customerId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        List<Room> availableRooms = roomService.checkAvailability(roomType);

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available of type: " + roomType);
            return null;
        }

        Room room = availableRooms.get(0); // Pick the first available room
        roomService.markRoomAsBooked(room.getRoomId());

        Booking booking = new Booking(customerId, room.getRoomId(), startDate, endDate);
        bookings.add(booking);
        System.out.println("Booking confirmed: " + booking.getBookingId());
        return booking;
    }

    public void cancelBooking(String bookingId) {
        bookings.stream()
                .filter(booking -> booking.getBookingId().equals(bookingId))
                .findFirst()
                .ifPresent(booking -> {
                    booking.setStatus(BookingStatus.CANCELLED);
                    roomService.markRoomAsAvailable(booking.getRoomId());
                    System.out.println("Booking cancelled: " + bookingId);
                });
    }
}
```

---

#### **Example Usage**

```java
import java.time.LocalDate;

public class HotelBookingSystem {
    public static void main(String[] args) {
        RoomService roomService = new RoomService();
        BookingService bookingService = new BookingService(roomService);

        // Add rooms
        roomService.addRoom(RoomType.SINGLE);
        roomService.addRoom(RoomType.DOUBLE);

        // Check availability
        System.out.println("Available rooms: " + roomService.checkAvailability(RoomType.SINGLE).size());

        // Create a booking
        Booking booking = bookingService.createBooking("customer1", RoomType.SINGLE, LocalDate.now(), LocalDate.now().plusDays(3));

        // Cancel the booking
        if (booking != null) {
            bookingService.cancelBooking(booking.getBookingId());
        }
    }
}
```

---

### Key Operations

1. **Add Room**: Add rooms by specifying the type (`RoomService#addRoom`).
2. **Check Availability**: Filter available rooms by type (`RoomService#checkAvailability`).
3. **Create Booking**: Check room availability, book the first available room, and mark it unavailable (`BookingService#createBooking`).
4. **Cancel Booking**: Update booking status and release the room (`BookingService#cancelBooking`).


----

In Spring Boot, microservices communicate using the following mechanisms:

### 1. **Synchronous Communication**  
   - **REST APIs**: Services communicate using HTTP with REST endpoints.
     - **Tools**: `RestTemplate` (legacy), `WebClient` (from Spring WebFlux).
     - **Example**:
       ```java
       WebClient webClient = WebClient.create("http://service-url");
       String response = webClient.get()
                                  .uri("/api/data")
                                  .retrieve()
                                  .bodyToMono(String.class)
                                  .block();
       ```
   - **gRPC**: High-performance RPC framework for synchronous calls.

---

### 2. **Asynchronous Communication**  
   - **Message Queues**: Services exchange messages via brokers like Kafka, RabbitMQ.
     - **Spring Kafka**:
       ```java
       @KafkaListener(topics = "topic-name", groupId = "group-id")
       public void consume(String message) {
           System.out.println("Received: " + message);
       }
       ```
     - **Spring AMQP (RabbitMQ)**:
       ```java
       @RabbitListener(queues = "queue-name")
       public void listen(String message) {
           System.out.println("Received: " + message);
       }
       ```

---

### 3. **Service Discovery**  
   - **Eureka (Netflix OSS)**: Enables dynamic discovery of service locations.
     - Services register with Eureka, and other services query it.
     - **Example**:
       ```properties
       spring.application.name=service-name
       eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka
       ```

---

### 4. **Event-Driven Communication**  
   - **Spring Cloud Stream**: Simplifies producing and consuming events with Kafka or RabbitMQ.
     - **Example**:
       ```java
       @EnableBinding(Source.class)
       public class MessageProducer {
           private final MessageChannel output;

           public MessageProducer(Source source) {
               this.output = source.output();
           }

           public void send(String message) {
               output.send(MessageBuilder.withPayload(message).build());
           }
       }
       ```

---

### 5. **API Gateway**  
   - **Spring Cloud Gateway**: Acts as a reverse proxy for routing and load balancing.
     - **Example Configuration**:
       ```yaml
       spring:
         cloud:
           gateway:
             routes:
               - id: user-service
                 uri: lb://USER-SERVICE
                 predicates:
                   - Path=/users/**
       ```

---

### 6. **Feign Client**  
   - Declarative REST client for making HTTP calls between services.
     - **Example**:
       ```java
       @FeignClient(name = "user-service")
       public interface UserServiceClient {
           @GetMapping("/users/{id}")
           User getUserById(@PathVariable("id") String id);
       }
       ```

       ### **Transaction Management in Spring Boot and Hibernate**

Spring Boot simplifies transaction management by integrating Spring's **Declarative Transaction Management** with **Hibernate** as the ORM.

---

### **Key Concepts**

#### 1. **Transactions**
A transaction ensures a set of operations execute as a single unit of work:
- **Atomicity**: All operations succeed or none.
- **Consistency**: Ensures data validity before and after the transaction.
- **Isolation**: Concurrent transactions are isolated from each other.
- **Durability**: Changes persist even after a system crash.

---

#### 2. **@Transactional Annotation**
- Marks methods or classes as transactional.
- Configures rollback, isolation, propagation, etc.
- Applied at:
  - **Class Level**: All methods in the class inherit the transactional behavior.
  - **Method Level**: Overrides class-level configurations.

**Example:**
```java
@Transactional
public void updateAccountBalance(Long accountId, Double amount) {
    Account account = accountRepository.findById(accountId).orElseThrow();
    account.setBalance(account.getBalance() + amount);
    accountRepository.save(account);
}
```

---

#### 3. **Propagation**
Defines how a method participates in an existing transaction.

| Propagation Type            | Description                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| **REQUIRED** (Default)      | Joins an existing transaction or creates a new one if none exists.         |
| **REQUIRES_NEW**            | Suspends the current transaction and starts a new one.                     |
| **NESTED**                  | Creates a nested transaction within the current transaction.               |
| **SUPPORTS**                | Runs in a transaction if one exists, otherwise executes non-transactionally.|
| **NOT_SUPPORTED**           | Suspends the current transaction and runs non-transactionally.             |
| **MANDATORY**               | Requires an active transaction, throws an exception if none exists.        |
| **NEVER**                   | Must execute non-transactionally, throws an exception if a transaction exists.|

**Example:**
```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void logTransaction(Long transactionId, String status) {
    TransactionLog log = new TransactionLog(transactionId, status);
    transactionLogRepository.save(log);
}
```

---

#### 4. **Isolation Levels**
Specifies the degree of isolation between concurrent transactions.

| Isolation Level          | Description                                                                                      |
|--------------------------|--------------------------------------------------------------------------------------------------|
| **DEFAULT**              | Uses the database's default isolation level.                                                   |
| **READ_UNCOMMITTED**     | Allows dirty reads (read data modified but not committed by other transactions).                |
| **READ_COMMITTED**       | Prevents dirty reads but allows non-repeatable reads.                                           |
| **REPEATABLE_READ**      | Prevents dirty and non-repeatable reads but allows phantom reads.                               |
| **SERIALIZABLE**         | Fully isolates transactions (highest level, slowest performance).                              |

**Example:**
```java
@Transactional(isolation = Isolation.SERIALIZABLE)
public void transferMoney(Long fromAccountId, Long toAccountId, Double amount) {
    // Code for money transfer
}
```

---

### **Rollback Behavior**

1. **Default Behavior**:
   - Rolls back for unchecked exceptions (`RuntimeException` and `Error`).
   - Does not roll back for checked exceptions (`Exception`).

2. **Custom Rollback**:
   Use `rollbackFor` or `noRollbackFor`.
   ```java
   @Transactional(rollbackFor = {CustomException.class})
   public void processOrder(Order order) throws CustomException {
       // Code for processing order
   }
   ```

---

### **Declarative vs Programmatic Transactions**

| Feature               | Declarative (`@Transactional`)                  | Programmatic (`TransactionTemplate`)    |
|-----------------------|-------------------------------------------------|-----------------------------------------|
| **Ease of Use**       | Simple, less boilerplate.                       | More control over transaction flow.     |
| **Use Case**          | Recommended for most cases.                    | Fine-grained control over transactions. |

**Programmatic Example:**
```java
@Autowired
private PlatformTransactionManager transactionManager;

public void executeTransaction() {
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.execute(status -> {
        // Transactional code
        return null;
    });
}
```

---

### **Best Practices**

1. Use `@Transactional` at the **service layer** (not repository or controller).
2. Ensure consistent transaction boundaries across layers.
3. Avoid mixing **REQUIRES_NEW** and nested transactions unnecessarily.
4. Use appropriate **isolation levels** based on performance and concurrency needs.
5. Use **readonly=true** for non-modifying methods to optimize database interactions.
   ```java
   @Transactional(readOnly = true)
   public List<Account> getAllAccounts() {
       return accountRepository.findAll();
   }
   ```

   Here's a program using the **Executor Framework** to print even and odd numbers using different threads:

### **Program Explanation**
1. **ThreadLocal**:
   - Not used in this specific scenario but is useful for thread-confined variables.
   - Could help maintain thread-specific data like counters, etc.

2. **Executor Framework**:
   - We'll use a **FixedThreadPool** with two threads.
   - One thread prints even numbers, and the other prints odd numbers.

3. **Synchronization**:
   - A **shared object** is used for inter-thread communication (using `wait()` and `notify()`).

---

### **Code**

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenOddPrinter {
    private static final int LIMIT = 10; // Upper limit for numbers
    private static final Object lock = new Object();
    private static int number = 1;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable printOdd = () -> {
            while (number <= LIMIT) {
                synchronized (lock) {
                    if (number % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + " - Odd: " + number++);
                        lock.notify(); // Notify other thread
                    } else {
                        try {
                            lock.wait(); // Wait for even thread
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        Runnable printEven = () -> {
            while (number <= LIMIT) {
                synchronized (lock) {
                    if (number % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " - Even: " + number++);
                        lock.notify(); // Notify other thread
                    } else {
                        try {
                            lock.wait(); // Wait for odd thread
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        executor.submit(printOdd);
        executor.submit(printEven);

        executor.shutdown();
    }
}
```

---

### **Output**
(Threads will alternate in printing numbers.)
```
pool-1-thread-1 - Odd: 1
pool-1-thread-2 - Even: 2
pool-1-thread-1 - Odd: 3
pool-1-thread-2 - Even: 4
pool-1-thread-1 - Odd: 5
pool-1-thread-2 - Even: 6
...
```

---

### **Key Points**
1. **Executor Framework**:
   - Provides thread pooling for efficient resource management.
   - `Executors.newFixedThreadPool(2)` creates a pool of two threads.

2. **Synchronization**:
   - Used `synchronized` block and `wait()/notify()` for inter-thread coordination.

3. **Thread Communication**:
   - Threads alternate by notifying each other.


**Improvements in Java Memory Management in Java 8**

Metaspace replaced PermGen for better class metadata management.
Compact Strings reduce memory usage for Latin-1 strings.
String Deduplication in G1 GC saves heap memory.
Improvements to Garbage Collection enhance heap performance.
Lambda expressions and streams optimize memory usage for functional programming constructs.


### **Monitoring Multiple Microservices**

Monitoring microservices is essential to ensure reliability, performance, and scalability. Below are key practices, tools, and approaches for monitoring microservices effectively.

---

### **Key Metrics to Monitor**
1. **Infrastructure Metrics**:
   - CPU, memory, disk usage, network I/O.
2. **Application Metrics**:
   - Response time, request throughput, error rates.
3. **Service Metrics**:
   - API latency, request counts, HTTP status codes.
4. **Database Metrics**:
   - Query execution time, connection pool usage.
5. **Logs**:
   - Application logs for debugging and audit trails.
6. **Distributed Tracing**:
   - End-to-end request traces to track cross-service calls.
7. **Custom Business Metrics**:
   - E.g., order processing time, successful transactions.

---

### **Monitoring Tools**
1. **Centralized Logging**:
   - Use tools like **ELK Stack** (Elasticsearch, Logstash, Kibana) or **Grafana Loki**.
   - Collect logs from all services and centralize them for analysis.

2. **Metrics Collection**:
   - Use **Prometheus** for metrics collection and **Grafana** for visualization.
   - Integrate with libraries like **Micrometer** in Spring Boot.

3. **Distributed Tracing**:
   - Use tools like **Jaeger**, **Zipkin**, or **OpenTelemetry**.
   - Trace requests across services to identify bottlenecks.

4. **APM Tools**:
   - Use tools like **New Relic**, **Datadog**, or **AppDynamics** for full-stack monitoring.

5. **Service Mesh**:
   - Use **Istio** or **Linkerd** to monitor, secure, and manage service-to-service communication.

---

### **Best Practices**
1. **Centralized Dashboard**:
   - Use a unified dashboard (e.g., Grafana) to display metrics from all microservices.

2. **Health Checks**:
   - Implement `/health` endpoints in all microservices.
   - Use Spring Boot’s **Actuator** to expose health metrics.

3. **Alerting**:
   - Configure alerting rules in Prometheus or Datadog for anomalies (e.g., high error rates).

4. **Correlation IDs**:
   - Pass a unique **Correlation ID** through requests to link logs and traces across microservices.

5. **Log Aggregation**:
   - Use structured logging (e.g., JSON format) to make logs easy to query in tools like Elasticsearch.

---

### **Spring Boot Integration Example**
1. **Exposing Metrics**:
   - Add **Micrometer** and **Actuator** dependencies:
     ```xml
     <dependency>
       <groupId>io.micrometer</groupId>
       <artifactId>micrometer-registry-prometheus</artifactId>
     </dependency>
     <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>
     ```

   - Expose metrics and health endpoints:
     ```yaml
     management:
       endpoints:
         web:
           exposure:
             include: health, metrics, prometheus
     ```

2. **Logging with Loki**:
   - Use Promtail to ship logs to Loki.
   - Format logs in JSON for better indexing.

3. **Distributed Tracing**:
   - Add **Spring Cloud Sleuth** and **Zipkin** dependencies:
     ```xml
     <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-sleuth</artifactId>
     </dependency>
     <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-sleuth-zipkin</artifactId>
     </dependency>
     ```

   - Configure Zipkin URL:
     ```yaml
     spring:
       zipkin:
         base-url: http://<zipkin-server>:9411
       sleuth:
         sampler:
           probability: 1.0
     ```

---

### **Example Monitoring Stack**
1. **Prometheus** for metrics collection.
2. **Grafana** for visualization (dashboards for each service).
3. **Jaeger** for distributed tracing.
4. **Loki** for centralized logging.

-----

A thread-safe Singleton in Java ensures only one instance of the class is created, even in a multithreaded environment. Below is an explanation and example of implementing such a Singleton.

---

### **Key Concepts for Thread-Safe Singleton**
1. **Lazy Initialization**:
   - Instance is created only when needed.

2. **Double-Checked Locking**:
   - Ensures synchronization is only performed when the instance is null, minimizing overhead.

3. **Volatile Keyword**:
   - Ensures visibility of changes to the instance variable across threads.

---

### **Example: Thread-Safe Singleton with Double-Checked Locking**

```java
public class Singleton {

    // Volatile variable ensures visibility across threads
    private static volatile Singleton instance;

    // Private constructor to prevent instantiation
    private Singleton() {
    }

    // Public method to get the singleton instance
    public static Singleton getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (Singleton.class) {
                if (instance == null) { // Second check (with locking)
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Singleton instance accessed!");
    }
}
```

---

### **How it Works**
1. **First Check**:
   - `if (instance == null)` checks if the instance already exists to avoid locking unnecessarily.

2. **Synchronized Block**:
   - Only one thread can initialize the instance at a time.

3. **Second Check**:
   - Ensures no other thread has already created the instance while waiting for the lock.

4. **Volatile Keyword**:
   - Prevents the JVM from reordering instructions, ensuring the instance is fully initialized before being visible to other threads.

---

### **Usage**

```java
public class Main {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        singleton.showMessage();
    }
}
```

---

### **Alternative: Singleton Using Enum**
An enum-based Singleton is inherently thread-safe and easier to implement:

```java
public enum SingletonEnum {
    INSTANCE;

    public void showMessage() {
        System.out.println("Singleton instance accessed via enum!");
    }
}
```

**Usage**:
```java
SingletonEnum.INSTANCE.showMessage();
```

---

### **Why Use Double-Checked Locking?**
It balances performance and thread safety by minimizing the overhead of synchronization, making it an efficient choice for Singleton implementation in a multithreaded environment.


---

An **immutable class** in Java is a class whose objects cannot be modified after creation. To create a custom immutable class, follow these steps:

---

### **Rules for Creating an Immutable Class**
1. **Declare the class as `final`**:
   - Prevent subclassing and modification of behavior.

2. **Make all fields private and final**:
   - Prevent direct access and ensure values cannot change.

3. **Do not provide setters**:
   - Avoid methods that modify fields.

4. **Initialize fields in the constructor**:
   - Assign values only once during object creation.

5. **Ensure deep immutability**:
   - If fields are mutable objects, return copies instead of direct references in getters.

---

### **Example: Custom Immutable Class**

```java
final class ImmutablePerson {
    
    // Private and final fields
    private final String name;
    private final int age;
    private final Address address; // Mutable field
    
    // Constructor to initialize all fields
    public ImmutablePerson(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = new Address(address.getStreet(), address.getCity()); // Create a defensive copy
    }
    
    // Getter for name
    public String getName() {
        return name;
    }
    
    // Getter for age
    public int getAge() {
        return age;
    }
    
    // Getter for address (returning a copy)
    public Address getAddress() {
        return new Address(address.getStreet(), address.getCity());
    }
}

// Mutable Address class used by ImmutablePerson
class Address {
    private String street;
    private String city;
    
    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }
    
    public String getStreet() {
        return street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
}
```

---

### **How It Works**
1. The `ImmutablePerson` class has private, final fields.
2. The `address` field is a mutable object, so a **defensive copy** is made in both the constructor and the getter to prevent external modification.
3. The class provides only getters, ensuring the state of the object cannot be changed.

---

### **Usage Example**

```java
public class Main {
    public static void main(String[] args) {
        Address address = new Address("123 Street", "CityA");
        ImmutablePerson person = new ImmutablePerson("John", 30, address);

        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Address: " + person.getAddress().getStreet() + ", " + person.getAddress().getCity());
        
        // Attempting to modify the address
        address.setStreet("456 Avenue"); // Does not affect ImmutablePerson
        System.out.println("Address after external modification: " + person.getAddress().getStreet());
    }
}
```

---

### **Output**
```
Name: John
Age: 30
Address: 123 Street, CityA
Address after external modification: 123 Street
```

---

### **Advantages**
1. Thread-safe: Immutable objects are inherently thread-safe without synchronization.
2. Simplicity: Their state is fixed, reducing bugs.
3. Easy caching: Can be safely shared without defensive copies.

-----

The **SOLID principles** are a set of design principles in object-oriented programming that help create maintainable, scalable, and robust software. Each principle stands for a key concept.

---

### **1. Single Responsibility Principle (SRP)**  
**Definition**: A class should have only one reason to change, i.e., it should have a single responsibility.  

**Example**:  
```java
// Class with single responsibility: handles user information
public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

// Class with single responsibility: sends email notifications
public class EmailService {
    public void sendEmail(User user, String message) {
        System.out.println("Sending email to " + user.getEmail() + " with message: " + message);
    }
}
```

---

### **2. Open/Closed Principle (OCP)**  
**Definition**: Classes should be open for extension but closed for modification.  

**Example**:  
```java
// Base class (closed for modification)
public abstract class Shape {
    public abstract double calculateArea();
}

// Extendable classes (open for extension)
public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

public class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}
```

---

### **3. Liskov Substitution Principle (LSP)**  
**Definition**: Subtypes should be substitutable for their base types without altering the correctness of the program.  

**Example**:  
```java
// Base class
public abstract class Bird {
    public abstract void fly();
}

// Subtype respecting LSP
public class Sparrow extends Bird {
    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

// Violation of LSP (Penguin can't fly)
public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguin can't fly");
    }
}
```

**Solution**: Separate behavior into interfaces:
```java
public interface Flyable {
    void fly();
}

public class Sparrow implements Flyable {
    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

public class Penguin {
    public void swim() {
        System.out.println("Penguin is swimming");
    }
}
```

---

### **4. Interface Segregation Principle (ISP)**  
**Definition**: A class should not be forced to implement interfaces it does not use.  

**Example** (Violation):
```java
public interface Animal {
    void fly();
    void swim();
}

public class Dog implements Animal {
    @Override
    public void fly() {
        // Not applicable
    }

    @Override
    public void swim() {
        System.out.println("Dog is swimming");
    }
}
```

**Solution**: Split the interface:
```java
public interface Flyable {
    void fly();
}

public interface Swimmable {
    void swim();
}

public class Dog implements Swimmable {
    @Override
    public void swim() {
        System.out.println("Dog is swimming");
    }
}

public class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}
```

---

### **5. Dependency Inversion Principle (DIP)**  
**Definition**: High-level modules should not depend on low-level modules. Both should depend on abstractions.  

**Example**:  
```java
// High-level module
public class NotificationService {
    private MessageSender sender;

    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void send(String message) {
        sender.sendMessage(message);
    }
}

// Abstraction
public interface MessageSender {
    void sendMessage(String message);
}

// Low-level module
public class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending email: " + message);
    }
}

public class SMSSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}
```

**Usage**:
```java
public class Main {
    public static void main(String[] args) {
        MessageSender emailSender = new EmailSender();
        NotificationService notificationService = new NotificationService(emailSender);
        notificationService.send("Hello via Email!");

        MessageSender smsSender = new SMSSender();
        notificationService = new NotificationService(smsSender);
        notificationService.send("Hello via SMS!");
    }
}
```

---

### **Summary of SOLID**
- **SRP**: One class, one responsibility.
- **OCP**: Extend functionality without modifying the base.
- **LSP**: Subtypes must behave like their parent.
- **ISP**: Use specific interfaces rather than forcing classes to implement unused methods.
- **DIP**: Depend on abstractions, not concrete implementations.


----

Design patterns are reusable solutions to common software design problems. They are categorized into three main groups: **Creational**, **Structural**, and **Behavioral**.

---

### **1. Creational Patterns**
Focus on object creation mechanisms to ensure objects are created appropriately for specific situations.

#### **a. Singleton**
Ensures a class has only one instance and provides a global point of access.

**Example:**
```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {} // Private constructor

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

---

#### **b. Factory Method**
Defines an interface for creating objects but lets subclasses decide the instantiation.

**Example:**
```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Shape {
    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}

class ShapeFactory {
    public static Shape getShape(String type) {
        if (type.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (type.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        }
        return null;
    }
}

// Usage
Shape shape = ShapeFactory.getShape("circle");
shape.draw();
```

---

#### **c. Builder**
Separates object construction from representation, useful for creating complex objects step-by-step.

**Example:**
```java
class Car {
    private String engine;
    private int wheels;

    public static class Builder {
        private String engine;
        private int wheels;

        public Builder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public Builder setWheels(int wheels) {
            this.wheels = wheels;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    private Car(Builder builder) {
        this.engine = builder.engine;
        this.wheels = builder.wheels;
    }

    public void display() {
        System.out.println("Car with engine: " + engine + ", wheels: " + wheels);
    }
}

// Usage
Car car = new Car.Builder().setEngine("V8").setWheels(4).build();
car.display();
```

---

### **2. Structural Patterns**
Deal with the composition of classes and objects to form larger structures.

#### **a. Adapter**
Converts one interface to another.

**Example:**
```java
interface MediaPlayer {
    void play(String audioType, String fileName);
}

class AudioPlayer implements MediaPlayer {
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else {
            System.out.println("Unsupported format");
        }
    }
}

class MediaAdapter implements MediaPlayer {
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            System.out.println("Playing vlc file: " + fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            System.out.println("Playing mp4 file: " + fileName);
        }
    }
}

// Usage
MediaPlayer player = new MediaAdapter();
player.play("vlc", "movie.vlc");
```

---

#### **b. Decorator**
Adds functionality to an object dynamically.

**Example:**
```java
interface Coffee {
    String getDescription();
    double cost();
}

class BasicCoffee implements Coffee {
    public String getDescription() {
        return "Basic Coffee";
    }

    public double cost() {
        return 5.0;
    }
}

class MilkDecorator implements Coffee {
    private Coffee coffee;

    public MilkDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    public double cost() {
        return coffee.cost() + 1.5;
    }
}

// Usage
Coffee coffee = new MilkDecorator(new BasicCoffee());
System.out.println(coffee.getDescription() + ": $" + coffee.cost());
```

---

#### **c. Proxy**
Provides a surrogate or placeholder for another object to control access.

**Example:**
```java
interface Service {
    void serve();
}

class RealService implements Service {
    public void serve() {
        System.out.println("Real service is serving");
    }
}

class ProxyService implements Service {
    private RealService realService;

    public void serve() {
        if (realService == null) {
            realService = new RealService();
        }
        System.out.println("Proxy: Adding control");
        realService.serve();
    }
}

// Usage
Service service = new ProxyService();
service.serve();
```

---

### **3. Behavioral Patterns**
Focus on communication between objects.

#### **a. Observer**
Defines a one-to-many dependency between objects so that when one changes, others are notified.

**Example:**
```java
interface Observer {
    void update(String message);
}

class Subscriber implements Observer {
    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(name + " received: " + message);
    }
}

class Publisher {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// Usage
Publisher publisher = new Publisher();
Observer sub1 = new Subscriber("Subscriber 1");
Observer sub2 = new Subscriber("Subscriber 2");

publisher.addObserver(sub1);
publisher.addObserver(sub2);
publisher.notifyObservers("Hello Subscribers!");
```

---

#### **b. Strategy**
Defines a family of algorithms and lets the client choose one at runtime.

**Example:**
```java
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

// Usage
PaymentStrategy payment = new PayPalPayment();
payment.pay(100);
```

---

### **Summary of Design Pattern Categories**
1. **Creational Patterns**: Singleton, Factory, Builder, etc.
2. **Structural Patterns**: Adapter, Decorator, Proxy, etc.
3. **Behavioral Patterns**: Observer, Strategy, Command, etc.

These patterns help write better, reusable, and flexible code.


-----

To sort a `Map` in Java using a `Comparator`, you can follow these steps:

1. **Use `entrySet()`**: The `Map` interface doesn't have a built-in method to sort entries directly, but you can use the `entrySet()` method to get a set of key-value pairs, which can be sorted.
  
2. **Create a `Comparator`**: You will need to create a custom comparator for sorting based on the keys or values of the map.

3. **Use `stream()` or `List.sort()`**: After getting the entries from the map, you can convert them to a list and then sort using the `Comparator`.

Here’s a simple example that demonstrates sorting a `Map` by its values using a `Comparator`.

### Example: Sort Map by Values

```java
import java.util.*;

public class SortMapExample {
    public static void main(String[] args) {
        // Create a map with some data
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 30);
        map.put("Banana", 10);
        map.put("Orange", 20);
        map.put("Grapes", 40);

        // Sort map by values
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());

        // Sort by values using a comparator
        sortedEntries.sort(Map.Entry.comparingByValue());

        // Print the sorted entries
        System.out.println("Map sorted by values:");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### Output:
```
Map sorted by values:
Banana: 10
Orange: 20
Apple: 30
Grapes: 40
```

### Explanation:
1. **`map.entrySet()`**: Converts the map into a set of key-value pairs (entries).
2. **`new ArrayList<>(map.entrySet())`**: Converts the set of entries into a list because lists support sorting.
3. **`sortedEntries.sort(Map.Entry.comparingByValue())`**: Sorts the entries by their values using `comparingByValue()` from `Map.Entry`. You can also use a custom comparator if you need to change the sorting logic.
4. **`for-each` loop**: Prints each entry in the sorted list.

### Example: Sort Map by Keys

If you want to sort the map by keys instead of values, you can use the following approach:

```java
import java.util.*;

public class SortMapByKeyExample {
    public static void main(String[] args) {
        // Create a map with some data
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 30);
        map.put("Banana", 10);
        map.put("Orange", 20);
        map.put("Grapes", 40);

        // Sort map by keys
        List<Map.Entry<String, Integer>> sortedEntriesByKey = new ArrayList<>(map.entrySet());

        // Sort by keys using a comparator
        sortedEntriesByKey.sort(Map.Entry.comparingByKey());

        // Print the sorted entries by keys
        System.out.println("Map sorted by keys:");
        for (Map.Entry<String, Integer> entry : sortedEntriesByKey) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### Output:
```
Map sorted by keys:
Apple: 30
Banana: 10
Grapes: 40
Orange: 20
```

In this case, the sorting is done by the keys of the map using `comparingByKey()`.

### Custom Comparator Example:

If you need a custom comparator to sort the map based on a specific order (e.g., descending values), you can do so like this:

```java
import java.util.*;

public class CustomSortMapExample {
    public static void main(String[] args) {
        // Create a map with some data
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 30);
        map.put("Banana", 10);
        map.put("Orange", 20);
        map.put("Grapes", 40);

        // Sort map by values in descending order using a custom comparator
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());

        // Custom comparator for descending order of values
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Print the sorted entries
        System.out.println("Map sorted by values in descending order:");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### Output:
```
Map sorted by values in descending order:
Grapes: 40
Apple: 30
Orange: 20
Banana: 10
```

### Key Concepts:
1. **Comparator**: Used to define the sorting logic for elements. You can sort either by keys or values of the map.
2. **`entrySet()`**: Used to get the entries of the map as a set of key-value pairs.
3. **`ArrayList`**: Converts the set of entries into a list, as lists can be sorted.
4. **`comparingByValue()` and `comparingByKey()`**: Helper methods from `Map.Entry` to simplify sorting by values or keys.