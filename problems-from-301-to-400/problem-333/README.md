# [417. Pacific Atlantic Water Flow](https://leetcode.com/problems/pacific-atlantic-water-flow/)

**Medium**

---

## Problem Description

There is an $m \times n$ rectangular island that borders both the **Pacific Ocean** and **Atlantic Ocean**. The **Pacific Ocean** touches the island's left and top edges, and the **Atlantic Ocean** touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an $m \times n$ integer matrix `heights` where `heights[r][c]` represents the height above sea level of the cell at coordinate $(r, c)$.

The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

Return *a 2D list of grid coordinates `result` where `result[i] = [ri, ci]` denotes that rain water can flow from cell `(ri, ci)` to **both** the Pacific and Atlantic oceans*.

---

## Examples

### Example 1

![Waterflow Grid](https://assets.leetcode.com/uploads/2021/06/08/waterflow-grid.jpg)

**Input:**
```json
heights = [
  [1, 2, 2, 3, 5],
  [3, 2, 3, 4, 4],
  [2, 4, 5, 3, 1],
  [6, 7, 1, 4, 5],
  [5, 1, 1, 2, 4]
]
```

**Output:**
```json
[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
```

**Explanation:**
- `[0, 4]`: `[0, 4]` $\rightarrow$ Pacific Ocean, `[0, 4]` $\rightarrow$ Atlantic Ocean
- `[1, 3]`: `[1, 3]` $\rightarrow$ `[0, 3]` $\rightarrow$ Pacific Ocean, `[1, 3]` $\rightarrow$ `[1, 4]` $\rightarrow$ Atlantic Ocean
- `[1, 4]`: `[1, 4]` $\rightarrow$ `[1, 3]` $\rightarrow$ `[0, 3]` $\rightarrow$ Pacific Ocean, `[1, 4]` $\rightarrow$ Atlantic Ocean
- `[2, 2]`: `[2, 2]` $\rightarrow$ `[1, 2]` $\rightarrow$ `[0, 2]` $\rightarrow$ Pacific Ocean, `[2, 2]` $\rightarrow$ `[2, 3]` $\rightarrow$ `[2, 4]` $\rightarrow$ Atlantic Ocean
- `[3, 0]`: `[3, 0]` $\rightarrow$ Pacific Ocean, `[3, 0]` $\rightarrow$ `[4, 0]` $\rightarrow$ Atlantic Ocean
- `[3, 1]`: `[3, 1]` $\rightarrow$ `[3, 0]` $\rightarrow$ Pacific Ocean, `[3, 1]` $\rightarrow$ `[4, 1]` $\rightarrow$ Atlantic Ocean
- `[4, 0]`: `[4, 0]` $\rightarrow$ Pacific Ocean, `[4, 0]` $\rightarrow$ Atlantic Ocean

*Note: There are other possible paths for these cells to flow to the Pacific and Atlantic oceans.*

### Example 2

**Input:**
```json
heights = [[1]]
```

**Output:**
```json
[[0, 0]]
```

**Explanation:**
The water can flow from the only cell to both the Pacific and Atlantic oceans.

---

## Constraints

- $m == \text{heights.length}$
- $n == \text{heights}[r].\text{length}$
- $1 \le m, n \le 200$
- $0 \le \text{heights}[r][c] \le 10^5$