import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];
        backtrack(0, n, board, cols, diag1, diag2, res);
        return res;
    }

    private void backtrack(int row, int n, char[][] board, boolean[] cols, boolean[] diag1, boolean[] diag2, List<List<String>> res) {
        if (row == n) {
            List<String> solution = new ArrayList<>();
            for (char[] r : board) solution.add(new String(r));
            res.add(solution);
            return;
        }
        for (int col = 0; col < n; col++) {
            if (cols[col] || diag1[row + col] || diag2[row - col + n - 1]) continue;
            board[row][col] = 'Q';
            cols[col] = diag1[row + col] = diag2[row - col + n - 1] = true;
            backtrack(row + 1, n, board, cols, diag1, diag2, res);
            board[row][col] = '.';
            cols[col] = diag1[row + col] = diag2[row - col + n - 1] = false;
        }
    }
}