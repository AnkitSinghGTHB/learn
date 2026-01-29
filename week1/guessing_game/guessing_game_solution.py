"""
Number Guessing Game - Complete Solution
Use this to check your work!
"""

import random

def main():
    print("🎲 Number Guessing Game")
    print("I'm thinking of a number between 1 and 100...")
    print()
    
    # Generate random number
    secret_number = random.randint(1, 100)
    attempts = 0
    
    while True:
        # Get user's guess
        try:
            guess = int(input("Enter your guess: "))
        except ValueError:
            print("Please enter a valid number!")
            continue
        
        attempts += 1
        
        # Check the guess
        if guess < secret_number:
            print("Too low! Try again.")
            print()
        elif guess > secret_number:
            print("Too high! Try again.")
            print()
        else:
            print(f"🎉 Correct! You got it in {attempts} attempts!")
            break
    
    # Play again?
    play_again = input("\nPlay again? (y/n): ")
    if play_again.lower() == 'y':
        main()
    else:
        print("Thanks for playing!")

if __name__ == "__main__":
    main()
