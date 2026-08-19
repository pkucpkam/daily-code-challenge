# 543. Diameter of Binary Tree

## Intuition

The **diameter** is the longest path between any two nodes. That path always passes through some node as its highest point (its *apex*). At the apex, the path length equals:

$$\text{left depth} + \text{right depth}$$

Instead of re-computing depths repeatedly (which would be $\mathcal{O}(N^2)$), we can do a single **post-order DFS** that computes the depth of every subtree bottom-up, and simultaneously updates the best diameter seen so far at each node.

---

## Key Insight & Algorithm

### Single-Pass Post-Order DFS (Optimal)

We define a helper `depth(node)` that:
1. Recursively computes the depth of the **left** and **right** subtrees.
2. Updates a global `diameter` variable with `left + right` — the path through this node.
3. Returns `max(left, right) + 1` — the longest arm this node contributes to its parent.

```
diameterOfBinaryTree(root):
    diameter = 0
    depth(root)
    return diameter

depth(node):
    if node == null → return 0
    left  = depth(node.left)
    right = depth(node.right)
    diameter = max(diameter, left + right)   // candidate path at this apex
    return max(left, right) + 1              // longest arm upward
```

**Why it works:**

- Every possible diameter path has exactly one apex node (the highest node on the path).
- When DFS visits the apex, `left + right` correctly counts all edges on that path.
- By taking the global maximum across all nodes, we guarantee the answer.

---

## Walkthrough on Example 1

Tree: `[1, 2, 3, 4, 5]`

```
        1
       / \
      2   3
     / \
    4   5
```

| Node visited | left | right | diameter update | returns |
|:---:|:---:|:---:|:---:|:---:|
| 4 | 0 | 0 | max(0, 0+0) = 0 | 1 |
| 5 | 0 | 0 | max(0, 0+0) = 0 | 1 |
| 2 | 1 | 1 | max(0, 1+1) = **2** | 2 |
| 3 | 0 | 0 | max(2, 0+0) = 2 | 1 |
| 1 | 2 | 1 | max(2, 2+1) = **3** | 3 |

✅ Answer: `3` — path `[4,2,1,3]` or `[5,2,1,3]`

---

## Complexity Analysis

| | Time | Space |
|---|---|---|
| **Single-Pass DFS** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ |

- **Time**: Each node is visited exactly once.
- **Space**: $\mathcal{O}(H)$ for the recursion call stack, where $H$ is the height of the tree.
  - Best case (balanced tree): $\mathcal{O}(\log N)$
  - Worst case (skewed tree): $\mathcal{O}(N)$

---

## Why Not Naive?

A naive approach would call a `height()` function for each node separately, resulting in $\mathcal{O}(N^2)$ time. The single-pass DFS avoids this by computing height **on the way back up**, making the diameter update a free byproduct of the depth computation.
