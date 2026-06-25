# TreeMap Approach: Binary Search for Minimum Start Time

## Intuition

The problem asks us to find the "right interval" for each interval in the given array. The right interval `j` for an interval `i` must satisfy `start_j >= end_i`, and we need to find the one with the **minimum** `start_j`. If no such interval exists, we return `-1`.

A naive approach would be to check every interval against every other interval, which would take **O(N²)** time. We can optimize this by organizing the data. By keeping the start times sorted, we can efficiently perform a binary search to find the smallest start time that is greater than or equal to our target end time. 

Java's `TreeMap` is the perfect data structure for this because it maintains its keys in sorted order and provides built-in methods for these exact types of queries.

## Algorithm

1. **Store Start Times and Indices:** 
   - Initialize a `TreeMap<Integer, Integer>`.
   - Iterate through the given `intervals`. For each interval, insert its start time (`intervals[i][0]`) as the key and its original index (`i`) as the value.
   - The `TreeMap` will automatically sort the entries by the start times.

2. **Querying the Right Interval:** 
   - Initialize a `result` array of the same length as the input.
   - Iterate through the `intervals` array one more time. For each interval at index `i`, we need to find the smallest start time in our map that is greater than or equal to its end time (`intervals[i][1]`).
   - Use the `treeMap.ceilingEntry(key)` method. It returns the key-value mapping associated with the least key strictly greater than or equal to the given key, or `null` if there is no such key.
   - We query the map with `ceilingEntry(intervals[i][1])`. 
   - If it returns an entry, we grab its value (the original index) and store it in `result[i]`. If it returns `null`, we store `-1`.

3. **Return Result:** 
   - Return the populated `result` array.

## Complexity

- **Time Complexity:** `O(N log N)`, where `N` is the number of intervals. Building the `TreeMap` takes `O(N log N)` time since each of the `N` insertions takes `O(log N)` time. The second loop queries the `TreeMap` `N` times, and each query via `ceilingEntry` also takes `O(log N)` time. Thus, the total time complexity is bounded by `O(N log N)`.
- **Space Complexity:** `O(N)`. We are storing `N` interval start times and their indices as key-value pairs in the `TreeMap`. The space required for the output array `result` is also `O(N)`.
