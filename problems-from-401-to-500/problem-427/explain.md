# 📚 Giải thích Chi tiết Bài 592 - Fraction Addition and Subtraction

**Mục tiêu:** Cho một chuỗi `expression` đại diện cho biểu thức cộng và trừ các phân số, hãy tính toán và trả về kết quả dưới dạng chuỗi phân số tối giản.

---

## 🎯 Phân tích bài toán

### 1. Quy tắc đầu vào và đầu ra
- **Đầu vào:** Một chuỗi `expression` chứa các ký tự số từ `'0'` đến `'9'`, dấu gạch chéo `'/'`, dấu cộng `'+'`, và dấu trừ `'-'`.
  - Mỗi phân số có dạng: `±numerator/denominator`.
  - Phân số đầu tiên nếu là số dương thì dấu `'+'` được lược bỏ (ví dụ `"1/3-1/2"`).
  - Giá trị của tử số và mẫu số của mỗi phân số ban đầu nằm trong đoạn `[1, 10]` (chú ý: có thể là số có 2 chữ số như `10`).
  - Số lượng phân số trong biểu thức: từ 1 đến 10 phân số.
- **Đầu ra:** Phân số tối giản (irreducible fraction) dưới dạng chuỗi `"numerator/denominator"`.
  - Nếu kết quả là số nguyên, mẫu số phải là `1` (ví dụ: `2` $\rightarrow$ `"2/1"`, `0` $\rightarrow$ `"0/1"`).
  - Dấu âm luôn đi cùng với tử số (ví dụ: `"-1/6"`).
  - Nếu kết quả là số dương, không thêm dấu `'+'` ở đầu.

---

## 💡 Ý tưởng cốt lõi & Cơ sở toán học (Key Insights)

### 1. Công thức cộng / trừ phân số
Giả sử ta đang có phân số tổng hiện tại là $\frac{A}{B}$ và phân số tiếp theo cần cộng vào là $\frac{a}{b}$:
$$\frac{A}{B} + \frac{a}{b} = \frac{A \cdot b + a \cdot B}{B \cdot b}$$

- Tử số mới: $A_{\text{mới}} = A \cdot b + a \cdot B$
- Mẫu số mới: $B_{\text{mới}} = B \cdot b$

### 2. Rút gọn phân số bằng ước chung lớn nhất (GCD)
Để đưa phân số $\frac{A}{B}$ về dạng tối giản:
- Tìm ước chung lớn nhất của trị tuyệt đối của tử số và mẫu số: $g = \gcd(|A|, B)$.
- Chia cả tử và mẫu cho $g$:
  $$A = \frac{A}{g}, \quad B = \frac{B}{g}$$
- Thuật toán Euclid tìm $\gcd(a, b)$:
  $$\gcd(a, b) = \begin{cases} a & \text{nếu } b = 0 \\ \gcd(b, a \pmod b) & \text{nếu } b \ne 0 \end{cases}$$

### 3. Rút gọn liên tục qua từng phân số (Incremental Reduction)
- Thay vì cộng dồn tất cả các phân số rồi mới tìm GCD ở bước cuối cùng, ta **rút gọn ngay sau mỗi lần cộng một phân số**.
- **Lợi ích:** 
  - Giữ cho $A$ và $B$ luôn ở mức nhỏ nhất có thể qua từng bước lặp.
  - Hoàn toàn loại bỏ nguy cơ tràn số (integer overflow) khi nhân mẫu số với nhau.
  - Phép tính $\gcd$ trên các số nhỏ hơn chạy nhanh hơn đáng kể.

### 4. Xử lý trường hợp phân số bằng 0
- Khi tử số $A = 0$: $\gcd(0, B) = B$.
- Phép rút gọn cho ra: $A = 0 / B = 0$ và $B = B / B = 1$.
- Kết quả tự động trở thành $\frac{0}{1}$, hoàn toàn chính xác theo yêu cầu đề bài (`"0/1"`).

### 5. Duyệt con trỏ trực tiếp (Pointer Scan) vs Regex / Scanner
- Chuỗi biểu thức có độ dài ngắn ($\le 10$ phân số).
- Sử dụng con trỏ duyệt mảng ký tự `char[]` một lượt (One-pass $\mathcal{O}(N)$) giúp:
  - Tránh chi phí khởi tạo Regex Pattern và Matcher.
  - Tránh cấp phát bộ nhớ trung gian không cần thiết của `Scanner` hay `String.split()`.
  - Đạt tốc độ thực thi tối đa **0ms (100th percentile)** trên LeetCode.

---

## ⭐ GIẢI PHÁP TỐI ƯU: One-pass Linear Scan + Thuật toán Euclid (GCD)

### 🛠️ Các bước thực hiện
1. Khởi tạo tổng ban đầu: `numerator = 0`, `denominator = 1` (đại diện cho $\frac{0}{1}$).
2. Chuyển `expression` thành mảng `chars` và dùng một con trỏ `i` duyệt từ đầu đến cuối:
   - **Xác định dấu:** Kiểm tra xem `chars[i]` có phải là `'+'` hoặc `'-'` không. Mặc định dấu là $+1$. Nếu là `'-'` thì `sign = -1`, tăng `i++`.
   - **Đọc tử số (`curNumerator`):** Đọc chuỗi các chữ số liên tiếp và nhân với `sign`.
   - **Bỏ qua ký tự `'/'`:** Tăng `i++`.
   - **Đọc mẫu số (`curDenominator`):** Đọc chuỗi các chữ số liên tiếp tiếp theo.
   - **Cộng vào phân số tổng:** 
     - `numerator = numerator * curDenominator + curNumerator * denominator`
     - `denominator = denominator * curDenominator`
   - **Rút gọn phân số:** 
     - $g = \gcd(|\text{numerator}|, \text{denominator})$
     - `numerator /= g`, `denominator /= g`
3. Trả về chuỗi kết quả: `numerator + "/" + denominator`.

---

### 💻 Code Java (Tối ưu nhất - 0ms)

```java
class Solution {
    public String fractionAddition(String expression) {
        int numerator = 0;
        int denominator = 1;

        char[] chars = expression.toCharArray();
        int n = chars.length;
        int i = 0;

        while (i < n) {
            // 1. Xác định dấu của phân số hiện tại
            int sign = 1;
            if (chars[i] == '+' || chars[i] == '-') {
                if (chars[i] == '-') {
                    sign = -1;
                }
                i++;
            }

            // 2. Đọc tử số (numerator)
            int curNumerator = 0;
            while (i < n && chars[i] >= '0' && chars[i] <= '9') {
                curNumerator = curNumerator * 10 + (chars[i] - '0');
                i++;
            }
            curNumerator *= sign;

            // 3. Bỏ qua ký tự phân cách '/'
            i++;

            // 4. Đọc mẫu số (denominator)
            int curDenominator = 0;
            while (i < n && chars[i] >= '0' && chars[i] <= '9') {
                curDenominator = curDenominator * 10 + (chars[i] - '0');
                i++;
            }

            // 5. Cộng phân số hiện tại vào tổng:
            // a/b + c/d = (a * d + c * b) / (b * d)
            numerator = numerator * curDenominator + curNumerator * denominator;
            denominator = denominator * curDenominator;

            // 6. Rút gọn phân số bằng ước chung lớn nhất (GCD)
            int commonDivisor = gcd(Math.abs(numerator), denominator);
            numerator /= commonDivisor;
            denominator /= commonDivisor;
        }

        return numerator + "/" + denominator;
    }

    // Thuật toán Euclid tìm ước chung lớn nhất (GCD)
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
```

---

### 📊 Đánh giá độ phức tạp thuật toán (Complexity)

| Tiêu chí | Độ phức tạp | Giải thích |
| :--- | :--- | :--- |
| **Thời gian (Time)** | $\mathcal{O}(N)$ | Với $N$ là độ dài chuỗi `expression`. Con trỏ `i` chỉ duyệt qua mỗi ký tự đúng 1 lần. Thuật toán Euclid cho các số nhỏ ($\le 100$) mất $\mathcal{O}(\log(\min(A, B))) \approx \mathcal{O}(1)$ bước. |
| **Không gian (Space)** | $\mathcal{O}(1)$ | Không sử dụng thêm cấu trúc dữ liệu phụ nào ngoài mảng ký tự và vài biến số nguyên đơn giản. |

---

## 🔍 Mô phỏng từng bước (Dry Run Walkthrough)

### Ví dụ: `expression = "-1/2+1/2+1/3"`

- **Khởi tạo:** `numerator = 0`, `denominator = 1` ($\frac{0}{1}$)

#### Lượt 1: Phân số `-1/2`
- Dấu: `-` $\rightarrow$ `sign = -1`
- Tử số: `curNumerator = -1`
- Mẫu số: `curDenominator = 2`
- Phép tính:
  - `numerator = 0 * 2 + (-1) * 1 = -1`
  - `denominator = 1 * 2 = 2`
- Rút gọn: $\gcd(|-1|, 2) = 1 \rightarrow \frac{-1}{2}$

#### Lượt 2: Phân số `+1/2`
- Dấu: `+` $\rightarrow$ `sign = 1`
- Tử số: `curNumerator = 1`
- Mẫu số: `curDenominator = 2`
- Phép tính:
  - `numerator = (-1) * 2 + 1 * 2 = 0`
  - `denominator = 2 * 2 = 4`
- Rút gọn: $\gcd(0, 4) = 4$
  - `numerator = 0 / 4 = 0`
  - `denominator = 4 / 4 = 1`
  - Kết quả tạm thời: $\frac{0}{1}$

#### Lượt 3: Phân số `+1/3`
- Dấu: `+` $\rightarrow$ `sign = 1`
- Tử số: `curNumerator = 1`
- Mẫu số: `curDenominator = 3`
- Phép tính:
  - `numerator = 0 * 3 + 1 * 1 = 1`
  - `denominator = 1 * 3 = 3`
- Rút gọn: $\gcd(1, 3) = 1 \rightarrow \frac{1}{3}$

#### Kết thúc:
- Trả về `"1/3"`.

---

## 📌 Các góc cạnh cần lưu ý (Edge Cases & Gotchas)

1. **Phân số đầu tiên không có dấu `+`:**
   - Ví dụ: `"1/3-1/2"`.
   - Thuật toán gán mặc định `sign = 1`, chỉ đổi thành `-1` khi gặp ký tự `'-'`. Do đó trường hợp này được xử lý tự nhiên và chính xác.

2. **Số có hai chữ số (`10`):**
   - Vòng lặp `while (chars[i] >= '0' && chars[i] <= '9')` xử lý đa chữ số (`num = num * 10 + digit`), đảm bảo đọc đúng cả số `10` mà không bị nhầm lẫn chỉ đọc `1`.

3. **Mẫu số luôn luôn dương:**
   - Vì các mẫu số trong chuỗi ban đầu luôn nằm trong `[1, 10]` và `denominator` bắt đầu từ `1`, tích của chúng luôn $> 0$.
   - Dấu âm (nếu có) luôn được giữ ở tử số `numerator`, đảm bảo đúng định dạng đầu ra.

4. **Kết quả là số nguyên:**
   - Đề bài yêu cầu: nếu kết quả là số nguyên (ví dụ `1`), định dạng phải là `"1/1"`, hay `2` là `"2/1"`.
   - Thuật toán giữ mẫu số `denominator = 1`, do đó chuỗi nối `numerator + "/" + denominator` luôn cho đúng định dạng `"1/1"`, `"2/1"`, `"0/1"`.

---

## 🔄 Cách tiếp cận thay thế: Dùng `java.util.Scanner`

Trong Java, ta có thể dùng `Scanner` với custom delimiter để bóc tách các phân số một cách ngắn gọn:

```java
import java.util.Scanner;

class SolutionAlternative {
    public String fractionAddition(String expression) {
        Scanner scanner = new Scanner(expression).useDelimiter("/|(?=[+-])");
        int numerator = 0;
        int denominator = 1;

        while (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            numerator = numerator * b + a * denominator;
            denominator = denominator * b;

            int g = gcd(Math.abs(numerator), denominator);
            numerator /= g;
            denominator /= g;
        }

        scanner.close();
        return numerator + "/" + denominator;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
```

> **So sánh:**
> - Cách dùng `Scanner` ngắn gọn về mặt số dòng code.
> - Tuy nhiên, `Scanner` sử dụng biểu thức chính quy (`regex`) nên chạy chậm hơn (~6ms đến 10ms) và tốn bộ nhớ hơn đáng kể so với phương pháp con trỏ tuyến tính (`char[]` scan - 0ms). Do đó **Linear Scan** vẫn là giải pháp được khuyên dùng hàng đầu khi phỏng vấn coding.
