# Longest Repeating Character Replacement - Explanation

## Problem Intuition

The problem asks for the longest substring of repeating characters, allowing at most `k` replacements. A substring is valid if the number of letters that are **different** from the most frequent letter in that substring is less than or equal to `k`.

In other words:
`Length of substring - frequency of most common character <= k`

This naturally points to a **Sliding Window** approach!

## Sliding Window Approach (O(N) Time, O(1) Space)

We can use two pointers, `left` and `right`, to represent our window. As we expand the window by moving `right`, we keep track of the frequencies of the characters currently in the window.

### Key Insights:

1. **Keep Track of Frequencies**: We use an array of size 26 (since there are only uppercase English letters) to count character occurrences in the current window.
2. **Track the Maximum Frequency**: We maintain a variable `maxFreq` to store the count of the most frequent character in the current window.
3. **Invalid Window Condition**: The window becomes invalid if the number of characters we would have to replace to make them all identical exceeds `k`. Mathematically: `(window_length) - maxFreq > k`.
4. **Shrink the Window**: When the window is invalid, we move the `left` pointer forward, decrementing the count of the character that leaves the window. 
   *(Note: We don't strictly need to update `maxFreq` when we shrink the window. Since we are looking for the **longest** valid substring, our answer will only increase when we find a window with a strictly greater `maxFreq`. It is a subtle but powerful optimization!)*

## Code Breakdown

```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0;
        int maxFreq = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Expand the window and update character frequency
            count[s.charAt(right) - 'A']++;
            
            // Update the historical maximum frequency in the window
            maxFreq = Math.max(maxFreq, count[s.charAt(right) - 'A']);
            
            // Check if window is invalid. If so, shrink from left.
            if ((right - left + 1) - maxFreq > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            
            // Record the max length seen so far
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

## Complexity Analysis

- **Time Complexity: $O(N)$** 
  Where $N$ is the length of the string. Both `left` and `right` pointers only move forward. The inner condition runs in $O(1)$ time, making the overall algorithm run in linear time.
- **Space Complexity: $O(1)$** 
  We use a fixed-size integer array of size 26 to store character frequencies. Space requirements do not grow with the input size.
