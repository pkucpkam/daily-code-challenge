class Solution {
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0)
            return 0;
        int n = ratings.length;
        long total = 1;
        int up = 0;
        int down = 0;
        int peak = 0;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;
                total += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                total += 1;
            } else {
                up = 0;
                down++;
                total += down;
                if (down > peak) {
                    total += 1;
                }
            }
        }

        return (int) total;
    }
}