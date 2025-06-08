### Week 10: SQL + API Practice - Detailed Teaching Guide

---

#### **Day 1: Basic SQL Queries - SELECT, WHERE, JOIN**

**Objective:** Learn foundational SQL commands used in database querying.

**Topics:**

* SELECT
* WHERE clause
* JOINs: INNER JOIN, LEFT JOIN

**Teaching Flow:**

1. **SELECT & WHERE:**

```sql
SELECT * FROM students;
SELECT name FROM students WHERE grade = 'A';
```

2. **JOIN Example:**

```sql
SELECT students.name, departments.name
FROM students
INNER JOIN departments ON students.dept_id = departments.id;
```

**Practice:**

* Retrieve all student names with grade A
* Join students with departments to list their department names

---

#### **Day 2: Aggregates, GROUP BY, HAVING**

**Objective:** Use aggregate functions for analytical queries.

**Topics:**

* COUNT, AVG, SUM, MAX, MIN
* GROUP BY and HAVING

**Teaching Flow:**

1. **Aggregates:**

```sql
SELECT COUNT(*) FROM students;
SELECT AVG(marks) FROM students;
```

2. **GROUP BY + HAVING:**

```sql
SELECT dept_id, AVG(marks)
FROM students
GROUP BY dept_id
HAVING AVG(marks) > 75;
```

**Practice:**

* Count students in each department
* List departments with avg marks above 70

---

#### **Day 3: Subqueries, Views, Indexes**

**Objective:** Learn advanced querying and performance optimization.

**Topics:**

* Subqueries (nested SELECTs)
* Creating and using Views
* Index basics and benefits

**Teaching Flow:**

1. **Subquery Example:**

```sql
SELECT name FROM students
WHERE marks > (SELECT AVG(marks) FROM students);
```

2. **Views:**

```sql
CREATE VIEW top_students AS
SELECT * FROM students WHERE marks > 85;
```

3. **Index Creation:**

```sql
CREATE INDEX idx_name ON students(name);
```

**Practice:**

* Create view of students in grade A
* Query students using subqueries for complex filtering

---

#### **Day 4: Design DB Schema for Mini Project**

**Objective:** Design relational schema for a real-world use case.

**Project Example:** Inventory Management System

**Entities:**

* Products (id, name, price, stock)
* Categories (id, name)
* Orders (id, date, total)
* OrderItems (id, order\_id, product\_id, quantity)

**Teaching Flow:**

* Identify entities and relationships (1:1, 1\:M, M\:N)
* Draw ER diagram (optional)
* Write CREATE TABLE statements

**Practice:**

* Define schema in SQL
* Set foreign key constraints

---

#### **Day 5–7: API + DB Integration Testing + Swagger**

**Objective:** Build, test, and document integrated API endpoints.

**Topics:**

* Integration testing with Spring Boot and DB
* Postman testing for endpoints
* Swagger setup for API documentation

**Teaching Flow:**

1. **Testing with Postman:**

   * Test all CRUD endpoints for correctness
   * Use both success and failure test cases
2. **Integration Testing (Spring Boot):**

```java
@SpringBootTest
@AutoConfigureMockMvc
public class StudentApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/students"))
               .andExpect(status().isOk());
    }
}
```

3. **Swagger Setup:**

```java
// In build.gradle or pom.xml add springdoc-openapi dependency
// http://localhost:8080/swagger-ui/index.html
```

**Practice:**

* Annotate controllers with `@Operation`
* Validate and document API endpoints using Swagger
