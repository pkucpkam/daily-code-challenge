# 📚 Giải thích Chi tiết Bài 674 - Longest Continuous Increasing Subsequence

**Mục tiêu:** Cho một mảng số nguyên chưa được sắp xếp `nums`. Hãy tìm và trả về độ dài của dãy con tăng liên tục (tức mảng con - subarray) dài nhất. Dãy con phải tăng nghiêm ngặt (strictly increasing).

---

## 🎯 Phân tích bài toán

### 1. Phân biệt Subsequence (Dãy con) và Subarray (Mảng con liên tục)
Đây là điểm mấu chốt quan trọng nhất để tránh nhầm lẫn với các bài toán LIS khác:
- **Subsequence (Dãy con):** Được tạo thành bằng cách xóa đi 0 hoặc nhiều phần tử mà không làm thay đổi thứ tự tương đối của các phần tử còn lại. Các phần tử **không bắt buộc phải đứng liền kề nhau** trong mảng gốc (ví dụ trong `[1, 3, 5, 4, 7]`, dãy `[1, 3, 5, 7]` là một subsequence tăng hợp lệ).
- **Continuous Increasing Subsequence (Dãy con tăng liên tục / Subarray):** Bắt buộc các phần tử phải **nằm liên tiếp kề nhau** trong bộ nhớ từ chỉ số $l$ đến $r$:
  $$[nums[l], nums[l+1], \dots, nums[r-1], nums[r]]$$
  thỏa mãn điều kiện tăng nghiêm ngặt:
  $$\forall i \in [l, r - 1], \quad nums[i] < nums[i+1]$$

---

### 2. Tính chất cục bộ của Tính liên tục (Local Property)
Vì các phần tử bắt buộc phải đứng liền kề nhau:
- Tính chất tăng ngặt của cả đoạn chỉ phụ thuộc vào việc **so sánh hai phần tử kề nhau $nums[i]$ và $nums[i - 1]$**:
  - Nếu $nums[i] > nums[i - 1]$: Đoạn tăng hiện tại được nối dài thêm 1 phần tử.
  - Nếu $nums[i] \le nums[i - 1]$: Đoạn tăng hiện tại bị đứt gãy. Một đoạn tăng mới bắt đầu ngay tại vị trí $i$ với độ dài khởi điểm là 1.
- Không cần dùng Quy hoạch động $\mathcal{O}(N^2)$ hay Cây phân đoạn $\mathcal{O}(N \log N)$ như bài 300 hay 673, ta có thể giải quyết bài toán chỉ với **một vòng lặp tuyến tính duy nhất $\mathcal{O}(N)$** và bộ nhớ hằng số $\mathcal{O}(1)$!

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le nums.length \le 10^4$.
- $-10^9 \le nums[i] \le 10^9$.
- Với $N \le 10^4$, thuật toán $\mathcal{O}(N)$ chỉ duyệt qua $10^4$ phần tử một lần, thời gian thực thi là tức thì ($< 1\text{ ms}$).

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Vét cạn (Brute Force) | Cách 2: Cửa sổ trượt (Sliding Window / Two Pointers) | Cách 3: Quét một lần với Biến đếm (One-Pass Counter ⭐) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Duyệt mọi cặp $(l, r)$ và kiểm tra tính tăng liên tục | Dùng con trỏ `anchor` lưu vị trí bắt đầu đoạn tăng, dời `anchor = i` khi bị đứt gãy | Dùng biến `curLen` đếm độ dài đoạn hiện tại, tăng lên khi $nums[i] > nums[i-1]$ hoặc reset về 1 |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Đánh giá** | Kém tối ưu, dư thừa | Tốt, trực quan theo góc nhìn chỉ số | **Ngắn gọn nhất, tối ưu tuyệt đối, chuẩn mực phỏng vấn** |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Quét Một Lần Tuyến Tính O(N)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Ta sử dụng 2 biến:
- `curLen`: Độ dài của dãy con tăng liên tục hiện tại đang xét (khởi tạo bằng 1 vì một phần tử đơn lẻ luôn có độ dài 1).
- `maxLen`: Độ dài lớn nhất từng ghi nhận được (khởi tạo bằng 1).

**Quy tắc cập nhật khi duyệt từ $i = 1$ đến $n - 1$:**
1. **Nếu $nums[i] > nums[i - 1]$:**
   - Dãy tăng tiếp tục duy trì $\implies$ `curLen++`.
   - Cập nhật lại kỷ lục: `maxLen = Math.max(maxLen, curLen)`.
2. **Nếu $nums[i] \le nums[i - 1]$:**
   - Dãy tăng bị đứt gãy $\implies$ Reset `curLen = 1` (bắt đầu chuỗi mới từ $nums[i]$).

Sau khi kết thúc vòng lặp, trả về `maxLen`.

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `nums = [1, 3, 5, 4, 7]`

```text
Chỉ số (i):     0       1       2       3       4
Giá trị:       [1]  <  [3]  <  [5]     [4]  <  [7]
                              \_______/
                             5 >= 4 (Đứt gãy)

Đoạn 1: [1, 3, 5] (Độ dài: 3)
Đoạn 2: [4, 7]    (Độ dài: 2)

-> Độ dài lớn nhất: max(3, 2) = 3
```

**Bảng trace chi tiết:**

| $i$ | $nums[i]$ | $nums[i-1]$ | So sánh | Hành động | `curLen` | `maxLen` |
| :---: | :---: | :---: | :---: | :--- | :---: | :---: |
| - | `nums[0] = 1` | - | Khởi tạo | - | `1` | `1` |
| **1** | `3` | `1` | $3 > 1$ | Kéo dài chuỗi | `2` | `2` |
| **2** | `5` | `3` | $5 > 3$ | Kéo dài chuỗi | `3` | `3` |
| **3** | `4` | `5` | $4 \le 5$ | **Đứt gãy!** Reset chuỗi mới | `1` | `3` |
| **4** | `7` | `4` | $7 > 4$ | Kéo dài chuỗi | `2` | `3` |

👉 Kết quả cuối cùng: `maxLen = 3`.

---

#### Ví dụ 2: `nums = [2, 2, 2, 2, 2]`

- Vì mọi $nums[i] == nums[i-1]$, điều kiện $nums[i] > nums[i-1]$ không bao giờ thỏa mãn.
- `curLen` liên tục bị reset về 1 ở mỗi bước.
- `maxLen` giữ nguyên bằng 1.
- 👉 Kết quả: `1`.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Quét một lần với Biến đếm (One-Pass Counter) ⭐

```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLen = 1;
        int curLen = 1;

        // Quét tuyến tính qua mảng từ phần tử thứ hai
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                curLen++;
                if (curLen > maxLen) {
                    maxLen = curLen;
                }
            } else {
                curLen = 1; // Chuỗi bị đứt gãy, bắt đầu chuỗi mới
            }
        }

        return maxLen;
    }
}
```

---

### Cách 2 (Tham khảo): Kỹ thuật Cửa sổ trượt (Sliding Window / Two Pointers)

```java
class SolutionSlidingWindow {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLen = 0;
        int anchor = 0; // Điểm neo đánh dấu vị trí bắt đầu của đoạn tăng hiện tại

        for (int i = 0; i < nums.length; i++) {
            // Nếu phát hiện đứt gãy tính tăng ngặt, dời mốc neo tới vị trí i
            if (i > 0 && nums[i - 1] >= nums[i]) {
                anchor = i;
            }
            // Độ dài đoạn tăng hiện tại là: i - anchor + 1
            maxLen = Math.max(maxLen, i - anchor + 1);
        }

        return maxLen;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

Dưới đây là chi tiết từng bước trong giải pháp tối ưu:

1. **`if (nums == null || nums.length == 0) return 0;`**:
   - Kiểm tra phòng thủ trường hợp mảng rỗng hoặc null.
2. **`int maxLen = 1; int curLen = 1;`**:
   - Vì mảng có ít nhất 1 phần tử (theo ràng buộc $1 \le nums.length$), bất kỳ phần tử đơn lẻ nào cũng tạo thành một đoạn tăng có độ dài tối thiểu là 1.
3. **`for (int i = 1; i < nums.length; i++)`**:
   - Bắt đầu duyệt từ vị trí thứ hai ($i = 1$) để so sánh với phần tử đứng liền trước ($i - 1$).
4. **`if (nums[i] > nums[i - 1])`**:
   - Điều kiện kiểm tra tính tăng nghiêm ngặt: $nums[i]$ phải lớn hơn hẳn $nums[i - 1]$.
5. **`curLen++; if (curLen > maxLen) maxLen = curLen;`**:
   - Mở rộng độ dài đoạn tăng hiện tại và lập tức cập nhật kỷ lục toàn cục nếu vượt qua `maxLen`.
6. **`else curLen = 1;`**:
   - Xử lý khi $nums[i] \le nums[i - 1]$ (không tăng hoặc bằng nhau): chuỗi tăng trước đó kết thúc, chuỗi tăng mới bắt đầu ngay tại $i$ với độ dài ban đầu là 1.
7. **`return maxLen;`**:
   - Trả về độ dài lớn nhất tìm được sau khi đã quét hết toàn bộ mảng.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- $\mathcal{O}(N)$:
  - Vòng lặp `for` chạy đúng $N - 1$ lần, với mỗi phần tử chỉ thực hiện một phép so sánh số nguyên và một vài phép gán giá trị cơ bản ($\mathcal{O}(1)$).
  - Với $N \le 10^4$, tổng số thao tác chỉ là $\approx 10^4$, thời gian chạy thực tế là $0\text{ ms}$ trên LeetCode (đánh bại 100% các bài nộp).

### Độ phức tạp Không gian (Space Complexity)
- $\mathcal{O}(1)$:
  - Thuật toán hoàn toàn không sử dụng mảng phụ, không đệ quy, không cấp phát thêm bộ nhớ.
  - Chỉ duy trì hai biến kiểu `int` nguyên thủy (`maxLen`, `curLen`), tiêu tốn $\mathcal{O}(1)$ bộ nhớ.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Đọc kỹ đề bài để phân biệt giữa Subsequence và Subarray:**
   - Rất nhiều ứng viên khi nhìn thấy chữ *Increasing Subsequence* liền nghĩ ngay đến thuật toán LIS phức tạp ($\mathcal{O}(N^2)$ hoặc $\mathcal{O}(N \log N)$).
   - Hãy chú ý từ khóa **Continuous** (hoặc **Subarray**) - tính liên tục làm bài toán trở nên đơn giản hơn rất nhiều và có thể giải quyết chỉ trong $\mathcal{O}(N)$ thời gian và $\mathcal{O}(1)$ bộ nhớ.

2. **Xử lý điều kiện biên (Edge cases):**
   - Mảng có 1 phần tử: Trả về 1 (không bao giờ duyệt vòng for).
   - Mảng giảm dần hoặc toàn bộ bằng nhau: `maxLen` luôn là 1.
   - Mảng tăng dần hoàn toàn: `curLen` tăng liên tục đến $N$, `maxLen = N`.

3. **Giao tiếp khi phỏng vấn:**
   - Nêu ngay nhận xét: *"Vì yêu cầu là continuous (liền kề), ta chỉ cần kiểm tra tính tăng giữa hai phần tử kề nhau $nums[i] > nums[i-1]$. Do đó bài toán có thể giải quyết bằng kỹ thuật Greedy / One-pass scan với $\mathcal{O}(N)$ time và $\mathcal{O}(1)$ space."*
   - Viết mã nguồn ngắn gọn, đặt tên biến rõ ràng (`curLen`, `maxLen`), thể hiện phong cách viết mã sạch (clean code).
