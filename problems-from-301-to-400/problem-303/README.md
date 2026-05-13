# 357. Count Numbers with Unique Digits

Difficulty: Medium

## Problem

Given an integer n, return the count of all numbers with unique digits x, where 0 <= x < 10^n.

## Examples

Example 1:

Input:

```text
n = 2
```

Output:

```text
91
```

Explanation: The answer is the total numbers in the range 0 ≤ x < 100, excluding 11, 22, 33, 44, 55, 66, 77, 88, 99.

Example 2:

Input:

```text
n = 0
```

Output:

```text
1
```

## Constraints

- 0 <= n <= 8

## Notes

- For n = 0 the only number is 0.
- For n > 0 consider combinatorics to count numbers with unique digits.