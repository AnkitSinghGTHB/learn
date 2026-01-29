# 📦 Hello Git Project

Learn the basics of Git version control!

## Objectives
- Initialize a Git repository
- Make commits
- (Optional) Push to GitHub

## Step-by-Step Guide

### 1. Initialize Repository
```powershell
# Navigate to this folder
cd "c:\Users\ankit\Downloads\learning\week 1\hello_git"

# Initialize Git
git init
```

### 2. Create Your First File
Create `hello.py`:
```python
print("Hello, Git!")
print("This is my first version-controlled file.")
```

### 3. Stage and Commit
```powershell
# Check status - see untracked file
git status

# Stage the file
git add hello.py

# Check status again - see staged file
git status

# Make your first commit
git commit -m "Add hello.py - my first commit!"
```

### 4. Make Changes and Commit Again
Edit `hello.py`:
```python
print("Hello, Git!")
print("This is my first version-controlled file.")
print("I just made a change!")  # New line
```

```powershell
# See the difference
git diff

# Stage and commit
git add hello.py
git commit -m "Add new print statement"
```

### 5. View History
```powershell
git log --oneline
```

## Bonus: Push to GitHub

1. Create a GitHub account at [github.com](https://github.com)
2. Create a new repository (without README)
3. Follow GitHub's instructions:
```powershell
git remote add origin https://github.com/YOUR_USERNAME/hello-git.git
git branch -M main
git push -u origin main
```

## Git Cheat Sheet

| Command | Description |
|---------|-------------|
| `git init` | Initialize repository |
| `git status` | Check current state |
| `git add .` | Stage all changes |
| `git commit -m "msg"` | Commit with message |
| `git log --oneline` | View commit history |
| `git diff` | See unstaged changes |
| `git push` | Push to remote |
| `git pull` | Pull from remote |

## Success Criteria
- [ ] Created a Git repository
- [ ] Made at least 2 commits
- [ ] Can view commit history
- [ ] (Bonus) Pushed to GitHub
