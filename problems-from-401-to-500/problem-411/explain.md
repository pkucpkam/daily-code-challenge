# 563. Binary Tree Tilt

## Problem Understanding

The **tilt** of a tree node is the absolute difference between:
- Sum of all values in its **left subtree**
- Sum of all values in its **right subtree**

If a node doesn't have a left or right child, that subtree sum is 0.

**Goal:** Return the sum of tilts of **all nodes** in the tree.

---

## Key Insights & Approach

### 1. Why Post-Order DFS?

To calculate the tilt of a node, we first need to know:
- The **sum of all values** in its left subtree
- The **sum of all values** in its right subtree

This means we must **process children before the parent** → **Post-order traversal**.

### 2. Single Pass Solution

Instead of calculating subtree sums separately, we can do it in one traversal:
- Recursively get the sum of left and right subtrees
- Calculate tilt at current node
- Accumulate into a global counter
- Return the subtree sum for parent to use

### 3. Why This is Optimal

- **Time:** $O(N)$ - Each node visited exactly once
- **Space:** $O(H)$ - Recursion stack depth (H = tree height)

---

## Algorithm Explanation

```
postOrder(node):
  IF node is null:
    RETURN 0
  
  leftSum = postOrder(node.left)
  rightSum = postOrder(node.right)
  
  tilt = |leftSum - rightSum|
  totalTilt += tilt
  
  RETURN leftSum + rightSum + node.val
```

---

## Step-by-Step Example Walkthrough

### Example 1: `root = [1,2,3]`

Tree structure:
```
    1
   / \
  2   3
```

**Execution:**

1. **Process Node 2** (leaf)
   - `leftSum = 0`, `rightSum = 0`
   - `tilt = |0 - 0| = 0`
   - `return 2`

2. **Process Node 3** (leaf)
   - `leftSum = 0`, `rightSum = 0`
   - `tilt = |0 - 0| = 0`
   - `return 3`

3. **Process Node 1** (root)
   - `leftSum = 2`, `rightSum = 3`
   - `tilt = |2 - 3| = 1`
   - `return 1 + 2 + 3 = 6`

**Total tilt:** `0 + 0 + 1 = 1` ✅

---

### Example 2: `root = [4,2,9,3,5,null,7]`

Tree structure:
```
      4
     / \
    2   9
   / \   \
  3   5   7
```

**Execution:**

1. **Node 3** → `tilt = 0`, `sum = 3`
2. **Node 5** → `tilt = 0`, `sum = 5`
3. **Node 2** → `tilt = |3-5| = 2`, `sum = 3+5+2 = 10`
4. **Node 7** → `tilt = 0`, `sum = 7`
5. **Node 9** → `tilt = |0-7| = 7`, `sum = 9+7 = 16`
6. **Node 4** → `tilt = |10-16| = 6`, `sum = 4+10+16 = 30`

**Total tilt:** `0 + 0 + 2 + 0 + 7 + 6 = 15` ✅

---

## Complexity Analysis

| Metric | Value | Explanation |
|:---|:---|:---|
| **Time Complexity** | $O(N)$ | Visit each node exactly once |
| **Space Complexity** | $O(H)$ | Recursion call stack, where H is tree height |
| **Best Case** | $O(\log N)$ | Balanced tree (H = log N) |
| **Worst Case** | $O(N)$ | Skewed tree (H = N) |
| **Space Complexity** | $\mathcal{O}(1)$ | Chỉ dùng biến đếm, không cần thêm bộ nhớ |

---

## So Sánh Các Cách Tiếp Cận

| Approach | Time | Space | Nhận Xét |
|:---|:---|:---|:---|
| **Brute Force** | $\mathcal{O}(N! )$ | $\mathcal{O}(1)$ | Thử mọi cách ghép — không thực tế |
| **Greedy + Sort** ✅ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(1)$ | **Tối ưu** — đơn giản và nhanh |
| **Counting Sort** | $\mathcal{O}(N + R)$ | $\mathcal{O}(R)$ | Dùng được vì giá trị nằm trong `[-10⁴, 10⁴]` |

> **Ghi chú:** Counting Sort (R = 2×10⁴) có thể đạt O(N) time nhưng cần O(R) space. Với bài này, Greedy + `Arrays.sort()` là cách viết **tối giản và đủ nhanh** nhất trong thực tế.
