# 388. Longest Absolute File Path - Explanation

## Problem Overview

The task is to find the length of the **longest absolute path to a file** in a given string representing a hierarchical file system. 

The file system representation uses:
* `\n` to separate different files/directories.
* `\t` to specify the depth/level of a file or directory (each additional `\t` represents one level deeper).
* A dot (`.`) to distinguish files from directories (only files have a `.`).

If there are no files in the system, we should return `0`.

---

## Key Intuition: Depth-Tracking Array (or Stack)

Since the input represents a depth-first representation of a file system hierarchy, we can process it **line-by-line** (or path-by-path):

1. **Depth Detection**: The number of leading `\t` characters tells us the current depth level (0-indexed).
2. **Path Length Accumulation**:
   * We need a way to look up the length of the parent directory path for any given depth.
   * We can use an array `pathLen` where `pathLen[depth]` stores the total prefix length up to `depth` (including the separating `/` character).
3. **Transition**:
   * If the current item is a **directory**:
     * We update `pathLen[depth + 1] = pathLen[depth] + nameLen + 1`. The `+ 1` accounts for the `/` separator.
   * If the current item is a **file**:
     * The absolute path length to this file is `pathLen[depth] + nameLen`.
     * We compare this with our global maximum `maxLen` and update it if it's larger.

---

## Step-by-Step Walkthrough

Let's trace the algorithm with **Example 2**:
`input = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"`

Initial State:
* `maxLen = 0`
* `pathLen = [0, 0, 0, 0, 0]`

| Step | Line | Leading Tabs (`depth`) | Name Length (`nameLen`) | Is File? | State Update | `pathLen` Array | `maxLen` |
|:---:| :--- |:---:|:---:|:---:| :--- | :--- |:---:|
| **1** | `"dir"` | `0` | `3` | No | Directory path length update at `depth + 1`: `pathLen[1] = pathLen[0] + 3 + 1 = 4` | `[0, 4, 0, 0, 0]` | `0` |
| **2** | `"\tsubdir1"` | `1` | `7` | No | Directory path length update at `depth + 1`: `pathLen[2] = pathLen[1] + 7 + 1 = 12` | `[0, 4, 12, 0, 0]` | `0` |
| **3** | `"\t\tfile1.ext"` | `2` | `9` | Yes | File absolute path: `pathLen[2] + 9 = 12 + 9 = 21`. Update `maxLen`. | `[0, 4, 12, 0, 0]` | `21` |
| **4** | `"\t\tsubsubdir1"` | `2` | `10` | No | Directory path length update at `depth + 1`: `pathLen[3] = pathLen[2] + 10 + 1 = 23` | `[0, 4, 12, 23, 0]` | `21` |
| **5** | `"\tsubdir2"` | `1` | `7` | No | Directory path length update at `depth + 1`: `pathLen[2] = pathLen[1] + 7 + 1 = 12` | `[0, 4, 12, 23, 0]` | `21` |
| **6** | `"\t\tsubsubdir2"` | `2` | `10` | No | Directory path length update at `depth + 1`: `pathLen[3] = pathLen[2] + 10 + 1 = 23` | `[0, 4, 12, 23, 0]` | `21` |
| **7** | `"\t\t\tfile2.ext"` | `3` | `9` | Yes | File absolute path: `pathLen[3] + 9 = 23 + 9 = 32`. Update `maxLen`. | `[0, 4, 12, 23, 0]` | `32` |

**Final Return Value:** `32`

---

## Complexity Analysis

* **Time Complexity:** $\mathcal{O}(N)$
  * Splitting the string takes $\mathcal{O}(N)$ where $N$ is the number of characters in `input`.
  * Iterating through the lines and finding the leading tabs takes $\mathcal{O}(N)$ time overall since each character in `input` is examined at most a constant number of times.
  
* **Space Complexity:** $\mathcal{O}(N)$
  * Storing the split array of lines requires $\mathcal{O}(N)$ space.
  * The auxiliary tracking array `pathLen` requires $\mathcal{O}(D)$ space, where $D$ is the maximum depth of the file system ($D \le N$).

---

## Optimal Java Implementation

```java
class Solution {
    public int lengthLongestPath(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        
        String[] lines = input.split("\n");
        // pathLen[d] stores the accumulated length of the path up to depth 'd' (including '/' separator)
        int[] pathLen = new int[lines.length + 1];
        int maxLen = 0;
        
        for (String line : lines) {
            int depth = 0;
            // Count the number of leading '\t' characters to determine the depth
            while (depth < line.length() && line.charAt(depth) == '\t') {
                depth++;
            }
            
            // The length of the file or directory name
            int nameLen = line.length() - depth;
            
            // If the name contains '.', it is a file
            if (line.indexOf('.') != -1) {
                maxLen = Math.max(maxLen, pathLen[depth] + nameLen);
            } else {
                // If it is a directory, update the path length for the next level
                // +1 is for the '/' character that separates directories
                pathLen[depth + 1] = pathLen[depth] + nameLen + 1;
            }
        }
        
        return maxLen;
    }
}
```
