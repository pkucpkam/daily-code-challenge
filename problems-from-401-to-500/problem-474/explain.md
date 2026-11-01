# 📚 Giải thích Chi tiết Bài 665 - Non-decreasing Array

**Mục tiêu:** Cho một mảng số nguyên `nums` gồm $n$ phần tử. Hãy kiểm tra xem ta có thể biến đổi mảng này thành **mảng không giảm (non-decreasing array)** bằng cách sửa đổi **nhiều nhất một phần tử** hay không. Một mảng được gọi là không giảm nếu $nums[i] \le nums[i + 1]$ thỏa mãn với mọi chỉ số $0 \le i \le n - 2$.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Mảng Không Giảm & Điểm Vi phạm (Inversion Point)

Trong một mảng không giảm, mỗi phần tử đứng sau luôn lớn hơn hoặc bằng phần tử đứng liền trước nó:
$$nums[0] \le nums[1] \le nums[2] \le \dots \le nums[n - 1]$$

Bất kỳ cặp chỉ số liền kề $(i, i + 1)$ nào có:
$$nums[i] > nums[i + 1]$$
đều bị coi là một **điểm vi phạm (violation point)** hay một **bước gãy giảm giá trị**.

Vì đề bài chỉ cho phép sửa đổi **tối đa 1 phần tử**:
1. Nếu xuất hiện từ $2$ điểm vi phạm trở lên trong quá trình duyệt $\implies$ Chắc chắn không có cách nào sửa $1$ phần tử mà cứu được cả $2$ vị trí gãy $\implies$ Kết luận ngay `false`!
2. Nếu không có bất kỳ điểm vi phạm nào ($0$ vi phạm) $\implies$ Mảng vốn dĩ đã không giảm $\implies$ Trả về `true`!
3. Nếu có **đúng 1 điểm vi phạm** tại vị trí $i$ ($nums[i] > nums[i + 1]$):
   - Ta bắt buộc phải sửa một trong hai phần tử $nums[i]$ hoặc $nums[i + 1]$.
   - Liệu sửa phần tử nào cũng được? **Hoàn toàn không!** Đây chính là cốt lõi và cái bẫy tinh vi nhất của bài toán.

---

### 2. Thách thức Cốt lõi: Lựa chọn Tham lam (Greedy Choice Dilemma)

Khi phát hiện $nums[i] > nums[i + 1]$, ta đứng trước đúng 2 phương án:

```text
       Trực quan hóa điểm gãy:
       
          nums[i] (Cao)
            ▲
            │ \
            │  \
            │   ▼ nums[i+1] (Thấp)
```

#### Phương án 1: Hạ thấp phần tử trước đó xuống $\implies nums[i] = nums[i + 1]$
- **Ý nghĩa:** Ta giảm giá trị của phần tử cao $nums[i]$ xuống bằng $nums[i + 1]$.
- **Lợi ích dài hạn:** Trong mảng không giảm, một phần tử càng nhỏ thì càng an toàn cho các phần tử phía sau ($nums[i + 1] \le nums[i + 2]$). Vì vậy, giữ $nums[i + 1]$ ở mức thấp luôn là **lựa chọn ưu tiên số 1**!
- **Điều kiện để thực hiện được:** 
  - Nếu $i == 0$: Nút đầu tiên không có phần tử đứng trước nó, nên hạ $nums[0] = nums[1]$ luôn luôn hợp lệ!
  - Nếu $i > 0$: Ta phải đảm bảo rằng sau khi hạ $nums[i]$, nó không được nhỏ hơn phần tử đứng trước nó:
    $$nums[i - 1] \le nums[i + 1]$$
  - Nếu thỏa mãn điều kiện này, hạ $nums[i]$ là lựa chọn tối ưu nhất!

#### Phương án 2: Nâng cao phần tử phía sau lên $\implies nums[i + 1] = nums[i]$
- **Khi nào bắt buộc phải dùng phương án này?**
  - Khi $i > 0$ và $nums[i - 1] > nums[i + 1]$.
  - Lúc này, nếu ta hạ $nums[i]$ xuống bằng $nums[i + 1]$, ta sẽ vô tình tạo ra vi phạm mới với phần tử phía trước: $nums[i - 1] > nums[i]$.
  - Do đó, ta **không còn lựa chọn nào khác** ngoài việc phải nâng phần tử $nums[i + 1]$ lên bằng $nums[i]$!
- **Hệ quả:** Vì $nums[i + 1]$ bị nâng lên cao, nó sẽ tạo áp lực lớn hơn cho các phần tử tiếp theo ($nums[i + 2], nums[i + 3], \dots$). Nếu các phần tử sau này không đủ lớn, mảng sẽ tiếp tục vi phạm và bị loại.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Thử Sửa Từng Vị Trí (Brute-force) | Cách 2: Tham Lam Duyệt Một Lượt (Single-pass Greedy ⭐) |
| :--- | :--- | :--- |
| **Ý tưởng** | Thử sửa từng phần tử tại điểm vi phạm, sau đó duyệt lại toàn bộ mảng để kiểm tra | Quyết định sửa ngay tại thời điểm gặp vi phạm dựa trên mối quan hệ với $nums[i - 1]$ |
| **Độ phức tạp Thời gian** | $\mathcal{O}(n)$ hoặc $\mathcal{O}(n^2)$ | $\mathcal{O}(n)$ - Duyệt đúng 1 vòng lặp duy nhất |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(n)$ nếu sao chép mảng | $\mathcal{O}(1)$ - Không tốn bộ nhớ phụ |
| **Đánh giá** | Dài dòng, tốn tài nguyên | Ngắn gọn, thanh lịch, đạt hiệu năng tuyệt đối $0 \text{ ms}$ |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Tham Lam Duyệt Một Lượt (Single-Pass Greedy)

### 💡 Quy tắc ra quyết định (Decision Matrix)

Khi duyệt $i$ từ $0$ đến $n - 2$:
Nếu gặp $nums[i] > nums[i + 1]$:
1. Tăng biến đếm `count++`.
2. Nếu `count > 1` $\implies$ Trả về `false` ngay lập tức.
3. Xét điều kiện để lựa chọn chiến lược sửa đổi:
   - **Nếu $i == 0$ hoặc $nums[i - 1] \le nums[i + 1]$:**
     $$\text{Gán } nums[i] = nums[i + 1]$$
   - **Ngược lại ($nums[i - 1] > nums[i + 1]$):**
     $$\text{Gán } nums[i + 1] = nums[i]$$

```text
Trường hợp 1: nums[i - 1] <= nums[i + 1]          Trường hợp 2: nums[i - 1] > nums[i + 1]
Ví dụ: [2, 4, 3, 5]                                Ví dụ: [3, 4, 2, 5]
i = 1: nums[1]=4 > nums[2]=3                       i = 1: nums[1]=4 > nums[2]=2
nums[i-1] = 2 <= nums[i+1] = 3                     nums[i-1] = 3 > nums[i+1] = 2

      4 (nums[i])                                        4 (nums[i])
     / \                                                / \
    /   3 (nums[i+1])                                  /   \
   2                                                  3     2 (nums[i+1])
  (nums[i-1])                                        (nums[i-1])

-> Hạ nums[i]: 4 biến thành 3                      -> Nâng nums[i+1]: 2 biến thành 4
Mảng mới: [2, 3, 3, 5] (HỢP LỆ)                    Mảng mới: [3, 4, 4, 5] (HỢP LỆ)
```

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    /**
     * Thuật toán Tối ưu: Tham lam một lượt (Single-pass Greedy)
     * 
     * Độ phức tạp:
     * - Thời gian: O(n) - Duyệt qua mảng đúng 1 lần duy nhất.
     * - Không gian: O(1) - Sử dụng đúng 1 biến đếm vi phạm, không tốn thêm bộ nhớ phụ.
     */
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                count++;
                // Nếu cần sửa nhiều hơn 1 phần tử -> Không thể thỏa mãn
                if (count > 1) {
                    return false;
                }

                // Quyết định chiến lược sửa đổi tham lam:
                if (i == 0 || nums[i - 1] <= nums[i + 1]) {
                    // Lựa chọn ưu tiên: Hạ nums[i] xuống bằng nums[i + 1]
                    nums[i] = nums[i + 1];
                } else {
                    // Bắt buộc: Nâng nums[i + 1] lên bằng nums[i]
                    nums[i + 1] = nums[i];
                }
            }
        }

        return true;
    }
}
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Ví dụ 1: `nums = [4, 2, 3]`

- $i = 0$: $nums[0] = 4 > nums[1] = 2$.
  - Phát hiện vi phạm: `count = 1`.
  - Vì $i == 0 \implies$ Hạ $nums[0] = nums[1] = 2$.
  - Mảng trở thành: `[2, 2, 3]`.
- $i = 1$: $nums[1] = 2 \le nums[2] = 3$ (Hợp lệ).
- Kết thúc vòng lặp. `count = 1 \le 1` $\implies$ **`true`**.

---

### Ví dụ 2: `nums = [4, 2, 1]`

- $i = 0$: $nums[0] = 4 > nums[1] = 2$.
  - Phát hiện vi phạm lần 1: `count = 1`.
  - Vì $i == 0 \implies$ Hạ $nums[0] = nums[1] = 2$.
  - Mảng trở thành: `[2, 2, 1]`.
- $i = 1$: $nums[1] = 2 > nums[2] = 1$.
  - Phát hiện vi phạm lần 2: `count = 2`.
  - `count > 1` $\implies$ Trả về **`false`** ngay lập tức.

---

### Ví dụ 3 (Cạm bẫy kinh điển): `nums = [3, 4, 2, 3]`

- $i = 0$: $nums[0] = 3 \le nums[1] = 4$ (Hợp lệ).
- $i = 1$: $nums[1] = 4 > nums[2] = 2$.
  - Phát hiện vi phạm lần 1: `count = 1`.
  - So sánh: $nums[i - 1] = nums[0] = 3 > nums[i + 1] = nums[2] = 2$.
  - Vì $3 > 2$, ta **không thể hạ** $4$ xuống $2$ (nếu hạ mảng sẽ thành `[3, 2, ...]`, bị gãy tại vị trí trước).
  - Bắt buộc nâng $nums[2] = nums[1] = 4$.
  - Mảng trở thành: `[3, 4, 4, 3]`.
- $i = 2$: $nums[2] = 4 > nums[3] = 3$.
  - Phát hiện vi phạm lần 2: `count = 2 > 1` $\implies$ Trả về **`false`**!
- *Giải thích thực tế:* Với `[3, 4, 2, 3]`:
  - Sửa $4 \to 2$: `[3, 2, 2, 3]` (Sai vì $3 > 2$).
  - Sửa $2 \to 4$: `[3, 4, 4, 3]` (Sai vì $4 > 3$).
  - Không có bất kỳ cách sửa 1 phần tử nào thành công!

---

### Ví dụ 4: `nums = [5, 7, 1, 8]`

- $i = 0$: $5 \le 7$ (Hợp lệ).
- $i = 1$: $nums[1] = 7 > nums[2] = 1$.
  - `count = 1`.
  - $nums[0] = 5 > nums[2] = 1 \implies$ Nâng $nums[2] = nums[1] = 7$.
  - Mảng trở thành: `[5, 7, 7, 8]`.
- $i = 2$: $nums[2] = 7 \le nums[3] = 8$ (Hợp lệ).
- Kết thúc: Trả về **`true`**!

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(n)$
- Vòng lặp duyệt qua mảng đúng một lần từ chỉ số $0$ đến $n - 2$.
- Tại mỗi chỉ số, các phép so sánh và gán giá trị đều diễn ra trong thời gian hằng số $\mathcal{O}(1)$.
- Có cơ chế cắt tỉa sớm (early-return) ngay khi `count > 1`.
- Do đó, tổng thời gian thực thi là tuyến tính $\mathcal{O}(n)$. Với $n \le 10^4$, thuật toán chạy trong chưa tới **$1 \text{ ms}$**!

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(1)$
- Thuật toán thực hiện sửa đổi trực tiếp tại chỗ (in-place) trên mảng `nums`.
- Chỉ sử dụng thêm đúng một biến nguyên `count` để đếm số lần vi phạm.
- Không cấp phát thêm bất kỳ mảng hoặc cấu trúc dữ liệu phụ nào $\implies \mathcal{O}(1)$ bộ nhớ.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Nguyên lý Tham lam Cục bộ nhưng Bảo vệ Toàn cục
- Khi được phép sửa một trong hai phần tử $(A, B)$ để giải quyết nghịch thế $A > B$:
  - Luôn muốn $B$ càng nhỏ càng tốt để có lợi cho tương lai.
  - Nhưng phải kiểm tra xem giảm $A$ xuống $B$ có làm tổn hại tới quá khứ (phần tử đứng trước $A$) hay không.
  - Đó chính là bản chất của phép kiểm tra $nums[i - 1] \le nums[i + 1]$.

### 2. Tránh bẫy "Chỉ đếm số cặp nghịch thế"
- Rất nhiều ứng viên mắc sai lầm sơ đẳng khi phỏng vấn: chỉ đếm số lần $nums[i] > nums[i + 1]$ và kết luận `true` nếu `count <= 1`.
- Hãy đưa ra phản ví dụ `[3, 4, 2, 3]` (chỉ có đúng một cặp nghịch thế $4 > 2$ tại $i = 1$, nhưng hoàn toàn không thể sửa được bằng 1 phép đổi) để chứng minh cho người phỏng vấn thấy tầm nhìn sâu sắc của bạn về bài toán!
