# Find All Numbers Disappeared in an Array

## 1. Mô tả bài toán
Cho một mảng `nums` chứa `n` số nguyên, trong đó mỗi số nằm trong phạm vi từ `[1, n]`. Hãy trả về một danh sách các số từ `1` đến `n` **không xuất hiện** trong mảng đó.
Ví dụ: `nums = [4,3,2,7,8,2,3,1]` -> Kết quả: `[5,6]`.

## 2. Ý tưởng cốt lõi
- Bài toán yêu cầu tìm các số thiếu mà không sử dụng thêm mảng phụ (độ phức tạp không gian ngoài kết quả trả về là \(O(1)\)).
- Vì các số trong mảng nằm trong dải `[1, n]`, chúng tương đương trực tiếp với các chỉ số (index) của chính mảng đó (`0` đến `n-1`).
- Ta có thể đánh dấu sự hiện diện của một số `x` bằng cách biến phần tử tại vị trí `x-1` thành số âm.
- Sau đó, duyệt lại mảng một lần nữa. Những vị trí nào vẫn chứa số dương nghĩa là số tương ứng với chỉ số đó không xuất hiện.

## 3. Giải thích thuật toán
1. Duyệt qua mảng `nums`:
   - Với mỗi số `num`, lấy giá trị tuyệt đối `val = |num|`.
   - Tìm chỉ số tương ứng: `index = val - 1`.
   - Nếu `nums[index]` đang dương, đổi nó thành số âm: `nums[index] = -nums[index]`. (Dấu âm này đóng vai trò như một "lá cờ" thông báo rằng số `index + 1` đã xuất hiện).
2. Tạo một danh sách kết quả `result`.
3. Duyệt lại mảng `nums` lần thứ hai:
   - Nếu `nums[i] > 0` (vị trí `i` không bị đánh dấu âm):
     - Nghĩa là số `i + 1` không hề xuất hiện trong mảng ban đầu.
     - Thêm `i + 1` vào `result`.
4. Trả về `result`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng hai lần độc lập.
- **Không gian (Space Complexity)**: \(O(1)\) - Nếu không tính danh sách kết quả trả về, ta thực hiện đánh dấu trực tiếp trên mảng đầu vào.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        // Bước 1: Đánh dấu các số đã xuất hiện
        for (int i = 0; i < nums.length; i++) {
            // Lấy chỉ số tương ứng với giá trị hiện tại
            int index = Math.abs(nums[i]) - 1;
            // Nếu vị trí này chưa được đánh dấu (đang dương) thì đổi dấu nó
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }
        
        // Bước 2: Kiểm tra những vị trí không bị đánh dấu (vẫn dương)
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                // Vị trí i tương ứng với số i+1 bị thiếu
                result.add(i + 1);
            }
        }

        return result;
    }
}
```
*(Ghi chú: Kỹ thuật dùng dấu âm này rất phổ biến trong các bài toán mảng yêu cầu thực hiện In-place).*
