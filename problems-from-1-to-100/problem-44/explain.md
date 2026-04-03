# Number of 1 Bits (Hamming Weight)

## 1. Mô tả bài toán
Cho một số nguyên dương `n`. Hãy viết hàm trả về số lượng bit '1' (còn gọi là trọng số Hamming - Hamming weight) trong biểu diễn nhị phân của số đó.

## 2. Ý tưởng cốt lõi
- Để đếm số lượng bit '1', ta duyệt qua từng bit của số nguyên.
- Sử dụng phép toán `n & 1` để kiểm tra xem bit cuối cùng có phải là '1' hay không.
- Sử dụng phép dịch phải không dấu `>>>` để dịch chuyển các bit và tiếp tục kiểm tra các vị trí khác.

## 3. Giải thích thuật toán
1. Khởi tạo biến `count = 0`.
2. Sử dụng vòng lặp `while (n != 0)`:
   - Thực hiện `n & 1`: 
     - Nếu bit cuối là `1`, kết quả là `1`.
     - Nếu bit cuối là `0`, kết quả là `0`.
   - Cộng kết quả trên vào `count`.
   - Dịch `n` sang phải 1 đơn vị bằng toán tử dịch phải không dấu `>>>=`. Toán tử này đảm bảo lấp đầy các bit trống bên trái bằng số 0, tránh vòng lặp vô hạn với số âm (nếu có).
3. Sau khi `n` trở về 0, trả về giá trị `count`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Vì số nguyên trong Java có kích thước cố định (32 bit), vòng lặp chạy tối đa 32 lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ dùng một biến đếm duy nhất.

## 5. Code (Java)
```java
class Solution {
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            // Kiểm tra bit cuối cùng có phải 1 không
            count += n & 1; 
            // Dịch phải không dấu
            n >>>= 1; 
        }
        return count;
    }
}
```
*(Mẹo tối ưu: Có thể sử dụng thuật toán n & (n - 1) để loại bỏ bit 1 cuối cùng trong mỗi lần lặp, giúp vòng lặp chạy nhanh hơn nếu số có ít bit 1).*
