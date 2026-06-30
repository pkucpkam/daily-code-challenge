# Stack Approach: O(M + N) Time and Space

## Intuition

The problem asks us to add two numbers represented by linked lists where the most significant digit comes first. For standard addition, we typically start from the least significant digit (the end of the numbers). However, traversing a singly linked list from the end is not straightforward. The follow-up constraint specifically asks us to solve it without reversing the input lists.

To process elements in reverse order without reversing the list, a **Stack** is the perfect data structure. A stack follows the Last-In-First-Out (LIFO) principle, allowing us to traverse the lists, push all digits onto stacks, and then pop them off to add starting from the least significant digit.

## Algorithm

1. Initialize two stacks, `stack1` and `stack2`.
2. Traverse the first list `l1` and push all its node values into `stack1`.
3. Traverse the second list `l2` and push all its node values into `stack2`.
4. Initialize `head = null` to build the resulting linked list from back to front, and a variable `carry = 0`.
5. Loop while either stack is not empty or `carry` is greater than 0:
   - Initialize `sum` with the current `carry`.
   - If `stack1` is not empty, pop the top value and add it to `sum`.
   - If `stack2` is not empty, pop the top value and add it to `sum`.
   - Create a new node with the value `sum % 10`.
   - Update `carry` to `sum / 10`.
   - Link the new node to the front of the result list: `newNode.next = head` and `head = newNode`.
6. Return `head`.

## Complexity

- **Time Complexity:** $\mathcal{O}(M + N)$ where $M$ and $N$ are the lengths of `l1` and `l2`. We traverse both lists once to push digits onto the stacks, and then pop them to compute the sum. Each step takes linear time relative to the list sizes.
- **Space Complexity:** $\mathcal{O}(M + N)$ to store the nodes in the stacks. The result list takes $\mathcal{O}(\max(M, N))$ space, which is required for the output, but the stacks contribute to extra auxiliary space.
