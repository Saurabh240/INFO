### Week 3: Java Collections & More DSA - Detailed Teaching Guide

---

#### **Day 1: ArrayList, LinkedList**

**Objective:** Learn about dynamic lists and their differences.

**Topics:**

* ArrayList: dynamic array
* LinkedList: doubly-linked list

**Teaching Flow:**

1. **ArrayList:**

```java
import java.util.ArrayList;
ArrayList<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
System.out.println(names.get(0));
```

2. **LinkedList:**

```java
import java.util.LinkedList;
LinkedList<String> queue = new LinkedList<>();
queue.add("First");
queue.add("Second");
```

3. **Comparison:**

* ArrayList: faster for access
* LinkedList: faster for insertion/deletion

**Practice:**

* Create a list of integers, insert/remove elements
* Create a to-do list using LinkedList

---

#### **Day 2: HashMap, HashSet**

**Objective:** Learn about key-value pairs and sets.

**Topics:**

* HashMap for key-value storage
* HashSet for unique elements

**Teaching Flow:**

1. **HashMap:**

```java
import java.util.HashMap;
HashMap<Integer, String> map = new HashMap<>();
map.put(1, "Apple");
map.put(2, "Banana");
System.out.println(map.get(1));
```

2. **HashSet:**

```java
import java.util.HashSet;
HashSet<String> set = new HashSet<>();
set.add("Java");
set.add("Java"); // Ignored
```

**Practice:**

* Count word frequency in a sentence using HashMap
* Find unique elements from an array using HashSet

---

#### **Day 3: Queue, Stack (Collections)**

**Objective:** Learn LIFO and FIFO structures using Java Collections.

**Topics:**

* Stack (LIFO)
* Queue (FIFO)

**Teaching Flow:**

1. **Stack:**

```java
import java.util.Stack;
Stack<Integer> stack = new Stack<>();
stack.push(10);
stack.push(20);
stack.pop();
```

2. **Queue:**

```java
import java.util.LinkedList;
Queue<Integer> queue = new LinkedList<>();
queue.offer(1);
queue.poll();
```

**Practice:**

* Reverse a string using stack
* Simulate print queue using Queue

---

#### **Day 4: Practice Problems Using Collections**

**Objective:** Apply knowledge of collections to real problems.

**Practice Problems:**

1. Store student names and roll numbers using HashMap
2. Track top 3 elements using ArrayList
3. Remove duplicates from ArrayList using HashSet
4. Implement a simple browser history using Stack
5. Manage customer service queue using LinkedList

---

#### **Day 5: Searching (Linear, Binary)**

**Objective:** Learn basic searching techniques.

**Topics:**

* Linear search
* Binary search (requires sorted array)

**Teaching Flow:**

1. **Linear Search:**

```java
int[] arr = {10, 20, 30};
for (int i = 0; i < arr.length; i++) {
    if (arr[i] == 20) {
        System.out.println("Found at index " + i);
    }
}
```

2. **Binary Search:**

```java
Arrays.sort(arr);
int result = Arrays.binarySearch(arr, 20);
```

**Practice:**

* Search element in unsorted array
* Search in sorted list

---

#### **Day 6: Sorting (Bubble, Selection, Insertion)**

**Objective:** Understand basic sorting algorithms.

**Teaching Flow:**

1. **Bubble Sort:**

```java
for (int i = 0; i < n-1; i++) {
    for (int j = 0; j < n-i-1; j++) {
        if (arr[j] > arr[j+1]) {
            // swap
        }
    }
}
```

2. **Selection Sort:**

* Find minimum in each pass, put at front

3. **Insertion Sort:**

* Shift larger elements right and insert at correct position

**Practice:**

* Sort array using each method manually

---

#### **Day 7: Practice - Sort Employees by Name/ID using Comparator**

**Objective:** Implement real-world object sorting.

**Teaching Flow:**

1. **Create Employee Class:**

```java
class Employee {
    int id;
    String name;
    // constructor, getters
}
```

2. **Comparator Example:**

```java
Collections.sort(list, new Comparator<Employee>() {
    public int compare(Employee a, Employee b) {
        return a.name.compareTo(b.name);
    }
});
```

**Practice:**

* Sort by name ascending
* Sort by id descending
* Use lambda expression for Comparator (Java 8+)
