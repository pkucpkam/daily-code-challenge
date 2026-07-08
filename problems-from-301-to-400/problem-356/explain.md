# Monotonic Stack Approach (O(N) Time and O(N) Space)

## Intuition

A **132 pattern** is a subsequence of three integers `nums[i]`, `nums[j]`, and `nums[k]` such that:
1. `i < j < k`
2. `nums[i] < nums[k] < nums[j]`

To find such a subsequence efficiently, we can break down the requirements:
- Let's refer to `nums[i]` as **"1"**, `nums[j]` as **"3"**, and `nums[k]` as **"2"**.
- We need to find a **"1"** that is smaller than **"2"**, where **"2"** itself is smaller than **"3"**, and they appear in the order **"1" -> "3" -> "2"**.
- To make it as easy as possible to find a valid **"1"** (`nums[i]`), we want to maximize the value of **"2"** (`nums[k]`) that is already paired with a valid, larger **"3"** (`nums[j]`) to its left.

If we iterate through the array from **right to left**:
- Every element we encounter is a candidate for being **"1"** (`nums[i]`).
- We can maintain a stack of candidates for **"3"** (`nums[j]`).
- We can also keep track of the largest possible value for **"2"** (`nums[k]`) we have seen so far (let's call this variable `third`).
- If we ever find a current element `nums[i]` such that `nums[i] < third`, we have successfully found a **132 pattern** because `third` (our **"2"**) is already known to be smaller than some **"3"** located to its left (which popped it off our stack).

## Algorithm

1. **Initialize:**
   - A `stack` to store candidates for the **"3"** (`nums[j]`) element.
   - A variable `third` initialized to `-infinity` to store the value of the **"2"** (`nums[k]`) element.

2. **Iterate from Right to Left:**
   - For each element `nums[i]`:
     - **Check Pattern:** If `nums[i] < third`, then we have found a valid pattern `nums[i] < third < nums[j]` where the index sequence is `i < j < k`. Return `true`.
     - **Maintain Monotonicity:** While the stack is not empty and the current element `nums[i]` is strictly greater than the top element of the stack:
       - Pop the top element from the stack and assign it to `third`. Since `nums[i] > stack.peek()`, the popped element is a valid candidate for **"2"** (`nums[k]`) associated with the current element as **"3"** (`nums[j]`).
       - Because we only pop elements that are smaller than `nums[i]`, and we traverse from right to left, the popped elements will always be smaller than the new stack elements, keeping the stack monotonically decreasing from bottom to top.
     - **Push Current:** Push `nums[i]` onto the stack.

3. **Return `false`** if the loop finishes without finding any pattern.

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - Although there is a nested `while` loop inside the `for` loop, each element is pushed onto the stack at most once and popped from the stack at most once. Therefore, the total number of stack operations is at most $2N$, leading to an overall linear time complexity.

- **Space Complexity:** $\mathcal{O}(N)$
  - In the worst case (e.g., a sorted array in ascending order), we might push all elements onto the stack, requiring $\mathcal{O}(N)$ space.
  - *Note:* We can optimize this to $\mathcal{O}(1)$ auxiliary space by reusing the input array `nums` as the stack array, since the stack pointer `top` always stays to the right of the current index `i`.
