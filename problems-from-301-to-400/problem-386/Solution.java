class Solution {
    public int countArrangement(int n) {
        int[] dp = new int[1 << n];
        dp[0] = 1;
        
        for (int mask = 0; mask < (1 << n); mask++) {
            int pos = Integer.bitCount(mask) + 1;
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) {
                    int num = i + 1;
                    if (num % pos == 0 || pos % num == 0) {
                        dp[mask | (1 << i)] += dp[mask];
                    }
                }
            }
        }
        
        return dp[(1 << n) - 1];
    }
}