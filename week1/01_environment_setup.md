# Environment Setup Guide

## 1. Install Python 3.10+

### Windows
1. Download from [python.org](https://www.python.org/downloads/)
2. **Important**: Check ✅ "Add Python to PATH" during installation
3. Verify installation:
   ```powershell
   python --version
   # Should show Python 3.10.x or higher
   ```

## 2. Install VS Code

1. Download from [code.visualstudio.com](https://code.visualstudio.com/)
2. Install these extensions:
   - **Python** (Microsoft) - Essential
   - **Pylance** - Better autocomplete
   - **Python Indent** - Auto-indentation

## 3. Virtual Environments (venv)

Virtual environments isolate your project dependencies.

### Create a Virtual Environment
```powershell
# Navigate to your project folder
cd your-project-folder

# Create venv
python -m venv venv

# Activate it (Windows)
.\venv\Scripts\activate

# Your prompt should now show (venv)
```

### Deactivate
```powershell
deactivate
```

### Why Use venv?
- Keeps project dependencies separate
- Avoid conflicts between projects
- Easy to share requirements

### Save Dependencies
```powershell
pip freeze > requirements.txt
```

### Install from Requirements
```powershell
pip install -r requirements.txt
```

## 4. Git Basics

### Install Git
Download from [git-scm.com](https://git-scm.com/downloads)

### Configure Git
```powershell
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### Essential Commands
 learn git: [Learn Git Branching](https://learngitbranching.js.org/)
```powershell
# Initialize a repository
git init

# Check status
git status

# Stage files
git add .                    # Stage all
git add filename.py          # Stage specific file

# Commit
git commit -m "Your message"

# View history
git log --oneline

# Push to remote
git push origin master
```

## 5. Your First Python File

Create `hello.py`:
```python
print("Hello, Python!")
```

Run it:
```powershell
python hello.py
```

---
**Next**: [02_python_basics.md](02_python_basics.md)
