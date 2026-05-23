class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            int count = countLessOrEqual(matrix, mid);
            if (count >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    private int countLessOrEqual(int[][] matrix, int target) {
        int n = matrix.length;
        int count = 0;
        int r = n - 1; // Start from bottom-left corner
        int c = 0;
        
        while (r >= 0 && c < n) {
            if (matrix[r][c] <= target) {
                count += r + 1;
                c++;
            } else {
                r--;
            }
        }
        
        return count;
    }
}