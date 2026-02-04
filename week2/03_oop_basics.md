# Lesson 03: Object-Oriented Programming (OOP)

## 📚 Overview
OOP is a programming paradigm that organizes code into **objects** - which contain both data (attributes) and functionality (methods).

---

## 1. Classes and Objects

A **class** is a blueprint. An **object** is an instance of that blueprint.

```python
# Define a class
class Dog:
    # Class attribute (shared by all instances)
    species = "Canis familiaris"
    
    # Constructor (initializer)
    def __init__(self, name, age):
        # Instance attributes (unique to each object)
        self.name = name
        self.age = age
    
    # Instance method
    def bark(self):
        return f"{self.name} says Woof!"
    
    def description(self):
        return f"{self.name} is {self.age} years old"

# Create objects (instances)
buddy = Dog("Buddy", 3)
max_dog = Dog("Max", 5)

# Access attributes
print(buddy.name)        # "Buddy"
print(max_dog.age)       # 5
print(buddy.species)     # "Canis familiaris"

# Call methods
print(buddy.bark())      # "Buddy says Woof!"
print(max_dog.description())  # "Max is 5 years old"
```

### The `self` Parameter
- `self` refers to the **current instance** of the class
- It's automatically passed when you call a method
- By convention, we always name it `self`

---

## 2. Dunder (Magic) Methods

Dunder methods (double underscore) let you define how objects behave with built-in Python operations.

```python
class Book:
    def __init__(self, title, author, pages):
        self.title = title
        self.author = author
        self.pages = pages
    
    # String representation (for print())
    def __str__(self):
        return f"'{self.title}' by {self.author}"
    
    # Developer representation (for debugging)
    def __repr__(self):
        return f"Book('{self.title}', '{self.author}', {self.pages})"
    
    # Length (for len())
    def __len__(self):
        return self.pages
    
    # Equality comparison (for ==)
    def __eq__(self, other):
        return self.title == other.title and self.author == other.author

# Usage
book1 = Book("1984", "George Orwell", 328)
book2 = Book("1984", "George Orwell", 328)

print(book1)           # '1984' by George Orwell
print(repr(book1))     # Book('1984', 'George Orwell', 328)
print(len(book1))      # 328
print(book1 == book2)  # True
```

### Common Dunder Methods
| Method | Purpose | Triggered By |
|--------|---------|--------------|
| `__init__` | Constructor | `Class()` |
| `__str__` | User-friendly string | `print()`, `str()` |
| `__repr__` | Developer string | `repr()`, console |
| `__len__` | Length | `len()` |
| `__eq__` | Equality | `==` |
| `__lt__` | Less than | `<` |
| `__add__` | Addition | `+` |
| `__getitem__` | Indexing | `obj[key]` |

---

## 3. Inheritance

Inheritance lets you create a new class based on an existing class.

```python
# Parent class (base class)
class Animal:
    def __init__(self, name):
        self.name = name
    
    def speak(self):
        raise NotImplementedError("Subclass must implement this")
    
    def describe(self):
        return f"I am {self.name}"

# Child class (derived class)
class Cat(Animal):
    def __init__(self, name, color):
        super().__init__(name)  # Call parent's __init__
        self.color = color
    
    def speak(self):
        return f"{self.name} says Meow!"
    
    def describe(self):
        return f"I am {self.name}, a {self.color} cat"

class Dog(Animal):
    def speak(self):
        return f"{self.name} says Woof!"

# Usage
whiskers = Cat("Whiskers", "orange")
buddy = Dog("Buddy")

print(whiskers.speak())    # "Whiskers says Meow!"
print(buddy.speak())       # "Buddy says Woof!"
print(whiskers.describe()) # "I am Whiskers, a orange cat"
print(buddy.describe())    # "I am Buddy" (inherited from Animal)
```

### Key Concepts:
- **`super()`**: Calls the parent class method
- **Method Override**: Child class can redefine parent methods
- **`isinstance()`**: Check if object is instance of a class

```python
print(isinstance(whiskers, Cat))     # True
print(isinstance(whiskers, Animal))  # True (inheritance!)
print(isinstance(buddy, Cat))        # False
```

---

## 4. Encapsulation

Encapsulation is about **bundling data with methods** and **controlling access**.

```python
class BankAccount:
    def __init__(self, owner, balance=0):
        self.owner = owner          # Public
        self._balance = balance     # Protected (convention)
        self.__pin = "1234"         # Private (name mangling)
    
    def deposit(self, amount):
        if amount > 0:
            self._balance += amount
            return True
        return False
    
    def withdraw(self, amount):
        if 0 < amount <= self._balance:
            self._balance -= amount
            return True
        return False
    
    def get_balance(self):
        return self._balance

# Usage
account = BankAccount("Alice", 1000)
account.deposit(500)
print(account.get_balance())  # 1500

# Access levels:
print(account.owner)       # OK - public
print(account._balance)    # Works but discouraged - protected
# print(account.__pin)     # Error! - private
print(account._BankAccount__pin)  # Works (name mangling) but DON'T!
```

### Convention:
- `name`: Public - use freely
- `_name`: Protected - internal use (by convention)
- `__name`: Private - name mangled to `_ClassName__name`

---

## 5. Class Methods and Static Methods

```python
class Pizza:
    # Class attribute
    base_price = 100
    
    def __init__(self, toppings):
        self.toppings = toppings
    
    # Regular instance method
    def price(self):
        return self.base_price + len(self.toppings) * 20
    
    # Class method - works with the class, not instances
    @classmethod
    def margherita(cls):
        return cls(["cheese", "tomato"])
    
    # Static method - doesn't need class or instance
    @staticmethod
    def is_valid_topping(topping):
        valid = ["cheese", "tomato", "mushroom", "olive", "pepperoni"]
        return topping.lower() in valid

# Usage
pizza1 = Pizza(["cheese", "mushroom", "olive"])
print(pizza1.price())  # 160

# Class method as factory
margherita = Pizza.margherita()
print(margherita.toppings)  # ["cheese", "tomato"]

# Static method
print(Pizza.is_valid_topping("Mushroom"))  # True
```

---

## 6. Properties (Getters and Setters)

```python
class Temperature:
    def __init__(self, celsius=0):
        self._celsius = celsius
    
    @property
    def celsius(self):
        return self._celsius
    
    @celsius.setter
    def celsius(self, value):
        if value < -273.15:
            raise ValueError("Temperature below absolute zero!")
        self._celsius = value
    
    @property
    def fahrenheit(self):
        return self._celsius * 9/5 + 32
    
    @fahrenheit.setter
    def fahrenheit(self, value):
        self._celsius = (value - 32) * 5/9

# Usage
temp = Temperature(25)
print(temp.celsius)      # 25
print(temp.fahrenheit)   # 77.0

temp.fahrenheit = 100
print(temp.celsius)      # 37.777...

temp.celsius = -300      # ValueError!
```

---

## 🧪 Practice Exercise

```python
# Create a Contact class for the Contact Manager project!

class Contact:
    def __init__(self, name, phone, email=None):
        # Your code here
        pass
    
    def __str__(self):
        # Return a nice string representation
        pass
    
    def __repr__(self):
        # Return a developer-friendly representation
        pass
    
    def __eq__(self, other):
        # Two contacts are equal if same name and phone
        pass
    
    def to_dict(self):
        # Convert to dictionary (for JSON saving)
        pass
    
    @classmethod
    def from_dict(cls, data):
        # Create Contact from dictionary
        pass

# Test your class:
# c1 = Contact("Alice", "123-456-7890", "alice@email.com")
# print(c1)
# print(c1.to_dict())
```

---

## 📖 Next Up
Learn about **File I/O** - reading and writing files, including JSON!
