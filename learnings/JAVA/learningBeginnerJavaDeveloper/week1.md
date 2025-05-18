# 🗓 Week 1: Java Basics (Day-by-Day Plan)

---

## ✅ **Day 1: Environment Setup & Terminology**

### 📦 **Step 1: Install JDK**

* Go to: [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)
* Download and install JDK (Java SE 17 recommended).
* Set environment variable:

  * `JAVA_HOME` = path to JDK
  * Add `JAVA_HOME/bin` to your `PATH`

### 🛠 **Step 2: Install IDE**

Choose one:

* **IntelliJ IDEA** (Community Edition): [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* **VS Code** with Java extensions

### 🧠 **Terminology**

* **JDK**: Java Development Kit – full package with compiler and tools.
* **JRE**: Java Runtime Environment – runs Java apps.
* **JVM**: Java Virtual Machine – interprets bytecode to native OS.
* **Compilation**: Converts `.java` → `.class`
* **Interpretation**: JVM runs `.class` file line-by-line

---

## ✅ **Day 2: Variables, Data Types, and Operators**

### 🎯 **Topics Covered**

* Primitive Data Types: `int`, `float`, `double`, `boolean`, `char`
* Operators: `+`, `-`, `*`, `/`, `%`, `==`, `!=`, `&&`, `||`

### 🔤 **Sample Code**

```java
public class DataTypesDemo {
    public static void main(String[] args) {
        int age = 25;
        float price = 99.99f;
        char grade = 'A';
        boolean isPassed = true;

        System.out.println("Age: " + age);
        System.out.println("Price: " + price);
        System.out.println("Grade: " + grade);
        System.out.println("Passed? " + isPassed);
    }
}
```
### Operators and Expressions in Java:

### Arithmetic Operators:
Arithmetic operators perform basic mathematical operations on operands. They include:
- `+`: Addition
- `-`: Subtraction
- `*`: Multiplication
- `/`: Division
- `%`: Modulus (remainder)

int a = 10, b = 3;
int sum = a + b;     // 13
int difference = a - b; // 7
int product = a * b; // 30
int quotient = a / b; // 3
int remainder = a % b; // 1

### Assignment Operators:
Assignment operators are used to assign values to variables and modify their values. They include:
- `=`: Assign
- `+=`: Add and assign
- `-=`: Subtract and assign
- `*=`: Multiply and assign
- `/=`: Divide and assign

int x = 5;
x += 3; // x is now 8 (x = x + 3)
x -= 2; // x is now 6 (x = x - 2)
x *= 4; // x is now 24 (x = x * 4)
x /= 3; // x is now 8 (x = x / 3)

### Comparison Operators:
Comparison operators are used to compare values and return boolean results. They include:
- `==`: Equal to
- `!=`: Not equal to
- `<`: Less than
- `>`: Greater than
- `<=`: Less than or equal to
- `>=`: Greater than or equal to

int p = 10, q = 5;
boolean isEqual = p == q;    // false
boolean isNotEqual = p != q; // true
boolean isLess = p < q;      // false
boolean isGreater = p > q;   // true
boolean isLessOrEqual = p <= q; // false
boolean isGreaterOrEqual = p >= q; // true

### Logical Operators:
Logical operators perform logical operations on boolean expressions. They include:
- `&&`: Logical AND
- `||`: Logical OR
- `!`: Logical NOT

boolean condition1 = true, condition2 = false;
boolean resultAND = condition1 && condition2; // false
boolean resultOR = condition1 || condition2;   // true
boolean resultNOT = !condition1;               // false

### Increment and Decrement Operators:
Increment and decrement operators are used to increase or decrease the value of a variable by 1.
- `++`: Increment by 1
- `--`: Decrement by 1

int count = 5;
count++; // count is now 6
count--; // count is now 5 again


### 🔍 Practice:

* Declare variables for a person (name, age, salary, isEmployed).
* Print their values.

---

## ✅ **Day 3: Conditionals (if-else, switch)**

### 🎯 **Topics Covered**

* `if`, `else if`, `else`
* `switch` statement

### 🧾 **Sample Code**

```java
public class ConditionalDemo {
    public static void main(String[] args) {
        int marks = 85;

        if (marks >= 90) {
            System.out.println("Grade: A+");
        } else if (marks >= 75) {
            System.out.println("Grade: A");
        } else {
            System.out.println("Grade: B or below");
        }

        char grade = 'A';
        switch (grade) {
            case 'A': System.out.println("Excellent"); break;
            case 'B': System.out.println("Good"); break;
            default: System.out.println("Try harder");
        }
    }
}
```

### 🔍 Practice:

* Create a program that checks if a number is even or odd.
* Write a switch program for days of the week.

---

## ✅ **Day 4: Loops (for, while, do-while)**

### 🎯 **Topics Covered**

* `for`, `while`, `do-while` loops
* Loop control: `break`, `continue`

### 🔁 **Sample Code**

```java
public class LoopDemo {
    public static void main(String[] args) {
        // for loop
        for (int i = 1; i <= 5; i++) {
            System.out.println("Number: " + i);
        }

        // while loop
        int j = 1;
        while (j <= 5) {
            System.out.println("While: " + j);
            j++;
        }

        // do-while loop
        int k = 1;
        do {
            System.out.println("Do-while: " + k);
            k++;
        } while (k <= 5);
    }
}
```

### 🔍 Practice:

* Print numbers 1–10 using each loop.
* Print only even numbers between 1–20.

---

## ✅ **Day 5: Arrays and Methods**

### 🎯 **Topics Covered**

* Arrays (`int[]`, `String[]`)
* Methods: `void`, `return type`, `parameters`

### 🧮 **Sample Code – Arrays**

```java
public class ArrayDemo {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40};

        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Element: " + numbers[i]);
        }
    }
}
```

### 📞 **Sample Code – Methods**

```java
public class MethodDemo {
    public static void greetUser(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        greetUser("John");
        int result = add(5, 7);
        System.out.println("Sum: " + result);
    }
}
```

### 🔍 Practice:

* Create a method to find the largest of 3 numbers.
* Create a method to reverse an array.

---

## 🔨 Practice Project 1: Calculator

### ✅ Features

* Take input: 2 numbers and operator (+, -, \*, /)
* Return result

```java
import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double num1 = sc.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = sc.nextDouble();

        System.out.print("Enter operator (+, -, *, /): ");
        char op = sc.next().charAt(0);

        double result = 0;
        switch (op) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/': result = num2 != 0 ? num1 / num2 : 0; break;
            default: System.out.println("Invalid operator"); return;
        }

        System.out.println("Result: " + result);
    }
}
```

---

## 🔨 Practice Project 2: Temperature Converter

```java
import java.util.Scanner;

public class TempConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Celsius temperature: ");
        double celsius = sc.nextDouble();

        double fahrenheit = (celsius * 9/5) + 32;
        System.out.println("Fahrenheit: " + fahrenheit);
    }
}
```