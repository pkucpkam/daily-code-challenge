# Third Maximum Number

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums`. Hãy tìm và trả về số lớn thứ ba (khác nhau) trong mảng này.
- Nếu số lớn thứ ba không tồn tại (mảng có ít hơn 3 số khác nhau), hãy trả về số lớn nhất.
Ví dụ:
- `[3, 2, 1]`: Trả về 1.
- `[1, 2]`: Trả về 2.
- `[2, 2, 3, 1]`: Trả về 1 (vì hai số 2 được tính là một giá trị lớn thứ hai).

## 2. Ý tưởng cốt lõi
- **Phương pháp 1 (Sắp xếp)**: Sắp xếp mảng giảm dần, sau đó duyệt qua mảng và đếm các giá trị khác nhau. Khi đếm đến 3 thì trả về giá trị đó.
- **Phương pháp 2 (Sử dụng 3 biến)**: Duy trì ba biến `max1`, `max2`, `max3` để lưu trữ ba giá trị lớn nhất tìm thấy cho đến nay. Phương pháp này tối ưu hơn về mặt thời gian ($O(n)$).

## 3. Giải thích thuật toán
*(Dựa theo cách giải sắp xếp trong file Solution.java)*:
1. Sắp xếp mảng `nums` theo thứ tự tăng dần.
2. Duyệt ngược từ cuối mảng (từ giá trị lớn nhất):
   - Sử dụng một biến `count` để đếm số lượng giá trị **duy nhất** đã gặp.
   - Khi `count == 3`, trả về phần tử hiện tại.
   - Sử dụng vòng lặp `while` để bỏ qua các phần tử trùng lặp (`nums[i] == nums[i-1]`).
3. Nếu kết thúc vòng lặp mà `count < 3`, trả về phần tử lớn nhất (`nums[nums.length - 1]`).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n \log n)\) do thao tác sắp xếp mảng. (Nếu dùng 3 biến sẽ là \(O(n)\)).
- **Không gian (Space Complexity)**: \(O(1)\) (Không tính không gian phục vụ sắp xếp).

## 5. Code (Java)
```java
import java.util.Arrays;

class Solution {
    public int thirdMax(int[] nums) {
        // Sắp xếp mảng tăng dần
        Arrays.sort(nums);
        int n = nums.length;
        int count = 0;
        
        // Duyệt từ cuối mảng về đầu để tìm các số lớn nhất
        for (int i = n - 1; i >= 0; i--) {
            count++;
            // Nếu đã tìm thấy số lớn thứ 3
            if (count == 3) {
                return nums[i];
            }
            // Bỏ qua các số trùng lặp
            while (i > 0 && nums[i] == nums[i - 1]) {
                i--;
            }
        }
        
        // Nếu không có số lớn thứ 3, trả về số lớn nhất
        return nums[n - 1];
    }
}
```
*(Mẹo: Sử dụng kiểu Long cho 3 biến max1, max2, max3 khởi tạo là Long.MIN_VALUE để xử lý các số nguyên âm nằm sát biên của kiểu int).*
