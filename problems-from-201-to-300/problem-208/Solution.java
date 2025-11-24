import java.util.*;

class Solution {
    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                pal[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || pal[i + 1][j - 1]);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        LinkedList<String> cur = new LinkedList<>();
        dfs(0, s, pal, cur, ans);
        return ans;
    }

    private void dfs(int start, String s, boolean[][] pal, LinkedList<String> cur, List<List<String>> ans) {
        if (start == s.length()) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (pal[start][end]) {
                cur.add(s.substring(start, end + 1));
                dfs(end + 1, s, pal, cur, ans);
                cur.removeLast();
            }
        }
    }
}