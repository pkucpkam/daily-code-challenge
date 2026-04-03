# Remove Linked List Elements

## 1. Mô tả bài toán
Cho `head` của một danh sách liên kết và một giá trị `val`. Hãy xóa tất cả các node trong danh sách có giá trị bằng `val` và trả về `head` mới của danh sách.

## 2. Ý tưởng cốt lõi
- Khó khăn lớn nhất khi xóa node trong Linked List là khi các node cần xóa nằm ở ngay đầu danh sách (`head`).
- Để giải quyết triệt để và đơn giản hóa code, ta sử dụng một **Node giả (Dummy Node)** đặt trước `head`.
- Duyệt qua danh sách, nếu node tiếp theo có giá trị cần xóa, ta chỉ cần bỏ qua nó bằng cách trỏ `next` của node hiện tại tới node sau nữa.

## 3. Giải thích thuật toán
1. Tạo một `dummy` node có `dummy.next` trỏ tới `head`.
2. Sử dụng con trỏ `current` bắt đầu từ `dummy`.
3. Vòng lặp `while (current.next != null)`:
   - Nếu `current.next.val == val`: Tiến hành xóa node `next` bằng cách gán `current.next = current.next.next`. Lưu ý: Sau khi xóa, ta **không** di chuyển `current` ngay vì node mới ở vị trí `next` cũng có thể mang giá trị cần xóa.
   - Ngược lại: Di chuyển `current = current.next`.
4. Trả về `dummy.next`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua danh sách liên kết đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng thêm một node giả và một con trỏ duy nhất.

## 5. Code (Java)
```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        // Sử dụng node giả để xử lý trường hợp cần xóa node đầu tiên
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;

        while (current.next != null) {
            if (current.next.val == val) {
                // Nhảy qua node cần xóa
                current.next = current.next.next;
            } else {
                // Chỉ di chuyển tiếp nếu node tiếp theo không phải là node cần xóa
                current = current.next;
            }
        }

        return dummy.next;
    }
}
```
