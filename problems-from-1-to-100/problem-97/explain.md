# Max Consecutive Ones

## 1. Mô tả bài toán
Cho một mảng `nums` chỉ chứa các số 0 và 1. Hãy tìm và trả về số lượng số 1 liên tiếp nhiều nhất trong mảng đó.
Ví dụ: `nums = [1, 1, 0, 1, 1, 1]` -> Kết quả: `3` (vì có 3 số 1 liên tiếp ở cuối mảng).

## 2. Ý tưởng cốt lõi
- Đây là bài toán đếm cơ bản. Ta duy trì hai biến:
    - `currentCount`: Đếm số lượng số 1 liên tiếp đang xét hiện tại.
    - `maxCount`: Lưu trữ giá trị lớn nhất của `currentCount` đã từng đạt được.
- Khi gặp số 0, chuỗi liên tiếp bị ngắt quãng, ta reset `currentCount` về 0.

## 3. Giải thích thuật toán
1. Khởi tạo `maxCount = 0` và `currentCount = 0`.
2. Duyệt qua từng phần tử `num` trong mảng `nums`:
   - Nếu `num == 1`:
     - Tăng `currentCount` lên 1.
     - Cập nhật `maxCount = Math.max(maxCount, currentCount)`.
   - Nếu `num == 0`:
     - Gán `currentCount = 0` (bắt đầu đếm lại từ đầu khi gặp số 1 tiếp theo).
3. Trả về `maxCount`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng đúng một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai biến số nguyên.

## 5. Code (Java)
```java
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0;
        int currentCount = 0;

        for (int num : nums) {
            if (num == 1) {
                // Nếu gặp số 1, tăng biến đếm hiện tại
                currentCount++;
                // Cập nhật giá trị kỷ lục nếu cần
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                }
            } else {
                // Nếu gặp số 0, chuỗi liên tiếp bị ngắt, reset đếm
                currentCount = 0;
            }
        }

        return maxCount;
    }
}
```
