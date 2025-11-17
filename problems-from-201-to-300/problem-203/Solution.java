import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return Collections.emptyList();

        Map<String, List<String>> preds = new HashMap<>(); 
        preds.put(beginWord, new ArrayList<>());

        Set<String> current = new HashSet<>();
        current.add(beginWord);

        boolean found = false;
        Set<String> visited = new HashSet<>();

        while (!current.isEmpty() && !found) {
            Set<String> nextLevel = new HashSet<>();
            visited.addAll(current);

            for (String word : current) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    char old = chs[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) continue;
                        chs[i] = c;
                        String nei = new String(chs);
                        if (!dict.contains(nei) || visited.contains(nei)) continue;
                        preds.computeIfAbsent(nei, k -> new ArrayList<>()).add(word);
                        nextLevel.add(nei);
                        if (nei.equals(endWord)) found = true;
                    }
                    chs[i] = old;
                }
            }
            current = nextLevel;
        }

        List<List<String>> res = new ArrayList<>();
        if (!found) return res;

        LinkedList<String> path = new LinkedList<>();
        path.add(endWord);
        dfsBuild(endWord, beginWord, preds, path, res);
        return res;
    }

    private void dfsBuild(String word, String beginWord, Map<String, List<String>> preds,
                          LinkedList<String> path, List<List<String>> res) {
        if (word.equals(beginWord)) {
            List<String> one = new ArrayList<>(path);
            Collections.reverse(one);
            res.add(one);
            return;
        }
        List<String> prevs = preds.get(word);
        if (prevs == null) return;
        for (String p : prevs) {
            path.addLast(p);
            dfsBuild(p, beginWord, preds, path, res);
            path.removeLast();
        }
    }
}