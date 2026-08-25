# 554. Brick Wall

## Intuition

We are given a 2D wall represented by `n` rows of brick widths. We want to draw a vertical line from top to bottom such that it crosses the **minimum number of bricks**.

A key observation is that drawing a vertical line through a wall either:
1. Passes through the interior of a brick (which counts as crossing a brick), or
2. Passes through the **edge** (boundary gap) between two adjacent bricks (which does **not** count as crossing a brick).

Instead of directly counting crossed bricks, we can invert the problem: **to minimize crossed bricks, we must maximize the number of brick edges aligned vertically at the exact same horizontal position!**

---

## Key Insights & Strategy

### 1. Edge Position Alignment
For each row, as we sum the widths of the bricks from left to right, each cumulative sum represents a **brick edge position** (a vertical boundary gap).

For example, if a row has brick widths `[1, 2, 2, 1]`:
- Edge after brick 1: `x = 1`
- Edge after brick 2: `x = 1 + 2 = 3`
- Edge after brick 3: `x = 1 + 2 + 2 = 5`
- Rightmost boundary: `x = 1 + 2 + 2 + 1 = 6` (outer edge of the wall, not allowed as per problem rules).

### 2. Hash Map Frequency Counting
We can use a Hash Map (`Map<Integer, Integer>`) to count how many rows share an edge at each horizontal position `x`:
- Key: Edge position `x` (cumulative sum)
- Value: Number of rows that have a brick boundary at position `x`

### 3. Calculating the Answer
- Let `maxEdges` be the maximum frequency of any intermediate edge position found in the hash map.
- The minimum number of bricks crossed by a vertical line passing through that position is:
  $$\text{Least Bricks Crossed} = \text{Total Rows} - \text{maxEdges}$$

> **Note:** We must skip the final cumulative sum (the total width of the wall) for each row, because drawing a line at the outer boundary of the wall is explicitly forbidden.

---

## Code Implementation (Java)

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> edgeCounts = new HashMap<>();
        int maxEdges = 0;

        for (List<Integer> row : wall) {
            int edgePosition = 0;
            // Iterate up to the second-to-last brick to avoid counting the rightmost edge of the wall
            for (int i = 0; i < row.size() - 1; i++) {
                edgePosition += row.get(i);
                int count = edgeCounts.getOrDefault(edgePosition, 0) + 1;
                edgeCounts.put(edgePosition, count);
                maxEdges = Math.max(maxEdges, count);
            }
        }

        return wall.size() - maxEdges;
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]`

Total rows $N = 6$.

| Row | Brick Widths | Intermediate Edge Positions (Cumulative Sums) |
|:---|:---|:---|
| Row 0 | `[1, 2, 2, 1]` | `1`, `3`, `5` |
| Row 1 | `[3, 1, 2]` | `3`, `4` |
| Row 2 | `[1, 3, 2]` | `1`, `4` |
| Row 3 | `[2, 4]` | `2` |
| Row 4 | `[3, 1, 2]` | `3`, `4` |
| Row 5 | `[1, 3, 1, 1]` | `1`, `4`, `5` |

**Edge Frequencies in Hash Map:**
- Position `1`: 3 rows (Rows 0, 2, 5)
- Position `2`: 1 row (Row 3)
- Position `3`: 3 rows (Rows 0, 1, 4)
- Position `4`: 4 rows (Rows 1, 2, 4, 5) $\rightarrow$ **`maxEdges = 4`**
- Position `5`: 2 rows (Rows 0, 5)

**Result:**
$$\text{Least Bricks Crossed} = 6 - 4 = 2$$

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Recommendation |
|:---|:---|:---|:---|
| **Hash Map Edge Counting** | $\mathcal{O}(N)$ | $\mathcal{O}(K)$ | **Optimal** — single pass over all bricks |

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the total number of bricks across all rows in the wall. We process each brick width exactly once.
- **Space Complexity:** $\mathcal{O}(K)$, where $K$ is the number of distinct intermediate edge positions stored in the Hash Map (at most $N$).
