# 199. Binary Tree Right Side View - BEST Solution (Thuật toán tối ưu)

## Ý tưởng chính: DFS với Right-First Traversal

**Khoá vàng:** Duyệt DFS từ **phải sang trái** (right-first). Lần đầu tiên ta gặp một độ sâu chính là node **bên phải nhất** ở tầng đó.

### Chiến lược:
1. Duyệt DFS với tham số `depth`
2. Khi `depth == result.size()` → node này chưa được thêm → nó là node bên phải nhất ở tầng này
3. **Ưu tiên đi phải trước** để đảm bảo node bên phải nhất được ghé thăm trước

## Tại sao DFS tốt hơn BFS về mặt thuật toán?

### So sánh chi tiết:

| Tiêu chí | BFS | DFS (Right-First) |
|---------|-----|-------------------|
| **Thời gian** | O(n) | O(n) |
| **Bộ nhớ (cây cân bằng)** | **O(n/2)** = O(n) | **O(log n)** ✅ Tốt hơn |
| **Bộ nhớ (cây lẻch)** | O(1) | O(n) |
| **Code độ phức tạp** | Bình thường | Đơn giản hơn |
| **Dễ hiểu** | Trực quan | Hơi khó một tí |

**Kết luận:** DFS right-first là **tốt nhất về mặt thuật toán** vì:
- ✅ Tiết kiệm bộ nhớ hơn cho cây cân bằng (phổ biến nhất)
- ✅ Code ngắn gọn, thanh lịch
- ✅ Độ phức tạp thời gian giống nhau

---

## Ví dụ: Cây cân bằng

```
           1
         /   \
        2     3          <- Tầng 1: rộng 2
       / \   / \
      4  5  6  7         <- Tầng 2: rộng 4
```

### BFS:
- Tầng 1: Lưu [2, 3] trong queue → bộ nhớ = 2
- Tầng 2: Lưu [4, 5, 6, 7] trong queue → bộ nhớ = **4** (tệ!)
- **Space: O(4) = O(n/2)**

### DFS Right-First:
- Đi sâu vào node phải nhất: 1 → 3 → 7
- Stack chỉ chứa đường đi: [1, 3, 7]
- **Space: O(3) = O(log n)** ✅ Tốt hơn!

---

## Thuật toán chi tiết

```
dfs(node, depth, result):
  if node == null:
    return
  
  if depth == result.size():
    # Lần đầu gặp độ sâu này → node bên phải nhất
    result.add(node.val)
  
  # QUAN TRỌNG: Đi phải trước!
  dfs(node.right, depth + 1, result)
  dfs(node.left, depth + 1, result)
```

---

## Ví dụ qua từng bước

### Input: `root = [1,2,3,null,5,null,4]`

```
        1          
       / \
      2   3        
       \   \
        5   4     

Expected: [1, 3, 4]
```

**Trace (DFS Right-First):**

| Call | Node | Depth | result.size() | Hành động | Result |
|------|------|-------|----------------|-----------|--------|
| 1 | 1 | 0 | 0 | 0==0 ✅ Add 1 | [1] |
| 2 | 3 | 1 | 1 | 1==1 ✅ Add 3 | [1,3] |
| 3 | 4 | 2 | 2 | 2==2 ✅ Add 4 | [1,3,4] |
| 4 | null | 3 | 3 | return | [1,3,4] |
| 5 | 2 | 1 | 3 | 1!=3 (skip) | [1,3,4] |
| 6 | 5 | 2 | 3 | 2!=3 (skip) | [1,3,4] |

**Output:** `[1, 3, 4]` ✅

### Input: `root = [1,2,3,4,null,null,null,5]`

```
        1          
       / \
      2   3        
     / 
    4    
     \
      5      

Expected: [1, 3, 5]
```

**Trace:**

| Bước | Node | Depth | Action | Result |
|------|------|-------|--------|--------|
| Đi phải | 1 | 0 | Add 1 | [1] |
| Đi phải | 3 | 1 | Add 3 | [1,3] |
| Đi phải | null | 2 | return | [1,3] |
| Đi trái | null | 2 | return | [1,3] |
| Đi trái | 2 | 1 | 1!=2 (skip) | [1,3] |
| Đi phải | 4 | 2 | Add 4 | [1,3,4] |❌ Sai!

**Chờ, hãy vẽ lại cây từ mảng [1,2,3,4,null,null,null,5]:**

Indices:   0  1  2  3  4  5  6  7
Array:    [1, 2, 3, 4, n, n, n, 5]

Vì array index bắt đầu từ 0:
- Node 1 (idx 0): left=idx 1 (node 2), right=idx 2 (node 3)
- Node 2 (idx 1): left=idx 3 (node 4), right=idx 4 (null)
- Node 3 (idx 2): left=idx 5 (null), right=idx 6 (null)
- Node 4 (idx 3): left=idx 7 (node 5), right=idx 8 (out of bound = null)

```
        1          
       / \
      2   3        
     / 
    4    
   /
  5     

Expected: [1, 3, 5]
```

**Trace (DFS Right-First):**

| Bước | Node | Depth | result.size() | Action | Result |
|------|------|-------|---|--------|--------|
| 1 | 1 | 0 | 0 | 0==0 Add | [1] |
| 2 | 3 | 1 | 1 | 1==1 Add | [1,3] |
| 3 | null (3.right) | 2 | 2 | return | [1,3] |
| 4 | null (3.left) | 2 | 2 | return | [1,3] |
| 5 | 2 | 1 | 2 | 1!=2 skip | [1,3] |
| 6 | 4 | 2 | 2 | 2==2 Add | [1,3,4] |

**Hmm, Output: [1,3,4]** không khớp expected [1,3,5].

Tôi nghĩ tôi hiểu nhầm. Hãy tính lại. Nếu ta đứng bên phải cây [1,2,3,4,null,null,null,5]:

```
        1          -> Nhìn thấy 1
       / \
      2   3        -> Nhìn thấy 3 (nó ở bên phải)
     / 
    4              -> Nhìn thấy 4 (nhưng ở tầng 2, vì chỉ có nó)
   /
  5                -> Nhìn thấy 5 (ở tầng 3, chỉ có nó)
```

Vậy expected output phải là [1, 3, 4, 5]!

Actually, hãy để tôi thử hiểu lại. Nếu ta đứng bên phải:
- Tầng 0: 1
- Tầng 1: 2 bên trái, 3 bên phải → thấy 3
- Tầng 2: 4 bên trái dưới 2, không có nút dưới 3 → thấy 4
- Tầng 3: 5 dưới 4 → thấy 5

Đúng, output phải là [1, 3, 4, 5].

Vậy DFS right-first sẽ:
- depth 0, node 1: add 1
- depth 1, node 3: add 3
- depth 2, node 4: add 4
- depth 3, node 5: add 5

OK, điều này có ý nghĩa.

---

## Code (Java)

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int depth, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Nếu đây là lần đầu tiên ta gặp độ sâu này
        // => node này là node bên phải nhất ở tầng này
        if (depth == result.size()) {
            result.add(node.val);
        }
        
        // Ưu tiên đi sang PHẢI trước
        // Để đảm bảo node bên phải nhất được ghé thăm trước
        dfs(node.right, depth + 1, result);
        dfs(node.left, depth + 1, result);
    }
}
```

---

## Độ phức tạp

| Tiêu chí | Giá trị |
|---------|--------|
| **Thời gian (Time)** | **O(n)** - duyệt mỗi nút 1 lần |
| **Bộ nhớ (Space)** | **O(h)** - đệ quy stack có chiều cao cây |
| | • Cây cân bằng: **O(log n)** ✅ |
| | • Cây lẻch: **O(n)** |

---

## Tại sao "Đi phải trước" lại quan trọng?

Nếu ta đi **trái trước**:

```
dfs(node.left, depth + 1, result);   // ❌ Sai!
dfs(node.right, depth + 1, result);
```

Với cây:
```
      1
     / \
    2   3
```

- Đi trái: depth 1, node 2 → add 2 ❌ SAI! Node bên phải là 3!
- Đi phải: depth 1, node 3 → add 3 ✅ Đúng!

**Vì vậy:** Đi phải trước để node bên phải nhất được gặp và thêm vào kết quả **trước**.

---

## So sánh 3 cách tiếp cận

### 1. BFS (Level Order)
```java
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        // Add nút cuối cùng
    }
}
// Space: O(w) - rộng tối đa
```

### 2. DFS Right-First ⭐ BEST
```java
dfs(node.right, ...);  // Phải trước
dfs(node.left, ...);
// Space: O(h) - chiều cao, tốt hơn cho cây cân bằng
```

### 3. DFS Sử dụng HashMap (Độ sâu tối đa)
```java
Map<Integer, Integer> depthToValue = ...;
// Cộng độ phức tạp thêm vào
```

**Kết luận:** **DFS Right-First** là cách tốt nhất! 🏆



