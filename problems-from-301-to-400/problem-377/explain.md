# 515. Find Largest Value in Each Tree Row

## Intuition

The problem requires us to find the maximum node value present at each level (row) of a binary tree. 

Since tree levels correspond directly to rows, level-order traversal (Breadth-First Search / BFS) is a natural fit. Alternatively, we can use Depth-First Search (DFS) while keeping track of the current depth level.

---

## Key Insights & Algorithm

### Approach 1: Level-Order Traversal (BFS) - Standard & Intuitive

1. **Queue Initialization**:
   - Use a Queue to process tree nodes level by level.
   - Return an empty list immediately if `root == null`.
2. **Level Processing**:
   - For each level, record `levelSize = queue.size()`.
   - Track `maxVal = Integer.MIN_VALUE` for the current row.
   - Dequeue `levelSize` elements, updating `maxVal = Math.max(maxVal, node.val)` and enqueueing non-null left and right children.
3. **Record Result**:
   - Append `maxVal` to the result list after processing all nodes of that row.

### Approach 2: Depth-First Search (DFS) - Optimal Stack Space for Balanced Trees

1. **Pre-Order Traversal**:
   - Perform a recursive DFS passing `(node, currentDepth, result)`.
2. **Dynamic Result Updates**:
   - If `currentDepth == result.size()`, this is the first time reaching `currentDepth`, so append `node.val`.
   - Otherwise, update `result.set(currentDepth, Math.max(result.get(currentDepth), node.val))`.
3. **Recursion**:
   - Recurse down `node.left` and `node.right` with `currentDepth + 1`.

---

## Detailed Step-by-Step (BFS Approach)

1. Check if `root == null`. If so, return an empty list `[]`.
2. Initialize an empty list `result` and a `Queue<TreeNode> queue`.
3. Add `root` to `queue`.
4. While `queue` is not empty:
   - Read `levelSize = queue.size()`.
   - Set `maxVal = Integer.MIN_VALUE`.
   - Loop `i` from `0` to `levelSize - 1`:
     - Dequeue node `current`.
     - `maxVal = Math.max(maxVal, current.val)`.
     - Enqueue `current.left` if not null.
     - Enqueue `current.right` if not null.
   - Add `maxVal` to `result`.
5. Return `result`.

---

## Complexity Analysis

### BFS (Breadth-First Search)
- **Time Complexity:** $\mathcal{O}(N)$ — Every node in the binary tree is enqueued and dequeued exactly once, where $N$ is the total number of nodes in the tree.
- **Space Complexity:** $\mathcal{O}(W)$ — Where $W$ is the maximum width of the tree (up to $\lceil N / 2 \rceil = \mathcal{O}(N)$ nodes for a complete binary tree).

### DFS (Depth-First Search)
- **Time Complexity:** $\mathcal{O}(N)$ — Every node is visited once during traversal.
- **Space Complexity:** $\mathcal{O}(H)$ — Where $H$ is the height of the tree ($\mathcal{O}(\log N)$ for balanced trees, $\mathcal{O}(N)$ for skewed trees).
