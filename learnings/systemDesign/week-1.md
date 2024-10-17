### **Day 1-3: Key Concepts**

#### **Day 1: Scalability**
- **Vertical Scaling (Scaling Up)**: Increasing the power of an individual server (e.g., adding more RAM, CPU).
  - **Example**: Upgrading a database server to handle more queries per second.
  - **Advantages**: Simpler to implement, consistent performance.
  - **Disadvantages**: Hardware limits, single point of failure.

- **Horizontal Scaling (Scaling Out)**: Adding more servers to distribute the load.
  - **Example**: Using multiple web servers behind a load balancer to serve increasing traffic.
  - **Advantages**: Better fault tolerance, theoretically limitless scaling.
  - **Disadvantages**: Complex to manage, requires distributed systems knowledge.

- **Key Considerations**: 
  - When to scale vertically vs. horizontally.
  - Trade-offs between cost, complexity, and performance.

#### **Day 2: Load Balancing**
- **Definition**: A technique to distribute incoming traffic across multiple servers to ensure availability and reliability.
- **Types of Load Balancers**:
  - **DNS-based Load Balancer**: Distributes requests at the DNS level by resolving to multiple IP addresses.
    - **Example**: AWS Route 53 routing traffic between multiple EC2 instances.
  - **Application Layer (L7) Load Balancer**: Distributes traffic based on application-level data such as HTTP headers, cookies.
    - **Example**: AWS Application Load Balancer (ALB) for routing traffic to microservices based on URL paths.

- **Techniques**:
  - **Round-robin**: Requests are distributed sequentially.
  - **Least Connections**: Traffic goes to the server with the fewest active connections.
  - **IP Hashing**: Routes requests based on the client's IP to ensure they go to the same server.

#### **Day 3: Caching**
- **Definition**: Storing frequently accessed data in a temporary storage (cache) to improve performance and reduce load on the backend.
- **Where to Cache**:
  - **Client-side Cache**: Browser caching static assets like images and CSS.
  - **Server-side Cache**: Database query results stored in Redis or Memcached to reduce the load on the database.
  - **CDN (Content Delivery Network)**: Caches static content close to users to reduce latency.

- **Cache Invalidation Strategies**:
  - **Time-to-Live (TTL)**: Set an expiration time for cache entries.
    - **Example**: Cache an API response for 10 minutes.
  - **Write-through Cache**: Data is written to the cache and the database simultaneously.
  - **Write-back Cache**: Data is written to the cache, and after some time, the data is written back to the database.

- **Example**: Caching popular product details in Redis to reduce database reads.

### **Day 5-7: Database Fundamentals**

#### **Day 5: RDBMS vs. NoSQL**
- **RDBMS (Relational Database)**:
  - **Definition**: Stores data in tables with relationships.
  - **Example**: MySQL, PostgreSQL.
  - **Advantages**: Strong consistency, complex queries using SQL.
  - **Disadvantages**: Less flexible for scaling, complex joins can be slow.

- **NoSQL (Non-relational Database)**:
  - **Definition**: Stores data without a fixed schema. Types include key-value stores, document stores, column stores, and graph databases.
  - **Example**: MongoDB (document store), DynamoDB (key-value store), Cassandra (column store).
  - **Advantages**: Horizontal scalability, flexible schema.
  - **Disadvantages**: Eventual consistency (in some cases), more complex query patterns.

#### **Day 6: Database Sharding**
- **Definition**: Splitting large datasets into smaller, more manageable pieces (shards) across different database instances.
- **Why Shard?**: To improve performance and handle large datasets by distributing the load.
- **Sharding Strategies**:
  - **Range-based Sharding**: Split data based on a range of values (e.g., user IDs 1-1000 go to shard 1).
  - **Hash-based Sharding**: Use a hash function to distribute data evenly across shards.
  
- **Example**: A social media app shards user data based on user IDs.

#### **Day 7: Database Replication**
- **Definition**: Copying data from one database server (master) to one or more backup servers (replicas).
- **Types of Replication**:
  - **Master-Slave Replication**: Write operations go to the master, and read operations are distributed across slaves.
  - **Leaderless Replication**: No single master node; write operations can go to any node (e.g., in Cassandra).
  - **Multi-master Replication**: Multiple servers can handle both reads and writes, useful for globally distributed applications.

- **Example**: Use master-slave replication in MySQL to handle read-heavy traffic.

---