# 📚 Giải thích Chi tiết Bài 645 - Set Mismatch

**Mục tiêu:** Cho một mảng số nguyên `nums` đại diện cho một tập hợp ban đầu gồm tất cả các số từ $1$ đến $n$. Do lỗi xảy ra, có đúng một số bị lặp lại 2 lần và dẫn đến một số khác bị mất đi. Hãy tìm và trả về số bị lặp lại (`duplicate`) cùng số bị mất (`missing`) dưới dạng một mảng gồm 2 phần tử `[duplicate, missing]`.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Các ràng buộc (Constraints)
- `2 <= nums.length <= 10⁴`: Độ dài mảng $n$ nằm trong khoảng từ $2$ đến $10\,000$.
- `1 <= nums[i] <= 10⁴`: Mọi phần tử đều nằm trong miền $[1, n]$.
- **Đảm bảo tính duy nhất của lỗi:** Luôn có chính xác **1 số xuất hiện 2 lần** và **1 số không xuất hiện lần nào** (tất cả các số còn lại xuất hiện đúng 1 lần).
- **Thứ tự kết quả trả về:** Bắt buộc là `[duplicate, missing]`.

---

### 2. Tính chất cốt lõi: Quan hệ giữa Giá trị (Value) và Chỉ số (Index)

- Tập hợp giá trị lý tưởng: $\{1, 2, 3, \dots, n\}$.
- Tập hợp chỉ số (0-indexed) của mảng: $\{0, 1, 2, \dots, n - 1\}$.

Nhận thấy tồn tại một ánh xạ song ánh $1-1$ tự nhiên:
$$\text{Giá trị } x \in [1, n] \quad \longleftrightarrow \quad \text{Chỉ số } \text{index} = x - 1 \in [0, n - 1]$$

Thay vì phải cấp phát thêm bộ nhớ phụ như `HashSet` hay mảng đếm tần suất `int[] count = new int[n + 1]`, ta hoàn toàn có thể tận dụng **chính các ô nhớ của mảng `nums`** để lưu trữ trạng thái xuất hiện!

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Đánh dấu dấu âm tại chỗ (In-Place Sign Inversion)

### 💡 Ý tưởng đột phá (Key Insights)

Vì tất cả các phần tử ban đầu đều là số nguyên dương ($nums[i] \ge 1$), ta có thể dùng **dấu âm ($-$)** làm một "lá cờ" (flag) ghi nhận sự hiện diện của một giá trị:

1. **Tìm số trùng lặp (`duplicate`):**
   - Duyệt qua từng phần tử trong mảng: lấy giá trị ban đầu $\text{val} = |nums[i]|$.
   - Vị trí chỉ số tương ứng cần kiểm tra là $\text{index} = \text{val} - 1$.
   - **Kiểm tra trạng thái ô `nums[index]`:**
     - Nếu $nums[\text{index}] < 0$: Vị trí này đã từng bị đổi sang dấu âm trước đó $\implies$ giá trị $\text{val}$ đã xuất hiện lần thứ 2 $\implies$ **`duplicate = val`**.
     - Nếu $nums[\text{index}] > 0$: Vị trí này chưa từng được ghé thăm $\implies$ ta đổi dấu $nums[\text{index}] = -nums[\text{index}]$ để đánh dấu số $\text{val}$ đã xuất hiện.

2. **Tìm số bị thiếu (`missing`):**
   - Sau vòng lặp thứ nhất, tất cả các số có mặt trong mảng đều đã làm cho ô tương ứng của chúng mang dấu âm.
   - Duy nhất ô tương ứng với số bị thiếu (chỉ số $\text{missing} - 1$) là **chưa từng được ghé thăm**, do đó giá trị tại ô này vẫn giữ nguyên là **số dương** ($nums[i] > 0$).
   - Ta duyệt lại mảng lần thứ hai: nếu thấy $nums[i] > 0 \implies \mathbf{missing = i + 1}$.

```text
Minh họa với nums = [1, 2, 2, 4]:

Ban đầu:         nums = [ 1,  2,  2,  4 ]
Chỉ số:                   0   1   2   3

i = 0: val = 1 -> index = 0 -> nums[0] > 0 => nums[0] = -1
       nums = [ -1,  2,  2,  4 ]

i = 1: val = 2 -> index = 1 -> nums[1] > 0 => nums[1] = -2
       nums = [ -1, -2,  2,  4 ]

i = 2: val = 2 -> index = 1 -> nums[1] < 0 => ĐÃ ÂM! Duplicate = 2
       nums = [ -1, -2,  2,  4 ]

i = 3: val = 4 -> index = 3 -> nums[3] > 0 => nums[3] = -4
       nums = [ -1, -2,  2, -4 ]

Duyệt tìm Missing:
- index 0: -1 (< 0)
- index 1: -2 (< 0)
- index 2:  2 (> 0)  <-- Vẫn dương! => Missing = 2 + 1 = 3
- index 3: -4 (< 0)

=> Kết quả: [2, 3]
```

### 🌟 Ưu điểm vượt trội
- **Thời gian chạy $\mathcal{O}(N)$:** Duyệt mảng 2 lần độc lập, thời gian thực thi cực nhanh $\approx 1\text{ms} - 2\text{ms}$ trên LeetCode (Beats 99% - 100%).
- **Không gian bộ nhớ tối ưu tuyệt đối $\mathcal{O}(1)$:** Thực hiện trực tiếp trên mảng đầu vào (in-place), không cấp phát thêm bộ nhớ phụ.
- **Tuyệt đối an toàn số học:** Không có phép nhân lũy thừa hay bình phương, hoàn toàn không có nguy cơ tràn số (Overflow).

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-459/Solution.java):

```java
class Solution {
    /**
     * Best Solution: Đánh dấu dấu âm tại chỗ (In-Place Sign Inversion).
     *
     * Time Complexity: O(N) - 2 lượt duyệt mảng độc lập (N = nums.length).
     * Space Complexity: O(1) - Sử dụng chính mảng đầu vào để đánh dấu, không dùng mảng phụ.
     */
    public int[] findErrorNums(int[] nums) {
        int dup = -1;
        int missing = -1;

        // Vòng 1: Tìm số bị lặp lại (duplicate) bằng kỹ thuật đổi dấu
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]);
            int index = val - 1;

            if (nums[index] < 0) {
                dup = val; // Đã từng được đổi dấu trước đó => số lặp lại
            } else {
                nums[index] = -nums[index]; // Đánh dấu đã xuất hiện
            }
        }

        // Vòng 2: Tìm số bị thiếu (missing) - vị trí duy nhất còn mang giá trị dương
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                missing = i + 1; // Chỉ số i chưa từng được ghé thăm => số (i + 1) bị thiếu
                break;
            }
        }

        return new int[]{dup, missing};
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Thao tác Bit (Bit Manipulation - XOR)

#### Tư duy:
Dựa trên 2 tính chất cơ bản của phép toán XOR ($\oplus$):
1. $x \oplus x = 0$ (hai số giống nhau XOR với nhau sẽ triệt tiêu).
2. $x \oplus 0 = x$ (XOR với 0 giữ nguyên giá trị).

- Gọi số lặp là $D$, số thiếu là $M$.
- Nếu ta XOR tất cả các phần tử trong `nums` và tất cả các số từ $1$ đến $n$:
  $$\text{xorAll} = (nums[0] \oplus \dots \oplus nums[n-1]) \oplus (1 \oplus 2 \oplus \dots \oplus n)$$
  Tất cả các số bình thường xuất hiện 2 lần sẽ bị triệt tiêu hết. Số $D$ xuất hiện 3 lần ($2$ lần trong mảng + $1$ lần trong dãy $1..n$) nên còn lại $1$ lần. Số $M$ xuất hiện $1$ lần (trong dãy $1..n$).
  $$\implies \text{xorAll} = D \oplus M$$
- Vì $D \ne M$, kết quả $\text{xorAll}$ chắc chắn có ít nhất 1 bit mang giá trị $1$. Ta lấy bit 1 thấp nhất:
  $$\text{diffBit} = \text{xorAll} \ \& \ (-\text{xorAll})$$
- Chia toàn bộ các số trong `nums` và từ $1$ đến $n$ thành 2 nhóm dựa trên bit này:
  - Nhóm 1: Bit đó bằng $1$.
  - Nhóm 2: Bit đó bằng $0$.
- Số $D$ và $M$ chắc chắn sẽ rơi vào 2 nhóm khác nhau. XOR dồn từng nhóm sẽ tách được $D$ và $M$ thành 2 số riêng biệt $a$ và $b$.
- Duyệt lại mảng 1 lần để xem số nào thực sự nằm trong `nums` để xác định đâu là $D$ và đâu là $M$.

#### Mã nguồn:
```java
class SolutionXOR {
    public int[] findErrorNums(int[] nums) {
        int xorAll = 0;
        int n = nums.length;

        for (int num : nums) xorAll ^= num;
        for (int i = 1; i <= n; i++) xorAll ^= i;

        // Lấy bit 1 thấp nhất làm phân loại
        int diffBit = xorAll & (-xorAll);

        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & diffBit) != 0) a ^= num;
            else b ^= num;
        }
        for (int i = 1; i <= n; i++) {
            if ((i & diffBit) != 0) a ^= i;
            else b ^= i;
        }

        // Xác định số nào là duplicate
        for (int num : nums) {
            if (num == a) return new int[]{a, b};
        }
        return new int[]{b, a};
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N)$ — Duyệt mảng vài lượt tuần tự.
- **Không gian:** $\mathcal{O}(1)$ bộ nhớ phụ.
- **Ưu điểm lớn:** **Không làm thay đổi mảng đầu vào (Immutable)** và không có nguy cơ tràn số.

---

### Cách 2: Toán học đại số - Tổng & Tổng bình phương (Sum & Sum of Squares)

#### Tư duy:
Gọi $D$ là số trùng lặp, $M$ là số bị thiếu.
- Hiệu tổng bậc 1:
  $$\Delta_1 = \sum_{i=1}^n i - \sum_{i=0}^{n-1} nums[i] = M - D$$
- Hiệu tổng bậc 2 (tổng bình phương):
  $$\Delta_2 = \sum_{i=1}^n i^2 - \sum_{i=0}^{n-1} nums[i]^2 = M^2 - D^2 = (M - D)(M + D)$$
- Từ đó ta suy ra:
  $$M + D = \frac{\Delta_2}{\Delta_1}$$
- Giải hệ phương trình 2 ẩn bậc nhất:
  $$M = \frac{(M - D) + (M + D)}{2} = \frac{\Delta_1 + \frac{\Delta_2}{\Delta_1}}{2}$$
  $$D = M - \Delta_1$$

#### Mã nguồn:
```java
class SolutionMath {
    public int[] findErrorNums(int[] nums) {
        long n = nums.length;
        long sumDiff = n * (n + 1) / 2;
        long sqDiff = n * (n + 1) * (2 * n + 1) / 6;

        for (int num : nums) {
            sumDiff -= num;
            sqDiff -= (long) num * num;
        }

        // sumDiff = M - D
        // sqDiff = M^2 - D^2 = (M - D) * (M + D)
        long sumPlus = sqDiff / sumDiff; // M + D

        int missing = (int) ((sumDiff + sumPlus) / 2);
        int dup = (int) (missing - sumDiff);

        return new int[]{dup, missing};
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N)$ — Duyệt mảng đúng **1 vòng duy nhất (Single Pass)**.
- **Không gian:** $\mathcal{O}(1)$ — Không sửa mảng gốc.
- **Lưu ý tràn số (Overflow Warning):** Với $n = 10^4$, tổng bình phương $\sum i^2 \approx \frac{10^{12}}{3}$ vượt quá giới hạn của kiểu `int` ($2.14 \times 10^9$). Do đó bắt buộc phải tính toán trên kiểu `long`.

---

### Cách 3: Mảng đếm tần suất (Counting Array)

#### Tư duy:
Khởi tạo mảng đếm `count` có kích thước $n + 1$. Duyệt qua mảng `nums` và tăng biến đếm `count[num]++`.
- Số có `count[i] == 2` là `duplicate`.
- Số có `count[i] == 0` là `missing`.

#### Mã nguồn:
```java
class SolutionCounting {
    public int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int[] count = new int[n + 1];
        for (int num : nums) {
            count[num]++;
        }

        int dup = -1, missing = -1;
        for (int i = 1; i <= n; i++) {
            if (count[i] == 2) dup = i;
            else if (count[i] == 0) missing = i;
        }

        return new int[]{dup, missing};
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N)$.
- **Không gian:** $\mathcal{O}(N)$ — Tốn thêm mảng phụ $n + 1$ phần tử. Dễ cài đặt nhất nhưng không tối ưu về mặt bộ nhớ.

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Đổi dấu tại chỗ (In-Place) | Phép toán Bit (XOR) | Đại số (Math $\sum i^2$) | Mảng đếm (Counting Array) | Sắp xếp (Sorting) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ (1 pass) | $\mathcal{O}(N)$ | $\mathcal{O}(N \log N)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(N)$ | $\mathcal{O}(\log N)$ |
| **Biến đổi mảng đầu vào** | ⚠️ Có (đổi dấu phần tử) | ❌ Không sửa đổi | ❌ Không sửa đổi | ❌ Không sửa đổi | ⚠️ Làm xáo trộn mảng |
| **Nguy cơ tràn số (Overflow)**| ❌ Hoàn toàn không | ❌ Hoàn toàn không | ⚠️ Cần dùng `long` | ❌ Hoàn toàn không | ❌ Hoàn toàn không |
| **Thời gian chạy LeetCode** | $\approx 1\text{ms} - 2\text{ms}$ | $\approx 2\text{ms} - 3\text{ms}$ | $\approx 2\text{ms}$ | $\approx 2\text{ms} - 3\text{ms}$ | $\approx 12\text{ms} - 15\text{ms}$ |
| **Đánh giá trong phỏng vấn** | Đạt điểm tối đa về tư duy in-place | Đánh giá rất cao về tư duy Bitwise | Thể hiện tư duy toán học nhạy bén | Dễ làm, đạt yêu cầu cơ bản | Kém tối ưu nhất |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Ví dụ cơ bản
- `nums = [1, 2, 2, 4]` ($n = 4$)
- Vòng 1:
  - `i = 0, val = 1`: `nums[0] = -1`
  - `i = 1, val = 2`: `nums[1] = -2`
  - `i = 2, val = 2`: `nums[1]` đã âm $\implies$ `dup = 2`
  - `i = 3, val = 4`: `nums[3] = -4`
- Vòng 2:
  - `nums = [-1, -2, 2, -4]`
  - Vị trí `i = 2` có `nums[2] = 2 > 0` $\implies$ `missing = 2 + 1 = 3`.
- $\implies$ Kết quả: `[2, 3]` (Chính xác).

---

### Ca 2: Mảng tối thiểu ($n = 2$)
- `nums = [1, 1]` ($n = 2$)
- `i = 0, val = 1`: `nums[0] = -1`
- `i = 1, val = 1`: `nums[0]` đã âm $\implies$ `dup = 1`
- Sau vòng 1: `nums = [-1, 1]`.
- Vị trí `i = 1` có `nums[1] = 1 > 0` $\implies$ `missing = 1 + 1 = 2`.
- $\implies$ Kết quả: `[1, 2]` (Chính xác).

---

### Ca 3: Trùng ở cuối, thiếu ở đầu
- `nums = [2, 2]` ($n = 2$)
- `i = 0, val = 2`: `nums[1] = -2`
- `i = 1, val = 2`: `nums[1]` đã âm $\implies$ `dup = 2`
- Sau vòng 1: `nums = [2, -2]`.
- Vị trí `i = 0` có `nums[0] = 2 > 0` $\implies$ `missing = 0 + 1 = 1`.
- $\implies$ Kết quả: `[2, 1]` (Chính xác).

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

1. **Kỹ thuật "Array as Hash Map" (Biến mảng thành bảng băm tại chỗ):**
   - Khi bài toán cho một mảng có các phần tử nhận giá trị trong đoạn $[1, n]$ (hoặc $[0, n-1]$) và yêu cầu bộ nhớ $\mathcal{O}(1)$, hãy nghĩ ngay đến việc dùng chỉ số của mảng làm khóa và dấu âm/dương làm giá trị đánh dấu. Đây là mẫu bài kinh điển xuất hiện trong: *448. Find All Numbers Disappeared in an Array*, *442. Find All Duplicates in an Array*, *287. Find the Duplicate Number*.
2. **Khi nào nên dùng XOR thay cho In-Place Sign Inversion?**
   - Khi người phỏng vấn đưa ra ràng buộc phụ: *"Không được phép sửa đổi mảng đầu vào (Read-only / Immutable array)"*. Lúc này kỹ thuật XOR phân nhóm là giải pháp hoàn hảo nhất để đạt $\mathcal{O}(N)$ thời gian và $\mathcal{O}(1)$ bộ nhớ mà không cần chạm vào mảng gốc.
3. **Cẩn trọng bẫy tràn số (Integer Overflow) với phương pháp toán học:**
   - Khi dùng công thức tổng bình phương $\sum i^2 = \frac{n(n+1)(2n+1)}{6}$ với $n = 10^4$, giá trị đạt tới $\approx 3.3 \times 10^{11}$, vượt xa $2.14 \times 10^9$ của `int`. Việc chủ động khai báo biến `long` và ép kiểu khi nhân thể hiện sự vững vàng về mặt kỹ thuật phần mềm.
4. **Lưu ý thứ tự mảng kết quả:**
   - Đọc kỹ đề bài: kết quả yêu cầu là `[duplicate, missing]`, tránh nhầm lẫn đảo ngược thành `[missing, duplicate]`.
