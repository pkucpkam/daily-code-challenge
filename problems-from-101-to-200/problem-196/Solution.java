class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if (n == 0) return 1;
        if (m < n) return 0;

        long[] dp = new long[n + 1];
        dp[0] = 1; 

        for (int i = 1; i <= m; i++) {
            char cs = s.charAt(i - 1);
            for (int j = n; j >= 1; j--) {
                if (t.charAt(j - 1) == cs) dp[j] += dp[j - 1];
            }
        }

        return (int) dp[n];
    }
}