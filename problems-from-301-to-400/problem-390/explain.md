# 532. K-diff Pairs in an Array

## Intuition

To find unique pairs `(nums[i], nums[j])` such that their absolute difference is `k`, we can utilize a hash map to count the frequencies of each number in the array. This allows us to quickly check for the existence of the required complementing number.

Since we are looking for unique pairs, operating on the unique keys of a hash map naturally helps avoid duplicates. We can consider two cases based on the value of `k`:
1.  **`k > 0`**: For any number `x`, we just need to check if `x + k` exists in our array. 
2.  **`k == 0`**: We are looking for pairs of the same number. Therefore, we just need to check if a number appears at least twice in the array.

---

## Key Insights & Algorithm

### Hash Map (Frequency Counter)

1.  **Count Frequencies**: Iterate through the input array `nums` and populate a hash map where the key is the number and the value is its frequency.
2.  **Iterate through Unique Numbers**: Iterate through the entries of the hash map.
    -   If `k > 0`, check if the map contains `key + k`. If it does, it means we found a valid unique pair `(key, key + k)`. We increment our pair count.
    -   If `k == 0`, check if the frequency of the current `key` is `>= 2`. If it is, we found a valid pair `(key, key)`. We increment our pair count.
3.  **Return Count**: After checking all unique numbers in the map, return the total count of pairs found.

---

## Complexity Analysis

-   **Time Complexity**: $\mathcal{O}(N)$
    -   We iterate through the array once to build the frequency map, taking $\mathcal{O}(N)$ time.
    -   Then we iterate through the unique elements in the map. In the worst case, this is $\mathcal{O}(N)$ iterations. Inside the loop, hash map lookups take $\mathcal{O}(1)$ time on average. Thus, the total time complexity is $\mathcal{O}(N)$.
-   **Space Complexity**: $\mathcal{O}(N)$
    -   The space is dominated by the hash map, which in the worst case (all unique elements) will store $N$ key-value pairs, taking $\mathcal{O}(N)$ space.
