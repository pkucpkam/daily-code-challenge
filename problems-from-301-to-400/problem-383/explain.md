# 523. Continuous Subarray Sum

## Intuition

The goal is to determine if an array `nums` has a contiguous subarray of length at least 2 whose sum is a multiple of $k$.

A naive approach would calculate the sum of all possible subarrays of length $\ge 2$, taking $\mathcal{O}(N^2)$ time, which will result in a Time Limit Exceeded (TLE) error given $N \le 10^5$.

To achieve an optimal $\mathcal{O}(N)$ solution, we can leverage the **Prefix Sum** technique combined with **Modular Arithmetic**:

If the sum of a subarray from index $i + 1$ to $j$ is a multiple of $k$:
$$(S[j] - S[i]) \pmod k = 0 \implies S[j] \pmod k = S[i] \pmod k$$

Where $S[x]$ is the prefix sum from index `0` up to index `x`.

> **Key Insight:** If two prefix sums have the exact same remainder when divided by $k$, the subarray between their respective ending indices has a sum that is a multiple of $k$.

---

## Key Insights & Algorithm

### Approach: Prefix Sum + Hash Map (Remainder Tracking)

1. Maintain a running sum `runningSum` as we iterate through `nums`.
2. Compute `remainder = runningSum % k`.
3. Use a HashMap `remainderMap` to store the **earliest index** where each remainder was encountered (`<Remainder, Index>`).
4. Initialize the map with `{0: -1}` to handle cases where a valid subarray starts from index `0`.
5. For each element at index `i`:
   - Calculate `remainder`.
   - If `remainder` exists in `remainderMap`:
     - Retrieve its first occurrence index `prevIndex`.
     - Check if `i - prevIndex >= 2`. If so, return `true`.
   - Else:
     - Store `(remainder, i)` in `remainderMap`. **Note:** Never overwrite an existing remainder's index, as keeping the earliest index maximizes the subarray length.
6. If the loop completes without finding a valid subarray, return `false`.

---

## Walkthrough

Consider `nums = [23, 2, 4, 6, 7]`, `k = 6`:

`remainderMap` starts as `{ 0: -1 }`.

| Index `i` | `nums[i]` | `runningSum` | `remainder` (`sum % 6`) | Action / Map State | Result |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Start** | — | `0` | — | `{ 0: -1 }` | — |
| **`0`** | `23` | `23` | `5` | Add `(5, 0)` | Map: `{ 0: -1, 5: 0 }` |
| **`1`** | `2` | `25` | `1` | Add `(1, 1)` | Map: `{ 0: -1, 5: 0, 1: 1 }` |
| **`2`** | `4` | `29` | `5` | Remainder `5` seen at `i = 0`. Length: $2 - 0 = 2 \ge 2$. | **Return `true`** ✅ |

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$ — We pass through the array `nums` once. Map operations (`containsKey`, `get`, `put`) operate in $\mathcal{O}(1)$ average time.
- **Space Complexity:** $\mathcal{O}(\min(N, K))$ — In the worst case, the map stores at most $K$ distinct remainders (or $N$ remainders if $N < K$).
