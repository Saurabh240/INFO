## 🧠 **DAY 1–2: PostgreSQL Deep Dive**

---

### ✅ 1. **Joins and Indexing**

#### 🔹 a. **Joins Overview**

* **Inner Join:** Common records in both tables.
* **Left Join:** All from left + matches from right.
* **Right Join / Full Outer Join:** Symmetric.

**🧪 Practice:**

```sql
SELECT c.name, o.amount
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id;
```

**💡 Task:** Write a query for all customers (even those without orders) showing customer name and order count.

---

#### 🔹 b. **Indexing Basics**

* **B-tree index:** Default for most datatypes.
* **GIN index:** For array or JSONB fields.
* **Covering index:** Include additional fields to avoid lookups.

**🧪 Practice:**

```sql
CREATE INDEX idx_customer_email ON customers(email);
```

**💡 Task:** Create index on `orders.created_at` and test query performance with `EXPLAIN ANALYZE`.

---

### ✅ 2. **Explain Plans & Performance**

* **EXPLAIN:** Execution plan without running.
* **ANALYZE:** Run + show timings.
* Seq Scan vs. Index Scan vs. Bitmap Scan.

**🧪 Example:**

```sql
EXPLAIN ANALYZE SELECT * FROM orders WHERE customer_id = 1;
```

**💡 Task:** Analyze performance of `orders` table query with and without index on `customer_id`.

---

### ✅ 3. **CTEs and Window Functions**

#### 🔹 a. **CTEs**

* Common Table Expressions → improve readability.

```sql
WITH top_customers AS (
  SELECT customer_id, COUNT(*) as order_count
  FROM orders
  GROUP BY customer_id
  ORDER BY order_count DESC
  LIMIT 10
)
SELECT * FROM top_customers;
```

**💡 Task:** Write CTE to find top 3 products by revenue.

---

#### 🔹 b. **Window Functions**

* Compute aggregates over partitions (non-collapsing).

```sql
SELECT customer_id, amount,
       RANK() OVER (PARTITION BY customer_id ORDER BY amount DESC) as rank
FROM orders;
```

**💡 Task:** Find latest order per customer using `ROW_NUMBER()`.

---

### ✅ 4. **Transactions and Isolation**

* ACID properties.
* `BEGIN`, `COMMIT`, `ROLLBACK`.
* Isolation levels: Read committed, repeatable read, serializable.

**💡 Task:** Write transaction to deduct inventory + create order atomically.

---

### ✅ 5. **Deadlocks**

* Cyclic wait → transaction abort.
* **Avoid by consistent locking order**.

**🧪 Example:** `SELECT ... FOR UPDATE`.

**💡 Task:** Simulate deadlock with two sessions locking rows in different order.

---

## 🧠 **DAY 3–4: Kafka Essentials**

---

### ✅ 1. **Kafka Core Concepts**

#### 🔹 a. **Topics & Partitions**

* Topic = category/feed.
* Partition = unit of parallelism & ordering.

**🧪 Example:**

```shell
kafka-topics.sh --create --topic orders --partitions 3 --replication-factor 1
```

**💡 Task:** Explain how Kafka ensures ordering at partition level.

---

#### 🔹 b. **Consumer Groups**

* Consumer group = horizontal scaling unit.
* One partition consumed by one consumer within group.

**💡 Task:** Describe rebalancing behavior if new consumer joins existing group.

---

### ✅ 2. **Kafka Streams**

* Stream processing API.
* Stateless vs stateful operations.
* Windowing.

**🧪 Example:**

```java
KStream<String, String> stream = builder.stream("input-topic");
stream.mapValues(value -> value.toUpperCase())
      .to("output-topic");
```

**💡 Task:** Create stream that aggregates orders by customer with tumbling window of 1 minute.

---

### ✅ 3. **Exactly-once Semantics**

* Idempotence at producer.
* Transactions spanning producer/consumer.

**💡 Task:** Configure producer for exactly-once semantics (`enable.idempotence=true`).

---

### ✅ 4. **Error Handling & Rebalancing**

* Retries, dead-letter topics.
* Sticky partition assignment.
* `max.poll.interval.ms`.

**💡 Task:** How to handle poison-pill record that always fails consumer?

---

## 🧠 **DAY 5–7: Low-Level System Design**

---

### ✅ 1. **Order Management System Design**

#### 🔹 Requirements:

* Users place orders.
* Inventory updates.
* Payment processing.
* Event-driven architecture.

**💡 Task:**
Draw API design with endpoints:

* `POST /orders`
* `GET /orders/{id}`
* `PATCH /orders/{id}/status`
  Include DB schema: `orders`, `order_items`, `inventory`.

---

### ✅ 2. **URL Shortener**

#### 🔹 Core Design:

* Short URL generation (base62).
* Collision avoidance.
* Expiry support.

**💡 Task:**
Design DB schema for `urls` table and REST API for encode/decode.

---

### ✅ 3. **Rate Limiter**

#### 🔹 Approaches:

* Token bucket.
* Redis + sliding window log.
* Fixed window counter.

**💡 Task:**
Design API: `POST /api/resource`
Implement Redis-based fixed window limiter at 100 req/min/user.

---

### ✅ 4. **Database Modeling Focus**

* Normalize when needed.
* Indexing strategy.
* Partitioning if large-scale.

**💡 Task:** Model schema for an ecommerce cart system.

---

### ✅ 5. **Caching in System Design**

* API caching (reverse proxy / CDN).
* DB result caching (Redis).
* Application-level cache.

**💡 Task:** Suggest caching strategy for `GET /products/{id}` API.

---

## 📘 **Behavioral Question Prep**

Prepare answers in **STAR format**:

1️⃣ **Handling failure in production:**

* Example of diagnosing production outage (e.g., service crash, DB lock).
* Communication with stakeholders.
* Fix + postmortem.

2️⃣ **Leading feature development:**

* Planning API design.
* Coordinating with frontend/backend teams.
* Delivery and feedback.

3️⃣ **Optimizing performance:**

* Index tuning example for slow SQL query.
* API performance: caching, pagination.

4️⃣ **Handling conflict:**

* Scenario with peer disagreement.
* How you reached resolution respectfully.