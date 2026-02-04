# Week 2 Exercises: Data Structures, OOP & File I/O

## 🎯 Exercise 1: Data Structure Mastery

### 1.1 List Operations
Create a shopping list manager:
```python
# Create functions for:
# - add_item(shopping_list, item) - add item to list
# - remove_item(shopping_list, item) - remove item from list
# - check_item(shopping_list, item) - return True if item exists
# - sort_list(shopping_list) - return sorted copy without modifying original
```

### 1.2 Dictionary Practice
Create a word frequency counter:
```python
text = """Python is a great programming language.
Python is easy to learn. Python is powerful."""

# Write a function that returns a dict with word frequencies
# Expected output: {"python": 3, "is": 3, "a": 1, ...}
```

### 1.3 Set Operations
```python
# Given these sets of students
math_students = {"Alice", "Bob", "Charlie", "David"}
physics_students = {"Charlie", "David", "Eve", "Frank"}
chemistry_students = {"Alice", "Eve", "George"}

# Find:
# 1. Students in ALL three classes
# 2. Students in math OR physics (but not both)
# 3. Students only in chemistry (not in math or physics)
```

---

## 🎯 Exercise 2: Comprehension Challenges

### 2.1 List Comprehensions
```python
# 1. Create a list of all even numbers from 1 to 50
evens = None  # Your code

# 2. Create a list of (number, square) tuples for 1-10
squares = None  # Your code

# 3. Flatten this nested list and remove duplicates
nested = [[1, 2, 3], [2, 3, 4], [3, 4, 5]]
flat_unique = None  # Your code

# 4. Convert all strings to uppercase, but only if len > 3
words = ["cat", "elephant", "dog", "butterfly", "ant"]
long_upper = None  # Your code
```

### 2.2 Dictionary Comprehensions
```python
# 1. Create a dict mapping letters to their ASCII values
letters = "abcdefghij"
ascii_map = None  # Your code

# 2. Invert this dictionary (swap keys and values)
original = {"a": 1, "b": 2, "c": 3}
inverted = None  # Your code

# 3. Filter a dict to only include items where value > 50
scores = {"Alice": 85, "Bob": 45, "Charlie": 92, "David": 38}
passing = None  # Your code
```

---

## 🎯 Exercise 3: OOP Design Challenge

### 3.1 Create a Library System
```python
class Book:
    """
    Represents a book with title, author, and ISBN.
    
    Requirements:
    - __init__ with title, author, isbn, available=True
    - __str__ returns "Title by Author"
    - __repr__ returns "Book('Title', 'Author', 'ISBN')"
    - __eq__ compares by ISBN
    """
    pass

class Library:
    """
    Represents a library with a collection of books.
    
    Requirements:
    - __init__ creates empty book list
    - add_book(book) - adds a book
    - remove_book(isbn) - removes by ISBN
    - find_by_author(author) - returns list of books (use comprehension!)
    - find_by_title(title_substring) - partial search (use comprehension!)
    - checkout(isbn) - marks book unavailable
    - return_book(isbn) - marks book available
    - available_books - property returning only available books
    """
    pass

# Test your implementation:
# lib = Library()
# lib.add_book(Book("1984", "George Orwell", "123"))
# lib.add_book(Book("Animal Farm", "George Orwell", "124"))
# lib.add_book(Book("Brave New World", "Aldous Huxley", "125"))
# print(lib.find_by_author("orwell"))  # Should find 2 books
# lib.checkout("123")
# print(lib.available_books)  # Should show 2 books
```

### 3.2 Create a Grade Calculator
```python
class Student:
    """
    Represents a student with grades.
    
    Requirements:
    - __init__ with name, grades (dict of subject: score)
    - average property - returns average grade
    - highest_subject property - returns subject with highest grade
    - is_passing property - True if average >= 60
    - __str__ returns "Name (Avg: X.X)"
    - __lt__ compares by average (for sorting)
    """
    pass

# Test:
# students = [
#     Student("Alice", {"Math": 85, "Physics": 90, "Chemistry": 78}),
#     Student("Bob", {"Math": 72, "Physics": 68, "Chemistry": 75}),
#     Student("Charlie", {"Math": 95, "Physics": 92, "Chemistry": 88})
# ]
# for s in sorted(students, reverse=True):
#     print(s)
```

---

## 🎯 Exercise 4: File I/O Challenges

### 4.1 Log File Analyzer
```python
# Create a function that:
# 1. Reads a log file (create a sample one first)
# 2. Counts ERROR, WARNING, and INFO entries
# 3. Returns a summary dict

# Sample log format:
# 2024-01-15 10:30:00 INFO User logged in
# 2024-01-15 10:31:00 ERROR Database connection failed
# 2024-01-15 10:32:00 WARNING Low memory

def analyze_log(filepath):
    """Return dict with counts: {"INFO": 0, "WARNING": 0, "ERROR": 0}"""
    pass
```

### 4.2 JSON Config Manager
```python
import json
from pathlib import Path

class ConfigManager:
    """
    Manages application configuration stored in JSON.
    
    Requirements:
    - __init__ with filepath, creates file if doesn't exist
    - get(key, default=None) - get a config value
    - set(key, value) - set and save a config value
    - delete(key) - remove a config key
    - all_settings property - returns copy of all settings
    """
    pass

# Test:
# config = ConfigManager("app_config.json")
# config.set("theme", "dark")
# config.set("language", "en")
# print(config.get("theme"))  # "dark"
# print(config.get("missing", "default"))  # "default"
```

### 4.3 CSV to JSON Converter
```python
import csv
import json

def csv_to_json(csv_path, json_path):
    """
    Convert a CSV file to JSON format.
    Each row becomes a dict with column headers as keys.
    """
    pass

def json_to_csv(json_path, csv_path):
    """
    Convert a JSON file (list of dicts) to CSV format.
    """
    pass
```

---

## 🎯 Exercise 5: External API Challenge

### 5.1 Pokemon Info Fetcher
```python
import requests

def get_pokemon_info(name_or_id):
    """
    Fetch Pokemon info from the PokeAPI.
    URL: https://pokeapi.co/api/v2/pokemon/{name_or_id}
    
    Return dict with: name, height, weight, types (list of type names)
    Return None if Pokemon not found.
    """
    pass

# Test:
# print(get_pokemon_info("pikachu"))
# print(get_pokemon_info(25))  # Also pikachu
# print(get_pokemon_info("notapokemon"))  # None
```

### 5.2 Quote of the Day
```python
import requests

def get_random_quote():
    """
    Fetch a random quote from https://api.quotable.io/random
    Return dict with: content, author
    """
    pass

def search_quotes(query, limit=5):
    """
    Search quotes by keyword from https://api.quotable.io/search/quotes?query=...
    Return list of quote dicts.
    """
    pass
```

---

## 🏆 Final Project: Contact Manager 2.0

Build a complete Contact Manager with all Week 2 concepts!

See the `contact_manager/` folder for the project structure.

### Requirements:
1. ✅ `Contact` class with proper OOP design
2. ✅ JSON file storage (save/load on startup)
3. ✅ List comprehensions for search/filter
4. ✅ Clean CLI interface
5. ✅ Error handling

### Bonus Features:
- 🌟 Import contacts from API (e.g., randomuser.me)
- 🌟 Export contacts to CSV
- 🌟 Categories/Groups for contacts
- 🌟 Duplicate detection

---

## ✅ Success Checklist
After completing these exercises, you should be able to:
- [ ] Choose the right data structure for a problem
- [ ] Write list, dict, and set comprehensions
- [ ] Design classes with proper __init__, __str__, etc.
- [ ] Use inheritance appropriately
- [ ] Read/write text, JSON, and CSV files
- [ ] Make HTTP requests and handle responses
- [ ] Handle errors gracefully
