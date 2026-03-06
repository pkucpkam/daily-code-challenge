# Copy List with Random Pointer

## Yêu cầu bài toán

- Cho một danh sách liên kết, trong đó mỗi node ngoài con trỏ `next` còn có thêm một con trỏ `random`. Con trỏ `random` này có thể trỏ tới bất kỳ node nào trong danh sách hoặc trỏ tới `null`.
- Nhiệm vụ: Tạo ra một bản sao hoàn chỉnh (**Deep Copy**) của danh sách này.
- Bản sao phải hoàn toàn độc lập với danh sách gốc về mặt bộ nhớ, nhưng phải giữ nguyên cấu trúc (giá trị, liên kết `next` và liên kết `random`).

## Ý tưởng cốt lõi

Thách thức lớn nhất là xử lý con trỏ `random`, vì khi chúng ta sao chép một node, node mà `random` trỏ tới có thể chưa được tạo ra.

Có hai cách tiếp cận phổ biến:
1. **Sử dụng HashMap**: Lưu ánh xạ từ `Node gốc -> Node sao chép`. Cách này tốn $O(N)$ bộ nhớ phụ.
2. **Phương pháp xen kẽ (Interweaving)**: Chèn các node bản sao trực tiếp vào sau các node gốc tương ứng. Cách này đạt được $O(1)$ bộ nhớ phụ (không tính bộ nhớ cho các node bản sao).

## Thuật toán (Phương pháp xen kẽ)

Thuật toán gồm 3 bước chính:
1. **Sao chép Node và xen kẽ**: Duyệt danh sách, với mỗi node gốc `A`, tạo bản sao `A'`. Chèn `A'` ngay sau `A`.
   - Danh sách sẽ trở thành: `A -> A' -> B -> B' -> C -> C'`.
2. **Thiết lập con trỏ Random for Node bản sao**: 
   - Với mỗi node gốc `cur`, node bản sao của nó là `cur.next`.
   - Nếu `cur.random` không null, thì `cur.next.random` chính là `cur.random.next` (vì node bản sao của node được trỏ tới cũng nằm ngay sau node đó).
3. **Tách danh sách**: Duyệt lại và tách danh sách xen kẽ thành hai danh sách riêng biệt: danh sách gốc ban đầu và danh sách bản sao mới.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt danh sách 3 lần.
- **Không gian**: $O(1)$ - Không sử dụng cấu trúc dữ liệu phụ trợ như HashMap.

## Code (Java)

```java
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;

        // B1: Tạo node bản sao và chèn xen kẽ
        Node cur = head;
        while (cur != null) {
            Node copy = new Node(cur.val);
            copy.next = cur.next;
            cur.next = copy;
            cur = copy.next;
        }

        // B2: Thiết lập con trỏ random cho các bản sao
        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        // B3: Tách hai danh sách
        cur = head;
        Node pseudoHead = new Node(0);
        Node copyIter = pseudoHead;
        while (cur != null) {
            Node next = cur.next.next;
            
            // Lấy ra bản sao và nối vào danh sách mới
            Node copy = cur.next;
            copyIter.next = copy;
            copyIter = copy;

            // Khôi phục lại liên kết next cho danh sách gốc
            cur.next = next;
            cur = next;
        }

        return pseudoHead.next;
    }
}
```
