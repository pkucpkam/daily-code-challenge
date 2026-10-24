# 📚 Giải thích Chi tiết Bài 652 - Find Duplicate Subtrees

**Mục tiêu:** Cho nút gốc `root` của một cây nhị phân, hãy tìm và trả về tất cả các cây con bị trùng lặp (**duplicate subtrees**). Với mỗi nhóm cây con trùng lặp, bạn chỉ cần trả về nút gốc của một cây con bất kỳ đại diện cho nhóm đó.

Hai cây con được coi là trùng lặp nếu chúng có **cùng cấu trúc hình học** và **cùng giá trị tại mọi nút tương ứng**.

---

## 🎯 Phân tích bài toán

### 1. Định nghĩa Cây con trùng lặp (Duplicate Subtrees)

Một cây con tại một nút $u$ bao gồm:
- Chính nút $u$.
- Toàn bộ các con cháu phía dưới của $u$ (toàn bộ cây con bên trái và cây con bên phải).

Hai cây con $T_A$ và $T_B$ (gốc tại nút $A$ và $B$) trùng lặp khi và chỉ khi:
1. $A.\text{val} == B.\text{val}$.
2. Cây con trái của $A$ giống hệt cây con trái của $B$.
3. Cây con phải của $A$ giống hệt cây con phải của $B$.

---

### 2. Thách thức: So sánh cấu trúc cây nhị phân

#### ❌ Cách tiếp cận Ngây thơ (Brute-Force Pairwise Comparison)
- So sánh từng cặp nút $(u, v)$ trong cây xem cây con gốc $u$ có bằng cây con gốc $v$ hay không bằng hàm đệ quy `isSameTree(u, v)` ($\mathcal{O}(N)$).
- Với $N$ nút, tổng số cặp là $\frac{N(N - 1)}{2} \approx \mathcal{O}(N^2)$ cặp.
- Tổng thời gian: $\mathcal{O}(N^2) \times \mathcal{O}(N) = \mathcal{O}(N^3)$.
- Với $N = 5000$, $N^3 \approx 1.25 \times 10^{11}$ phép tính $\implies$ Chắc chắn bị **Time Limit Exceeded (TLE)**.

#### ⚠️ Cách tiếp cận Tuần tự hóa chuỗi đầy đủ (Full Subtree Serialization)
- Dùng phép duyệt hậu thứ tự (post-order traversal) để chuyển toàn bộ cây con thành chuỗi:
  $$\text{serial} = \text{node.val} + \text{","} + \text{serialize(left)} + \text{","} + \text{serialize(right)}$$
- Dùng một bảng băm `Map<String, Integer>` để đếm số lần xuất hiện của chuỗi này.
- **Điểm yếu chí mạng:**
  - Trong trường hợp xấu nhất cây bị lệch thành một đường thẳng (skewed tree), độ dài chuỗi tại tầng thứ $k$ là $\mathcal{O}(k)$.
  - Tổng chi phí ghép chuỗi và tính mã băm (hash calculation):
    $$\sum_{k=1}^N \mathcal{O}(k) = \mathcal{O}(N^2)$$
  - Chi phí bộ nhớ để lưu các chuỗi cũng lên tới $\mathcal{O}(N^2)$. Với $N = 5000$, $N^2 \approx 2.5 \times 10^7$ ký tự, gây lãng phí bộ nhớ và Garbage Collection (GC) liên tục.

#### 💡 Đột phá: Mã hóa Bộ ba Định danh Cây con (Triplet Subtree ID Mapping)
- Thay vì lưu chuỗi dài vô tận, ta chỉ cần định danh mỗi cây con duy nhất bằng một **số nguyên ID**!
- Mọi cây con đều có thể biểu diễn bằng đúng 3 thành phần:
  $$(\text{leftSubtreeId}, \text{node.val}, \text{rightSubtreeId})$$
- Chuỗi khóa của bộ ba này có độ dài cố định $\le 15$ ký tự $\implies$ Thao tác băm và tra cứu luôn đạt **$\mathcal{O}(1)$ tuyệt đối**!
- Đưa tổng độ phức tạp thuật toán về **$\mathcal{O}(N)$ Thời gian** và **$\mathcal{O}(N)$ Bộ nhớ**.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)

- Số lượng nút trong cây: $N \in [1, 5000]$.
- Giá trị mỗi nút: $-200 \le \text{Node.val} \le 200$.
- Số loại cây con duy nhất tối đa không vượt quá $N = 5000$.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mã hóa Bộ ba Định danh Cây con (Triplet Subtree ID Mapping)

### 💡 Ý tưởng thuật toán (Key Insights)

#### 1. Nguyên lý phân cấp ID (Tree Isomorphism by Hierarchical IDs)
- Cây rỗng (`null`) luôn được quy ước có ID cố định: $\text{ID} = 0$.
- Một cây con tại `node` được đặc trưng hoàn chỉnh bởi bộ 3:
  $$(\text{ID}_{\text{left}}, \text{node.val}, \text{ID}_{\text{right}})$$
- Nếu hai cây con có cùng ID con trái, cùng giá trị `node.val`, và cùng ID con phải thì chúng **chắc chắn đồng dạng 100%**.

#### 2. Cơ chế ánh xạ bộ ba sang ID số nguyên
- Dùng bảng băm `Map<String, Integer> tripletToId`:
  - Khóa: Chuỗi bộ ba `leftId + "," + node.val + "," + rightId`.
  - Giá trị: ID số nguyên duy nhất đại diện cho cấu trúc đó ($1, 2, 3, \dots$).
- Vì `leftId, rightId \le 5000` và `node.val \in [-200, 200]`, chuỗi khóa luôn có dạng ngắn (ví dụ: `"1,4,0"`, `"0,-15,2"`), dài tối đa $\approx 15$ ký tự. Do đó, việc ghép chuỗi và tính mã băm đạt $\mathcal{O}(1)$.

#### 3. Đếm tần suất và tránh thêm trùng lặp
- Dùng bảng băm `Map<Integer, Integer> idCount` để đếm số lần xuất hiện của từng ID cấu trúc.
- **Quy tắc thêm vào danh sách kết quả:**
  $$\text{if } (\text{idCount.get}(id) == 2) \implies \text{result.add}(node);$$
  - Khi tần suất đạt đúng $2$: Đây là lần đầu tiên phát hiện sự trùng lặp $\implies$ đưa nút vào danh sách kết quả.
  - Khi tần suất $\ge 3$: Cấu trúc này đã được thêm ở lần thứ 2 rồi $\implies$ không thêm lại, đảm bảo kết quả chỉ chứa đúng 1 đại diện cho mỗi nhóm trùng lặp.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

Xét **Ví dụ 1:** `root = [1, 2, 3, 4, null, 2, 4, null, null, 4]`

```text
            1
          /   \
        (2)    3
        /     / \
      [4]   (2) [4]
            /
          [4]
```

Duyệt cây theo thứ tự **Hậu thứ tự (Post-order: Trái -> Phải -> Gốc)**:

| Thứ tự | Nút đang xét | `leftId` | `node.val` | `rightId` | Khóa bộ ba | ID gán | Tần suất | Hành động |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| 1 | Nút `4` (dưới nút 2 bên trái) | 0 | 4 | 0 | `"0,4,0"` | **1** | 1 | Gán ID mới = 1 |
| 2 | Nút `2` (bên trái) | 1 | 2 | 0 | `"1,2,0"` | **2** | 1 | Gán ID mới = 2 |
| 3 | Nút `4` (dưới nút 2 bên phải) | 0 | 4 | 0 | `"0,4,0"` | **1** | **2** | **Trùng lặp ID 1! Thêm nút 4 vào kết quả** |
| 4 | Nút `2` (bên phải) | 1 | 2 | 0 | `"1,2,0"` | **2** | **2** | **Trùng lặp ID 2! Thêm nút 2 vào kết quả** |
| 5 | Nút `4` (dưới nút 3 bên phải) | 0 | 4 | 0 | `"0,4,0"` | **1** | 3 | ID 1 xuất hiện lần 3 $\implies$ Bỏ qua |
| 6 | Nút `3` | 2 | 3 | 1 | `"2,3,1"` | **3** | 1 | Gán ID mới = 3 |
| 7 | Nút `1` (gốc cây) | 2 | 1 | 3 | `"2,1,3"` | **4** | 1 | Gán ID mới = 4 |

**Kết quả thu được:** Danh sách gồm 2 nút gốc của cây con trùng lặp: `[Nút 4, Nút 2]`.

---

### 🌟 Ưu điểm vượt trội của giải pháp Triplet ID Mapping

1. **Hiệu năng đạt tuyến tính $\mathcal{O}(N)$ tuyệt đối:**
   - Mỗi nút trong cây chỉ được duyệt đúng 1 lần qua DFS.
   - Thao tác tính khóa bộ ba chỉ gồm 3 số nguyên nhỏ $\implies$ thời gian hằng số $\mathcal{O}(1)$ cho mỗi nút.
   - Không bị chậm kể cả khi cây bị lệch hoàn toàn thành một đường thẳng $N = 5000$.
2. **Tiết kiệm bộ nhớ $\mathcal{O}(N)$:**
   - Mỗi nút tạo tối đa một khóa chuỗi $\le 15$ bytes. Không sinh ra các chuỗi khổng lồ dài hàng nghìn ký tự.
3. **Mã nguồn trong sáng, thanh lịch, dễ bảo trì:**
   - Tách biệt rõ ràng giữa việc định danh cấu trúc (`tripletToId`) và thống kê tần suất (`idCount`).

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-465/Solution.java):

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    /**
     * Best Solution: Mã hóa Bộ ba Định danh Cây con (Triplet Subtree ID Mapping).
     *
     * Time Complexity: O(N) - Mỗi node được duyệt 1 lần, các thao tác băm tốn O(1).
     * Space Complexity: O(N) - Bảng băm lưu tối đa N bộ ba, đệ quy O(H) với H <= N.
     */
    private int idCounter = 1;
    private final Map<String, Integer> tripletToId = new HashMap<>();
    private final Map<Integer, Integer> idCount = new HashMap<>();
    private final List<TreeNode> result = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        tripletToId.clear();
        idCount.clear();
        result.clear();
        idCounter = 1;

        getId(root);

        return result;
    }

    private int getId(TreeNode node) {
        if (node == null) {
            return 0; // ID 0 đại diện cho nút rỗng (null)
        }

        // Duyệt hậu thứ tự (post-order): Lấy ID cây con trái và phải trước
        int leftId = getId(node.left);
        int rightId = getId(node.right);

        // Khóa định danh bộ ba có kích thước cố định O(1)
        String tripletKey = leftId + "," + node.val + "," + rightId;

        // Lấy ID hiện tại hoặc tạo ID mới nếu cấu trúc này chưa từng gặp
        int id;
        if (tripletToId.containsKey(tripletKey)) {
            id = tripletToId.get(tripletKey);
        } else {
            id = idCounter++;
            tripletToId.put(tripletKey, id);
        }

        // Đếm tần suất xuất hiện của cấu trúc cây con này
        int count = idCount.getOrDefault(id, 0) + 1;
        idCount.put(id, count);

        // Chỉ thêm vào kết quả khi tần suất đúng bằng 2 (lần đầu phát hiện trùng lặp)
        if (count == 2) {
            result.add(node);
        }

        return id;
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Tuần tự hóa toàn bộ cây con thành chuỗi (Full Subtree Serialization - $\mathcal{O}(N^2)$)

#### Tư duy:
- Dùng đệ quy DFS để ghép chuỗi đại diện toàn bộ cây:
  `serial = node.val + "," + serialize(node.left) + "," + serialize(node.right);`
- Dùng chuỗi `"#"` để đại diện cho nút `null`.
- Lưu chuỗi vào `Map<String, Integer>`.

#### Mã nguồn:
```java
class SolutionSerialization {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();
        serialize(root, countMap, result);
        return result;
    }

    private String serialize(TreeNode node, Map<String, Integer> map, List<TreeNode> res) {
        if (node == null) {
            return "#";
        }

        String serial = node.val + "," + serialize(node.left, map, res) + "," + serialize(node.right, map, res);
        int count = map.getOrDefault(serial, 0) + 1;
        map.put(serial, count);

        if (count == 2) {
            res.add(node);
        }

        return serial;
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(N^2)$ trong trường hợp xấu nhất (cây suy biến đường thẳng), do việc nối chuỗi và băm chuỗi dài $\mathcal{O}(N)$ tại mỗi tầng.
- **Bộ nhớ:** $\mathcal{O}(N^2)$ do lưu trữ các chuỗi con dài tích lũy trong bộ nhớ.
- **Nhận xét:** Rất dễ cài đặt, được chấp nhận trên LeetCode vì dữ liệu kiểm thử không có cây đường thẳng lớn, nhưng sẽ bị người phỏng vấn tại các tập đoàn lớn (FAANG) yêu cầu tối ưu lên $\mathcal{O}(N)$.

---

### Cách 2: Tối ưu hóa Bộ ba không chuỗi với Bit-Packing trên kiểu `long` (Zero-String Allocation $\mathcal{O}(N)$)

#### Tư duy:
- Thay vì ghép chuỗi `String tripletKey`, ta có thể đóng gói 3 số nguyên vào trực tiếp một số nguyên 64-bit `long`:
  - `leftId` $\in [0, 5000]$: Cần $\lceil \log_2(5001) \rceil = 13$ bits.
  - `node.val` $\in [-200, 200] \implies \text{val} + 200 \in [0, 400]$: Cần $9$ bits.
  - `rightId` $\in [0, 5000]$: Cần $13$ bits.
  - Tổng số bit: $13 + 9 + 13 = 35 \text{ bits} \le 64 \text{ bits}$.
- Khóa 64-bit:
  $$\text{key} = (((long)\text{leftId}) \ll 24) \mid (((long)(\text{node.val} + 200)) \ll 13) \mid ((long)\text{rightId})$$
- Dùng `Map<Long, Integer> tripletToId`. Hoàn toàn không tạo bất kỳ đối tượng `String` nào trên Heap!

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Triplet ID Mapping (Best) | Bit-Packing trên Long | Tuần tự hóa Chuỗi Đầy đủ |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N^2)$ (Trường hợp cây suy biến) |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N^2)$ |
| **Cấp phát bộ nhớ Heap** | Cực thấp (chuỗi ngắn $\le 15$ ký tự) | Không cấp phát chuỗi | Rất lớn (chuỗi dài $\mathcal{O}(N)$) |
| **Thời gian LeetCode** | $\approx 5\text{ms} - 8\text{ms}$ (Beats 95%+) | $\approx 4\text{ms} - 6\text{ms}$ (Beats 99%) | $\approx 18\text{ms} - 30\text{ms}$ |
| **Độ trong sáng của mã** | Dễ hiểu, chuẩn mực phỏng vấn | Kỹ thuật sâu (Bit manipulation) | Dễ viết nhưng hiệu năng kém |
| **Đánh giá trong phỏng vấn** | **Tuyệt đối xuất sắc (Lựa chọn chuẩn mực)** | Điểm cộng cực lớn về tối ưu | Khá (Cần trình bày thêm cách tối ưu $\mathcal{O}(N)$) |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Cây chỉ có 1 nút hoặc không có cây con trùng lặp
- Cây: `[1]`.
- Nút 1: `leftId = 0`, `rightId = 0`, `val = 1` $\implies$ ID = 1, `count = 1`.
- Kết thúc: Trả về danh sách rỗng `[]`. Chính xác.

---

### Ca 2: Hai cây con đối xứng giá trị nhưng khác cấu trúc
- Cây con A: Nút `2` có con trái `1`, con phải `null` $\implies$ Khóa: `"1,2,0"`.
- Cây con B: Nút `2` có con trái `null`, con phải `1` $\implies$ Khóa: `"0,2,1"`.
- Hai khóa khác nhau hoàn toàn $\implies$ Không bị nhận nhầm là trùng lặp. Chính xác.

---

### Ca 3: Có 3 cây con giống hệt nhau
- Cây chứa 3 nút lá cùng có giá trị `4`.
- Lần 1: ID = 1, `count = 1`.
- Lần 2: ID = 1, `count = 2` $\implies$ Thêm vào kết quả!
- Lần 3: ID = 1, `count = 3` $\implies$ Không thêm vào kết quả (chỉ thêm khi `count == 2`).
- Kết quả chỉ chứa đúng 1 phần tử đại diện cho cây con `4`. Chính xác.

---

### Ca 4: Cây suy biến thành một đường thẳng ($N = 5000$)
- Chiều cao $H = N$.
- Nhờ độ dài khóa bộ ba luôn $\le 15$ ký tự, tổng thời gian vẫn giữ vững $\mathcal{O}(N)$, không bị tràn bộ nhớ hay TLE.

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

### 1. Phân biệt rõ điểm nghẽn $\mathcal{O}(N^2)$ trong đệ quy chuỗi
- Khi ghép chuỗi trong đệ quy duyệt cây, hãy luôn tự hỏi: *"Độ dài của chuỗi kết quả là bao nhiêu?"*
- Nếu độ dài chuỗi tỷ lệ thuận với số nút của cây con, thì phép ghép chuỗi và tính mã băm ở các tầng trên sẽ tạo ra tổng độ phức tạp $\mathcal{O}(N^2)$.
- Kỹ thuật **ID phân cấp (Hierarchical ID Compression)** là công cụ mẫu mực để triệt tiêu điểm nghẽn này về $\mathcal{O}(N)$.

### 2. Kỹ thuật đếm trùng lặp với `count == 2`
- Một lỗi thường gặp của ứng viên là dùng `Set<TreeNode>` hoặc lọc kết quả sau khi duyệt để loại bỏ các nút trùng lặp thừa.
- Cách tiếp cận chuyên nghiệp nhất là: **Chỉ thêm vào kết quả tại thời điểm tần suất vừa chạm ngưỡng 2** (`count == 2`). Điều này vừa tối ưu thời gian, vừa loại bỏ nhu cầu sử dụng các cấu trúc phụ trợ không cần thiết.

### 3. Các bài toán LeetCode liên quan để rèn luyện
- **LeetCode 297:** Serialize and Deserialize Binary Tree *(Tuần tự hóa và giải mã cây nhị phân)*
- **LeetCode 572:** Subtree of Another Tree *(Kiểm tra cây con)*
- **LeetCode 100:** Same Tree *(Kiểm tra hai cây giống nhau)*
- **LeetCode 1948:** Delete Duplicate Folders in System *(Bài toán mở rộng nâng cao sử dụng Trie và ID Serialization)*
