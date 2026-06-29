# Two-Pointer Approach (Read and Write Pointers): O(N) Time

## Intuition

The problem requires us to compress an array of characters `chars` in-place and return the new length. Since the output must be stored in the input array `chars` itself and we must use only $\mathcal{O}(1)$ extra space, we can use a **Two-Pointer** technique:
1. A **`read` pointer** to scan through the original character array.
2. A **`write` pointer** to modify the array in-place with the compressed characters and their counts.

Because the compressed representation of any group of characters is always shorter than or equal to the group's length (e.g., `"aa"` $\rightarrow$ `"a2"`, `"a"` $\rightarrow$ `"a"`, `"aaaa"` $\rightarrow$ `"a4"`), the `write` pointer will never overshoot the `read` pointer. This ensures we do not overwrite any characters before they are read.

## Algorithm

1. Initialize two pointers: `read = 0` and `write = 0`.
2. Iterate while `read < chars.length`:
   - Keep track of the current character `currentChar = chars[read]`.
   - Count the consecutive occurrences of `currentChar` by advancing `read`. Let this count be `count`.
   - Write `currentChar` to the `write` position: `chars[write++] = currentChar`.
   - If `count > 1`, convert `count` to a string and write its digits one by one to the `write` positions, advancing `write` for each digit.
3. Once all characters are processed, return `write` as the new length of the compressed array.

## Dry Run Example

Let's trace the algorithm with the input `chars = ["a","a","b","b","c","c","c"]`:

| Step | `read` | `currentChar` | Group Length (`count`) | Action | `chars` State | `write` |
|---|---|---|---|---|---|---|
| **Start** | `0` | - | - | - | `["a","a","b","b","c","c","c"]` | `0` |
| **1** | `2` | `'a'` | `2` | Write `'a'`, then count digit `'2'` | `["a","2","b","b","c","c","c"]` | `2` |
| **2** | `4` | `'b'` | `2` | Write `'b'`, then count digit `'2'` | `["a","2","b","2","c","c","c"]` | `4` |
| **3** | `7` | `'c'` | `3` | Write `'c'`, then count digit `'3'` | `["a","2","b","2","c","3","c"]` | `6` |
| **End** | `7` | - | - | Loop terminates, return `write = 6` | `["a","2","b","2","c","3","c"]` | `6` |

The first `6` elements of the array are `["a","2","b","2","c","3"]`, which is correct.

## Complexity

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the length of `chars`. We traverse the array with the `read` pointer exactly once. The inner digit-writing step takes $\mathcal{O}(\log_{10}(\text{count}))$ time, which is at most $4$ operations since the maximum count is $2000$. Thus, the total time complexity remains linear.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. We perform the entire compression in-place and only use a few variables (`read`, `write`, `count`, etc.) for tracking indices.
