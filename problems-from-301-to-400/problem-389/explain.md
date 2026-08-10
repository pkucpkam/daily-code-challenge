# 530. Minimum Absolute Difference in BST

## Intuition

A Binary Search Tree (BST) has a very important property: an **in-order traversal** of a BST visits the nodes in strictly increasing (sorted) order. 

Because we want to find the minimum absolute difference between the values of *any* two different nodes, we only need to compare the values of adjacent nodes in this sorted sequence. The minimum difference between any two elements in a sorted array must be between two adjacent elements.

Thus, we can perform an in-order traversal, keep track of the previously visited node's value, and continuously update the minimum difference found so far.

---

## Key Insights & Algorithm

### In-Order Traversal (DFS)

1. **State Variables**:
   - `prev`: Keeps track of the value of the previously visited node. Initially set to `null`.
   - `minDiff`: Stores the minimum absolute difference found. Initially set to `Integer.MAX_VALUE`.

2. **Traversal Logic**:
   - Recursively traverse the left subtree.
   - **Process current node**:
     - If `prev` is not `null`, calculate the difference between the current node's value and `prev`. 
     - Update `minDiff` with the minimum of its current value and the calculated difference.
     - Update `prev` to the current node's value.
   - Recursively traverse the right subtree.

3. **Result**:
   - After the traversal completes, `minDiff` will hold the minimum absolute difference between any two nodes in the BST.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(N)$
  - We visit each node in the tree exactly once during the in-order traversal, where $N$ is the number of nodes in the BST.
  
- **Space Complexity**: $\mathcal{O}(H)$
  - The space complexity is determined by the maximum depth of the recursion stack, which is proportional to the height of the tree $H$. 
  - In the worst case (a skewed tree), this could be $\mathcal{O}(N)$. In the best case (a perfectly balanced tree), this would be $\mathcal{O}(\log N)$.
