class Solution {
    public int minDistance(String word1, String word2) {
        // Fast path for identical strings
        if (word1.equals(word2)) {
            return 0;
        }

        // Ensure word2 is the shorter string to minimize space to O(min(m, n))
        if (word1.length() < word2.length()) {
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }

        int m = word1.length();
        int n = word2.length();
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();

        // dp[j] stores the length of the Longest Common Subsequence (LCS)
        // of word1[0...i-1] and word2[0...j-1]
        int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            int prev = 0; // Represents dp[i-1][j-1]
            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Holds dp[i-1][j] which becomes dp[i-1][j-1] in the next iteration
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = prev + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }

        int lcs = dp[n];
        return m + n - 2 * lcs;
    }
}