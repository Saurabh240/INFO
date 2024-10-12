Here’s a revised and formatted version of the content as a Q&A for an eBook, with grammatical corrections and simplified explanations:

##### TRANSACTIOB MANAGEMENT

### Q-1) What is a Transaction?

A transaction is an operation or a series of operations in which either all steps are completed successfully, or none are. This ensures consistency and reliability. For example, in a money transfer application, if money is deducted from one account but not credited to another due to a crash, the transaction is rolled back to prevent any loss of money. Transactions ensure that unless a transaction is committed, no permanent changes occur, and if something goes wrong, the system can roll back to the previous state.

### Q-2) What are the ACID Properties of a Transaction?

ACID stands for Atomicity, Consistency, Isolation, and Durability:

- Atomicity: Ensures that all operations within a transaction are completed; otherwise, the transaction is entirely rolled back.
- Consistency: Ensures that the database remains in a consistent state before and after the transaction.
- Isolation: Ensures that transactions do not interfere with each other; the data in one transaction is not visible to others until the transaction is committed.
- Durability: Once a transaction is committed, the changes are permanent and cannot be undone.

### Q-3) What are Distributed Transactions?

A distributed transaction, also known as a global or XA transaction, spans multiple databases or resources like messaging brokers. For example, when transferring money between two different banks, each with its own database, the transaction must ensure that money is deducted from one bank and credited to another. This involves a transaction manager and resource managers, using a Two-Phase Commit protocol to ensure all or nothing happens across all resources.

### Q-4) What are Transaction Isolation Levels?

Transaction isolation levels determine how database transactions are isolated from each other. There are four main isolation levels, each addressing different types of anomalies:

1. Read Uncommitted: Allows dirty reads, meaning one transaction can read data that has been modified but not yet committed by another transaction.
2. Read Committed: Prevents dirty reads by allowing transactions to only read committed data. However, non-repeatable reads and phantom reads can still occur.
3. Repeatable Read: Ensures that if a transaction reads a record, it can read the same record again with the same data, preventing non-repeatable reads. Phantom reads can still occur.
4. Serializable: The most strict isolation level, preventing all anomalies, including phantom reads, but with a performance trade-off.

### Q-5) What is Optimistic vs. Pessimistic Locking?

These are two approaches to handling concurrent access to database records by multiple transactions:
Optimistic Locking: Assumes that conflicts between transactions are rare. Each transaction checks the version number or timestamp of the record before updating it. If another transaction has modified the record, the first transaction must retry or discard its changes. This approach is best when the system has many read operations and fewer updates.

Pessimistic Locking: Assumes that conflicts are common and explicitly locks records or tables during a transaction, preventing other transactions from accessing them until the lock is released. This provides better data integrity but can lead to deadlocks and reduced performance. It’s often used with specific transaction isolation levels, like Read Committed and Repeatable Read.


### Q-6) What is @Transactional in Spring?
@Transactional is used to manage transactions in Spring. It ensures that methods are executed within a transaction boundary.