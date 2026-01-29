"""
Number Guessing Game - Starter Code
Complete the TODOs to finish this project!
"""

import random

def main():
    print("🎲 Number Guessing Game")
    diff=input("Enter difficulty level (easy, medium, hard): ")
    if(diff=="easy"):
        print("I'm thinking of a number between 1 and 50...")
        secret_number = random.randint(1, 50)
    elif(diff=="medium"):
        print("I'm thinking of a number between 1 and 100...")
        secret_number = random.randint(1, 100)
    elif(diff=="hard"):
        print("I'm thinking of a number between 1 and 500...")
        secret_number = random.randint(1, 500)
    print()
    
    # TODO: Generate a random number between 1 and 100
    # Hint: Use random.randint(1, 100)
    #secret_number = random.randint(1, 100)
    
    # TODO: Initialize attempt counter
    attempts = 0
    
    # TODO: Create a game loop that:
    # 1. Asks for user's guess (convert to int)
    # 2. Increment attempts
    # 3. Compare guess to secret_number
    # 4. Print "Too high!" or "Too low!" hints
    # 5. Break loop when guess is correct
    # 6. Print congratulations with number of attempts
    
    while True:
        guess = int(input("Enter your guess:"))
        attempts +=1
        if guess==secret_number:
            print("Congratulations! You guessed the number in",attempts,"attempts")
            choice=input("Wanna play again? ")
            if (choice.lower()=="yes"):
                main()
            else:
                exit()
        elif guess<secret_number:
            print("Too low!")
        else:
            print("Too high!")

if __name__ == "__main__":
    main()