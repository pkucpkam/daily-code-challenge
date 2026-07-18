# 477. Total Hamming Distance

**Medium**

The Hamming distance between two integers is the number of positions at which the corresponding bits are different.

Given an integer array `nums`, return the sum of Hamming distances between all the pairs of the integers in `nums`.

## Example 1:
```
Input: nums = [4,14,2]
Output: 6
Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just showing the four bits relevant in this case).
The answer will be:
HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.
```

## Example 2:
```
Input: nums = [4,14,4]
Output: 4
```

## Constraints:
- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 10^9`
- The answer for the given input will fit in a 32-bit integer.

---

## Optimal Solution Approach

The naive approach of checking every pair takes O(N²) time, which is too slow for `N = 10^4`. 

Instead of looking at pairs of numbers, we can look at the numbers **bit by bit**:
1. Since the numbers fit in a 32-bit integer, we iterate through each bit position `i` from `0` to `31`.
2. For each bit position, we count how many numbers in the array have their `i`-th bit set to `1`. Let this count be `k`.
3. The remaining `N - k` numbers will have their `i`-th bit set to `0` (where `N` is the size of the array).
4. The number of pairs that differ at the `i`-th bit is simply the number of `1`s multiplied by the number of `0`s, which is `k * (N - k)`.
5. We sum this contribution across all 32 bit positions to get the total Hamming distance.

### Complexity
- **Time Complexity:** O(N), specifically O(32 * N), since we iterate over all numbers for each of the 32 bits.
- **Space Complexity:** O(1), as we only use a few primitive variables.