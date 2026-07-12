# Unique Substrings in Wraparound String

## Intuition

The problem asks us to find the number of unique, non-empty substrings of `s` that are also substrings of the infinite wraparound string `base` ("abcdefghijklmnopqrstuvwxyz..."). 

A substring of `s` is present in `base` if and only if it consists of consecutive letters in the alphabet. For instance, "ab", "bc", "za" are valid, but "ac" is not.

To avoid counting duplicate substrings, we can observe a property of substrings:
If we know the maximum length of a valid substring ending with a specific character, let's say 'd', and that maximum length is 4 (e.g., "abcd"), then we also inherently have valid substrings of lengths 3, 2, and 1 ending at 'd' (which are "bcd", "cd", and "d"). The number of unique substrings ending at 'd' is exactly equal to the length of the longest valid substring ending at 'd'.

By finding the maximum length of valid substrings ending at each of the 26 lowercase English letters, we can simply sum these maximum lengths to find the total number of unique valid substrings. This approach guarantees that we won't double-count identical substrings because we uniquely identify substrings by their ending character and their maximum length.

## Algorithm

1. Initialize an array `dp` of size 26 to store the maximum length of valid contiguous substrings ending with each lowercase letter.
2. Initialize `maxLength` to 0 to keep track of the current consecutive sequence length.
3. Iterate through each character in the string `s` (using an index `i`):
   - Check if the current character `s[i]` is consecutive to the previous character `s[i-1]`.
     - A character is consecutive if `s[i] - s[i-1] == 1` or if `s[i-1] == 'z'` and `s[i] == 'a'`.
   - If it is consecutive, increment `maxLength` by 1.
   - If it is not consecutive, reset `maxLength` to 1, since the current character forms a new valid substring of length 1 by itself.
   - Update `dp[s[i] - 'a']` to be the maximum of its current value and `maxLength`.
4. After traversing the string, sum all the values in the `dp` array.
5. Return the sum.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(N)$
- We iterate through the string `s` of length $N$ exactly once.
- Inside the loop, all operations (character comparison, array access, and `Math.max`) are performed in $\mathcal{O}(1)$ time.
- The final summation over the `dp` array takes $\mathcal{O}(1)$ time since the array size is always 26.
- Therefore, the overall time complexity is $\mathcal{O}(N)$.

### Space Complexity: $\mathcal{O}(1)$
- We use an integer array `dp` of size 26. Since the size is constant and does not depend on the input string length $N$, the space complexity is strictly $\mathcal{O}(1)$.
- Only a few extra variables (`maxLength`, `totalSubstrings`) are used, which also take constant space.
