class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        
        // Try starting from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        // Base case: found the word
        if (index == word.length()) return true;
        
        // Boundary check and character match
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // Mark current cell as visited
        char temp = board[i][j];
        board[i][j] = '*'; // or any invalid character
        
        // Explore 4 directions
        boolean found = dfs(board, word, i + 1, j, index + 1) ||  // down
                       dfs(board, word, i - 1, j, index + 1) ||   // up
                       dfs(board, word, i, j + 1, index + 1) ||   // right
                       dfs(board, word, i, j - 1, index + 1);     // left
        
        // Backtrack: restore the original character
        board[i][j] = temp;
        
        return found;
    }
}