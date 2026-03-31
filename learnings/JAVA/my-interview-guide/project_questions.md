# Interview Q&A — Behavioral & Technical (Senior Java Developer)

---

## Q1. Tell me about yourself.

**Answer:**

I'm currently working as a Senior Java Developer at IBM, where I'm leading development of microservices for a high-transaction banking platform.

My primary work involves designing scalable services for exposure calculation and risk assessment, where we process large volumes of financial transactions using Kafka-based event-driven architecture.

I have around 7+ years of experience working with Java, Spring Boot, and microservices, and I've also been actively involved in cloud-native deployments using Docker and Kubernetes on AWS and GCP.

One of my key achievements was leading the migration from Java 8 to Java 17 and monolith to microservices, which improved performance by around 25% and reduced deployment time by 40%.

I also mentor junior developers and contribute to architectural decisions, especially around scalability and fault tolerance.

Currently, I'm looking for opportunities where I can work on large-scale distributed systems, take more ownership in system design, and contribute to impactful business solutions.

---

## Q2. Walk me through your current project architecture.

**Answer:**

Our system is designed as a microservices-based architecture for handling high-volume banking transactions and exposure calculations.

The flow starts with the **Transaction Processor Service**, which receives incoming transaction data via REST APIs. This service performs initial validation and publishes events to Kafka topics.

From there, downstream services like **roll-up services** and **summary services** consume these events asynchronously. Roll-up services aggregate data at different levels (like account or customer), and summary services generate final exposure views used for risk assessment.

We also have **manual and suspense review services**, which handle edge cases or failed transactions that require human intervention.

**For communication:**
- We use synchronous REST calls (WebClient) where an immediate response is required
- We use Kafka-based asynchronous communication for high-throughput processing and decoupling services

**Kafka is central to our system — we use it to:**
- Handle large volumes of events reliably
- Enable parallel processing using partitions
- Maintain loose coupling between services

**For fault tolerance:**
- We use Resilience4j for circuit breaking and retries
- Implement idempotency to avoid duplicate processing
- Use retry topics and DLQs for failed Kafka messages

**For scalability:**
- Services are containerized using Docker and deployed on Kubernetes
- We scale horizontally using Kubernetes HPA based on CPU and Kafka lag

This architecture allows us to process high transaction volumes with reliability and minimal downtime.

---

## Q3. How do you handle duplicate messages in Kafka?

**Answer:**

Kafka does not guarantee exactly-once consumption by default, so we handle duplicate processing at the application level.

We implement idempotency by maintaining a **unique transaction ID**. Before processing a message, we check if it has already been processed — via DB or cache like Redis.

Additionally, we use **manual offset commits** only after successful processing to reduce duplicate chances, and configure retry + DLQ topics for failures.

---

## Q4. What is a Kafka consumer group and how does it work?

**Answer:**

A consumer group is a set of consumers that work together to consume messages from a topic.

Each partition is assigned to **only one consumer within a group**, ensuring parallel processing and load balancing.

If a consumer fails, Kafka **rebalances** partitions across the remaining consumers automatically.

---

## Q5. What partition strategy do you use and why?

**Answer:**

We use **account ID as the partition key** to ensure that all events related to the same account go to the same partition.

This is important because it allows us to:
- Maintain **ordering per account**
- Avoid **race conditions** in balance and exposure calculations

---

## Q6. How does Kafka guarantee message ordering?

**Answer:**

Kafka guarantees ordering **only within a partition**, not across partitions.

Since we use account ID as the partition key, all events for a specific account go to the same partition, ensuring correct ordering for that entity.

---

## Q7. Your Kafka consumer is lagging heavily in production. How do you identify the root cause, fix it, and prevent it in future?

**Answer:**

### 1. Identify Root Cause

First, I'd validate where the bottleneck is using metrics and logs:

- **Kafka metrics:** Consumer lag per partition (Prometheus / Burrow), fetch rate, bytes consumed/sec
- **Consumer health:** Pod status, restarts, OOMs (Kubernetes), thread pool saturation, GC pauses
- **Processing time:** Avg/percentile processing latency per message
- **Rebalance issues:** Frequent rebalances cause pauses in consumption
- **External dependencies:** DB latency, downstream API slowness

> Key question: Are we under-consuming due to **low parallelism** or **slow processing**?

### 2. Fix (Targeted Actions)

**If CPU / processing bottleneck:**
- Optimize handler logic (reduce DB calls, batch writes)
- Introduce batch consumption (poll + process in batches)
- Use async processing within consumer (bounded thread pool)

**If insufficient parallelism:**
- Increase number of partitions
- Scale consumer instances (ensure ≤ partitions per consumer group)
- Tune `max.poll.records`, `fetch.min.bytes`, `fetch.max.wait.ms`

**If dependency is slow:**
- Add caching (e.g., Redis)
- Apply circuit breakers/timeouts (Resilience4j)
- Move heavy work to async downstream topics

**If failures causing retries:**
- Route poison messages to DLQ
- Avoid blocking the main consumer loop

### 3. Prevent (Engineering + Ops)

- **Autoscaling:** HPA based on Kafka lag (not just CPU)
- **Backpressure:** Pause/resume consumption if downstream is slow
- **Idempotent processing:** Safe retries without duplicates
- **Observability:** Dashboards for lag, throughput, error rate, processing latency with alerts on lag thresholds
- **Capacity planning:** Partition count aligned with peak throughput

**Summary:** I'd start by checking consumer lag and processing metrics to identify whether the issue is due to slow processing, insufficient parallelism, or downstream dependency latency. If processing is slow, I'd optimize logic or introduce batch processing. If parallelism is the issue, I'd scale consumers and increase partitions. For failures, I'd use DLQs to avoid blocking consumption. Long-term, I'd implement autoscaling based on Kafka lag, add observability, and ensure idempotent processing to handle retries safely.

---

## Q8. What happens if a consumer processes a message successfully but crashes before committing the offset?

**Answer:**

If the consumer crashes before committing the offset, Kafka will **re-deliver the message**, which can lead to duplicate processing.

To handle this safely, we implement **idempotent processing** using a unique transaction ID. Before processing, we check if the message has already been processed — for example, using a database or Redis cache.

This ensures that even if the same message is consumed again, it does not cause incorrect results like duplicate transactions.

---

## Q9. How do you handle distributed transactions across microservices? 2PC or Saga? Choreography vs Orchestration?

**Answer:**

In our microservices architecture, we use the **Saga pattern** to manage distributed transactions instead of 2PC, because 2PC is not scalable and tightly couples services.

Each service maintains its own local transaction, and after completing its operation, it publishes an event to Kafka, which triggers the next step in the workflow.

We follow the **orchestration-based Saga pattern**, where a central orchestrator service controls the flow of the transaction. The orchestrator sends commands to each service and listens for success or failure events.

**Example — banking transaction flow:**
1. **Step 1:** Transaction service debits amount
2. **Step 2:** Exposure service updates risk metrics
3. **Step 3:** Notification service sends confirmation

If any step fails, the orchestrator triggers **compensating transactions** — such as refunding the amount or reverting exposure updates.

We implement this using Kafka for communication, where each step emits events, and the orchestrator maintains the state of the transaction.

**To handle failures:**
- We ensure idempotent compensation logic
- Use retries and DLQ for failed events
- Maintain transaction state in a persistent store to recover from crashes

---

## Q10. What are the differences between Choreography and Orchestration Saga? When would you use each?

**Answer:**

**Choreography:** There is no central controller. Each service performs its local transaction and publishes an event, which is consumed by the next service. The flow is completely event-driven and decentralized.

**Orchestration:** A central orchestrator controls the entire workflow. It explicitly tells each service what action to perform and handles success/failure responses.

### When to Use

| | Choreography | Orchestration |
|---|---|---|
| Complexity | Simple workflows | Complex business processes |
| Coupling | Loosely coupled | More controlled |
| Visibility | Hard to track flow | Easy to monitor centrally |
| Best for | Independent service events | Banking, financial flows |

Choreography is better for simple workflows where services are loosely coupled and the flow is straightforward. Orchestration is preferred for complex business processes — like banking transactions — where we need better control, monitoring, and error handling.

**Handling the single point of failure risk in orchestration:**
- Deploy orchestrator in highly available mode (multiple replicas)
- Use persistent state storage
- Enable retry and recovery mechanisms

---

## Q11. Explain end-to-end JWT authentication in your microservices.

**Answer:**

JWT-based authentication is a **stateless mechanism** where the server does not store session data. The token has three parts: **header**, **payload (claims)**, and **signature** — where the signature ensures integrity and prevents tampering.

### 1. Login Flow

1. Client sends credentials (username/password) to the authentication API
2. Spring Security uses `AuthenticationManager` and `UserDetailsService` to validate credentials
3. If valid, the server generates a JWT token containing user details and roles (claims), signed with a secret key
4. This token is returned to the client

### 2. Request Flow (After Login)

1. Client sends JWT in the `Authorization` header (`Bearer <token>`)
2. A JWT filter (`OncePerRequestFilter`) intercepts every request
3. It extracts and validates the token (signature + expiry check)
4. If valid, it sets authentication in the `SecurityContext`
5. The request proceeds to the controller

### 3. Authorization

Roles/permissions are stored in the token payload. Spring Security uses them for role-based access control via annotations like `@PreAuthorize("hasRole('ADMIN')")`.

### 4. Token Expiry & Refresh

JWTs are short-lived for security:
- **Access token:** Short expiry (e.g., 15 minutes)
- **Refresh token:** Longer expiry (e.g., 7 days)

When the access token expires:
1. Client calls the refresh API
2. Server validates the refresh token
3. Server issues a new access token

### 5. In Microservices

Each service validates the JWT **independently** using the same secret key or public key (in case of asymmetric encryption — RS256). This ensures **stateless and scalable** authentication across all services without any shared session store.

---

## Q12. What is the N+1 problem in Hibernate/JPA? How do you identify and fix it?

**Answer:**

The N+1 problem occurs in JPA/Hibernate when fetching a parent entity along with its child entities, typically due to lazy loading.

**Example:** If we fetch a list of customers:
- **1 query** is executed to fetch all customers
- Then, **for each customer**, an additional query fetches its orders

So if we have N customers → **1 + N queries** total → severe performance issue at scale.

### Root Cause

Child collections are usually lazy-loaded by default. When we iterate over them, Hibernate fires a separate query for each parent entity.

### How to Identify

- Enable SQL logging (`show_sql`, `format_sql` in `application.yml`)
- Use Hibernate statistics
- APM tools (Dynatrace, New Relic) to detect excessive query counts

### How to Fix

**1. JOIN FETCH (Most common)**
```java
@Query("SELECT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllWithOrders();
```
Fetches parent and child in a single query.

**2. @EntityGraph (Cleaner, more flexible)**
```java
@EntityGraph(attributePaths = {"orders"})
List<Customer> findAll();
```

**3. Batch Fetching (Reduces N queries to N/batch)**
```properties
spring.jpa.properties.hibernate.default_batch_fetch_size=20
```
Reduces queries from N to N/batch_size.

**4. DTO Projection (Best for read-heavy APIs)**
```java
@Query("SELECT new com.example.CustomerDTO(c.name, c.email) FROM Customer c")
List<CustomerDTO> findCustomerSummaries();
```
Fetches only required fields — avoids loading unnecessary entity data entirely.

---

## Q13. How do you design a highly available microservice system?

**Answer:**

To design a highly available microservices system, I focus on **eliminating single points of failure** and ensuring the system can handle failures gracefully.

### 1. Load Balancing

Distribute traffic across multiple instances of a service:
- **Client-side:** Using service discovery (Eureka)
- **Server-side:** Using API Gateway or external load balancer (AWS ALB)

### 2. Service Discovery

Since services are dynamic (pods come and go), we use service discovery (Eureka/Consul) so services can register themselves and discover others without hardcoding endpoints.

### 3. Fault Tolerance

- Circuit breakers (Resilience4j) to stop cascading failures
- Retries with exponential backoff
- Timeouts on all downstream calls

### 4. Asynchronous Communication

We use Kafka for async processing to decouple services and absorb traffic spikes without dropping requests.

### 5. Scalability

We deploy services on Kubernetes and use:
- Horizontal Pod Autoscaling (HPA)
- Scaling based on CPU or Kafka consumer lag

### 6. Database Design

Each microservice owns its database — avoids tight coupling and allows independent scaling.

### 7. Deployment Strategy

Zero-downtime deployments:
- **Rolling updates** — gradual replacement of pods
- **Blue-Green deployments** — instant switch between environments
- **Canary deployments** — route small % of traffic to new version first

### 8. Observability

- **Logging:** ELK Stack (Elasticsearch, Logstash, Kibana)
- **Monitoring:** Prometheus + Grafana
- **Tracing:** Zipkin for distributed request tracing

These enable quick issue detection and debugging across services.

**Overall goal:** Build a system that is resilient, scalable, and can recover automatically from failures without impacting users.

---

## Q14. Your API is getting 10x traffic suddenly. What breaks first? How do you handle it?

**Answer:**

If traffic increases 10x, I'd first identify the bottleneck across layers — API, service, or database — and apply targeted scaling and protection mechanisms.

### 1. What Breaks First

- **API layer:** Thread pool saturation, high latency, connection timeouts
- **Downstream services:** Cascading failures if circuit breakers aren't in place
- **Database:** Connection pool exhaustion and slow queries under load

### 2. Immediate Stabilization (Priority Zero)

Before scaling anything:
- Enable **rate limiting / throttling** at the API Gateway
- Apply **circuit breakers and timeouts** to prevent cascading failures
- Use **graceful degradation** — return partial or cached responses for non-critical data

### 3. Scale the Right Layer

- Increase pod replicas using **Kubernetes HPA**
- Scale based on CPU + request rate or Kafka consumer lag
- Ensure enough Kafka partitions if event-driven processing is involved

### 4. Reduce Load (Smart Optimization)

Instead of only scaling, reduce the load:
- Add **Redis caching** for frequently accessed data
- Use **CDN** for static content
- Introduce **async processing via Kafka** for non-critical operations

### 5. Database Bottleneck (Critical)

- Check slow queries and add proper indexing
- Optimize joins and use pagination (never unbounded result sets)
- Increase connection pool size carefully (HikariCP tuning)
- Introduce **read replicas** for scaling read traffic
- Consider **sharding/partitioning** for very high sustained load

### 6. Long-Term Fix

- **Load testing** (JMeter / Gatling) to baseline performance
- **Capacity planning** with autoscaling policies tuned for known traffic patterns

**Key principle:** Don't scale blindly. Protect the system first, then scale bottlenecks, then optimize for sustained traffic.

---

## Q15. Tell me about a time you handled a critical production issue. (STAR Format)

**Answer:**

### Situation

In my current project at IBM, we had a critical production issue where Kafka consumers started lagging heavily during peak transaction hours. This caused delays in exposure calculations and impacted downstream reporting systems.

### Task

I was responsible for quickly identifying the root cause, stabilizing the system, and ensuring minimal business impact — since this was directly affecting financial transaction processing.

### Action

I approached it in a structured way:

1. **Investigated metrics first** — checked Kafka consumer lag metrics and noticed a sudden spike coinciding with the latest deployment
2. **Identified root cause** — analyzed logs and found that the recent deployment introduced a slow database call inside the consumer processing logic, causing each message to take significantly longer and building up a backlog
3. **Immediate fix** — rolled back the deployment immediately to stabilize the system
4. **Root cause fix:**
   - Optimized the slow query by adding proper indexing and reducing unnecessary joins
   - Introduced batch processing instead of processing messages one by one
   - Temporarily increased consumer instances and partitions to clear the existing backlog faster
5. **Long-term prevention:**
   - Added monitoring alerts on Kafka consumer lag thresholds
   - Introduced performance testing as a mandatory pre-deployment gate
   - Added circuit breaker and timeout configurations for all DB calls within consumers

### Result

- Reduced consumer lag by over **80%** within a few hours
- Improved system processing time by approximately **30%**
- No financial data inconsistency occurred throughout the incident
- Avoided similar issues in future through improved monitoring and deployment safeguards
