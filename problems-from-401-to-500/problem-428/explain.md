# 📚 Giải thích Chi tiết Bài 593 - Valid Square

**Mục tiêu:** Cho tọa độ của 4 điểm trong không gian 2D: `p1`, `p2`, `p3`, `p4` (theo thứ tự ngẫu nhiên). Xác định xem 4 điểm này có tạo thành một **hình vuông hợp lệ** (valid square) hay không.

---

## 🎯 Phân tích bài toán

### 1. Định nghĩa hình vuông trong hình học Euclid
Một tứ giác được gọi là hình vuông khi và chỉ khi thỏa mãn đồng thời các tính chất:
1. **4 cạnh có độ dài bằng nhau và lớn hơn 0:** $s_1 = s_2 = s_3 = s_4 = s > 0$.
2. **4 góc vuông ($90^\circ$):** Tương đương với việc **2 đường chéo có độ dài bằng nhau** ($d_1 = d_2 = d$) và cắt nhau tại trung điểm của mỗi đường.
3. **Mối quan hệ Pythagoras giữa đường chéo và cạnh:**
   $$d^2 = s^2 + s^2 = 2s^2$$

### 2. Thách thức của bài toán
- **Thứ tự các điểm là ngẫu nhiên:** Đầu vào không báo trước điểm nào kề nhau, điểm nào đối diện nhau qua đường chéo.
- **Tránh sai số số thực (Floating-point precision):** 
  - Khi tính khoảng cách giữa hai điểm $(x_1, y_1)$ và $(x_2, y_2)$:
    $$\text{dist} = \sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2}$$
  - Phép khai căn `Math.sqrt()` sinh ra số thực có thể gây sai số làm tròn khi so sánh `==`.
  - **Khắc phục:** Ta chỉ làm việc với **bình phương khoảng cách** ($\text{dist}^2 = \Delta x^2 + \Delta y^2$). Tất cả đều là số nguyên tuyệt đối chính xác $100\%$.
- **Giới hạn giá trị:**
  - $-10^4 \le x_i, y_i \le 10^4 \implies \Delta x \le 2 \cdot 10^4 \implies \Delta x^2 \le 4 \cdot 10^8$.
  - Khoảng cách bình phương lớn nhất: $\Delta x^2 + \Delta y^2 \le 8 \cdot 10^8$.
  - Giá trị này nhỏ hơn `Integer.MAX_VALUE` ($\approx 2.14 \cdot 10^9$), vì vậy hoàn toàn an toàn khi dùng kiểu `int` 32-bit mà không lo tràn số.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Tính 6 khoảng cách & Sắp xếp mảng nguyên thủy

### 💡 Ý tưởng cốt lõi (Key Insight)

1. **Tổng số đoạn thẳng giữa 4 điểm:**
   - Giữa 4 điểm phân biệt, số cặp điểm (đoạn thẳng) có thể tạo thành là tổ hợp chập 2 của 4:
     $$\binom{4}{2} = \frac{4 \times 3}{2} = 6 \text{ đoạn thẳng}$$

2. **Cấu trúc khoảng cách của một hình vuông:**
   Trong một hình vuông hợp lệ, 6 đoạn thẳng này luôn phân thành đúng 2 nhóm:
   - **4 cạnh của hình vuông:** Có độ dài bằng nhau là $s$. Bình phương độ dài là $s^2$.
   - **2 đường chéo:** Nối 2 cặp đỉnh đối diện, có độ dài bằng nhau là $d = s\sqrt{2}$. Bình phương độ dài là $d^2 = 2s^2$.
   - Do $s > 0 \implies d^2 = 2s^2 > s^2$, đường chéo luôn dài hơn cạnh.

3. **Thuật toán xử lý:**
   - Tính bình phương độ dài của cả 6 cặp điểm và lưu vào mảng `int[] d` gồm 6 phần tử.
   - Sắp xếp mảng `d` tăng dần bằng `Arrays.sort(d)`.
   - Sau khi sắp xếp:
     - 4 phần tử đầu tiên ($d[0], d[1], d[2], d[3]$) bắt buộc phải là 4 cạnh.
     - 2 phần tử cuối ($d[4], d[5]$) bắt buộc phải là 2 đường chéo.
   - Kiểm tra 4 điều kiện:
     1. $d[0] > 0$: Cạnh phải có độ dài dương (ngăn các điểm trùng nhau).
     2. $d[0] == d[1] == d[2] == d[3]$: 4 cạnh bằng nhau (hình thoi).
     3. $d[4] == d[5]$: 2 đường chéo bằng nhau.
     4. $d[4] == 2 \times d[0]$: Thỏa mãn định lý Pythagoras (đảm bảo góc vuông $90^\circ$).

---

### 💻 Mã nguồn Java (Best Solution)

```java
import java.util.Arrays;

class Solution {
    /**
     * Best Solution: Tính bình phương khoảng cách giữa tất cả 6 cặp điểm,
     * sau đó sắp xếp và kiểm tra tính chất hình học của hình vuông.
     *
     * Time Complexity: O(1) - chỉ có 4 điểm, 6 khoảng cách.
     * Space Complexity: O(1) - mảng độ dài cố định 6 phần tử nguyên thủy.
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[] d = {
            dist(p1, p2),
            dist(p1, p3),
            dist(p1, p4),
            dist(p2, p3),
            dist(p2, p4),
            dist(p3, p4)
        };

        // Sắp xếp tăng dần 6 khoảng cách
        Arrays.sort(d);

        // Một hình vuông hợp lệ khi và chỉ khi:
        // 1. Độ dài cạnh phải > 0: d[0] > 0 (loại trừ trường hợp các điểm trùng nhau)
        // 2. 4 cạnh có độ dài bằng nhau: d[0] == d[1] == d[2] == d[3]
        // 3. 2 đường chéo có độ dài bằng nhau: d[4] == d[5]
        // 4. Quan hệ Pythagoras: đường chéo^2 = 2 * cạnh^2 (d[4] == 2 * d[0])
        return d[0] > 0
            && d[0] == d[1]
            && d[1] == d[2]
            && d[2] == d[3]
            && d[4] == d[5]
            && d[4] == 2 * d[0];
    }

    /**
     * Tính bình phương khoảng cách Euclid giữa 2 điểm a và b:
     * dist^2 = (a.x - b.x)^2 + (a.y - b.y)^2
     * Không dùng Math.sqrt() để tránh sai số số thực và tối ưu tốc độ.
     */
    private int dist(int[] a, int[] b) {
        int dx = a[0] - b[0];
        int dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
}
```

---

## 🔄 CÁCH TIẾP CẬN 2: Sắp xếp 4 điểm theo tọa độ (Lexicographical Sort)

### 💡 Ý tưởng
Nếu ta sắp xếp 4 điểm theo thứ tự từ điển: ưu tiên hoành độ $x$ tăng dần, nếu $x$ bằng nhau thì xét tung độ $y$ tăng dần.

Giả sử sau khi sắp xếp, 4 điểm là $p[0], p[1], p[2], p[3]$:
- **Đặc tính toán học:** Điểm nhỏ nhất $p[0]$ và điểm lớn nhất $p[3]$ **luôn luôn là hai đỉnh đối diện nhau qua đường chéo** trong bất kỳ góc quay nào của hình vuông trên mặt phẳng 2D.
- Tương tự, $p[1]$ và $p[2]$ là hai đỉnh đối diện của đường chéo còn lại.
- Bốn cạnh kề của hình vuông khi đó cố định là:
  $$(p[0], p[1]), \quad (p[0], p[2]), \quad (p[1], p[3]), \quad (p[2], p[3])$$
- Hai đường chéo là:
  $$(p[0], p[3]), \quad (p[1], p[2])$$

### 💻 Code tham khảo

```java
import java.util.Arrays;

class SolutionLexicographical {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[][] p = {p1, p2, p3, p4};

        // Sắp xếp 4 điểm theo x, rồi đến y
        Arrays.sort(p, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));

        // Kiểm tra 4 cạnh bằng nhau và > 0, cùng 2 đường chéo bằng nhau
        return dist(p[0], p[1]) > 0
            && dist(p[0], p[1]) == dist(p[0], p[2])
            && dist(p[0], p[1]) == dist(p[1], p[3])
            && dist(p[0], p[1]) == dist(p[2], p[3])
            && dist(p[0], p[3]) == dist(p[1], p[2]);
    }

    private int dist(int[] a, int[] b) {
        int dx = a[0] - b[0];
        int dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
}
```

---

## 🔄 CÁCH TIẾP CẬN 3: Sử dụng `HashSet` (Hash Set Counting)

### 💡 Ý tưởng & Cảnh báo bẫy (Gotcha)
Nhiều bạn nghĩ rằng chỉ cần đưa 6 khoảng cách vào một `HashSet<Integer>`:
- Nếu `set.size() == 2` và `!set.contains(0)` thì là hình vuông?
- **⚠️ Cẩn thận:** Chỉ `set.size() == 2` là **CHƯA ĐỦ**!
  - Ví dụ: Một hình thoi có góc $60^\circ$ và $120^\circ$ được tạo bởi 2 tam giác đều chung cạnh. Các khoảng cách giữa 4 đỉnh gồm: 5 cạnh/đoạn bằng $s$, và 1 đường chéo dài bằng $3s$. `HashSet` cũng chỉ có đúng 2 phần tử ($s^2$ và $3s^2$), nhưng đây **không phải hình vuông**!
  - Để dùng `HashSet` an toàn, bắt buộc phải đếm tần suất (hoặc kiểm tra định lý Pythagoras $d^2 = 2s^2$). Vì vậy, cách sắp xếp mảng nguyên thủy ở Cách 1 vẫn là giải pháp đơn giản, sạch sẽ và an toàn nhất.

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :--- | :--- | :--- |
| **Best: 6 Pairwise Distances + Sort** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | **Tối ưu nhất.** Code ngắn gọn, dùng mảng nguyên thủy `int[]`, không cấp phát đối tượng phụ (Zero-allocation), đạt 0ms (100% beats). |
| **Lexicographical Sort (4 points)** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | Rất hay về mặt toán học nhưng cần dùng custom Comparator / lambda để sắp xếp mảng 2 chiều. |
| **HashSet Counting** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | Tốn chi phí Autoboxing `Integer` và dễ dính bẫy nếu không kiểm tra tỷ lệ Pythagoras $d^2 = 2s^2$. |

> **Ghi chú về $\mathcal{O}(1)$:** Vì số lượng điểm luôn cố định là 4 (không phụ thuộc vào tham số $N$), số phép tính khoảng cách luôn là 6, nên thời gian chạy và bộ nhớ tiêu thụ là hằng số tuyệt đối $\mathcal{O}(1)$.

---

## ⚠️ Các trường hợp đặc biệt (Corner / Edge Cases)

1. **Các điểm trùng nhau ($p_1 = p_2 = p_3 = p_4$):**
   - Khoảng cách giữa các điểm là `0`.
   - Điều kiện `d[0] > 0` sẽ bắt trọn trường hợp này và trả về `false`.
2. **2 hoặc 3 điểm trùng nhau:**
   - Ít nhất một khoảng cách bằng `0` $\implies d[0] == 0 \implies \text{false}$.
3. **Hình chữ nhật không phải hình vuông:**
   - 4 góc vuông nhưng chiều dài $\ne$ chiều rộng $\implies d[0] \ne d[2] \implies \text{false}$.
4. **Hình thoi (Rhombus) không vuông:**
   - 4 cạnh bằng nhau nhưng 2 đường chéo khác nhau $\implies d[4] \ne d[5] \implies \text{false}$.
5. **4 điểm thẳng hàng (Collinear):**
   - Không thể tạo thành 4 cạnh bằng nhau và 2 đường chéo thỏa mãn $d^2 = 2s^2 \implies \text{false}$.
