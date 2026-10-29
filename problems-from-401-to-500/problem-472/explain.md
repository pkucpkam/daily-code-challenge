# 📚 Giải thích Chi tiết Bài 661 - Image Smoother

**Mục tiêu:** Cho một ma trận số nguyên kích thước $m \times n$ biểu diễn ảnh xám `img` (grayscale image), trong đó mỗi phần tử đại diện cho cường độ sáng của một điểm ảnh (pixel). Hãy trả về ma trận kết quả sau khi áp dụng **bộ lọc làm mịn (image smoother)** kích thước $3 \times 3$ lên từng ô của ảnh. Giá trị trung bình của mỗi ô và 8 ô xung quanh được làm tròn xuống số nguyên gần nhất (floor division).

---

## 🎯 Phân tích bài toán

### 1. Định nghĩa Bộ lọc làm mịn ảnh (Box Filter / Mean Filter 3x3)

Trong xử lý ảnh kỹ thuật số (Digital Image Processing), bộ lọc làm mịn (còn gọi là *Box Blur* hay *Mean Filter*) là một phép tích chập (convolution) cơ bản nhằm giảm nhiễu (noise reduction) và làm mờ các chi tiết sắc nhọn.

Với mỗi điểm ảnh tại tọa độ $(i, j)$:
- Cửa sổ bộ lọc bao gồm chính ô $(i, j)$ và tối đa 8 ô lân cận xung quanh (theo cả 8 hướng: trên, dưới, trái, phải và 4 góc chéo).
- Tập hợp các tọa độ hợp lệ nằm trong cửa sổ $3 \times 3$ quanh $(i, j)$ được định nghĩa là:
  $$\mathcal{N}(i, j) = \{(r, c) \mid \max(0, i-1) \le r \le \min(m-1, i+1), \; \max(0, j-1) \le c \le \min(n-1, j+1)\}$$
- Giá trị làm mịn mới của ô $(i, j)$ bằng tổng giá trị của tất cả các ô trong $\mathcal{N}(i, j)$ chia cho số lượng ô hợp lệ $|\mathcal{N}(i, j)|$, làm tròn xuống:
  $$\text{smoothed}(i, j) = \left\lfloor \frac{\sum_{(r, c) \in \mathcal{N}(i, j)} img[r][c]}{|\mathcal{N}(i, j)|} \right\rfloor$$

```text
       Sơ đồ vùng lân cận 3x3 của ô (i, j):
       ┌───────────┬───────────┬───────────┐
       │ (i-1, j-1)│ (i-1, j)  │ (i-1, j+1)│
       ├───────────┼───────────┼───────────┤
       │ (i,   j-1)│  (i, j)   │ (i,   j+1)│
       ├───────────┼───────────┼───────────┤
       │ (i+1, j-1)│ (i+1, j)  │ (i+1, j+1)│
       └───────────┴───────────┴───────────┘
```

---

### 2. Xử lý Điều kiện Biên (Boundary Conditions)

Tùy thuộc vào vị trí của ô $(i, j)$ trên ma trận, số lượng ô lân cận hợp lệ $|\mathcal{N}(i, j)|$ sẽ thay đổi:

1. **Ô nội vi (Interior Cell):** Nằm hoàn toàn bên trong ma trận $\implies$ Có đủ $9$ ô lân cận.
2. **Ô cạnh (Edge Cell):** Nằm trên 1 trong 4 biên ngoài cùng (không phải góc) $\implies$ Có đúng $6$ ô lân cận.
3. **Ô góc (Corner Cell):** Nằm ở 4 góc của ma trận $\implies$ Có đúng $4$ ô lân cận.
4. **Trường hợp thoái hóa đặc biệt:**
   - Ma trận $1 \times 1$: Chỉ có duy nhất $1$ ô $\implies |\mathcal{N}| = 1$.
   - Ma trận dạng hàng $1 \times n$: Mỗi ô chỉ có từ $2$ đến $3$ ô lân cận.
   - Ma trận dạng cột $m \times 1$: Mỗi ô chỉ có từ $2$ đến $3$ ô lân cận.

> 💡 **Kỹ thuật xử lý biên gọn gàng:**  
> Thay vì viết nhiều nhánh `if-else` lồng nhau để kiểm tra từng ô góc hay ô cạnh, ta chỉ cần giới hạn phạm vi vòng lặp hai chiều:
> - Hàng: $r \in [\max(0, i-1), \min(m-1, i+1)]$
> - Cột: $c \in [\max(0, j-1), \min(n-1, j+1)]$
>
> Cách tiếp cận này tự động bao quát tất cả các trường hợp biên từ $1 \times 1$ cho tới ma trận kích thước bất kỳ một cách chuẩn xác 100%.

---

### 3. Thách thức Cốt lõi: Xung đột Ghi đè Trạng thái (In-Place Mutation Dilemma)

Tại sao ta **không thể** vừa duyệt vừa gán trực tiếp `img[i][j] = avg`?

Giả sử ta duyệt tuần tự từ trái sang phải, từ trên xuống dưới:
- Khi tính toán xong giá trị mới cho ô $(0, 0)$, nếu ta ghi đè ngay lập tức vào `img[0][0]`.
- Khi chuyển sang ô tiếp theo là $(0, 1)$, ô này cần lấy giá trị lân cận tại $(0, 0)$.
- Lúc này, `img[0][0]` đã bị biến đổi thành giá trị trung bình mới, **mất đi giá trị gốc ban đầu**!
- Hậu quả: Toàn bộ các ô phía sau sẽ đọc sai dữ liệu, dẫn tới kết quả sai lệch dây chuyền.

Đây là dạng bài toán kinh điển về **State Dependency (Phụ thuộc Trạng thái)**, tương tự như bài toán nổi tiếng *LeetCode 289 - Game of Life*.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Cấp phát Mảng Mới (Auxiliary Matrix) | Cách 2: Thao tác Bit Tại chỗ (Bit Manipulation In-Place) | Cách 3: Mảng Cộng dồn 2D (2D Prefix Sum / Integral Image) |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp Thời gian** | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n)$ |
| **Độ phức tạp Không gian** | $\mathcal{O}(m \cdot n)$ bộ nhớ phụ | $\mathcal{O}(1)$ Auxiliary Space | $\mathcal{O}(m \cdot n)$ bộ nhớ mảng tiền tố |
| **Bảo toàn dữ liệu vào** | Có (Immutable/Non-destructive) | Không (Ghi đè tại chỗ trên `img`) | Tùy chọn |
| **Độ phức tạp cài đặt** | Cực kỳ đơn giản | Ngắn gọn, yêu cầu hiểu sâu về Bitwise | Cần xử lý công thức Prefix 2D |
| **Phù hợp nhất khi** | Cần code an toàn, không side-effect | Cần tối ưu triệt để bộ nhớ ($\mathcal{O}(1)$) | Kernel kích thước lớn $K \times K$ ($K \gg 3$) |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Bit Manipulation In-Place với $\mathcal{O}(1)$ Bộ Nhớ

### 💡 Chìa khóa vàng: Tận dụng Ràng buộc $0 \le img[i][j] \le 255$

Hãy quan sát kỹ ràng buộc đề bài:
$$0 \le img[i][j] \le 255$$

- Trong hệ nhị phân, số $255 = 2^8 - 1 = \text{0xFF} = 11111111_2$. Tức là mỗi giá trị điểm ảnh chỉ sử dụng **tối đa đúng 8 bit**!
- Trong Java, kiểu dữ liệu `int` là một số nguyên có độ rộng lên tới **32 bit**:
  $$\underbrace{00000000}_{\text{bit 24..31}} \quad \underbrace{00000000}_{\text{bit 16..23}} \quad \underbrace{00000000}_{\text{bit 8..15 (trống)}} \quad \underbrace{XXXXXXXX}_{\text{bit 0..7 (giá trị gốc)}}$$

Điều này mang lại một cơ hội tuyệt vời: **Ta hoàn toàn có thể nhét cả 2 trạng thái (Giá trị Cũ & Giá trị Mới) vào cùng một ô nhớ 32-bit!**

```text
       Sơ đồ phân bổ Bit trong một phần tử int (32 bits):
       
       ┌──────────────────┬──────────────────┬──────────────────┐
       │   Bit 16 -> 31   │   Bit 8 -> 15    │    Bit 0 -> 7    │
       │    (Không dùng)  │  GIÁ TRỊ MỚI     │   GIÁ TRỊ GỐC    │
       │      00000000    │ (Smoothed Value) │ (Original Value) │
       └──────────────────┴──────────────────┴──────────────────┘
```

---

### 🛠️ Bộ ba phép toán Bit cốt lõi

1. **Trích xuất giá trị gốc (Đọc an toàn):**
   Dù một ô $(r, c)$ đã được tính toán hay chưa, giá trị gốc của nó luôn nằm nguyên vẹn tại 8 bit thấp. Ta chỉ cần áp dụng mặt nạ bit `0xFF` (`255`):
   $$\text{originalVal} = img[r][c] \ \& \ \text{0xFF}$$

2. **Lưu trữ giá trị mới mà không phá hủy giá trị gốc (Ghi an toàn):**
   Sau khi tính được giá trị trung bình $avg$, ta dịch trái $8$ bit để đưa $avg$ vào vùng bit từ 8 đến 15, sau đó kết hợp với giá trị hiện tại bằng phép toán bit OR (`|`):
   $$img[i][j] = img[i][j] \ | \ (avg \ll 8)$$
   Lúc này, ô $(i, j)$ đồng thời chứa:
   - Giá trị gốc ở bit 0–7.
   - Giá trị trung bình mới ở bit 8–15.

3. **Cập nhật giá trị cuối cùng (Hoàn tất):**
   Sau khi đã duyệt qua toàn bộ ma trận và tất cả các ô đều đã được tính toán, ta thực hiện lượt duyệt thứ hai để dịch phải $8$ bit:
   $$img[i][j] = img[i][j] \gg 8$$
   Phép dịch phải này sẽ vứt bỏ 8 bit cũ và đưa giá trị làm mịn mới về lại vị trí bit 0–7.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### 1. Phiên bản Tối ưu nhất: In-Place Bit Manipulation ($\mathcal{O}(1)$ Space)

```java
class Solution {
    /**
     * Thuật toán Tối ưu: Bit Manipulation (Thao tác Bit tại chỗ - In-Place)
     * 
     * Độ phức tạp:
     * - Thời gian: O(m * n) - Duyệt qua mỗi phần tử đúng 2 lần với hằng số lân cận tối đa là 9.
     * - Không gian phụ trợ: O(1) Auxiliary Space - Không cấp phát thêm bất kỳ mảng 2D nào.
     */
    public int[][] imageSmoother(int[][] img) {
        int m = img.length;
        int n = img[0].length;

        // Bước 1: Duyệt từng ô, tính giá trị làm mịn và mã hóa vào bit 8 -> 15
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                int count = 0;

                // Xác định biên của cửa sổ 3x3 bao quanh ô (i, j)
                int rStart = Math.max(0, i - 1);
                int rEnd = Math.min(m - 1, i + 1);
                int cStart = Math.max(0, j - 1);
                int cEnd = Math.min(n - 1, j + 1);

                for (int r = rStart; r <= rEnd; r++) {
                    for (int c = cStart; c <= cEnd; c++) {
                        // Trích xuất giá trị gốc bằng mặt nạ 0xFF (bit 0 -> 7)
                        sum += (img[r][c] & 0xFF);
                        count++;
                    }
                }

                int avg = sum / count;
                // Mã hóa giá trị trung bình vào các bit 8 -> 15 của img[i][j]
                img[i][j] |= (avg << 8);
            }
        }

        // Bước 2: Dịch phải 8 bit để khôi phục giá trị làm mịn mới làm kết quả chính thức
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                img[i][j] >>= 8;
            }
        }

        return img;
    }
}
```

---

### 2. Phiên bản Tham khảo: Tạo Mảng Mới (Safe Simulation - Non-destructive)

Dành cho các tình huống thực tế cần bảo toàn tuyệt đối ma trận đầu vào (tránh side-effect):

```java
class SolutionSafe {
    public int[][] imageSmoother(int[][] img) {
        int m = img.length;
        int n = img[0].length;
        int[][] result = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                int count = 0;

                int rStart = Math.max(0, i - 1);
                int rEnd = Math.min(m - 1, i + 1);
                int cStart = Math.max(0, j - 1);
                int cEnd = Math.min(n - 1, j + 1);

                for (int r = rStart; r <= rEnd; r++) {
                    for (int c = cStart; c <= cEnd; c++) {
                        sum += img[r][c];
                        count++;
                    }
                }

                result[i][j] = sum / count;
            }
        }

        return result;
    }
}
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Ví dụ 1: `img = [[1,1,1],[1,0,1],[1,1,1]]`

Ma trận kích thước $3 \times 3$:

```text
       1   1   1
       1   0   1
       1   1   1
```

#### Bảng tính chi tiết từng vị trí:

| Vị trí $(i, j)$ | Loại ô | Các ô lân cận hợp lệ | Tổng giá trị ($sum$) | Số ô ($count$) | Phép chia làm tròn xuống | Kết quả $avg$ |
| :---: | :---: | :--- | :---: | :---: | :---: | :---: |
| $(0, 0)$ | Góc | $(0,0), (0,1), (1,0), (1,1)$ | $1 + 1 + 1 + 0 = 3$ | $4$ | $\lfloor 3 / 4 \rfloor$ | **$0$** |
| $(0, 1)$ | Cạnh | $(0,0), (0,1), (0,2), (1,0), (1,1), (1,2)$ | $1 + 1 + 1 + 1 + 0 + 1 = 5$ | $6$ | $\lfloor 5 / 6 \rfloor$ | **$0$** |
| $(0, 2)$ | Góc | $(0,1), (0,2), (1,1), (1,2)$ | $1 + 1 + 0 + 1 = 3$ | $4$ | $\lfloor 3 / 4 \rfloor$ | **$0$** |
| $(1, 0)$ | Cạnh | $(0,0), (0,1), (1,0), (1,1), (2,0), (2,1)$ | $1 + 1 + 1 + 0 + 1 + 1 = 5$ | $6$ | $\lfloor 5 / 6 \rfloor$ | **$0$** |
| $(1, 1)$ | Tâm | Cả 9 ô trong ma trận | $8 \times 1 + 0 = 8$ | $9$ | $\lfloor 8 / 9 \rfloor$ | **$0$** |
| $(1, 2)$ | Cạnh | $(0,1), (0,2), (1,1), (1,2), (2,1), (2,2)$ | $1 + 1 + 0 + 1 + 1 + 1 = 5$ | $6$ | $\lfloor 5 / 6 \rfloor$ | **$0$** |
| $(2, 0)$ | Góc | $(1,0), (1,1), (2,0), (2,1)$ | $1 + 0 + 1 + 1 = 3$ | $4$ | $\lfloor 3 / 4 \rfloor$ | **$0$** |
| $(2, 1)$ | Cạnh | $(1,0), (1,1), (1,2), (2,0), (2,1), (2,2)$ | $1 + 0 + 1 + 1 + 1 + 1 = 5$ | $6$ | $\lfloor 5 / 6 \rfloor$ | **$0$** |
| $(2, 2)$ | Góc | $(1,1), (1,2), (2,1), (2,2)$ | $0 + 1 + 1 + 1 = 3$ | $4$ | $\lfloor 3 / 4 \rfloor$ | **$0$** |

$\implies$ Kết quả trả về: `[[0,0,0],[0,0,0],[0,0,0]]`.

---

### Ví dụ 2: Minh họa Quá trình Đóng gói Bit trên Ô $(0, 0)$

Xét ô $(0, 0)$ trong Ví dụ 2 với giá trị gốc là $100$:
- Giá trị gốc: $100_{10} = \text{0x0064} = 00000000 \; 01100100_2$.
- Tính được $avg = 137_{10} = \text{0x0089} = 00000000 \; 10001001_2$.

#### Diễn biến các bước thao tác bit:
1. **Dịch trái $avg$ 8 bit:**
   $$avg \ll 8 = \text{0x8900} = 10001001 \; 00000000_2$$
2. **Gộp vào `img[0][0]` bằng phép OR:**
   $$img[0][0] = \text{0x0064} \ | \ \text{0x8900} = \text{0x8964} = 10001001 \; 01100100_2 \quad (35172_{10})$$
3. **Khi ô lân cận $(0, 1)$ đọc ô $(0, 0)$:**
   $$img[0][0] \ \& \ \text{0x00FF} = \text{0x8964} \ \& \ \text{0x00FF} = \text{0x0064} = 100_{10}$$
   > ✨ Giá trị gốc $100$ được bảo toàn chính xác tuyệt đối, không hề bị ảnh hưởng bởi giá trị $137$ mới được đóng gói!
4. **Ở lượt duyệt cuối (Dịch phải 8 bit):**
   $$img[0][0] \gg 8 = \text{0x8964} \gg 8 = \text{0x0089} = 137_{10}$$
   Ô $(0, 0)$ chính thức nhận giá trị mới $137$.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(m \cdot n)$
- Ma trận có tổng cộng $m \times n$ ô.
- Tại mỗi ô, hai vòng lặp lồng nhau duyệt tối đa qua cửa sổ $3 \times 3 = 9$ ô.
- Số lượng phép toán ở mỗi ô bị chặn trên bởi một hằng số $C \le 9$.
- Lượt duyệt thứ hai dịch bit thực hiện đúng $m \times n$ phép dịch bit $\mathcal{O}(1)$.
- Tổng thời gian thực thi:
  $$\mathcal{T}(m, n) = \mathcal{O}(9 \cdot m \cdot n) + \mathcal{O}(m \cdot n) = \mathcal{O}(m \cdot n)$$
- Với $m, n \le 200$, số phép toán tối đa chỉ khoảng $200 \times 200 \times 9 \approx 3.6 \times 10^5$, hoàn thành trong chưa đầy **$2 \text{ ms}$** trên máy ảo Java!

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(1)$ Auxiliary Space
- Toàn bộ quá trình tính toán và lưu trữ đều được thực hiện trực tiếp trên ma trận đầu vào `img`.
- Không sử dụng thêm bất kỳ mảng 2 chiều phụ nào $\implies$ Không gian phụ trợ đạt mức tối ưu tuyệt đối: $\mathcal{O}(1)$.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Kỹ thuật Đóng gói Trạng thái (State Packing / Bit Manipulation)
- Khi một bài toán trên ma trận yêu cầu cập nhật đồng thời (simultaneous update) mà mỗi phần tử có miền giá trị nhỏ hơn kiểu dữ liệu của nó (ví dụ $[0, 255]$ trong `int` 32-bit), hãy nghĩ ngay tới **Bit Manipulation In-Place**.
- Các bài toán kinh điển tương tự trên LeetCode:
  - **289. Game of Life:** Trạng thái sống/chết ban đầu lưu ở bit 0, trạng thái mới lưu ở bit 1.
  - **73. Set Matrix Zeroes:** Tận dụng hàng 0 và cột 0 làm mảng cờ hiệu (flag markers).
  - **48. Rotate Image:** Xoay ma trận tại chỗ bằng phép chuyển vị (transpose) và đảo cột (reflect).

### 2. Trade-off trong Phỏng vấn Kỹ thuật (Engineering Trade-off)
Khi phỏng vấn tại các công ty lớn (FAANG/Big Tech), đừng vội vàng viết ngay code in-place mà hãy thảo luận với người phỏng vấn:
- **Phương án an toàn (Pure Function):** Giải thích rằng trong môi trường đa luồng (multi-threading) hoặc hệ thống thực tế, hàm không nên làm thay đổi dữ liệu đầu vào (in-place mutation) để tránh side-effect.
- **Phương án tối ưu bộ nhớ:** Khi hệ thống bị giới hạn tài nguyên nghiêm ngặt (embedded systems, vi điều khiển, GPU shader), giải pháp **Bit Manipulation $\mathcal{O}(1)$ bộ nhớ** là minh chứng rõ nét cho năng lực tối ưu hóa cấp thấp của bạn!
