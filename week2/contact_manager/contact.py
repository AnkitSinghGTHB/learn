"""
Contact class for the Contact Manager 2.0 project.

This module defines the Contact class with proper OOP design including:
- Constructor with validation
- Dunder methods (__str__, __repr__, __eq__)
- Serialization methods (to_dict, from_dict)
"""


class Contact:
    """
    Represents a contact with name, phone, and optional email.
    
    Attributes:
        name (str): The contact's full name
        phone (str): The contact's phone number
        email (str, optional): The contact's email address
    
    Example:
        >>> contact = Contact("Alice", "123-456-7890", "alice@email.com")
        >>> print(contact)
        Alice - 123-456-7890 (alice@email.com)
    """
    
    def __init__(self, name, phone, email=None):
        """
        Initialize a new Contact.
        
        Args:
            name (str): The contact's name (required)
            phone (str): The contact's phone number (required)
            email (str, optional): The contact's email address
        
        Raises:
            ValueError: If name or phone is empty
        """
        if not name or not name.strip():
            raise ValueError("Name cannot be empty")
        if not phone or not phone.strip():
            raise ValueError("Phone cannot be empty")
        
        self.name = name.strip()
        self.phone = phone.strip()
        self.email = email.strip() if email else None
    
    def __str__(self):
        """Return a user-friendly string representation."""
        if self.email:
            return f"{self.name} - {self.phone} ({self.email})"
        return f"{self.name} - {self.phone}"
    
    def __repr__(self):
        """Return a developer-friendly representation."""
        return f"Contact('{self.name}', '{self.phone}', '{self.email}')"
    
    def __eq__(self, other):
        """Two contacts are equal if they have the same name and phone."""
        if not isinstance(other, Contact):
            return False
        return self.name.lower() == other.name.lower() and self.phone == other.phone
    
    def __hash__(self):
        """Make Contact hashable (for use in sets/dicts)."""
        return hash((self.name.lower(), self.phone))
    
    def to_dict(self):
        """
        Convert the contact to a dictionary (for JSON serialization).
        
        Returns:
            dict: Dictionary with name, phone, and email keys
        """
        return {
            "name": self.name,
            "phone": self.phone,
            "email": self.email
        }
    
    @classmethod
    def from_dict(cls, data):
        """
        Create a Contact from a dictionary.
        
        Args:
            data (dict): Dictionary with 'name', 'phone', and optional 'email'
        
        Returns:
            Contact: A new Contact instance
        
        Example:
            >>> data = {"name": "Alice", "phone": "123-456-7890"}
            >>> contact = Contact.from_dict(data)
        """
        return cls(
            name=data.get("name", ""),
            phone=data.get("phone", ""),
            email=data.get("email")
        )
    
    def matches(self, query):
        """
        Check if the contact matches a search query.
        
        Args:
            query (str): The search string
        
        Returns:
            bool: True if query matches name, phone, or email
        """
        query = query.lower()
        if query in self.name.lower():
            return True
        if query in self.phone:
            return True
        if self.email and query in self.email.lower():
            return True
        return False


# Example usage and testing
if __name__ == "__main__":
    # Create contacts
    c1 = Contact("Alice Smith", "123-456-7890", "alice@email.com")
    c2 = Contact("Bob Jones", "098-765-4321")
    c3 = Contact("Alice Smith", "123-456-7890")  # Same as c1
    
    # Test __str__
    print("String representation:")
    print(f"  {c1}")
    print(f"  {c2}")
    
    # Test __repr__
    print(f"\nRepr: {repr(c1)}")
    
    # Test __eq__
    print(f"\nc1 == c3: {c1 == c3}")  # True
    print(f"c1 == c2: {c1 == c2}")    # False
    
    # Test to_dict
    print(f"\nAs dict: {c1.to_dict()}")
    
    # Test from_dict
    data = {"name": "Charlie", "phone": "555-1234", "email": "charlie@test.com"}
    c4 = Contact.from_dict(data)
    print(f"From dict: {c4}")
    
    # Test matches
    print(f"\nc1 matches 'alice': {c1.matches('alice')}")
    print(f"c1 matches '456': {c1.matches('456')}")
    print(f"c1 matches 'bob': {c1.matches('bob')}")
