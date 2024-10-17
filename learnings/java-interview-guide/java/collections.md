### Q-1: What are the different collection types?

- `List`: Allows duplicates, with implementations like `ArrayList` and `LinkedList`.
- `Set`: Does not allow duplicates, with implementations like `HashSet`, `LinkedHashSet`, and `TreeSet` (via `SortedSet`).
- `Queue`: First-in, first-out structure, implemented by `PriorityQueue`.
- `BlockingQueue`: Used for the producer-consumer pattern.
- `Map`: Stores key-value pairs, with implementations like `HashMap`, `LinkedHashMap`, and `TreeMap` (a sorted map).

### Q-2: ArrayList vs LinkedList

- `ArrayList`: Uses an array; insertion can be expensive (O(n)) due to shifting elements. Fast random access due to array backing.
- `LinkedList`: A doubly linked list; insertion is easier, but random access is slower as it requires traversal.
- `ArrayList` is preferred for read-intensive applications, while `LinkedList` is better for frequent insertions.

### Q-3: Vector vs ArrayList

- `Vector`: Thread-safe with synchronized methods, but slower.
- `ArrayList`: Not synchronized by default, offering better performance but requiring external synchronization for thread safety.

### Q-4: HashMap vs LinkedHashMap

- `HashMap`: Does not maintain insertion order.
- `LinkedHashMap`: Maintains the order of elements based on insertion.

### Q-5: How to create a Generic Class

- Define the class with a generic type placeholder (e.g., `<T>`). This placeholder can be used in fields, method parameters, and return types.
- Instantiate the class with a specific type, replacing the placeholder with the desired type.

### Q-6: Failfast vs Failsafe Iterators *

- `Failfast Iterators`: Used by traditional collections like `ArrayList` and `HashSet`. They throw `ConcurrentModificationException` if the collection is modified during iteration.
- `Failsafe Iterators`: Used by concurrent collections like `CopyOnWriteArrayList`. They allow modifications during iteration without exceptions.

### Q-7: Producer-Consumer Pattern *

- `BlockingQueue` is recommended for implementing the producer-consumer pattern. The `put()` method blocks if the queue is full, while the `take()` method blocks if the queue is empty, ensuring coordination between producers and consumers.

### Q-8: Comparable vs Comparator

- `Comparable`: Defines natural/default ordering with the `compareTo` method.
- `Comparator`: Allows custom ordering with the `compare` method.
- `Comparable` is used for default sorting in collections like `TreeSet`, while `Comparator` provides custom sorting.

### Q-9: What are concurrent collections? *

- `Concurrent collections` like `CopyOnWriteArrayList` and `ConcurrentHashMap` are designed for multithreaded environments, allowing safe concurrent modifications.
- These collections implement `failsafe iterators` and offer features like fine-grained locking (in `ConcurrentHashMap`) for enhanced performance and safety.

### Q-10: What is a Set in Java?
A collection that doesn't allow duplicates. Implementations include HashSet, TreeSet, LinkedHashSet.

### Q-11: Difference between Iterator and ListIterator? *
Iterator: Only forward iteration.
ListIterator: Bidirectional iteration (forward and backward).

### Q-12: Explain BlockingQueue.
A thread-safe queue that supports blocking operations, waiting for space or elements when necessary. Common implementations: ArrayBlockingQueue, LinkedBlockingQueue.

### Q-13: Difference between map() and flatMap() in Streams?
map(): Transforms each element individually.
flatMap(): Flattens and transforms each element into a stream.