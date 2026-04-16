# 274. H-Index - Best Solution

## Problem Summary

Given an array `citations`, return the researcher's **h-index**.

The h-index is the maximum value `h` such that the researcher has at least `h` papers with at least `h` citations each.

## Best Idea: Counting Buckets (No Sorting)

Instead of sorting (`O(n log n)`), we can solve in `O(n)` using frequency counting.

Let `n = citations.length`.

- Create `bucket` of size `n + 1`.
- `bucket[i]` = number of papers with exactly `i` citations (for `0 <= i < n`).
- Any citation count `>= n` is grouped into `bucket[n]`.

Why grouping works:

- For h-index, we only care whether citations are at least `h`.
- Values above `n` behave the same as `n` when checking `h <= n`.

Then scan `h` from `n` down to `0`:

- Maintain `papers` = number of papers with citations `>= h`.
- Add `bucket[h]` while moving downward.
- First `h` where `papers >= h` is the answer.

## Why This Is Correct

- At step `h`, `papers` exactly counts papers with at least `h` citations.
- If `papers >= h`, h-index condition is satisfied for `h`.
- Scanning from high to low guarantees the first valid `h` is the maximum possible one.

So the returned value is exactly the definition of h-index.

## Complexity

- Time: `O(n)`
- Space: `O(n)`

This is optimal for this problem and faster than sorting for large inputs.

## Java Code

```java
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] bucket = new int[n + 1];

        for (int c : citations) {
            if (c >= n) {
                bucket[n]++;
            } else {
                bucket[c]++;
            }
        }

        int papers = 0;
        for (int h = n; h >= 0; h--) {
            papers += bucket[h];
            if (papers >= h) {
                return h;
            }
        }

        return 0;
    }
}
```
