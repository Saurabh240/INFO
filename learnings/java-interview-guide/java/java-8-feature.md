### Q-1: Features of Java 8

- Java 8 introduced features like lambda expressions, functional interfaces, default methods, predicates, functions, and the Stream API, which enhance code conciseness and functional programming capabilities.

### Q-2: What is a Lambda

- Lambda expressions are anonymous functions that allow for more concise code by eliminating the need for boilerplate code. They consist of parameters, an arrow symbol (`->`), and a body. Lambdas simplify code that would otherwise require verbose anonymous inner classes.

### Q-3: What are Functional Interfaces

- A functional interface is an interface with exactly one abstract method. Examples include `Runnable` and `Comparator`. Functional interfaces can have multiple default methods and can be used with lambda expressions, which provide a shorthand for their single abstract method.

### Q-4: What is the Use of Lambda

- Lambda expressions simplify code by replacing anonymous inner classes. For instance, using a lambda for `Runnable` reduces code complexity by avoiding a separate class definition for simple thread tasks.

### Q-5: What is a Predicate

- A `Predicate` is a functional interface that takes a single argument and returns a boolean. It is used for filtering and conditional logic. For example, a `Predicate<Integer>` can check if a number is greater than 20, returning true or false accordingly.

### Q-6: What are Predicate Joins

- Predicate joins allow combining multiple predicates using logical operations. For example, `Predicate<Integer>` can be combined with `and`, `or`, and `negate` methods to form complex conditions like checking if a number is both greater than 10 and even.

### Q-7: What is a Function

- A `Function` is a functional interface that accepts a parameter and returns a result. It differs from `Predicate` by not being restricted to boolean returns. The `apply` method in `Function` performs transformations on input data.

### Q-8: What are Default Methods on Interfaces

- Default methods in interfaces provide a way to add new methods with default implementations without breaking existing classes. This feature allows interface evolution while maintaining backward compatibility.

### Q-9: Can a Class Implement Two Interfaces with the Same Default Method

- Yes, a class can implement two interfaces with the same default method signature. However, the class must provide its own implementation of the default method to resolve conflicts and avoid compilation errors.

### Q-10: How to Use Stream Filter

- To filter even numbers from a list using streams, invoke the `stream()` method on the list, use `filter()` with a predicate for even numbers, and then collect the results with `collect(Collectors.toList())`.

### Q-11: Other Methods on Stream

- Besides `filter()`, streams support methods like `count()` for counting filtered elements, `sorted()` for sorting with a comparator, `max()` and `min()` for finding the maximum and minimum elements, respectively.

### Q-12: Map vs Filter

- The `filter()` method retains elements that match a predicate, while `map()` transforms elements into new values based on provided logic. `filter()` focuses on selection, whereas `map()` focuses on transformation.
