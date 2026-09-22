# 📚 Giải thích Chi tiết Bài 598 - Range Addition II

**Mục tiêu:** Cho một ma trận kích thước `m x n` ban đầu toàn số 0, cùng danh sách các thao tác `ops` trong đó mỗi thao tác `ops[i] = [a_i, b_i]` cộng thêm 1 vào tất cả các ô từ hàng `0` đến `a_i - 1` và từ cột `0` đến `b_i - 1`. Nhiệm vụ là đếm số lượng phần tử có giá trị lớn nhất trong ma trận sau khi hoàn thành tất cả các thao tác.

---

## 🎯 Phân tích bài toán

### 1. Bản chất của mỗi thao tác
- Mỗi thao tác `[a_i, b_i]` cập nhật một vùng hình chữ nhật có:
  - Góc trên bên trái cố định tại: `(0, 0)`.
  - Góc dưới bên phải tại: `(a_i - 1, b_i - 1)`.
  - Số lượng hàng được tăng: $a_i$ hàng ($0 \le x < a_i$).
  - Số lượng cột được tăng: $b_i$ cột ($0 \le y < b_i$).

### 2. Cảnh báo bẫy mô phỏng ngây thơ (Brute-force Simulation Trap)
Nhiều bạn mới làm sẽ nghĩ ngay đến việc:
1. Khởi tạo một mảng 2 chiều `int[][] M = new int[m][n]`.
2. Với mỗi phép toán `[a, b]` trong `ops`, dùng 2 vòng lặp lồng nhau duyệt từ $x = 0 \to a-1$ và $y = 0 \to b-1$ để cộng dồn `M[x][y]++`.
3. Sau đó tìm giá trị lớn nhất và đếm số lần xuất hiện của nó.

**⚠️ Tại sao cách làm này sẽ thất bại hoàn toàn?**
- **Tràn bộ nhớ (Memory Limit Exceeded / OutOfMemoryError):**
  - Ràng buộc đề bài: $m, n \le 4 \times 10^4$.
  - Kích thước ma trận có thể lên tới $m \times n = 4 \cdot 10^4 \times 4 \cdot 10^4 = 1.6 \times 10^9$ phần tử.
  - Một mảng `int` chứa $1.6 \times 10^9$ số nguyên cần tối thiểu:
    $$1.6 \times 10^9 \times 4 \text{ bytes} \approx 6.4 \text{ GB RAM}$$
    Vượt xa giới hạn bộ nhớ cho phép của LeetCode (thường là 256MB hoặc 512MB).
- **Hết thời gian (Time Limit Exceeded):**
  - Số lượng thao tác có thể lên tới $10^4$.
  - Độ phức tạp thời gian mô phỏng là $\mathcal{O}(k \times m \times n) \approx 10^4 \times 1.6 \times 10^9 = 1.6 \times 10^{13}$ phép tính, chương trình sẽ bị treo ngay lập tức.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Giao của các hình chữ nhật (Intersection of Rectangles)

### 💡 Ý tưởng cốt lõi (Key Insights)

1. **Gốc tọa độ `(0, 0)` luôn luôn được tăng trong MỌI thao tác:**
   - Vì mỗi thao tác `[a_i, b_i]` đều phủ một hình chữ nhật có góc trái trên là `(0, 0)` (với $1 \le a_i \le m$ và $1 \le b_i \le n$).
   - Do đó, ô `(0, 0)` **chắc chắn thuộc về tất cả các hình chữ nhật**.
   - Sau khi thực hiện toàn bộ $k$ thao tác ($k = \text{ops.length}$), ô `(0, 0)` có giá trị là $k$.
   - Vì không ô nào có thể được tăng nhiều hơn $k$ lần, nên **giá trị lớn nhất trong ma trận chắc chắn bằng $k$** (hoặc bằng `0` nếu không có thao tác nào).

2. **Điều kiện để một ô `(x, y)` đạt giá trị lớn nhất:**
   - Để ô `(x, y)` cũng đạt giá trị cực đại đó, ô `(x, y)` bắt buộc phải **nằm trong phạm vi của TẤT CẢ các thao tác**.
   - Tức là với mọi thao tác $i$:
     $$0 \le x < a_i \quad \text{và} \quad 0 \le y < b_i$$
   - Điều này đồng nghĩa:
     $$0 \le x < \min(a_i) \quad \text{và} \quad 0 \le y < \min(b_i)$$

3. **Hình học: Phần giao nhau (Intersection):**
   - Vùng chứa tất cả các ô đạt giá trị cực đại chính là **phần giao nhau** của tất cả các hình chữ nhật thao tác (và bị giới hạn bởi kích thước ma trận ban đầu $m \times n$).
   - Giao của các hình chữ nhật cùng xuất phát từ `(0, 0)` lại là một hình chữ nhật mới xuất phát từ `(0, 0)` với kích thước:
     $$\text{minRow} = \min(m, \min_i a_i)$$
     $$\text{minCol} = \min(n, \min_i b_i)$$
   - Số lượng ô thỏa mãn chính là diện tích của hình chữ nhật giao nhau này:
     $$\text{Kết quả} = \text{minRow} \times \text{minCol}$$

---

### 💻 Mã nguồn Java (Best Solution)

```java
class Solution {
    /**
     * Best Solution: Tìm phần giao nhau của tất cả các hình chữ nhật thao tác.
     *
     * Time Complexity: O(k) với k = ops.length (duyệt 1 vòng qua mảng ops).
     * Space Complexity: O(1) (chỉ dùng 2 biến nguyên lưu min).
     */
    public int maxCount(int m, int n, int[][] ops) {
        int minRow = m;
        int minCol = n;

        for (int[] op : ops) {
            minRow = Math.min(minRow, op[0]);
            minCol = Math.min(minCol, op[1]);
        }

        return minRow * minCol;
    }
}
```

---

### 🔍 Giải thích chi tiết từng dòng mã

1. **Khởi tạo:**
   ```java
   int minRow = m;
   int minCol = n;
   ```
   - Ban đầu, khi chưa xét phép toán nào (hoặc nếu `ops` rỗng), vùng tối đa chính là toàn bộ ma trận $m \times n$.
2. **Duyệt qua từng thao tác `op`:**
   ```java
   for (int[] op : ops) {
       minRow = Math.min(minRow, op[0]);
       minCol = Math.min(minCol, op[1]);
   }
   ```
   - Mỗi thao tác `op = [a, b]` thu hẹp chiều cao tối đa xuống $\min(\text{minRow}, a)$ và chiều rộng tối đa xuống $\min(\text{minCol}, b)$.
3. **Tính diện tích vùng giao:**
   ```java
   return minRow * minCol;
   ```
   - Tích của hai kích thước chính là tổng số ô được tăng giá trị đầy đủ ở mọi thao tác.

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :--- | :--- | :--- |
| **Mô phỏng trực tiếp (Brute Force)** | $\mathcal{O}(k \cdot m \cdot n)$ | $\mathcal{O}(m \cdot n)$ | **Bị loại:** Gặp lỗi `OutOfMemoryError` và `Time Limit Exceeded`. |
| **Best Solution (Intersection of Rectangles)** | $\mathcal{O}(k)$ | $\mathcal{O}(1)$ | **Tối ưu tuyệt đối:** Thời gian chạy `0ms` (Beats 100%), bộ nhớ `O(1)`. |

- **Thời gian ($\mathcal{O}(k)$):** Với $k$ là số lượng thao tác trong `ops` ($0 \le k \le 10^4$). Ta chỉ duyệt qua mảng `ops` đúng 1 vòng lặp. Mỗi bước chỉ tốn 2 phép toán so sánh `Math.min`.
- **Không gian ($\mathcal{O}(1)$):** Không tạo mảng hay cấp phát bất kỳ vùng nhớ phụ nào ngoài 2 biến số nguyên nguyên thủy `minRow` và `minCol`.

---

## ⚠️ Các trường hợp đặc biệt (Corner / Edge Cases)

1. **Mảng `ops` rỗng (`ops.length == 0`):**
   - Không có thao tác nào được thực hiện.
   - Toàn bộ $m \times n$ ô đều giữ nguyên giá trị ban đầu là `0` (và `0` chính là giá trị lớn nhất).
   - Vòng lặp `for` không chạy, hàm trả về ngay $m \times n$, hoàn toàn chính xác.
2. **Không lo tràn số kiểu `int`:**
   - Giá trị lớn nhất của $m \times n$ theo ràng buộc:
     $$4 \times 10^4 \times 4 \times 10^4 = 1.6 \times 10^9$$
   - Giá trị cực đại của `int` 32-bit có dấu trong Java là $2^{31} - 1 \approx 2.147 \times 10^9$.
   - Vì $1.6 \times 10^9 < 2.147 \times 10^9$, phép nhân `minRow * minCol` hoàn toàn an toàn trong kiểu `int` mà không lo bị tràn số (overflow).
3. **Thao tác có kích thước tối thiểu $1 \times 1$:**
   - Nếu có ít nhất một thao tác là `[1, 1]`, thì `minRow = 1` và `minCol = 1`.
   - Kết quả trả về `1 * 1 = 1` (chỉ duy nhất ô `(0, 0)` đạt giá trị lớn nhất).
