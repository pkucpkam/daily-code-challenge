# 665. Non-decreasing Array

**Difficulty:** Medium 🟡  
**Topics:** Array  

---

## Problem Statement

Given an array `nums` with `n` integers, your task is to check if it could become non-decreasing by modifying **at most one element**.

We define an array is non-decreasing if `nums[i] <= nums[i + 1]` holds for every `i` (0-based) such that `(0 <= i <= n - 2)`.

---

## Examples

### Example 1

```text
Input: nums = [4,2,3]
Output: true
```

**Explanation:** You could modify the first `4` to `1` to get a non-decreasing array.

---

### Example 2

```text
Input: nums = [4,2,1]
Output: false
```

**Explanation:** You cannot get a non-decreasing array by modifying at most one element.

---

## Constraints

- `n == nums.length`
- `1 <= n <= 10⁴`
- `-10⁵ <= nums[i] <= 10⁵`