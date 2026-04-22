class Solution {
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(board, row, col);

                if (board[row][col] == 1 && (liveNeighbors == 2 || liveNeighbors == 3)) {
                    board[row][col] = 3;
                }

                if (board[row][col] == 0 && liveNeighbors == 3) {
                    board[row][col] = 2;
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] >>= 1;
            }
        }
    }

    private int countLiveNeighbors(int[][] board, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;
        int liveNeighbors = 0;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue;
                }

                int nextRow = row + dRow;
                int nextCol = col + dCol;

                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols) {
                    liveNeighbors += board[nextRow][nextCol] & 1;
                }
            }
        }

        return liveNeighbors;
    }
}