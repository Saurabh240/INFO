### Week 1: Java Basics & IDE Setup - Detailed Teaching Guide

---

#### **Day 1: Java Installation, IDE Setup, Hello World, Variables, Data Types**

**Objective:** Understand how to install Java, set up IDE, and write the first Java program.

**Topics:**

* JDK installation
* Setting up IntelliJ IDEA / Eclipse
* Structure of a Java program
* Variables and data types

**Teaching Flow:**

1. **JDK Setup:**

   * Download JDK (latest LTS, e.g., Java 17) from Oracle or OpenJDK.
   * Set JAVA\_HOME and configure path.
2. **IDE Setup:**

   * Install IntelliJ IDEA or Eclipse.
   * Create a new project.
3. **Hello World Program:**

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

4. **Variables and Data Types:**

   * int, float, double, char, boolean, String
   * Type casting

**Practice:**

* Print name, age, and height.
* Convert `int` to `double` and vice versa.

---

#### **Day 2: Operators, Conditional Statements (`if`, `switch`)**

**Objective:** Learn how to use operators and decision-making structures.

**Topics:**

* Arithmetic, Relational, Logical, Unary, Assignment operators
* `if`, `else`, `else-if`, `switch`

**Teaching Flow:**

1. **Operators:**

```java
int a = 10, b = 20;
System.out.println(a + b);  // Arithmetic
System.out.println(a > b);  // Relational
```

2. **`if` statements:**

```java
if (a > b) {
    System.out.println("a is greater");
} else {
    System.out.println("b is greater");
}
```

3. **`switch` statement:**

```java
int day = 2;
switch (day) {
    case 1: System.out.println("Sunday"); break;
    case 2: System.out.println("Monday"); break;
    default: System.out.println("Invalid");
}
```

**Practice:**

* Check if a number is even or odd.
* Print day of week using switch.

---

#### **Day 3: Loops (`for`, `while`, `do-while`)**

**Objective:** Learn different loop structures for iteration.

**Topics:**

* `for`, `while`, `do-while`

**Teaching Flow:**

1. **For loop:**

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

2. **While loop:**

```java
int i = 1;
while (i <= 5) {
    System.out.println(i);
    i++;
}
```

3. **Do-while loop:**

```java
int j = 1;
do {
    System.out.println(j);
    j++;
} while (j <= 5);
```

**Practice:**

* Print 1 to 10 using all loops.
* Sum of first N numbers.

---

#### **Day 4: Arrays (1D and 2D), Basic Array Problems**

**Objective:** Understand how to use and manipulate arrays.

**Topics:**

* One-dimensional and two-dimensional arrays

**Teaching Flow:**

1. **1D Arrays:**

```java
int[] arr = {10, 20, 30};
System.out.println(arr[0]);
```

2. **2D Arrays:**

```java
int[][] matrix = {{1,2}, {3,4}};
System.out.println(matrix[0][1]);
```

**Practice:**

* Find max/min in an array.
* Reverse an array.
* Print 2D matrix.

---

#### **Day 5: Strings & String Methods**

**Objective:** Learn string operations and manipulations.

**Topics:**

* String declaration, methods: `length()`, `charAt()`, `indexOf()`, `substring()`

**Teaching Flow:**

1. **Basics:**

```java
String name = "Java";
System.out.println(name.length());
```

2. **Important Methods:**

```java
String str = "Hello World";
System.out.println(str.substring(0, 5));
System.out.println(str.indexOf("W"));
```

**Practice:**

* Reverse a string.
* Check for palindrome.

---

#### **Day 6: Introduction to Java Methods, Recursion Basics**

**Objective:** Understand method creation, calling, parameters, return types, recursion.

**Topics:**

* Method declaration and invocation
* Return types and parameters
* Recursive functions

**Teaching Flow:**

1. **Methods:**

```java
public static int add(int a, int b) {
    return a + b;
}
```

2. **Recursion:**

```java
public static int factorial(int n) {
    if (n == 0) return 1;
    return n * factorial(n - 1);
}
```

**Practice:**

* Factorial using recursion.
* Fibonacci using recursion.

---

#### **Day 7: Practice Day**

**Objective:** Revise and apply week's concepts.

**Practice Problems:**

1. Print prime numbers between 1–50
2. Reverse a string and a number
3. Find largest element in an array
4. Sum of all even numbers between 1–100
5. Check if a number is palindrome