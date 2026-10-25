# 📚 Giải thích Chi tiết Bài 653 - Two Sum IV - Input is a BST

**Mục tiêu:** Cho nút gốc `root` của một Cây Tìm Kiếm Nhị Phân (**Binary Search Tree - BST**) và một số nguyên mục tiêu `k`. Trả về `true` nếu tồn tại hai phần tử khác nhau trong cây có tổng bằng `k`, hoặc `false` nếu không tồn tại bất kỳ cặp nào thỏa mãn.

---

## 🎯 Phân tích bài toán

### 1. Đặc tính Cốt lõi của Cây Tìm Kiếm Nhị Phân (BST)

Một Cây Tìm Kiếm Nhị Phân (BST) hợp lệ tuân theo nguyên tắc bất biến:
- Mọi nút trên cây con bên trái đều có giá trị **nhỏ hơn** giá trị nút hiện tại:
  $$\forall u \in \text{LeftSubtree}(\text{node}), \quad u.\text{val} < \text{node}.\text{val}$$
- Mọi nút trên cây con bên phải đều có giá trị **lớn hơn** giá trị nút hiện tại:
  $$\forall v \in \text{RightSubtree}(\text{node}), \quad v.\text{val} > \text{node}.\text{val}$$

> 💡 **Hệ quả vàng:**
> 1. Phép duyệt **Trung thứ tự (In-order Traversal: Trái $\to$ Gốc $\to$ Phải)** trên BST luôn sinh ra một dãy giá trị **tăng dần nghiêm ngặt (Strictly Increasing)**.
> 2. Phép duyệt **Trung thứ tự đảo ngược (Reverse In-order Traversal: Phải $\to$ Gốc $\to$ Trái)** luôn sinh ra một dãy giá trị **giảm dần nghiêm ngặt (Strictly Decreasing)**.

---

### 2. Các hướng tiếp cận bài toán

```
                       [Cây BST & Mục tiêu k]
                                  │
         ┌────────────────────────┼────────────────────────┐
         ▼                        ▼                        ▼
  [Cách 1: HashSet]       [Cách 2: Mảng + 2 Trỏ]   [Cách 3: Dual BST Iterator]
  - Duyệt cây DFS/BFS     - Duyệt In-order ra List - 2 Stack mô phỏng 2 con trỏ
  - O(N) Thời gian        - 2 con trỏ chạy từ 2 đầu- Duyệt Lazy theo nhu cầu
  - O(N) Bộ nhớ           - O(N) Thời gian         - O(N) Thời gian
  - Không tận dụng BST    - O(N) Bộ nhớ            - O(H) Bộ nhớ TỐI ƯU
```

#### ❌ Hướng 1: Duyệt vét cạn (Brute-Force Pairwise)
- So sánh từng cặp nút $(u, v)$ trong cây xem $u.\text{val} + v.\text{val} == k$ hay không.
- Tổng số cặp nút: $\frac{N(N - 1)}{2} \approx \mathcal{O}(N^2)$.
- Với $N = 10^4$, số phép tính lên tới $\approx 5 \times 10^7$ phép tính $\implies$ Quá chậm và không tận dụng được cấu trúc cây.

#### ⚠️ Hướng 2: Tìm kiếm nhị phân phần bù cho từng nút (BST Binary Search)
- Với mỗi nút $u$ trong cây, ta cần tìm một nút $v$ khác $u$ sao cho $v.\text{val} = k - u.\text{val}$.
- Tận dụng cây BST để tìm kiếm giá trị bù trong $\mathcal{O}(H)$ (với $H$ là chiều cao cây).
- Tổng thời gian: $\mathcal{O}(N \times H)$.
  - Nếu cây cân bằng: $\mathcal{O}(N \log N)$.
  - Nếu cây suy biến thành đường thẳng (skewed tree): $H = N \implies \mathcal{O}(N^2)$ (nguy cơ TLE).

#### ⚠️ Hướng 3: Bảng băm kết hợp Duyệt cây (HashSet + DFS / BFS)
- Duyệt qua từng nút của cây và dùng một tập hợp `HashSet<Integer> seen`:
  - Tại mỗi nút $u$: nếu $k - u.\text{val} \in seen$, trả về `true`.
  - Ngược lại, thêm $u.\text{val}$ vào `seen` và duyệt tiếp.
- **Ưu điểm:** Cực kỳ trực quan, dễ lập trình, có khả năng dừng sớm.
- **Nhược điểm:** Tiêu tốn $\mathcal{O}(N)$ bộ nhớ và hoàn toàn **bỏ qua đặc tính sắp xếp có sẵn của BST** (thuật toán này hoạt động trên bất kỳ cây nhị phân nào, không cần phải là BST).

#### ⚠️ Hướng 4: Chuyển sang Mảng bằng In-order + Kỹ thuật Hai con trỏ
- Duyệt In-order để chuyển toàn bộ cây thành danh sách đã sắp xếp `List<Integer>`.
- Dùng kỹ thuật Hai con trỏ (Two Pointers) kinh điển của bài *Two Sum II - Input Array Is Sorted*:
  - Con trỏ trái `left = 0`, con trỏ phải `right = list.size() - 1`.
  - Tăng `left` nếu tổng $< k$, giảm `right` nếu tổng $> k$.
- **Nhược điểm:** Bắt buộc phải duyệt hết $100\%$ các nút trong cây để dựng mảng trước (không thể dừng sớm ở pha duyệt cây) và tiêu tốn $\mathcal{O}(N)$ bộ nhớ phụ trợ cho danh sách.

#### 💡 Đột phá: Hai con trỏ trực tiếp trên BST bằng Dual BST Iterator (Tối ưu Bộ nhớ Tuyệt đối $\mathcal{O}(H)$)
- Thay vì đổ toàn bộ cây ra mảng $\mathcal{O}(N)$, ta mô phỏng hai con trỏ trực tiếp trên cây BST bằng **hai ngăn xếp (Dual Stacks)**!
  - **Con trỏ trái (Forward Iterator):** Duyệt In-order lười (Lazy In-order), chỉ sinh phần tử nhỏ tiếp theo khi cần.
  - **Con trỏ phải (Reverse Iterator):** Duyệt Reverse In-order lười, chỉ sinh phần tử lớn tiếp theo khi cần.
- **Không gian bộ nhớ:** Tại mọi thời điểm, mỗi ngăn xếp chỉ lưu các nút trên đường đi từ gốc đến nút lá hiện tại, tức chiều cao cây $\mathcal{O}(H)$ ($\mathcal{O}(\log N)$ với cây cân bằng)!
- **Dừng sớm (Early Exit):** Dừng lại ngay khi tìm thấy tổng bằng $k$ mà không cần chạm đến phần còn lại của cây.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)

- Số lượng nút: $N \in [1, 10^4]$.
- Giá trị nút: $-10^4 \le \text{Node.val} \le 10^4$.
- Mục tiêu: $-10^5 \le k \le 10^5$.
- Tổng hai số tối đa nằm trong khoảng $[-2 \times 10^4, 2 \times 10^4]$, hoàn toàn an toàn trong kiểu số nguyên `int` 32-bit (không lo tràn số).
- Cây chỉ có $1$ nút ($N = 1$): Không thể tìm được 2 phần tử khác nhau $\implies$ Chắc chắn trả về `false`.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Kỹ thuật Hai Con Trỏ kết hợp Dual BST Iterator

### 💡 Ý tưởng thuật toán (Key Insights)

#### 1. Nguyên lý Duyệt Lười theo Nhu cầu (Lazy On-demand Traversal)
Trong kỹ thuật Hai con trỏ trên mảng đã sắp xếp:
- Ta chỉ cần 2 biến chỉ số `left` và `right`.
- Khi cần phần tử nhỏ hơn tiếp theo, ta chỉ việc tăng `left++`.
- Khi cần phần tử lớn hơn tiếp theo, ta chỉ việc giảm `right--`.

Trên cây BST, ta có thể xây dựng cơ chế tương tự bằng **BST Iterator**:
- Mỗi lần gọi `next()`, iterator sẽ chỉ thực hiện đúng số bước cần thiết để tìm ra phần tử kế tiếp trong thứ tự duyệt.
- Dữ liệu không được nạp ồ ạt, giúp tiết kiệm bộ nhớ tối đa.

#### 2. Cấu trúc của Forward Iterator (Duyệt Tăng Dần)
- Dùng ngăn xếp `stack`.
- **Hàm `pushAll(node)`:** Từ nút hiện tại, liên tục đi sang nhánh trái (`node = node.left`) và đẩy tất cả các nút trên đường đi vào ngăn xếp. Đỉnh ngăn xếp luôn là nút có giá trị nhỏ nhất chưa được thăm.
- **Hàm `next()`:**
  1. Lấy nút trên đỉnh ngăn xếp: `curr = stack.pop()`.
  2. Vì đã thăm `curr`, bước kế tiếp trong In-order là đi sang cây con phải: gọi `pushAll(curr.right)`.
  3. Trả về `curr`.

#### 3. Cấu trúc của Reverse Iterator (Duyệt Giảm Dần)
- Hoàn toàn đối xứng với Forward Iterator!
- **Hàm `pushAll(node)`:** Liên tục đi sang nhánh phải (`node = node.right`) và đẩy các nút vào ngăn xếp. Đỉnh ngăn xếp luôn là nút có giá trị lớn nhất chưa được thăm.
- **Hàm `next()`:**
  1. Lấy nút trên đỉnh ngăn xếp: `curr = stack.pop()`.
  2. Bước kế tiếp trong Reverse In-order là đi sang cây con trái: gọi `pushAll(curr.left)`.
  3. Trả về `curr`.

#### 4. Quy tắc điều phối Hai Con Trỏ
Khởi tạo: `left = leftIter.next()`, `right = rightIter.next()`.

Vòng lặp chạy khi `left != right` và `left.val < right.val`:
$$\text{sum} = \text{left.val} + \text{right.val}$$

- $\text{sum} == k$: Tìm thấy cặp hợp lệ $\implies$ trả về `true`.
- $\text{sum} < k$: Tổng hiện tại quá nhỏ, cần một giá trị lớn hơn $\implies$ tiến con trỏ trái:
  $$\text{left} = \text{leftIter.hasNext()} \ ? \ \text{leftIter.next()} : \text{null}$$
- $\text{sum} > k$: Tổng hiện tại quá lớn, cần một giá trị nhỏ hơn $\implies$ lùi con trỏ phải:
  $$\text{right} = \text{rightIter.hasNext()} \ ? \ \text{rightIter.next()} : \text{null}$$

Nếu hai con trỏ gặp nhau mà chưa tìm thấy tổng bằng $k$ $\implies$ trả về `false`.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `root = [5, 3, 6, 2, 4, null, 7]`, `k = 9`

Cấu trúc cây BST:
```text
            5
          /   \
        3       6
       / \       \
      2   4       7
```

Dãy giá trị khi duyệt In-order: `[2, 3, 4, 5, 6, 7]`.

##### Diễn biến từng bước với Dual BST Iterator:

1. **Khởi tạo:**
   - `leftIter` rẽ hết sang trái từ gốc `5`: Đẩy `5 -> 3 -> 2` vào `stackL`.
   - `rightIter` rẽ hết sang phải từ gốc `5`: Đẩy `5 -> 6 -> 7` vào `stackR`.
   - `left = leftIter.next()`: Lấy `2` ra khỏi `stackL`.
   - `right = rightIter.next()`: Lấy `7` ra khỏi `stackR`.

```text
  leftStack (Top -> Bottom): [3, 5]     |  left.val  = 2
  rightStack (Top -> Bottom): [6, 5]    |  right.val = 7
```

2. **Kiểm tra tổng:**
   $$\text{sum} = \text{left.val} + \text{right.val} = 2 + 7 = 9$$
   $$\text{sum} == k \ (9 == 9) \implies \mathbf{TRUE!}$$

> ⚡ **Nhận xét ấn tượng:** Chỉ sau đúng **1 phép tính tổng**, thuật toán đã tìm ra đáp án và dừng ngay lập tức mà không cần chạm tới các nút `3, 4, 5, 6`!

---

#### Ví dụ 2: `root = [5, 3, 6, 2, 4, null, 7]`, `k = 28`

Bảng truy vết từng bước (Trace Table):

| Bước | `left.val` | `right.val` | `sum` | So sánh với `k = 28` | Hành động tiếp theo | Trạng thái Stack L | Trạng thái Stack R |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **1** | `2` | `7` | `9` | $9 < 28$ | Tăng `left` | `[3, 5]` | `[6, 5]` |
| **2** | `3` | `7` | `10` | $10 < 28$ | Tăng `left` (`pushAll(4)`) | `[4, 5]` | `[6, 5]` |
| **3** | `4` | `7` | `11` | $11 < 28$ | Tăng `left` | `[5]` | `[6, 5]` |
| **4** | `5` | `7` | `12` | $12 < 28$ | Tăng `left` (`pushAll(6)`) | `[6]` | `[6, 5]` |
| **5** | `6` | `7` | `13` | $13 < 28$ | Tăng `left` | `[]` | `[6, 5]` |
| **6** | `7` | `7` | - | `left == right` | **Hai con trỏ gặp nhau!** | `[]` | `[5]` |

Kết luận: Vòng lặp dừng vì `left == right` (cùng trỏ vào nút có giá trị `7`). Kết quả trả về là **`false`**.

---

## 💻 Phân tích mã nguồn chi tiết (Code Walkthrough)

### 1. Hàm chính `findTarget(TreeNode root, int k)`

```java
public boolean findTarget(TreeNode root, int k) {
    if (root == null) {
        return false;
    }

    // Khởi tạo hai bộ duyệt: xuôi (tăng dần) và ngược (giảm dần)
    BSTIterator leftIter = new BSTIterator(root, true);
    BSTIterator rightIter = new BSTIterator(root, false);

    TreeNode left = leftIter.next();
    TreeNode right = rightIter.next();

    // Duyệt cho đến khi hai con trỏ gặp nhau hoặc vượt qua nhau
    while (left != null && right != null && left != right && left.val < right.val) {
        int sum = left.val + right.val;

        if (sum == k) {
            return true; // Tìm thấy cặp nút thỏa mãn
        } else if (sum < k) {
            // Tổng còn thiếu -> Cần số lớn hơn -> Tiến con trỏ trái
            left = leftIter.hasNext() ? leftIter.next() : null;
        } else {
            // Tổng quá thừa -> Cần số nhỏ hơn -> Lùi con trỏ phải
            right = rightIter.hasNext() ? rightIter.next() : null;
        }
    }

    return false; // Không tồn tại cặp nút nào có tổng bằng k
}
```

### 2. Lớp bổ trợ `BSTIterator`

```java
private static class BSTIterator {
    private final Deque<TreeNode> stack = new ArrayDeque<>();
    private final boolean forward; // true: In-order, false: Reverse In-order

    public BSTIterator(TreeNode root, boolean forward) {
        this.forward = forward;
        pushAll(root);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode curr = stack.pop();
        // Nếu duyệt tiến: sau khi thăm nút, đi sang con phải và nạp toàn bộ nhánh trái của nó
        // Nếu duyệt lùi: sau khi thăm nút, đi sang con trái và nạp toàn bộ nhánh phải của nó
        if (forward) {
            pushAll(curr.right);
        } else {
            pushAll(curr.left);
        }
        return curr;
    }

    private void pushAll(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = forward ? node.left : node.right;
        }
    }
}
```

---

## 🌿 Giải pháp Bổ trợ: Bảng băm (HashSet + DFS)

Trong trường hợp cây đầu vào **không phải là BST** (một cây nhị phân tổng quát bất kỳ), hoặc trong các cuộc thi cần code cực ngắn trong vòng 1 phút, giải pháp **HashSet + DFS** là lựa chọn tuyệt vời:

```java
class SolutionHashSet {
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> seen = new HashSet<>();
        return dfs(root, k, seen);
    }

    private boolean dfs(TreeNode node, int k, Set<Integer> seen) {
        if (node == null) return false;

        // Nếu đã gặp số bù trước đó -> Thành công
        if (seen.contains(k - node.val)) {
            return true;
        }

        // Ghi nhận giá trị hiện tại vào bảng băm
        seen.add(node.val);

        // Đệ quy tìm kiếm trên hai nhánh con
        return dfs(node.left, k, seen) || dfs(node.right, k, seen);
    }
}
```

- **Độ phức tạp:** $\mathcal{O}(N)$ thời gian, $\mathcal{O}(N)$ không gian bộ nhớ.

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Tiêu chí | Giải pháp Tối ưu (Dual BST Iterator) | Bảng băm (HashSet + DFS) | Mảng + Hai con trỏ |
| :--- | :---: | :---: | :---: |
| **Thời gian Tốt nhất (Best Time)** | $\mathcal{O}(1)$ *(khi phần tử nhỏ nhất + lớn nhất $= k$)* | $\mathcal{O}(1)$ | $\mathcal{O}(N)$ *(phải duyệt hết cây để dựng mảng)* |
| **Thời gian Trung bình (Avg Time)** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Thời gian Xấu nhất (Worst Time)** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Bộ nhớ Cây Cân bằng (Balanced)** | $\mathbf{\mathcal{O}(\log N)}$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Bộ nhớ Xấu nhất (Skewed Tree)** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Dừng sớm (Early Exit)** | Có ✅ | Có ✅ | Không ❌ |
| **Khai thác thuộc tính BST** | Trọn vẹn $100\%$ ✅ | Không dùng ❌ | Có dùng ✅ |

### Phân tích chi tiết Bộ nhớ $\mathcal{O}(H)$ so với $\mathcal{O}(N)$:
- Với $N = 10^4$ nút:
  - Nếu cây BST cân bằng: Chiều cao $H \approx \lceil\log_2 10000\rceil \approx 14$.
  - Hai ngăn xếp chỉ lưu tối đa khoảng $2 \times 14 = \mathbf{28}$ con trỏ nút trong bộ nhớ!
  - Trong khi đó, mảng hay HashSet cần cấp phát dung lượng cho cả $\mathbf{10{,}000}$ phần tử $\implies$ Giải pháp Dual BST Iterator tiết kiệm bộ nhớ hơn **gấp 350 lần**!

---

## ⚖️ Bảng so sánh toàn diện các phương pháp

```text
+-----------------------+------------------+------------------+------------------+--------------------+
| Tiêu chí              | Brute-Force      | HashSet + DFS    | In-order to List | Dual BST Iterator  |
+-----------------------+------------------+------------------+------------------+--------------------+
| Time (Worst)          | O(N^2)           | O(N)             | O(N)             | O(N)               |
| Space (Auxiliary)     | O(H)             | O(N)             | O(N)             | O(H) [Tối ưu nhất] |
| Early Exit?           | Có               | Có               | Không            | Có                 |
| BST Property Used?    | Không            | Không            | Có               | Có                 |
| Độ phức tạp cài đặt   | Rất dễ           | Rất dễ           | Dễ               | Trung bình         |
| Điểm đánh giá PV      | ❌ Tránh dùng    | ⚠️ Chấp nhận     | ⚠️ Tạm ổn        | ⭐ Điểm tuyệt đối  |
+-----------------------+------------------+------------------+------------------+--------------------+
```

---

## 💡 Mẹo phỏng vấn & Bài học kinh nghiệm (Interview Insights & Takeaways)

1. **Nhận diện dạng bài:** Khi gặp bài toán có từ khóa **BST** và yêu cầu tìm cặp phần tử thỏa mãn điều kiện tổng/hiệu, hãy nghĩ ngay đến **In-order Traversal** vì nó tương đương với một mảng đã được sắp xếp sẵn.
2. **Câu hỏi nâng cao của nhà tuyển dụng (Follow-up Question):**
   > *"Can you solve this problem with $\mathcal{O}(H)$ auxiliary space instead of $\mathcal{O}(N)$?"*
   - Đây là câu hỏi kinh điển của các kỹ sư Google/Meta khi ứng viên đưa ra lời giải HashSet hay In-order List.
   - Việc trình bày kỹ thuật **Dual BST Iterator** sử dụng hai ngăn xếp sẽ ngay lập tức ghi điểm tuyệt đối về khả năng tối ưu hóa tài nguyên phần cứng.
3. **Mở rộng sang $\mathcal{O}(1)$ Space (Morris Traversal):**
   - Về lý thuyết, có thể dùng thuật toán Morris Traversal để duyệt cây với $\mathcal{O}(1)$ không gian bổ sung. Tuy nhiên, việc chạy đồng thời hai con trỏ ngược chiều bằng Morris Traversal sẽ làm thay đổi cấu trúc con trỏ tạm thời của cây và tiềm ẩn nguy cơ xung đột con trỏ, do đó **Dual BST Iterator với $\mathcal{O}(H)$ space** được công nhận là giải pháp chuẩn mực và an toàn nhất trong thực tế công nghiệp.
