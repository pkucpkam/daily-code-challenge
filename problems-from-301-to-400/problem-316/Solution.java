import java.util.Random;

class Solution {
    private final int[] original;
    private final Random random;

    public Solution(int[] nums) {
        this.original = nums.clone();
        this.random = new Random();
    }

    public int[] reset() {
        return original.clone();
    }

    public int[] shuffle() {
        int[] shuffled = original.clone();

        for (int i = 0; i < shuffled.length; i++) {
            int randomIndex = i + random.nextInt(shuffled.length - i);
            int temp = shuffled[i];
            shuffled[i] = shuffled[randomIndex];
            shuffled[randomIndex] = temp;
        }

        return shuffled;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
