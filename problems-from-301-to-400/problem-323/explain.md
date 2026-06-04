# 395. Longest Substring with At Least K Repeating Characters - Explanation

## Approach 1: Divide and Conquer (Recommended)

### Intuition

The problem requires us to find the longest substring where every character appears at least $k$ times. 

If we count the occurrences of all characters in the string `s`:
- If a character $c$ appears **fewer than $k$ times** in the entire string, then $c$ **cannot be part of any valid substring**.
- Therefore, the character $c$ acts as a **splitter/delimiter**. Any valid substring must lie either completely to the left or completely to the right of $c$.

This suggests a natural **Divide and Conquer** approach:
1. Count the frequency of each character in the current substring `s[start...end]`.
2. Find the first character $c$ whose frequency is less than $k$.
3. If no such character exists, the entire substring `s[start...end]` is valid, so we return its length (`end - start`).
4. If such a character $c$ is found at index `i`, we split the substring into two parts:
   - Left part: `s[start...i]`
   - Right part: `s[nextStart...end]` (where we skip any contiguous invalid splitters).
5. Recursively solve for both parts and return the maximum of their results.

---

### Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$ on average.
  - In the worst case, at each step we split the string into parts. Since the alphabet size is constant (at most 26 lowercase English letters), the recursion depth is at most 26.
  - At each recursion level, we scan the substring of length $L$ to count frequencies, which takes $\mathcal{O}(L)$ time.
  - Thus, the total time complexity is bounded by $26 \times \mathcal{O}(N) = \mathcal{O}(N)$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.
  - The recursion stack has a maximum depth of 26.
  - The frequency array takes a constant size of 26 elements.

---

### Trace Example: `s = "ababbc"`, `k = 2`

1. **`helper(s, 0, 6)`** (representing `"ababbc"`):
   - Frequency counts: `a` = 2, `b` = 3, `c` = 1.
   - We scan and find that `'c'` at index 5 has frequency $1 < 2$.
   - Split at index 5:
     - Left branch: `helper(s, 0, 5)` (representing `"ababb"`)
     - Right branch: `helper(s, 6, 6)` (representing `""`) -> returns 0 immediately because length < $k$.
2. **`helper(s, 0, 5)`** (representing `"ababb"`):
   - Frequency counts: `a` = 2, `b` = 3.
   - No characters have frequency $< 2$.
   - Returns length: $5 - 0 = 5$.
3. **Result:** `Math.max(5, 0) = 5`.

---

## Approach 2: Sliding Window (Alternative)

### Intuition

Normally, we cannot use a simple sliding window because the condition "at least $k$ times" does not have a monotonic property (adding a character can make an invalid window valid). 

However, we can force monotonicity by introducing a constraint: **the number of unique characters in the window must be exactly $U$** (where $1 \le U \le 26$).

For a fixed target $U$:
1. We slide a window `[left, right]` and maintain:
   - `uniqueCount`: the number of unique characters in the window.
   - `countAtLeastK`: the number of unique characters in the window with frequency $\ge k$.
2. If `uniqueCount <= U`, we expand the window by moving `right` and updating counts.
3. If `uniqueCount > U`, we shrink the window by moving `left` until `uniqueCount <= U`.
4. Whenever `uniqueCount == U` and `uniqueCount == countAtLeastK`, the window is valid, and we update the maximum length.

By looping $U$ from 1 to 26, we find the global maximum length.

### Complexity Analysis

- **Time Complexity:** $\mathcal{O}(26 \cdot N) = \mathcal{O}(N)$ since we run the sliding window exactly 26 times.
- **Space Complexity:** $\mathcal{O}(1)$ as we only store counters and frequency maps of size 26.
