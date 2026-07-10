# Minimum Moves to Equal Array Elements II

## Intuition

The problem asks for the minimum number of moves to make all array elements equal. In one move, we can increment or decrement any element by $1$. This is equivalent to finding a target value $x$ that minimizes the sum of absolute differences:
$$ \text{Total Moves} = \sum_{i=0}^{n-1} |nums[i] - x| $$

This is a classic mathematical optimization problem. The value $x$ that minimizes the sum of absolute deviations is the **median** of the array.

### Why the Median?
Consider a sorted array of numbers on a 1D coordinate line.
Suppose we pair the elements from the outside in: the first with the last, the second with the second-to-last, and so on. For any pair $(a, b)$ where $a \le b$, we want to find a target $x$ that minimizes:
$$ |a - x| + |b - x| $$
- If we choose $x$ such that $a \le x \le b$, the sum of distances is exactly $b - a$ (the distance between them).
- If we choose $x$ outside the interval $[a, b]$, the sum of distances is strictly greater than $b - a$.

To minimize the total distance for all pairs simultaneously, our target $x$ must lie within the intervals of all pairs. The only point (or range of points) that satisfies this for every pair is the **median** of the dataset.

Thus, we can:
1. Sort the array.
2. Pair the elements at index `i` and `n - 1 - i`.
3. The minimum moves required for this pair is `nums[n - 1 - i] - nums[i]`.
4. Sum these differences for all pairs.

---

## Algorithm

1. **Sort** the input array `nums`.
2. Initialize two pointers: `i = 0` (pointing to the start) and `j = nums.length - 1` (pointing to the end).
3. Initialize `moves = 0`.
4. While `i < j`:
   - Add the distance between the paired elements `nums[j] - nums[i]` to `moves`.
   - Increment `i` and decrement `j`.
5. Return `moves`.

---

## Complexity Analysis

### Time Complexity: $\mathcal{O}(N \log N)$
- The most expensive operation is sorting the array `nums` of size $N$, which takes $\mathcal{O}(N \log N)$ time.
- The two-pointer traversal takes linear time $\mathcal{O}(N)$.

### Space Complexity: $\mathcal{O}(1)$ or $\mathcal{O}(\log N)$
- We only use a few variables for the two-pointer approach, requiring $\mathcal{O}(1)$ auxiliary space.
- Depending on the sorting implementation (e.g., dual-pivot Quicksort in Java), it may use $\mathcal{O}(\log N)$ space for the recursion stack.

---

## Alternative Approach: Quickselect $\mathcal{O}(N)$

Instead of sorting the entire array, we only need the median element. We can find the median ($k$-th smallest element where $k = n / 2$) in $\mathcal{O}(N)$ average time using the **Quickselect** algorithm (similar to Quicksort partition).

Once the median is found, we can compute the sum of absolute differences in $\mathcal{O}(N)$ time:
- **Average Time Complexity**: $\mathcal{O}(N)$
- **Worst-case Time Complexity**: $\mathcal{O}(N^2)$ (if pivots are chosen poorly, though randomizing pivots prevents this in practice).
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space if implemented iteratively.
