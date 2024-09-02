# Spring Framework and Spring Boot Overview

## Q-1: What is Dependency Injection and IoC?

### Dependency Injection (DI)

- **Definition**: DI is a design pattern where an external component provides the dependencies of a class rather than the class creating them internally.
- **Example**:
  - **Without DI**: A `ProductController` creates an instance of `ProductDao` inside itself.
  - **With DI**: The `ProductController` has its `ProductDao` dependency injected by an external framework, such as Spring.
- **Benefit**: Focuses on business logic rather than object creation, leading to more manageable and testable code.

### Inversion of Control (IoC)

- **Definition**: IoC is a principle where the control of object creation is shifted from the application code to an external framework or container.
- **Purpose**: Moves the responsibility of creating and managing object dependencies away from the application to a framework like Spring.
- **Example**: Spring dynamically creates and injects `ProductDao` into `ProductController`, adhering to the IoC principle.

## Q-2: What are the Spring Bean Scopes?

### Singleton Scope

- **Definition**: Only one instance of the bean is created per Spring IoC container. The same instance is used throughout the application.
- **Default Scope**: Singleton is the default bean scope in Spring.
- **Use Case**: Suitable for stateless beans like controllers and DAOs.

### Prototype Scope

- **Definition**: A new instance of the bean is created every time it is requested.
- **Use Case**: Suitable for stateful beans, as each request gets a new instance, preventing data corruption across threads.

## Q-3: Prototype in Singleton

### Can a Prototype Bean be Injected into a Singleton Bean?

- **Answer**: Yes, a prototype bean can be injected into a singleton bean. However, the singleton bean will use the same instance of the prototype bean that was injected at runtime. To obtain a new instance of the prototype bean, you may need to manually request it from the application context.

## Q-4: What are HTTP Scopes?

### Request Scope

- **Definition**: A new bean instance is created for each HTTP request.
- **Use Case**: Ideal for beans that need to be unique per request.

### Session Scope

- **Definition**: A single bean instance is used across an HTTP session, which may consist of multiple requests and responses.
- **Use Case**: Suitable for beans that need to maintain state across multiple requests within the same session.

### Global Session Scope

- **Definition**: Applies to portlet applications, creating a single bean instance across all portlets in a global session.
- **Use Case**: Useful in portlet-based applications for maintaining state across portlets.

## Q-5: What are the Problems with Traditional Spring?

### Configuration Complexity

- **Pre-Spring Boot**: Required extensive XML or Java-based configuration for different modules (e.g., Core, MVC, DAO, ORM).
- **Issue**: Maintaining configuration and ensuring compatibility between modules was cumbersome.

### Dependency Management

- **Pre-Spring Boot**: Manual inclusion of dependencies in Maven or Gradle files, with compatibility checks between versions.

### Deployment Challenges

- **Pre-Spring Boot**: Applications needed to be manually deployed to external web containers or application servers.

## Q-6: Why Use Spring Boot?

### Auto-Configuration

- **Definition**: Automatically configures the application based on dependencies on the classpath, reducing manual setup.
- **Example**: Adds and configures a DispatcherServlet for web applications or a DataSource for JPA.

### Dependency Management

- **Spring Boot Starters**: Simplify dependency inclusion and version management through predefined starters (e.g., `spring-boot-starter-web`, `spring-boot-starter-data-jpa`).

### Embedded Containers

- **Definition**: Provides embedded servlet containers (e.g., Tomcat, Jetty, Undertow), making deployment simpler.

### Actuators

- **Definition**: Offers endpoints to monitor application health, configuration, and metrics, enhancing application management and monitoring.

## Q-7: What is @SpringBootApplication?

### Definition

- **Annotation**: The entry point for a Spring Boot application, combining three annotations:
  - **@SpringBootConfiguration**: Marks the class as a source of bean definitions.
  - **@EnableAutoConfiguration**: Enables auto-configuration based on classpath dependencies.
  - **@ComponentScan**: Scans for components in the package and its sub-packages.

### Purpose

- **Combines**: Configures and initializes the application context, enabling automatic setup and scanning for Spring components.

## Q-8: What is @SpringBootTest?

### Definition

- **Annotation**: Used to run tests with a Spring application context.
- **Function**: Loads the entire Spring context, making all beans available for testing.
- **Mechanism**: Uses a Spring extension class to create and manage the Spring context, then runs tests with JUnit.
