# Week 2 - Exercise 3: Object-Oriented Programming Practice

"""
This file contains exercises for practicing OOP concepts:
- Classes and Objects
- Constructors (__init__)
- Dunder methods (__str__, __repr__, __eq__, etc.)
- Properties
- Inheritance
"""

# =============================================================================
# EXERCISE 3.1: Basic Class - Rectangle
# =============================================================================

class Rectangle:
    """
    A class representing a rectangle.
    
    TODO: Implement the following:
    - __init__(self, width, height)
    - area property (width * height)
    - perimeter property (2 * (width + height))
    - is_square property (True if width == height)
    - __str__ returns "Rectangle(width x height)"
    - __eq__ compares by area
    - __lt__ compares by area (for sorting)
    - scale(factor) method - multiply both dimensions by factor
    """
    
    def __init__(self, width, height):
        # TODO: Implement
        pass


# =============================================================================
# EXERCISE 3.2: Bank Account with Encapsulation
# =============================================================================

class BankAccount:
    """
    A class representing a bank account.
    
    TODO: Implement the following:
    - __init__(self, owner, initial_balance=0)
    - balance property (protected, read-only from outside)
    - deposit(amount) - add money (only positive amounts)
    - withdraw(amount) - remove money (check sufficient funds)
    - transfer(amount, other_account) - transfer to another account
    - __str__ returns "Account of {owner}: ${balance}"
    - transaction_history - list of all transactions (as tuples)
    """
    
    def __init__(self, owner, initial_balance=0):
        # TODO: Implement
        pass


# =============================================================================
# EXERCISE 3.3: Inheritance - Animal Kingdom
# =============================================================================

class Animal:
    """
    Base class for animals.
    
    Attributes:
    - name (str)
    - age (int)
    - _energy (int, protected) - starts at 100
    
    Methods:
    - eat(food) - increases energy by 10, prints "{name} eats {food}"
    - sleep(hours) - increases energy by hours * 5
    - speak() - abstract, raises NotImplementedError
    - __str__ returns "{name} the {class_name}, age {age}"
    """
    
    def __init__(self, name, age):
        # TODO: Implement
        pass
    
    def speak(self):
        raise NotImplementedError("Subclass must implement speak()")


class Dog(Animal):
    """
    A dog that can bark and fetch.
    
    Additional attributes:
    - breed (str)
    
    Methods:
    - speak() returns "{name} says Woof!"
    - fetch() - prints "{name} fetches the ball!", decreases energy by 10
    """
    
    def __init__(self, name, age, breed):
        # TODO: Implement (use super().__init__)
        pass


class Cat(Animal):
    """
    A cat that can meow and scratch.
    
    Additional attributes:
    - indoor (bool)
    
    Methods:
    - speak() returns "{name} says Meow!"
    - scratch() - prints "{name} scratches the furniture!"
    """
    
    def __init__(self, name, age, indoor=True):
        # TODO: Implement
        pass


# =============================================================================
# EXERCISE 3.4: Student Grade System
# =============================================================================

class Student:
    """
    A class representing a student with grades.
    
    TODO: Implement:
    - __init__(self, name, student_id)
    - grades dict attribute (subject: score)
    - add_grade(subject, score) - add or update a grade
    - average property - calculate average grade
    - highest_subject property - subject with highest grade
    - passing property - True if average >= 60
    - __str__ returns "Name (ID: student_id, Avg: X.X)"
    - __lt__ compares by average (for sorting)
    
    Class attributes:
    - passing_grade = 60
    """
    
    passing_grade = 60
    
    def __init__(self, name, student_id):
        # TODO: Implement
        pass


# =============================================================================
# EXERCISE 3.5: Inventory Item with Class Methods
# =============================================================================

class InventoryItem:
    """
    A class representing an item in inventory.
    
    Attributes:
    - name (str)
    - price (float)
    - quantity (int)
    
    Methods:
    - total_value property - price * quantity
    - restock(amount) - add to quantity
    - sell(amount) - reduce quantity (check availability)
    - __str__ returns "{name}: {quantity} units @ ${price}"
    
    Class methods:
    - from_string(s) - create from "name,price,quantity" string
    
    Static methods:
    - is_valid_price(price) - returns True if price > 0
    """
    
    def __init__(self, name, price, quantity):
        # TODO: Implement
        pass
    
    @classmethod
    def from_string(cls, s):
        """Create an InventoryItem from a string like 'Widget,9.99,100'"""
        # TODO: Implement
        pass
    
    @staticmethod
    def is_valid_price(price):
        """Check if price is valid (positive number)"""
        # TODO: Implement
        pass


# =============================================================================
# TEST ALL EXERCISES
# =============================================================================

if __name__ == "__main__":
    print("=" * 60)
    print("Testing OOP Exercises")
    print("=" * 60)
    
    # Test Rectangle
    print("\n--- Testing Rectangle ---")
    try:
        r1 = Rectangle(4, 5)
        r2 = Rectangle(5, 4)
        r3 = Rectangle(3, 3)
        print(f"r1: {r1}")
        print(f"r1 area: {r1.area}")
        print(f"r1 perimeter: {r1.perimeter}")
        print(f"r3 is square: {r3.is_square}")
        print(f"r1 == r2 (same area): {r1 == r2}")
    except Exception as e:
        print(f"Rectangle not implemented: {e}")
    
    # Test BankAccount
    print("\n--- Testing BankAccount ---")
    try:
        acc1 = BankAccount("Alice", 1000)
        acc2 = BankAccount("Bob", 500)
        print(acc1)
        acc1.deposit(200)
        acc1.withdraw(100)
        acc1.transfer(300, acc2)
        print(f"After transactions: {acc1}")
        print(f"Bob's account: {acc2}")
    except Exception as e:
        print(f"BankAccount not implemented: {e}")
    
    # Test Animal Inheritance
    print("\n--- Testing Animal Inheritance ---")
    try:
        dog = Dog("Buddy", 3, "Golden Retriever")
        cat = Cat("Whiskers", 5, indoor=True)
        print(dog)
        print(dog.speak())
        print(cat)
        print(cat.speak())
    except Exception as e:
        print(f"Animal classes not implemented: {e}")
    
    # Test Student
    print("\n--- Testing Student ---")
    try:
        s1 = Student("Alice", "S001")
        s1.add_grade("Math", 85)
        s1.add_grade("Physics", 92)
        s1.add_grade("Chemistry", 78)
        print(s1)
        print(f"Average: {s1.average}")
        print(f"Highest: {s1.highest_subject}")
        print(f"Passing: {s1.passing}")
    except Exception as e:
        print(f"Student not implemented: {e}")
    
    # Test InventoryItem
    print("\n--- Testing InventoryItem ---")
    try:
        item1 = InventoryItem("Widget", 9.99, 100)
        print(item1)
        print(f"Total value: ${item1.total_value}")
        item2 = InventoryItem.from_string("Gadget,19.99,50")
        print(f"From string: {item2}")
        print(f"Is 10 valid price: {InventoryItem.is_valid_price(10)}")
        print(f"Is -5 valid price: {InventoryItem.is_valid_price(-5)}")
    except Exception as e:
        print(f"InventoryItem not implemented: {e}")
