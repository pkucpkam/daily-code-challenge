# 436. Find Right Interval

**Difficulty:** Medium

You are given an array of `intervals`, where `intervals[i] = [start_i, end_i]` and each `start_i` is unique.

The right interval for an interval `i` is an interval `j` such that `start_j >= end_i` and `start_j` is minimized. Note that `i` may equal `j`.

Return an array of right interval indices for each interval `i`. If no right interval exists for interval `i`, then put `-1` at index `i`.

## Examples

**Example 1:**

```
Input: intervals = [[1,2]]
Output: [-1]
Explanation: There is only one interval in the collection, so it outputs -1.
```

**Example 2:**

```
Input: intervals = [[3,4],[2,3],[1,2]]
Output: [-1,0,1]
Explanation: There is no right interval for [3,4].
The right interval for [2,3] is [3,4] since start_0 = 3 is the smallest start that is >= end_1 = 3.
The right interval for [1,2] is [2,3] since start_1 = 2 is the smallest start that is >= end_2 = 2.
```

**Example 3:**

```
Input: intervals = [[1,4],[2,3],[3,4]]
Output: [-1,2,-1]
Explanation: There is no right interval for [1,4] and [3,4].
The right interval for [2,3] is [3,4] since start_2 = 3 is the smallest start that is >= end_1 = 3.
```

## Constraints:

- `1 <= intervals.length <= 2 * 10^4`
- `intervals[i].length == 2`
- `-10^6 <= start_i <= end_i <= 10^6`
- The start point of each interval is unique.