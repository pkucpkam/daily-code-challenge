class Solution {
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 2][n + 2];
        
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                int minCost = Integer.MAX_VALUE;
                
                int start = i + (len - 1) / 2;
                for (int k = start; k < j; k++) {
                    int cost = k + Math.max(dp[i][k - 1], dp[k + 1][j]);
                    minCost = Math.min(minCost, cost);
                }
                
                dp[i][j] = (len == 2) ? i : minCost;
            }
        }
        
        return dp[1][n];
    }
}