609. Find Duplicate File in System
Medium
Topics
 # 609. Find Duplicate File in System
 
 **Difficulty:** Medium  
 **Topics:** Hash Table / String  
 
 ---
 
 ## Problem Statement
 
 Given a list of directory information, including the directory path and all the files with their contents in that directory, return all duplicate files in the file system in terms of their paths. You may return the answer in any order.
 
 A group of duplicate files consists of at least two files that have the same content.
 
 A single directory information string in the input list has the following format:
 
 ```text
 root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)
 ```
 
 It means there are n files (f1.txt, f2.txt ... fn.txt) with content (f1_content, f2_content ... fn_content) respectively in the directory "root/d1/d2/.../dm". Note that n >= 1 and m >= 0. If m = 0, it means the directory is just the root directory.
 
 The output is a list of groups of duplicate file paths. For each group, it contains all the file paths of the files that have the same content. A file path has the following format:
 
 ```text
 directory_path/file_name.txt
 ```
 
 ---
 
 ## Examples
 
 ### Example 1
 
 **Input:**
 
 ```text
 paths = ["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
 ```
 
 **Output:**
 
 ```text
 [["root/a/2.txt", "root/c/d/4.txt", "root/4.txt"], ["root/a/1.txt", "root/c/3.txt"]]
 ```
 
 ### Example 2
 
 **Input:**
 
 ```text
 paths = ["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)"]
 ```
 
 **Output:**
 
 ```text
 [["root/a/2.txt", "root/c/d/4.txt"], ["root/a/1.txt", "root/c/3.txt"]]
 ```
 
 ---
 
 ## Constraints
 
 - `1 <= paths.length <= 2 * 10^4`
 - `1 <= paths[i].length <= 3000`
 - `1 <= sum(paths[i].length) <= 5 * 10^5`
 - `paths[i]` consists of English letters, digits, `/`, `.`, `(`, `)`, and spaces.
 - No files or directories share the same name in the same directory.
 - Each given directory information string represents a unique directory.
 - A single blank space separates the directory path and file information.
 
 ---
 
 ## Follow-up
 
 1. Imagine you are given a real file system. How will you search files: DFS or BFS?
 2. If the file content is very large (GB level), how will you modify your solution?
 3. If you can only read the file 1 KB at a time, how will you modify your solution?
 4. What is the time complexity of your modified solution? What is the most time-consuming part and memory-consuming part of it? How can you optimize it?
 5. How can you make sure the duplicate files you find are not false positives?