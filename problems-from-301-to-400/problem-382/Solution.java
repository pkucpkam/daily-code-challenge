class Solution {
    public int findLUSlength(String[] strs) {
        int result = -1;

        for (int i = 0; i < strs.length; i++) {
            boolean isCommon = false;

            for (int j = 0; j < strs.length; j++) {
                if (i != j && isSubsequence(strs[i], strs[j])) {
                    isCommon = true;
                    break;
                }
            }

            if (!isCommon) {
                result = Math.max(result, strs[i].length());
            }
        }

        return result;
    }

    private boolean isSubsequence(String s, String t) {
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) i++;
        }
        return i == s.length();
    }
}