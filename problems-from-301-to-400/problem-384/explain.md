# 524. Longest Word in Dictionary through Deleting

## Intuition

We are given a target string `s` and a `dictionary` of words. We need to find a word from `dictionary` such that:
1. The word can be formed by deleting characters from `s` (i.e., the word is a **subsequence** of `s`).
2. Among all valid words, we select the **longest** word.
3. If there is a tie in length, we pick the word that is **lexicographically smallest**.

Since the constraints on $N$ (dictionary length) and $L$ (word length) are small ($\le 1000$), we can iterate through each word in the dictionary, check if it is a valid subsequence of `s`, and keep track of the best candidate word matching our criteria.

---

## Key Insights & Algorithm

### Approach: Two-Pointer Subsequence Verification with Filtering

Instead of sorting the entire dictionary (which incurs extra $\mathcal{O}(D \log D \cdot L)$ time overhead), we can scan the dictionary in a single pass while tracking the best word `longest`.

#### 1. Optimization - Pre-Filter Words:
Before calling the two-pointer subsequence check on a word `w`, compare `w` with our current best `longest`:
- If `w.length() > longest.length()`: `w` is a better candidate by length.
- If `w.length() == longest.length()` and `w.compareTo(longest) < 0`: `w` is equal in length but lexicographically smaller.
- Otherwise, `w` cannot improve our result, so we skip checking `w` entirely.

#### 2. Subsequence Check (`isSubsequence(s, word)`):
To check if `word` is a subsequence of `s`:
- Maintain two pointers: `i` for `s` and `j` for `word`.
- Traverse `s`. Whenever `s.charAt(i) == word.charAt(j)`, advance pointer `j`.
- If `j` reaches `word.length()`, then all characters of `word` were matched in order within `s`, returning `true`.
- If `s` ends before `j` reaches `word.length()`, return `false`.

---

## Walkthrough

Consider `s = "abpcplea"`, `dictionary = ["ale", "apple", "monkey", "plea"]`:

| Word | Candidate Check | Subsequence Check vs `"abpcplea"` | Match? | Current Best `longest` |
| :--- | :--- | :--- | :--- | :--- |
| **Start** | — | — | — | `""` |
| `"ale"` | `len(3) > len(0)` | Matches `a..p..c..l..e..` $\rightarrow$ `j` matches `"ale"` | ✅ Yes | `"ale"` |
| `"apple"` | `len(5) > len(3)` | Matches `a..b..p..c..p..l..e..` $\rightarrow$ matches `"apple"` | ✅ Yes | `"apple"` |
| `"monkey"` | `len(6) > len(5)` | Missing `'m'` in `"abpcplea"` | ❌ No | `"apple"` |
| `"plea"` | `len(4) < len(5)` | Skipped (cannot beat length 5) | — | `"apple"` |

**Final Output:** `"apple"`

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(D \cdot (S + L))$
  - Where $D$ is the number of words in the dictionary ($D \le 1000$), $S$ is the length of string `s` ($S \le 1000$), and $L$ is the maximum word length ($L \le 1000$).
  - For each word, comparing string lengths / lexicographical order takes $\mathcal{O}(L)$, and the two-pointer scan takes at most $\mathcal{O}(S)$ steps.
  - In practice, skipping non-candidate words makes it even faster.

- **Space Complexity:** $\mathcal{O}(1)$
  - Uses only a few primitive integer pointers for two-pointer tracking and a single string reference for `longest`. No extra data structures or memory allocations required.

