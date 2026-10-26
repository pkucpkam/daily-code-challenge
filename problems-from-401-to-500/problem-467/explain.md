# 📚 Giải thích Chi tiết Bài 654 - Maximum Binary Tree

**Mục tiêu:** Cho một mảng số nguyên `nums` không chứa các phần tử trùng lặp. Hãy xây dựng một **Cây Nhị Phân Cực Đại (Maximum Binary Tree)** theo thuật toán đệ quy:
1. Tạo một nút gốc có giá trị bằng giá trị lớn nhất trong mảng `nums`.
2. Đệ quy xây dựng cây con bên trái từ mảng con nằm bên trái giá trị lớn nhất.
3. Đệ quy xây dựng cây con bên phải từ mảng con nằm bên phải giá trị lớn nhất.
4. Trả về nút gốc của cây nhị phân vừa tạo.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Cây Cực Đại là Cây Descartes (Cartesian Tree)

Cây nhị phân cực đại trong bài toán này thực chất là một cấu trúc dữ liệu kinh điển mang tên **Cây Descartes (Cartesian Tree)**:
- **Đặc tính 1 (Heap Property):** Cây thỏa mãn tính chất Max-Heap: Giá trị của nút cha luôn lớn hơn giá trị của hai nút con.
- **Đặc tính 2 (BST Property on Indices):** Phép duyệt **Trung thứ tự (In-order Traversal: Trái $\to$ Gốc $\to$ Phải)** trên cây tái hiện chính xác thứ tự ban đầu của các phần tử trong mảng `nums`.

```text
                  Mảng nums ban đầu: [3, 2, 1, 6, 0, 5]
                                     │
                                     ▼
                        Cây Descartes (Max-Heap):
                                     6 (max toàn mảng)
                                   /   \
           (các phần tử bên trái) 3     5 (các phần tử bên phải)
                                   \   /
                                    2 0
                                     \
                                      1
         Duyệt In-order: 3 -> 2 -> 1 -> 6 -> 0 -> 5  ==  nums ban đầu!
```

---

### 2. Các hướng tiếp cận bài toán

```
                       [Bài toán: Xây dựng Cây Cực Đại]
                                      │
         ┌────────────────────────────┴────────────────────────────┐
         ▼                                                         ▼
  [Cách 1: Chia để trị / Đệ quy]                            [Cách 2: Ngăn xếp Đơn điệu]
  - Tìm max trên đoạn [l, r]                                - Duyệt mảng 1 lần từ trái sang phải
  - Đệ quy nửa trái và nửa phải                             - Stack duy trì các nút giảm dần
  - O(N^2) xấu nhất (mảng sắp xếp)                          - O(N) TUYẾN TÍNH TUYỆT ĐỐI ⭐
  - O(N) bộ nhớ Call Stack                                  - O(N) bộ nhớ Stack
```

#### ⚠️ Cách 1: Chia để trị trực tiếp theo đề bài (Divide & Conquer)
- Với mỗi đoạn con `[left, right]`:
  1. Quét tuyến tính qua đoạn để tìm chỉ số `maxIdx` của phần tử lớn nhất: $\mathcal{O}(\text{length})$.
  2. Tạo nút `root = new TreeNode(nums[maxIdx])`.
  3. Đệ quy xây dựng:
     $$\text{root.left} = \text{build}(nums, \text{left}, maxIdx - 1)$$
     $$\text{root.right} = \text{build}(nums, maxIdx + 1, \text{right})$$
- **Hạn chế lớn nhất:**
  - Nếu mảng đã được sắp xếp tăng dần (`[1, 2, 3, 4, 5]`) hoặc giảm dần (`[5, 4, 3, 2, 1]`):
    - Phần tử lớn nhất luôn nằm ở một đầu mút của mảng con.
    - Cây bị suy biến hoàn toàn thành một đường thẳng (chiều cao $H = N$).
    - Chi phí tìm kiếm phần tử cực đại:
      $$N + (N - 1) + (N - 2) + \dots + 1 = \frac{N(N + 1)}{2} = \mathcal{O}(N^2)$$
  - Với $N = 1000$, $\mathcal{O}(N^2) \approx 5 \times 10^5$ phép tính (chạy vừa kịp nhưng không tối ưu).

#### 💡 Đột phá: Ngăn xếp Đơn điệu Giảm dần (Monotonic Decreasing Stack)
- Thay vì xây dựng từ trên xuống (Top-down) và liên tục tìm kiếm cực đại $\mathcal{O}(N)$, ta duyệt mảng một lần từ trái sang phải và xây dựng cây từ dưới lên (**Bottom-up**)!
- Duy trì một ngăn xếp (`Stack`) chứa các nút có giá trị **giảm dần nghiêm ngặt** từ đáy lên đỉnh.
- Khi gặp phần tử mới `curr`:
  - Mọi nút trên đỉnh stack nhỏ hơn `curr` đều nằm ở **bên trái** `curr` trong mảng, nên chúng phải thuộc **cây con bên trái** của `curr`. Nút bị pop cuối cùng (nút lớn nhất trong số các nút nhỏ hơn `curr`) sẽ trở thành con trái trực tiếp của `curr`.
  - Nút còn lại trên đỉnh stack (nếu có) lớn hơn `curr` và nằm ở bên trái `curr`, nên `curr` sẽ trở thành **con phải trực tiếp** của nút đó!
- **Độ phức tạp vượt trội:** Mỗi phần tử được đưa vào stack đúng $1$ lần và lấy ra khỏi stack tối đa $1$ lần $\implies$ Thời gian chạy **$\mathcal{O}(N)$ tuyến tính tuyệt đối!**

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)

- $1 \le \text{nums.length} \le 1000$.
- $0 \le \text{nums}[i] \le 1000$.
- Tất cả các số trong `nums` đều là **duy nhất** (không có phần tử trùng lặp) $\implies$ Đảm bảo tính đơn điệu nghiêm ngặt và cây kết quả là duy nhất.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Ngăn xếp Đơn điệu Giảm dần (Monotonic Decreasing Stack)

### 💡 Ý tưởng thuật toán (Key Insights)

#### 1. Nguyên lý Bất biến của Ngăn xếp (Stack Invariant)
Ngăn xếp luôn lưu trữ các nút trên **nhánh phải nhất (Rightmost spine)** của cây hiện tại:
$$\text{stack.bottom}.\text{val} > \dots > \text{stack.top}.\text{val}$$
- Vì ta duyệt từ trái sang phải, bất kỳ phần tử mới nào `curr` cũng nằm ở bên phải tất cả các nút hiện có trong ngăn xếp.

#### 2. Xử lý khi gặp phần tử mới `curr = new TreeNode(num)`
1. **Tìm con trái của `curr`:**
   - Trong khi `!stack.isEmpty() && stack.peek().val < curr.val`:
     - Nút `node = stack.pop()` nhỏ hơn `curr` và nằm ở bên trái `curr`.
     - Vì ngăn xếp giảm dần từ đáy lên đỉnh, nút bị pop sau sẽ lớn hơn nút bị pop trước.
     - Do đó, nút bị pop **cuối cùng** sẽ là nút lớn nhất bên trái `curr` $\implies$ gán `curr.left = node`.
2. **Tìm cha của `curr`:**
   - Sau khi kết thúc vòng lặp pop:
     - Nếu ngăn xếp không rỗng: Nút `stack.peek()` là nút đầu tiên bên trái có giá trị lớn hơn `curr`. Theo nguyên tắc của cây cực đại, `curr` phải là con bên phải trực tiếp của nút này:
       $$\text{stack.peek().right} = curr$$
3. **Đẩy `curr` vào ngăn xếp:**
   - `stack.push(curr)`.

#### 3. Xác định Gốc của Cây (Root of the Tree)
- Sau khi duyệt hết mảng, đáy của ngăn xếp (phần tử lớn nhất trong toàn bộ mảng) chính là **nút gốc của toàn bộ Cây Cực Đại**.
- Với `ArrayDeque`, ta có thể truy xuất đáy ngăn xếp trong $\mathcal{O}(1)$ bằng phương thức `stack.peekLast()`.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

Xét **Ví dụ 1:** `nums = [3, 2, 1, 6, 0, 5]`

#### Bảng diễn biến từng bước qua Ngăn xếp:

| Bước ($i$) | Giá trị `num` | Các nút bị Pop (`stack.peek().val < num`) | Thiết lập Con trái (`curr.left`) | Thiết lập Con phải (`stack.peek().right`) | Trạng thái Stack (Đáy $\to$ Đỉnh) |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **0** | `3` | Không có (Stack rỗng) | `null` | Không có | `[3]` |
| **1** | `2` | Không có ($3 > 2$) | `null` | `Node(3).right = Node(2)` | `[3, 2]` |
| **2** | `1` | Không có ($2 > 1$) | `null` | `Node(2).right = Node(1)` | `[3, 2, 1]` |
| **3** | `6` | Pop `1`, Pop `2`, Pop `3` | `Node(6).left = Node(3)` | Không có (Stack rỗng) | `[6]` |
| **4** | `0` | Không có ($6 > 0$) | `null` | `Node(6).right = Node(0)` | `[6, 0]` |
| **5** | `5` | Pop `0` | `Node(5).left = Node(0)` | `Node(6).right = Node(5)` | `[6, 5]` |

---

#### Hình vẽ trực quan quá trình biến đổi cây:

##### 1. Sau khi xử lý `3, 2, 1`:
```text
      3
       \
        2
         \
          1
Stack: [3, 2, 1]
```

##### 2. Khi phần tử `6` xuất hiện:
- `6 > 1` $\implies$ Pop `1`, `curr.left = 1`.
- `6 > 2` $\implies$ Pop `2`, `curr.left = 2` (nút `2` đã chứa con phải là `1`).
- `6 > 3` $\implies$ Pop `3`, `curr.left = 3` (nút `3` đã chứa toàn bộ nhánh `3 -> 2 -> 1`).
- Stack rỗng $\implies$ Đẩy `6` vào:
```text
        6
       /
      3
       \
        2
         \
          1
Stack: [6]
```

##### 3. Khi phần tử `0` xuất hiện:
- $6 > 0 \implies$ `Node(6).right = Node(0)`.
- Stack: `[6, 0]`.

##### 4. Khi phần tử `5` xuất hiện:
- $5 > 0 \implies$ Pop `0`, `Node(5).left = Node(0)`.
- Đỉnh stack là `6` ($6 > 5$) $\implies$ `Node(6).right = Node(5)`.
- Đẩy `5` vào stack.

##### Cây hoàn chỉnh cuối cùng (Gốc là đáy stack: `6`):
```text
            6
          /   \
        3       5
         \     /
          2   0
           \
            1
```

---

## 💻 Phân tích mã nguồn chi tiết (Code Walkthrough)

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        // Dùng ArrayDeque làm ngăn xếp lưu trữ các nút theo thứ tự giảm dần
        Deque<TreeNode> stack = new ArrayDeque<>();

        for (int num : nums) {
            TreeNode curr = new TreeNode(num);

            // Pop các nút nhỏ hơn num:
            // Nút bị pop cuối cùng là nút lớn nhất trong số các nút nhỏ hơn num ở bên trái
            // => trở thành con trái trực tiếp của curr
            while (!stack.isEmpty() && stack.peek().val < num) {
                curr.left = stack.pop();
            }

            // Nút còn lại trên đỉnh stack là nút lớn hơn gần nhất ở bên trái num
            // => nhận curr làm con phải trực tiếp
            if (!stack.isEmpty()) {
                stack.peek().right = curr;
            }

            // Đưa curr vào stack để duy trì tính chất đơn điệu giảm dần
            stack.push(curr);
        }

        // Đáy ngăn xếp là phần tử lớn nhất toàn mảng => gốc của toàn bộ cây
        return stack.peekLast();
    }
}
```

---

## 🌿 Giải pháp Bổ trợ: Chia để trị / Đệ quy (Divide & Conquer)

Dưới đây là cách cài đặt đệ quy truyền thống theo đúng mô tả đề bài:

```java
class SolutionRecursion {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // Tìm chỉ số của phần tử lớn nhất trong đoạn [left, right]
        int maxIdx = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        // Tạo nút gốc với giá trị cực đại
        TreeNode root = new TreeNode(nums[maxIdx]);

        // Đệ quy xây dựng cây con trái và cây con phải
        root.left = build(nums, left, maxIdx - 1);
        root.right = build(nums, maxIdx + 1, right);

        return root;
    }
}
```

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Tiêu chí | Ngăn xếp Đơn điệu (Monotonic Stack) ⭐ | Chia để trị / Đệ quy (Divide & Conquer) |
| :--- | :---: | :---: |
| **Thời gian Tốt nhất (Best Time)** | $\mathcal{O}(N)$ | $\mathcal{O}(N \log N)$ *(khi cây hoàn toàn cân bằng)* |
| **Thời gian Trung bình (Avg Time)** | $\mathbf{\mathcal{O}(N)}$ | $\mathcal{O}(N \log N)$ |
| **Thời gian Xấu nhất (Worst Time)** | $\mathbf{\mathcal{O}(N)}$ | $\mathcal{O}(N^2)$ *(khi mảng đã được sắp xếp)* |
| **Không gian Bộ nhớ (Space)** | $\mathcal{O}(N)$ *(ngăn xếp lưu tối đa $N$ nút)* | $\mathcal{O}(N)$ *(ngăn xếp đệ quy khi cây lệch)* |
| **Nguy cơ Stack Overflow** | Không có ✅ *(lặp vòng tuyến tính)* | Có ⚠️ *(nếu đệ quy quá sâu khi $N$ lớn)* |

### Chứng minh Đánh giá Khấu hao (Amortized Analysis) của Monotonic Stack:
- Mỗi phần tử trong mảng $N$ phần tử được `push` vào ngăn xếp đúng $1$ lần.
- Mỗi phần tử chỉ có thể bị `pop` ra khỏi ngăn xếp tối đa $1$ lần trong toàn bộ quá trình chạy thuật toán.
- Tổng số thao tác trên ngăn xếp:
  $$\text{Total Operations} \le N \ (\text{lần push}) + N \ (\text{lần pop}) = 2N \text{ thao tác} = \mathcal{O}(N)$$
- Vì vậy, thời gian chạy chắc chắn đạt **$\mathcal{O}(N)$ tuyến tính tuyệt đối**, bất kể hình dạng hay thứ tự mảng đầu vào.

---

## ⚖️ Bảng so sánh toàn diện các phương pháp

```text
+-----------------------+----------------------------------+------------------------------------+
| Tiêu chí              | Chia để trị (Divide & Conquer)   | Ngăn xếp Đơn điệu (Monotonic Stack)|
+-----------------------+----------------------------------+------------------------------------+
| Thời gian xấu nhất    | O(N^2)                           | O(N) [Tối ưu tuyệt đối]            |
| Tư tưởng tiếp cận     | Top-down (Từ trên xuống)         | Bottom-up (Từ dưới lên)            |
| Thuật toán nền tảng   | Quét tìm Max từng đoạn           | Cây Descartes (Cartesian Tree)     |
| Bộ nhớ phụ trợ        | O(N) Call Stack                  | O(N) Ngăn xếp tường minh           |
| Tránh lỗi tràn Stack  | Không đảm bảo                    | Tuyệt đối an toàn                  |
| Điểm phỏng vấn        | Chấp nhận ở mức cơ bản           | ⭐ Ghi điểm xuất sắc (FAANG)       |
+-----------------------+----------------------------------+------------------------------------+
```

---

## 💡 Ứng dụng thực tế & Mẹo phỏng vấn (Applications & Interview Takeaways)

1. **Cây Descartes và bài toán RMQ (Range Minimum/Maximum Query):**
   - Cây Descartes là cầu nối kỳ diệu giữa bài toán **Range Maximum Query (RMQ)** và bài toán **Tổ tiên chung gần nhất (Lowest Common Ancestor - LCA)**.
   - Phần tử lớn nhất trên đoạn `[i, j]` trong mảng chính là $\text{LCA}(\text{node}_i, \text{node}_j)$ trên Cây Descartes!
2. **Kỹ thuật Ngăn xếp Đơn điệu (Monotonic Stack) kinh điển:**
   - Dạng bài toán này cùng họ với các bài toán nổi tiếng:
     - *LeetCode 84: Largest Rectangle in Histogram*
     - *LeetCode 739: Daily Temperatures*
     - *LeetCode 907: Sum of Subarray Minimums*
   - Quy tắc vàng: Bất cứ khi nào cần tìm **"phần tử lớn hơn gần nhất bên trái/phải"**, hãy nghĩ ngay đến Ngăn xếp Đơn điệu!
