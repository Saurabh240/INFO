### Q-1) What are the important components involved in compiling and running a Java program?

- The first software needed is the **JDK (Java Development Kit)**, which includes the **JRE (Java Runtime Environment)** and essential tools like the Java compiler.
- JDKs are specific to operating systems, and while JRE can be installed separately, usually both are installed together.
- Java Compiler: Converts Java classes into platform-independent **bytecode**.
- JVM (Java Virtual Machine): Interprets bytecode to machine code, making Java platform-independent.
- JIT (Just-In-Time) Compiler: Part of JVM that optimizes bytecode interpretation by compiling it to machine code during runtime.
- By default, JIT is enabled, but it can be disabled for debugging purposes.

### Q-2) What are constructors, and how do they differ from other methods?

- A constructor initializes object properties when it's created and has the same name as the class.
- Unlike other methods, a constructor is invoked only once when an object is instantiated.
- You can invoke one constructor from another within the same class using the `this()` method, and the superclass constructor using the `super()` method.

### Q-3) What is the difference between an abstract class and an interface?

- An abstract class may have both abstract and concrete methods. If a class has even one abstract method, it must be declared abstract.
- An interface can only contain abstract methods (until Java 8). A class can implement multiple interfaces but can extend only one abstract class.

### Q-4) Why is multiple inheritance not supported in Java?

- Multiple inheritance is not supported due to potential conflicts when two superclasses have methods with the same signature (e.g., the "diamond problem").
- This could cause ambiguity during method invocation, leading to compile-time errors.

### Q-5) Can a class implement two interfaces with the same method signature?

- Yes, a class can implement multiple interfaces with the same method signature. The class must provide an implementation for that method, avoiding issues common with multiple inheritance in other languages.

### Q-6) What are the methods inherited from the Object class?

- Key methods: `equals()`, `hashCode()`, `finalize()`, `clone()`, and `toString()`.
- Synchronization methods: `wait()`, `notify()`, etc., which cannot be overridden but are used in multithreading.

### Q-7) What is the default implementation of the hashCode method?

- If not overridden, the `hashCode()` method returns the memory location of the object as a hash value.

### Q-8) What is the default implementation of the toString method?

- The `toString()` method returns a string representation in the format: `ClassName@HexadecimalHashcode`.

### Q-9) What is the difference between the equals method and the == operator?

- The `==` operator compares **object references** (shallow comparison), while the `equals()` method, by default, also does the same unless overridden for deep comparison.
- Strings, primitives, and enums have overridden `equals()` methods for deep comparison.

### Q-10) What are the differences between final, finally, and finalize?

- final: A keyword to declare constants, prevent method overriding, and class inheritance.
- finally: A block in exception handling that executes regardless of whether an exception occurs.
- finalize(): A method called by the garbage collector before an object is destroyed, but its execution is not guaranteed.

### Q-11) What are generics, and what is type erasure?

- Generics: Introduced in Java 1.5 to enforce type safety by specifying the type of objects stored in collections.
- Type Erasure: The compiler removes generic type information after ensuring type safety at compile time, allowing older Java code to run on newer JVMs.
