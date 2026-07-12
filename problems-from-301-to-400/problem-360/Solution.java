class Solution {
    public int findSubstringInWraproundString(String s) {
        int[] dp = new int[26];
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (s.charAt(i) - s.charAt(i - 1) == 1 || (s.charAt(i - 1) == 'z' && s.charAt(i) == 'a'))) {
                maxLength++;
            } else {
                maxLength = 1;
            }
            
            int index = s.charAt(i) - 'a';
            dp[index] = Math.max(dp[index], maxLength);
        }
        
        int totalSubstrings = 0;
        for (int i = 0; i < 26; i++) {
            totalSubstrings += dp[i];
        }
        
        return totalSubstrings;
    }
}