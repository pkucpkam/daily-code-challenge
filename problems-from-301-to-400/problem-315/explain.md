# 382. Linked List Random Node - Explanation

## Problem Overview
We need to design an algorithm to return a random node's value from a singly linked list. The crucial requirement is that **each node must have the same probability of being chosen**.

There is also a follow-up constraint: 
- What if the linked list is extremely large and its length is unknown to you?
- Could you solve this efficiently without using extra space?

---

## Approaches

### Approach 1: Convert to Array (Brute Force)
The most straightforward way is to traverse the linked list once, extract all node values, and store them in an `ArrayList`.
- **Initialization:** Iterate through the linked list and add all values to an array.
- **`getRandom()`:** Simply generate a random index between `0` and `array.size() - 1` and return the element at that index.

**Complexity:**
- **Time:** `O(N)` for initialization, `O(1)` for `getRandom()`.
- **Space:** `O(N)` because we are storing all elements in an array.
- **Drawback:** It does not satisfy the Follow-up requirement. If the linked list is exceptionally long, storing it in an array will consume a large amount of memory.

---

### Approach 2: Reservoir Sampling (The "Best" / Optimal Solution)
Reservoir Sampling is a family of randomized algorithms for choosing a simple random sample, without replacement, of `k` items from a population of unknown size `N` in a single pass. For this problem, `k = 1`.

**How it works (for k = 1):**
1. We traverse the linked list node by node.
2. For the `i-th` node (1-indexed), we choose to keep its value as our potential result with a probability of `1/i`.
3. To achieve a `1/i` probability in Java, we can generate a random number from `0` to `i - 1`. If the random number equals `0` (or any specific number in that range), we update our result with the current node's value.
4. When we finish traversing the list, the chosen result is returned.

**Mathematical Proof of Uniform Probability:**
Let's prove that every node has exactly a `1/N` chance of being chosen, where `N` is the total number of nodes.

Suppose we want to know the probability of the `i-th` node being the final answer:
`P(Node i is the final answer)` = `P(Node i is picked at step i)` × `P(Node i is NOT replaced in step i+1)` × ... × `P(Node i is NOT replaced in step N)`

Mathematically:
`P` = $(1/i) * (1 - 1/(i+1)) * (1 - 1/(i+2)) * ... * (1 - 1/N)$
`P` = $(1/i) * (i/(i+1)) * ((i+1)/(i+2)) * ... * ((N-1)/N)$

If we cancel out the diagonal terms:
`P = 1 / N`

Every single element has exactly the same probability `1/N` of being returned!

**Complexity:**
- **Time:** `O(1)` for initialization, `O(N)` for `getRandom()`. We only do a single pass when `getRandom()` is called.
- **Space:** `O(1)`. We only need a few variables (pointers, counter, result) to keep track of our state. We don't allocate any extra space regardless of the list size.

This perfectly satisfies the Follow-up constraints and is the "best" and expected solution in coding interviews for this problem.