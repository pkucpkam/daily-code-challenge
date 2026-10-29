class Solution {
    public boolean isPossible(int[] nums) {
        int n = nums.length;
        // Bất kỳ phân hoạch hợp lệ nào cũng yêu cầu mỗi dãy con có độ dài >= 3
        if (n < 3) {
            return false;
        }

        // p1: Số dãy con kết thúc tại phần tử trước (prev) có độ dài = 1
        // p2: Số dãy con kết thúc tại phần tử trước (prev) có độ dài = 2
        // p3: Số dãy con kết thúc tại phần tử trước (prev) có độ dài >= 3
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;

        int prev = 0;
        boolean hasPrev = false;

        int i = 0;
        while (i < n) {
            int curr = nums[i];
            int count = 0;
            // Đếm tần suất xuất hiện của giá trị curr trong mảng đã sắp xếp
            while (i < n && nums[i] == curr) {
                count++;
                i++;
            }

            if (hasPrev && curr != prev + 1) {
                // Xuất hiện khoảng trống (curr > prev + 1):
                // Các dãy con kết thúc tại prev không thể kéo dài tiếp được nữa.
                // Nếu còn dãy nào chưa đủ độ dài 3 (p1 > 0 hoặc p2 > 0) -> thất bại.
                if (p1 > 0 || p2 > 0) {
                    return false;
                }
                p1 = 0;
                p2 = 0;
                p3 = 0;
            }

            // Bắt buộc phải có đủ phần tử curr để ghép vào các dãy có độ dài 1 và 2
            if (count < p1 + p2) {
                return false;
            }

            int c2 = p1; // Các dãy độ dài 1 nhận curr trở thành độ dài 2
            int rem = count - (p1 + p2);

            // Tham lam tối ưu: Ưu tiên kéo dài các dãy đã hoàn thiện (độ dài >= 3)
            // Việc kéo dài dãy >= 3 an toàn hơn bắt đầu dãy mới vì không tạo thêm gánh nặng cho tương lai.
            int c3 = p2 + Math.min(rem, p3);

            // Số phần tử curr còn thừa sau khi đã nối hết vào dãy >= 3 sẽ tạo dãy mới độ dài 1
            int c1 = Math.max(0, rem - p3);

            p1 = c1;
            p2 = c2;
            p3 = c3;
            prev = curr;
            hasPrev = true;
        }

        // Sau khi duyệt hết mảng, không được còn bất kỳ dãy con nào có độ dài < 3
        return p1 == 0 && p2 == 0;
    }
}