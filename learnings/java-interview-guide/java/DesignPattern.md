**1) What are the Design Patterns you have used?**

- **What design patterns have you implemented in your projects?**
  - **Data Access Object (DAO):** Used for data access layers to separate database logic.
  - **Singleton:** Ensures only one instance of a class, typically used for utility classes.
  - **Prototype:** Configured in frameworks like Spring to define bean scopes (singleton or prototype).
  - **Dependency Injection (DI) and Inversion of Control (IoC):** Used in Spring for managing object dependencies and lifecycle.
  - **Model-View-Controller (MVC):** Applied in web layers, particularly with Spring Web.
  - **Factory:** Used to create instances of utility classes or other objects.
  - **Facade, Builder, Command:** Additional patterns applied as needed for various use cases.

**2) What are Singleton Best Practices?**

- **How do you implement the Singleton pattern and what best practices do you follow?**
  - **Private Constructor:** Ensure the class cannot be instantiated from outside.
  - **Static Instance Field:** Define a static field to hold the single instance.
  - **Static Method:** Provide a static method to create and return the single instance.
  - **Lazy Initialization with Synchronization:** Use a static block or synchronized method to ensure thread safety.
  - **Prevent Cloning:** Implement `Cloneable` interface and override the `clone` method to throw an exception if cloning is attempted.
  - **Prevent Serialization/Deserialization Issues:** Implement `readResolve` method to ensure the same instance is returned during deserialization.
