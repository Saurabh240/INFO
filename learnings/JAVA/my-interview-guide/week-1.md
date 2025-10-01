## 🧠 **DAY 1–2: Core Java**

---

### ✅ 1. **Java 8–21 Feature Deep Dive**

#### 🔹 a. **Streams API (Java 8)**

* Used for processing collections declaratively.
* Key methods: `map`, `filter`, `reduce`, `collect`, `sorted`

**🧪 Practice:**

```java
List<String> names = Arrays.asList("John", "Jane", "Tom", "Jerry");
List<String> filtered = names.stream()
                             .filter(name -> name.startsWith("J"))
                             .sorted()
                             .collect(Collectors.toList());
System.out.println(filtered);
```

**💡 Task:** From a list of employees, filter those with salary > 50k, sort by name, and collect names into a list.
      Solution : 
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1,"Saurabh", 70000 ));
        employeeList.add(new Employee(2,"Gaurav", 50000 ));
        employeeList.add(new Employee(3,"Mohan", 60000 ));
        List<Employee> filteredList = employeeList.stream()
                .filter(e -> e.salary() >= 50000)
                .sorted(Comparator.comparing(Employee::name))
                .toList();
        System.out.println(filteredList);
---

#### 🔹 b. **Optional (Java 8)**

* Avoids `NullPointerException` by explicitly handling nulls.

**🧪 Practice:**

```java
Optional<String> name = Optional.ofNullable(getName());
System.out.println(name.orElse("Default Name"));
```

**💡 Task:** Implement a method `getUserEmail(id)` that returns `Optional<String>`, and log “Email not found” if empty.
 Solution : 
 public class Test {
    public static void main(String[] args) {
        System.out.println(getUserEmail("test@gmail.com").orElse("Email not found"));
    }
    public static Optional<String> getUserEmail(String email) {
        return Optional.ofNullable(email);
    }
}
---

#### 🔹 c. **CompletableFuture (Java 8)**

* For async programming without blocking.

**🧪 Practice:**

```java
CompletableFuture.supplyAsync(() -> "Hello")
                 .thenApply(s -> s + " World")
                 .thenAccept(System.out::println);
```

**💡 Task:** Chain 3 async tasks: Fetch User → Get Orders → Send Email.
 Solution : 
 public class Test {
        public static void main(String[] args) {
            CompletableFuture<Void> workflow = fetchUser()
                    .thenCompose(Test::getOrders)
                    .thenCompose(Test::sendEmail);

            workflow.join();
            System.out.println("Workflow completed successfully.");
        }

        static CompletableFuture<User> fetchUser() {
            return CompletableFuture.supplyAsync(() -> {
                simulateDelay("Fetching user...");
                return new User("Alice");
            });
        }

        static CompletableFuture<List<Order>> getOrders(User user) {
            return CompletableFuture.supplyAsync(() -> {
                simulateDelay("Getting orders for " + user.name);
                return List.of(new Order("O-123"), new Order("O-456"));
            });
        }

        static CompletableFuture<Void> sendEmail(List<Order> orders) {
            return CompletableFuture.runAsync(() -> {
                simulateDelay("Sending email for orders: " + orders.size());
                System.out.println("Email sent for orders: " + orders.size());
            });
        }

        static void simulateDelay(String msg) {
            try {
                System.out.println(msg);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

class User {
    String name;
    User(String name) { this.name = name; }
}

class Order {
    String orderId;
    Order(String orderId) { this.orderId = orderId; }
}
---

#### 🔹 d. **Records (Java 16)**

* Immutable data carriers.

**🧪 Practice:**

```java
record Person(String name, int age) {}
Person p = new Person("Alice", 30);
System.out.println(p.name());
```

**💡 Task:** Replace a standard POJO (User, Product) with a record.

---

#### 🔹 e. **Sealed Classes (Java 17)**

* Restrict class hierarchies.

**🧪 Practice:**

```java
sealed interface Shape permits Circle, Square {}
final class Circle implements Shape {}
final class Square implements Shape {}
```

**💡 Task:** Model a payment system with sealed interface: `PaymentMethod → Card, UPI, Wallet`.

---

#### 🔹 f. **Pattern Matching (Java 17/21)**

* Enhanced `instanceof` and switch matching.

**🧪 Practice:**

```java
if (obj instanceof String s) {
    System.out.println("Length: " + s.length());
}
```

**💡 Task:** Use pattern matching in switch to classify an `Object` as Integer, String, or other.
 Solution : 
 public class Test {
    public static void main(String[] args) {
      classifyObject(4);
      classifyObject("hello");
      classifyObject(12.5);
    }

    static void classifyObject(Object obj) {
        switch (obj) {
            case Integer i -> System.out.println(i);
            case String s -> System.out.println(s);
                default -> System.out.println("Unknown Type");
        }
    }
}
---

### ✅ 2. **Memory Model: Heap, Stack, GC**

#### 🔹 Concepts

* **Stack**: Method call frames, primitive vars.
* **Heap**: Objects, class fields.
* **GC**: Automatically frees heap memory — generational GC, G1, ZGC.

**💡 Task:**
Explain output and memory flow:

```java
public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        t = null;
        System.gc();
    }

    @Override
    protected void finalize() {
        System.out.println("Finalize called");
    }
}
```

---

### ✅ 3. **equals(), hashCode(), compareTo(), comparator**

#### 🔹 a. `equals()` & `hashCode()`

* `hashCode()` is used in hash-based collections
* **Contract**:

  * Equal objects must have same hashCode
  * Override both when using in `HashSet`, `HashMap`

**🧪 Practice:**

```java
class Product {
    int id;
    String name;

    // TODO: Implement equals() and hashCode()
}
```

**💡 Task:** Insert multiple products into a `HashSet`, detect duplicates by id.
 Solution : 

class Product {
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;  // Compare by id only
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Hash by id only
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "'}";
    }
}

public class HashSetDuplicateDetection {
    public static void main(String[] args) {
        Set<Product> products = new HashSet<>();

        Product p1 = new Product(1, "Laptop");
        Product p2 = new Product(2, "Phone");
        Product p3 = new Product(1, "Tablet"); // Same id as p1 → duplicate by id

        System.out.println("Adding p1: " + products.add(p1)); // true
        System.out.println("Adding p2: " + products.add(p2)); // true
        System.out.println("Adding p3: " + products.add(p3)); // false → duplicate id

        System.out.println("\nProducts in HashSet:");
        products.forEach(System.out::println);
    }
}

---

#### 🔹 b. `Comparable` vs `Comparator`

* `Comparable`: natural order (via `compareTo`)
* `Comparator`: custom sort logic

**🧪 Practice:**

```java
class Employee implements Comparable<Employee> {
    int id;
    String name;

    public int compareTo(Employee o) {
        return this.id - o.id;
    }
}
```

**💡 Task:** Sort list of employees by name (custom comparator) and by id (comparable).

---

## 🧩 Practical Coding Set (Combined)

Pick 3–4 to solve in your IDE or on [LeetCode](https://leetcode.com/), [HackerRank](https://www.hackerrank.com/), or [CodingBat](https://codingbat.com/java).

1. Create a `List<Employee>` and:

   * Filter by age > 30
   * Group by department
   * Sort by salary descending
2. Create a `CompletableFuture` chain that:

   * Downloads a file
   * Processes it
   * Saves result to DB
3. Replace existing POJO `Transaction` with Java 16 Record
4. Implement a `Map<ProductCategory, List<Product>>` grouping logic using Streams
5. Create a sealed hierarchy of Notification: `Email`, `SMS`, `Push` with pattern matching switch

========================

## 🧠 **DAY 3–4: Multithreading & Concurrency**

---

### ✅ 1. **Core Thread Concepts**

#### 🔹 a. **Creating Threads (Thread, Runnable, Callable)**

**🧪 Basic Thread:**

```java
Thread t = new Thread(() -> System.out.println("Hello from thread"));
t.start();
```

**🧪 Callable + Future (return result):**

```java
Callable<String> task = () -> "Result";
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<String> future = executor.submit(task);
System.out.println(future.get());
executor.shutdown();
```

**💡 Task:** Write a program using `Callable` that calculates factorial of a number and returns the result.

---

### ✅ 2. **Executor Framework**

* Abstracts thread creation and lifecycle management.

**🧪 Types:**

```java
Executors.newFixedThreadPool(5);
Executors.newCachedThreadPool();
Executors.newSingleThreadExecutor();
```

**💡 Task:**

* Submit 5 tasks to a fixed thread pool that print thread name and sleep for 1 sec.

---

### ✅ 3. **Synchronization**

#### 🔹 a. **`synchronized` keyword**

* Prevents race conditions on shared mutable state.

**🧪 Example:**

```java
public synchronized void increment() {
    count++;
}
```

**💡 Task:** Create a counter class and 1000 threads incrementing the counter. Compare output with and without `synchronized`.

---

#### 🔹 b. **ReentrantLock**

* More flexible than `synchronized` (tryLock, interruptible, fairness).

**🧪 Example:**

```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

**💡 Task:** Rewrite counter example using `ReentrantLock`.

---

### ✅ 4. **ForkJoinPool (Parallelism)**

* Best for divide-and-conquer tasks (splitting recursively).

**🧪 Example:**

```java
class SumTask extends RecursiveTask<Long> {
    long start, end;
    SumTask(long start, long end) { this.start = start; this.end = end; }

    protected Long compute() {
        if (end - start <= 10)
            return LongStream.rangeClosed(start, end).sum();

        long mid = (start + end) / 2;
        SumTask left = new SumTask(start, mid);
        SumTask right = new SumTask(mid + 1, end);
        left.fork();
        return right.compute() + left.join();
    }
}
```

**💡 Task:** Implement ForkJoin to calculate sum of 1 to 1 million.

---

### ✅ 5. **Virtual Threads (Java 21)**

* Lightweight threads for concurrent I/O operations.
* Part of **Project Loom**.

**🧪 Example (Java 21):**

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 1000).forEach(i ->
        executor.submit(() -> {
            Thread.sleep(1000);
            System.out.println("Task " + i);
            return null;
        })
    );
}
```

**💡 Task:** Convert a traditional fixed thread pool example into virtual thread executor. Compare memory and time usage.

---

### 🧩 Practical Coding Challenges

| Problem                               | Description                                                     |
| ------------------------------------- | --------------------------------------------------------------- |
| **Thread-safe Bank Account**          | Simulate 2 threads depositing and withdrawing from same account |
| **Producer-Consumer (BlockingQueue)** | Use `ArrayBlockingQueue` to coordinate threads                  |
| **Parallel Word Count**               | Count words in 10 files using thread pool                       |
| **ForkJoin Image Blur**               | Simulate image processing with divide-and-conquer               |
| **Virtual Threads Benchmark**         | Benchmark 10,000 threads: virtual vs traditional                |


=========================

## 🧠 **Day 5: Collections Framework Deep Dive**

---

### ✅ 1. **HashMap Internal Working**

#### 🔹 a. **Basic Mechanics**

* **Structure**: Array of buckets (`Node<K,V>[] table`)
* **Hashing**: Uses `key.hashCode()` and applies hash spreading
* **Collision Handling**: Uses **LinkedList → TreeNode (Red-Black Tree)** if bucket size > 8
* **Resize Mechanism**: Threshold = `capacity * loadFactor` (default 0.75)

#### 🔹 b. **Java 8 Improvements**

* Treeification of buckets after threshold
* Use of `hash(key)` method for better distribution

**🧪 Source Peek:**

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

---

#### 💡 Practical Task:

Implement a **custom HashMap** with `put()` and `get()` using array + LinkedList.

---

#### 🎯 Interview Qs:

* How does HashMap handle collisions?
* What happens if two keys have same `hashCode()` but different `equals()`?
* What’s the impact of poor `hashCode()` implementation?

---

### ✅ 2. **Concurrent Collections**

#### 🔹 a. **ConcurrentHashMap**

* Thread-safe alternative to `HashMap`
* **Segmented locking** (Java 7), **bucket-level locking** with CAS (Java 8+)
* Doesn’t allow null keys/values

**🧪 Practice:**

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("A", 1);
map.putIfAbsent("A", 10); // won't overwrite
```

---

#### 🔹 b. **CopyOnWriteArrayList**

* Safe for reads with infrequent writes
* Copies full array on write

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("one");
```

---

#### 🔹 c. **BlockingQueue Implementations**

* `ArrayBlockingQueue`, `LinkedBlockingQueue`, `PriorityBlockingQueue`
* Used in producer-consumer patterns

---

#### 💡 Practical Task:

Create a multithreaded app using `ConcurrentHashMap` where:

* Multiple threads update vote counts for candidates
* Use atomic operations: `computeIfPresent`, `merge`, `putIfAbsent`

---

### ✅ 3. **Comparison Table**

| Feature                  | HashMap | Hashtable        | ConcurrentHashMap     |
| ------------------------ | ------- | ---------------- | --------------------- |
| Thread-safe              | ❌       | ✅ (synchronized) | ✅ (lock-striping/CAS) |
| Null keys/values         | ✅       | ❌                | ❌                     |
| Performance (concurrent) | ❌       | ❌                | ✅                     |

---

### 🧩 Coding Challenges

| Problem                                     | Concept                               |
| ------------------------------------------- | ------------------------------------- |
| Design a thread-safe word frequency map     | `ConcurrentHashMap`, `merge()`        |
| Implement an LRU cache                      | `LinkedHashMap` override              |
| Build leaderboard with top N scores         | `PriorityQueue`, custom comparator    |
| Simulate voting system with concurrency     | `ConcurrentHashMap`, atomic updates   |
| Build URL shortener with collision handling | HashMap + handling collision manually |

===============

## 🧠 **Day 6–7: DSA – Arrays, Strings, Hashing, Recursion**

### 📅 Goal:

* Revise fundamentals of DSA for Java interviews
* Solve 4–5 curated problems daily (difficulty: Easy → Medium → 1 Hard)
* Focus on **patterns**, not just solutions

---

## ✅ 1. **Arrays**

#### 🔹 Key Concepts:

* Prefix Sum
* Sliding Window
* Two Pointers
* Kadane’s Algorithm (Max Subarray Sum)

#### 🔹 Practice Set:

| Problem                       | Pattern          | Platform      |
| ----------------------------- | ---------------- | ------------- |
| ✅ Two Sum                     | HashMap lookup   | LeetCode #1   |
| ✅ Maximum Subarray            | Kadane’s Algo    | LeetCode #53  |
| ✅ Move Zeroes                 | Two pointers     | LeetCode #283 |
| ✅ Longest Subarray with Sum K | Prefix sum + Map | GFG           |
| ✅ Container With Most Water   | Two pointer      | LeetCode #11  |

---

## ✅ 2. **Strings**

#### 🔹 Key Concepts:

* Character Frequency
* Palindromes
* Sliding Window for substrings
* StringBuilder for performance

#### 🔹 Practice Set:

| Problem                         | Pattern              | Platform      |
| ------------------------------- | -------------------- | ------------- |
| ✅ Valid Anagram                 | HashMap/Array freq   | LeetCode #242 |
| ✅ Longest Substring w/o Repeat  | Sliding Window       | LeetCode #3   |
| ✅ Group Anagrams                | Hashing              | LeetCode #49  |
| ✅ Longest Palindromic Substring | Expand Around Center | LeetCode #5   |
| ✅ String Compression            | In-place             | LeetCode #443 |

---

## ✅ 3. **Hashing (Map / Set)**

#### 🔹 Key Concepts:

* HashSet for uniqueness
* HashMap for frequency/count
* Avoid nested loops → use hashing

#### 🔹 Practice Set:

| Problem                   | Pattern               | Platform      |
| ------------------------- | --------------------- | ------------- |
| ✅ Subarray Sum Equals K   | Prefix Sum + HashMap  | LeetCode #560 |
| ✅ Top K Frequent Elements | Bucket Sort / MinHeap | LeetCode #347 |
| ✅ Intersection of Arrays  | Set operations        | LeetCode #349 |
| ✅ Isomorphic Strings      | Map pattern           | LeetCode #205 |

---

## ✅ 4. **Recursion & Backtracking**

#### 🔹 Key Concepts:

* Base case + recursive call
* Backtracking = undo step
* Call stack understanding

#### 🔹 Practice Set:

| Problem                       | Pattern            | Platform            |
| ----------------------------  | ------------------ | ------------------- |
| ✅ Fibonacci with Memoization | Recursion + DP     | LeetCode            |
| ✅ Subsets                    | Backtracking       | LeetCode #78        |
| ✅ Permutations               | Backtracking       | LeetCode #46        |
| ✅ Combination Sum            | DFS + Backtracking | LeetCode #39        |
| ✅ Sudoku Solver              | Backtracking       | LeetCode #37 (Hard) |

---

## 📊 Daily Plan (Example)

### 🔸 **Day 6**

* Arrays (2): Two Sum, Max Subarray
* Strings (2): Valid Anagram, Longest Substring w/o Repeat
* Hashing (1): Subarray Sum = K

### 🔸 **Day 7**

* Recursion (2): Subsets, Permutations
* Strings (1): Group Anagrams
* Hashing (2): Top K Frequent, Isomorphic Strings

---

## 🧠 Tips for Senior-Level Interviews

* Explain brute force before optimal
* Talk through time/space complexity
* Use meaningful variable names and dry runs
* If stuck: use example input to guide code structure

