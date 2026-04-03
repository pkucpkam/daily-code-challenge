# First Bad Version

## 1. Mô tả bài toán
Bạn là một quản lý sản phẩm. Phiên bản mới nhất của sản phẩm bị lỗi kiểm định chất lượng. Vì mỗi phiên bản được phát triển dựa trên phiên bản trước đó, nên tất cả các phiên bản sau phiên bản lỗi đầu tiên cũng đều bị lỗi.
Cho `n` phiên bản `[1, 2, ..., n]` và một API `isBadVersion(version)` trả về giá trị boolean cho biết phiên bản đó có lỗi hay không. Hãy tìm ra phiên bản bị lỗi đầu tiên với số lần gọi API ít nhất.

## 2. Ý tưởng cốt lõi
- Đây là một bài toán tìm kiếm trên một dãy đã được sắp xếp ngầm định (ví dụ: `[false, false, true, true, true]`).
- Để tối thiểu hóa số lần gọi API, chúng ta sử dụng thuật toán **Tìm kiếm nhị phân (Binary Search)** thay vì duyệt tuyến tính $O(n)$.
- Trong mỗi bước, ta kiểm tra phiên bản ở giữa (`mid`).
    - Nếu `mid` bị lỗi, có khả năng `mid` là lỗi đầu tiên, hoặc lỗi đầu tiên nằm ở phía bên trái.
    - Nếu `mid` không lỗi, lỗi đầu tiên chắc chắn nằm ở phía bên phải (`mid + 1`).

## 3. Giải thích thuật toán
1. Khởi tạo `left = 1` và `right = n`.
2. Trong khi `left < right`:
   - Tính toán `mid = left + (right - left) / 2` (Sử dụng công thức này để tránh tràn số nguyên thay vì `(left + right) / 2`).
   - Gọi `isBadVersion(mid)`:
     - Nếu trả về `true` (có lỗi): Thu hẹp phạm vi tìm kiếm về bên trái, bao gồm cả `mid`: `right = mid`.
     - Nếu trả về `false` (không lỗi): Thu hẹp phạm vi về bên phải, bắt đầu từ `mid + 1`: `left = mid + 1`.
3. Khi vòng lặp kết thúc, `left` sẽ bằng `right` và trỏ đúng vào phiên bản lỗi đầu tiên.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log n)\) - Số lần gọi API giảm một nửa sau mỗi bước.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một vài biến chỉ số.

## 5. Code (Java)
```java
public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        
        while (left < right) {
            // Tránh tràn số khi left và right lớn
            int mid = left + (right - left) / 2;
            
            if (isBadVersion(mid)) {
                // Nếu mid lỗi, tìm tiếp ở phía trái (bao gồm mid)
                right = mid;
            } else {
                // Nếu mid không lỗi, lỗi chắc chắn nằm sau mid
                left = mid + 1;
            }
        }
        
        // Khi left == right, đó là phiên bản lỗi đầu tiên
        return left;
    }
}
```
