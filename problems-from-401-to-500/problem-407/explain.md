# 558. Logical OR of Two Binary Grids Represented as Quad-Trees

## Intuition

A Quad-Tree represents a 2D binary matrix by recursively dividing non-uniform sub-grids into four equal quadrants (`topLeft`, `topRight`, `bottomLeft`, `bottomRight`). Leaf nodes represent regions of uniform values (all 1s or all 0s).

The problem asks us to compute the element-wise **logical bitwise OR** of two binary matrices given their Quad-Tree representations. Since Quad-Trees naturally decompose space hierarchically, we can solve this problem recursively using a **Divide and Conquer** approach.

---

## Key Insights & Algorithmic Strategy

### 1. Base Cases (Leaf Node Short-Circuiting)

When operating on two Quad-Tree nodes `quadTree1` and `quadTree2`, we can optimize by checking if either node is a leaf:

- **If `quadTree1` is a leaf:**
  - If `quadTree1.val == true` (represents a sub-grid of all `1`s):  
    Since $\text{True} \lor X = \text{True}$, the result of the logical OR over this entire region is a grid of all `1`s. We can directly return `quadTree1`.
  - If `quadTree1.val == false` (represents a sub-grid of all `0`s):  
    Since $\text{False} \lor X = X$, the logical OR result is simply `quadTree2`. We can directly return `quadTree2`.

- **If `quadTree2` is a leaf:**
  - If `quadTree2.val == true`, return `quadTree2`.
  - If `quadTree2.val == false`, return `quadTree1`.

### 2. Recursive Divide & Conquer

If both `quadTree1` and `quadTree2` are internal (non-leaf) nodes, we recursively compute the logical OR for each of their four corresponding child quadrants:

- $\text{topLeft} = \text{intersect}(\text{quadTree1.topLeft}, \text{quadTree2.topLeft})$
- $\text{topRight} = \text{intersect}(\text{quadTree1.topRight}, \text{quadTree2.topRight})$
- $\text{bottomLeft} = \text{intersect}(\text{quadTree1.bottomLeft}, \text{quadTree2.bottomLeft})$
- $\text{bottomRight} = \text{intersect}(\text{quadTree1.bottomRight}, \text{quadTree2.bottomRight})$

### 3. Tree Compression / Simplification

After recursively constructing the four child quadrants, we must check whether they can be merged into a single leaf node.

Four quadrants can be merged if and only if:
1. All four children are leaf nodes (`isLeaf == true`).
2. All four children have the **same value** (`topLeft.val == topRight.val == bottomLeft.val == bottomRight.val`).

- **If mergeable:** Return a single leaf node with `val = topLeft.val` and `isLeaf = true`.
- **Otherwise:** Return a non-leaf node (`isLeaf = false`, `val = false`) with the four computed children.

---

## Code Implementation (Java)

```java
/*
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node() {}

    public Node(boolean _val, boolean _isLeaf) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = null;
        topRight = null;
        bottomLeft = null;
        bottomRight = null;
    }

    public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
};
*/

class Solution {
    public Node intersect(Node quadTree1, Node quadTree2) {
        // Base Case 1: If quadTree1 is a leaf node
        if (quadTree1.isLeaf) {
            return quadTree1.val ? quadTree1 : quadTree2;
        }
        
        // Base Case 2: If quadTree2 is a leaf node
        if (quadTree2.isLeaf) {
            return quadTree2.val ? quadTree2 : quadTree1;
        }

        // Recursive Step: Compute OR for each of the 4 quadrants
        Node topLeft = intersect(quadTree1.topLeft, quadTree2.topLeft);
        Node topRight = intersect(quadTree1.topRight, quadTree2.topRight);
        Node bottomLeft = intersect(quadTree1.bottomLeft, quadTree2.bottomLeft);
        Node bottomRight = intersect(quadTree1.bottomRight, quadTree2.bottomRight);

        // Compression Step: Merge 4 identical leaf children into 1 leaf node
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val
                && topRight.val == bottomLeft.val
                && bottomLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true);
        }

        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
}
```

---

## Step-by-Step Walkthrough

### Example Scenario

Consider two Quad-Trees:
- `quadTree1`: Internal node with 4 children where `topLeft` = leaf(1), `topRight` = leaf(1), `bottomLeft` = leaf(0), `bottomRight` = leaf(0).
- `quadTree2`: Leaf node with `val = true` (represents a full grid of 1s).

1. **Invocation:** `intersect(quadTree1, quadTree2)`
2. `quadTree1.isLeaf` is `false`.
3. `quadTree2.isLeaf` is `true` and `quadTree2.val` is `true`.
4. **Base Case Hit:** `quadTree2.val ? quadTree2 : quadTree1` returns `quadTree2` directly.
5. **Result:** Entire region evaluates to a leaf with `val = true`. Short-circuiting avoids traversing the 4 children of `quadTree1`.

---

## Complexity Analysis

| Approach | Time Complexity | Auxiliary Space Complexity | Recommendation |
|:---|:---|:---|:---|
| **Matrix Reconstruction + Bitwise OR** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N^2)$ | Sub-optimal — wastes space and memory allocating 2D grid matrices |
| **Recursive Quad-Tree Intersect (Divide & Conquer)** | $\mathcal{O}(M)$ | $\mathcal{O}(H)$ | **Optimal** — directly operates on Quad-Tree with short-circuiting |

- **Time Complexity:** $\mathcal{O}(M)$, where $M$ is the number of nodes in the two Quad-Trees. In the worst case (when no early short-circuiting occurs), we visit each node once, bounded by grid size $\mathcal{O}(N^2)$ where $N = 2^x$.
- **Space Complexity:** $\mathcal{O}(H)$, where $H$ is the height of the Quad-Tree. Since $N = 2^x$ with $0 \le x \le 9$, maximum tree height $H \le 9$. The call stack requires at most $\mathcal{O}(\log N) = \mathcal{O}(x)$ space, which is $\mathcal{O}(1)$ auxiliary space.


