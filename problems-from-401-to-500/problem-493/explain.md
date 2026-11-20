# 📚 Giải thích Chi tiết Bài 693 - Binary Number with Alternating Bits

**Mục tiêu:** Cho một số nguyên dương $n$. Hãy kiểm tra xem biểu diễn nhị phân của $n$ có các bit liền kề xen kẽ nhau hay không (nghĩa là không có hai bit kề nhau nào có cùng giá trị, ví dụ `101`, `1010`).

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm của số có các bit xen kẽ
- Một số có các bit xen kẽ sẽ có dạng nhị phân:
  $$n = (1010\dots)_2 \quad \text{hoặc} \quad n = (10101\dots)_2$$
- Giữa hai bit bất kỳ đứng cạnh nhau:
  $$\forall i: \quad \text{bit}_i \ne \text{bit}_{i-1}$$

---

### 2. Phép biến đổi kỳ diệu với toán tử XOR ($\oplus$)
Xét phép toán:
$$x = n \oplus (n \gg 1)$$
- Khi ta dịch phải $n$ đi 1 bit ($n \gg 1$), mỗi bit tại vị trí $i$ sẽ được xếp thẳng hàng với bit lân cận của nó tại vị trí $i - 1$ trong số $n$ ban đầu.
- Khi thực hiện phép XOR giữa hai bit cạnh nhau:
  - Nếu hai bit khác nhau ($0 \oplus 1$ hoặc $1 \oplus 0$): kết quả là **$1$**.
  - Nếu hai bit giống nhau ($0 \oplus 0$ hoặc $1 \oplus 1$): kết quả là **$0$**.
- Do đó:
  - Nếu $n$ có **các bit xen kẽ nhau**, thì mọi cặp bit liền kề đều khác nhau $\implies$ toàn bộ các bit của $x$ từ bit cao nhất (MSB) tới bit thấp nhất (LSB) đều bằng **$1$**!
  - Ví dụ với $n = 5 = (101)_2$:
    $$n \gg 1 = (010)_2$$
    $$x = n \oplus (n \gg 1) = (101)_2 \oplus (010)_2 = (111)_2$$
  - Ngược lại, nếu trong $n$ tồn tại ít nhất một cặp bit kề nhau giống nhau, thì tại vị trí đó phép XOR sẽ cho ra bit **$0$**, khiến $x$ không thể là dãy toàn bit 1.

---

### 3. Cách kiểm tra một số có toàn bit 1 trong $\mathcal{O}(1)$
Làm thế nào để kiểm tra một số $x$ có dạng toàn bit 1 ($x = 2^k - 1$, ví dụ $7 = 111_2$, $15 = 1111_2$)?
- Khi cộng $1$ vào số có toàn bit 1:
  $$x + 1 = (111\dots1)_2 + 1 = (1000\dots0)_2 = 2^k$$
- Khi lấy phép AND bit giữa $x$ và $x + 1$:
  $$(111\dots1)_2 \ \& \ (1000\dots0)_2 = 0$$
- Vì vậy:
  $$(x \ \& \ (x + 1)) == 0 \iff x \text{ gồm toàn bit 1 liên tiếp từ LSB}$$

---

### 4. Xử lý tràn số (Overflow Prevention)
- Ràng buộc: $1 \le n \le 2^{31} - 1$.
- Với số xen kẽ lớn nhất trong phạm vi số nguyên 32-bit:
  $$n = 0x55555555 = (01010101\dots0101)_2$$
- Khi đó:
  $$x = n \oplus (n \gg 1) = 0x7FFFFFFF = 2^{31} - 1$$
- Nếu dùng kiểu `int` 32-bit có dấu, phép cộng $x + 1$ sẽ bị tràn số sang giá trị âm (`Integer.MIN_VALUE = -2147483648`).
- Để thuật toán an toàn tuyệt đối, tránh undefined behavior do tràn số nguyên có dấu, ta ép kiểu sang `long`:
  ```java
  long x = (long) n ^ (n >> 1);
  return (x & (x + 1)) == 0;
  ```

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Vòng lặp chia đôi (`while`) | Cách 2: Phép toán Bit $\mathcal{O}(1)$ (XOR Trick ⭐) |
| :--- | :--- | :--- |
| **Ý tưởng** | Lấy bit cuối `n & 1`, so sánh với bit trước đó, rồi dịch phải `n >>= 1` | Dùng $x = n \oplus (n \gg 1)$ rồi kiểm tra $(x \ \& \ (x + 1)) == 0$ |
| **Độ phức tạp Thời gian** | $\mathcal{O}(\log n)$ (tối đa 31 vòng lặp) | $\mathcal{O}(1)$ (chỉ 3 phép tính bit tức thì) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Đánh giá** | Dễ nghĩ, trực quan | **Cực kỳ thanh lịch, đỉnh cao của tư duy Bit Manipulation** |

---

## 📐 Chứng minh tính đúng đắn (Mathematical Proof)

### Định lý
Một số nguyên dương $n$ có các bit liền kề luôn khác nhau khi và chỉ khi $x = n \oplus (n \gg 1)$ có dạng $2^k - 1$.

**Chứng minh:**
1. Giả sử biểu diễn nhị phân của $n$ có độ dài $k$ bit ($b_{k-1} b_{k-2} \dots b_1 b_0$ với $b_{k-1} = 1$).
2. Số $n \gg 1$ có biểu diễn nhị phân là $0 b_{k-1} b_{k-2} \dots b_1$.
3. Bit thứ $i$ ($0 \le i \le k - 2$) của $x = n \oplus (n \gg 1)$ là $b_i \oplus b_{i+1}$.
   Bit thứ $k - 1$ của $x$ là $b_{k-1} \oplus 0 = 1 \oplus 0 = 1$.
4. $n$ có các bit xen kẽ $\iff \forall i \in [0, k - 2]: b_i \ne b_{i+1} \iff b_i \oplus b_{i+1} = 1$.
5. Điều này tương đương với việc tất cả các bit từ $0$ đến $k - 1$ của $x$ đều bằng $1$, tức $x = \sum_{i=0}^{k-1} 2^i = 2^k - 1$.
6. Khi $x = 2^k - 1$, $x + 1 = 2^k$. Vì $2^k - 1$ và $2^k$ không có bất kỳ bit 1 nào chung vị trí, nên $x \ \& \ (x + 1) = 0$.
7. Ngược lại, nếu tồn tại bất kỳ $j$ nào sao cho $b_j = b_{j+1}$, thì bit thứ $j$ của $x$ bằng $0$, làm đứt gãy dãy bit 1 liên tục, khi đó phép tính $x \ \& \ (x + 1)$ chắc chắn khác 0. ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Phép Toán Bit O(1)

### 💡 Thuật toán chi tiết
1. Tính `long x = (long) n ^ (n >> 1);`.
2. Kiểm tra điều kiện `(x & (x + 1)) == 0`.
3. Trả về kết quả `true` hoặc `false`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `n = 5` (Nhị phân: `101`)
```text
  n      =  1 0 1  (5)
  n >> 1 =  0 1 0  (2)
-----------------
  x      =  1 1 1  (7 = 2³ - 1, toàn bit 1!)

  x + 1  =  1 0 0 0 (8)
  x & (x + 1) = (0111) & (1000) = 0000 == 0 -> TRUE ✅
```

#### Ví dụ 2: `n = 7` (Nhị phân: `111`)
```text
  n      =  1 1 1  (7)
  n >> 1 =  0 1 1  (3)
-----------------
  x      =  1 0 0  (4, xuất hiện bit 0 ở giữa!)

  x + 1  =  1 0 1  (5)
  x & (x + 1) = (100) & (101) = 100 != 0 -> FALSE ❌
```

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Phép toán Bit $\mathcal{O}(1)$ ⭐ (Khuyên dùng)

```java
class Solution {
    public boolean hasAlternatingBits(int n) {
        // Dịch phải 1 bit và XOR với chính nó:
        // Nếu các bit xen kẽ nhau (10101...), kết quả x sẽ là dãy toàn bit 1 (11111...).
        long x = (long) n ^ (n >> 1);

        // Kiểm tra xem x có dạng toàn bit 1 (2^k - 1) hay không:
        // Nếu x toàn bit 1 thì x + 1 sẽ có dạng 1000... (lũy thừa của 2),
        // khi đó x & (x + 1) sẽ luôn bằng 0.
        return (x & (x + 1)) == 0;
    }
}
```

---

### Cách 2 (Tham khảo): Vòng lặp kiểm tra từng bit $\mathcal{O}(\log n)$

```java
class SolutionIterative {
    public boolean hasAlternatingBits(int n) {
        int prev = n & 1; // Lấy bit cuối cùng
        n >>= 1;

        while (n > 0) {
            int curr = n & 1;
            if (curr == prev) {
                return false; // Phát hiện 2 bit liền kề giống nhau
            }
            prev = curr;
            n >>= 1;
        }

        return true;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`long x = (long) n ^ (n >> 1);`**:
   - Ép kiểu `n` sang `long` để đảm bảo khi $x = 2^{31} - 1$, phép toán $x + 1 = 2^{31}$ được tính toán chính xác trong miền 64-bit mà không bị tràn số bù 2 sang số âm.
   - Phép XOR lấy hiệu số nhị phân giữa các cặp bit kề nhau: biến mọi cặp khác nhau thành $1$, và biến cặp giống nhau thành $0$.
2. **`return (x & (x + 1)) == 0;`**:
   - Biểu thức kinh điển kiểm tra số toàn bit $1$: số có toàn bit 1 khi cộng thêm 1 sẽ bật bit kế tiếp và xóa sạch tất cả các bit 1 phía sau thành 0. Phép AND hai số này cho kết quả triệt tiêu hoàn toàn về 0.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(1)$**:
  - Chỉ thực hiện đúng 4 phép toán bit cơ bản cấp độ phần cứng máy tính: dịch bit (`>>`), XOR (`^`), cộng (`+`), và AND (`&`).
  - Thời gian thực thi là tuyệt đối $\mathcal{O}(1)$, chạy trong $0\text{ ms}$ trên LeetCode (đánh bại $100\%$ các bài nộp).

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(1)$**:
  - Chỉ dùng một biến cục bộ `x` kiểu `long`, không tiêu tốn thêm bất kỳ bộ nhớ nào.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Bộ sưu tập các thủ thuật Bit Manipulation kinh điển:**
   - Xóa bit 1 thấp nhất: `n & (n - 1)`.
   - Lấy bit 1 thấp nhất (Lowbit): `n & (-n)`.
   - Kiểm tra lũy thừa của 2 ($2^k$): `n > 0 && (n & (n - 1)) == 0`.
   - Kiểm tra số toàn bit 1 ($2^k - 1$): `n > 0 && (n & (n + 1)) == 0`.
2. **Kỹ năng gây ấn tượng khi phỏng vấn:**
   - Trình bày cách tiếp cận vòng lặp trước để chứng minh hiểu rõ yêu cầu bài toán.
   - Sau đó đề xuất: *"Chúng ta có thể tối ưu bài này về $\mathcal{O}(1)$ thời gian mà không cần bất kỳ vòng lặp nào bằng kỹ thuật XOR với bản sao dịch phải 1 bit."*
   - Giải thích cặn kẽ tại sao cần ép kiểu `long` để phòng ngừa tràn số signed 32-bit `Integer.MAX_VALUE`. Điều này thể hiện sự cẩn trọng và kiến thức nền tảng vững vàng về kiểu dữ liệu trong Java.
