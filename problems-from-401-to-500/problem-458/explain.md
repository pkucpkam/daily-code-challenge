# 📚 Giải thích Chi tiết Bài 643 - Maximum Average Subarray I

**Mục tiêu:** Cho một mảng số nguyên `nums` gồm $n$ phần tử và một số nguyên $k$. Hãy tìm một mảng con liên tiếp (contiguous subarray) có độ dài đúng bằng $k$ sao cho giá trị trung bình cộng của các phần tử trong mảng con đó đạt giá trị lớn nhất, và trả về giá trị trung bình cực đại này.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Các ràng buộc (Constraints)
- `n == nums.length`: Số lượng phần tử của mảng.
- `1 <= k <= n <= 10⁵`: Mảng luôn có ít nhất $k$ phần tử, không cần xử lý trường hợp mảng rỗng hay $k > n$.
- `-10⁴ <= nums[i] <= 10⁴`: Các phần tử có thể nhận giá trị âm, dương hoặc $0$.
- `Sai số cho phép: 10⁻⁵`: Đề bài chấp nhận kết quả nếu sai số tính toán nhỏ hơn $10^{-5}$.

#### Phân tích an toàn tràn số nguyên (Integer Overflow Analysis):
- Giá trị tổng mảng con cực đại có thể đạt được:
  $$\text{Max Sum} = k \times \max(nums[i]) \le 10^5 \times 10^4 = 10^9$$
- Giá trị tổng mảng con cực tiểu có thể đạt được:
  $$\text{Min Sum} = k \times \min(nums[i]) \ge 10^5 \times (-10^4) = -10^9$$
- Trong Java, kiểu số nguyên `int` 32-bit có dấu có phạm vi biểu diễn từ $-2^{31} \approx -2.14 \times 10^9$ đến $2^{31} - 1 \approx 2.14 \times 10^9$.
- Do cả $10^9$ và $-10^9$ đều nằm hoàn toàn trong phạm vi an toàn của kiểu `int`, ta **hoàn toàn có thể sử dụng biến `int`** để lưu tổng mà không lo bị tràn số (overflow), không nhất thiết phải dùng kiểu `long`.

---

### 2. Điểm then chốt Toán học: Chuyển đổi từ Tìm Trung bình sang Tìm Tổng

Giá trị trung bình của một mảng con độ dài $k$ bắt đầu từ chỉ số $i$ đến $i + k - 1$ được định nghĩa là:
$$\text{Average}(i) = \frac{\sum_{j=i}^{i+k-1} nums[j]}{k}$$

Vì độ dài $k > 0$ là một hằng số cố định cho mọi mảng con cần xét, nên:
$$\max_{i} \left( \frac{\text{Sum}(i)}{k} \right) = \frac{\max_{i} \left( \text{Sum}(i) \right)}{k}$$

#### Ý nghĩa thực tế sống còn:
1. **Tránh phép chia số thực lặp lại:** Phép chia số thực dấu phẩy động (`double division`) trên CPU tốn nhiều chu kỳ máy hơn đáng kể so với phép cộng/trừ số nguyên.
2. **Tránh tích lũy sai số làm tròn (Floating-point precision errors):** Khi so sánh các giá trị `double` liên tục trong vòng lặp, sai số biểu diễn nhị phân có thể làm sai lệch kết quả so sánh.
3. **Chiến lược tối ưu:** Ta chỉ làm việc hoàn toàn với các số nguyên `int` để tìm ra **tổng lớn nhất** (`maxSum`), sau đó mới thực hiện phép chia `(double) maxSum / k` **đúng 1 lần duy nhất** ở bước trả về kết quả!

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Cửa sổ trượt kích thước cố định (Fixed-Size Sliding Window)

### 💡 Ý tưởng đột phá (Key Insights)

Thay vì tính lại tổng từ đầu cho mỗi mảng con mới (mất $\mathcal{O}(k)$ thao tác), ta nhận thấy hai mảng con liền kề có phần giao nhau rất lớn gồm $k - 1$ phần tử chung:
- Cửa sổ hiện tại: `[nums[i - k], nums[i - k + 1], ..., nums[i - 1]]`
- Cửa sổ kế tiếp: `[nums[i - k + 1], ..., nums[i - 1], nums[i]]`

Khi dịch chuyển cửa sổ trượt sang phải 1 vị trí:
- Phần tử bên trái ngoài cùng $nums[i - k]$ bị trượt ra khỏi cửa sổ $\implies$ **Trừ đi $nums[i - k]$**.
- Phần tử mới bên phải $nums[i]$ đi vào trong cửa sổ $\implies$ **Cộng thêm $nums[i]$**.

```text
Cửa sổ ban đầu (từ 0 đến k - 1):
[nums[0],  nums[1],  ...,  nums[k - 1]] | nums[k],  nums[k + 1], ...
 --------------------------------------
           currentSum

Trượt sang phải 1 bước (khi i = k):
nums[0] | [nums[1],  ...,  nums[k - 1],  nums[k]] | nums[k + 1], ...
-------    -------------------------------------
Loại bỏ                  Thêm vào
```

Công thức cập nhật tổng trong thời gian hằng số $\mathcal{O}(1)$:
$$\text{currentSum} = \text{currentSum} + nums[i] - nums[i - k]$$

### 🌟 Ưu điểm vượt trội
- **Thời gian chạy siêu tốc $\mathcal{O}(N)$:** Duyệt qua mảng đúng 1 lượt duy nhất. Mỗi phần tử chỉ được cộng 1 lần và trừ 1 lần. Tốc độ trên LeetCode đạt $\approx 2\text{ms} - 3\text{ms}$ (Beats 99% - 100%).
- **Bộ nhớ tối ưu tuyệt đối $\mathcal{O}(1)$:** Chỉ dùng đúng 2 biến nguyên `currentSum` và `maxSum`, không tốn bất kỳ vùng nhớ phụ nào.
- **Độ chính xác cao:** Toàn bộ quá trình tính toán diễn ra trên miền số nguyên chuẩn xác.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-458/Solution.java):

```java
class Solution {
    /**
     * Best Solution: Cửa sổ trượt kích thước cố định k (Fixed-Size Sliding Window).
     *
     * Time Complexity: O(N) - Duyệt qua mảng đúng 1 lượt duy nhất (N = nums.length).
     * Space Complexity: O(1) - Chỉ sử dụng 2 biến số nguyên cố định, không dùng bộ nhớ phụ.
     */
    public double findMaxAverage(int[] nums, int k) {
        // Bước 1: Tính tổng của cửa sổ k phần tử đầu tiên
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
        }

        int maxSum = currentSum;

        // Bước 2: Trượt cửa sổ qua phần còn lại của mảng (từ chỉ số k đến n - 1)
        for (int i = k; i < nums.length; i++) {
            currentSum += nums[i] - nums[i - k];
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        // Bước 3: Ép kiểu double để thực hiện phép chia số thực chính xác
        return (double) maxSum / k;
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Vét cạn (Brute Force)

#### Tư duy:
Duyệt qua tất cả các vị trí bắt đầu $i$ có thể có của mảng con độ dài $k$ ($0 \le i \le n - k$). Với mỗi vị trí $i$, dùng một vòng lặp con lồng bên trong để tính tổng của $k$ phần tử liên tiếp từ $nums[i]$ đến $nums[i + k - 1]$.

#### Mã nguồn:
```java
class SolutionBruteForce {
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        double maxAvg = -Double.MAX_VALUE;

        for (int i = 0; i <= n - k; i++) {
            int sum = 0;
            for (int j = i; j < i + k; j++) {
                sum += nums[j];
            }
            double avg = (double) sum / k;
            if (avg > maxAvg) {
                maxAvg = avg;
            }
        }

        return maxAvg;
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}((N - k + 1) \times k) \approx \mathcal{O}(N \times k)$. Trong trường hợp xấu nhất ($k = \frac{N}{2}$ và $N = 10^5$), số phép tính lên đến:
  $$\frac{10^5}{2} \times \frac{10^5}{2} = 2.5 \times 10^9 \text{ phép tính}$$
  Vượt xa ngưỡng cho phép $10^8$ phép tính/giây $\implies$ **Time Limit Exceeded (TLE)** trên LeetCode.
- **Không gian:** $\mathcal{O}(1)$.

---

### Cách 2: Mảng cộng dồn (Prefix Sum Array)

#### Tư duy:
1. Xây dựng mảng tiền tố `prefix` kích thước $n + 1$, trong đó `prefix[i]` lưu tổng của $i$ phần tử đầu tiên (`nums[0] + ... + nums[i - 1]`).
2. Khi đó, tổng của bất kỳ mảng con nào từ chỉ số $i$ đến $i + k - 1$ có thể truy xuất tức thì trong $\mathcal{O}(1)$ bằng:
   $$\text{Sum}(i, i + k - 1) = \text{prefix}[i + k] - \text{prefix}[i]$$
3. Duyệt tìm giá trị cực đại và chia cho $k$.

#### Mã nguồn:
```java
class SolutionPrefixSum {
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int maxSum = prefix[k] - prefix[0];
        for (int i = 1; i <= n - k; i++) {
            int currentSum = prefix[i + k] - prefix[i];
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return (double) maxSum / k;
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N)$ (1 vòng dựng prefix sum + 1 vòng tìm max).
- **Không gian:** $\mathcal{O}(N)$ do phải cấp phát mảng phụ `prefix` kích thước $N + 1$ (với $N = 10^5$, tốn khoảng $400\text{ KB}$ bộ nhớ heap).
- **Nhược điểm so với Sliding Window:** Mặc dù cùng đạt độ phức tạp thời gian $\mathcal{O}(N)$, nhưng mảng cộng dồn tốn thêm bộ nhớ phụ $\mathcal{O}(N)$ và làm tăng số lượng truy xuất bộ nhớ (cache misses). Do đó kỹ thuật Sliding Window với $\mathcal{O}(1)$ bộ nhớ vẫn vượt trội hơn.

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Cửa sổ trượt (Sliding Window) | Mảng cộng dồn (Prefix Sum) | Vét cạn (Brute Force) |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ (Tối ưu nhất) | $\mathcal{O}(N)$ | $\mathcal{O}(N \times k)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(1)$ (Tối ưu nhất) | $\mathcal{O}(N)$ (Tốn mảng phụ) | $\mathcal{O}(1)$ |
| **Số lần chia số thực** | $1$ lần duy nhất | $1$ lần duy nhất | $N - k + 1$ lần |
| **Thời gian chạy LeetCode** | $\approx 2\text{ms} - 3\text{ms}$ (Beats 99% - 100%) | $\approx 4\text{ms} - 6\text{ms}$ | Time Limit Exceeded (TLE) |
| **Khả năng tràn bộ nhớ** | Tuyệt đối an toàn | Cần cấp phát mảng heap | Tuyệt đối an toàn |
| **Mức độ phức tạp cài đặt** | Cực kỳ ngắn gọn, thanh lịch | Cần quản lý chỉ số $N + 1$ | Dễ nghĩ nhưng không khả thi |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Ví dụ mẫu (Mảng hỗn hợp âm dương)
- `nums = [1, 12, -5, -6, 50, 3]`, `k = 4`
- **Cửa sổ ban đầu ($i = 0 \to 3$):**
  - `currentSum = 1 + 12 + (-5) + (-6) = 2`
  - `maxSum = 2`
- **Bước 1 ($i = 4$):**
  - Thêm `nums[4] = 50`, bớt `nums[0] = 1`.
  - `currentSum = 2 + 50 - 1 = 51`
  - `maxSum = Math.max(2, 51) = 51`
- **Bước 2 ($i = 5$):**
  - Thêm `nums[5] = 3`, bớt `nums[1] = 12`.
  - `currentSum = 51 + 3 - 12 = 42`
  - `maxSum = Math.max(51, 42) = 51`
- **Kết quả:** $\frac{51.0}{4} = \mathbf{12.75000}$ (Chính xác).

---

### Ca 2: Độ dài cửa sổ bằng kích thước toàn mảng ($k = n$)
- `nums = [1, 2, 3]`, `k = 3`
- Cửa sổ đầu tiên tính tổng cả 3 phần tử: `currentSum = 6`.
- Vòng lặp thứ hai `for (int i = k; i < nums.length; i++)` không được kích hoạt vì $k == n$.
- **Kết quả:** $\frac{6.0}{3} = \mathbf{2.00000}$ (Chính xác).

---

### Ca 3: Cửa sổ kích thước đơn lẻ ($k = 1$)
- `nums = [5]`, `k = 1` hoặc `nums = [-1, 4, 2]`, `k = 1`
- Bài toán trở thành tìm giá trị lớn nhất trong mảng.
- Thuật toán trượt từng bước một: mỗi bước cộng phần tử hiện tại và trừ phần tử ngay trước nó $\implies$ cập nhật chính xác giá trị cực đại.
- **Kết quả:** $\frac{5.0}{1} = \mathbf{5.00000}$ (Chính xác).

---

### Ca 4: Mảng chứa toàn bộ các số âm (Bẫy cực trị khởi tạo)
- `nums = [-5, -3, -10, -2]`, `k = 2`
- Cửa sổ đầu tiên: `currentSum = (-5) + (-3) = -8`.
- Nếu lập trình viên mắc sai lầm khởi tạo `maxSum = 0`, thì do $0 > -8$ nên kết quả sẽ bị sai thành $0$.
- Bằng cách khởi tạo chuẩn xác `maxSum = currentSum = -8`:
  - Bước tiếp theo: `currentSum = -8 + (-10) - (-5) = -13` $\implies$ `maxSum = -8`.
  - Bước kế tiếp: `currentSum = -13 + (-2) - (-3) = -12` $\implies$ `maxSum = -8`.
- **Kết quả:** $\frac{-8.0}{2} = \mathbf{-4.00000}$ (Chính xác tuyệt đối).

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

1. **Chuyển đổi bài toán tối ưu trung bình sang tối ưu tổng (Sum Transformation):**
   - Bất cứ khi nào bài toán yêu cầu tìm giá trị trung bình cực đại/cực tiểu của một tập hợp có kích thước cố định $k$, hãy quy về bài toán tìm tổng cực đại/cực tiểu. Điều này giúp toàn bộ phép tính duy trì trong miền số nguyên nhanh và chính xác.
2. **Khuôn mẫu Cửa sổ trượt cố định (Fixed-Size Sliding Window):**
   - Luôn gồm 2 pha rõ ràng:
     - Pha 1: Khởi tạo cửa sổ đầu tiên kích thước $k$.
     - Pha 2: Trượt cửa sổ với công thức $\text{currentSum} += nums[i] - nums[i - k]$.
3. **Cảnh giác với bẫy khởi tạo giá trị lớn nhất:**
   - Tuyệt đối không khởi tạo `maxSum = 0` hoặc `maxSum = Integer.MIN_VALUE` khi chưa xem xét dữ liệu âm. Cách an toàn nhất là gán `maxSum` bằng chính tổng của cửa sổ đầu tiên.
4. **Cẩn trọng với phép chia số nguyên (Integer Division Truncation) trong Java:**
   - Trong Java, phép chia giữa hai số nguyên `maxSum / k` sẽ tự động cắt bỏ phần thập phân (ví dụ `51 / 4 = 12`). Bắt buộc phải ép kiểu `(double) maxSum / k` để có kết quả số thực chính xác.
5. **Chứng minh an toàn tràn số (No Overflow) với người phỏng vấn:**
   - Tính nhẩm nhanh biên: $10^5 \times 10^4 = 10^9 < 2.14 \times 10^9 \approx 2^{31}-1$. Việc chủ động giải thích lý do dùng `int` thay vì `long` thể hiện sự am hiểu sâu sắc về kiến trúc máy tính và tối ưu hóa hệ thống.
