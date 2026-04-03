# Reverse Linked List

## 1. Mô tả bài toán
Cho `head` của một danh sách liên kết đơn. Hãy đảo ngược danh sách này và trả về danh sách đã đảo ngược.
Ví dụ: `1 -> 2 -> 3` thành `3 -> 2 -> 1`.

## 2. Ý tưởng cốt lõi
- Bài toán này có thể giải quyết bằng cách thay đổi hướng của các con trỏ `next`. Thay vì trỏ tới node tiếp theo, mỗi node sẽ trỏ ngược lại node đứng trước nó.
- Ta cần sử dụng 3 con trỏ đi theo trình tự để không bị mất liên lạc với phần còn lại của danh sách:
    1. `prev`: Lưu trữ node đứng trước (ban đầu là `null`).
    2. `curr`: Node hiện tại đang xử lý (ban đầu là `head`).
    3. `next`: Tạm thời lưu trữ node tiếp theo trước khi ta thay đổi liên kết của `curr`.

## 3. Giải thích thuật toán
1. Khởi tạo `prev = null` và `curr = head`.
2. Vòng lặp `while (curr != null)`:
   - Lưu lại node tiếp theo: `next = curr.next`.
   - **Đảo ngược liên kết**: Cho `curr.next` trỏ về `prev`.
   - Di chuyển cặp con trỏ lên một bước:
     - `prev = curr`.
     - `curr = next`.
3. Khi `curr` bằng `null`, vòng lặp kết thúc. Lúc này `prev` chính là node đầu mới của danh sách đã đảo ngược. Trả về `prev`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mỗi node trong danh sách đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một vài biến con trỏ, không tốn thêm bộ nhớ.

## 5. Code (Java)
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            // 1. Lưu lại node tiếp theo
            ListNode next = curr.next;
            
            // 2. Đảo ngược con trỏ
            curr.next = prev;
            
            // 3. Tiến con trỏ lên
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
}
```
