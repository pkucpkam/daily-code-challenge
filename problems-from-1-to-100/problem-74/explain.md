# Intersection of Two Arrays II

## 1. Mô tả bài toán
Cho hai mảng số nguyên `nums1` và `nums2`. Hãy tìm và trả về một mảng chứa các phần tử chung của cả hai mảng.
Khác với phần I, yêu cầu ở đây là:
- Mỗi phần tử trong kết quả phải xuất hiện **đúng số lần** mà nó xuất hiện trong cả hai mảng (số lần xuất hiện tối thiểu của phần tử đó giữa hai mảng).
- Kết quả có thể trả về theo bất kỳ thứ tự nào.

## 2. Ý tưởng cốt lõi
- Vì chúng ta cần quan tâm đến số lượng (tần suất) xuất hiện của từng phần tử, cấu trúc dữ liệu phù hợp nhất là **HashMap**.
- Ta đếm tần suất xuất hiện của từng số trong mảng thứ nhất.
- Khi duyệt qua mảng thứ hai, nếu gặp số đã có trong Map và số lượng còn lại > 0, ta đưa nó vào kết quả và giảm số lượng tương ứng trong Map đi 1.

## 3. Giải thích thuật toán
1. Tạo một `HashMap<Integer, Integer>` đặt tên là `countMap` để lưu `{Giá trị: Số lần xuất hiện}` từ mảng `nums1`.
2. Duyệt qua mảng `nums2`:
   - Nếu `num` có trong `countMap` và giá trị hiện tại của nó lớn hơn 0:
     - Thêm `num` vào danh sách kết quả (`intersectionList`).
     - Giảm giá trị của `num` trong `countMap` đi 1 đơn vị.
3. Chuyển đổi danh sách kết quả thành mảng `int[]` và trả về.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n + m)\) - Với `n, m` là độ dài hai mảng. Ta duyệt mỗi mảng một lần, các thao tác trên HashMap là $O(1)$.
- **Không gian (Space Complexity)**: \(O(\min(n, m))\) - Không gian lưu trữ Map tỷ lệ thuận với số lượng phần tử duy nhất trong mảng nhỏ hơn.

## 5. Code (Java)
```java
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        // Đếm tần suất xuất hiện các số trong mảng nums1
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums1) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Tìm các phần tử chung dựa trên tần suất
        List<Integer> intersectionList = new ArrayList<>();
        for (int num : nums2) {
            if (countMap.containsKey(num) && countMap.get(num) > 0) {
                intersectionList.add(num);
                // Giảm số lượng còn lại sau khi đã lấy 1 đơn vị
                countMap.put(num, countMap.get(num) - 1);
            }
        }

        // Chuyển List sang mảng int[]
        int[] result = new int[intersectionList.size()];
        for (int i = 0; i < intersectionList.size(); i++) {
            result[i] = intersectionList.get(i);
        }
        return result;
    }
}
```
*(Gợi ý: Nếu mảng đã được sắp xếp sẵn, ta có thể sử dụng phương pháp Hai con trỏ để tiết kiệm không gian).*
