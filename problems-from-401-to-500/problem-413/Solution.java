class Solution {
    /**
     * Reshapes an m x n matrix into an r x c matrix in row-traversing order.
     * 
     * Time Complexity: O(m * n) - single-pass traversal of all matrix elements.
     * Space Complexity: O(r * c) - space allocated for the reshaped matrix.
     */
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length;
        int n = mat[0].length;

        // If reshaping is not possible due to element count mismatch, return original matrix
        if (m * n != r * c) {
            return mat;
        }

        int[][] result = new int[r][c];

        // Directly map 1D index i to 2D coordinates in both mat and result
        for (int i = 0; i < m * n; i++) {
            result[i / c][i % c] = mat[i / n][i % n];
        }

        return result;
    }
}