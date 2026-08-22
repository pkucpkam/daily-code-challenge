# 551. Student Attendance Record I

## Intuition

The problem requires us to determine if a student is eligible for an attendance award based on their attendance record string `s`. 
The record consists of three characters:
- `'A'`: Absent
- `'L'`: Late
- `'P'`: Present

The student qualifies for an award **if and only if** both of the following conditions are met:
1. **Total Absents:** The student was absent (`'A'`) **strictly fewer than 2 days** in total (i.e., at most $1$ absent day).
2. **Consecutive Lates:** The student was **never late** (`'L'`) for **$3$ or more consecutive days** (i.e., no block of `"LLL"` exists).

---

## Key Insights & Optimal Solution (Single Pass Iteration)

### Single-Pass Iteration

We can solve this problem in a single pass over the string while maintaining two counters:
- `absents`: tracks the total number of `'A'` characters encountered so far.
- `lates`: tracks the current sequence of consecutive `'L'` characters.

### State Transitions during Traversal
For each character $c$ in string $s$:
- **If $c == 'A'$:**
  - Increment `absents` by $1$.
  - If `absents >= 2`, return `false` immediately (Early Exit).
  - Reset `lates = 0` because the streak of consecutive lates is broken by an `'A'`.
- **If $c == 'L'$:**
  - Increment `lates` by $1$.
  - If `lates >= 3`, return `false` immediately (Early Exit).
- **If $c == 'P'$:**
  - Reset `lates = 0` because the streak of consecutive lates is broken by a `'P'`.

If the loop finishes without triggering any disqualification condition, return `true`.

---

## Code Implementation (Java)

```java
class Solution {
    public boolean checkRecord(String s) {
        int absents = 0;
        int lates = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == 'A') {
                absents++;
                if (absents >= 2) {
                    return false;
                }
                lates = 0; // Reset consecutive late streak
            } else if (c == 'L') {
                lates++;
                if (lates >= 3) {
                    return false;
                }
            } else {
                lates = 0; // Reset consecutive late streak
            }
        }

        return true;
    }
}
```

---

## Walkthrough Example

### Example 1: `s = "PPALLP"`

- `i = 0`, $c =$ `'P'`: `absents = 0`, `lates = 0`
- `i = 1`, $c =$ `'P'`: `absents = 0`, `lates = 0`
- `i = 2`, $c =$ `'A'`: `absents = 1`, `lates = 0` (`absents < 2` $\rightarrow$ OK)
- `i = 3`, $c =$ `'L'`: `absents = 1`, `lates = 1` (`lates < 3` $\rightarrow$ OK)
- `i = 4`, $c =$ `'L'`: `absents = 1`, `lates = 2` (`lates < 3` $\rightarrow$ OK)
- `i = 5`, $c =$ `'P'`: `absents = 1`, `lates = 0`

Result: `true`

### Example 2: `s = "PPALLL"`

- `i = 0`, $c =$ `'P'`: `absents = 0`, `lates = 0`
- `i = 1`, $c =$ `'P'`: `absents = 0`, `lates = 0`
- `i = 2`, $c =$ `'A'`: `absents = 1`, `lates = 0`
- `i = 3`, $c =$ `'L'`: `absents = 1`, `lates = 1`
- `i = 4`, $c =$ `'L'`: `absents = 1`, `lates = 2`
- `i = 5`, $c =$ `'L'`: `absents = 1`, `lates = 3` $\rightarrow$ `lates >= 3`, return `false` immediately!

Result: `false`

---

## Alternative Approaches

### 1. Built-In String Search Methods (Concise One-Liner)

We can express the rules directly using Java string methods:
- `s.indexOf("A") == s.lastIndexOf("A")` ensures that `'A'` appears at most once in `s`.
- `!s.contains("LLL")` ensures there is no substring of 3 consecutive `'L'`s.

```java
class Solution {
    public boolean checkRecord(String s) {
        return s.indexOf("A") == s.lastIndexOf("A") && !s.contains("LLL");
    }
}
```

- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$

### 2. Regular Expressions (Regex)

Using regular expressions to match valid strings:
- `!s.matches(".*(A.*A|LLL).*")` guarantees fewer than 2 `'A'`s and no 3 consecutive `'L'`s.

```java
class Solution {
    public boolean checkRecord(String s) {
        return !s.matches(".*(A.*A|LLL).*");
    }
}
```

- **Time Complexity:** $\mathcal{O}(N)$ with regex engine overhead.
- **Space Complexity:** $\mathcal{O}(N)$ for pattern compilation & NFA matching.

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Notes |
|:---|:---|:---|:---|
| **Single Pass (Recommended)** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Optimal, supports early exits, zero string allocation |
| **String Built-in Methods** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Very concise, easy to read |
| **Regex Match** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Higher constant overhead |

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the length of string `s`. We iterate through the string at most once.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary memory since only two integer counters are stored.

