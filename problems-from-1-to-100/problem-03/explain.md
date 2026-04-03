# Add Two Numbers

## 1. Mô tả bài toán
Cho hai danh sách liên kết không rỗng (Linked List) đại diện cho hai số nguyên không âm. Các chữ số được lưu trữ theo thứ tự ngược (chữ số hàng đơn vị ở đầu danh sách). Nhiệm vụ là cộng gộp hai số này và trả về kết quả dưới dạng một Linked List tương tự.

## 2. Ý tưởng cốt lõi
- Xử lý cùng lúc từng node song song từ 2 danh sách như phép toán cộng viết giấy từ hàng đơn vị sang hàng tỷ. 
- Xây dựng một danh sách liên kết mới qua node ảo (dummy node).
- Giữ một biến nhớ (`carry`) để cộng vào node tiếp theo nếu phép cộng hiện tại >= 10.

## 3. Giải thích thuật toán
- Node `d` dùng giữ đỉnh của danh sách liên kết mới, con trỏ `current` để đi tiếp từng nhịp.
- Vòng lặp `while l1 or l2 or carry` dùng để xử lý hết mọi node trong hai danh sách và đảm bảo việc cộng số dư cuối cùng.
- Nếu list 1 hay 2 đã đi qua hết thì xem toán hạng đó như là mốc bằng `0` thông qua toán tử rút gọn `val1 = l1.val if l1 else 0`.
- Cập nhật tổng `total` cộng theo công thức `val1 + val2 + carry`.
- Cập nhật `carry` nhận phần chục của tổng `total // 10`, còn node tiếp nối của kết quả sẽ mang phần đơn vị `total % 10`.
- Nhảy con trỏ tham chiếu của `current`, `l1`, `l2` đến node kế tiếp và lặp lại cho đến cuối con đường.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \( O(\max(M, N)) \). Tiến độ phụ thuộc vào chiều dài của danh sách dài nhất mà ta phải lặp.
- **Không gian (Space Complexity)**: \( O(\max(M, N)) \). Chiều dài của danh sách liên kết mới tối đa là lớn hơn chuỗi gốc 1 đơn vị (nếu carry ra 1 hàng phụ).

## 5. Code
```python
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution(object):
    def addTwoNumbers(self, l1, l2):
        """
        :type l1: Optional[ListNode]
        :type l2: Optional[ListNode]
        :rtype: Optional[ListNode]
        """
        d = ListNode()
    current = d
    carry = 0  
    
    while l1 or l2 or carry:
        val1 = l1.val if l1 else 0
        val2 = l2.val if l2 else 0
        
        total = val1 + val2 + carry
        carry = total // 10  
        current.next = ListNode(total % 10) 
        current = current.next  
        
        if l1: l1 = l1.next
        if l2: l2 = l2.next
    
    return d.next
```
