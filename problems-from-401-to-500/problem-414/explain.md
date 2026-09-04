# 567. Permutation in String

## Problem Understanding

Given two strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`, or `false` otherwise.

In other words, return `true` if one of `s1`'s permutations is a contiguous substring of `s2`.

---

## Key Insights & Mathematical Properties

### 1. Character Frequency Equivalence
Two strings are permutations of each other if and only if:
- They have identical lengths.
- The frequency of every character in both strings is identical.

### 2. Fixed-Size Sliding Window
Since any permutation of `s1` must have a length of $n_1 = \text{s1.length()}$, we only need to inspect contiguous substrings in `s2` of length $n_1$.

### 3. Match Tracking for $\mathcal{O}(1)$ Window Updates
Instead of re-comparing frequency arrays of size 26 at each step ($\mathcal{O}(26 \times n_2)$), we maintain a variable `matches` (ranging from $0$ to $26$):
- `matches` represents the number of alphabet characters (out of 26) whose count in the current window of `s2` equals its count in `s1`.
- When sliding the window by one position to the right:
  - Add character at the right edge $\rightarrow$ Update `s2Count` and `matches`.
  - Remove character at the left edge $\rightarrow$ Update `s2Count` and `matches`.
- If `matches == 26` at any point, a valid permutation of `s1` exists in `s2`.

---

## Optimal Approach: Fixed-Size Sliding Window with Match Counter

### Algorithm Steps:
1. **Early Return:** If `s1.length() > s2.length()`, return `false` immediately.
2. **Frequency Initialization:** Create two frequency arrays `s1Count` and `s2Count` of size 26. Populate counts for `s1` and the first window of `s2` (indices $0$ to $n_1 - 1$).
3. **Initial Match Calculation:** Count how many characters (0 through 25) have equal frequencies in `s1Count` and `s2Count`. Store in `matches`.
4. **Slide Window:** Iterate $i$ from $0$ to $n_2 - n_1 - 1$:
   - If `matches == 26`, return `true`.
   - Update counts for the incoming character at $i + n_1$ and outgoing character at $i$.
   - Dynamically adjust `matches` when counts match or diverge.
5. **Final Check:** Return `matches == 26`.

---

## Code Implementation

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // A permutation of s1 cannot exist in s2 if s1 is longer than s2
        if (n1 > n2) {
            return false;
        }

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        // Populate character frequencies for s1 and the first window of s2
        for (int i = 0; i < n1; i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }

        // Count how many of the 26 character counts match initially
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] == s2Count[i]) {
                matches++;
            }
        }

        // Slide the window across s2
        for (int i = 0; i < n2 - n1; i++) {
            if (matches == 26) {
                return true;
            }

            int right = s2.charAt(i + n1) - 'a';
            int left = s2.charAt(i) - 'a';

            // Include new character on the right of the window
            s2Count[right]++;
            if (s1Count[right] == s2Count[right]) {
                matches++;
            } else if (s1Count[right] + 1 == s2Count[right]) {
                matches--;
            }

            // Exclude old character on the left of the window
            s2Count[left]--;
            if (s1Count[left] == s2Count[left]) {
                matches++;
            } else if (s1Count[left] - 1 == s2Count[left]) {
                matches--;
            }
        }

        return matches == 26;
    }
}
```

---

## Step-by-Step Example Walkthrough

### Example 1: `s1 = "ab"`, `s2 = "eidbaooo"`

1. **Lengths:** $n_1 = 2$, $n_2 = 8$. Valid!
2. **Initial Counts ($i = 0..1$):**
   - `s1Count`: `a: 1`, `b: 1`, others `0`.
   - `s2Count` (window `"ei"`): `e: 1`, `i: 1`, others `0`.
   - `matches` calculation: 24 character counts match (`0 == 0`), `a`, `b`, `e`, `i` do not match. Initial `matches = 24`.

3. **Sliding Window:**
   - **$i = 0$** (Window `"ei"`): `matches` ($24$) $\neq 26$.
     - Incoming: `d` at index $2$. `s2Count[d]` goes from $0 \rightarrow 1$. `matches` drops to $23$.
     - Outgoing: `e` at index $0$. `s2Count[e]` goes from $1 \rightarrow 0$. `matches` increases to $24$.
   - **$i = 1$** (Window `"id"`): `matches` ($24$) $\neq 26$.
     - Incoming: `b` at index $3$. `s2Count[b]` goes from $0 \rightarrow 1$. `matches` increases to $25$.
     - Outgoing: `i` at index $1$. `s2Count[i]` goes from $1 \rightarrow 0$. `matches` increases to $26$.
   - **$i = 2$** (Window `"db"`): Check `matches == 26` $\rightarrow$ **True!**

4. **Result:** `true`

---

## Complexity Analysis

| Metric | Complexity | Explanation |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(n_1 + n_2)$ | We count character frequencies of `s1` in $\mathcal{O}(n_1)$ time and slide a fixed window across `s2` in $\mathcal{O}(n_2 - n_1)$ steps, performing $\mathcal{O}(1)$ updates per step. |
| **Space Complexity** | $\mathcal{O}(1)$ | Uses two fixed-size frequency arrays of size 26 for lowercase English letters. |

---

## Comparison of Approaches

| Approach | Time Complexity | Space Complexity | Notes |
|:---|:---|:---|:---|
| **Brute Force (Sort Substrings)** | $\mathcal{O}(n_1! \times n_2)$ | $\mathcal{O}(n_1)$ | Generates all permutations of `s1` and searches in `s2`. TLE for large strings. |
| **Naive Sliding Window (Array Compare)** | $\mathcal{O}(26 \times n_2)$ | $\mathcal{O}(1)$ | Compares array of size 26 at each window position. |
| **Optimized Sliding Window (Match Counter)** ✅ | $\mathcal{O}(n_1 + n_2)$ | $\mathcal{O}(1)$ | Single pass with $\mathcal{O}(1)$ incremental update per window slide. |
