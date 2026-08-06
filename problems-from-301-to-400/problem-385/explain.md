# 525. Contiguous Array

## Intuition

We are given a binary array `nums` consisting only of `0`s and `1`s. We need to find the maximum length of a contiguous subarray with an equal number of `0`s and `1`s.

A key transformation simplifies this problem:
- Treat `1` as `+1`
- Treat `0` as `-1`

With this transformation, a subarray with an equal number of `0`s and `1`s will have a total sum of `0`.

If the running (prefix) sum at index `j` is equal to the running sum at index `i` (where $i < j$), then the sum of elements from index `i + 1` to `j` must be `0`. This means the subarray `nums[i + 1 ... j]` has an equal number of `0`s and `1`s.

---

## Key Insights & Algorithm

### Prefix Sum with Hash Map (Earliest Index Tracking)

1. **Transform 0 to -1**:
   As we iterate through `nums`, update a running balance variable `count`:
   - If `nums[i] == 1`, add `1` to `count`.
   - If `nums[i] == 0`, subtract `1` from `count`.

2. **Track Earliest Occurrences**:
   To maximize the subarray length `j - i`, we want to find the **earliest** index `i` where the same `count` occurred.
   - Use a Hash Map `map` to store `(count -> index)`.
   - Initialize `map.put(0, -1)` to account for valid subarrays that start from index `0`.

3. **Single Pass Update**:
   - For each index `i`:
     - Update `count`.
     - If `map` already contains `count`, a subarray summing to `0` exists between `map.get(count) + 1` and `i`. Calculate length `i - map.get(count)` and update `maxLen`.
     - If `map` does **not** contain `count`, store `map.put(count, i)`. (Do not update if `count` is already present, as we only care about the earliest occurrence).

---

## Walkthrough

Consider `nums = [0, 1, 0, 0, 1, 1, 0]`:

| Index `i` | `nums[i]` | `count` Change | Running `count` | Map State `(count -> index)` | Subarray Match | Length | `maxLen` |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Start** | — | — | `0` | `{0: -1}` | — | — | `0` |
| `0` | `0` | `-1` | `-1` | `{0: -1, -1: 0}` | First time | — | `0` |
| `1` | `1` | `+1` | `0` | `{0: -1, -1: 0}` | Seen `0` at `-1` | `1 - (-1) = 2` | `2` |
| `2` | `0` | `-1` | `-1` | `{0: -1, -1: 0}` | Seen `-1` at `0` | `2 - 0 = 2` | `2` |
| `3` | `0` | `-1` | `-2` | `{0: -1, -1: 0, -2: 3}` | First time | — | `2` |
| `4` | `1` | `+1` | `-1` | `{0: -1, -1: 0, -2: 3}` | Seen `-1` at `0` | `4 - 0 = 4` | `4` |
| `5` | `1` | `+1` | `0` | `{0: -1, -1: 0, -2: 3}` | Seen `0` at `-1` | `5 - (-1) = 6` | `6` |
| `6` | `0` | `-1` | `-1` | `{0: -1, -1: 0, -2: 3}` | Seen `-1` at `0` | `6 - 0 = 6` | `6` |

**Final Output:** `6` (Subarray `nums[0...5]` has length 6).

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - We traverse the array of length $N$ exactly once.
  - Hash map lookups and insertions take $\mathcal{O}(1)$ average time per element.

- **Space Complexity:** $\mathcal{O}(N)$
  - In the worst case (e.g. array of all `1`s or all `0`s), the map stores up to $N + 1$ unique prefix sum counts.


