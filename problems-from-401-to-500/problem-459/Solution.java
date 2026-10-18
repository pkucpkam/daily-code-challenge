class Solution {
    public int[] findErrorNums(int[] nums) {
        int dup = -1;
        int missing = -1;

        // Vòng 1: Tìm số bị lặp lại (duplicate) bằng kỹ thuật đổi dấu
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]);
            int index = val - 1;

            if (nums[index] < 0) {
                dup = val; // Đã từng được đổi dấu trước đó => số lặp lại
            } else {
                nums[index] = -nums[index]; // Đánh dấu đã xuất hiện
            }
        }

        // Vòng 2: Tìm số bị thiếu (missing) - vị trí duy nhất còn mang giá trị dương
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                missing = i + 1; // Chỉ số i chưa từng được ghé thăm => số (i + 1) bị thiếu
                break;
            }
        }

        return new int[]{dup, missing};
    }
}