# Spring Framework and Spring Boot Overview

## Q-1: What is Spring Boot, and why did you use Spring Boot in your project?
**A**: Spring Boot is a framework for RAD (Rapid Application Development) using Spring Framework with support for auto-configuration and embedded application servers like Tomcat or Jetty. It helps in creating production-ready standalone applications that you can easily run.

## Q-2: Is it possible to change the port of the embedded Tomcat server in Spring Boot?
**A**: Yes, by default, the port is 8080, but you can change it in `application.properties` by setting `server.port=<new_port_no>`.

## Q-3: How can you disable a specific auto-configuration class?
**A**: You can disable an auto-configuration class by using the `exclude` attribute.  
Example:
```java
@EnableAutoConfiguration(exclude={DataSourceConfiguration.class})
```

## Q-4: What is @SpringBootApplication in Spring Boot?
**A**: `@SpringBootApplication` is a meta-annotation that combines:
- `@SpringBootConfiguration`: Marks the class as a source of bean definitions.
- `@EnableAutoConfiguration`: Enables auto-configuration based on classpath dependencies.
- `@ComponentScan`: Scans for components in the package and its sub-packages.

## Q-5: How do you use a property defined in the application.properties file in your Java class?
**A**: You can use the `@Value` annotation to access properties defined in the `application.properties` file.
Example:
```java
@Value("${custom.value}")
private String customVal;
```

## Q-6: What is the difference between @Controller and @RestController in Spring?
**A**:
- `@Controller`: Returns a view and is used for web MVC applications.
- `@RestController`: A combination of `@Controller` and `@ResponseBody`, returning JSON/XML data directly instead of a view.

## Q-7: What are Spring Boot Profiles?
**A**: Profiles manage environment-specific configurations (`dev`, `test`, `prod`).
- You can define profile-specific properties files (e.g., `application-dev.properties`).
- Activate profiles via the `spring.profiles.active` property or JVM argument `-Dspring.profiles.active`.

## Q-8: What is a Spring Boot Actuator?
**A**: The Spring Boot Actuator provides operational information such as application health, metrics, environment properties, and more for running Spring Boot applications.

## Q-9: What is Dependency Injection (DI) and Inversion of Control (IoC)?
**A**: 
- **Dependency Injection (DI)**: It is a design pattern where an external component provides the dependencies of a class rather than the class creating them. It focuses on business logic rather than object creation, making code more manageable and testable.
- **Inversion of Control (IoC)**: IoC is a principle that shifts the control of object creation from the application code to an external framework like Spring. This moves the responsibility of managing object dependencies to the framework.

## Q-10: What are Spring Bean Scopes?
**A**:
- **Singleton Scope**: Only one instance of the bean is created per Spring IoC container. It is the default scope and is used for stateless beans.
- **Prototype Scope**: A new instance is created each time it is requested, making it suitable for stateful beans.
- **Request Scope**: Creates a new bean instance for each HTTP request.
- **Session Scope**: A single bean instance is used across an HTTP session.
- **Global Session Scope**: Used in portlet applications, creating a single instance across all portlets in a global session.

## Q-11: Can a Prototype Bean be injected into a Singleton Bean?
**A**: Yes, a prototype bean can be injected into a singleton bean. However, the same prototype instance will be used unless you manually request a new instance from the application context.

## Q-12: What are the Problems with Traditional Spring?
**A**:
- **Configuration Complexity**: Required extensive XML or Java-based configuration for different modules.
- **Dependency Management**: Manual dependency inclusion with compatibility checks between versions.
- **Deployment Challenges**: Required manual deployment to external web or application servers.

## Q-13: Why use Spring Boot?
**A**:
- **Auto-Configuration**: Automatically configures the application based on classpath dependencies.
- **Dependency Management**: Simplifies dependency management through predefined starters.
- **Embedded Containers**: Provides embedded servlet containers like Tomcat, making deployment easier.
- **Actuators**: Offers endpoints to monitor application health, configuration, and metrics.

## Q-14: What is @SpringBootTest?
**A**: `@SpringBootTest` is an annotation used to run tests with a Spring application context. It loads the entire Spring context, making all beans available for testing.

## Q-15: What is @Autowired in Spring?
**A**: `@Autowired` is used for dependency injection. Spring automatically resolves and injects beans marked with this annotation.