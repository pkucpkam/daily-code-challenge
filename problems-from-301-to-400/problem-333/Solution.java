import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return result;
        }

        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        // DFS from top and bottom borders
        for (int c = 0; c < n; c++) {
            dfs(heights, 0, c, pacific, heights[0][c]);
            dfs(heights, m - 1, c, atlantic, heights[m - 1][c]);
        }

        // DFS from left and right borders
        for (int r = 0; r < m; r++) {
            dfs(heights, r, 0, pacific, heights[r][0]);
            dfs(heights, r, n - 1, atlantic, heights[r][n - 1]);
        }

        // Find common cells
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (pacific[r][c] && atlantic[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    private void dfs(int[][] heights, int r, int c, boolean[][] ocean, int prevHeight) {
        int m = heights.length;
        int n = heights[0].length;

        // Base cases: out of bounds, already visited, or height is less than previous (water can't flow uphill relative to the ocean perspective)
        if (r < 0 || r >= m || c < 0 || c >= n || ocean[r][c] || heights[r][c] < prevHeight) {
            return;
        }

        ocean[r][c] = true;

        for (int[] dir : DIRECTIONS) {
            dfs(heights, r + dir[0], c + dir[1], ocean, heights[r][c]);
        }
    }
}