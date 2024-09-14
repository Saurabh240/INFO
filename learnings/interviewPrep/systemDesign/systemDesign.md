#### The Bigtech System Design Interview Bootcamp

### reference https://ibm-learning.udemy.com/course/the-bigtech-system-design-interview-bootcamp/learn/lecture/36083568#notes

#### TODO List system design

#### Step 1 -> Requirement Analysis

#### Step 2 -> Capacity Estimation

3 KPIs to estimate the capacity
1-> Request => write 1 RPS , read 5 RPS
2-> Bandwidth => 10kb/sec
3-> Storage => 4000 GB for next 5 years

Given

- 20k daily active users
- 5 todo items per day for one user
- Read-write ratio 5:1
- Each todo item has 20kb

Writes
20k DAU x 5 = 100l requests /day

100k/10^5 = 1 request per second

Reads
1 RPS x 5 = 5 Requests per second

1 write RPS + 5 read RPS = 6 total RPS

6 RPS \* 20kb = 120 kb per second

1 write RPS \* 20 kb = 20 kb

20 kb x 2 x 10^8
= 4 x 10^9/ 10^6 GB
= 4000 GB

#### Step 3 Data Modeling

free drawing tool https://excalidraw.com/

#### Step 4 API Design

#### Step 5 System Design

Scaling Services
Multiple Instances

- Increase the load a systemcan handle
- Layer Service symbols to represent multiple instances

Load Balancer

- Distributes Traffic Evenly

#### Step 6 Design Discussion

###### URL Shortner Systems

Latency is the amount of time in ms it takes a single message to be delivered

- The concept applies where data is being requested and transferred

##### Tiny URL mock interview

##### System Analysis

Analyse the system and set three core assumptions

1. Is it read or write heavy ?
2. Distributed System or single server ?
3. Whats more important data consistency or availability ?

##### Functional Requirements

Tasks

1. Define whats the core feature of tinyURL
2. Put together a set of support features.

Core Features

- The service should generate a unique and short link for any given regular url.
- user click a short link and get redirect to the original link

Support Features

- Link History , registered users should see all links they ever created.
- Link Expiration , links should expire after a default timestamp of 5 years

##### Non Functional Requirement

#### Qualitative NFRs

- High Availability
- Scalable
- Low Latency

#### Expected Scale

- What are the qs you would ask the interviewer to capture the scale of tinyURL ?

1. How many Daily Active User does TinyURL has ?
2. Are there events that lead to spike in traffic ?
3. How often do users interact with the application per day ?
4. What will be the read / write ratio ?
5. How long are the urls on average that are supposed to be shortened ?
6. Whats the replication factor ?

#### Capacity Estimation

Calculate Requests
Given

- 100 million DAU
- 1 click per user per day
- 10:1 read / write ratio

Reads
1 click per day x 100000000 DAU =
10^8 reads per day=
10^8 / 10^5 =
10^3 =
1000 read request per sec

Writes
1000 read RPS / 10 =
100 write RPS

Estimates : read 1000 RPS , write 100 RPS

Read Bandwidth

1000 RPS x 200 Byte =
10^3 x 2 x 10^2 =
2 x 10^5 / 10^3 kB/s =
200 kB/s

Write Bandwidth
200 / 10 = 20 kB/s

Storage for 5 years
20 x 2 x 10^8 KB
4 x 10^9 / 10^6 GB
4000 GB
4 TB for 5 years

#### ACID

A -> Atomicity (Something that cannot be broken down into smaller parts)
C -> Consistency (All Data points within the database must align in order to properly read and accepted)
I -> Isolation (Concurrently executing transactions are isolated from each other)
D -> Durability

#### BASE

Basically Available -> Guarantees Its always possible to read and write data even though it but might not be consistent
Soft State -> This is a mechanism to create consistency over time
Eventual Consistency -> it allows for high availability and scalability of system
