### Week 4: Exception Handling & File I/O - Detailed Teaching Guide

---

#### **Day 1: Try-catch-finally, Checked vs Unchecked Exceptions**

**Objective:** Understand Java exception types and how to handle them.

**Topics:**

* Exception hierarchy
* try-catch-finally structure
* Checked vs Unchecked Exceptions

**Teaching Flow:**

1. **Basic try-catch:**

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero.");
}
```

2. **finally block:**

```java
finally {
    System.out.println("Always executes");
}
```

3. **Checked vs Unchecked:**

* Checked: IOException, SQLException (must be handled)
* Unchecked: NullPointerException, ArithmeticException

**Practice:**

* Handle ArrayIndexOutOfBoundsException
* Use finally with return in try-catch

---

#### **Day 2: Custom Exceptions, `throw` and `throws`**

**Objective:** Create and use user-defined exceptions.

**Topics:**

* throw keyword to manually throw exceptions
* throws clause in method declaration
* Custom exception classes

**Teaching Flow:**

1. **throw:**

```java
if (age < 18) {
    throw new ArithmeticException("Underage");
}
```

2. **throws:**

```java
void readFile() throws IOException {
    // code
}
```

3. **Custom exception:**

```java
class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}
```

**Practice:**

* Create `InvalidAgeException`
* Throw and catch custom exception

---

#### **Day 3: File Reading/Writing using `FileReader`, `BufferedReader`, `FileWriter`**

**Objective:** Learn how to read and write files in Java.

**Topics:**

* Reading: FileReader, BufferedReader
* Writing: FileWriter, BufferedWriter

**Teaching Flow:**

1. **Reading a file:**

```java
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
String line;
while ((line = reader.readLine()) != null) {
    System.out.println(line);
}
reader.close();
```

2. **Writing to file:**

```java
BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
writer.write("Hello Java");
writer.close();
```

**Practice:**

* Read file content and print word count
* Write user input to file

---

#### **Day 4: Serialization & Deserialization**

**Objective:** Store and retrieve object state.

**Topics:**

* Serializable interface
* ObjectOutputStream, ObjectInputStream

**Teaching Flow:**

1. **Make class serializable:**

```java
class Student implements Serializable {
    int id;
    String name;
}
```

2. **Serialization:**

```java
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"));
out.writeObject(student);
out.close();
```

3. **Deserialization:**

```java
ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"));
Student s = (Student) in.readObject();
in.close();
```

**Practice:**

* Serialize/deserialize Employee objects

---

#### **Day 5: Java 8 Intro - Lambda Basics**

**Objective:** Learn functional programming basics with lambda expressions.

**Topics:**

* Syntax and use
* Functional interfaces (`Runnable`, `Comparator`)

**Teaching Flow:**

1. **Basic lambda:**

```java
Runnable r = () -> System.out.println("Running thread");
r.run();
```

2. **With parameters:**

```java
Comparator<String> comp = (a, b) -> a.compareTo(b);
```

**Practice:**

* Write lambda to compare strings by length
* Use lambda to print elements of list

---

#### **Day 6: Stream API Basics (`map`, `filter`)**

**Objective:** Use Java Stream API for processing collections.

**Topics:**

* map(), filter(), forEach()
* Collectors.toList()

**Teaching Flow:**

1. **Example with List:**

```java
List<String> names = Arrays.asList("John", "Jane", "Alex");
names.stream()
     .filter(n -> n.startsWith("J"))
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

**Practice:**

* Filter even numbers and square them
* Count strings longer than 5 characters

---

#### **Day 7: Practice - Console App with Exception and File Handling**

**Objective:** Build an app integrating all week’s concepts.

**Project Goal:** Create a console-based Contact Book app

**Features:**

* Add and view contacts (name, phone number)
* Save contacts to file using serialization
* Load contacts at startup
* Use try-catch-finally and custom exceptions

**Practice:**

* Catch file errors
* Validate input using custom exceptions
* Use streams to filter/search contacts
