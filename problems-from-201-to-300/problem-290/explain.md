# Explanation - 316. Remove Duplicate Letters

## Problem Analysis

The goal is to remove all duplicate letters from a string `s` such that:
1. Every letter appears exactly once.
2. The resulting string is the **lexicographical smallest** among all possible results.

### Example
`s = "cbacdcbc"`
- Potential results: `"acdb"`, `"adbc"`, `"badc"`, etc.
- The smallest one is `"acdb"`.

## Approach: Greedy with Monotonic Stack

We can use a greedy strategy combined with a **stack** to maintain the characters of the result. The stack will behave like a "monotonic-ish" stack where we try to keep it sorted in increasing order, but we only remove elements if we know they will appear again later in the string.

### Data Structures
1. **Count Array (`int[] count`)**: Stores the frequency of each character remaining in the string as we process it.
2. **Visited Array (`boolean[] visited`)**: Tracks whether a character is already in the stack to avoid adding duplicates.
3. **Stack (`Stack<Character>`)**: Builds the final result.

### Step-by-Step Algorithm
1. **Count Frequencies**: Iterate through the string once to count the total occurrences of each character.
2. **Process Characters**: Iterate through the string again, character by character:
   - **Decrement Count**: Reduce the frequency of the current character in the count array.
   - **Skip if Visited**: If the character is already in the stack, move to the next character (we already have it in a potentially better position).
   - **Pop from Stack (Greedy Step)**: While the stack is not empty, and the current character is **smaller** than the top of the stack, and the character at the top of the stack **will appear again** later:
     - Pop the character from the stack.
     - Mark it as **not visited**.
   - **Push to Stack**: Add the current character to the stack and mark it as **visited**.
3. **Build Result**: After processing all characters, the stack contains the characters in the desired order.

## Complexity Analysis

- **Time Complexity**: $O(n)$, where $n$ is the length of the string. We iterate through the string twice, and each character is pushed and popped from the stack at most once.
- **Space Complexity**: $O(1)$ (ignoring the output string). The stack, count array, and visited array all have a maximum size of 26 (the number of lowercase English letters).

## Visualization
For `s = "bcabc"`:
1. `b`: Stack `[b]`, counts `{'a':1, 'b':1, 'c':2}`, visited `{'b'}`
2. `c`: Stack `[b, c]`, counts `{'a':1, 'b':1, 'c':1}`, visited `{'b', 'c'}`
3. `a`: 
   - `a < c` and `c` appears later? Yes. Pop `c`, visited `{'b'}`.
   - `a < b` and `b` appears later? Yes. Pop `b`, visited `{}`.
   - Push `a`. Stack `[a]`, counts `{'a':0, 'b':1, 'c':1}`, visited `{'a'}`.
4. `b`: Stack `[a, b]`, counts `{'a':0, 'b':0, 'c':1}`, visited `{'a', 'b'}`.
5. `c`: Stack `[a, b, c]`, counts `{'a':0, 'b':0, 'c':0}`, visited `{'a', 'b', 'c'}`.
**Result**: `"abc"`
