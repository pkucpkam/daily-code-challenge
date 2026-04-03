# Power of Four

## 1. Mô tả bài toán
Cho một số nguyên `n`. Hãy trả về `true` nếu `n` là một lũy thừa của 4 (\(n = 4^x\)), ngược lại trả về `false`.
Lưu ý: \(4^0 = 1\), vì vậy 1 là lũy thừa của 4.

## 2. Ý tưởng cốt lõi
Để xác định một số có phải là lũy thừa của 4 hay không, ta có thể dùng nhiều cách:
- **Cách 1 (Vòng lặp/Đệ quy)**: Chia liên tiếp cho 4. Nếu số còn lại cuối cùng là 1 thì đúng.
- **Cách 2 (Bit Manipulation)**: Một số là lũy thừa của 4 phải thỏa mãn 3 điều kiện:
    1. Lớn hơn 0.
    2. Là lũy thừa của 2 (`n & (n - 1) == 0`).
    3. Bit 1 duy nhất phải nằm ở vị trí các hàng lẻ (vị trí 1, 3, 5...). Ta kiểm tra điều này bằng cách VỚI (`&`) với mặt nạ (mask) `0x55555555`.

## 3. Giải thích thuật toán
*(Dựa theo cách giải vòng lặp trong file Solution.java)*:
1. Kiểm tra trường hợp cơ sở: Nếu `n <= 0`, trả về `false`.
2. Sử dụng vòng lặp `while (n % 4 == 0)`:
   - Trong mỗi bước, thực hiện chia `n` cho 4: `n /= 4`.
   - Vòng lặp sẽ dừng khi `n` không còn chia hết cho 4 nữa.
3. Sau khi thoát vòng lặp, kiểm tra xem `n` có bằng 1 hay không.
   - Nếu `n == 1`, nghĩa là `n` được tạo thành hoàn toàn từ các tích của số 4 -> Trả về `true`.
   - Nếu `n != 1`, trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log_4 n)\) - Số lần lặp tỷ lệ thuận với số mũ của 4.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm cấu trúc dữ liệu.

## 5. Code (Java)
```java
class Solution {
    public boolean isPowerOfFour(int n) {
        // Lũy thừa của 4 phải là số dương
        if (n <= 0) {
            return false;
        }
        // Chia n cho 4 liên tục cho đến khi không chia hết được nữa
        while (n % 4 == 0) {
            n /= 4;
        }
        // Nếu còn dư lại 1 thì đó chính là lũy thừa của 4
        return n == 1;
    }
}
```
*(Mẹo: Giải pháp Bit Manipulation: return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;)*
