class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, row, col, c)) {
                            board[row][col] = c;
                            if (solve(board))
                                return true;
                            board[row][col] = '.'; 
                        }
                    }
                    return false; 
                }
            }
        }
        return true; 
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == c || board[i][col] == c)
                return false;
            int boxRow = 3 * (row / 3) + i / 3;
            int boxCol = 3 * (col / 3) + i % 3;
            if (board[boxRow][boxCol] == c)
                return false;
        }
        return true;
    }
}


// Better Solution 

/*
   class Solution {
    private final int[] row = new int[9];
    private final int[] col = new int[9];
    private final int[] box = new int[9];

    private final int[] empties = new int[81];
    private int emptyCount = 0;
    private static final int ALL= (1<<9) - 1;

    public void solveSudoku(char[][] board){
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                char ch = board[r][c];

                if(ch == '.'){
                    empties[emptyCount++] = r*9 + c;
                }else{
                    int d = ch - '1';
                    int bit = 1 << d;
                    row[r] |= bit;
                    col[c] |= bit;
                    box[boxId(r,c)] |= bit;
                }
            }
        }
        backTrack(board);
    }

    private boolean backTrack(char[][] board){
        if(emptyCount == 0) return true;

        int bestIdx = -1, bestMask = 0, bestCount = 10;
        for(int i = 0; i< emptyCount; i++){
            int pos = empties[i];
            int r = pos / 9 ,c = pos % 9;
            int mask = candidatesMask(r,c);
            int cnt = Integer.bitCount(mask);

            if(cnt == 0) return false;
            if(cnt < bestCount){
                bestCount = cnt;
                bestMask = mask;
                bestIdx = i;

                if(cnt == 1) break;
            }
        }

        swapEmpties(bestIdx, emptyCount -1);
        int pos = empties[emptyCount -1];
        int r = pos / 9 ,c = pos % 9;
        int b =boxId(r,c);
        emptyCount--;

        for(int m = bestMask; m != 0 ; m &= (m -1)){
            int lsb = m & -m;
            int d = Integer.numberOfTrailingZeros(lsb);
            int bit = 1 << d;

            board[r][c] = (char) ('1' + d);
            row[r] |= bit; col[c] |= bit; box[b] |= bit;

            if(backTrack(board)) return true;

            row[r] ^= bit; col[c] ^= bit; box[b] ^= bit;
            board[r][c] = '.';
        }
        empties[emptyCount] = pos;
        swapEmpties(bestIdx, emptyCount);
        emptyCount++;

        return false;
    }

    private int boxId(int r, int c){
        return (r / 3) * 3 +(c / 3);
    }

    private int candidatesMask(int r, int c){
        return ALL & ~(row[r] | col[c] | box[boxId (r,c)]);
    }

    private void swapEmpties(int i, int j){
        if(i == j) return;
        int tmp =empties[i];
        empties[i] = empties[j];
        empties[j] = tmp;
    }
}
*/
