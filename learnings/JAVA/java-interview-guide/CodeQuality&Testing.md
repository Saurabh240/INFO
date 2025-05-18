**1) What is Unit Testing?**

- **What is unit testing?**
  - Unit testing involves testing individual components of a Java or Spring Boot application in isolation. This means testing each class and its public methods independently from the rest of the application. Dependencies are mocked to ensure that only the class under test is executed, verifying its behavior in isolation.

---

**2) What is Mocking?**

- **How have you used mocking in your projects?**
  - Mocking is used to simulate the behavior of real objects. To use mocking:
    - Add a mocking library like Mockito to the classpath.
    - Annotate objects to be mocked with `@Mock`.
    - Annotate the class under test with `@InjectMocks` to inject mocked dependencies.
    - Use `when` to define return values for method calls on mocked objects.
    - Invoke the method under test and use `verify` to ensure expected interactions with mocks.
    - Use JUnit assertions to verify the results.

---

**3) What are the Various Testing Tools You Have Used?**

- **What testing tools have you used?**
  - **JUnit:** For writing and running unit tests.
  - **Mockito:** For mocking dependencies in unit tests.
  - **PowerMock:** For mocking private and static methods.
  - **DBUnit:** For database-related tests.

---

**4) What are the Important JUnit 5 and Mockito Annotations?**

- **What are some important JUnit and Mockito annotations?**
  - **JUnit 5:**
    - `@Test`: Marks a method as a test method.
    - `@BeforeEach`: Runs before each test method.
    - `@BeforeAll`: Runs once before all test methods.
    - `@AfterEach`: Runs after each test method.
    - `@AfterAll`: Runs once after all test methods.
  - **Mockito:**
    - `@Mock`: Creates a mock instance of a class or interface.
    - `@InjectMocks`: Injects mocks into the class under test.

---

**5) Code Review Checklist**

- **What are some code review guidelines you follow?**
  - **Functionality:** Verify the code meets the requirements and acceptance criteria.
  - **Reuse:** Ensure the code uses existing components and libraries where applicable.
  - **OOP Principles:** Check for proper use of encapsulation, inheritance, and interfaces.
  - **Null Checks & Exception Handling:** Ensure null checks and proper exception handling are in place.
  - **Clean Code:** Use meaningful names, keep functions short, limit parameters, and adhere to formatting standards.
  - **Logging:** Ensure appropriate log levels and avoid logging sensitive information.
  - **Security:** Verify the use of immutable objects, authentication, authorization, encryption, and digital signatures.
  - **Performance:** Use efficient collections, ensure thread safety, and avoid memory leaks.
  - **Configuration:** Move hardcoded values to properties files.
  - **Test Coverage:** Ensure meaningful tests with sufficient coverage for functionality and edge cases.

---
