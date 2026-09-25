# 📚 Giải thích Chi tiết Bài 605 - Can Place Flowers

**Mục tiêu:** Cho một mảng số nguyên `flowerbed` đại diện cho một luống hoa (trong đó `0` nghĩa là ô trống, `1` nghĩa là ô đã trồng hoa) và một số nguyên `n`. Hoa không được trồng ở hai ô kề nhau. Hãy xác định xem có thể trồng thêm đủ `n` bông hoa mới vào luống hoa mà không vi phạm quy tắc ô kề nhau hay không.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- `1 <= flowerbed.length <= 2 * 10⁴`: Độ dài luống hoa lên tới 20,000 ô.
- `flowerbed[i]` chỉ nhận giá trị `0` hoặc `1`.
- Dữ liệu ban đầu đảm bảo **không có hai bông hoa nào được trồng kề nhau sẵn**.
- `0 <= n <= flowerbed.length`: Số lượng hoa cần trồng thêm.

### 2. Quy tắc trồng hoa
Một ô thứ $i$ có thể trồng thêm hoa nếu và chỉ nếu thỏa mãn đồng thời 3 điều kiện:
1. Bản thân ô đó đang trống: `flowerbed[i] == 0`.
2. Ô liền trước bên trái đang trống (hoặc $i$ là vị trí đầu luống hoa): `i == 0 || flowerbed[i - 1] == 0`.
3. Ô liền sau bên phải đang trống (hoặc $i$ là vị trí cuối luống hoa): `i == length - 1 || flowerbed[i + 1] == 0`.

### 3. Bản chất Thuật toán Tham lam (Greedy Choice Property)
- **Câu hỏi cốt lõi:** Khi duyệt từ trái sang phải, nếu gặp một ô hợp lệ có thể trồng hoa ngay, ta có nên trồng luôn không? Hay nên bỏ qua để chờ ô tiếp theo?
- **Chứng minh tính tối ưu của Tham lam:**
  - Giả sử ô $i$ hợp lệ để trồng hoa.
  - Nếu ta trồng tại $i$, vị trí $i + 1$ bị khóa (không trồng được), nhưng các vị trí từ $i + 2$ trở đi vẫn có cơ hội trồng hoa.
  - Nếu ta **không** trồng tại $i$ mà dành quyền trồng cho $i + 1$, thì cả ô $i$ và $i + 2$ đều bị khóa, và cơ hội trồng tiếp theo chỉ bắt đầu từ $i + 3$.
  - Rõ ràng, việc trồng càng sớm (về phía bên trái) càng để lại nhiều khoảng trống cho phần còn lại của luống hoa.
  - $\implies$ **Chiến lược Tham lam (Greedy): Cứ thấy ô nào có thể trồng được là trồng ngay lập tức!**

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Tham lam kết hợp Tối ưu bước nhảy (Jump Optimization)

### 💡 Ý tưởng đột phá (Key Insights)

Thay vì duyệt tuần tự từng ô $i = 0, 1, 2, \dots$ và kiểm tra cả hai bên trái phải, ta có thể tối ưu hóa luồng duyệt bằng các bước nhảy thông minh:

1. **Nếu `flowerbed[i] == 1`:**
   - Ô này đã có hoa $\implies$ Ô kế tiếp $i + 1$ chắc chắn không thể trồng hoa.
   - Nhảy cóc ngay sang: `i += 2`.

2. **Nếu `flowerbed[i] == 0`:**
   - Ta chỉ cần nhìn sang ô bên phải $i + 1$ (vì nếu ô bên trái có hoa, bước trước đó đã nhảy qua ô $i$ rồi):
     - **Trường hợp A: Ô bên phải cũng là `0` (hoặc $i$ là ô cuối cùng của mảng `i == length - 1`):**
       - Ta hoàn toàn an toàn để trồng một bông hoa tại ô $i$!
       - Giảm số hoa cần trồng: `n--`.
       - Nếu `n <= 0`: **Kết thúc sớm (Early Exit)** và trả về `true` ngay lập tức!
       - Vì vừa trồng hoa tại $i$, ô tiếp theo $i + 1$ bị khóa $\implies$ Nhảy ngay sang: `i += 2`.
     - **Trường hợp B: Ô bên phải là `1` (`flowerbed[i + 1] == 1`):**
       - Ô $i$ không thể trồng hoa (vì vướng ô $i + 1$).
       - Ô $i + 1$ đã có hoa $\implies$ Ô $i + 2$ cũng bị khóa.
       - Do đó, cả $i$, $i + 1$, $i + 2$ đều không thể trồng hoa mới $\implies$ Nhảy cóc thẳng sang: `i += 3`!

### 🌟 Ưu điểm vượt trội của giải pháp
- **Không sửa đổi dữ liệu đầu vào (Zero-mutation):** Không cần gán `flowerbed[i] = 1`, giữ nguyên tính toàn vẹn của mảng (Pure function).
- **Giảm số bước lặp đáng kể:** Nhờ bước nhảy `+2` và `+3`, số vòng lặp thực tế chỉ khoảng $N / 2$ đến $N / 3$.
- **Cắt tỉa nhánh sớm (Early Termination):** Trả về `true` ngay khi `n <= 0` mà không cần duyệt hết luống hoa.
- **Thời gian chạy:** `0ms` (Beats 100% trên LeetCode).

---

### 💻 Mã nguồn Java (Best Solution)

```java
class Solution {
    /**
     * Best Solution: Thuật toán Tham lam (Greedy) kết hợp Tối ưu bước nhảy (Jump Optimization).
     * 
     * Time Complexity: O(N) - duyệt tối đa N phần tử, trung bình chỉ N/2 đến N/3.
     * Space Complexity: O(1) - không cấp phát bộ nhớ phụ, không mutate mảng đầu vào.
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        // Trường hợp đặc biệt: không cần trồng thêm hoa nào
        if (n <= 0) {
            return true;
        }

        int length = flowerbed.length;
        int i = 0;

        while (i < length) {
            if (flowerbed[i] == 1) {
                // Đã có hoa tại i -> vị trí i + 1 không được trồng -> nhảy sang i + 2
                i += 2;
            } else if (i == length - 1 || flowerbed[i + 1] == 0) {
                // flowerbed[i] == 0 và vị trí kế tiếp cũng là 0 (hoặc cuối mảng)
                // -> Trồng hoa tại i!
                n--;
                if (n <= 0) {
                    return true;
                }
                // Sau khi trồng tại i -> vị trí i + 1 bị khóa -> nhảy sang i + 2
                i += 2;
            } else {
                // flowerbed[i] == 0 nhưng flowerbed[i + 1] == 1
                // -> Vị trí i không trồng được, và hoa tại i + 1 khóa luôn i + 2 -> nhảy sang i + 3
                i += 3;
            }
        }

        return n <= 0;
    }
}
```

---

## 🔍 Cách hoạt động từng bước (Walkthrough)

### Ví dụ 1: `flowerbed = [1, 0, 0, 0, 1]`, `n = 1`

```text
Chỉ số:      0    1    2    3    4
Mảng:       [1,   0,   0,   0,   1]
```

- **Khởi tạo:** `i = 0`, `n = 1`.
- **Bước 1 (`i = 0`):**
  - `flowerbed[0] == 1` $\implies$ Nhảy `i += 2` $\implies$ `i = 2`.
- **Bước 2 (`i = 2`):**
  - `flowerbed[2] == 0`.
  - Kiểm tra ô kế tiếp `flowerbed[3] == 0` $\implies$ **Trồng hoa tại $i = 2$!**
  - Giảm `n`: `n = 1 - 1 = 0`.
  - Vì `n <= 0`, hàm **ngay lập tức trả về `true`**! (Bỏ qua việc xét các ô còn lại).

---

### Ví dụ 2: `flowerbed = [0, 0, 1, 0, 0]`, `n = 2`

```text
Chỉ số:      0    1    2    3    4
Mảng:       [0,   0,   1,   0,   0]
```

- **Khởi tạo:** `i = 0`, `n = 2`.
- **Bước 1 (`i = 0`):**
  - `flowerbed[0] == 0`, ô kế tiếp `flowerbed[1] == 0` $\implies$ **Trồng hoa tại $i = 0$**.
  - `n = 2 - 1 = 1`.
  - Nhảy `i += 2` $\implies$ `i = 2`.
- **Bước 2 (`i = 2`):**
  - `flowerbed[2] == 1` $\implies$ Nhảy `i += 2` $\implies$ `i = 4`.
- **Bước 3 (`i = 4`):**
  - `flowerbed[4] == 0`, và $i = 4$ là cuối mảng (`i == length - 1`) $\implies$ **Trồng hoa tại $i = 4$**.
  - `n = 1 - 1 = 0`.
  - Vì `n <= 0`, hàm **ngay lập tức trả về `true`**!

---

### Ví dụ 3: Bước nhảy `i += 3` với `flowerbed = [0, 1, 0, 0, 0]`, `n = 1`

```text
Chỉ số:      0    1    2    3    4
Mảng:       [0,   1,   0,   0,   0]
```

- **Khởi tạo:** `i = 0`, `n = 1`.
- **Bước 1 (`i = 0`):**
  - `flowerbed[0] == 0`, nhưng ô kế tiếp `flowerbed[1] == 1`.
  - Cả ô 0, 1, 2 đều không thể trồng $\implies$ Nhảy cóc thẳng `i += 3` $\implies$ `i = 3`.
- **Bước 2 (`i = 3`):**
  - `flowerbed[3] == 0`, ô kế tiếp `flowerbed[4] == 0` $\implies$ **Trồng hoa tại $i = 3$**.
  - `n = 1 - 1 = 0` $\implies$ Trả về `true`!

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (Alternative Approaches)

### Cách 1: Duyệt tuần tự & Kiểm tra 3 điểm (Trái - Giữa - Phải)

Đây là cách trực quan và phổ biến nhất:
- Duyệt qua từng chỉ số từ `0` đến `length - 1`.
- Tại mỗi vị trí $i$, nếu `flowerbed[i] == 0`, kiểm tra:
  - Bên trái trống: `prev = (i == 0 || flowerbed[i - 1] == 0)`
  - Bên phải trống: `next = (i == length - 1 || flowerbed[i + 1] == 0)`
  - Nếu cả hai đều đúng: Đặt `flowerbed[i] = 1`, giảm `n--`.

```java
class SolutionStandardGreedy {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int length = flowerbed.length;
        for (int i = 0; i < length; i++) {
            if (flowerbed[i] == 0) {
                boolean prevEmpty = (i == 0 || flowerbed[i - 1] == 0);
                boolean nextEmpty = (i == length - 1 || flowerbed[i + 1] == 0);

                if (prevEmpty && nextEmpty) {
                    flowerbed[i] = 1; // Sửa đổi mảng
                    n--;
                    if (n <= 0) {
                        return true;
                    }
                }
            }
        }
        return n <= 0;
    }
}
```

- **Nhược điểm so với Best Solution:**
  - Phải duyệt qua tất cả các phần tử (không có bước nhảy).
  - Phải sửa đổi mảng đầu vào (`flowerbed[i] = 1`).

---

### Cách 2: Công thức Toán học theo số lượng số `0` liên tiếp

Ta có thể đếm số lượng số `0` liên tục giữa các bông hoa:
- **Ở giữa 2 bông hoa:** Nếu có $k$ số `0` liên tiếp kẹp giữa hai số `1`, số hoa tối đa có thể trồng là:
  $$\lfloor \frac{k - 1}{2} \rfloor$$
  *(Ví dụ: `1 0 0 0 1` có $k = 3 \implies (3 - 1) / 2 = 1$ bông)*
- **Ở đầu mảng (trước bông hoa đầu tiên):** Nếu có $k$ số `0` ở đầu, số hoa trồng được là:
  $$\lfloor \frac{k}{2} \rfloor$$
  *(Ví dụ: `0 0 1` có $k = 2 \implies 2 / 2 = 1$ bông)*
- **Ở cuối mảng (sau bông hoa cuối cùng):** Nếu có $k$ số `0` ở cuối, số hoa trồng được là:
  $$\lfloor \frac{k}{2} \rfloor$$
- **Trường hợp mảng toàn số `0`:** Với $k$ số `0`, số hoa trồng được là:
  $$\lfloor \frac{k + 1}{2} \rfloor$$

Cách này đòi hỏi phải phân tách nhiều trường hợp biên (đầu mảng, cuối mảng, mảng không có số 1) nên code dễ bị lỗi sót trường hợp hơn so với thuật toán Tham lam trực tiếp.

---

## ⚠️ Các trường hợp biên quan trọng (Edge Cases)

| Trường hợp | Ví dụ | Xử lý |
|:---|:---|:---|
| **$n = 0$** | `flowerbed = [1, 0, 1]`, `n = 0` | Không cần trồng thêm hoa nào $\implies$ Trả về `true` ngay ở dòng đầu tiên. |
| **Mảng chỉ có 1 phần tử `[0]`** | `flowerbed = [0]`, `n = 1` | Ô 0 vừa là đầu vừa là cuối mảng $\implies$ Thỏa mãn điều kiện trồng hoa $\implies$ Trả về `true`. |
| **Mảng chỉ có 1 phần tử `[1]`** | `flowerbed = [1]`, `n = 1` | Đã có hoa $\implies$ Không trồng được $\implies$ Trả về `false`. |
| **Trồng ở hai đầu luống hoa** | `flowerbed = [0, 0, 1, 0, 0]`, `n = 2` | Hai đầu biên không có ô kế cận ngoài mảng $\implies$ Điều kiện biên `i == 0` và `i == length - 1` hoạt động trơn tru. |
| **Mảng toàn số `0`** | `flowerbed = [0, 0, 0]`, `n = 2` | Trồng tại $i = 0$ và $i = 2$ $\implies$ Trả về `true`. |

---

## 📊 Bảng so sánh các phương pháp & Đánh giá độ phức tạp

| Phương pháp | Time Complexity | Space Complexity | Sửa đổi mảng? | Đánh giá |
|:---|:---:|:---:|:---:|:---|
| **Best: Tham lam + Nhảy cóc (Jump Optimization)** | $\mathcal{O}(N)$ (Duyệt $\sim \frac{N}{2}$) | $\mathcal{O}(1)$ | ❌ Không | **Tối ưu nhất:** 0ms, không side-effect, code gọn gàng, có Early Exit. |
| **Duyệt tuần tự kiểm tra 3 điểm** | $\mathcal{O}(N)$ (Duyệt toàn bộ $N$) | $\mathcal{O}(1)$ | ✔️ Có (`arr[i]=1`) | Dễ hiểu nhưng phải duyệt từng ô và sửa đổi dữ liệu gốc. |
| **Đếm số lượng số `0` liên tiếp (Toán học)** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | ❌ Không | Khá phức tạp khi xử lý các đoạn `0` ở đầu và cuối luống hoa. |
