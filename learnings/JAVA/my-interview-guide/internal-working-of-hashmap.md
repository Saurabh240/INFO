# 1. What is HashMap?

A `HashMap` stores data as **key-value pairs**.

```java
Map<String, Integer> map = new HashMap<>();

map.put("Apple", 10);
map.put("Banana", 20);
```

Unlike an array where indexes are integers,

```text
Array
Index -> Value

0 -> A
1 -> B
2 -> C
```

HashMap allows

```text
Key -> Value

Apple -> 10
Banana -> 20
```

Internally HashMap converts every key into an integer index.

---

# 2. Internal Data Structure

Internally HashMap maintains an array.

```text
table[]

Index

0
1
2
3
4
5
6
7
8
...
15
```

Default capacity = **16**

Each array element is called a **bucket**.

Each bucket stores a Node.

Node looks like

```java
class Node<K,V>{

    final int hash;

    final K key;

    V value;

    Node<K,V> next;
}
```

Each bucket can contain

* one node
* linked list
* red-black tree (Java 8+)

---

# 3. What happens during put()?

Suppose

```java
map.put("Apple",100);
```

### Step 1

Calculate hashCode()

```java
"Apple".hashCode()
```

Suppose

```text
6347654
```

---

### Step 2

HashMap improves the hash

Instead of directly using

```text
6347654
```

it computes

```java
hash = h ^ (h >>> 16)
```

Why?

To spread bits uniformly.

Otherwise many objects would fall into same bucket.

---

### Step 3

Calculate bucket index

Capacity

```text
16
```

Index

```java
index = (capacity-1) & hash
```

Instead of

```java
hash % capacity
```

Java uses

```java
&
```

because it is much faster.

Suppose

```text
index = 6
```

---

### Step 4

Go to bucket 6

```text
0

1

2

3

4

5

6 ---> null

7

8
```

Bucket is empty.

Create Node

```text
-------------------------
hash = 1234
key = Apple
value =100
next=null
-------------------------
```

Store in bucket.

Done.

---

# 4. What if another key comes?

```java
map.put("Banana",200);
```

Hash becomes

```text
index = 10
```

Store

```text
10 -> Banana
```

Easy.

---

# 5. Collision

Now

```java
map.put("Cat",300);

map.put("Dog",400);
```

Suppose both produce

```text
index = 8
```

Now

```text
Bucket 8

Cat -> Dog
```

Actually

```text
Bucket 8

------------------
Cat
next
 |
 V
------------------
Dog
next=null
```

This is called

## Collision

Two keys map to same bucket.

---

# 6. How collision is handled?

### Before Java 8

Linked List

```text
Bucket

A -> B -> C -> D
```

Searching

```text
O(n)
```

Worst case

---

### Java 8+

If linked list size becomes

```text
>=8
```

AND table capacity >=64

then

Linked List

becomes

```text
Red Black Tree
```

Searching

```text
O(log n)
```

instead of

```text
O(n)
```

Interview Question:

**When does HashMap convert LinkedList into Tree?**

Answer:

* Bucket size >=8
* Table capacity >=64

---

# 7. How get() works?

Suppose

```java
map.get("Apple");
```

Again

Step 1

```java
hashCode()
```

Step 2

Improved hash

Step 3

Find bucket

Suppose bucket

```text
6
```

Now compare

```java
hash
```

then

```java
equals()
```

If

```java
key.equals(existingKey)
```

returns true

Return value.

---

# 8. Why both hashCode() and equals()?

Suppose

```java
class Employee{

int id;

String name;
}
```

Two objects

```java
Employee e1=new Employee(1,"John");

Employee e2=new Employee(1,"John");
```

Different memory

```text
e1 != e2
```

But logically same.

Therefore

Need

```java
hashCode()

equals()
```

Contract

```
equals()==true

↓

hashCode must be same
```

Otherwise HashMap breaks.

---

# 9. Internal Flow

```text
put(key,value)

↓

hashCode()

↓

Improve Hash

↓

Find bucket

↓

Bucket empty?

↓

YES

Store Node

↓

NO

Compare hash

↓

Compare equals()

↓

Same Key?

↓

YES

Update Value

↓

NO

Add new Node
```

---

# 10. Resize (Rehashing)

Suppose capacity

```text
16
```

Load factor

```text
0.75
```

Threshold

```text
16 × 0.75 =12
```

After inserting

13th element

HashMap resizes.

Capacity becomes

```text
32
```

Every node is rehashed.

This is expensive.

---

# 11. Time Complexity

| Operation | Average | Worst                                       |
| --------- | ------- | ------------------------------------------- |
| put       | O(1)    | O(n) before Java 8, O(log n) with tree bins |
| get       | O(1)    | O(n) before Java 8, O(log n) with tree bins |
| remove    | O(1)    | O(n) / O(log n)                             |

---

# 12. How does the key behave internally?

Suppose

```java
Employee e=new Employee(10,"John");

map.put(e,"Developer");
```

HashMap stores

```text
hash

key reference

value
```

Node

```text
-------------------
hash = 45233

key ------> Employee

value ----> Developer
-------------------
```

Notice

**HashMap stores the reference of the key object**, not a copy of the key.

---

# 13. What happens if we modify the key?

Suppose

```java
class Employee{

int id;

String name;

public int hashCode(){
return id;
}

public boolean equals(Object o){
...
}
}
```

Now

```java
Employee e=new Employee(1,"John");

map.put(e,"Developer");
```

Hash

```text
1
```

Bucket

```text
5
```

Stored successfully.

Now modify

```java
e.id=100;
```

Now

Hash becomes

```text
100
```

But HashMap still stores the object in **bucket 5** because it was placed there using the original hash.

Now

```java
map.get(e);
```

HashMap computes

```text
hash=100
```

Looks in a **different bucket**, not bucket 5.

Result

```java
null
```

Even though the key object is still inside the map!

---

## Visualization

Before modification

```text
Bucket 5

Employee(id=1)
```

After

```java
e.id=100;
```

Object becomes

```text
Employee(id=100)
```

But still physically stored in

```text
Bucket 5
```

Now

```java
map.get(e)
```

searches

```text
Bucket 9
```

Nothing found.

---

# 14. Can we remove the modified key?

```java
map.remove(e);
```

Fails.

Because

HashMap searches wrong bucket.

Memory

```text
Bucket5

Employee(id=100)
```

remains.

The entry is effectively unreachable using that mutated key reference.

---

# 15. Why are immutable keys recommended?

Classes like

* `String`
* `Integer`
* `UUID`

are immutable.

Example

```java
String s="Java";

map.put(s,100);
```

You cannot change

```java
s
```

internally.

Its hashCode never changes.

Therefore

* bucket never changes
* retrieval always works
* removal always works

That's why `String` is the most common key type in a `HashMap`.

---

# 16. Interview Trap

```java
String s="ABC";

map.put(s,1);

s="XYZ";

System.out.println(map.get("ABC"));
```

Output?

```
1
```

Why?

Because

```java
s="XYZ";
```

does **not modify the original `String` object**. It simply makes the variable `s` point to a new `String`. The original `"ABC"` object remains unchanged and is still used as the key inside the `HashMap`.

---

# 17. Best Practices for Keys

* Use **immutable** objects as keys.
* If using custom objects, correctly override both `equals()` and `hashCode()`.
* Never modify fields that participate in `equals()` or `hashCode()` after inserting the object into the map.
* If a key must be mutable, remove it from the map before changing it, then reinsert it.

---

## Interview Answer (2-minute version)

> Internally, `HashMap` maintains an array of buckets. During `put()`, it computes the key's `hashCode()`, mixes the hash bits, calculates the bucket index using `(n - 1) & hash`, and stores a `Node` containing the hash, key reference, value, and next pointer. If multiple keys map to the same bucket, collisions are handled using a linked list, which is converted to a red-black tree in Java 8+ when a bucket contains at least 8 entries and the table capacity is at least 64. During `get()`, the same hash computation locates the bucket, and then both the stored hash and `equals()` are used to find the correct key. If a mutable key is modified after insertion and that modification changes its `hashCode()` or `equals()`, the entry remains in its original bucket but future lookups compute a different bucket index, making the key effectively unreachable. This is why immutable keys such as `String` are strongly recommended for use in `HashMap`.
