class Solution {
    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length(), k);
    }

    private int helper(String s, int start, int end, int k) {
        if (end - start < k) {
            return 0;
        }

        int[] counts = new int[26];
        for (int i = start; i < end; i++) {
            counts[s.charAt(i) - 'a']++;
        }

        for (int i = start; i < end; i++) {
            if (counts[s.charAt(i) - 'a'] < k) {
                int nextStart = i + 1;
                while (nextStart < end && counts[s.charAt(nextStart) - 'a'] < k) {
                    nextStart++;
                }
                
                int left = helper(s, start, i, k);
                int right = helper(s, nextStart, end, k);
                return Math.max(left, right);
            }
        }

        return end - start;
    }
}