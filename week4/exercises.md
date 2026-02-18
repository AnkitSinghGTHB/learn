# Week 4 Exercises: Java OOP & Collections

Practice these exercises from beginner → intermediate → advanced. Each section builds on the lessons.

---

## 🟢 Beginner Exercises

### Exercise 1: Create a `Book` Class
Create a `Book` class with fields `title`, `author`, `pages`, and `price`. Include:
- A parameterized constructor
- A `toString()` override
- A method `isLongBook()` that returns `true` if pages > 300

```java
// Expected usage:
Book b = new Book("Clean Code", "Robert Martin", 464, 29.99);
System.out.println(b);            // Book{title='Clean Code', author='Robert Martin', pages=464, price=29.99}
System.out.println(b.isLongBook()); // true
```

---

### Exercise 2: Constructor Overloading
Extend your `Book` class with three constructors:
1. `Book(title, author, pages, price)` — full
2. `Book(title, author)` — defaults: pages=0, price=0.0
3. `Book()` — defaults: "Unknown", "Unknown", 0, 0.0

Use constructor chaining (`this(...)`) to avoid code duplication.

---

### Exercise 3: ArrayList Operations
Create an `ArrayList<String>` of 5 city names. Then:
1. Add a new city at index 2
2. Remove a city by name
3. Check if "Tokyo" is in the list
4. Print all cities using a for-each loop
5. Sort the list alphabetically and print

---

### Exercise 4: HashMap Basics
Create a `HashMap<String, Integer>` to store student names and their scores. Then:
1. Add 5 students
2. Update one student's score
3. Remove one student
4. Print all entries using `entrySet()`
5. Find the student with the highest score

---

### Exercise 5: HashSet for Unique Elements
Given the array `{1, 5, 2, 3, 5, 2, 4, 1, 3}`:
1. Use a `HashSet` to find all unique elements
2. Print them sorted (hint: `TreeSet`)
3. Count how many duplicates were removed

---

## 🟡 Intermediate Exercises

### Exercise 6: Inheritance Hierarchy
Create the following class hierarchy:

```
        Vehicle
       /       \
     Car      Truck
      |
  ElectricCar
```

- `Vehicle`: fields `brand`, `year`, `speed`; methods `accelerate()`, `brake()`, `toString()`
- `Car` adds `numDoors`; overrides `toString()`
- `Truck` adds `payload` (max weight in kg); has `loadCargo(weight)` method
- `ElectricCar` adds `batteryLevel`; overrides `accelerate()` to drain battery

Test with polymorphism:
```java
Vehicle[] garage = { new Car(...), new Truck(...), new ElectricCar(...) };
for (Vehicle v : garage) {
    v.accelerate();
    System.out.println(v);
}
```

---

### Exercise 7: Interface Implementation
Create an interface `Payable` with a method `double calculatePay()`.

Implement it in three classes:
1. `FullTimeEmployee` — monthly salary
2. `PartTimeEmployee` — hourly rate × hours worked
3. `Contractor` — project fee

Write a method `processPayroll(List<Payable> employees)` that calculates and prints total payroll.

---

### Exercise 8: Encapsulated `BankAccount`
Create a fully encapsulated `BankAccount` class:
- Private fields: `accountNumber`, `ownerName`, `balance`
- Public constructor with validation (balance ≥ 0, name not empty)
- Getters for all fields
- `deposit(amount)` — validates positive amount
- `withdraw(amount)` — validates sufficient funds
- `transferTo(BankAccount other, double amount)` — atomic transfer
- `toString()` that masks the account number (show last 4 digits only)

---

### Exercise 9: Word Frequency Counter
Write a program that:
1. Takes a sentence as input
2. Uses a `HashMap<String, Integer>` to count word frequencies
3. Uses a `TreeMap` to print words alphabetically
4. Finds the most frequent word
5. Finds all words that appear only once

Test with: `"the quick brown fox jumps over the lazy fox the fox"`

---

### Exercise 10: Contact Book with Collections
Create a `ContactBook` class that manages contacts using `HashMap<String, Contact>`:
- `Contact` class has: `name`, `phone`, `email`
- Methods: `addContact()`, `removeContact()`, `findByName()`, `findByPhone()`, `listAll()`
- `findByPhone()` should search through all contacts (values) — think about which collection helps here

---

## 🔴 Advanced / Interview Exercises

### Exercise 11: Comparable & Comparator
Create a `Product` class with `name`, `price`, and `rating`.
- Implement `Comparable` to sort by price ascending
- Create `Comparator`s for:
  - Sort by rating descending
  - Sort by name alphabetically
  - Sort by price descending, then by rating descending

Test all orderings with a `List<Product>` and `Collections.sort()`.

---

### Exercise 12: Two Sum (LeetCode #1)
Given an array of integers and a target, return the indices of the two numbers that add up to the target.
- Use a `HashMap` for O(n) time complexity
- Handle the case where no solution exists

```java
twoSum(new int[]{2, 7, 11, 15}, 9)  → [0, 1]
twoSum(new int[]{3, 2, 4}, 6)       → [1, 2]
```

---

### Exercise 13: Group Anagrams (LeetCode #49)
Given an array of strings, group the anagrams together.
- Use a `HashMap<String, List<String>>`
- Key = sorted characters of each word

```java
groupAnagrams({"eat", "tea", "tan", "ate", "nat", "bat"})
→ {aet=[eat, tea, ate], ant=[tan, nat], abt=[bat]}
```

---

### Exercise 14: Valid Parentheses (LeetCode #20)
Given a string containing only `(){}[]`, determine if the input is valid.
- Every open bracket must have a matching close bracket
- Brackets must close in the correct order
- Use a `Deque` (stack)

```java
isValid("()")      → true
isValid("()[]{}")  → true
isValid("(]")      → false
isValid("([)]")    → false
```

---

### Exercise 15: Design a Parking Lot (OOP + Collections)
Design a `ParkingLot` system using OOP principles:

Classes needed:
- `Vehicle` (abstract) → `Car`, `Motorcycle`, `Truck`
- `ParkingSpot` with size (`SMALL`, `MEDIUM`, `LARGE`)
- `ParkingLot` with a `List<ParkingSpot>` and a `Map<String, ParkingSpot>` (plate → spot)

Features:
1. Park a vehicle (find a suitable spot based on vehicle size)
2. Remove a vehicle by license plate
3. Check available spots by size
4. Display all parked vehicles

This is a classic system design interview question!

---

### Exercise 16: Immutable Class
Create an immutable `Transaction` class:
- Fields: `id`, `from`, `to`, `amount`, `timestamp`, `tags` (List<String>)
- Make it truly immutable (defensive copies, no setters, final class)
- Override `equals()`, `hashCode()`, and `toString()`
- Test that modifying the original `tags` list doesn't affect the Transaction

---

### Exercise 17: LRU Cache (LeetCode #146)
Implement a Least Recently Used (LRU) Cache using `LinkedHashMap`:
- `LRUCache(int capacity)` — initialize with positive capacity
- `int get(int key)` — return value if exists, else -1
- `void put(int key, int value)` — insert/update; evict least recently used if over capacity

```java
LRUCache cache = new LRUCache(2);
cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1
```

---

## 🏆 Challenge: Student Management System
Build a complete system combining ALL Week 4 concepts:

1. **Classes**: `Student` (implements `Comparable`), `Course`, `Enrollment`
2. **Inheritance**: `GraduateStudent extends Student`
3. **Interfaces**: `Printable`, `Searchable`
4. **Encapsulation**: All fields private, validated setters
5. **Collections**:
   - `ArrayList<Student>` for course rosters
   - `HashMap<String, Course>` for course catalog
   - `HashSet<Student>` for unique enrollment tracking
   - `TreeSet<Student>` for sorted GPA rankings

Features: enroll, drop, search by name/GPA, print transcript, calculate stats.

See the `student_management/` folder for a starter implementation!

---

## ✅ Completion Tracker
- [ ] Exercise 1: Book class
- [ ] Exercise 2: Constructor overloading
- [ ] Exercise 3: ArrayList operations
- [ ] Exercise 4: HashMap basics
- [ ] Exercise 5: HashSet unique elements
- [ ] Exercise 6: Inheritance hierarchy
- [ ] Exercise 7: Interface Payable
- [ ] Exercise 8: Encapsulated BankAccount
- [ ] Exercise 9: Word frequency counter
- [ ] Exercise 10: Contact book
- [ ] Exercise 11: Comparable & Comparator
- [ ] Exercise 12: Two Sum
- [ ] Exercise 13: Group Anagrams
- [ ] Exercise 14: Valid Parentheses
- [ ] Exercise 15: Parking Lot design
- [ ] Exercise 16: Immutable class
- [ ] Exercise 17: LRU Cache
- [ ] Challenge: Student Management System
