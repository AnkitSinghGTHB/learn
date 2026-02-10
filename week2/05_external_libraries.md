# Lesson 05: External Libraries & HTTP Requests

## 📚 Overview
Learn to use external Python packages, focusing on `requests` - the most popular HTTP library.

---

## 1. Installing Packages with pip

```bash
# Install a package
pip install requests

# Install specific version
pip install requests==2.28.0

# Install from requirements.txt
pip install -r requirements.txt

# List installed packages
pip list

# Save current packages to requirements.txt
pip freeze > requirements.txt
```

### Virtual Environments (Best Practice!)
```bash
# Create a virtual environment
python -m venv venv

# Activate it (Windows)
venv\Scripts\activate

# Activate it (Mac/Linux)
source venv/bin/activate

# Install packages (only in this environment)
pip install requests

# Deactivate when done
deactivate
```

---

## 2. Making HTTP Requests with `requests`

### Basic GET Request
```python
import requests

# Simple GET request
response = requests.get("https://api.github.com")

# Check status
print(response.status_code)  # 200 = success

# Get response body
print(response.text)         # Raw text
print(response.json())       # Parse as JSON (dict)

# Response headers
print(response.headers["Content-Type"])
```

### GET with Parameters
```python
# Query parameters (added to URL)
params = {
    "q": "python",
    "sort": "stars",
    "order": "desc"
}
response = requests.get(
    "https://api.github.com/search/repositories",
    params=params
)
# Actual URL: https://api.github.com/search/repositories?q=python&sort=stars&order=desc

data = response.json()
print(f"Found {data['total_count']} repositories")
```

### POST Request
```python
# Sending data (form or JSON)
payload = {
    "title": "New Post",
    "body": "This is the content",
    "userId": 1
}

# Form data
response = requests.post(
    "https://jsonplaceholder.typicode.com/posts",
    data=payload
)

# JSON data (more common for APIs)
response = requests.post(
    "https://jsonplaceholder.typicode.com/posts",
    json=payload  # Automatically sets Content-Type header
)

print(response.json())
```

### Headers and Authentication
```python
# Custom headers
headers = {
    "Authorization": "Bearer YOUR_TOKEN_HERE",
    "User-Agent": "MyApp/1.0",
    "Accept": "application/json"
}

response = requests.get(
    "https://api.github.com/user",
    headers=headers
)

# Basic authentication
response = requests.get(
    "https://api.example.com/data",
    auth=("username", "password")
)
```

---

## 3. Handling Responses

```python
import requests

def fetch_data(url):
    try:
        response = requests.get(url, timeout=10)
        
        # Raise an exception for bad status codes (4xx, 5xx)
        response.raise_for_status()
        
        return response.json()
    
    except requests.exceptions.Timeout:
        print("Request timed out!")
    except requests.exceptions.ConnectionError:
        print("Connection error!")
    except requests.exceptions.HTTPError as e:
        print(f"HTTP error: {e}")
    except requests.exceptions.RequestException as e:
        print(f"Request failed: {e}")
    
    return None
```

### Status Code Reference
| Code | Meaning |
|------|---------|
| 200 | OK - Success |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Server Error |

---

## 4. Working with APIs - Practical Examples

### Example 1: Weather API
```python
import requests

def get_weather(city):
    # Using wttr.in (free, no API key needed)
    url = f"https://wttr.in/{city}?format=j1"
    
    response = requests.get(url, timeout=10)
    if response.status_code == 200:
        data = response.json()
        current = data["current_condition"][0]
        return {
            "city": city,
            "temp_c": current["temp_C"],
            "description": current["weatherDesc"][0]["value"],
            "humidity": current["humidity"]
        }
    return None

weather = get_weather("Mumbai")
print(f"Temperature in {weather['city']}: {weather['temp_c']}°C")
```

### Example 2: Random User Generator
```python
import requests

def get_random_user():
    response = requests.get("https://randomuser.me/api/")
    if response.status_code == 200:
        user = response.json()["results"][0]
        return {
            "name": f"{user['name']['first']} {user['name']['last']}",
            "email": user["email"],
            "country": user["location"]["country"]
        }
    return None

user = get_random_user()
print(f"Random user: {user['name']} from {user['country']}")
```

### Example 3: GitHub API
```python
import requests

def get_user_repos(username):
    url = f"https://api.github.com/users/{username}/repos"
    response = requests.get(url)
    
    if response.status_code == 200:
        repos = response.json()
        return [
            {
                "name": repo["name"],
                "stars": repo["stargazers_count"],
                "url": repo["html_url"]
            }
            for repo in repos
        ]
    return []

repos = get_user_repos("octocat")
for repo in repos[:5]:
    print(f"⭐ {repo['stars']} - {repo['name']}")
```

---

## 5. Downloading Files

```python
import requests

def download_file(url, filename):
    response = requests.get(url, stream=True)
    response.raise_for_status()
    
    with open(filename, "wb") as file:
        for chunk in response.iter_content(chunk_size=8192):
            file.write(chunk)
    
    print(f"Downloaded: {filename}")

# Download an image
download_file(
    "https://via.placeholder.com/150",
    "placeholder.png"
)
```

---

## 6. Session Objects (For Multiple Requests)

```python
import requests

# Create a session to persist settings across requests
session = requests.Session()

# Set default headers
session.headers.update({
    "Authorization": "Bearer YOUR_TOKEN",
    "User-Agent": "MyApp/1.0"
})

# All requests through session share these settings
response1 = session.get("https://api.example.com/data")
response2 = session.get("https://api.example.com/more")

# Session also persists cookies automatically!
```

---

## 🧪 Practice: Contact Manager with API (Optional Enhancement)

```python
# Enhance your Contact Manager to sync with a mock API

import requests

class ContactAPI:
    BASE_URL = "https://jsonplaceholder.typicode.com/users"
    
    def fetch_sample_contacts(self):
        """Fetch sample contacts from the API."""
        response = requests.get(self.BASE_URL)
        if response.status_code == 200:
            users = response.json()
            return [
                {
                    "name": user["name"],
                    "phone": user["phone"],
                    "email": user["email"]
                }
                for user in users
            ]
        return []

# Test it:
# api = ContactAPI()
# contacts = api.fetch_sample_contacts()
# for contact in contacts[:3]:
#     print(f"{contact['name']}: {contact['phone']}")
```

---

## 📋 Requirements.txt for Week 2

```
# Save this as requirements.txt
requests>=2.28.0
```

---

## 🎯 Summary

1. **pip** - Install Python packages
2. **requests.get()** - Fetch data
3. **requests.post()** - Send data
4. **response.json()** - Parse JSON response
5. **response.raise_for_status()** - Check for errors
6. **Try/except** - Handle network errors gracefully
7. **Sessions** - Share settings across requests

---

## 📖 Next Up
Time to build the **Contact Manager 2.0** project! 🚀
