# Week 05: Java Exception Handling, File I/O & Generics

## 🎯 Objective
Build **robust, production-grade Java applications** that handle errors gracefully, read/write files efficiently, and leverage generics for type-safe, reusable code. These three topics are **top interview favourites** — you'll encounter them in coding rounds, system design discussions, and code reviews.

## 📚 Topics Covered

| Day | Topic | File |
|-----|-------|------|
| 1 | Exception Basics — try/catch/finally, Exception Hierarchy | `01_exception_basics.md` |
| 2 | Checked vs Unchecked — throw, throws, Error Types | `02_checked_unchecked.md` |
| 3 | Custom Exceptions — Design Patterns & Best Practices | `03_custom_exceptions.md` |
| 4 | Exception Interview Patterns — Tricky Questions & Gotchas | `04_exception_interview_patterns.md` |
| 5 | File I/O — java.nio.file, Path, Files, Readers/Writers | `05_file_io.md` |
| 6 | Generics — Type Parameters, Bounded Types, Generic Methods | `06_generics.md` |
| 7 | Advanced Generics & Interview Patterns — Wildcards, PECS, Type Erasure | `07_generics_advanced_interview.md` |

## 🛠️ Hands-On Projects
1. **Safe Parser** — Read a file of mixed data, calculate sum, handle invalid lines gracefully
   - Located in: `safe_parser/`
2. **Generic Box** — Create a `Box<T>` class with constraints and utility methods
   - Located in: `generic_box/`
3. **Student Records** — Full File I/O + Exception Handling + Generics combined project
   - Located in: `student_records/`

## ✅ Success Checklist
- [ ] I understand the Exception class hierarchy (Throwable → Error / Exception → RuntimeException)
- [ ] I can write try-catch-finally blocks and know when each block executes
- [ ] I know the difference between checked and unchecked exceptions
- [ ] I can use `throw` to throw exceptions and `throws` to declare them
- [ ] I can create custom exception classes with constructors and messages
- [ ] I understand try-with-resources and `AutoCloseable`
- [ ] I can read/write files using `java.nio.file.Files` and `Path`
- [ ] I can use `BufferedReader` / `BufferedWriter` for efficient file operations
- [ ] I can create generic classes (`Box<T>`) and generic methods (`<T> void print(T item)`)
- [ ] I understand bounded type parameters (`<T extends Comparable<T>>`)
- [ ] I understand wildcards (`?`, `? extends T`, `? super T`) and PECS
- [ ] I know what type erasure is and how it affects generics at runtime
- [ ] I can solve common interview problems involving exceptions and generics
- [ ] I understand multi-catch, exception chaining, and suppressed exceptions

## 🚀 Getting Started
1. Read through the lesson files in order (01 → 07)
2. Type out every code example — don't just read!
3. Complete the exercises in `exercises.md`
4. Build the three hands-on projects
5. Revisit the interview patterns in lessons 04 and 07

## 📂 Folder Structure
```
week 5/
├── README.md
├── 01_exception_basics.md
├── 02_checked_unchecked.md
├── 03_custom_exceptions.md
├── 04_exception_interview_patterns.md
├── 05_file_io.md
├── 06_generics.md
├── 07_generics_advanced_interview.md
├── exercises.md
├── safe_parser/
│   └── SafeParser.java
├── generic_box/
│   └── GenericBoxDemo.java
└── student_records/
    └── StudentRecords.java
```

## 💡 Week 4 → Week 5 Connection
| Week 4 (What you know) | Week 5 (What's new) |
|-------------------------|---------------------|
| Creating classes & objects | Custom exception classes |
| `ArrayList`, `HashMap` storage | Type-safe `List<T>` via generics |
| Method overriding (`toString()`) | Overriding with generic return types |
| Encapsulation with `private` fields | Defensive programming with exceptions |
| `Comparable` / `Comparator` | Bounded generics `<T extends Comparable<T>>` |
| Writing methods | Methods that declare `throws` |
