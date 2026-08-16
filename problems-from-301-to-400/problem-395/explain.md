# 540. Single Element in a Sorted Array

## Intuition

We are given a sorted array where every element appears exactly twice except for one single element that appears once. Our goal is to find this single element in $\mathcal{O}(\log N)$ time and $\mathcal{O}(1)$ space.

### Index Parity Observation

In a sorted array where every element appears twice in pairs, before the single element appears:
- The **first occurrence** of every pair is at an **even index** ($0, 2, 4, \dots$).
- The **second occurrence** of every pair is at an **odd index** ($1, 3, 5, \dots$).

For example, in `[1, 1, 2, 3, 3, 4, 4, 8, 8]`:
- Pair `1, 1`: indices `0` (even), `1` (odd).
- Single element `2` at index `2`.
- Pair `3, 3`: indices `3` (odd), `4` (even). Notice how the parity pattern shifted!
- Pair `4, 4`: indices `5` (odd), `6` (even).

Once the single element is introduced, the parity pattern flips for all subsequent pairs:
- **Left of Single Element**: `nums[even] == nums[odd]` (i.e., `nums[i] == nums[i + 1]` when `i` is even).
- **Right of Single Element**: `nums[even] != nums[odd]` (i.e., `nums[i] == nums[i - 1]` when `i` is even).

This monotonic property allows us to use **Binary Search** to locate the single element in $\mathcal{O}(\log N)$ time.

---

## Key Insights & Algorithm

### 1. Binary Search on Parity

Instead of checking whether `mid` is even or odd using modulo operations, we can use the bitwise XOR trick (`mid ^ 1`):
- If `mid` is **even**, `mid ^ 1 = mid + 1`.
- If `mid` is **odd**, `mid ^ 1 = mid - 1`.

Thus, `nums[mid] == nums[mid ^ 1]` tests whether `mid` and its partner index form a valid pair according to the pre-single element parity pattern.

### 2. Decision Logic

1. Initialize `low = 0` and `high = nums.length - 1`.
2. While `low < high`:
   - Compute `mid = low + (high - low) / 2`.
   - Compare `nums[mid]` with `nums[mid ^ 1]`:
     - **If `nums[mid] == nums[mid ^ 1]`**:
       The pair up to `mid` is intact and follows the correct parity. The single element must be strictly to the **right** of `mid`. Therefore, set `low = mid + 1`.
     - **If `nums[mid] != nums[mid ^ 1]`**:
       The parity pattern is disrupted at or before `mid`. The single element must be at or to the **left** of `mid`. Therefore, set `high = mid`.
3. When `low == high`, the loop terminates and `nums[low]` is the single element.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(\log N)$
  - Binary search reduces the search space by half in each iteration, where $N$ is the number of elements in `nums`.

- **Space Complexity**: $\mathcal{O}(1)$
  - Only a constant amount of extra space is used for variables (`low`, `high`, `mid`).

