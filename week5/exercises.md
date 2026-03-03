# Week 5: Exercises

## 🏋️ Exception Handling Exercises

### Exercise 1: Safe Array Operations
Write a class `SafeArray` that wraps an `int[]` and provides safe methods:
- `get(int index)` — returns the element or throws a custom `IndexOutOfRangeException` with a helpful message
- `set(int index, int value)` — sets the element or throws `IndexOutOfRangeException`
- `sum()` — returns the sum of all elements
- `average()` — returns the average, throws `EmptyArrayException` if the array is empty

```java
// Expected usage:
SafeArray arr = new SafeArray(new int[]{10, 20, 30});
System.out.println(arr.get(1));     // 20
System.out.println(arr.sum());      // 60
System.out.println(arr.average());  // 20.0
arr.get(10);  // throws IndexOutOfRangeException: Index 10 out of range [0, 2]
```

---

### Exercise 2: Bank Transaction System
Create a banking system with these custom exceptions:
- `InsufficientFundsException` (checked) — with fields for `requestedAmount` and `currentBalance`
- `InvalidAmountException` (unchecked) — for negative or zero amounts
- `AccountClosedException` (checked) — when operating on a closed account

The `BankAccount` class should have:
- `deposit(double amount)` — throws `InvalidAmountException` if amount ≤ 0
- `withdraw(double amount)` — throws `InsufficientFundsException` or `InvalidAmountException`
- `close()` — marks the account as closed
- All methods throw `AccountClosedException` if the account is closed

```java
// Expected behavior:
BankAccount acc = new BankAccount("Alice", 1000);
acc.deposit(500);       // Balance: 1500
acc.withdraw(200);      // Balance: 1300
acc.withdraw(5000);     // throws InsufficientFundsException
acc.deposit(-100);      // throws InvalidAmountException
acc.close();
acc.deposit(100);       // throws AccountClosedException
```

---

### Exercise 3: Multi-Catch and Finally Tracing
Predict the output of each program:

#### 3a:
```java
public static void main(String[] args) {
    try {
        System.out.println("A");
        String s = null;
        s.length();
    } catch (NullPointerException e) {
        System.out.println("B");
    } finally {
        System.out.println("C");
    }
    System.out.println("D");
}
```

#### 3b:
```java
public static int mystery() {
    int x = 0;
    try {
        x = 1;
        return x;
    } finally {
        x = 2;
    }
}
// What does System.out.println(mystery()) print?
```

#### 3c:
```java
public static void main(String[] args) {
    try {
        method1();
    } catch (Exception e) {
        System.out.println("Caught: " + e.getMessage());
    }
}

static void method1() throws Exception {
    try {
        method2();
    } catch (RuntimeException e) {
        System.out.println("method1 catch: " + e.getMessage());
        throw new Exception("from method1");
    } finally {
        System.out.println("method1 finally");
    }
}

static void method2() {
    throw new RuntimeException("from method2");
}
```

<details>
<summary>Answers</summary>

**3a:** `A B C D`

**3b:** `1` — The return value (1) is computed BEFORE finally runs. Finally changes x to 2, but the return value was already captured.

**3c:**
```
method1 catch: from method2
method1 finally
Caught: from method1
```

</details>

---

## 🏋️ File I/O Exercises

### Exercise 4: Student Grade Processor
Create a program that:
1. Reads a file `grades.txt` with format: `Name,Subject,Score` (one per line)
2. Handles invalid lines gracefully (non-numeric scores, missing fields)
3. Calculates the average score per student
4. Writes a report to `grade_report.txt`

```
// grades.txt:
Alice,Math,85
Alice,Science,92
Bob,Math,NotANumber
Bob,Science,78
Charlie,Math,91
Charlie,Science
Alice,History,88
```

```
// Expected grade_report.txt:
=== Student Grade Report ===
Alice: 88.33 (3 valid grades)
Bob: 78.00 (1 valid grade, 1 error)
Charlie: 91.00 (1 valid grade, 1 error)

Errors encountered:
  Line 3: Invalid score "NotANumber" for Bob in Math
  Line 6: Missing score for Charlie in Science
```

---

### Exercise 5: File Copy Utility
Write a `FileCopier` class with:
- `copy(String source, String dest)` — copies a text file
- `copyWithLineNumbers(String source, String dest)` — copies and prepends line numbers
- `copyFilteredLines(String source, String dest, String keyword)` — copies only lines containing the keyword
- Handle all exceptions: file not found, permission denied, disk full

---

### Exercise 6: Configuration File Manager
Write a `ConfigManager` class that:
1. Reads key=value pairs from a `.properties` file
2. Provides `getString(key)`, `getInt(key)`, `getBoolean(key)` methods
3. Throws custom `ConfigKeyNotFoundException` for missing keys
4. Throws custom `ConfigParseException` for invalid values (e.g., "abc" as an int)
5. Supports default values: `getInt(key, defaultValue)`
6. Can write changes back to the file

---

## 🏋️ Generics Exercises

### Exercise 7: Generic Pair with Operations
Create a `Pair<A, B>` class with:
- Constructor, getters, `toString()`, `equals()`, `hashCode()`
- `swap()` — returns a new `Pair<B, A>` with values swapped
- `map()` — applies functions to both values
- Static factory `of(A, B)` method

```java
// Expected usage:
Pair<String, Integer> p = Pair.of("Alice", 25);
System.out.println(p);           // (Alice, 25)

Pair<Integer, String> swapped = p.swap();
System.out.println(swapped);     // (25, Alice)

Pair<String, String> mapped = p.map(
    name -> name.toUpperCase(),
    age -> age + " years"
);
System.out.println(mapped);      // (ALICE, 25 years)
```

---

### Exercise 8: Generic Collection Utilities
Write a generic `CollectionUtils` class with:

1. `filter(List<T>, Predicate<T>)` → returns filtered list
2. `transform(List<T>, Function<T, R>)` → returns transformed list
3. `reduce(List<T>, T identity, BinaryOperator<T>)` → reduces to single value
4. `partition(List<T>, Predicate<T>)` → returns Pair<List<T>, List<T>> (matching, non-matching)
5. `zip(List<A>, List<B>)` → returns List<Pair<A, B>>
6. `frequency(List<T>)` → returns Map<T, Integer>

```java
// Expected usage:
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> evens = CollectionUtils.filter(nums, n -> n % 2 == 0);
System.out.println(evens);  // [2, 4, 6, 8, 10]

List<String> strings = CollectionUtils.transform(nums, n -> "Item " + n);
System.out.println(strings);  // [Item 1, Item 2, ...]

int sum = CollectionUtils.reduce(nums, 0, Integer::sum);
System.out.println(sum);  // 55

var parts = CollectionUtils.partition(nums, n -> n > 5);
System.out.println(parts.getFirst());   // [6, 7, 8, 9, 10]
System.out.println(parts.getSecond());  // [1, 2, 3, 4, 5]
```

---

### Exercise 9: Generic Bounded Stack
Create a `BoundedStack<T>` that:
- Has a maximum capacity set in the constructor
- `push(T)` throws custom `StackFullException` when full
- `pop()` throws custom `StackEmptyException` when empty
- `peek()`, `size()`, `isEmpty()`, `isFull()`
- `toList()` → returns elements as a List<T>
- `drain()` → removes and returns all elements

---

### Exercise 10: Putting It All Together — Generic CSV Parser
Create a `CsvParser<T>` class that:
1. Reads a CSV file using File I/O
2. Parses each row into objects of type `T` using a `Function<String[], T>` mapper
3. Throws appropriate exceptions for file errors and parsing errors
4. Returns a `List<T>` of parsed objects
5. Supports configurable delimiters

```java
// Expected usage:
CsvParser<Student> parser = new CsvParser<>(
    row -> new Student(row[0], Integer.parseInt(row[1]), Double.parseDouble(row[2]))
);

try {
    List<Student> students = parser.parse("students.csv");
    students.forEach(System.out::println);
} catch (FileNotFoundException e) {
    System.out.println("File not found!");
} catch (CsvParseException e) {
    System.out.println("Parse error at line " + e.getLineNumber() + ": " + e.getMessage());
}
```

---

## 🏆 Challenge Exercises (Interview Level)

### Challenge 1: Exception-Safe Resource Pool
Implement a generic `ResourcePool<T extends AutoCloseable>` that:
- Pre-allocates N resources using a `Supplier<T>`
- `acquire()` → returns a resource (throws PoolExhaustedException if empty)
- `release(T)` → returns a resource to the pool
- Implements `AutoCloseable` to close all resources
- Is thread-safe (use `synchronized`)

### Challenge 2: Generic Event System
Implement a type-safe event system:
- `EventBus` with `subscribe(Class<T>, Consumer<T>)` and `publish(T event)`
- Events can be any type
- Support event hierarchy (subscribing to `Event` receives all event subtypes)
- Use generics to ensure type safety between event type and handler

### Challenge 3: Write Your Own Optional
Implement `MyOptional<T>` from scratch:
- `of(T value)`, `empty()`, `ofNullable(T)`
- `isPresent()`, `isEmpty()`
- `get()` (throws `NoSuchElementException` if empty)
- `orElse(T)`, `orElseGet(Supplier<T>)`, `orElseThrow(Supplier<Exception>)`
- `map(Function<T, R>)`, `flatMap(Function<T, MyOptional<R>>)`
- `filter(Predicate<T>)`
