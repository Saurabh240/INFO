# Collections Framework — Interview Guide (7+ YOE)

---

## 1. HashMap Internals

### Q1. How does HashMap work internally in Java 8+?

**Data structure:** Array of `Node<K,V>` (buckets). Each bucket holds a linked list, which converts to a **Red-Black Tree** when bucket size exceeds 8 (and total map size > 64).

**Put operation flow:**
1. Compute `hash(key)` — spreads bits to reduce collisions
2. `index = hash & (capacity - 1)` — maps to bucket
3. If bucket empty → insert directly
4. If collision → traverse linked list, check `equals()`; insert at end or replace
5. If bucket size > 8 && total size > 64 → treeify (O(n) → O(log n) lookups)
6. If `size > capacity * loadFactor` → resize (double capacity, rehash all entries)

```java
// Java 8 hash spreading — reduces high-bit collisions
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    // XOR with upper 16 bits mixes high and low bits
    // Improves distribution, especially for keys with similar lower bits
}
```

**Key interview scenarios:**

```java
// Scenario 1: Poor hashCode causes O(n) HashMap — all keys same bucket
class BadKey {
    @Override public int hashCode() { return 1; } // everything collides!
    @Override public boolean equals(Object o) { return this == o; }
}
Map<BadKey, String> map = new HashMap<>();
// All 1000 entries in one bucket — get() is O(n), or O(log n) after treeification

// Scenario 2: Mutable key — never do this
class MutableKey {
    int value;
    @Override public int hashCode() { return value; }
    @Override public boolean equals(Object o) { return ((MutableKey)o).value == value; }
}
MutableKey key = new MutableKey();
key.value = 1;
map.put(key, "data");
key.value = 2; // hash changed! map.get(key) now returns null — lost entry
```

**Capacity and load factor:**
```java
// Default: capacity=16, loadFactor=0.75 → resize at 12 entries
// Pre-size when you know approximate count — avoids resizing overhead
Map<String, User> users = new HashMap<>(expectedSize * 4 / 3 + 1);

// Or use Guava's Maps.newHashMapWithExpectedSize(n)
```

---

## 2. HashMap vs LinkedHashMap vs TreeMap

### Q2. When do you use which Map implementation?

| | `HashMap` | `LinkedHashMap` | `TreeMap` |
|---|---|---|---|
| Ordering | None | Insertion or access order | Natural/custom sorted order |
| `get`/`put` | O(1) avg | O(1) avg | O(log n) |
| Null keys | 1 allowed | 1 allowed | ❌ (throws NPE) |
| Use case | General purpose | LRU cache, ordered iteration | Sorted map, range queries |

```java
// LinkedHashMap as LRU Cache — classic interview problem
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    LRUCache(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity; // evict oldest on overflow
    }
}

// TreeMap for sorted/range operations
TreeMap<LocalDate, Transaction> txByDate = new TreeMap<>();
// Range query — all transactions in last 30 days
NavigableMap<LocalDate, Transaction> recent = txByDate
    .tailMap(LocalDate.now().minusDays(30));

// Get entry just before or after a key
Map.Entry<LocalDate, Transaction> prev = txByDate.floorEntry(targetDate);
Map.Entry<LocalDate, Transaction> next = txByDate.ceilingEntry(targetDate);
```

---

## 3. ConcurrentHashMap

### Q3. How is ConcurrentHashMap thread-safe without locking the whole map?

**Java 7:** Used segment-level locking (16 segments by default — 16 threads can write simultaneously).

**Java 8+:** Replaced segments with **bucket-level CAS + synchronized blocks**. Much finer granularity.

```java
ConcurrentHashMap<String, Integer> voteCount = new ConcurrentHashMap<>();

// Thread-safe atomic operations — critical for concurrent updates
voteCount.put("CandidateA", 0);

// compute — atomic read-modify-write (NOT separate get + put)
voteCount.compute("CandidateA", (key, val) -> val == null ? 1 : val + 1);

// merge — better for accumulation
voteCount.merge("CandidateA", 1, Integer::sum);

// putIfAbsent — initialize only if key absent
voteCount.putIfAbsent("CandidateB", 0);

// computeIfAbsent — lazy initialization of complex values
Map<String, List<String>> grouped = new ConcurrentHashMap<>();
grouped.computeIfAbsent("category", k -> new CopyOnWriteArrayList<>()).add("item");
```

**Key difference from `Hashtable`:**
```java
// Hashtable — entire map locked on every operation (coarse-grained, poor performance)
Hashtable<String, String> ht = new Hashtable<>(); // synchronized on 'this'

// ConcurrentHashMap — lock per bucket, reads are lock-free
ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();

// ConcurrentHashMap does NOT allow null keys or values
// (null would be ambiguous — key absent vs key mapped to null)
chm.put(null, "value"); // throws NullPointerException

// Iterators are weakly consistent — won't throw ConcurrentModificationException
// but may/may not reflect concurrent modifications
```

---

## 4. BlockingQueue & Producer-Consumer

### Q4. Implement a thread-safe producer-consumer using `BlockingQueue`.

```java
// BlockingQueue implementations:
// ArrayBlockingQueue  — bounded, fair option
// LinkedBlockingQueue — optionally bounded, higher throughput
// PriorityBlockingQueue — unbounded, priority-ordered
// SynchronousQueue    — zero capacity, direct handoff (used in Executors.newCachedThreadPool)

public class TransactionProcessor {
    private final BlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(1000);
    private final ExecutorService producers = Executors.newFixedThreadPool(4);
    private final ExecutorService consumers = Executors.newFixedThreadPool(8);
    private volatile boolean running = true;

    // Producer — put() blocks if queue is full (backpressure)
    public void startProducer(String source) {
        producers.submit(() -> {
            while (running) {
                Transaction tx = fetchFrom(source);
                try {
                    queue.put(tx); // blocks if full — natural backpressure
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    // Consumer — take() blocks if queue is empty
    public void startConsumer() {
        consumers.submit(() -> {
            while (running || !queue.isEmpty()) {
                try {
                    Transaction tx = queue.poll(1, TimeUnit.SECONDS); // timeout avoids infinite block
                    if (tx != null) process(tx);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
```

---

## 5. CopyOnWriteArrayList

### Q5. When should you use `CopyOnWriteArrayList` over `ArrayList`?

`CopyOnWriteArrayList` creates a **fresh copy of the underlying array on every write**. Reads are lock-free.

```java
CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

// Reads — completely lock-free, no synchronization overhead
// Safe to iterate while other threads are adding/removing
for (EventListener listener : listeners) {
    listener.onEvent(event); // even if listeners is modified concurrently — no CME
}

// Writes — expensive (full array copy)
listeners.add(newListener);    // copies entire array
listeners.remove(oldListener); // copies entire array
```

**When to use:**
- ✅ Read-heavy, write-rare scenarios (event listeners, observer registries)
- ✅ Iteration without `ConcurrentModificationException` is critical
- ❌ High write frequency — every write copies the array (O(n) space + time)
- ❌ Large lists — copying is expensive

---

## 6. PriorityQueue

### Q6. How does `PriorityQueue` work and what are common interview applications?

`PriorityQueue` is a **min-heap** by default (smallest element at head).

```java
// Min-heap — smallest first
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5); minHeap.offer(1); minHeap.offer(3);
minHeap.poll(); // returns 1

// Max-heap — largest first
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

// Custom priority — transactions by amount descending
PriorityQueue<Transaction> highValueFirst = new PriorityQueue<>(
    Comparator.comparing(Transaction::getAmount).reversed()
);

// Classic problem: Top K frequent elements using min-heap of size K
public List<String> topKFrequent(List<String> words, int k) {
    Map<String, Integer> freq = new HashMap<>();
    words.forEach(w -> freq.merge(w, 1, Integer::sum));

    // Min-heap of size k — kicks out least frequent
    PriorityQueue<String> heap = new PriorityQueue<>(
        Comparator.comparingInt(freq::get)
    );
    for (String word : freq.keySet()) {
        heap.offer(word);
        if (heap.size() > k) heap.poll(); // remove lowest frequency
    }

    List<String> result = new ArrayList<>(heap);
    Collections.reverse(result);
    return result;
}
```

---

## 7. Collections Comparison Cheatsheet

### Q7. Quick comparison — which collection for which use case?

| Use case | Best choice | Why |
|---|---|---|
| Fast key-value lookup | `HashMap` | O(1) average |
| Ordered key-value | `LinkedHashMap` | Insertion order maintained |
| Sorted keys, range queries | `TreeMap` | O(log n), NavigableMap API |
| Thread-safe map | `ConcurrentHashMap` | Bucket-level locking, no global lock |
| Thread-safe sorted map | `ConcurrentSkipListMap` | Lock-free, sorted |
| Unique elements, fast lookup | `HashSet` | Backed by HashMap |
| Sorted unique elements | `TreeSet` | Backed by TreeMap |
| Thread-safe set | `ConcurrentHashMap.newKeySet()` | Better than `Collections.synchronizedSet` |
| FIFO queue | `LinkedList` / `ArrayDeque` | `ArrayDeque` faster, no null |
| Thread-safe queue | `LinkedBlockingQueue` | Bounded or unbounded |
| Priority ordering | `PriorityQueue` | Min-heap, O(log n) offer/poll |
| Read-heavy list, rare writes | `CopyOnWriteArrayList` | Lock-free reads |
| LRU cache | `LinkedHashMap` (accessOrder) | O(1) with eviction policy |
