# 513. Find Bottom Left Tree Value

## Intuition

The task is to find the value of the **leftmost node in the bottom row** of a binary tree. 

A standard Level-Order Traversal (Breadth-First Search / BFS) visits nodes level by level from left to right. However, if we reverse the traversal order—enqueueing the **right child before the left child**—we process nodes level by level from **right to left**.

With this **Right-to-Left BFS** approach, the very **last node popped from the queue** will always be the leftmost node in the deepest level.

---

## Key Insights & Algorithm

### Approach 1: Right-to-Left BFS (Optimal & Elegant)

1. **Reverse Traversal Order**:
   - Standard BFS processes levels Left $\rightarrow$ Right.
   - Right-to-Left BFS processes levels Right $\rightarrow$ Left.
2. **Natural Termination**:
   - Because we visit right children first, the traversal proceeds top-to-bottom and right-to-left.
   - The final element dequeued is guaranteed to be the bottom-leftmost element of the tree.
   - No explicit depth tracking or level-size tracking is needed.

### Approach 2: DFS (Pre-Order Traversal)

Alternatively, we can use a **Pre-Order DFS** (`Root -> Left -> Right`):
- Maintain global/instance variables `maxDepth` and `bottomLeftVal`.
- Recurse down the tree, tracking `currentDepth`.
- Whenever `currentDepth > maxDepth`, update `maxDepth = currentDepth` and `bottomLeftVal = node.val`.
- Since left subtrees are visited before right subtrees, the first node encountered at any newly reached depth is always the leftmost node at that level.

---

## Detailed Step-by-Step (Right-to-Left BFS)

1. Initialize a FIFO Queue and enqueue `root`.
2. Maintain a reference `node` for the current dequeued element.
3. While `queue` is not empty:
   - Dequeue `node = queue.poll()`.
   - If `node.right` is not null, enqueue `node.right`.
   - If `node.left` is not null, enqueue `node.left`.
4. When the loop terminates (queue is empty), return `node.val`.

---

## Complexity Analysis

### Time Complexity:
- $\mathcal{O}(N)$: Every node in the binary tree is enqueued and dequeued exactly once, where $N$ is the total number of nodes in the tree.

### Space Complexity:
- $\mathcal{O}(W)$: Where $W$ is the maximum width of the binary tree (the maximum number of nodes at any level). In the worst case (a full binary tree), the queue holds up to $\lceil N / 2 \rceil = \mathcal{O}(N)$ nodes.
