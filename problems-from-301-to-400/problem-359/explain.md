# Can I Win

## Intuition

The game has two players who take turns choosing integers without replacement to reach a target sum. This is a classic combinatorial game, which can be modeled using the **Minimax** theorem.

Since both players play optimally:
- A player is in a winning position if they can make a move that either:
  1. Reaches or exceeds the `desiredTotal` immediately.
  2. Puts the opponent in a state from which the opponent *cannot* win (i.e., the next state is a losing position for the opponent).
- A player is in a losing position if all possible moves they can make lead to a winning position for the opponent.

### State Representation

Because `maxChoosableInteger` is at most 20, we can represent the set of chosen numbers using a **bitmask** of size 20:
- The $i$-th bit of the integer `state` is set to `1` if the number $i$ has already been chosen.
- The $i$-th bit is set to `0` if the number $i$ is still available.

Since the sum of chosen numbers is uniquely determined by which numbers have been picked, the current state (defined by the bitmask) is sufficient to uniquely identify the game configuration. We don't need to store the current sum separately in our memoization table.

### Edge Cases

1. **Sum of all numbers is less than `desiredTotal`**:
   If the sum of all numbers from $1$ to `maxChoosableInteger` is less than `desiredTotal`, neither player can ever reach the target. The first player cannot win, so we return `false`.
   $$\sum_{i=1}^{\text{maxChoosableInteger}} i < \text{desiredTotal} \implies \text{return false}$$
2. **`desiredTotal <= 0`**:
   The first player wins immediately since the initial total is already met/exceeded. We return `true`.

---

## Algorithm

1. Verify edge cases (total sum check and non-positive `desiredTotal`).
2. Initialize a memoization array `memo` of size $2^{\text{maxChoosableInteger}}$ to store the results of subproblems:
   - `0` indicates the state has not been computed yet.
   - `1` indicates the state leads to a forced win (`true`).
   - `2` indicates the state leads to a forced loss (`false`).
3. Define a recursive depth-first search (`dfs`) function:
   - If the current `state` has been computed, return its cached result.
   - Loop through all integers from $1$ to `maxChoosableInteger`:
     - If the number $i$ is not chosen yet (i.e., `(state & (1 << (i - 1))) == 0`):
       - If choosing $i$ meets or exceeds the remaining `desiredTotal` (i.e., `desiredTotal - i <= 0`), or if the opponent's next turn returns `false` (meaning the opponent loses):
         - Mark `memo[state] = 1` (win) and return `true`.
   - If no move leads to a win, mark `memo[state] = 2` (lose) and return `false`.

---

## Complexity Analysis

### Time Complexity: $\mathcal{O}(2^M \cdot M)$
- There are at most $2^M$ states, where $M = \text{maxChoosableInteger}$.
- For each state, we iterate through $M$ choices to make the next move.
- In the worst case, we visit every state once and do $O(M)$ work per state, resulting in a time complexity of $\mathcal{O}(2^M \cdot M)$.
- Given $M \le 20$, the maximum number of operations is approximately $2^{20} \cdot 20 \approx 2 \times 10^7$, which runs in less than 50 milliseconds in Java.

### Space Complexity: $\mathcal{O}(2^M)$
- The memoization table requires $2^M$ bytes of memory.
- For $M = 20$, $2^{20} \text{ bytes} \approx 1 \text{ MB}$, which is extremely memory efficient.
- The recursive call stack can go at most $M$ levels deep, requiring $\mathcal{O}(M)$ auxiliary space.
