# Move Zeroes

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums`. Hãy di chuyển tất cả các số 0 về cuối mảng trong khi giữ nguyên thứ tự tương đối của các phần tử khác 0.
Yêu cầu: Phải thực hiện trực tiếp trên mảng hiện tại (**In-place**) mà không được tạo một bản sao của mảng.

## 2. Ý tưởng cốt lõi
- Bài toán có thể giải quyết bằng cách sử dụng **Hai con trỏ**.
- Con trỏ thứ nhất (`lastNonZero`) dùng để ghi nhớ vị trí tiếp theo mà một số khác 0 nên được đặt vào.
- Con trỏ thứ hai dùng để duyệt qua toàn bộ mảng.
- Sau khi đã đưa tất cả các số khác 0 lên đầu mảng, ta chỉ việc lấp đầy các vị trí còn lại bằng số 0.

## 3. Giải thích thuật toán
1. Khởi tạo một biến `lastNonZero = 0`.
2. Duyệt qua mảng bằng một vòng lặp `for (int i = 0; i < nums.length; i++)`:
   - Nếu tìm thấy một phần tử `nums[i] != 0`:
     - Gán giá trị đó vào vị trí của con trỏ ghi nhớ: `nums[lastNonZero] = nums[i]`.
     - Tăng `lastNonZero` lên 1 đơn vị.
3. Sau khi kết thúc vòng lặp trên, tất cả các số khác 0 đã được dồn lên đầu mảng theo đúng thứ tự.
4. Chạy một vòng lặp thứ hai từ vị trí `lastNonZero` đến hết mảng:
   - Gán `nums[i] = 0` cho mọi vị trí còn lại này.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng tối đa 2 lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm mảng phụ, thực hiện trực tiếp trên không gian mảng đầu vào.

## 5. Code (Java)
```java
class Solution {
    public void moveZeroes(int[] nums) {
        // Con trỏ lưu vị trí của phần tử khác 0 tiếp theo
        int lastNonZero = 0;
        
        // Bước 1: Dồn tất cả các số khác 0 lên đầu
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZero] = nums[i];
                lastNonZero++;
            }
        }
        
        // Bước 2: Lấp đầy phần còn lại của mảng bằng số 0
        for (int i = lastNonZero; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```
*(Mẹo: Bạn có thể tối ưu hơn bằng cách thực hiện hoán đổi (swap) thay vì gán đè, điều này sẽ giúp hoàn thành bài toán chỉ trong một vòng lặp).*
