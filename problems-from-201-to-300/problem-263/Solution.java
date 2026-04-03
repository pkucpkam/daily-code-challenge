class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // dp[j] stores the maximal square side ending at current row and column j - 1.
        int[] dp = new int[n + 1];
        int maxSide = 0;

        for (int i = 1; i <= m; i++) {
            int prev = 0; // dp value from top-left (previous row, previous column)
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];

                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), prev) + 1;
                    maxSide = Math.max(maxSide, dp[j]);
                } else {
                    dp[j] = 0;
                }

                prev = temp;
            }
        }

        return maxSide * maxSide;
    }
}