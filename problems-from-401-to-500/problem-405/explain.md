# 556. Next Greater Element III

## Intuition

Given a positive integer `n`, we want to find the **smallest integer** that is strictly greater than `n` and consists of the **exact same digits** as `n`.

This problem is equivalent to finding the **next lexicographical permutation** of the digits of `n` (similar to C++ `std::next_permutation`). If the digits are already in descending order (e.g., `321` or `54321`), no greater permutation is possible, so we return `-1`. Additionally, if the next valid permutation exceeds the maximum 32-bit signed integer limit (`2,147,483,647`), we must return `-1`.

---

## Key Insights & Algorithmic Strategy

To construct the next smallest greater integer using the same digits:

### 1. Identify the Pivot (`i`)
Scan the digits from right to left to find the first digit that is smaller than the digit immediately to its right:
$$\text{digits}[i] < \text{digits}[i + 1]$$
- The digits to the right of index `i` (from `i + 1` to the end) are in non-increasing order.
- If no such index `i` exists ($i < 0$), the entire number is in non-increasing order. No larger permutation exists $\rightarrow$ return `-1`.

### 2. Find the Successor (`j`)
Scan from right to left to find the first (smallest) digit at index `j` ($j > i$) that is strictly greater than $\text{digits}[i]$:
$$\text{digits}[j] > \text{digits}[i]$$

### 3. Swap the Pivot and Successor
Swap $\text{digits}[i]$ with $\text{digits}[j]$. Since $\text{digits}[j] > \text{digits}[i]$, this step guarantees that the newly formed number is strictly greater than `n`.

### 4. Reverse the Suffix
The suffix from index $i + 1$ to the end of the array is still in non-increasing order. To make the new number as small as possible, reverse this suffix into non-decreasing (ascending) order.

### 5. Check for 32-Bit Integer Overflow
Convert the modified digit sequence into a 64-bit integer (`long`). If the value exceeds `Integer.MAX_VALUE` ($2^{31} - 1 = 2,147,483,647$), return `-1`. Otherwise, return the integer value.

---

## Code Implementation (Java)

```java
class Solution {
    public int nextGreaterElement(int n) {
        char[] digits = String.valueOf(n).toCharArray();
        int len = digits.length;

        // Step 1: Find the first decreasing digit from right to left
        int i = len - 2;
        while (i >= 0 && digits[i] >= digits[i + 1]) {
            i--;
        }

        // If no such digit is found, digits are in non-increasing order; no greater permutation exists
        if (i < 0) {
            return -1;
        }

        // Step 2: Find the smallest digit to the right of 'i' that is strictly greater than digits[i]
        int j = len - 1;
        while (j > i && digits[j] <= digits[i]) {
            j--;
        }

        // Step 3: Swap digits at indices i and j
        swap(digits, i, j);

        // Step 4: Reverse the digits from index i + 1 to the end to get the smallest next permutation
        reverse(digits, i + 1, len - 1);

        // Step 5: Parse the resulting string into long and check for 32-bit integer overflow
        try {
            long val = Long.parseLong(new String(digits));
            return val > Integer.MAX_VALUE ? -1 : (int) val;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `n = 12`

1. Digits array: `['1', '2']`
2. **Step 1:** Scan right to left. Index `i = 0` (`'1' < '2'`).
3. **Step 2:** Scan right to left for digit $> '1' \rightarrow$ Index `j = 1` (`'2'`).
4. **Step 3:** Swap `digits[0]` and `digits[1]` $\rightarrow \text{array} = ['2', '1']$.
5. **Step 4:** Reverse suffix from index 1 to 1 $\rightarrow \text{array} = ['2', '1']$.
6. **Step 5:** Parse `21`. $21 \le 2147483647 \rightarrow$ **Result:** `21`.

---

### Example 2: `n = 12431`

1. Digits array: `['1', '2', '4', '3', '1']`
2. **Step 1:** Scan right to left for `digits[i] < digits[i+1]`:
   - At $i = 3$: `'3' \ge '1'`
   - At $i = 2$: `'4' \ge '3'`
   - At $i = 1$: `'2' < '4'` $\rightarrow$ **Pivot `i = 1`** (value `'2'`).
3. **Step 2:** Find rightmost digit $> '2'$:
   - At $j = 4$: `'1' \le '2'`
   - At $j = 3$: `'3' > '2'` $\rightarrow$ **Successor `j = 3`** (value `'3'`).
4. **Step 3:** Swap `digits[1]` (`'2'`) and `digits[3]` (`'3'`) $\rightarrow ['1', \mathbf{3}, '4', \mathbf{2}, '1']$.
5. **Step 4:** Reverse suffix from index 2 to 4 (`['4', '2', '1']` $\rightarrow$ `['1', '2', '4']`).
   - Final array: `['1', '3', '1', '2', '4']`.
6. **Step 5:** Parse `13124`. $13124 \le 2147483647 \rightarrow$ **Result:** `13124`.

---

### Example 3: `n = 21`

1. Digits array: `['2', '1']`
2. **Step 1:** Scan right to left $\rightarrow$ At $i = 0$, `'2' \ge '1'`. Loop ends with $i = -1$.
3. Since $i < 0$, no larger permutation exists $\rightarrow$ **Result:** `-1`.

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Recommendation |
|:---|:---|:---|:---|
| **Next Permutation Algorithm** | $\mathcal{O}(d) \approx \mathcal{O}(1)$ | $\mathcal{O}(d) \approx \mathcal{O}(1)$ | **Optimal** — single pass right-to-left scan |

- **Time Complexity:** $\mathcal{O}(d)$, where $d$ is the number of digits in $n$. Since $n \le 2^{31} - 1$, $d \le 10$. Thus, the time complexity is effectively $\mathcal{O}(1)$ in practice.
- **Space Complexity:** $\mathcal{O}(d)$, for the character array used to store digit permutations, which is $\mathcal{O}(1)$ space.

