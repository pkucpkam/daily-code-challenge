# Valid Perfect Square

## 1. Mô tả bài toán
Cho một số nguyên dương `num`. Hãy trả về `true` nếu `num` là một số chính phương (là bình phương của một số nguyên), ngược lại trả về `false`.
Yêu cầu quan trọng: **Không được sử dụng bất kỳ hàm thư viện nào có sẵn như `sqrt`.**

## 2. Ý tưởng cốt lõi
- Vì dãy các số bình phương (\(1, 4, 9, 16, ...\)) là một dãy tăng dần, chúng ta có thể áp dụng thuật toán **Tìm kiếm nhị phân (Binary Search)** để tìm xem có số nguyên `x` nào thỏa mãn `x * x = num` hay không.
- Phạm vi tìm kiếm sẽ từ 1 đến `num`. Tuy nhiên, với kiểu `int` 32-bit, giá trị tối đa của `x` sao cho `x * x` không vượt quá `Integer.MAX_VALUE` là 46340.

## 3. Giải thích thuật toán
1. Xử lý trường hợp cơ bản: Nếu `num < 0` trả về `false`, nếu `num <= 1` trả về `true`.
2. Khởi tạo `left = 1` và `right = 46340` (hoặc `num` nếu `num` nhỏ hơn).
3. Trong khi `left <= right`:
   - Tính `mid = left + (right - left) / 2`.
   - Tính bình phương `square = mid * mid`.
   - Nếu `square == num`: Tìm thấy số chính phương -> Trả về `true`.
   - Nếu `square < num`: Số cần tìm lớn hơn, cập nhật `left = mid + 1`.
   - Nếu `square > num`: Số cần tìm nhỏ hơn, cập nhật `right = mid - 1`.
4. Nếu kết thúc vòng lặp mà không tìm thấy, trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log \sqrt{N})\) - Với $N$ là giá trị của `num`. Vì ta tìm kiếm trong phạm vi từ 1 đến $\sqrt{N}$.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ dùng các biến quản lý chỉ số.

## 5. Code (Java)
```java
class Solution {
    public boolean isPerfectSquare(int num) {
        if (num < 0) return false;
        if (num <= 1) return true;
        
        int left = 1;
        // Căn bậc hai của Integer.MAX_VALUE xấp xỉ 46340.95
        int right = Math.min(num, 46340);
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int square = mid * mid;
            
            if (square == num) {
                return true;
            } else if (square < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```
*(Ghi chú: Việc giới hạn `right` ở 46340 giúp tránh hiện tượng tràn số (overflow) khi thực hiện phép tính `mid * mid`).*
