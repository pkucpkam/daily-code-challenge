# Find Minimum in Rotated Sorted Array II

## Yêu cầu bài toán

- Cho một mảng `nums` ban đầu được sắp xếp tăng dần, nhưng có thể chứa các **phần tử trùng lặp**.
- Mảng này đã bị xoay (rotate) tại một điểm nào đó.
- Nhiệm vụ: Tìm phần tử nhỏ nhất của mảng.
- Yêu cầu: Cố gắng tối ưu hóa thời gian chạy.

## Ý tưởng cốt lõi

Đây là phiên bản mở rộng của bài toán tìm phần tử nhỏ nhất trong mảng xoay, nhưng điểm khác biệt quan trọng là sự xuất hiện của các phần tử trùng lặp.
Việc có các phần tử trùng lặp khiến phép so sánh nhị phân tiêu chuẩn có thể bị "mất phương hướng". Ví dụ: `nums[left] == nums[mid] == nums[right]`. Trong trường hợp này, chúng ta không thể xác định điểm gãy nằm ở nửa trái hay nửa phải.

Giải pháp:
- Khi `nums[mid] == nums[right]`, thay vì loại bỏ một nửa mảng, chúng ta chỉ có thể chắc chắn loại bỏ được một phần tử biên là `right` (vì `nums[mid]` đã đại diện cho giá trị đó). Điều này giúp thu hẹp phạm vi tìm kiếm một cách an toàn.

## Thuật toán

1. Khởi tạo `left = 0`, `right = n - 1`.
2. Trong khi `left < right`:
   - Tính `mid = left + (right - left) / 2`.
   - So sánh `nums[mid]` với `nums[right]`:
     - Nếu `nums[mid] > nums[right]`: Điểm gãy nằm ở bên phải `mid`. Gán `left = mid + 1`.
     - Nếu `nums[mid] < nums[right]`: Điểm gãy nằm ở bên trái hoặc chính là `mid`. Gán `right = mid`.
     - Nếu `nums[mid] == nums[right]`: Không xác định được hướng. Chỉ thu hẹp biên phải: `right--`.
3. Trả về `nums[left]`.

## Độ phức tạp
- **Thời gian**: Trung bình là $O(\log N)$. Tuy nhiên, trong trường hợp xấu nhất (tất cả phần tử đều bằng nhau), độ phức tạp có thể lên tới $O(N)$.
- **Không gian**: $O(1)$.

## Code (Java)

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // Trường hợp bằng nhau, chỉ có thể thu hẹp biên một cách an toàn
                right--;
            }
        }
        return nums[left];
    }
}
```
