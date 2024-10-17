https://github.com/ashishps1/awesome-low-level-design

System Design Involves Figuring Out
* What are the requirements of the System
* Who are the users and How many ?
* What components do we need in our Design ?
* How these components should be Organized ?
* How to make the System Scalable ?
* How to make the System Reliable ?
* How to make the System Easy to Maintain ?

### Key Concepts
1. Scalability :-  How well a system can handle more users and data without slowing down.
  1.1 Vertical Scaling
  1.2 Horizontal Scaling

2. Performance:-  How fast your system works ?
   Measured by Latency and ThroughPut
   2.1 Latency:- Time it takes for a single task
   2.2 Throughput:- How many tasks your system can handle in a certain time?

3. Availability:- Your system is up and running when users need it without any significant downtime.

4. Reliability:- Your system is doing what it's supposed to do even if things go wrong.
    Can be made reliable using Replication , Redundancy and Failover Mechanism

5. Consistency:- All users see the same data at the same time no matter which part of the system 
    they interact with.

6. CAP Theorem:- The CAP Theorem, also known as Brewer's Theorem, is a fundamental concept in distributed systems. It states that a distributed system can only provide two out of the following three guarantees at the same time:

6.1 Consistency (C): Every read receives the most recent write. This means all nodes in the system return the same data at any point in time.

6.2 Availability (A): Every request (read or write) receives a response, even if some nodes are down. The system remains operational 100% of the time.

6.3 Partition Tolerance (P): The system continues to operate despite network partitions (communication breakdown between nodes).

Consistency:
A system is consistent if, after a write completes, all future reads will return that value.
Example: In a banking system, if you transfer money from one account to another, the system should immediately reflect the updated balances for both accounts, regardless of which node you query.

Availability:
A system is available if it guarantees that every request receives a response (it could be successful or a failure), without guarantee of the latest data.
Example: If one server in a cluster goes down, the system should still respond to requests by serving data from another server.

Partition Tolerance:
A system is partition-tolerant if it continues to function even if the communication between parts of the system (nodes) is lost.
Example: In a distributed system with nodes in different regions, if the connection between the regions is disrupted, the system can still continue to operate independently.

7. Data Storage and Retrieval
Choosing the Right Database , Designing the Database Schema and using Techologies like Partitioning , Sharding and Replication

8. ACID Transactions
8.1 Atomicity
8.2 Consistency
8.3 Isolation
8.4 Durability

9. Consistent Hashing
Used to spread data over a group of servers

10. Rate Limiting
Controls the rate at which clients can make requests to a system
It prevents abuse , protect against ddos attacks , ensure fair usage of resources

11. Network and Communication
How different parts of a system communicate?
it involves Network Protocols, APIs, Message Queues and Event Driven Architecture

12. Security and Privacy
Putting in place methods to keep important data safe and stop unwanted access.
it involves Authentication , Authorization and Encryption

### Building Blocks
1. Application Servers:-  Computers that handles the business logic and processing required by the application.

2. Load Balancers:- Distribute incoming requests to different servers to ensure no single server gets overwhelmed

3. Databases:- SQL and NoSQL 

4. Caching:- Store Frequently accessed data in a fast access storage to reduce the load on primary data source and improve response times.

5. Message Queues:- Enables asynchronous communication between system components.

6. Storage:- Store and retrive data such as files, images or videos.
   can use local file systems, Distributed file systems and Object Storage Systems

7. Proxy Servers:- Act as an intermediary btw client and app server
  used for load balancing, caching, security and content filtering

8. CDN:- group of server spread across different locations



### Commonly Asked Questions:- 

1. Design a URL shortning service like TinyURL
2. Design a Social Media Platform like Twitter/Instagram
3. Design a Chat Application like Whatsapp/Slack
4. Design a Web Crawler
5. Design a Video Streaming service like Youtube/Netflix
6. Design an ecommerce platform like Amazon
7. Design a ride sharing Service like Uber/Lyft
8. Design a Notification System
9. Design a Key-Value Store like Redis
10. Design a Scalable Logging and Monitoring System