# LTIMindtree — Interview Question Bank

## Round 1 — L1 Technical

### API / Microservices
1. How would you implement API rate limiting?
2. How would you handle duplicate events, especially in a payment processing system?
3. How would you implement asynchronous programming?
4. How would you implement idempotency in Kafka?

### Kafka / Event-Driven Architecture
5. How would you handle duplicate events in a payment processing system?
6. How would you ensure idempotency in Kafka?
7. How would you use Kafka transactions for payment/event processing?
8. How would you handle duplicate events when a Kafka consumer updates an external database?
9. How would you use the Outbox Pattern when publishing events?

### Java — Asynchronous Programming / Concurrency
10. How would you implement asynchronous programming in Java?
11. How would you use `CompletableFuture` for independent services?
12. How would you combine multiple asynchronous calls using `CompletableFuture`?
13. How would you handle exceptions in `CompletableFuture`?
14. How would you use `ExecutorService` for asynchronous processing?

### Swagger / API Documentation
15. How would you configure Swagger documentation?
16. How would you document API methods, sample requests, responses and status codes?
17. How would you keep Swagger documentation updated when new APIs are added?

### Validation / Exception Handling
18. How would you implement request validation?
19. How would you validate DTO fields such as email and password?
20. How would you implement centralized/global exception handling?
21. How would you handle specific exceptions such as Resource Not Found, Illegal State and Illegal Argument exceptions?
22. How would you handle generic/unhandled exceptions?
23. How would you return appropriate HTTP status codes for exceptions?

### Authentication / Authorization
24. How would you implement authentication and authorization?
25. How would you implement JWT authentication?
26. How would you generate and validate JWT tokens?
27. How would you implement role-based authorization?
28. How would you use `@PreAuthorize` for roles such as Admin, Staff and Customer?
29. How would you implement refresh tokens?
30. How would you use access tokens and refresh tokens together?

### React / Frontend
31. How would you implement reusable components in the frontend?
32. How would you handle backend/API errors in React?
33. How would you structure common API calls?
34. How would you separate frontend services from modules/components?

### Caching
35. How would you store data in a cache?
36. How would you validate cached data?
37. How would you design a GET endpoint that retrieves cached product data?
38. How would you validate product price and quantity?
39. How would you handle validation failures using a global exception handler?

---

## Round 2 — L2 / S&P Global Client

### Project / Responsibilities
40. Can you describe your current project and your day-to-day activities?

### Java 21
41. What Java version are you currently working with?
42. What Java 21 features are you utilizing?
43. Have you worked with virtual threads?
44. What pattern-matching enhancements are you using in Java 21?

### Java Streams
45. How would you find student data by sorting a list of students in reverse order and picking the student with the highest score?
46. How would you use Java Streams to sort students in reverse order?
47. How would you retrieve the highest-scoring student using `findFirst()`?
48. What would be the return type of the Stream operation?

### HashMap / HashSet / equals & hashCode
49. If you use a hash-based collection such as HashMap or HashSet and insert two Student objects having the same ID and name but different scores, how will duplicates be handled?
50. How do `equals()` and `hashCode()` affect duplicate detection in a hash-based collection?
51. How would you define equality for the Student object?

### Exception Handling
52. Do you have global exception handling implemented?

### Java Checked Exceptions / Method Overriding
53. If a parent method declares `throws IOException` and a child method overrides it but declares `throws SQLException`, what happens?
54. What checked exceptions can an overriding method declare?

### Concurrency / Executor Framework
55. Have you worked with concurrency, specifically the Executor framework?
56. How have you used a custom thread-pool executor?
57. How have you used `CompletableFuture` to combine multiple independent services?

### Runnable vs Callable
58. What is the difference between `Runnable` and `Callable`?

### Thread Pool
59. How do you determine the optimal number of threads for a thread pool?
60. What factors do you consider when sizing a thread pool?

### Databases
61. What databases have you used?

### JPA / ORM / Querying
62. Have you worked with ORMs such as JPA?
63. Have you worked with Criteria Queries?
64. Have you worked with JPQL?

### SQL
65. How would you find departments with more than 50 employees?

### Architecture
66. Have you worked with both monolithic and microservices architectures?

### Spring Boot Configuration
67. How would you change the port for a Spring Boot application?
68. How would you run two instances of the same Spring Boot application on different ports?
69. How would you use separate profiles to run instances on different ports?

### Multiple DataSources / Multiple Databases
70. How would you configure two databases in a Spring Boot application?
71. How would you configure primary and secondary DataSources?
72. How would you configure separate EntityManager/persistence beans for multiple databases?

### Stored Procedures
73. How would you call a stored procedure?
74. How would you pass parameters to a stored procedure from Spring/JPA?

### Spring Transactions
75. Can `@Transactional` be used on private methods?
76. Why doesn't `@Transactional` work as expected on private methods?

### Security — External APIs
77. How do you secure external API access?
78. How do you implement JWT authentication and authorization for external API access?
79. How does the JWT filter validate the token?
80. How do you validate token authenticity and expiration?
81. How do you use roles for authorization?

### Security — Service-to-Service
82. How do you secure service-to-service communication?
83. How would you use client ID and client credentials for service-to-service authentication?
84. How would an authentication/authorization service issue credentials or access tokens for another service?

### AI / Developer Tools
85. What AI/ML tools are you currently using?
86. How are you using GitHub Copilot?
87. Are you building skills/agents for repetitive development tasks?
