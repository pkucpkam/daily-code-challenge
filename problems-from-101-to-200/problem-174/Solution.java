class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && h < heights[stack.peekLast()]) {
                int height = heights[stack.pollLast()];
                int leftIndex = stack.isEmpty() ? -1 : stack.peekLast();
                int width = i - leftIndex - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.offerLast(i);
        }

        return maxArea;
    }
}