# Day 7: Advanced Generics & Interview Patterns — Wildcards, PECS & Type Erasure

## 🎯 Learning Goals
- Master wildcards: `?`, `? extends T`, `? super T`
- Understand the PECS principle (Producer Extends, Consumer Super)
- Deep-dive into type erasure and its implications
- Solve common generics interview questions
- Know generic patterns used in the Java Collections Framework

---

## 🃏 Wildcards — The `?` Symbol

Wildcards represent an **unknown type**. They're used when you don't need to refer to the type by name.

### Unbounded Wildcard: `?`
```java
import java.util.List;

// Accepts a List of ANY type
public static void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}

// Usage:
printList(List.of("Hello", "World"));    // ✅
printList(List.of(1, 2, 3));             // ✅
printList(List.of(true, false));         // ✅
```

> **Why not `List<Object>`?** Because `List<String>` is NOT a subtype of `List<Object>`. But `List<String>` IS a subtype of `List<?>`.

### Upper Bounded: `? extends T`
```java
// Accepts List<Number>, List<Integer>, List<Double>, etc.
public static double sumOfList(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) {
        sum += n.doubleValue();
    }
    return sum;
}

// Usage:
System.out.println(sumOfList(List.of(1, 2, 3)));          // 6.0 (Integer)
System.out.println(sumOfList(List.of(1.5, 2.5, 3.5)));    // 7.5 (Double)
System.out.println(sumOfList(List.of(1L, 2L, 3L)));       // 6.0 (Long)
```

### Lower Bounded: `? super T`
```java
// Accepts List<Integer>, List<Number>, List<Object>
public static void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
}

// Usage:
List<Integer> intList = new ArrayList<>();
List<Number> numList = new ArrayList<>();
List<Object> objList = new ArrayList<>();

addIntegers(intList);   // ✅
addIntegers(numList);   // ✅
addIntegers(objList);   // ✅
// addIntegers(new ArrayList<Double>());  // ❌ Double is not Integer or a supertype
```

---

## 🧭 PECS — Producer Extends, Consumer Super

This is the **golden rule** for choosing between `extends` and `super`:

```
╔══════════════════════════════════════════════════════════╗
║  PECS: Producer Extends, Consumer Super                 ║
║                                                         ║
║  If you READ from it  → use ? extends T (PRODUCER)      ║
║  If you WRITE to it   → use ? super T   (CONSUMER)      ║
║  If you do BOTH       → use exact type T (no wildcard)   ║
╚══════════════════════════════════════════════════════════╝
```

### Visual Explanation
```java
// PRODUCER: List produces items for us to read
//           We read FROM this list → ? extends T
public static double sum(List<? extends Number> producer) {
    double total = 0;
    for (Number n : producer) {    // ✅ CAN read
        total += n.doubleValue();
    }
    // producer.add(42);           // ❌ CANNOT write (except null)
    return total;
}

// CONSUMER: List consumes items we give it
//           We write TO this list → ? super T
public static void fillWithInts(List<? super Integer> consumer) {
    consumer.add(1);               // ✅ CAN write Integer
    consumer.add(2);               // ✅ CAN write Integer
    // Integer n = consumer.get(0); // ❌ CANNOT read as Integer (only as Object)
}
```

### Real-World Example: Copy Method
```java
// Copy from source (producer) to destination (consumer)
public static <T> void copy(List<? extends T> source, List<? super T> dest) {
    for (T item : source) {      // Read from source (extends)
        dest.add(item);          // Write to dest (super)
    }
}

// Usage:
List<Integer> ints = List.of(1, 2, 3);
List<Number> nums = new ArrayList<>();
copy(ints, nums);  // Copies Integer list into Number list ✅
```

> **Interview Tip**: `Collections.copy()` in the JDK uses exactly this signature. Being able to explain PECS shows deep understanding.

### Why Can't You Add to `? extends`?

```java
List<? extends Number> list = new ArrayList<Integer>();
// list.add(3.14);  // ❌ What if the list is actually ArrayList<Integer>?
// list.add(42);    // ❌ What if the list is actually ArrayList<Double>?
// The compiler can't know the actual type, so it blocks ALL adds (except null)
```

### Why Can't You Read Typed from `? super`?

```java
List<? super Integer> list = new ArrayList<Number>();
list.add(42);                // ✅ Adding Integer is always safe
Object item = list.get(0);   // Only safe as Object
// Integer n = list.get(0);  // ❌ What if it contains a Double?
```

---

## 🗑️ Type Erasure — What Happens at Runtime

### What Is Type Erasure?
The Java compiler removes all generic type information after compilation. At runtime, there are NO generics — only raw types.

```java
// What you write (compile time):
Box<String> box = new Box<>("Hello");
String s = box.getItem();

// What the JVM sees (after type erasure):
Box box = new Box("Hello");
String s = (String) box.getItem();  // Compiler inserts the cast
```

### Proof: Generics Don't Exist at Runtime
```java
List<String> strings = new ArrayList<>();
List<Integer> integers = new ArrayList<>();

// Both have the SAME class at runtime!
System.out.println(strings.getClass() == integers.getClass());  // true
System.out.println(strings.getClass().getName());  // java.util.ArrayList (no <String>)
```

### Type Erasure Rules

| Generic Type | Erased To |
|-------------|-----------|
| `T` (unbounded) | `Object` |
| `T extends Number` | `Number` |
| `T extends Comparable & Serializable` | `Comparable` (first bound) |
| `List<String>` | `List` |
| `Map<String, Integer>` | `Map` |

### What Type Erasure Breaks
```java
// ❌ Can't check generic type at runtime
if (list instanceof List<String>) { }   // Compile error

// ❌ Can't create generic arrays
T[] array = new T[10];                  // Compile error

// ❌ Can't use .class with generic types
Class<List<String>> cls = List<String>.class;  // Compile error

// ❌ Overloading on generic type doesn't work
void process(List<String> list) { }    // These two
void process(List<Integer> list) { }   // have the same erasure! ❌
```

---

## 🧩 Generic Patterns Used in Java Collections

### Pattern 1: The `Comparable<T>` Pattern
```java
public class Student implements Comparable<Student> {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(this.gpa, other.gpa);
    }

    @Override
    public String toString() {
        return name + " (GPA: " + gpa + ")";
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>(List.of(
            new Student("Alice", 3.8),
            new Student("Bob", 3.2),
            new Student("Charlie", 3.9)
        ));

        Collections.sort(students);  // Uses compareTo
        students.forEach(System.out::println);
        // Bob (GPA: 3.2)
        // Alice (GPA: 3.8)
        // Charlie (GPA: 3.9)
    }
}
```

### Pattern 2: Generic Utility Methods
```java
public class CollectionUtils {

    // Find max in any comparable collection
    public static <T extends Comparable<? super T>> T max(Collection<? extends T> coll) {
        Iterator<? extends T> it = coll.iterator();
        T max = it.next();
        while (it.hasNext()) {
            T next = it.next();
            if (next.compareTo(max) > 0) {
                max = next;
            }
        }
        return max;
    }

    // Convert any collection to a list
    public static <T> List<T> toList(Collection<? extends T> source) {
        return new ArrayList<>(source);
    }

    // Null-safe get
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        V value = map.get(key);
        return value != null ? value : defaultValue;
    }
}
```

### Pattern 3: Generic Builder Pattern
```java
public class GenericBuilder<T> {
    private final Supplier<T> constructor;
    private final List<Consumer<T>> setters = new ArrayList<>();

    public GenericBuilder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public <V> GenericBuilder<T> with(BiConsumer<T, V> setter, V value) {
        setters.add(instance -> setter.accept(instance, value));
        return this;
    }

    public T build() {
        T instance = constructor.get();
        setters.forEach(setter -> setter.accept(instance));
        return instance;
    }
}
```

---

## 🔥 Generics Interview Questions

### Q1: Can you use primitives with generics?
**No.** Generics only work with reference types. Use wrapper classes:
```java
// ❌ Box<int> box = new Box<>(5);
Box<Integer> box = new Box<>(5);     // ✅ Auto-boxing
```

### Q2: What is a raw type?
Using a generic class without type parameters:
```java
List rawList = new ArrayList();   // Raw type — no type safety
rawList.add("hello");
rawList.add(42);                  // No compile error, but unsafe
```

> **Warning**: Raw types exist only for backward compatibility with pre-Java-5 code. Never use them in new code.

### Q3: Why is `List<String>` not a subtype of `List<Object>`?

Because it would break type safety:
```java
List<String> strings = new ArrayList<>();
// If this were allowed:
List<Object> objects = strings;    // ❌ Hypothetical
objects.add(42);                   // Adding Integer to a String list!
String s = strings.get(0);        // 💥 ClassCastException — 42 is not a String
```

### Q4: What's the difference between `<T>` and `<?>`?
```java
// <T> — you can REFER TO the type in the method body
<T> void addToList(List<T> list, T element) {
    list.add(element);  // Can use T
}

// <?> — you CANNOT refer to the type (anonymous)
void printList(List<?> list) {
    // Can only read as Object
    // Cannot add to the list (except null)
}
```

### Q5: Diamond operator `<>`
```java
// Java 7+ — compiler infers the type from the left side
List<String> list = new ArrayList<>();            // ✅ Diamond operator
Map<String, List<Integer>> map = new HashMap<>(); // ✅ Much cleaner!

// Before Java 7 — had to repeat the type
List<String> list = new ArrayList<String>();      // Verbose
```

### Q6: Recursive type bounds
```java
// The Comparable pattern uses recursive type bounds:
public class MyClass implements Comparable<MyClass> {
    @Override
    public int compareTo(MyClass other) { ... }
}

// The generic version:
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
```

### Q7: What does `Class<T>` mean?
```java
// Class<T> is itself generic!
Class<String> stringClass = String.class;
Class<Integer> intClass = Integer.class;

// Useful for type-safe reflection:
public <T> T createInstance(Class<T> clazz) throws Exception {
    return clazz.getDeclaredConstructor().newInstance();
}

String s = createInstance(String.class);  // Returns String, not Object
```

---

## 📊 Quick Reference: Wildcard Cheat Sheet

| Syntax | Name | Can Read As | Can Write | Use When |
|--------|------|-------------|-----------|----------|
| `List<?>` | Unbounded | `Object` | Only `null` | Read-only, any type |
| `List<? extends Number>` | Upper bounded | `Number` | Only `null` | Reading numbers |
| `List<? super Integer>` | Lower bounded | `Object` | `Integer` and below | Adding integers |
| `List<Number>` | Exact type | `Number` | `Number` and below | Read and write |

---

## 🧪 Try It Yourself

### Generic Pair Sorting
```java
import java.util.*;

public class GenericSortDemo {

    // Sort any list of Comparable items
    public static <T extends Comparable<T>> void bubbleSort(List<T> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    // Count items matching a condition
    public static <T> int countIf(List<T> list, java.util.function.Predicate<T> predicate) {
        int count = 0;
        for (T item : list) {
            if (predicate.test(item)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        // Sort strings
        List<String> words = new ArrayList<>(List.of("banana", "apple", "cherry"));
        bubbleSort(words);
        System.out.println(words);  // [apple, banana, cherry]

        // Sort integers
        List<Integer> nums = new ArrayList<>(List.of(5, 2, 8, 1, 9));
        bubbleSort(nums);
        System.out.println(nums);   // [1, 2, 5, 8, 9]

        // Count with predicate
        int evenCount = countIf(nums, n -> n % 2 == 0);
        System.out.println("Even numbers: " + evenCount);  // 2

        int longWords = countIf(words, w -> w.length() > 5);
        System.out.println("Long words: " + longWords);    // 2
    }
}
```

---

## 🔑 Key Takeaways
- **Wildcards** (`?`, `? extends T`, `? super T`) allow flexible method parameters
- **PECS**: Use `extends` to **read** from a collection, `super` to **write** to it
- **Type erasure** removes all generic info at runtime — generics are a compile-time feature only
- `List<String>` is NOT a subtype of `List<Object>` — generics are **invariant**
- But `List<String>` IS a subtype of `List<?>` and `List<? extends Object>`
- Use `<T>` when you need to refer to the type; use `<?>` when you don't
- The diamond operator `<>` lets the compiler infer type arguments
- Never use raw types in new code — always parameterize
- `Comparable<T>` with recursive bounds is a foundational Java pattern
