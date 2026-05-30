# 386. Lexicographical Numbers - Explanation

## Problem Overview

The task is to return all numbers from `1` to `n` sorted in **lexicographical (dictionary) order**. 

For example, for `n = 13`, the output should be:
`[1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]`.

### Constraints & Requirements
* **Time Complexity:** $O(n)$
* **Space Complexity:** $O(1)$ extra space (excluding the memory used to store the output).

---

## Key Intuition: 10-ary Trie Traversal (DFS)

If we visualize lexicographical ordering, it behaves exactly like a **Pre-order Traversal (Depth-First Search)** on a **10-ary Tree** (a tree where each node has up to 10 children, from `0` to `9`):

```text
                  (root)
               /    |    \    ...   \
              1     2     3          9
            / | \
           10 11 12 ... 19
          /
        100 ...
```

By traversing this tree using DFS, we naturally visit nodes in lexicographical order:
1. We start at `1`.
2. From any number `curr`, we try to go one level deeper by multiplying by `10` (`curr * 10`).
3. If going deeper exceeds `n`, we cannot go deeper. We instead move horizontally to the next sibling (`curr + 1`).
4. If we reach a number ending in `9` (e.g., `19` or `29`) or exceed `n`, we cannot move horizontally anymore. In this case, we **backtrack** by dividing by `10` (`curr / 10`) until we can safely increment the value to get the next branch.

---

## Detailed Algorithm Steps

To achieve $O(1)$ extra space, we simulate the DFS iteratively using a single tracking variable `curr` (initially `1`):

1. **Go Deeper:** If `curr * 10 <= n`, the next number is `curr * 10`.
2. **Move Horizontally or Backtrack:** If `curr * 10 > n`:
   * We need to increment `curr` to move to the next sibling.
   * However, we must backtrack if:
     * `curr >= n` (we've exceeded the upper limit `n`).
     * `curr % 10 == 9` (we've reached the last sibling at this level).
   * Backtracking is done by dividing `curr` by `10` until neither condition is met.
   * Finally, we increment `curr` (`curr++`) to find the next valid number.

---

## Step-by-Step Walkthrough ($n = 13$)

| Step | Current Value (`curr`) | Decision | Next Value | Result List |
| :--- | :--- | :--- | :--- | :--- |
| 1 | `1` | `1 * 10 <= 13` (Yes, go deeper) | `10` | `[1]` |
| 2 | `10` | `10 * 10 <= 13` (No). `10 < 13` and `10 % 10 != 9` (Move right) | `11` | `[1, 10]` |
| 3 | `11` | `11 * 10 <= 13` (No). `11 < 13` and `11 % 10 != 9` (Move right) | `12` | `[1, 10, 11]` |
| 4 | `12` | `12 * 10 <= 13` (No). `12 < 13` and `12 % 10 != 9` (Move right) | `13` | `[1, 10, 11, 12]` |
| 5 | `13` | `13 * 10 <= 13` (No). `13 >= 13` (Must backtrack). `13 / 10 = 1`. `1 < 13` and `1 % 10 != 9` (Stop backtracking). Increment: `1 + 1` | `2` | `[1, 10, 11, 12, 13]` |
| 6 | `2` | `2 * 10 <= 13` (No). `2 < 13` and `2 % 10 != 9` (Move right) | `3` | `[1, 10, 11, 12, 13, 2]` |
| ... | ... | ... | ... | ... |
| 12 | `8` | `8 * 10 <= 13` (No). `8 < 13` and `8 % 10 != 9` (Move right) | `9` | `[..., 8]` |
| 13 | `9` | `9 * 10 <= 13` (No). `9 % 10 == 9` (Must backtrack). `9 / 10 = 0`. Increment: `0 + 1` | `1` (Loop finishes after 13 items) | `[1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]` |

---

## Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n)$
  We generate exactly `n` numbers. Each number is visited once, and the backtracking operations (divisions by `10`) are amortized over the entire traversal, resulting in an average of $\mathcal{O}(1)$ operations per number.
  
* **Space Complexity:** $\mathcal{O}(1)$ extra space
  We only keep track of a few auxiliary variables (`curr`, loop indexes). No recursion stack or stack data structures are used.

---

## Optimal Java Implementation

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>();
        int curr = 1;
        
        for (int i = 0; i < n; i++) {
            result.add(curr);
            
            if (curr * 10 <= n) {
                curr *= 10; // Go one level deeper (down to the next decimal digit)
            } else {
                // Move horizontally or backtrack
                while (curr % 10 == 9 || curr >= n) {
                    curr /= 10; // Backtrack to parent node
                }
                curr++; // Move to next sibling
            }
        }
        
        return result;
    }
}
```

