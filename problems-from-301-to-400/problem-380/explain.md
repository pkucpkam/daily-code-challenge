# 519. Random Flip Matrix

## Intuition

The problem requires us to randomly select and flip a `0` to a `1` in an $M \times N$ binary matrix. All `0`s must be equally likely to be selected. The matrix size can be up to $10^4 \times 10^4 = 10^8$. Due to this large size, creating an array or matrix to keep track of every element will result in a Memory Limit Exceeded (MLE). We need a sparse representation that uses memory proportional only to the number of flipped elements.

This problem is isomorphic to picking a random element from an array of size $K$ without replacement.

---

## Key Insights & Algorithm

### Approach: Hash Map Virtual Swapping (Fisher-Yates Shuffle)

We can conceptualize the $M \times N$ matrix as a 1D array of size $M \times N$. An element at `(i, j)` in the 2D matrix corresponds to the index `k = i * N + j` in the 1D array, where `0 <= k < M * N`. Similarly, an index `k` maps back to `[k / N, k % N]`.

To pick a random available cell without replacement efficiently:
1. Keep track of the number of available choices, `total`, initially set to $M \times N$.
2. When `flip()` is called, generate a random integer `r` uniformly distributed in `[0, total - 1]`.
3. The index `r` is the chosen cell. However, if `r` was previously picked, it would have been mapped to another available index. We use a Hash Map `map` to track these reassignments. The actual chosen index is `res = map.getOrDefault(r, r)`.
4. To efficiently remove this chosen index from future selections, we swap it with the last available index, which is at `total - 1`. We update the mapping for `r` to point to whatever `total - 1` was pointing to: `map.put(r, map.getOrDefault(total - 1, total - 1))`.
5. Decrement `total` by 1.
6. Return `[res / N, res % N]`.

For `reset()`, we simply restore `total = M * N` and clear the hash map.

---

## Complexity Analysis

- **Time Complexity:**
  - `Solution(int m, int n)`: $\mathcal{O}(1)$ time.
  - `flip()`: $\mathcal{O}(1)$ average time. Hash map operations (`get` and `put`) take $\mathcal{O}(1)$ on average. Random number generation takes $\mathcal{O}(1)$.
  - `reset()`: $\mathcal{O}(1)$ time. Clearing the hash map takes time proportional to its size, but bounded by the number of `flip` operations $K$ (at most $1000$).
- **Space Complexity:** $\mathcal{O}(K)$ where $K$ is the number of calls to `flip()`. The hash map stores at most one entry per `flip()` operation. Since $K \le 1000$, this easily fits in memory.
