# Lesson 04: File I/O (Input/Output)

## 📚 Overview
Learn to read and write files in Python - essential for persisting data!

---

## 1. The `with` Statement (Context Manager)

**Always use `with`** when working with files. It automatically handles closing the file, even if an error occurs.

```python
# ✅ GOOD - uses context manager
with open("myfile.txt", "r") as file:
    content = file.read()
# File automatically closed here!

# ❌ BAD - manual handling (error-prone)
file = open("myfile.txt", "r")
content = file.read()
file.close()  # Easy to forget, especially with errors!
```

---

## 2. File Modes

| Mode | Description |
|------|-------------|
| `'r'` | Read (default) - file must exist |
| `'w'` | Write - creates new or **overwrites** existing |
| `'a'` | Append - adds to end of file |
| `'x'` | Exclusive create - fails if file exists |
| `'r+'` | Read and write |
| `'b'` | Binary mode (e.g., `'rb'`, `'wb'`) |

---

## 3. Reading Text Files

```python
# Read entire file as one string
with open("story.txt", "r") as file:
    content = file.read()
    print(content)

# Read all lines as a list
with open("story.txt", "r") as file:
    lines = file.readlines()  # ['line1\n', 'line2\n', ...]
    
# Read line by line (memory efficient for large files)
with open("story.txt", "r") as file:
    for line in file:
        print(line.strip())  # strip() removes \n

# Read specific number of characters
with open("story.txt", "r") as file:
    first_100_chars = file.read(100)
```

---

## 4. Writing Text Files

```python
# Write (overwrites existing content!)
with open("output.txt", "w") as file:
    file.write("Hello, World!\n")
    file.write("This is line 2.\n")

# Write multiple lines at once
lines = ["Line 1", "Line 2", "Line 3"]
with open("output.txt", "w") as file:
    file.writelines(line + "\n" for line in lines)

# Append to existing file
with open("log.txt", "a") as file:
    file.write("New log entry\n")
```

---

## 5. Working with JSON

JSON (JavaScript Object Notation) is perfect for storing structured data.

```python
import json

# Python dict to JSON file
data = {
    "name": "Alice",
    "age": 30,
    "hobbies": ["reading", "coding", "hiking"],
    "is_student": False
}

# Write JSON to file
with open("data.json", "w") as file:
    json.dump(data, file, indent=4)  # indent for pretty printing

# Read JSON from file
with open("data.json", "r") as file:
    loaded_data = json.load(file)
    print(loaded_data["name"])  # "Alice"
```

### JSON Methods Cheatsheet
| Method | Purpose |
|--------|---------|
| `json.dump(obj, file)` | Write Python object to file as JSON |
| `json.load(file)` | Read JSON file to Python object |
| `json.dumps(obj)` | Convert Python object to JSON string |
| `json.loads(string)` | Parse JSON string to Python object |

```python
# String conversions
json_string = json.dumps(data)  # '{"name": "Alice", ...}'
parsed = json.loads(json_string)  # Back to dict
```

---

## 6. Working with CSV Files

```python
import csv

# Writing CSV
data = [
    ["Name", "Age", "City"],
    ["Alice", 30, "Mumbai"],
    ["Bob", 25, "Delhi"],
    ["Charlie", 35, "Bangalore"]
]

with open("people.csv", "w", newline="") as file:
    writer = csv.writer(file)
    writer.writerows(data)

# Reading CSV
with open("people.csv", "r") as file:
    reader = csv.reader(file)
    for row in reader:
        print(row)  # ['Name', 'Age', 'City'], etc.

# Using DictReader (better!)
with open("people.csv", "r") as file:
    reader = csv.DictReader(file)
    for row in reader:
        print(row["Name"], row["Age"])
```

---

## 7. Path Handling with `pathlib`

Modern Python uses `pathlib` for file paths (better than string manipulation).

```python
from pathlib import Path

# Create path object
data_dir = Path("data")
file_path = data_dir / "contacts.json"  # data/contacts.json

# Check if exists
if file_path.exists():
    print("File exists!")

if data_dir.is_dir():
    print("It's a directory!")

# Create directory
data_dir.mkdir(exist_ok=True)  # No error if already exists

# Get all JSON files in a directory
json_files = list(data_dir.glob("*.json"))

# Read/write using Path
content = file_path.read_text()
file_path.write_text("Hello!")

# File info
print(file_path.name)      # contacts.json
print(file_path.stem)      # contacts
print(file_path.suffix)    # .json
print(file_path.parent)    # data
```

---

## 8. Exception Handling with Files

```python
from pathlib import Path
import json

def load_contacts(filepath):
    path = Path(filepath)
    
    try:
        with open(path, "r") as file:
            return json.load(file)
    except FileNotFoundError:
        print(f"File {filepath} not found. Starting with empty list.")
        return []
    except json.JSONDecodeError:
        print(f"Error parsing {filepath}. File may be corrupted.")
        return []
    except PermissionError:
        print(f"Permission denied to read {filepath}.")
        return []

def save_contacts(filepath, contacts):
    path = Path(filepath)
    
    # Create parent directory if it doesn't exist
    path.parent.mkdir(parents=True, exist_ok=True)
    
    try:
        with open(path, "w") as file:
            json.dump(contacts, file, indent=2)
        return True
    except (IOError, OSError) as e:
        print(f"Error saving file: {e}")
        return False
```

---

## 🧪 Practice: Contact Storage

```python
# contacts_storage.py
import json
from pathlib import Path

class ContactStorage:
    def __init__(self, filepath="contacts.json"):
        self.filepath = Path(filepath)
        self.contacts = []
        self.load()
    
    def load(self):
        """Load contacts from JSON file."""
        # Your code here
        pass
    
    def save(self):
        """Save contacts to JSON file."""
        # Your code here
        pass
    
    def add(self, contact):
        """Add a contact and save."""
        # Your code here
        pass
    
    def remove(self, name):
        """Remove a contact by name and save."""
        # Your code here
        pass
    
    def find(self, name):
        """Find contacts by name (partial match)."""
        # Use list comprehension!
        pass

# Test it:
# storage = ContactStorage()
# storage.add({"name": "Alice", "phone": "123-456-7890"})
# storage.add({"name": "Bob", "phone": "098-765-4321"})
# print(storage.find("ali"))  # Should find Alice
```

---

## 📖 Next Up
Learn about **external libraries** - making HTTP requests with `requests`!
