# Week 2 - Exercise 2: Comprehensions Practice

"""
This file contains exercises for practicing Python comprehensions:
- List comprehensions
- Dictionary comprehensions
- Set comprehensions
- Generator expressions
"""

# =============================================================================
# EXERCISE 2.1: List Comprehensions
# =============================================================================

# TODO 1: Create a list of all even numbers from 1 to 50
evens = None  # Your code here

# TODO 2: Create a list of (number, square) tuples for 1-10
# Expected: [(1, 1), (2, 4), (3, 9), ...]
squares_tuples = None  # Your code here

# TODO 3: Flatten this nested list and remove duplicates
nested = [[1, 2, 3], [2, 3, 4], [3, 4, 5]]
flat_unique = None  # Your code here (hint: use set then list)

# TODO 4: Convert all strings to uppercase, but only if length > 3
words = ["cat", "elephant", "dog", "butterfly", "ant"]
long_upper = None  # Your code here

# TODO 5: Get all numbers divisible by both 3 AND 5 between 1 and 100
divisible_by_both = None  # Your code here

# TODO 6: Create a list of words that are palindromes
candidates = ["radar", "hello", "level", "world", "noon", "python", "civic"]
palindromes = None  # Your code here


# =============================================================================
# EXERCISE 2.2: Dictionary Comprehensions
# =============================================================================

# TODO 7: Create a dict mapping letters to their ASCII values
letters = "abcdefghij"
ascii_map = None  # Your code here

# TODO 8: Invert this dictionary (swap keys and values)
original = {"a": 1, "b": 2, "c": 3}
inverted = None  # Your code here

# TODO 9: Filter to only include items where value > 50
scores = {"Alice": 85, "Bob": 45, "Charlie": 92, "David": 38, "Eve": 67}
passing = None  # Your code here

# TODO 10: Create a dict of word lengths
sentence = "The quick brown fox jumps over the lazy dog"
word_lengths = None  # Your code here (word: length)

# TODO 11: Square only the even values, keep odd values as-is
numbers = {"a": 1, "b": 2, "c": 3, "d": 4, "e": 5}
modified = None  # Your code here


# =============================================================================
# EXERCISE 2.3: Set Comprehensions
# =============================================================================

# TODO 12: Extract unique first letters from words
words_list = ["apple", "banana", "avocado", "blueberry", "cherry", "apricot"]
first_letters = None  # Your code here

# TODO 13: Get unique word lengths from a sentence
sentence2 = "The quick brown fox jumps over the lazy dog"
unique_lengths = None  # Your code here

# TODO 14: Find common elements between two lists using sets
list1 = [1, 2, 3, 4, 5, 6, 7, 8]
list2 = [5, 6, 7, 8, 9, 10]
common = None  # Your code here


# =============================================================================
# EXERCISE 2.4: Nested Comprehensions
# =============================================================================

# TODO 15: Create a 5x5 multiplication table as a 2D list
# Expected: [[1,2,3,4,5], [2,4,6,8,10], ...]
multiplication_table = None  # Your code here

# TODO 16: Flatten a 2D matrix into a 1D list
matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
flattened = None  # Your code here

# TODO 17: Create all pairs (i, j) where i < j for i, j in range(5)
pairs = None  # Your code here


# =============================================================================
# EXERCISE 2.5: Generator Expressions
# =============================================================================

# TODO 18: Calculate sum of squares of first 100 numbers using generator
sum_of_squares = None  # Your code here (use sum() with generator)

# TODO 19: Find the first number > 1000 that is divisible by 17
# (use a generator with next())
first_divisible = None  # Your code here


# =============================================================================
# TEST ALL EXERCISES
# =============================================================================

if __name__ == "__main__":
    print("=" * 60)
    print("Testing Comprehension Exercises")
    print("=" * 60)
    
    print("\n--- List Comprehensions ---")
    print(f"1. Evens (1-50): {evens[:10]}... (showing first 10)")
    print(f"2. Square tuples: {squares_tuples}")
    print(f"3. Flat unique: {flat_unique}")
    print(f"4. Long words upper: {long_upper}")
    print(f"5. Divisible by 3 and 5: {divisible_by_both}")
    print(f"6. Palindromes: {palindromes}")
    
    print("\n--- Dictionary Comprehensions ---")
    print(f"7. ASCII map: {ascii_map}")
    print(f"8. Inverted: {inverted}")
    print(f"9. Passing scores: {passing}")
    print(f"10. Word lengths: {word_lengths}")
    print(f"11. Modified numbers: {modified}")
    
    print("\n--- Set Comprehensions ---")
    print(f"12. First letters: {first_letters}")
    print(f"13. Unique lengths: {unique_lengths}")
    print(f"14. Common elements: {common}")
    
    print("\n--- Nested Comprehensions ---")
    print(f"15. Multiplication table (first 3 rows): {multiplication_table[:3] if multiplication_table else None}")
    print(f"16. Flattened matrix: {flattened}")
    print(f"17. Pairs where i<j: {pairs}")
    
    print("\n--- Generator Expressions ---")
    print(f"18. Sum of squares (1-100): {sum_of_squares}")
    print(f"19. First number > 1000 divisible by 17: {first_divisible}")
