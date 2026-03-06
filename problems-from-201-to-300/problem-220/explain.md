# Insertion Sort List

## Yêu cầu bài toán

- Cho đầu của một danh sách liên kết đơn.
- Sắp xếp danh sách đó bằng thuật toán **Sắp xếp chèn (Insertion Sort)** và trả về đầu của danh sách đã được sắp xếp.
- Quy trình của Insertion Sort:
  - Duyệt qua từng phần tử của danh sách chưa sắp xếp.
  - Với mỗi phần tử, tìm vị trí thích hợp trong phần danh sách đã được sắp xếp và chèn nó vào đó.

## Ý tưởng cốt lõi

Khác với mảng (Array), việc chèn một phần tử vào giữa danh sách liên kết rất hiệu quả về mặt thời gian (chỉ cần đổi các con trỏ `next`). Tuy nhiên, danh sách liên kết đơn chỉ cho phép duyệt một chiều, vì vậy chúng ta không thể duyệt ngược lại để tìm vị trí chèn như trên mảng.
Giải pháp là sử dụng một node giả (`dummy`) làm mốc bắt đầu của danh sách đã sắp xếp. Với mỗi node cần chèn, chúng ta sẽ duyệt từ `dummy` về phía trước cho đến khi tìm được vị trí phù hợp.

## Thuật toán

1. Khởi tạo một node `dummy` để lưu trữ đầu của kết quả sắp xếp.
2. Sử dụng con trỏ `curr` để duyệt qua danh sách gốc.
3. Trong mỗi bước lặp:
   - Lưu lại node tiếp theo: `next = curr.next`.
   - Tìm vị trí chèn: Sử dụng con trỏ `prev` bắt đầu từ `dummy`. Di chuyển `prev` cho đến khi `prev.next.val` lớn hơn hoặc bằng `curr.val`.
   - Chèn node `curr` vào giữa `prev` và `prev.next`.
   - Tiếp tục với node `next` đã lưu.
4. Trả về `dummy.next`.

## Độ phức tạp
- **Thời gian**: $O(N^2)$ - Trong trường hợp xấu nhất (danh sách giảm dần), chúng ta phải duyệt qua phần đã sắp xếp cho mọi phần tử mới.
- **Không gian**: $O(1)$ - Chỉ sử dụng một vài con trỏ và một node giả.

## Code (Java)

```java
class Solution {
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            ListNode prev = dummy;

            // Tìm vị trí chèn trong danh sách đã sắp xếp
            while (prev.next != null && prev.next.val < curr.val) {
                prev = prev.next;
            }

            // Thực hiện chèn
            curr.next = prev.next;
            prev.next = curr;

            curr = next;
        }

        return dummy.next;
    }
}
```
