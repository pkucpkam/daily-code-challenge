# 541. Reverse String II

## Intuition

The problem requires us to process a string in chunks of size `2k`. For every `2k` block of characters starting from index `0`, we must reverse the first `k` characters and leave the remaining `k` characters (if any) as they are.

If near the end of the string:
- Fewer than `k` characters remain: Reverse **all** remaining characters.
- Between `k` and `2k` characters remain: Reverse the first `k` characters and leave the rest unchanged.

---

## Key Insights & Algorithm

### 1. Block-Based Traversal

Instead of processing character by character and maintaining complex state counters, we can increment our loop pointer `i` by `2k` in each step (`i += 2 * k`). 

For each iteration starting at index `i`:
- The starting index for reversing is `start = i`.
- The ending index for reversing is `end = Math.min(i + k - 1, n - 1)`.

Using `Math.min(i + k - 1, n - 1)` automatically handles all edge cases:
- If `i + k - 1 < n`, there are at least `k` characters left, so we reverse exactly `k` characters ending at `i + k - 1`.
- If `i + k - 1 >= n`, there are fewer than `k` characters left, so we reverse up to the end of the string `n - 1`.

### 2. In-Place Reversal via Two-Pointer Technique

Because strings are immutable in Java, we convert `s` into a character array `char[] arr = s.toCharArray()`.

For the range `[start, end]`:
- Swap `arr[start]` and `arr[end]`.
- Increment `start` and decrement `end` until `start >= end`.

Finally, convert `arr` back into a `String` and return it.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(N)$
  - We iterate through the string in steps of `2k`. Each character is inspected and at most swapped once. Thus, total time spent is proportional to $N$, the length of string `s`.

- **Space Complexity**: $\mathcal{O}(N)$
  - In Java, strings are immutable, so converting `s` to `char[]` requires $\mathcal{O}(N)$ extra memory. The auxiliary space used for loop pointers is $\mathcal{O}(1)$.
