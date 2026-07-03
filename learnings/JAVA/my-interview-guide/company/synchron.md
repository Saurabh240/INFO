# 1. What is Effectively Final?

Java 8 introduced **effectively final variables**.

A variable is effectively final if:

* it is assigned only once
* final keyword is not written

Example

```java
int x = 10;

Runnable r = () -> {
    System.out.println(x);
};
```

Works because x never changes.

But

```java
x++;
```

Now compiler gives error.

Reason:

Lambda captures local variables by value.

---

# 2. Generics

Generics provide **compile-time type safety**.

Without generics

```java
List list = new ArrayList();
list.add("ABC");
Integer i = (Integer) list.get(0);
```

Runtime exception.

With generics

```java
List<String> list = new ArrayList<>();
```

Compiler prevents wrong type.

Benefits

* Type safety
* No casting
* Reusable code

---

## What does

```java
? extends A
```

mean?

Upper bounded wildcard.

Means

> Any subclass of A.

Example

```java
List<? extends Number>
```

Can contain

* Integer
* Double
* Float

But cannot add elements.

Remember

**Producer Extends Consumer Super (PECS)**

Producer

```
extends
```

Consumer

```
super
```

---

# 3. Checked Exception Advantages & Disadvantages

Advantages

* Compiler forces handling
* Better API documentation
* Prevents accidental ignoring

Disadvantages

* Boilerplate code
* Too many try-catch blocks
* Developers sometimes write empty catch

Interview conclusion:

Checked exceptions are good for recoverable situations like IOException.

---

# 4. HashMap Key Internals

HashMap uses

```
hashCode()

↓

bucket

↓

equals()
```

Steps

Suppose

```java
map.put(emp, value);
```

1 hashCode()

2 bucket calculation

3 if bucket empty

store

Else

equals()

If equal

replace

Else

linked list/tree

---

## What happens if key changes?

Very famous interview question.

```java
Employee e = new Employee(1);

map.put(e,"ABC");

e.setId(10);
```

Now

```java
map.get(e);
```

returns null.

Reason

Hash code changed.

Object remains in old bucket.

HashMap searches new bucket.

Hence object becomes unreachable.

Never use mutable objects as keys.

---

# 5. Guess Code Output

Usually they ask

```java
Integer a = 127;
Integer b = 127;

System.out.println(a==b);
```

Output

```
true
```

Because Integer cache.

Now

```java
Integer a=128;
Integer b=128;
```

Output

```
false
```

---

# 6. Dependency Injection

Instead of creating dependency

```java
class Service{

Repository repo=new Repository();

}
```

Inject it.

```java
@Service

class Service{

private Repository repo;

public Service(Repository repo){

this.repo=repo;

}

}
```

Advantages

* Loose coupling
* Easy testing
* Easy mocking
* Better maintainability

Spring container injects beans.

---

# 7. HashMap vs ConcurrentHashMap

| HashMap              | ConcurrentHashMap          |
| -------------------- | -------------------------- |
| Not thread-safe      | Thread-safe                |
| Allows one null key  | No null key/value          |
| Fail-fast iterator   | Weakly consistent iterator |
| Entire map unsafe    | Bucket level locking + CAS |
| Faster single thread | Better concurrent access   |

---

# 8. Improve API Performance

Mention multiple points.

* Pagination
* Database indexing
* Redis cache
* Connection pooling
* Async processing
* Compression (GZIP)
* DTO instead of entity
* Avoid N+1 query
* Batch processing
* Kafka for async work
* Proper SQL tuning
* Response caching
* HTTP Keep Alive

---

# 9. What is SOP?

Two possibilities.

Programming

```
System.out.println()

```

Interview usually means

Standard Operating Procedure

Development process

* Coding standard
* Code review
* Testing
* Deployment
* Monitoring

---

# 10. Variable Length Arguments

```java
public void add(int... nums)
```

Equivalent to array.

Call

```java
add(1,2,3);
```

---

Can main use varargs?

Yes

```java
public static void main(String... args)
```

Perfectly valid.

---

# 11. Difference

```java
int i=9;

int j=09;
```

First

decimal

Second

Invalid

Reason

Leading zero means octal.

Octal digits

0-7

9 is invalid.

---

# 12. Base Class

Base class

Parent class.

```java
class Animal

class Dog extends Animal
```

Animal is base class.

---

# 13. Base Class vs Abstract Class

Base class

May have implementation.

Can instantiate.

Abstract

Cannot instantiate.

Contains abstract methods.

---

# 14. Abstract Class vs Interface

Abstract

Can have constructor

Can have state

Can have normal methods

Interface

No constructor

Supports multiple inheritance

Java 8

default

static

methods

Functional interface

Exactly one abstract method.

Example

```java
Runnable
Comparator
Predicate
Function
Supplier
Consumer
```

---

# 15. Collections Used

Mention

ArrayList

LinkedList

HashMap

ConcurrentHashMap

TreeMap

HashSet

LinkedHashMap

PriorityQueue

Deque

---

# 16. Constant hashCode()

If hashCode()

always returns

1

Everything goes into same bucket.

Before Java 8

O(n)

After Java 8

Linked list becomes tree

O(log n)

if bucket size >8.

---

# 17. Rehashing

When

```
size >

capacity × load factor

```

Default

```
16

0.75

```

Threshold

12

Capacity doubles

16→32→64

Recalculate bucket.

---

# 18. Criteria for HashMap Key

Good key

Immutable

Overrides equals()

Overrides hashCode()

Examples

String

UUID

Integer

---

Can String?

Yes.

Perfect key.

Can int?

Primitive

No

Use Integer.

---

# 19. Primitive as Value?

Primitive cannot be stored.

Autoboxing

```java
map.put("A",5);
```

Actually

```java
Integer.valueOf(5)
```

---

# 20. Fail Fast vs Fail Safe

Fail Fast

Throws

ConcurrentModificationException

Example

ArrayList

HashMap

Fail Safe

Works on copy

ConcurrentHashMap

CopyOnWriteArrayList

---

# 21. Comparing Interest Rate

Use

```java
BigDecimal
```

Never float/double.

Compare

```java
compareTo()

```

Not equals()

---

# 22. Equals & hashCode Contract

Rules

Equal objects

Must have same hashCode

Unequal objects

May have same hashCode

hashCode can collide

equals confirms equality

---

# 23. Make Classes Testable

Constructor Injection

Interfaces

Dependency Injection

No static methods

Small methods

SOLID

Avoid new keyword

---

# 24. Object Creation on Heap

```java
Employee e=new Employee();
```

Memory

```
Stack

↓

Reference

↓

Heap

↓

Object

```

Constructor initializes object.

---

# 25. Final Keyword

Final variable

Cannot change.

Final method

Cannot override.

Final class

Cannot inherit.

Example

```java
String
```

---

# 26. Loose Coupling

Use

Interfaces

Dependency Injection

Factory Pattern

Strategy Pattern

Constructor Injection

---

# 27. Why Interfaces?

Multiple implementations

Testing

Mocking

Loose coupling

SOLID

---

# 28. Abstract Data Type

ADT defines

"What"

not

"How"

Example

List

Queue

Stack

Interface defines behavior.

Implementation

ArrayList

LinkedList

---

# 29. Mocking

Replace real dependency during testing.

Example

Repository

↓

Mockito

```java
when(repo.findById(1))

.thenReturn(emp);
```

Only Service gets tested.

---

# 30. Object Promotion in GC

Young Generation

↓

Minor GC

↓

Survivor

↓

After surviving multiple GC

↓

Old Generation

↓

Major GC

---

# 31. Common Spring Boot Questions

### Why constructor injection over field injection?

* Immutable dependencies
* Easier testing
* No reflection
* Recommended by Spring

---

### Difference between @Component, @Service, @Repository

Component

Generic bean.

Service

Business layer.

Repository

DAO + exception translation.

---

### Why Spring Boot?

* Embedded server
* Auto Configuration
* Starter dependencies
* Production ready Actuator
* Minimal configuration

---

# 32. JPA/Hibernate Questions

Difference between save() and saveAndFlush()

Lazy vs Eager Loading

N+1 Problem

First-level cache

Second-level cache

Cascade Types

Fetch Join

Optimistic vs Pessimistic Locking

---

# 33. Kafka Questions

What is partition?

Consumer Group?

Offset?

Why Kafka is faster?

Exactly Once?

Idempotent Producer?

---

# 34. Jenkins Questions

Pipeline stages

```
Checkout

↓

Build

↓

Test

↓

Sonar

↓

Package

↓

Deploy

↓

Smoke Test

```

---

# 35. REST API Questions

PUT vs PATCH

POST vs PUT

Idempotency

HTTP status codes

JWT Authentication

Versioning

Pagination

Swagger/OpenAPI

---

# Coding Questions Frequently Asked at Synchron

* Reverse a String without using built-in methods.
* Find the first non-repeated character in a string.
* Find duplicate elements in a list using Java Streams.
* Group employees by department and calculate average salary.
* Find the second highest salary using Streams.
* Merge two sorted arrays.
* Implement an LRU Cache.
* Detect a cycle in a linked list.
* Design a thread-safe Singleton.
* Fix failing JUnit test cases (type conversion, null handling, boundary conditions).
* Refactor tightly coupled code to use interfaces and dependency injection.
* Implement a Toll Booth or Obstacle Runs solution with comprehensive JUnit tests.

## Questions you should ask the interviewer

* What are the major technical challenges the team is currently solving?
* How are microservices deployed and monitored in production?
* What is the CI/CD process and branching strategy?
* Which cloud platform and observability tools are used?
* What are the expectations for this role in the first 90 days?

For an 8-year profile, also be prepared for **system design** questions such as designing a payment service, order management system, or URL shortener, and be ready to discuss performance tuning, caching, resilience patterns, and production troubleshooting from your own projects.
