# 🗓 Week 4: Oracle SQL Basics (Day-by-Day Plan)

---

## ✅ **Day 1: Installation & SQL Introduction**

### 🧰 Setup Options

* **Option 1: Oracle Live SQL** (no install)
  → [https://livesql.oracle.com](https://livesql.oracle.com)
* **Option 2: Oracle XE (local installation)**
  → [https://www.oracle.com/database/technologies/appdev/xe.html](https://www.oracle.com/database/technologies/appdev/xe.html)

### 🧠 Terminology

| Term           | Description                                         |
| -------------- | --------------------------------------------------- |
| **SQL**        | Structured Query Language                           |
| **DDL**        | Data Definition Language (CREATE, ALTER, DROP)      |
| **DML**        | Data Manipulation Language (INSERT, UPDATE, DELETE) |
| **DQL**        | Data Query Language (SELECT)                        |
| **Constraint** | Rules enforced on table data (e.g., PRIMARY KEY)    |

### 🧾 First Query

```sql
SELECT 'Welcome to Oracle SQL!' AS Message FROM dual;
```

---

## ✅ **Day 2: Creating Tables with DDL**

### 🎯 Topics

* `CREATE TABLE`
* Data types: `VARCHAR2`, `NUMBER`, `DATE`
* Constraints: `PRIMARY KEY`, `NOT NULL`, `UNIQUE`, `DEFAULT`

### 🧾 Sample Table: `students`

```sql
CREATE TABLE students (
    student_id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    age NUMBER DEFAULT 18,
    join_date DATE DEFAULT SYSDATE
);
```

### 📘 Practice

* Create a `courses` table with `course_id`, `name`, `duration`, `fee`.
* Add constraints like `NOT NULL`, `PRIMARY KEY`.

---

## ✅ **Day 3: DML (INSERT, UPDATE, DELETE)**

### 🎯 Topics

* Insert single/multiple rows
* Update data
* Delete rows

### 🧾 Sample Queries

```sql
-- INSERT
INSERT INTO students (student_id, name, age)
VALUES (1, 'Alice', 20);

-- UPDATE
UPDATE students SET age = 21 WHERE student_id = 1;

-- DELETE
DELETE FROM students WHERE student_id = 1;
```

### 📘 Practice

* Insert at least 5 rows into your `students` and `courses` tables.
* Update and delete one record.

---

## ✅ **Day 4: SELECT Queries & Filtering**

### 🎯 Topics

* `SELECT`, `WHERE`, `AND`, `OR`, `BETWEEN`, `LIKE`, `IN`
* Sorting with `ORDER BY`
* Aliasing using `AS`

### 🧾 Sample Queries

```sql
-- Basic SELECT
SELECT * FROM students;

-- WHERE clause
SELECT name FROM students WHERE age > 18;

-- LIKE and IN
SELECT name FROM students WHERE name LIKE 'A%';
SELECT * FROM students WHERE age IN (18, 20);

-- ORDER BY
SELECT * FROM students ORDER BY name ASC;
```

### 📘 Practice

* Find all students aged between 18 and 25.
* List students whose names start with 'S'.

---

## ✅ **Day 5: JOINS & Relationships**

### 🎯 Topics

* Foreign Keys
* INNER JOIN, LEFT JOIN
* Relationships between tables

### 🧾 Sample Tables

```sql
CREATE TABLE enrollments (
    enroll_id NUMBER PRIMARY KEY,
    student_id NUMBER REFERENCES students(student_id),
    course_id NUMBER REFERENCES courses(course_id),
    enroll_date DATE DEFAULT SYSDATE
);
```

### 🧾 Sample JOIN

```sql
SELECT s.name, c.name AS course
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id;
```

### 📘 Practice

* Create and insert into `enrollments`.
* Fetch students and their courses using JOIN.

---

## ✅ **Day 6: Constraints, Alter & Drop**

### 🎯 Topics

* Adding constraints later
* ALTER table structure
* Dropping tables safely

### 🧾 Examples

```sql
-- Add new column
ALTER TABLE students ADD (email VARCHAR2(100));

-- Add constraint
ALTER TABLE students ADD CONSTRAINT uniq_email UNIQUE (email);

-- Drop table
DROP TABLE enrollments;
```

### 📘 Practice

* Add an email column to `students` and enforce uniqueness.
* Drop a sample table after backing up its data.

---

## 🔨 Mini Project: Course Enrollment System

### 🧱 Tables:

* `students(student_id, name, age)`
* `courses(course_id, name, duration)`
* `enrollments(enroll_id, student_id, course_id, enroll_date)`

### 🔨 Tasks:

1. Insert 5 students and 3 courses.
2. Enroll each student into at least one course.
3. Fetch:

   * List of all enrolled students and course names.
   * All students not enrolled in any course.
   * Number of students per course (using `GROUP BY`).

### 🧾 Example Query

```sql
SELECT c.name AS course_name, COUNT(*) AS total_students
FROM enrollments e
JOIN courses c ON c.course_id = e.course_id
GROUP BY c.name;
```

---

## 📘 Summary – By End of Week 4

You should now:

* Use all major SQL commands (DDL, DML, DQL)
* Design related tables and apply keys/constraints
* Write complex queries with JOINs and conditions
* Build a course enrollment system on Oracle