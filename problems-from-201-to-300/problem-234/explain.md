# Two Sum II - Input Array Is Sorted

## Yêu cầu bài toán

- Cho mảng `numbers` đã **sắp xếp không giảm** và đánh chỉ số theo dạng **1-indexed**.
- Tìm hai phần tử `numbers[index1]` và `numbers[index2]` sao cho:
	- `numbers[index1] + numbers[index2] == target`
	- `1 <= index1 < index2 <= numbers.length`
- Trả về mảng kết quả `[index1, index2]`.
- Không được dùng cùng một phần tử hai lần.
- Bảo đảm có **đúng một lời giải**.
- Yêu cầu dùng **constant extra space** (không gian phụ hằng số).

## Giải thích bài toán

Vì mảng đã được sắp xếp tăng dần, ta có thể tận dụng thứ tự này để tìm cặp số bằng `target` mà không cần dùng `HashMap`.

Ý tưởng trực quan:

- Nếu tổng hiện tại quá nhỏ, ta cần số lớn hơn => dịch con trỏ trái sang phải.
- Nếu tổng hiện tại quá lớn, ta cần số nhỏ hơn => dịch con trỏ phải sang trái.

Do đề đảm bảo có đúng một đáp án, ta chắc chắn sẽ gặp đúng cặp cần tìm.

## Cách giải tối ưu (Two Pointers)

### Ý tưởng

- Đặt `left = 0` (đầu mảng), `right = numbers.length - 1` (cuối mảng).
- Trong khi `left < right`:
	- Tính `sum = numbers[left] + numbers[right]`.
	- Nếu `sum == target`: trả về `[left + 1, right + 1]` (đổi sang 1-indexed).
	- Nếu `sum < target`: tăng `left`.
	- Nếu `sum > target`: giảm `right`.

### Vì sao đúng?

- Mảng tăng dần nên:
	- Tăng `left` sẽ làm giá trị bên trái không giảm, giúp tổng tăng.
	- Giảm `right` sẽ làm giá trị bên phải không tăng, giúp tổng giảm.
- Mỗi bước đều loại bỏ một phần chắc chắn không thể tạo ra đáp án, nên thuật toán hội tụ đến cặp đúng.

## Độ phức tạp

- Thời gian: `O(n)` vì mỗi con trỏ di chuyển tối đa `n` bước.
- Không gian phụ: `O(1)` đúng theo yêu cầu đề.

## Code (Java)

```java
class Solution {
		public int[] twoSum(int[] numbers, int target) {
				int left = 0;
				int right = numbers.length - 1;

				while (left < right) {
						int sum = numbers[left] + numbers[right];

						if (sum == target) {
								return new int[] {left + 1, right + 1};
						}

						if (sum < target) {
								left++;
						} else {
								right--;
						}
				}

				return new int[] {-1, -1};
		}
}
```
