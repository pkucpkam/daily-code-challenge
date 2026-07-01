# HashMap Approach: O(n²) Time and O(n) Space

## Intuition

The problem asks us to find the number of tuples `(i, j, k)` such that the distance between point `i` and point `j` is equal to the distance between point `i` and point `k`. Since the order matters, `(i, j, k)` is distinct from `(i, k, j)`.

To solve this efficiently, we can consider each point `i` as the "pivot" (or the corner) of the boomerang. For a fixed point `i`, we can compute its distance to all other points `j` in the plane. If there are `k` points that are at the exact same distance from `i`, we can pick any two of them to form a boomerang. The number of ways to pick 2 ordered points from `k` points is simply the permutation `k * (k - 1)`.

By repeating this process for every possible point `i` as the pivot, we can accumulate the total number of boomerangs.

## Algorithm

1. Initialize a variable `boomerangs` to `0` to keep track of the total count.
2. Initialize a HashMap `map` to store the frequencies of each distance from the current pivot point `i`.
3. Iterate over each point `i` from `0` to `n - 1`:
   - Iterate over each point `j` from `0` to `n - 1`:
     - If `i == j`, skip this pair as a point's distance to itself is `0` and cannot form a valid boomerang leg.
     - Calculate the squared distance between `points[i]` and `points[j]`. We use squared distance to avoid floating-point inaccuracies and expensive square root operations.
     - Increment the count for this distance in the `map`.
   - Iterate over the values (frequencies) in the `map`. For each frequency `count`, add `count * (count - 1)` to `boomerangs`.
   - Clear the `map` for the next pivot point `i`.
4. Return the total `boomerangs`.

## Complexity

- **Time Complexity:** $\mathcal{O}(n^2)$ where $n$ is the number of points. For each of the $n$ points, we calculate its distance to the other $n - 1$ points, which takes $\mathcal{O}(n)$ time. Calculating the sum of `count * (count - 1)` takes at most $\mathcal{O}(n)$ time per pivot. The total time is thus $\mathcal{O}(n^2)$.
- **Space Complexity:** $\mathcal{O}(n)$. The HashMap stores the frequencies of distances for a single point. In the worst case, all other $n - 1$ points could be at different distances, resulting in at most $n$ entries in the map.
