# 503. Next Greater Element II

## Intuition

The problem asks us to find the **next greater element** for each element in a **circular array**. 

A standard Next Greater Element problem on a linear array can be efficiently solved in $\mathcal{O}(N)$ time using a **Monotonic Stack**. However, because the array is **circular**, the next greater element for an item near the end of the array might actually appear near the beginning.

### Handling Circularity

Instead of physically duplicating the array into size $2N$ (which wastes memory), we can **simulate traversing an array of length $2N$** by iterating `i` from `0` to `2N - 1` and using the modulo operator `i % N` to access elements.

- In the **first pass** (`0` to `N - 1`), we process elements normally and push their indices onto the stack.
- In the **second pass** (`N` to `2N - 1`), we do not need to push elements onto the stack anymore, but we continue popping elements from the stack whenever a greater element is encountered. This handles circular wrap-around seamlessly.

---

## Key Insights & Algorithm

1. **Monotonic Decreasing Stack**:
   - We store the **indices** of elements in the stack such that the corresponding array values are strictly decreasing from bottom to top.
2. **Finding the Next Greater Element**:
   - For each element `num = nums[i % N]`, while the stack is non-empty and `nums[stack.peek()] < num`, the current `num` is the next greater element for `stack.peek()`. We set `result[stack.pop()] = num`.
3. **Pushing to Stack**:
   - We only push indices to the stack during the first pass (`i < N`). Pushing during the second pass is unnecessary because any element pushed in the second pass would be a duplicate.
4. **Default Result**:
   - Initialize the `result` array with `-1`. Any index whose value is never popped from the stack does not have a next greater element.

---

## Detailed Step-by-Step

1. Initialize `n = nums.length`, an output array `result` filled with `-1`, and an empty stack `stack` to hold indices.
2. Iterate `i` from `0` to `2 * n - 1`:
   - `num = nums[i % n]`
   - While `stack` is not empty and `nums[stack.peek()] < num`:
     - `result[stack.pop()] = num`
   - If `i < n`:
     - `stack.push(i)`
3. Return `result`.

---

## Complexity Analysis

### Time Complexity:
- $\mathcal{O}(N)$: We iterate through $2N$ elements. Each index is pushed onto the stack at most once (during the first $N$ iterations) and popped at most once. Therefore, the total number of stack operations is bounded by $\mathcal{O}(N)$.

### Space Complexity:
- $\mathcal{O}(N)$: Auxiliary space used by the stack to store up to $N$ indices. The output array `result` of size $N$ is required to store the answer.
