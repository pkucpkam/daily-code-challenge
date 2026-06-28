# Cyclic Sort / Index Mapping Approach: O(N) Time

## Intuition

We are given an array of size `n` where all integers are in the range `[1, n]`. Some elements appear twice, and others appear once. We need to find all the elements that appear twice, but with a strict constraint: **O(N) time and O(1) extra space** (excluding the space for the output array).

Because the elements are bounded between `1` and `n`, we can use the input array itself to store additional information. This is a common technique known as **Index Mapping**. 

The idea is that each number `x` in the array can be mapped to the index `x - 1`. As we iterate through the array, for each number we see, we can navigate to its corresponding index (`Math.abs(num) - 1`) and negate the value at that index. 

If we ever find that the value at that mapped index is *already negative*, it means we have visited this index before, which implies we have seen this number before! Thus, it's a duplicate.

## Algorithm

1. Initialize an empty list `result` to store the duplicates.
2. Iterate through each number `num` in the array `nums`:
   - Calculate the index that this number corresponds to: `index = Math.abs(num) - 1`. We use `Math.abs` because the number might have been negated in a previous step.
   - Check the value at `nums[index]`:
     - If `nums[index] < 0`, it means we have already encountered the number `Math.abs(num)` before. So, add `Math.abs(num)` to the `result`.
     - Otherwise, negate the value at `nums[index]` by doing `nums[index] = -nums[index]`. This marks the number `Math.abs(num)` as seen.
3. Return the `result` list.

## Complexity

- **Time Complexity:** `O(N)`, where `N` is the length of the array `nums`. We iterate through the array exactly once, and each operation inside the loop takes constant time `O(1)`.
- **Space Complexity:** `O(1)`. We only modify the input array in place. The space required for the output array does not count toward the space complexity.
