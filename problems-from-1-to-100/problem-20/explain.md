## 1. Mô tả bài toán

Bài toán yêu cầu chúng ta xóa bỏ các phần tử trùng lặp trong một danh sách liên kết đã được sắp xếp. Điều kiện là mỗi phần tử chỉ được xuất hiện một lần duy nhất. Sau khi xóa, danh sách liên kết phải vẫn giữ nguyên thứ tự sắp xếp tăng dần.

Ví dụ:
*   Nếu danh sách đầu vào là `[1,1,2]`, đầu ra mong muốn là `[1,2]`.
*   Nếu danh sách đầu vào là `[1,1,2,3,3]`, đầu ra mong muốn là `[1,2,3]`.

## 2. Ý tưởng cốt lõi

Vì danh sách liên kết đã được sắp xếp, các phần tử trùng lặp sẽ luôn nằm cạnh nhau. Ý tưởng chính là duyệt qua danh sách và so sánh giá trị của nút hiện tại (`current`) với nút kế tiếp (`current.next`). Nếu chúng có giá trị bằng nhau, điều đó có nghĩa là `current.next` là một phần tử trùng lặp. Chúng ta chỉ cần bỏ qua nút trùng lặp này bằng cách trỏ con trỏ `next` của nút `current` đến nút sau nút trùng lặp (`current.next.next`). Nếu các giá trị không bằng nhau, tức là không có trùng lặp, chúng ta chỉ cần di chuyển con trỏ `current` đến nút kế tiếp và tiếp tục kiểm tra.

## 3. Giải thích thuật toán

Thuật toán hoạt động theo các bước sau:

1.  **Xử lý trường hợp đặc biệt:**
    *   Nếu danh sách rỗng (`head == null`), không có gì để làm, trả về `null`.

2.  **Khởi tạo con trỏ:**
    *   Tạo một con trỏ `current` và khởi tạo nó trỏ vào `head` của danh sách. Con trỏ này sẽ dùng để duyệt qua danh sách.

3.  **Duyệt và xóa trùng lặp:**
    *   Sử dụng một vòng lặp `while` để duyệt qua danh sách. Vòng lặp sẽ tiếp tục chừng nào `current` chưa phải là nút cuối cùng (tức là `current.next` không phải `null`).
    *   **Bên trong vòng lặp:**
        *   **So sánh giá trị:** Kiểm tra xem giá trị của nút `current` (`current.val`) có bằng giá trị của nút kế tiếp (`current.next.val`) hay không.
        *   **Nếu có trùng lặp (`current.val == current.next.val`):**
            *   Điều này có nghĩa là `current.next` là một bản sao của `current`. Để loại bỏ nó, chúng ta cập nhật con trỏ `next` của `current` để nó trỏ trực tiếp đến nút sau nút `current.next` (tức là `current.next = current.next.next`).
            *   **Quan trọng:** Sau khi xóa nút trùng lặp, chúng ta không di chuyển con trỏ `current` sang nút kế tiếp. Bởi vì nút mới mà `current.next` trỏ tới có thể cũng là một bản sao của `current` (ví dụ: `1 -> 1 -> 1 -> 2`). Nếu di chuyển `current`, chúng ta có thể bỏ sót việc xóa.
        *   **Nếu không có trùng lặp (`current.val != current.next.val`):**
            *   Không có trùng lặp giữa `current` và `current.next`. Chúng ta an toàn để di chuyển con trỏ `current` lên một nút (`current = current.next`) để tiếp tục kiểm tra các cặp nút tiếp theo.

4.  **Trả về kết quả:**
    *   Sau khi vòng lặp kết thúc, tất cả các phần tử trùng lặp đã được xóa. Trả về `head` của danh sách đã được chỉnh sửa.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity): O(N)**
    *   Chúng ta chỉ duyệt qua danh sách liên kết một lần duy nhất. Mỗi nút được truy cập và xử lý (so sánh hoặc cập nhật con trỏ `next`) một số lần hằng số. Do đó, thời gian thực thi tỷ lệ tuyến tính với số lượng nút `N` trong danh sách.

*   **Độ phức tạp không gian (Space Complexity): O(1)**
    *   Chúng ta chỉ sử dụng một vài con trỏ (`head`, `current`) mà không sử dụng bất kỳ cấu trúc dữ liệu bổ sung nào phụ thuộc vào kích thước của đầu vào. Vì vậy, độ phức tạp không gian là hằng số.

## 5. Code

java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // Xử lý trường hợp danh sách rỗng
        if (head == null) {
            return null;
        }

        // Khởi tạo con trỏ 'current' bắt đầu từ đầu danh sách
        ListNode current = head;

        // Duyệt qua danh sách cho đến khi 'current' hoặc 'current.next' là null
        // Tức là 'current' không phải là nút cuối cùng
        while (current.next != null) {
            // Nếu giá trị của nút hiện tại bằng giá trị của nút kế tiếp
            if (current.val == current.next.val) {
                // Bỏ qua nút kế tiếp (là nút trùng lặp) bằng cách
                // trỏ con trỏ 'next' của 'current' đến nút sau nút kế tiếp
                current.next = current.next.next;
            } else {
                // Nếu không có trùng lặp, di chuyển con trỏ 'current' đến nút kế tiếp
                current = current.next;
            }
        }

        // Trả về đầu danh sách đã được chỉnh sửa
        return head;
    }
}