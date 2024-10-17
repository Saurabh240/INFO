========
#### **Problem Statement**: Design a service like Bit.ly that takes a long URL and returns a shorter one, which redirects users to the original URL.

#### **Key Requirements**:
1. **Shorten URLs**: Convert a long URL into a unique short URL.
2. **Redirect**: When the short URL is clicked, redirect users to the original URL.
3. **Scalability**: Handle millions of URLs and high traffic.
4. **Availability**: Ensure the service is always available.

#### **Components**:
- **API Layer**: To accept long URLs and return short URLs.
- **Database**: Store the mapping between short and long URLs. Choose a key-value store (e.g., Redis, DynamoDB) for fast lookups.
- **Hashing**: Generate unique short URLs.
  - **Example**: Use a Base62-encoded hash (letters + digits) for the shortened URL.
- **Load Balancer**: Distribute traffic across multiple servers.
- **Caching**: Cache frequently requested short URLs to reduce database load.
- **Expiration (Optional)**: Expire unused URLs after a certain time period.

#### **Solution**:
1. **Short URL Generation**:
   - Use a **Base62 encoding** technique (comprising letters and numbers) to generate a short unique key.
   - Example: Convert URL "https://www.example.com/article/12345" to "abc123".

2. **Database Schema**:
   - **Table: URLMappings**
     - Columns: ShortURL, LongURL, ExpirationDate (optional).
     - Example: Store mappings such as "abc123" -> "https://www.example.com/article/12345".

3. **Load Balancer**: Distribute the requests for short URL lookups across multiple application servers.

4. **Cache**: Frequently accessed short URLs are cached in Redis to speed up response time for popular links.
======

Functional Requirements

1. Given a long url create a short url
2. Given a short url redirect to a long url
3. Expiry of the URL

Non Functional Requirement
1. Very low latency
2. Very High Availability
3. URL should not be predictable

Read Heavy
100:1
1 Million URL month create
100 Million URL month read

100000000
--------------  = 50 query per second
30 * 24 * 3600

1 million * 12 * 10
total url in 10 yrs
1 million * 12 * 10 * 500 (if size of one url is 500 byte)

60 GB total
RAM 1 day TTL

50 qps * 24 * 3600 = 5 million qpd

25% of 5 million to cache -> 1.25 million 
1.25 million * 500 bytes = 1GB cache

API Design

REST API
1. POST: /create-url
  * Params: long-url
  * Status code: 201 Created

(String longUrl, long userId, String apiKey, Date expiry)


2. GET: /{short-url}
  * Status Code: 301 Permanent Redirect

Schema 
long-url: String
short-url: String
expireDate: timestamp


Key Question: How long should the URL be?
1. Need to know the scale of application?
* Example: 1000 URLs generated per second

1000 * 60 * 60 * 24 * 365  = 31.5 billion urls created each year

10 to 1 read to write requests means 300 billion reads per year

What characters we can use
Alphanumeric
* a-z -> 26
* A-Z -> 26
* 0-9 -> 10
Total 62 characters

unique short urls

if 1 char  -> 1^62 -> 1


if 6 char -> 56 billion
if 7 char -> 3.5 trillion

62^n > 1000000 * 12 * 10


Client -> Load Balancer -> Webserver -> Database

Zookeeper to get the range

NoSQL database like MongoDB or Cassandra

Can use Distributed Cache like Redis

