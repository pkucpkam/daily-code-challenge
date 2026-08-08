# 528. Random Pick with Weight

## Intuition

The problem requires us to randomly pick an index `i` with a probability proportional to its weight `w[i]`. A common real-world analogy for this is a "roulette wheel," where each index corresponds to a slot on the wheel, and the size of the slot is proportional to its weight.

For example, if `w = [1, 3]`:
- The total weight is `1 + 3 = 4`.
- Index `0` has weight `1`, so it should have a `1/4` (25%) chance of being picked.
- Index `1` has weight `3`, so it should have a `3/4` (75%) chance of being picked.

To simulate this programmatically, we can align these weights on a straight line, representing them as cumulative sums. Then, we can randomly throw a dart onto this line and see which segment it lands on!

---

## Key Insights & Algorithm

### Prefix Sums and Binary Search

1. **Prefix Sums (Cumulative Weights)**:
   - We construct an array of prefix sums `prefixSums` where `prefixSums[i]` is the sum of weights from index `0` up to `i`.
   - For `w = [1, 3]`, our `prefixSums` would be `[1, 4]`.
   - These sums define boundaries:
     - Index `0` spans from `0` to `1` (exclusive of `0`, inclusive of `1`).
     - Index `1` spans from `1` to `4` (exclusive of `1`, inclusive of `4`).

2. **Random Target Generation**:
   - We need to pick a random number `target` strictly greater than `0` and up to the `totalSum` (which is the last element in `prefixSums`).
   - In Java, `random.nextInt(totalSum) + 1` generates a random integer in the range `[1, totalSum]`.

3. **Binary Search**:
   - Once we have our `target`, we need to find the first index in `prefixSums` that is **greater than or equal to** the `target`.
   - Since `w` only contains positive integers, `prefixSums` is strictly increasing (sorted). This makes it perfect for **Binary Search**.
   - If `prefixSums[mid] < target`, we know the target lies to the right, so `left = mid + 1`.
   - If `prefixSums[mid] >= target`, this index might be our answer, or the answer lies to the left, so `right = mid`.

---

## Walkthrough

Consider `w = [1, 3, 2]`:
- **Initialization**: 
  - `prefixSums = [1, 1+3, 1+3+2] = [1, 4, 6]`.
  - `totalSum = 6`.

- **Pick Index**:
  - We generate a random `target` between `1` and `6`.
  
  **Case 1: `target = 5`**
  - Binary search bounds: `left = 0`, `right = 2`.
  - `mid = 1`, `prefixSums[1] = 4`.
  - Since `4 < 5`, `left = mid + 1 = 2`.
  - `left = 2`, `right = 2`. Loop ends.
  - Return `2` (the correct index for segment `[5, 6]`).

  **Case 2: `target = 2`**
  - Binary search bounds: `left = 0`, `right = 2`.
  - `mid = 1`, `prefixSums[1] = 4`.
  - Since `4 >= 2`, `right = mid = 1`.
  - New bounds: `left = 0`, `right = 1`.
  - `mid = 0`, `prefixSums[0] = 1`.
  - Since `1 < 2`, `left = mid + 1 = 1`.
  - `left = 1`, `right = 1`. Loop ends.
  - Return `1` (the correct index for segment `[2, 3, 4]`).

---

## Complexity Analysis

- **Time Complexity**:
  - **Constructor**: $\mathcal{O}(N)$ to iterate through the weights and build the `prefixSums` array.
  - **`pickIndex()`**: $\mathcal{O}(\log N)$ because we use binary search over the `prefixSums` array.
- **Space Complexity**:
  - $\mathcal{O}(N)$ to store the `prefixSums` array.
