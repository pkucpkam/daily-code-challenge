# 521. Longest Uncommon Subsequence I

## Intuition

The problem asks for the length of the longest uncommon subsequence between two strings `a` and `b`. An uncommon subsequence is a subsequence of exactly one of the strings.

At first glance, this problem seems like it might require a complex dynamic programming approach similar to the Longest Common Subsequence problem. However, there's a very simple logical trick:

1. **If the two strings are identical (`a.equals(b)`):** Every subsequence of `a` is also a subsequence of `b` (and vice-versa). Therefore, there cannot be any uncommon subsequence. The answer is `-1`.
2. **If the two strings are different lengths:** The longer string is a subsequence of itself, but it *cannot* possibly be a subsequence of the shorter string (because it has more characters). Therefore, the longer string itself is the longest uncommon subsequence. Its length is `Math.max(a.length(), b.length())`.
3. **If the two strings are the same length but not identical:** The string `a` is a subsequence of itself. Because it has the exact same length as `b` but is not identical to `b`, `a` cannot be formed by deleting characters from `b`. Thus, `a` (or `b`) itself is an uncommon subsequence. Its length is `a.length()`.

Combining cases 2 and 3: if the strings are not identical, the length of the longest uncommon subsequence is always the maximum of their lengths.

---

## Key Insights & Algorithm

### Approach: Simple String Comparison

1. Check if string `a` is equal to string `b`.
2. If they are equal, return `-1`.
3. If they are not equal, return the length of the longer string using `Math.max(a.length(), b.length())`.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(\min(N, M))$ where $N$ and $M$ are the lengths of the strings. The string equality check `a.equals(b)` first checks if lengths are equal (which is $\mathcal{O}(1)$), and if so, compares characters up to length $N$. So the worst case is proportional to the string length.
- **Space Complexity:** $\mathcal{O}(1)$. We only use constant extra space for variables.
