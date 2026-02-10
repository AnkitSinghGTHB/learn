"""
Storage module for the Contact Manager 2.0 project.

This module handles persisting contacts to a JSON file and loading them back.
It demonstrates:
- File I/O with context managers
- JSON serialization/deserialization
- Error handling
- Path handling with pathlib
"""

import json
from pathlib import Path
from contact import Contact


class ContactStorage:
    """
    Handles saving and loading contacts from a JSON file.
    
    Attributes:
        filepath (Path): Path to the JSON storage file
        contacts (list): List of Contact objects
    
    Example:
        >>> storage = ContactStorage("contacts.json")
        >>> storage.add(Contact("Alice", "123-456-7890"))
        >>> storage.save()
    """
    
    def __init__(self, filepath="contacts.json"):
        """
        Initialize the storage with a file path.
        
        Args:
            filepath (str): Path to the JSON file for storing contacts
        """
        self.filepath = Path(filepath)
        self.contacts = []
        self.load()
    
    def load(self):
        """
        Load contacts from the JSON file.
        
        If the file doesn't exist or is invalid, starts with an empty list.
        """
        if not self.filepath.exists():
            self.contacts = []
            return
        
        try:
            with open(self.filepath, "r", encoding="utf-8") as file:
                data = json.load(file)
                self.contacts = [Contact.from_dict(c) for c in data]
                print(f"Loaded {len(self.contacts)} contacts from {self.filepath}")
        except json.JSONDecodeError:
            print(f"Warning: {self.filepath} is corrupted. Starting fresh.")
            self.contacts = []
        except Exception as e:
            print(f"Error loading contacts: {e}")
            self.contacts = []
    
    def save(self):
        """
        Save all contacts to the JSON file.
        
        Returns:
            bool: True if save was successful, False otherwise
        """
        try:
            # Create parent directories if they don't exist
            self.filepath.parent.mkdir(parents=True, exist_ok=True)
            
            with open(self.filepath, "w", encoding="utf-8") as file:
                data = [c.to_dict() for c in self.contacts]
                json.dump(data, file, indent=2, ensure_ascii=False)
            return True
        except Exception as e:
            print(f"Error saving contacts: {e}")
            return False
    
    def add(self, contact):
        """
        Add a contact and save to file.
        
        Args:
            contact (Contact): The contact to add
        
        Returns:
            bool: True if added successfully
        """
        # Check for duplicates
        if contact in self.contacts:
            print(f"Contact '{contact.name}' already exists!")
            return False
        
        self.contacts.append(contact)
        self.save()
        return True
    
    def remove(self, name):
        """
        Remove a contact by name.
        
        Args:
            name (str): Name of the contact to remove
        
        Returns:
            bool: True if removed, False if not found
        """
        for contact in self.contacts:
            if contact.name.lower() == name.lower():
                self.contacts.remove(contact)
                self.save()
                return True
        return False
    
    def find(self, query):
        """
        Find contacts matching a search query.
        
        Uses list comprehension to filter contacts!
        
        Args:
            query (str): Search string to match against name, phone, or email
        
        Returns:
            list: List of matching Contact objects
        """
        # Using list comprehension as required by the project!
        return [c for c in self.contacts if c.matches(query)]
    
    def get_all(self):
        """
        Get all contacts.
        
        Returns:
            list: Copy of the contacts list
        """
        return self.contacts.copy()
    
    def get_by_name(self, name):
        """
        Get a specific contact by exact name.
        
        Args:
            name (str): Exact name to search for
        
        Returns:
            Contact or None: The contact if found
        """
        for contact in self.contacts:
            if contact.name.lower() == name.lower():
                return contact
        return None
    
    def update(self, name, **kwargs):
        """
        Update a contact's information.
        
        Args:
            name (str): Name of the contact to update
            **kwargs: Fields to update (phone, email)
        
        Returns:
            bool: True if updated successfully
        """
        contact = self.get_by_name(name)
        if not contact:
            return False
        
        if "phone" in kwargs:
            contact.phone = kwargs["phone"]
        if "email" in kwargs:
            contact.email = kwargs["email"]
        if "new_name" in kwargs:
            contact.name = kwargs["new_name"]
        
        self.save()
        return True
    
    def __len__(self):
        """Return the number of contacts."""
        return len(self.contacts)
    
    def __iter__(self):
        """Allow iteration over contacts."""
        return iter(self.contacts)


# Example usage and testing
if __name__ == "__main__":
    print("Testing ContactStorage...")
    
    # Create storage (will load existing or start fresh)
    storage = ContactStorage("test_contacts.json")
    
    # Add some contacts
    storage.add(Contact("Alice Smith", "123-456-7890", "alice@email.com"))
    storage.add(Contact("Bob Jones", "098-765-4321", "bob@email.com"))
    storage.add(Contact("Charlie Brown", "555-1234"))
    
    # List all contacts
    print(f"\nAll contacts ({len(storage)} total):")
    for contact in storage:
        print(f"  - {contact}")
    
    # Search for contacts
    print("\nSearching for 'ali':")
    results = storage.find("ali")
    for contact in results:
        print(f"  - {contact}")
    
    # Update a contact
    storage.update("Charlie Brown", email="charlie@email.com")
    print(f"\nAfter update: {storage.get_by_name('Charlie Brown')}")
    
    # Clean up test file
    Path("test_contacts.json").unlink(missing_ok=True)
    print("\nTest complete! (test file deleted)")
