# Arranging Coins

## 1. Mô tả bài toán
Bạn có `n` đồng xu và muốn xây dựng một cầu thang. Hàng thứ `i` của cầu thang phải có đúng `i` đồng xu. Hàng cuối cùng có thể không đầy đủ.
Hãy tìm số lượng hàng **đầy đủ** mà bạn có thể xây dựng được.
Ví dụ:
- `n = 5`: Hàng 1 (1 xu), Hàng 2 (2 xu). Tổng là 3 xu. Hàng 3 cần 3 xu nhưng chỉ còn 2 xu -> Trả về 2.

## 2. Ý tưởng cốt lõi
- Tổng số đồng xu cần thiết để xây dựng một cầu thang có `k` hàng đầy đủ là: \(S = 1 + 2 + ... + k = \frac{k(k+1)}{2}\).
- Bài toán trở thành tìm số nguyên `k` lớn nhất sao cho \(\frac{k(k+1)}{2} \le n\).
- Ta có thể sử dụng **Tìm kiếm nhị phân (Binary Search)** trong khoảng `[0, n]` để tìm giá trị `k` này một cách hiệu quả.

## 3. Giải thích thuật toán
1. Thiết lập dải tìm kiếm: `left = 0`, `right = n`.
2. Trong khi `left <= right`:
   - Tính `mid = left + (right - left) / 2`.
   - Tính số xu cần dùng cho `mid` hàng: `curr = mid * (mid + 1) / 2`.
     - *Lưu ý: Cần ép kiểu sang `long` khi tính toán để tránh tràn số vì `mid` có thể rất lớn*.
   - So sánh `curr` với `n`:
     - Nếu `curr == n`: Trả về `mid` (đúng bằng số xu).
     - Nếu `curr < n`: Có thể xây được nhiều hàng hơn, tăng `left = mid + 1`.
     - Nếu `curr > n`: Vượt quá số xu hiện có, giảm `right = mid - 1`.
3. Sau khi kết thúc, `right` sẽ là số hàng đầy đủ lớn nhất thỏa mãn điều kiện.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log n)\) - Tìm kiếm nhị phân trên dải từ 0 đến $n$.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng cấu trúc dữ liệu bổ sung.

## 5. Code (Java)
```java
class Solution {
    public int arrangeCoins(int n) {
        // Tìm kiếm nhị phân từ 0 đến n
        long left = 0, right = n;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            // Công thức tính tổng n số tự nhiên đầu tiên
            long sum = mid * (mid + 1) / 2;
            
            if (sum == n) {
                return (int) mid;
            } else if (sum < n) {
                // Vẫn đủ xu, thử tìm hàng cao hơn
                left = mid + 1;
            } else {
                // Vượt quá số xu, phải giảm số hàng xuống
                right = mid - 1;
            }
        }
        
        // Trả về right (số hàng nguyên lớn nhất)
        return (int) right;
    }
}
```
*(Gợi ý: Bài này cũng có thể giải bằng công thức toán học nghiệm của phương trình bậc hai: \(k = \lfloor \frac{\sqrt{8n + 1} - 1}{2} \rfloor\) với độ phức tạp O(1)).*
