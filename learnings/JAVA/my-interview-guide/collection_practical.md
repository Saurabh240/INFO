### Q11. Find the list of distinct department names from a list of employees, sorted alphabetically.
 
```java
record Employee(String name, String department, double salary, int age) {}
 
List<Employee> employees = List.of(
    new Employee("Alice", "Engineering", 90000, 30),
    new Employee("Bob",   "HR",          60000, 28),
    new Employee("Carol", "Engineering", 85000, 35),
    new Employee("Dave",  "Finance",     75000, 40),
    new Employee("Eve",   "HR",          62000, 27)
);
 
List<String> departments = employees.stream()
    .map(Employee::department)
    .distinct()
    .sorted()
    .collect(Collectors.toList());
 
// Output: [Engineering, Finance, HR]
```
 
> `map()` extracts the field, `distinct()` removes duplicates (uses `equals`), `sorted()` natural ordering.
 
---
 
### Q12. Find the employee with the highest salary.
 
```java
Optional<Employee> highestPaid = employees.stream()
    .max(Comparator.comparingDouble(Employee::salary));
 
highestPaid.ifPresent(e ->
    System.out.println("Highest paid: " + e.name() + " - " + e.salary())
);
// Output: Highest paid: Alice - 90000.0
```
 
> **Why `Optional`?** The stream could be empty — `max()` correctly returns `Optional.empty()` instead of throwing an exception.
 
**Follow-up — what if you want top 3 earners?**
```java
List<Employee> top3 = employees.stream()
    .sorted(Comparator.comparingDouble(Employee::salary).reversed())
    .limit(3)
    .collect(Collectors.toList());
```
 
---
 
### Q13. Group employees by department and get count per department.
 
```java
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,
        Collectors.counting()
    ));
 
// Output: {Engineering=2, HR=2, Finance=1}
```
 
**Variation — get average salary per department:**
```java
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,
        Collectors.averagingDouble(Employee::salary)
    ));
```
 
**Variation — get list of employee names per department:**
```java
Map<String, List<String>> namesByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,
        Collectors.mapping(Employee::name, Collectors.toList())
    ));
```
 
---
 
### Q14. Filter employees with salary > 70,000 and collect their names into a comma-separated string.
 
```java
String result = employees.stream()
    .filter(e -> e.salary() > 70000)
    .map(Employee::name)
    .sorted()
    .collect(Collectors.joining(", "));
 
// Output: Alice, Carol, Dave
```
 
> `Collectors.joining(delimiter, prefix, suffix)` — can also produce `[Alice, Carol, Dave]` with prefix `[` and suffix `]`.
 
---
 
### Q15. Find the sum and average salary of all employees using streams.
 
```java
// Using IntSummaryStatistics / DoubleSummaryStatistics
DoubleSummaryStatistics stats = employees.stream()
    .mapToDouble(Employee::salary)
    .summaryStatistics();
 
System.out.println("Count : " + stats.getCount());
System.out.println("Sum   : " + stats.getSum());
System.out.println("Avg   : " + stats.getAverage());
System.out.println("Min   : " + stats.getMin());
System.out.println("Max   : " + stats.getMax());
 
// Individual calculations
double totalSalary = employees.stream()
    .mapToDouble(Employee::salary)
    .sum();
 
OptionalDouble avgSalary = employees.stream()
    .mapToDouble(Employee::salary)
    .average();
```
 
> `mapToDouble()` converts to a primitive `DoubleStream`, which has numeric terminal operations (`sum()`, `average()`, `summaryStatistics()`). This avoids boxing overhead of `Stream<Double>`.
 
---
 
### Q16. Partition employees into two groups — salary above and below 75,000.
 
```java
Map<Boolean, List<Employee>> partitioned = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.salary() > 75000));
 
List<Employee> highEarners = partitioned.get(true);   // salary > 75000
List<Employee> lowEarners  = partitioned.get(false);  // salary <= 75000
 
System.out.println("High earners: " +
    highEarners.stream().map(Employee::name).collect(Collectors.joining(", ")));
// Output: High earners: Alice, Carol
```
 
> `partitioningBy` is a special case of `groupingBy` that always produces exactly two groups (`true`/`false`). Use it when you have a binary condition.
 
---
 
### Q17. Find the second highest salary using streams.
 
```java
// Approach 1: distinct + sorted + skip
OptionalDouble secondHighest = employees.stream()
    .mapToDouble(Employee::salary)
    .distinct()
    .boxed()
    .sorted(Comparator.reverseOrder())
    .skip(1)
    .mapToDouble(Double::doubleValue)
    .findFirst();
 
// Approach 2: collect distinct sorted list
List<Double> sortedSalaries = employees.stream()
    .map(Employee::salary)
    .distinct()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
 
double secondHighestSalary = sortedSalaries.get(1); // 85000.0
```
 
> **Key:** `.distinct()` ensures ties don't count as separate entries. If two employees earn 90,000, the second-highest is the next unique value.
 
---
 
### Q18. Flatten a list of lists using `flatMap`.
 
```java
List<List<String>> nestedSkills = List.of(
    List.of("Java", "Spring", "Kafka"),
    List.of("Docker", "Kubernetes"),
    List.of("AWS", "GCP", "Java")
);
 
List<String> allSkills = nestedSkills.stream()
    .flatMap(Collection::stream)
    .distinct()
    .sorted()
    .collect(Collectors.toList());
 
// Output: [AWS, Docker, GCP, Java, Kafka, Kubernetes, Spring]
```
 
> `map()` would give `Stream<List<String>>` (stream of lists). `flatMap()` "flattens" each list into individual elements, giving a single `Stream<String>`.
 
**Real-world use:** Flatten employee skill sets, order line items, nested API responses.
 
---
 
### Q19. What is the difference between `map()` and `flatMap()`? Between `findFirst()` and `findAny()`?
 
**`map()` vs `flatMap()`:**
 
```java
// map: one-to-one transformation → Stream<List<String>>
employees.stream().map(e -> List.of(e.name(), e.department()));
 
// flatMap: one-to-many + flatten → Stream<String>
employees.stream().flatMap(e -> Stream.of(e.name(), e.department()));
```
 
**`findFirst()` vs `findAny()`:**
 
| Method | Guarantee | Best for |
|--------|-----------|---------|
| `findFirst()` | Returns first element in **encounter order** | Sequential streams, ordered results |
| `findAny()` | Returns **any** element (non-deterministic) | Parallel streams (faster) |
 
```java
// Sequential stream — both return same result
Optional<Employee> first = employees.stream()
    .filter(e -> e.department().equals("Engineering"))
    .findFirst();  // guaranteed: Alice
 
// Parallel stream — findAny is faster (no coordination needed)
Optional<Employee> any = employees.parallelStream()
    .filter(e -> e.department().equals("Engineering"))
    .findAny();    // could return Alice or Carol
```
 
---
 
### Q20. Implement a word frequency counter using streams on a list of sentences.
 
```java
List<String> sentences = List.of(
    "the quick brown fox",
    "the fox jumped over the lazy dog",
    "the dog barked at the fox"
);
 
Map<String, Long> wordFrequency = sentences.stream()
    .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
    .map(String::toLowerCase)
    .collect(Collectors.groupingBy(
        word -> word,
        Collectors.counting()
    ));
 
// Sort by frequency descending
wordFrequency.entrySet().stream()
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
    .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
 
// Output:
// the  : 5
// fox  : 3
// dog  : 2
// ...
```
 
> **Pipeline:** split sentences → flatten to words → lowercase → group + count → sort by frequency. This pattern comes up in banking for log analysis, transaction code frequency, and audit event counting.
