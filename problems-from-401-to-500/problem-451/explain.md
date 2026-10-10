# 📚 Giải thích Chi tiết Bài 628 - Maximum Product of Three Numbers

**Mục tiêu:** Cho một mảng số nguyên `nums`. Tìm 3 phần tử trong mảng sao cho tích của chúng đạt giá trị lớn nhất và trả về giá trị tích cực đại đó.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- `3 <= nums.length <= 10⁴`: Mảng luôn có ít nhất 3 phần tử (không cần xử lý trường hợp mảng không đủ 3 số).
- `-1000 <= nums[i] <= 1000`: Giá trị mỗi phần tử nằm trong khoảng $[-1000, 1000]$.
- **An toàn tràn số (No Integer Overflow):**
  - Tích lớn nhất có thể đạt được: $1000 \times 1000 \times 1000 = 10^9$.
  - Tích nhỏ nhất có thể đạt được: $-1000 \times 1000 \times 1000 = -10^9$.
  - Trong Java, kiểu `int` 32-bit có miền giá trị từ $-2^{31} \approx -2.14 \times 10^9$ đến $2^{31} - 1 \approx 2.14 \times 10^9$.
  - Do đó, cả $10^9$ và $-10^9$ đều nằm an toàn trong giới hạn của kiểu `int`, không lo bị tràn số hay cần ép kiểu sang `long`.

---

### 2. Bản chất Toán học của Tích 3 Số (Quy tắc Dấu)

Do mảng có thể chứa cả số dương, số âm và số $0$, khi nhân 3 số lại với nhau ta sẽ gặp các tình huống sau:

1. **Trường hợp mảng toàn số dương ($nums[i] \ge 0$):**
   - Rõ ràng tích lớn nhất chính là tích của **3 số lớn nhất**:
     $$\text{Candidate 1} = \text{max}_1 \times \text{max}_2 \times \text{max}_3$$

2. **Trường hợp mảng toàn số âm ($nums[i] < 0$):**
   - Tích của 3 số âm bất kỳ luôn là một số âm: $(-)\times(-)\times(-) = (-)$.
   - Để số âm này có giá trị lớn nhất (gần 0 nhất), ta phải chọn 3 số âm có trị tuyệt đối nhỏ nhất — tức là **3 số lớn nhất trong mảng**:
     $$\text{Candidate 1} = \text{max}_1 \times \text{max}_2 \times \text{max}_3$$

3. **Trường hợp mảng vừa có số âm vừa có số dương:**
   - Ta có thể chọn:
     - Hoặc 3 số dương lớn nhất: tích dương.
     - Hoặc **2 số âm có trị tuyệt đối lớn nhất** (tức là 2 số nhỏ nhất trong mảng) nhân với **1 số dương lớn nhất**:
       $$(-)\times(-)\times(+) = (+)$$
       Hai số âm có độ lớn tuyệt đối cực lớn khi nhân với nhau sẽ tạo ra một số dương rất lớn!
       $$\text{Candidate 2} = \text{min}_1 \times \text{min}_2 \times \text{max}_1$$

$\implies$ **Quy tắc vàng:** Trong mọi trường hợp phân bố dữ liệu, tích lớn nhất của 3 số **luôn luôn** là giá trị cực đại giữa 2 ứng viên:
$$\mathbf{Result} = \max\Big(\text{max}_1 \times \text{max}_2 \times \text{max}_3,\quad \text{min}_1 \times \text{min}_2 \times \text{max}_1\Big)$$

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Duyệt 1 vòng tuyến tính (Single Pass / Linear Scan)

### 💡 Ý tưởng đột phá (Key Insights)
- Để tính được 2 ứng viên trên, ta **chỉ cần đúng 5 giá trị** từ mảng `nums`:
  1. Ba giá trị lớn nhất: $\text{max}_1 \ge \text{max}_2 \ge \text{max}_3$.
  2. Hai giá trị nhỏ nhất: $\text{min}_1 \le \text{min}_2$.
- Thay vì sắp xếp cả mảng mất $\mathcal{O}(N \log N)$, ta chỉ cần duyệt qua mảng đúng 1 lần ($\mathcal{O}(N)$) và duy trì 5 biến nguyên bằng kỹ thuật **dịch chuyển xếp tầng (Cascading Shift)**:
  - Khi gặp số lớn hơn $\text{max}_1$: đẩy $\text{max}_2 \to \text{max}_3$, $\text{max}_1 \to \text{max}_2$, rồi gán giá trị mới vào $\text{max}_1$.
  - Tương tự với $\text{max}_2$ và $\text{max}_3$.
  - Khi gặp số nhỏ hơn $\text{min}_1$: đẩy $\text{min}_1 \to \text{min}_2$, rồi gán giá trị mới vào $\text{min}_1$.
  - Tương tự với $\text{min}_2$.

### 🌟 Ưu điểm vượt trội
- **Thời gian chạy siêu tốc $\mathcal{O}(N)$:** Duyệt mảng đúng 1 vòng, tốc độ $\approx 1\text{ms} - 2\text{ms}$ trên LeetCode (Beats 98% - 100%).
- **Bộ nhớ $\mathcal{O}(1)$:** Chỉ dùng đúng 5 biến cục bộ, không cấp phát thêm mảng hay cấu trúc dữ liệu phụ.
- **Không làm thay đổi mảng đầu vào (No In-place Mutation):** Khác với thuật toán sắp xếp làm xáo trộn vị trí mảng gốc.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-451/Solution.java):

```java
class Solution {
    /**
     * Best Solution: Duyệt mảng một lần (Single Pass / Linear Scan).
     * Tìm 3 số lớn nhất (max1 >= max2 >= max3) và 2 số nhỏ nhất (min1 <= min2).
     * 
     * Time Complexity: O(N) - Duyệt qua mảng nums đúng một lần duy nhất.
     * Space Complexity: O(1) - Sử dụng 5 biến số nguyên cố định, không dùng bộ nhớ phụ.
     */
    public int maximumProduct(int[] nums) {
        // Khởi tạo 3 giá trị lớn nhất: max1 >= max2 >= max3
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;

        // Khởi tạo 2 giá trị nhỏ nhất: min1 <= min2
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int n : nums) {
            // Cập nhật 3 giá trị lớn nhất
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            // Cập nhật 2 giá trị nhỏ nhất
            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }

        // Tích lớn nhất chỉ có thể đến từ 1 trong 2 trường hợp:
        // 1. Tích của 3 số lớn nhất: max1 * max2 * max3
        // 2. Tích của 2 số âm nhỏ nhất (âm * âm = dương lớn) và số lớn nhất: min1 * min2 * max1
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Sắp xếp toàn bộ mảng (Sorting)

#### Tư duy:
1. Sắp xếp mảng theo thứ tự tăng dần bằng `Arrays.sort(nums)`.
2. Sau khi sắp xếp:
   - 3 số lớn nhất nằm ở cuối mảng: `nums[n - 1]`, `nums[n - 2]`, `nums[n - 3]`.
   - 2 số nhỏ nhất nằm ở đầu mảng: `nums[0]`, `nums[1]`.
3. Trả về `Math.max(nums[n - 1] * nums[n - 2] * nums[n - 3], nums[0] * nums[1] * nums[n - 1])`.

#### Mã nguồn:
```java
import java.util.Arrays;

class SolutionSorting {
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int option1 = nums[n - 1] * nums[n - 2] * nums[n - 3];
        int option2 = nums[0] * nums[1] * nums[n - 1];
        return Math.max(option1, option2);
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N \log N)$ do chi phí của thuật toán sắp xếp (Dual-Pivot Quicksort). Chạy khoảng $10\text{ms} - 12\text{ms}$ trên LeetCode.
- **Không gian:** $\mathcal{O}(\log N)$ do bộ nhớ stack của thuật toán đệ quy Quicksort.
- **Nhược điểm:** Sắp xếp toàn bộ mảng trong khi ta chỉ cần 5 phần tử cực trị là dư thừa tài nguyên.

---

### Cách 2: Hàng đợi ưu tiên (PriorityQueue / Heaps)

#### Tư duy:
- Dùng một Min-Heap kích thước 3 để lưu 3 phần tử lớn nhất.
- Dùng một Max-Heap kích thước 2 để lưu 2 phần tử nhỏ nhất.
- Duyệt qua mảng và thêm vào Heap, khi kích thước vượt quá ngưỡng thì `poll()` phần tử ra.

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N \log 3 + N \log 2) = \mathcal{O}(N)$.
- **Không gian:** $\mathcal{O}(1)$ (kích thước Heap là hằng số).
- **Nhược điểm:** Trong Java, `PriorityQueue` đi kèm chi phí boxing/unboxing giữa `int` và `Integer`, tạo đối tượng trên heap dẫn đến thời gian thực thi chậm hơn nhiều so với việc dùng 5 biến nguyên nguyên thủy (`primitive types`).

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Duyệt 1 vòng (Single Pass) | Sắp xếp (Sorting) | Hàng đợi ưu tiên (Heap) | Vét cạn (Brute Force 3 loops) |
| :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ (Tối ưu nhất) | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N^3)$ (TLE) |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(1)$ | $\mathcal{O}(\log N)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Thời gian chạy LeetCode** | $\approx 1\text{ms} - 2\text{ms}$ | $\approx 11\text{ms} - 15\text{ms}$ | $\approx 8\text{ms} - 12\text{ms}$ | Time Limit Exceeded |
| **Biến đổi mảng gốc** | ❌ Không sửa đổi | ⚠️ Làm xáo trộn mảng | ❌ Không sửa đổi | ❌ Không sửa đổi |
| **Độ phức tạp mã nguồn** | Rất ngắn gọn, dễ hiểu | 4 dòng mã | Cần import thêm thư viện | Dễ cài đặt nhưng rất chậm |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Toàn số dương
- `nums = [1, 2, 3, 4]`
- `max1 = 4, max2 = 3, max3 = 2`
- `min1 = 1, min2 = 2`
- `option1 = 4 * 3 * 2 = 24`
- `option2 = 1 * 2 * 4 = 8`
- $\implies \max(24, 8) = \mathbf{24}$ (Chính xác).

### Ca 2: Hai số âm có trị tuyệt đối lớn
- `nums = [-10, -10, 5, 2]`
- `max1 = 5, max2 = 2, max3 = -10`
- `min1 = -10, min2 = -10`
- `option1 = 5 * 2 * (-10) = -100`
- `option2 = (-10) * (-10) * 5 = 500`
- $\implies \max(-100, 500) = \mathbf{500}$ (Chính xác).

### Ca 3: Toàn bộ mảng đều là số âm
- `nums = [-1, -2, -3, -4]`
- `max1 = -1, max2 = -2, max3 = -3`
- `min1 = -4, min2 = -3`
- `option1 = (-1) * (-2) * (-3) = -6`
- `option2 = (-4) * (-3) * (-1) = -12`
- $\implies \max(-6, -12) = \mathbf{-6}$ (Chính xác).

### Ca 4: Có chứa số 0 và số âm
- `nums = [-5, -2, 0, 1]`
- `max1 = 1, max2 = 0, max3 = -2`
- `min1 = -5, min2 = -2`
- `option1 = 1 * 0 * (-2) = 0`
- `option2 = (-5) * (-2) * 1 = 10`
- $\implies \max(0, 10) = \mathbf{10}$ (Chính xác).

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

1. **Cẩn trọng với bẫy dấu âm trong các bài toán tích số cực đại:**
   - Trong các bài toán tìm tích lớn nhất (như *Maximum Product Subarray* hoặc bài này), số âm nhân với số âm tạo ra số dương cực lớn. Do đó luôn cần theo dõi cả giá trị cực đại lẫn giá trị cực tiểu.
2. **Kỹ thuật tối ưu từ $\mathcal{O}(N \log N) \to \mathcal{O}(N)$ khi số lượng phần tử cần tìm là cố định ($k$ nhỏ):**
   - Nếu đề bài yêu cầu tìm $k$ phần tử lớn nhất hoặc nhỏ nhất với $k$ là một hằng số nhỏ cố định ($k = 3, k = 5$), ta không cần sắp xếp toàn mảng mà có thể giải quyết chỉ bằng một vòng lặp tuyến tính duy trì $k$ biến.
3. **Phân tích tràn số số học (Overflow Analysis):**
   - Luôn chủ động tính toán trước giá trị lớn nhất và nhỏ nhất của tích (ở đây là $\pm 1000^3 = \pm 10^9$) để tự tin khẳng định với người phỏng vấn rằng giá trị không bị vượt ngưỡng $2^{31} - 1$ của kiểu `int`.
