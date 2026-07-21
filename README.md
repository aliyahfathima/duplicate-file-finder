# 🔍 Duplicate File Finder & Disk Space Analyzer

A core-Java CLI tool that recursively scans a directory, detects **exact duplicate files** using SHA-256 content hashing, reports wasted disk space, and safely removes duplicates — built entirely with the JDK standard library (no external dependencies).

## Why I built this
Most beginner Java projects are CRUD apps. I wanted something that solves a **real problem** (duplicate files eating up disk space) while demonstrating hashing, recursion, and algorithmic thinking — not just syntax.

## Tech Stack
- Core Java (JDK 17+)
- `java.security.MessageDigest` — SHA-256 content hashing
- `java.io` — recursive file traversal & stream-based reading
- `java.util.HashMap` — O(n) duplicate grouping
- No external libraries, no database — pure core Java

## How It Works
1. Recursively walks the given folder (handles unlimited subfolder depth).
2. Computes a SHA-256 hash for every file's **content** (not name/size — so renamed duplicates are still caught).
3. Groups files by hash in a `HashMap<String, List<File>>` — any hash shared by 2+ files is a duplicate group.
4. Reports total wasted disk space and can delete duplicates (keeping the first copy).


## Why hashing instead of comparing file names/sizes?
Two files can have the same name/size and different content, or different names and identical content. Content-based SHA-256 hashing is the only way to guarantee two files are byte-for-byte identical.


## How to Run
```bash
cd src
javac *.java
java Main
```

## Sample Output

===== Duplicate File Finder & Disk Space Analyzer =====
1. Scan a folder
2. View duplicate files
3. View wasted space
4. Delete duplicates (keep first copy)
5. Exit
Choose an option: 1
Enter folder path to scan: D:\filecheck
Scan complete in 82 ms.
Files scanned: 3
Files skipped (unreadable): 0

===== Duplicate File Finder & Disk Space Analyzer =====
1. Scan a folder
2. View duplicate files
3. View wasted space
4. Delete duplicates (keep first copy)
5. Exit
Choose an option: 2
No duplicate files found.

===== Duplicate File Finder & Disk Space Analyzer =====
1. Scan a folder
2. View duplicate files
3. View wasted space
4. Delete duplicates (keep first copy)
5. Exit
Choose an option: 3
Total wasted space: 0.00 MB

===== Duplicate File Finder & Disk Space Analyzer =====
1. Scan a folder
2. View duplicate files
3. View wasted space
4. Delete duplicates (keep first copy)
5. Exit
Choose an option: 4
Are you sure you want to delete duplicate files? (yes/no): yes
Deleted 0 duplicate file(s).

===== Duplicate File Finder & Disk Space Analyzer =====
1. Scan a folder
2. View duplicate files
3. View wasted space
4. Delete duplicates (keep first copy)
5. Exit
Choose an option: 5
Exiting. Bye!

