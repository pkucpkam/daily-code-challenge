class Solution {
    private static final int[][] DIRECTIONS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public double knightProbability(int n, int k, int row, int column) {
        double[][] prevDp = new double[n][n];
        prevDp[row][column] = 1.0;

        for (int step = 0; step < k; step++) {
            double[][] currDp = new double[n][n];

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (prevDp[r][c] > 0.0) {
                        double prob = prevDp[r][c] / 8.0;
                        for (int[] dir : DIRECTIONS) {
                            int nr = r + dir[0];
                            int nc = c + dir[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                currDp[nr][nc] += prob;
                            }
                        }
                    }
                }
            }

            prevDp = currDp;
        }

        double totalProbability = 0.0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                totalProbability += prevDp[r][c];
            }
        }

        return totalProbability;
    }
}
