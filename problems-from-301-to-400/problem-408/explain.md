# 559. Maximum Depth of N-ary Tree

## Intuition

Một **N-ary Tree** là cây mà mỗi node có thể có bất kỳ số lượng con nào. Để tìm **độ sâu tối đa**, ta cần đi xuống tất cả các nhánh và chọn nhánh dài nhất.

Ý tưởng tự nhiên nhất là dùng **DFS (Depth-First Search)** đệ quy:
- Mỗi node đóng góp thêm **1 tầng** vào độ sâu.
- Độ sâu của một node = **độ sâu lớn nhất trong số các con** + 1.
- **Base case:** node `null` → trả về 0.

---

## Key Insights & Algorithmic Strategy

### 1. Base Case

```
root == null  →  return 0
```

Nếu cây rỗng, độ sâu bằng 0.

### 2. Đệ Quy (Post-order DFS)

Với mỗi node, ta duyệt qua **tất cả các con**, gọi đệ quy để lấy độ sâu từng nhánh, rồi lấy **max** trong số đó:

```
maxDepth(node) = max(maxDepth(child) for child in node.children) + 1
```

### 3. Tại Sao Không Cần BFS?

BFS (dùng queue) cũng cho kết quả đúng với O(N) nhưng tốn thêm bộ nhớ queue. DFS đệ quy:
- Code ngắn gọn hơn
- Stack space = O(H) — tương đương BFS trong trường hợp xấu nhất

---

## Code Implementation (Java)

```java
class Solution {
    public int maxDepth(Node root) {
        if (root == null) return 0;

        int maxChildDepth = 0;
        for (Node child : root.children) {
            maxChildDepth = Math.max(maxChildDepth, maxDepth(child));
        }

        return maxChildDepth + 1;
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `root = [1,null,3,2,4,null,5,6]`

Cấu trúc cây:
```
        1
      / | \
     3  2  4
    / \
   5   6
```

| Lời Gọi | Trả Về |
|:---|:---|
| `maxDepth(5)` | 0 + 1 = **1** |
| `maxDepth(6)` | 0 + 1 = **1** |
| `maxDepth(3)` | max(1, 1) + 1 = **2** |
| `maxDepth(2)` | 0 + 1 = **1** |
| `maxDepth(4)` | 0 + 1 = **1** |
| `maxDepth(1)` | max(2, 1, 1) + 1 = **3** ✅ |

### Example 2: `root = [1,null,2,3,4,5,null,...]`

Cây có 5 tầng → Output: **5** ✅

---

## Complexity Analysis

| Tiêu Chí | Giá Trị | Ghi Chú |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(N)$ | Mỗi node được thăm đúng 1 lần |
| **Space Complexity** | $\mathcal{O}(H)$ | Stack đệ quy sâu theo chiều cao cây $H$ |

- **Worst case space:** $\mathcal{O}(N)$ khi cây suy biến thành danh sách liên kết (mỗi node chỉ có 1 con).
- **Best case space:** $\mathcal{O}(\log N)$ khi cây cân bằng hoàn toàn.

---

## So Sánh Các Cách Tiếp Cận

| Approach | Time | Space | Nhận Xét |
|:---|:---|:---|:---|
| **DFS Đệ Quy** ✅ | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | **Tối ưu** — ngắn gọn, dễ hiểu |
| **BFS Iterative** | $\mathcal{O}(N)$ | $\mathcal{O}(W)$ | Dùng queue, $W$ = độ rộng tối đa của cây |
| **DFS Iterative** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | Dùng stack tường minh, dài hơn DFS đệ quy |
