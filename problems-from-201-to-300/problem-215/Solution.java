class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null)
            return false;
        int n = s.length();
        Set<String> dict = new HashSet<>(wordDict);
        int maxLen = 0;
        for (String w : wordDict)
            if (w.length() > maxLen)
                maxLen = w.length();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            int start = Math.max(0, i - maxLen);
            for (int j = start; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}