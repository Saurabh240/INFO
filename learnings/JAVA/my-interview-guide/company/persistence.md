## 1. Current Project / Architecture

**1. Explain about your project.**
Currently I'm working on a banking platform related to payment authorization and risk assessment. My current project has two important journeys — the Payment Events Journey and the Exposure Journey.
At a high level, when a payment event comes into the platform, the Payment Events Journey ingests and validates the event, enriches it with required metadata and routes it through the appropriate risk-processing path. It's primarily an event-driven architecture using Kafka, with gRPC used for some synchronous communication between components.
The other major journey is the Exposure Journey. Its purpose is to calculate and maintain the One Exposure, which represents the amount a borrower owes at a particular point in time. Exposure is calculated and reconciled using inputs such as GAR transaction data, GAR summary data, authorization records, reversals, adjustments and payment events.
The Exposure Journey has different processors for transaction matching, exposure calculation, refresh, roll-up and reconciliation. Data comes through the ingress layer, is processed by the appropriate processors, persisted through the data layer and then distributed to downstream systems.
Technically, we're using Java and Spring Boot, Kafka and Google Pub/Sub for event-driven processing, gRPC for service communication, Cassandra/PostgreSQL and RDM for persistence, and Kubernetes for deployment.
My role is primarily on the backend side — working on Java/Spring Boot services, event processing, APIs, troubleshooting, testing and understanding the end-to-end flow across these journeys.

**2. Explain the architecture.**
```
                 PAYMENT EVENT
                      |
                      v
              Payment Events
                  Ingress
                      |
                      v
               Validation /
               Enrichment
                      |
                      v
               Risk Routing
                      |
                      v
                 Processor
                      |
                Kafka / gRPC
                      |
                      v
             EXPOSURE JOURNEY
                      |
              +-------+-------+
              |               |
              v               v
       Transaction       Summary /
        Matching         Exposure
              |           Processing
              +-------+-------+
                      |
                      v
               Exposure State
                      |
          +-----------+-----------+
          |                       |
          v                       v
     Account Level          Client/Customer
       Exposure               Roll-up
          |
          v
      Persistence /
       Downstream
```

**3. What is Exposure?**
Exposure represents the amount that a borrower owes at a particular point in time. The challenge is that this amount isn't necessarily static — different events can change it, for example transactions, payment events, authorization records, reversals or adjustments. So the Exposure Journey continuously processes these inputs and maintains an up-to-date exposure at different levels, starting from transaction-level processing and eventually aggregating it to account, client and customer levels.

**4. Explain the Payment Events Journey.**
The Payment Events Journey is responsible for processing payment-related events as they enter the platform. The flow starts with an ingress component, where events are received and validated. The event is then enriched with the metadata required for downstream processing. Based on the available information, the risk-routing layer determines the appropriate processing path. The processor then performs the core business processing and produces the required downstream events.
Kafka is the primary event-driven communication mechanism, while gRPC is used where synchronous service-to-service communication is required. The key benefit of this architecture is that ingestion, routing and processing are separated, so individual components can evolve and scale independently.

**5. Explain the Exposure Journey.**
The Exposure Journey is more focused on calculating, updating and reconciling exposure. It receives different types of inputs such as GAR transaction and summary data, authorization records, reversals, adjustments and payment events.
Depending on the type of input, different processors perform specific responsibilities — the transaction-matching processor handles transaction-level matching, the exposure processors calculate or refresh exposure, and the roll-up processor aggregates exposure to higher levels such as client and customer. There are also processors for reconciliation, regulatory relationships, manual re-aging and suspense aging.
Conceptually: ingestion → processing/matching → exposure calculation → aggregation → persistence/downstream distribution.

**6. Why did you use Kafka?**
Kafka fits well because the system processes a continuous flow of business events and different downstream components need to consume and process those events independently. It provides decoupling between producers and consumers, allows consumers to scale independently and provides durable event storage so events can be replayed when required. It also fits our processing model because different stages of the journey can consume events, perform their processing and publish subsequent events.

**7. Why Kafka AND Google Pub/Sub?**
They serve messaging requirements within the overall architecture, but the exact choice depends on the integration and platform boundary. Kafka is used heavily for our event-driven processing and streaming flows, while Google Pub/Sub provides managed messaging capabilities within the GCP ecosystem. I wouldn't introduce both simply for the same purpose — the choice should depend on the existing platform architecture, operational requirements and which systems need to integrate.

**8. Why do you need both transaction-level and account-level exposure?**
They represent different levels of aggregation. Transaction-level processing allows us to identify and reconcile individual financial events. Account-level exposure represents the resulting financial position of an account. Once account-level exposure is calculated, it can be aggregated further to client or customer level depending on the business requirement. Maintaining these levels also allows the system to support both detailed reconciliation and higher-level risk or authorization decisions.

**9. Why use gRPC?**
gRPC is useful for efficient service-to-service communication, particularly when we control both sides of the communication. It uses Protocol Buffers for strongly typed contracts and generally has lower serialization overhead than JSON-based REST communication. In our architecture, Kafka is useful for asynchronous event-driven communication, while gRPC is used where one service needs a direct synchronous interaction with another.

**10. Why Cassandra/PostgreSQL/RDM? Why multiple databases?**
The different stores serve different requirements in the platform. Relational storage is appropriate where we need structured data and transactional capabilities. Cassandra is designed for high-scale distributed access patterns where we need predictable performance and horizontal scalability. RDM is also part of the platform's persistence/data-service architecture. I wouldn't choose a database simply because it's available — the important factor is the access pattern, consistency requirements, scale and operational characteristics of the workload.

**11. What is the biggest challenge in this architecture?**
The biggest challenge with an event-driven distributed architecture is that troubleshooting and maintaining consistency become more complex. A single business event can pass through multiple services and asynchronous stages, so when something goes wrong, it's not always immediately obvious where the problem occurred. That's why correlation IDs, structured logging, tracing, metrics, consumer-lag monitoring and clear retry/DLQ mechanisms are important. Idempotency is another key consideration because events may be retried or replayed.

**12. How would you explain the entire project in 30 seconds?**
It's a banking authorization and risk platform built around event-driven processing. The Payment Events Journey ingests, validates and routes payment events through risk-processing components, while the Exposure Journey calculates and maintains the borrower's current exposure using transaction, authorization, payment and adjustment data. The architecture is primarily Java/Spring Boot with Kafka and gRPC, persistence through RDM and databases such as Cassandra/PostgreSQL, and Kubernetes-based deployment. My role is primarily backend engineering around these event-processing journeys, including development, troubleshooting, testing and understanding the end-to-end transaction flow.

---

## 2. Your Exact Contribution

**13. What exactly did you work on / What is your contribution?**
My role is a combination of hands-on development and technical ownership across the Payment Events and Exposure journeys. On the development side, I've designed and implemented backend APIs and microservices using Java and Spring Boot, worked with Kafka-based event processing, database integration and security. Apart from development, I work on production issues, performance optimization, CI/CD and technical design. I also participate in code reviews and mentor junior engineers, so my responsibility isn't limited to writing code — I also help with technical decisions and take ownership of issues through to resolution.
From an ownership perspective, I focus on understanding the business flow first and then the specific component I'm changing, including its inputs, processing logic, persistence and downstream events — because changes in one processor can affect the overall journey.

---

## 3. Difficult Technical Problem

**14. Explain one technically difficult problem you solved.**
One challenging problem was scaling our Kafka-based event processing as transaction volume increased significantly. We had to increase processing capacity while making sure that increasing throughput didn't introduce duplicate processing or inconsistent downstream state. I worked on the event-driven design, including consumer configuration, idempotent processing and deduplication. We also looked at bottlenecks in the processing pipeline and database interactions, and introduced appropriate retry and error-handling mechanisms while monitoring consumer lag and processing latency. The result was that the system could handle significantly higher event volume while maintaining reliability and improving latency.

---

## 4. Slow API Troubleshooting

**15. How do you troubleshoot a slow API?**
I first try to identify where the latency is being introduced rather than immediately increasing infrastructure resources. I look at application metrics and distributed traces to break down the request latency, then check whether the bottleneck is in the application logic, database, external API, Kafka, Elasticsearch or infrastructure. For database issues, I check query execution plans, indexes and connection pool behaviour. For Java issues, I look at CPU, memory, thread pools and GC behaviour. For external calls, I check timeout and latency metrics. Once I identify the bottleneck, I make the appropriate change, validate it with performance testing and then monitor the application after deployment.

---

## 5. Kafka Failure Handling

**16. What happens when an event / Kafka consumer processing fails?**
The first thing is to distinguish between a transient technical failure and a permanent business or data failure. For transient failures, I'd use controlled retries with appropriate backoff. For messages that repeatedly fail after the retry limit, I'd move them to a dead-letter topic/DLQ so that one problematic event doesn't block the overall processing flow. I'd monitor consumer lag and failure metrics, and provide sufficient observability to identify the failed event, understand the reason and replay it after the underlying issue is resolved. Since these are financial events, idempotency is particularly important because replaying an event must not accidentally apply the same business operation twice — and I wouldn't acknowledge or commit a message as successfully processed until the required business processing has actually completed.

**17. How do you troubleshoot a failed Kafka processing flow?**
I'd trace the event from the producer to the consumer. First I'd check whether the event was actually produced successfully. Then I'd check the Kafka topic and partition, consumer group status and consumer lag. Next I'd check application logs using the event or correlation ID and identify whether processing failed because of validation, business logic, database access or a downstream dependency. If the event is in a DLQ, I'd inspect the failure reason and determine whether it can be safely replayed. Finally, after fixing the root cause, I'd replay the affected events and verify that the downstream state is correct.

**18. How do you handle failure between microservices?**
It depends on whether the communication is synchronous or asynchronous. For synchronous calls, I consider connection and read timeouts, retries where they're safe, circuit breakers and fallback behaviour. For asynchronous communication through Kafka, I consider retries, consumer error handling, idempotency and dead-letter handling. An important point is that retries shouldn't blindly be applied to every failure because they can amplify an outage — the operation also needs to be idempotent where retries are possible.

---

## 6. Kafka Duplicate / Exactly-Once Processing

**19. How do you make event processing idempotent?**
The consumer should be able to process the same event more than once without producing an incorrect business result. Typically I'd use a unique event ID or business transaction ID and maintain a way of identifying whether that event has already been processed. Database constraints or a processed-event mechanism can help enforce this. This is particularly important because a consumer can fail after completing the business operation but before acknowledging the Kafka message, causing the message to be delivered again.

**20. What happens if the same payment event arrives twice?**
I wouldn't assume that the event will arrive only once. The consumer should detect duplicates using a unique event or transaction identifier. If the event has already been successfully processed, we should avoid applying the business operation again and acknowledge the duplicate appropriately. This is why idempotency is a key design consideration in our event-driven processing.

**21. How do you handle duplicate Kafka events?**
I assume duplicate delivery is possible in a distributed system, so consumers should ideally be idempotent. We can include a unique event ID or business transaction ID with every event. Before applying a business operation, the consumer can check whether that event has already been processed, or enforce uniqueness at the database level. For example, if a transaction event arrives twice, the second delivery should not create a second transaction — we can use the event ID as an idempotency key and maintain a processed-event record or use an appropriate unique constraint. I prefer designing consumers to tolerate replay rather than assuming Kafka will never deliver a duplicate.

**22. How does Kafka guarantee exactly-once processing?**
Kafka provides exactly-once semantics through features such as idempotent producers and transactions, but exactly-once behavior depends on the complete processing flow. At the Kafka level, the producer can use idempotence to prevent duplicate records caused by retries, and Kafka transactions can atomically publish records and commit consumer offsets. However, if my consumer is also updating an external database, Kafka alone cannot automatically make that database update exactly once — in that case I also need application-level idempotency or deduplication. So exactly-once should be considered an end-to-end property rather than something Kafka automatically guarantees for every external operation.

---

## 7. DB ↔ Elasticsearch Consistency

**23. A client reports that data in the API and Elasticsearch is inconsistent. What would you do?**
I would first determine whether the inconsistency is due to delayed indexing, failed events, partial processing, or an incorrect synchronization process. I would compare the source-of-truth database with Elasticsearch, check application/Kafka logs and consumer lag, and identify failed indexing operations. For recovery, I would make indexing idempotent and provide a reindex/reconciliation mechanism rather than manually changing Elasticsearch.

**24. How do you maintain consistency between DB and Elasticsearch?**
I would treat the transactional database as the source of truth and Elasticsearch as a derived search index. When data changes in the database, we can publish an event representing that change and have a consumer update Elasticsearch asynchronously. This avoids coupling the database transaction directly to Elasticsearch availability — if Elasticsearch is temporarily unavailable, the event can be retried rather than failing the original business transaction. I would also make the Elasticsearch update idempotent and have monitoring and a reconciliation or reindexing mechanism to recover missing or inconsistent documents.

**25. What if the DB update succeeds but event publishing fails?**
I wouldn't rely on a simple sequence of updating the database and then publishing to Kafka because there is a failure window between those two operations. Depending on the architecture, I would consider the transactional outbox pattern — the business update and the outbox event are persisted within the same database transaction. A separate process then reads the outbox and publishes the event to Kafka. This means that if the database transaction succeeds, we also have a durable record of the event that needs to be published, and if Kafka is temporarily unavailable, the event can be retried later.

---

## 8. Production Incident

**26. Tell me about a production incident you handled.**
At GlobalLogic, I worked on an enterprise asset management platform where I handled multiple P1 and P2 production incidents. One of the key things I learned was not to jump directly into fixing the symptom — we first identified whether the problem was application-level, database-related, infrastructure-related or caused by an external dependency. We used ELK logs, thread dumps and heap analysis to investigate production issues. After identifying the root cause, we implemented the fix, validated it and monitored the system after deployment. Over time, this helped reduce our MTTR from around 6 hours to around 4 hours.
The biggest learning was the importance of structured incident handling, good observability and documenting the root cause — resolving the immediate problem isn't enough; we also need to put preventive measures in place so the same issue doesn't repeatedly come back.

**27. How do you monitor a production microservice?**
I look at monitoring from three perspectives: logs, metrics and traces. For metrics, I'd monitor request latency, throughput, error rate, CPU, memory, JVM metrics, database connection pools and Kafka consumer lag where applicable. For logs, I want structured logs with correlation or trace IDs so that a request can be followed across services. For distributed systems, tracing helps identify whether latency is coming from our service or a downstream dependency. We've worked with tools such as ELK, Prometheus, Grafana and Zipkin, and on AWS we've also used CloudWatch and X-Ray. The goal isn't just to collect logs and metrics but to make it possible to quickly identify the location and root cause of a production problem.

---

## 9. Unclear Requirements / Estimation

**28. How do you estimate project timelines when client requirements are unclear?**
If requirements are unclear, I wouldn't immediately give a fixed commitment because that creates unnecessary risk. First I'd break the requirement into smaller functional areas and identify assumptions, dependencies and unknowns. I'd discuss the critical questions with the client and clarify the acceptance criteria. If there is a technically uncertain area, I'd do a small proof of concept or spike to understand the complexity. Once the requirements are sufficiently clear, I'd break the work into smaller stories, estimate them with the team and provide a timeline along with the assumptions and dependencies. If the scope changes later, I'd communicate the impact clearly and revise the timeline rather than silently absorbing additional scope.

**29. How do you handle changing client requirements?**
First I try to understand why the requirement changed and whether it's business-critical or simply a preference. Then I assess the impact on the existing design, development effort, testing and delivery timeline. I communicate that impact clearly to the client and discuss options — for example, whether we should replace an existing item, move the change to the next iteration or extend the timeline. I don't want the team to simply say yes to every change and then miss the original commitment. I prefer transparent scope management and clear communication.

**30. How do you handle disagreement with a client or team member?**
I try to separate the technical disagreement from the person. First I understand their reasoning and make sure we're solving the same business problem. Then I explain my proposal with data, technical trade-offs, maintainability and impact rather than saying that my approach is simply better. If there are multiple valid approaches, I compare them based on cost, complexity, performance, reliability and delivery timeline. If we still can't reach an agreement, I involve the appropriate technical lead or architect and make sure the final decision is documented. Once a decision is made, I fully support it even if it wasn't my preferred approach.

---

## 10. Why Change + Why Persistent

**31. Why are you looking for a change?**
I've had a very good learning experience in my current role, particularly working on banking microservices, Java modernization, Kafka, cloud deployment and technical leadership. At this stage, I'm looking for a role where I can take broader ownership of backend systems, work on different technical challenges and have more direct interaction with clients and stakeholders. The Persistent opportunity is interesting to me because it combines strong backend engineering with cloud and data technologies, while also giving me an opportunity to contribute from a technical ownership perspective.

**32. What difficulties are you facing in your current company?**
One challenge is that the scope of my current role has become somewhat predictable. I've gained good experience in the existing system, but I'm looking for broader ownership and exposure to new technical problems. That's one of the reasons I'm exploring this opportunity.

**33. Why Persistent?**
What attracted me to Persistent is that the role isn't limited to pure development — it combines backend engineering, cloud technologies, data platforms and client interaction. My core strength is Java and backend microservices, and I've also worked with cloud-native deployments, Kafka, databases and production systems, so I see a good overlap with the role. At the same time, the client-facing nature of the role gives me an opportunity to grow beyond implementation into technical discussions, solution design and ownership.

---

## Additional Questions

*(Unique questions from the source material that don't fall under the 10 focus areas above — kept as-is, none removed.)*

**34. How do you test such an event-driven system?**
I look at testing at multiple levels. Unit tests cover individual business components and processing logic. Integration tests validate interactions between components such as Kafka and databases — our setup also supports Testcontainers, which allows us to test against realistic infrastructure dependencies. Then we have end-to-end or journey-level validation to ensure that an event entering the system produces the expected downstream state. For critical event processing, I'd also test failure scenarios, duplicate events, retries and replay behaviour rather than only the happy path.

**35. How would you troubleshoot a slow Elasticsearch query?**
First I'd reproduce the query and use Elasticsearch's profiling or explain capabilities to understand where the time is being spent. I'd check the query itself, mappings, whether we're using text versus keyword correctly, the number and size of shards, aggregations and the amount of data being searched. At the cluster level I'd check CPU, JVM heap, garbage collection, disk I/O and cluster health. I'd also look for inefficient queries, such as unnecessary scoring when we only need filtering, or aggregations over unnecessarily large datasets. After optimization, I'd validate the improvement under representative load.

**36. Why Elasticsearch instead of database search?**
I would use Elasticsearch when the primary requirement is fast and flexible search over large volumes of data, especially full-text search, filtering, relevance-based search or aggregations. A relational database is still preferable for transactional operations and maintaining the source of truth. So I would typically use PostgreSQL or another transactional database for business transactions and Elasticsearch as a search-optimized projection of that data.

**37. How do you optimize a slow MongoDB query?**
I start with the actual query pattern rather than creating indexes blindly. I use MongoDB's explain functionality to understand whether the query is performing a collection scan or using an appropriate index. Then I look at the filtering and sorting fields and create a suitable single or compound index. I also use projection when I don't need the complete document and avoid returning unnecessarily large result sets. For aggregation pipelines, I try to filter early and review expensive stages such as lookup, sort and group. Finally, I monitor the query after the change to make sure the index actually improves the workload.

**38. When would you use MongoDB vs PostgreSQL?**
I would choose based on the access pattern and consistency requirements rather than saying one is universally better. PostgreSQL is a strong choice for relational data, transactions, joins and well-defined schemas where ACID consistency is important. MongoDB is useful when the data is naturally document-oriented, the schema evolves frequently, or we frequently retrieve related information as a single document. So I would consider the business requirements, data relationships, transaction requirements, scale and access patterns before choosing between them.

**39. How would you deploy a Java microservice on GCP?**
For a containerized Spring Boot service, I would package the application as a Docker image, push it to Google Artifact Registry and deploy it on Cloud Run or GKE depending on the operational requirements. For a relatively stateless service where I want managed infrastructure and automatic scaling, Cloud Run is a good option. If I need more control over networking, workloads or Kubernetes-level configuration, I'd consider GKE. I'd also configure IAM with least privilege, secrets management, health checks, logging, monitoring and autoscaling. For asynchronous communication, GCP Pub/Sub can be used to decouple services.

**40. How do you optimize BigQuery cost and performance?**
My first focus would be reducing the amount of data scanned. I would avoid `SELECT *` and retrieve only the required columns. For large tables, I'd use appropriate partitioning, commonly based on a date or timestamp when the access pattern supports it, so queries can benefit from partition pruning. I would consider clustering on frequently filtered or grouped fields, review joins and aggregations, and avoid repeatedly processing the same large datasets when pre-aggregation or materialized views would be more appropriate. I would also use BigQuery's query execution information to understand bytes processed and identify expensive stages.

**41. What is partitioning vs clustering in BigQuery?**
Partitioning divides a table into logical partitions, commonly based on a date or timestamp. When a query filters on the partitioning column, BigQuery can avoid scanning irrelevant partitions. Clustering organizes data within the table based on selected columns, which can improve performance for queries that frequently filter or group on those columns. They can also be used together — for example, I might partition an event table by event date and cluster it by customer ID if customer-based queries are frequent.

**42. How did you measure your performance improvement?**
We first established a baseline by measuring the API latency under a representative workload. We then used profiling and monitoring to identify the bottleneck. After making the optimization, we ran the same or comparable workload and compared the latency metrics against the baseline. We also monitored the application after deployment to make sure the improvement was sustained in production and that we hadn't simply moved the bottleneck somewhere else. So my approach is always: baseline → identify bottleneck → optimize → measure again → validate in production.

**43. What exactly did you tune in the connection pool?**
The important parameters include maximum pool size, minimum idle connections, connection timeout and connection lifetime or idle settings. But I wouldn't simply increase the pool size because more connections don't automatically mean better performance — the database has a finite capacity, and too many connections can actually increase contention. I would look at application concurrency, database connection utilization, query latency and the number of application instances before deciding the appropriate pool configuration.

---

## Interview Mental Model

```
                 PAYMENT
                    │
                    ▼
          ┌──────────────────┐
          │ Payment Events   │
          │     Journey      │
          └────────┬─────────┘
                   │
             Kafka / gRPC
                   │
                   ▼
          ┌──────────────────┐
          │    Exposure      │
          │     Journey      │
          └────────┬─────────┘
                   │
          ┌────────┼────────┐
          ▼        ▼        ▼
      Matching  Calculate  Refresh
          │        │        │
          └────────┼────────┘
                   ▼
             Account Level
                   │
                   ▼
           Client / Customer
                   │
                   ▼
          Authorization /
            Risk Decision
```
