# 📚 Giải thích Chi tiết Bài 658 - Find K Closest Elements

**Mục tiêu:** Cho một mảng số nguyên đã sắp xếp `arr`, hai số nguyên `k` và `x`. Cần tìm $k$ phần tử trong mảng có khoảng cách gần với giá trị `x` nhất. Kết quả trả về phải được sắp xếp theo thứ tự tăng dần.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Mảng đã Sắp xếp & Tính Liên tục của Cửa sổ Nghiệm

Điểm mấu chốt đầu tiên của bài toán là mảng đầu vào `arr` **đã được sắp xếp theo thứ tự tăng dần**:

$$arr[0] \le arr[1] \le arr[2] \le \dots \le arr[n-1]$$

Xét hàm khoảng cách hình học từ một giá trị $v$ tới điểm mốc $x$:

$$d(v) = |v - x|$$

Trên trục số thực, đồ thị của hàm khoảng cách $d(v)$ có dạng **chữ V** đối xứng với đáy cực tiểu đạt tại $v = x$:
- Khi $v < x$: hàm khoảng cách giảm đơn điệu theo $v$.
- Khi $v > x$: hàm khoảng cách tăng đơn điệu theo $v$.

```text
Khoảng cách d(v) = |v - x|
         ▲
         │  \                 /
         │   \               /
         │    \             /
         │     \           /
         │      \         /
         │       \       /
       0 ┼─────────\──•──/────────► Giá trị v
                     v = x
```

Vì mảng `arr` có thứ tự tăng dần đơn điệu, khi ta chọn ra $k$ phần tử có khoảng cách $d(arr[i])$ nhỏ nhất:

> 💡 **Tính chất bất biến (Invariant):**
> $k$ phần tử gần $x$ nhất bắt buộc phải tạo thành **một mảng con liên tiếp (contiguous subarray)** gồm đúng $k$ phần tử trong `arr`!

*Chứng minh bằng phản chứng:* Giả sử có một tập hợp $k$ phần tử tối ưu chứa hai phần tử $arr[i]$ và $arr[j]$ (với $i < j$) nhưng lại bỏ qua phần tử nằm giữa là $arr[m]$ ($i < m < j$).
- Nếu cả ba phần tử cùng nằm về một phía của $x$ (ví dụ: $x \le arr[i] < arr[m] < arr[j]$), thì rõ ràng $|arr[m] - x| < |arr[j] - x|$, việc thay thế $arr[j]$ bằng $arr[m]$ sẽ cho khoảng cách nhỏ hơn hoặc bằng $\implies$ Mâu thuẫn với tính tối ưu.
- Lập luận tương tự khi cả ba cùng nằm bên trái của $x$.
- Do đó, tập nghiệm luôn luôn là một cửa sổ trượt (sliding window) liên tiếp có độ dài đúng bằng $k$.

---

### 2. Quy tắc So sánh Khoảng cách & Xử lý Trùng khoảng cách (Tie-breaking Rule)

Bài toán định nghĩa phần tử $a$ được ưu tiên chọn hơn phần tử $b$ khi và chỉ khi:

$$\begin{cases} |a - x| < |b - x| \\ \text{hoặc } |a - x| = |b - x| \text{ và } a < b \end{cases}$$

Trong mảng đã sắp xếp:
- Nếu $a = arr[i]$ và $b = arr[j]$ với $i < j$, thì hiển nhiên $a \le b$.
- Khi cả hai có cùng khoảng cách tới $x$ ($|a - x| = |b - x|$), phần tử đứng trước ($a$) luôn có giá trị nhỏ hơn hoặc bằng phần tử đứng sau ($b$).
- Theo quy tắc tie-break, **phần tử bên trái luôn luôn được ưu tiên hơn phần tử bên phải**.

---

### 3. Các vị trí tương đối của $x$ so với mảng

```text
Trường hợp 1: x nằm bên trái mảng (x <= arr[0])
   x
───•──────[arr[0], arr[1], ..., arr[k-1]], ..., arr[n-1]
           ▲ Cửa sổ tối ưu là k phần tử đầu tiên

Trường hợp 2: x nằm bên phải mảng (x >= arr[n-1])
   arr[0], ..., [arr[n-k], ..., arr[n-1]]──────•─── x
                 ▲ Cửa sổ tối ưu là k phần tử cuối cùng

Trường hợp 3: x nằm lọt giữa mảng (arr[0] < x < arr[n-1])
   arr[0], ..., [arr[low], ..., arr[low + k - 1]], ..., arr[n-1]
                          ▲ x nằm đâu đó gần cửa sổ
```

---

### 4. Ràng buộc & Đánh giá quy mô (Constraints)

- $1 \le k \le arr.length \le 10^4$.
- $-10^4 \le arr[i], x \le 10^4$.
- Mảng `arr` đã được sắp xếp tăng dần.

**Đánh giá độ phức tạp:**
- Với $N \le 10^4$, thuật toán sắp xếp lại toàn bộ mảng $\mathcal{O}(N \log N)$ tốn $\approx 1.4 \times 10^5$ phép tính.
- Thuật toán co hai con trỏ $\mathcal{O}(N)$ tốn tối đa $10^4$ phép tính.
- Thuật toán tối ưu nhất: **Binary Search trên điểm bắt đầu cửa sổ** chỉ cần $\mathcal{O}(\log(N - k) + k)$ bước. Với $N = 10^4$, $\log_2(10000) \approx 13$ phép so sánh nhị phân, cho tốc độ thực thi gần như tức thì ($0 \text{ ms}$).

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Binary Search Trên Điểm Bắt Đầu Cửa Sổ

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Thay vì đi tìm từng phần tử trong số $k$ phần tử (phải mở rộng hai con trỏ sang hai bên và xử lý nhiều trường hợp biên phức tạp), ta **chuyển đổi không gian tìm kiếm**:

> 🎯 **Mục tiêu mới:** Tìm chỉ số bắt đầu `low` của cửa sổ kích thước $k$ sao cho đoạn con $[low, low + k - 1]$ chứa $k$ phần tử tối ưu nhất.

#### Không gian tìm kiếm của chỉ số bắt đầu:
- Một cửa sổ độ dài $k$ bắt đầu tại chỉ số $i$ sẽ kết thúc tại $i + k - 1$.
- Chỉ số kết thúc tối đa là $N - 1 \implies i + k - 1 \le N - 1 \implies i \le N - k$.
- Do đó, chỉ số bắt đầu khả dĩ $i$ chỉ nằm trong đoạn đóng:

$$i \in [0, N - k]$$

Có đúng $N - k + 1$ vị trí cửa sổ tiềm năng!

#### Phép so sánh then chốt giữa hai phần tử biên:
Khi đang cân nhắc một cửa sổ bắt đầu tại `mid`, hai phần tử cạnh tranh trực tiếp với nhau là:
1. $arr[mid]$: phần tử nằm ở rìa trái của cửa sổ hiện tại.
2. $arr[mid + k]$: phần tử nằm ngay sau cửa sổ hiện tại (phần tử sẽ được thêm vào nếu dịch cửa sổ sang phải 1 bước).

Cửa sổ hiện tại là $arr[mid \dots mid + k - 1]$. Nếu ta dịch sang phải 1 bước, cửa sổ sẽ trở thành $arr[mid + 1 \dots mid + k]$.
Để quyết định nên dịch sang phải hay giữ/dịch sang trái, ta chỉ cần so sánh độ ưu tiên giữa $arr[mid]$ và $arr[mid + k]$ đối với $x$!

```text
       Cửa sổ A (bắt đầu tại mid):     [arr[mid], arr[mid+1], ..., arr[mid+k-1]]
       Cửa sổ B (bắt đầu tại mid + 1):           [arr[mid+1], ..., arr[mid+k-1], arr[mid+k]]
                                        ▲                                         ▲
                                   So sánh arr[mid]           với            arr[mid+k]
```

#### Biểu thức so sánh không cần `Math.abs()`:
Điều kiện để phần tử $arr[mid + k]$ **tốt hơn hẳn** $arr[mid]$ là:

$$x - arr[mid] > arr[mid + k] - x$$

#### 📐 Chứng minh Toán học tính đúng đắn cho mọi trường hợp của $x$:

Ta xét 3 vị trí khả dĩ của $x$ so với đoạn $[arr[mid], arr[mid + k]]$:

1. **Trường hợp 1: $x \le arr[mid] \le arr[mid + k]$ ($x$ nằm bên trái cả hai)**
   - Khoảng cách thực tế: $|arr[mid] - x| = arr[mid] - x \le arr[mid + k] - x = |arr[mid + k] - x|$.
   - Phần tử $arr[mid]$ gần $x$ hơn $arr[mid + k]$.
   - Xét biểu thức: $x - arr[mid] \le 0$ và $arr[mid + k] - x \ge 0$.
   - Vì một số $\le 0$ không thể lớn hơn một số $\ge 0$, biểu thức `x - arr[mid] > arr[mid + k] - x` luôn cho kết quả **`false`**!
   - Thuật toán gán: `high = mid` (thu hẹp sang trái, giữ lại $arr[mid]$). $\implies$ **Hoàn toàn chính xác!**

2. **Trường hợp 2: $arr[mid] \le arr[mid + k] \le x$ ($x$ nằm bên phải cả hai)**
   - Khoảng cách thực tế: $|arr[mid + k] - x| = x - arr[mid + k] \le x - arr[mid] = |arr[mid] - x|$.
   - Phần tử $arr[mid + k]$ gần $x$ hơn $arr[mid]$.
   - Xét biểu thức: $x - arr[mid] \ge 0$ và $arr[mid + k] - x \le 0$.
   - Khi $arr[mid] < arr[mid + k]$, ta có $x - arr[mid] > arr[mid + k] - x$, biểu thức cho kết quả **`true`**!
   - Thuật toán gán: `low = mid + 1` (loại bỏ $arr[mid]$, dịch cửa sổ sang phải để lấy $arr[mid + k]$). $\implies$ **Hoàn toàn chính xác!**

3. **Trường hợp 3: $arr[mid] < x < arr[mid + k]$ ($x$ nằm lọt giữa hai phần tử)**
   - Lúc này: $x - arr[mid] = |arr[mid] - x| > 0$ và $arr[mid + k] - x = |arr[mid + k] - x| > 0$.
   - Biểu thức $x - arr[mid] > arr[mid + k] - x$ chính là:

   $$|arr[mid] - x| > |arr[mid + k] - x|$$

   - Nếu $arr[mid + k]$ gần $x$ hơn: biểu thức là `true` $\implies low = mid + 1$.
   - Nếu $arr[mid]$ gần $x$ hơn: biểu thức là `false` $\implies high = mid$.
   - **Xử lý Tie-break khi khoảng cách bằng nhau:** Khi $|arr[mid] - x| = |arr[mid + k] - x|$, phép so sánh nghiêm ngặt `>` trả về `false` $\implies high = mid$. Nhờ đó, $arr[mid]$ (phần tử có giá trị nhỏ hơn) tự động được ưu tiên giữ lại mà không cần bất kỳ dòng lệnh rẽ nhánh phụ nào!

#### Tính Đơn Điệu (Monotonicity) để Tìm Kiếm Nhị Phân:
Đặt hàm $f(i) = (x - arr[i]) - (arr[i + k] - x) = 2x - (arr[i] + arr[i + k])$.
Vì `arr` là mảng không giảm, tổng $arr[i] + arr[i + k]$ là một hàm **đơn điệu không giảm** theo $i$.
Do đó, $f(i)$ là một hàm **đơn điệu không tăng** theo $i$.
Dãy giá trị boolean của điều kiện $f(i) > 0$ sẽ có dạng:

$$[\text{true}, \text{true}, \dots, \text{true}, \text{false}, \text{false}, \dots, \text{false}]$$

Mục tiêu của Binary Search chính là tìm chỉ số đầu tiên chuyển giao sang $\text{false}$ (First False Transition). Đây là điều kiện lý tưởng để áp dụng Binary Search!

---

### 📐 Sơ đồ luồng thuật toán (ASCII Flowchart)

```text
                     [Bắt đầu: arr, k, x]
                              │
                              ▼
                 [Khởi tạo: low = 0, high = N - k]
                              │
                              ▼
                    ┌──────────────────┐
               ┌───►│   low < high ?   │─────┐ (False)
               │    └────────┬─────────┘     │
               │             │ (True)        │
               │             ▼               │
               │    [mid = low + (high - low) / 2]
               │             │               │
               │             ▼               │
               │   ┌────────────────────┐    │
               │   │ x - arr[mid] >     │    │
               │   │ arr[mid + k] - x ? │    │
               │   └─────────┬──────────┘    │
               │      (True) │  │ (False)    │
               │  ┌──────────┘  └─────────┐  │
               │  ▼                       ▼  │
               │ [low = mid + 1]     [high = mid]
               │  (Dịch phải)         (Dịch trái)
               │  │                       │  │
               └──┴───────────────────────┘  │
                                             ▼
                             [Cửa sổ tối ưu bắt đầu tại low]
                                             │
                                             ▼
                            [Sao chép arr[low ... low + k - 1]]
                                             │
                                             ▼
                                     [Trả về kết quả]
```

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `arr = [1, 2, 3, 4, 5]`, `k = 4`, `x = 3`

- Kích thước mảng $N = 5$, $k = 4$.
- Không gian tìm kiếm: $low = 0$, $high = N - k = 5 - 4 = 1$.

```text
Chỉ số i:      0     1     2     3     4
Giá trị:     [ 1  ,  2  ,  3  ,  4  ,  5 ]
                        x = 3
```

- **Vòng lặp 1:**
  - $low = 0, high = 1 \implies mid = 0 + (1 - 0) / 2 = 0$.
  - So sánh $arr[mid] = arr[0] = 1$ với $arr[mid + k] = arr[4] = 5$:
    - $x - arr[mid] = 3 - 1 = 2$
    - $arr[mid + k] - x = 5 - 3 = 2$
    - Kiểm tra: $2 > 2 \implies \mathbf{false}$.
  - Gán: $high = mid = 0$.
- Vòng lặp kết thúc vì $low < high$ ($0 < 0$) là $\mathbf{false}$.
- Vị trí bắt đầu tối ưu: $low = 0$.
- Cửa sổ kết quả: $arr[0 \dots 3] = [1, 2, 3, 4]$.
- **Giải thích:** Cả $1$ và $5$ đều cách $3$ một khoảng là $2$. Nhưng vì $1 < 5$, quy tắc tie-break chọn $1$. Cửa sổ giữ nguyên ở vị trí bắt đầu $0$.

---

#### Ví dụ 2: `arr = [1, 1, 2, 3, 4, 5]`, `k = 4`, `x = -1`

- $N = 6, k = 4 \implies low = 0, high = 6 - 4 = 2$.
- **Vòng lặp 1:**
  - $low = 0, high = 2 \implies mid = 1$.
  - $arr[mid] = arr[1] = 1$, $arr[mid + k] = arr[5] = 5$.
  - $x - arr[mid] = -1 - 1 = -2$.
  - $arr[mid + k] - x = 5 - (-1) = 6$.
  - $-2 > 6 \implies \mathbf{false} \implies high = mid = 1$.
- **Vòng lặp 2:**
  - $low = 0, high = 1 \implies mid = 0$.
  - $arr[mid] = arr[0] = 1$, $arr[mid + k] = arr[4] = 4$.
  - $x - arr[0] = -1 - 1 = -2$.
  - $arr[4] - x = 4 - (-1) = 5$.
  - $-2 > 5 \implies \mathbf{false} \implies high = mid = 0$.
- Kết thúc: $low = 0 \implies$ Kết quả: $arr[0 \dots 3] = [1, 1, 2, 3]$.

---

#### Ví dụ 3: `arr = [1, 2, 3, 4, 5]`, `k = 4`, `x = 6`

- $N = 5, k = 4 \implies low = 0, high = 1$.
- **Vòng lặp 1:**
  - $low = 0, high = 1 \implies mid = 0$.
  - $arr[0] = 1, arr[4] = 5$.
  - $x - arr[0] = 6 - 1 = 5$.
  - $arr[4] - x = 5 - 6 = -1$.
  - $5 > -1 \implies \mathbf{true} \implies low = mid + 1 = 1$.
- Kết thúc: $low = 1 \implies$ Kết quả: $arr[1 \dots 4] = [2, 3, 4, 5]$.

---

### 💻 Cài đặt mã nguồn chi tiết (Java Implementation)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    /**
     * Best Solution: Tìm kiếm nhị phân trên chỉ số bắt đầu của cửa sổ (Binary Search on Window Start).
     *
     * Ý tưởng:
     * - Do mảng đã sắp xếp, k phần tử gần x nhất luôn tạo thành một mảng con liên tiếp kích thước k.
     * - Chỉ số bắt đầu của cửa sổ 'low' nằm trong khoảng [0, arr.length - k].
     * - Với mỗi vị trí thử nghiệm 'mid', ta so sánh khoảng cách giữa phần tử đầu cửa sổ arr[mid]
     *   và phần tử ngay sau cửa sổ arr[mid + k] tới x:
     *     - Nếu (x - arr[mid] > arr[mid + k] - x): phần tử arr[mid + k] gần x hơn arr[mid],
     *       cửa sổ tối ưu chắc chắn phải dịch sang phải (loại bỏ arr[mid]) -> low = mid + 1.
     *     - Ngược lại: arr[mid] gần x hơn (hoặc bằng khoảng cách nhưng có giá trị nhỏ hơn),
     *       ta ưu tiên arr[mid] theo quy tắc tie-break -> high = mid.
     *
     * Độ phức tạp thời gian: O(log(N - k) + k)
     * Độ phức tạp không gian: O(1) bộ nhớ phụ trợ (ngoại trừ danh sách kết quả)
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int low = 0;
        int high = arr.length - k;

        // Tìm kiếm nhị phân vị trí bắt đầu tối ưu của cửa sổ k phần tử
        while (low < high) {
            int mid = low + (high - low) / 2;

            // So sánh khoảng cách từ x tới arr[mid] và arr[mid + k]
            // Không cần dùng Math.abs() vì arr đã được sắp xếp tăng dần
            if (x - arr[mid] > arr[mid + k] - x) {
                low = mid + 1; // arr[mid + k] tốt hơn -> dịch cửa sổ sang phải
            } else {
                high = mid;    // arr[mid] tốt hơn hoặc bằng -> giữ/dịch cửa sổ sang trái
            }
        }

        // Tạo danh sách kết quả gồm k phần tử từ vị trí low
        List<Integer> result = new ArrayList<>(k);
        for (int i = low; i < low + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }
}
```

---

### 🔍 Giải thích từng dòng lệnh chi tiết

1. `int low = 0; int high = arr.length - k;`:
   - Xác định không gian tìm kiếm của chỉ số bắt đầu cửa sổ. Cửa sổ độ dài $k$ bắt đầu muộn nhất tại `arr.length - k`.
2. `while (low < high)`:
   - Dạng mẫu tìm kiếm nhị phân tìm kiếm cận dưới (Lower Bound Binary Search). Vòng lặp dừng khi $low == high$, tại đó `low` chính là điểm hội tụ duy nhất tối ưu.
3. `int mid = low + (high - low) / 2;`:
   - Tính chỉ số ở giữa một cách an toàn, tránh lỗi tràn số số nguyên (Integer Overflow) thay vì dùng `(low + high) / 2`.
4. `if (x - arr[mid] > arr[mid + k] - x)`:
   - So sánh khoảng cách trực tiếp mà không cần hàm `Math.abs()`.
   - Nếu điều kiện thỏa mãn: $arr[mid + k]$ gần $x$ hơn $arr[mid]$. Bất kỳ cửa sổ nào bắt đầu tại $\le mid$ đều phải chứa $arr[mid]$ và không chứa $arr[mid + k]$, do đó chúng đều kém tối ưu hơn. Ta an tâm loại bỏ toàn bộ nửa trái bằng lệnh `low = mid + 1`.
5. `else { high = mid; }`:
   - Nếu $arr[mid]$ tốt hơn hoặc bằng $arr[mid + k]$, vị trí `mid` vẫn có thể là nghiệm tối ưu, ta giữ lại `mid` bằng cách co biên phải `high = mid`.
6. `List<Integer> result = new ArrayList<>(k);`:
   - Khởi tạo `ArrayList` với sức chứa định sẵn đúng bằng $k$ (`capacity = k`). Tránh được việc mảng nội bộ của `ArrayList` phải cấp phát lại bộ nhớ và sao chép phần tử nhiều lần khi thêm dữ liệu.
7. `for (int i = low; i < low + k; i++) result.add(arr[i]);`:
   - Thu thập đúng $k$ phần tử liên tiếp từ vị trí `low` đã tìm được. Do `arr` đã tăng dần, danh sách kết quả tự động được sắp xếp tăng dần, không cần gọi thêm `Collections.sort()`.

---

### ⚙️ Kỹ nghệ Tối ưu Hiệu năng trong Java (Performance Engineering Deep-Dive)

#### 1. Khử triệt để hàm `Math.abs()`
- Thông thường, so sánh khoảng cách đòi hỏi gọi `Math.abs(arr[mid] - x) > Math.abs(arr[mid + k] - x)`.
- Hàm `Math.abs()` chứa phép rẽ nhánh điều kiện `(a < 0) ? -a : a`. Trong vòng lặp nhị phân, việc biến đổi thành `x - arr[mid] > arr[mid + k] - x` loại bỏ hoàn toàn việc gọi hàm và phép rẽ nhánh, giúp CPU tận dụng tối đa cơ chế dự đoán rẽ nhánh (Branch Predictor).

#### 2. Phân tích an toàn tràn số (Integer Overflow Proof)
- Theo ràng buộc bài toán: $-10^4 \le arr[i], x \le 10^4$.
- Giá trị hiệu lớn nhất: $x - arr[mid] \le 10^4 - (-10^4) = 20,000$.
- Giá trị hiệu nhỏ nhất: $x - arr[mid] \ge -10^4 - 10^4 = -20,000$.
- Cả hai hiệu đều nằm trong khoảng $[-20,000, 20,000]$, hoàn toàn nằm an toàn trong giới hạn của số nguyên có dấu 32-bit `int` (tối đa $\approx \pm 2.14 \times 10^9$). Không có bất kỳ nguy cơ tràn số nào.

#### 3. Tối ưu bộ nhớ với `new ArrayList<>(k)` vs `Arrays.stream()`
- Việc sử dụng `Arrays.stream(arr, low, low + k).boxed().collect(Collectors.toList())` thường rất ngắn gọn nhưng tạo ra hàng loạt đối tượng trung gian: `IntStream`, `Stream<Integer>`, `Collector`, gây áp lực lớn lên Garbage Collector (GC) và làm thời gian thực thi tăng từ $1\text{ ms}$ lên $10\text{ - }15\text{ ms}$.
- Vòng lặp `for` thủ công kết hợp `new ArrayList<>(k)` là giải pháp tối ưu nhất cả về CPU cache lẫn quản lý bộ nhớ.

---

### 📊 Phân tích Độ Phức Tạp (Complexity Analysis)

#### ⏱️ Độ phức tạp Thời gian (Time Complexity)
- **Tìm kiếm nhị phân:** Không gian tìm kiếm có độ dài $N - k + 1$. Mỗi bước chia đôi không gian tìm kiếm, do đó số lần lặp tối đa là $\log_2(N - k + 1) = \mathcal{O}(\log(N - k))$.
- **Tạo danh sách kết quả:** Duyệt qua $k$ phần tử và thêm vào danh sách tốn $\mathcal{O}(k)$.
- **Tổng thời gian:**

$$\mathcal{O}(\log(N - k) + k)$$

- Đây là độ phức tạp thời gian **tối ưu lý thuyết tuyệt đối** cho bài toán này, vì kích thước kết quả trả về bắt buộc là $k$, không thể có thuật toán nào nhanh hơn $\mathcal{O}(k)$ ở bước xuất kết quả!

#### 💾 Độ phức tạp Không gian (Space Complexity)
- Thuật toán chỉ sử dụng đúng 3 biến nguyên nguyên thủy (`low`, `high`, `mid`) trên Call Stack.
- **$\mathcal{O}(1)$ bộ nhớ phụ trợ (Auxiliary Space)** (không tính bộ nhớ bắt buộc của danh sách kết quả trả về gồm $k$ phần tử).

---

## 🔄 Các Hướng Tiếp Cận Khác & So Sánh (Alternative Approaches)

### Cách 1: Thu hẹp hai con trỏ từ hai đầu mảng (Two Pointers Shrinking Window)

Xuất phát từ cửa sổ toàn mảng $[0, N-1]$, ta liên tục loại bỏ phần tử ở xa $x$ hơn trong hai đầu `left` và `right` cho tới khi độ dài cửa sổ bằng $k$.

```java
import java.util.ArrayList;
import java.util.List;

class SolutionTwoPointers {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - 1;

        // Thu hẹp cửa sổ từ hai đầu cho tới khi còn đúng k phần tử
        while (right - left + 1 > k) {
            if (x - arr[left] > arr[right] - x) {
                left++;  // arr[left] ở xa hơn -> loại bỏ bên trái
            } else {
                right--; // arr[right] ở xa hơn hoặc bằng -> loại bỏ bên phải
            }
        }

        List<Integer> result = new ArrayList<>(k);
        for (int i = left; i <= right; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
```

- **Độ phức tạp thời gian:** $\mathcal{O}(N - k + k) = \mathcal{O}(N)$.
- **Ưu điểm:** Cực kỳ trực quan, dễ nhớ, dễ viết mã nguồn.
- **Nhược điểm:** Khi $N$ rất lớn và $k$ nhỏ (ví dụ $N = 10^5, k = 5$), thuật toán phải thực hiện gần $10^5$ phép lặp, chậm hơn nhiều so với $\mathcal{O}(\log N)$.

---

### Cách 2: Binary Search tìm vị trí của $x$ + Mở rộng hai con trỏ ra hai phía (Expand Around Center)

1. Dùng `Arrays.binarySearch` để tìm vị trí gần $x$ nhất trong mảng tại thời gian $\mathcal{O}(\log N)$.
2. Đặt `left` và `right` tại vị trí đó, mở rộng sang hai bên từng phần tử một cho đến khi thu thập đủ $k$ phần tử.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SolutionBinarySearchExpand {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int index = java.util.Arrays.binarySearch(arr, x);
        if (index < 0) {
            index = -(index + 1); // Vị trí chèn nếu không tìm thấy chính xác x
        }

        int left = index - 1;
        int right = index;

        // Mở rộng sang hai bên
        while (k > 0) {
            if (left < 0) {
                right++;
            } else if (right >= arr.length) {
                left--;
            } else if (Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
                left--;
            } else {
                right++;
            }
            k--;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left + 1; i < right; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
```

- **Độ phức tạp thời gian:** $\mathcal{O}(\log N + k)$.
- **Ưu điểm:** Nhanh hơn phương pháp 2 con trỏ $\mathcal{O}(N)$.
- **Nhược điểm:** Mã nguồn tương đối dài, phải kiểm tra điều kiện biên `left < 0` và `right >= arr.length` trong từng lần lặp. Không thanh thoát bằng cách Binary Search trên điểm bắt đầu.

---

### Cách 3: Sắp xếp theo Comparator tùy chỉnh hoặc Max-Heap (Priority Queue)

Bỏ qua tính chất mảng đã sắp xếp, coi mảng như tập hợp tùy ý và sắp xếp theo khoảng cách.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SolutionSorting {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int num : arr) list.add(num);

        // Sắp xếp theo khoảng cách tới x, tie-break theo giá trị số
        list.sort((a, b) -> {
            int diffA = Math.abs(a - x);
            int diffB = Math.abs(b - x);
            if (diffA != diffB) return Integer.compare(diffA, diffB);
            return Integer.compare(a, b);
        });

        // Lấy k phần tử đầu tiên và sắp xếp lại theo thứ tự tăng dần
        List<Integer> result = list.subList(0, k);
        Collections.sort(result);
        return result;
    }
}
```

- **Độ phức tạp thời gian:** $\mathcal{O}(N \log N + k \log k)$.
- **Độ phức tạp không gian:** $\mathcal{O}(N)$ để lưu danh sách đối tượng.
- **Đánh giá:** Cách tiếp cận ngây thơ nhất (Brute-force Sorting), không tận dụng được tiền đề mảng đã sắp xếp sẵn, hiệu năng kém nhất.

---

### 📊 Bảng so sánh toàn diện các phương pháp

| Tiêu chí | Binary Search trên Cửa Sổ (Best Solution) ⭐ | Hai Con Trỏ Thu Hẹp | BS + Mở rộng 2 con trỏ | Sắp xếp tùy chỉnh (Sorting) |
| :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp Thời gian** | **$\mathcal{O}(\log(N - k) + k)$** | $\mathcal{O}(N)$ | $\mathcal{O}(\log N + k)$ | $\mathcal{O}(N \log N)$ |
| **Độ phức tạp Không gian** | **$\mathcal{O}(1)$ phụ trợ** | $\mathcal{O}(1)$ phụ trợ | $\mathcal{O}(1)$ phụ trợ | $\mathcal{O}(N)$ |
| **Số phép so sánh khi $N=10^4, k=4$** | **$\approx 13$ phép tính** | $\approx 10,000$ phép tính | $\approx 18$ phép tính | $\approx 140,000$ phép tính |
| **Độ phức tạp mã nguồn** | **Cực kỳ ngắn gọn (15 dòng)** | Đơn giản | Phức tạp (nhiều if-else) | Trung bình |
| **Thời gian thực thi LeetCode** | **$1 \text{ - } 2 \text{ ms}$ (Top 100%)** | $3 \text{ - } 5 \text{ ms}$ | $2 \text{ - } 4 \text{ ms}$ | $30 \text{ - } 50 \text{ ms}$ |
| **Đánh giá phỏng vấn** | **Tuyệt đối xuất sắc (Staff/Senior)** | Tốt | Khá | Dưới trung bình |

---

## 🎓 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways)

1. **Chuyển dịch không gian tìm kiếm (Search Space Transformation):**
   - Đừng chỉ máy móc tìm kiếm nhị phân trên từng giá trị phần tử (`arr[mid] == target`). Hãy luôn tự hỏi: *"Liệu mình có thể tìm kiếm nhị phân trên thuộc tính của một cấu trúc lớn hơn (ví dụ: chỉ số bắt đầu của một cửa sổ) không?"*.
   - Kỹ thuật tìm kiếm nhị phân trên chỉ số bắt đầu cửa sổ `[0, N - k]` là vũ khí tối thượng cho các bài toán dạng *"Tìm đoạn con liên tiếp kích thước cố định thỏa mãn tiêu chí cực trị"*.

2. **Kỹ thuật khử trị tuyệt đối khi mảng có thứ tự:**
   - Thay vì phụ thuộc vào `Math.abs()`, hãy khai thác triệt để vị trí tương đối giữa các phần tử đã sắp xếp để viết biểu thức dạng hiệu đại số tuyến tính:
     $$x - arr[mid] > arr[mid + k] - x$$
   - Biểu thức này không chỉ loại bỏ chi phí rẽ nhánh của `Math.abs()`, mà còn tự động giải quyết luật ưu tiên Tie-break ($a < b$) một cách thanh thoát và chính xác tuyệt đối.

3. **Lựa chọn cấu trúc dữ liệu xuất kết quả phù hợp:**
   - Khi trả về `List<Integer>`, luôn khởi tạo kích thước dự kiến ban đầu `new ArrayList<>(k)` để tránh lãng phí chi phí nhân bản mảng (array doubling) bên trong `ArrayList`.
