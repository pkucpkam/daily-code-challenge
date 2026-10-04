class Solution {
    /**
     * Best Solution: Đếm tần suất (Frequency Counting) + Công thức Khung Thời gian Tham lam (Greedy Math / Bucket Model).
     * 
     * Time Complexity: O(tasks.length) - Duyệt qua mảng tasks một lần để đếm tần số, sau đó duyệt mảng tần số kích thước 26.
     * Space Complexity: O(1) - Sử dụng mảng đếm tần suất cố định 26 phần tử (bảng chữ cái tiếng Anh in hoa).
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        if (n == 0) {
            return tasks.length;
        }

        // Bước 1: Đếm tần suất xuất hiện của từng loại task (A - Z)
        int[] freq = new int[26];
        int maxFreq = 0;

        for (char task : tasks) {
            int count = ++freq[task - 'A'];
            if (count > maxFreq) {
                maxFreq = count;
            }
        }

        // Bước 2: Đếm số lượng task có cùng tần suất lớn nhất (maxFreq)
        int countMaxFreq = 0;
        for (int f : freq) {
            if (f == maxFreq) {
                countMaxFreq++;
            }
        }

        // Bước 3: Tính toán khoảng thời gian tối thiểu dựa trên mô hình khung (Bucket model)
        // Cần (maxFreq - 1) khung hoàn chỉnh, mỗi khung có kích thước (n + 1).
        // Khung cuối cùng chỉ chứa các task có tần suất cực đại (countMaxFreq).
        int minIntervals = (maxFreq - 1) * (n + 1) + countMaxFreq;

        // Nếu số lượng task quá nhiều, các ô idle được lấp đầy và mở rộng mà không phát sinh thêm idle
        return Math.max(tasks.length, minIntervals);
    }
}