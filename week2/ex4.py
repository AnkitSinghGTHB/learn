# Week 2 - Exercise 4: File I/O and External Libraries Practice

"""
This file contains exercises for practicing:
- File reading and writing
- JSON handling
- CSV handling
- HTTP requests with the requests library
"""

import json
from pathlib import Path

# =============================================================================
# EXERCISE 4.1: Text File Operations
# =============================================================================

def create_sample_log():
    """Create a sample log file for testing."""
    log_content = """2024-01-15 10:30:00 INFO User logged in
2024-01-15 10:31:00 ERROR Database connection failed
2024-01-15 10:32:00 WARNING Low memory detected
2024-01-15 10:33:00 INFO User performed action
2024-01-15 10:34:00 ERROR File not found
2024-01-15 10:35:00 INFO Process completed
2024-01-15 10:36:00 WARNING High CPU usage
2024-01-15 10:37:00 INFO User logged out
"""
    with open("sample_log.txt", "w") as f:
        f.write(log_content)
    print("Created sample_log.txt")


def analyze_log(filepath):
    """
    Analyze a log file and count entries by level.
    
    TODO: Implement this function to:
    1. Read the log file
    2. Count INFO, WARNING, and ERROR entries
    3. Return a dict like {"INFO": 4, "WARNING": 2, "ERROR": 2}
    """
    # TODO: Implement
    pass


def find_errors(filepath):
    """
    Find all ERROR lines in the log file.
    
    TODO: Return a list of error messages (just the message part, not timestamp).
    """
    # TODO: Implement
    pass


# =============================================================================
# EXERCISE 4.2: JSON File Operations
# =============================================================================

def save_settings(settings, filepath="settings.json"):
    """
    Save settings dictionary to a JSON file.
    
    TODO: Implement with proper error handling.
    """
    # TODO: Implement
    pass


def load_settings(filepath="settings.json"):
    """
    Load settings from a JSON file.
    
    TODO: Return settings dict, or empty dict if file doesn't exist.
    """
    # TODO: Implement
    pass


def update_setting(key, value, filepath="settings.json"):
    """
    Update a single setting in the JSON file.
    
    TODO: Load existing settings, update the key, save back.
    """
    # TODO: Implement
    pass


# =============================================================================
# EXERCISE 4.3: CSV Operations
# =============================================================================

def create_sample_csv():
    """Create a sample CSV file for testing."""
    import csv
    data = [
        ["Name", "Age", "City", "Score"],
        ["Alice", "30", "Mumbai", "85"],
        ["Bob", "25", "Delhi", "72"],
        ["Charlie", "35", "Bangalore", "91"],
        ["Diana", "28", "Chennai", "68"],
        ["Eve", "32", "Kolkata", "88"]
    ]
    with open("students.csv", "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerows(data)
    print("Created students.csv")


def read_csv_as_dicts(filepath):
    """
    Read a CSV file and return a list of dictionaries.
    
    TODO: Each row becomes a dict with headers as keys.
    Example: [{"Name": "Alice", "Age": "30", ...}, ...]
    """
    # TODO: Implement using csv.DictReader
    pass


def filter_csv_by_score(filepath, min_score):
    """
    Filter students by minimum score.
    
    TODO: Return list of student dicts where Score >= min_score.
    """
    # TODO: Implement using comprehension
    pass


def csv_to_json(csv_path, json_path):
    """
    Convert a CSV file to JSON format.
    
    TODO: Read CSV, convert to list of dicts, save as JSON.
    """
    # TODO: Implement
    pass


# =============================================================================
# EXERCISE 4.4: Path Operations with pathlib
# =============================================================================

def organize_files(directory):
    """
    Organize files in a directory by extension.
    
    TODO: 
    1. Create subdirectories for each file extension
    2. Move/copy files into appropriate directories
    3. Return a dict of {extension: [filenames]}
    
    Note: For safety, just return the plan, don't actually move files.
    """
    # TODO: Implement
    pass


def find_large_files(directory, min_size_kb=100):
    """
    Find files larger than min_size_kb in a directory.
    
    TODO: Return list of (filename, size_kb) tuples.
    """
    # TODO: Implement
    pass


# =============================================================================
# EXERCISE 4.5: HTTP Requests (requires 'requests' library)
# =============================================================================

def get_random_user():
    """
    Fetch a random user from https://randomuser.me/api/
    
    TODO: Return dict with name, email, and country.
    Handle potential errors gracefully.
    """
    try:
        import requests
        # TODO: Implement
        pass
    except ImportError:
        print("requests library not installed. Run: pip install requests")
        return None


def get_github_repos(username):
    """
    Fetch public repositories for a GitHub user.
    
    TODO: Return list of dicts with name, description, and stars.
    URL: https://api.github.com/users/{username}/repos
    """
    try:
        import requests
        # TODO: Implement
        pass
    except ImportError:
        print("requests library not installed. Run: pip install requests")
        return []


def get_weather(city):
    """
    Get weather for a city using wttr.in API.
    
    TODO: Return dict with city, temperature (C), and description.
    URL: https://wttr.in/{city}?format=j1
    """
    try:
        import requests
        # TODO: Implement
        pass
    except ImportError:
        print("requests library not installed. Run: pip install requests")
        return None


# =============================================================================
# EXERCISE 4.6: Combining Everything - Config Manager
# =============================================================================

class ConfigManager:
    """
    A class to manage application configuration stored in JSON.
    
    TODO: Implement:
    - __init__(filepath) - load config or create empty
    - get(key, default=None) - get a config value
    - set(key, value) - set and save a config value
    - delete(key) - remove a config key
    - all property - return copy of all settings
    - save() - save to file
    - load() - load from file
    """
    
    def __init__(self, filepath="config.json"):
        # TODO: Implement
        pass


# =============================================================================
# TEST ALL EXERCISES
# =============================================================================

if __name__ == "__main__":
    print("=" * 60)
    print("Testing File I/O and External Libraries Exercises")
    print("=" * 60)
    
    # Test log analysis
    print("\n--- Testing Log Analysis ---")
    create_sample_log()
    try:
        counts = analyze_log("sample_log.txt")
        print(f"Log counts: {counts}")
        errors = find_errors("sample_log.txt")
        print(f"Errors: {errors}")
    except Exception as e:
        print(f"Not implemented: {e}")
    
    # Test JSON operations
    print("\n--- Testing JSON Operations ---")
    try:
        settings = {"theme": "dark", "language": "en", "notifications": True}
        save_settings(settings)
        loaded = load_settings()
        print(f"Loaded settings: {loaded}")
        update_setting("theme", "light")
        print(f"After update: {load_settings()}")
    except Exception as e:
        print(f"Not implemented: {e}")
    
    # Test CSV operations
    print("\n--- Testing CSV Operations ---")
    create_sample_csv()
    try:
        students = read_csv_as_dicts("students.csv")
        print(f"Students: {students[:2]}...")
        high_scorers = filter_csv_by_score("students.csv", 80)
        print(f"High scorers: {high_scorers}")
    except Exception as e:
        print(f"Not implemented: {e}")
    
    # Test HTTP requests
    print("\n--- Testing HTTP Requests ---")
    try:
        user = get_random_user()
        if user:
            print(f"Random user: {user}")
        
        repos = get_github_repos("octocat")
        if repos:
            print(f"Octocat repos (first 3): {repos[:3]}")
        
        weather = get_weather("Mumbai")
        if weather:
            print(f"Weather: {weather}")
    except Exception as e:
        print(f"Not implemented or error: {e}")
    
    # Test ConfigManager
    print("\n--- Testing ConfigManager ---")
    try:
        config = ConfigManager("test_config.json")
        config.set("app_name", "MyApp")
        config.set("version", "1.0.0")
        print(f"App name: {config.get('app_name')}")
        print(f"All settings: {config.all}")
    except Exception as e:
        print(f"Not implemented: {e}")
    
    # Cleanup
    print("\n--- Cleanup ---")
    for f in ["sample_log.txt", "settings.json", "students.csv", 
              "test_config.json", "students.json"]:
        try:
            Path(f).unlink()
            print(f"Deleted {f}")
        except FileNotFoundError:
            pass
