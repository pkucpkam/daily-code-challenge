class Solution {
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        if (maxMove <= 0) {
            return 0;
        }

        final int MOD = 1_000_000_007;
        int[][] count = new int[m][n];
        count[startRow][startColumn] = 1;
        int totalPaths = 0;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Simulate step by step up to maxMove
        for (int step = 0; step < maxMove; step++) {
            int[][] nextCount = new int[m][n];

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    int ways = count[r][c];
                    if (ways > 0) {
                        for (int[] dir : dirs) {
                            int nr = r + dir[0];
                            int nc = c + dir[1];

                            if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                                totalPaths = (totalPaths + ways) % MOD;
                            } else {
                                nextCount[nr][nc] = (nextCount[nr][nc] + ways) % MOD;
                            }
                        }
                    }
                }
            }

            count = nextCount;
        }

        return totalPaths;
    }
}