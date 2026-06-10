# 402. Remove K Digits - Giải thích thuật toán

Bài toán yêu cầu loại bỏ đúng `k` chữ số từ chuỗi số `num` sao cho số nhận được là nhỏ nhất có thể.

Với độ dài của chuỗi `num` lên tới $10^5$, thuật toán duyệt qua tất cả các tổ hợp (backtracking) sẽ bị quá thời gian (Time Limit Exceeded). Do đó, chúng ta cần tìm một thuật toán tối ưu hơn bằng phương pháp **Tham lam (Greedy)** kết hợp với **Ngăn xếp đơn điệu (Monotonic Stack)** để đạt độ phức tạp $O(N)$.

---

## Ý tưởng cốt lõi

Khi so sánh hai số có cùng số lượng chữ số, giá trị của số nào nhỏ hơn phụ thuộc hoàn toàn vào chữ số khác biệt đầu tiên từ bên trái sang. 
Ví dụ: So sánh `143...` và `123...`, chữ số khác biệt đầu tiên là `4` và `2`. Vì `2 < 4` nên số bắt đầu bằng `12...` chắc chắn sẽ nhỏ hơn số bắt đầu bằng `14...`.

Vì vậy, nguyên tắc tham lam ở đây là: **Cố gắng giữ cho các chữ số bên trái càng nhỏ càng tốt.** 

Cụ thể, khi duyệt qua chuỗi số từ trái qua phải, nếu ta thấy chữ số sau nhỏ hơn chữ số trước, ta nên xóa chữ số trước đi (nếu vẫn còn lượt xóa `k`).

---

## Giải pháp sử dụng Monotonic Stack

Ta có thể mô phỏng quá trình này một cách hiệu quả bằng cách sử dụng một ngăn xếp (stack):

1. **Duyệt qua từng chữ số** của `num`:
   - Chừng nào ngăn xếp không rỗng, chữ số ở đỉnh ngăn xếp **lớn hơn** chữ số hiện tại, và ta vẫn còn lượt xóa (`k > 0`):
     - Ta loại bỏ chữ số ở đỉnh ngăn xếp (pop).
     - Giảm số lượt xóa đi 1 (`k--`).
   - Đẩy chữ số hiện tại vào ngăn xếp (push).
2. **Xử lý lượt xóa còn dư**:
   - Sau khi duyệt hết chuỗi, nếu vẫn còn lượt xóa (`k > 0`), ta sẽ loại bỏ các chữ số ở đỉnh ngăn xếp (vì lúc này ngăn xếp đã ở trạng thái tăng dần, các chữ số lớn nhất nằm ở trên cùng).
3. **Xử lý số không ở đầu (leading zeros) & Trả về kết quả**:
   - Lấy các chữ số từ đáy ngăn xếp lên để tạo thành kết quả.
   - Bỏ qua các chữ số `0` ở đầu.
   - Nếu kết quả rỗng (tất cả các số bị xóa hoặc chỉ còn lại số 0), ta trả về `"0"`.

### Tối ưu hóa hiệu năng bằng mảng `char[]`
Thay vì sử dụng `Stack<Character>` hay `Deque<Character>` có chi phí tạo đối tượng và boxing/unboxing lớn, ta sử dụng một mảng ký tự `char[] stack` và một biến con trỏ `top` để mô phỏng ngăn xếp. Điều này giúp tối ưu hóa tối đa thời gian chạy và bộ nhớ.

---

## Ví dụ minh họa với `num = "1432219"`, `k = 3`

Quá trình duyệt chuỗi:

| Ký tự duyệt | Stack hiện tại | `k` còn lại | Hành động |
| :--- | :--- | :--- | :--- |
| Bắt đầu | `[]` | 3 | |
| `'1'` | `['1']` | 3 | Đẩy `'1'` vào stack |
| `'4'` | `['1', '4']` | 3 | Vì `4 > 1`, đẩy `'4'` vào stack |
| `'3'` | `['1', '3']` | 2 | Vì `3 < 4` và `k > 0`: pop `'4'`, giảm `k` còn 2, đẩy `'3'` |
| `'2'` | `['1', '2']` | 1 | Vì `2 < 3` và `k > 0`: pop `'3'`, giảm `k` còn 1, đẩy `'2'` |
| `'2'` | `['1', '2', '2']` | 1 | Vì `2 == 2`, đẩy `'2'` vào stack |
| `'1'` | `['1', '2', '1']` | 0 | Vì `1 < 2` và `k > 0`: pop `'2'`, giảm `k` còn 0, đẩy `'1'` |
| `'9'` | `['1', '2', '1', '9']` | 0 | Vì `k = 0`, đẩy thẳng `'9'` vào stack |

Sau khi kết thúc, stack chứa `['1', '2', '1', '9']`.
Kết quả trả về: `"1219"`.

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(N)$
  Với $N$ là độ dài của chuỗi `num`. Mỗi chữ số được đẩy vào và lấy ra khỏi ngăn xếp tối đa 1 lần. Do đó, tổng số thao tác trên ngăn xếp là tuyến tính.
- **Độ phức tạp không gian (Space Complexity):** $O(N)$
  Ta sử dụng một mảng `char[]` có kích thước bằng $N$ để mô phỏng ngăn xếp.
