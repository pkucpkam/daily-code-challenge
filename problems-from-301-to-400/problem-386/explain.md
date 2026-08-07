# 526. Beautiful Arrangement

## Intuition

We are asked to find the number of "beautiful arrangements" of numbers from `1` to `n`. 
A permutation is beautiful if for every position `pos` (1-indexed), the number `num` at that position satisfies either:
- `num` is divisible by `pos` (`num % pos == 0`)
- `pos` is divisible by `num` (`pos % num == 0`)

Given the small constraint $n \le 15$, this problem is a perfect candidate for exploring all possibilities. While a simple backtracking approach (trying all valid placements) works very well, we can optimize it using **Dynamic Programming with Bitmask** to solve it even more elegantly.

---

## Key Insights & Algorithm

### Dynamic Programming with Bitmask

1. **State Representation**:
   - We use an integer `mask` to represent the subset of numbers from `1` to `n` that have already been placed. 
   - The $i$-th bit of `mask` is set to `1` if the number `i + 1` has been used.
   - For example, if `n = 4` and `mask = 0101` (in binary), it means numbers `1` and `3` have been placed.

2. **Determining the Position**:
   - In a permutation, if we have placed $k$ numbers, the next number we place will be at position $k + 1$.
   - The number of placed elements is simply the number of `1`s in the `mask` (which we can get using `Integer.bitCount(mask)`).
   - Therefore, `pos = Integer.bitCount(mask) + 1`.

3. **DP Transition**:
   - Let `dp[mask]` be the number of valid beautiful arrangements using the subset of numbers represented by `mask`.
   - Base case: `dp[0] = 1` (1 way to arrange an empty set).
   - We iterate through all possible `mask`s from `0` to `2^n - 1`. For each `mask`, we try to place an unused number `num = i + 1` at the current `pos`.
   - If `num` satisfies the divisibility condition (`num % pos == 0 || pos % num == 0`), we add the ways from `dp[mask]` to `dp[mask | (1 << i)]`.

4. **Final Answer**:
   - After computing all states, the answer is `dp[(1 << n) - 1]`, which represents the number of valid arrangements using all $n$ numbers.

---

## Walkthrough

Consider `n = 2`:
- We have 2 numbers: `1, 2`. `1 << 2 = 4`, so `mask` goes from `0` to `3`.
- `dp[0] = 1`.

- **`mask = 0 (00)`**: `pos = 1`. Unused numbers: `1, 2`.
  - Try `num = 1`: `1 % 1 == 0` (Valid). `dp[1] += dp[0]` $\rightarrow$ `dp[1] = 1`.
  - Try `num = 2`: `2 % 1 == 0` (Valid). `dp[2] += dp[0]` $\rightarrow$ `dp[2] = 1`.

- **`mask = 1 (01)`**: `pos = 2`. Placed `1`. Unused: `2`.
  - Try `num = 2`: `2 % 2 == 0` (Valid). `dp[3] += dp[1]` $\rightarrow$ `dp[3] = 1`.

- **`mask = 2 (10)`**: `pos = 2`. Placed `2`. Unused: `1`.
  - Try `num = 1`: `2 % 1 == 0` (Valid). `dp[3] += dp[2]` $\rightarrow$ `dp[3] = 2`.

- **`mask = 3 (11)`**: `pos = 3`. All placed. Loop ends.

**Result:** `dp[3] = 2`. The two arrangements are `[1,2]` and `[2,1]`.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(n \cdot 2^n)$
  - There are $2^n$ possible states (masks). For each state, we iterate $n$ times to find an unused number.
  - Since $n \le 15$, $15 \cdot 2^{15} = 491,520$ operations, which executes in a few milliseconds.
- **Space Complexity:** $\mathcal{O}(2^n)$
  - We use an array `dp` of size $2^n$ to store the number of valid arrangements for each mask.
  - $2^{15} = 32,768$ integers, which is extremely lightweight.
