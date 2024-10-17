**TDD (Test-Driven Development)** is a software development approach where tests are written **before** the actual code. It follows a simple cycle of writing a test, writing the minimum amount of code to pass the test, and then refactoring the code while keeping all tests passing.

The primary objective of TDD is to improve the quality and structure of the code. TDD is based on short development cycles and continuous testing, ensuring that every feature is covered by tests before it is implemented.

### **TDD Process (Red-Green-Refactor Cycle)**:

1. **Red**: Write a failing test for a new feature or functionality (since no code exists yet, the test will fail).
2. **Green**: Write the minimum amount of code necessary to make the test pass.
3. **Refactor**: Clean up the code while ensuring that all tests still pass.

### Steps in the TDD Cycle:

1. **Write the test**: Start by writing a test case for the smallest functionality you want to implement.
2. **Run the test and fail**: Run the test, which should fail because the functionality is not implemented yet.
3. **Write the code**: Write just enough code to make the test pass.
4. **Run the test and pass**: Run the test again, and it should pass.
5. **Refactor**: Refactor the code to improve structure and readability, without changing its behavior.
6. **Repeat**: Continue this process for each small piece of functionality.

---

### **TDD Example: FizzBuzz**

Let’s consider implementing the **FizzBuzz** problem using TDD. The problem is as follows:

- Print "Fizz" if a number is divisible by 3.
- Print "Buzz" if a number is divisible by 5.
- Print "FizzBuzz" if a number is divisible by both 3 and 5.
- Otherwise, print the number itself.

#### 1. **Write the First Test (RED)**

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {

    @Test
    public void testFizzBuzzDivisibleByThree() {
        FizzBuzz fb = new FizzBuzz();
        assertEquals("Fizz", fb.fizzBuzz(3));
    }
}
```

- **Explanation**: We write a test to check if calling `fizzBuzz(3)` returns `"Fizz"`. Since no code is written for `FizzBuzz` yet, this test will fail.

#### 2. **Run the Test and Fail (RED)**

- Running the test will fail because the `FizzBuzz` class and the `fizzBuzz()` method don’t exist yet.

#### 3. **Write the Minimum Code (GREEN)**

```java
public class FizzBuzz {
    public String fizzBuzz(int number) {
        if (number % 3 == 0) {
            return "Fizz";
        }
        return String.valueOf(number);
    }
}
```

- **Explanation**: We write the simplest code to make the test pass, handling only the case where the number is divisible by 3.

#### 4. **Run the Test and Pass (GREEN)**

- The test passes because we now return `"Fizz"` when the number is divisible by 3.

#### 5. **Write More Tests for Additional Functionality (RED)**

We now write another test for the "Buzz" case.

```java
@Test
public void testFizzBuzzDivisibleByFive() {
    FizzBuzz fb = new FizzBuzz();
    assertEquals("Buzz", fb.fizzBuzz(5));
}
```

- **Explanation**: We expect `fizzBuzz(5)` to return `"Buzz"`. This test will fail because we haven’t implemented logic for numbers divisible by 5 yet.

#### 6. **Write the Code to Pass the Test (GREEN)**

```java
public class FizzBuzz {
    public String fizzBuzz(int number) {
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}
```

- **Explanation**: We add the condition for divisibility by 5 and return `"Buzz"` when the condition is met.

#### 7. **Run the Test and Pass (GREEN)**

- Both the previous and the new test pass.

#### 8. **Add the Final Test for "FizzBuzz" Case (RED)**

```java
@Test
public void testFizzBuzzDivisibleByThreeAndFive() {
    FizzBuzz fb = new FizzBuzz();
    assertEquals("FizzBuzz", fb.fizzBuzz(15));
}
```

- **Explanation**: We expect `fizzBuzz(15)` to return `"FizzBuzz"`. This test will fail because we haven’t handled the case where the number is divisible by both 3 and 5.

#### 9. **Write the Code to Pass the Test (GREEN)**

```java
public class FizzBuzz {
    public String fizzBuzz(int number) {
        if (number % 3 == 0 && number % 5 == 0) {
            return "FizzBuzz";
        }
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}
```

- **Explanation**: We add the condition for divisibility by both 3 and 5, returning `"FizzBuzz"` when appropriate.

#### 10. **Refactor (REFACTOR)**

After all tests pass, we can refactor the code to improve clarity and structure if needed.

```java
public class FizzBuzz {
    public String fizzBuzz(int number) {
        if (number % 15 == 0) {  // A better way to check divisibility by 3 and 5
            return "FizzBuzz";
        }
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}
```

### Final Code:

```java
public class FizzBuzz {
    public String fizzBuzz(int number) {
        if (number % 15 == 0) { 
            return "FizzBuzz";
        }
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}
```

---

### **Advantages of TDD**:

1. **Improved Code Quality**: Since tests are written first, the code is written to satisfy the test, ensuring that the code is working as expected from the start.
2. **Better Design**: Writing tests first encourages thinking about the design and forces you to write more modular, testable code.
3. **Less Debugging**: Bugs are caught early in development because of continuous testing.
4. **Comprehensive Test Coverage**: TDD ensures that tests are written for each feature, resulting in better test coverage.
5. **Confidence in Refactoring**: You can safely refactor code knowing that tests will catch any regressions.

### **Disadvantages of TDD**:

1. **Initial Slowdown**: Writing tests first can slow down initial development, but it saves time in debugging and maintenance.
2. **Requires Discipline**: It requires developers to stick to the TDD process, which can be challenging in fast-paced environments.
3. **Over-testing**: There’s a risk of writing too many small or irrelevant tests, leading to increased maintenance overhead.

### **Conclusion**:

TDD helps ensure code correctness by requiring tests to be written before code. It forces developers to think about design, edge cases, and functionality before writing the actual implementation. By adhering to the **Red-Green-Refactor** cycle, TDD promotes a disciplined, test-first approach that leads to better software design and reduced bugs in production.