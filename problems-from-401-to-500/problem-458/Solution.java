class Solution {
    public double findMaxAverage(int[] nums, int k) {
        // Tính tổng của cửa sổ k phần tử đầu tiên
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
        }

        int maxSum = currentSum;

        // Trượt cửa sổ qua phần còn lại của mảng
        for (int i = k; i < nums.length; i++) {
            currentSum += nums[i] - nums[i - k];
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        // Ép kiểu double để thực hiện phép chia số thực chính xác
        return (double) maxSum / k;
    }
}