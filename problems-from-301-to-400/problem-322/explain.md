# 394. Decode String - Explanation

## Approach: Two Stacks

The problem involves nested structures represented by square brackets `[]`. This naturally suggests a **Stack** data structure, which follows the Last-In-First-Out (LIFO) principle, perfect for handling innermost brackets first.

### Data Structures
We use two stacks to keep track of the state before entering a new bracket context:
1. `countStack`: Stores the repetition numbers (`k`).
2. `stringStack`: Stores the strings built up *before* encountering a `[` character.

### Algorithm
We iterate through each character in the string `s`, maintaining a `currentString` (to build the string at the current nesting level) and a number `k` (to build multi-digit numbers).

1. **If it's a digit:** We update `k`. Since digits can be contiguous (e.g., "100"), we do `k = k * 10 + (ch - '0')`.
2. **If it's an open bracket `[`:** 
   - This means we are entering a new inner context. We must save our current state.
   - Push `k` to `countStack`.
   - Push `currentString` to `stringStack`.
   - Reset `currentString` to empty and `k` to 0 for the new context inside the brackets.
3. **If it's a closed bracket `]`:**
   - This means we have finished the current context and need to decode it.
   - Pop the multiplier (`currentK`) from `countStack`.
   - Pop the previously built string from `stringStack`.
   - Repeat the `currentString` for `currentK` times and append it to the popped string.
   - The result becomes our new `currentString` as we exit the bracket.
4. **If it's a letter:** Simply append it to `currentString`.

### Example Trace
For `s = "3[a2[c]]"`:

- `'3'`: `k` becomes 3
- `'['`: push `3` to `countStack`, push `""` to `stringStack`. Reset `currentString`="", `k`=0.
- `'a'`: `currentString` becomes `"a"`
- `'2'`: `k` becomes 2
- `'['`: push `2` to `countStack`, push `"a"` to `stringStack`. Reset `currentString`="", `k`=0.
- `'c'`: `currentString` becomes `"c"`
- `']'`: pop `2` and `"a"`. Append `"c"` two times to `"a"`. `currentString` becomes `"acc"`.
- `']'`: pop `3` and `""`. Append `"acc"` three times to `""`. `currentString` becomes `"accaccacc"`.

### Complexity
- **Time Complexity:** $\mathcal{O}(\max(k) \cdot n)$, where $n$ is the length of a given string. The time complexity is dominated by the length of the final decoded string. In the worst case, we are creating very long strings. However, the constraints say the length of the final output string will never exceed $10^5$, so time complexity is safely bounded by $\mathcal{O}(\text{length of output})$.
- **Space Complexity:** $\mathcal{O}(\text{length of output})$, used by the stacks and the StringBuilders to store the nested results and the final answer.
