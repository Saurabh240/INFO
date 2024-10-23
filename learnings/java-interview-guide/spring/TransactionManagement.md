### ***Transaction management***

### **Easy Questions:**

1. **What is a Transaction?**
   - A transaction is a unit of work where either all operations succeed, or none do, ensuring consistency and reliability in operations like money transfers.

2. **What are the ACID Properties of a Transaction?**
   - ACID stands for **Atomicity**, **Consistency**, **Isolation**, and **Durability**—ensuring that transactions are executed reliably.

3. **What is `@Transactional` in Spring?**
   - `@Transactional` is an annotation in Spring used to define the scope of a transaction at the method or class level, ensuring that the methods within the boundary are executed within a transactional context.

4. **What is the default transaction propagation behavior in Spring?**
   - The default propagation behavior is **PROPAGATION_REQUIRED**, meaning the method will use an existing transaction or start a new one if none exists.

5. **What is the role of the Transaction Manager in Spring?**
   - The **Transaction Manager** coordinates the transaction across multiple resources, ensuring consistency in commit and rollback scenarios.

6. **What is the difference between `@Transactional` on a class and a method?**
   - When placed on a class, all methods in that class inherit the transaction settings, while placing it on a method applies only to that specific method.

7. **What happens if a transaction is not committed in Spring?**
   - If a transaction is not committed, any changes made during the transaction are not applied to the database and are instead rolled back.

### **Intermediate Questions:**

8. **What are Distributed Transactions?**
   - Distributed transactions span multiple databases or resources (e.g., message brokers), ensuring all or nothing happens across all resources using protocols like Two-Phase Commit (2PC).

9. **What is the Propagation setting in Spring transactions?**
   - Transaction **propagation** defines how transaction boundaries behave when a transaction is called within another transaction. For example:
     - `REQUIRED`: Joins an existing transaction or creates a new one.
     - `REQUIRES_NEW`: Suspends the existing transaction and creates a new one.
     - `MANDATORY`: Throws an exception if no existing transaction is found.

10. **What are Transaction Isolation Levels?**
    - Isolation levels define the degree to which transactions are isolated from one another. The levels are:
      - **Read Uncommitted**
      - **Read Committed**
      - **Repeatable Read**
      - **Serializable**

11. **How does transaction rollback work in Spring?**
    - By default, **unchecked exceptions** (subclasses of `RuntimeException`) trigger a rollback, while **checked exceptions** do not. You can customize this behavior using the `rollbackFor` or `noRollbackFor` attributes of the `@Transactional` annotation.

12. **What is the difference between Optimistic and Pessimistic Locking?**
    - **Optimistic Locking** assumes conflicts are rare and uses versioning or timestamps to detect conflicts, while **Pessimistic Locking** locks resources to prevent conflicts, assuming they are more common.

13. **What is the difference between Programmatic and Declarative transaction management in Spring?**
    - **Declarative Transaction Management** uses annotations like `@Transactional`, while **Programmatic Transaction Management** involves explicitly managing transactions using the `PlatformTransactionManager` API.

14. **How does `PROPAGATION_REQUIRES_NEW` work in Spring?**
    - This propagation type suspends the existing transaction and starts a new one, committing or rolling back the new transaction independently of the outer transaction.

15. **What is the isolation level `SERIALIZABLE`, and when should it be used?**
    - **SERIALIZABLE** is the highest isolation level, ensuring complete isolation between transactions but with a performance trade-off. It should be used in scenarios requiring strict consistency, such as financial transactions.

### **Advanced Questions:**

16. **What are the potential issues with Pessimistic Locking?**
    - Pessimistic locking can lead to deadlocks and reduced performance, especially in systems with high concurrency, as resources are locked until a transaction completes.

17. **What is the Two-Phase Commit (2PC) protocol in distributed transactions?**
    - 2PC is a protocol used to ensure consistency in distributed transactions. It involves two phases: a **prepare phase**, where all participants agree to commit or rollback, and a **commit phase**, where the actual commit or rollback happens.

18. **What is the N+1 select problem, and how does it relate to transactions?**
    - The **N+1 problem** occurs when a system executes one query to retrieve entities and then an additional query for each entity to retrieve related data. It can lead to excessive database calls in transactional contexts.

19. **What is the role of the `@Transactional(readOnly = true)` attribute in Spring?**
    - The `readOnly` attribute optimizes performance by avoiding unnecessary locking in transactions that only read data. It hints to the underlying database that no data modification will occur.

20. **How can you implement retry mechanisms for transaction failures in Spring?**
    - Spring’s `@Retryable` annotation or the **RetryTemplate** can be used to retry transactions upon failure, handling issues like transient network or database errors.

21. **How can you prevent deadlocks in transaction management?**
    - Deadlocks can be mitigated by using **Pessimistic Locking** carefully, setting proper **timeout values**, and ensuring **consistent locking orders** across different transactions.

22. **What is the difference between XA and non-XA transactions?**
    - **XA Transactions** are distributed transactions that span multiple resources and require a coordinator (e.g., JTA) to manage them. **Non-XA Transactions** are simpler, local transactions that involve a single resource.

23. **What is a Savepoint in transaction management?**
    - A **Savepoint** allows you to mark a specific point within a transaction, so you can roll back to that point without rolling back the entire transaction.

24. **What is the role of a Transaction Synchronization Manager in Spring?**
    - The **TransactionSynchronizationManager** in Spring keeps track of transaction synchronization callbacks for resources that participate in a transaction. It helps ensure proper order of commit, flush, and cleanup operations.

25. **How do Nested Transactions work in Spring?**
    - Nested transactions allow a transaction within a transaction. If the inner transaction fails, only the changes in the nested transaction are rolled back, not the outer one. Spring supports nested transactions with `PROPAGATION_NESTED`.