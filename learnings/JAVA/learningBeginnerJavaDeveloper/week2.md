# 🗓 Week 2: OOP + Collections (Day-by-Day Plan)

---

## ✅ **Day 1: Classes, Objects, Constructors, and `this` Keyword**

### 🎯 Topics

* Class: Blueprint for objects
* Object: Instance of a class
* Constructor: Initializes an object
* `this`: Refers to current object

### 🧾 Sample Code

```java
public class Student {
    String name;
    int age;

    // Constructor
    Student(String name, int age) {
        this.name = name; // using 'this' to differentiate between class field and parameter
        this.age = age;
    }

    void displayInfo() {
        System.out.println("Name: " + this.name + ", Age: " + this.age);
    }

    public static void main(String[] args) {
        Student s1 = new Student("Alice", 20);
        s1.displayInfo();
    }
}
```

### 🔍 Practice

* Create a `Car` class with model, year, and brand.
* Add a constructor and a method to print details.

---

## ✅ **Day 2: Inheritance, Method Overriding & Polymorphism**

### 🎯 Topics

* Inheritance: `extends` keyword
* Method Overriding
* Polymorphism: compile-time (method overloading) & runtime (overriding)

### 🧾 Sample Code

```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    void sound() {
        System.out.println("Dog barks");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        Animal a = new Dog();  // Polymorphism
        a.sound();             // Dog barks
    }
}
```

### 🔍 Practice

* Create a `Person` superclass and `Teacher`, `Student` subclasses.
* Override a method `getRole()` in each.

---

## ✅ **Day 3: Encapsulation and Abstraction**

### 🎯 Topics

* Encapsulation: Wrapping data using private fields + getters/setters
* Abstraction: Hiding internal logic using abstract classes

### 🧾 Sample Code – Encapsulation

```java
public class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

### 🧾 Sample Code – Abstraction

```java
abstract class Shape {
    abstract void draw();
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing a Circle");
    }
}
```

### 🔍 Practice

* Create an abstract class `Vehicle` with abstract method `start()`.
* Create concrete classes `Car`, `Bike`.

---

## ✅ **Day 4: Interfaces & Abstract Classes**

### 🎯 Topics

* Interface: Contract for methods (`implements`)
* Differences from abstract classes

### 🧾 Sample Code

```java
interface Payment {
    void pay(double amount);
}

class CreditCard implements Payment {
    public void pay(double amount) {
        System.out.println("Paid using credit card: ₹" + amount);
    }
}
```

### 🔍 Practice

* Create an interface `Playable` with method `play()`.
* Implement it in classes `Music`, `Video`.

---

## ✅ **Day 5: Java Collections – List, Set, Map**

### 🎯 Topics

* List (ArrayList): Ordered, duplicates allowed
* Set (HashSet): Unordered, no duplicates
* Map (HashMap): Key-value pairs

### 🧾 Sample Code

```java
import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");

        Set<Integer> numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);

        Map<String, String> capitals = new HashMap<>();
        capitals.put("India", "New Delhi");

        System.out.println(names);
        System.out.println(numbers);
        System.out.println(capitals.get("India"));
    }
}
```

### 🔍 Practice

* Use `List` to store student names.
* Use `Map` to store student roll numbers and names.

---

## ✅ **Day 6: Iterators and Enhanced For Loop**

### 🎯 Topics

* `for-each` loop
* `Iterator` to loop through `List` and `Set`

### 🧾 Sample Code

```java
import java.util.*;

public class IteratorDemo {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("Pen", "Pencil", "Eraser");

        for (String item : items) {
            System.out.println(item);
        }

        Iterator<String> iterator = items.iterator();
        while (iterator.hasNext()) {
            System.out.println("Iterator: " + iterator.next());
        }
    }
}
```

---

## 📘 Key Terminology

| Term          | Meaning                                                 |
| ------------- | ------------------------------------------------------- |
| **Object**    | Instance of a class                                     |
| **Reference** | Variable that holds memory address of object            |
| **Heap**      | Runtime memory for objects                              |
| **Stack**     | Memory for method calls and local variables             |
| **Static**    | Belongs to class, not object (e.g., `static int count`) |

---

## 🔨 Practice Project: Student Management System

### 🎯 Features:

* Add new students
* View student list
* Search by roll number

### 🧾 Sample Structure

```java
class Student {
    private int roll;
    private String name;

    public Student(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    public int getRoll() { return roll; }
    public String getName() { return name; }

    public void printDetails() {
        System.out.println("Roll: " + roll + ", Name: " + name);
    }
}

public class StudentManager {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student(101, "Alice"));
        students.add(new Student(102, "Bob"));

        System.out.println("All Students:");
        for (Student s : students) {
            s.printDetails();
        }

        int searchRoll = 101;
        for (Student s : students) {
            if (s.getRoll() == searchRoll) {
                System.out.println("Found Student: " + s.getName());
            }
        }
    }
}
```
