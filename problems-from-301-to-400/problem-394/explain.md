# 539. Minimum Time Difference

## Intuition

The problem asks us to find the minimum time difference in minutes between any pair of time points given in `"HH:MM"` format on a 24-hour clock.

Key observations:
1. **Total Minutes in a Day**: A 24-hour day has $24 \times 60 = 1440$ possible minute values (from `0` for `00:00` to `1439` for `23:59`).
2. **Circular Nature of Time**: The 24-hour clock wraps around at midnight. Therefore, the difference between the latest time point in a day and the earliest time point across midnight is $(1440 - \text{last} + \text{first})$.
3. **Pigeonhole Principle**: If there are more than 1440 time points in the input list, at least two time points must be identical, making the minimum difference `0` immediately.

Instead of sorting the list of time points in $\mathcal{O}(N \log N)$ time, we can use a **Bucket Array / Boolean Tracking** strategy (Bucket Sort) to process the time points in $\mathcal{O}(N)$ time with $\mathcal{O}(1)$ auxiliary space.

---

## Key Insights & Algorithm

### 1. Fast Input Size Check (Pigeonhole Principle)
If `timePoints.size() > 1440`, return `0` immediately.

### 2. Time Conversion & Duplicate Detection
- Convert each `"HH:MM"` string into total minutes:
  $$\text{totalMinutes} = \text{hours} \times 60 + \text{minutes}$$
- Use a boolean array `minutes` of size `1440`.
- Mark `minutes[totalMinutes] = true`. If `minutes[totalMinutes]` was already `true`, return `0` immediately as a duplicate has been detected.

### 3. Linear Scan for Consecutive & Circular Differences
- Iterate through the boolean array from `0` to `1439`.
- Maintain `prev` (the minute of the previously seen time point), `first` (the minute of the earliest time point), and `last` (the minute of the latest time point).
- For consecutive present time points, update `minDiff = Math.min(minDiff, i - prev)`.
- Account for the circular wrap-around across midnight:
  $$\text{minDiff} = \min(\text{minDiff},\ 1440 - \text{last} + \text{first})$$

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(N)$
  - Converting $N$ time strings takes $\mathcal{O}(N)$ time. Since $N$ is capped at 1440 by the Pigeonhole check, the loop over time points runs at most 1440 times.
  - Scanning the boolean array of size 1440 takes fixed $\mathcal{O}(1)$ time.
  - Overall time complexity is strictly $\mathcal{O}(N)$, which is bounded by a small constant.

- **Space Complexity**: $\mathcal{O}(1)$
  - A fixed boolean array of size 1440 is used, which consumes negligible and constant memory.
