# Python Basics

## Variables

Variables store data. Python is dynamically typed (no need to declare types).

```python
# Creating variables
name = "Alice"          # String
age = 25                # Integer
height = 5.9            # Float
is_student = True       # Boolean

# Multiple assignment
x, y, z = 1, 2, 3

# Same value to multiple variables
a = b = c = 0
```

## Data Types

### Integers (`int`)
Whole numbers, positive or negative.
```python
count = 42
negative = -10
big_number = 1_000_000  # Underscores for readability
```

### Floats (`float`)
Decimal numbers.
```python
price = 19.99
pi = 3.14159
scientific = 1.5e-3     # 0.0015    1.5 * 10^ (-3)
```

### Booleans (`bool`)
True or False.
```python
is_active = True
has_permission = False

# Boolean operations
result = True and False  # False
result = True or False   # True
result = not True        # False
```

### Strings (`str`)
Text data.
```python
single = 'Hello'
double = "World"
multi_line = """This is
a multi-line
string"""

# String operations
greeting = "Hello" + " " + "World"  # Concatenation
repeated = "Ha" * 3                  # "HaHaHa"
length = len("Python")               # 6

# F-strings (formatted strings) - VERY USEFUL!
name = "Alice"
age = 25
message = f"My name is {name} and I'm {age} years old"
```

## Type Checking & Conversion

```python
# Check type
print(type(42))        # <class 'int'>
print(type("hello"))   # <class 'str'>

# Convert between types
num_str = "42"
num_int = int(num_str)      # String to int
num_float = float(num_str)  # String to float
back_to_str = str(42)       # Int to string
```

## Comments

```python
# This is a single-line comment

# You can have multiple
# single-line comments

"""
This is a multi-line comment
or docstring. Often used to
document functions.
"""
```

## Operators

### Arithmetic
```python
a = 10
b = 3

print(a + b)   # 13  Addition
print(a - b)   # 7   Subtraction
print(a * b)   # 30  Multiplication
print(a / b)   # 3.33 Division (float)
print(a // b)  # 3   Floor division (int)
print(a % b)   # 1   Modulo (remainder)
print(a ** b)  # 1000 Exponentiation
```

### Comparison
```python
x = 5
y = 10

print(x == y)  # False (equal)
print(x != y)  # True  (not equal)
print(x < y)   # True  (less than)
print(x > y)   # False (greater than)
print(x <= y)  # True  (less or equal)
print(x >= y)  # False (greater or equal)
```

## Input from User

```python
name = input("What's your name? ")
print(f"Hello, {name}!")

# Input is always a string - convert if needed
age = int(input("Enter your age: "))
```

## Practice Exercises

1. Create variables for your name, age, and favorite number
2. Print them using an f-string
3. Ask the user for two numbers and print their sum
4. Convert a temperature from Celsius to Fahrenheit: `F = C * 9/5 + 32`

---
**Next**: [03_control_flow.md](03_control_flow.md)
