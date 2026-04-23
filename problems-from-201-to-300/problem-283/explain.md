# 300. Longest Increasing Subsequence - Best Solution

## Tóm tắt bài toán

Cho một mảng số nguyên `nums`, hãy tìm độ dài của dãy con tăng строго nhất dài nhất.

## Ý tưởng tối ưu

Ta dùng mảng `tails` để lưu giá trị nhỏ nhất có thể ở vị trí cuối của một dãy tăng với độ dài tương ứng.

Ý nghĩa của `tails[i]`:

- `tails[0]` là giá trị nhỏ nhất có thể kết thúc một dãy tăng độ dài 1.
- `tails[1]` là giá trị nhỏ nhất có thể kết thúc một dãy tăng độ dài 2.
- ...

Với mỗi `num` trong `nums`, ta tìm vị trí đầu tiên trong `tails` mà giá trị đó lớn hơn hoặc bằng `num` bằng binary search:

- Nếu tìm thấy vị trí `pos`, gán `tails[pos] = num`.
- Nếu `pos` bằng `size`, tăng `size` lên 1.

Nhờ vậy, `tails` luôn được giữ tối ưu theo kiểu tham lam: ta không cần lưu toàn bộ dãy con, chỉ cần giữ các đuôi nhỏ nhất có thể.

## Vì sao đúng

- Nếu một dãy tăng độ dài `k` có đuôi nhỏ hơn, nó luôn có lợi hơn hoặc ít nhất không tệ hơn cho việc kéo dài về sau.
- Binary search giúp đặt mỗi phần tử vào đúng vị trí dài nhất mà nó có thể mở rộng.
- `size` chính là độ dài lớn nhất đã tạo được, nên khi duyệt hết mảng, đó là đáp án.

## Độ phức tạp

- Time: `O(n log n)`
- Space: `O(1)`

## Java Code

```java
class Solution {
    public void gameOfLife(int[][] board) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int num : nums) {
            int left = 0;
            int right = size;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;

            if (left == size) {
                size++;
            }
        }

        return size;
    }
}
```
