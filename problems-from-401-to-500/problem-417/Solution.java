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