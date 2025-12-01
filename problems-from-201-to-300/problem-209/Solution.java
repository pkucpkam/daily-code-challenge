class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                pal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || pal[i + 1][j - 1]);
            }
        }

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (pal[0][i]) {
                dp[i] = 0;
                continue;
            }
            int minCuts = Integer.MAX_VALUE;
            for (int j = 1; j <= i; j++) {
                if (pal[j][i]) {
                    minCuts = Math.min(minCuts, dp[j - 1] + 1);
                }
            }
            dp[i] = minCuts;
        }
        return dp[n - 1];
    }
}