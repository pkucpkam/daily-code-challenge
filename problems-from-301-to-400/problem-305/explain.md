# 368. Largest Divisible Subset

**Độ khó:** Medium

## Mô Tả Bài Toán

Cho một tập hợp các số nguyên dương **phân biệt** `nums`, hãy tìm tập con lớn nhất sao cho mọi cặp phần tử `(answer[i], answer[j])` trong tập con này đều thỏa mãn:
- `answer[i] % answer[j] == 0`, hoặc
- `answer[j] % answer[i] == 0`

Nếu có nhiều lời giải, hãy trả về bất kỳ lời giải nào.

## Ý Tưởng Giải

Bài toán này có thể được giải quyết bằng phương pháp **Quy hoạch động (Dynamic Programming)**, tương tự như bài toán tìm **Dãy con tăng dài nhất (Longest Increasing Subsequence - LIS)**.

### 1. Sắp xếp
Đầu tiên, ta sắp xếp mảng `nums` theo thứ tự tăng dần. Khi mảng đã được sắp xếp, nếu `nums[i] % nums[j] == 0` (với `i > j`), thì `nums[i]` chắc chắn chia hết cho tất cả các số mà `nums[j]` chia hết. Đây là tính chất bắc cầu quan trọng.

### 2. Công thức quy hoạch động
- Gọi `dp[i]` là độ dài của tập con lớn nhất kết thúc tại chỉ số `i`.
- `dp[i] = max(dp[j] + 1)` với mọi `j < i` và `nums[i] % nums[j] == 0`.
- Nếu không tìm thấy `j` nào thỏa mãn, `dp[i] = 1`.

### 3. Truy vết (Backtracking)
Để trả về tập con thực sự chứ không chỉ độ dài, chúng ta cần:
- Một mảng `parent[i]` để lưu chỉ số của phần tử đứng trước `nums[i]` trong tập con.
- Sau khi tìm được chỉ số `max_index` có `dp[max_index]` lớn nhất, ta lần ngược từ `max_index` theo mảng `parent` để lấy các phần tử.

## Độ Phức Tạp

- **Thời gian:** `O(n^2)` do có hai vòng lặp lồng nhau.
- **Không gian:** `O(n)` để lưu trữ mảng `dp` và `parent`.

## Code (Java)

```java
import java.util.*;

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        if (n == 0) return new ArrayList<>();
        
        Arrays.sort(nums);
        int[] dp = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);
        
        int maxLen = 1;
        int lastIndex = 0;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (lastIndex != -1) {
            result.add(nums[lastIndex]);
            lastIndex = parent[lastIndex];
        }
        
        return result;
    }
}
```