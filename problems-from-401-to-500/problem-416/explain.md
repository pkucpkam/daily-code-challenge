# 572. Subtree of Another Tree

## Problem Understanding

Given the roots of two binary trees `root` and `subRoot`, return `true` if there exists a subtree of `root` that has the exact same structure and node values as `subRoot`, and `false` otherwise.

### Definition of a Subtree
A subtree of a binary tree `tree` consists of:
1. A node $T$ in `tree`.
2. **All** of node $T$'s descendants in `tree`.

> **Crucial Difference (Subtree vs Partial Subgraph):**  
> A candidate subtree cannot merely match a prefix of nodes while leaving out deeper child nodes. If node $T$ in `root` has additional children not present in `subRoot`, it is **not** a valid subtree (as demonstrated in Example 2).

---

## Key Insights & Approach

### 1. Two-Level Recursive Strategy
The problem naturally decomposes into two interconnected recursive tasks:

1. **Outer Traversal (`isSubtree(root, subRoot)`):**
   - Traverse every node in `root` as a potential anchor (candidate root).
   - At each node, test if the tree rooted here is identical to `subRoot`.
   - If not, recursively test the left and right children:
     $$\text{isSubtree}(\text{root}, \text{subRoot}) = \text{isSameTree}(\text{root}, \text{subRoot}) \lor \text{isSubtree}(\text{root.left}, \text{subRoot}) \lor \text{isSubtree}(\text{root.right}, \text{subRoot})$$

2. **Inner Comparison (`isSameTree(p, q)`):**
   - Simultaneously traverse trees `p` and `q`.
   - Both nodes must be `null` simultaneously (base case match).
   - If only one is `null` or values differ (`p.val != q.val`), they are not identical.
   - Recursively confirm both left subtrees and right subtrees match:
     $$\text{isSameTree}(p, q) = (p.\text{val} == q.\text{val}) \land \text{isSameTree}(p.\text{left}, q.\text{left}) \land \text{isSameTree}(p.\text{right}, q.\text{right})$$

---

## Detailed Algorithm

```text
Function isSubtree(root, subRoot):
    If root is null:
        Return false
    If isSameTree(root, subRoot):
        Return true
    Return isSubtree(root.left, subRoot) OR isSubtree(root.right, subRoot)

Function isSameTree(p, q):
    If p is null AND q is null:
        Return true
    If p is null OR q is null OR p.val != q.val:
        Return false
    Return isSameTree(p.left, q.left) AND isSameTree(p.right, q.right)
```

---

## Alternative Approaches

### 1. Subtree Height Pruning (Optimization on DFS)
- A subtree in `root` can only match `subRoot` if both trees have the **exact same height**.
- By computing the height of `subRoot` once and computing heights of subtrees in `root` during a post-order traversal, we only invoke `isSameTree` when the subtree height equals `height(subRoot)`.
- This eliminates redundant tree comparisons at almost all internal nodes.

### 2. Tree Serialization + KMP Algorithm ($\mathcal{O}(N + M)$ Theoretical Bound)
- We can serialize both trees into strings using a pre-order traversal with:
  - Distinct markers for null nodes (e.g. `"#"`).
  - Delimiters around values (e.g. `",3,"`) to distinguish between numbers like `2` and `12`.
- `subRoot` is a subtree of `root` if and only if the serialized string of `subRoot` is a substring of `root`'s serialized string.
- Using Knuth-Morris-Pratt (KMP) string matching, the search runs in strictly linear $\mathcal{O}(N + M)$ time.

---

## Code Implementation

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     * Determines if subRoot is a subtree of root.
     * 
     * Time Complexity: O(N * M) worst-case, O(N) average-case.
     * Space Complexity: O(H) where H is the height of root.
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }

        // If the current tree matches subRoot, we found our match
        if (isSameTree(root, subRoot)) {
            return true;
        }

        // Otherwise, check left and right subtrees
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /**
     * Helper to verify if two binary trees are identical in structure and values.
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

---

## Step-by-Step Example Walkthrough

### Example 1: `root = [3,4,5,1,2]`, `subRoot = [4,1,2]`

```text
       root:               subRoot:
         3                    4
        / \                  / \
       4   5                1   2
      / \
     1   2
```

1. Call `isSubtree(node 3, subRoot)`:
   - `isSameTree(node 3, subRoot)`: `3 != 4` $\rightarrow$ `false`.
2. Recursive call to left child: `isSubtree(node 4, subRoot)`:
   - `isSameTree(node 4, subRoot)`:
     - Root values match: `4 == 4`.
     - Left child check: `isSameTree(node 1, node 1)` $\rightarrow$ `true`.
     - Right child check: `isSameTree(node 2, node 2)` $\rightarrow$ `true`.
     - Result: `true`!
3. Return `true` up the recursion chain.  
**Output:** `true` ✅

---

### Example 2: `root = [3,4,5,1,2,null,null,null,null,0]`, `subRoot = [4,1,2]`

```text
       root:               subRoot:
         3                    4
        / \                  / \
       4   5                1   2
      / \
     1   2
        /
       0
```

1. `isSubtree(node 3, subRoot)` $\rightarrow$ root values mismatch (`3 != 4`).
2. `isSubtree(node 4, subRoot)`:
   - `isSameTree(node 4, subRoot)`:
     - Root values match: `4 == 4`.
     - Left subtrees match (`1 == 1`).
     - Right subtree check:
       - `node 2` in `root` has a left child `0`.
       - `node 2` in `subRoot` has `left == null`.
       - `isSameTree(node 0, null)` returns `false`.
   - Result: `false`.
3. Check remaining subtrees $\rightarrow$ none match.  
**Output:** `false` ✅

---

## Complexity Analysis

| Metric | Complexity | Notes |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \times M)$ worst-case<br>$\mathcal{O}(N)$ average-case | In the worst case (e.g. all nodes having the same value), `isSameTree` ($\mathcal{O}(M)$) runs on every node of `root`. In typical trees, mismatches fail in $\mathcal{O}(1)$. |
| **Space Complexity** | $\mathcal{O}(H_{\text{root}})$ | Bounded by the recursion call stack depth. $\mathcal{O}(\log N)$ for balanced trees, $\mathcal{O}(N)$ for skewed trees. |

---

## Edge Cases & Pitfalls Handled

1. **Subtree vs Prefix:** As shown in Example 2, the subtree must contain **all** descendants. A match fails if `root` has extra nodes below.
2. **Single-node Trees:** Handles cases where both trees consist of only a single node.
3. **Empty Main Tree:** If `root` reaches `null`, returns `false` safely without `NullPointerException`.
