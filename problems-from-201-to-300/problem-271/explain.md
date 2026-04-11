# 238. Product of Array Except Self - Best Solution

## Mô tả bài toán

Cho mảng số nguyên `nums`, hãy trả về mảng `answer` sao cho:

- `answer[i] = tích của tất cả phần tử trong nums, trừ nums[i]`

Yêu cầu:

- Không dùng phép chia.
- Thời gian `O(n)`.
- Theo follow-up: dùng `O(1)` bộ nhớ phụ (không tính mảng output).

## Ý tưởng tối ưu

Ta tách tích thành 2 phần cho mỗi vị trí `i`:

- Tích bên trái `i`.
- Tích bên phải `i`.

Cách làm:

1. Duyệt từ trái sang phải để điền `answer[i]` = tích các phần tử bên trái `i`.
2. Dùng biến `suffixProduct` để giữ tích bên phải khi duyệt từ phải sang trái.
3. Tại mỗi `i`, cập nhật `answer[i] *= suffixProduct`.

Nhờ vậy, không cần mảng phụ trái/phải riêng, chỉ dùng output + 1 biến.

## Vì sao đúng

Sau lượt duyệt trái -> phải:

- `answer[i] = nums[0] * nums[1] * ... * nums[i - 1]`

Khi duyệt phải -> trái, `suffixProduct` tại vị trí `i` chính là:

- `nums[i + 1] * nums[i + 2] * ... * nums[n - 1]`

Do đó:

- `answer[i] *= suffixProduct`
- `answer[i] = (tích bên trái i) * (tích bên phải i)`
- Đây chính là tích mọi phần tử trừ `nums[i]`.

Thuật toán xử lý đúng cả khi có số 0 vì không dùng phép chia.

## Độ phức tạp

- Thời gian: `O(n)`
- Bộ nhớ phụ: `O(1)` (không tính mảng output `answer`)

## Java Code

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        int suffixProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }

        return answer;
    }
}
```
