# 213. House Robber II - Giải thích lời giải tối ưu

## Ý tưởng cốt lõi

Với phiên bản House Robber thường (nhà xếp thẳng hàng), công thức quy hoạch động là:

- `dp[i] = max(dp[i - 1], dp[i - 2] + nums[i])`

Trong bài này, các nhà nằm trên **vòng tròn**, nên nhà đầu và nhà cuối là kề nhau.
Vì vậy ta **không thể cướp đồng thời** cả 2 nhà này.

Ta tách bài toán thành 2 trường hợp tuyến tính:

1. Cướp trong đoạn `0..n-2` (bỏ nhà cuối).
2. Cướp trong đoạn `1..n-1` (bỏ nhà đầu).

Đáp án cuối cùng là:

- `max(robLinear(0, n-2), robLinear(1, n-1))`

## Xử lý biên

- Nếu `n == 1` thì chỉ có một nhà, kết quả là `nums[0]`.

## Tối ưu bộ nhớ về O(1)

Thay vì mảng `dp`, ta chỉ cần 2 biến:

- `prev2` tương ứng `dp[i - 2]`
- `prev1` tương ứng `dp[i - 1]`

Khi xét nhà `i`:

- `take = prev2 + nums[i]` (cướp nhà i)
- `skip = prev1` (bỏ nhà i)
- `cur = max(take, skip)`

Sau đó dịch cửa sổ:

- `prev2 = prev1`
- `prev1 = cur`

## Độ đúng

Trong mỗi đoạn tuyến tính, công thức DP đảm bảo tại mỗi vị trí đều chọn phương án tốt nhất giữa:

- Không cướp nhà hiện tại.
- Cướp nhà hiện tại và cộng kết quả tốt nhất đến `i-2`.

Do ràng buộc vòng tròn chỉ tạo xung đột giữa nhà đầu và nhà cuối, việc chia thành 2 đoạn loại trừ xung đột đó là đầy đủ và không bỏ sót nghiệm tối ưu.

## Độ phức tạp

- Thời gian: `O(n)`
- Bộ nhớ phụ: `O(1)`

## Java code

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int case1 = robLinear(nums, 0, n - 2);
        int case2 = robLinear(nums, 1, n - 1);
        return Math.max(case1, case2);
    }

    private int robLinear(int[] nums, int left, int right) {
        int prev2 = 0;
        int prev1 = 0;

        for (int i = left; i <= right; i++) {
            int take = prev2 + nums[i];
            int skip = prev1;
            int cur = Math.max(take, skip);
            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }
}
```
