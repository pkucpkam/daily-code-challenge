# 198. House Robber - Best Solution

## Ý tưởng

Tại mỗi ngôi nhà, có 2 trạng thái:

- `robPrevious`: tổng lớn nhất nếu ngôi nhà trước đó được cướp.
- `skipPrevious`: tổng lớn nhất nếu ngôi nhà trước đó không cướp.

Với ngôi nhà hiện tại có tiền `money`:

- Nếu cướp nhà hiện tại, nhà trước phải bỏ qua:
	`robCurrent = skipPrevious + money`
- Nếu bỏ qua nhà hiện tại, lấy max của 2 trạng thái trước:
	`skipCurrent = max(skipPrevious, robPrevious)`

Sau đó cập nhật lại cho vòng lặp tiếp theo.

Kết quả cuối cùng là:

`max(robPrevious, skipPrevious)`

## Tại sao tối ưu

- Chỉ duyệt mảng 1 lần => `O(n)`.
- Chỉ dùng 2 cặp biến trạng thái => `O(1)` bộ nhớ phụ.

Đây là cách tối ưu về bộ nhớ cho bài này (so với cách DP mảng `O(n)`).

## Code (Java)

```java
class Solution {
		public int rob(int[] nums) {
				int robPrevious = 0;
				int skipPrevious = 0;

				for (int money : nums) {
						int robCurrent = skipPrevious + money;
						int skipCurrent = Math.max(skipPrevious, robPrevious);

						robPrevious = robCurrent;
						skipPrevious = skipCurrent;
				}

				return Math.max(robPrevious, skipPrevious);
		}
}
```

## Ví dụ nhanh

`nums = [2, 7, 9, 3, 1]`

- Sau 2: `(rob, skip) = (2, 0)`
- Sau 7: `(7, 2)`
- Sau 9: `(11, 7)`
- Sau 3: `(10, 11)`
- Sau 1: `(12, 11)`

Kết quả = `max(12, 11) = 12`.
