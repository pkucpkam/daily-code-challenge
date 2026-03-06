# Find Peak Element

## Yêu cầu bài toán

- Cho một mảng số nguyên `nums`. Một phần tử được gọi là "đỉnh" (peak) nếu nó lớn hơn hẳn các phần tử lân cận của nó.
- Nhiệm vụ: Tìm một phần tử đỉnh và trả về chỉ số (index) của nó. Nếu mảng có nhiều đỉnh, bạn có thể trả về bất kỳ đỉnh nào.
- Giả định: `nums[-1] = nums[n] = -∞` (các phần tử ngoài biên được coi là cực nhỏ).
- Yêu cầu: Thuật toán phải chạy trong thời gian $O(\log N)$.

## Ý tưởng cốt lõi

Yêu cầu thời gian $O(\log N)$ là một gợi ý mạnh mẽ cho việc sử dụng **Tìm kiếm nhị phân (Binary Search)**.
Mặc dù mảng không được sắp xếp, chúng ta vẫn có thể áp dụng tìm kiếm nhị phân dựa trên tính chất của "độ dốc":
- Nếu phần tử ở giữa `mid` nhỏ hơn phần tử bên phải nó (`mid + 1`), điều này có nghĩa là chúng ta đang ở một đoạn dốc đang đi lên. Chắc chắn sẽ có ít nhất một đỉnh ở phía bên phải.
- Ngược lại, nếu `mid` lớn hơn phần tử bên phải, chúng ta đang ở một đoạn dốc đi xuống. Chắc chắn sẽ có ít nhất một đỉnh ở phía bên trái (bao gồm cả chính `mid`).

## Thuật toán

1. Khởi tạo `left = 0`, `right = n - 1`.
2. Trong khi `left < right`:
   - Tính `mid = left + (right - left) / 2`.
   - Nếu `nums[mid] < nums[mid + 1]`: Cập nhật `left = mid + 1` (tìm ở nửa phải).
   - Ngược lại (`nums[mid] > nums[mid + 1]`): Cập nhật `right = mid` (tìm ở nửa trái).
3. Khi vòng lặp kết thúc, `left` và `right` sẽ gặp nhau tại một đỉnh. Trả về `left`.

## Độ phức tạp
- **Thời gian**: $O(\log N)$ - Tìm kiếm nhị phân.
- **Không gian**: $O(1)$ - Chỉ sử dụng các biến chỉ số.

## Code (Java)

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // So sánh phần tử hiện tại với phần tử kế tiếp
            if (nums[mid] < nums[mid + 1]) {
                // Đang lên dốc, đỉnh nằm ở phía bên phải
                left = mid + 1;
            } else {
                // Đang xuống dốc, đỉnh nằm ở phía bên trái hoặc chính là mid
                right = mid;
            }
        }
        return left;
    }
}
```
