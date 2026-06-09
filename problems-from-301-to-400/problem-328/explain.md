# 400. Nth Digit - Giải thích thuật toán

Bài toán yêu cầu tìm chữ số thứ `n` trong chuỗi số nguyên vô hạn được tạo ra bằng cách viết liên tiếp các số tự nhiên bắt đầu từ 1: `1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...`

Với giới hạn $n \le 2^{31} - 1$, việc sinh ra toàn bộ chuỗi số này là bất khả thi vì sẽ bị tràn bộ nhớ và quá thời gian chạy. Chúng ta cần sử dụng phương pháp **phân nhóm toán học** để tìm ra kết quả trong thời gian $O(\log_{10} n)$.

---

## Ý tưởng cốt lõi

Chúng ta chia dãy số thành các nhóm dựa trên số lượng chữ số của chúng:
- **Nhóm 1:** Các số có 1 chữ số (từ $1$ đến $9$). Có $9$ số, tương ứng với $9 \times 1 = 9$ chữ số.
- **Nhóm 2:** Các số có 2 chữ số (từ $10$ đến $99$). Có $90$ số, tương ứng với $90 \times 2 = 180$ chữ số.
- **Nhóm 3:** Các số có 3 chữ số (từ $100$ đến $999$). Có $900$ số, tương ứng với $900 \times 3 = 2700$ chữ số.
- **Tổng quát:** Nhóm các số có `len` chữ số sẽ bắt đầu từ $10^{len-1}$. Có $9 \times 10^{len-1}$ số, tương ứng với `len` $\times 9 \times 10^{len-1}$ chữ số.

Thuật toán gồm 3 bước chính:

### Bước 1: Xác định độ dài chữ số (`len`) của số chứa chữ số thứ `n`
Bắt đầu từ `len = 1`, trừ dần số lượng chữ số của từng nhóm khỏi `n` cho đến khi `n` nhỏ hơn hoặc bằng số lượng chữ số của nhóm hiện tại.
- Đồng thời, ta cập nhật biến `start` đại diện cho số nhỏ nhất của nhóm tiếp theo (ví dụ: $1 \rightarrow 10 \rightarrow 100 \rightarrow \dots$).
- Cần chú ý dùng kiểu dữ liệu `long` cho `count` và `start` để tránh tràn số (integer overflow) khi tính số lượng chữ số của nhóm lớn.

### Bước 2: Xác định số cụ thể (`num`) chứa chữ số thứ `n`
Sau khi thoát vòng lặp ở Bước 1, ta biết chữ số cần tìm nằm trong một số có độ dài là `len`, và vị trí tương đối của nó trong nhóm này là `n` (đã được cập nhật).
Số chứa chữ số đó được tính bằng công thức:
$$\text{num} = \text{start} + \frac{n - 1}{\text{len}}$$
*(sử dụng phép chia nguyên để dịch chuyển từ số bắt đầu `start`)*

### Bước 3: Tìm chữ số cụ thể trong số `num`
Vị trí của chữ số cần tìm tính từ bên trái của số `num` (chỉ số bắt đầu từ 0) là:
$$\text{index} = (n - 1) \bmod \text{len}$$
Ta chuyển số `num` thành chuỗi ký tự và lấy ký tự tại vị trí `index` vừa tính được, sau đó chuyển ngược lại thành chữ số.

---

## Ví dụ minh họa với `n = 11`

1. **Bước 1:** 
   - Ban đầu `n = 11`, `len = 1`, `start = 1`, `count = 9`.
   - Vì `n > count` ($11 > 9$), ta cập nhật:
     - `n = 11 - 9 = 2`
     - `len = 2`
     - `start = 10`
     - `count = 2 * 9 * 10 = 180`
   - Lúc này `n = 2 <= count` ($2 \le 180$), vòng lặp dừng lại.

2. **Bước 2:**
   - Số chứa chữ số cần tìm là:
     $$\text{num} = 10 + \frac{2 - 1}{2} = 10 + 0 = 10$$

3. **Bước 3:**
   - Chỉ số của chữ số cần tìm trong số `10` là:
     $$\text{index} = (2 - 1) \bmod 2 = 1$$
   - Ký tự tại vị trí 1 trong chuỗi `"10"` là `'0'`, tương ứng với chữ số `0`.
   - Kết quả trả về là `0`.

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(\log_{10} n)$
  Số lần lặp của vòng lặp tìm `len` tối đa chỉ bằng số lượng chữ số của $n$ (đối với $n \le 2^{31} - 1$, tối đa là 10 vòng lặp). Do đó thuật toán chạy cực kỳ nhanh và tối ưu (gần như $O(1)$).
- **Độ phức tạp không gian (Space Complexity):** $O(\log_{10} n)$
  Để lấy chữ số cụ thể, ta chuyển đổi số `num` thành chuỗi có độ dài tối đa là 10 ký tự. Vì thế bộ nhớ sử dụng là không đáng kể ($O(1)$).
