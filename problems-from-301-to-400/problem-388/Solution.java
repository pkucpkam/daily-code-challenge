class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int r = click[0];
        int c = click[1];
        
        // If the first click is a mine, game over.
        if (board[r][c] == 'M') {
            board[r][c] = 'X';
            return board;
        }
        
        // Otherwise, it is an 'E'. Perform DFS.
        dfs(board, r, c);
        
        return board;
    }
    
    private void dfs(char[][] board, int r, int c) {
        int m = board.length;
        int n = board[0].length;
        
        // Base case / boundary check / unrevealed empty square check
        if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'E') {
            return;
        }
        
        // Count adjacent mines
        int mines = countMines(board, r, c, m, n);
        
        if (mines > 0) {
            // Bordering at least one mine, reveal number and stop recursion
            board[r][c] = (char) (mines + '0');
        } else {
            // Completely empty, mark as 'B' and recurse on all 8 neighbors
            board[r][c] = 'B';
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    dfs(board, r + i, c + j);
                }
            }
        }
    }
    
    private int countMines(char[][] board, int r, int c, int m, int n) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nr = r + i;
                int nc = c + j;
                // Only checking for 'M', as 'X' can only happen to the originally clicked cell
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'M') {
                    count++;
                }
            }
        }
        return count;
    }
}