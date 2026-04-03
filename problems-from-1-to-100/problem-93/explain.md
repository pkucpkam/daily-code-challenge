# Hamming Distance

## 1. Mô tả bài toán
Khoảng cách Hamming giữa hai số nguyên là số lượng vị trí mà tại đó các bit tương ứng khác nhau.
Cho hai số nguyên `x` và `y`, hãy tính và trả về khoảng cách Hamming giữa chúng.
Ví dụ: 
- `x = 1 (0001)`, `y = 4 (0100)`. Hai vị trí bit khác nhau (vị trí thứ 0 và thứ 2). Khoảng cách là `2`.

## 2. Ý tưởng cốt lõi
- Phép toán logic **XOR (`^`)** là phép toán hoàn hảo cho bài này. Kết quả của `x ^ y` sẽ trả về một số mà các bit 1 đại diện cho những vị trí có sự khác biệt giữa `x` và `y`.
- Sau khi thực hiện XOR, bài toán trở thành: **Đếm số lượng bit 1** trong kết quả đó.

## 3. Giải thích thuật toán
1. Tính `res = x ^ y`.
2. Khởi tạo `distance = 0`.
3. Sử dụng vòng lặp để đếm bit 1 (Hamming Weight):
   - Kiểm tra bit cuối cùng (`res & 1`). Nếu là 1, tăng `distance`.
   - Dịch phải `res` một bit: `res >>= 1`.
   - Tiếp tục cho đến khi `res == 0`.
4. Trả về `distance`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Số lượng bit của kiểu `int` là cố định (32 bit).
- **Không gian (Space Complexity)**: \(O(1)\) - Không tốn bộ nhớ phụ.

## 5. Code (Java)
```java
class Solution {
    public int hammingDistance(int x, int y) {
        // Thực hiện phép XOR để tìm các bit khác biệt
        int xorResult = x ^ y;
        int distance = 0;
        
        // Đếm số lượng bit 1 trong kết quả XOR
        while (xorResult != 0) {
            // Kiểm tra bit cuối có phải là 1 không
            if ((xorResult & 1) == 1) {
                distance++;
            }
            // Dịch sang phải để kiểm tra bit tiếp theo
            xorResult >>= 1;
        }
        
        return distance;
    }
}
```
*(Mẹo: Bạn có thể sử dụng hàm có sẵn Integer.bitCount(x ^ y) để đạt hiệu suất cao nhất).*
