# Giải Thích - 319. Bulb Switcher

## Phân Tích Bài Toán

Có `n` bóng đèn, ban đầu đều tắt. Quá trình bật/tắt diễn ra qua `n` vòng:
- Vòng 1: Bật tất cả các bóng (toggles: 1, 2, 3, 4, ...).
- Vòng 2: Thay đổi trạng thái mỗi 2 bóng (toggles: 2, 4, 6, 8, ...).
- Vòng 3: Thay đổi trạng thái mỗi 3 bóng (toggles: 3, 6, 9, 12, ...).
- ...
- Vòng $i$: Thay đổi trạng thái của bóng thứ $i, 2i, 3i, \dots$.
- Vòng $n$: Thay đổi trạng thái của bóng thứ $n$.

Mục tiêu là tìm số bóng đèn còn **SÁNG** sau $n$ vòng.

---

## Quan Sát Quan Trọng

1.  **Trạng thái của một bóng đèn:** Một bóng đèn thứ $k$ sẽ thay đổi trạng thái ở vòng $d$ nếu và chỉ nếu $d$ là một **ước số** (divisor) của $k$.
2.  **Bóng đèn SÁNG khi nào?** 
    - Một bóng đèn ban đầu TẮT.
    - Nó sẽ SÁNG nếu nó được nhấn (toggle) một số **lẻ** lần.
    - Số lần nhấn tương đương với **số lượng ước số** của số thứ tự bóng đèn đó.

### Ví dụ:
- Bóng đèn số **6**: Các ước số là $\{1, 2, 3, 6\}$ (4 ước số - số chẵn).
    - Vòng 1: Tắt -> Bật
    - Vòng 2: Bật -> Tắt
    - Vòng 3: Tắt -> Bật
    - Vòng 6: Bật -> Tắt
    - **Kết quả:** TẮT.
- Bóng đèn số **9**: Các ước số là $\{1, 3, 9\}$ (3 ước số - số lẻ).
    - Vòng 1: Tắt -> Bật
    - Vòng 3: Bật -> Tắt
    - Vòng 9: Tắt -> Bật
    - **Kết quả:** SÁNG.

---

## Tại Sao Số Lẻ Các Ước Số Chỉ Xuất Hiện Ở Số Chính Phương?

Thông thường, các ước số luôn đi theo cặp. Ví dụ với số 12: $(1, 12), (2, 6), (3, 4)$. Vì đi theo cặp nên tổng số ước thường là số chẵn.

Tuy nhiên, đối với **Số Chính Phương** (Perfect Squares) như $k = x^2$, sẽ có một cặp ước trùng nhau là $(x, x)$. 
- Ví dụ số 16: $(1, 16), (2, 8), (4, 4)$. 
- Các ước độc nhất là $\{1, 2, 4, 8, 16\}$ -> Tổng cộng có 5 ước (số lẻ).

**Kết luận:** Chỉ những bóng đèn có số thứ tự là **số chính phương** mới còn sáng.

---

## Cách Tính Kết Quả

Bài toán yêu cầu tìm số lượng số chính phương trong khoảng từ $1$ đến $n$.

Các số chính phương là: $1^2, 2^2, 3^2, \dots, x^2$ sao cho $x^2 \le n$.
Giá trị lớn nhất của $x$ thỏa mãn điều kiện này chính là:
$$x = \lfloor \sqrt{n} \rfloor$$

Vì vậy, kết quả chỉ đơn giản là phần nguyên của căn bậc hai của $n$.

---

## Phân Tích Độ Phức Tạp

- **Độ phức tạp thời gian:** $O(1)$ hoặc $O(\log n)$ tùy thuộc vào cách triển khai hàm `sqrt`.
- **Độ phức tạp không gian:** $O(1)$.

## Hiện Thực Mã (Java)

```java
class Solution {
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }
}
```
