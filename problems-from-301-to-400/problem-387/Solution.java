import java.util.Random;

class Solution {
    private int[] prefixSums;
    private int totalSum;
    private Random random;

    public Solution(int[] w) {
        this.prefixSums = new int[w.length];
        this.random = new Random();
        
        int currentSum = 0;
        for (int i = 0; i < w.length; i++) {
            currentSum += w[i];
            this.prefixSums[i] = currentSum;
        }
        this.totalSum = currentSum;
    }
    
    public int pickIndex() {
        // Generate a random target from 1 to totalSum inclusive
        int target = this.random.nextInt(this.totalSum) + 1;

        // Binary search to find the target in prefixSums
        int left = 0;
        int right = this.prefixSums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (this.prefixSums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */