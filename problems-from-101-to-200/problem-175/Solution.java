class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                heights[c] = (matrix[r][c] == '1') ? heights[c] + 1 : 0;
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>(); // indices, increasing heights
        int max = 0;
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && h < heights[stack.peekLast()]) {
                int height = heights[stack.pollLast()];
                int left = stack.isEmpty() ? -1 : stack.peekLast();
                int width = i - left - 1;
                max = Math.max(max, height * width);
            }
            stack.offerLast(i);
        }
        return max;
    }
}