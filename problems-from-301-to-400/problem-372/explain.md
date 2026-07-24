# 497. Random Point in Non-overlapping Rectangles

## Intuition

The problem asks us to select an integer point uniformly at random from a set of non-overlapping axis-aligned rectangles.

To guarantee that **every integer point across all rectangles has an equal probability of being chosen**, the probability of selecting a particular rectangle must be directly proportional to the number of integer points it contains.

For a rectangle defined by $(a_i, b_i, x_i, y_i)$:
- Number of integer points along the x-axis: $(x_i - a_i + 1)$
- Number of integer points along the y-axis: $(y_i - b_i + 1)$
- Total integer points inside rectangle $i$:
  $$\text{points}_i = (x_i - a_i + 1) \times (y_i - b_i + 1)$$

Once a rectangle is selected based on point weights, we pick a point $(x, y)$ uniformly at random within that rectangle.

Instead of scanning all rectangles linearly on every query (which would be $\mathcal{O}(N)$ per pick), we compute a **Prefix Sum array** of integer point counts during initialization. This allows us to sample the target rectangle in **$\mathcal{O}(\log N)$** time using **Binary Search**.

---

## Algorithm

1. **Initialization (`Solution(rects)`)**:
   - Calculate the number of integer points in each rectangle: $\text{pts}_i = (x_i - a_i + 1) \times (y_i - b_i + 1)$.
   - Build a prefix sum array `pref` where `pref[i]` stores the total cumulative points up to rectangle $i$.
   - Store the grand total number of integer points $\text{totalPoints} = \text{pref}[N - 1]$.

2. **Picking a Point (`pick()`)**:
   - Generate a random integer `target` in the range $[0, \text{totalPoints} - 1]$.
   - Binary search over `pref` to find the smallest index `idx` such that `target < pref[idx]`. This selects rectangle `rects[idx]` with probability proportional to its point count.
   - For `rects[idx] = [a, b, x, y]`:
     - Select $X = a + \text{random.nextInt}(x - a + 1)$
     - Select $Y = b + \text{random.nextInt}(y - b + 1)$
   - Return $[X, Y]$.

---

## Complexity Analysis

### Time Complexity:
- **Constructor `Solution(rects)`**: $\mathcal{O}(N)$ where $N$ is the number of rectangles. We perform a single linear scan over `rects` to compute prefix sums.
- **`pick()`**: $\mathcal{O}(\log N)$ per call. We perform binary search over a sorted prefix sum array of size $N$, followed by $\mathcal{O}(1)$ random number generations.

### Space Complexity:
- **`Solution` Object**: $\mathcal{O}(N)$ to store the `rects` reference and the prefix sum array `pref` of size $N$.
