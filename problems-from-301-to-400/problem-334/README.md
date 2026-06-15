# [419. Battleships in a Board](https://leetcode.com/problems/battleships-in-a-board/)

**Medium**

---

## Problem Description

Given an $m \times n$ matrix `board` where each cell is a battleship `'X'` or empty `'.'`, return *the number of the battleships on `board`*.

Battleships can only be placed horizontally or vertically on `board`. In other words, they can only be made of the shape $1 \times k$ ($1$ row, $k$ columns) or $k \times 1$ ($k$ rows, $1$ column), where $k$ can be of any size. At least one horizontal or vertical cell separates between two battleships (i.e., there are no adjacent battleships).

---

## Examples

### Example 1

![Battleships](https://assets.leetcode.com/uploads/2024/06/21/image.png)

**Input:**
```json
board = [
  ["X", ".", ".", "X"],
  [".", ".", ".", "X"],
  [".", ".", ".", "X"]
]
```

**Output:**
```json
2
```

### Example 2

**Input:**
```json
board = [
  ["."]
]
```

**Output:**
```json
0
```

---

## Constraints

- $m == \text{board.length}$
- $n == \text{board}[i].\text{length}$
- $1 \le m, n \le 200$
- `board[i][j]` is either `'.'` or `'X'`.

---

## Follow up

Could you do it in one-pass, using only $O(1)$ extra memory and without modifying the values of `board`?