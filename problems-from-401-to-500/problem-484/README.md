# 678. Valid Parenthesis String

**Difficulty:** Medium 🟡  
**Topics:** Greedy, String  
**Companies:** premium lock icon  
**Hint:**  

---

## Problem Statement

Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.

The following rules define a valid string:

- Any left parenthesis '(' must have a corresponding right parenthesis ')'.
- Any right parenthesis ')' must have a corresponding left parenthesis '('.
- Left parenthesis '(' must go before the corresponding right parenthesis ')'.
- '*' could be treated as a single right parenthesis ')', a single left parenthesis '(', or an empty string "".

---

## Examples

### Example 1

```text
Input: s = "()"
Output: true
```

### Example 2

```text
Input: s = "(*)"
Output: true
```

### Example 3

```text
Input: s = "(*))"
Output: true
```

### Example 4

```text
Input: s = "("
Output: false
```

---

## Constraints

- `1 <= s.length <= 100`
- `s[i]` is `'('`, `')'`, or `'*'`.

---

## Solution

### Greedy Range

After processing each character, keep a range of possible values for the
number of unmatched left parentheses:

- `minOpen` is the smallest possible count.
- `maxOpen` is the largest possible count.

For each character:

- `'('` increases both bounds.
- `')'` decreases both bounds.
- `'*'` can be `'('`, `')'`, or empty, so it decreases `minOpen` and
  increases `maxOpen`.

If `maxOpen` becomes negative, there are more closing parentheses than any
possible interpretation can match, so the string is invalid. Since `minOpen`
cannot be negative, clamp it to zero after each step.

At the end, the string is valid if zero unmatched left parentheses is possible,
which is exactly `minOpen == 0`.

### Correctness

The range always contains every possible number of unmatched left parentheses
after the processed prefix. Each transition considers all valid
interpretations of the current character, so the invariant is preserved.
If `maxOpen < 0`, no interpretation can match the closing parentheses. If
`minOpen == 0` at the end, at least one interpretation produces a balanced
string.

### Complexity

- **Time:** `O(n)`
- **Space:** `O(1)`