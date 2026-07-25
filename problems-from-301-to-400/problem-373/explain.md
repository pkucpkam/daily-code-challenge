# 498. Diagonal Traverse

## Intuition

The problem asks us to traverse an $m \times n$ matrix diagonally in a snake-like pattern, alternating directions for consecutive diagonals:
- **Up-Right ($\nearrow$)**: Moving from bottom-left towards top-right (`row--`, `col++`).
- **Down-Left ($\swarrow$)**: Moving from top-right towards bottom-left (`row++`, `col--`).

Instead of creating intermediate lists for each diagonal and reversing every second list, we can **simulate the traversal directly in $\mathcal{O}(1)$ auxiliary space** using a direction state variable.

### Direction Switching & Boundary Conditions

When moving **Up-Right** (`dir = 1`):
1. If we hit the **last column** (`col == n - 1`), we cannot move right any further. We move **down** (`row++`) and switch direction (`dir = -1`).
2. Else if we hit the **first row** (`row == 0`), we cannot move up any further. We move **right** (`col++`) and switch direction (`dir = -1`).
3. Otherwise, continue moving Up-Right (`row--`, `col++`).

When moving **Down-Left** (`dir = -1`):
1. If we hit the **last row** (`row == m - 1`), we cannot move down any further. We move **right** (`col++`) and switch direction (`dir = 1`).
2. Else if we hit the **first column** (`col == 0`), we cannot move left any further. We move **down** (`row++`) and switch direction (`dir = 1`).
3. Otherwise, continue moving Down-Left (`row++`, `col--`).

> [!NOTE]
> **Boundary Order Priority:** When reaching a corner (such as the top-right corner `(0, n - 1)` while moving up-right), checking the column condition `col == n - 1` before `row == 0` is essential so that we step down to row `1` rather than stepping out of bounds into column `n`.

---

## Algorithm

1. Initialize `m = mat.length`, `n = mat[0].length`, an output array `result` of size $m \times n$, `row = 0`, `col = 0`, and `dir = 1`.
2. Loop `i` from `0` to $m \times n - 1$:
   - Store `mat[row][col]` into `result[i]`.
   - Update `(row, col)` and `dir` according to the directional transition rules.
3. Return `result`.

---

## Complexity Analysis

### Time Complexity:
- $\mathcal{O}(m \times n)$: Each cell in the matrix is visited exactly once, performing $\mathcal{O}(1)$ operations per cell.

### Space Complexity:
- $\mathcal{O}(1)$ auxiliary space: We only use scalar variables (`row`, `col`, `dir`, `m`, `n`). The output array of size $m \times n$ is required for the final answer.
