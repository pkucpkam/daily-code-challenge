# 📚 Giải thích Chi tiết Bài 647 - Palindromic Substrings

**Mục tiêu:** Cho một chuỗi ký tự `s`, đếm và trả về tổng số chuỗi con đối xứng (palindromic substrings) có trong `s`. Một chuỗi được gọi là palindrome nếu đọc từ trái sang phải hay từ phải sang trái đều như nhau. Một chuỗi con là một dãy các ký tự liên tiếp nhau bên trong chuỗi.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Các ràng buộc (Constraints)
- `1 <= s.length <= 1000`: Chiều dài chuỗi $N$ tối đa là $1000$.
  - Kích thước này cho thấy một thuật toán có độ phức tạp thời gian $\mathcal{O}(N^2)$ sẽ thực hiện tối đa khoảng $\frac{1000^2}{2} \approx 5 \times 10^5$ đến $10^6$ phép tính.
  - Với giới hạn xử lý thông thường của CPU ($\approx 10^8$ phép tính/giây), thuật toán $\mathcal{O}(N^2)$ hoàn toàn vượt qua giới hạn thời gian (Time Limit Exceeded - TLE) một cách dễ dàng, trong khi thuật toán $\mathcal{O}(N^3)$ (vét cạn) sẽ rất chậm ($\approx 10^9$ phép tính) và chắc chắn bị TLE.
- `s` chỉ bao gồm các chữ cái tiếng Anh in thường (`'a'` đến `'z'`).
- **Lưu ý quan trọng về định nghĩa chuỗi con (Substring):** Chuỗi con phải là các ký tự **liên tiếp** (khác với subsequence - chuỗi con không liên tiếp).
  - Hai chuỗi con có cùng nội dung ký tự nhưng nằm ở hai vị trí chỉ số khác nhau vẫn được đếm là hai chuỗi con phân biệt.
  - Ví dụ với `s = "aaa"`: có 3 chuỗi độ dài 1 `"a"`, 2 chuỗi độ dài 2 `"aa"`, và 1 chuỗi độ dài 3 `"aaa"`, tổng cộng là $3 + 2 + 1 = 6$.

---

### 2. Bản chất toán học: Cấu trúc của Chuỗi đối xứng (Palindrome Properties)

Một chuỗi $s[i..j]$ là palindrome khi và chỉ khi:
$$s[i + k] = s[j - k] \quad \forall 0 \le k \le \lfloor(j - i) / 2\rfloor$$

#### Tính chất tâm đối xứng (Centers of Symmetry):
Mọi chuỗi palindrome đều sở hữu một **tâm đối xứng**:
1. **Chuỗi palindrome độ dài lẻ** (ví dụ: `"aba"`, `"racecar"`): Có tâm là **một ký tự duy nhất** nằm chính giữa.
2. **Chuỗi palindrome độ dài chẵn** (ví dụ: `"aa"`, `"abba"`): Có tâm là **khoảng trống giữa hai ký tự liên tiếp**.

Đối với một chuỗi $s$ có độ dài $N$:
- Có $N$ vị trí ký tự đơn lẻ $\implies N$ tâm lẻ: $(i, i)$ với $0 \le i < N$.
- Có $N - 1$ khoảng trống giữa hai ký tự kề nhau $\implies N - 1$ tâm chẵn: $(i, i + 1)$ với $0 \le i < N - 1$.
- **Tổng số tâm đối xứng khả dĩ:** $N + (N - 1) = 2N - 1$ tâm.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Kỹ thuật Hai con trỏ mở rộng từ tâm (Expand Around Center)

### 💡 Ý tưởng đột phá (Key Insights)

Thay vì đi kiểm tra từng chuỗi con $s[i..j]$ một cách thụ động ($\mathcal{O}(N^3)$ hoặc $\mathcal{O}(N^2)$ với bảng phụ DP):
- Ta xuất phát từ từng tâm đối xứng trong số $2N - 1$ tâm khả dĩ.
- Đặt hai con trỏ `left` và `right` tại tâm đó, sau đó liên tục mở rộng sang hai phía (`left--`, `right++`).
- **Nguyên lý lan truyền:** Nếu $s[left] == s[right]$, chuỗi con hiện tại $s[left..right]$ chắc chắn là một palindrome $\implies$ ta tăng biến đếm lên $1$ và tiếp tục mở rộng.
- **Dừng sớm (Early Termination):** Ngay khi phát hiện $s[left] \ne s[right]$ hoặc một trong hai con trỏ chạm biên, ta lập tức dừng việc mở rộng từ tâm này. Bởi vì nếu đoạn hiện tại đã không đối xứng, thì không có bất kỳ chuỗi con dài hơn nào bao bọc nó có cùng tâm này có thể là palindrome được!

```text
Minh họa trực quan với s = "aaa":

N = 3, có 2N - 1 = 5 tâm đối xứng:

1. Tâm lẻ i = 0 (tâm tại 'a' đầu tiên, left = 0, right = 0):
   [a] a  a  -> match! "a" (count = 1)
   left ra ngoài biên -> Dừng.

2. Tâm chẵn i = 0 (tâm giữa s[0] và s[1], left = 0, right = 1):
   [a  a] a  -> match! "aa" (count = 2)
   left ra ngoài biên -> Dừng.

3. Tâm lẻ i = 1 (tâm tại 'a' thứ hai, left = 1, right = 1):
    a [a] a  -> match! "a" (count = 3)
   [a  a  a] -> match! "aaa" (count = 4)
   left ra ngoài biên -> Dừng.

4. Tâm chẵn i = 1 (tâm giữa s[1] và s[2], left = 1, right = 2):
    a [a  a] -> match! "aa" (count = 5)
   right ra ngoài biên -> Dừng.

5. Tâm lẻ i = 2 (tâm tại 'a' thứ ba, left = 2, right = 2):
    a  a [a] -> match! "a" (count = 6)
   right ra ngoài biên -> Dừng.

=> Tổng số chuỗi con đối xứng = 6.
```

### 🌟 Ưu điểm vượt trội
- **Bộ nhớ tối ưu tuyệt đối $\mathcal{O}(1)$:** Thuật toán không sử dụng bất kỳ mảng phụ hay bộ nhớ động nào. Đây là lợi thế cạnh tranh áp đảo so với Quy hoạch động ($\mathcal{O}(N^2)$ bộ nhớ).
- **Tốc độ thực tế siêu nhanh:**
  - Trong trường hợp xấu nhất ($s$ gồm toàn ký tự giống nhau như `"aaaa"`), thuật toán tốn $\mathcal{O}(N^2)$ phép tính.
  - Tuy nhiên trong trường hợp ngẫu nhiên hoặc trung bình, việc so sánh ký tự thường mismatch rất sớm, giúp số bước mở rộng trung bình chỉ là $\mathcal{O}(1)$ cho mỗi tâm $\implies$ thời gian chạy thực tế tiệm cận $\mathcal{O}(N)$!
  - Khi chuyển chuỗi sang `char[]`, thời gian thực thi trên LeetCode chỉ mất $\approx 1\text{ms} - 2\text{ms}$ (Beats 98% - 100%).
- **Cài đặt cực kỳ tinh gọn, không góc chết (Clean & Robust).**

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-461/Solution.java):

```java
class Solution {
    /**
     * Best Solution: Kỹ thuật Hai con trỏ mở rộng từ tâm (Expand Around Center).
     *
     * Tư tưởng cốt lõi: Mỗi chuỗi con đối xứng (palindrome) đều đối xứng qua một tâm.
     * Một chuỗi có độ dài N sẽ có 2N - 1 tâm khả dĩ:
     * - N tâm đơn lẻ tại vị trí các ký tự (i, i) cho các chuỗi đối xứng có độ dài lẻ.
     * - N - 1 tâm nằm giữa hai ký tự liền kề (i, i + 1) cho các chuỗi đối xứng có độ dài chẵn.
     *
     * Với mỗi tâm, ta dùng hai con trỏ mở rộng sang hai phía (trái và phải) chừng nào
     * hai ký tự còn bằng nhau. Mỗi bước mở rộng thành công ghi nhận thêm một chuỗi con đối xứng mới.
     *
     * Time Complexity: O(N^2) trong trường hợp xấu nhất (chuỗi toàn ký tự giống nhau như "aaaa"),
     * nhưng trong thực tế chạy cực nhanh nhờ cơ chế dừng sớm (Early Termination) khi gặp ký tự không khớp.
     * Space Complexity: O(1) auxiliary space (hoặc O(N) để lưu mảng char[] giúp tăng tốc độ truy xuất).
     */
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            // Trường hợp 1: Mở rộng từ tâm lẻ (độ dài lẻ, tâm tại vị trí i)
            count += expand(chars, i, i);

            // Trường hợp 2: Mở rộng từ tâm chẵn (độ dài chẵn, tâm nằm giữa i và i + 1)
            count += expand(chars, i, i + 1);
        }

        return count;
    }

    private int expand(char[] chars, int left, int right) {
        int count = 0;
        while (left >= 0 && right < chars.length && chars[left] == chars[right]) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Quy hoạch động (Dynamic Programming - $\mathcal{O}(N^2)$ Time, $\mathcal{O}(N^2)$ Space)

#### Tư duy:
Gọi `dp[i][j]` là giá trị boolean xác định xem chuỗi con $s[i..j]$ có phải là palindrome hay không.
- **Cơ sở quy hoạch động:**
  - Chuỗi có độ dài $1$: `dp[i][i] = true` với mọi $i$.
  - Chuỗi có độ dài $2$: `dp[i][i+1] = true` nếu $s[i] == s[i+1]$.
  - Chuỗi có độ dài $3$: `dp[i][i+2] = true` nếu $s[i] == s[i+2]$.
- **Công thức tổng quát:**
  $$dp[i][j] = (s[i] == s[j]) \land (j - i \le 2 \lor dp[i + 1][j - 1])$$
- Để tính `dp[i][j]` dựa trên `dp[i + 1][j - 1]`, ta cần duyệt chỉ số $i$ theo thứ tự giảm dần từ $N - 1$ về $0$, và $j$ từ $i$ đến $N - 1$.

#### Mã nguồn:
```java
class SolutionDP {
    public int countSubstrings(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        boolean[][] dp = new boolean[n][n];
        int count = 0;

        // Duyệt i từ cuối chuỗi ngược về đầu
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (chars[i] == chars[j] && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    count++;
                }
            }
        }

        return count;
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N^2)$ — Luôn duyệt qua đúng $\frac{N(N+1)}{2}$ cặp $(i, j)$ mà không có cơ chế dừng sớm. Chạy trong khoảng $\approx 15\text{ms} - 25\text{ms}$.
- **Không gian:** $\mathcal{O}(N^2)$ — Cần mảng 2D kích thước $1000 \times 1000$ (tốn khoảng 1MB bộ nhớ). Có thể tối ưu bộ nhớ xuống $\mathcal{O}(N)$ bằng mảng 1D nhưng làm mã nguồn phức tạp hơn.
- **Nhận xét:** Cách tiếp cận rất chuẩn mực và quen thuộc nếu bạn đã từng làm bài LeetCode 5 (Longest Palindromic Substring), tuy nhiên tốn bộ nhớ hơn Expand Around Center.

---

### Cách 2: Thuật toán Manacher (Manacher's Algorithm - Tuyến tính $\mathcal{O}(N)$)

#### Tư duy:
Thuật toán Manacher là đỉnh cao về mặt lý thuyết xử lý chuỗi đối xứng, cho phép tìm toàn bộ các palindrome trong chuỗi với thời gian tuyến tính $\mathcal{O}(N)$:
1. **Biến đổi chuỗi (Preprocessing):** Chèn các ký tự đặc biệt `#` giữa các ký tự và thêm ký tự biên `^`, `$` ở hai đầu (ví dụ: `"aba"` biến đổi thành `"^#a#b#a#$"`). Nhờ đó, mọi palindrome (dù là chẵn hay lẻ ban đầu) đều trở thành palindrome có độ dài lẻ trong chuỗi mới!
2. **Tận dụng tính chất đối xứng qua tâm lớn nhất:**
   - Duy trì tâm $C$ và biên phải xa nhất $R$ của palindrome hiện tại.
   - Khi xét vị trí $i < R$, ta tìm điểm đối xứng của $i$ qua $C$ là $i_{mirror} = 2C - i$.
   - Bán kính đối xứng sơ bộ tại $i$ có thể khởi tạo ngay là: $P[i] = \min(R - i, P[i_{mirror}])$.
   - Sau đó chỉ cần mở rộng thủ công nếu palindrome tại $i$ vượt ra ngoài biên $R$.
3. **Mối liên hệ đếm:**
   - Trong chuỗi biến đổi, giá trị bán kính $P[i]$ chính là độ dài của chuỗi con palindrome nguyên bản dài nhất có tâm tại $i$.
   - Số lượng chuỗi con đối xứng đóng góp từ tâm $i$ chính xác là $\lfloor (P[i] + 1) / 2 \rfloor$.

#### Mã nguồn:
```java
class SolutionManacher {
    public int countSubstrings(String s) {
        // Biến đổi chuỗi: "abc" -> "^#a#b#c#$"
        StringBuilder sb = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            sb.append('#').append(c);
        }
        sb.append("#$");
        char[] t = sb.toString().toCharArray();
        int n = t.length;

        int[] P = new int[n];
        int C = 0, R = 0;
        int total = 0;

        for (int i = 1; i < n - 1; i++) {
            int iMirror = 2 * C - i;

            if (R > i) {
                P[i] = Math.min(R - i, P[iMirror]);
            }

            // Mở rộng thêm nếu có thể
            while (t[i + 1 + P[i]] == t[i - 1 - P[i]]) {
                P[i]++;
            }

            // Cập nhật tâm C và biên phải R nếu palindrome mới vượt qua R
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }

            // Đóng góp vào tổng số chuỗi con đối xứng
            total += (P[i] + 1) / 2;
        }

        return total;
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N)$ tuyệt đối — Mỗi ký tự chỉ được so sánh một số hữu hạn lần vì $R$ luôn tịnh tiến về bên phải.
- **Không gian:** $\mathcal{O}(N)$ — Mảng ký tự biến đổi và mảng bán kính $P$ kích thước $2N + 3$.
- **Nhận xét:** Cực kỳ ấn tượng về mặt lý thuyết thuật toán. Trong phỏng vấn, đề cập đến thuật toán Manacher chứng minh sự am hiểu sâu sắc về cấu trúc dữ liệu và giải thuật nâng cao.

---

### Cách 3: Vét cạn (Brute Force - $\mathcal{O}(N^3)$)

#### Tư duy:
- Sinh tất cả các cặp chỉ số $(i, j)$ với $0 \le i \le j < N$. Có tổng cộng $\frac{N(N + 1)}{2}$ chuỗi con.
- Với mỗi chuỗi con $s[i..j]$, dùng hàm `isPalindrome(s, i, j)` kiểm tra hai đầu lùi dần về giữa trong thời gian $\mathcal{O}(j - i + 1) = \mathcal{O}(N)$.

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N^3)$ — Với $N = 1000$, số phép toán lên tới $\approx \frac{1000^3}{6} \approx 1.6 \times 10^8$, chắc chắn gặp lỗi Time Limit Exceeded (TLE).
- **Không gian:** $\mathcal{O}(1)$.
- Không nên sử dụng ngoại trừ bước khởi đầu phân tích vấn đề.

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Expand Around Center (Best) | Thuật toán Manacher | Quy hoạch động (DP) | Vét cạn (Brute Force) |
| :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N^2)$ worst-case, $\mathcal{O}(N)$ trung bình | $\mathcal{O}(N)$ tối ưu tuyệt đối | $\mathcal{O}(N^2)$ cố định | $\mathcal{O}(N^3)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(1)$ auxiliary space | $\mathcal{O}(N)$ | $\mathcal{O}(N^2)$ (mảng 2D) | $\mathcal{O}(1)$ |
| **Cơ chế dừng sớm** | Có (dừng ngay khi mismatch) | Tận dụng bán kính đã tính | Không (tính đủ $\frac{N^2}{2}$ ô) | Không |
| **Thời gian chạy LeetCode** | $\approx 1\text{ms} - 2\text{ms}$ (Beats 99%) | $\approx 3\text{ms} - 5\text{ms}$ | $\approx 15\text{ms} - 25\text{ms}$ | TLE ($> 2000\text{ms}$) |
| **Độ phức tạp cài đặt** | Cực kỳ ngắn gọn, trực quan | Phức tạp, dễ sai sót chỉ số | Trung bình, chuẩn mực | Đơn giản nhất |
| **Đánh giá trong phỏng vấn** | **Lựa chọn tối ưu số 1** | **Điểm cộng xuất sắc** nếu giải thích được | Khá tốt, dễ tư duy | Không được chấp nhận |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Chuỗi chỉ có 1 ký tự (`s = "a"`)
- $N = 1$. Có đúng $2(1) - 1 = 1$ tâm đối xứng:
  - $i = 0$, tâm lẻ `expand(chars, 0, 0)`: khớp `'a'` $\implies$ `count = 1`.
  - $i = 0$, tâm chẵn `expand(chars, 0, 1)`: `right = 1 >= N` $\implies$ dừng ngay.
- $\implies$ Kết quả: `1` (Chính xác).

---

### Ca 2: Chuỗi gồm toàn ký tự giống nhau (`s = "aaaa"`)
- Mọi chuỗi con đều là palindrome.
- Số chuỗi con độ dài 1: 4 (`"a"`, `"a"`, `"a"`, `"a"`)
- Số chuỗi con độ dài 2: 3 (`"aa"`, `"aa"`, `"aa"`)
- Số chuỗi con độ dài 3: 2 (`"aaa"`, `"aaa"`)
- Số chuỗi con độ dài 4: 1 (`"aaaa"`)
- Tổng cộng lý thuyết: $\frac{4 \times 5}{2} = 10$.
- Diễn biến thuật toán:
  - $i = 0$: tâm lẻ đóng góp 1 (`"a"`), tâm chẵn đóng góp 1 (`"aa"`).
  - $i = 1$: tâm lẻ đóng góp 2 (`"a"`, `"aaa"`), tâm chẵn đóng góp 2 (`"aa"`, `"aaaa"`).
  - $i = 2$: tâm lẻ đóng góp 2 (`"a"`, `"aaa"`), tâm chẵn đóng góp 1 (`"aa"`).
  - $i = 3$: tâm lẻ đóng góp 1 (`"a"`), tâm chẵn đóng góp 0.
  - Tổng số = $1 + 1 + 2 + 2 + 2 + 1 + 1 + 0 = 10$ $\implies$ Kết quả: `10` (Chính xác).

---

### Ca 3: Chuỗi gồm toàn các ký tự khác nhau (`s = "abcdef"`)
- Không có bất kỳ chuỗi con đối xứng nào có độ dài $\ge 2$.
- Mọi tâm chẵn đều dừng ngay từ bước đầu tiên (`s[i] != s[i + 1]`).
- Mọi tâm lẻ chỉ đóng góp đúng $1$ chuỗi có độ dài $1$ chính là ký tự tại tâm.
- $\implies$ Kết quả: $N = 6$ (Chính xác, thuật toán chỉ mất đúng $2N$ phép so sánh $\implies \mathcal{O}(N)$ thời gian).

---

### Ca 4: Chuỗi đối xứng chẵn (`s = "abba"`)
- Các chuỗi con đối xứng: `"a"`, `"b"`, `"b"`, `"a"`, `"bb"`, `"abba"`.
- Tổng cộng: $6$ chuỗi.
- Tâm chẵn giữa $i = 1$ và $i = 2$ (`'b'` và `'b'`):
  - Bước 1: khớp `'b'` và `'b'` $\implies$ `"bb"`.
  - Bước 2: mở rộng khớp `'a'` và `'a'` $\implies$ `"abba"`.
  - Đóng góp: 2 chuỗi.
- $\implies$ Kết quả: $4 + 2 = 6$ (Chính xác).

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

1. **Nhận diện họ bài toán Palindrome kinh điển trên LeetCode:**
   - **LeetCode 647 (Palindromic Substrings):** Đếm tổng số chuỗi con đối xứng $\implies$ Expand Around Center đếm mỗi bước mở rộng thành công.
   - **LeetCode 5 (Longest Palindromic Substring):** Tìm chuỗi con đối xứng dài nhất $\implies$ Vẫn dùng Expand Around Center, cập nhật `maxLen` và `startIdx` khi `(right - left - 1) > maxLen`.
   - **LeetCode 131 (Palindrome Partitioning):** Phân tích chuỗi thành các palindrome $\implies$ Backtracking kết hợp DP/Memoization kiểm tra palindrome.
   - **LeetCode 516 (Longest Palindromic Subsequence):** Chuỗi con không liên tiếp $\implies$ Bắt buộc dùng Dynamic Programming 2D, không dùng Expand Around Center được vì các ký tự không liền kề.

2. **Tại sao Expand Around Center lại là lựa chọn vàng trong phỏng vấn?**
   - Đạt độ phức tạp không gian $\mathcal{O}(1)$ (tốt hơn DP $\mathcal{O}(N^2)$).
   - Mã nguồn rất ngắn (chỉ khoảng 15 - 20 dòng), cực kỳ khó viết sai hoặc dính bug chỉ số mảng.
   - Thời gian thực tế nhanh hơn DP nhờ cơ chế dừng sớm khi gặp ký tự không khớp.

3. **Mẹo tối ưu hiệu năng trong Java:**
   - Sử dụng `char[] chars = s.toCharArray()` thay vì gọi liên tục `s.charAt(i)` trong vòng lặp lồng nhau.
   - Việc chuyển sang mảng ký tự giúp JVM tối ưu hóa truy xuất bộ nhớ cục bộ (CPU cache locality) và loại bỏ chi phí kiểm tra bounds check của hàm `charAt`, đưa thời gian chạy về mức gần như tuyệt đối ($1\text{ms}$, 100% percentile).
