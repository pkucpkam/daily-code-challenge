# 406. Queue Reconstruction by Height - Giải thích thuật toán

Bài toán yêu cầu dựng lại hàng đợi từ danh sách thông tin của từng người `[h, k]`, trong đó:
- `h`: Chiều cao của người đó.
- `k`: Số lượng người đứng trước người đó có chiều cao lớn hơn hoặc bằng `h`.

Với số lượng người $N \le 2000$, chúng ta có thể sử dụng phương pháp **Tham lam (Greedy)** kết hợp với **Sắp xếp (Sorting)** để giải quyết bài toán một cách tối ưu và dễ hiểu với độ phức tạp $O(N^2)$.

---

## Ý tưởng cốt lõi

Quy tắc cơ bản của phương pháp tham lam ở đây là: **Xếp những người cao hơn trước, người thấp hơn sau.**

Tại sao ý tưởng này lại đúng?
1. Khi chúng ta xếp những người có chiều cao lớn hơn trước, những vị trí tiếp theo dành cho người thấp hơn sẽ **không ảnh hưởng** đến điều kiện `k` của những người cao hơn đã đứng trước (vì người thấp hơn chèn vào thì không được tính vào số lượng người cao hơn hoặc bằng).
2. Khi xếp một người có chiều cao $h$ hiện tại, tất cả những người đã nằm trong hàng đợi đều có chiều cao lớn hơn hoặc bằng $h$. Do đó, để thỏa mãn điều kiện có đúng $k$ người cao hơn hoặc bằng đứng trước, chúng ta chỉ cần **chèn trực tiếp người này vào vị trí chỉ số (index) đúng bằng $k$** trong hàng đợi.

### Quy trình sắp xếp:
- **Chiều cao (`h`) giảm dần:** Để đảm bảo khi xét đến ai, mọi người đứng trước họ trong hàng đợi đều cao hơn hoặc bằng họ.
- **Số người đứng trước (`k`) tăng dần (nếu cùng chiều cao):** Nếu hai người có cùng chiều cao $h$, người có $k$ nhỏ hơn phải đứng trước để làm tăng số lượng người cao hơn hoặc bằng cho người có $k$ lớn hơn đứng sau.

---

## Các bước thuật toán

1. **Sắp xếp** mảng `people`:
   - So sánh theo chiều cao `h` giảm dần (`b[0] - a[0]`).
   - Nếu `h` bằng nhau, so sánh theo `k` tăng dần (`a[1] - b[1]`).
2. **Khởi tạo** một danh sách liên kết hoặc mảng động (như `ArrayList` trong Java) để chứa kết quả.
3. **Duyệt qua từng người** sau khi đã sắp xếp:
   - Chèn người đó vào danh sách tại vị trí `people[i][1]` (tức là chỉ số `k` của họ).
4. **Chuyển đổi** danh sách kết quả về dạng mảng 2 chiều và trả về.

---

## Ví dụ minh họa với `people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]`

### Bước 1: Sắp xếp
Sau khi sắp xếp theo luật trên:
`people = [[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]]`

### Bước 2: Chèn vào danh sách từng người một
- **Chèn `[7,0]`** vào vị trí `0`:
  `List = [[7,0]]`
- **Chèn `[7,1]`** vào vị trí `1`:
  `List = [[7,0], [7,1]]`
- **Chèn `[6,1]`** vào vị trí `1`:
  `List = [[7,0], [6,1], [7,1]]` *(đẩy `[7,1]` ra sau)*
- **Chèn `[5,0]`** vào vị trí `0`:
  `List = [[5,0], [7,0], [6,1], [7,1]]`
- **Chèn `[5,2]`** vào vị trí `2`:
  `List = [[5,0], [7,0], [5,2], [6,1], [7,1]]`
- **Chèn `[4,4]`** vào vị trí `4`:
  `List = [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]`

Kết quả cuối cùng thu được khớp hoàn toàn với ví dụ mẫu!

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(N^2)$
  - Sắp xếp mảng mất $O(N \log N)$.
  - Vòng lặp duyệt qua $N$ người, mỗi lần thực hiện chèn phần tử vào `ArrayList`. Thao tác chèn tại một chỉ số bất kỳ trong `ArrayList` mất trung bình $O(N)$ do phải dịch chuyển các phần tử phía sau.
  - Tổng thời gian là $O(N \log N + N^2) = O(N^2)$. Với $N \le 2000$, số lượng phép tính tối đa khoảng $4 \times 10^6$, chương trình chạy cực kỳ nhanh (dưới 10ms).
- **Độ phức tạp không gian (Space Complexity):** $O(N)$
  - Cần sử dụng một danh sách phụ để chứa hàng đợi trung gian có kích thước $N$.

