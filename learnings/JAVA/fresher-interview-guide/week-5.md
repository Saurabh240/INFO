### Week 5: Java 8 Deep Dive - Detailed Teaching Guide

---

#### **Day 1: Functional Interfaces (`Predicate`, `Consumer`, `Function`)**

**Objective:** Understand functional interfaces and their use with lambdas.

**Topics:**

* `Predicate<T>`: boolean-valued function
* `Consumer<T>`: performs action, returns nothing
* `Function<T, R>`: transforms input to output

**Teaching Flow:**

1. **Predicate:**

```java
Predicate<String> startsWithJ = s -> s.startsWith("J");
System.out.println(startsWithJ.test("Java"));
```

2. **Consumer:**

```java
Consumer<String> print = s -> System.out.println(s);
print.accept("Hello");
```

3. **Function:**

```java
Function<Integer, String> convert = i -> "Number: " + i;
System.out.println(convert.apply(5));
```

**Practice:**

* Check if string length > 5 using Predicate
* Print all elements in a list using Consumer
* Convert names to uppercase using Function

---

#### **Day 2: Stream Operations: `map`, `filter`, `collect`, `reduce`**

**Objective:** Master data processing using Stream API.

**Topics:**

* Intermediate operations: `map()`, `filter()`
* Terminal operations: `collect()`, `reduce()`

**Teaching Flow:**

1. **map & filter:**

```java
List<String> names = Arrays.asList("John", "Jane", "Jack");
names.stream()
     .filter(name -> name.length() > 3)
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

2. **collect():**

```java
List<String> jNames = names.stream()
    .filter(n -> n.startsWith("J"))
    .collect(Collectors.toList());
```

3. **reduce():**

```java
int sum = Arrays.asList(1, 2, 3, 4).stream()
    .reduce(0, Integer::sum);
```

**Practice:**

* Filter even numbers, square them
* Sum all values > 10 using reduce

---

#### **Day 3: Optional Class, Method References**

**Objective:** Avoid `null` safely and simplify lambda expressions.

**Topics:**

* `Optional` class methods
* Method references (`Class::method`)

**Teaching Flow:**

1. **Optional Usage:**

```java
Optional<String> name = Optional.ofNullable("John");
System.out.println(name.orElse("Unknown"));
```

2. **Common Methods:**

* `isPresent()`, `orElse()`, `ifPresent()`

3. **Method Reference:**

```java
List<String> names = Arrays.asList("Alice", "Bob");
names.forEach(System.out::println);
```

**Practice:**

* Create a method that takes Optional parameter
* Use method references with stream

---

#### **Day 4: Practice Problems using Java 8**

**Objective:** Reinforce functional programming with exercises.

**Practice Set:**

1. Filter a list of integers to keep only odd numbers
2. Transform a list of names to uppercase
3. Count names that start with 'A'
4. Sort a list using Comparator and method reference
5. Get summary stats (min, max, avg) from integer list using streams

**Bonus:**

* Use Optional to avoid null checks in a method that returns a username

---

#### **Day 5–7: Mini-Project — Student Management System with Java 8**

**Objective:** Build a real-world project using Java 8 features.

**Project Goal:** Console-based Student Management App

**Features:**

* Student class: id, name, marks, department
* Add, view, and filter students

**Requirements:**

1. Use `Stream` API to:

   * Filter students with marks > 70
   * Group students by department
   * Get student with highest marks
2. Use `Predicate`, `Consumer`, `Function` for student filtering
3. Use `Optional` to safely handle null student data

**Suggested Enhancements:**

* Sort students by marks using method reference
* Add option to display stats (avg marks, count per department)

**Practice:**

* Build menus and switch-case logic for user input
* Write unit tests for filtering logic (if applicable)
