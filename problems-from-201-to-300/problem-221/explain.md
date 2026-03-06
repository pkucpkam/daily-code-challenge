# Sort List

## Yêu cầu bài toán

- Cho đầu của một danh sách liên kết đơn.
- Hãy sắp xếp danh sách theo thứ tự tăng dần.
- Yêu cầu: Độ phức tạp thời gian phải là $O(N \log N)$ và độ phức tạp không gian là $O(1)$ (hoặc biến đổi trực tiếp trên danh sách hiện có).

## Ý tưởng cốt lõi

Để đạt được độ phức tạp $O(N \log N)$ cho danh sách liên kết, thuật toán hiệu quả nhất là **Sắp xếp trộn (Merge Sort)**.
Merge Sort hoạt động theo nguyên lý "Chia để trị":
1. **Chia**: Chia danh sách thành hai nửa bằng cách tìm trung điểm (sử dụng hai con trỏ `slow` và `fast`).
2. **Trị (Đệ quy)**: Tiếp tục chia nhỏ cho đến khi danh sách chỉ còn 1 node (đã được sắp xếp).
3. **Trộn**: Hợp nhất hai danh sách đã sắp xếp lại thành một danh sách duy nhất.

Đối với danh sách liên kết, việc trộn hai danh sách đã sắp xếp rất tối ưu vì không cần dùng thêm mảng phụ như khi thao tác trên mảng thông thường.

## Thuật toán

1. **Điều kiện dừng**: Nếu `head` là `null` hoặc chỉ có 1 node, trả về chính nó.
2. **Tìm trung điểm**:
   - Sử dụng `slow` và `fast`. Khi vòng lặp kết thúc, `slow` nằm ở trung điểm.
   - Ngắt kết nối danh sách: `mid = slow.next`, `slow.next = null`.
3. **Đệ quy**: Gọi `sortList` cho nửa đầu (bắt đầu từ `head`) và nửa sau (bắt đầu từ `mid`).
4. **Trộn (Merge)**: Sử dụng một hàm hỗ trợ để trộn hai danh sách kết quả dựa trên so sánh giá trị các node.

## Độ phức tạp
- **Thời gian**: $O(N \log N)$ - Tương tự như Merge Sort trên mảng.
- **Không gian**: $O(\log N)$ - Do sử dụng ngăn xếp đệ quy (recursion stack). Nếu muốn đạt $O(1)$, cần sử dụng phương pháp lặp (iterative merge sort).

## Code (Java)

```java
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // 1. Chia danh sách
        ListNode mid = getMid(head);
        ListNode left = head;
        ListNode right = mid.next;
        mid.next = null; // Ngắt kết nối

        // 2. Đệ quy sắp xếp từng nửa
        left = sortList(left);
        right = sortList(right);

        // 3. Trộn lại
        return merge(left, right);
    }

    private ListNode getMid(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
}
```
