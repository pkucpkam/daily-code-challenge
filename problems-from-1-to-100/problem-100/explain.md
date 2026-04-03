# Next Greater Element I

## 1. Mô tả bài toán
Phần tử lớn hơn tiếp theo (**Next Greater Element**) của một số `x` trong mảng là phần tử lớn hơn đầu tiên nằm ở bên phải của `x` trong cùng mảng đó.
Cho hai mảng `nums1` và `nums2` không trùng lặp, trong đó `nums1` là tập con của `nums2`.
Với mỗi số trong `nums1`, hãy tìm phần tử lớn hơn tiếp theo của nó trong `nums2`. Nếu không có, kết quả là `-1`.
Ví dụ: 
- `nums1 = [4,1,2], nums2 = [1,3,4,2]` -> Kết quả: `[-1, 3, -1]`.

## 2. Ý tưởng cốt lõi
- Để tìm phần tử lớn hơn bên phải cho mọi số trong `nums2` một cách hiệu quả, ta sử dụng cấu trúc dữ liệu **Ngăn xếp (Stack)** và kỹ thuật **Monotonic Stack**.
- Ta duyệt qua `nums2`, dùng Stack để giữ các số đang chờ tìm "phần tử lớn hơn tiếp theo".
- Khi gặp một số mới lớn hơn số ở đỉnh Stack, đó chính là kết quả cần tìm cho số ở đỉnh Stack đó.
- Lưu trữ kết quả vào một **HashMap** với khóa là giá trị của số và giá trị là "phần tử lớn hơn tiếp theo" của nó.

## 3. Giải thích thuật toán
1. Duyệt qua mảng `nums2`:
   - Trong khi Stack không rỗng và số hiện tại `num` lớn hơn số ở đỉnh Stack:
     - Ta đã tìm thấy Next Greater Element cho số ở đỉnh Stack.
     - `map.put(stack.pop(), num)`.
   - Đẩy `num` vào Stack.
2. Những số còn lại trong Stack sau khi duyệt xong `nums2` là những số không có phần tử lớn hơn bên phải -> `map.put(số đó, -1)`.
3. Duyệt qua `nums1`, với mỗi số `x`, tra cứu nhanh trong Map: `result[i] = map.get(x)`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n + m)\) - Với $n, m$ là độ dài hai mảng. Ta duyệt mỗi mảng một lần, mỗi phần tử trong `nums2` được đẩy và lấy ra khỏi Stack đúng một lần.
- **Không gian (Space Complexity)**: \(O(m)\) - Sử dụng HashMap và Stack để lưu trữ thông tin của mảng `nums2`.

## 5. Code (Java)
```java
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map lưu: Giá trị số -> Phần tử lớn hơn tiếp theo của nó
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        // Duyệt nums2 để tìm Next Greater Element cho từng số bằng Monotonic Stack
        for (int num : nums2) {
            // Nếu số hiện tại lớn hơn số ở đỉnh Stack
            while (!stack.isEmpty() && num > stack.peek()) {
                // Số hiện tại là Next Greater Element của số ở đỉnh Stack
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        
        // Đối mảng nums1, tra cứu kết quả từ Map
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            // Nếu không có trong map, mặc định là -1
            result[i] = map.getOrDefault(nums1[i], -1);
        }
        
        return result;
    }
}
```
*(Ghi chú: Đây là một trong những ứng dụng quan trọng nhất của Monotonic Stack).*