# Spring Data JPA and ORM Documentation

## 1. What is Spring Data JPA?

Spring Data JPA is a framework that simplifies the creation of the data access layer for applications using Java Persistence API (JPA). It reduces boilerplate code by allowing you to:

- Create a JPA entity class and a repository interface extending one of the Spring Data JPA repository interfaces.
- Automatically perform CRUD operations and more on the database without needing to manually handle entity managers or Hibernate templates.
- Use finder methods to define queries abstractly.
- Utilize built-in support for paging and sorting results.
- Execute JPQL and native queries when needed.

### Benefits

- Reduces boilerplate code for CRUD operations.
- Handles entity managers and factories internally.
- Provides support for finder methods and dynamic queries.
- Includes paging and sorting capabilities.

## 2. How to Use Spring Data JPA

To use Spring Data JPA in your Spring Boot application:

1. **Add Dependencies**:

   - Add the Spring Data JPA starter dependency to your `pom.xml`.
   - Include the database driver dependency (e.g., MySQL or Oracle).

2. **Create Model and Repository**:

   - Define a JPA entity class with appropriate JPA annotations.
   - Create a repository interface that extends `JpaRepository` or `CrudRepository`.

3. **Configuration**:

   - Configure the datasource in `application.properties` with URL, username, and password.
   - Spring Boot automatically creates the data source and beans based on these properties.

4. **Testing**:
   - Write test cases to verify repository operations (e.g., saving and retrieving entities).

## 3. Create Coupon Service Data Access Layer

1. **Create Spring Boot Project**:

   - Use Spring Tool Suite (STS) to create a new Spring Starter Project named "coupon service".
   - Add dependencies for Spring Data JPA and MySQL.

2. **Create Entities**:

   - Define the `Coupon` entity with fields: `id`, `code`, `discount`, and `expiryDate`.
   - Annotate the class with `@Entity` and specify primary key with `@Id` and `@GeneratedValue`.

3. **Create Repository**:

   - Define a `CouponRepo` interface extending `JpaRepository`.

4. **Configure Datasource**:

   - Set datasource properties in `application.properties`.

5. **Testing**:
   - Write a JUnit test to save and retrieve a coupon entity.

## 4. ORM (Object-Relational Mapping)

ORM is the process of mapping Java classes to database tables and their fields to columns. Benefits include:

- Avoid direct SQL usage and low-level JDBC APIs.
- Use high-level operations like `save`, `update`, and `delete` on objects.

## 5. Create Product Service Data Access Layer

1. **Create Spring Boot Project**:

   - Create a new Spring Starter Project named "product service".
   - Add dependencies for Spring Data JPA and MySQL.

2. **Create Entities**:

   - Define the `Product` entity with fields: `id`, `name`, `description`, and `price`.
   - Annotate the class with `@Entity` and specify primary key with `@Id` and `@GeneratedValue`.

3. **Create Repository**:

   - Define a `ProductRepo` interface extending `JpaRepository`.

4. **Configure Datasource**:

   - Update `application.properties` with appropriate database name and credentials.

5. **Testing**:
   - Write a JUnit test to save and retrieve a product entity.

## 6. JPA (Java Persistence API)

JPA is a standard for performing object-relational mapping in Java EE applications. Key points include:

- JPA specification and API help developers interact with ORM tools like Hibernate, Open JPA, and EclipseLink.
- JPA abstracts database operations using entities, entity managers, and various annotations.

## 7. Different Entity Object States

Entities in Hibernate can have three states:

- **Transient**: The object is not associated with a Hibernate session.
- **Persistent**: The object is associated with a Hibernate session and reflects changes in the database.
- **Detached**: The object was previously persistent but is no longer associated with the Hibernate session.

## 8. JPA Associations

JPA supports four types of associations:

- **One-to-One**: A single entity is related to another single entity.
- **Many-to-Many**: Multiple entities are related to multiple entities.
- **One-to-Many**: A single entity is related to multiple entities.
- **Many-to-One**: Multiple entities are related to a single entity.

Associations can be configured as unidirectional or bidirectional.

## 9. Cascading

Cascading propagates operations like `persist`, `merge`, `remove`, `refresh`, and `detach` from a parent entity to its child entities. Types include:

- `CascadeType.PERSIST`
- `CascadeType.MERGE`
- `CascadeType.REMOVE`
- `CascadeType.REFRESH`
- `CascadeType.DETACH`
- `CascadeType.ALL`

## 10. Lazy Loading

Lazy loading defers the loading of associated entities until they are accessed. It is configured using the `fetch` attribute in annotations like `@OneToMany` and `@ManyToMany`.

- **Eager Loading**: Fetches associated data immediately.
- **Lazy Loading**: Fetches associated data on demand.

## 11. Two Levels of Caching

Hibernate supports caching at two levels:

### Level 1 Cache (Session Cache)

- **Scope**: Session level.
- **Behavior**: Data is cached within a Hibernate session. When a client accesses the application, Hibernate's session caches data so that repeated requests within the same session use the cached data instead of querying the database again.
- **Default**: Always enabled and free.
- **Limitations**: The cache is specific to the session, meaning different sessions do not share this cache.

### Level 2 Cache (Session Factory Cache)

- **Scope**: Session factory level.
- **Behavior**: Data is cached at the session factory level, allowing cached data to be shared across multiple sessions. This means that when different users access the application, their sessions can use the shared cache if configured.
- **Configuration**: Requires additional setup, such as integrating a caching provider like Ehcache, JBoss Treecache, or OSCache.
- **Benefits**: Reduces database load by reusing cached data across sessions, improving performance for frequently accessed data.

**Example**:

When a client accesses data for the first time, the data is loaded from the database into the level 2 cache. On subsequent accesses, the session checks the level 2 cache before querying the database. If the data is found in the cache, it is returned directly without additional database queries.

**Configuration**: Level 2 cache needs to be explicitly configured in Hibernate, and caching providers like Ehcache are often used due to their ease of use and powerful features.

## 12. How to Configure Second Level Cache

To configure second-level caching in a Spring Boot project:

1. **Select a Caching Library**:

   - Choose a second-level caching library such as Ehcache or Hazelcast.
   - Add the corresponding dependency to your `pom.xml`.

2. **Create Configuration File**:

   - For Ehcache, create an `ehcache.xml` configuration file.
   - Define cache settings including temporary storage location, maximum elements, and time to live for cached objects.

3. **Update `application.properties`**:

   - Enable second-level caching by setting `spring.jpa.properties.hibernate.cache.use_second_level_cache=true`.

4. **Configure Cache Region Factory**:

   - Set the cache region factory class in `application.properties`:
     - For Ehcache: `spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory`.
     - For Hazelcast: Use the appropriate class for Hazelcast.

5. **Point to Configuration File**:

   - Specify the path to the `ehcache.xml` file or other configuration files in your classpath.

6. **Annotate Entities**:
   - Use the `@Cache` annotation on entity classes to specify caching strategies and regions.

**Example**:

````xml
<!-- ehcache.xml -->
<ehcache>
    <cache name="myCache"
           maxEntries="1000"
           eternal="false"
           timeToLiveSeconds="300"/>
</ehcache>

```properties
# application.properties
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
java
Copy code
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

```java
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCache")
public class Product {
    // fields, getters, and setters
}

## 12. JPQL (Java Persistence Query Language)

JPQL is a query language similar to SQL but operates on JPA entities rather than database tables. Key points include:

- JPQL queries are written against entity objects and their attributes.
- Supports selecting, updating, and deleting operations on entities.
- Queries are created using the `EntityManager`'s `createQuery` method.

### Example

```java
String jpql = "SELECT c FROM Coupon c WHERE c.discount > :discount";
TypedQuery<Coupon> query = entityManager.createQuery(jpql, Coupon.class);
query.setParameter("discount", 10);
List<Coupon> results = query.getResultList();
````
