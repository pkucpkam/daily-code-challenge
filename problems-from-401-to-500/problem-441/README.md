# 611. Valid Triangle Number

**Difficulty:** Medium 🟡  
**Topics:** Array, Two Pointers, Binary Search, Greedy, Sorting  

---

## Problem Statement

Given an integer array `nums`, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

---

## Examples

### Example 1

```text
Input: nums = [2,2,3,4]
Output: 3
```

**Explanation:** Valid combinations are:
- `2, 3, 4` (using the first 2)
- `2, 3, 4` (using the second 2)
- `2, 2, 3`

---

### Example 2

```text
Input: nums = [4,2,3,4]
Output: 4
```

---

## Constraints

- `1 <= nums.length <= 1000`
- `0 <= nums[i] <= 1000`