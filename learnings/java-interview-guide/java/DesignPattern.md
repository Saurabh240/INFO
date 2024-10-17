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

  ### 3. What are different ways to create a singleton class
  1. Eager Initialization
In this approach, the instance of the singleton class is created at the time of class loading.

public class EagerSingleton {
    // Eagerly creating instance
    private static final EagerSingleton instance = new EagerSingleton();

    // Private constructor to prevent instantiation
    private EagerSingleton() {}

    // Public method to provide access to the instance
    public static EagerSingleton getInstance() {
        return instance;
    }
}
Pros: Simple and thread-safe because the instance is created when the class is loaded.
Cons: Instance is created even if the application might not use it, which may lead to resource wastage.

2. Lazy Initialization
The instance is created only when it is needed, not at the time of class loading.

public class LazySingleton {
    private static LazySingleton instance;

    // Private constructor
    private LazySingleton() {}

    // Public method to provide the instance
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
Pros: Instance is created only when required, saving resources.
Cons: Not thread-safe in a multithreaded environment.

3. Thread-Safe Singleton (Synchronized Method)
To make the lazy initialization thread-safe, you can synchronize the getInstance() method.

public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {}

    // Synchronized method to control simultaneous access
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
Pros: Thread-safe.
Cons: Synchronization reduces performance due to locking overhead.

