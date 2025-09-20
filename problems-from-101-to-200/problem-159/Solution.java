class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j-1];
        }
        
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i-1][0];
        }
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }
        
        return grid[m-1][n-1];
    }
    
    public boolean isNumber(String s) {
        boolean numSeen = false, dotSeen = false, eSeen = false;
        int countPlusMinus = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                numSeen = true;
            } else if (c == '.') {
                if (eSeen || dotSeen) return false;
                dotSeen = true;
            } else if (c == 'e' || c == 'E') {
                if (eSeen || !numSeen) return false; 
                eSeen = true;
                numSeen = false; 
            } else if (c == '+' || c == '-') {
                if (countPlusMinus == 2) return false; 
                if (i > 0 && (s.charAt(i-1) != 'e' && s.charAt(i-1) != 'E')) return false; 
                if (i == s.length() - 1) return false; 
                countPlusMinus++;
            } else {
                return false; 
            }
        }
        
        return numSeen; 
    }
}