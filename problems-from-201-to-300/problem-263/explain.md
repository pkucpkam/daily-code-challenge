# 221. Maximal Square - Best Solution

## Mô tả bài toán

Cho ma trận nhị phân `matrix` kích thước `m x n` gồm ký tự `'0'` và `'1'`.
Hãy tìm hình vuông lớn nhất chỉ chứa `'1'` và trả về **diện tích** của hình vuông đó.

## Ý tưởng cốt lõi (Dynamic Programming)

### Trực giác rất ngắn gọn

Tại mỗi ô, ta không hỏi "diện tích lớn nhất là bao nhiêu", mà hỏi:

- "Nếu coi ô này là góc dưới-phải, thì hình vuông toàn `'1'` lớn nhất có **cạnh** bao nhiêu?"

Gọi giá trị đó là `dp[i][j]`.

### Công thức chuyển trạng thái

- Nếu `matrix[i][j] == '0'` thì `dp[i][j] = 0` (không thể tạo hình vuông toàn `1`).
- Nếu `matrix[i][j] == '1'` thì:

```text
dp[i][j] = min(
	dp[i-1][j],    // phía trên
	dp[i][j-1],    // bên trái
	dp[i-1][j-1]   // chéo trên-trái
) + 1
```

Lý do: để có hình vuông cạnh `k` tại `(i, j)`, thì ba ô "nền" (trên, trái, chéo trên-trái) đều phải hỗ trợ được ít nhất cạnh `k - 1`.

## Minh họa chạy tay (theo Example 1)

Ma trận đầu vào:

```text
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```

Ta xây bảng `dp` (mỗi ô là cạnh lớn nhất tại vị trí đó):

Hàng 1:

```text
1 0 1 0 0
```

Hàng 2:

```text
1 0 1 1 1
```

Hàng 3:

```text
1 1 1 2 2
```

Giải thích ô quan trọng:

- Tại ô hàng 3 cột 4 (giá trị gốc là `1`):
  - trên = 1, trái = 1, chéo = 1
  - `dp = min(1, 1, 1) + 1 = 2`
- Nghĩa là đã tạo được hình vuông cạnh 2.

Hàng 4:

```text
1 0 0 1 0
```

Giá trị cạnh lớn nhất toàn bảng là `2`, nên diện tích là `2 * 2 = 4`.

## Vì sao dùng mảng 1 chiều?

Ta chỉ cần lưu 1 hàng DP để giảm bộ nhớ từ `O(mn)` xuống `O(n)`:

- `dp[j]`: giá trị cột `j` của hàng hiện tại (sau khi cập nhật) hoặc hàng trước (trước khi cập nhật)
- `dp[j - 1]`: ô bên trái của hàng hiện tại
- `prev`: giá trị chéo trên-trái (giá trị cũ của `dp[j - 1]` ở hàng trước)

Cuối cùng, nếu cạnh lớn nhất là `maxSide` thì kết quả là `maxSide * maxSide`.

## Mẹo nhớ nhanh

- Bài này tối ưu theo "cạnh", không tối ưu trực tiếp theo diện tích.
- Thấy hình vuông + ma trận nhị phân thì nghĩ đến công thức `min(3 ô lân cận) + 1`.
- Kết quả cuối cùng luôn là `maxSide * maxSide`.

## Độ phức tạp

- Thời gian: `O(m * n)`
- Bộ nhớ: `O(n)`

## Java code

```java
class Solution {
	public int maximalSquare(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;

		// dp[j] lưu cạnh lớn nhất của hình vuông kết thúc tại cột j - 1 của hàng hiện tại.
		int[] dp = new int[n + 1];
		int maxSide = 0;

		for (int i = 1; i <= m; i++) {
			int prev = 0; // giá trị chéo trên-trái
			for (int j = 1; j <= n; j++) {
				int temp = dp[j];

				if (matrix[i - 1][j - 1] == '1') {
					dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), prev) + 1;
					maxSide = Math.max(maxSide, dp[j]);
				} else {
					dp[j] = 0;
				}

				prev = temp;
			}
		}

		return maxSide * maxSide;
	}
}
```
