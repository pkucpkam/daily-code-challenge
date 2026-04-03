# Excel Sheet Column Title

## 1. Mô tả bài toán
Cho một số nguyên `columnNumber`. Hãy trả về tiêu đề cột tương ứng của nó dưới dạng chuỗi như xuất hiện trong một trang tính Excel.
Ví dụ:
- 1 -> A
- 2 -> B
- 26 -> Z
- 27 -> AA
- 28 -> AB
- 701 -> ZY

## 2. Ý tưởng cốt lõi
- Bài toán này thực chất là việc chuyển đổi từ hệ thập phân (cơ số 10) sang một hệ cơ số 26 biến thể (A-Z).
- Tuy nhiên, khác với hệ số thông thường (bắt đầu từ 0), Excel bắt đầu từ 1.
- Để xử lý việc lệch chỉ số này, trong mỗi bước lặp, ta nên trừ `columnNumber` đi 1 trước khi thực hiện phép chia lấy dư cho 26. Điều này đưa phạm vi 1-26 về 0-25, tương ứng hoàn hảo với các ký tự từ 'A' đến 'Z'.

## 3. Giải thích thuật toán
1. Khởi tạo một `StringBuilder` để lưu kết quả.
2. Trong khi `columnNumber > 0`:
   - Giảm `columnNumber` đi 1 đơn vị: `columnNumber--`.
   - Tính phần dư: `remainder = columnNumber % 26`.
   - Chuyển phần dư thành ký tự: `(char) ('A' + remainder)` và thêm vào đầu (hoặc thêm vào cuối rồi đảo ngược).
   - Cập nhật `columnNumber = columnNumber / 26`.
3. Đảo ngược chuỗi kết quả (nếu thêm vào cuối) và trả về.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log_{26} N)\) - Số lần lặp tỉ lệ thuận với số lượng chữ cái trong tiêu đề cột.
- **Không gian (Space Complexity)**: \(O(1)\) - Ngoại trừ chuỗi kết quả trả về, ta không dùng thêm cấu trúc dữ liệu nào đáng kể.

## 5. Code (Java)
```java
class Solution {
    public String convertToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        while (columnNumber > 0) {
            // Đưa về 0-indexed bằng cách trừ 1
            columnNumber--; 
            int remainder = columnNumber % 26;
            result.append((char) ('A' + remainder));
            columnNumber /= 26;
        }
        // Kết quả bị ngược (từ hàng đơn vị lên), cần reverse
        return result.reverse().toString();      
    }
}
```
