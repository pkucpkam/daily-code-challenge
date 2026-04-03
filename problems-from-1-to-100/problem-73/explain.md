# Intersection of Two Arrays

## 1. Mô tả bài toán
Cho hai mảng số nguyên `nums1` và `nums2`. Hãy tìm và trả về một mảng chứa các phần tử chung của cả hai mảng.
Yêu cầu:
- Mỗi phần tử trong kết quả phải là **duy nhất** (không trùng lặp).
- Kết quả có thể trả về theo bất kỳ thứ tự nào.

## 2. Ý tưởng cốt lõi
- Để tìm các phần tử chung và đảm bảo tính duy nhất, cấu trúc dữ liệu tốt nhất là **HashSet**.
- Một HashSet dùng để lưu trữ các giá trị từ mảng thứ nhất.
- Một HashSet thứ hai dùng để lưu kết quả giao nhau (để tự động lọc trùng nếu mảng thứ hai có nhiều phần tử giống nhau cùng nằm trong mảng thứ nhất).

## 3. Giải thích thuật toán
1. Khởi tạo `set1` kiểu `HashSet` và đưa toàn bộ phần tử của `nums1` vào đó.
2. Khởi tạo `intersectionSet` kiểu `HashSet` để chứa kết quả.
3. Duyệt qua từng phần tử `num` trong `nums2`:
   - Nếu `set1` chứa `num`, điều đó có nghĩa là `num` xuất hiện trong cả hai mảng.
   - Thêm `num` vào `intersectionSet`.
4. Chuyển đổi `intersectionSet` thành mảng số nguyên `int[]` và trả về.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n + m)\) - Với `n, m` lần lượt là độ dài của hai mảng. Ta duyệt qua mỗi mảng đúng một lần. Thao tác trên HashSet có độ phức tạp trung bình là $O(1)$.
- **Không gian (Space Complexity)**: \(O(n + k)\) - Với `n` là không gian cho `set1` và `k` là số lượng phần tử chung duy nhất.

## 5. Code (Java)
```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // Lưu các phần tử duy nhất của nums1
        Set<Integer> set1 = new HashSet<>();
        for (int num : nums1) {
            set1.add(num);
        }

        // Tìm các phần tử chung và lưu vào một set khác để tránh trùng lặp kết quả
        Set<Integer> intersectionSet = new HashSet<>();
        for (int num : nums2) {
            if (set1.contains(num)) {
                intersectionSet.add(num);
            }
        }

        // Chuyển Set sang mảng int[]
        int[] result = new int[intersectionSet.size()];
        int i = 0;
        for (int num : intersectionSet) {
            result[i++] = num;
        }

        return result;
    }
}
```
