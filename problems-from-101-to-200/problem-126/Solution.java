import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (words.length == 0 || s.length() == 0) return res;

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        if (s.length() < totalLen) return res;

        Map<String, Integer> wordMap = new HashMap<>();
        for (String w : words) wordMap.put(w, wordMap.getOrDefault(w, 0) + 1);

        for (int i = 0; i < wordLen; i++) {
            int left = i, right = i, count = 0;
            Map<String, Integer> seen = new HashMap<>();
            while (right + wordLen <= s.length()) {
                String w = s.substring(right, right + wordLen);
                right += wordLen;
                if (wordMap.containsKey(w)) {
                    seen.put(w, seen.getOrDefault(w, 0) + 1);
                    count++;
                    while (seen.get(w) > wordMap.get(w)) {
                        String leftWord = s.substring(left, left + wordLen);
                        seen.put(leftWord, seen.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }
                    if (count == wordCount) {
                        res.add(left);
                    }
                } else {
                    seen.clear();
                    count = 0;
                    left = right;
                }
            }
        }
        return res;
    }
}