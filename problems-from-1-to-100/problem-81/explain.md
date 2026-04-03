# Binary Watch

## 1. Mô tả bài toán
Một chiếc đồng hồ nhị phân có 4 đèn LED ở hàng trên đại diện cho giờ (0-11) và 6 đèn LED ở hàng dưới đại diện cho phút (0-59).
Cho một số nguyên `turnedOn` là tổng số đèn LED đang sáng. Hãy trả về tất cả các mốc thời gian có thể có.
Quy tắc định dạng:
- Giờ không có số 0 ở đầu (ví dụ: "1:00", không phải "01:00").
- Phút phải có 2 chữ số (ví dụ: "10:02", không phải "10:2").

## 2. Ý tưởng cốt lõi
- Vì số lượng mốc thời gian trong một ngày là rất ít (12 giờ * 60 phút = 720 khả năng), cách tiếp cận đơn giản và hiệu quả nhất là **Duyệt qua tất cả các mốc thời gian**.
- Với mỗi mốc thời gian `(giờ, phút)`, ta đếm tổng số bit 1 trong biểu diễn nhị phân của cả `giờ` và `phút`.
- Nếu tổng số bit 1 bằng `turnedOn`, đây là một mốc thời gian hợp lệ.

## 3. Giải thích thuật toán
1. Duyệt `i` từ 0 đến 11 (giờ).
2. Duyệt `j` từ 0 đến 59 (phút).
3. Sử dụng hàm `Integer.bitCount(n)` để đếm số bit đang bật (số bit 1) của `i` và `j`.
4. Nếu `Integer.bitCount(i) + Integer.bitCount(j) == turnedOn`:
   - Định dạng chuỗi kết quả theo mẫu `h:mm`. Trong Java, sử dụng `String.format("%d:%02d", i, j)`.
   - Thêm vào danh sách kết quả.
5. Trả về danh sách.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Vì số lần lặp tối đa luôn là một hằng số cố định (12 * 60 = 720 lần).
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm cấu trúc dữ liệu phức tạp (không tính không gian lưu trữ kết quả trả về).

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();
        // Duyệt qua 12 giờ
        for (int h = 0; h < 12; h++) {
            // Duyệt qua 60 phút
            for (int m = 0; m < 60; m++) {
                // Kiểm tra tổng số bit 1 của giờ và phút
                if (Integer.bitCount(h) + Integer.bitCount(m) == turnedOn) {
                    // Định dạng theo quy tắc h:mm
                    result.add(String.format("%d:%02d", h, m));
                }
            }
        }
        return result;
    }
}
```
