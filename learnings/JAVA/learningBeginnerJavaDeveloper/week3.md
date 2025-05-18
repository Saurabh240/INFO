## ✅ **Day 1: Introduction to Exceptions**

### 🎯 Topics

* What are exceptions?
* Difference between **errors** and **exceptions**
* Types of exceptions: **Checked** and **Unchecked**

### 🧾 Sample Code – Runtime Exception (Unchecked)

```java
public class DivideByZero {
    public static void main(String[] args) {
        int a = 10, b = 0;
        int result = a / b; // Throws ArithmeticException
        System.out.println("Result: " + result);
    }
}
```

### 🧾 Sample Code – Compile-Time Exception (Checked)

```java
import java.io.FileReader;

public class FileReadError {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("nonexistent.txt"); // FileNotFoundException
    }
}
```

### 📘 Practice

* Try dividing by 0 and accessing array elements out of bounds.
* Try reading a missing file to trigger a checked exception.

---

## ✅ **Day 2: try-catch-finally**

### 🎯 Topics

* Handling exceptions using `try-catch`
* Using `finally` for cleanup
* Multiple catch blocks

### 🧾 Sample Code

```java
public class TryCatchDemo {
    public static void main(String[] args) {
        try {
            int[] nums = {1, 2, 3};
            System.out.println(nums[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid array index.");
        } finally {
            System.out.println("Cleanup done.");
        }
    }
}
```

### 📘 Practice

* Handle multiple exception types (e.g., division and null).
* Add logging or custom messages in `finally`.

---

## ✅ **Day 3: Custom Exceptions & throw/throws**

### 🎯 Topics

* `throw` keyword
* `throws` declaration
* Custom Exception class creation

### 🧾 Sample Code – Custom Exception

```java
class AgeException extends Exception {
    public AgeException(String msg) {
        super(msg);
    }
}

public class CustomExceptionDemo {
    static void checkAge(int age) throws AgeException {
        if (age < 18) throw new AgeException("Not eligible to vote.");
    }

    public static void main(String[] args) {
        try {
            checkAge(16);
        } catch (AgeException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
```

### 📘 Practice

* Create a `BalanceException` for minimum bank balance.
* Use `throws` in method declarations.

---

## ✅ **Day 4: File I/O – Reading Files**

### 🎯 Topics

* `File`, `FileReader`, `BufferedReader`
* Reading line-by-line from a text file

### 🧾 Sample Code

```java
import java.io.*;

public class ReadFile {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Line: " + line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File read error: " + e.getMessage());
        }
    }
}
```

### 📘 Practice

* Create a file with student names and read it.
* Handle file not found exceptions gracefully.

---

## ✅ **Day 5: File I/O – Writing Files**

### 🎯 Topics

* `FileWriter`, `BufferedWriter`
* Appending to a file

### 🧾 Sample Code

```java
import java.io.*;

public class WriteFile {
    public static void main(String[] args) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
            writer.write("Hello, Java File I/O!");
            writer.newLine();
            writer.close();
            System.out.println("Write successful.");
        } catch (IOException e) {
            System.out.println("File write error: " + e.getMessage());
        }
    }
}
```

### 📘 Practice

* Create a log file and write errors to it.
* Create a program to add multiple lines of student data.

---

## ✅ **Day 6: Combined Project – Student File Manager**

### 🎯 Project Objective

* Accept student details (name, age)
* Write data to `students.txt`
* Read and display data from the file

### 🧾 Project Code

```java
import java.io.*;
import java.util.Scanner;

public class StudentFileManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName = "students.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            System.out.print("Enter student age: ");
            int age = sc.nextInt();

            writer.write(name + "," + age);
            writer.newLine();
            writer.close();
            System.out.println("Student saved successfully.\n");

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            System.out.println("Current Students:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("Name: " + parts[0] + ", Age: " + parts[1]);
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }
    }
}
```

---

## 📘 Key Terminology

| Term                 | Meaning                                    |
| -------------------- | ------------------------------------------ |
| **Exception**        | Problem during execution                   |
| **try-catch**        | Block to handle exceptions                 |
| **finally**          | Always executes, useful for cleanup        |
| **FileReader**       | Reads file content                         |
| **BufferedReader**   | Reads file line-by-line efficiently        |
| **throw/throws**     | Used for declaring and throwing exceptions |
| **Custom Exception** | User-defined class extending `Exception`   |

---