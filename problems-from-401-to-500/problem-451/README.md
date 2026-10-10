# 628. Maximum Product of Three Numbers

**Difficulty:** Easy 🟢  
**Topics:** Array, Math, Sorting  

---

## Problem Statement

You are given an integer array `nums`.

Find three numbers whose product is maximum and return the maximum product.

---

## Examples

### Example 1

```text
Input: nums = [1,2,-3]
Output: -6
```

**Explanation:** The only three numbers are 1, 2, and -3, so the maximum product is `1 * 2 * -3 = -6`.

---

### Example 2

```text
Input: nums = [1,2,3,4]
Output: 24
```

**Explanation:** The largest product comes from the three greatest numbers: `2 * 3 * 4 = 24`.

---

### Example 3

```text
Input: nums = [-1,-2,-3]
Output: -6
```

**Explanation:** The only three numbers are -1, -2, and -3, so the maximum product is `(-1) * (-2) * (-3) = -6`.

---

## Constraints

- `3 <= nums.length <= 10⁴`
- `-1000 <= nums[i] <= 1000`