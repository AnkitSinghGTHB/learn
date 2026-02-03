# Lesson 01: Python Data Structures

## 📚 Overview
Python provides four core built-in data structures. Understanding when to use each is key to writing efficient, readable code.

---

## 1. Lists `[]`
**Ordered, mutable, allows duplicates**

```python
# Creating lists
fruits = ["apple", "banana", "cherry"]
numbers = [1, 2, 3, 4, 5]
mixed = [1, "hello", 3.14, True]

# Accessing elements (0-indexed)
print(fruits[0])      # "apple"
print(fruits[-1])     # "cherry" (last element)

# Slicing
print(fruits[0:2])    # ["apple", "banana"]
print(fruits[::2])    # Every 2nd element

# Modifying
fruits.append("orange")        # Add to end
fruits.insert(1, "mango")      # Insert at index
fruits.remove("banana")        # Remove by value
popped = fruits.pop()          # Remove & return last
fruits[0] = "grape"            # Update by index

# Useful methods
len(fruits)                    # Length
fruits.sort()                  # Sort in place
fruits.reverse()               # Reverse in place
"apple" in fruits              # Check membership
fruits.index("cherry")         # Find index
fruits.count("apple")          # Count occurrences
```

### When to use Lists:
- You need an **ordered** collection
- You need to **modify** the collection
- You need to allow **duplicates**
- You need **index-based** access

---

## 2. Tuples `()`
**Ordered, immutable, allows duplicates**

```python
# Creating tuples
coordinates = (10, 20)
rgb = (255, 128, 0)
single = (42,)  # Note: comma needed for single element

# Accessing (same as lists)
print(coordinates[0])   # 10
print(rgb[-1])          # 0

# Unpacking
x, y = coordinates
r, g, b = rgb

# Tuples are IMMUTABLE - this would error:
# coordinates[0] = 15  # TypeError!

# Why use tuples?
# 1. Faster than lists
# 2. Can be used as dictionary keys
# 3. Signal that data shouldn't change
```

### When to use Tuples:
- Data that **shouldn't change** (coordinates, RGB values)
- **Dictionary keys** (lists can't be keys!)
- **Function returns** with multiple values
- **Unpacking** assignments

---

## 3. Sets `{}`
**Unordered, mutable, NO duplicates**

```python
# Creating sets
unique_numbers = {1, 2, 3, 3, 3}  # Results in {1, 2, 3}
empty_set = set()  # NOT {} (that's an empty dict!)

# Adding/removing
unique_numbers.add(4)
unique_numbers.remove(1)      # Raises error if not found
unique_numbers.discard(99)    # No error if not found

# Set operations (this is where sets shine!)
a = {1, 2, 3, 4}
b = {3, 4, 5, 6}

print(a | b)   # Union: {1, 2, 3, 4, 5, 6}
print(a & b)   # Intersection: {3, 4}
print(a - b)   # Difference: {1, 2}
print(a ^ b)   # Symmetric difference: {1, 2, 5, 6}

# Fast membership testing
print(3 in a)  # True (O(1) average time!)
```

### When to use Sets:
- **Remove duplicates** from a list: `list(set(my_list))`
- **Fast membership testing** (much faster than lists)
- **Mathematical set operations** (union, intersection, etc.)
- When **order doesn't matter**

---

## 4. Dictionaries `{key: value}`
**Ordered (Python 3.7+), mutable, NO duplicate keys**

```python
# Creating dictionaries
person = {
    "name": "Alice",
    "age": 30,
    "city": "Mumbai"
}

# Accessing values
print(person["name"])           # "Alice"
print(person.get("email"))      # None (safe access)
print(person.get("email", "N/A"))  # "N/A" (with default)

# Modifying
person["age"] = 31              # Update existing
person["email"] = "a@test.com"  # Add new key
del person["city"]              # Delete key
popped = person.pop("age")      # Remove & return

# Iterating
for key in person:
    print(key, person[key])

for key, value in person.items():
    print(f"{key}: {value}")

# Useful methods
person.keys()    # All keys
person.values()  # All values
person.items()   # All key-value pairs as tuples
```

### When to use Dictionaries:
- **Key-value pairs** (like a real dictionary!)
- **Fast lookups** by key (O(1) average)
- **JSON-like data** structures
- **Counting occurrences** (or use `collections.Counter`)

---

## 🔄 Quick Comparison Table

| Feature | List | Tuple | Set | Dict |
|---------|------|-------|-----|------|
| Ordered | ✅ | ✅ | ❌ | ✅* |
| Mutable | ✅ | ❌ | ✅ | ✅ |
| Duplicates | ✅ | ✅ | ❌ | Keys: ❌ |
| Indexed | ✅ | ✅ | ❌ | By key |
| Syntax | `[]` | `()` | `{}` | `{k:v}` |

*Dictionaries maintain insertion order since Python 3.7

---

## 🧪 Try It Yourself

```python
# Exercise 1: Create a list of your 5 favorite movies
movies = []  # Fill this in!

# Exercise 2: Create a tuple with your birth date (year, month, day)
birthdate = ()  # Fill this in!

# Exercise 3: Remove duplicates from this list using a set
numbers = [1, 2, 2, 3, 3, 3, 4, 4, 4, 4]
unique = None  # Your code here

# Exercise 4: Create a dictionary representing a book
book = {}  # Add title, author, year, pages
```

---

## 📖 Next Up
Learn about **comprehensions** - a Pythonic way to create data structures in a single line!
