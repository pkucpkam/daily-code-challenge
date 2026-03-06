# Find Minimum in Rotated Sorted Array

## Yêu cầu bài toán

- Cho một mảng `nums` ban đầu được sắp xếp tăng dần và gồm các phần tử duy nhất.
- Mảng này đã bị xoay (rotate) tại một điểm nào đó. Ví dụ, mảng `[0,1,2,4,5,6,7]` có thể trở thành `[4,5,6,7,0,1,2]`.
- Nhiệm vụ: Tìm phần tử nhỏ nhất của mảng này.
- Yêu cầu: Giải thuật phải chạy trong thời gian $O(\log N)$.

## Ý tưởng cốt lõi

Vì đề bài yêu cầu độ phức tạp $O(\log N)$ trên một mảng có tính chất "sắp xếp một phần", chúng ta sử dụng phương pháp **Tìm kiếm nhị phân (Binary Search)**.

Trong một mảng đã bị xoay, tại bất kỳ vị trí chia nào, chúng ta luôn có ít nhất một nửa mảng vẫn được sắp xếp theo đúng thứ tự. 
- Nếu phần tử ở giữa (`mid`) lớn hơn phần tử ở cuối mảng (`right`), điều đó có nghĩa là "phần sụt gãy" (nơi chứa giá trị nhỏ nhất) đang nằm ở bên phải của `mid`.
- Ngược lại, nếu `mid` nhỏ hơn hoặc bằng `right`, phần tử nhỏ nhất có thể chính là `mid` hoặc nằm ở bên trái của `mid`.

## Thuật toán

1. Khởi tạo hai con trỏ: `left = 0`, `right = n - 1`.
2. Trong khi `left < right`:
   - Tính `mid = left + (right - left) / 2`.
   - So sánh phần tử tại `mid` với phần tử tại `right`:
     - Nếu `nums[mid] > nums[right]`: Có nghĩa là điểm xoay và phần tử nhỏ nhất nằm trong đoạn `[mid + 1, right]`. Gán `left = mid + 1`.
     - Nếu `nums[mid] <= nums[right]`: Có nghĩa là phần tử nhỏ nhất có thể là `mid` hoặc nằm trong đoạn `[left, mid]`. Gán `right = mid`.
3. Khi vòng lặp kết thúc, `left` sẽ trỏ tới phần tử nhỏ nhất.

## Độ phức tạp
- **Thời gian**: $O(\log N)$ - Tìm kiếm nhị phân luôn chia đôi không gian tìm kiếm.
- **Không gian**: $O(1)$ - Chỉ sử dụng vài biến nguyên.

## Code (Java)

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                // Điểm gãy nằm ở bên phải, loại bỏ nửa bên trái
                left = mid + 1;
            } else {
                // Điểm gãy có thể là mid hoặc nằm bên trái
                right = mid;
            }
        }
        
        // Khi left == right, đó chính là phần tử nhỏ nhất
        return nums[left];
    }
}
```
