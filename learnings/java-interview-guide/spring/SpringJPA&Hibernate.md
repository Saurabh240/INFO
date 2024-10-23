# ***Spring Data JPA and ORM Documentation***

### **Easy Questions:**

1. **What is Spring Data JPA?**
   - Spring Data JPA simplifies data access layers by reducing boilerplate code through repository interfaces and built-in support for CRUD, finder methods, and pagination.

2. **How to use Spring Data JPA in a Spring Boot project?**
   - Add Spring Data JPA dependencies, create JPA entities, define repository interfaces, and configure data sources in `application.properties`.

3. **What is JPA (Java Persistence API)?**
   - JPA is a specification that provides an object-relational mapping (ORM) framework to manage relational data using Java objects, working with ORM tools like Hibernate.

4. **What is an ORM (Object-Relational Mapping)?**
   - ORM is a technique for mapping Java objects to database tables and vice versa, enabling high-level operations on objects without dealing with SQL directly.

5. **What is the role of the `JpaRepository` interface in Spring Data JPA?**
   - `JpaRepository` provides basic CRUD operations and pagination out-of-the-box without needing to implement methods manually.

6. **What are JPA associations?**
   - JPA supports four types of relationships: One-to-One, One-to-Many, Many-to-One, and Many-to-Many, which can be unidirectional or bidirectional.

7. **How does Lazy Loading work in JPA?**
   - Lazy Loading defers fetching associated entities until they are accessed. Configured using the `fetch` attribute in annotations like `@OneToMany`.

8. **What is JPQL (Java Persistence Query Language)?**
   - JPQL is an object-oriented query language that operates on JPA entities rather than database tables, allowing queries on entities and attributes.

### **Intermediate Questions:**

9. **What are the different entity states in Hibernate?**
   - Entities can be in **Transient**, **Persistent**, or **Detached** states depending on their association with a Hibernate session.

10. **What is the difference between `@OneToMany` and `@ManyToOne` in JPA?**
    - `@OneToMany`: A single entity is related to multiple entities.  
    - `@ManyToOne`: Multiple entities are related to a single entity.

11. **What is Cascading in JPA?**
    - Cascading allows operations on a parent entity (e.g., `persist`, `remove`, `merge`) to propagate to associated child entities. Cascade types include `PERSIST`, `MERGE`, `REMOVE`, etc.

12. **What is the difference between `CrudRepository` and `JpaRepository`?**
    - `CrudRepository` provides basic CRUD operations, while `JpaRepository` extends it to include JPA-specific methods like batch operations and pagination.

13. **What is the use of `@Query` in Spring Data JPA?**
    - `@Query` is used to define custom JPQL or native SQL queries directly in the repository interface.

14. **What is the difference between JPQL and native queries?**
    - **JPQL** operates on JPA entities and their relationships, abstracting away from the underlying database, while **native queries** allow direct SQL execution on the database.

15. **How do you implement pagination in Spring Data JPA?**
    - Use the `Pageable` and `Page` interfaces provided by `JpaRepository` to paginate results by specifying page size and number.

16. **How can you define sorting in Spring Data JPA?**
    - Use the `Sort` interface or the `PageRequest.of()` method to sort results based on entity attributes.

### **Advanced Questions:**

17. **What are the two levels of caching in Hibernate?**
    - **First Level Cache**: Session-level cache, which is enabled by default.  
    - **Second Level Cache**: SessionFactory-level cache, shared across sessions, and requires explicit configuration with providers like EhCache or Hazelcast.

18. **How to configure the second-level cache in Spring Boot with Hibernate?**
    - Choose a caching provider (e.g., Ehcache), configure cache settings in `application.properties`, and annotate entities with `@Cache` to enable caching.

19. **What is the N+1 query problem in Hibernate, and how do you solve it?**
    - The N+1 problem occurs when one query fetches an entity, followed by N queries to fetch associated entities. It can be solved by using **eager fetching** or **JOIN FETCH** in JPQL.

20. **What are Hibernate Session and SessionFactory?**
    - **Session**: Represents a single unit of work with the database, managing entity states.  
    - **SessionFactory**: A heavyweight object that holds configurations and provides session instances. It is shared across multiple sessions.

21. **What is the difference between `EntityManager` and `Session` in Hibernate?**
    - **EntityManager** is JPA's interface for managing entities and their lifecycle, while **Session** is Hibernate's API. JPA is a specification, while Hibernate is an implementation.

22. **How does dirty checking work in Hibernate?**
    - Dirty checking in Hibernate automatically detects changes made to persistent objects during a session and synchronizes those changes with the database without explicitly calling `update()`.

23. **What is the use of the `@EntityGraph` annotation in JPA?**
    - `@EntityGraph` is used to define a graph of related entities that should be fetched together, improving performance by avoiding multiple queries (solves N+1 problem).

24. **How can you handle transactions in Spring Data JPA?**
    - Transactions in Spring Data JPA are managed using the `@Transactional` annotation, which can be applied at the method or class level for declarative transaction management.

25. **What are orphan removal and cascading in JPA?**
    - **Orphan Removal**: Automatically removes child entities when the parent is removed or the relationship is broken.
    - **Cascading**: Propagates operations like `persist`, `merge`, or `remove` from the parent to the child entity.

26. **What is the purpose of `@Inheritance` in JPA?**
    - `@Inheritance` is used to define inheritance strategies (single table, joined, or table-per-class) for entity hierarchies in JPA.

27. **How do you handle optimistic locking in Hibernate?**
    - Optimistic locking in Hibernate is handled using a `@Version` field in the entity, which increments on each update to prevent concurrent updates from overwriting changes.

28. **What is the difference between `find()` and `getReference()` in JPA?**
    - `find()` immediately retrieves the entity from the database, while `getReference()` returns a proxy object that is lazily loaded when accessed.