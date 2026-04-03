# Number Complement

## 1. Mô tả bài toán
Số bù (complement) của một số nguyên là kết quả thu được khi ta đảo ngược tất cả các bit của nó (0 thành 1, 1 thành 0) trong biểu diễn nhị phân.
Lưu ý: Chỉ thực hiện đảo các bit có nghĩa (không tính các số 0 ở đầu).
Ví dụ: `5` là `101`, số bù là `010` (tức là 2).

## 2. Ý tưởng cốt lõi
- Phép toán **XOR (`^`)** với số 1 sẽ đảo ngược bit (`0 ^ 1 = 1`, `1 ^ 1 = 0`).
- Để chỉ đảo ngược những bit có nghĩa của số `num`, ta cần tạo ra một **mặt nạ (mask)** có cùng độ dài bit với `num` và toàn là bit 1.
    - Ví dụ: `num = 5` (`101`), ta cần `mask = 7` (`111`).
    - Kết quả sẽ là `5 ^ 7 = 2` (`101 ^ 111 = 010`).

## 3. Giải thích thuật toán
1. Khởi tạo `mask = 0`.
2. Tạo một biến tạm `temp = num`.
3. Trong khi `temp > 0`:
   - Dịch chuyển `mask` sang trái 1 bit và điền bit 1 vào: `mask = (mask << 1) | 1`.
   - Dịch phải `temp` để đếm số lượng bit: `temp >>= 1`.
4. Sau vòng lặp, `mask` sẽ có toàn bộ bit 1 với độ dài đúng bằng `num`.
5. Trả về `num ^ mask`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Số bit của số nguyên tối đa là 31 (đối với số dương kiểu `int`). Vòng lặp chạy tối đa 31 lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ dùng hai biến số nguyên.

## 5. Code (Java)
```java
class Solution {
    public int findComplement(int num) {
        // Tạo mặt nạ gồm các bit 1 có cùng độ dài với num
        int mask = 0;
        int temp = num;
        
        while (temp > 0) {
            // Dịch trái và thêm 1 vào vị trí cuối
            mask = (mask << 1) | 1;
            temp >>= 1;
        }
        
        // XOR num với mask để đảo ngược tất cả các bit
        return num ^ mask;
    }
}
```
*(Mẹo: Một cách khác để tính mask nhanh là dùng logarit hoặc dịch bit: (Integer.highestOneBit(num) << 1) - 1).*
