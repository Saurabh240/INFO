### Q-1: How to create threads

- Threads can be created by either extending the `Thread` class and overriding the `run()` method or by implementing the `Runnable` interface and providing an implementation for the `run()` method. The first approach requires creating an instance of the thread class and invoking `start()`, while the second approach involves passing an instance of the `Runnable` implementation to a `Thread` instance and then calling `start()`.

### Q-2: What is Synchronization

- Synchronization is used to manage access to shared data among multiple threads, preventing data corruption. By marking methods or blocks with the `synchronized` keyword, a thread obtains a lock on the object, blocking other threads from accessing synchronized methods or blocks until the lock is released.

### Q-3: What are class-level locks

- Each class in Java has a unique class-level lock. When a thread executes a synchronized static method, it acquires the class-level lock, which prevents other threads from accessing synchronized static methods in that class until the lock is released.

### Q-4: What are synchronized blocks

- Synchronized blocks are used to lock only specific sections of code rather than an entire method. This allows for finer control over synchronization by locking only the necessary lines of code within a block, using a specified object or class as the lock.

### Q-5: How do threads communicate

- Threads communicate using `wait()`, `notify()`, and `notifyAll()` methods. The `wait()` method releases the lock on an object, allowing other threads to acquire it. Once the work is done, a thread can use `notify()` to wake up one waiting thread or `notifyAll()` to wake up all waiting threads. These methods must be used within a synchronized context to avoid `IllegalMonitorStateException`.