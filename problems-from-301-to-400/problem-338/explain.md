# 427. Construct Quad Tree - Solution Explanation

## Problem Overview

We are given an `n x n` grid consisting of `0`s and `1`s, where `n` is a power of 2. We need to represent this grid as a **Quad Tree**.

A Quad Tree is a tree data structure in which each internal node has exactly four children. It is commonly used to partition a two-dimensional space by recursively subdividing it into four quadrants.

Rules for nodes:
*   **Leaf Node (`isLeaf = true`)**: Represents a grid (or sub-grid) where all cells have the *exact same value*. Its `val` attribute is `true` if all cells are `1`, and `false` if all cells are `0`.
*   **Internal Node (`isLeaf = false`)**: Represents a grid that contains a mix of `0`s and `1`s. Its `val` attribute can be arbitrary (usually `true` by default), and it must have exactly four children representing the four quadrants of the grid: `topLeft`, `topRight`, `bottomLeft`, and `bottomRight`.

---

## Approach: Bottom-Up Recursion (Optimized)

### Intuition

A naive **Top-Down** approach would involve checking every cell in the current grid to see if they are all identical. If they are, we create a leaf node. If they aren't, we split the grid into four quadrants and recurse. While conceptually simple, this forces us to repeatedly check the same cells in the worst case, leading to a time complexity of `O(N log N)`, where `N` is the total number of cells (`N = n^2`).

We can optimize this to **`O(N)`** using a **Bottom-Up** approach.

Instead of checking the grid before we split, we *blindly split* the grid until we reach the base case: a `1 x 1` grid. A `1 x 1` grid is inherently uniform, so it's always a leaf node.

As the recursion unwinds and returns the 4 quadrant nodes back to the parent, the parent can easily determine if it should be a leaf or an internal node by looking at its children:
1.  **Merge Condition:** If all 4 children are **leaf nodes** AND they all have the **same value**, we can merge them! The parent itself becomes a single leaf node with that same value.
2.  **Otherwise:** The grid must be mixed. The parent becomes an internal node, and the 4 returned nodes become its children.

### Step-by-Step Walkthrough

1.  **Base Case:** If `size == 1`, we are looking at a single cell `grid[r][c]`. We immediately return a leaf node: `new Node(grid[r][c] == 1, true)`.
2.  **Divide:** Calculate `half = size / 2`.
3.  **Recurse:** Recursively call the `construct` method for the 4 quadrants:
    *   `topLeft` starting at `(r, c)`
    *   `topRight` starting at `(r, c + half)`
    *   `bottomLeft` starting at `(r + half, c)`
    *   `bottomRight` starting at `(r + half, c + half)`
4.  **Conquer (Merge or Link):**
    *   Check if `topLeft`, `topRight`, `bottomLeft`, and `bottomRight` are all leaves (`isLeaf == true`).
    *   Check if their `val` attributes are all equal.
    *   If both conditions are met, discard the children and return a new leaf node for the parent.
    *   If not, return a new internal node (`isLeaf = false`) pointing to these four children.

---

## Complexity Analysis

*   **Time Complexity:** `O(N)` where `N = n^2` is the total number of cells in the grid.
    *   We visit each cell exactly once to create the `1 x 1` leaf nodes.
    *   The merging step takes `O(1)` time per internal node. The total number of nodes in a Quad Tree of `N` leaves is at most `(4N - 1) / 3`, meaning the number of merge checks is proportional to `N`.
*   **Space Complexity:** `O(log n)`
    *   The space complexity is determined by the maximum depth of the recursion stack.
    *   Since the grid dimensions are halved at each step, the maximum depth of the tree is `log2(n)`. Therefore, the call stack takes `O(log n)` space.
