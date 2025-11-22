class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;
        java.util.Deque<int[]> q = new java.util.ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') { board[i][0] = 'S'; q.offer(new int[]{i, 0}); }
            if (board[i][n - 1] == 'O') { board[i][n - 1] = 'S'; q.offer(new int[]{i, n - 1}); }
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') { board[0][j] = 'S'; q.offer(new int[]{0, j}); }
            if (board[m - 1][j] == 'O') { board[m - 1][j] = 'S'; q.offer(new int[]{m - 1, j}); }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int r = p[0], c = p[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'O') {
                    board[nr][nc] = 'S';
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'S') board[i][j] = 'O';
            }
        }
    }
}


