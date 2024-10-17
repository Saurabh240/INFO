Functional Requirement
* Feed Publishing
* Feed retrieval
* Notification, Analytics service etc.

Non Functional Requirement
* High Availability
* Minimal Latency

Estimates
* 20 million DAU (Daily Active Users)
* 5 * 20 million = 100 million tweets / day

100 million / (60 secs * 60 mins * 24 hrs) = 1000 tweets per second

Assume 100:1 read to write ratio = 100,000 tweets read requests per second

* 100 million * 100 bytes = 10 GB / day

* 10 GB * 365 days * 10 years = 36.5 TB

Data Model
USERS
TWEETS
FOLLOWERS
FEEDS
FEEDS_TWEETS

API Design

REST API

1. POST /tweet
* params: content and authtoken

2. GET /feed
* params: auth_token

GraphQL
* Query Language
* Single Endpoint
* Queries and Mutations
* Client specifies the shape of the request
* Help solve the over fetching of the data

query feed($userID: UUID) {
    feeds(userID: $userID) {
        feeds_tweets {
            tweet_id
        }
    }
}

Feed Publishing
 fanout
 fanout is the process of distributing a message or content update to all the subscribers or followers of a particular feed

 * two main strategies:
 1. fanout on write (push model)
 2. fanout on read (pull model)

 Fanout on write (push model)

 Pros
 * reads faster as feed is pre computed at write
 * feed generated in real time

 Cons
 * pre computing for inactive user is a waste of resource
 * Hotkey Problem generating news feed for a user with lots of followers

 Fanout on read (pull model)

 Pros
 * Don't waste resources for inactive users.
 * Hotkey Problem is avoided

 Cons
 * Reads slower

 Hybrid Approach

 * Use a combination of the push and pull models

 * Use the push model for the majority of users 
    * When most people post the news feed caches of their followers are updated

* Use the pull model for celebrities (People with large followings)

* To avoid overloading the system, force each user to get the latest posts from celebrities they follow on read

 Additional discussion points
 * Keep service stateless
 * Have multiple instances of each service
 * Spreading services across multiple data centres
 * Many read replica to handle large read load
 * Cache as much data as possible
 * Monitor usage metrics in order to predict QPS
 * Store content in CDN
 
  

