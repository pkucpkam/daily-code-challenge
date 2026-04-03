# Contains Duplicate II

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums` và một số nguyên `k`. Hãy trả về `true` nếu tồn tại hai chỉ số khác nhau `i` và `j` sao cho `nums[i] == nums[j]` và khoảng cách giữa chúng không vượt quá `k` (\(|i - j| \le k\)).

## 2. Ý tưởng cốt lõi
- Bài toán này có thể giải quyết hiệu quả bằng phương pháp **Cửa sổ trượt (Sliding Window)** kết hợp với **HashSet**.
- Chúng ta duy trì một tập hợp chứa tối đa `k` phần tử gần nhất đã duyệt qua.
- Nếu một phần tử mới xuất hiện đã có trong tập hợp này, nghĩa là điều kiện \(\text{nums}[i] == \text{nums}[j]\) và khoảng cách \(\le k\) được thỏa mãn.

## 3. Giải thích thuật toán
1. Khởi tạo một `HashSet<Integer>` tên là `seen`.
2. Duyệt qua mảng `nums` với chỉ số `i`:
   - Kiểm tra xem `nums[i]` có nằm trong `seen` hay không. Nếu có, trả về `true`.
   - Thêm `nums[i]` vào `seen`.
   - **Duy trì cửa sổ**: Nếu kích thước của `seen` vượt quá `k`, ta cần loại bỏ phần tử cũ nhất (phần tử ở vị trí `i - k`) để đảm bảo các phần tử trong `seen` luôn nằm trong khoảng cách `k` so với vị trí tiếp theo.
3. Nếu kết thúc vòng lặp mà không tìm thấy cặp nào, trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng đúng một lần, các thao tác trên HashSet (`add`, `contains`, `remove`) tốn \(O(1)\).
- **Không gian (Space Complexity)**: \(O(\min(n, k))\) - HashSet chỉ lưu trữ tối đa `k` phần tử tại bất kỳ thời điểm nào.

## 5. Code (Java)
```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> seen = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Nếu đã xuất hiện số này trong cửa sổ kích thước k
            if (seen.contains(nums[i])) {
                return true;
            }
            
            seen.add(nums[i]);
            
            // Nếu cửa sổ vượt quá kích thước k, loại bỏ phần tử xa nhất
            if (seen.size() > k) {
                seen.remove(nums[i - k]);
            }
        }
        
        return false;
    }
}
```
