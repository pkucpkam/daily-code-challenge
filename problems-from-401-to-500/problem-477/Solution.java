class Solution {
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] last = new int[10];

        // Ghi nhận vị trí xuất hiện cuối cùng của từng chữ số (0 - 9)
        for (int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }

        // Quét từ trái sang phải để tìm chữ số đầu tiên có thể hoán đổi với một chữ số lớn hơn
        for (int i = 0; i < digits.length; i++) {
            // Thử các chữ số lớn hơn chữ số hiện tại, ưu tiên từ 9 giảm dần
            for (int d = 9; d > digits[i] - '0'; d--) {
                // Nếu chữ số d tồn tại và xuất hiện ở phía sau vị trí i
                if (last[d] > i) {
                    // Hoán đổi chữ số tại i với chữ số d tại vị trí xuất hiện cuối cùng
                    char temp = digits[i];
                    digits[i] = digits[last[d]];
                    digits[last[d]] = temp;

                    return Integer.parseInt(new String(digits));
                }
            }
        }

        // Không có phép hoán đổi nào làm số lớn hơn
        return num;
    }
}
