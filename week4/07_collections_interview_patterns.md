# Day 7: Collections — Interview Patterns & Advanced Techniques

## 🎯 Learning Goals
- Solve classic interview problems using collections
- Master frequency counting, two-pointer-like patterns, and anagram detection
- Use `Stack`, `Queue`, and `PriorityQueue`
- Understand the `Collections` and `Arrays` utility classes
- Know thread-safe collections

---

## 🔥 Pattern 1: Frequency Counter (HashMap)

The most common interview pattern with HashMap. Count how often each element appears.

### Word Frequency
```java
public static Map<String, Integer> wordFrequency(String text) {
    Map<String, Integer> freq = new HashMap<>();
    String[] words = text.toLowerCase().split("\\s+");

    for (String word : words) {
        freq.merge(word, 1, Integer::sum);
        // Equivalent older approach:
        // freq.put(word, freq.getOrDefault(word, 0) + 1);
    }
    return freq;
}

// Usage:
String text = "the cat sat on the mat the cat";
Map<String, Integer> freq = wordFrequency(text);
// {the=3, cat=2, sat=1, on=1, mat=1}
```

### Character Frequency (Interview Classic ⭐)
```java
public static Map<Character, Integer> charFrequency(String s) {
    Map<Character, Integer> freq = new HashMap<>();
    for (char c : s.toCharArray()) {
        freq.merge(c, 1, Integer::sum);
    }
    return freq;
}
```

### Find First Non-Repeating Character
```java
public static char firstNonRepeating(String s) {
    // LinkedHashMap preserves insertion order!
    Map<Character, Integer> freq = new LinkedHashMap<>();
    for (char c : s.toCharArray()) {
        freq.merge(c, 1, Integer::sum);
    }
    for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
        if (entry.getValue() == 1) {
            return entry.getKey();
        }
    }
    return '\0';  // No non-repeating character
}

// Example:
firstNonRepeating("aabbccd");   // 'd'
firstNonRepeating("aabbc");      // 'c'
firstNonRepeating("aabb");       // '\0'
```

---

## 🔥 Pattern 2: Duplicate Detection (HashSet)

### Find Duplicates in Array
```java
public static List<Integer> findDuplicates(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    List<Integer> duplicates = new ArrayList<>();

    for (int num : nums) {
        if (!seen.add(num)) {    // add() returns false if already exists
            duplicates.add(num);
        }
    }
    return duplicates;
}

// Example:
findDuplicates(new int[]{1, 2, 3, 2, 4, 1, 5});
// [2, 1]
```

### Check if Array Has Duplicates (One-Liner)
```java
public static boolean hasDuplicates(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int n : nums) {
        if (!set.add(n)) return true;
    }
    return false;
}
```

### Find Common Elements (Intersection)
```java
public static Set<Integer> intersection(int[] a, int[] b) {
    Set<Integer> setA = new HashSet<>();
    for (int n : a) setA.add(n);

    Set<Integer> result = new HashSet<>();
    for (int n : b) {
        if (setA.contains(n)) {
            result.add(n);
        }
    }
    return result;
}

// Example:
intersection(new int[]{1,2,3,4}, new int[]{3,4,5,6});
// [3, 4]
```

---

## 🔥 Pattern 3: Anagram Detection (Sorted or Frequency)

### Check if Two Strings Are Anagrams
```java
// Method 1: Sort and compare
public static boolean isAnagram(String a, String b) {
    char[] sortedA = a.toLowerCase().toCharArray();
    char[] sortedB = b.toLowerCase().toCharArray();
    Arrays.sort(sortedA);
    Arrays.sort(sortedB);
    return Arrays.equals(sortedA, sortedB);
}

// Method 2: Frequency map (faster for large strings)
public static boolean isAnagramFreq(String a, String b) {
    if (a.length() != b.length()) return false;

    Map<Character, Integer> freq = new HashMap<>();
    for (char c : a.toLowerCase().toCharArray()) {
        freq.merge(c, 1, Integer::sum);
    }
    for (char c : b.toLowerCase().toCharArray()) {
        freq.merge(c, -1, Integer::sum);
    }
    return freq.values().stream().allMatch(v -> v == 0);
}

// Example:
isAnagram("listen", "silent");  // true
isAnagram("hello", "world");    // false
```

### Group Anagrams (LeetCode #49 — Medium ⭐⭐)
```java
public static Map<String, List<String>> groupAnagrams(String[] words) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String word : words) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);

        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
    }
    return groups;
}

// Example:
groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
// {aet=[eat, tea, ate], ant=[tan, nat], abt=[bat]}
```

---

## 🔥 Pattern 4: Two-Sum (HashMap Lookup)

### Classic Two Sum (LeetCode #1 ⭐⭐⭐)
```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();  // value → index

    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{ map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[]{};  // No solution
}

// Example:
twoSum(new int[]{2, 7, 11, 15}, 9);  // [0, 1]  (2 + 7 = 9)
```

### Subarray Sum Equals K (LeetCode #560)
```java
public static int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);  // Base case: empty subarray
    int sum = 0, count = 0;

    for (int num : nums) {
        sum += num;
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}

// subarraySum(new int[]{1,1,1}, 2)  → 2  ([1,1] at index 0-1 and 1-2)
```

---

## 🔥 Pattern 5: Top-K Elements

### Find K Most Frequent Elements
```java
public static List<Integer> topKFrequent(int[] nums, int k) {
    // Step 1: Count frequencies
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) {
        freq.merge(n, 1, Integer::sum);
    }

    // Step 2: Use a PriorityQueue (min-heap) of size k
    PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
        new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
        minHeap.offer(entry);
        if (minHeap.size() > k) {
            minHeap.poll();  // Remove the least frequent
        }
    }

    List<Integer> result = new ArrayList<>();
    while (!minHeap.isEmpty()) {
        result.add(minHeap.poll().getKey());
    }
    Collections.reverse(result);
    return result;
}

// topKFrequent(new int[]{1,1,1,2,2,3}, 2)  → [1, 2]
```

---

## 📚 Stack & Queue

### Stack (LIFO — Last In, First Out)
```java
import java.util.ArrayDeque;
import java.util.Deque;

// Use ArrayDeque instead of Stack class (more efficient)
Deque<String> stack = new ArrayDeque<>();

stack.push("First");   // Add to top
stack.push("Second");
stack.push("Third");

System.out.println(stack.peek());  // "Third" (look at top, don't remove)
System.out.println(stack.pop());   // "Third" (remove from top)
System.out.println(stack.pop());   // "Second"
System.out.println(stack.size());  // 1
```

### Stack Interview Problem: Valid Parentheses (LeetCode #20 ⭐)
```java
public static boolean isValidParentheses(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> pairs = Map.of(')', '(', '}', '{', ']', '[');

    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {
            stack.push(c);    // Opening bracket → push
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;  // Mismatch or empty stack
            }
        }
    }
    return stack.isEmpty();  // All brackets matched
}

// isValidParentheses("({[]})")  → true
// isValidParentheses("({[}])")  → false
```

### Queue (FIFO — First In, First Out)
```java
import java.util.LinkedList;
import java.util.Queue;

Queue<String> queue = new LinkedList<>();

queue.offer("First");   // Add to end
queue.offer("Second");
queue.offer("Third");

System.out.println(queue.peek());  // "First" (front of queue)
System.out.println(queue.poll());  // "First" (remove from front)
System.out.println(queue.poll());  // "Second"
```

### PriorityQueue (Min-Heap)
```java
import java.util.PriorityQueue;

// Min-heap: smallest first
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(1);
minHeap.offer(3);

System.out.println(minHeap.poll());  // 1 (smallest)
System.out.println(minHeap.poll());  // 3
System.out.println(minHeap.poll());  // 5

// Max-heap: largest first
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
maxHeap.offer(5);
maxHeap.offer(1);
maxHeap.offer(3);

System.out.println(maxHeap.poll());  // 5 (largest)
```

---

## 🔧 `Collections` & `Arrays` Utility Classes

### Collections (for List, Set, Map)
```java
List<Integer> nums = new ArrayList<>(List.of(3, 1, 4, 1, 5));

Collections.sort(nums);               // [1, 1, 3, 4, 5]
Collections.reverse(nums);            // [5, 4, 3, 1, 1]
Collections.shuffle(nums);            // Random order
int min = Collections.min(nums);      // 1
int max = Collections.max(nums);      // 5
int freq = Collections.frequency(nums, 1);  // 2
Collections.swap(nums, 0, 4);
Collections.fill(nums, 0);            // [0, 0, 0, 0, 0]

// Unmodifiable wrappers
List<String> immutable = Collections.unmodifiableList(List.of("A", "B"));
Map<String, Integer> emptyMap = Collections.emptyMap();
List<String> singletonList = Collections.singletonList("only");
```

### Arrays (for arrays)
```java
int[] arr = {3, 1, 4, 1, 5};

Arrays.sort(arr);                     // [1, 1, 3, 4, 5]
int idx = Arrays.binarySearch(arr, 3); // 2
int[] copy = Arrays.copyOf(arr, arr.length);
int[] range = Arrays.copyOfRange(arr, 1, 4);  // [1, 3, 4]
Arrays.fill(arr, 0);                  // [0, 0, 0, 0, 0]
String str = Arrays.toString(arr);     // "[0, 0, 0, 0, 0]"

// Convert array to List
List<Integer> list = Arrays.asList(1, 2, 3);  // Fixed-size list
List<Integer> mutable = new ArrayList<>(Arrays.asList(1, 2, 3)); // Mutable

// Compare arrays
boolean equal = Arrays.equals(new int[]{1,2}, new int[]{1,2});  // true
// Don't use == for arrays!
```

---

## 🔒 Thread-Safe Collections (Preview)

```java
import java.util.concurrent.*;

// Thread-safe versions
Map<String, Integer> safeMap = new ConcurrentHashMap<>();
List<String> safeList = new CopyOnWriteArrayList<>();
Set<String> safeSet = ConcurrentHashMap.newKeySet();

// Or wrap existing collections (less efficient)
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

---

## 💼 Interview Cheat Sheet — Which Collection to Use?

| Problem Type | Collection | Example |
|-------------|-----------|---------|
| Count frequency of elements | `HashMap<T, Integer>` | Word count, char count |
| Find duplicates | `HashSet` | Contains duplicate |
| Check membership (O(1)) | `HashSet` | Two sum complement lookup |
| Key-value pairs | `HashMap` | Phone book, cache |
| Maintain sorted order | `TreeMap` / `TreeSet` | Leaderboard, ranges |
| Preserve insertion order | `LinkedHashMap` / `LinkedHashSet` | LRU cache, first unique |
| LIFO (undo, matching brackets) | `Deque` (as stack) | Valid parentheses |
| FIFO (BFS, scheduling) | `Queue` / `LinkedList` | Level-order traversal |
| Top-K problems | `PriorityQueue` | K most frequent |
| Group by property | `HashMap<K, List<V>>` | Group anagrams |
| Sliding window unique count | `HashMap` | Longest substring without repeat |

---

## 🧪 Try It Yourself — Mini Interview Prep

```java
import java.util.*;

public class CollectionProblems {

    // Problem 1: Find the majority element (appears > n/2 times)
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            count.merge(n, 1, Integer::sum);
            if (count.get(n) > nums.length / 2) return n;
        }
        return -1;
    }

    // Problem 2: Longest consecutive sequence
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int longest = 0;
        for (int n : set) {
            if (!set.contains(n - 1)) {  // Start of a sequence
                int current = n;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }

    // Problem 3: First unique character in a string
    public static int firstUniqueChar(String s) {
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : s.toCharArray()) {
            freq.merge(c, 1, Integer::sum);
        }
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        // Test Problem 1
        System.out.println(majorityElement(new int[]{3,2,3}));  // 3

        // Test Problem 2
        System.out.println(longestConsecutive(new int[]{100,4,200,1,3,2}));  // 4

        // Test Problem 3
        System.out.println(firstUniqueChar("leetcode"));  // 0 (index of 'l')
    }
}
```

---

## 🔑 Key Takeaways
- **Frequency counting** = HashMap + `merge()` or `getOrDefault()`
- **Duplicate detection** = HashSet's `add()` returns `false` for duplicates
- **Two Sum pattern** = HashMap to store complement lookups
- **Anagram problems** = sort characters or use frequency maps
- Use `ArrayDeque` as stack (not `Stack` class) and `LinkedList` as queue
- `PriorityQueue` = min-heap by default; use `Collections.reverseOrder()` for max-heap
- Always think: "Can I trade space for time with a HashSet/HashMap?"
