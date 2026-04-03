# Summary Ranges

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums` đã được sắp xếp tăng dần và các phần tử là duy nhất.
Hãy trả về danh sách ngắn nhất các dải số (ranges) bao phủ toàn bộ các số trong mảng.
Một dải `[a, b]` bao gồm tất cả các số nguyên từ `a` đến `b`. Định dạng đầu ra:
- `"a->b"` nếu `a != b`
- `"a"` nếu `a == b`

## 2. Ý tưởng cốt lõi
- Duyệt qua mảng và tìm các đoạn số liên tiếp (số sau bằng số trước cộng 1).
- Khi gặp một số không liên tiếp, ta kết thúc dải hiện tại và bắt đầu một dải mới.
- Ta cần ghi nhớ điểm bắt đầu (`start`) của mỗi dải.

## 3. Giải thích thuật toán
1. Nếu mảng rỗng, trả về danh sách rỗng.
2. Khởi tạo `start = nums[0]`.
3. Duyệt mảng từ chỉ số `1` đến `n` (duyệt lố 1 chỉ số để xử lý dải cuối cùng):
   - Kiểm tra xem phần tử hiện tại `nums[i]` có phá vỡ tính liên tiếp không (hoặc đã hết mảng): `if (i == n || nums[i] != nums[i - 1] + 1)`.
   - Nếu tính liên tiếp bị phá vỡ:
     - Nếu dải chỉ có 1 phần tử (`start == nums[i-1]`): Thêm chuỗi `"start"` vào kết quả.
     - Nếu dải có nhiều phần tử: Thêm chuỗi `"start->nums[i-1]"` vào kết quả.
     - Nếu chưa hết mảng, cập nhật `start = nums[i]` để bắt đầu dải mới.
4. Trả về danh sách kết quả.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua mảng `nums` đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không tính không gian lưu trữ danh sách kết quả trả về.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        if (nums.length == 0) return ranges;

        int start = nums[0];
        for (int i = 1; i <= nums.length; i++) {
            // Kiểm tra nếu phần tử hiện tại không liên tiếp với phần tử trước đó
            if (i == nums.length || nums[i] != nums[i - 1] + 1) {
                if (start == nums[i - 1]) {
                    ranges.add(String.valueOf(start));
                } else {
                    ranges.add(start + "->" + nums[i - 1]);
                }
                
                // Cập nhật điểm khởi đầu cho dải mới
                if (i < nums.length) {
                    start = nums[i];
                }
            }
        }
        return ranges;
    }
}
```
