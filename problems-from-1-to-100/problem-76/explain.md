# Guess Number Higher or Lower

## 1. Mô tả bài toán
Bạn đang chơi một trò chơi đoán số. Đối thủ chọn một số trong phạm vi từ 1 đến `n`. Bạn cần đoán xem số đó là số nào.
Mỗi lần bạn đoán sai, đối thủ sẽ cho bạn biết số họ chọn lớn hơn hay nhỏ hơn số bạn vừa đoán thông qua API `guess(num)`:
- `-1`: Số chọn nhỏ hơn số bạn đoán (`pick < num`).
- `1`: Số chọn lớn hơn số bạn đoán (`pick > num`).
- `0`: Bạn đã đoán đúng (`pick == num`).

## 2. Ý tưởng cốt lõi
- Đây là bài toán tìm kiếm một giá trị trong dải số đã sắp xếp từ 1 đến `n`.
- Thuật toán tối ưu nhất là **Tìm kiếm nhị phân (Binary Search)**.
- Ta liên tục chia đôi khoảng tìm kiếm dựa trên phản hồi của API `guess`.

## 3. Giải thích thuật toán
1. Khởi tạo `left = 1` và `right = n`.
2. Trong khi `left <= right`:
   - Tính toán điểm giữa `mid = left + (right - left) / 2` (để tránh tràn số).
   - Gọi hàm `res = guess(mid)`.
   - Nếu `res == 0`: Trả về `mid` vì đã tìm thấy số cần tìm.
   - Nếu `res == -1`: Số của đối thủ nhỏ hơn, thu hẹp phạm vi sang trái: `right = mid - 1`.
   - Nếu `res == 1`: Số của đối thủ lớn hơn, thu hẹp phạm vi sang phải: `left = mid + 1`.
3. Mặc dù đầu bài đảm bảo luôn có kết quả, hàm vẫn trả về `-1` ở cuối theo quy tắc lập trình.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log n)\) - Số lần đoán tối đa tỷ lệ thuận với logarit cơ số 2 của `n`.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng vài biến lưu trữ các giới hạn.

## 5. Code (Java)
```java
public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int left = 1, right = n;
        
        while (left <= right) {
            // Tính trung điểm để đoán
            int mid = left + (right - left) / 2;
            int res = guess(mid);
            
            if (res == 0) {
                // Đoán đúng
                return mid;
            } else if (res < 0) {
                // Số cần tìm nhỏ hơn mid, tìm bên trái
                right = mid - 1;
            } else {
                // Số cần tìm lớn hơn mid, tìm bên phải
                left = mid + 1;
            }
        }
        
        return -1;
    }
}
```
*(Ghi chú: Đây là ví dụ điển hình nhất minh họa tính hiệu quả của tìm kiếm nhị phân).*
