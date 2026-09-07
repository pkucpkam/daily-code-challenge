# 575. Distribute Candies

## Problem Understanding

Alice has $n$ candies, where the $i^{\text{th}}$ candy is of type `candyType[i]`. Alice is advised by her doctor to only eat $\frac{n}{2}$ candies in total ($n$ is guaranteed to be even). Alice wants to maximize the number of **different types** of candies she can eat while staying within the limit of $\frac{n}{2}$ total candies.

We need to determine the maximum number of unique candy types Alice can consume.

---

## Key Insights & Mathematical Derivation

### 1. Two Natural Upper Bounds

Let:
- $n = \text{candyType.length}$ (total number of candies).
- $u$ be the number of unique candy types present in `candyType` ($u = |\text{Set}(\text{candyType})|$).

Alice's choices are constrained by two fundamental limits:
1. **The Doctor's Quota ($\frac{n}{2}$):** Alice can eat at most $\frac{n}{2}$ candies in total. Even if every single candy was of a unique type, she can never eat more than $\frac{n}{2}$ different types.
2. **The Available Diversity ($u$):** Alice cannot eat a candy type that does not exist in the collection. Thus, the number of distinct types she eats cannot exceed $u$.

### 2. The Optimal Answer: $\min\left(u, \frac{n}{2}\right)$

Is it always possible to achieve exactly $\min\left(u, \frac{n}{2}\right)$? **Yes**, by a greedy constructive argument:

- **Case 1: $u \ge \frac{n}{2}$**  
  There are at least $\frac{n}{2}$ distinct candy types available. Alice can simply choose exactly $1$ candy from any $\frac{n}{2}$ different types.  
  $\implies \text{Result} = \frac{n}{2}$.

- **Case 2: $u < \frac{n}{2}$**  
  Alice can pick $1$ candy of every available distinct type ($u$ types). She still needs to eat $\left(\frac{n}{2} - u\right)$ more candies to reach the doctor's limit, which she can fulfill by picking duplicates from the already selected types without increasing or decreasing the unique count.  
  $\implies \text{Result} = u$.

Combining both cases:
$$\text{Max Different Types} = \min\left(u, \frac{n}{2}\right)$$

### 3. Early Termination Optimization

Since the result can never exceed $\frac{n}{2}$, we can insert candies into our set one by one and stop iterating the moment the number of unique candies reaches $\frac{n}{2}$. This avoids processing the remainder of the array.

---

## Approaches

### Approach 1: Hash Set with Early Exit (Recommended / Most Idiomatic)
- Use a `HashSet<Integer>` to record seen candy types.
- Traverse the array `candyType` and add each candy to the set.
- If `uniqueTypes.size() == n / 2`, terminate immediately and return $\frac{n}{2}$.
- If the loop finishes, return `uniqueTypes.size()`.

### Approach 2: Boolean / Direct-Address Array (Fastest Benchmark)
- Constraints specify that $-10^5 \le \text{candyType}[i] \le 10^5$, spanning a fixed range of $200,001$ possible values.
- We can allocate a boolean array `seen` of size $200,001$ with an offset of $100,000$.
- This avoids object allocation, autoboxing (`int` to `Integer`), and hash collision overheads, yielding $O(1)$ auxiliary operations with near-zero runtime overhead.

### Approach 3: Sorting
- Sort `candyType` in non-decreasing order ($\mathcal{O}(n \log n)$).
- Iterate through the sorted array and count adjacent distinct elements until either the end is reached or the count hits $\frac{n}{2}$.
- Requires $\mathcal{O}(1)$ extra space (if modifying the input array is permitted), but incurs an $\mathcal{O}(n \log n)$ time penalty.

---

## Code Implementation

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    /**
     * Finds the maximum number of different types of candies Alice can eat.
     * 
     * Key Logic:
     * Alice can eat at most n / 2 candies total.
     * Let u be the number of unique candy types:
     * - If u >= n / 2: Alice can pick 1 candy of n / 2 distinct types -> n / 2.
     * - If u < n / 2:  Alice can eat all u distinct types and fill the remaining
     *                  quota with duplicates -> u.
     * Therefore, the answer is strictly min(u, n / 2).
     * 
     * Time Complexity: O(n) average time with early exit.
     * Space Complexity: O(n) to store at most n / 2 unique candy types in a HashSet.
     */
    public int distributeCandies(int[] candyType) {
        int maxAllowed = candyType.length / 2;
        Set<Integer> uniqueTypes = new HashSet<>();

        for (int type : candyType) {
            uniqueTypes.add(type);
            // Early exit: Alice can never eat more than candyType.length / 2 types
            if (uniqueTypes.size() == maxAllowed) {
                return maxAllowed;
            }
        }

        return uniqueTypes.size();
    }
}
```

---

## Step-by-Step Example Walkthrough

### Example 1: `candyType = [1, 1, 2, 2, 3, 3]`

1. **Calculate Limit:** $n = 6 \implies \text{maxAllowed} = \frac{6}{2} = 3$.
2. **Track Unique Elements:**
   - Add `1` $\rightarrow \text{set} = \{1\}$, size = $1$.
   - Add `1` $\rightarrow \text{already present}$, size = $1$.
   - Add `2` $\rightarrow \text{set} = \{1, 2\}$, size = $2$.
   - Add `2` $\rightarrow \text{already present}$, size = $2$.
   - Add `3` $\rightarrow \text{set} = \{1, 2, 3\}$, size = $3$.
   - **Early Exit:** `set.size() == maxAllowed` ($3 == 3$) $\implies$ Return `3`.

**Output:** `3` ✅

---

### Example 2: `candyType = [1, 1, 2, 3]`

1. **Calculate Limit:** $n = 4 \implies \text{maxAllowed} = \frac{4}{2} = 2$.
2. **Track Unique Elements:**
   - Add `1` $\rightarrow \text{set} = \{1\}$, size = $1$.
   - Add `1` $\rightarrow \text{already present}$, size = $1$.
   - Add `2` $\rightarrow \text{set} = \{1, 2\}$, size = $2$.
   - **Early Exit:** `set.size() == maxAllowed` ($2 == 2$) $\implies$ Return `2`.
   *(Notice element `3` does not even need to be inspected).*

**Output:** `2` ✅

---

### Example 3: `candyType = [6, 6, 6, 6]`

1. **Calculate Limit:** $n = 4 \implies \text{maxAllowed} = \frac{4}{2} = 2$.
2. **Track Unique Elements:**
   - Add `6` $\rightarrow \text{set} = \{6\}$, size = $1$.
   - Add `6`, `6`, `6` $\rightarrow$ duplicates, size remains $1$.
3. **End of Loop:** Return `set.size()` = $1$.

**Output:** `1` ✅

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Notes |
| :--- | :--- | :--- | :--- |
| **HashSet with Early Exit** ✅ | $\mathcal{O}(n)$ average | $\mathcal{O}(n)$ | Stores at most $\frac{n}{2}$ elements. Idiomatic and general across arbitrary integer ranges. |
| **Boolean Array with Offset** | $\mathcal{O}(n)$ | $\mathcal{O}(R)$ where $R = 200,001$ | Constant memory footprint (~200 KB), zero heap allocations, fastest raw execution. |
| **Sorting** | $\mathcal{O}(n \log n)$ | $\mathcal{O}(1)$ | Avoids hash tables, but slower due to sorting comparisons. |

---

## Edge Cases & Pitfalls Handled

1. **All Candies of the Same Type ($u = 1$):**  
   Example: `[5, 5, 5, 5]`. Correctly returns $1$ since only one type exists.
2. **All Candies are Unique ($u = n$):**  
   Example: `[1, 2, 3, 4]`. Returns $\frac{n}{2} = 2$ upon inspecting the first $\frac{n}{2}$ elements via early exit.
3. **Negative Values:**  
   Constraints include negative candy types (e.g. `[-100, -100, 5, 10]`). The `HashSet` handles negative values natively via `Integer.hashCode()`.
4. **Minimal Input Size ($n = 2$):**  
   If `candyType = [1, 2]`, $\text{maxAllowed} = 1$, exits after the first element with result $1$.
