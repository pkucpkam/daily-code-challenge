class Solution {
    public int numTrees(int n) {
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            long ways = 0;
            for (int root = 1; root <= i; root++) {
                ways += dp[root - 1] * dp[i - root];
            }
            dp[i] = ways;
        }
        return (int) dp[n];
    }
}