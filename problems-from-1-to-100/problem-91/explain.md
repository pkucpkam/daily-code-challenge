# Assign Cookies

## 1. Mô tả bài toán
Giả sử bạn là một người phụ huynh tuyệt vời và muốn chia bánh quy cho các con của mình. Mỗi đứa trẻ có một **mức độ tham ăn** `g[i]` (là kích thước tối thiểu của miếng bánh mà đứa trẻ đó sẽ thấy hài lòng). Mỗi miếng bánh quy có một **kích thước** `s[j]`.
Nếu `s[j] >= g[i]`, bạn có thể đưa miếng bánh `j` cho đứa trẻ `i` và nó sẽ thấy hài lòng.
Mục tiêu là **tối đa hóa** số lượng trẻ em hài lòng. Lưu ý: Mỗi đứa trẻ chỉ nhận tối đa một miếng bánh.

## 2. Ý tưởng cốt lõi
- Đây là một bài toán điển hình sử dụng thuật toán **Tham lam (Greedy)**.
- Để tối ưu hóa, ta nên dành những miếng bánh nhỏ nhất có thể cho những đứa trẻ có mức độ tham ăn nhỏ nhất.
- Đầu tiên, cần sắp xếp cả danh sách mức độ tham ăn của trẻ và danh sách kích thước bánh theo thứ tự tăng dần.

## 3. Giải thích thuật toán
1. Sắp xếp mảng `g` (greed factors) và `s` (cookie sizes) tăng dần.
2. Sử dụng hai con trỏ: `i` cho trẻ em và `j` cho bánh quy.
3. Chạy vòng lặp trong khi vẫn còn trẻ em và vẫn còn bánh:
   - So sánh: Nếu `s[j] >= g[i]` (miếng bánh đủ lớn):
     - Đứa trẻ hài lòng, tăng biến đếm `contentChildren`.
     - Chuyển sang đứa trẻ tiếp theo: `i++`.
   - Bất kể đứa trẻ có hài lòng hay không, ta cũng phải chuyển sang miếng bánh tiếp theo: `j++` (vì miếng bánh hiện tại quá nhỏ hoặc đã được dùng rồi).
4. Trả về `contentChildren`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N \log N + M \log M)\) - Phụ thuộc chủ yếu vào thao tác sắp xếp mảng.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng cấu trúc dữ liệu bổ sung đáng kể (tùy thuộc vào thuật toán sắp xếp của ngôn ngữ).

## 5. Code (Java)
```java
import java.util.Arrays;

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        // Sắp xếp cả hai danh sách để áp dụng chiến thuật tham lam
        Arrays.sort(g);
        Arrays.sort(s);
        
        int i = 0; // Con trỏ cho trẻ em
        int j = 0; // Con trỏ cho bánh quy
        
        // Duyệt cho đến khi hết một trong hai mảng
        while (i < g.length && j < s.length) {
            // Nếu bánh quy hiện tại thoả mãn đứa trẻ hiện tại
            if (s[j] >= g[i]) {
                i++; // Trẻ hài lòng, chuyển sang trẻ tiếp theo
            }
            // Luôn chuyển sang miếng bánh tiếp theo (lớn hơn)
            j++;
        }
        
        // i chính là số lượng trẻ em hài lòng
        return i;
    }
}
```
*(Ghi chú: Việc sắp xếp cho phép chúng ta không bỏ lỡ bất kỳ cơ hội nào để làm hài lòng một đứa trẻ với "chi phí" bánh thấp nhất).*
