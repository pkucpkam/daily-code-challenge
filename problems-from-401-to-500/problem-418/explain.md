# 576. Out of Boundary Paths

## Problem Understanding

You are given an $m \times n$ grid and a ball initially placed at the position `[startRow, startColumn]`.
At each step, you can move the ball to one of the 4 adjacent cells (up, down, left, right).
You are allowed to make at most `maxMove` moves.

A move that crosses the grid boundary (i.e. moving outside the coordinate bounds $0 \le r < m$ and $0 \le c < n$) counts as moving out of boundary and ends that path.
The goal is to find the total number of paths that move the ball out of the boundary in $\le \text{maxMove}$ steps.
Since the result can be very large, return it modulo $10^9 + 7$.

---

## Key Insights & Mathematical Formulation

### 1. Overlapping Subproblems & Optimal Substructure
At any coordinate $(r, c)$ with $k$ moves remaining, the number of successful exit paths depends only on:
- Whether the current position is already out of bounds (base case: 1 valid exit path).
- The sum of successful paths from the 4 neighboring cells with $k - 1$ moves remaining.

Because many different move sequences arrive at the same cell $(r, c)$ with the same remaining moves $k$, a pure recursive brute-force solution would branch $4^{\text{maxMove}}$, leading to exponential Time Limit Exceeded (TLE). We must use **Dynamic Programming** or **Memoization**.

### 2. Two Perspectives to Model the Problem

#### Perspective A: Backward DP (Top-Down DFS with Memoization)
Define $dp(r, c, k)$ as the number of paths to exit the grid starting from $(r, c)$ with at most $k$ moves remaining.
- **Base Cases:**
  - If $r < 0 \lor r \ge m \lor c < 0 \lor c \ge n$: Return $1$ (we have crossed the boundary).
  - If $k = 0$: Return $0$ (ran out of moves before crossing the boundary).
- **Recurrence:**
  $$dp(r, c, k) = \sum_{(dr, dc) \in \text{dirs}} dp(r + dr, c + dc, k - 1) \pmod{10^9 + 7}$$

#### Perspective B: Forward DP (Bottom-Up Reachability - Space Optimized)
Instead of asking "how many ways to exit from $(r, c)$", we ask:
"At step $s$, how many paths reach cell $(r, c)$?"

Let $\text{count}[r][c]$ be the number of paths that reach $(r, c)$ at step $s$.
1. At step $0$: $\text{count}[\text{startRow}][\text{startColumn}] = 1$, and all other cells are $0$.
2. For each step from $1$ to $\text{maxMove}$:
   - For each cell $(r, c)$ where $\text{count}[r][c] > 0$:
     - For each of the 4 adjacent directions $(nr, nc)$:
       - If $(nr, nc)$ is out of bounds: these paths step outside at this move, so add $\text{count}[r][c]$ to $\text{totalPaths}$.
       - If $(nr, nc)$ is inside bounds: add $\text{count}[r][c]$ to $\text{nextCount}[nr][nc]$.
3. Transition: $\text{count} \leftarrow \text{nextCount}$.

> **Space Optimization Advantage:** Forward DP only requires tracking the current step and next step grids of size $m \times n$. This reduces space complexity from $\mathcal{O}(m \cdot n \cdot \text{maxMove})$ down to $\mathcal{O}(m \cdot n)$.

---

## Detailed Implementations

### Approach 1: Space-Optimized Bottom-Up DP (Recommended / Best Solution)

```java
class Solution {
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        if (maxMove <= 0) {
            return 0;
        }

        final int MOD = 1_000_000_007;
        int[][] count = new int[m][n];
        count[startRow][startColumn] = 1;
        int totalPaths = 0;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Simulate each move from 0 to maxMove - 1
        for (int step = 0; step < maxMove; step++) {
            int[][] nextCount = new int[m][n];

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    int ways = count[r][c];
                    if (ways > 0) {
                        for (int[] dir : dirs) {
                            int nr = r + dir[0];
                            int nc = c + dir[1];

                            if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                                totalPaths = (totalPaths + ways) % MOD;
                            } else {
                                nextCount[nr][nc] = (nextCount[nr][nc] + ways) % MOD;
                            }
                        }
                    }
                }
            }

            count = nextCount;
        }

        return totalPaths;
    }
}
```

---

### Approach 2: Top-Down DFS with Memoization

```java
class Solution {
    private static final int MOD = 1_000_000_007;
    private Integer[][][] memo;
    private int m, n;
    private final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        this.m = m;
        this.n = n;
        this.memo = new Integer[m][n][maxMove + 1];
        return dfs(startRow, startColumn, maxMove);
    }

    private int dfs(int r, int c, int moves) {
        // Crossed the boundary -> found 1 valid path
        if (r < 0 || r >= m || c < 0 || c >= n) {
            return 1;
        }

        // Exhausted moves without crossing boundary
        if (moves == 0) {
            return 0;
        }

        if (memo[r][c][moves] != null) {
            return memo[r][c][moves];
        }

        int paths = 0;
        for (int[] dir : dirs) {
            paths = (paths + dfs(r + dir[0], c + dir[1], moves - 1)) % MOD;
        }

        return memo[r][c][moves] = paths;
    }
}
```

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Description |
| :--- | :--- | :--- | :--- |
| **Top-Down DFS + Memoization** | $\mathcal{O}(m \cdot n \cdot \text{maxMove})$ | $\mathcal{O}(m \cdot n \cdot \text{maxMove})$ | Visits reachable states, 3D memo table and recursion stack. |
| **Space-Optimized DP (Best)** | $\mathcal{O}(m \cdot n \cdot \text{maxMove})$ | $\mathcal{O}(m \cdot n)$ | Rolling $m \times n$ tables, cache-friendly, eliminates 3D allocation. |

- **Time Complexity:** $\mathcal{O}(m \cdot n \cdot \text{maxMove})$. For $m, n \le 50$ and $\text{maxMove} \le 50$, total inner-loop iterations $\le 50 \times 50 \times 50 \times 4 = 500{,}000$ operations, which easily runs in $\approx 3$–$5$ ms in Java.
- **Space Complexity:** $\mathcal{O}(m \cdot n)$. Only two 2D arrays of size $m \times n$ are maintained at any given time ($\approx 50 \times 50 \times 4\text{ bytes} \approx 10\text{ KB}$).

---

## Edge Cases

1. **`maxMove == 0`:** The ball cannot move at all, so it can never exit $\implies$ returns `0`.
2. **$1 \times 1$ Grid:** Every step moves out of boundary in all 4 directions immediately.
3. **Ball on Borders/Corners:** In a corner cell, 2 directions exit immediately; along an edge, 1 direction exits immediately. Handled naturally by boundary check.
4. **Modulo Operations:** Modulo arithmetic is applied on every addition to ensure intermediate results never overflow a standard 32-bit signed integer.
