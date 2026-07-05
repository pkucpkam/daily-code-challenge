# Greedy Approach: O(N log N) Time and O(1) Space

## Intuition

This problem can be modeled as finding the minimum number of points needed to intersect a set of intervals. This is a classic greedy interval scheduling problem.

To minimize the number of arrows, we want each arrow to burst as many balloons as possible:
- If we sort the balloons by their **end coordinates**, we can greedily place an arrow at the end coordinate of the first balloon. This ensures the arrow travels as far to the right as possible, maximizing its chance of bursting subsequent balloons.
- Any balloon that starts before or at this arrow's position will be burst by it.
- As soon as we find a balloon that starts *after* the current arrow's position, we need a new arrow. We then place this new arrow at the end coordinate of that balloon and repeat the process.

## Algorithm

1. **Edge Case:** If the `points` array is empty, return `0`.
2. **Sort by End Coordinate:** Sort the balloons in ascending order of their end coordinates (`points[i][1]`).
   - *Note:* We must use `Integer.compare(a[1], b[1])` instead of subtraction `a[1] - b[1]` to avoid integer overflow issues (since coordinates can range from $-2^{31}$ to $2^{31}-1$).
3. **Greedy Selection:**
   - Initialize `arrows = 1` and place the first arrow at the end of the first balloon: `arrowPos = points[0][1]`.
   - Iterate through the remaining balloons starting from index `1`:
     - If the start of the current balloon `points[i][0]` is greater than `arrowPos`, it is out of range of the current arrow.
     - Increment `arrows` and update `arrowPos = points[i][1]`.
4. Return `arrows`.

## Complexity

- **Time Complexity:** $\mathcal{O}(N \log N)$ where $N$ is the number of balloons. This is dominated by the sorting step. The subsequent linear scan takes $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(1)$ or $\mathcal{O}(\log N)$ depending on the space required by the sorting algorithm implementation in Java (e.g., dual-pivot quicksort).
