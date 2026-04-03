# 221. Maximal Square

**Difficulty:** Medium

Given an `m x n` binary matrix filled with `'0'` and `'1'`, find the largest square containing only `'1'` and return its area.

## Example 1

![Example 1](https://assets.leetcode.com/uploads/2020/11/26/max1grid.jpg)

```text
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
```

## Example 2

![Example 2](https://assets.leetcode.com/uploads/2020/11/26/max2grid.jpg)

```text
Input: matrix = [["0","1"],["1","0"]]
Output: 1
```

## Example 3

```text
Input: matrix = [["0"]]
Output: 0
```

## Constraints

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 300`
- `matrix[i][j]` is `'0'` or `'1'`

