# Contains Duplicate

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums`. Hãy trả về `true` nếu bất kỳ giá trị nào xuất hiện ít nhất hai lần trong mảng và trả về `false` nếu mọi phần tử trong mảng là duy nhất.

## 2. Ý tưởng cốt lõi
- Để kiểm tra sự tồn tại của các phần tử trùng lặp một cách nhanh chóng, ta có thể sử dụng cấu trúc dữ liệu **HashSet**.
- HashSet cho phép chúng ta kiểm tra sự tồn tại của một phần tử và thêm phần tử mới với độ phức tạp trung bình là \(O(1)\).
- Trong quá trình duyệt mảng, nếu ta gặp một số đã tồn tại trong HashSet, nghĩa là mảng có chứa phần tử trùng lặp.

## 3. Giải thích thuật toán
1. Khởi tạo một mẫu tập hợp `seen` kiểu `HashSet<Integer>`.
2. Duyệt qua từng số `num` trong mảng `nums`:
   - Kiểm tra xem `num` đã có trong tập hợp `seen` hay chưa bằng phương thức `seen.contains(num)`.
   - Nếu đã có, ngay lập tức trả về `true`.
   - Nếu chưa có, thêm `num` vào tập hợp: `seen.add(num)`.
3. Nếu duyệt hết toàn bộ mảng mà không tìm thấy giá trị nào trùng lặp, trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua mảng `nums` một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(n)\) - Trong trường hợp xấu nhất (không có trùng lặp), ta cần lưu trữ tất cả `n` phần tử vào HashSet.

## 5. Code (Java)
```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        // Sử dụng HashSet để lưu trữ các phần tử đã duyệt qua
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            // Nếu đã tồn tại trong set, nghĩa là trùng lặp
            if (seen.contains(num)) {
                return true;
            }
            // Thêm phần tử vào set
            seen.add(num);
        }
        
        return false;
    }
}
```
