### Week 9: Spring Boot Introduction - Detailed Teaching Guide

---

#### **Day 1: What is Spring Boot, Setup Project Using Spring Initializr**

**Objective:** Understand the basics of Spring Boot and set up a new project.

**Topics:**

* Spring vs Spring Boot
* Auto-configuration, starters
* Spring Initializr setup

**Teaching Flow:**

1. **Concept:** Spring Boot simplifies app setup and development.
2. **Spring Initializr:**

   * Website: [https://start.spring.io/](https://start.spring.io/)
   * Dependencies: Spring Web, Spring Boot DevTools, Spring Data JPA
3. **IDE Setup:**

   * Open generated project in IntelliJ or Eclipse
   * Basic directory structure explanation

**Practice:**

* Create a project named `StudentAPI`
* Build and run with default controller

---

#### **Day 2: Create a REST Controller, Understand Annotations**

**Objective:** Build REST endpoints using Spring MVC annotations.

**Topics:**

* `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`
* `@RequestBody`, `@PathVariable`

**Teaching Flow:**

1. **Controller Example:**

```java
@RestController
@RequestMapping("/students")
public class StudentController {
    @GetMapping("/{id}")
    public String getStudent(@PathVariable int id) {
        return "Student ID: " + id;
    }

    @PostMapping
    public String addStudent(@RequestBody String student) {
        return "Added: " + student;
    }
}
```

**Practice:**

* Create simple endpoints: GET all, GET by ID, POST

---

#### **Day 3: Services, Repositories**

**Objective:** Understand layered architecture and service abstraction.

**Topics:**

* `@Service`, `@Repository`
* Dependency injection with `@Autowired`

**Teaching Flow:**

1. **Create Service:**

```java
@Service
public class StudentService {
    public String getStudentById(int id) {
        return "Student#" + id;
    }
}
```

2. **Use in Controller:**

```java
@Autowired
private StudentService studentService;
```

**Practice:**

* Add service layer to student API
* Create a dummy repository (interface for now)

---

#### **Day 4: Connect Spring Boot to MySQL/Postgres (JPA)**

**Objective:** Connect application to a database using Spring Data JPA.

**Topics:**

* Spring Boot DataSource configuration
* Entity creation with `@Entity`
* JPA Repositories

**Teaching Flow:**

1. **application.properties:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

2. **Entity + Repository:**

```java
@Entity
public class Student {
    @Id
    @GeneratedValue
    private int id;
    private String name;
}

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {}
```

**Practice:**

* Add DB support and verify data insertion via POST endpoint

---

#### **Day 5: CRUD REST API Project**

**Objective:** Implement a full CRUD app with Spring Boot + DB

**Project:** Student Management System

**Endpoints:**

* GET /students
* GET /students/{id}
* POST /students
* PUT /students/{id}
* DELETE /students/{id}

**Practice:**

* Test all endpoints using Postman
* Verify DB operations

---

#### **Day 6–7: Extend Project with Validations, Exception Handling**

**Objective:** Add input validations and global exception management.

**Topics:**

* `@Valid`, `@NotBlank`, `@Size`
* `@ControllerAdvice`, `@ExceptionHandler`

**Teaching Flow:**

1. **Validations in Entity:**

```java
@NotBlank
@Size(min = 2, message = "Name too short")
private String name;
```

2. **Exception Handling:**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

**Practice:**

* Add validation to Student fields
* Handle `EntityNotFoundException`
* Return user-friendly error responses
