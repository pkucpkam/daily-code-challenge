# 375. Guess Number Higher or Lower II

**Độ khó:** Medium

## Mô Tả Bài Toán

Trò chơi đoán số từ $1$ đến $n$. Khi bạn đoán một số $x$ sai:
* Bạn phải trả $x$ USD.
* Bạn sẽ được biết số cần tìm lớn hơn hay nhỏ hơn số bạn đoán.
* Bạn tiếp tục đoán cho tới khi tìm ra số đúng.

Hãy tìm số tiền **tối thiểu** cần chuẩn bị để **đảm bảo chắc chắn thắng** (guarantee a win), bất kể số được chọn ban đầu là số nào.

---

## Ý Tưởng Giải (Quy Hoạch Động - Dynamic Programming)

Đây là bài toán tìm chiến lược tối ưu trong trường hợp xấu nhất (**Minimax**). Chúng ta muốn **tối thiểu hóa (minimize)** số tiền phải trả trong **kịch bản tệ nhất (maximum cost)**.

Gọi $dp[i][j]$ là số tiền tối thiểu cần có để chắc chắn thắng khi đoán số trong khoảng $[i, j]$.

### 1. Trạng thái cơ bản (Base cases)
* Nếu khoảng chỉ có 1 phần tử ($i = j$) hoặc không có phần tử nào ($i > j$), ta luôn đoán trúng ngay mà không mất tiền:
  $$dp[i][j] = 0 \quad \text{với mọi } i \ge j$$

### 2. Công thức truy hồi (Transition relation)
Với khoảng $[i, j]$ (với $i < j$), ta có thể chọn đoán bất kỳ số $k$ nào nằm trong khoảng này ($i \le k \le j$).
* Nếu đoán sai và số cần tìm bé hơn $k$: ta mất phí $k$ và tiếp tục đoán trong khoảng $[i, k-1]$ $\rightarrow$ chi phí tệ nhất là $k + dp[i][k-1]$.
* Nếu đoán sai và số cần tìm lớn hơn $k$: ta mất phí $k$ và tiếp tục đoán trong khoảng $[k+1, j]$ $\rightarrow$ chi phí tệ nhất là $k + dp[k+1][j]$.
* Để **đảm bảo chắc chắn thắng** trong mọi trường hợp, ta phải lấy giá trị lớn hơn (tệ nhất) giữa hai nhánh để phòng ngừa mọi kịch bản xấu nhất:
  $$\text{Cost}(k) = k + \max(dp[i][k-1], dp[k+1][j])$$
* Ta muốn tìm chiến lược thông minh nhất (tốn ít tiền nhất), nên ta chọn số $k$ sao cho $\text{Cost}(k)$ được tối thiểu hóa:
  $$dp[i][j] = \min_{i \le k \le j} \left( k + \max(dp[i][k-1], dp[k+1][j]) \right)$$

---

## Các Tối Ưu Hóa Quan Trọng (Optimizations)

Mặc dù giải thuật quy hoạch động cơ bản có độ phức tạp $O(n^3)$ chạy rất nhanh với $n \le 200$, ta có thể tối ưu hóa để tăng tốc độ chạy gấp nhiều lần:

1. **Thu hẹp phạm vi tìm kiếm $k$ (Search Space Reduction):**
   Trong khoảng $[i, j]$, số đoán tối ưu $k$ luôn nằm ở nửa sau của khoảng đó. Do đó, ta chỉ cần duyệt $k$ bắt đầu từ giá trị trung vị:
   $$start = i + \lfloor (len - 1) / 2 \rfloor$$

2. **Bỏ qua cận trên $j$ (Strictly Less than $j$):**
   Với khoảng có độ dài $\ge 2$, đoán số lớn nhất $j$ **không bao giờ là tối ưu** vì đoán $j-1$ luôn cho phí thấp hơn (do phí đoán $j-1 < j$ và các khoảng con tương ứng có chi phí nhỏ hơn hoặc bằng). Do đó, ta chỉ cần duyệt $k < j$.

---

## Độ Phức Tạp

* **Thời gian:** $O(n^3)$ nhưng nhờ các tối ưu hóa ở trên, số lần lặp thực tế giảm đi hơn một nửa, chạy cực kỳ nhanh (~2ms trên LeetCode).
* **Không gian:** $O(n^2)$ sử dụng ma trận `dp` kích thước $(n+2) \times (n+2)$.

---

## Code (Java)

```java
class Solution {
    public int getMoneyAmount(int n) {
        // dp[i][j] lưu trữ số tiền tối thiểu để thắng trong khoảng [i, j]
        int[][] dp = new int[n + 2][n + 2];
        
        // len là độ dài của khoảng đoán
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                int minCost = Integer.MAX_VALUE;
                
                // Tối ưu hóa: k chỉ cần chạy từ nửa sau khoảng đoán và k < j
                int start = i + (len - 1) / 2;
                for (int k = start; k < j; k++) {
                    int cost = k + Math.max(dp[i][k - 1], dp[k + 1][j]);
                    minCost = Math.min(minCost, cost);
                }
                
                dp[i][j] = (len == 2) ? i : minCost;
            }
        }
        
        return dp[1][n];
    }
}
```
