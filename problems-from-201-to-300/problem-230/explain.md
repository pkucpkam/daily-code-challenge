# Maximum Gap

## Yêu cầu bài toán

- Cho một mảng số nguyên `nums`. Hãy tìm khoảng cách lớn nhất giữa hai phần tử kề nhau sau khi mảng đã được sắp xếp tăng dần.
- Nếu mảng có ít hơn 2 phần tử, trả về 0.
- Yêu cầu khắt khe: Thuật toán phải chạy trong thời gian tuyến tính $O(N)$ và sử dụng không gian tuyến tính $O(N)$.

## Ý tưởng cốt lõi

Việc sắp xếp mảng thông thường (như `Arrays.sort`) tốn $O(N \log N)$, không thỏa mãn yêu cầu. Để đạt được $O(N)$, chúng ta cần sử dụng thuật toán sắp xếp dựa trên phân phối, cụ thể là **Bucket Sort (Sắp xếp theo thùng)**.

Dựa trên **Nguyên lý chuồng bồ câu (Pigeonhole Principle)**:
Nếu chúng ta có $N$ số được phân bổ từ giá trị `min` đến `max`, khoảng cách trung bình giữa chúng là $(max - min) / (n - 1)$. 
Khoảng cách lớn nhất chắc chắn phải lớn hơn hoặc bằng giá trị trung bình này. Nếu chúng ta chia mảng thành các "thùng" (buckets) có kích thước nhỏ hơn khoảng cách trung bình, thì khoảng cách lớn nhất không thể nằm bên trong một thùng mà phải nằm **giữa các thùng**.

## Thuật toán

1. Kiểm tra nếu mảng có ít hơn 2 phần tử thì trả về 0.
2. Tìm giá trị nhỏ nhất (`min`) và lớn nhất (`max`) của mảng.
3. Tính kích thước thùng: `bucket_size = max(1, (max - min) / (n - 1))`.
4. Tính số lượng thùng: `bucket_count = (max - min) / bucket_size + 1`.
5. Tạo các thùng để lưu giá trị nhỏ nhất và lớn nhất của mỗi thùng.
6. Duyệt qua mảng và đưa từng số vào thùng tương ứng, cập nhật `min/max` cho mỗi thùng.
7. Duyệt qua các thùng (bỏ qua thùng trống) để tính khoảng cách giữa giá trị nhỏ nhất của thùng hiện tại và giá trị lớn nhất của thùng trước đó có dữ liệu.
8. Cập nhật và trả về khoảng cách lớn nhất tìm được.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Chúng ta duyệt qua mảng và các thùng vài lần.
- **Không gian**: $O(N)$ - Để lưu trữ thông tin của các thùng.

## Code (Java)

```java
import java.util.Arrays;

class Solution {
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) return 0;

        int n = nums.length;
        int min = nums[0], max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        if (min == max) return 0;

        int bucketSize = Math.max(1, (max - min) / (n - 1));
        int bucketCount = (max - min) / bucketSize + 1;

        int[] bucketMin = new int[bucketCount];
        int[] bucketMax = new int[bucketCount];
        Arrays.fill(bucketMin, Integer.MAX_VALUE);
        Arrays.fill(bucketMax, Integer.MIN_VALUE);

        for (int num : nums) {
            int idx = (num - min) / bucketSize;
            bucketMin[idx] = Math.min(bucketMin[idx], num);
            bucketMax[idx] = Math.max(bucketMax[idx], num);
        }

        int maxGap = 0;
        int prevMax = min;
        for (int i = 0; i < bucketCount; i++) {
            if (bucketMin[i] == Integer.MAX_VALUE) continue;
            maxGap = Math.max(maxGap, bucketMin[i] - prevMax);
            prevMax = bucketMax[i];
        }

        return maxGap;
    }
}
```
