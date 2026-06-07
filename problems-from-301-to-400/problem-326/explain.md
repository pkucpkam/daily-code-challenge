# Explanation: 398. Random Pick Index

## Approach: Hash Map (Optimal Time)
The most time-efficient way to solve this problem when there are many queries (`pick` calls) is to pre-process the array. We can use a Hash Map where the keys are the unique numbers from the array and the values are lists of indices where these numbers occur.

### Algorithm
1. **Initialization (`Solution` constructor):**
   - Iterate through the given array `nums`.
   - For each number, add its current index to the corresponding list in the hash map.
   - We also initialize a `Random` object to handle generating random numbers later.

2. **Picking (`pick` method):**
   - Given a `target`, we retrieve the list of indices from our hash map.
   - We then generate a random integer bounded by the size of this list.
   - We return the index located at this randomly generated position within the list.

### Complexity Analysis
- **Time Complexity:**
  - `Solution(int[] nums)`: **O(N)** where N is the number of elements in `nums`. We traverse the array exactly once to populate the Hash Map. Adding an element to an `ArrayList` and inserting into a `HashMap` both take O(1) time on average.
  - `pick(int target)`: **O(1)** time. Looking up the list of indices in the hash map takes O(1) time, and picking a random index from the list takes O(1) time.

- **Space Complexity:**
  - **O(N)**. The Hash Map needs to store every index of the array across the various lists. In the worst case (all numbers are unique), there are N keys with lists of size 1. In another extreme (all numbers are the same), there is 1 key with a list of size N. Either way, the total space required scales linearly with the input array's size.

## Alternative Approach: Reservoir Sampling
If the array was extremely large or coming as a continuous stream where we couldn't fit it into memory (O(1) Space required), **Reservoir Sampling** would be the preferred approach.
Instead of precomputing, you iterate through the array on every `pick` call. If `nums[i] == target`, you pick this index with a probability of `1 / count` (where `count` is the number of times we've seen `target` so far). However, this would make the `pick` time **O(N)**, which might lead to Time Limit Exceeded when `pick` is called heavily (`10^4` times). For the constraints of this LeetCode problem, the Hash Map approach guarantees optimal performance.
