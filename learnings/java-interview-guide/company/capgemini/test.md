Here are some common **Java and GCP-related interview questions** for a Level 1 interview tailored for candidates with 6+ years of experience. The questions cover Java concepts, GCP services, and practical problem-solving skills.

---

### **Java Questions**
#### 1. **What is the difference between `HashMap` and `ConcurrentHashMap`?**
- **Answer**:
  - **HashMap**: 
    - Not thread-safe.
    - Allows one null key and multiple null values.
    - Uses fail-fast iteration.
  - **ConcurrentHashMap**: 
    - Thread-safe (uses bucket-level locking for better concurrency).
    - Does not allow null keys or null values.
    - Uses fail-safe iteration (doesn't throw `ConcurrentModificationException`).

---

#### 2. **What are the different types of class loaders in Java?**
- **Answer**:
  1. **Bootstrap ClassLoader**: Loads core Java classes (e.g., `java.lang`, `java.util`).
  2. **Extension ClassLoader**: Loads classes from the `ext` directory or `java.ext.dirs`.
  3. **System/Application ClassLoader**: Loads classes from the application's classpath.
  4. **Custom ClassLoader**: User-defined class loaders.

---

#### 3. **Explain Spring Boot’s auto-configuration. How does it work?**
- **Answer**:
  - Spring Boot auto-configuration automatically configures Spring beans based on the classpath dependencies.
  - It uses the `@EnableAutoConfiguration` or `spring.factories` mechanism to load configuration classes.
  - Example: If `spring-boot-starter-web` is added, it auto-configures `DispatcherServlet`, `ViewResolver`, and other web-related beans.

---

#### 4. **What is a functional interface in Java? Provide an example.**
- **Answer**:
  - A **functional interface** is an interface with exactly one abstract method.
  - Example: 
    ```java
    @FunctionalInterface
    public interface Calculator {
        int add(int a, int b);
    }
    Calculator calc = (a, b) -> a + b;
    System.out.println(calc.add(5, 10)); // Output: 15
    ```

---

#### 5. **How does garbage collection work in Java?**
- **Answer**:
  - Java's garbage collection is automatic memory management that frees unused objects.
  - Garbage collectors work in different generations:
    - **Young Generation**: Newly created objects; minor GC cleans this area.
    - **Old Generation**: Long-lived objects; major GC occurs less frequently.
    - **Permanent Generation** (pre-Java 8): Metadata used by JVM.
  - Algorithms: **Mark-and-Sweep**, **G1 GC**, **CMS GC**, etc.

---

### **Google Cloud Platform (GCP) Questions**
#### 6. **What are the key GCP services you have worked with?**
- **Answer**:
  - **Compute Engine**: Virtual machines.
  - **App Engine**: PaaS for web applications.
  - **Cloud Functions**: Event-driven serverless functions.
  - **Cloud Storage**: Object storage.
  - **BigQuery**: Data warehouse for analytics.
  - **Cloud Pub/Sub**: Messaging system for asynchronous communication.
  - **Kubernetes Engine (GKE)**: Managed Kubernetes.

---

#### 7. **How does GCP IAM (Identity and Access Management) work?**
- **Answer**:
  - IAM controls who can access resources and what they can do.
  - Key components:
    - **Roles**: Predefined (e.g., Viewer, Editor) or custom.
    - **Permissions**: Define actions on resources.
    - **Policies**: Bind members to roles for specific resources.

---

#### 8. **How would you deploy a Spring Boot application to GCP?**
- **Answer**:
  1. **Create a JAR/WAR** of the application.
  2. Deploy to one of the following:
     - **Compute Engine**: Use a VM and install JDK.
     - **App Engine**: Use a `app.yaml` configuration file.
     - **Kubernetes Engine**: Containerize the app using Docker and deploy it on GKE.
  3. Use GCP services like Cloud SQL, Pub/Sub, or Memorystore if needed.

---

#### 9. **Explain how Pub/Sub works in GCP.**
- **Answer**:
  - **Pub/Sub** is an asynchronous messaging system.
  - Components:
    - **Publisher**: Sends messages to a topic.
    - **Subscriber**: Pulls messages from a subscription.
    - **Topic**: Channels where messages are sent.
  - Use cases: Event-driven systems, decoupled microservices communication.

---

#### 10. **What are the storage options in GCP? Compare them.**
- **Answer**:
  - **Cloud Storage**: Object storage, unstructured data.
  - **Cloud SQL**: Relational databases (e.g., MySQL, PostgreSQL).
  - **Cloud Spanner**: Globally distributed, consistent database.
  - **Bigtable**: NoSQL database for large analytical workloads.
  - **Firestore**: NoSQL database for mobile and web apps.

---

#### 11. **How do you monitor and debug GCP applications?**
- **Answer**:
  - Use **Stackdriver (Cloud Monitoring and Logging)**:
    - **Logging**: View application logs.
    - **Monitoring**: Track metrics, uptime checks, and create alerts.
    - **Error Reporting**: Identify and analyze exceptions.
    - **Trace**: View request latencies.

---

#### 12. **How do you secure applications in GCP?**
- **Answer**:
  - Use **IAM roles** and least privilege principles.
  - Encrypt data at rest and in transit (Cloud KMS).
  - Use **VPC Service Controls** for resource isolation.
  - Apply firewall rules for Compute Engine instances.

---

### **Scenario-Based Questions**
#### 13. **How would you design a scalable and fault-tolerant application on GCP?**
- **Answer**:
  - Use **GKE or App Engine** for scalability.
  - Employ **Cloud Pub/Sub** for decoupled communication.
  - Use **Cloud Storage** or **Bigtable** for data storage.
  - Set up **Load Balancers** for fault tolerance.
  - Monitor using **Stackdriver**.

---

#### 14. **How would you troubleshoot latency in a microservices architecture on GCP?**
- **Answer**:
  1. Use **Stackdriver Trace** to analyze request latencies.
  2. Check **Cloud Logging** for bottlenecks or errors.
  3. Optimize APIs or database queries.
  4. Consider scaling instances or improving network configuration.

---

### **Additional Tips for Capgemini Interviews**
1. Highlight experience with **Java frameworks** like Spring Boot and Hibernate.
2. Focus on practical use cases of GCP in your past projects.
3. Prepare to explain **design patterns** and architectural decisions.
4. Demonstrate knowledge of **CI/CD pipelines** and DevOps tools (if applicable).

Let me know if you'd like detailed explanations for any specific topics!


Here are **practical Java Stream API coding questions** that cover various aspects like filtering, mapping, reducing, grouping, sorting, and parallel streams. Each question includes a detailed solution.

---

### **1. Filter Even Numbers from a List**
**Question**: Given a list of integers, filter out all even numbers and return them in a new list.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> evenNumbers = numbers.stream()
                                           .filter(n -> n % 2 == 0)
                                           .collect(Collectors.toList());
        
        System.out.println(evenNumbers); // Output: [2, 4, 6, 8, 10]
    }
}
```

---

### **2. Find Maximum and Minimum in a List**
**Question**: Find the maximum and minimum values in a list using streams.

**Solution**:
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 7, 1, 9, 4, 6, 2);
        
        int max = numbers.stream().max(Integer::compareTo).orElseThrow();
        int min = numbers.stream().min(Integer::compareTo).orElseThrow();
        
        System.out.println("Max: " + max); // Output: 9
        System.out.println("Min: " + min); // Output: 1
    }
}
```

---

### **3. Sum of Numbers in a List**
**Question**: Calculate the sum of all numbers in a list.

**Solution**:
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        int sum = numbers.stream()
                         .reduce(0, Integer::sum);
        
        System.out.println("Sum: " + sum); // Output: 15
    }
}
```

---

### **4. Count the Number of Words Starting with a Specific Letter**
**Question**: Given a list of words, count how many start with the letter 'a'.

**Solution**:
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "apricot", "grape", "avocado");
        
        long count = words.stream()
                          .filter(word -> word.startsWith("a"))
                          .count();
        
        System.out.println("Words starting with 'a': " + count); // Output: 3
    }
}
```

---

### **5. Group Words by Their Length**
**Question**: Group a list of words by their lengths.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "grape", "kiwi", "pear");
        
        Map<Integer, List<String>> groupedByLength = words.stream()
                                                          .collect(Collectors.groupingBy(String::length));
        
        System.out.println(groupedByLength);
        // Output: {5=[apple, grape], 6=[banana], 4=[kiwi, pear]}
    }
}
```

---

### **6. Convert List of Strings to Uppercase**
**Question**: Convert all strings in a list to uppercase.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        List<String> uppercased = words.stream()
                                       .map(String::toUpperCase)
                                       .collect(Collectors.toList());
        
        System.out.println(uppercased); // Output: [APPLE, BANANA, CHERRY]
    }
}
```

---

### **7. Sort a List of Objects by a Field**
**Question**: Sort a list of employees by their salary in descending order.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String name;
    double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return name + ": " + salary;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000),
            new Employee("Bob", 60000),
            new Employee("Charlie", 55000)
        );
        
        List<Employee> sortedBySalary = employees.stream()
                                                 .sorted((e1, e2) -> Double.compare(e2.salary, e1.salary))
                                                 .collect(Collectors.toList());
        
        sortedBySalary.forEach(System.out::println);
        // Output:
        // Bob: 60000.0
        // Charlie: 55000.0
        // Alice: 50000.0
    }
}
```

---

### **8. Parallel Stream to Process a Large List**
**Question**: Use a parallel stream to find the sum of squares of numbers in a large list.

**Solution**:
```java
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 10000).boxed().toList();
        
        long sumOfSquares = numbers.parallelStream()
                                   .mapToLong(n -> n * n)
                                   .sum();
        
        System.out.println("Sum of squares: " + sumOfSquares);
        // Output: 333383335000
    }
}
```

---

### **9. Remove Duplicates from a List**
**Question**: Remove duplicate elements from a list of integers.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        
        List<Integer> uniqueNumbers = numbers.stream()
                                             .distinct()
                                             .collect(Collectors.toList());
        
        System.out.println(uniqueNumbers); // Output: [1, 2, 3, 4, 5]
    }
}
```

---

### **10. Find First Non-Repeating Character in a String**
**Question**: Given a string, find the first non-repeating character.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String input = "swiss";
        
        Optional<Character> firstNonRepeating = input.chars()
                                                     .mapToObj(c -> (char) c)
                                                     .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()))
                                                     .entrySet().stream()
                                                     .filter(entry -> entry.getValue() == 1)
                                                     .map(Map.Entry::getKey)
                                                     .findFirst();
        
        System.out.println("First non-repeating character: " + firstNonRepeating.orElse(null));
        // Output: w
    }
}
```

---

### **11. Partition Numbers into Even and Odd**
**Question**: Partition a list of integers into even and odd numbers.

**Solution**:
```java
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                                                         .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        
        System.out.println("Even: " + partitioned.get(true)); // Output: [2, 4, 6, 8]
        System.out.println("Odd: " + partitioned.get(false)); // Output: [1, 3, 5, 7]
    }
}
```

---

These examples demonstrate the versatility of the Java Stream API. They test your knowledge of filtering, mapping, reducing, grouping, sorting, and parallel processing. Let me know if you'd like to explore a specific concept further!