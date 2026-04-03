# Palindrome Linked List

## 1. Mô tả bài toán
Cho `head` của một danh sách liên kết đơn. Hãy kiểm tra xem danh sách đó có phải là một **chuỗi đối xứng (palindrome)** hay không.
Ví dụ:
- `1 -> 2 -> 2 -> 1`: `true`
- `1 -> 2`: `false`

## 2. Ý tưởng cốt lõi
- Để kiểm tra tính đối xứng của Linked List với độ phức tạp $O(n)$ thời gian và $O(1)$ không gian, ta thực hiện các bước:
    1. Tìm node ở giữa cây bằng thuật toán **Hai con trỏ (Trỏ nhanh và Trỏ chậm)**.
    2. Đảo ngược nửa sau của danh sách liên kết từ vị trí node giữa.
    3. So sánh các giá trị của nửa đầu và nửa sau đã đảo ngược.
    4. Trả về kết quả.

## 3. Giải thích thuật toán
1. **Tìm trung điểm**: Dùng `slow` và `fast`. Khi `fast` đi hết danh sách, `slow` sẽ nằm ở giữa.
2. **Đảo ngược nửa sau**: Bắt đầu từ `slow`, thực hiện đảo ngược liên kết các node giống như bài toán "Reverse Linked List".
3. **So sánh**:
   - Dùng con trỏ `firstHalf` bắt đầu từ `head`.
   - Dùng con trỏ `secondHalf` bắt đầu từ đầu danh sách vừa đảo ngược (`prev`).
   - Duyệt song song qua cả hai nửa. Nếu phát hiện giá trị khác nhau, trả về `false`.
4. Nếu duyệt hết nửa sau mà không có sai khác, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta duyệt qua danh sách tối đa 3 lần (tìm giữa, đảo ngược, so sánh).
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng các biến con trỏ, không tốn bộ nhớ phụ tỷ lệ với số lượng node.

## 5. Code (Java)
```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 1. Tìm node giữa
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Đảo ngược nửa sau của danh sách
        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        // 3. So sánh hai nửa
        ListNode firstHalf = head, secondHalf = prev;
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return true;
    }
}
```
