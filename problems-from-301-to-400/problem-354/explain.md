# Mathematical Approach: O(N) Time and O(1) Space

## Intuition

Incrementing $n - 1$ elements by $1$ is mathematically equivalent to **decrementing** $1$ element by $1$ relative to all other elements. 

For example, if we have `[1, 2, 3]`:
- Incrementing the two smallest elements by $1$ gives `[2, 3, 3]`.
- Relative to the maximum element, this is the same as reducing the largest element from $3$ to $2$, which is `[2, 3, 2]` (if we shift the baseline).
- More formally, if our target is to make all elements equal, we can think of it as pushing all elements down to the minimum element of the array.

Let $min\_val$ be the minimum value in the array. To make any element $x$ equal to $min\_val$, we must decrement it by $x - min\_val$. 
Thus, the minimum number of moves is the sum of differences between each element and the minimum element:
$$\text{Moves} = \sum_{i=0}^{n-1} (nums[i] - min\_val)$$

## Algorithm

1. **Find the Minimum:** Traverse the array to find the minimum element, `min`.
2. **Calculate Moves:** Traverse the array again, and for each element `num`, add `num - min` to a running sum `moves`.
3. Return `moves`.

*Note:* Since the problem guarantees that the final answer fits in a 32-bit signed integer, we can accumulate the differences directly in an `int` without worrying about overflow during intermediate additions.

## Complexity

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of elements in the array. We make two linear passes over the array.
- **Space Complexity:** $\mathcal{O}(1)$ as we only store a few variables (`min`, `moves`).
