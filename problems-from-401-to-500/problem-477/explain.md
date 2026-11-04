# 📚 Giải thích Chi tiết Bài 670 - Maximum Swap

**Mục tiêu:** Cho một số nguyên không âm `num`. Bạn được phép hoán đổi vị trí của tối đa hai chữ số đúng một lần để thu được số có giá trị lớn nhất có thể. Trả về số lớn nhất sau khi đã thực hiện phép hoán đổi tối ưu (hoặc giữ nguyên nếu không thể làm số lớn hơn).

---

## 🎯 Phân tích bài toán

### 1. Ý nghĩa Vị trí và Trọng số Chữ số (Place-Value Significance)

Một số nguyên dương được biểu diễn theo hệ thập phân với các hàng:
$$\text{num} = d_{N-1} \cdot 10^{N-1} + d_{N-2} \cdot 10^{N-2} + \dots + d_1 \cdot 10^1 + d_0 \cdot 10^0$$

Trong đó:
- Chữ số bên trái nhất ($d_{N-1}$) có trọng số lớn nhất (hàng cao nhất).
- Chữ số bên phải nhất ($d_0$) có trọng số nhỏ nhất (hàng đơn vị).
- Khi ta hoán đổi chữ số ở vị trí $i$ với chữ số ở vị trí $j$ ($i < j$), độ thay đổi giá trị của số là:
  $$\Delta = (digits[j] - digits[i]) \cdot (10^{\text{pos}(i)} - 10^{\text{pos}(j)})$$
- Để số mới lớn hơn số cũ ($\Delta > 0$), ta bắt buộc phải đưa một chữ số **lớn hơn** về phía trước (ở vị trí có trọng số cao hơn) và đưa chữ số **nhỏ hơn** lùi về phía sau.
- Càng đưa chữ số lớn lên hàng cao bên trái bao nhiêu, giá trị số thu được càng tăng vọt bấy nhiêu.

---

### 2. Chiến lược Tham Lam (Greedy Choice Property)

Để tạo ra số có giá trị lớn nhất chỉ với **tối đa một lần hoán đổi**:
1. **Ưu tiên vị trí bên trái:** Ta cần quét từ trái sang phải để tìm vị trí $i$ đầu tiên có thể thay thế bằng một chữ số lớn hơn.
2. **Ưu tiên chữ số lớn nhất có thể:** Nếu ở phía sau vị trí $i$ có nhiều chữ số lớn hơn $digits[i]$, ta phải chọn chữ số **lớn nhất** (ví dụ: phía sau có cả `7` và `9`, ta phải ưu tiên đổi với `9`).
3. **Ưu tiên vị trí xuất hiện cuối cùng (Rightmost Position):**
   - Giả sử chữ số lớn nhất đó xuất hiện ở nhiều vị trí phía sau (ví dụ: `num = 1993`). Ta có hai chữ số `9` ở chỉ số $1$ và $2$.
   - Nếu đổi với số `9` đầu tiên: ta được `9193`.
   - Nếu đổi với số `9` cuối cùng: ta được `9913`.
   - Rõ ràng $9913 > 9193$.
   - **Lý do:** Khi hoán đổi, chữ số nhỏ hơn ($1$) sẽ bị đẩy về vị trí cũ của chữ số lớn. Đẩy chữ số nhỏ về vị trí càng thấp (càng về bên phải) thì "tổn thất" giá trị càng nhỏ! Do đó, ta luôn phải chọn vị trí xuất hiện **cuối cùng** của chữ số lớn nhất.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)

- $0 \le num \le 10^8$.
- Độ dài chữ số: $N \le 9$ (tối đa 9 chữ số).
- Với $N \le 9$, không gian bài toán cực kỳ nhỏ gọn:
  - Một thuật toán duyệt $\mathcal{O}(N)$ chỉ mất tối đa $9 \times 10 = 90$ thao tác cơ bản.
  - Bộ nhớ chỉ tốn mảng kích thước cố định $10$ phần tử.
  - Thời gian thực thi chắc chắn đạt tuyệt đối **$0 \text{ ms}$ (Top 100%)**.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Vét cạn mọi cặp hoán đổi (Brute Force) | Cách 2: Tham lam với Bảng vị trí cuối cùng (Greedy Bucket ⭐) | Cách 3: Duyệt một lượt Suffix Max (Single-pass Right-to-Left) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Thử đổi mọi cặp $(i, j)$ với $i < j$, tìm số lớn nhất | Lưu chỉ số cuối cùng của mỗi chữ số $0..9$. Quét trái sang phải, tìm số lớn nhất ở sau để đổi | Quét từ phải qua trái, duy trì chữ số cực đại hậu tố và lưu cặp hoán đổi tối ưu |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ hoặc $\mathcal{O}(N^3)$ | $\mathcal{O}(10 \times N) = \mathcal{O}(N)$ | $\mathcal{O}(N)$ - Đúng 1 vòng lặp |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ mảng ký tự | $\mathcal{O}(N)$ mảng ký tự + $\mathcal{O}(1)$ mảng tần số | $\mathcal{O}(N)$ mảng ký tự |
| **Đánh giá** | Dễ nghĩ nhưng lãng phí phép thử | Trực quan, dễ hiểu, kinh điển trong phỏng vấn | Khéo léo, tối ưu thao tác lặp |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Tham Lam Dựa Trên Vị Trí Xuất Hiện Cuối Cùng (Greedy with Last Occurrence Bucket)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

1. Chuyển số `num` thành mảng ký tự `digits` để tiện thao tác hoán đổi.
2. Dùng mảng `last` kích thước 10 để lưu **vị trí xuất hiện cuối cùng** của mỗi chữ số từ $0$ đến $9$:
   $$last[d] = \text{chỉ số lớn nhất } j \text{ mà } digits[j] == d$$
3. Duyệt từng vị trí $i$ từ trái qua phải ($0 \to N - 1$):
   - Ta tìm kiếm một chữ số $d$ trong khoảng $[9, digits[i] + 1]$ (giảm dần từ $9$ xuống để đảm bảo tìm số lớn nhất trước).
   - Nếu tồn tại $d$ sao cho $last[d] > i$ (nghĩa là chữ số $d$ lớn hơn chữ số tại $i$ và xuất hiện ở phía sau $i$):
     - Ta lập tức thực hiện hoán đổi `digits[i]` và `digits[last[d]]`.
     - Chuyển `digits` ngược lại thành số nguyên và trả về kết quả ngay (vì đây chắc chắn là phép hoán đổi tối ưu nhất cho hàng cao nhất có thể cải thiện).
4. Nếu duyệt hết toàn bộ mảng mà không thực hiện phép đổi nào, nghĩa là các chữ số đã xếp theo thứ tự giảm dần (hoặc không thể tối ưu hơn), trả về `num` ban đầu.

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `num = 2736`

```text
Mảng chữ số:   ['2', '7', '3', '6']
Chỉ số (index):  0    1    2    3

Bảng vị trí xuất hiện cuối cùng (last):
Chữ số: 0  1  2  3  4  5  6  7  8  9
Vị trí: -  -  0  2  -  -  3  1  -  -

Quá trình quét:
- Tại i = 0 (chữ số '2'):
  Kiểm tra các chữ số d từ 9 giảm dần về 3:
  + d = 9: không tồn tại
  + d = 8: không tồn tại
  + d = 7: last[7] = 1 > 0  ===> TÌM THẤY!
  
  Hoán đổi digits[0] và digits[1]:
  ['2', '7', '3', '6']  -->  ['7', '2', '3', '6']
  
Kết quả: 7236
```

---

#### Ví dụ 2: `num = 1993` (Trường hợp trùng chữ số lớn nhất)

```text
Mảng chữ số:   ['1', '9', '9', '3']
Chỉ số (index):  0    1    2    3

Bảng last:
last[1] = 0, last[3] = 3, last[9] = 2  (chỉ số của số 9 cuối cùng!)

Quá trình quét:
- Tại i = 0 (chữ số '1'):
  Kiểm tra d = 9:
  last[9] = 2 > 0  ===> Đổi vị trí 0 với vị trí 2!
  
  ['1', '9', '9', '3']  -->  ['9', '9', '1', '3'] = 9913 (TỐI ƯU NHẤT)
  
  *Ghi chú: Nếu đổi với số 9 ở vị trí 1, kết quả là 9193 < 9913!
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Ví dụ 1: `num = 9973` (Số đã tối ưu)

1. Mảng: `['9', '9', '7', '3']`.
2. Bảng `last`: `last[9] = 1`, `last[7] = 2`, `last[3] = 3`.
3. Quét từng vị trí:
   - $i = 0$ (`'9'`): không có chữ số nào $> 9$.
   - $i = 1$ (`'9'`): không có chữ số nào $> 9$.
   - $i = 2$ (`'7'`): kiểm tra $d = 9$: $last[9] = 1 < 2$ (số $9$ nằm ở phía trước, không thể đổi về sau).
   - $i = 3$ (`'3'`): không còn vị trí nào phía sau.
4. Không có hoán đổi nào $\implies$ Trả về `9973`.

---

### Ví dụ 2: `num = 98368`

1. Mảng: `['9', '8', '3', '6', '8']`.
2. Bảng `last`: `last[9] = 0`, `last[8] = 4`, `last[6] = 3`, `last[3] = 2`.
3. Quét từng vị trí:
   - $i = 0$ (`'9'`): không có chữ số $> 9$.
   - $i = 1$ (`'8'`): kiểm tra $d = 9$: $last[9] = 0 < 1$.
   - $i = 2$ (`'3'`): kiểm tra $d = 9$ ($last[9] = 0 < 2$), $d = 8$ ($last[8] = 4 > 2$):
     - Thỏa mãn: $last[8] = 4 > 2$.
     - Đổi `digits[2]` với `digits[4]`.
     - Số trở thành: `98863`.
4. Trả về `98863`.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### 1. Giải pháp Tham Lam với Bảng Vị Trí Cuối (Greedy Bucket - Khuyên dùng)

```java
class Solution {
    /**
     * Thuật toán: Greedy với Bảng vị trí xuất hiện cuối cùng
     * 
     * Độ phức tạp:
     * - Thời gian: O(N) với N là số chữ số (N <= 9), tối đa 90 thao tác.
     * - Không gian: O(N) để lưu mảng ký tự và O(1) bảng phụ trợ 10 phần tử.
     */
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] last = new int[10];

        // Bước 1: Ghi nhận vị trí xuất hiện cuối cùng của từng chữ số (0 - 9)
        for (int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }

        // Bước 2: Quét từ trái sang phải để tìm vị trí đầu tiên có thể cải thiện
        for (int i = 0; i < digits.length; i++) {
            // Thử các chữ số lớn hơn chữ số hiện tại, ưu tiên từ 9 giảm dần
            for (int d = 9; d > digits[i] - '0'; d--) {
                // Nếu chữ số d tồn tại và nằm ở phía sau vị trí i
                if (last[d] > i) {
                    // Hoán đổi vị trí i với vị trí xuất hiện cuối cùng của chữ số d
                    char temp = digits[i];
                    digits[i] = digits[last[d]];
                    digits[last[d]] = temp;

                    // Trả về số nguyên kết quả ngay lập tức
                    return Integer.parseInt(new String(digits));
                }
            }
        }

        // Không có phép hoán đổi nào làm số lớn hơn
        return num;
    }
}
```

---

### 2. Biến thể Duyệt Một Lượt từ Phải sang Trái (Single-pass Suffix Max)

> [!TIP]
> **Câu hỏi mở rộng trong phỏng vấn:** *"Bạn có thể giải bài này chỉ với đúng một lượt duyệt không cần lặp qua 10 chữ số tại mỗi bước không?"*
> 
> Bằng cách duyệt từ phải qua trái, ta luôn duy trì vị trí của chữ số lớn nhất tính đến thời điểm hiện tại (`maxIdx`). Khi gặp một chữ số nhỏ hơn chữ số lớn nhất phía sau nó, ta ghi nhận đây là một ứng viên hoán đổi tiềm năng. Vì duyệt từ phải qua trái nên ứng viên cuối cùng được cập nhật sẽ chính là vị trí ngoài cùng bên trái!

```java
class SolutionOnePass {
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int n = digits.length;

        int maxIdx = n - 1;
        int swap1 = -1;
        int swap2 = -1;

        // Duyệt từ phải sang trái
        for (int i = n - 2; i >= 0; i--) {
            if (digits[i] > digits[maxIdx]) {
                // Cập nhật vị trí của chữ số lớn nhất mới
                maxIdx = i;
            } else if (digits[i] < digits[maxIdx]) {
                // Ghi nhận cặp hoán đổi tiềm năng:
                // swap1 càng dịch về bên trái (khi i giảm dần) càng tối ưu
                swap1 = i;
                swap2 = maxIdx;
            }
            // Nếu digits[i] == digits[maxIdx]: giữ nguyên maxIdx để giữ lại vị trí ngoài cùng bên phải
        }

        // Nếu tìm thấy cặp cần hoán đổi
        if (swap1 != -1) {
            char temp = digits[swap1];
            digits[swap1] = digits[swap2];
            digits[swap2] = temp;
            return Integer.parseInt(new String(digits));
        }

        return num;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. `char[] digits = Integer.toString(num).toCharArray();`
   - Chuyển số nguyên thành mảng ký tự để có thể truy xuất và hoán đổi trực tiếp theo chỉ số trong thời gian $\mathcal{O}(1)$.
2. `int[] last = new int[10];`
   - Tạo mảng kích thước cố định $10$ tương ứng với $10$ chữ số từ `'0'` đến `'9'`.
3. `last[digits[i] - '0'] = i;`
   - Khi duyệt từ trái qua phải, nếu một chữ số xuất hiện nhiều lần thì giá trị ghi đè sau cùng sẽ chính là vị trí xuất hiện cuối cùng (rightmost index).
4. `for (int d = 9; d > digits[i] - '0'; d--)`
   - Tại vị trí $i$, ta tìm kiếm tham lam: ưu tiên số $9$ trước, rồi đến $8, 7, \dots$ miễn là lớn hơn chữ số hiện tại `digits[i]`.
5. `if (last[d] > i)`
   - Kiểm tra xem chữ số lớn hơn đó có nằm ở bên phải của $i$ hay không. Nếu có, đây chính là cặp hoán đổi tối ưu nhất.
6. `return Integer.parseInt(new String(digits));`
   - Ngay sau khi thực hiện hoán đổi duy nhất này, số thu được chắc chắn là cực đại toàn cục, trả về kết quả ngay mà không cần xét tiếp.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(N)$
- Gọi $N$ là số lượng chữ số của `num`. Vì $num \le 10^8$, $N \le 9$.
- Vòng lặp 1: Ghi nhận vị trí cuối cùng tốn $\mathcal{O}(N)$ bước.
- Vòng lặp 2: Quét qua tối đa $N$ chữ số. Tại mỗi chữ số kiểm tra tối đa $10$ giá trị từ $9$ về $0$. Số phép so sánh tối đa là $9 \times 10 = 90$ thao tác.
- Chuyển đổi chuỗi thành số nguyên tốn $\mathcal{O}(N)$.
- Tổng thời gian: $\mathcal{O}(N)$, tương đương với thời gian hằng số $\mathcal{O}(1)$ trên thực tế, chạy trong **$0 \text{ ms}$**.

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(N)$ hoặc $\mathcal{O}(1)$
- Mảng ký tự `digits` lưu $N$ chữ số: $\mathcal{O}(N) \le 9 \text{ chars}$.
- Mảng `last` có độ dài cố định $10$: $\mathcal{O}(1)$ auxiliary space.
- Không phát sinh cấu trúc dữ liệu đệ quy hay cấu trúc động nào khác.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Nguyên lý Tham lam trong Bài toán Biểu diễn Số
- Khi bài toán yêu cầu tối đa hóa một số thông qua số lần hoán đổi giới hạn, hãy luôn nghĩ đến **hàng có trọng số cao nhất (trái nhất)** trước tiên.
- Bất kỳ sự gia tăng nào ở hàng cao hơn đều áp đảo mọi sự suy giảm ở các hàng thấp hơn (ví dụ: tăng 1 đơn vị ở hàng nghìn $+1000$ vượt xa việc mất 9 đơn vị ở hàng trăm $-900$).

### 2. Bẫy Chữ số Trùng lặp (Duplicate Maximum Digits)
- Rất nhiều ứng viên quên mất trường hợp có nhiều chữ số lớn nhất bằng nhau (như `num = 1993` hoặc `num = 98368`).
- Hãy luôn nhớ: Hoán đổi với chữ số lớn nhất **xuất hiện sau cùng (rightmost)** để đẩy chữ số nhỏ hơn về vị trí có trọng số nhỏ nhất có thể.
- Mảng `last[d]` tự động lưu lại vị trí xuất hiện cuối cùng một cách tự nhiên mà không cần viết thêm điều kiện phức tạp.
