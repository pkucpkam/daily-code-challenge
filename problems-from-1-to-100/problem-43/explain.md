# Reverse Bits

## 1. Mô tả bài toán
Cho một số nguyên không dấu 32-bit `n`. Hãy đảo ngược thứ tự các bit của nó và trả về giá trị số nguyên mới.
Ví dụ: `0000...1011` đảo ngược thành `1101...0000`.

## 2. Ý tưởng cốt lõi
- Chúng ta cần duyệt qua tất cả 32 bit của số `n`.
- Trong mỗi bước, ta lấy bit cuối cùng của `n` và đưa nó vào vị trí phù hợp trong số kết quả `result`.
- Quá trình này được thực hiện bằng các phép toán bit: **Dịch trái (`<<`)**, **Dịch phải (`>>`)**, và **Phép VÀ (`&`)**, **Phép HOẶC (`|`)**.

## 3. Giải thích thuật toán
1. Khởi tạo `result = 0`.
2. Sử dụng vòng lặp 32 lần (tương ứng 32 bit):
   - Dịch `result` sang trái 1 vị trí để tạo chỗ trống ở hàng đơn vị: `result <<= 1`.
   - Lấy bit cuối cùng của `n` bằng phép toán `n & 1`.
   - Đưa bit đó vào vị trí trống của `result` bằng phép toán `result |= (n & 1)`.
   - Dịch `n` sang phải 1 vị trí để xét bit tiếp theo: `n >>= 1`.
3. Trả về `result`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Vòng lặp luôn chạy đúng 32 lần, không phụ thuộc vào giá trị của `n`.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một biến số nguyên để lưu kết quả.

## 5. Code (Java)
```java
public class Solution {
    public int reverseBits(int n) {
        int result = 0;
        // Duyệt qua 32 bit của số nguyên
        for (int i = 0; i < 32; i++) {
            // Dịch kết quả sang trái để nhận bit mới
            result <<= 1; 
            // Lấy bit cuối của n và đưa vào result
            result |= (n & 1); 
            // Dịch n sang phải để xét bit tiếp theo
            n >>= 1; 
        }
        return result;
    }
}
```
*(Lưu ý: Trong Java, kiểu int là có dấu, nhưng các phép toán bit vẫn hoạt động chính xác trên biểu diễn nhị phân).*
