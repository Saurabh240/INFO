### Week 6: Multithreading Basics - Detailed Teaching Guide

---

#### **Day 1: Thread Creation (`Thread` class, `Runnable`)**

**Objective:** Understand basic multithreading concepts and how to create threads.

**Topics:**

* Thread class
* Runnable interface

**Teaching Flow:**

1. **Extending Thread class:**

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread is running");
    }
}

MyThread t1 = new MyThread();
t1.start();
```

2. **Using Runnable interface:**

```java
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable thread");
    }
}

Thread t2 = new Thread(new MyRunnable());
t2.start();
```

**Practice:**

* Create and run multiple threads
* Use lambda with Runnable for thread definition

---

#### **Day 2: `synchronized`, Race Condition, `sleep`, `join`**

**Objective:** Understand synchronization and thread lifecycle control.

**Topics:**

* Race condition and synchronization
* `sleep()` and `join()` methods

**Teaching Flow:**

1. **Race condition example:**

```java
class Counter {
    int count = 0;
    void increment() {
        count++;
    }
}
```

2. **Synchronization:**

```java
synchronized void increment() {
    count++;
}
```

3. **sleep & join:**

```java
Thread.sleep(1000);
t1.join();
```

**Practice:**

* Show data inconsistency without sync
* Demonstrate thread sleep and join behavior

---

#### **Day 3: ExecutorService, Callable, Future**

**Objective:** Manage threads using modern Java concurrency utilities.

**Topics:**

* ExecutorService
* Callable and Future

**Teaching Flow:**

1. **ExecutorService:**

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.execute(() -> System.out.println("Task executed"));
executor.shutdown();
```

2. **Callable & Future:**

```java
Callable<String> task = () -> "Result";
Future<String> future = executor.submit(task);
System.out.println(future.get());
```

**Practice:**

* Submit tasks with return values
* Use Future to wait for results

---

#### **Day 4: Practice - Producer-Consumer Pattern**

**Objective:** Learn coordination between threads.

**Topics:**

* Shared resources
* Wait and notify methods

**Teaching Flow:**

1. **Producer-Consumer using wait/notify:**

```java
class SharedQueue {
    Queue<Integer> queue = new LinkedList<>();
    int capacity = 5;

    synchronized void produce() throws InterruptedException {
        while (queue.size() == capacity) wait();
        queue.add(1);
        notify();
    }

    synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()) wait();
        queue.remove();
        notify();
    }
}
```

**Practice:**

* Implement with threads for producer and consumer
* Add delays and capacity control

---

#### **Day 5–7: Revise DSA - Arrays, Strings, Recursion**

**Objective:** Strengthen core algorithmic skills.

**Topics:**

* DSA practice sets for each topic

**Practice Set:**

1. **Arrays:**

   * Find max/min element
   * Kadane’s algorithm (max subarray sum)
2. **Strings:**

   * Reverse a string
   * Check for palindrome
   * Count vowels/consonants
3. **Recursion:**

   * Factorial, Fibonacci
   * Print subsets
   * Solve Tower of Hanoi

**Suggested Tools:**

* LeetCode, HackerRank, CodingNinjas

**Goal:**

* Practice 4–5 problems per day from each category
* Focus on edge cases and dry runs
