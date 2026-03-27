# 209. Minimum Size Subarray Sum - Giải thích lời giải tối ưu

## Ý tưởng tốt nhất (O(n)): Sliding Window

Vì tất cả phần tử `nums[i]` đều dương, khi mở rộng cửa sổ sang phải thì tổng chỉ tăng hoặc giữ xu hướng tăng.
Điều này cho phép dùng hai con trỏ để tìm đoạn con ngắn nhất có tổng `>= target`.

## Cách làm

1. Dùng hai con trỏ `left`, `right` biểu diễn cửa sổ hiện tại.
2. Mỗi bước tăng `right`, cộng `nums[right]` vào `windowSum`.
3. Khi `windowSum >= target`, thử co cửa sổ từ trái:
   - Cập nhật đáp án `minLen = min(minLen, right - left + 1)`.
   - Trừ `nums[left]`, rồi tăng `left` để cửa sổ nhỏ lại.
4. Làm đến hết mảng.
5. Nếu không có đoạn nào hợp lệ, trả về `0`.

## Vì sao đúng?

- Mỗi lần `windowSum >= target`, cửa sổ hiện tại là một ứng viên hợp lệ.
- Co từ trái giúp tìm cửa sổ ngắn nhất với cùng điểm cuối `right`.
- Duyệt mọi `right` từ trái sang phải đảm bảo không bỏ sót đáp án toàn cục.

## Độ phức tạp

- Thời gian: `O(n)` vì mỗi phần tử được thêm vào và loại ra khỏi cửa sổ tối đa 1 lần.
- Bộ nhớ phụ: `O(1)`.

## Java code

```java
class Solution {
	public int minSubArrayLen(int target, int[] nums) {
		int left = 0;
		int windowSum = 0;
		int minLen = Integer.MAX_VALUE;

		for (int right = 0; right < nums.length; right++) {
			windowSum += nums[right];

			while (windowSum >= target) {
				minLen = Math.min(minLen, right - left + 1);
				windowSum -= nums[left];
				left++;
			}
		}

		return minLen == Integer.MAX_VALUE ? 0 : minLen;
	}
}
```

## Ghi chú thêm

Follow-up `O(n log n)` có thể làm bằng prefix sum + binary search, nhưng với đề này thì sliding window là tối ưu và ngắn gọn nhất.
