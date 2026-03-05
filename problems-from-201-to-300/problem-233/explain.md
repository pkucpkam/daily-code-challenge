# Fraction to Recurring Decimal — Explanation

## Best Approach

- Use long-division simulation with a hash map: `remainder -> index in result string`.
- If a remainder repeats, the digits between first occurrence and current position form the recurring cycle, so wrap them with `(` and `)`.
- Convert `numerator` and `denominator` to `long` before `abs` to avoid overflow for `Integer.MIN_VALUE`.

## How It Works

- If `numerator == 0`, return `"0"`.
- Determine sign: result is negative only when exactly one of numerator/denominator is negative (`xor`).
- Append integer part: `dividend / divisor`.
- Start fractional part with `remainder = dividend % divisor`.
- For each step:
	- If remainder was seen before, insert `(` at stored index and append `)`, then stop.
	- Otherwise, store current result length for this remainder.
	- Multiply remainder by `10`, append next digit `remainder / divisor`, then update `remainder %= divisor`.
- If remainder becomes `0`, the decimal terminates (no parentheses).

## Why This Is Optimal

- Time: `O(k)`, where `k` is the number of produced decimal digits (until termination or cycle detection).
- Space: `O(k)` for stored remainders.
- This is optimal in practice and standard for this problem, because cycle detection in decimal expansion requires tracking previously seen remainders.

## Edge Cases Covered

- Zero numerator: `0 / x`.
- Negative results and correct sign placement.
- Large bounds including `-2^31` via `long` conversion.
- Non-repeating example: `1/2 -> 0.5`.
- Repeating example: `4/333 -> 0.(012)`.