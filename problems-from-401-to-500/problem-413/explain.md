# 566. Reshape the Matrix

## Problem Understanding

You are given an `m x n` matrix `mat` and target dimensions `r` (rows) and `c` (columns).  
The goal is to reshape `mat` into an `r x c` matrix while preserving the original row-traversing order (row-major order).

- If the total number of elements in the original matrix (`m * n`) does **not** equal the required number of elements in the reshaped matrix (`r * c`), the operation is illegal. In this case, return the original matrix `mat`.
- Otherwise, construct and return the new `r x c` matrix.

---

## Key Insights & Mathematical Properties

### 1. Element Quantity Validation
For a matrix reshape to be valid, the total element count must be strictly conserved:
$$\text{Total Elements} = m \times n = r \times c$$
If $m \times n \neq r \times c$, return `mat` immediately.

### 2. 1D Index Conversion (Row-Major Indexing)
Any 2D cell position $(r_{idx}, c_{idx})$ in a matrix with column width $W$ corresponds to a unique 1D linear index $k$:
$$k = r_{idx} \times W + c_{idx}$$

Conversely, given a 1D linear index $k$ (where $0 \le k < m \times n$), we can map it back to a 2D coordinate in a matrix with column width $W$:
$$\text{row} = \lfloor k / W \rfloor, \quad \text{col} = k \pmod W$$

### 3. Direct Index Mapping
Instead of using two nested loops and tracking row/column pointers manually, we can iterate a single counter $i$ from $0$ to $m \times n - 1$:
- Element in `mat`: `mat[i / n][i % n]`
- Position in `result`: `result[i / c][i % c]`

---

## Optimal Approach: Single 1D Index Mapping Loop

### Algorithm Steps:
1. Fetch dimensions of `mat`: `m = mat.length`, `n = mat[0].length`.
2. Validate size condition: If `m * n != r * c`, return `mat`.
3. Allocate result matrix `result` of size `r x c`.
4. Loop $i$ from $0$ to $m \times n - 1$:
   - Assign `result[i / c][i % c] = mat[i / n][i % n]`.
5. Return `result`.

---

## Code Implementation

```java
class Solution {
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length;
        int n = mat[0].length;

        // If total elements differ, operation is invalid
        if (m * n != r * c) {
            return mat;
        }

        int[][] result = new int[r][c];

        // Map 1D index i directly to 2D coordinates in both matrices
        for (int i = 0; i < m * n; i++) {
            result[i / c][i % c] = mat[i / n][i % n];
        }

        return result;
    }
}
```

---

## Step-by-Step Example Walkthrough

### Example 1: `mat = [[1, 2], [3, 4]]` ($2 \times 2$), `r = 1`, `c = 4`

1. **Validation:** $m \times n = 2 \times 2 = 4$, $r \times c = 1 \times 4 = 4$. Valid!
2. **Loop Execution ($i = 0$ to $3$):**
   - **$i = 0$**:
     - `mat[0 / 2][0 % 2]` $\rightarrow$ `mat[0][0] = 1`
     - `result[0 / 4][0 % 4]` $\rightarrow$ `result[0][0] = 1`
   - **$i = 1$**:
     - `mat[1 / 2][1 % 2]` $\rightarrow$ `mat[0][1] = 2`
     - `result[1 / 4][1 % 4]` $\rightarrow$ `result[0][1] = 2`
   - **$i = 2$**:
     - `mat[2 / 2][2 % 2]` $\rightarrow$ `mat[1][0] = 3`
     - `result[2 / 4][2 % 4]` $\rightarrow$ `result[0][2] = 3`
   - **$i = 3$**:
     - `mat[3 / 2][3 % 2]` $\rightarrow$ `mat[1][1] = 4`
     - `result[3 / 4][3 % 4]` $\rightarrow$ `result[0][3] = 4`

3. **Final Result:** `[[1, 2, 3, 4]]`

---

## Complexity Analysis

| Metric | Complexity | Explanation |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(m \times n)$ | We visit each element of the matrix exactly once in a single loop. |
| **Space Complexity** | $\mathcal{O}(r \times c)$ | Required space to store the reshaped matrix output. Auxiliary space is $\mathcal{O}(1)$. |

---

## Comparison of Approaches

| Approach | Time Complexity | Space Complexity | Notes |
|:---|:---|:---|:---|
| **Two Pointer / Nested Loops** | $\mathcal{O}(m \times n)$ | $\mathcal{O}(r \times c)$ | Manually increments row/col counters. Slower and more boilerplate. |
| **Flatten to 1D Array then Reshape** | $\mathcal{O}(m \times n)$ | $\mathcal{O}(m \times n)$ | Requires extra intermediate array allocation. |
| **Direct 1D Index Mapping (Optimal)** ✅ | $\mathcal{O}(m \times n)$ | $\mathcal{O}(r \times c)$ | Cleanest code, zero intermediate allocations, optimal single-pass traversal. |

