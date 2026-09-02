# 565. Array Nesting

## Problem Understanding

You are given an array `nums` of length `n` containing a permutation of numbers in the range `[0, n - 1]`.  
Starting from any index `k`, you build a sequence of elements: `nums[k], nums[nums[k]], nums[nums[nums[k]]], ...` until a duplicate number appears (which creates a cycle).

**Goal:** Find the maximum length among all such sets `s[k]`.

---

## Key Insights & Mathematical Properties

### 1. Permutation Functional Graph (Disjoint Cycles)
Since `nums` is a permutation of numbers from `0` to `n - 1`:
- Every index `i` has exactly **one outgoing edge** pointing to `nums[i]`.
- Every value `v` has exactly **one incoming edge** (since all numbers are distinct).
- This graph structure forms a collection of **disjoint closed cycles**.

### 2. No Overlapping or Nested Cycles
- Every number belongs to **exactly one cycle**.
- If two indices `a` and `b` belong to the same sequence, they are part of the **same cycle** and will produce sets of identical length.
- Once a node in a cycle has been visited, the entire cycle is known. We do **not** need to re-traverse any element in that cycle.

### 3. $\mathcal{O}(1)$ Auxiliary Space via In-Place Marking
- Instead of using an extra `boolean[] visited` array ($\mathcal{O}(N)$ space), we can mark an index as visited in-place by setting `nums[i] = -1` (since all original values are $\ge 0$).

---

## Optimal Approach: In-Place Visited Marking

### Algorithm Steps:
1. Iterate through each index `i` from `0` to `n - 1`.
2. If `nums[i] != -1` (unvisited):
   - Start traversing the cycle starting at index `i`.
   - Maintain a `count` of elements in the current cycle.
   - For each element in the cycle, store `next = nums[curr]`, set `nums[curr] = -1`, and move `curr = next`.
   - Update `maxLength = max(maxLength, count)`.
3. Return `maxLength`.

---

## Code Implementation

```java
class Solution {
    public int arrayNesting(int[] nums) {
        int maxLength = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] != -1) {
                int count = 0;
                int curr = i;

                while (nums[curr] != -1) {
                    int next = nums[curr];
                    nums[curr] = -1; // Mark as visited
                    curr = next;
                    count++;
                }

                maxLength = Math.max(maxLength, count);
            }
        }

        return maxLength;
    }
}
```

---

## Step-by-Step Example Walkthrough

### Example 1: `nums = [5, 4, 0, 3, 1, 6, 2]`

- **Index 0 (`nums[0] = 5`)**: Unvisited
  - `curr = 0` $\rightarrow$ `next = 5`, set `nums[0] = -1`, `count = 1`
  - `curr = 5` $\rightarrow$ `next = 6`, set `nums[5] = -1`, `count = 2`
  - `curr = 6` $\rightarrow$ `next = 2`, set `nums[6] = -1`, `count = 3`
  - `curr = 2` $\rightarrow$ `next = 0`, set `nums[2] = -1`, `count = 4`
  - `curr = 0` $\rightarrow$ `nums[0] == -1` (cycle complete)
  - `maxLength = max(0, 4) = 4`

- **Index 1 (`nums[1] = 4`)**: Unvisited
  - `curr = 1` $\rightarrow$ `next = 4`, set `nums[1] = -1`, `count = 1`
  - `curr = 4` $\rightarrow$ `next = 1`, set `nums[4] = -1`, `count = 2`
  - `curr = 1` $\rightarrow$ `nums[1] == -1` (cycle complete)
  - `maxLength = max(4, 2) = 4`

- **Index 2 (`nums[2] = -1`)**: Already visited, skip.
- **Index 3 (`nums[3] = 3`)**: Unvisited
  - `curr = 3` $\rightarrow$ `next = 3`, set `nums[3] = -1`, `count = 1`
  - `curr = 3` $\rightarrow$ `nums[3] == -1` (cycle complete)
  - `maxLength = max(4, 1) = 4`

- **Indices 4, 5, 6 (`-1`)**: Already visited, skip.

**Final Answer:** `4`

---

## Complexity Analysis

| Metric | Complexity | Explanation |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(N)$ | Every element is processed at most twice (once in outer loop, once during cycle traversal). |
| **Space Complexity** | $\mathcal{O}(1)$ | Modifies the input array in-place without using extra memory. |

---

## Comparison of Approaches

| Approach | Time | Space | Notes |
|:---|:---|:---|:---|
| **Brute Force** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | Re-traverses cycles repeatedly without storing visited state |
| **Boolean Visited Array** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Ideal if mutating the input array is strictly forbidden |
| **In-Place Marking (Optimal)** ✅ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Best performance and memory usage |
