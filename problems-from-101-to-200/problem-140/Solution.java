class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*')
                dp[0][j] = dp[0][j - 1];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1), pc = p.charAt(j - 1);
                if (pc == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (pc == '?' || sc == pc) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }
}

// Better Solution 
// class Solution {
//     public boolean isMatch(String s, String p) {
//         int i = 0, j = 0;
//         int starIdx = -1, match = 0;
//         while (i < s.length()) {
//             if (j < p.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')) {
//                 i++;
//                 j++;
//             } else if (j < p.length() && p.charAt(j) == '*') {
//                 starIdx = j;
//                 match = i;
//                 j++;
//             } else if (starIdx != -1) {
//                 j = starIdx + 1;
//                 match++;
//                 i = match;
//             } else {
//                 return false;
//             }
//         }
//         while (j < p.length() && p.charAt(j) == '*') {
//             j++;
//         }
//         return j == p.length();
//     }
// }