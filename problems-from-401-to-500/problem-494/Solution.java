class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int m = grid.length;
        int n = grid[0].length;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c, m, n));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c, int m, int n) {
        // Kiểm tra biên ma trận và điều kiện dừng (nước hoặc ô đã thăm)
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] != 1) {
            return 0;
        }

        // Đánh dấu ô đã duyệt bằng cách chuyển 1 thành 0 (kỹ thuật "đánh chìm đảo")
        grid[r][c] = 0;
        int area = 1;

        // Loang sang 4 ô kề cạnh: dưới, trên, phải, trái
        area += dfs(grid, r + 1, c, m, n);
        area += dfs(grid, r - 1, c, m, n);
        area += dfs(grid, r, c + 1, m, n);
        area += dfs(grid, r, c - 1, m, n);

        return area;
    }
}
