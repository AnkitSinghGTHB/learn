# Day 5: Collections — ArrayList & Lists

## 🎯 Learning Goals
- Understand the Collections Framework hierarchy
- Master `ArrayList` operations (add, remove, search, sort)
- Know `LinkedList` and when to use it
- Use the `List` interface for flexible code
- Understand autoboxing and wrapper classes

---

## 🗂️ The Collections Framework — Big Picture

```
                    Iterable
                       |
                   Collection
                  /    |     \
               List   Set   Queue
              / |       |       \
     ArrayList |    HashSet   PriorityQueue
       LinkedList  TreeSet
                   LinkedHashSet
```

And separately: `Map` (not a Collection, but part of the framework)
```
                     Map
                   /  |  \
           HashMap TreeMap LinkedHashMap
```

### Arrays vs Collections
| Feature | Array | ArrayList |
|---------|-------|-----------|
| Size | Fixed | Dynamic (grows automatically) |
| Types | Primitives + Objects | Objects only (autoboxing for primitives) |
| Syntax | `int[]` | `ArrayList<Integer>` |
| Length | `.length` | `.size()` |
| Add/Remove | ❌ Can't | ✅ `add()`, `remove()` |
| Performance | Faster (no overhead) | Slightly slower (wrapper objects) |
| Memory | Less | More (object overhead) |

---

## 📋 ArrayList — The Workhorse

`ArrayList` is a **resizable array**. It's the most commonly used collection.

### Creating an ArrayList
```java
import java.util.ArrayList;
import java.util.List;

// Type-safe with generics
ArrayList<String> names = new ArrayList<>();
ArrayList<Integer> numbers = new ArrayList<>();  // Integer, not int!

// Using the List interface (PREFERRED — more flexible)
List<String> fruits = new ArrayList<>();

// Initialize with values
List<String> colors = new ArrayList<>(List.of("Red", "Green", "Blue"));

// Initialize with initial capacity (performance optimization)
List<Integer> bigList = new ArrayList<>(1000);
```

### Core Operations — CRUD
```java
List<String> fruits = new ArrayList<>();

// ── CREATE (Add) ──
fruits.add("Apple");             // [Apple]
fruits.add("Banana");            // [Apple, Banana]
fruits.add("Cherry");            // [Apple, Banana, Cherry]
fruits.add(1, "Blueberry");      // [Apple, Blueberry, Banana, Cherry]
                                 //          ↑ inserted at index 1

// ── READ ──
String first = fruits.get(0);           // "Apple"
int size = fruits.size();               // 4
boolean hasBanana = fruits.contains("Banana");  // true
int bananaIndex = fruits.indexOf("Banana");     // 2
boolean empty = fruits.isEmpty();               // false

// ── UPDATE ──
fruits.set(0, "Avocado");  // [Avocado, Blueberry, Banana, Cherry]

// ── DELETE ──
fruits.remove("Banana");   // [Avocado, Blueberry, Cherry]  (by value)
fruits.remove(0);          // [Blueberry, Cherry]            (by index)
fruits.clear();            // []                             (remove all)
```

### ⚠️ Remove Gotcha with Integers
```java
List<Integer> nums = new ArrayList<>(List.of(10, 20, 30));

nums.remove(1);              // Removes element at INDEX 1 → removes 20
// Result: [10, 30]

nums.remove(Integer.valueOf(10));  // Removes the VALUE 10
// Result: [30]
```

> **Important**: `remove(int)` removes by **index**. `remove(Object)` removes by **value**. For `Integer` lists, use `Integer.valueOf()` to remove by value.

---

## 🔄 Iterating Over Lists

```java
List<String> fruits = new ArrayList<>(List.of("Apple", "Banana", "Cherry"));

// Method 1: Enhanced for loop (MOST COMMON)
for (String fruit : fruits) {
    System.out.println(fruit);
}

// Method 2: Traditional for loop (when you need the index)
for (int i = 0; i < fruits.size(); i++) {
    System.out.println(i + ": " + fruits.get(i));
}

// Method 3: forEach with lambda (Java 8+ — clean one-liners)
fruits.forEach(fruit -> System.out.println(fruit));

// Method 4: forEach with method reference
fruits.forEach(System.out::println);

// Method 5: Iterator (needed for safe removal during iteration)
Iterator<String> it = fruits.iterator();
while (it.hasNext()) {
    String fruit = it.next();
    if (fruit.startsWith("B")) {
        it.remove();  // Safe removal during iteration!
    }
}
```

### ⚠️ ConcurrentModificationException
```java
// ❌ WRONG — modifying while iterating with for-each
for (String fruit : fruits) {
    if (fruit.equals("Banana")) {
        fruits.remove(fruit);  // 💥 ConcurrentModificationException!
    }
}

// ✅ CORRECT — use Iterator.remove()
Iterator<String> it = fruits.iterator();
while (it.hasNext()) {
    if (it.next().equals("Banana")) {
        it.remove();  // Safe!
    }
}

// ✅ CORRECT — use removeIf (Java 8+, cleanest)
fruits.removeIf(f -> f.equals("Banana"));
```

---

## 📊 Sorting Lists

```java
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

// ── Sort Strings ──
List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));
Collections.sort(names);             // [Alice, Bob, Charlie]  (alphabetical)
Collections.sort(names, Collections.reverseOrder());  // [Charlie, Bob, Alice]

// ── Sort using .sort() method (Java 8+) ──
names.sort(null);                             // Natural order
names.sort(Comparator.reverseOrder());        // Reverse
names.sort(Comparator.comparing(String::length));  // By length

// ── Sort numbers ──
List<Integer> nums = new ArrayList<>(List.of(42, 7, 23, 1, 99));
Collections.sort(nums);   // [1, 7, 23, 42, 99]

// ── Sort custom objects ──
List<Student> students = new ArrayList<>();
students.add(new Student("Alice", 20, 3.8));
students.add(new Student("Bob", 22, 3.5));
students.add(new Student("Charlie", 19, 3.9));

// Option A: If Student implements Comparable
Collections.sort(students);

// Option B: Custom comparator
students.sort(Comparator.comparing(s -> s.gpa));                    // Ascending GPA
students.sort(Comparator.comparingDouble(s -> s.gpa).reversed());   // Descending GPA
students.sort(Comparator.comparing((Student s) -> s.name));         // By name

// Multi-level sort: by GPA descending, then by name
students.sort(Comparator.comparingDouble((Student s) -> s.gpa)
                         .reversed()
                         .thenComparing(s -> s.name));
```

---

## 🔍 Searching Lists

```java
List<Integer> nums = new ArrayList<>(List.of(5, 3, 8, 1, 9, 2, 7));

// Linear search
boolean found = nums.contains(8);       // true — O(n)
int index = nums.indexOf(8);            // 2 — first occurrence
int lastIndex = nums.lastIndexOf(8);    // 2 — last occurrence

// Binary search (list MUST be sorted first!)
Collections.sort(nums);                  // [1, 2, 3, 5, 7, 8, 9]
int pos = Collections.binarySearch(nums, 7);  // 4 — O(log n)
```

---

## 📦 Autoboxing & Wrapper Classes

Java collections can't store primitives — they need **objects**. Java automatically converts between primitives and their wrapper classes.

```java
// Autoboxing: int → Integer (automatic)
List<Integer> nums = new ArrayList<>();
nums.add(5);    // int 5 is auto-boxed to Integer.valueOf(5)
nums.add(10);
nums.add(15);

// Unboxing: Integer → int (automatic)
int first = nums.get(0);  // Integer unboxed to int

// All wrapper classes:
// int     → Integer
// double  → Double
// boolean → Boolean
// char    → Character
// long    → Long
// float   → Float
// byte    → Byte
// short   → Short
```

### ⚠️ Autoboxing Pitfalls
```java
// Null unboxing  — NullPointerException!
Integer x = null;
int y = x;  // 💥 NullPointerException

// Performance — creating many wrapper objects in loops
// ❌ Slow
List<Integer> nums = new ArrayList<>();
for (int i = 0; i < 1_000_000; i++) {
    nums.add(i);  // Creates 1 million Integer objects
}

// ✅ For pure number crunching, use int[] instead
```

---

## 🔗 LinkedList vs ArrayList

```java
import java.util.LinkedList;

List<String> arrayList = new ArrayList<>();
List<String> linkedList = new LinkedList<>();
```

| Operation | ArrayList | LinkedList |
|-----------|----------|------------|
| `get(i)` — random access | ⚡ O(1) | 🐌 O(n) |
| `add(end)` — append | ⚡ O(1)* | ⚡ O(1) |
| `add(0, x)` — insert at front | 🐌 O(n) | ⚡ O(1) |
| `remove(0)` — remove from front | 🐌 O(n) | ⚡ O(1) |
| `remove(middle)` | 🐌 O(n) | ⚡ O(1)** |
| Memory | Less (contiguous) | More (node + pointers) |

> \* Amortized. \*\* Once you have the node reference.

### Rule of Thumb
- **Default**: Use `ArrayList` (faster for most operations)
- Use `LinkedList` only when you do **lots of insertions/removals at the beginning or middle** and don't need random access
- In practice, `ArrayList` wins almost always due to CPU cache locality

---

## 🛠️ Useful List Methods

```java
List<Integer> nums = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));

// Convert to array
Integer[] arr = nums.toArray(new Integer[0]);

// Create a sublist (view, not a copy!)
List<Integer> sub = nums.subList(2, 5);  // [4, 1, 5]

// Unmodifiable list
List<String> immutable = List.of("A", "B", "C");           // Java 9+
List<String> immutable2 = Collections.unmodifiableList(names);  // Pre-Java 9

// Copy a list
List<Integer> copy = new ArrayList<>(nums);

// Fill / Replace all
Collections.fill(copy, 0);  // [0, 0, 0, 0, 0, 0, 0, 0]

// Frequency
int count = Collections.frequency(nums, 1);  // 2 (1 appears twice)

// Min / Max
int min = Collections.min(nums);  // 1
int max = Collections.max(nums);  // 9

// Shuffle (randomize)
Collections.shuffle(nums);

// Swap
Collections.swap(nums, 0, nums.size() - 1);  // Swap first and last

// Reverse
Collections.reverse(nums);
```

---

## 🧪 Try It Yourself

```java
import java.util.*;

public class GroceryList {
    private List<String> items;

    public GroceryList() {
        items = new ArrayList<>();
    }

    public void addItem(String item) {
        if (!items.contains(item)) {
            items.add(item);
            System.out.println("Added: " + item);
        } else {
            System.out.println(item + " is already in the list!");
        }
    }

    public void removeItem(String item) {
        if (items.remove(item)) {
            System.out.println("Removed: " + item);
        } else {
            System.out.println(item + " not found!");
        }
    }

    public void printSorted() {
        List<String> sorted = new ArrayList<>(items);
        Collections.sort(sorted);
        System.out.println("Grocery List (sorted): " + sorted);
    }

    public static void main(String[] args) {
        GroceryList grocery = new GroceryList();
        grocery.addItem("Milk");
        grocery.addItem("Eggs");
        grocery.addItem("Bread");
        grocery.addItem("Milk");        // Already exists
        grocery.printSorted();           // [Bread, Eggs, Milk]
        grocery.removeItem("Eggs");
        grocery.printSorted();           // [Bread, Milk]
    }
}
```

---

## 🔑 Key Takeaways
- **ArrayList** = dynamic array; use for most situations
- **LinkedList** = doubly-linked list; use for frequent insert/remove at head
- Always program to the **interface**: `List<String> list = new ArrayList<>()`
- Use `Iterator.remove()` or `removeIf()` when removing during iteration
- Autoboxing converts `int ↔ Integer` automatically (watch for null!)
- `Collections` utility class: `sort()`, `reverse()`, `shuffle()`, `min()`, `max()`, `frequency()`
