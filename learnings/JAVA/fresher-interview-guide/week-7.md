### Week 7: JDBC + Simple DB Integration - Detailed Teaching Guide

---

#### **Day 1: Introduction to JDBC, Installing PostgreSQL/MySQL**

**Objective:** Understand JDBC and set up a database for Java connectivity.

**Topics:**

* JDBC overview
* PostgreSQL/MySQL installation
* Database client tools (DBeaver, pgAdmin, MySQL Workbench)

**Teaching Flow:**

1. **JDBC Architecture:**

   * JDBC Drivers
   * Connection Interface
2. **Installation:**

   * Download and install PostgreSQL/MySQL
   * Create database and user using client tool

**Practice:**

* Create a database named `school_db`
* Create a table `students` with columns `id`, `name`, `grade`

---

#### **Day 2: Java DB Connection, `Statement`, `PreparedStatement`**

**Objective:** Connect Java to database and execute queries.

**Topics:**

* Loading JDBC driver
* `Connection`, `Statement`, `PreparedStatement`

**Teaching Flow:**

1. **Add JDBC dependency (PostgreSQL):**

```xml
<!-- Maven -->
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.2.24</version>
</dependency>
```

2. **Connection Example:**

```java
Connection conn = DriverManager.getConnection(
  "jdbc:postgresql://localhost:5432/school_db", "user", "password");
```

3. **Statement vs PreparedStatement:**

```java
PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
stmt.setInt(1, 1);
ResultSet rs = stmt.executeQuery();
```

**Practice:**

* Fetch and print all records from the students table

---

#### **Day 3: Insert, Read, Update, Delete Operations from Java**

**Objective:** Perform CRUD operations from a Java program.

**Topics:**

* SQL query execution using Java
* ResultSet iteration

**Teaching Flow:**

1. **Insert:**

```java
PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO students VALUES (?, ?, ?)");
insertStmt.setInt(1, 1);
insertStmt.setString(2, "John");
insertStmt.setString(3, "A");
insertStmt.executeUpdate();
```

2. **Read, Update, Delete:**

* Read using SELECT
* Update using UPDATE with where clause
* Delete using DELETE

**Practice:**

* Build functions: addStudent(), updateStudent(), deleteStudent(), fetchAll()

---

#### **Day 4: Practice - CLI App to Manage Student Records**

**Objective:** Build a Java-based console app using JDBC.

**Features to Implement:**

* Menu-based app to:

  * Add a student
  * View all students
  * Update student info
  * Delete a student

**Technical Scope:**

* Use `Scanner` for input
* Use `PreparedStatement` for DB operations
* Exception handling and input validation

**Enhancements:**

* Sort students by name
* Search by grade

---

#### **Day 5–7: LeetCode Practice - Easy Strings & Arrays**

**Objective:** Practice real coding questions related to strings and arrays.

**Daily Goal:** 3–5 problems/day

**Recommended Topics:**

* Arrays:

  * Remove duplicates from sorted array
  * Merge sorted arrays
  * Max subarray sum (Kadane's Algorithm)
* Strings:

  * Valid anagram
  * Reverse words in a string
  * Check palindrome

**Practice Platforms:**

* LeetCode (Easy filter)
* HackerRank
* CodeStudio (Coding Ninjas)

**Goal:**

* Improve speed and accuracy with foundational problems
* Prepare for common interview coding questions
