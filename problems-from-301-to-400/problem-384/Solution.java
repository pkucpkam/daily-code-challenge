import java.util.List;

class Solution {
    public String findLongestWord(String s, List<String> dictionary) {
        String longest = "";
        
        for (String word : dictionary) {
            int wordLen = word.length();
            int longestLen = longest.length();
            
            // Only check if word is longer, or same length but lexicographically smaller
            if (wordLen > longestLen || (wordLen == longestLen && word.compareTo(longest) < 0)) {
                if (isSubsequence(s, word)) {
                    longest = word;
                }
            }
        }
        
        return longest;
    }
    
    private boolean isSubsequence(String s, String word) {
        int i = 0, j = 0;
        int sLen = s.length(), wordLen = word.length();
        
        while (i < sLen && j < wordLen) {
            if (s.charAt(i) == word.charAt(j)) {
                j++;
            }
            i++;
        }
        
        return j == wordLen;
    }
}