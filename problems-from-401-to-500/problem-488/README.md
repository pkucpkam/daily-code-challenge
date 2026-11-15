# 686. Repeated String Match

**Difficulty:** Medium 🟡  
**Topics:** String, String Matching  

---

## Problem Statement

Given two strings `a` and `b`, return *the minimum number of times you should repeat string* `a` *so that string* `b` *is a substring of it*. If it is impossible for `b` to be a substring of `a` after repeating it, return `-1`.

**Notice:**
- String `"abc"` repeated $0$ times is `""`.
- Repeated $1$ time is `"abc"`.
- Repeated $2$ times is `"abcabc"`.

---

## Examples

### Example 1

```text
Input: a = "abcd", b = "cdabcdab"
Output: 3
```

**Explanation:** We return 3 because by repeating a three times `"abcdabcdabcd"`, b is a substring of it.

---

### Example 2

```text
Input: a = "a", b = "aa"
Output: 2
```

---

## Constraints

- `1 <= a.length, b.length <= 10⁴`
- `a` and `b` consist of lowercase English letters.
