import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) {
            return result;
        }

        int[] hash = new int[26];
        for (char c : p.toCharArray()) {
            hash[c - 'a']++;
        }

        int left = 0, right = 0, count = p.length();
        
        while (right < s.length()) {
            if (hash[s.charAt(right) - 'a'] >= 1) {
                count--;
            }
            hash[s.charAt(right) - 'a']--;
            right++;
            
            if (count == 0) {
                result.add(left);
            }
            
            if (right - left == p.length()) {
                if (hash[s.charAt(left) - 'a'] >= 0) {
                    count++;
                }
                hash[s.charAt(left) - 'a']++;
                left++;
            }
        }
        return result;
    }
}