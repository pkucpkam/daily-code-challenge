# Greedy Approach: Interval Scheduling

## Intuition

The problem asks for the minimum number of intervals to remove to make the remaining intervals non-overlapping. This is equivalent to finding the maximum number of mutually non-overlapping intervals, and then subtracting that number from the total number of intervals. This is a classic interval scheduling problem that can be solved using a greedy approach.

The optimal greedy strategy is to always pick the interval that ends the earliest. By doing so, we leave as much room as possible for the subsequent intervals, which maximizes the total number of non-overlapping intervals we can keep.

## Algorithm

1. **Sort by End Time**: Sort the given `intervals` in ascending order based on their end times. By doing this, the first element will be the one that finishes first.
2. **Initialize Tracking Variables**: 
    - Keep track of the `end` time of the last valid interval we decided to keep. Initially, this is the end time of the first interval in the sorted list.
    - Initialize a `count` variable to `0` to keep track of the number of overlapping intervals we need to remove.
3. **Iterate Through Intervals**: Start iterating from the second interval.
    - **Overlap Detected**: If the start time of the current interval is strictly less than our tracked `end` time (`intervals[i][0] < end`), it means they overlap. Since we sorted by end time, the tracked `end` is guaranteed to be less than or equal to the current interval's end time. We greedily choose to keep the previous interval because it ends earlier, and we "remove" the current one by incrementing `count`.
    - **No Overlap**: If the start time of the current interval is greater than or equal to the tracked `end` time, they do not overlap. We can keep this new interval, so we update our tracked `end` time to the end time of this current interval.
4. **Return Count**: After checking all intervals, the `count` will hold the minimum number of removals required.

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \log N)$, where $N$ is the number of intervals. Sorting the array takes $\mathcal{O}(N \log N)$ time, and the subsequent linear scan takes $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(\log N)$ or $\mathcal{O}(N)$, depending on the sorting algorithm implementation used by the language (e.g., Dual-Pivot Quicksort in Java takes $\mathcal{O}(\log N)$ space on average, while TimSort can take $\mathcal{O}(N)$). We use $\mathcal{O}(1)$ auxiliary space excluding sorting.
