# Maximum Product Subarray

## Yêu cầu bài toán

- Cho một mảng số nguyên `nums`.
- Tìm một mảng con liên tiếp (có ít nhất một số) sao cho tích của các phần tử trong đó là lớn nhất.
- Trả về giá trị tích lớn nhất đó.

## Ý tưởng cốt lõi

Bài toán này thoạt nhìn giống như bài toán "Tổng mảng con lớn nhất" (Kadane's algorithm), nhưng tích của các số lại phức tạp hơn do sự hiện diện của số âm và số không:
- Nhân một số âm với một số âm khác sẽ tạo ra một số dương lớn.
- Số không sẽ làm cho tích của bất kỳ mảng con nào chứa nó trở về không.

Vì vậy, tại mỗi vị trí, chúng ta không chỉ cần theo dõi tích lớn nhất (`max_so_far`), mà còn phải theo dõi cả tích nhỏ nhất (`min_so_far`) kết thúc tại điểm đó. Một tích cực âm có thể trở thành tích cực dương rất lớn nếu gặp một số âm tiếp theo.

## Thuật toán

Chúng ta sử dụng kỹ thuật Quy hoạch động (Dynamic Programming) tối ưu không gian:
1. Khởi tạo `res`, `curMax`, `curMin` bằng giá trị của phần tử đầu tiên trong mảng.
2. Duyệt qua mảng bắt đầu từ phần tử thứ hai:
   - Nếu số hiện tại `nums[i]` là số âm, hãy tráo đổi giá trị của `curMax` và `curMin`. (Vì số lớn nhất nhân số âm sẽ thành nhỏ nhất, và số nhỏ nhất nhân số âm sẽ thành lớn nhất).
   - Cập nhật `curMax`: là giá trị lớn nhất giữa `nums[i]` và `curMax * nums[i]`.
   - Cập nhật `curMin`: là giá trị nhỏ nhất giữa `nums[i]` và `curMin * nums[i]`.
   - Cập nhật kết quả cuối cùng `res = max(res, curMax)`.
3. Trả về `res`.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt mảng một lần duy nhất.
- **Không gian**: $O(1)$ - Chỉ sử dụng ba biến nguyên.

## Code (Java)

```java
class Solution {
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;

        int res = nums[0];
        int curMax = nums[0];
        int curMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Khi gặp số âm, cực đại và cực tiểu sẽ đổi chỗ cho nhau
            if (nums[i] < 0) {
                int temp = curMax;
                curMax = curMin;
                curMin = temp;
            }

            // Tính toán giá trị mới tại vị trí i
            curMax = Math.max(nums[i], curMax * nums[i]);
            curMin = Math.min(nums[i], curMin * nums[i]);

            // Cập nhật kết quả tốt nhất từng thấy
            res = Math.max(res, curMax);
        }

        return res;
    }
}
```
