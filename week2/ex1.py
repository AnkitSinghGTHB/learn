# Week 2 - Exercise 1: Data Structures Practice

"""
This file contains exercises for practicing Python data structures:
- Lists, Tuples, Sets, Dictionaries
"""

# =============================================================================
# EXERCISE 1.1: Shopping List Manager
# =============================================================================

def add_item(shopping_list, item):
    """Add an item to the shopping list."""
    # TODO: Implement this function
    pass


def remove_item(shopping_list, item):
    """Remove an item from the shopping list. Return True if removed, False otherwise."""
    # TODO: Implement this function
    pass


def check_item(shopping_list, item):
    """Check if an item exists in the shopping list."""
    # TODO: Implement this function
    pass


def sort_list(shopping_list):
    """Return a sorted copy of the shopping list (don't modify original)."""
    # TODO: Implement this function
    pass


# Test your shopping list functions:
if __name__ == "__main__":
    print("=" * 50)
    print("Testing Shopping List Manager")
    print("=" * 50)
    
    my_list = []
    
    # Test add_item
    add_item(my_list, "apples")
    add_item(my_list, "bananas")
    add_item(my_list, "milk")
    print(f"After adding items: {my_list}")
    
    # Test check_item
    print(f"'milk' in list: {check_item(my_list, 'milk')}")
    print(f"'bread' in list: {check_item(my_list, 'bread')}")
    
    # Test remove_item
    remove_item(my_list, "bananas")
    print(f"After removing 'bananas': {my_list}")
    
    # Test sort_list
    add_item(my_list, "zebra")
    add_item(my_list, "cheese")
    sorted_list = sort_list(my_list)
    print(f"Original list: {my_list}")
    print(f"Sorted copy: {sorted_list}")


# =============================================================================
# EXERCISE 1.2: Word Frequency Counter
# =============================================================================

def count_word_frequencies(text):
    """
    Count the frequency of each word in the text.
    - Convert to lowercase
    - Remove punctuation
    - Return a dictionary {word: count}
    """
    # TODO: Implement this function
    pass


# Test word frequency counter:
if __name__ == "__main__":
    print("\n" + "=" * 50)
    print("Testing Word Frequency Counter")
    print("=" * 50)
    
    sample_text = """Python is a great programming language.
    Python is easy to learn. Python is powerful."""
    
    frequencies = count_word_frequencies(sample_text)
    print(f"Word frequencies: {frequencies}")
    # Expected: {"python": 3, "is": 3, "a": 1, ...}


# =============================================================================
# EXERCISE 1.3: Set Operations
# =============================================================================

def analyze_student_enrollments():
    """Analyze student enrollments across different classes."""
    math_students = {"Alice", "Bob", "Charlie", "David"}
    physics_students = {"Charlie", "David", "Eve", "Frank"}
    chemistry_students = {"Alice", "Eve", "George"}
    
    # TODO: Find students in ALL three classes
    all_three = None
    
    # TODO: Find students in math OR physics (union)
    math_or_physics = None
    
    # TODO: Find students ONLY in chemistry (not in math or physics)
    only_chemistry = None
    
    # TODO: Find students in math but NOT in physics
    math_not_physics = None
    
    return {
        "all_three": all_three,
        "math_or_physics": math_or_physics,
        "only_chemistry": only_chemistry,
        "math_not_physics": math_not_physics
    }


# Test set operations:
if __name__ == "__main__":
    print("\n" + "=" * 50)
    print("Testing Set Operations")
    print("=" * 50)
    
    results = analyze_student_enrollments()
    for category, students in results.items():
        print(f"{category}: {students}")


# =============================================================================
# EXERCISE 1.4: Dictionary Manipulation
# =============================================================================

def merge_dictionaries(dict1, dict2):
    """
    Merge two dictionaries. If same key exists, add the values.
    Example: {"a": 1} + {"a": 2, "b": 3} = {"a": 3, "b": 3}
    """
    # TODO: Implement this function
    pass


def find_max_value_key(d):
    """Return the key with the maximum value."""
    # TODO: Implement this function
    pass


def group_by_first_letter(words):
    """
    Group words by their first letter.
    Example: ["apple", "ant", "banana"] -> {"a": ["apple", "ant"], "b": ["banana"]}
    """
    # TODO: Implement this function
    pass


# Test dictionary manipulation:
if __name__ == "__main__":
    print("\n" + "=" * 50)
    print("Testing Dictionary Manipulation")
    print("=" * 50)
    
    # Test merge
    d1 = {"a": 1, "b": 2}
    d2 = {"b": 3, "c": 4}
    merged = merge_dictionaries(d1, d2)
    print(f"Merged: {merged}")  # {"a": 1, "b": 5, "c": 4}
    
    # Test find_max_value_key
    scores = {"Alice": 85, "Bob": 92, "Charlie": 78}
    top_scorer = find_max_value_key(scores)
    print(f"Top scorer: {top_scorer}")  # Bob
    
    # Test group_by_first_letter
    words = ["apple", "ant", "banana", "berry", "cherry", "apricot"]
    grouped = group_by_first_letter(words)
    print(f"Grouped: {grouped}")
