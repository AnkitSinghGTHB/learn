# Lesson 02: Python Comprehensions

## 📚 Overview
Comprehensions are a concise, Pythonic way to create data structures. They're more readable and often faster than traditional loops.

---

## 1. List Comprehensions

### Basic Syntax
```python
# Traditional way
squares = []
for x in range(10):
    squares.append(x ** 2)

# List comprehension (same result, one line!)
squares = [x ** 2 for x in range(10)]
# [0, 1, 4, 9, 16, 25, 36, 49, 64, 81]
```

### With Conditions (Filtering)
```python
# Only even squares
even_squares = [x ** 2 for x in range(10) if x % 2 == 0]
# [0, 4, 16, 36, 64]

# Multiple conditions
result = [x for x in range(100) if x % 2 == 0 if x % 5 == 0]
# [0, 10, 20, 30, 40, 50, 60, 70, 80, 90]
```

### With If-Else (Transformation)
```python
# If-else goes BEFORE the for
labels = ["even" if x % 2 == 0 else "odd" for x in range(5)]
# ["even", "odd", "even", "odd", "even"]

# Practical example: Replace negative numbers with 0
numbers = [5, -3, 8, -1, 2]
positive = [n if n > 0 else 0 for n in numbers]
# [5, 0, 8, 0, 2]
```

### Nested List Comprehensions
```python
# Flatten a matrix (2D list to 1D)
matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
flat = [num for row in matrix for num in row]
# [1, 2, 3, 4, 5, 6, 7, 8, 9]

# Create a multiplication table
table = [[i * j for j in range(1, 6)] for i in range(1, 6)]
# [[1,2,3,4,5], [2,4,6,8,10], [3,6,9,12,15], ...]
```

---

## 2. Dictionary Comprehensions

### Basic Syntax
```python
# Create a dict from two lists
keys = ["a", "b", "c"]
values = [1, 2, 3]
d = {k: v for k, v in zip(keys, values)}
# {"a": 1, "b": 2, "c": 3}

# Square numbers as dict
squares = {x: x**2 for x in range(1, 6)}
# {1: 1, 2: 4, 3: 9, 4: 16, 5: 25}
```

### With Conditions
```python
# Only even squares
even_squares = {x: x**2 for x in range(10) if x % 2 == 0}
# {0: 0, 2: 4, 4: 16, 6: 36, 8: 64}

# Filter a dictionary
prices = {"apple": 1.5, "banana": 0.5, "cherry": 3.0}
expensive = {k: v for k, v in prices.items() if v > 1}
# {"apple": 1.5, "cherry": 3.0}
```

### Transforming Keys/Values
```python
# Swap keys and values
original = {"a": 1, "b": 2, "c": 3}
swapped = {v: k for k, v in original.items()}
# {1: "a", 2: "b", 3: "c"}

# Uppercase all keys
data = {"name": "Alice", "city": "Mumbai"}
upper = {k.upper(): v for k, v in data.items()}
# {"NAME": "Alice", "CITY": "Mumbai"}
```

---

## 3. Set Comprehensions

```python
# Basic set comprehension
squares = {x**2 for x in range(-5, 6)}
# {0, 1, 4, 9, 16, 25} - Note: no duplicates!

# Extract unique first letters
words = ["apple", "banana", "avocado", "blueberry"]
first_letters = {word[0] for word in words}
# {"a", "b"}
```

---

## 4. Generator Expressions (Bonus!)

Generator expressions look like list comprehensions but use `()` instead of `[]`. They're **memory efficient** because they generate values on-the-fly.

```python
# List comprehension - creates entire list in memory
squares_list = [x**2 for x in range(1000000)]

# Generator expression - generates values one at a time
squares_gen = (x**2 for x in range(1000000))

# Use generators when:
# 1. You only need to iterate once
# 2. The sequence is very large
# 3. You want to save memory

# Works great with functions like sum(), max(), etc.
total = sum(x**2 for x in range(1000))  # No extra brackets needed!
```

---

## 🎯 Pattern Summary

```python
# List:   [expression for item in iterable if condition]
# Dict:   {key: value for item in iterable if condition}
# Set:    {expression for item in iterable if condition}
# Gen:    (expression for item in iterable if condition)
```

---

## 💡 Best Practices

### ✅ DO:
```python
# Simple, readable comprehensions
names = [name.upper() for name in names if name]
```

### ❌ DON'T:
```python
# Overly complex - just use a regular loop!
result = [x if x > 0 else -x for x in [y**2 - z for y, z in zip(a, b)] if x != 0]
```

**Rule of thumb**: If your comprehension is longer than ~80 characters or hard to understand at a glance, use a regular loop instead.

---

## 🧪 Practice Exercises

```python
# Exercise 1: Create a list of squares for numbers 1-20
squares = None  # Your code here

# Exercise 2: Filter words longer than 5 characters
words = ["python", "is", "awesome", "and", "powerful"]
long_words = None  # Your code here

# Exercise 3: Create a dict mapping numbers to their cubes (1-10)
cubes = None  # Your code here

# Exercise 4: Extract unique vowels from a sentence
sentence = "The quick brown fox jumps over the lazy dog"
vowels = None  # Your code here

# Exercise 5: Flatten this nested list
nested = [[1, 2], [3, 4], [5, 6]]
flat = None  # Your code here
```

---

## 📖 Next Up
Learn about **Object-Oriented Programming** - classes, objects, and more!
