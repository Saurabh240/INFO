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

### Q-7) What is the default implementation of the hashCode method? *

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

### Q-12) What is transient in Java?
Used to indicate that a field should not be serialized.

### Q-13) Explain memory management in Java.
Managed by JVM: Stack (for method calls, local variables) and Heap (for object storage).
Garbage collector automatically reclaims memory.

### Q-14 Difference between Shallow and Deep Copy

### 1. **Shallow Copy**
- **Definition**: A shallow copy of an object copies the object’s field values, but does **not** copy objects referenced by the object’s fields. In other words, it copies the top-level object but not the nested objects.
- **Effect**: Both the original and copied objects share references to the same nested objects.

### 2. **Deep Copy**
- **Definition**: A deep copy copies the object as well as all the objects it references, recursively. This results in a fully independent clone of the original object, with no shared references between the original and the copy.
- **Effect**: Modifications to the copied object’s nested objects will not affect the original object.

---

### **Example: Shallow Copy and Deep Copy**

```java
class Address {
    String city;
    String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Shallow copy using clone()
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // Shallow copy
    }

    // Deep copy using manual method
    public Person deepCopy() {
        return new Person(this.name, new Address(this.address.city, this.address.country));  // Deep copy
    }

    @Override
    public String toString() {
        return name + " lives in " + address;
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address address1 = new Address("New York", "USA");
        Person person1 = new Person("John", address1);

        // Shallow Copy
        Person shallowCopyPerson = (Person) person1.clone();
        System.out.println("Before modification:");
        System.out.println("Original: " + person1);
        System.out.println("Shallow Copy: " + shallowCopyPerson);

        // Modify the address in the shallow copy
        shallowCopyPerson.address.city = "Los Angeles";

        System.out.println("\nAfter modifying shallow copy's address:");
        System.out.println("Original: " + person1);  // Affected due to shallow copy
        System.out.println("Shallow Copy: " + shallowCopyPerson);

        // Deep Copy
        Person deepCopyPerson = person1.deepCopy();
        deepCopyPerson.address.city = "San Francisco";

        System.out.println("\nAfter modifying deep copy's address:");
        System.out.println("Original: " + person1);  // Not affected
        System.out.println("Deep Copy: " + deepCopyPerson);
    }
}
```

### Output:
```
Before modification:
Original: John lives in New York, USA
Shallow Copy: John lives in New York, USA

After modifying shallow copy's address:
Original: John lives in Los Angeles, USA
Shallow Copy: John lives in Los Angeles, USA

After modifying deep copy's address:
Original: John lives in Los Angeles, USA
Deep Copy: John lives in San Francisco, USA
```

### **Explanation**:
- **Shallow Copy**:
  - When we modify the `city` field of `shallowCopyPerson.address`, it also changes the `address` of `person1`. This happens because both `person1` and `shallowCopyPerson` share the same `Address` object (shallow copy).
  
- **Deep Copy**:
  - When we modify the `city` field of `deepCopyPerson.address`, the original object `person1` remains unaffected. This is because a deep copy creates a completely new `Address` object for `deepCopyPerson`.
               |
### Q-15 How would you create an immutable class ?
Creating an **immutable class** in Java means designing a class whose instances cannot be modified after they are created. Immutable objects provide several advantages, such as simplicity in concurrent programming (they are inherently thread-safe) and predictable behavior.

Here are the steps and best practices for creating an immutable class in Java:

### 1. **Make the Class `final`**
   - This prevents subclassing, ensuring that the immutability cannot be compromised by a subclass.

public final class ImmutableClass {
    // class content
}

### 2. **Declare All Fields as `private` and `final`**
   - Make all fields `private` to restrict direct access, and `final` to ensure that they are assigned only once (either at the point of declaration or in the constructor).

public final class ImmutableClass {
    private final String name;
    private final int age;

    public ImmutableClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters only
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

### 3. **Do Not Provide "Setter" Methods**
   - Since the goal is to prevent modification after object creation, do not provide setter methods that allow fields to be changed.

// No setter methods

### 4. **Initialize All Fields Through the Constructor**
   - Ensure that all fields are initialized once via the constructor. Any object references passed to the constructor should also be deeply copied or cloned if mutable, to avoid unintended modifications.

public ImmutableClass(String name, int age) {
    this.name = name;
    this.age = age;
}

### 5. **Perform Deep Copy or Defensive Copy for Mutable Fields**
   - If your class contains fields that reference mutable objects, ensure that you return a deep copy (or defensive copy) of these objects from any getter methods. This prevents modification of the internal state of the class by external code.

public final class ImmutableClass {
    private final String name;
    private final int[] scores; // mutable array

    public ImmutableClass(String name, int[] scores) {
        this.name = name;
        // Defensive copy of mutable array
        this.scores = scores.clone();
    }

    public String getName() {
        return name;
    }

    // Return a copy of the array to ensure immutability
    public int[] getScores() {
        return scores.clone(); // Defensive copy
    }
}

### 6. **Ensure Class is `Serializable` if Needed, but Prevent Mutability Through Serialization**
   - If your immutable class needs to implement `Serializable`, you need to ensure that immutability is preserved during the serialization/deserialization process. Typically, immutable objects don’t require special handling during deserialization, as their state cannot change.

public final class ImmutableClass implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int age;

    public ImmutableClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters...
}

### Complete Example of an Immutable Class:

import java.util.Date;

public final class ImmutablePerson {

    // Immutable fields
    private final String name;
    private final int age;
    private final Date birthDate; // Mutable object (Date)

    // Constructor to initialize fields
    public ImmutablePerson(String name, int age, Date birthDate) {
        this.name = name;
        this.age = age;
        // Defensive copy of mutable object
        this.birthDate = new Date(birthDate.getTime());
    }

    // Getters without setters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Return a copy of the mutable object to maintain immutability
    public Date getBirthDate() {
        return new Date(birthDate.getTime());
    }
}
```

### Key Features:
1. **Class is `final`**: The class cannot be subclassed, which preserves immutability.
2. **Fields are `private` and `final`**: Fields are private to prevent external modification and final to ensure they are initialized only once.
3. **No setters**: Only getters are provided.
4. **Defensive copying**: For mutable fields (like `Date`), a defensive copy is used to ensure that internal state cannot be modified by external code.

### Additional Considerations:

1. **Immutable Collections**: For collections like `List`, `Set`, and `Map`, use `Collections.unmodifiableList()`, `Collections.unmodifiableSet()`, etc., or return a deep copy of the collection.
   ```java
   public List<String> getFriends() {
       return Collections.unmodifiableList(friends);
   }
   ```

2. **String and Wrapper Classes**: In Java, `String`, `Integer`, `Double`, and other wrapper classes are immutable by default, so you don't need to perform deep copying for them.

3. **Thread Safety**: Since immutable objects are inherently thread-safe, no synchronization is required when sharing them between threads.

### Summary of Steps to Create an Immutable Class:
1. Make the class `final`.
2. Make all fields `private` and `final`.
3. Initialize fields through the constructor.
4. Do not provide setters.
5. Perform deep copy for mutable fields when passing them to or returning them from the class.
6. Optionally implement `Serializable` if the class needs to be serialized.