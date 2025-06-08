### Week 8: Data Structures (Interview-centric) - Detailed Teaching Guide

---

#### **Day 1: Stack, Queue (DSA Implementation)**

**Objective:** Understand how to implement Stack and Queue using arrays and linked lists.

**Topics:**

* Stack (LIFO)
* Queue (FIFO)
* Array and LinkedList implementation

**Teaching Flow:**

1. **Stack using array:**

```java
class Stack {
    int[] arr = new int[100];
    int top = -1;

    void push(int x) {
        arr[++top] = x;
    }

    int pop() {
        return arr[top--];
    }
}
```

2. **Queue using array:**

```java
class Queue {
    int[] arr = new int[100];
    int front = 0, rear = 0;

    void enqueue(int x) {
        arr[rear++] = x;
    }

    int dequeue() {
        return arr[front++];
    }
}
```

**Practice:**

* Implement stack using LinkedList
* Reverse a string using stack

---

#### **Day 2: LinkedList (Singly, Doubly)**

**Objective:** Understand structure and traversal of Linked Lists.

**Topics:**

* Singly Linked List
* Doubly Linked List

**Teaching Flow:**

1. **Singly Linked List:**

```java
class Node {
    int data;
    Node next;
}
```

2. **Operations:**

* Insert at head/tail
* Delete node
* Traverse

**Practice:**

* Write function to insert/delete at position
* Print list in reverse using recursion

---

#### **Day 3: Binary Search, Hashing Concepts**

**Objective:** Understand binary search algorithm and basics of hashing.

**Topics:**

* Binary Search
* HashMap fundamentals
* Collision and Hash function idea

**Teaching Flow:**

1. **Binary Search:**

```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

2. **HashMap Usage:**

```java
HashMap<Integer, String> map = new HashMap<>();
map.put(1, "Java");
```

**Practice:**

* Search for number in sorted array
* Implement frequency map

---

#### **Day 4: Practice - Reverse LL, Detect Loop**

**Objective:** Solve common LinkedList interview problems.

**Problems:**

1. **Reverse a linked list:**

```java
Node reverse(Node head) {
    Node prev = null, curr = head;
    while (curr != null) {
        Node next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

2. **Detect loop (Floyd’s Algorithm):**

```java
Node slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) return true;
}
```

**Practice:**

* Reverse a list recursively
* Remove loop if detected

---

#### **Day 5: HashMap-based Problems**

**Objective:** Practice real-world problems using HashMap.

**Problems:**

1. Two Sum problem
2. Find duplicates in array
3. First non-repeating character in string
4. Group anagrams

**Teaching Flow:**

* Use HashMap for frequency counting and mapping
* Emphasize time complexity improvements

**Practice:**

* Implement each of the above problems
* Dry run with inputs and discuss edge cases

---

#### **Day 6–7: Mock Test 1 - Coding + Java Concepts**

**Objective:** Simulate interview environment with coding + theory questions.

**Coding Questions Examples:**

1. Reverse words in a string
2. Validate parentheses using Stack
3. Binary search variation (find first occurrence)

**Java Concept Questions:**

* OOP Principles
* Differences between List and Set
* Explain HashMap internal working
* Final vs finally vs finalize

**Instructions:**

* Allocate 90 mins
* 3 coding problems (easy to medium)
* 4–5 Java concept questions
* Review answers and explain corrections
