# HDFC Bank — Interview Question Bank

### Java 21 / Concurrency
1. Can you explain Virtual Threads in Java 21?
2. Can you explain `ConcurrentHashMap`?
3. How does `ConcurrentHashMap` achieve thread safety?
4. How are buckets/indexes calculated in a HashMap/ConcurrentHashMap?
5. What happens when multiple elements have the same hash/index?
6. When does a linked list get converted into a Red-Black Tree?
7. How does resizing work in `ConcurrentHashMap`?
8. Why are `equals()` and `hashCode()` important for hash-based collections?

### Serialization
9. What is `serialVersionUID` used for?
10. What happens if the `serialVersionUID` of the serialized and deserialized classes doesn't match?
11. Is serialization only used when transferring data over a network?

### Asynchronous Programming
12. How do you make asynchronous calls for independent services?
13. How does `CompletableFuture.supplyAsync()` work?
14. How would you combine multiple `CompletableFuture` calls?
15. What is the difference between `CompletableFuture.allOf()` and `anyOf()`?
16. How do you handle exceptions in `CompletableFuture`?
17. Which executor does `CompletableFuture.supplyAsync()` use by default?

### External API / Microservices
18. How do you handle external API failures?
19. How would you implement timeout for an external API?
20. When should you use retries?
21. What is exponential backoff?
22. What is a Circuit Breaker?
23. Explain the Closed, Open, and Half-Open states of a Circuit Breaker.
24. What is a fallback mechanism?
25. What is the Bulkhead pattern?
26. When should you NOT retry an API call?
27. How do you handle retries for non-idempotent operations such as payments?

### Spring Transactions
28. How does the `@Transactional` annotation work in Spring?
29. How does Spring implement `@Transactional` internally?
30. What happens when an exception occurs inside a transactional method?
31. What is the default rollback behavior of `@Transactional`?
32. What is transaction propagation?
33. What is the difference between `REQUIRED` and `REQUIRES_NEW`?
34. What is transaction isolation?
35. What are the different isolation levels?
36. What is self-invocation in Spring and how can it affect `@Transactional`?

### Global Exception Handling
37. How do you implement global exception handling in Spring Boot?
38. What is the purpose of `@RestControllerAdvice`?
39. How does `@ExceptionHandler` work?
40. How would you handle specific exceptions and generic exceptions?
41. How would you design a standard error response for REST APIs?

### Coding
42. Implement a `generateToken()` method where the token can be generated only once. If `generateToken()` is called again, it should throw an exception.
43. Is your `generateToken()` implementation thread-safe?
44. How would you modify `generateToken()` to make it thread-safe?
45. What could happen if two threads call `generateToken()` simultaneously?
