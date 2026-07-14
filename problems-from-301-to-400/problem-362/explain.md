# Implement Rand10() Using Rand7()

## Intuition

The problem asks us to implement a function `rand10()` that generates a uniform random integer from 1 to 10 using only the provided `rand7()` function, which generates a uniform random integer from 1 to 7.

This is a classic problem of Rejection Sampling. The core idea is to use the given random generator to create a larger uniform range and then sample from it. If the generated number falls within a desired range that maps evenly to our target, we accept it. Otherwise, we reject it and try again.

To generate a larger range, we can call `rand7()` twice. We can think of the first call as determining the row and the second call as determining the column of a $7 \times 7$ matrix.
`idx = (rand7() - 1) * 7 + rand7()` will generate a uniform random integer in the range `[1, 49]`.

Since we want numbers from 1 to 10, we can use the numbers from 1 to 40. There are exactly 40 numbers, so each number from 1 to 10 corresponds to exactly 4 distinct outcomes (e.g., 1, 11, 21, 31 map to 1). Thus, if the generated number `idx` is $\le 40$, we can return `1 + (idx - 1) % 10`. If `idx > 40`, we reject it and try again.

## Optimization

We can further optimize this to minimize the expected number of calls to `rand7()`. When `idx > 40`, we have 9 numbers (from 41 to 49). Instead of just throwing them away, we can reuse this entropy.
1. `idx - 40` gives a uniform random number from 1 to 9.
2. We call `rand7()` again.
3. We generate a new `idx = (idx - 40 - 1) * 7 + rand7()`, which gives a number from 1 to $9 \times 7 = 63$.
4. We can use the numbers from 1 to 60. If `idx <= 60`, return `1 + (idx - 1) % 10`.
5. If `idx > 60`, we have 3 numbers (61 to 63). We can reuse this entropy too!
6. `idx - 60` gives a uniform random number from 1 to 3.
7. We call `rand7()` again.
8. We generate a new `idx = (idx - 60 - 1) * 7 + rand7()`, which gives a number from 1 to $3 \times 7 = 21$.
9. We use numbers from 1 to 20. If `idx <= 20`, return `1 + (idx - 1) % 10`.
10. If `idx == 21`, we just reject it and restart the whole process from the beginning.

## Algorithm

1. Enter an infinite loop (`while (true)`).
2. Generate `row = rand7()` and `col = rand7()`. Compute `idx = (row - 1) * 7 + col` (range 1-49).
3. If `idx <= 40`, return `1 + (idx - 1) % 10`.
4. If `idx > 40`, set `row = idx - 40` (range 1-9) and generate a new `col = rand7()`. Compute `idx = (row - 1) * 7 + col` (range 1-63).
5. If `idx <= 60`, return `1 + (idx - 1) % 10`.
6. If `idx > 60`, set `row = idx - 60` (range 1-3) and generate a new `col = rand7()`. Compute `idx = (row - 1) * 7 + col` (range 1-21).
7. If `idx <= 20`, return `1 + (idx - 1) % 10`.
8. If none of the conditions match, continue the loop and start over.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(1)$ expected, $\mathcal{O}(\infty)$ worst-case
- The probability of getting a valid number in the first attempt is $40/49$. 
- With the optimization, the probability of rejecting and starting completely over is just $1 / (49 \times 63 \times 21)$, which is incredibly small. The expected number of calls to `rand7()` is approximately 2.193, making the expected time complexity $\mathcal{O}(1)$. The worst-case is technically infinite if we keep hitting the rejected numbers, but the probability of this is $0$.

### Space Complexity: $\mathcal{O}(1)$
- We only use a few integer variables (`row`, `col`, `idx`), so the space complexity is $\mathcal{O}(1)$.
