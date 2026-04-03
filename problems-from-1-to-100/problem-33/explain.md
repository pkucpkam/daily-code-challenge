# Linked List Cycle

## 1. Mô tả bài toán
Cho `head` của một danh sách liên kết, hãy xác định xem danh sách đó có chứa một chu kỳ (vòng lặp) hay không.
Một chu kỳ xảy ra khi có một node trong danh sách có thể được truy cập lại bằng cách liên tục đi theo con trỏ `next`.

## 2. Ý tưởng cốt lõi
- Sử dụng thuật toán **Floyd’s Cycle-Finding Algorithm** (thường được gọi là thuật toán "Rùa và Thỏ").
- Ý tưởng là sử dụng hai con trỏ di chuyển với tốc độ khác nhau:
    - Con trỏ **chậm** (`slow`): Di chuyển 1 bước mỗi lần.
    - Con trỏ **nhanh** (`fast`): Di chuyển 2 bước mỗi lần.
- Nếu danh sách có vòng lặp, con trỏ nhanh chắc chắn sẽ "đuổi kịp" và gặp con trỏ chậm tại một thời điểm nào đó bên trong vòng lặp.
- Nếu không có vòng lặp, con trỏ nhanh sẽ chạm tới cuối danh sách (`null`).

## 3. Giải thích thuật toán
1. Kiểm tra trường hợp đặc biệt: Nếu `head` hoặc `head.next` là `null`, trả về `false`.
2. Khởi tạo hai con trỏ:
   - `slow = head`
   - `fast = head.next`
3. Chạy vòng lặp trong khi `slow` khác `fast`:
   - Nếu `fast` hoặc `fast.next` là `null`, nghĩa là đã đi tới cuối danh sách mà không gặp vòng lặp -> Trả về `false`.
   - Di chuyển `slow` thêm 1 bước: `slow = slow.next`.
   - Di chuyển `fast` thêm 2 bước: `fast = fast.next.next`.
4. Nếu thoát khỏi vòng lặp `while` (nghĩa là `slow == fast`), trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Trong đó `n` là số lượng node. Nếu không có chu kỳ, con trỏ nhanh đi hết danh sách. Nếu có chu kỳ, con trỏ nhanh sẽ gặp con trỏ chậm sau một số vòng lặp tỉ lệ thuận với số node.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai con trỏ, không tốn thêm bộ nhớ theo kích thước danh sách.

## 5. Code (Java)
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (slow != fast) {
            // Nếu con trỏ nhanh chạm tới cuối, không có vòng lặp
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return true;
    }
}
```
