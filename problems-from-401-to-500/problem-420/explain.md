# 581. Shortest Unsorted Continuous Subarray

## Problem Understanding

Given an integer array `nums`, find the length of the shortest continuous subarray such that sorting only this subarray in non-decreasing order results in the entire array being sorted in non-decreasing order.

If the array is already sorted, no subarray needs to be reordered, and the answer is `0`.

---

## Key Insights & Mathematical Formulation

### 1. The Three-Section Partition: $[A \mid B \mid C]$

Any array that can be sorted by sorting a middle subarray can be conceptually split into three contiguous segments:

$$\text{nums} = [\underbrace{A}_{\text{Sorted Prefix}} \mid \underbrace{B}_{\text{Unsorted Subarray}} \mid \underbrace{C}_{\text{Sorted Suffix}}]$$

Where:
- $A = \text{nums}[0 \dots \text{left} - 1]$ is already sorted in non-decreasing order.
- $B = \text{nums}[\text{left} \dots \text{right}]$ is the subarray we choose to sort.
- $C = \text{nums}[\text{right} + 1 \dots n - 1]$ is already sorted in non-decreasing order.

### 2. Invariant Conditions for Complete Sorting

For the entire array to be sorted after sorting only $B$, two strict boundary invariants must hold:
1. Every element in the prefix $A$ must be less than or equal to **every element in $B$ and $C$**:
   $$\max(A) \le \min(B \cup C)$$
2. Every element in the suffix $C$ must be greater than or equal to **every element in $A$ and $B$**:
   $$\min(C) \ge \max(A \cup B)$$

Equivalently:
- **Right Boundary ($\text{right}$):** Any element at index $i$ that is **smaller** than the maximum element seen so far to its left ($\max_{0 \le k < i} \text{nums}[k]$) violates the sorted order. Therefore, the rightmost index where this violation occurs is the right boundary $\text{right}$. Everything after $\text{right}$ is greater than or equal to all elements preceding it.
- **Left Boundary ($\text{left}$):** Any element at index $j$ that is **larger** than the minimum element seen so far to its right ($\min_{j < k < n} \text{nums}[k]$) violates the sorted order. Therefore, the leftmost index where this violation occurs is the left boundary $\text{left}$. Everything before $\text{left}$ is smaller than or equal to all elements following it.

### 3. The One-Pass Parallel Traversal

We can locate both $\text{left}$ and $\text{right}$ simultaneously in a single pass of length $n$:
- Moving left-to-right (index $i$ from $1$ to $n - 1$): maintain `maxSoFar`. If $\text{nums}[i] < \text{maxSoFar}$, update $\text{right} = i$.
- Moving right-to-left (index $j$ from $n - 2$ down to $0$): maintain `minSoFar`. If $\text{nums}[j] > \text{minSoFar}$, update $\text{left} = j$.

If no violations are found ($\text{right} == -1$), the array is already sorted, so return `0`. Otherwise, the length of the shortest subarray is:
$$\text{Length} = \text{right} - \text{left} + 1$$

---

## Detailed Approaches & Evolution

### Approach 1: Sorting & Comparison (Brute Force / Naive)

1. Clone the array: `int[] sorted = nums.clone()`.
2. Sort the cloned array: `Arrays.sort(sorted)`.
3. Compare `nums` with `sorted` from left to right to find the first index where $\text{nums}[i] \ne \text{sorted}[i]$ (this is $\text{left}$).
4. Compare `nums` with `sorted` from right to left to find the last index where $\text{nums}[j] \ne \text{sorted}[j]$ (this is $\text{right}$).
5. If $\text{left} \ge \text{right}$, the array was already sorted (return `0`), otherwise return $\text{right} - \text{left} + 1$.

- **Time Complexity:** $\mathcal{O}(n \log n)$ due to sorting.
- **Space Complexity:** $\mathcal{O}(n)$ to store the copy.

---

### Approach 2: Monotonic Stack

1. Use a monotonic increasing stack to find the left boundary:
   - Iterate from $0$ to $n - 1$. While the current element is smaller than the element at the stack top, pop from the stack and update $\text{left} = \min(\text{left}, \text{popped\_index})$.
2. Use another monotonic stack iterating backwards from $n - 1$ to $0$:
   - While the current element is larger than the stack top, pop and update $\text{right} = \max(\text{right}, \text{popped\_index})$.
3. Return $\text{right} - \text{left} + 1$ if valid, else `0`.

- **Time Complexity:** $\mathcal{O}(n)$ — each index pushed and popped at most once.
- **Space Complexity:** $\mathcal{O}(n)$ for the stack.

---

### Approach 3: Two-Pass Min/Max Subarray Expansion

1. Scan from left to right to find the first dip (where $\text{nums}[i] < \text{nums}[i - 1]$).
2. Scan from right to left to find the first rise (where $\text{nums}[i] > \text{nums}[i + 1]$).
3. Within this provisional window, determine the minimum value $m$ and maximum value $M$.
4. Expand the left boundary leftwards as long as an element is $> m$.
5. Expand the right boundary rightwards as long as an element is $< M$.

- **Time Complexity:** $\mathcal{O}(n)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### Approach 4: Optimal One-Pass Two Pointers (Best Solution)

This approach merges the tracking into a single unified pass with constant space:
- Maintain running `maxSoFar` from index $0$ upwards.
- Maintain running `minSoFar` from index $n - 1$ downwards.
- In a single loop where $i$ goes from $1$ to $n - 1$ and $j = n - 1 - i$:
  - Update `maxSoFar` with $\text{nums}[i]$; if $\text{nums}[i] < \text{maxSoFar}$, set $\text{right} = i$.
  - Update `minSoFar` with $\text{nums}[j]$; if $\text{nums}[j] > \text{minSoFar}$, set $\text{left} = j$.

- **Time Complexity:** $\mathcal{O}(n)$
- **Space Complexity:** $\mathcal{O}(1)$

---

## Step-by-Step Execution Trace (Example 1)

Input: `nums = [2, 6, 4, 8, 10, 9, 15]`, $n = 7$.  
Initial state: `left = -1`, `right = -1`, `maxSoFar = 2`, `minSoFar = 15`.

| $i$ | $j = 6 - i$ | $\text{nums}[i]$ | $\text{maxSoFar}$ | Update $\text{right}$? | $\text{nums}[j]$ | $\text{minSoFar}$ | Update $\text{left}$? |
| :-: | :---------: | :--------------: | :---------------: | :--------------------: | :--------------: | :---------------: | :-------------------: |
| $1$ | $5$         | $6$              | $\max(2, 6) = 6$  | $6 \ge 6$ (No)         | $9$              | $\min(15, 9) = 9$ | $9 \le 9$ (No)        |
| $2$ | $4$         | $4$              | $\max(6, 4) = 6$  | $4 < 6 \implies \mathbf{right = 2}$ | $10$        | $\min(9, 10) = 9$ | $10 > 9 \implies \mathbf{left = 4}$  |
| $3$ | $3$         | $8$              | $\max(6, 8) = 8$  | $8 \ge 8$ (No)         | $8$              | $\min(9, 8) = 8$  | $8 \le 8$ (No)        |
| $4$ | $2$         | $10$             | $\max(8, 10) = 10$| $10 \ge 10$ (No)       | $4$              | $\min(8, 4) = 4$  | $4 \le 4$ (No)        |
| $5$ | $1$         | $9$              | $\max(10, 9) = 10$| $9 < 10 \implies \mathbf{right = 5}$| $6$         | $\min(4, 6) = 4$  | $6 > 4 \implies \mathbf{left = 1}$   |
| $6$ | $0$         | $15$             | $\max(10, 15) = 15$| $15 \ge 15$ (No)      | $2$              | $\min(4, 2) = 2$  | $2 \le 2$ (No)        |

**Final Result:**
- $\text{left} = 1$
- $\text{right} = 5$
- $\text{Length} = \text{right} - \text{left} + 1 = 5 - 1 + 1 = \mathbf{5}$
- The subarray to sort is `[6, 4, 8, 10, 9]`.

---

## Code Implementation (Java)

```java
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int n = nums.length;
        int left = -1;
        int right = -1;

        int maxSoFar = nums[0];
        int minSoFar = nums[n - 1];

        for (int i = 1; i < n; i++) {
            // Left-to-right pass:
            // Find the rightmost element that is smaller than the running maximum
            maxSoFar = Math.max(maxSoFar, nums[i]);
            if (nums[i] < maxSoFar) {
                right = i;
            }

            // Right-to-left pass:
            // Find the leftmost element that is larger than the running minimum
            int j = n - 1 - i;
            minSoFar = Math.min(minSoFar, nums[j]);
            if (nums[j] > minSoFar) {
                left = j;
            }
        }

        return right == -1 ? 0 : right - left + 1;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :---: | :--- |
| **Time Complexity** | $\mathcal{O}(n)$ | We perform a single traversal from index $1$ to $n - 1$. Each iteration performs constant-time $\mathcal{O}(1)$ arithmetic and comparisons. |
| **Space Complexity** | $\mathcal{O}(1)$ | Only a constant number of primitive variables (`n`, `left`, `right`, `maxSoFar`, `minSoFar`, `i`, `j`) are allocated on the stack. No heap allocation or recursion. |

---

## Comparison of Approaches

| Approach | Time Complexity | Auxiliary Space | Passes | Extra Notes |
| :--- | :---: | :---: | :---: | :--- |
| **Sorting & Comparison** | $\mathcal{O}(n \log n)$ | $\mathcal{O}(n)$ | $2$ | Simplest to write, but wastes space and fails the $\mathcal{O}(n)$ follow-up constraint. |
| **Monotonic Stack** | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | $2$ | Satisfies linear time, but allocates $\mathcal{O}(n)$ memory for stack structures. |
| **Min/Max Window Expansion** | $\mathcal{O}(n)$ | $\mathcal{O}(1)$ | $4$ | Optimal complexity, but involves multi-stage pointer scanning and edge checks. |
| **One-Pass Two Pointers** | $\mathcal{O}(n)$ | $\mathcal{O}(1)$ | $1$ | **Optimal.** Cleanest logic, minimal lines of code, and lowest constant factors. |

---

## Edge Cases Handled

1. **Already Sorted Array (`nums = [1, 2, 3, 4]`):**
   - No element is smaller than `maxSoFar` and no element is greater than `minSoFar`.
   - `right` remains `-1`, and the function returns `0`.

2. **Reverse Sorted Array (`nums = [5, 4, 3, 2, 1]`):**
   - Every element violates the condition.
   - `left` becomes `0`, `right` becomes $n - 1$.
   - Returns full length $n$.

3. **Array with Identical Elements (`nums = [2, 2, 2, 2]`):**
   - Strict inequality checks (`<` and `>`) ensure no false positives.
   - `right` remains `-1`, returns `0`.

4. **Single Element or Empty Array (`nums = [1]`):**
   - The initial guard `if (nums == null || nums.length <= 1) return 0;` handles this in $\mathcal{O}(1)$.

5. **Duplicates at Boundaries (`nums = [1, 3, 2, 2, 2]`):**
   - Handles repeated values correctly; `left = 1` and `right = 4`, returning $4$ (`[3, 2, 2, 2]`).
