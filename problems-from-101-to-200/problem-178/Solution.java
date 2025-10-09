import java.util.*;

class Solution {
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;
        return helper(s1, s2, new HashMap<>());
    }

    private boolean helper(String a, String b, Map<String, Boolean> memo) {
        String key = a + "#" + b;
        if (memo.containsKey(key))
            return memo.get(key);
        if (a.equals(b)) {
            memo.put(key, true);
            return true;
        }
        if (!sameCharMultiset(a, b)) {
            memo.put(key, false);
            return false;
        }

        int n = a.length();
        for (int i = 1; i < n; i++) {
            if (helper(a.substring(0, i), b.substring(0, i), memo)
                    && helper(a.substring(i), b.substring(i), memo)) {
                memo.put(key, true);
                return true;
            }
            if (helper(a.substring(0, i), b.substring(n - i), memo)
                    && helper(a.substring(i), b.substring(0, n - i), memo)) {
                memo.put(key, true);
                return true;
            }
        }

        memo.put(key, false);
        return false;
    }

    private boolean sameCharMultiset(String a, String b) {
        int[] cnt = new int[26];
        for (int i = 0; i < a.length(); i++) {
            cnt[a.charAt(i) - 'a']++;
            cnt[b.charAt(i) - 'a']--;
        }
        for (int c : cnt)
            if (c != 0)
                return false;
        return true;
    }
}