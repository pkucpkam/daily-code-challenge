# 309. Best Time to Buy and Sell Stock with Cooldown

**Difficulty:** Medium

## Problem

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i`th day.

Find the maximum profit you can achieve. You may complete as many transactions as you like, with the following restrictions:

- After you sell your stock, you cannot buy stock on the next day.
- You may not engage in multiple transactions simultaneously. You must sell the stock before you buy again.

## Examples

### Example 1

```text
Input: prices = [1,2,3,0,2]
Output: 3
Explanation: transactions = [buy, sell, cooldown, buy, sell]
```

### Example 2

```text
Input: prices = [1]
Output: 0
```

## Constraints

- `1 <= prices.length <= 5000`
- `0 <= prices[i] <= 1000`