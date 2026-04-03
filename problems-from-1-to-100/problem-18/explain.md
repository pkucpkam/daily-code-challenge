# Sqrt(x)

## 1. Mô tả bài toán
Cho một số nguyên không âm `x`, hãy tính và trả về căn bậc hai của `x` được làm tròn xuống số nguyên gần nhất. Kết quả trả về cũng phải là một số nguyên không âm.
Yêu cầu không sử dụng các hàm lũy thừa hoặc toán tử có sẵn của ngôn ngữ (như `Math.sqrt()` hay `pow`).

## 2. Ý tưởng cốt lõi
- Căn bậc hai của `x` là một số `n` sao cho \(n^2 \le x < (n+1)^2\).
- Vì tập hợp các số nguyên từ 0 đến `x` là một dãy số đã được sắp xếp, chúng ta có thể sử dụng thuật toán **Tìm kiếm nhị phân (Binary Search)** để tìm giá trị `mid` sao cho `mid * mid` gần `x` nhất mà không vượt quá `x`.

## 3. Giải thích thuật toán
1. Trường hợp đặc biệt: Nếu `x = 0`, trả về `0`.
2. Khởi tạo `left = 1` và `right = x`.
3. Trong khi `left <= right`:
   - Tính `mid = left + (right - left) / 2` (để tránh tràn số).
   - Tính `square = mid * mid`. Lưu ý ép kiểu sang `long` để tránh tràn số khi `mid` lớn.
   - Nếu `square == x`: Trả về `mid` (tìm thấy căn bậc hai chính xác).
   - Nếu `square < x`: Căn bậc hai nằm ở nửa bên phải, cập nhật `left = mid + 1`.
   - Nếu `square > x`: Căn bậc hai nằm ở nửa bên trái, cập nhật `right = mid - 1`.
4. Khi vòng lặp kết thúc, `right` sẽ là giá trị nguyên lớn nhất mà có bình phương nhỏ hơn hoặc bằng `x`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log x)\) nhờ sử dụng tìm kiếm nhị phân trên phạm vi từ 1 đến `x`.
- **Không gian (Space Complexity)**: \(O(1)\) vì chỉ sử dụng một vài biến số nguyên đơn giản.

## 5. Code (Java)
```java
public class Solution {
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }

        int left = 1, right = x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;

            if (square == x) {
                return mid;
            } else if (square < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }
}
```
