# Palindrome Partitioning II

## Yêu cầu bài toán

- Cho một chuỗi `s`.
- Bạn có thể thực hiện thao tác cắt chữ. Cắt sao cho mọi cấu thành chuỗi phụ bị rời ra đều là một chuỗi Palindrome (đối xứng).
- Yêu cầu: Trả về **SỐ LẦN CẮT TỐI THIỂU** để làm thỏa mãn điều kiện đó.

Ví dụ: `s = "aab"`
=> Số lần cắt mỏng nhất: 1 lần. ("aa" | "b"). Hai cục đều rập khuôn hợp pháp Palindrome.

## Ý tưởng cốt lõi

So với phiên bản I (chỉ in mọi cách chia), phiên bản II này đặc biệt hỏi số liệu tối thiểu - mà lại cấm liệt kê không kiểm soát vì có thể TLE (Quá thời gian - $N$ max là $2000$). Nên ta không dùng DFS Backtracking, mà phải dồn toàn bộ não vào **Quy hoạch động (Dynamic Programming)** 1 chiều.

1. Bảng DP phụ: Dựa vào kinh nghiệm bài 1, ta làm ra bảng tiền xử lý khả năng Palindrome của đoạn con: `pal[i][j] = ...`. 
2. Xây mảng DP chính: `dp[i]` lưu trữ số nhát cắt tối thiểu để chia đẹp được **đoạn chuỗi con trải từ index `0` tới `i`**.
   - Nếu bản thân nguyên trọn khúc từ `0` tới `i` đã là Palindrome `pal[0][i] == true`, thì khỏi cần phải cắt tí nào! Đặt `dp[i] = 0`.
   - Nếu không, ta phải lùng sục kiếm điểm chốt `j` làm nhát cắt chẻ ngang khúc `(0 -> i)`. Điều kiện nhát cắt hợp tác là đoạn lưng (`j` tới `i`) phải là Palindrome. Khi cái đuôi từ `j` chui được khe hẹp, nhát cắt tiếp theo sẽ ké của đoạn đầu `dp[j-1]` là xong. Cố tìm ranh giới `j` cho số cắt nhỏ nhất.

## Thuật toán

1. Tiền duyệt khởi động ma trận bool 2D `pal[n][n]` xác nhận bảng true/false đối xứng cực nhanh (Mất $O(N^2)$).
2. Chuẩn bị mảng đếm 1D `dp[n]`:
3. Duỗi vòng for tìm `i` tịnh tiến:
   - Nếu đoạn `0..i` đối xứng trọn vẹn: `dp[i] = 0`. Bỏ qua.
   - Nếu không, dò vòng for index ranh giới `j` chạy từ `1` lên `i`:
     - Nếu đoạn đuôi `(j..i)` được xác nhận trong mảng nháp `pal` là Palindrome:
     - Biến `minCuts = Math.min(minCuts, dp[j-1] + 1)`. 
   - Sau khi thử đủ nơi nhạy cảm, chốt hạ ghi `dp[i] = minCuts`.

4. Ở điểm kết, mảng `dp[n-1]` nắm vận mệnh số lượng cắt tối giản nhất của cả chuỗi dài. 

## Vì sao đúng và tối ưu?

- Quy hoạch động đã tính tất cả mọi trường hợp (đã cắt hoặc chưa, cắt ở đâu là đẹp), qua đó bảo chứng việc luôn bám vào giá giảm để tích dần lên số to. Mọi khúc tính sau chỉ mượn trực tiếp của khúc đầu mà đoạn kia đã là chân lí nên không lệch đi đâu số lượng CẮT.

## Độ phức tạp
- Thời gian: `O(N^2)`. Có hai vòng lặp lồng lớn, 1 vòng tìm sự tương thích Palindrome gốc và 1 vòng nhảy mảng Cắt theo ranh giới, đều tiêu giảm $O(N^2)$. Giới hạn 2000 -> 4 triệu lần chạy (rất mượt đối với tiêu chuẩn LeetCode).
- Không gian bộ nhớ phụ: `O(N^2)` cho ma trận DP Boolean và `O(N)` cho mảng theo dõi nhát chém DP nguyên thủy.

## Code (Java)

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                pal[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 2 || pal[i + 1][j - 1]);
            }
        }

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (pal[0][i]) {
                dp[i] = 0;
                continue;
            }
            int minCuts = Integer.MAX_VALUE;
            // j đóng vai trò là "điểm bắt đầu của phần đuôi nằm sau nhát cắt cuối cùng"
            for (int j = 1; j <= i; j++) {
                if (pal[j][i]) {
                    minCuts = Math.min(minCuts, dp[j - 1] + 1);
                }
            }
            dp[i] = minCuts;
        }
        return dp[n - 1];
    }
}
```
