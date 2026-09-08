# Capgemini L2 Interview — Questions & Answers

## 1. Tell me about yourself

*Keep it different from your first technical round.*

> "I have around 8 years of experience in software development, primarily focused on Java, Spring Boot, REST APIs and microservices. I've worked with databases such as PostgreSQL and MySQL, messaging technologies like Kafka, and cloud and container technologies including AWS, Docker and Kubernetes.
>
> In my current role, I'm involved in developing and enhancing backend services, designing APIs, troubleshooting production issues and working with different teams to deliver features. Over the years I've also gained experience in performance optimization, application modernization and cloud-based deployments.
>
> At this stage, I'm looking for a role where I can take greater ownership of technical solutions and work on larger-scale Java, microservices and AWS-based systems, which is why this opportunity interests me."

---

## 2. Explain your current project

*This is probably the #1 question.*

Use this structure:

```
Business problem
      ↓
Architecture
      ↓
Technologies
      ↓
Your responsibility
      ↓
Challenges
      ↓
Impact
```

Don't spend 5 minutes describing the business.

**Example**

> "I'm currently working on an enterprise application based on Spring Boot microservices. The services expose REST APIs and communicate synchronously through REST and asynchronously through Kafka where required. PostgreSQL is used for transactional data, and the applications are containerized and deployed in a cloud environment.
>
> My responsibility is mainly around backend development, API design, business logic, database integration, production support and troubleshooting. I also participate in design discussions and work with other teams when there are dependencies between services.
>
> One area where I've contributed significantly is troubleshooting production issues and improving API performance and reliability."

Then stop. Let them ask follow-ups.

---

## 3. What exactly is your contribution?

*This is very important at L2.*

Don't say: *"We developed..."*

Instead say: *"My individual contribution was..."*

**Example**

> "My primary responsibility was the backend implementation. I designed and developed REST APIs, implemented business logic, handled database interactions through JPA/Hibernate, and worked on Kafka-based integrations. I was also involved in code reviews, production troubleshooting and coordinating with dependent teams."

---

## 4. Tell me about a challenging production issue

*Use STAR.*

**Situation**
> "We had an issue where one of our APIs started responding significantly slower than normal."

**Task**
> "I was responsible for identifying the root cause and restoring normal performance."

**Action**
> "I checked application logs and metrics first, then analyzed database performance and identified an inefficient query. I reviewed the execution plan, optimized the query and added/modified the appropriate index. I then validated the change in a lower environment before deploying it."

**Result**
> "The API response time improved significantly and the issue was resolved without impacting the business flow."

**L2 follow-up:** *"How did you know the database was the problem?"*

> "I correlated the application latency with database execution time and checked database metrics and logs. The slow query was consistently taking significantly longer than the rest of the API processing."

Don't invent specific numbers unless you actually have them.

---

## 5. One microservice gets 10x traffic. What would you do?

> "First I'd determine whether the bottleneck is CPU, memory, database, downstream services or network using metrics and logs. If the application instances are the bottleneck, I'd horizontally scale that particular microservice behind an ALB rather than scaling all services. I'd configure autoscaling based on appropriate metrics and verify that the database and downstream services can handle the additional load.
>
> If the workload contains operations that don't require synchronous processing, I'd consider Kafka or SQS. For frequently accessed data, caching could also reduce database load. I'd also consider rate limiting if the traffic is unexpected or abusive."

This is a very good L2 answer.

---

## 6. API suddenly becomes slow. How do you troubleshoot?

Use this sequence:

```
Client
  ↓
ALB/API Gateway
  ↓
Application
  ↓
Database
  ↓
External service
```

> "I would first identify where the latency is occurring rather than immediately changing the code. I'd check application metrics, logs, request latency, CPU and memory, database performance, connection pool utilization and downstream service latency. I'd compare the current behavior with a known healthy baseline. Once I identify the bottleneck, I'd fix the root cause and validate the improvement."

This shows production maturity.

---

## 7. What if a downstream microservice is unavailable?

> "I would prevent the failure from cascading to other services. I'd configure appropriate connection and read timeouts, use retries with exponential backoff where retrying is safe, and use a circuit breaker to stop continuously calling an unhealthy service. Depending on the business requirement, we could return a fallback response or process the request asynchronously through Kafka/SQS."

Add:

> "I would be careful with retries for non-idempotent operations because retries can potentially create duplicate transactions."

That's a nice senior-level point.

---

## 8. How do you handle production incidents?

*A Process Lead may particularly like this question.*

> "First, I focus on impact assessment and stabilization rather than immediately trying to implement a permanent fix. I check monitoring, logs and recent deployments to identify the likely cause. If necessary, I rollback or apply a safe mitigation. Once the system is stable, I identify the root cause, implement the permanent fix, test it and document the incident. For significant incidents, I would also conduct an RCA and identify preventive actions."

Excellent terms to use: **Mitigation → Root Cause → Permanent Fix → RCA → Preventive Action**

---

## 9. How do you prioritize multiple production issues?

> "I prioritize primarily based on business impact, severity and number of users affected. A production outage or critical transaction failure takes priority over a minor functional issue. I also consider SLAs and dependencies. If multiple issues are critical, I communicate the priorities clearly and involve the appropriate stakeholders rather than trying to handle everything individually."

---

## 10. How do you handle a tight deadline?

> "First I clarify the actual business priority and scope. Then I break the work into smaller deliverables, identify dependencies and risks, and communicate early if there is a potential impact to the timeline. I try to protect critical quality areas such as testing and security rather than simply rushing the implementation."

---

## 11. What if you disagree with your architect/lead?

*Very common managerial question.*

> "I would first understand the reasoning behind their approach and then present my concern with technical evidence, such as performance, maintainability or operational impact. If we still disagree, I'd look for a data-driven way to validate the options, such as a proof of concept. Ultimately, once the decision is made, I'd support the team's decision and focus on successful implementation."

Don't say: *"I will convince them that my approach is correct."*

---

## 12. Tell me about a conflict with a teammate

> "In one situation, we had different opinions about an implementation approach. Instead of making it personal, we discussed the trade-offs around maintainability, performance and delivery timelines. We evaluated the options and agreed on the approach that best matched the project requirements. The important thing for me was keeping the discussion focused on the solution rather than the individual."

---

## 13. Have you mentored junior developers?

*If yes:*

> "Yes. I've helped junior developers understand the codebase, reviewed their implementations and guided them on Java, Spring Boot, debugging and development practices. I try not just to provide the solution but explain the reasoning so they can handle similar problems independently."

---

## 14. How do you ensure code quality?

> "I follow practices such as clean and maintainable code, appropriate design principles, unit testing, code reviews and static analysis. Before merging, I validate functional scenarios and edge cases. For critical changes, I also consider performance, backward compatibility and failure scenarios."

Mention tools you genuinely use: **JUnit, Mockito, SonarQube, Git, CI/CD**

---

## 15. How do you handle a requirement that isn't clear?

> "I don't start implementation based on assumptions. I clarify the business requirement, identify expected inputs and outputs and confirm edge cases. If the requirement involves multiple teams, I document the agreed behavior so everyone has the same understanding."

This is particularly good for a Process Lead.

---

## 16. How do you communicate technical problems to a client?

> "I avoid unnecessary technical terminology initially. I explain the business impact, current status, what we're doing to resolve it and the expected timeline. If the client needs technical details, I provide those separately."

**Example**

Instead of: *"The connection pool is exhausted."*

Say: *"The service is currently unable to process requests because its database connections are saturated. We're increasing capacity and investigating why the connections aren't being released as expected."*

That's strong client communication.

---

## 17. What if the client asks for something urgently?

> "I'd first understand the actual business priority and impact. Then I'd assess the technical effort, dependencies and risk. If it can be safely delivered within the requested timeline, I'd prioritize it. If not, I'd clearly communicate the constraints and propose alternatives such as delivering a smaller MVP first."

---

## 18. How do you handle changing requirements?

> "I first assess the impact on existing functionality, design, effort and timeline. I discuss the impact with the relevant stakeholders and then incorporate the change after the requirement is confirmed. I try to keep the implementation flexible enough to accommodate reasonable changes without introducing unnecessary complexity."

---

## 19. Why are you looking for a change?

> "I'm looking for the next step in my career where I can take broader ownership and work on larger-scale cloud-native systems. My current experience has given me a strong foundation in Java, Spring Boot and microservices, and now I want to expand that further through challenging projects involving AWS, architecture and greater technical responsibility."

Don't mention salary as your primary reason.

---

## 20. Why Capgemini?

> "I'm interested in Capgemini because of its global client base and the scale of its technology and cloud transformation projects. The role also aligns closely with my experience in Java, Spring Boot, microservices and AWS. I'm looking for an environment where I can contribute technically while also taking greater ownership, and this opportunity seems aligned with that."

---

## 21. Why should we select you?

*This is your closing pitch:*

> "I bring around 8 years of hands-on experience with Java, Spring Boot, microservices and related cloud technologies. I can contribute not only to development but also to troubleshooting, production support, design discussions and technical ownership. I've worked in enterprise environments where reliability, communication and delivery are important, so I believe I can contribute effectively to both the technical and delivery aspects of the team."

---

## 22. Are you comfortable working with clients and global teams?

> "Yes. I'm comfortable working with distributed teams and communicating with different stakeholders. I'm also comfortable explaining technical issues to non-technical stakeholders and coordinating with dependent teams to resolve issues."

---

## 23. Where do you see yourself in 3–5 years?

> "I'd like to grow toward a technical leadership role where I remain hands-on while taking ownership of larger components and architecture. I also want to mentor other engineers and contribute to technical decisions and delivery."

---

## 24. Salary question

*You've already told them ₹28 LPA, and they've explained the 9% variable.*

**If they ask again:**

> "As discussed earlier, I'm targeting around ₹28 LPA considering my experience and the responsibilities of the Senior Java and AWS role. I'm comfortable with the 9% variable structure you explained, assuming it is guaranteed as discussed."

**If they ask whether you're flexible:**

> "I'm open to discussing the overall structure, but my preference would be to stay around ₹28 LPA."

Don't suddenly reduce yourself to ₹25–26L unless you have a specific reason.

---

## 25. Notice period

**If your official notice is still 90 days:**

> "My official notice period is 90 days. However, I'm willing to discuss an early release with my current organization and I'll make every reasonable effort to join earlier if required."

**If they say:** *"We need you sooner."*

> "I understand. I'll discuss the possibility of an early release and see what can be worked out."

Don't promise a date you cannot guarantee.
