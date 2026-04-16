# 274. H-Index

**Difficulty:** Medium

Given an integer array `citations` where `citations[i]` is the number of citations a researcher received for their `i`th paper, return the researcher's **h-index**.

According to the definition of h-index on Wikipedia: the h-index is the maximum value of `h` such that the given researcher has published at least `h` papers that have each been cited at least `h` times.

## Example 1

```text
Input: citations = [3,0,6,1,5]
Output: 3
```

Explanation:

- The researcher has 5 papers with citations `[3,0,6,1,5]`.
- There are 3 papers with at least 3 citations.
- The remaining 2 papers have no more than 3 citations.
- So, the h-index is `3`.

## Example 2

```text
Input: citations = [1,3,1]
Output: 1
```

## Constraints

- `n == citations.length`
- `1 <= n <= 5000`
- `0 <= citations[i] <= 1000`