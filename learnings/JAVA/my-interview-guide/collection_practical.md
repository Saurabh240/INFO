---
 
### Q1. Find the second highest salary from an Employee table.
 
**Schema:** `Employee(id, name, salary, department_id)`
 
```sql
-- Approach 1: Using LIMIT + OFFSET (MySQL/PostgreSQL)
SELECT DISTINCT salary
FROM Employee
ORDER BY salary DESC
LIMIT 1 OFFSET 1;
 
-- Approach 2: Using subquery (works on all DBs)
SELECT MAX(salary) AS second_highest
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);
 
-- Approach 3: Using DENSE_RANK (most interview-preferred)
SELECT salary
FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
    FROM Employee
) ranked
WHERE rnk = 2;
```
 
> **Key point:** `DENSE_RANK` handles ties correctly. If two employees share the highest salary, `DENSE_RANK` still finds a true second-highest. `ROW_NUMBER` would not.
 
---
 
### Q2. Find employees who earn more than their manager.
 
**Schema:** `Employee(id, name, salary, manager_id)`
 
```sql
SELECT e.name AS employee, e.salary AS emp_salary,
       m.name AS manager,  m.salary AS mgr_salary
FROM Employee e
JOIN Employee m ON e.manager_id = m.id
WHERE e.salary > m.salary;
```
 
> **Self-join** — the same table is aliased twice: `e` for employees, `m` for their managers. A common follow-up: "What if `manager_id` is NULL?" — those rows are excluded by INNER JOIN; use LEFT JOIN if you want to include employees with no manager.
 
---
 
### Q3. Find duplicate records in a table.
 
**Schema:** `Employee(id, name, email)`
 
```sql
-- Find emails that appear more than once
SELECT email, COUNT(*) AS cnt
FROM Employee
GROUP BY email
HAVING COUNT(*) > 1;
 
-- Get full duplicate rows
SELECT *
FROM Employee
WHERE email IN (
    SELECT email
    FROM Employee
    GROUP BY email
    HAVING COUNT(*) > 1
)
ORDER BY email;
```
 
> **Follow-up — delete duplicates keeping the lowest id:**
```sql
DELETE FROM Employee
WHERE id NOT IN (
    SELECT MIN(id)
    FROM Employee
    GROUP BY email
);
```
 
---
 
### Q4. Find the department with the highest average salary.
 
**Schema:** `Employee(id, name, salary, dept_id)`, `Department(id, dept_name)`
 
```sql
SELECT d.dept_name, AVG(e.salary) AS avg_salary
FROM Employee e
JOIN Department d ON e.dept_id = d.id
GROUP BY d.dept_name
ORDER BY avg_salary DESC
LIMIT 1;
 
-- More robust: handles ties
SELECT dept_name, avg_salary
FROM (
    SELECT d.dept_name,
           AVG(e.salary) AS avg_salary,
           RANK() OVER (ORDER BY AVG(e.salary) DESC) AS rnk
    FROM Employee e
    JOIN Department d ON e.dept_id = d.id
    GROUP BY d.dept_name
) t
WHERE rnk = 1;
```
 
---
 
### Q5. What is the difference between `RANK()`, `DENSE_RANK()`, and `ROW_NUMBER()`?
 
**Schema:** `Employee(name, salary)`
 
```sql
SELECT name, salary,
       ROW_NUMBER()  OVER (ORDER BY salary DESC) AS row_num,
       RANK()        OVER (ORDER BY salary DESC) AS rnk,
       DENSE_RANK()  OVER (ORDER BY salary DESC) AS dense_rnk
FROM Employee;
```
 
**Sample output for salaries: 90000, 90000, 80000, 70000**
 
| name  | salary | ROW_NUMBER | RANK | DENSE_RANK |
|-------|--------|------------|------|------------|
| Alice | 90000  | 1          | 1    | 1          |
| Bob   | 90000  | 2          | 1    | 1          |
| Carol | 80000  | 3          | 3    | 2          |
| Dave  | 70000  | 4          | 4    | 3          |
 
> **Key rule:** `RANK` skips numbers after ties (1,1,3,4). `DENSE_RANK` never skips (1,1,2,3). `ROW_NUMBER` is always unique regardless of ties.
 
---
 
### Q6. Write a query to get cumulative (running) total of sales per month.
 
**Schema:** `Sales(sale_date, amount)`
 
```sql
SELECT
    DATE_FORMAT(sale_date, '%Y-%m') AS month,
    SUM(amount)                     AS monthly_sales,
    SUM(SUM(amount)) OVER (
        ORDER BY DATE_FORMAT(sale_date, '%Y-%m')
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    )                               AS cumulative_sales
FROM Sales
GROUP BY DATE_FORMAT(sale_date, '%Y-%m')
ORDER BY month;
```
 
> **Note:** `SUM(SUM(amount))` — the inner SUM aggregates rows into monthly totals first; the outer window SUM accumulates across months. This pattern (window function on aggregate) is a common advanced SQL question.
 
---
 
### Q7. Find employees who joined in the last 30 days and have a salary above the company average.
 
**Schema:** `Employee(id, name, salary, join_date)`
 
```sql
SELECT id, name, salary, join_date
FROM Employee
WHERE join_date >= CURRENT_DATE - INTERVAL 30 DAY
  AND salary > (SELECT AVG(salary) FROM Employee);
```
 
> **Follow-up:** "How would you optimise this?" — Index on `join_date`, and since the subquery is a scalar correlated query (runs once), it's already efficient. For very large tables, precompute average into a CTE.
 
```sql
WITH avg_salary AS (
    SELECT AVG(salary) AS avg_sal FROM Employee
)
SELECT e.id, e.name, e.salary, e.join_date
FROM Employee e, avg_salary a
WHERE e.join_date >= CURRENT_DATE - INTERVAL 30 DAY
  AND e.salary > a.avg_sal;
```
 
---
 
### Q8. What is the difference between `WHERE` and `HAVING`?
 
| Aspect | WHERE | HAVING |
|--------|-------|--------|
| Filters | Individual rows | Grouped rows (post-aggregation) |
| Used with | Any SELECT | Always with GROUP BY |
| Can use aggregates | ❌ No | ✅ Yes |
| Execution order | Before GROUP BY | After GROUP BY |
 
```sql
-- WHERE: filter before grouping
SELECT dept_id, AVG(salary)
FROM Employee
WHERE salary > 30000          -- filters individual rows first
GROUP BY dept_id;
 
-- HAVING: filter after grouping
SELECT dept_id, AVG(salary) AS avg_sal
FROM Employee
GROUP BY dept_id
HAVING AVG(salary) > 60000;  -- filters groups (departments)
```
 
> **Can they be combined?** Yes — `WHERE` reduces rows before grouping (cheaper), `HAVING` filters after. Use both together for best performance.
 
---
 
### Q9. Find the top 3 earners in each department.
 
**Schema:** `Employee(id, name, salary, department)`
 
```sql
SELECT name, department, salary
FROM (
    SELECT
        name,
        department,
        salary,
        DENSE_RANK() OVER (
            PARTITION BY department
            ORDER BY salary DESC
        ) AS dept_rank
    FROM Employee
) ranked
WHERE dept_rank <= 3
ORDER BY department, dept_rank;
```
 
> **`PARTITION BY department`** resets the rank for each department independently. This is the standard approach for "top-N per group" problems — a very common Infosys SQL question pattern.
 
---
 
### Q10. What is the difference between `INNER JOIN`, `LEFT JOIN`, `RIGHT JOIN`, and `FULL OUTER JOIN`?
 
**Schema:** `Orders(order_id, customer_id)`, `Customers(customer_id, name)`
 
```sql
-- INNER JOIN: only matching rows in both tables
SELECT c.name, o.order_id
FROM Customers c INNER JOIN Orders o ON c.customer_id = o.customer_id;
 
-- LEFT JOIN: all customers, NULL if no order
SELECT c.name, o.order_id
FROM Customers c LEFT JOIN Orders o ON c.customer_id = o.customer_id;
 
-- RIGHT JOIN: all orders, NULL if no customer match
SELECT c.name, o.order_id
FROM Customers c RIGHT JOIN Orders o ON c.customer_id = o.customer_id;
 
-- FULL OUTER JOIN: all rows from both (MySQL uses UNION)
SELECT c.name, o.order_id
FROM Customers c LEFT  JOIN Orders o ON c.customer_id = o.customer_id
UNION
SELECT c.name, o.order_id
FROM Customers c RIGHT JOIN Orders o ON c.customer_id = o.customer_id;
```
 
| JOIN Type | Returns |
|-----------|---------|
| INNER     | Rows with matches in BOTH tables |
| LEFT      | All left table rows + matched right (NULL if no match) |
| RIGHT     | All right table rows + matched left (NULL if no match) |
| FULL OUTER | All rows from both, NULLs where no match |
 
---
