# Explanation of the Optimal Solution (O(N) Time, O(1) Space)

A brute force solution would calculate `F(k)` for each `k` from `0` to `n-1`, resulting in an $O(N^2)$ time complexity. This will cause a Time Limit Exceeded (TLE) for large inputs.

We can optimize this to $O(N)$ by finding a mathematical relationship between $F(k)$ and $F(k-1)$.

## Mathematical Deduction

Let the array be `A = [a, b, c, d]` with size `n = 4`.

- `F(0) = 0*a + 1*b + 2*c + 3*d`
- `F(1) = 0*d + 1*a + 2*b + 3*c`
- `F(2) = 0*c + 1*d + 2*a + 3*b`
- `F(3) = 0*b + 1*c + 2*d + 3*a`

Let's compute the difference `F(1) - F(0)`:
`F(1) - F(0) = (1*a + 2*b + 3*c) - (1*b + 2*c + 3*d)`
`F(1) - F(0) = a + b + c - 3*d`

We can rewrite this by adding and subtracting `d`:
`F(1) - F(0) = a + b + c + d - 4*d`
`F(1) - F(0) = sum(A) - n * A[3]`

Similarly, let's look at `F(2) - F(1)`:
`F(2) - F(1) = (1*d + 2*a + 3*b) - (1*a + 2*b + 3*c)`
`F(2) - F(1) = d + a + b - 3*c`
`F(2) - F(1) = a + b + c + d - 4*c`
`F(2) - F(1) = sum(A) - n * A[2]`

### General Formula

From the pattern above, we can deduce:

**$F(k) = F(k-1) + \text{sum} - n \cdot A[n-k]$**

## Algorithm

1. Calculate the total sum of the array (`sum`) and the initial value `F(0)` (`f`).
2. Initialize `maxF` with the value of `F(0)`.
3. Loop from `k = 1` to `n - 1`, and for each step:
   - Calculate `F(k)` using the general formula: `f = f + sum - n * A[n - k]`.
   - Update `maxF` with the maximum value between the current `maxF` and the newly calculated `f`.
4. Return `maxF`.

## Complexity

- **Time Complexity:** $O(N)$ because we make two passes over the array of size $N$. One pass to calculate the initial `sum` and `F(0)`, and a second pass to calculate `F(1)` to `F(n-1)`.
- **Space Complexity:** $O(1)$ because we only use a few extra variables for the computation. 

*Note: We use `long` to prevent integer overflow during the intermediate calculations.*
