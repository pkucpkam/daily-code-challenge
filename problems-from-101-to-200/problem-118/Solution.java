import java.util.*;


class Solution {
    private static final String[] KEYS = {
        "",    "",    "abc", "def", "ghi", "jkl",
        "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) return res;
        backtrack(res, digits, 0, new StringBuilder());
        return res;
    }

    private void backtrack(List<String> res, String digits, int idx, StringBuilder sb) {
        if (idx == digits.length()) {
            res.add(sb.toString());
            return;
        }
        String letters = KEYS[digits.charAt(idx) - '0'];
        for (char c : letters.toCharArray()) {
            sb.append(c);
            backtrack(res, digits, idx + 1, sb);
            sb.deleteCharAt(sb.length() - 1); 
        }
    }
}