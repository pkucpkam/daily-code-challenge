# 📚 Giải thích Chi tiết Bài 655 - Print Binary Tree

**Mục tiêu:** Cho nút gốc `root` của một cây nhị phân, hãy xây dựng một ma trận chuỗi kích thước $m \times n$ (chỉ số bắt đầu từ $0$) biểu diễn bố cục trực quan chuẩn hóa của cây nhị phân theo các quy tắc hình học nghiêm ngặt.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Ma trận Biểu diễn Cây Nhị Phân

Bài toán yêu cầu vẽ cây nhị phân lên một lưới (ma trận $m \times n$) sao cho:
1. **Các tầng của cây** tương ứng với **các hàng** của ma trận:
   - Gốc nằm ở hàng đầu tiên ($r = 0$).
   - Các nút con trực tiếp nằm ở hàng kế tiếp ($r + 1$).
2. **Cân xứng hình học đối xứng hoàn hảo:**
   - Cây được mở rộng đầy đủ theo chiều cao tối đa của nó.
   - Nút gốc nằm chính giữa hàng đầu tiên.
   - Mỗi nút con rẽ sang trái hoặc sang phải một khoảng cách đều đặn, tạo nên một cây cân đối hoàn chỉnh như sơ đồ phân cấp dạng kim tự tháp.

```text
               Cột:  0    1    2    3    4    5    6
           Hàng 0: ["",  "",  "",  "1", "",  "",  ""]   <-- Gốc ở chính giữa
           Hàng 1: ["",  "2", "",  "",  "",  "3", ""]   <-- Con cách gốc một khoảng offset
           Hàng 2: ["",  "",  "4", "",  "",  "",  ""]   <-- Tầng tiếp theo khoảng cách giảm 1/2
```

---

### 2. Phân tích các Quy tắc Kích thước & Tọa độ

#### A. Kích thước Ma trận ($m \times n$)
- **Chiều cao cây $h$ (Height):** Định nghĩa là số cạnh trên đường đi dài nhất từ gốc tới một lá bất kỳ:
  - Cây chỉ gồm $1$ nút gốc có chiều cao $h = 0$.
  - Cây rỗng (`null`) có chiều cao $h = -1$.
  - Công thức đệ quy:
    $$h(\text{node}) = 1 + \max(h(\text{node.left}), \, h(\text{node.right}))$$
- **Số hàng ($m$):** Cây có chiều cao $h$ sẽ có $h + 1$ tầng, do đó:
  $$m = h + 1$$
- **Số cột ($n$):** Một cây nhị phân đầy đủ có chiều cao $h$ sở hữu tối đa $2^{h + 1} - 1$ nút lá nếu mở rộng hết mức. Để đảm bảo đủ không gian cho mọi nút ở tầng thấp nhất phân tách nhau:
  $$n = 2^{h + 1} - 1 = (1 \ll (h + 1)) - 1$$

#### B. Tọa độ Nút Gốc & Quy luật Dịch chuyển Cột (Offset)
- **Tọa độ nút gốc:** Nằm ở hàng $0$ và cột trung tâm:
  $$r_{\text{root}} = 0, \quad c_{\text{root}} = \frac{n - 1}{2}$$
- **Quy luật cho nút con:** Cho nút cha đặt tại vị trí $(r, c)$:
  - Khoảng dịch chuyển cột cho các nút con ở hàng $r + 1$ là:
    $$\Delta c = 2^{h - r - 1} = 1 \ll (h - r - 1)$$
  - **Con trái (Left child):** Đặt tại $(r + 1, \, c - \Delta c)$.
  - **Con phải (Right child):** Đặt tại $(r + 1, \, c + \Delta c)$.

#### C. Góc nhìn Toán học: Phân hoạch Khoảng Nhị phân (Binary Interval Bisection)
Quy tắc trên tương đương với việc chia đôi không gian cột:
- Tại hàng $0$, nút gốc quản lý toàn bộ khoảng cột $[0, \, n - 1]$. Cột của gốc là trung điểm:
  $$c = \frac{0 + (n - 1)}{2} = \frac{n - 1}{2}$$
- Cây con bên trái quản lý khoảng cột $[0, \, c - 1]$, có trung điểm là:
  $$c_{\text{left}} = \frac{0 + c - 1}{2} = c - \frac{c + 1}{2} = c - 2^{h - r - 1}$$
- Cây con bên phải quản lý khoảng cột $[c + 1, \, n - 1]$, có trung điểm là:
  $$c_{\text{right}} = \frac{(c + 1) + (n - 1)}{2} = c + 2^{h - r - 1}$$
> 💡 Hai cách tiếp cận này (công thức dịch chuyển offset và phân hoạch khoảng nhị phân) hoàn toàn tương đương nhau về mặt toán học!

---

### 3. Các hướng tiếp cận bài toán

```
                       [Bài toán: Print Binary Tree]
                                     │
          ┌──────────────────────────┴──────────────────────────┐
          ▼                                                     ▼
   [Cách 1: Đệ quy DFS]                                 [Cách 2: Duyệt BFS]
   - Tính chiều cao h                                   - Tính chiều cao h
   - Đệ quy điền ma trận với tọa độ (r, c)              - Dùng Queue lưu (node, r, c)
   - Code ngắn gọn, trực quan                           - Duyệt theo từng tầng
   - O(h) Call Stack tối ưu (h <= 10)                   - Cần cấp phát thêm đối tượng Queue
```

#### ⚠️ Cách 2: Duyệt theo tầng (BFS với Queue)
- Dùng hàng đợi lưu các bộ ba `(node, r, c)`.
- Bắt đầu với `(root, 0, (n - 1) / 2)`.
- Lấy từng phần tử ra, gán vào ma trận `matrix[r][c]`, rồi đẩy các con với tọa độ mới vào Queue.
- **Nhược điểm:** Phải tạo thêm lớp đóng gói hoặc mảng đối tượng lưu trữ tọa độ cho mỗi nút trong Queue, tốn bộ nhớ Heap hơn so với Call Stack của DFS.

#### ⭐ Cách 1: Đệ quy theo chiều sâu (DFS - Lựa chọn Tối ưu)
- Tính chiều cao $h$ của cây trước bằng DFS $\mathcal{O}(N)$.
- Cấp phát mảng 2 chiều kích thước $m \times n$, điền sẵn chuỗi rỗng `""`.
- Gọi hàm đệ quy `fill(node, r, c)`:
  - Gán `matrix[r][c] = String.valueOf(node.val)`.
  - Nếu có con trái: `fill(node.left, r + 1, c - offset)`.
  - Nếu có con phải: `fill(node.right, r + 1, c + offset)`.
- **Ưu điểm:** Cực kỳ trực quan, tận dụng trực tiếp công thức đệ quy của đề bài, không phát sinh chi phí quản lý hàng đợi. Chiều cao tối đa $h \le 10$, Call Stack chỉ tốn tối đa $10$ khung bộ nhớ.

---

### 4. Ràng buộc & Đánh giá quy mô (Constraints)

- Số lượng nút: $N \in [1, 2^{10}] = [1, 1024]$.
- Giá trị nút: $-99 \le \text{Node.val} \le 99$.
- Chiều sâu cây (depth): Thuộc khoảng $[1, 10]$, nghĩa là chiều cao $h \in [0, 9]$.
- **Đánh giá quy mô ma trận lớn nhất:**
  - $m_{\max} = 9 + 1 = 10$ hàng.
  - $n_{\max} = 2^{9 + 1} - 1 = 1023$ cột.
  - Tổng số ô ma trận lớn nhất: $10 \times 1023 = 10,230$ ô.
  - Với $10^4$ phần tử, thời gian khởi tạo và gán giá trị chỉ mất dưới $1 \text{ ms}$, hoàn toàn nằm trong giới hạn an toàn tuyệt đối.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): DFS kết hợp Ánh xạ Tọa độ Trực tiếp trên Mảng 2D

### 💡 Ý tưởng thuật toán (Key Insights)

#### 1. Tính toán Chiều cao $h$ Chuẩn xác
Cây nhị phân theo định nghĩa của bài toán có:
- Chiều cao của cây rỗng (`null`) là $-1$.
- Chiều cao của nút lá (lá không có con) là $1 + \max(-1, -1) = 0$.
```java
private int getHeight(TreeNode node) {
    if (node == null) return -1;
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
}
```

#### 2. Tận dụng Phép Dịch Bit (Bitwise Shift)
Lũy thừa cơ số $2$ được tính toán siêu tốc thông qua toán tử dịch bit trái:
- $2^{h + 1} = 1 \ll (h + 1)$.
- Do đó: $n = (1 \ll (h + 1)) - 1$.
- Khoảng cách dịch chuyển tại hàng $r$:
  $$\text{offset} = 2^{h - r - 1} = 1 \ll (h - r - 1)$$

#### 3. Tối ưu hóa Bộ nhớ Ma trận trong Java
Thay vì dùng `List<List<String>>` với các thao tác `new ArrayList<>()` và `list.get(r).set(c, val)` gây chi phí kiểm tra biên nhiều lần:
1. Dùng mảng 2 chiều nguyên bản: `String[][] matrix = new String[m][n]`.
2. Khởi tạo giá trị rỗng nhanh chóng:
   ```java
   for (String[] row : matrix) {
       Arrays.fill(row, "");
   }
   ```
3. Truy cập và ghi giá trị với chi phí $\mathcal{O}(1)$:
   ```java
   matrix[r][c] = Integer.toString(node.val);
   ```
4. Đóng gói kết quả thông qua `Arrays.asList(row)`:
   - `Arrays.asList(row)` tạo một wrapper `List<String>` trực tiếp trên mảng hàng `row` mà **không cần sao chép các phần tử**, giúp tiết kiệm đáng kể thời gian chạy và bộ nhớ GC.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

Xét **Ví dụ 2:** `root = [1, 2, 3, null, 4]`

```text
               1
             /   \
            2     3
             \
              4
```

#### Bước 1: Tính chiều cao $h$
- Nút `4` (lá): $h(4) = 0$.
- Nút `2`: con trái rỗng ($-1$), con phải `4` ($0$) $\implies h(2) = 1 + \max(-1, 0) = 1$.
- Nút `3` (lá): $h(3) = 0$.
- Nút `1`: con trái `2` ($1$), con phải `3` ($0$) $\implies h(1) = 1 + \max(1, 0) = 2$.
- Vậy chiều cao toàn cây: $h = 2$.

#### Bước 2: Kích thước ma trận
- Số hàng: $m = h + 1 = 2 + 1 = 3$ (chỉ số hàng $r \in \{0, 1, 2\}$).
- Số cột: $n = 2^{2 + 1} - 1 = 8 - 1 = 7$ (chỉ số cột $c \in \{0, 1, 2, 3, 4, 5, 6\}$).

#### Bước 3: Diễn biến từng bước đệ quy DFS

| Bước | Nút hiện tại (`node`) | Hàng $r$ | Cột $c$ | Công thức tính $c$ | Offset $= 1 \ll (2 - r - 1)$ | Nhánh con gọi tiếp |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **1** | `Node(1)` | $0$ | $3$ | $(7 - 1) / 2 = 3$ | $1 \ll 1 = 2$ | Trái $\to (1, 3 - 2 = 1)$<br>Phải $\to (1, 3 + 2 = 5)$ |
| **2** | `Node(2)` | $1$ | $1$ | $3 - 2 = 1$ | $1 \ll 0 = 1$ | Trái: `null`<br>Phải $\to (2, 1 + 1 = 2)$ |
| **3** | `Node(4)` | $2$ | $2$ | $1 + 1 = 2$ | $1 \ll -1$ (không tính) | Cả hai con đều `null` (Dừng) |
| **4** | `Node(3)` | $1$ | $5$ | $3 + 2 = 5$ | $1 \ll 0 = 1$ | Cả hai con đều `null` (Dừng) |

#### Kết quả Ma trận sau khi điền hoàn tất:

```text
         Cột:     0     1     2     3     4     5     6
      --------------------------------------------------
      Hàng 0: [  "" ,  "" ,  "" , "1" ,  "" ,  "" ,  ""  ]
      Hàng 1: [  "" , "2" ,  "" ,  "" ,  "" , "3" ,  ""  ]
      Hàng 2: [  "" ,  "" , "4" ,  "" ,  "" ,  "" ,  ""  ]
```

---

## 💻 Cài đặt thuật toán chi tiết (Java Implementation)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     * Best Solution: Đệ quy theo chiều sâu (DFS) kết hợp Ánh xạ Tọa độ Ma trận.
     */
    public List<List<String>> printTree(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Bước 1: Xác định chiều cao cây h
        int height = getHeight(root);
        int m = height + 1;
        int n = (1 << (height + 1)) - 1;

        // Bước 2: Khởi tạo mảng ma trận 2D hiệu năng cao
        String[][] matrix = new String[m][n];
        for (String[] row : matrix) {
            Arrays.fill(row, "");
        }

        // Bước 3: Đặt gốc tại vị trí trung tâm và đệ quy điền các nút con
        fill(root, matrix, 0, (n - 1) / 2, height);

        // Bước 4: Đóng gói mảng thành List<List<String>> trả về
        List<List<String>> result = new ArrayList<>(m);
        for (String[] row : matrix) {
            result.add(Arrays.asList(row));
        }

        return result;
    }

    /**
     * Tính chiều cao của cây nhị phân (gốc đơn lẻ có chiều cao 0).
     */
    private int getHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * Đệ quy DFS điền giá trị nút vào ma trận theo quy tắc khoảng dịch chuyển offset.
     */
    private void fill(TreeNode node, String[][] matrix, int r, int c, int height) {
        if (node == null) {
            return;
        }

        // Gán giá trị nút hiện tại
        matrix[r][c] = Integer.toString(node.val);

        // Tính khoảng dịch chuyển cột cho hai nhánh con
        int offset = 1 << (height - r - 1);

        if (node.left != null) {
            fill(node.left, matrix, r + 1, c - offset, height);
        }
        if (node.right != null) {
            fill(node.right, matrix, r + 1, c + offset, height);
        }
    }
}
```

---

## 📊 Phân tích độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity)
- **Tính chiều cao cây (`getHeight`):** Thăm mọi nút trong cây đúng $1$ lần $\implies \mathcal{O}(N)$, với $N$ là số nút của cây.
- **Khởi tạo ma trận rỗng:** Cấp phát và gọi `Arrays.fill` trên $m$ hàng, mỗi hàng $n$ phần tử $\implies \mathcal{O}(m \times n) = \mathcal{O}((h + 1) \cdot 2^{h + 1})$.
- **Đệ quy điền tọa độ (`fill`):** Mỗi nút trong cây được duyệt đúng $1$ lần, mỗi bước thực hiện gán chỉ số và tính toán bit trong $\mathcal{O}(1) \implies \mathcal{O}(N)$.
- **Tạo danh sách kết quả:** Thêm $m$ hàng vào danh sách $\implies \mathcal{O}(m)$.
- **Tổng thời gian:**
  $$\mathcal{T} = \mathcal{O}(N) + \mathcal{O}(m \times n) + \mathcal{O}(N) + \mathcal{O}(m) = \mathcal{O}(m \times n) = \mathcal{O}(h \cdot 2^h)$$
  Vì số nút $N \le 2^{h + 1} - 1 = n$, chi phí $\mathcal{O}(m \times n)$ chiếm ưu thế và là giới hạn dưới bắt buộc để khởi tạo ma trận đầu ra.

### 2. Độ phức tạp Không gian (Space Complexity)
- **Bộ nhớ Call Stack đệ quy:** Chiều sâu đệ quy tối đa của hàm `getHeight` và `fill` bằng chiều cao của cây $h \implies \mathcal{O}(h)$. Với $h \le 10$, Call Stack tiêu tốn không đáng kể (vài KB).
- **Không gian lưu trữ kết quả:** Ma trận kích thước $m \times n$ chiếm $\mathcal{O}(m \times n)$ bộ nhớ để chứa kết quả đầu ra theo yêu cầu bài toán.
- **Tổng bộ nhớ phụ trợ (Auxiliary Space):** $\mathcal{O}(h)$ tối ưu.

---

## 💡 Bẫy thường gặp & Kinh nghiệm phỏng vấn (Common Pitfalls & Best Practices)

> [!WARNING]
> **1. Bẫy định nghĩa chiều cao cây (0-indexed vs 1-indexed):**
> Trong các bài toán cấu trúc dữ liệu, có hai trường phái định nghĩa chiều cao:
> - Định nghĩa theo số cạnh (Edge-based): Cây chỉ có $1$ nút gốc có chiều cao $0$. Cây rỗng có chiều cao $-1$.
> - Định nghĩa theo số nút/tầng (Node-based / Depth): Cây chỉ có $1$ nút gốc có chiều cao $1$. Cây rỗng có chiều cao $0$.
> 
> Đề bài LeetCode 655 quy định số hàng $m = \text{height} + 1$. Với cây có 1 nút gốc, ma trận có đúng $1$ hàng $\implies \text{height} = 0$. Vì vậy hàm `getHeight` **bắt buộc phải trả về `-1` khi gặp nút `null`**!

> [!TIP]
> **2. Tối ưu hiệu năng bằng `String[][]` thay vì `ArrayList<ArrayList<String>>`:**
> Việc khởi tạo `new ArrayList<>()` lồng nhau và liên tục gọi `list.get(r).set(c, val)` gây chi phí gián tiếp và kiểm tra chỉ số biên (Bounds check). Sử dụng mảng 2 chiều nguyên bản `String[][]` giúp tận dụng bộ nhớ liên tục, tăng tốc độ truy cập CPU Cache.

> [!TIP]
> **3. Đóng gói nhanh chóng bằng `Arrays.asList(row)`:**
> Phương thức `Arrays.asList(matrix[i])` tạo một List view tham chiếu trực tiếp đến mảng `matrix[i]` trong thời gian $\mathcal{O}(1)$, không hề sao chép lại các chuỗi con sang mảng mới.

---

## 🏆 Tổng kết

| Tiêu chí | DFS Ánh xạ Tọa độ (Được chọn) | BFS theo tầng |
| :--- | :--- | :--- |
| **Tính đơn giản & Trong sáng** | ⭐⭐⭐⭐⭐ (Ngắn gọn, dễ hiểu) | ⭐⭐⭐ (Cồng kềnh, cần Queue) |
| **Thời gian thực thi** | $\mathcal{O}(m \times n)$ | $\mathcal{O}(m \times n)$ |
| **Bộ nhớ phụ trợ** | $\mathcal{O}(h)$ (Call Stack) | $\mathcal{O}(2^h)$ (Queue chứa tầng rộng nhất) |
| **Khả năng gặp lỗi** | Cực thấp | Dễ nhầm lẫn khi đóng gói đối tượng |
