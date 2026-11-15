# 📚 Giải thích Chi tiết Bài 686 - Repeated String Match

**Mục tiêu:** Cho hai chuỗi `a` và `b`. Hãy tìm số lần lặp lại tối thiểu của chuỗi `a` để chuỗi `b` trở thành một chuỗi con (substring) của chuỗi vừa lặp. Nếu không có cách nào, trả về `-1`.

---

## 🎯 Phân tích bài toán

### 1. Điều kiện cần về mặt độ dài
Gọi:
- $m = a.length()$ (độ dài chuỗi $a$).
- $n = b.length()$ (độ dài chuỗi $b$).

Để chuỗi lặp của $a$ (kí hiệu là $a^{(k)}$) có thể chứa được $b$ dưới dạng chuỗi con, điều kiện tiên quyết là độ dài của $a^{(k)}$ phải **lớn hơn hoặc bằng** độ dài của $b$:
$$k \cdot m \ge n \implies k \ge \left\lceil \frac{n}{m} \right\rceil$$

Như vậy, số lần lặp tối thiểu ban đầu ta phải thử là:
$$k = \left\lceil \frac{n}{m} \right\rceil$$

---

### 2. Điểm mấu chốt: Vì sao chỉ cần kiểm tra tối đa $k$ và $k + 1$ lần?
Nhiều người sẽ băn khoăn: *Liệu có cần lặp thêm $k + 2, k + 3, \dots$ lần hay không? Làm sao biết khi nào dừng lại để trả về $-1$?*

Ta có định lý toán học sau:
- Mọi chuỗi con $b$ xuất hiện trong chuỗi lặp $a^{(k)}$ đều có vị trí bắt đầu $start$ trong một bản sao nào đó của $a$.
- Do tính chất tuần hoàn chu kỳ $m$ của chuỗi $a$, nếu $b$ có thể xuất hiện thì nó **chắc chắn phải bắt đầu tại một vị trí $start \in [0, m - 1]$ ngay trong bản sao đầu tiên của $a$**.
- Trường hợp vị trí bắt đầu xa nhất có thể: $start = m - 1$ (ký tự cuối cùng của bản sao đầu tiên).
- Khi đó, các ký tự của $b$ sẽ trải dài từ chỉ số $m - 1$ đến $(m - 1) + n - 1 = m + n - 2$.
- Tổng độ dài chuỗi cần thiết tính từ chỉ số $0$ để bao trọn chỉ số $m + n - 2$ là:
  $$(m + n - 2) + 1 = m + n - 1$$
- Số bản sao $a$ cần thiết để đạt được độ dài này là:
  $$\left\lceil \frac{m + n - 1}{m} \right\rceil = 1 + \left\lceil \frac{n - 1}{m} \right\rceil$$
- Ta dễ dàng nhận thấy:
  $$\left\lceil \frac{n - 1}{m} \right\rceil \le \left\lceil \frac{n}{m} \right\rceil = k$$
  $$\implies 1 + \left\lceil \frac{n - 1}{m} \right\rceil \le k + 1$$

👉 **Kết luận cực kỳ quan trọng:**
Số lần lặp cần thiết để chứa $b$ **chỉ có thể là $k$ hoặc $k + 1$**!
Nếu sau $k + 1$ lần lặp mà chuỗi lặp vẫn không chứa $b$, thì theo tính chất tuần hoàn, việc lặp thêm $k + 2, k + 3, \dots$ lần cũng sẽ không thể tạo ra thêm bất kỳ vị trí bắt đầu mới nào trong đoạn $[0, m - 1]$. Do đó ta kết luận ngay là **`-1`**.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le a.length, b.length \le 10^4$.
- Chuỗi kết quả sau $k + 1$ lần lặp có độ dài tối đa là $m \times (k + 1) \le m \times (n/m + 2) = n + 2m \approx 3 \times 10^4$ ký tự.
- Với độ dài khoảng $3 \times 10^4$, các thao tác nối chuỗi bằng `StringBuilder` và tìm kiếm chuỗi con bằng `indexOf()` trong Java chạy cực kỳ nhanh (dưới $5\text{ ms}$).

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: `StringBuilder` + `indexOf()` ⭐ | Cách 2: Thuật toán KMP (Knuth-Morris-Pratt) |
| :--- | :--- | :--- |
| **Ý tưởng** | Ghép chuỗi đến độ dài $\ge n$, kiểm tra; nếu chưa có thì ghép thêm đúng 1 lần nữa rồi kiểm tra lại | Xây dựng mảng tiền tố LPS cho $b$, duyệt qua chuỗi tuần hoàn $a$ bằng chỉ số `i % m` |
| **Độ phức tạp Thời gian** | $\mathcal{O}((n/m + 1) \cdot m + n) \approx \mathcal{O}(m + n)$ (nhờ JVM SIMD intrinsics) | $\mathcal{O}(m + n)$ (chặt chẽ về mặt lý thuyết) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(m + n)$ (cho `StringBuilder`) | $\mathcal{O}(n)$ (cho mảng LPS của $b$) |
| **Độ phức tạp Cài đặt** | **Cực kỳ ngắn gọn, dễ nhớ (15 dòng code)** | Khá dài, dễ nhầm lẫn trong phòng phỏng vấn |
| **Khuyên dùng** | **Tối ưu nhất cho phỏng vấn thực chiến** | Thích hợp để giải thích mở rộng nâng cao |

---

## 📐 Chứng minh tính đúng đắn (Correctness Proof)

### Bổ đề:
Nếu chuỗi $b$ là chuỗi con của $a^{(C)}$ (chuỗi $a$ lặp lại $C$ lần), thì $C$ nhỏ nhất thỏa mãn phải thuộc tập $\{k, k + 1\}$, trong đó $k = \lceil n / m \rceil$.

**Chứng minh:**
1. **$C \ge k$:** Chuỗi con có độ dài $n$ không thể nằm trong một chuỗi có độ dài nhỏ hơn $n$. Vì vậy $C \cdot m \ge n \implies C \ge \lceil n / m \rceil = k$.
2. **$C \le k + 1$:** Giả sử tồn tại một lần xuất hiện của $b$ bắt đầu tại vị trí $idx \ge 0$. Do tính chất $a[(idx + x) \pmod m] = a[idx + x]$, vị trí xuất hiện đầu tiên của $b$ luôn có thể dịch về một vị trí bắt đầu $i \in [0, m - 1]$ trong bản sao đầu tiên. Khi đó điểm kết thúc là $i + n - 1 \le m - 1 + n - 1 = m + n - 2$. Số ký tự từ đầu đến điểm kết thúc là $m + n - 1$. Độ dài của $k + 1$ bản sao là:
   $$(k + 1) \cdot m = k \cdot m + m \ge n + m > m + n - 1$$
   Do đó, $k + 1$ bản sao luôn bao phủ hoàn toàn mọi khả năng xuất hiện của $b$.
3. Từ (1) và (2), nếu $b$ xuất hiện thì nó phải xuất hiện trong $k$ hoặc $k + 1$ bản sao. Nếu cả hai trường hợp đều không chứa $b$, thì $b$ không thể là chuỗi con của bất kỳ số lần lặp nào. ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): StringBuilder + Giới hạn K + 1

### 💡 Thuật toán chi tiết

1. Khởi tạo một `StringBuilder sb` rỗng và biến đếm `count = 0`.
2. Vòng lặp `while (sb.length() < b.length())`:
   - Nối thêm chuỗi `a` vào `sb`: `sb.append(a)`.
   - Tăng `count++`.
   - Sau vòng lặp này, `count` chính là $k = \lceil n / m \rceil$.
3. Kiểm tra lần 1: `if (sb.indexOf(b) != -1) return count;`
   - Nếu tìm thấy, $k$ chính là số lần lặp tối thiểu.
4. Nếu chưa tìm thấy, ta thêm đúng **1 lần** chuỗi `a` nữa:
   - `sb.append(a); count++;`
5. Kiểm tra lần 2: `if (sb.indexOf(b) != -1) return count;`
   - Nếu tìm thấy, $k + 1$ chính là số lần lặp tối thiểu.
6. Nếu sau bước 5 vẫn không thấy, chắc chắn không thể tạo thành $b$, trả về `-1`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `a = "abcd"`, `b = "cdabcdab"`
- $m = 4$, $n = 8 \implies k = \lceil 8 / 4 \rceil = 2$.
- Lặp 2 lần ($k = 2$):
  - `sb = "abcdabcd"` (độ dài 8).
  - Kiểm tra `"cdabcdab"` trong `"abcdabcd"` $\implies$ **Không có** (`indexOf == -1`).
- Thêm 1 lần nữa ($k + 1 = 3$):
  - `sb = "abcdabcdabcd"` (độ dài 12).
  - Vị trí từ chỉ số 2 đến 9: `"ab[cdabcdab]cd"`.
  - Tìm thấy tại chỉ số 2! $\implies$ **Trả về 3**.

---

#### Ví dụ 2: `a = "a"`, `b = "aa"`
- $m = 1, n = 2 \implies k = \lceil 2 / 1 \rceil = 2$.
- Lặp 2 lần: `sb = "aa"`.
- Tìm thấy ngay lập tức $\implies$ **Trả về 2**.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        // Lặp chuỗi a cho đến khi độ dài của sb ít nhất bằng độ dài của b
        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Kiểm tra xem b đã là chuỗi con của sb chưa (lặp k lần)
        if (sb.indexOf(b) != -1) {
            return count;
        }

        // Thêm 1 lần lặp nữa để bao phủ trường hợp b bắt đầu lệch ở cuối chuỗi a
        sb.append(a);
        count++;

        // Kiểm tra lại lần cuối (lặp k + 1 lần)
        if (sb.indexOf(b) != -1) {
            return count;
        }

        return -1;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`StringBuilder sb = new StringBuilder(); int count = 0;`**:
   - Sử dụng `StringBuilder` để nối chuỗi hiệu quả, tránh tạo nhiều đối tượng bất biến (`String`) gây tốn bộ nhớ Heap.
2. **`while (sb.length() < b.length()) { sb.append(a); count++; }`**:
   - Tự động lặp lại chuỗi `a` đúng số lần $k = \lceil n / m \rceil$ cần thiết để độ dài chuỗi lặp không nhỏ hơn độ dài của $b$.
3. **`if (sb.indexOf(b) != -1) return count;`**:
   - Kiểm tra nếu $b$ đã xuất hiện (xảy ra khi $b$ bắt đầu ở vị trí thuận lợi, không bị tràn sang bản sao thứ $k + 1$).
4. **`sb.append(a); count++;`**:
   - Nối thêm đúng 1 bản sao của $a$ để xử lý trường hợp xấu nhất ($b$ bắt đầu từ ký tự cuối cùng của bản sao đầu tiên).
5. **`if (sb.indexOf(b) != -1) return count;`**:
   - Kiểm tra lần cuối sau khi đã bao phủ toàn bộ mọi vị trí bắt đầu khả dĩ.
6. **`return -1;`**:
   - Nếu cả 2 lần đều không tìm thấy, toán học đảm bảo $b$ không thể xuất hiện trong bất kỳ số lần lặp nào.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N + M)$ trung bình**:
  - Quá trình ghép chuỗi mất $\mathcal{O}((k + 1) \cdot m) = \mathcal{O}(n + m)$ thời gian.
  - Hàm `indexOf()` trong Java hiện đại sử dụng các lệnh vector hóa phần cứng (SIMD intrinsics), cho tốc độ tìm kiếm chuỗi con đạt xấp xỉ tuyến tính $\mathcal{O}(n + m)$ trên dữ liệu thực tế.
  - Với $n, m \le 10^4$, tổng thời gian chạy thực tế trên LeetCode chỉ $\approx 2-3\text{ ms}$.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N + M)$**:
  - Bộ nhớ được cấp phát cho `StringBuilder` có dung lượng tối đa là $(k + 1) \cdot m \le n + 2m \le 3 \times 10^4$ ký tự, tương đương chưa đến $60\text{ KB}$.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Quy nạp biên trên (Upper Bound Invariant):**
   - Điểm sáng lớn nhất khi phỏng vấn bài này là **chứng minh được tại sao chỉ cần lặp tối đa $k + 1$ lần**.
   - Việc trả lời được: *"Vị trí bắt đầu của $b$ có thể dịch tuần hoàn về $i \in [0, m - 1]$, do đó chiều dài lớn nhất cần bao phủ là $m + n - 1$, tương ứng tối đa $k + 1$ bản sao"* sẽ gây ấn tượng mạnh với người phỏng vấn về tư duy toán học và tính chặt chẽ.
2. **Tránh bẫy vòng lặp vô tận:**
   - Nhiều ứng viên viết vòng lặp `while (true)` kiểm tra `indexOf` rồi thêm `a`, nhưng không biết khi nào nên dừng dẫn đến TLE hoặc Memory Limit Exceeded khi chuỗi không thể khớp.
3. **Mở rộng thuật toán KMP:**
   - Nếu người phỏng vấn yêu cầu độ phức tạp thời gian chặt chẽ trong trường hợp xấu nhất (Worst-case $\mathcal{O}(N + M)$), hãy giải thích cách áp dụng KMP duyệt chỉ số tuần hoàn `i % m` mà không cần cấp phát thêm bộ nhớ ghép chuỗi.
