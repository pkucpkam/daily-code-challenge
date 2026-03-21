# 199. Binary Tree Right Side View

**Độ khó:** Medium

## Mô tả bài toán

Cho một cây nhị phân, hãy tưởng tượng bạn đang đứng ở phía bên phải của cây và nhìn vào nó. Trả về các giá trị của các nút mà bạn có thể nhìn thấy theo thứ tự từ trên xuống dưới.

## Ví dụ

### Ví dụ 1:
**Input:** `root = [1,2,3,null,5,null,4]`

**Output:** `[1,3,4]`

![Example 1](https://assets.leetcode.com/uploads/2024/11/24/tmpd5jn43fs-1.png)

### Ví dụ 2:
**Input:** `root = [1,2,3,4,null,null,null,5]`

**Output:** `[1,3,4,5]`

![Example 2](https://assets.leetcode.com/uploads/2024/11/24/tmpkpe40xeh-1.png)

### Ví dụ 3:
**Input:** `root = [1,null,3]`

**Output:** `[1,3]`

### Ví dụ 4:
**Input:** `root = []`

**Output:** `[]`

## Ràng buộc

- Số lượng nút trong cây nằm trong khoảng `[0, 100]`
- `-100 <= Node.val <= 100`