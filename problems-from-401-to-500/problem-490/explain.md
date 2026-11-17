# 📚 Giải thích Chi tiết Bài 688 - Knight Probability in Chessboard

**Mục tiêu:** Trên bàn cờ vua kích thước $n \times n$, quân Mã xuất phát từ ô $(row, column)$ và thực hiện đúng $k$ nước đi. Ở mỗi bước, quân Mã chọn ngẫu nhiên đều 1 trong 8 nước đi hợp lệ của quân Mã (xác suất $1/8$ cho mỗi hướng). Nếu quân Mã đi ra ngoài bàn cờ, nó lập tức dừng lại. Hãy tính xác suất để sau đúng $k$ bước, quân Mã vẫn nằm bên trong bàn cờ.

---

## 🎯 Phân tích bài toán

### 1. Quy tắc di chuyển của quân Mã (Knight Moves)
Một quân Mã tại ô $(r, c)$ luôn có đúng **8 hướng di chuyển** hình chữ L:
$$(\Delta r, \Delta c) \in \{(-2,-1), (-2,1), (-1,-2), (-1,2), (1,-2), (1,2), (2,-1), (2,1)\}$$
- Mỗi hướng được chọn với xác suất như nhau là $\frac{1}{8} = 0.125$.
- Nếu ô đích $(r + \Delta r, c + \Delta c)$ nằm ngoài giới hạn $[0, n-1] \times [0, n-1]$, nhánh xác suất đó bị loại bỏ (quân Mã rời khỏi bàn cờ).

---

### 2. Bản chất Quy hoạch động & Tính chất Markov
- Giả sử sau $t$ bước, ta biết xác suất để quân Mã đang đứng tại từng ô $(r, c)$ trên bàn cờ là $P_t(r, c)$.
- Xác suất quân Mã đứng tại các ô ở bước $t + 1$ **chỉ phụ thuộc hoàn toàn vào trạng thái ở bước $t$** mà không phụ thuộc vào lộ trình trước đó (tính chất chuỗi Markov).
- Vì có rất nhiều lộ trình khác nhau có thể cùng dẫn quân Mã về chung một ô $(r, c)$ sau cùng một số bước $t$, bài toán tồn tại vô số **bài toán con gối nhau (overlapping subproblems)** $\implies$ Quy hoạch động (Dynamic Programming) là phương pháp giải quyết hoàn hảo.

---

### 3. Ràng buộc & Độ chính xác số học (Constraints & Precision)
- $1 \le n \le 25$, $0 \le k \le 100$.
- Tổng số thao tác tối đa: $k \times n^2 \times 8 \le 100 \times 25^2 \times 8 = 500,000$ phép tính $\implies$ chạy tức thì ($\approx 2\text{ ms}$).
- **Vấn đề sai số dấu phẩy động (`double`):**
  - Xác suất nhỏ nhất có thể xảy ra là $(1/8)^{100} = 2^{-300} \approx 4.9 \times 10^{-91}$, hoàn toàn nằm gọn trong miền biểu diễn chuẩn của kiểu `double` IEEE 754 ($\ge 2.2 \times 10^{-308}$), không xảy ra tràn số dưới (underflow).
  - Đặc biệt: chia cho $8.0$ thực chất là nhân với $2^{-3}$ (chỉ trừ 3 ở phần mũ của số thực nhị phân), do đó **không sinh ra bất kỳ sai số làm tròn nào** ở bước chia xác suất!

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Đệ quy vét cạn (Brute Force) | Cách 2: Top-Down DP (Memoization 3D) | Cách 3: Bottom-Up DP Tối ưu Không gian (`Rolling Array` ⭐) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Thử đệ quy cả 8 hướng ở mỗi bước | Lưu mảng 3 chiều `memo[k][r][c]` để tránh tính lặp | Chỉ lưu bảng xác suất của bước trước `prevDp` để đẩy sang bước hiện tại `currDp` |
| **Độ phức tạp Thời gian** | $\mathcal{O}(8^k)$ | $\mathcal{O}(k \cdot n^2)$ | $\mathcal{O}(k \cdot n^2)$ (bỏ qua ô có xác suất 0 giúp chạy nhanh hơn nữa) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(k)$ | $\mathcal{O}(k \cdot n^2)$ | $\mathcal{O}(n^2)$ |
| **Đánh giá** | **TLE** (với $k = 100$, $8^{100}$ quá khổng lồ) | Tốt, dễ hiểu nhưng tốn bộ nhớ 3D và overhead đệ quy | **Tối ưu tuyệt đối cả thời gian lẫn không gian bộ nhớ** |

---

## 📐 Công thức Quy hoạch động & Chứng minh (DP Formulation)

### Định nghĩa trạng thái
Gọi $dp[step][r][c]$ là xác suất để quân Mã đứng tại ô $(r, c)$ sau đúng $step$ bước đi (và chưa từng rơi ra ngoài bàn cờ trong suốt $step$ bước đó).

### Trạng thái cơ sở (Base Case)
Tại bước $0$ (chưa di chuyển), quân Mã chắc chắn $100\%$ đang đứng ở ô xuất phát $(row, column)$:
$$dp[0][row][column] = 1.0$$
$$dp[0][r][c] = 0.0 \quad \forall (r, c) \ne (row, column)$$

### Công thức chuyển trạng thái (Forward Push Transition)
Với mỗi bước $step$ từ $0$ đến $k - 1$, và mỗi ô $(r, c)$ trên bàn cờ có $dp[step][r][c] > 0$:
Với mỗi hướng $(\Delta r, \Delta c)$ trong 8 hướng của quân Mã, gọi ô đích là $(nr, nc) = (r + \Delta r, c + \Delta c)$. Nếu $0 \le nr < n$ và $0 \le nc < n$:
$$dp[step + 1][nr][nc] \mathrel{+}= \frac{dp[step][r][c]}{8.0}$$

### Kết quả bài toán
Do các biến cố "quân Mã kết thúc ở ô $(r, c)$ sau $k$ bước" là đôi một xung khắc (quân Mã chỉ có thể đứng ở duy nhất 1 ô tại một thời điểm), xác suất để quân Mã vẫn còn trên bàn cờ bằng tổng xác suất của tất cả các ô trên bàn cờ sau bước $k$:
$$\text{Answer} = \sum_{r = 0}^{n - 1} \sum_{c = 0}^{n - 1} dp[k][r][c]$$

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Bottom-Up DP Tối ưu Không gian $\mathcal{O}(N^2)$

### 💡 Kỹ thuật tối ưu mảng cuốn (Rolling Array)
Vì lớp $dp[step + 1]$ chỉ cần đọc dữ liệu từ lớp $dp[step]$, ta không cần mảng 3 chiều `double[k + 1][n][n]`. Thay vào đó ta chỉ dùng hai mảng 2 chiều `prevDp` và `currDp`:
1. Khởi tạo `prevDp[row][column] = 1.0`.
2. Lặp `step` từ `0` đến `k - 1`:
   - Tạo mảng mới `currDp = new double[n][n]`.
   - Với mọi ô `(r, c)` có `prevDp[r][c] > 0.0`, phân bổ `prevDp[r][c] / 8.0` tới các ô hợp lệ trong 8 hướng của `currDp`.
   - Cập nhật `prevDp = currDp`.
3. Cộng toàn bộ các phần tử trong `prevDp` và trả về kết quả.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `n = 3, k = 2, row = 0, column = 0`

**Bước 0 (Khởi tạo):**
```text
[ 1.0,  0.0,  0.0 ]
[ 0.0,  0.0,  0.0 ]
[ 0.0,  0.0,  0.0 ]
```

**Bước 1 (Từ `(0, 0)` có 2 nước đi hợp lệ trong bàn cờ: `(1, 2)` và `(2, 1)`, mỗi nước nhận $1.0 / 8 = 0.125$):**
```text
[ 0.0,    0.0,    0.0   ]
[ 0.0,    0.0,    0.125 ]
[ 0.0,    0.125,  0.0   ]
```
*(Tổng xác suất còn lại trên bàn cờ sau bước 1: $0.125 + 0.125 = 0.25$)*

**Bước 2:**
- Từ `(1, 2)` (xác suất $0.125$), mỗi hướng nhận $0.125 / 8 = 0.015625$:
  - Đi hợp lệ tới `(0, 0)` và `(2, 0)`.
- Từ `(2, 1)` (xác suất $0.125$), mỗi hướng nhận $0.125 / 8 = 0.015625$:
  - Đi hợp lệ tới `(0, 0)` và `(0, 2)`.

**Bảng trạng thái sau bước 2:**
```text
[ 0.03125,  0.0,  0.015625 ]
[ 0.0,      0.0,  0.0      ]
[ 0.015625, 0.0,  0.0      ]
```

👉 **Tổng xác suất sau 2 bước:** $0.03125 + 0.015625 + 0.015625 = \mathbf{0.0625}$.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    private static final int[][] DIRECTIONS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public double knightProbability(int n, int k, int row, int column) {
        double[][] prevDp = new double[n][n];
        prevDp[row][column] = 1.0;

        for (int step = 0; step < k; step++) {
            double[][] currDp = new double[n][n];

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (prevDp[r][c] > 0.0) {
                        double prob = prevDp[r][c] / 8.0;
                        for (int[] dir : DIRECTIONS) {
                            int nr = r + dir[0];
                            int nc = c + dir[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                currDp[nr][nc] += prob;
                            }
                        }
                    }
                }
            }

            prevDp = currDp;
        }

        double totalProbability = 0.0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                totalProbability += prevDp[r][c];
            }
        }

        return totalProbability;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`private static final int[][] DIRECTIONS = ...`**:
   - Khai báo hằng số tĩnh chứa 8 vector dịch chuyển của quân Mã để tránh khởi tạo lại mảng nhiều lần.
2. **`double[][] prevDp = new double[n][n]; prevDp[row][column] = 1.0;`**:
   - Thiết lập trạng thái ban đầu ở bước 0: quân Mã đứng tại `(row, column)` với xác suất tuyệt đối `1.0`. Nếu `k == 0`, vòng lặp chính không chạy và kết quả trả về là `1.0`.
3. **`if (prevDp[r][c] > 0.0)`**:
   - Bước cắt tỉa đơn giản nhưng cực kỳ hiệu quả: ở các bước đầu tiên, phần lớn các ô trên bàn cờ có xác suất bằng `0.0`. Bỏ qua các ô này tránh thực hiện 8 vòng lặp kiểm tra hướng vô ích.
4. **`double prob = prevDp[r][c] / 8.0;`**:
   - Tính sẵn xác suất chuyển tiếp một lần trước khi duyệt 8 hướng thay vì lặp lại phép chia 8 lần bên trong vòng lặp con.
5. **`if (nr >= 0 && nr < n && nc >= 0 && nc < n) currDp[nr][nc] += prob;`**:
   - Chỉ cộng dồn xác suất vào các ô nằm trong phạm vi bàn cờ. Các nước đi văng ra ngoài biên tự động bị bỏ qua.
6. **`prevDp = currDp;`**:
   - Chuyển sang bước tiếp theo bằng cách thay thế tham chiếu `prevDp` thành `currDp`.
7. **Vòng lặp tính tổng `totalProbability`**:
   - Cộng toàn bộ xác suất còn lại trên mọi ô của bàn cờ sau đúng `k` nước đi và trả về.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(K \cdot N^2)$**:
  - Vòng lặp ngoài chạy đúng $K$ lần.
  - Bên trong duyệt qua $N \times N$ ô, mỗi ô xét tối đa $8$ hướng đi (hằng số).
  - Tổng số phép toán: $\mathcal{O}(8 \cdot K \cdot N^2) = \mathcal{O}(K \cdot N^2)$.
  - Với $K \le 100$ và $N \le 25$, thuật toán chạy rất nhanh ($\approx 2\text{ ms}$ trên LeetCode).

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N^2)$**:
  - Sử dụng kỹ thuật Rolling Array nên tại mọi thời điểm chỉ giữ tối đa 2 mảng 2 chiều kích thước $N \times N$.
  - Với $N = 25$, mỗi mảng chỉ tốn $25 \times 25 \times 8\text{ bytes} = 5\text{ KB}$, giảm mạnh bộ nhớ so với $\mathcal{O}(K \cdot N^2)$ của giải pháp mảng 3 chiều.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Tư duy "Push DP" vs "Pull DP":**
   - Trong bài này, dùng **Push DP** (từ ô hiện tại đẩy xác suất `prevDp[r][c] / 8.0` sang 8 ô đích) kết hợp điều kiện `if (prevDp[r][c] > 0.0)` sẽ nhanh hơn **Pull DP** (đứng ở ô đích nhìn ngược về 8 ô nguồn) vì ở các bước đầu chỉ có rất ít ô mang xác suất khác 0.
2. **Mở rộng câu hỏi phỏng vấn (Follow-up Questions):**
   - *Nếu $k$ cực lớn (ví dụ $k = 10^9$) nhưng $n$ nhỏ (ví dụ $n \le 8$) thì làm thế nào?*
     - Lúc này ta coi mỗi ô $(r, c)$ là một đỉnh của đồ thị gồm $N^2$ đỉnh, lập **ma trận chuyển trạng thái (Transition Matrix)** kích thước $N^2 \times N^2$ với các phần tử bằng $1/8$ nếu hai ô đi tới nhau được bằng nước Mã.
     - Dùng **Lũy thừa ma trận nhanh (Matrix Exponentiation)** tính $M^k$ với độ phức tạp thời gian $\mathcal{O}(N^6 \log K)$!
   - *Bài toán tương tự có thể luyện thêm:* **LeetCode 576 - Out of Boundary Paths** (tính số cách đi ra ngoài biên trong tối đa $N$ bước — cấu trúc DP gần như giống hệt).
