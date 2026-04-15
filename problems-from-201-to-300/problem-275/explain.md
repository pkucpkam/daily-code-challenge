# 264. Ugly Number II - Best Solution

## Problem Summary

An ugly number is a positive integer whose prime factors are only `2`, `3`, and `5`.

Given `n`, return the `n`th ugly number.

## Best Idea: Dynamic Programming + 3 Pointers

Build ugly numbers in increasing order.

Let `ugly[i]` be the `(i + 1)`th ugly number.

At any time, the next ugly number must come from one of:

- `ugly[p2] * 2`
- `ugly[p3] * 3`
- `ugly[p5] * 5`

Where:

- `p2` points to the smallest ugly number that can generate a new candidate by multiplying `2`
- `p3` does the same for `3`
- `p5` does the same for `5`

For each position:

1. Compute the 3 candidates.
2. Take the minimum as next ugly number.
3. Move every pointer that created this minimum.

Important duplicate handling:

- If next value is both `ugly[p2] * 2` and `ugly[p3] * 3` (example: `6`), increment both pointers.

## Why This Is Correct

- Every generated value is ugly, because it is an earlier ugly number multiplied by `2`, `3`, or `5`.
- We always choose the smallest available candidate, so sequence is sorted.
- Moving all matching pointers avoids duplicates.
- Since every ugly number can be written from a smaller ugly number times `2/3/5`, no valid number is skipped.

So the algorithm generates ugly numbers in exact order, and returns the `n`th one.

## Complexity

- Time: `O(n)`
- Space: `O(n)`

With `n <= 1690`, this is optimal and very efficient.

## Java Code

```java
class Solution {
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;

        int p2 = 0;
        int p3 = 0;
        int p5 = 0;

        for (int i = 1; i < n; i++) {
            int next2 = ugly[p2] * 2;
            int next3 = ugly[p3] * 3;
            int next5 = ugly[p5] * 5;

            int nextUgly = Math.min(next2, Math.min(next3, next5));
            ugly[i] = nextUgly;

            if (nextUgly == next2) {
                p2++;
            }
            if (nextUgly == next3) {
                p3++;
            }
            if (nextUgly == next5) {
                p5++;
            }
        }

        return ugly[n - 1];
    }
}
```
