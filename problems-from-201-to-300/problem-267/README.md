# 230. Kth Smallest Element in a BST

**Difficulty:** Medium

## Description

Given the root of a binary search tree, and an integer `k`, return the `k`th smallest value (1-indexed) among all node values in the tree.

## Examples

### Example 1

![Example 1](https://assets.leetcode.com/uploads/2021/01/28/kthtree1.jpg)

```text
Input: root = [3,1,4,null,2], k = 1
Output: 1
```

### Example 2

![Example 2](https://assets.leetcode.com/uploads/2021/01/28/kthtree2.jpg)

```text
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3
```

## Constraints

- The number of nodes in the tree is `n`.
- `1 <= k <= n <= 10^4`
- `0 <= Node.val <= 10^4`

## Follow-up

If the BST is modified often, meaning insert and delete operations happen frequently, and you need to find the `k`th smallest value often, how would you optimize it?