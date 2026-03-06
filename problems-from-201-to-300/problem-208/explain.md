# Palindrome Partitioning

## Yêu cầu bài toán

- Cho một chuỗi `s`.
- Nhiệm vụ: Hãy chia chuỗi `s` thành nhiều phần sao cho **mọi chuỗi con được chia ra đều là một chuỗi Palindrome (chuỗi đối xứng)**.
- Yêu cầu: Trả về **tất cả** các cách chia (phân hoạch) có thể xảy ra.

Ví dụ: `s = "aab"`
Kết quả: `[["a", "a", "b"], ["aa", "b"]]`. Mọi phần tử tách ra trong đây (`a`, `b`, `aa`) đều là Palindrome.

## Ý tưởng cốt lõi

Bài toán này yêu cầu liệt kê **tất cả cấu hình**, nên kỹ thuật **Quay lui (Backtracking - DFS)** là sự lựa chọn hợp lý nhất để thử nghiệm mọi ranh giới cắt chữ. Bằng cách duyệt qua từng vị trí trong chuỗi để thử "cắt", nếu đoạn bị cắt ra hợp lệ (là palindrome) thì ta mới cho gọi đệ quy cắt tiếp đoạn còn lại đằng sau.

Để tránh việc phải đi kiểm tra lại tính chất đối xứng của cùng một chuỗi con nhiều lần gây phung phí, ta có thể dùng **Quy hoạch động (Dynamic Programming)** để tạo trước một bảng boolean kiểm định chuỗi con `pal[i][j]` (đoạn từ `i` đến `j` có phải Palindrome hay không).

## Thuật toán

### 1. Xây dựng mảng đánh giá trước (Tiền xử lý) bằng DP
- Khởi tạo mảng boolean 2 chiều `pal[n][n]`.
- Duyệt `i` đi ngược từ `n-1` về `0` và `j` đi tới từ `i` về `n`.
- Công thức: `pal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || pal[i + 1][j - 1]);`.
- Bất kì 2 kí tự đầu/cuối của dải giống nhau, kèm theo phần ruột bên trong của chúng cũng là Palindrome (hoặc độ dài ruột nhỏ hơn 2 kí tự), thì chuỗi to đó là Palindrome.

### 2. Thuật toán quay lui (Backtracking/DFS)
- Bắt đầu duyệt đỉnh đệ quy DFS với `start = 0`. Mảng tạm là `cur`, kết quả tổng là `ans`.
- Ở mỗi vòng lặp tại `start`, ta thử cắt vị trí tại `end` (chạy từ `start` về cuối chữ).
- Nếu `pal[start][end]` là CHUẨN (`true` - hợp lệ mảng đối xứng):
  - Ta lập tức cắt chuỗi này đưa vào tạm trữ `cur.add()`.
  - Khởi động đệ quy chạy tiếp phần chưa cắt `dfs(end + 1, ...)`.
  - Quay lui: gỡ bỏ phần đã thêm ở mảng tạm khỏi danh sách vòng `cur.removeLast()` khi DFS rẽ nhánh khác.
- Điểm đỗ: Khi index `start` đã chạy tới cuối (`start == s.length()`), nghĩa là đã tiêu hóa hết mà không dính lỗi dở dang, ta thêm bản sao mảng `cur` vào kết quả `ans`.

## Độ phức tạp

- Thời gian: `O(N * 2^N)`, vì một chữ dài $N$ có thể có cao nhất $2^N$ cách chia mảng, kèm vòng lặp `O(N)` để xử lý bảng tra `pal`. N có thể dao động $N \leq 16$ nên độ phức tạp này đáp ứng mượt mà.
- Không gian phụ: `O(N^2)` lưu bảng tra cứu hai chiều. Không gian call stack của Backtracking là `O(N)`.

## Code (Java)

```java
import java.util.*;

class Solution {
    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                pal[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || pal[i + 1][j - 1]);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        LinkedList<String> cur = new LinkedList<>();
        dfs(0, s, pal, cur, ans);
        return ans;
    }

    private void dfs(int start, String s, boolean[][] pal, LinkedList<String> cur, List<List<String>> ans) {
        if (start == s.length()) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (pal[start][end]) {
                cur.add(s.substring(start, end + 1));
                dfs(end + 1, s, pal, cur, ans);
                cur.removeLast();
            }
        }
    }
}
```
