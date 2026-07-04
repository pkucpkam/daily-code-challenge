# Bucket Sort Approach: O(N) Time and O(N) Space

## Intuition

To sort the characters by their frequencies in decreasing order, we first need to know how many times each character appears. We can easily count this using a Hash Map. 

After we have the frequencies, instead of using a standard sorting algorithm that takes $O(N \log N)$ time, we can utilize **Bucket Sort**. Since the maximum frequency of any character cannot exceed the length of the string `s`, we can create an array of "buckets" where the index of the array represents the frequency, and the value at that index is a list of characters that have exactly that frequency.

Finally, we just iterate through our buckets from the highest frequency (the end of the array) to the lowest, appending the characters to our result string the required number of times.

## Algorithm

1. **Count Frequencies:** Iterate through the string `s` and use a Hash Map to store the frequency of each character.
2. **Create Buckets:** Initialize an array of Lists called `buckets` with a size of `s.length() + 1`. This size is chosen because the maximum possible frequency of a character is the length of the string itself.
3. **Populate Buckets:** Iterate through the entries of the Hash Map. For each character and its frequency, add the character to the list at `buckets[frequency]`.
4. **Build Result:** Initialize a `StringBuilder`. Iterate through the `buckets` array backwards (from `s.length()` down to `0`).
    - If a bucket is not empty, iterate through the characters in that bucket.
    - Append each character to the `StringBuilder` exactly `frequency` times.
5. Return the resulting string.

## Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of the string `s`. Counting the frequencies takes $\mathcal{O}(N)$. Populating the buckets takes $\mathcal{O}(U)$, where $U$ is the number of unique characters ($U \le N$). Rebuilding the string takes $\mathcal{O}(N)$ because we append a total of $N$ characters. Thus, the overall time complexity is $\mathcal{O}(N)$.
- **Space Complexity:** $\mathcal{O}(N)$. The Hash Map stores at most $N$ key-value pairs, taking $\mathcal{O}(N)$ space. The `buckets` array takes $\mathcal{O}(N)$ space. The result string builder also takes $\mathcal{O}(N)$ space.
