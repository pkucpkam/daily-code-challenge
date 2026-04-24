class NumMatrix {
    private final int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        prefix = new int[rows + 1][cols + 1];

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                prefix[r][c] = matrix[r - 1][c - 1]
                        + prefix[r - 1][c]
                        + prefix[r][c - 1]
                        - prefix[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int r1 = row1;
        int c1 = col1;
        int r2 = row2 + 1;
        int c2 = col2 + 1;

        return prefix[r2][c2]
                - prefix[r1][c2]
                - prefix[r2][c1]
                + prefix[r1][c1];
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */