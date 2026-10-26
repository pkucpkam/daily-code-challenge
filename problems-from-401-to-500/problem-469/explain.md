# 📚 Giải thích Chi tiết Bài 657 - Robot Return to Origin

**Mục tiêu:** Cho một chuỗi `moves` biểu diễn chuỗi hành động di chuyển của một robot bắt đầu từ gốc tọa độ $(0, 0)$ trên mặt phẳng 2D. Xác định xem sau khi hoàn thành tất cả các bước di chuyển, robot có quay trở lại đúng vị trí ban đầu $(0, 0)$ hay không.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Hình học & Hệ Tọa độ Descartes 2D

Robot bắt đầu tại điểm gốc $(0, 0)$ trên mặt phẳng tọa độ Decartes hai chiều. Mỗi ký tự trong chuỗi `moves` tương ứng với một vector dịch chuyển đơn vị có độ dài bằng $1$:

- `'U'` (Up - Lên trên): Đi lên theo chiều dương trục tung $\implies \vec{v}_U = (0, +1)$
- `'D'` (Down - Xuống dưới): Đi xuống theo chiều âm trục tung $\implies \vec{v}_D = (0, -1)$
- `'R'` (Right - Sang phải): Đi sang phải theo chiều dương trục hoành $\implies \vec{v}_R = (+1, 0)$
- `'L'` (Left - Sang trái): Đi sang trái theo chiều âm trục hoành $\implies \vec{v}_L = (-1, 0)$

```text
                  y (Trục tung)
                  ▲
                  │  'U' (0, +1)
                  │
   'L' (-1, 0)    │    'R' (+1, 0)
  ◄───────────────┼───────────────► x (Trục hoành)
             (0, 0) Origin
                  │
                  │  'D' (0, -1)
                  ▼
```

Tọa độ cuối cùng $(x, y)$ của robot sau khi thực hiện tất cả các bước di chuyển được tính bằng tổng vector của tất cả các bước:

$$x = \sum \Delta x = \text{count}('R') - \text{count}('L')$$

$$y = \sum \Delta y = \text{count}('U') - \text{count}('D')$$

> 💡 **Điều kiện cần và đủ để robot trở về gốc $(0, 0)$:**
> $$\begin{cases} x = 0 \iff \text{count}('R') = \text{count}('L') \\ y = 0 \iff \text{count}('U') = \text{count}('D') \end{cases}$$
>
> Nói cách khác: Số bước sang phải phải cân bằng chính xác với số bước sang trái, và số bước đi lên phải cân bằng chính xác với số bước đi xuống.

---

### 2. Tính Độc Lập của Hai Trục Tọa Độ

- Trục hoành ($x$) và trục tung ($y$) **hoàn toàn trực giao và độc lập với nhau**:
  - Việc di chuyển `'U'` hay `'D'` chỉ làm thay đổi tọa độ $y$, hoàn toàn không tác động đến $x$.
  - Việc di chuyển `'R'` hay `'L'` chỉ làm thay đổi tọa độ $x$, hoàn toàn không tác động đến $y$.
- Bài toán cũng ghi rõ: *"The way that the robot is 'facing' is irrelevant"*. Robot không có khái niệm góc xoay hay quán tính; mỗi lệnh di chuyển luôn có ý nghĩa dịch chuyển tuyệt đối trên hệ trục tọa độ.

---

### 3. Khai phá Quy luật Toán học: Cắt tỉa Chẵn Lẻ $\mathcal{O}(1)$ (Parity Pruning)

Khoảng cách Manhattan (L1 Distance) từ vị trí robot tại thời điểm $t$ đến gốc tọa độ $(0, 0)$ được định nghĩa là:

$$d_{\text{Manhattan}}(t) = |x_t| + |y_t|$$

Tại mỗi bước di chuyển bất kỳ (`'U'`, `'D'`, `'L'`, `'R'`), chính xác một trong hai tọa độ sẽ thay đổi $+1$ hoặc $-1$. Do đó:

$$d_{\text{Manhattan}}(t + 1) \equiv d_{\text{Manhattan}}(t) + 1 \pmod 2$$

Nói cách khác: **Tính chẵn lẻ (Parity) của khoảng cách Manhattan thay đổi luân phiên sau mỗi bước di chuyển!**

- Ban đầu tại $t = 0$: $d_{\text{Manhattan}}(0) = |0| + |0| = 0$ (là một số chẵn).
- Sau $N$ bước: $d_{\text{Manhattan}}(N) \equiv N \pmod 2$.
- Để robot quay về gốc $(0, 0)$: $d_{\text{Manhattan}}(N) = |0| + |0| = 0$ (phải là số chẵn).

Từ đó ta suy ra:

$$0 \equiv N \pmod 2 \implies N \text{ bắt buộc phải là một số chẵn!}$$

Hoặc chứng minh trực quan từ số lượng ký tự:

$$N = \text{count}('U') + \text{count}('D') + \text{count}('L') + \text{count}('R')$$

Khi robot về đích, $\text{count}('U') = \text{count}('D')$ và $\text{count}('L') = \text{count}('R')$, ta có:

$$N = 2 \times \text{count}('U') + 2 \times \text{count}('R') = 2 \cdot (\text{count}('U') + \text{count}('R'))$$

Vì $N$ bằng $2$ nhân với một số nguyên, $N$ **luôn luôn là số chẵn**.

> ⚡ **Cắt tỉa sớm cực mạnh (Early Exit $\mathcal{O}(1)$):**
> Nếu độ dài chuỗi $N$ là số lẻ (`(moves.length() & 1) != 0`), robot **chắc chắn không bao giờ** có thể trở về gốc tọa độ. Ta trả về `false` ngay lập tức tại thời điểm ban đầu mà không cần duyệt bất kỳ ký tự nào!

---

### 4. Ràng buộc & Đánh giá Quy mô (Constraints)

- Độ dài chuỗi: $1 \le \text{moves.length} \le 2 \times 10^4$.
- Chuỗi chỉ chứa các ký tự `'U'`, `'D'`, `'L'`, `'R'`.
- Tọa độ cực đại: Trong trường hợp cực đoan robot đi liên tục về một hướng, tọa độ $x$ hoặc $y$ tối đa đạt $\pm 20,000$. Giá trị này hoàn toàn nằm gọn trong kiểu dữ liệu số nguyên có dấu 32-bit `int` của Java (tối đa $\approx 2 \times 10^9$), không có bất kỳ rủi ro tràn số nào.
- Với $N \le 20,000$, một thuật toán $\mathcal{O}(N)$ chỉ mất chưa đến $1 \text{ ms}$ để hoàn thành, hoàn toàn đáp ứng xuất sắc giới hạn thời gian chạy $1000 \text{ ms}$.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mô Phỏng Tọa Độ Kết Hợp Cắt Tỉa Chẵn Lẻ

### 💡 Ý tưởng thuật toán (Key Insights)

```
                            [Chuỗi moves]
                                  │
                                  ▼
                   ┌──────────────────────────────┐
                   │  Kiểm tra độ dài chuỗi (N)   │
                   └──────────────┬───────────────┘
                                  │
                ┌─────────────────┴─────────────────┐
                │ N là số lẻ                        │ N là số chẵn
                ▼                                   ▼
        [Trả về false]                     [Khởi tạo x = 0, y = 0]
      (Cắt tỉa sớm O(1))                            │
                                                    ▼
                                           [Duyệt từng ký tự]
                                        'U' ➔ y++   |  'D' ➔ y--
                                        'R' ➔ x++   |  'L' ➔ x--
                                                    │
                                                    ▼
                                      [Kiểm tra x == 0 && y == 0]
```

1. **Bước 1 (Cắt tỉa chẵn lẻ):** Kiểm tra bit cuối cùng của độ dài: `(moves.length() & 1) != 0`. Nếu là số lẻ, trả về `false` ngay lập tức.
2. **Bước 2 (Khởi tạo tọa độ):** Đặt `x = 0`, `y = 0`.
3. **Bước 3 (Duyệt và cập nhật):**
   - Dùng `moves.charAt(i)` để duyệt qua từng ký tự mà không tạo mảng `char[]` phụ trên Heap.
   - Dùng lệnh `switch-case` tương ứng với mã ASCII của ký tự để tăng/giảm $x, y$.
4. **Bước 4 (Kết luận):** Trả về kết quả của biểu thức `x == 0 && y == 0`.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `moves = "UD"` ($N = 2$, chẵn)

```text
  y ▲
    │
  1 ┼        • (0, 1) [Sau bước 'U']
    │        │
    │        ▼ [Bước 'D']
  0 ┼───────(0, 0) Start / End ────────► x
    │
```

| Bước | Ký tự | Hành động | Tọa độ $(x, y)$ | Nhận xét |
| :---: | :---: | :---: | :---: | :--- |
| **0** | Khởi tạo | Điểm xuất phát | `(0, 0)` | Gốc tọa độ |
| **1** | `'U'` | `y++` | `(0, 1)` | Robot đi lên 1 đơn vị |
| **2** | `'D'` | `y--` | `(0, 0)` | Robot đi xuống 1 đơn vị |

- Kết quả: $x = 0$ và $y = 0 \implies \mathbf{true}$.

---

#### Ví dụ 2: `moves = "LL"` ($N = 2$, chẵn)

```text
  y ▲
    │
    │  [Bước 'L']        [Bước 'L']
  0 ┼─────• (-2, 0) ◄────────• (-1, 0) ◄────── (0, 0) Start ─────► x
    │   (Kết thúc)
```

| Bước | Ký tự | Hành động | Tọa độ $(x, y)$ | Nhận xét |
| :---: | :---: | :---: | :---: | :--- |
| **0** | Khởi tạo | Điểm xuất phát | `(0, 0)` | Gốc tọa độ |
| **1** | `'L'` | `x--` | `(-1, 0)` | Robot sang trái 1 đơn vị |
| **2** | `'L'` | `x--` | `(-2, 0)` | Robot sang trái tiếp 1 đơn vị |

- Kết quả: $x = -2 \ne 0 \implies \mathbf{false}$.

---

#### Ví dụ 3: `moves = "UDU"` ($N = 3$, lẻ)

- Độ dài $N = 3$.
- Phép kiểm tra bit: `3 & 1 = 1 != 0` $\implies$ Cắt tỉa sớm, trả về $\mathbf{false}$ ngay tại $\mathcal{O}(1)$ mà không cần thực hiện bất kỳ phép dịch chuyển nào.

---

### 💻 Cài đặt mã nguồn chi tiết (Java Implementation)

```java
class Solution {
    /**
     * Best Solution: Mô phỏng dịch chuyển tọa độ (Coordinate Simulation)
     * kết hợp Cắt tỉa sớm tính chẵn lẻ (Parity Check Early Pruning).
     *
     * Time Complexity:  O(1) tốt nhất (khi độ dài chuỗi lẻ), O(N) xấu nhất.
     * Space Complexity: O(1) tuyệt đối.
     */
    public boolean judgeCircle(String moves) {
        // Cắt tỉa sớm O(1): Nếu số bước di chuyển là lẻ, robot không bao giờ có thể quay lại (0, 0)
        if ((moves.length() & 1) != 0) {
            return false;
        }

        int x = 0;
        int y = 0;
        int len = moves.length();

        for (int i = 0; i < len; i++) {
            char move = moves.charAt(i);
            switch (move) {
                case 'U':
                    y++;
                    break;
                case 'D':
                    y--;
                    break;
                case 'R':
                    x++;
                    break;
                case 'L':
                    x--;
                    break;
                default:
                    break;
            }
        }

        // Robot quay về gốc khi và chỉ khi cả 2 tọa độ x và y đều trở về 0
        return x == 0 && y == 0;
    }
}
```

---

### 🔍 Giải thích từng dòng lệnh chi tiết

1. `if ((moves.length() & 1) != 0) return false;`:
   - Phép toán bitwise AND `len & 1` kiểm tra bit trọng số thấp nhất (LSB).
   - Nếu bit này là `1`, số là số lẻ $\implies$ trả về `false` ngay tức khắc. Phép tính bit nhanh hơn phép chia lấy dư `% 2` ở cấp độ phần cứng.
2. `int x = 0; int y = 0;`:
   - Khởi tạo hai biến nguyên nguyên thủy (primitive integers) đại diện cho tọa độ hiện tại. Trình biên dịch JIT sẽ trực tiếp đặt hai biến này vào các thanh ghi CPU (CPU registers), loại bỏ hoàn toàn việc truy cập RAM.
3. `int len = moves.length();`:
   - Lưu trữ trước độ dài chuỗi vào biến cục bộ `len` để tránh việc gọi lại phương thức `length()` trong mỗi lần lặp.
4. `moves.charAt(i)` trong vòng lặp:
   - Truy xuất trực tiếp ký tự tại chỉ số `i` của chuỗi bên dưới mà không tạo mảng `char[]` mới, giữ cho bộ nhớ bổ trợ ở mức $\mathcal{O}(1)$ tuyệt đối.
5. `switch (move)`:
   - Được biên dịch thành lệnh `tableswitch` hoặc `lookupswitch` trong Java Bytecode, cho phép nhảy trực tiếp đến nhãn tương ứng với độ trễ thấp và dự đoán rẽ nhánh (branch prediction) hiệu quả.
6. `return x == 0 && y == 0;`:
   - Trả về `true` nếu cả độ dịch chuyển ngang và độ dịch chuyển dọc đều bị triệt tiêu hoàn toàn về `0`.

---

### ⚙️ Kỹ nghệ Tối ưu Hiệu năng trong Java (Performance Engineering Deep-Dive)

Khi giải bài này trên Java, có hai cách duyệt chuỗi phổ biến:

#### 1. `moves.charAt(i)` vs `moves.toCharArray()`

| Đặc điểm | `moves.charAt(i)` (Cách dùng trong bài) | `moves.toCharArray()` |
| :--- | :--- | :--- |
| **Bộ nhớ phụ (Auxiliary Memory)** | **$\mathcal{O}(1)$ Tuyệt đối** | $\mathcal{O}(N)$ trên Heap |
| **Áp lực lên Garbage Collector (GC)** | **$0$ đối tượng được sinh ra** | Tạo một mảng `char[N]` kích thước tới $20,000$ phần tử |
| **Hiệu ứng CPU Cache** | Đọc trực tiếp từ mảng `byte[]`/`char[]` nội bộ của `String` | Đòi hỏi một lượt gọi `System.arraycopy` trước khi duyệt |
| **Đánh giá** | **Giải pháp sạch, tối ưu bộ nhớ lý tưởng nhất** | Chạy nhanh trên benchmark nhưng lãng phí bộ nhớ |

#### 2. Kỹ thuật mảng đếm (Lookup Array)

Một số lập trình viên sử dụng một mảng ánh xạ 128 phần tử ASCII:
```java
int[] counts = new int[128];
for (int i = 0; i < len; i++) counts[moves.charAt(i)]++;
return counts['U'] == counts['D'] && counts['R'] == counts['L'];
```
Tuy nhiên, cách này đòi hỏi cấp phát mảng trên Heap hoặc Stack và tốn thêm các thao tác truy xuất bộ nhớ qua mảng thay vì giữ trực tiếp biến `x, y` trên thanh ghi CPU. Do đó, mô phỏng trực tiếp bằng 2 biến nguyên `x` và `y` luôn là tối ưu nhất.

---

### 📊 Phân tích Độ Phức Tạp (Complexity Analysis)

#### ⏱️ Độ phức tạp Thời gian (Time Complexity)
- **Trường hợp tốt nhất (Best Case):** $\mathcal{O}(1)$
  - Xảy ra khi chuỗi có độ dài lẻ (chiếm xấp xỉ $50\%$ các trường hợp ngẫu nhiên). Thuật toán chỉ thực hiện một phép tính bitwise và dừng ngay lập tức.
- **Trường hợp xấu nhất (Worst Case):** $\mathcal{O}(N)$
  - Khi độ dài chuỗi chẵn, thuật toán duyệt qua $N$ ký tự đúng $1$ lần duy nhất. Tại mỗi bước, chỉ có một phép so sánh `switch` và một phép cộng/trừ đơn giản.
  - Tổng số thao tác tối đa: $20,000$ bước $\implies < 1 \text{ ms}$.

#### 💾 Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(1)$ Tuyệt đối:**
  - Chỉ duy trì 3 biến nguyên nguyên thủy (`x`, `y`, `len`).
  - Không tạo thêm bất kỳ cấu trúc dữ liệu phụ trợ nào (không dùng `HashMap`, không mảng tần suất, không tạo mảng `char[]` phụ).

---

## 🔄 Các Hướng Tiếp Cận Khác & So Sánh (Alternative Approaches)

### Cách 1: Đếm tần suất bằng mảng ký tự ASCII (Frequency Array)

```java
class Solution {
    public boolean judgeCircle(String moves) {
        if ((moves.length() & 1) != 0) return false;
        
        int[] freq = new int[128];
        for (int i = 0; i < moves.length(); i++) {
            freq[moves.charAt(i)]++;
        }
        return freq['U'] == freq['D'] && freq['L'] == freq['R'];
    }
}
```
- **Ưu điểm:** Tách biệt rõ ràng việc đếm từng loại bước.
- **Nhược điểm:** Tốn thêm mảng `freq[128]`, không tinh gọn bằng việc gộp thẳng vào 2 trục $x, y$.

---

### Cách 2: Dùng `HashMap`

```java
class Solution {
    public boolean judgeCircle(String moves) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : moves.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return Objects.equals(map.get('U'), map.get('D')) && 
               Objects.equals(map.get('L'), map.get('R'));
    }
}
```
- **Ưu điểm:** Thể hiện rõ tư duy dùng cấu trúc dữ liệu lưu trữ cặp khóa-giá trị.
- **Nhược điểm:** Tệ nhất trong các cách. Tốn chi phí Autoboxing `char` $\to$ `Character`, `int` $\to$ `Integer`, tính mã băm `hashCode()`, xử lý va chạm băm và cấp phát đối tượng trên Heap. Chậm hơn cách tối ưu từ $10$ đến $20$ lần.

---

### 📊 Bảng so sánh toàn diện các phương pháp

| Tiêu chí | Mô phỏng Tọa độ + Parity (Best Solution) ⭐ | Mảng Tần suất ASCII | Bảng băm `HashMap` |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp Thời gian (Best Case)** | **$\mathcal{O}(1)$** (Nhờ cắt tỉa lẻ) | $\mathcal{O}(1)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Thời gian (Worst Case)** | **$\mathcal{O}(N)$** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Không gian** | **$\mathcal{O}(1)$ Tuyệt đối** | $\mathcal{O}(1)$ ($128$ `int`) | $\mathcal{O}(1)$ (Tối đa 4 mục) |
| **Bộ nhớ Heap phát sinh** | **$0$ bytes** | Cấp phát mảng `int[128]` | Cấp phát nhiều đối tượng Node & Integer |
| **Tốc độ thực thi thực tế** | **Nhanh nhất ($\sim 1 \text{ - } 3 \text{ ms}$)** | Nhanh ($\sim 3 \text{ - } 5 \text{ ms}$) | Rất chậm ($\sim 15 \text{ - } 25 \text{ ms}$) |
| **Đánh giá phỏng vấn** | **Hoàn hảo (Senior level)** | Chấp nhận được | Dưới kỳ vọng (Over-engineered) |

---

## 🎓 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways)

1. **Khai thác Bất biến & Tính Chẵn Lẻ (Invariants & Parity):**
   - Trong các bài toán chuyển động trên lưới (Grid / Cartesian plane), luôn chú ý đến tính chẵn lẻ của khoảng cách Manhattan $|x| + |y|$.
   - Phát hiện ra điều kiện độ dài $N$ bắt buộc phải chẵn để cắt tỉa $\mathcal{O}(1)$ là điểm cộng rất lớn trong mắt người phỏng vấn, chứng minh tư duy toán học nhạy bén.
2. **Khử Bớt Trạng Thái Dư Thừa:**
   - Ban đầu có thể ta nghĩ cần đếm riêng $4$ biến (`u`, `d`, `l`, `r`). Nhưng vì $u$ và $d$ đối nghịch nhau, ta chỉ cần $1$ biến $y = u - d$. Tương tự, $x = r - l$. Giảm một nửa số biến cần quản lý.
3. **Ý thức về Quản lý Bộ nhớ trong Java:**
   - Tránh việc lạm dụng `.toCharArray()` khi chỉ cần duyệt qua chuỗi một lần. Việc ưu tiên `charAt(i)` giúp duy trì $\mathcal{O}(1)$ space thuần túy và giảm tải cho Garbage Collector.
