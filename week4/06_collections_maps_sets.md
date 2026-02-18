# Day 6: Collections — Maps & Sets

## 🎯 Learning Goals
- Master `HashMap` for key-value storage
- Understand `HashSet` for unique elements
- Know `TreeMap`, `TreeSet`, and `LinkedHashMap`
- Understand `equals()` and `hashCode()` contract with collections
- See real-world use cases for each collection type

---

## 🗺️ HashMap — Key-Value Pairs

`HashMap<K, V>` stores data as **key-value pairs**. Keys must be unique; values can repeat. O(1) average for get/put.

### Creating & Basic Operations
```java
import java.util.HashMap;
import java.util.Map;

// Creating a HashMap
Map<String, Integer> ages = new HashMap<>();

// ── PUT (Add / Update) ──
ages.put("Alice", 25);
ages.put("Bob", 30);
ages.put("Charlie", 28);
ages.put("Alice", 26);   // Updates Alice's age (keys are unique!)

System.out.println(ages);
// {Alice=26, Bob=30, Charlie=28}  (order NOT guaranteed)

// ── GET (Read) ──
int aliceAge = ages.get("Alice");       // 26
Integer unknownAge = ages.get("Dave");  // null (key doesn't exist)
int safeAge = ages.getOrDefault("Dave", 0);  // 0 (default value)

// ── CONTAINS ──
boolean hasAlice = ages.containsKey("Alice");   // true
boolean hasAge30 = ages.containsValue(30);      // true

// ── REMOVE ──
ages.remove("Bob");                    // Removes Bob
ages.remove("Charlie", 99);           // Does nothing (value doesn't match)
ages.remove("Charlie", 28);           // Removes Charlie

// ── SIZE & EMPTY ──
int size = ages.size();     // 1
boolean empty = ages.isEmpty();  // false
```

### Iterating Over a HashMap
```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Math", 95);
scores.put("Science", 88);
scores.put("English", 92);
scores.put("History", 78);

// Method 1: Iterate over keys
for (String subject : scores.keySet()) {
    System.out.println(subject + ": " + scores.get(subject));
}

// Method 2: Iterate over values only
for (int score : scores.values()) {
    System.out.println(score);
}

// Method 3: Iterate over entries (MOST EFFICIENT for both key and value)
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " → " + entry.getValue());
}

// Method 4: forEach with lambda (Java 8+)
scores.forEach((subject, score) ->
    System.out.println(subject + ": " + score)
);
```

### HashMap Advanced Methods (Java 8+)
```java
Map<String, Integer> wordCount = new HashMap<>();

// ── putIfAbsent — only add if key doesn't exist ──
wordCount.putIfAbsent("hello", 0);   // Adds hello=0
wordCount.putIfAbsent("hello", 99);  // Does nothing (key exists)

// ── merge — combine old and new values ──
wordCount.merge("hello", 1, Integer::sum);  // hello: 0+1 = 1
wordCount.merge("hello", 1, Integer::sum);  // hello: 1+1 = 2
wordCount.merge("world", 1, Integer::sum);  // world: 1 (new key)

// ── compute — calculate a new value from key and old value ──
wordCount.compute("hello", (key, val) -> val == null ? 1 : val + 1);  // hello: 3

// ── computeIfAbsent — great for initializing complex values ──
Map<String, List<String>> groups = new HashMap<>();
groups.computeIfAbsent("fruits", k -> new ArrayList<>()).add("Apple");
groups.computeIfAbsent("fruits", k -> new ArrayList<>()).add("Banana");
// {fruits=[Apple, Banana]}

// ── replaceAll — modify all values ──
scores.replaceAll((subject, score) -> score + 5);  // Add 5 bonus to all
```

---

## 🔄 The `equals()` and `hashCode()` Contract (Interview Critical ⭐⭐⭐)

When you use custom objects as HashMap **keys** or in a HashSet, you MUST override both `equals()` and `hashCode()`.

### How HashMap Works Internally
```
HashMap stores entries in "buckets" based on hashCode():

hashCode() → bucket index → scan bucket with equals()

Step 1: key.hashCode() → hash → bucket index (e.g., bucket 5)
Step 2: Look in bucket 5 for matching key using equals()
```

### The Contract
1. If `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must be true
2. If `hashCode()` is different, `equals()` must return false
3. If `hashCode()` is the same, `equals()` may or may not be true (collision)

```java
public class Student {
    private String name;
    private int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        // Modern way using Objects.hash()
        return java.util.Objects.hash(name, id);
    }
}

// Now works correctly as HashMap key:
Map<Student, String> grades = new HashMap<>();
grades.put(new Student("Alice", 101), "A");
grades.put(new Student("Alice", 101), "A+");  // Updates (same key!)

String grade = grades.get(new Student("Alice", 101));  // "A+" ✅
// Without overriding equals/hashCode, this returns null! ❌
```

> **Golden Rule**: Always override `equals()` AND `hashCode()` together when using custom objects in HashMap/HashSet.

---

## 🔵 HashSet — Unique Elements

`HashSet<E>` stores **unique** elements with no duplicates. O(1) average for add/remove/contains.

```java
import java.util.HashSet;
import java.util.Set;

Set<String> uniqueNames = new HashSet<>();

// ── ADD ──
uniqueNames.add("Alice");   // true (added)
uniqueNames.add("Bob");     // true (added)
uniqueNames.add("Alice");   // false (duplicate — NOT added)

System.out.println(uniqueNames);       // [Alice, Bob]  (no order guarantee)
System.out.println(uniqueNames.size()); // 2

// ── CONTAINS ──
boolean hasAlice = uniqueNames.contains("Alice");  // true

// ── REMOVE ──
uniqueNames.remove("Bob");  // true

// ── ITERATE ──
for (String name : uniqueNames) {
    System.out.println(name);
}

// ── REMOVE DUPLICATES FROM A LIST ──
List<Integer> numbers = List.of(1, 2, 3, 2, 4, 1, 5, 3);
Set<Integer> unique = new HashSet<>(numbers);
System.out.println(unique);  // [1, 2, 3, 4, 5]

// Convert back to list
List<Integer> deduped = new ArrayList<>(unique);
```

### Set Operations (Math-like)
```java
Set<String> setA = new HashSet<>(Set.of("Apple", "Banana", "Cherry"));
Set<String> setB = new HashSet<>(Set.of("Banana", "Date", "Cherry"));

// UNION (A ∪ B) — all elements from both
Set<String> union = new HashSet<>(setA);
union.addAll(setB);
// [Apple, Banana, Cherry, Date]

// INTERSECTION (A ∩ B) — common elements
Set<String> intersection = new HashSet<>(setA);
intersection.retainAll(setB);
// [Banana, Cherry]

// DIFFERENCE (A - B) — elements in A but not in B
Set<String> difference = new HashSet<>(setA);
difference.removeAll(setB);
// [Apple]

// SYMMETRIC DIFFERENCE (A ⊕ B) — elements in one but not both
Set<String> symDiff = new HashSet<>(setA);
symDiff.addAll(setB);
Set<String> common = new HashSet<>(setA);
common.retainAll(setB);
symDiff.removeAll(common);
// [Apple, Date]
```

---

## 🌳 TreeMap & TreeSet — Sorted Collections

### TreeMap — Sorted Keys
```java
import java.util.TreeMap;

Map<String, Integer> sortedScores = new TreeMap<>();
sortedScores.put("Charlie", 88);
sortedScores.put("Alice", 95);
sortedScores.put("Bob", 92);

System.out.println(sortedScores);
// {Alice=95, Bob=92, Charlie=88}  ← Alphabetically sorted by key!

// TreeMap-specific methods
System.out.println(sortedScores.firstKey());    // "Alice"
System.out.println(sortedScores.lastKey());     // "Charlie"
System.out.println(sortedScores.higherKey("Bob"));  // "Charlie"
System.out.println(sortedScores.lowerKey("Bob"));   // "Alice"
```

### TreeSet — Sorted Unique Elements
```java
import java.util.TreeSet;

Set<Integer> sortedNums = new TreeSet<>();
sortedNums.add(42);
sortedNums.add(7);
sortedNums.add(23);
sortedNums.add(7);   // Duplicate — ignored

System.out.println(sortedNums);  // [7, 23, 42]  ← Always sorted!

// TreeSet-specific methods
TreeSet<Integer> tree = new TreeSet<>(sortedNums);
System.out.println(tree.first());    // 7
System.out.println(tree.last());     // 42
System.out.println(tree.higher(7));  // 23 (strictly greater)
System.out.println(tree.lower(42));  // 23 (strictly less)
System.out.println(tree.ceiling(8)); // 23 (greater than or equal)
System.out.println(tree.floor(40));  // 23 (less than or equal)
```

---

## 🔗 LinkedHashMap & LinkedHashSet — Insertion Order

```java
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

// LinkedHashMap — maintains insertion order
Map<String, Integer> ordered = new LinkedHashMap<>();
ordered.put("Charlie", 88);
ordered.put("Alice", 95);
ordered.put("Bob", 92);

System.out.println(ordered);
// {Charlie=88, Alice=95, Bob=92}  ← Insertion order preserved!

// LinkedHashSet — unique + insertion order
Set<String> orderedSet = new LinkedHashSet<>();
orderedSet.add("Third");
orderedSet.add("First");
orderedSet.add("Second");

System.out.println(orderedSet);
// [Third, First, Second]  ← Insertion order preserved!
```

### LRU Cache with LinkedHashMap
```java
// Access-order LinkedHashMap (least recently used first)
Map<String, String> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > 3;  // Max 3 entries
    }
};

lruCache.put("A", "1");
lruCache.put("B", "2");
lruCache.put("C", "3");
lruCache.get("A");          // Access A → moves to end
lruCache.put("D", "4");     // Evicts B (least recently used)

System.out.println(lruCache);  // {C=3, A=1, D=4}
```

---

## 📊 Collection Comparison Chart (Interview Reference ⭐)

| Collection | Ordered? | Sorted? | Duplicates? | Null? | Thread-safe? | Performance |
|-----------|----------|---------|-------------|-------|-------------|-------------|
| `ArrayList` | ✅ Index | ❌ | ✅ | ✅ | ❌ | O(1) get, O(n) search |
| `LinkedList` | ✅ Index | ❌ | ✅ | ✅ | ❌ | O(1) add/remove head |
| `HashMap` | ❌ | ❌ | V: ✅, K: ❌ | K: 1 null | ❌ | O(1) avg |
| `LinkedHashMap` | ✅ Insertion | ❌ | V: ✅, K: ❌ | K: 1 null | ❌ | O(1) avg |
| `TreeMap` | ✅ Sorted | ✅ | V: ✅, K: ❌ | ❌ | ❌ | O(log n) |
| `HashSet` | ❌ | ❌ | ❌ | 1 null | ❌ | O(1) avg |
| `LinkedHashSet` | ✅ Insertion | ❌ | ❌ | 1 null | ❌ | O(1) avg |
| `TreeSet` | ✅ Sorted | ✅ | ❌ | ❌ | ❌ | O(log n) |

### When to Use What?
| Need | Use |
|------|-----|
| Dynamic list, frequent random access | `ArrayList` |
| Key-value lookup, fast | `HashMap` |
| Unique elements, fast lookup | `HashSet` |
| Sorted keys | `TreeMap` |
| Sorted unique elements | `TreeSet` |
| Preserve insertion order + uniqueness | `LinkedHashSet` |
| Preserve insertion order + key-value | `LinkedHashMap` |
| LRU Cache | `LinkedHashMap` (access order) |

---

## 🧪 Try It Yourself

```java
import java.util.*;

public class PhoneBook {
    private Map<String, String> contacts;

    public PhoneBook() {
        contacts = new TreeMap<>();  // Sorted by name
    }

    public void addContact(String name, String phone) {
        contacts.put(name, phone);
        System.out.println("Added: " + name + " → " + phone);
    }

    public String lookup(String name) {
        return contacts.getOrDefault(name, "Not found");
    }

    public void removeContact(String name) {
        if (contacts.remove(name) != null) {
            System.out.println("Removed: " + name);
        } else {
            System.out.println(name + " not in contacts");
        }
    }

    public Set<String> searchByPrefix(String prefix) {
        Set<String> results = new LinkedHashSet<>();
        for (String name : contacts.keySet()) {
            if (name.toLowerCase().startsWith(prefix.toLowerCase())) {
                results.add(name);
            }
        }
        return results;
    }

    public void printAll() {
        contacts.forEach((name, phone) ->
            System.out.println("  " + name + ": " + phone)
        );
    }

    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.addContact("Alice", "555-0101");
        pb.addContact("Bob", "555-0202");
        pb.addContact("Anna", "555-0303");
        pb.addContact("Charlie", "555-0404");

        System.out.println("\nAll contacts:");
        pb.printAll();

        System.out.println("\nLookup Bob: " + pb.lookup("Bob"));
        System.out.println("Search 'A': " + pb.searchByPrefix("A"));
    }
}
```

---

## 🔑 Key Takeaways
- **HashMap**: O(1) key-value pairs, no ordering guarantee
- **TreeMap**: sorted by keys, O(log n)
- **LinkedHashMap**: insertion-order, useful for LRU cache
- **HashSet**: O(1) unique elements
- **TreeSet**: sorted unique elements
- Override `equals()` AND `hashCode()` when using custom objects as keys/elements
- Use `getOrDefault()`, `putIfAbsent()`, `merge()`, `computeIfAbsent()` for cleaner code
- Program to interfaces: `Map<K,V>`, `Set<E>`, `List<E>`
