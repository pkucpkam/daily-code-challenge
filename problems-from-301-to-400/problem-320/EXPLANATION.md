# Explanation of Elimination Game Solution

## The Approach

To achieve the optimal $O(\log n)$ time complexity and $O(1)$ space complexity, we must simulate the process without actually creating or modifying any arrays.

Instead of keeping track of all the elements, we only need to keep track of the **head** (the very first element) of the remaining sequence. If we know the first element when the array size is reduced to 1, that single element is our answer.

To do this, we maintain four variables:
1. `head`: The first element of the remaining list (initially `1`).
2. `step`: The gap between consecutive elements in the remaining list (initially `1`).
3. `remaining`: The number of elements currently left (initially `n`).
4. `leftToRight`: A boolean flag indicating the current direction of traversal (initially `true`).

## When Does the Head Change?

In each pass, we eliminate half of the elements. But the key question is: **does the `head` change?**

There are only two scenarios where the `head` will change and move to the right (by `step`):

1. **When moving from Left to Right:** 
   We always delete the first element, so the `head` will **always** be deleted. The new head will be the next available number, which is `head + step`.
2. **When moving from Right to Left, AND the number of remaining elements is ODD:** 
   Because we start deleting from the rightmost element, if there's an odd number of elements, we will eventually delete the leftmost element (the `head`). Thus, the head becomes `head + step`.
   *(If the number of remaining elements is EVEN, we delete the 2nd, 4th, 6th... from the left, meaning the 1st element (`head`) survives, so it doesn't change).*

## State Updates After Each Pass

After checking if the `head` needs to be updated and making the change, we update the other variables for the next pass:
- `remaining = remaining / 2` (since half of the numbers are deleted).
- `step = step * 2` (since the gap between remaining numbers doubles).
- `leftToRight = !leftToRight` (flip the direction).

We repeat this process as long as `remaining > 1`.

## Example Walkthrough

Let's say $n = 9$.
Initial State: `head = 1, step = 1, remaining = 9, leftToRight = true`
List: `[1, 2, 3, 4, 5, 6, 7, 8, 9]`

**Pass 1:**
- `leftToRight` is `true`, so we update `head`.
- `head = 1 + 1 = 2`.
- `remaining = 9 / 2 = 4`.
- `step = 1 * 2 = 2`.
- `leftToRight = false`.
- *(Implicit List is now `[2, 4, 6, 8]`)*

**Pass 2:**
- `leftToRight` is `false`. We check if `remaining` is odd. `4 % 2 == 0`, so it is even.
- `head` remains `2`.
- `remaining = 4 / 2 = 2`.
- `step = 2 * 2 = 4`.
- `leftToRight = true`.
- *(Implicit List is now `[2, 6]`)*

**Pass 3:**
- `leftToRight` is `true`, so we update `head`.
- `head = 2 + 4 = 6`.
- `remaining = 2 / 2 = 1`.
- `step = 4 * 2 = 8`.
- `leftToRight = false`.
- *(Implicit List is now `[6]`)*

Now `remaining = 1`. The loop terminates. The answer is `head`, which is **6**.

## Complexity
- **Time Complexity:** $O(\log n)$. At each step, we divide the remaining elements by 2.
- **Space Complexity:** $O(1)$. We only use four variables to keep track of the state, regardless of the size of $n$.
