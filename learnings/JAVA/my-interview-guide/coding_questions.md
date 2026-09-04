# Java Coding Interview Problems

## 1. Group Anagrams*

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

Complexity: `O(n * k log k)`, where `n` = number of strings, `k` = average string length.

### Solution 2: Using streams (Java 8+)

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

### Solution 3: Frequency-count key (faster, lowercase English letters only)

```java
public static List<List<String>> groupAnagrams(String[] words) {

    Map<String, List<String>> map = new HashMap<>();

    for (String word : words) {

        int[] count = new int[26];

        for (char ch : word.toCharArray()) {
            count[ch - 'a']++;
        }

        StringBuilder key = new StringBuilder();

        for (int value : count) {
            key.append('#').append(value);
        }

        map.computeIfAbsent(key.toString(), k -> new ArrayList<>())
           .add(word);
    }

    return new ArrayList<>(map.values());
}
```

Complexity: `O(n * k)` — avoids the `log k` sorting cost of Solution 1.

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

### Solution 1: HashMap, two passes

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

### Solution 2: LinkedHashMap, single pass (order-preserving)

```java
public static Character firstNonRepeating(String str) {
    Map<Character, Integer> frequency = new LinkedHashMap<>();

    for (char ch : str.toCharArray()) {
        frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
    }

    for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
        if (entry.getValue() == 1) {
            return entry.getKey();
        }
    }

    return null;
}
```

### Solution 3: LinkedHashMap + streams (Java 8+)

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

## 3. Valid / Balanced Parentheses (Stack)*

Check whether a string of brackets is balanced.

**Input**
```
"()[]{}"   -> true
"([{}])"   -> true
"([)]"     -> false
```

### Solution 1: Stack with explicit if-checks

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

### Solution 2: Cleaner version using a Map

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

---

## 4. Two Sum*

Find two indices whose values add up to the target.

**Input**
```
nums = [2, 7, 11, 15], target = 9
```

**Output**
```
[0, 1]
```

### Solution: HashMap complement lookup

```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];

        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }

        map.put(nums[i], i);
    }

    return new int[]{-1, -1};
}
```

---

## 5. Find Duplicates

Find all duplicate elements in an array.

**Input**
```
[1, 2, 3, 2, 4, 1, 5]
```

**Output**
```
[2, 1]
```

### Solution: Two sets (seen / duplicates)

```java
public static List<Integer> findDuplicates(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new LinkedHashSet<>();

    for (int num : nums) {
        if (!seen.add(num)) {
            duplicates.add(num);
        }
    }

    return new ArrayList<>(duplicates);
}
```

---

## 6. Reverse String

Reverse a string in place using two pointers.

**Input**
```
"hello"
```

**Output**
```
"olleh"
```

### Solution: Two-pointer swap — O(n)

```java
public static String reverse(String str) {
    char[] chars = str.toCharArray();

    int left = 0;
    int right = chars.length - 1;

    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;

        left++;
        right--;
    }

    return new String(chars);
}
```

---

## 7. Anagram Check

Check whether two strings contain the same characters with the same frequencies.

**Input**
```
"listen"
"silent"
```

**Output**
```
true
```

### Solution: Fixed-size count array — O(n)

```java
public static boolean isAnagram(String s, String t) {

    if (s.length() != t.length()) {
        return false;
    }

    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int value : count) {
        if (value != 0) {
            return false;
        }
    }

    return true;
}
```

---

## 8. Second Highest Number

Find the second distinct highest number in an array. (Clarify with the interviewer whether duplicates count — this solution treats the second highest as the second *distinct* value.)

**Input**
```
[10, 5, 20, 8, 20]
```

**Output**
```
Highest = 20
Second highest = 10
```

### Solution: Single pass, track two running values — O(n)

```java
public static Integer secondHighest(int[] nums) {

    Integer highest = null;
    Integer secondHighest = null;

    for (int num : nums) {

        if (highest == null || num > highest) {
            secondHighest = highest;
            highest = num;
        }
        else if (num != highest &&
                 (secondHighest == null || num > secondHighest)) {
            secondHighest = num;
        }
    }

    return secondHighest;
}
```

---

## 9. Missing Number

Given numbers from 0 to n (with one missing), find the missing number.

**Input**
```
[3, 0, 1]
```

**Output**
```
Numbers should be: 0, 1, 2, 3
Missing = 2
```

### Solution: XOR — O(n)

```java
public static int missingNumber(int[] nums) {

    int xor = nums.length;

    for (int i = 0; i < nums.length; i++) {
        xor ^= i;
        xor ^= nums[i];
    }

    return xor;
}
```

**Why XOR?**

```
x ^ x = 0
x ^ 0 = x
```

All existing numbers cancel each other out, so only the missing number remains.

---

## 10. Longest Substring Without Repeating Characters*

One of the most important sliding-window interview questions.

**Input**
```
"abcabcbb"
```

**Output**
```
"abc"  -> length 3
```

### Solution 1: Sliding window with HashSet — O(n)

```java
public static int longestSubstring(String s) {

    Set<Character> set = new HashSet<>();

    int left = 0;
    int maxLength = 0;

    for (int right = 0; right < s.length(); right++) {

        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left));
            left++;
        }

        set.add(s.charAt(right));

        maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
}
```

### Solution 2: HashMap, jump the left pointer directly — O(n)

```java
public static int longestSubstring(String s) {

    Map<Character, Integer> map = new HashMap<>();

    int left = 0;
    int maxLength = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);

        if (map.containsKey(ch)) {
            left = Math.max(left, map.get(ch) + 1);
        }

        map.put(ch, right);

        maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
}
```

---

## 11. Merge Two Sorted Arrays*

**Input**
```
A = [1, 3, 5]
B = [2, 4, 6]
```

**Output**
```
[1, 2, 3, 4, 5, 6]
```

### Solution: Two-pointer merge

```java
public static int[] mergeSortedArrays(int[] a, int[] b) {

    int[] result = new int[a.length + b.length];

    int i = 0;
    int j = 0;
    int k = 0;

    while (i < a.length && j < b.length) {

        if (a[i] <= b[j]) {
            result[k++] = a[i++];
        } else {
            result[k++] = b[j++];
        }
    }

    while (i < a.length) {
        result[k++] = a[i++];
    }

    while (j < b.length) {
        result[k++] = b[j++];
    }

    return result;
}
```

Complexity: `O(n + m)` time, `O(n + m)` space.

---

## 12. Find Frequency of Characters

**Input**
```
"banana"
```

**Output**
```
b = 1
a = 3
n = 2
```

### Solution 1: HashMap loop

```java
public static Map<Character, Integer> characterFrequency(String str) {

    Map<Character, Integer> frequency = new HashMap<>();

    for (char ch : str.toCharArray()) {
        frequency.put(
            ch,
            frequency.getOrDefault(ch, 0) + 1
        );
    }

    return frequency;
}
```

### Solution 2: Using streams (Java 8+)

```java
public static Map<Character, Long> characterFrequencyStream(String str) {

    return str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.counting()
            ));
}
```
