347. Top K Frequent Elements
Medium
Topics
premium lock icon
Companies
Given an integer array `nums` and an integer `k`, return the `k` most frequent elements. You may return the answer in any order.

 
## Examples

Example 1

Input: `nums = [1,1,1,2,2,3]`, `k = 2`

Output: `[1,2]`

Example 2

Input: `nums = [1]`, `k = 1`

Output: `[1]`

Example 3

Input: `nums = [1,2,1,2,1,2,3,1,3,2]`, `k = 2`

Output: `[1,2]`

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- `k` is in the range `[1, number of unique elements in the array]`
- It is guaranteed that the answer is unique

## Follow-up

Can you design an algorithm with time complexity better than `O(n log n)`, where `n` is the length of `nums`?

---

If you want, I can also add a concise solution (bucket sort or heap-based) and sample code in Python, C++, or Java. Which language do you prefer?
Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.