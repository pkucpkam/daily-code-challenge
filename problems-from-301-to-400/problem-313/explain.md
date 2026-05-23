# 378. Kth Smallest Element in a Sorted Matrix

**Độ khó:** Medium (Trung bình)

---

## 1. Đề Bài

Cho một ma trận kích thước $n \times n$, trong đó mỗi hàng và mỗi cột đều được sắp xếp theo thứ tự tăng dần. Hãy tìm và trả về phần tử nhỏ thứ $k$ trong ma trận này.

*Lưu ý:* Đây là phần tử nhỏ thứ $k$ theo thứ tự đã sắp xếp, chứ không phải là phần tử phân biệt (distinct) thứ $k$.

Yêu cầu tìm giải pháp có độ phức tạp bộ nhớ tốt hơn $O(n^2)$.

### Ví Dụ 1:
- **Đầu vào:** `matrix = [[1,5,9],[10,11,13],[12,13,15]]`, `k = 8`
- **Đầu ra:** `13`
- **Giải thích:** Các phần tử trong ma trận khi được viết dưới dạng mảng sắp xếp tăng dần là `[1, 5, 9, 10, 11, 12, 13, 13, 15]`. Phần tử nhỏ thứ 8 là `13`.

### Ví Dụ 2:
- **Đầu vào:** `matrix = [[-5]]`, `k = 1`
- **Đầu ra:** `-5`

### Ràng Buộc (Constraints):
- $n == \text{matrix.length} == \text{matrix}[i].length$
- $1 \le n \le 300$
- $-10^9 \le \text{matrix}[i][j] \le 10^9$
- Tất cả các hàng và cột của `matrix` được đảm bảo xếp theo thứ tự không giảm (tăng dần).
- $1 \le k \le n^2$

---

## 2. Các Hướng Tiếp Cận & Ý Tưởng Giải Quyết

Bài toán này có thể giải quyết bằng 3 phương pháp chính với sự đánh đổi về thời gian và bộ nhớ:

### Cách 1: Thuật toán Naive (Sắp xếp lại toàn bộ ma trận)
- Chuyển tất cả các phần tử trong ma trận $n \times n$ vào một mảng 1D, sắp xếp mảng đó và lấy phần tử ở chỉ số `k - 1`.
- **Độ phức tạp:** Thời gian $O(n^2 \log(n^2)) = O(n^2 \log n)$, Không gian $O(n^2)$ để lưu trữ mảng. Cách này vi phạm yêu cầu bộ nhớ tốt hơn $O(n^2)$.

### Cách 2: Sử dụng Min-Heap (Priority Queue)
- Vì các hàng được sắp xếp tăng dần, chúng ta có thể tối ưu hóa bằng cách đưa phần tử đầu tiên của mỗi hàng vào Min-Heap.
- Khi lấy (pop) phần tử nhỏ nhất ra khỏi Heap, ta lấy phần tử tiếp theo từ hàng tương ứng để đẩy vào Heap. Lặp lại quá trình này $k$ lần.
- **Độ phức tạp:** Thời gian $O(k \log n)$, Không gian $O(n)$ để lưu trữ Heap. Rất tốt khi $k$ nhỏ, nhưng khi $k$ tiến tới $n^2$ thì độ phức tạp thời gian vẫn là $O(n^2 \log n)$.

### Cách 3: Tìm Kiếm Nhị Phân trên Không Gian Giá Trị (Binary Search on Value Range) - **Tối Ưu Nhất!**
- Không giống như tìm kiếm nhị phân thông thường trên chỉ số (index), chúng ta tìm kiếm nhị phân trên **khoảng giá trị khả thi** từ cực tiểu `matrix[0][0]` đến cực đại `matrix[n-1][n-1]`.
- Với mỗi giá trị `mid`, chúng ta đếm xem có bao nhiêu phần tử trong ma trận nhỏ hơn hoặc bằng `mid` (gọi là hàm `countLessOrEqual`).
  - Do ma trận có các hàng và cột đã sắp xếp tăng dần, chúng ta có thể viết hàm đếm này chạy trong thời gian tối ưu $O(n)$ (sử dụng kỹ thuật hai con trỏ đi từ góc dưới bên trái hoặc góc trên bên phải ma trận).
- **Thuật toán tìm kiếm nhị phân:**
  - Nếu số phần tử $\le mid$ lớn hơn hoặc bằng $k$, điều đó có nghĩa là phần tử nhỏ thứ $k$ chắc chắn $\le mid$. Chúng ta thu hẹp phạm vi tìm kiếm sang nửa trái: `high = mid`.
  - Ngược lại, nếu số phần tử $\le mid$ nhỏ hơn $k$, phần tử nhỏ thứ $k$ phải nằm ở nửa phải: `low = mid + 1`.
- **Độ phức tạp:**
  - Thời gian: $O(n \log(\text{Max} - \text{Min}))$. Với giá trị phần tử tối đa khoảng $10^9$ và tối thiểu $-10^9$, khoảng cách là $2 \cdot 10^9$. Số lần lặp tìm kiếm nhị phân tối đa là $\log_2(2 \cdot 10^9) \approx 31$ lần. Do đó, thời gian chạy thực tế cực kỳ nhanh, gần như tuyến tính $O(n)$.
  - Không gian: $O(1)$ bổ sung (đáp ứng xuất sắc yêu cầu bộ nhớ hằng số).

---

## 3. Thuật Toán Hai Con Trỏ Đếm Số Phần Tử $\le$ `Target` trong $O(n)$

Hàm `countLessOrEqual(matrix, target)` hoạt động như sau:
1. Bắt đầu từ góc dưới cùng bên trái: hàng `r = n - 1`, cột `c = 0`.
2. Lặp lại cho đến khi vượt ra ngoài ma trận (`r >= 0` và `c < n`):
   - Nếu `matrix[r][c] <= target`: Có nghĩa là tất cả các phần tử từ dòng `0` đến dòng `r` trong cột `c` đều nhỏ hơn hoặc bằng `target` (vì cột đã được sắp xếp). Do đó, ta cộng thêm `r + 1` vào kết quả, và dịch sang cột bên phải: `c++`.
   - Nếu `matrix[r][c] > target`: Phần tử này lớn hơn `target`, do đó ta cần tìm phần tử nhỏ hơn ở hàng phía trên: `r--`.

---

## 4. Minh Họa Từng Bước Chạy (Dry Run)

Ví dụ với `matrix = [[1, 5, 9], [10, 11, 13], [12, 13, 15]]`, `k = 8`.
Khoảng giá trị tìm kiếm ban đầu: `low = 1`, `high = 15`.

| Bước tìm kiếm nhị phân | `mid` | Kết quả đếm số phần tử $\le$ `mid` | Cập nhật khoảng | Giải thích |
| :--- | :--- | :--- | :--- | :--- |
| **Khởi tạo** | - | - | `low = 1`, `high = 15` | - |
| **Vòng lặp 1** | `8` | `countLessOrEqual(matrix, 8) = 2` | `low = 9`, `high = 15` | Số phần tử $\le 8$ chỉ là 2 (`{1, 5}`), nhỏ hơn `k = 8`. |
| **Vòng lặp 2** | `12` | `countLessOrEqual(matrix, 12) = 6` | `low = 13`, `high = 15` | Số phần tử $\le 12$ là 6 (`{1, 5, 9, 10, 11, 12}`), nhỏ hơn `k = 8`. |
| **Vòng lặp 3** | `14` | `countLessOrEqual(matrix, 14) = 8` | `low = 13`, `high = 14` | Số phần tử $\le 14$ là 8 (`{1, 5, 9, 10, 11, 12, 13, 13}`), $\ge k$. |
| **Vòng lặp 4** | `13` | `countLessOrEqual(matrix, 13) = 8` | `low = 13`, `high = 13` | Số phần tử $\le 13$ là 8, $\ge k$. |
| **Kết thúc** | - | - | Vòng lặp dừng vì `low == high = 13` | Kết quả trả về `13`. |

---

## 5. Mã Nguồn Java Chi Tiết

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            int count = countLessOrEqual(matrix, mid);
            if (count >= k) {
                high = mid; // Thu hẹp về phía trái
            } else {
                low = mid + 1; // Thu hẹp về phía phải
            }
        }
        
        return low; // Khi low == high, ta tìm được phần tử nhỏ thứ k
    }
    
    private int countLessOrEqual(int[][] matrix, int target) {
        int n = matrix.length;
        int count = 0;
        int r = n - 1; // Bắt đầu từ góc dưới bên trái
        int c = 0;
        
        while (r >= 0 && c < n) {
            if (matrix[r][c] <= target) {
                // Tất cả phần tử từ hàng 0 đến r ở cột c đều <= target
                count += r + 1;
                c++; // Di chuyển sang cột bên phải
            } else {
                r--; // Di chuyển lên hàng trên
            }
        }
        
        return count;
    }
}
```

---

## 6. Phân Tích Độ Phức Tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(n \log(\text{Max} - \text{Min}))$
  - Trong đó $n$ là số hàng/cột của ma trận.
  - Phép tìm kiếm nhị phân thực hiện tối đa $\log_2(\text{Max} - \text{Min})$ bước. Ở mỗi bước, hàm `countLessOrEqual` duyệt ma trận từ dưới-trái lên trên-phải với tối đa $2n$ phép so sánh.
  - Với khoảng giá trị của LeetCode: $\text{Max} - \text{Min} \le 2 \times 10^9$, thuật toán chạy tối đa $31 \times (2n)$ phép tính, nhanh hơn rất nhiều so với phương pháp Heap khi $k$ lớn.
- **Độ phức tạp không gian (Space Complexity):** $O(1)$
  - Chỉ sử dụng một số biến phụ để lưu trữ con trỏ và đếm số lượng, không tiêu tốn thêm bộ nhớ động.

---

## 7. Giải Thích Tại Sao `low` Chắc Chắn Thuộc Ma Trận

Một thắc mắc rất phổ biến đối với thuật toán tìm kiếm nhị phân trên không gian giá trị là: **"Tại sao giá trị trả về `low` chắc chắn có mặt trong ma trận mà không phải là một số lơ lửng nào đó?"**

### Câu trả lời:
1. Thuật toán của chúng ta luôn thu hẹp phạm vi dựa trên thực tế:
   - Nếu `countLessOrEqual(mid) >= k`, điều này có nghĩa là có ít nhất $k$ phần tử nhỏ hơn hoặc bằng `mid`. Khi ta gán `high = mid`, ta giữ lại khả năng `mid` chính là đáp án, hoặc đáp án nhỏ hơn `mid`.
   - Nếu `countLessOrEqual(mid) < k`, phần tử nhỏ thứ $k$ chắc chắn phải lớn hơn `mid`, nên ta gán `low = mid + 1`.
2. Khi khoảng tìm kiếm thu hẹp về `low == high`, `low` chính là giá trị nhỏ nhất thỏa mãn `countLessOrEqual(low) >= k`.
3. Giả sử có một số $X$ không nằm trong ma trận nhưng thỏa mãn `countLessOrEqual(X) >= k`. Khi đó, phần tử thực tế lớn nhất trong ma trận mà $\le X$ (gọi là $Y$) cũng sẽ có cùng số lượng phần tử nhỏ hơn hoặc bằng nó, tức là `countLessOrEqual(Y) == countLessOrEqual(X) >= k`.
4. Vì $Y < X$ (do $X$ không có trong ma trận và $Y$ là phần tử có mặt lớn nhất $\le X$), trong quá trình tìm kiếm nhị phân, thuật toán sẽ liên tục thu hẹp khoảng về phía nhỏ hơn và cuối cùng sẽ dừng lại chính xác tại $Y$ (phần tử thực tế có mặt trong ma trận). Do đó, giá trị trả về `low` luôn được đảm bảo thuộc ma trận.