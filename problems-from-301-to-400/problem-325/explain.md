# Integer Replacement - Solution Explanation

## Problem
Given a positive integer `n`, we want to find the minimum number of operations to reduce `n` to `1`.
- If `n` is even, we replace `n` with `n / 2`.
- If `n` is odd, we replace `n` with `n + 1` or `n - 1`.

## Approach: Greedy with Bitwise Logic
A brute-force approach (like BFS or recursion with memoization) works but takes $O(\log n)$ memory and might be slower. The optimal approach is a **Greedy Algorithm** which achieves $O(\log n)$ time and $O(1)$ space.

### Why Greedy?
When `n` is even, our only choice is to divide by 2. This is always optimal because it reduces the number by half in a single step.

When `n` is odd, we have to choose between `n + 1` and `n - 1`. Both operations will make the number even. Our goal is to choose the operation that will allow us to divide by 2 as many times as possible in the subsequent steps (i.e., we want to maximize the trailing zeros in its binary representation).

Let's look at the binary representation of `n`:
- Since `n` is odd, its last bit is `1`.
- We can check the last two bits using `n % 4` (or `n & 3`).
- **Case 1: `n % 4 == 1`** (e.g., ends in `01` in binary)
  If we subtract 1, the number ends in `00`, which means it can be divided by 2 at least twice in a row. If we add 1, it ends in `10`, which can only be divided by 2 once before becoming odd again. Therefore, we should **subtract 1**.
  *Example:* `5` (binary `101`). `5 - 1 = 4` (binary `100`, two divisions). `5 + 1 = 6` (binary `110`, one division).

- **Case 2: `n % 4 == 3`** (e.g., ends in `11` in binary)
  If we add 1, we flip all the consecutive trailing `1`s to `0`s (e.g., `...011 + 1 = ...100`), resulting in at least two trailing zeros, so we get at least two divisions by 2. If we subtract 1, it ends in `10`, giving only one division by 2. Therefore, we should **add 1**.
  *Example:* `7` (binary `111`). `7 + 1 = 8` (binary `1000`, three divisions). `7 - 1 = 6` (binary `110`, one division).

- **Special Case: `n == 3`**
  `3` ends in `11` (so `3 % 4 == 3`). According to the rule above, we should add 1 (making it `4`). But `4 -> 2 -> 1` takes 2 steps (total 3 operations). If we subtract 1 instead, `3 - 1 = 2`, and `2 -> 1` takes 1 step (total 2 operations). So for `n = 3`, it's strictly better to **subtract 1**.

### Handling Integer Overflow
The problem states that `n` can be up to `2^31 - 1` (which is `Integer.MAX_VALUE`). If `n = 2^31 - 1` (an odd number ending in 11) and we do `n + 1`, it will overflow the 32-bit signed integer and become negative (`-2147483648`). 
To prevent this, we cast `n` to a `long` variable at the beginning of our method before performing any operations.

## Complexity
- **Time Complexity:** $O(\log n)$. In the worst case, we do an addition/subtraction followed by a division by 2. The value decreases logarithmically.
- **Space Complexity:** $O(1)$. We only use a few variables for counting and storing the state, regardless of the size of $n$.
