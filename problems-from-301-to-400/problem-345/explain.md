# Sliding Window Approach: O(N) Time

## Intuition

The problem asks us to find all start indices of `p`'s anagrams in `s`. An anagram is a string formed by rearranging the letters of a different word or phrase, using all the original letters exactly once. This means two strings are anagrams if they have the exact same character frequencies.

Since we are looking for substrings in `s` of the exact same length as `p`, this naturally suggests a **Sliding Window** approach.

We can maintain a frequency map (or an array of size 26, since the strings only contain lowercase English letters) for the characters in `p`. As we iterate through `s`, we maintain a sliding window of size equal to `p.length()`. For each character entering the window, we update our window's character frequencies. For each character leaving the window, we also update the frequencies. At each step, if the window's frequency matches `p`'s frequency, we've found an anagram.

## Algorithm

1. **Edge Cases**: If `s` is shorter than `p`, it's impossible to find any anagrams, so we return an empty list immediately.
2. **Frequency Array**: Create an integer array `hash` of size 26 to store the character counts for string `p`.
3. **Sliding Window**:
   - Use two pointers, `left` and `right`, both starting at 0.
   - We maintain a `count` variable initialized to `p.length()`, which represents the number of characters we still need to match.
   - As `right` moves forward, we decrement the frequency of `s.charAt(right)` in our `hash`. If the frequency was `>= 1` (meaning it's a character we actually need), we decrement `count`.
   - When `count == 0`, we have successfully matched all characters in `p`. The substring in our window is an anagram! We add `left` to our result list.
   - We need to maintain a fixed window size of `p.length()`. When the window size (`right - left`) reaches `p.length()`, we must slide the `left` pointer forward to make room for the next character. 
   - As `left` moves forward, we increment the frequency of `s.charAt(left)` in our `hash`. If the frequency becomes `>= 1` (meaning we just removed a character that was part of `p`), we must increment `count` because we now need to match this character again.
4. **Return** the list of starting indices.

## Complexity

- **Time Complexity:** `O(N)`, where `N` is the length of string `s`. We traverse the string `s` exactly once with the `right` pointer, and the `left` pointer also moves at most `N` times. All operations inside the loop are `O(1)`.
- **Space Complexity:** `O(1)`. The `hash` array requires constant space `O(26)` regardless of the input size.
