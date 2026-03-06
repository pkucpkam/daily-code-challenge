# Word Break

## Yêu cầu bài toán

- Cho một chuỗi `s` và một danh sách các từ `wordDict` (từ điển).
- Xác định xem chuỗi `s` có thể được phân tách thành một chuỗi các từ (cách nhau bởi khoảng trắng) sao cho mỗi từ đều nằm trong từ điển hay không.
- Bạn có thể tái sử dụng các từ trong từ điển nhiều lần.

## Ý tưởng cốt lõi

Đây là một bài toán điển hình của **Quy hoạch động (Dynamic Programming)**. Thay vì thử tất cả các cách phân tách (có thể dẫn đến độ phức tạp lũy thừa), chúng ta sẽ chia nhỏ bài toán.

Gọi `dp[i]` là một giá trị boolean cho biết liệu chuỗi con `s[0...i-1]` (độ dài `i`) có thể phân tách được hay không.
Để tính `dp[i]`, chúng ta kiểm tra mọi vị trí `j` từ `0` đến `i-1`:
- Nếu `dp[j]` là đúng (chuỗi con đến `j` phân tách được) VÀ chuỗi con còn lại `s[j...i-1]` nằm trong từ điển.
- Thì `dp[i]` sẽ là đúng.

## Thuật toán

1. Sử dụng một `HashSet` để lưu trữ `wordDict` giúp việc tra cứu từ có độ phức tạp $O(1)$.
2. Tìm chiều dài của từ dài nhất trong từ điển (`maxLen`) để tối ưu vòng lặp kiểm tra chuỗi con.
3. Khởi tạo mảng `dp` kích thước `n + 1` với `dp[0] = true` (chuỗi rỗng luôn coi là phân tách được).
4. Duyệt `i` từ 1 tới `n` (độ dài chuỗi con đang xét):
   - Duyệt ngược `j` từ `i-1` xuống tối thiểu `i - maxLen`:
     - Nếu `dp[j]` bằng `true` và `s.substring(j, i)` có trong `HashSet`:
       - Đặt `dp[i] = true` và dừng vòng lặp trong.
5. Kết quả trả về là `dp[n]`.

## Độ phức tạp
- **Thời gian**: $O(N \cdot maxLen)$ - Với $N$ là độ dài của chuỗi `s` và $maxLen$ là độ dài từ lớn nhất trong từ điển.
- **Không gian**: $O(N + K)$ - Với $N$ là kích thước mảng `dp` và $K$ là số lượng từ trong từ điển lưu trong HashSet.

## Code (Java)

```java
import java.util.*;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null) return false;
        
        Set<String> set = new HashSet<>(wordDict);
        int n = s.length();
        int maxLen = 0;
        for (String w : wordDict) {
            maxLen = Math.max(maxLen, w.length());
        }

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            // Chỉ cần kiểm tra các từ có độ dài tối đa là maxLen
            for (int j = i - 1; j >= 0 && i - j <= maxLen; j--) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
```
