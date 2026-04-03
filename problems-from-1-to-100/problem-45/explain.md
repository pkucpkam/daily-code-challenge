# Happy Number

## 1. Mô tả bài toán
Một số được gọi là **số hạnh phúc (happy number)** nếu thực hiện quy trình sau và kết thúc bằng số 1:
- Thay thế số đó bằng tổng bình phương các chữ số của nó.
- Tiếp tục quá trình này cho đến khi số đó bằng 1 (là hạnh phúc), hoặc nó lặp vô hạn trong một chu kỳ không bao gồm số 1 (không hạnh phúc).

## 2. Ý tưởng cốt lõi
- Bài toán này có thể được xem như việc phát hiện một vòng lặp trong một danh sách các số được tạo ra.
- Tương tự như bài toán phát hiện vòng lặp trong Linked List, ta có thể sử dụng thuật toán **Hai con trỏ (Floyd's Cycle-Finding Algorithm)**:
    - Con trỏ `slow` thực hiện tính toán 1 bước.
    - Con trỏ `fast` thực hiện tính toán 2 bước.
- Nếu `slow` và `fast` gặp nhau tại giá trị 1, đó là số hạnh phúc. Nếu gặp nhau tại một giá trị khác 1, nghĩa là nó rơi vào một vòng lặp vô tận.

## 3. Giải thích thuật toán
1. Hàm trợ giúp `getNext(n)`: Tính tổng bình phương các chữ số của `n`.
2. Khởi tạo `slow = n`, `fast = n`.
3. Sử dụng vòng lặp `do-while`:
   - `slow = getNext(slow)` (đi 1 bước).
   - `fast = getNext(getNext(fast))` (đi 2 bước).
   - Tiếp tục cho đến khi `slow == fast`.
4. Sau khi thoát vòng lặp (vì chắc chắn sẽ gặp nhau hoặc chạm tới 1), kiểm tra:
   - Nếu `slow == 1`, trả về `true`.
   - Ngược lại trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: Khó xác định chính xác nhưng thực nghiệm cho thấy rất nhanh (gần như \(O(\log n)\)). Các số không hạnh phúc thường rơi vào một chu kỳ cố định (ví dụ chu kỳ có chứa số 4).
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai biến con trỏ.

## 5. Code (Java)
```java
class Solution {
    public boolean isHappy(int n) {
        int slow = n, fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast);

        return slow == 1;
    }

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int digit = n % 10;
            totalSum += digit * digit;
            n /= 10;
        }
        return totalSum;
    }
}
```
