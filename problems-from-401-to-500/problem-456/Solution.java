class Solution {
    /**
     * Best Solution: Phân tích cú pháp trực tiếp duyệt 1 lượt (Single-Pass Parsing).
     *
     * Tư duy giải thuật:
     * - Mọi phương trình bậc nhất một ẩn đều có thể đưa về dạng: A * x + B = 0  <=>  A * x = -B.
     * - Duyệt qua chuỗi từ trái sang phải với 1 con trỏ i:
     *   + Khi đứng ở vế trái (trước dấu '='): các số hạng mang trọng số side = +1.
     *   + Khi bước qua dấu '=': các số hạng mang trọng số side = -1 (tương đương chuyển vế đổi dấu).
     *   + Đọc dấu (+ / -) và phần giá trị số đi kèm.
     *   + Nếu gặp ký tự 'x':
     *       * Nếu có chữ số đi kèm (hasNum == true): hệ số là giá trị số đó (ví dụ "2x", "0x").
     *       * Nếu không có chữ số đi kèm (hasNum == false): hệ số mặc định là 1 (ví dụ "x", "+x", "-x").
     *       * Cộng dồn vào tổng hệ số coeffX: coeffX += side * sign * factor.
     *   + Nếu không có 'x': đây là hằng số tự do, cộng dồn vào constVal: constVal += side * sign * num.
     * - Kết luận nghiệm:
     *   + Nếu coeffX == 0 và constVal == 0: 0*x = 0  => "Infinite solutions" (Vô số nghiệm).
     *   + Nếu coeffX == 0 và constVal != 0: 0*x = -B != 0 => "No solution" (Vô nghiệm).
     *   + Nếu coeffX != 0: Nghiệm duy nhất x = -constVal / coeffX.
     *
     * Time Complexity: O(N) - với N là độ dài chuỗi phương trình, duyệt chính xác 1 lần.
     * Space Complexity: O(1) - chỉ sử dụng vài biến nguyên thủy, không regex, không cấp phát bộ nhớ phụ.
     */
    public String solveEquation(String equation) {
        int n = equation.length();
        int coeffX = 0;
        int constVal = 0;
        int side = 1; // +1 cho vế trái, -1 cho vế phải của dấu '='

        int i = 0;
        while (i < n) {
            char c = equation.charAt(i);

            // Chuyển sang vế phải của phương trình
            if (c == '=') {
                side = -1;
                i++;
                continue;
            }

            // Xác định dấu của số hạng (+ hoặc -)
            int sign = 1;
            if (i < n && equation.charAt(i) == '+') {
                i++;
            } else if (i < n && equation.charAt(i) == '-') {
                sign = -1;
                i++;
            }

            // Đọc phần giá trị số (nếu có)
            int num = 0;
            boolean hasNum = false;
            while (i < n && Character.isDigit(equation.charAt(i))) {
                num = num * 10 + (equation.charAt(i) - '0');
                hasNum = true;
                i++;
            }

            // Kiểm tra xem số hạng này gắn với biến 'x' hay là hằng số tự do
            if (i < n && equation.charAt(i) == 'x') {
                int factor = hasNum ? num : 1;
                coeffX += side * sign * factor;
                i++; // Bỏ qua ký tự 'x'
            } else {
                constVal += side * sign * num;
            }
        }

        // Biện luận số nghiệm của phương trình bậc nhất: coeffX * x + constVal = 0
        if (coeffX == 0) {
            return constVal == 0 ? "Infinite solutions" : "No solution";
        }

        return "x=" + (-constVal / coeffX);
    }
}