# Circular Array Loop (O(N) Time and O(1) Space)

## Intuition

To detect a cycle in a circular array, we can think of the array elements and their transitions as a directed graph. 
Since each element points to exactly one next element, this graph consists of paths that can either:
1. Form a cycle of length > 1 moving in a single direction (positive/forward or negative/backward).
2. End in a cycle of length 1 (self-loop, i.e., `nums[i] % n == 0` or moving to itself).
3. Experience a change in direction (positive to negative or vice versa).

Using **Floyd's Cycle Detection Algorithm (Slow and Fast Pointers / Tortoise and Hare)**, we can detect cycles efficiently:
* The `slow` pointer moves by 1 step at a time.
* The `fast` pointer moves by 2 steps at a time.
* If a cycle exists, they will eventually meet.

### Constraints & Edge Cases
1. **Direction Consistency**: A cycle must be either entirely forward or entirely backward. 
   We must ensure that all elements along the path have the same sign (i.e., `nums[curr] * nums[next] > 0`). If we detect a change of direction, we abort the search from the current starting index.
2. **Cycle Length**: A cycle of length 1 (e.g. `nums[i]` points to `i`) is invalid.
   If the pointers meet, we must verify that the length of the cycle is greater than 1 (`slow != getNext(nums, slow)`).
3. **Efficiency (O(N) Time)**: 
   If we do not find a valid cycle starting from index `i`, we can mark all nodes along that path as visited by setting their value to `0`. 
   Any path entering a node marked `0` is guaranteed not to lead to a valid cycle (since we already explored it and failed). 
   This optimization ensures that we visit each element at most a constant number of times.

---

## Algorithm

1. **Iterate** through each index `i` from `0` to `n - 1`:
   - If `nums[i] == 0`, skip it as it's already marked as "no valid cycle".
   - Initialize two pointers `slow = i` and `fast = i`.
   
2. **Cycle Detection (Slow/Fast Pointers)**:
   - Move `slow` forward by 1 step:
     - Check if the transition is in the same direction (`nums[slow] * nums[nextSlow] > 0`). If not, break.
     - Set `slow = nextSlow`.
   - Move `fast` forward by 2 steps:
     - Check direction consistency for both steps. If any transition changes sign, break.
     - Set `fast = nextFast2`.
   - If `slow == fast`:
     - Check if it's a self-loop (`slow == getNext(nums, slow)`). If yes, break (invalid cycle).
     - Otherwise, return `true`.

3. **In-place Marking (Optimization)**:
   - If the search from `i` fails, re-traverse the path starting from `i`.
   - Set each node's value to `0` as we traverse, stopping when we hit a change in direction or an already marked node (`nums[curr] * val <= 0`).

4. **Return `false`** if we finish iterating through all indices without finding a cycle.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(N)$
  - Each element is visited a constant number of times: once by the slow/fast pointers, and once by the marking phase to set it to `0`.
  - Once an element is set to `0`, it is never re-processed. Thus, the total time complexity is linear.

- **Space Complexity**: $\mathcal{O}(1)$
  - We do not use any extra space except for a few local variables (`slow`, `fast`, `curr`, `val`). We reuse the input array `nums` to mark visited nodes.
