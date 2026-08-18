# 542. 01 Matrix

## Intuition

The problem asks for the distance of the nearest `0` for every cell in an `m x n` binary matrix.

Finding the shortest distance in an unweighted grid typically suggests a **Breadth-First Search (BFS)** or **Dynamic Programming (DP)**. 

Instead of starting a search from each `1` to find a `0` (which would be inefficient at $\mathcal{O}((M \times N)^2)$), we can reverse our perspective:
1. **Multi-Source BFS**: Start BFS simultaneously from all `0` cells.
2. **Two-Pass Dynamic Programming**: Compute distances in two directional scans (Top-Left to Bottom-Right, then Bottom-Right to Top-Left).

---

## Key Insights & Algorithm

### 1. Two-Pass Dynamic Programming (Optimal Space)

Any cell $(r, c)$ can reach a `0` from four possible directions: **top**, **left**, **bottom**, or **right**.

- **First Pass (Top-Left to Bottom-Right):**
  We iterate through the matrix from top-left $(0, 0)$ to bottom-right $(m-1, n-1)$.
  For each non-zero cell `mat[r][c]`, its minimum distance considering top and left neighbors is:
  $$\text{dist}[r][c] = \min(\text{dist}[r-1][c], \text{dist}[r][c-1]) + 1$$

- **Second Pass (Bottom-Right to Top-Left):**
  We iterate through the matrix from bottom-right $(m-1, n-1)$ to top-left $(0, 0)$.
  For each non-zero cell `mat[r][c]`, we compare its current value with its bottom and right neighbors:
  $$\text{dist}[r][c] = \min(\text{dist}[r][c], \min(\text{dist}[r+1][c], \text{dist}[r][c+1]) + 1)$$

By combining both passes, every cell accounts for paths originating from all four directions.

### 2. Multi-Source BFS (Alternative Approach)

- Push all cells containing `0` into a Queue and set their distance to `0`.
- Mark all `1` cells as unvisited (`-1`).
- Perform standard BFS: pop cell $(r, c)$ from the Queue and update adjacent unvisited cells with $\text{dist}[r][c] + 1$, pushing them into the Queue.

---

## Complexity Analysis

### Two-Pass DP (Implemented Solution)
- **Time Complexity**: $\mathcal{O}(M \times N)$
  - We scan the grid twice. Each cell is processed in constant time $\mathcal{O}(1)$.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space
  - The matrix is updated in-place without requiring extra queues or dynamic memory allocation.

### Multi-Source BFS
- **Time Complexity**: $\mathcal{O}(M \times N)$
  - Each cell is visited and pushed to the queue at most once.
- **Space Complexity**: $\mathcal{O}(M \times N)$
  - The queue stores up to $M \times N$ cells in the worst case.
