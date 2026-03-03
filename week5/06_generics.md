# Day 6: Generics — Type Parameters, Bounded Types & Generic Methods

## 🎯 Learning Goals
- Understand why generics exist and the problems they solve
- Create generic classes with type parameters (`<T>`)
- Write generic methods
- Use bounded type parameters (`<T extends Comparable<T>>`)
- Understand generic interfaces
- Know the rules around generics and inheritance

---

## 🔥 Why Generics?

### The Problem: Without Generics (Pre-Java 5)
```java
// Without generics — everything is Object
List list = new ArrayList();
list.add("Hello");
list.add(42);         // No compile-time check 😱
list.add(true);

String s = (String) list.get(0);  // Works
String x = (String) list.get(1);  // 💥 ClassCastException at RUNTIME!
```

### The Solution: With Generics
```java
// With generics — type-safe at COMPILE TIME
List<String> list = new ArrayList<>();
list.add("Hello");
// list.add(42);     // ❌ Compile error! Can only add Strings

String s = list.get(0);  // No cast needed ✅
```

> **Key Insight**: Generics move type errors from **runtime** (crashes in production) to **compile-time** (caught before running). This is called **type safety**.

---

## 📦 Creating a Generic Class

### Basic Syntax
```java
//          ↓ Type parameter (placeholder for a type)
public class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Box[" + item + "]";
    }
}
```

### Usage — T Gets Replaced
```java
// T = String
Box<String> stringBox = new Box<>("Hello");
String s = stringBox.getItem();  // No cast needed
System.out.println(stringBox);   // Box[Hello]

// T = Integer
Box<Integer> intBox = new Box<>(42);
int n = intBox.getItem();        // Auto-unboxing
System.out.println(intBox);      // Box[42]

// T = List<String>
Box<List<String>> listBox = new Box<>(List.of("a", "b", "c"));
List<String> items = listBox.getItem();
```

> **Convention**: Common type parameter names:
> | Letter | Meaning |
> |--------|---------|
> | `T` | Type |
> | `E` | Element (used in collections) |
> | `K` | Key |
> | `V` | Value |
> | `N` | Number |
> | `R` | Return type |

---

## 🔧 Multiple Type Parameters

```java
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}

// Usage:
Pair<String, Integer> nameAge = new Pair<>("Alice", 25);
Pair<Integer, Boolean> idActive = new Pair<>(101, true);

System.out.println(nameAge);    // Alice = 25
System.out.println(nameAge.getKey());    // Alice
System.out.println(nameAge.getValue());  // 25
```

### Triple
```java
public class Triple<A, B, C> {
    private A first;
    private B second;
    private C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}

// Usage:
Triple<String, Integer, Double> student = new Triple<>("Alice", 20, 3.8);
System.out.println(student);  // (Alice, 20, 3.8)
```

---

## 🏗️ Generic Methods

You can make individual methods generic without making the entire class generic:

```java
public class Utilities {

    // Generic method — <T> declares the type parameter
    public static <T> void printArray(T[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // Generic method that returns a value
    public static <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    // Multiple type parameters in a method
    public static <K, V> boolean containsEntry(Map<K, V> map, K key, V value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    public static void main(String[] args) {
        // Java infers T from the argument type
        printArray(new String[]{"Hello", "World"});     // [Hello, World]
        printArray(new Integer[]{1, 2, 3, 4, 5});       // [1, 2, 3, 4, 5]
        printArray(new Double[]{1.1, 2.2, 3.3});        // [1.1, 2.2, 3.3]

        String first = getFirst(List.of("a", "b", "c"));
        System.out.println(first);  // a
    }
}
```

> **Note**: The `<T>` before the return type **declares** the type parameter. Without it, `T` is just a class name lookup:
> ```java
> public static <T> T getFirst(List<T> list) { ... }
> //            ^^^  ← This declares T
> //                 ^ ← This uses T as return type
> ```

---

## 📐 Bounded Type Parameters

### Upper Bound: `extends`

Restrict `T` to a specific type or its subclasses:

```java
// T must be a Number or its subclass (Integer, Double, Float, etc.)
public class NumberBox<T extends Number> {
    private T value;

    public NumberBox(T value) {
        this.value = value;
    }

    public double doubleValue() {
        return value.doubleValue();  // Can call Number methods because T extends Number
    }

    public boolean isPositive() {
        return value.doubleValue() > 0;
    }
}

// Usage:
NumberBox<Integer> intBox = new NumberBox<>(42);
NumberBox<Double> doubleBox = new NumberBox<>(3.14);
// NumberBox<String> stringBox = new NumberBox<>("hello");  // ❌ Compile error!

System.out.println(intBox.doubleValue());   // 42.0
System.out.println(doubleBox.isPositive()); // true
```

### Multiple Bounds
```java
// T must implement BOTH Comparable AND Serializable
public class SortableBox<T extends Comparable<T> & java.io.Serializable> {
    private T item;

    public SortableBox(T item) {
        this.item = item;
    }

    public boolean isGreaterThan(T other) {
        return item.compareTo(other) > 0;  // Can call compareTo because T extends Comparable
    }
}
```

> **Rule**: When using multiple bounds, the **class** must come first, then **interfaces**:
> ```java
> <T extends SomeClass & Interface1 & Interface2>  // ✅ Correct
> <T extends Interface1 & SomeClass>               // ❌ Class must be first
> ```

---

## 🔄 Bounded Generic Methods

### Finding the Maximum
```java
public class GenericUtils {

    // T must be Comparable so we can use compareTo()
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    public static <T extends Comparable<T>> T max(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        T result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(result) > 0) {
                result = array[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(max(3, 7));          // 7
        System.out.println(max("apple", "banana"));  // banana
        System.out.println(max(3.14, 2.71));    // 3.14

        Integer[] nums = {5, 2, 8, 1, 9, 3};
        System.out.println(max(nums));          // 9
    }
}
```

---

## 🏛️ Generic Interfaces

```java
// Generic interface
public interface Repository<T> {
    void save(T item);
    T findById(int id);
    List<T> findAll();
    void delete(int id);
}

// Implementing with a concrete type
public class UserRepository implements Repository<User> {
    private Map<Integer, User> storage = new HashMap<>();

    @Override
    public void save(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public User findById(int id) {
        return storage.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }
}

// Implementing with a still-generic type
public class InMemoryRepository<T> implements Repository<T> {
    private Map<Integer, T> storage = new HashMap<>();
    // ... implement methods generically
}
```

---

## ⚠️ Generics Restrictions — What You CAN'T Do

```java
public class GenericRestrictions<T> {

    // ❌ Cannot create instances of type parameter
    // T obj = new T();

    // ❌ Cannot create arrays of parameterized types
    // T[] arr = new T[10];

    // ❌ Cannot use primitive types
    // Box<int> box = new Box<>(5);          // ❌ Use Integer instead
    Box<Integer> box = new Box<>(5);         // ✅ Auto-boxing

    // ❌ Cannot use instanceof with generic types
    // if (obj instanceof T) { }

    // ❌ Cannot create static fields of type T
    // static T instance;

    // ❌ Cannot throw or catch generic types
    // catch (T e) { }
}
```

### Why These Restrictions? — Type Erasure (Preview)
At compile time, the compiler replaces `T` with `Object` (or the bound). This is called **type erasure**. At runtime, there's no `T` — just `Object`. That's why you can't do `new T()` — the JVM doesn't know what class `T` is.

```java
// What you write:
Box<String> box = new Box<>("Hello");

// What the compiler produces (after type erasure):
Box box = new Box("Hello");  // T → Object
```

> We'll explore type erasure in depth in Day 7.

---

## 🧩 Generic Class with Comparable — Sorting Pattern

```java
public class SortedPair<T extends Comparable<T>> {
    private T first;
    private T second;

    public SortedPair(T a, T b) {
        if (a.compareTo(b) <= 0) {
            this.first = a;
            this.second = b;
        } else {
            this.first = b;
            this.second = a;
        }
    }

    public T getMin() { return first; }
    public T getMax() { return second; }

    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }

    public static void main(String[] args) {
        SortedPair<Integer> nums = new SortedPair<>(7, 3);
        System.out.println(nums);            // [3, 7]
        System.out.println(nums.getMin());   // 3
        System.out.println(nums.getMax());   // 7

        SortedPair<String> words = new SortedPair<>("banana", "apple");
        System.out.println(words);           // [apple, banana]
    }
}
```

---

## 🧪 Try It Yourself

### Generic Stack Implementation
```java
public class GenericStack<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public GenericStack() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void push(T element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        T element = (T) elements[--size];
        elements[size] = null;  // Help garbage collection
        return element;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return (T) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Object[] newArray = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    public static void main(String[] args) {
        GenericStack<String> stack = new GenericStack<>();
        stack.push("Java");
        stack.push("Python");
        stack.push("Go");

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());  // Go, Python, Java (LIFO)
        }

        GenericStack<Integer> numStack = new GenericStack<>();
        numStack.push(10);
        numStack.push(20);
        System.out.println("Top: " + numStack.peek());  // 20
        System.out.println("Size: " + numStack.size());  // 2
    }
}
```

---

## 🔑 Key Takeaways
- Generics provide **compile-time type safety** — catch errors before running
- Use `<T>` to create **generic classes**, **generic methods**, and **generic interfaces**
- **Bounded types** (`<T extends Comparable<T>>`) restrict what types `T` can be
- Multiple bounds use `&`: `<T extends Number & Comparable<T>>`
- Generics work with **reference types only** — use `Integer` not `int`
- Common conventions: `T` (Type), `E` (Element), `K` (Key), `V` (Value)
- You cannot use `new T()`, `T[]`, or `instanceof T` due to **type erasure**
- Generic interfaces like `Repository<T>` enable the **repository pattern**
