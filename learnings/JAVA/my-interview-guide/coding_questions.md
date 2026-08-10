# Java Coding Interview Problems

## 1. Group Anagrams

Given an array of strings, group all strings that are anagrams of each other.

**Input**
```
["eat", "tea", "tan", "ate", "nat", "bat"]
```

**Output**
```
[["eat","tea","ate"], ["tan","nat"], ["bat"]]
```

### Solution 1: Sort each string as the key (simplest, most common in interviews)

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();

    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars); // canonical form

        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }

    return new ArrayList<>(map.values());
}
```

### Using streams (Java 8+)

```java
public List<List<String>> groupAnagrams(String[] strs) {
    return new ArrayList<>(
        Arrays.stream(strs)
            .collect(Collectors.groupingBy(s -> {
                char[] chars = s.toCharArray();
                Arrays.sort(chars);
                return new String(chars);
            }))
            .values()
    );
}
```

---

## 2. First Non-Repeated Character

Find the first character in a string that appears exactly once (doesn't repeat anywhere else in the string).

**Input**
```
"swiss"
```

**Output**
```
'w'   // s repeats, w appears once and is first such char
```

### Solution: HashMap (LinkedHashMap for clarity, but a simple array/map works)

```java
public char firstNonRepeatedChar(String s) {
    Map<Character, Integer> freq = new HashMap<>();

    // Pass 1: count frequencies
    for (char c : s.toCharArray()) {
        freq.put(c, freq.getOrDefault(c, 0) + 1);
    }

    // Pass 2: find first with count == 1
    for (char c : s.toCharArray()) {
        if (freq.get(c) == 1) {
            return c;
        }
    }

    return '\0'; // or throw exception / return Optional.empty()
}
```

### Using LinkedHashMap + streams (Java 8+)

```java
public Character firstNonRepeatedChar(String s) {
    Map<Character, Long> freq = s.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    LinkedHashMap::new,   // preserves insertion order
                    Collectors.counting()));

    return freq.entrySet().stream()
            .filter(e -> e.getValue() == 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
}
```

---

## 3. Valid Parentheses (Stack)

### Solution

```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }

    return stack.isEmpty();
}
```

### Cleaner version using a Map

```java
public boolean isValid(String s) {
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {
            stack.push(c);
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
        }
    }

    return stack.isEmpty();
}
```
