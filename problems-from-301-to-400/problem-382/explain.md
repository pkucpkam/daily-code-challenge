# 522. Longest Uncommon Subsequence II

## Intuition

This is the array version of the problem. We need to find the **longest** string in `strs` that is **not** a subsequence of any other string in `strs`.

The key insight is:

> A string is a valid "uncommon subsequence" if it is **not a subsequence** of any other string in the array.

Why? Because every string is a subsequence of itself. So a string `s` is uncommon if and only if no **other** string `t` in the array has `s` as its subsequence (which would mean `s` is "shared"). The longest such string wins.

---

## Key Insights & Algorithm

### Approach: Brute-Force Subsequence Check

For each string `strs[i]`, check against every other string `strs[j]` (`i ≠ j`) to see if `strs[i]` is a subsequence of `strs[j]`.

- If `strs[i]` is **not** a subsequence of **any** other string → it's uncommon → candidate for the answer.
- Track the maximum length among all uncommon candidates.

#### Steps:
1. Iterate over each string `strs[i]`.
2. For each `strs[i]`, use a nested loop to check every `strs[j]` where `j ≠ i`.
3. Use a two-pointer `isSubsequence(s, t)` helper to check if `s` is a subsequence of `t`.
4. If no `strs[j]` contains `strs[i]` as a subsequence, update `result` with `strs[i].length()`.
5. Return `result` (or `-1` if no uncommon subsequence found).

#### Why not just check for duplicates?
If two strings are identical, each is a subsequence of the other, so neither can be uncommon — they cancel each other out. The `isSubsequence` check naturally handles this case.

---

## Walkthrough

```
strs = ["aba", "cdc", "eae"]
```

| String  | Is subsequence of any other? | Uncommon? |
|---------|------------------------------|-----------|
| `"aba"` | No (`"cdc"`, `"eae"` don't contain it) | ✅ Yes, length = 3 |
| `"cdc"` | No | ✅ Yes, length = 3 |
| `"eae"` | No | ✅ Yes, length = 3 |

Result: `3` ✓

---

```
strs = ["aaa", "aaa", "aa"]
```

| String  | Is subsequence of any other? | Uncommon? |
|---------|------------------------------|-----------|
| `"aaa"` | Yes — subsequence of the other `"aaa"` | ❌ No |
| `"aaa"` | Yes — subsequence of the other `"aaa"` | ❌ No |
| `"aa"`  | Yes — subsequence of `"aaa"` | ❌ No |

Result: `-1` ✓

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N^2 \cdot L)$ where $N$ is the number of strings and $L$ is the maximum string length. We compare each pair with a linear subsequence check.
- **Space Complexity:** $\mathcal{O}(1)$. Only a few integer pointers are used; no extra data structures.
