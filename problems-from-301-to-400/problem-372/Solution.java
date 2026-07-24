import java.util.Random;

class Solution {

    private int[][] rects;
    private int[] pref;
    private int totalPoints;
    private Random random;

    public Solution(int[][] rects) {
        this.rects = rects;
        this.pref = new int[rects.length];
        this.random = new Random();
        int sum = 0;
        for (int i = 0; i < rects.length; i++) {
            int pts = (rects[i][2] - rects[i][0] + 1) * (rects[i][3] - rects[i][1] + 1);
            sum += pts;
            pref[i] = sum;
        }
        this.totalPoints = sum;
    }

    public int[] pick() {
        int target = random.nextInt(totalPoints);
        int idx = binarySearch(target);
        int[] rect = rects[idx];
        int x = rect[0] + random.nextInt(rect[2] - rect[0] + 1);
        int y = rect[1] + random.nextInt(rect[3] - rect[1] + 1);
        return new int[]{x, y};
    }

    private int binarySearch(int target) {
        int low = 0, high = pref.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (target < pref[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(rects);
 * int[] param_1 = obj.pick();
 */