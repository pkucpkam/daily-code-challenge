import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord))
            return 0;

        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visited = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);
        visited.add(beginWord);
        int len = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
            }

            Set<String> nextLevel = new HashSet<>();
            for (String word : beginSet) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char old = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old)
                            continue;
                        chars[i] = c;
                        String nw = new String(chars);
                        if (endSet.contains(nw))
                            return len + 1;
                        if (dict.contains(nw) && !visited.contains(nw)) {
                            nextLevel.add(nw);
                            visited.add(nw);
                        }
                    }
                    chars[i] = old;
                }
            }
            beginSet = nextLevel;
            len++;
        }

        return 0;
    }
}