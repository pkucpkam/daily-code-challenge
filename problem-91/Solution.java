import java.util.Arrays;

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int contentChildren = 0;
        int i = 0, j = 0;

        while (i < g.length && j < s.length) {
            if (g[i] <= s[j]) {
                contentChildren++;
                i++;
            }
            j++; 
        }

        return contentChildren;
    }
}