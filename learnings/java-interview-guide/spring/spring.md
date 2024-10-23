### ***Spring***
### **Spring Core:**

1. **What is Dependency Injection (DI) in Spring?**
   - DI is a design pattern where an object receives its dependencies from an external source rather than creating them itself. In Spring, DI is managed by the IoC container, which automatically wires together components.

2. **What are the types of Dependency Injection in Spring?**
   - There are two main types:
     - **Constructor Injection**: Dependencies are provided via the constructor.
     - **Setter Injection**: Dependencies are provided through setter methods.

3. **What is the IoC container in Spring?**
   - The IoC (Inversion of Control) container in Spring is responsible for managing the lifecycle and dependencies of beans. It instantiates, configures, and manages objects using configuration metadata (XML, annotations, or Java config).

4. **What is the difference between `BeanFactory` and `ApplicationContext`?**
   - `BeanFactory` is the basic IoC container, whereas `ApplicationContext` is a more advanced container that supports features like event propagation, declarative transactions, and AOP integration.

5. **What are Spring Bean Scopes?**
   - Bean scopes define the lifecycle of beans. Common scopes include:
     - **Singleton**: A single instance per Spring container.
     - **Prototype**: A new instance is created for every request.
     - **Request/Session**: Used in web applications for request and session scopes.

6. **What are `@PostConstruct` and `@PreDestroy` annotations?**
   - `@PostConstruct` is called after the bean has been initialized, and `@PreDestroy` is called just before the bean is destroyed. They are lifecycle callback methods.

7. **What is a `BeanPostProcessor` in Spring?**
   - `BeanPostProcessor` is used to intercept bean initialization logic before and after the Spring container instantiates and configures a bean.

---

### **Spring MVC:**

8. **What is Spring MVC?**
   - Spring MVC is a framework for building web applications. It follows the Model-View-Controller design pattern, where the controller handles HTTP requests, the model represents data, and the view renders the response.

9. **What is the difference between `@RequestMapping`, `@GetMapping`, and `@PostMapping`?**
   - `@RequestMapping` is a general-purpose annotation to map any HTTP method, whereas `@GetMapping` is specifically for GET requests and `@PostMapping` for POST requests.

10. **What is the purpose of `@ModelAttribute` in Spring MVC?**
    - `@ModelAttribute` binds form data to a model object, automatically populating the model with request parameters, which can be used in the controller and view.

11. **What is `@RequestParam` vs. `@PathVariable` in Spring MVC?**
    - :
      - `@RequestParam` is used to extract query parameters from the request URL.
      - `@PathVariable` is used to extract URI path parameters from the URL.

12. **What is a ViewResolver in Spring MVC?**
    - A ViewResolver resolves logical view names returned by controllers to actual view templates (e.g., JSP, Thymeleaf) for rendering.

13. **What is an Interceptor in Spring MVC?**
    - An Interceptor is used to perform pre-processing and post-processing on HTTP requests before they reach the controller or after the controller processes them. It is useful for tasks like logging, authentication, or modifying the response.

---

### **Spring REST:**

14. **What is the difference between REST and SOAP?**
    - REST is an architectural style that uses HTTP for communication, is stateless, and supports various data formats (JSON, XML). SOAP is a protocol that relies on XML and provides more rigid structure and standards for communication.

15. **How do you handle exceptions in Spring REST?**
    - You can handle exceptions globally using `@ControllerAdvice` and `@ExceptionHandler` annotations to map exceptions to specific HTTP response codes and messages.

16. **What is the purpose of `@ResponseBody` and `@RestController` in Spring REST?**
    -
      - `@ResponseBody` tells Spring to write the return value of a method directly to the HTTP response body as JSON or XML.
      - `@RestController` is a combination of `@Controller` and `@ResponseBody`, indicating that the class is a REST controller.

17. **How does Spring handle content negotiation?**
    - Content negotiation is the process of deciding the content type (e.g., JSON, XML) to return based on the request headers (`Accept`). Spring uses `ContentNegotiatingViewResolver` or annotations like `@Produces` and `@Consumes` for this.

---

### **Spring Cloud (for Microservices):**

18. **What is Spring Cloud Config?**
    - Spring Cloud Config provides server-side and client-side support for centralized configuration management across microservices. It allows services to fetch configuration properties from a central configuration server.

19. **What is Spring Cloud Eureka?**
    - Eureka is a service registry where microservices register themselves, and clients discover services using this registry for load balancing and service discovery.

20. **What is Feign in Spring Cloud?**
    - Feign is a declarative REST client in Spring Cloud that simplifies the way services communicate with each other by writing minimal code for HTTP requests and responses.

21. **What is Hystrix and how does it provide fault tolerance?**
    - Hystrix implements the circuit breaker pattern in microservices to prevent cascading failures. It monitors calls to external services and opens a circuit (stops calls) if failures reach a certain threshold.

---

### **Caching in Spring:**

22. **What is Spring Cache Abstraction?**
    - Spring Cache Abstraction provides a consistent way to add caching capabilities to Spring applications. It abstracts cache operations via annotations like `@Cacheable`, `@CacheEvict`, and `@CachePut`.

23. **How do you enable caching in Spring Boot?**
    - Caching is enabled by adding the `@EnableCaching` annotation in a Spring configuration class and using annotations like `@Cacheable` on methods where you want to cache results.

24. **What caching providers does Spring support?**
    - Spring supports various caching providers like Ehcache, Hazelcast, Redis, and others. You can configure these providers in `application.properties` or `application.yml`.

---

### **Spring Batch:**

25. **What is Spring Batch?**
    - Spring Batch is a framework for building batch processing applications. It supports large-scale data processing in jobs, which can include steps like reading, processing, and writing data.

26. **What are the key components of Spring Batch?**
    - The key components include:
      - **Job**: Represents the batch process.
      - **Step**: Represents a stage in the job (e.g., reading, processing, writing).
      - **ItemReader**: Reads input data.
      - **ItemProcessor**: Processes data.
      - **ItemWriter**: Writes the processed data to the output.

---

### **Spring WebFlux (Reactive Programming):**

27. **What is Spring WebFlux?**
    - Spring WebFlux is the reactive web framework introduced in Spring 5, designed for non-blocking, asynchronous applications using the Reactive Streams API with `Mono` and `Flux`.

28. **What is the difference between `Mono` and `Flux` in Spring WebFlux?** 
      - `Mono` represents a single value or an empty value.
      - `Flux` represents a stream of 0 to many values.

29. **What are the advantages of using Spring WebFlux over Spring MVC?**
    - Spring WebFlux provides better scalability for high-concurrency applications since it uses a non-blocking event-driven model. It’s more suited for handling large volumes of requests without creating a thread for each request.

---

### **Spring Boot Actuator:**

30. **What is Spring Boot Actuator?**
    - Spring Boot Actuator provides production-ready features like monitoring, health checks, and metrics collection through a set of endpoints (`/actuator/*`).

31. **How do you expose custom metrics in Spring Boot Actuator?**
    - You can create custom metrics by injecting a `MeterRegistry` bean and using it to define and register metrics.

---

### **Spring Testing:**

32. **What is `@WebMvcTest` used for?**
    - `@WebMvcTest` is used for testing Spring MVC controllers. It focuses only on the web layer, disabling full Spring context loading.

33. **How do you perform integration testing in Spring Boot?**
    - Integration tests are performed using `@SpringBootTest`, which loads the full application context. This annotation can be combined with TestContainers for running external dependencies like databases.

34. **How do you mock dependencies in Spring tests?**
    - Dependencies can be mocked using the `@MockBean` annotation from Spring or using libraries like Mockito to mock service/repository dependencies