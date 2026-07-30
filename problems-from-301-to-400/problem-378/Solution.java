class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }

        // dp[j] stores the length of the longest palindromic subsequence for s[i..j]
        int[] dp = new int[n];

        // Process from right to left to optimize space to O(N)
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1; // Base case: single character is a palindrome of length 1
            int prev = 0; // Represents dp[i + 1][j - 1]

            for (int j = i + 1; j < n; j++) {
                int temp = dp[j]; // Store dp[i + 1][j] before updating dp[j]

                if (s.charAt(i) == s.charAt(j)) {
                    dp[j] = prev + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                prev = temp; // Carry forward dp[i + 1][j] to become dp[i + 1][j - 1] for next j
            }
        }

        return dp[n - 1];
    }
}