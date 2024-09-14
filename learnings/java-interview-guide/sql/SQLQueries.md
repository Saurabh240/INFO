
Q1: What is the difference between `INNER JOIN` and `OUTER JOIN`?
    - `INNER JOIN`: Returns records that have matching values in both tables.
    - `OUTER JOIN`: Returns all records when there is a match in one of the tables, and the unmatched rows will contain NULL.
  
Q2: Write a query to fetch the second-highest salary from the Employee table.
  SELECT MAX(salary)
  FROM Employee
  WHERE salary < (SELECT MAX(salary) FROM Employee);

Q3: Write a SQL query to get the employee with the highest salary in each department.
  SELECT department, MAX(salary)
  FROM Employee
  GROUP BY department;

Q4: Write a query to display the employees who have the same salary.
  SELECT salary, COUNT(*)
  FROM Employee
  GROUP BY salary
  HAVING COUNT(*) > 1;

Q5: Write a query to find all employees hired in the last 30 days.
  SELECT * 
  FROM Employee 
  WHERE hire_date >= NOW() - INTERVAL 30 DAY;

Q6: How do you find all managers (employees with subordinates)?**
  SELECT DISTINCT manager_id
  FROM Employee
  WHERE manager_id IS NOT NULL;

Q7: Write a query to delete duplicate rows from a table.
  DELETE FROM Employee
  WHERE id NOT IN (
    SELECT MIN(id)
    FROM Employee
    GROUP BY name, salary, department
  );

Q8: Write a query to display the department name along with the total number of employees working in that department.
  SELECT department, COUNT(*)
  FROM Employee
  GROUP BY department;

Q9: Write a query to find employees who earn more than the average salary.
  SELECT *
  FROM Employee
  WHERE salary > (SELECT AVG(salary) FROM Employee);

Q10: Write a query to fetch the top 3 highest salaries from the Employee table.
  SELECT DISTINCT salary
  FROM Employee
  ORDER BY salary DESC
  LIMIT 3;

Q11: Write a query to find employees whose names start with the letter "A".
  SELECT *
  FROM Employee
  WHERE name LIKE 'A%';

Q12: How would you improve the performance of a slow-running query?
    - Create indexes on frequently used columns.
    - Use appropriate `JOINs`.
    - Avoid `SELECT *`, specify only the required columns.
    - Optimize `WHERE` clauses with conditions that filter a large number of rows.
    - Use indexing and limit the result set with pagination.

Q13: Write a query to fetch common records between two tables `Employee1` and `Employee2`.
  SELECT * 
  FROM Employee1 
  INTERSECT 
  SELECT * 
  FROM Employee2;

Q14: Write a query to calculate the average salary of each job position in the company.
  SELECT job_position, AVG(salary)
  FROM Employee
  GROUP BY job_position;

Q15: Write a query to display the employee name along with their boss's name.

SELECT e1.Name AS Employee, e2.Name AS Boss
FROM Employee e1
LEFT JOIN Employee e2 ON e1.BossID = e2.EmpID;

Q16: Write a query to find the nth highest salary

SELECT DISTINCT salary
FROM Employee
ORDER BY salary DESC
LIMIT 1 OFFSET n-1;