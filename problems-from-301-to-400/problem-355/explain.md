# Hash Map Approach (O(N^2) Time and O(N^2) Space)

## Intuition

The problem asks us to find the number of tuples `(i, j, k, l)` such that `nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0`.
A brute-force approach would check all combinations of `(i, j, k, l)`, resulting in an $O(N^4)$ time complexity, which is too slow since $n$ can be up to $200$ ($200^4 = 1.6 \times 10^9$ operations).

To optimize this, we can divide the four arrays into two groups:
1. `nums1` and `nums2`
2. `nums3` and `nums4`

We can rewrite the equation as:
$$nums1[i] + nums2[j] = -(nums3[k] + nums4[l])$$

By calculating all possible sums of pairs from `nums1` and `nums2`, and storing their frequencies in a Hash Map, we can then find the complement sums from `nums3` and `nums4` in $O(1)$ lookup time.

## Algorithm

1. **Count Pairs in First Group:**
   - Initialize a Hash Map to store the sum of `nums1[i] + nums2[j]` as the key and its frequency count as the value.
   - Iterate through every element `a` in `nums1` and every element `b` in `nums2`.
   - Compute the sum `a + b` and increment its count in the Hash Map.

2. **Match with Second Group:**
   - Initialize a counter `count = 0`.
   - Iterate through every element `c` in `nums3` and every element `d` in `nums4`.
   - Compute the target complement `-(c + d)`.
   - If `-(c + d)` exists in the Hash Map, add its frequency count to `count`.

3. **Return `count`.**

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N^2)$
  - Generating the sum frequencies for `nums1` and `nums2` takes $\mathcal{O}(N^2)$ time as it requires nested loops.
  - Finding the complement for each pair sum of `nums3` and `nums4` also takes $\mathcal{O}(N^2)$ time, with average $\mathcal{O}(1)$ lookup time for each map query.
- **Space Complexity:** $\mathcal{O}(N^2)$
  - In the worst case, the Hash Map will store up to $N^2$ unique sum entries when all pair sums from `nums1` and `nums2` are distinct.
