import java.util.*;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        int n = s.length();
        if (n < 4 || n > 12)
            return res; 
        backtrack(s, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(String s, int start, List<String> parts, List<String> res) {
        int remainingParts = 4 - parts.size();
        int remainingChars = s.length() - start;
        if (remainingChars < remainingParts || remainingChars > remainingParts * 3)
            return; 

        if (parts.size() == 4) {
            if (start == s.length())
                res.add(String.join(".", parts));
            return;
        }

        for (int len = 1; len <= 3; len++) {
            if (start + len > s.length())
                break;
            if (len > 1 && s.charAt(start) == '0')
                break;
            String seg = s.substring(start, start + len);
            int val = Integer.parseInt(seg);
            if (val > 255)
                continue;
            parts.add(seg);
            backtrack(s, start + len, parts, res);
            parts.remove(parts.size() - 1);
        }
    }
}