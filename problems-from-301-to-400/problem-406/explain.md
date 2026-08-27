# 557. Reverse Words in a String III

## Intuition

The problem asks us to reverse the characters of each individual word in a sentence while preserving word order and spaces.

Since words are separated by spaces, we can identify each word by searching for spaces (or the end of the string). Reversing a word in-place using two pointers (left and right) is simple and efficient. By converting the string into a character array, we can perform all character reversals directly in memory before constructing the final result string.

---

## Key Insights & Algorithmic Strategy

### 1. In-Place Character Array Reversal (Two Pointers)
Strings in Java are immutable. Therefore, converting `s` to a character array `char[] chars = s.toCharArray()` allows us to manipulate individual characters efficiently without repeatedly allocating new string objects.

### 2. Identifying Word Boundaries
We iterate through the array using an `end` index from `0` to `n` (where `n = chars.length`).
- A word boundary is reached when `end == n` (end of sentence) or `chars[end] == ' '` (space separator).
- When a boundary is detected, the current word spans from index `start` to `end - 1`.

### 3. Reversing the Substring
We reverse the characters between `start` and `end - 1` using a standard two-pointer swap technique:
- Increment `left` and decrement `right` until they meet or cross.

### 4. Updating the Next Word Start
After reversing the word, we update `start = end + 1` to mark the beginning of the next word.

---

## Code Implementation (Java)

```java
class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int start = 0;

        for (int end = 0; end <= n; end++) {
            if (end == n || chars[end] == ' ') {
                reverse(chars, start, end - 1);
                start = end + 1;
            }
        }

        return new String(chars);
    }

    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `s = "Let's take LeetCode contest"`

1. **Initial Array:** `['L', 'e', 't', '\'', 's', ' ', 't', 'a', 'k', 'e', ' ', ...]`
2. **Word 1 (`"Let's"`):**
   - `start = 0`. At `end = 5`, `chars[5] == ' '`.
   - Reverse from index `0` to `4` (`"Let's"` $\rightarrow$ `"s'teL"`).
   - Set `start = 6`.
3. **Word 2 (`"take"`):**
   - At `end = 10`, `chars[10] == ' '`.
   - Reverse from index `6` to `9` (`"take"` $\rightarrow$ `"ekat"`).
   - Set `start = 11`.
4. **Word 3 (`"LeetCode"`):**
   - At `end = 19`, `chars[19] == ' '`.
   - Reverse from index `11` to `18` (`"LeetCode"` $\rightarrow$ `"edoCteeL"`).
   - Set `start = 20`.
5. **Word 4 (`"contest"`):**
   - At `end = 27` (`end == n`).
   - Reverse from index `20` to `26` (`"contest"` $\rightarrow$ `"tsetnoc"`).
6. **Final String:** `"s'teL ekat edoCteeL tsetnoc"`.

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Recommendation |
|:---|:---|:---|:---|
| **String Split + StringBuilder** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Sub-optimal — creates multiple String objects |
| **Two Pointers on `char[]`** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | **Optimal** — single pass, minimal allocation overhead |

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of string $s$. We traverse each character once to find spaces and swap each character at most once during reversal.
- **Space Complexity:** $\mathcal{O}(N)$, to store the character array for modification since Java strings are immutable.

