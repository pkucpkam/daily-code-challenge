# 384. Shuffle an Array - Explanation

## Problem Overview

We need to implement a class that supports two operations:

* `reset()` returns the array in its original order.
* `shuffle()` returns a random permutation of the array.

The important requirement is that every possible permutation must be equally likely.

## Best Approach: Fisher-Yates Shuffle

The best solution is the Fisher-Yates shuffle algorithm.

For each position `i` from left to right:

1. Pick a random index `j` from the range `[i, n - 1]`.
2. Swap `nums[i]` with `nums[j]`.
3. Continue until every position has been fixed.

Once position `i` is processed, it will not be changed again. This means each position receives one uniformly random element from the remaining unplaced elements.

## Why This Is Uniform

Assume the array has `n` elements.

* For the first position, each element has probability `1 / n` of being chosen.
* For the second position, each remaining element has probability `1 / (n - 1)` of being chosen.
* This continues until the last position.

So the probability of any exact permutation is:

```text
1/n * 1/(n-1) * 1/(n-2) * ... * 1/1 = 1/n!
```

There are `n!` possible permutations, and each one has probability `1 / n!`, so all permutations are equally likely.

## Implementation Details

We store a copy of the original array:

```java
this.original = nums.clone();
```

Then:

* `reset()` returns `original.clone()` so callers receive the original order without mutating internal state.
* `shuffle()` creates a fresh clone of `original`, shuffles that clone, and returns it.

This keeps the class simple and avoids accidental changes to the stored original array.

## Complexity

Let `n` be `nums.length`.

* Constructor time: `O(n)`
* `reset()` time: `O(n)`
* `shuffle()` time: `O(n)`
* Space: `O(n)` for storing the original array and returning cloned arrays

## Code

```java
import java.util.Random;

class Solution {
    private final int[] original;
    private final Random random;

    public Solution(int[] nums) {
        this.original = nums.clone();
        this.random = new Random();
    }

    public int[] reset() {
        return original.clone();
    }

    public int[] shuffle() {
        int[] shuffled = original.clone();

        for (int i = 0; i < shuffled.length; i++) {
            int randomIndex = i + random.nextInt(shuffled.length - i);
            int temp = shuffled[i];
            shuffled[i] = shuffled[randomIndex];
            shuffled[randomIndex] = temp;
        }

        return shuffled;
    }
}
```
