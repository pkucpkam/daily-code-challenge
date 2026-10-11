# 633. Sum of Square Numbers

## Approach: Two Pointers

We need to determine whether there are non-negative integers `a` and `b` such that:

$$a^2 + b^2 = c$$

The largest possible value for either number is $\lfloor\sqrt{c}\rfloor$. Start with two pointers:

- `left = 0`
- `right = floor(sqrt(c))`

At each step, calculate `left * left + right * right`:

- If the sum equals `c`, return `true`.
- If the sum is smaller than `c`, increase `left` to make the sum larger.
- If the sum is larger than `c`, decrease `right` to make the sum smaller.

If the pointers cross, no valid pair exists, so return `false`.

## Correctness

For a fixed `right`, increasing `left` can only increase the sum. Therefore, when the current sum is smaller than `c`, decreasing `left` cannot produce a solution and moving `left` forward is the only useful direction.

Similarly, when the current sum is larger than `c`, increasing `right` cannot produce a solution, so moving `right` backward is safe.

The algorithm checks every candidate pair that can still produce `c` without skipping a possible solution. Therefore, it returns `true` exactly when such a pair exists.

## Complexity

- **Time:** $O(\sqrt{c})$
- **Space:** $O(1)$

`long` is used for the calculations so that squaring values remains safe for the largest allowed input.

## Java Solution

```java
class Solution {
    public boolean judgeSquareSum(int c) {
        long left = 0;
        long right = (long) Math.sqrt(c);

        while (left <= right) {
            long sum = left * left + right * right;

            if (sum == c) {
                return true;
            }
            if (sum < c) {
                left++;
            } else {
                right--;
            }
        }

        return false;
    }
}
```
