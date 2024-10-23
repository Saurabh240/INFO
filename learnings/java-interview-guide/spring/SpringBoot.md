# ***Spring Boot Interview Questions***
### **Easy Questions**

1. **What is Spring Boot, and why did you use Spring Boot in your project?**
   - Spring Boot is a framework for RAD (Rapid Application Development) using Spring Framework with auto-configuration and embedded servers like Tomcat or Jetty.

2. **What are the advantages of using Spring Boot?**
   - Rapid development, auto-configuration, embedded servers, no XML configuration, production-ready features, and starter dependencies.

3. **What is the default port of Tomcat in Spring Boot?**
   - The default port for embedded Tomcat is **8080**.

4. **Is it possible to change the port of the embedded Tomcat server in Spring Boot?**
   - Yes, by setting `server.port=<new_port>` in `application.properties` or `application.yml`.

5. **Can we create a non-web application in Spring Boot?**
   - Yes, Spring Boot can create non-web applications like batch processing apps by excluding web-related dependencies.

6. **What are starter dependencies?**
   - Starter dependencies simplify setting up Spring Boot projects with predefined dependencies for common use cases.

7. **What is @SpringBootApplication?**
   - `@SpringBootApplication` is a meta-annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.

8. **What is @RestController in Spring Boot?**
   - `@RestController` is a combination of `@Controller` and `@ResponseBody`, used to create RESTful web services that return JSON or XML.

9. **What is the difference between @RestController and @Controller in Spring Boot?**
   - `@RestController` returns data (JSON/XML), while `@Controller` returns views (HTML pages).

10. **What is Spring Initializr?**
    - Spring Initializr is a web-based tool to generate Spring Boot projects with selected dependencies.

11. **What are the basic annotations that Spring Boot offers?**
    - `@SpringBootApplication`, `@RestController`, `@Autowired`, `@RequestMapping`, `@GetMapping`, etc.

12. **What is Dependency Injection?**
    - A design pattern in which an object receives its dependencies from an external source, typically managed by the Spring IoC container.

13. **What is @Autowired in Spring?**
    - `@Autowired` is used for dependency injection, allowing Spring to automatically inject beans where needed.

### **Intermediate Questions**

14. **What are Spring Boot Profiles?**
    - Profiles allow environment-specific configurations (e.g., `dev`, `prod`). They can be activated via `spring.profiles.active`.

15. **What is Spring Boot Actuator?**
    - Spring Boot Actuator provides production-ready features for monitoring and managing applications (e.g., health checks, metrics).

16. **How does Spring Boot work?**
    - Spring Boot uses auto-configuration to automatically set up application components based on classpath dependencies. It also uses embedded servers like Tomcat.

17. **How does a Spring Boot application get started?**
    - By calling `SpringApplication.run()` from the main class annotated with `@SpringBootApplication`.

18. **What is the purpose of using @ComponentScan?**
    - `@ComponentScan` tells Spring where to scan for components, services, and other Spring-managed beans.

19. **What is Spring Boot CLI, and what are its benefits?**
    - Spring Boot CLI is a command-line tool to quickly develop and run Spring Boot applications using Groovy scripts. It reduces boilerplate code.

20. **What are the most common Spring Boot CLI commands?**
    - `spring run`, `spring init`, `spring test`, among others.

21. **What are Spring Bean Scopes?**
    - Scopes like `Singleton`, `Prototype`, `Request`, `Session`, and `Global Session` define the lifecycle of Spring beans.

22. **What is Inversion of Control (IoC)?**
    - IoC is a principle where the control of object creation is transferred to the Spring IoC container instead of being managed by application code.

23. **What is an IoC container?**
    - The Spring IoC container is responsible for managing the lifecycle and dependencies of beans in the application context.

24. **How do you use a property defined in the application.properties file in your Java class?**
    - Use `@Value("${property.name}")` to inject property values.

25. **What is @SpringBootTest?**
    - `@SpringBootTest` is an annotation to run tests with a full Spring application context, allowing integration testing.

### **Advanced Questions**

26. **What is the difference between RequestMapping and GetMapping?**
    - `@RequestMapping` is a general-purpose mapping for all HTTP methods, while `@GetMapping` is a shortcut for mapping GET requests only.

27. **Can we override or replace the embedded Tomcat server in Spring Boot?**
    - Yes, Spring Boot allows replacing embedded Tomcat with servers like Jetty or Undertow by including the appropriate starter dependency.

28. **Can we disable the default web server in a Spring Boot application?**
    - Yes, by setting `spring.main.web-application-type=none` in `application.properties`.

29. **How to disable a specific auto-configuration class?**
    - Use `@EnableAutoConfiguration(exclude = {ClassName.class})` or `@SpringBootApplication(exclude = {ClassName.class})`.

30. **What are the actuator-provided endpoints used for monitoring a Spring Boot application?**
    - Common endpoints include `/actuator/health`, `/actuator/metrics`, `/actuator/info`, and `/actuator/env`.

31. **How to get the list of all beans in your Spring Boot application?**
    - You can either use the `/actuator/beans` endpoint or retrieve beans from `ApplicationContext.getBeanDefinitionNames()`.

32. **How to enable debugging logs in a Spring Boot application?**
    - Set `logging.level.root=DEBUG` in `application.properties`.

33. **What are the key components of Spring Boot?**
    - Spring Boot Starters, Auto-Configuration, Spring Boot CLI, and Spring Boot Actuator.

34. **Why use Spring Boot over Spring?**
    - Spring Boot simplifies development with auto-configuration, embedded servers, and reduced configuration overhead compared to traditional Spring.

35. **What are the problems with traditional Spring?**
    - Configuration complexity, manual dependency management, and deployment challenges that are simplified in Spring Boot.

36. **Describe the flow of HTTPS requests through a Spring Boot application.**
    1. HTTPS request is received and decrypted by the embedded server.
    2. The request is forwarded to the Spring Boot application.
    3. Mapped to a controller method, which processes the request and returns a response.

37. **What is Spring Boot dependency management?**
    - Spring Boot provides curated dependency versions through starters, ensuring compatibility and simplifying project setup.

38. **What are the use cases for Spring Profiles?**
    - Managing environment-specific configurations (e.g., `dev`, `test`, `prod`), allowing easy deployment to different environments.

39. **How to enable Spring Actuator in a Spring Boot application?**
    - Add the `spring-boot-starter-actuator` dependency to your project.

40. **What is the starter dependency of the Spring Boot module?**
    - Starters are pre-configured dependencies, like `spring-boot-starter-web` or `spring-boot-starter-data-jpa`, that simplify setup for common tasks.