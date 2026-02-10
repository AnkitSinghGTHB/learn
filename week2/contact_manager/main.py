#!/usr/bin/env python3
"""
Contact Manager 2.0 - Main Application

A CLI-based contact manager demonstrating:
- Object-Oriented Programming (Contact class)
- File I/O (JSON storage)
- List Comprehensions (search/filter)
- Error Handling

Usage:
    python main.py

Commands:
    add     - Add a new contact
    list    - List all contacts
    search  - Search for contacts
    edit    - Edit a contact
    delete  - Remove a contact
    import  - Import sample contacts from API
    export  - Export contacts to CSV
    quit    - Exit the program
"""

from contact import Contact
from storage import ContactStorage


def print_menu():
    """Display the main menu."""
    print("\n" + "=" * 40)
    print("       📱 CONTACT MANAGER 2.0")
    print("=" * 40)
    print("  1. Add contact")
    print("  2. List all contacts")
    print("  3. Search contacts")
    print("  4. Edit contact")
    print("  5. Delete contact")
    print("  6. Import from API")
    print("  7. Export to CSV")
    print("  0. Quit")
    print("=" * 40)


def add_contact(storage):
    """Add a new contact."""
    print("\n--- Add New Contact ---")
    
    name = input("Name: ").strip()
    if not name:
        print("❌ Name cannot be empty!")
        return
    
    phone = input("Phone: ").strip()
    if not phone:
        print("❌ Phone cannot be empty!")
        return
    
    email = input("Email (optional): ").strip() or None
    
    try:
        contact = Contact(name, phone, email)
        if storage.add(contact):
            print(f"✅ Added: {contact}")
        else:
            print("❌ Contact already exists!")
    except ValueError as e:
        print(f"❌ Error: {e}")


def list_contacts(storage):
    """List all contacts."""
    print("\n--- All Contacts ---")
    
    if len(storage) == 0:
        print("📭 No contacts yet. Add some!")
        return
    
    # Using enumerate for numbered list
    for i, contact in enumerate(storage, 1):
        print(f"  {i}. {contact}")
    
    print(f"\nTotal: {len(storage)} contact(s)")


def search_contacts(storage):
    """Search for contacts."""
    print("\n--- Search Contacts ---")
    
    query = input("Search term: ").strip()
    if not query:
        print("❌ Please enter a search term!")
        return
    
    # This uses list comprehension inside storage.find()!
    results = storage.find(query)
    
    if not results:
        print(f"🔍 No contacts found matching '{query}'")
        return
    
    print(f"\n🔍 Found {len(results)} contact(s):")
    for contact in results:
        print(f"  - {contact}")


def edit_contact(storage):
    """Edit an existing contact."""
    print("\n--- Edit Contact ---")
    
    name = input("Enter contact name to edit: ").strip()
    contact = storage.get_by_name(name)
    
    if not contact:
        print(f"❌ Contact '{name}' not found!")
        return
    
    print(f"Current: {contact}")
    print("(Press Enter to keep current value)")
    
    new_name = input(f"New name [{contact.name}]: ").strip()
    new_phone = input(f"New phone [{contact.phone}]: ").strip()
    new_email = input(f"New email [{contact.email or 'none'}]: ").strip()
    
    updates = {}
    if new_name:
        updates["new_name"] = new_name
    if new_phone:
        updates["phone"] = new_phone
    if new_email:
        updates["email"] = new_email if new_email.lower() != "none" else None
    
    if updates:
        storage.update(name, **updates)
        print(f"✅ Updated: {storage.get_by_name(new_name or name)}")
    else:
        print("ℹ️ No changes made.")


def delete_contact(storage):
    """Delete a contact."""
    print("\n--- Delete Contact ---")
    
    name = input("Enter contact name to delete: ").strip()
    
    if not name:
        print("❌ Please enter a name!")
        return
    
    contact = storage.get_by_name(name)
    if not contact:
        print(f"❌ Contact '{name}' not found!")
        return
    
    confirm = input(f"Delete '{contact.name}'? (y/n): ").strip().lower()
    if confirm == "y":
        storage.remove(name)
        print(f"✅ Deleted: {name}")
    else:
        print("ℹ️ Cancelled.")


def import_from_api(storage):
    """Import sample contacts from an API."""
    print("\n--- Import from API ---")
    
    try:
        import requests
    except ImportError:
        print("❌ 'requests' library not installed.")
        print("   Run: pip install requests")
        return
    
    print("Fetching sample contacts from randomuser.me...")
    
    try:
        response = requests.get("https://randomuser.me/api/?results=5", timeout=10)
        response.raise_for_status()
        
        users = response.json()["results"]
        imported = 0
        
        for user in users:
            name = f"{user['name']['first']} {user['name']['last']}"
            phone = user["phone"]
            email = user["email"]
            
            contact = Contact(name, phone, email)
            if storage.add(contact):
                imported += 1
        
        print(f"✅ Imported {imported} new contact(s)!")
        
    except requests.exceptions.RequestException as e:
        print(f"❌ API error: {e}")


def export_to_csv(storage):
    """Export contacts to a CSV file."""
    print("\n--- Export to CSV ---")
    
    import csv
    
    if len(storage) == 0:
        print("❌ No contacts to export!")
        return
    
    filename = input("Filename (default: contacts_export.csv): ").strip()
    if not filename:
        filename = "contacts_export.csv"
    if not filename.endswith(".csv"):
        filename += ".csv"
    
    try:
        with open(filename, "w", newline="", encoding="utf-8") as file:
            writer = csv.writer(file)
            writer.writerow(["Name", "Phone", "Email"])
            
            for contact in storage:
                writer.writerow([contact.name, contact.phone, contact.email or ""])
        
        print(f"✅ Exported {len(storage)} contacts to {filename}")
        
    except Exception as e:
        print(f"❌ Export error: {e}")


def main():
    """Main application loop."""
    print("\n🚀 Starting Contact Manager 2.0...")
    
    # Initialize storage (loads existing contacts)
    storage = ContactStorage("contacts.json")
    
    while True:
        print_menu()
        
        choice = input("Enter choice (0-7): ").strip()
        
        if choice == "1":
            add_contact(storage)
        elif choice == "2":
            list_contacts(storage)
        elif choice == "3":
            search_contacts(storage)
        elif choice == "4":
            edit_contact(storage)
        elif choice == "5":
            delete_contact(storage)
        elif choice == "6":
            import_from_api(storage)
        elif choice == "7":
            export_to_csv(storage)
        elif choice == "0":
            print("\n👋 Goodbye!")
            break
        else:
            print("❌ Invalid choice. Please try again.")


if __name__ == "__main__":
    main()
