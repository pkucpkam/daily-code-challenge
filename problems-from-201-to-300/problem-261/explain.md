# 215. Kth Largest Element in an Array - Best Solution

## Core idea

This problem asks for the `k`th largest element, not sorted output.
With constraints:

- `-10^4 <= nums[i] <= 10^4`

the value range is fixed and small (`20001` values). So the optimal approach here is a frequency array (counting), not full sorting.

## Why this is best here

- No sorting step.
- Deterministic linear scan over values.
- Time complexity: `O(n + 20001)`.
- Extra memory: `O(20001)` (constant relative to constraints).

For this specific constraint set, this is typically faster and more stable than quickselect in practice.

## Steps

1. Create `freq[20001]`, where index `i` maps to value `i - 10000`.
2. Count frequency of each number.
3. Traverse from largest value down.
4. Decrease `k` by each frequency.
5. When `k <= 0`, current value is the answer.

## Correctness intuition

Traversing from high to low enumerates elements in descending order conceptually.
If a value appears `c` times, it occupies `c` positions in this order.
Subtracting frequencies from `k` skips exactly those positions.
The first value that makes `k <= 0` is exactly the `k`th largest.

## Complexity

- Time: `O(n + 20001)`
- Space: `O(20001)`

## Java code

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        final int OFFSET = 10000;
        final int SIZE = 20001;
        int[] freq = new int[SIZE];

        for (int num : nums) {
            freq[num + OFFSET]++;
        }

        int remain = k;
        for (int idx = SIZE - 1; idx >= 0; idx--) {
            remain -= freq[idx];
            if (remain <= 0) {
                return idx - OFFSET;
            }
        }

        return -1;
    }
}
```
