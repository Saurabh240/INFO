# 2. Your 90-second introduction

This is one of the most important answers.

### Interview answer

> "I'm a Senior Java and Microservices Engineer with over 7 years of experience building enterprise applications, primarily using Java, Spring Boot, REST APIs, microservices, Kafka, PostgreSQL and cloud technologies.
>
> In my current role at IBM, I work on a banking-domain application where I develop and enhance backend services, design REST APIs, work with event-driven communication using Kafka, and deploy applications using Docker and Kubernetes.
>
> I've also worked extensively with database optimization, production troubleshooting, CI/CD and cloud-native deployments.
>
> On the frontend side, I have experience with React and modern JavaScript, particularly integrating React applications with REST APIs and implementing reusable components.
>
> My strength is taking ownership of a feature end-to-end—from understanding the requirement and designing the solution to implementation, testing, deployment and production support.
>
> I'm now looking for a role where I can contribute at a larger engineering scale, work on complex enterprise problems, and take more ownership of architecture and technical decisions."

**Don't make this longer than ~90 seconds.**

---

# 3. CORE JAVA — highest-priority questions

The JD specifically calls out **OOP, exception handling, collections, concurrency, streams and lambdas**. 

## Q1. HashMap vs ConcurrentHashMap?

### Interview answer

> "HashMap is not thread-safe, whereas ConcurrentHashMap is designed for concurrent access.
>
> With HashMap, simultaneous modifications from multiple threads can lead to inconsistent results.
>
> ConcurrentHashMap allows multiple threads to read and update the map safely while providing better concurrency than synchronizing the entire map.
>
> Internally, modern ConcurrentHashMap uses fine-grained synchronization and CAS-based operations rather than locking the complete map.
>
> I would use HashMap in a single-threaded context and ConcurrentHashMap when the map is shared between multiple concurrent threads."

### Follow-up they may ask

**Can ConcurrentHashMap contain null keys?**

> "No. ConcurrentHashMap doesn't allow null keys or null values because null is used to represent the absence of a value, which would create ambiguity during concurrent operations."

---

# 4. ArrayList vs LinkedList

### Interview answer

> "ArrayList is backed by a dynamic array, so random access using an index is O(1).
>
> LinkedList is based on nodes, so accessing an arbitrary element is O(n).
>
> LinkedList can theoretically provide O(1) insertion or deletion when we already have the node position, but finding that position is O(n).
>
> In most real-world applications I prefer ArrayList because of better cache locality and generally better performance for iteration and random access."

**Senior-level point:** Don't say "LinkedList is better for insertion" without explaining the caveat.

---

# 5. HashMap internal working

This is **very likely**.

### Interview answer

> "HashMap stores entries using buckets. When we insert a key-value pair, HashMap calculates the hash of the key and uses it to determine the bucket.
>
> If multiple keys map to the same bucket, collisions are handled using a linked structure, and in modern Java implementations, a heavily-collided bucket can be converted into a balanced tree.
>
> During lookup, HashMap calculates the hash again and checks the corresponding bucket using hash and equals.
>
> That's why immutable objects are generally preferred as keys—their hash code must remain consistent while they're being used as keys."

### Follow-up

**Why both hashCode() and equals()?**

> "hashCode determines the bucket, while equals determines whether two objects are actually equal within that bucket."

---

# 6. == vs equals()

> "`==` compares primitive values directly or object references for objects.
>
> `equals()` is used for logical equality and can be overridden by a class.
>
> For example, two different String objects can contain the same value, so `equals()` can return true while `==` returns false."

---

# 7. String vs StringBuilder vs StringBuffer

> "String is immutable, so repeated concatenation creates new objects.
>
> StringBuilder is mutable and is preferred for string manipulation in a single-threaded context.
>
> StringBuffer is also mutable but synchronizes its methods, making it thread-safe but generally slower.
>
> So normally I'd use StringBuilder unless I specifically need synchronized access."

---

# 8. Checked vs unchecked exception

### Interview answer

> "Checked exceptions are verified by the compiler and generally represent conditions that the application may reasonably be expected to recover from.
>
> Unchecked exceptions extend RuntimeException and generally represent programming errors or invalid state.
>
> In Spring Boot applications, I prefer meaningful domain exceptions and centralized exception handling using `@ControllerAdvice`, rather than propagating technical exceptions directly to the API consumer."

---

# 9. What is immutability? How would you create an immutable class?

Expected senior-level question.

### Answer

> "An immutable object cannot change its state after construction.
>
> To create one, I would make the class final, keep fields private and final, initialize them through the constructor, avoid setters, and make defensive copies for mutable fields such as Date, List or Map.
>
> Immutability is useful in concurrent applications because immutable objects are inherently safer to share between threads."

---

# 10. Java Streams — very likely

### Q: Difference between map() and flatMap()?

> "`map()` transforms each element into another element, so one input generally produces one output.
>
> `flatMap()` is used when each input can produce multiple elements and we want to flatten those results into a single stream."

Example:

```java
List<List<String>> data;

data.stream()
    .flatMap(List::stream)
    .toList();
```

---

# 11. Intermediate vs terminal operations

> "Intermediate operations such as `filter`, `map` and `sorted` return another Stream and are lazily evaluated.
>
> Terminal operations such as `collect`, `forEach`, `count` and `reduce` trigger stream processing."

### Follow-up:

**Why is lazy evaluation useful?**

> "It allows the stream pipeline to avoid unnecessary processing and enables optimizations such as short-circuiting."

---

# 12. map vs filter vs reduce

Know this perfectly.

```text
filter → select elements
map    → transform elements
reduce → combine elements into one result
```

---

# 13. Concurrency — extremely important

The JD explicitly mentions **threads/concurrency**. 

## Q: Thread vs ExecutorService?

> "Creating threads manually gives us limited control over thread lifecycle and can lead to excessive thread creation.
>
> ExecutorService provides a managed thread pool where tasks are submitted and workers execute them.
>
> In enterprise applications, I'd generally prefer ExecutorService or Spring's task execution abstractions rather than creating raw threads."

---

# 14. synchronized vs Lock

> "`synchronized` provides simple intrinsic locking and automatically releases the lock when the block exits.
>
> `Lock`, such as ReentrantLock, provides more advanced capabilities like tryLock, interruptible locking and optional fairness.
>
> I prefer synchronized when the synchronization requirement is simple and Lock when I need more control over lock acquisition."

---

# 15. CompletableFuture

Very good senior-level question.

### Interview answer

> "CompletableFuture allows asynchronous and composable processing.
>
> For example, if I need to call two independent downstream services, I can execute them concurrently and combine their results instead of calling them sequentially.
>
> It also provides mechanisms such as `thenApply`, `thenCompose`, `thenCombine` and exception handling."

Potential example:

```java
CompletableFuture<User> user = getUser();
CompletableFuture<Account> account = getAccount();

user.thenCombine(account,
        (u, a) -> buildResponse(u, a));
```

---

# 16. volatile vs synchronized

### Interview answer

> "`volatile` guarantees visibility of changes between threads but does not provide atomicity for compound operations.
>
> `synchronized` provides both mutual exclusion and visibility guarantees.
>
> For example, `count++` is not thread-safe even if count is volatile because it involves read, modify and write operations."

---

# 17. SPRING BOOT

This will probably be the **second major section**.

The JD explicitly requires dependency injection, bean lifecycle, transactions, database access, Spring Security and Spring Cloud. 

---

# 18. What happens when Spring Boot application starts?

### Interview answer

> "Spring Boot starts the application context, performs component scanning, identifies configuration and bean definitions, creates and wires the required beans using dependency injection, and then starts the embedded web server for a web application.
>
> During bean creation, lifecycle callbacks such as post-processors and initialization methods can also execute."

---

# 19. @Component vs @Service vs @Repository

> "`@Component` is the generic stereotype.
>
> `@Service` is semantically used for service-layer components.
>
> `@Repository` represents persistence components and also enables Spring's exception translation mechanism for persistence exceptions.
>
> Technically all three are detected as Spring components, but using the appropriate stereotype makes the architecture clearer."

---

# 20. Constructor injection vs field injection

### Strong answer

> "I prefer constructor injection.
>
> It makes dependencies explicit, allows fields to be final, improves testability and prevents creating partially initialized objects.
>
> Field injection hides dependencies and makes unit testing harder."

---

# 21. @Transactional — extremely likely

### Interview answer

> "`@Transactional` defines a transaction boundary around a method or class.
>
> Spring typically creates a proxy around the bean and manages transaction begin, commit and rollback around the method invocation.
>
> If an appropriate runtime exception occurs, the transaction is rolled back by default.
>
> I usually keep transaction boundaries at the service layer because a business operation may involve multiple repository calls that should succeed or fail together."

### Very important follow-up

**Does @Transactional work when one method in the same class calls another @Transactional method?**

> "Normally not through the Spring proxy, because the internal call uses `this` rather than going through the proxy. Therefore proxy-based transactional interception may not be triggered."

This is a **very good Deloitte-level question**.

---

# 22. Propagation

Know these:

```text
REQUIRED
REQUIRES_NEW
SUPPORTS
MANDATORY
NESTED
```

Most important:

### REQUIRED

> "Join an existing transaction if one exists; otherwise create a new one."

### REQUIRES_NEW

> "Suspend the existing transaction and start a completely independent transaction."

Example:

> "This can be useful when audit logging needs to commit independently of the main business transaction."

---

# 23. REST API design

Deloitte specifically asks for REST API best practices. 

### Q: How would you design a production-grade REST API?

Answer:

> "I would first define clear resource-oriented endpoints and appropriate HTTP methods and status codes.
>
> I would validate input, implement centralized exception handling, define consistent error responses, use pagination for large collections, support appropriate authentication and authorization, and document the API using OpenAPI.
>
> I would also consider idempotency for operations such as payments or order creation, API versioning where required, observability through logging and metrics, and proper timeout and resilience handling for downstream calls."

That's a **strong senior answer**.

---

# 24. PUT vs PATCH

> "PUT generally represents replacement of a resource and is expected to be idempotent.
>
> PATCH represents a partial modification of a resource.
>
> For example, updating only a customer's email address would naturally fit PATCH."

---

# 25. Microservices architecture

This is **one of the most important areas in this JD**.

### Q: How do you decide service boundaries?

### Interview answer

> "I wouldn't create services simply based on database tables or technical layers.
>
> I'd look at business capabilities, domain ownership, data ownership, transaction boundaries and independent scalability or deployment requirements.
>
> A good microservice should have a clear responsibility and own its business logic and preferably its data.
>
> I'd also consider operational complexity because creating too many small services can introduce unnecessary network calls, deployment overhead and distributed transaction problems."

Excellent senior answer.

---

# 26. How do microservices communicate?

Expected answer:

> "For synchronous communication we can use REST or gRPC depending on requirements.
>
> For asynchronous communication we can use Kafka or another messaging platform.
>
> I'd use synchronous communication when the caller needs an immediate response and asynchronous messaging when decoupling, resilience or event-driven processing is more appropriate."

---

# 27. Distributed transaction problem

Very likely.

### Answer

> "Traditional ACID transactions don't naturally span independent microservices.
>
> Instead of relying on distributed two-phase commit, I'd generally prefer patterns such as Saga, where a business transaction is split into local transactions with compensating actions.
>
> For example, if an order is created, payment succeeds but inventory reservation fails, the system can execute a compensating payment reversal."

---

# 28. Kafka — prepare this even though not explicitly listed

Because your background includes Kafka and it commonly comes up in Java microservices interviews.

### Q: What happens when Kafka consumer processing fails?

> "It depends on whether the failure is transient or permanent.
>
> For a transient issue, such as a temporary downstream service failure, I would use controlled retries with appropriate backoff.
>
> For a permanent or repeatedly failing message, I would eventually move it to a dead-letter topic so that it doesn't block normal processing.
>
> I'd also make the consumer idempotent because retries can result in duplicate processing."

---

# 29. Exactly-once processing?

Don't casually claim exactly-once business processing.

### Strong answer:

> "Kafka provides exactly-once semantics in specific configurations and processing patterns, but that doesn't automatically mean the entire business workflow is exactly once.
>
> For external databases or APIs, I would still design the consumer for idempotency and use patterns such as transactional outbox where appropriate."

---

# 30. DATABASE / JPA

The JD explicitly requires RDBMS/SQL, normalization, query troubleshooting and JPA/Hibernate. 

## Q: Lazy vs eager loading?

> "Lazy loading means the association is loaded when accessed, while eager loading retrieves it immediately.
>
> For most relationships, I prefer lazy loading by default because eager loading can unnecessarily fetch large object graphs.
>
> However, lazy loading needs careful handling to avoid N+1 queries and LazyInitializationException."

---

# 31. What is N+1 problem?

### Answer

> "Suppose I fetch 100 orders in one query and then Hibernate executes another query for each order to fetch its customer. Instead of one query, we end up with 101 queries.
>
> That's the N+1 problem.
>
> Depending on the use case, I can solve it using fetch joins, entity graphs, batch fetching or DTO projections."

---

# 32. How do you troubleshoot a slow SQL query?

Give this structured answer:

> "First I'd reproduce the issue and identify the actual query.
>
> Then I'd inspect the execution plan using EXPLAIN or EXPLAIN ANALYZE.
>
> I'd check indexes, joins, filtering conditions, cardinality, sorting and whether unnecessary columns or rows are being fetched.
>
> I'd also check whether the ORM is generating inefficient queries, particularly N+1 queries.
>
> Finally I'd validate the improvement using realistic data and execution metrics rather than assuming an index automatically solves the issue."

Very strong answer.

---

# 33. JPA save vs saveAndFlush

Possible question.

> "`save()` persists the entity within the persistence context, while `saveAndFlush()` explicitly triggers a flush to the database.
>
> I wouldn't use saveAndFlush by default because unnecessary flushes can hurt performance. I'd use it only when I specifically need database synchronization at that point in the transaction."

---

# 34. SECURITY — VERY HIGH PRIORITY

The JD explicitly mentions **Spring Security, OAuth2/OIDC, JWT, authentication and authorization**. 

Given your previous interview preparation, make sure you can explain JWT **end-to-end**.

## Q: Explain JWT authentication flow.

### Interview answer

> "The user first authenticates with the identity provider by providing their credentials.
>
> After successful authentication, the identity provider issues an access token, typically a JWT.
>
> The client sends that token with subsequent API requests using the Authorization header:
>
> `Authorization: Bearer <token>`
>
> The backend's security layer validates the token signature and checks claims such as issuer, audience, expiration and potentially scopes or roles.
>
> If validation succeeds, Spring Security creates an authenticated security context and the request proceeds to the controller.
>
> Authorization is then applied based on roles or authorities.
>
> The important point is that validating a JWT is not just checking whether the token is structurally valid. We must verify the signature and relevant claims."

### JWT validation checklist

Remember:

```text
Signature
Issuer (iss)
Audience (aud)
Expiration (exp)
Not-before (nbf), if applicable
Scopes / roles
Algorithm
```

---

# 35. Authentication vs authorization

> "Authentication answers 'Who are you?'
>
> Authorization answers 'What are you allowed to do?'
>
> For example, OAuth2/OIDC can authenticate the user and provide identity information, while application authorization can determine whether that user has permission to access an admin endpoint."

---

# 36. OAuth2 vs OIDC

Very likely because the JD explicitly says OAuth2/OIDC. 

### Answer

> "OAuth2 is primarily an authorization framework for obtaining access to protected resources.
>
> OpenID Connect extends OAuth2 to provide an identity layer, allowing the client to authenticate the user and obtain identity information through ID tokens."

---

# 37. React — prepare these carefully

The JD wants **4–7 years React**, TypeScript, hooks, state management, REST/GraphQL, authentication, testing and performance. 

## Q: What happens when state changes in React?

> "When state changes, React schedules a re-render of the component.
>
> React creates the new element tree and compares it with the previous one during reconciliation, then updates the necessary parts of the DOM.
>
> The goal is to minimize actual DOM updates rather than recreating the entire DOM."

---

# 38. useState vs useEffect

> "`useState` is used to manage component state.
>
> `useEffect` is used to perform side effects such as API calls, subscriptions or interacting with external systems.
>
> The dependency array determines when the effect runs."

### Follow-up

**What happens with an empty dependency array?**

> "The effect runs after the initial render, and its cleanup runs when the component unmounts."

---

# 39. useMemo vs useCallback

This is a classic.

> "`useMemo` memoizes a calculated value.
>
> `useCallback` memoizes a function reference.
>
> I wouldn't use them everywhere. I'd use them when there is an actual performance benefit, such as avoiding expensive calculations or preventing unnecessary child renders caused by changing function references."

---

# 40. Context API vs Redux

### Answer

> "Context is useful for relatively global values such as theme, authentication state or configuration.
>
> Redux Toolkit is more appropriate when application state becomes complex, shared across many parts of the application, and requires predictable state transitions and debugging.
>
> I wouldn't introduce Redux simply because an application uses React; I'd choose based on state complexity."

---

# 41. React performance optimization

The JD specifically calls out **code splitting, lazy loading, memoization and large datasets**. 

### Answer

> "I'd first measure the problem rather than blindly applying optimizations.
>
> Typical techniques include React.memo where appropriate, useMemo/useCallback for expensive or referentially sensitive operations, lazy loading and code splitting, virtualization for large lists, pagination, avoiding unnecessary state updates, and optimizing API calls.
>
> I'd use React DevTools Profiler to identify components that are actually causing expensive renders."

---

# 42. How would React call your Spring Boot API?

### Answer

> "I'd typically expose REST APIs from Spring Boot and consume them from React using fetch or an HTTP client.
>
> I'd define a clear API contract using OpenAPI, handle loading, success and error states, implement pagination where necessary, and centralize common concerns such as authentication headers and error handling.
>
> For authentication, I'd integrate with the application's OAuth2/OIDC flow and ensure tokens aren't exposed unnecessarily."

---

# 43. TypeScript questions

### Q: interface vs type?

A good answer:

> "Both can define object shapes, but interfaces are particularly useful for object-oriented contracts and can be extended or merged, while type aliases are more flexible for unions, intersections and other type compositions.
>
> In a React application I'd use whichever gives the clearest domain model and consistency with the project's conventions."

---

# 44. TESTING

The JD explicitly requires JUnit, debugging, Jest, React Testing Library and E2E testing. 

## Q: Unit test vs integration test?

> "A unit test verifies a small unit of behavior in isolation, typically mocking external dependencies.
>
> An integration test verifies that multiple components work together, such as a service interacting with a repository or database.
>
> I use unit tests for fast feedback and integration tests for validating actual component boundaries."

---

# 45. Mockito — important

### Q: @Mock vs @InjectMocks?

> "`@Mock` creates a mock dependency.
>
> `@InjectMocks` creates the class under test and injects the available mocks into it."

### Follow-up:

**When would you NOT mock?**

> "I avoid mocking the component I'm actually trying to test. I also avoid excessive mocking of simple domain objects or internal implementation details because that can make tests brittle."

---

# 46. Production issue question

This is **very likely for Software Engineer III**.

### Q: Production API suddenly becomes slow. What do you do?

Give this exact structure:

> "First I would assess the impact—whether all users are affected or only a specific API or service.
>
> Then I'd check observability data such as application logs, metrics, latency, error rates, CPU, memory, database performance and downstream dependencies.
>
> I'd identify whether the bottleneck is in the application, database, network or an external service.
>
> If necessary I'd mitigate the immediate impact—for example by scaling instances, disabling a problematic feature or rolling back a recent deployment.
>
> Once stabilized, I'd identify the root cause, implement a permanent fix, add appropriate monitoring or tests, and document the incident and preventive actions."

That's exactly the kind of **ownership-driven** response the JD asks for. 

---

# 47. Design question they may ask

## "Design an order management microservice."

Use this framework:

```text
React
  ↓
API Gateway
  ↓
Order Service
  ↓
PostgreSQL

Order Service
  ↓
Kafka
 ├── Payment Service
 ├── Inventory Service
 └── Notification Service
```

Then explain:

### API

```text
POST /orders
GET /orders/{id}
GET /orders?page=0&size=20
PATCH /orders/{id}
```

### Important design points

* Authentication → OAuth2/OIDC
* Authorization → roles/scopes
* Database → PostgreSQL
* Async events → Kafka
* Idempotency → request ID/idempotency key
* Resilience → timeout/retry/circuit breaker
* Transaction → local DB transaction
* Distributed workflow → Saga
* Reliability → outbox pattern
* Observability → logs + metrics + traces
* API documentation → OpenAPI

This single design answer can cover **Java + Spring + Microservices + Kafka + DB + security + scalability**.

---

# 48. Deloitte-specific behavioral questions

The JD places considerable emphasis on **client communication, ambiguity, ownership and collaboration**. 

Prepare these:

### Q1. Tell me about a difficult production issue you solved.

Structure:

```text
Situation
↓
Impact
↓
Investigation
↓
Root cause
↓
Fix
↓
Prevention
```

Don't just say:

> "I fixed the issue."

Say:

> "I first established the impact, checked logs and metrics, isolated the failing dependency, identified the root cause, applied a mitigation, and then implemented a permanent fix with monitoring/tests to prevent recurrence."

---

# 49. "Tell me about a technical decision you disagreed with."

Strong answer:

> "I first try to understand the underlying constraint rather than immediately disagreeing.
>
> I present the alternatives with measurable trade-offs—performance, maintainability, security, cost and operational complexity.
>
> If the team chooses another approach after discussion, I support the decision unless there's a significant security or reliability concern."

Very suitable for Deloitte's client-facing environment.

---

# 50. "How do you handle ambiguous requirements?"

### Answer

> "I don't start implementation immediately.
>
> I first identify the ambiguity and ask targeted questions around business behavior, edge cases, expected volumes, security requirements and failure scenarios.
>
> I document the assumptions and confirm them with the relevant stakeholder.
>
> Once the expected behavior is clear, I propose a solution and identify any risks before implementation."

The JD almost directly asks for this behavior. 

---

# 51. "How do you review someone else's code?"

Answer:

> "I look at correctness first, then security, maintainability, performance, error handling, test coverage and consistency with project standards.
>
> I also try to understand the intent before suggesting changes.
>
> My review comments should be actionable rather than simply pointing out that something is wrong."

---

# 53. One important concern: React

The JD says:

> **4–7 years hands-on React**

while your strongest profile is clearly **Java/Spring Boot/microservices/backend**.

So **do not try to portray yourself as a React specialist if they drill deeply**.

If asked:

### "How strong are you in React?"

Use:

> "My primary strength is backend engineering with Java and Spring Boot. I have practical experience building React-based applications and integrating them with REST APIs, working with components, hooks, state management and authentication flows. I would consider myself stronger on the backend side, but I'm comfortable contributing across the full stack."

That is much safer than claiming expert React knowledge and then getting trapped in advanced frontend questions.

---



# Deloitte Interview Preparation — SQL, Stream API & Easy Coding

For a **1-hour Deloitte Software Engineer III interview**, I would prepare the following 30 questions. The Deloitte JD emphasizes Java, Spring Boot, microservices, SQL/JPA, React/TypeScript, security, testing and production troubleshooting.

---

# Part 1 — 10 Important SQL Questions

## 1. Find the 2nd highest salary

**Table:**

```text
Employee(id, name, salary, department_id)
```

### Query

```sql
SELECT MAX(salary)
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);
```

### Better approach with DENSE_RANK

```sql
SELECT name, salary
FROM (
    SELECT e.*,
           DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
    FROM Employee e
) t
WHERE rnk = 2;
```

### Follow-up

**Why DENSE_RANK instead of ROW_NUMBER?**

Because if two employees have the highest salary, `DENSE_RANK()` treats them as the same rank.

---

## 2. Find the 3rd highest salary in each department

```sql
SELECT *
FROM (
    SELECT e.*,
           DENSE_RANK() OVER (
               PARTITION BY department_id
               ORDER BY salary DESC
           ) AS rnk
    FROM Employee e
) t
WHERE rnk = 3;
```

### Key concept

```text
PARTITION BY department_id
```

means ranking restarts for every department.

---

## 3. Find employees earning more than their manager

Assume:

```text
Employee(id, name, salary, manager_id)
```

### Query

```sql
SELECT e.name AS employee,
       e.salary AS employee_salary,
       m.name AS manager,
       m.salary AS manager_salary
FROM Employee e
JOIN Employee m
  ON e.manager_id = m.id
WHERE e.salary > m.salary;
```

### Concept tested

**Self JOIN**

---

## 4. Find duplicate records

Suppose:

```text
Employee(id, email, name)
```

Find duplicate emails.

```sql
SELECT email, COUNT(*) AS cnt
FROM Employee
GROUP BY email
HAVING COUNT(*) > 1;
```

### Follow-up

**How would you delete duplicates while keeping one record?**

For PostgreSQL:

```sql
DELETE FROM Employee
WHERE id IN (
    SELECT id
    FROM (
        SELECT id,
               ROW_NUMBER() OVER (
                   PARTITION BY email
                   ORDER BY id
               ) AS rn
        FROM Employee
    ) t
    WHERE rn > 1
);
```

---

## 5. INNER JOIN vs LEFT JOIN

Question:

> Find all employees including employees who don't have a department.

```sql
SELECT e.name, d.name
FROM Employee e
LEFT JOIN Department d
       ON e.department_id = d.id;
```

### Interview explanation

> "INNER JOIN returns only matching records from both tables, whereas LEFT JOIN returns all records from the left table and matching records from the right table. Non-matching right-side values become NULL."

---

## 6. Find departments having more than 5 employees

```sql
SELECT department_id,
       COUNT(*) AS employee_count
FROM Employee
GROUP BY department_id
HAVING COUNT(*) > 5;
```

### Important follow-up

**WHERE vs HAVING?**

> "`WHERE` filters rows before grouping, while `HAVING` filters groups after aggregation."

---

## 7. Find employees whose salary is greater than department average

```sql
SELECT e.*
FROM Employee e
JOIN (
    SELECT department_id,
           AVG(salary) AS avg_salary
    FROM Employee
    GROUP BY department_id
) d
ON e.department_id = d.department_id
WHERE e.salary > d.avg_salary;
```

### Window-function version

```sql
SELECT *
FROM (
    SELECT e.*,
           AVG(salary) OVER (
               PARTITION BY department_id
           ) AS avg_salary
    FROM Employee e
) t
WHERE salary > avg_salary;
```

---

## 8. Find the highest-paid employee in each department

```sql
SELECT *
FROM (
    SELECT e.*,
           ROW_NUMBER() OVER (
               PARTITION BY department_id
               ORDER BY salary DESC
           ) AS rn
    FROM Employee e
) t
WHERE rn = 1;
```

### Follow-up

**What if you want all employees tied for highest salary?**

Use `DENSE_RANK()` instead of `ROW_NUMBER()`.

---

## 9. SQL performance: query is slow. What will you do?

### Interview answer

> "First I would identify the actual slow query and measure its execution time.
>
> Then I would inspect the execution plan using EXPLAIN or EXPLAIN ANALYZE.
>
> I'd check whether appropriate indexes exist, whether joins are causing large scans, whether filtering is happening efficiently, and whether unnecessary columns or rows are being fetched.
>
> I'd also check for ORM-related problems such as N+1 queries.
>
> After making a change, I'd compare execution plans and performance using realistic data."

**Don't simply say:** "I'll add an index."

A senior interviewer expects you to investigate first.

---

## 10. DELETE vs TRUNCATE vs DROP

| Command  | Purpose               |
| -------- | --------------------- |
| DELETE   | Deletes selected rows |
| TRUNCATE | Removes all rows      |
| DROP     | Removes table/object  |

### Interview answer

> "`DELETE` is a DML operation and can use a WHERE condition. `TRUNCATE` removes all rows more efficiently and has different transaction/logging behavior depending on the database. `DROP` removes the table itself."

---

# SQL Priority

If you have limited preparation time:

1. 2nd/3rd highest salary
2. Highest salary per department
3. Employee > manager
4. Employee > department average
5. Duplicate records
6. JOINs
7. GROUP BY/HAVING
8. Query optimization
9. Window functions
10. DELETE/TRUNCATE/DROP

---

# Part 2 — 10 Important Stream API Questions

The JD specifically requires Java Streams and Lambdas, and recent Deloitte interview experiences also report Stream API coding questions.

---

## 1. Find even numbers from a list

```java
List<Integer> result = numbers.stream()
        .filter(n -> n % 2 == 0)
        .toList();
```

Know:

```text
filter()  → selects
map()     → transforms
collect() → collects
```

---

## 2. Find duplicate elements

```java
Set<Integer> seen = new HashSet<>();

Set<Integer> duplicates = numbers.stream()
        .filter(n -> !seen.add(n))
        .collect(Collectors.toSet());
```

### Important

`Set.add()` returns:

```text
true  → element was new
false → element already existed
```

---

## 3. Find frequency of each element

```java
Map<Integer, Long> frequency =
        numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
```

Example:

```text
[1,2,2,3,3,3]

1 → 1
2 → 2
3 → 3
```

---

## 4. Find highest salary employee

```java
Optional<Employee> employee =
        employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
```

### Follow-up

**Why Optional?**

> "Because the stream may be empty, so Optional represents the possible absence of a result instead of returning null."

---

## 5. Sort employees by salary descending

```java
List<Employee> result =
        employees.stream()
                .sorted(
                    Comparator.comparing(Employee::getSalary)
                              .reversed()
                )
                .toList();
```

---

## 6. Find top 3 highest-paid employees

```java
List<Employee> top3 =
        employees.stream()
                .sorted(
                    Comparator.comparing(Employee::getSalary)
                              .reversed()
                )
                .limit(3)
                .toList();
```

### Follow-up

**Top 3 distinct salaries?**

```java
List<Integer> result =
        employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
```

---

## 7. Find employees having salary greater than 100,000

```java
List<Employee> result =
        employees.stream()
                .filter(e -> e.getSalary() > 100000)
                .toList();
```

---

## 8. Group employees by department

```java
Map<String, List<Employee>> result =
        employees.stream()
                .collect(
                    Collectors.groupingBy(
                        Employee::getDepartment
                    )
                );
```

This is a very important enterprise Stream API question.

---

## 9. Find highest-paid employee in each department

```java
Map<String, Optional<Employee>> result =
        employees.stream()
                .collect(
                    Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy(
                            Comparator.comparing(Employee::getSalary)
                        )
                    )
                );
```

---

## 10. Find the 3rd highest salary

```java
Optional<Integer> thirdHighest =
        employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst();
```

---

# Stream API Follow-ups You MUST Know

## map() vs flatMap()

```text
map:
A → B

flatMap:
A → Stream<B>
then flatten
```

Example:

```java
List<List<Integer>> lists;

lists.stream()
     .flatMap(List::stream)
     .toList();
```

---

## findFirst() vs findAny()

> "`findFirst()` respects encounter order when the stream has one, whereas `findAny()` is allowed to return any matching element and can be useful in parallel processing."

---

## map() vs peek()

> "`map()` transforms elements. `peek()` is mainly intended for observing elements, commonly for debugging, and should not generally be used for business transformations."

---

## Sequential vs parallel stream

> "Parallel streams divide work across multiple threads, but they aren't automatically faster. They can introduce overhead and thread-safety concerns, especially for small datasets or operations with shared mutable state."

Don't say:

> "Parallel streams are always faster."

---

# Part 3 — 10 Easy Coding Questions

For your experience level, I wouldn't spend most of your preparation on hard LeetCode.

Recent Deloitte experiences include approachable problems such as Two Sum, Valid Anagram, simple string manipulation, Bubble Sort and Maximum Subarray Sum.

---

## 1. Two Sum ⭐⭐⭐

Given:

```text
[2,7,11,15]
target = 9
```

Return:

```text
[0,1]
```

### Optimal approach

Use HashMap.

```java
Map<Integer, Integer> map = new HashMap<>();

for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];

    if (map.containsKey(complement)) {
        return new int[]{map.get(complement), i};
    }

    map.put(nums[i], i);
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

## 2. Reverse a String

Input:

```text
"hello"
```

Output:

```text
"olleh"
```

Know both:

```java
new StringBuilder(str).reverse().toString();
```

and a manual implementation without using `reverse()`.

---

## 3. Check whether String is palindrome

```text
madam → true
hello → false
```

Use two pointers:

```text
left = 0
right = length - 1

while left < right:
    compare characters
```

Complexity:

```text
Time: O(n)
Space: O(1)
```

---

## 4. Find first non-repeating character

Input:

```text
swiss
```

Output:

```text
w
```

Approach:

```java
Map<Character, Integer> frequency
```

Then iterate again.

This tests:

* HashMap
* Strings
* Iteration
* Complexity

---

## 5. Find duplicate numbers in an array

Input:

```text
[1,2,3,2,4,1]
```

Output:

```text
1,2
```

Use:

```java
Set<Integer> seen
```

and explain the time/space complexity.

---

## 6. Maximum Subarray Sum ⭐⭐⭐

Input:

```text
[-2,1,-3,4,-1,2,1,-5,4]
```

Output:

```text
6
```

Because:

```text
4 + (-1) + 2 + 1 = 6
```

Know **Kadane's algorithm**:

```java
int current = nums[0];
int max = nums[0];

for (int i = 1; i < nums.length; i++) {
    current = Math.max(nums[i], current + nums[i]);
    max = Math.max(max, current);
}
```

Complexity:

```text
Time: O(n)
Space: O(1)
```

---

## 7. Count vowels / remove vowels

Input:

```text
"developer"
```

Output could be:

```text
4
```

or:

```text
"dvlpr"
```

depending on the question.

Prepare both variations.

---

## 8. Check Anagram ⭐⭐

Input:

```text
listen
silent
```

Output:

```text
true
```

One solution:

```java
char[] a = s1.toCharArray();
char[] b = s2.toCharArray();

Arrays.sort(a);
Arrays.sort(b);

return Arrays.equals(a, b);
```

Also know the HashMap frequency approach.

---

## 9. Find missing number

Input:

```text
[1,2,3,5]
```

Output:

```text
4
```

Know the XOR approach as well as the sum-formula approach.

---

## 10. Bubble sort / sort an array without built-in sort

```java
for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
            int temp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = temp;
        }
    }
}
```

Complexity:

```text
Time: O(n²)
Space: O(1)
```

---

# Final Priority for YOUR Deloitte Interview

## SQL

| Priority | Question                      |
| -------- | ----------------------------- |
| 🔥🔥🔥   | 2nd/3rd highest salary        |
| 🔥🔥🔥   | Highest salary per department |
| 🔥🔥🔥   | Employee > manager            |
| 🔥🔥🔥   | Employee > department average |
| 🔥🔥     | Duplicate records             |
| 🔥🔥     | JOINs                         |
| 🔥🔥     | GROUP BY/HAVING               |
| 🔥🔥     | Query optimization            |
| 🔥       | Window functions              |
| 🔥       | DELETE/TRUNCATE/DROP          |

## Streams

| Priority | Question                        |
| -------- | ------------------------------- |
| 🔥🔥🔥   | Group employees by department   |
| 🔥🔥🔥   | Highest employee per department |
| 🔥🔥🔥   | 3rd highest salary              |
| 🔥🔥🔥   | Top 3 employees                 |
| 🔥🔥🔥   | Duplicate elements              |
| 🔥🔥     | Frequency                       |
| 🔥🔥     | map vs flatMap                  |
| 🔥🔥     | Sorting                         |
| 🔥       | findFirst vs findAny            |
| 🔥       | Parallel stream                 |

## Coding

| Priority | Problem                       |
| -------- | ----------------------------- |
| 🔥🔥🔥   | Two Sum                       |
| 🔥🔥🔥   | Maximum Subarray              |
| 🔥🔥🔥   | Anagram                       |
| 🔥🔥     | First non-repeating character |
| 🔥🔥     | Palindrome                    |
| 🔥🔥     | Duplicate elements            |
| 🔥🔥     | Missing number                |
| 🔥       | Reverse String                |
| 🔥       | Remove vowels                 |
| 🔥       | Bubble sort                   |

---

# What I would prioritize before the interview

For your **1-hour Deloitte interview**, I would expect something broadly like:

```text
Project discussion
      ↓
Core Java
      ↓
Stream API coding
      ↓
SQL
      ↓
Spring Boot / Microservices
      ↓
React
      ↓
Security
      ↓
Production scenario
```

So don't spend all your time on SQL or LeetCode.

If time is limited, make sure you can **write these from memory**:

1. 2nd highest salary
2. Highest salary per department
3. Employee > manager
4. Employee > department average
5. Group employees by department using Streams
6. Highest employee per department using Streams
7. 3rd highest salary using Streams
8. Duplicate elements using Streams
9. Two Sum
10. Maximum Subarray

These give you the highest return for this particular interview.
