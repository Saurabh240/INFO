1. Explain about your project.
Currently I'm working on a banking platform related to payment authorization and risk assessment.
My current project has two important journeys that I've worked with — the Payment Events Journey and the Exposure Journey.
At a high level, when a payment event comes into the platform, the Payment Events Journey ingests and validates the event, enriches it with required metadata and routes it through the appropriate risk-processing path. It's primarily an event-driven architecture using Kafka, with gRPC used for some synchronous communication between components.
The other major journey is the Exposure Journey. Its purpose is to calculate and maintain the One Exposure, which represents the amount a borrower owes at a particular point in time. Exposure is calculated and reconciled using inputs such as GAR transaction data, GAR summary data, authorization records, reversals, adjustments and payment events.
The Exposure Journey has different processors for transaction matching, exposure calculation, refresh, roll-up and reconciliation. At a high level, data comes through the ingress layer, is processed by the appropriate processors, persisted through the data layer and then distributed to downstream systems.
Technically, we're using Java and Spring Boot, Kafka and Google Pub/Sub for event-driven processing, gRPC for service communication, Cassandra/PostgreSQL and RDM for persistence, and Kubernetes for deployment.
My role is primarily on the backend side — working on Java/Spring Boot services, event processing, APIs, troubleshooting, testing and understanding the end-to-end flow across these journeys.

2. Explain the architecture
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

3. What is Exposure?
Exposure represents the amount that a borrower owes at a particular point in time.
The challenge is that this amount isn't necessarily static. Different events can change the exposure — for example, transactions, payment events, authorization records, reversals or adjustments.
So the Exposure Journey continuously processes these inputs and maintains an up-to-date exposure at different levels, starting from transaction-level processing and eventually aggregating it to account, client and customer levels.

4. Explain Payment Events Journey
The Payment Events Journey is responsible for processing payment-related events as they enter the platform.
The flow starts with an ingress component, where events are received and validated. The event is then handled and enriched with the metadata required for downstream processing.
Based on the available information, the risk-routing layer determines the appropriate processing path. The processor then performs the core business processing and produces the required downstream events.
Kafka is the primary event-driven communication mechanism, while gRPC is used where synchronous service-to-service communication is required.
The key benefit of this architecture is that ingestion, routing and processing are separated, so individual components can evolve and scale independently.

5. Explain Exposure Journey
The Exposure Journey is more focused on calculating, updating and reconciling exposure.
It receives different types of inputs such as GAR transaction and summary data, authorization records, reversals, adjustments and payment events.
Depending on the type of input, different processors perform specific responsibilities. For example, the transaction-matching processor handles transaction-level matching, the exposure processors calculate or refresh exposure, and the roll-up processor aggregates exposure to higher levels such as client and customer.
There are also processors for reconciliation, regulatory relationships, manual re-aging and suspense aging.
So conceptually, I look at the journey as:
ingestion → processing/matching → exposure calculation → aggregation → persistence/downstream distribution.

6. What exactly did YOU work on?
My involvement has been primarily on the backend engineering side across these journeys. I've worked with the Java/Spring Boot services, event-processing flow, Kafka-based communication, debugging and testing. I also need to understand the upstream and downstream components because changes in one processor can affect the overall journey.
From an ownership perspective, I focus on understanding the business flow first and then the specific component I'm changing, including its inputs, processing logic, persistence and downstream events.

7. Why did you use Kafka?
Kafka fits well because the system is processing a continuous flow of business events and different downstream components need to consume and process those events independently.
It provides decoupling between producers and consumers, allows consumers to scale independently and provides durable event storage so that events can be replayed when required.
It also fits well with our processing model because different stages of the journey can consume events, perform their processing and publish subsequent events.

8. Why Kafka AND Google Pub/Sub?
They serve messaging requirements within the overall architecture, but the exact choice depends on the integration and platform boundary.
Kafka is used heavily for our event-driven processing and streaming flows, while Google Pub/Sub provides managed messaging capabilities within the GCP ecosystem.
I wouldn't introduce both simply for the same purpose. The choice should depend on the existing platform architecture, operational requirements and which systems need to integrate.

9. Why do you need both transaction-level and account-level exposure?
They represent different levels of aggregation.
Transaction-level processing allows us to identify and reconcile individual financial events. Account-level exposure represents the resulting financial position of an account.
Once account-level exposure is calculated, it can then be aggregated further to client or customer level depending on the business requirement.
Maintaining these levels also allows the system to support both detailed reconciliation and higher-level risk or authorization decisions.

10. What happens when an event fails?
The first thing is to distinguish between a transient technical failure and a permanent business or data failure.
For transient failures, we can retry processing. For messages that repeatedly fail, we need an error or DLQ mechanism so that one problematic event doesn't block the overall processing flow.
We also need sufficient observability to identify the failed event, understand the reason and replay it after the underlying issue is resolved.
Since these are financial events, idempotency is particularly important because replaying an event must not accidentally apply the same business operation twice.

11. How do you make event processing idempotent?
The consumer should be able to process the same event more than once without producing an incorrect business result.
Typically I'd use a unique event ID or business transaction ID and maintain a way of identifying whether that event has already been processed. Database constraints or a processed-event mechanism can help enforce this.
This is particularly important because a consumer can fail after completing the business operation but before acknowledging the Kafka message, causing the message to be delivered again.

12. What happens if the same payment event arrives twice?
I wouldn't assume that the event will arrive only once. The consumer should detect duplicates using a unique event or transaction identifier.
If the event has already been successfully processed, we should avoid applying the business operation again and acknowledge the duplicate appropriately.
This is why idempotency is a key design consideration in our event-driven processing.

13. Why use gRPC?
gRPC is useful for efficient service-to-service communication, particularly when we control both sides of the communication.
It uses Protocol Buffers for strongly typed contracts and generally has lower serialization overhead than JSON-based REST communication.
In our architecture, Kafka is useful for asynchronous event-driven communication, while gRPC can be used where one service needs a direct synchronous interaction with another service.

14. Why Cassandra/PostgreSQL/RDM? Why multiple databases?
The different stores serve different requirements in the platform.
Relational storage is appropriate where we need structured data and transactional capabilities. Cassandra is designed for high-scale distributed access patterns where we need predictable performance and horizontal scalability.
RDM is also part of the platform's persistence/data-service architecture.
I wouldn't choose a database simply because it's available. The important factor is the access pattern, consistency requirements, scale and operational characteristics of the workload.

15. How do you troubleshoot a failed Kafka processing flow?
I'd trace the event from the producer to the consumer.
First I'd check whether the event was actually produced successfully.
Then I'd check the Kafka topic and partition, consumer group status and consumer lag.
Next I'd check application logs using the event or correlation ID and identify whether processing failed because of validation, business logic, database access or a downstream dependency.
If the event is in a DLQ, I'd inspect the failure reason and determine whether it can be safely replayed.
Finally, after fixing the root cause, I'd replay the affected events and verify that the downstream state is correct.

16. How do you test such an event-driven system?
I look at testing at multiple levels.
Unit tests cover individual business components and processing logic.
Integration tests validate interactions between components such as Kafka and databases. Our setup also supports Testcontainers, which allows us to test against realistic infrastructure dependencies.
Then we have end-to-end or journey-level validation to ensure that an event entering the system produces the expected downstream state.
For critical event processing, I'd also test failure scenarios, duplicate events, retries and replay behaviour rather than only the happy path.

17. What is the biggest challenge in this architecture?
The biggest challenge with an event-driven distributed architecture is that troubleshooting and maintaining consistency become more complex.
A single business event can pass through multiple services and asynchronous stages, so when something goes wrong, it's not always immediately obvious where the problem occurred.
That's why correlation IDs, structured logging, tracing, metrics, consumer-lag monitoring and clear retry/DLQ mechanisms are important.
Another important consideration is idempotency because events may be retried or replayed.

18. How I'd explain the entire project in 30 seconds
It's a banking authorization and risk platform built around event-driven processing. The Payment Events Journey ingests, validates and routes payment events through risk-processing components, while the Exposure Journey calculates and maintains the borrower's current exposure using transaction, authorization, payment and adjustment data. The architecture is primarily Java/Spring Boot with Kafka and gRPC, persistence through RDM and databases such as Cassandra/PostgreSQL, and Kubernetes-based deployment. My role is primarily backend engineering around these event-processing journeys, including development, troubleshooting, testing and understanding the end-to-end transaction flow.

19. Your interview mental model
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
