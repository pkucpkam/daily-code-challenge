# 📚 Giải thích Chi tiết Bài 680 - Valid Palindrome II

**Mục tiêu:** Cho một chuỗi `s`, xác định xem có thể biến chuỗi `s` thành chuỗi đối xứng (palindrome) sau khi xóa **tối đa một ký tự** hay không.

---

## 🎯 Phân tích bài toán

### 1. Bản chất của chuỗi đối xứng (Palindrome)
- Một chuỗi là **palindrome** nếu đọc từ trái sang phải hay từ phải sang trái đều như nhau (ví dụ: `"aba"`, `"racecar"`).
- Về mặt chỉ số: với chuỗi có độ dài $n$, ta có $s[i] == s[n - 1 - i]$ với mọi $0 \le i < \lfloor n / 2 \rfloor$.
- Kỹ thuật tự nhiên và tối ưu nhất để kiểm tra một chuỗi có đối xứng hay không là sử dụng **Hai con trỏ (Two Pointers)**:
  - Con trỏ `left` bắt đầu từ đầu chuỗi (`0`), con trỏ `right` bắt đầu từ cuối chuỗi (`n - 1`).
  - Lần lượt di chuyển về phía tâm: `left++`, `right--` và kiểm tra xem từng cặp $s[left]$ và $s[right]$ có bằng nhau hay không.

---

### 2. Điểm mấu chốt: Cho phép xóa tối đa 1 ký tự
- Khi duyệt hai con trỏ từ ngoài vào trong:
  - **Trường hợp 1:** Chừng nào $s[left] == s[right]$, hai ký tự này đã hoàn toàn khớp nhau để tạo nên tính đối xứng của chuỗi. Ta không có lý do gì để xóa một trong hai ký tự này vì việc xóa sẽ làm giảm cơ hội đối xứng của các ký tự còn lại mà không đem lại lợi ích gì (lựa chọn tham lam - Greedy Choice).
  - **Trường hợp 2:** Khi gặp cặp ký tự khác nhau đầu tiên ($s[left] \ne s[right]$):
    - Vì bài toán cho phép xóa **nhiều nhất 1 ký tự**, nên ký tự "gây lỗi" này bắt buộc phải là một trong hai ký tự đang xét: hoặc là $s[left]$, hoặc là $s[right]$.
    - Không thể có chuyện xóa một ký tự nào khác bên trong mà sửa được sự bất đồng giữa $s[left]$ và $s[right]$.
    - Do đó, ta chỉ có đúng **2 lựa chọn duy nhất**:
      1. Bỏ qua (xóa) ký tự tại $left$: kiểm tra xem chuỗi con từ $left + 1$ đến $right$ có là palindrome hay không.
      2. Bỏ qua (xóa) ký tự tại $right$: kiểm tra xem chuỗi con từ $left$ đến $right - 1$ có là palindrome hay không.
    - Nếu ít nhất một trong hai chuỗi con trên là palindrome, thì câu trả lời là `true`.
    - Nếu cả hai chuỗi con đều không phải palindrome, thì không có cách nào xóa 1 ký tự để chuỗi thành đối xứng, trả về `false`.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le s.length \le 10^5$.
- `s` chỉ gồm các chữ cái tiếng Anh in thường.
- Với $N \le 10^5$, một thuật toán có độ phức tạp thời gian $\mathcal{O}(N^2)$ (như vét cạn thử xóa từng ký tự) sẽ thực hiện tới $\approx 10^{10}$ phép tính, chắc chắn gặp lỗi **Time Limit Exceeded (TLE)**. Ta bắt buộc phải áp dụng thuật toán tối ưu tuyến tính $\mathcal{O}(N)$.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Vét cạn (Brute Force) | Cách 2: Hai con trỏ + Tham lam (Two Pointers & Greedy ⭐) |
| :--- | :--- | :--- |
| **Ý tưởng** | Thử xóa lần lượt từng ký tự tại vị trí $i$ ($0 \le i < n$), sau đó kiểm tra chuỗi mới có phải palindrome không | Dùng 2 con trỏ từ ngoài vào, chỉ khi gặp ký tự khác nhau đầu tiên mới rẽ 2 nhánh kiểm tra đoạn con |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ (cắt chuỗi con) | $\mathcal{O}(1)$ |
| **Kết quả thực tế** | **TLE** trên LeetCode ($N = 10^5$) | **$100\%$ Accepted**, tốc độ nhanh nhất ($< 5\text{ ms}$) |

---

## 📐 Chứng minh tính đúng đắn (Correctness Proof)

### Bổ đề 1: Tính chất tham lam khi hai ký tự hai đầu trùng nhau
Nếu chuỗi $s$ có tiền tố và hậu tố trùng nhau: $s = c + s' + c$, thì việc xóa tối đa 1 ký tự để biến $s$ thành palindrome tương đương với việc xóa tối đa 1 ký tự để biến $s'$ thành palindrome.

**Chứng minh:**
- Nếu xóa 1 ký tự trong $s'$ mà $s'$ trở thành palindrome $P$, thì $s$ sau khi xóa ký tự đó sẽ là $c + P + c$, rõ ràng cũng là palindrome.
- Giả sử ngược lại, ta xóa ký tự $c$ ở đầu hoặc ở cuối. Khi đó ta đã tiêu tốn quyền xóa duy nhất, và chuỗi còn lại là $c + s'$ (hoặc $s' + c$). Để chuỗi này là palindrome, nó phải đối xứng hoàn toàn mà không được xóa thêm ký tự nào nữa. Tuy nhiên, nếu $c + s'$ là palindrome thì $s'$ cũng có thể tạo thành đối xứng dễ dàng hơn khi giữ lại cả hai ký tự $c$ ban đầu. Do đó, việc giữ lại cặp ký tự khớp $s[left] == s[right]$ không bao giờ loại bỏ bất kỳ phương án hợp lệ tối ưu nào. ∎

### Bổ đề 2: Tính độc nhất của phân nhánh tại điểm không khớp đầu tiên
Giả sử tại vị trí `left` và `right`, ta gặp $s[left] \ne s[right]$ lần đầu tiên.
Vì trước đó mọi cặp ký tự từ ngoài vào đều bằng nhau ($s[0] == s[n-1], s[1] == s[n-2], \dots$), chuỗi chỉ có thể trở thành palindrome nếu ký tự không khớp nằm ở vị trí `left` hoặc `right`. 
- Nếu xóa $s[left]$, đoạn cần kiểm tra là $s[left + 1 \dots right]$.
- Nếu xóa $s[right]$, đoạn cần kiểm tra là $s[left \dots right - 1]$.
Vì quyền xóa 1 ký tự đã được sử dụng hết, chuỗi con được chọn bắt buộc phải là một palindrome nghiêm ngặt (không được xóa thêm ký tự nào). ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Hai Con Trỏ O(N)

### 💡 Ý tưởng thuật toán

1. Khởi tạo hai con trỏ `left = 0` và `right = s.length() - 1`.
2. Dùng vòng lặp `while (left < right)`:
   - Nếu `s.charAt(left) == s.charAt(right)`: tiếp tục thu hẹp phạm vi `left++`, `right--`.
   - Nếu `s.charAt(left) != s.charAt(right)`:
     - Gọi hàm phụ `isPalindrome(s, left + 1, right)` (thử xóa `left`).
     - Gọi hàm phụ `isPalindrome(s, left, right - 1)` (thử xóa `right`).
     - Nếu một trong hai nhánh trả về `true`, ta trả về `true`. Ngược lại trả về `false`.
3. Nếu duyệt hết vòng lặp mà không gặp cặp nào khác nhau (chuỗi vốn đã là palindrome), trả về `true`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ: `s = "abca"`

```text
Chỉ số:     0    1    2    3
Chuỗi:      a    b    c    a
            ^              ^
          left           right

Bước 1: s[left=0] == 'a', s[right=3] == 'a' (Khớp)
        -> left = 1, right = 2

Chỉ số:     0    1    2    3
Chuỗi:      a    b    c    a
                 ^    ^
               left  right

Bước 2: s[left=1] == 'b', s[right=2] == 'c' (Khác nhau!)
        Phân thành 2 nhánh:
        - Nhánh 1: Bỏ s[left] (ký tự 'b'), kiểm tra đoạn [2, 2] -> "c" là palindrome (TRUE)
        - Nhánh 2: Bỏ s[right] (ký tự 'c'), kiểm tra đoạn [1, 1] -> "b" là palindrome (TRUE)

Kết luận: TRUE (có thể xóa 'b' hoặc 'c')
```

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    public boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Thử bỏ qua ký tự bên trái hoặc ký tự bên phải
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }

        return true;
    }

    // Hàm phụ kiểm tra chuỗi con từ l đến r có là palindrome nghiêm ngặt hay không
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`int left = 0; int right = s.length() - 1;`**:
   - Khởi tạo 2 con trỏ ở hai đầu mút của chuỗi ban đầu.
2. **`while (left < right)`**:
   - Vòng lặp tiếp tục chừng nào hai con trỏ chưa gặp nhau hoặc vượt qua nhau.
3. **`if (s.charAt(left) != s.charAt(right))`**:
   - Phát hiện điểm sai khác đầu tiên. Tại đây ta phải quyết định xóa một ký tự.
4. **`return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);`**:
   - Toán tử `||` (short-circuit OR) sẽ kiểm tra nhánh xóa ký tự bên trái trước. Nếu nhánh này đúng, nhánh thứ hai không cần chạy nữa. Nếu nhánh đầu sai, ta kiểm tra tiếp nhánh xóa ký tự bên phải.
   - Truyền trực tiếp chỉ số `left` và `right` vào hàm phụ, tránh hoàn toàn chi phí tạo chuỗi con `substring()` (giúp tiết kiệm bộ nhớ $\mathcal{O}(1)$).
5. **`left++; right--;`**:
   - Nếu $s[left] == s[right]$, tiếp tục thu hẹp phạm vi kiểm tra vào phía trong.
6. **`return true;`**:
   - Nếu kết thúc vòng lặp mà không có điểm bất đồng nào, chuỗi đã là palindrome sẵn (xóa 0 ký tự), trả về `true`.
7. **`isPalindrome(String s, int left, int right)`**:
   - Hàm phụ kiểm tra tính đối xứng thông thường bằng kỹ thuật hai con trỏ tiêu chuẩn. Nếu có bất kỳ ký tự nào không khớp, lập tức trả về `false`.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N)$**:
  - Giai đoạn duyệt ban đầu: so sánh tối đa $N / 2$ cặp ký tự.
  - Khi gặp điểm không khớp đầu tiên, thuật toán gọi tối đa 2 lần hàm `isPalindrome`. Mỗi lần duyệt qua một đoạn con có độ dài nhỏ hơn $N$, mất tối đa $N$ phép so sánh.
  - Tổng số phép so sánh: $\le N/2 + 2 \times N = \mathcal{O}(N)$.
  - Với $N = 10^5$, thuật toán chạy tức thì ($< 5\text{ ms}$).

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(1)$**:
  - Không sử dụng thêm mảng hay chuỗi mới (không dùng `substring()`).
  - Toàn bộ thao tác chỉ dùng các biến nguyên thủy `left`, `right` trên chuỗi gốc, bộ nhớ phụ là hằng số.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Tránh tạo chuỗi mới (`substring()`):**
   - Rất nhiều ứng viên viết: `isPalindrome(s.substring(left + 1, right + 1))`. Việc này tiêu tốn thêm $\mathcal{O}(N)$ bộ nhớ cho mỗi lần cắt chuỗi và tăng thời gian chạy do thao tác sao chép bộ nhớ của JVM. Thay vào đó, hãy truyền chỉ số `(left, right)` vào hàm kiểm tra.
2. **Chiến lược Tham lam (Greedy Choice Property):**
   - Ta chỉ cần rẽ nhánh duy nhất **một lần** tại điểm không khớp đầu tiên. Không cần phải quay lui (backtracking) hay thử xóa các vị trí khác vì việc xóa tại các vị trí đã khớp không mang lại giải pháp tối ưu.
3. **Mở rộng bài toán (Follow-up Questions):**
   - *Nếu đề bài cho phép xóa tối đa $K$ ký tự thay vì $1$ ký tự thì sao?*
     - Ta có thể mở rộng hàm đệ quy có kèm tham số `k`: khi $s[left] \ne s[right]$, nếu $k > 0$, ta thử cả hai nhánh với $k - 1$. Độ phức tạp khi đó là $\mathcal{O}(2^K \cdot N)$.
     - Hoặc có thể chuyển sang Quy hoạch động (Dynamic Programming - bài toán biến thể của *Longest Palindromic Subsequence* / *Edit Distance*). Với $K = 1$, nhánh rẽ chỉ sâu tối đa 1 tầng nên thuật toán tham lam hai con trỏ là nhanh và tối ưu nhất.
