# 594. Longest Harmonious Subsequence

**Difficulty:** Easy 🟢

## Description

We define a harmonious array as an array where the difference between its maximum value and its minimum value is exactly `1`.

Given an integer array `nums`, return the length of its longest harmonious subsequence among all its possible subsequences.

---

## Examples

### Example 1

```text
Input: nums = [1,3,2,2,5,2,3,7]
Output: 5
```

**Explanation:** The longest harmonious subsequence is [3,2,2,2,3].

---

### Example 2

```text
Input: nums = [1,2,3,4]
Output: 2
```

**Explanation:** The longest harmonious subsequences are [1,2], [2,3], and [3,4], all of which have a length of 2.

---

### Example 3

```text
Input: nums = [1,1,1,1]
Output: 0
```

**Explanation:** No harmonic subsequence exists.

---

## Constraints

- `1 <= nums.length <= 2 * 10⁴`
- `-10⁹ <= nums[i] <= 10⁹`