# 📚 Giải thích Chi tiết Bài 682 - Baseball Game

**Mục tiêu:** Mô phỏng bảng ghi điểm của một trận bóng chày với các quy tắc đặc biệt và tính tổng tất cả các điểm số hợp lệ còn lại sau khi hoàn thành danh sách thao tác.

---

## 🎯 Phân tích bài toán

### 1. Ý nghĩa các thao tác (Operations)
Bắt đầu với một danh sách điểm trống (`record = []`), ta duyệt qua từng thao tác:
- **Số nguyên `x`**: Ghi nhận một điểm số mới là `x` $\implies$ thêm `x` vào cuối danh sách (`push`).
- **`'+'`**: Điểm mới bằng tổng của hai điểm số hợp lệ gần nhất $\implies$ lấy 2 phần tử đỉnh, cộng lại và thêm kết quả vào cuối danh sách.
- **`'D'`**: Điểm mới bằng gấp đôi điểm số hợp lệ gần nhất $\implies$ nhân đôi phần tử đỉnh và thêm kết quả vào cuối danh sách.
- **`'C'`**: Hủy bỏ (xóa) điểm số hợp lệ gần nhất $\implies$ loại bỏ phần tử ở đỉnh danh sách (`pop`).

Cuối cùng, trả về **tổng của toàn bộ điểm số** còn lại trong danh sách.

---

### 2. Cấu trúc dữ liệu phù hợp: Ngăn xếp (Stack / LIFO)
- Nhận thấy mọi thao tác `+`, `D`, `C` đều chỉ tương tác với **các phần tử được thêm vào gần nhất** (Last In, First Out).
- Điều này phản ánh chính xác hành vi của cấu trúc dữ liệu **Ngăn xếp (Stack)**:
  - Thêm điểm: `push`
  - Hủy điểm (`'C'`): `pop`
  - Đọc điểm gần nhất (`'D'`): `peek`
  - Đọc 2 điểm gần nhất (`'+'`): xem 2 phần tử trên cùng của stack.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le operations.length \le 1000$.
- Giá trị điểm mỗi thao tác trong khoảng $[-3 \cdot 10^4, 3 \cdot 10^4]$.
- Đề bài đảm bảo mọi thao tác đều hợp lệ (khi gặp `+` luôn có ít nhất 2 điểm trước đó; khi gặp `C` hoặc `D` luôn có ít nhất 1 điểm trước đó).
- Tổng điểm và các giá trị trung gian đều vừa vặn trong kiểu số nguyên 32-bit có dấu (`int` trong Java).
- Vì số lượng thao tác tối đa chỉ là $1000$, ta có thể sử dụng trực tiếp một mảng nguyên thủy `int[] scores = new int[operations.length]` để mô phỏng stack. Điều này giúp tối ưu hóa hiệu năng vượt trội so với `Stack<Integer>` hay `ArrayList<Integer>` do không mất chi phí boxing/unboxing và cấp phát đối tượng trên Heap.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Sử dụng `java.util.Stack<Integer>` | Cách 2: Sử dụng `ArrayList<Integer>` | Cách 3: Mảng tĩnh mô phỏng Stack (`int[]`) ⭐ |
| :--- | :--- | :--- | :--- |
| **Cấu trúc** | `Stack<Integer>` | `ArrayList<Integer>` | `int[]` nguyên thủy kích thước $N$ + con trỏ đỉnh `size` |
| **Overhead** | Đồng bộ hóa (synchronized), boxing `int` $\to$ `Integer` | Boxing `Integer`, resize mảng động | **Không overhead**, mảng cố định $N \le 1000$ chiếm $\approx 4\text{ KB}$ |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N)$ (chậm do boxing) | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ (siêu nhanh, $\approx 1-2\text{ ms}$) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Đánh giá** | Lỗi thời trong Java hiện đại | Tốt, dễ hiểu | **Tối ưu tuyệt đối, chuẩn phong cách phỏng vấn High-Performance** |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mô phỏng Stack bằng Mảng + Biến Tổng Dồn

### 💡 Ý tưởng thuật toán cốt lõi

1. Khởi tạo mảng `scores` có độ dài bằng số lượng thao tác `operations.length` và biến con trỏ `size = 0`.
2. Duy trì biến `totalSum = 0` lưu trữ tổng điểm hiện tại:
   - Thay vì phải duyệt lại mảng để tính tổng ở cuối, ta **cộng/trừ dồn trực tiếp vào `totalSum`** ở mỗi thao tác:
     - Thêm `score`: `totalSum += score`
     - Hủy điểm `'C'`: `totalSum -= scores[--size]`
     - Nhân đôi `'D'`: `totalSum += doubled`
     - Tổng hai điểm `'+'`: `totalSum += sumTwo`
3. Duyệt tuần tự qua từng chuỗi thao tác `op` trong `operations` bằng câu lệnh `switch-case`:
   - `"+"`: Lấy `scores[size - 1] + scores[size - 2]`, đưa vào `scores[size++]`, cộng vào `totalSum`.
   - `"D"`: Lấy `scores[size - 1] * 2`, đưa vào `scores[size++]`, cộng vào `totalSum`.
   - `"C"`: Giảm `size`, trừ điểm vừa hủy khỏi `totalSum`.
   - `default`: Parse chuỗi thành số nguyên `Integer.parseInt(op)`, thêm vào mảng và cộng vào `totalSum`.
4. Trả về `totalSum`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `ops = ["5", "2", "C", "D", "+"]`

| Bước | Thao tác (`op`) | Hành động | Trạng thái Stack (`scores`) | Con trỏ `size` | Biến đổi `totalSum` | `totalSum` |
| :---: | :---: | :--- | :--- | :---: | :--- | :---: |
| 1 | `"5"` | Push 5 | `[5]` | 1 | $+ 5$ | **5** |
| 2 | `"2"` | Push 2 | `[5, 2]` | 2 | $+ 2$ | **7** |
| 3 | `"C"` | Pop 2 (hủy) | `[5]` | 1 | $- 2$ | **5** |
| 4 | `"D"` | Push $5 \times 2 = 10$ | `[5, 10]` | 2 | $+ 10$ | **15** |
| 5 | `"+"` | Push $10 + 5 = 15$ | `[5, 10, 15]` | 3 | $+ 15$ | **30** |

👉 **Kết quả cuối cùng:** `30`.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    public int calPoints(String[] operations) {
        int[] scores = new int[operations.length];
        int size = 0;
        int totalSum = 0;

        for (String op : operations) {
            switch (op) {
                case "+":
                    int sumTwo = scores[size - 1] + scores[size - 2];
                    scores[size++] = sumTwo;
                    totalSum += sumTwo;
                    break;
                case "D":
                    int doubled = scores[size - 1] * 2;
                    scores[size++] = doubled;
                    totalSum += doubled;
                    break;
                case "C":
                    totalSum -= scores[--size];
                    break;
                default:
                    int score = Integer.parseInt(op);
                    scores[size++] = score;
                    totalSum += score;
                    break;
            }
        }

        return totalSum;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`int[] scores = new int[operations.length];`**:
   - Cấp phát mảng nguyên thủy có kích thước bằng số lượng thao tác. Vì mỗi thao tác thêm tối đa 1 phần tử, kích thước của mảng luôn đủ để chứa toàn bộ điểm số mà không bao giờ bị tràn mảng.
2. **`int size = 0; int totalSum = 0;`**:
   - `size`: đóng vai trò con trỏ đỉnh ngăn xếp (chỉ số phần tử tiếp theo sẽ được ghi vào).
   - `totalSum`: duy trì tổng các điểm số đang hợp lệ theo thời gian thực.
3. **`switch (op)`**:
   - Sử dụng `switch` trên chuỗi trong Java hiện đại, trình biên dịch tối ưu hóa bằng bảng băm mã băm chuỗi (string hash code), cho tốc độ phân nhánh cực nhanh.
4. **`case "+":`**:
   - Lấy hai phần tử gần nhất tại chỉ số `size - 1` và `size - 2`. Cộng lại, lưu vào `scores[size]` rồi tăng `size` lên 1 (`scores[size++] = sumTwo`). Cộng giá trị này vào `totalSum`.
5. **`case "D":`**:
   - Lấy phần tử đỉnh `scores[size - 1]`, nhân đôi, ghi vào `scores[size++]`, cộng vào `totalSum`.
6. **`case "C":`**:
   - Thực hiện `--size` để lùi con trỏ đỉnh (tương đương thao tác `pop`), đồng thời trừ trực tiếp điểm số bị hủy khỏi `totalSum`.
7. **`default:`**:
   - Trường hợp chuỗi là một số nguyên (có thể âm hoặc dương, ví dụ `"-2"`), dùng `Integer.parseInt(op)` để chuyển đổi sang kiểu `int`, thêm vào mảng và cộng dồn vào `totalSum`.
8. **`return totalSum;`**:
   - Trả về kết quả ngay lập tức mà không cần một vòng lặp phụ để tính tổng các phần tử trong mảng.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N)$**:
  - Với $N$ là số lượng thao tác (`operations.length`).
  - Duyệt qua mảng `operations` đúng 1 lần duy nhất ($N$ vòng lặp).
  - Trong mỗi vòng lặp, các thao tác truy xuất mảng theo chỉ số, phép toán cộng/nhân/trừ và parse chuỗi số ngắn đều tốn thời gian hằng số $\mathcal{O}(1)$.
  - Tổng thời gian thực thi: $\mathcal{O}(N)$. Với $N \le 1000$, thời gian chạy thực tế $\approx 1-2\text{ ms}$.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N)$**:
  - Mảng nguyên thủy `int[] scores` tiêu tốn đúng $N \times 4\text{ bytes} \le 4\text{ KB}$ bộ nhớ.
  - Không sử dụng thêm bất kỳ đối tượng hay bộ nhớ phụ nào khác.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Nhận diện dạng bài Ngăn xếp (Stack):**
   - Khi bài toán yêu cầu các thao tác: "thêm phần tử", "xóa phần tử vừa thêm", hoặc "tính toán dựa trên 1 hoặc 2 phần tử gần nhất", đó là dấu hiệu kinh điển của cấu trúc dữ liệu **Stack (LIFO)**.
2. **Kỹ thuật duy trì tổng dồn (Running Sum):**
   - Thay vì lưu các điểm số rồi duyệt lại từ đầu đến cuối để cộng tổng ($\mathcal{O}(N)$ bước phụ), duy trì biến `totalSum` cập nhật trực tiếp tại mỗi thao tác giúp code ngắn gọn, trực quan và tiết kiệm một lần duyệt bộ nhớ cache.
3. **Ưu tiên Mảng nguyên thủy (`int[]`) thay vì `java.util.Stack`:**
   - Trong các buổi phỏng vấn Java, việc đề xuất sử dụng mảng nguyên thủy `int[]` để mô phỏng stack khi biết trước giới hạn trên ($N \le 1000$) thể hiện tư duy tối ưu sâu sắc về bộ nhớ, tránh overhead của `Integer` object wrapper và thread-safety synchronization của lớp cổ điển `Stack`.
