class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int num = 1, left = 0, right = n - 1, top = 0, bottom = n - 1;
        while (left <= right && top <= bottom) {
            for (int j = left; j <= right; j++)
                res[top][j] = num++;
            top++;
            for (int i = top; i <= bottom; i++)
                res[i][right] = num++;
            right--;
            if (top <= bottom) {
                for (int j = right; j >= left; j--)
                    res[bottom][j] = num++;
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--)
                    res[i][left] = num++;
                left++;
            }
        }
        return res;
    }
}