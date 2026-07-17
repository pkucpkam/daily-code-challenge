# 475. Heaters

## Intuition

The problem asks us to find the minimum radius of heaters required to warm all houses. 
Since the houses and heaters are given as arrays of positions, a natural way to simplify the problem is to sort both arrays. Once sorted, we can iterate through the houses from left to right and find the closest heater for each house.

For a house at position `house`, we want to find a heater that minimizes the distance `abs(heater - house)`. Because both the houses and heaters are sorted, we can use a two-pointer approach to efficiently find the closest heater for every house in a single pass.

## Two-Pointer Approach

We start with two pointers, one iterating through the `houses` and another `i` pointing to the current heater in `heaters` (initially `0`).
For each `house`, we check if the next heater at `i + 1` is closer to or at the same distance from the house compared to the current heater at `i`.
If it is, it means the next heater is a better (or equally good) choice, so we move our heater pointer `i` forward. We keep doing this until the current heater is strictly closer than the next heater.
The minimum radius needed for this specific `house` will be the distance to the closest heater we just found. To cover all houses, we need to take the maximum of these minimum radii.

## Algorithm

1. Sort the `houses` array in ascending order.
2. Sort the `heaters` array in ascending order.
3. Initialize a variable `i = 0` to point to the current heater, and `res = 0` to keep track of the maximum radius needed.
4. Iterate through each `house` in `houses`:
   - While `i < heaters.length - 1` and `abs(heaters[i + 1] - house) <= abs(heaters[i] - house)`:
     - Increment `i`. This moves to the next heater as long as it is closer to or at the same distance from the current house.
   - Update `res = max(res, abs(heaters[i] - house))`.
5. Return `res`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(N \log N + M \log M)$
- $N$ is the number of houses and $M$ is the number of heaters.
- Sorting the `houses` takes $\mathcal{O}(N \log N)$ time.
- Sorting the `heaters` takes $\mathcal{O}(M \log M)$ time.
- The two-pointer traversal takes $\mathcal{O}(N + M)$ time because the heater pointer `i` only moves forward and at most traverses the `heaters` array once across all iterations of the outer loop.
- Therefore, the total time complexity is bounded by the sorting step: $\mathcal{O}(N \log N + M \log M)$.

### Space Complexity: $\mathcal{O}(\log N + \log M)$ or $\mathcal{O}(N + M)$
- Depending on the language's built-in sorting algorithm, sorting may require $\mathcal{O}(\log N + \log M)$ stack space (for QuickSort) or $\mathcal{O}(N + M)$ space. For Java primitives, `Arrays.sort` uses Dual-Pivot Quicksort which takes $\mathcal{O}(\log N)$ extra space.
- Aside from the sorting space, we only use a few integer variables, so the auxiliary space is $\mathcal{O}(1)$.
