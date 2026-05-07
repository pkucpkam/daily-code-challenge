class Solution {
    public boolean increasingTriplet(int[] nums) {
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int n : nums) {
            if (n <= first) {
                first = n; // smallest so far
            } else if (n <= second) {
                second = n; // second smallest so far
            } else {
                // n > first and n > second
                return true;
            }
        }

        return false;
    }
}