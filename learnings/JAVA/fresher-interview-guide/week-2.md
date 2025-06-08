### Week 2: OOP + DSA Fundamentals - Detailed Teaching Guide

---

#### **Day 1: Classes, Objects, Constructors**

**Objective:** Learn the fundamentals of object-oriented programming.

**Topics:**

* Class structure, object instantiation
* Constructors: default and parameterized

**Teaching Flow:**

1. **Class & Object:**

```java
class Car {
    String model;
    int year;
}

Car myCar = new Car();
myCar.model = "Toyota";
myCar.year = 2020;
```

2. **Constructor:**

```java
class Car {
    String model;
    int year;

    Car(String m, int y) {
        model = m;
        year = y;
    }
}
```

**Practice:**

* Create a `Student` class with name, age, and grade.
* Create multiple objects using parameterized constructor.

---

#### **Day 2: `this` keyword, static methods, method overloading**

**Objective:** Understand reference handling, shared methods, and overloading.

**Topics:**

* `this` keyword usage
* Static methods and variables
* Method overloading

**Teaching Flow:**

1. **this keyword:**

```java
class Student {
    String name;
    Student(String name) {
        this.name = name;
    }
}
```

2. **Static methods:**

```java
class MathUtil {
    static int add(int a, int b) {
        return a + b;
    }
}
```

3. **Overloading:**

```java
class Printer {
    void print(int a) { System.out.println(a); }
    void print(String a) { System.out.println(a); }
}
```

**Practice:**

* Create a calculator with overloaded methods for `int` and `double`.

---

#### **Day 3: Inheritance, Method Overriding**

**Objective:** Understand how classes inherit behavior.

**Topics:**

* `extends` keyword
* Method overriding using `@Override`

**Teaching Flow:**

1. **Inheritance:**

```java
class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}
class Dog extends Animal {
    void sound() {
        System.out.println("Bark");
    }
}
```

2. **Overriding:**

```java
@Override
void sound() { ... }
```

**Practice:**

* Create `Shape -> Circle, Rectangle` classes with overridden `area()` methods.

---

#### **Day 4: Polymorphism, `instanceof`, Abstract Classes**

**Objective:** Demonstrate polymorphic behavior and abstraction.

**Topics:**

* Runtime Polymorphism
* Abstract classes and methods

**Teaching Flow:**

1. **Polymorphism Example:**

```java
Animal obj = new Dog();
obj.sound();  // Calls Dog's method
```

2. **instanceof:**

```java
if (obj instanceof Dog) { ... }
```

3. **Abstract Class:**

```java
abstract class Animal {
    abstract void sound();
}
```

**Practice:**

* Create abstract class `Vehicle` with abstract method `start()`
* Extend with `Car`, `Bike`

---

#### **Day 5: Interfaces vs Abstract Classes**

**Objective:** Differentiate between interfaces and abstract classes.

**Topics:**

* Interface declaration and implementation
* Multiple inheritance via interfaces

**Teaching Flow:**

1. **Interface Example:**

```java
interface Drawable {
    void draw();
}
class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}
```

2. **Difference Table:**
   \| Feature | Abstract Class | Interface |
   \|--------|----------------|-----------|
   \| Fields | Can have | Only constants |
   \| Methods | Abstract + Concrete | Only abstract (Java 7) or default/static (Java 8+) |

**Practice:**

* Create `Payable` interface, implement in `Employee` class

---

#### **Day 6: Encapsulation, Access Modifiers**

**Objective:** Secure data using encapsulation and control visibility.

**Topics:**

* Private fields + public getters/setters
* Access modifiers: `private`, `protected`, `public`, default

**Teaching Flow:**

1. **Encapsulation Example:**

```java
class Person {
    private String name;
    public void setName(String n) { name = n; }
    public String getName() { return name; }
}
```

2. **Modifiers Recap:**
   \| Modifier | Class | Package | Subclass | World |
   \|----------|-------|---------|----------|--------|
   \| private | Y | N | N | N |
   \| public | Y | Y | Y | Y |

**Practice:**

* Create a `BankAccount` class with private balance and methods to deposit/withdraw

---

#### **Day 7: Mini Project - Library System**

**Objective:** Combine all OOP concepts in a single project.

**Project Goal:** Build a console-based Library Management System.

**Features to implement:**

* `Book` class with id, title, author
* `Library` class with ArrayList of books
* Methods: addBook(), viewBooks(), searchBook(String title)
* Use constructors, encapsulation, interface `Searchable`

**Extension:**

* Interface for `Borrowable`, abstract class for `User`

**Practice:**

* Present code with clear comments
* Ask students to enhance system with `User -> Student` class who borrows books
