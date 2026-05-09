# 341. Flatten Nested List Iterator - Giải thích thuật toán

## Phân tích bài toán
Bài toán yêu cầu chúng ta thiết kế một Iterator để "làm phẳng" (flatten) một danh sách lồng nhau (`NestedList`). Mỗi phần tử trong danh sách có thể là một số nguyên hoặc lại là một danh sách khác chứa các số nguyên hoặc các danh sách khác.

Mục tiêu là khi gọi `next()`, chúng ta sẽ nhận được số nguyên tiếp theo theo đúng thứ tự xuất hiện trong cấu trúc lồng nhau.

---

## Phương pháp: Sử dụng Stack (Ngăn xếp)

Cách tiếp cận hiệu quả nhất và "lười" (lazy loading) là sử dụng một **Stack** để lưu trữ các phần tử. Thay vì làm phẳng toàn bộ danh sách ngay từ đầu (tốn bộ nhớ), chúng ta chỉ giải nén các danh sách lồng nhau khi cần thiết.

### 1. Ý tưởng chính
- Sử dụng một Stack để chứa các đối tượng `NestedInteger`.
- Trong hàm khởi tạo, đẩy tất cả các phần tử của danh sách ban đầu vào Stack theo **thứ tự ngược lại** (phần tử cuối cùng vào trước, phần tử đầu tiên vào sau). Điều này giúp phần tử đầu tiên luôn nằm ở đỉnh Stack.
- Trong hàm `hasNext()`, chúng ta kiểm tra đỉnh Stack:
    - Nếu đỉnh Stack là một số nguyên, trả về `true`.
    - Nếu đỉnh Stack là một danh sách, ta lấy danh sách đó ra (pop), và đẩy các phần tử của nó vào Stack theo thứ tự ngược lại.
    - Lặp lại cho đến khi tìm thấy một số nguyên hoặc Stack trống.

### 2. Tại sao lại xử lý trong `hasNext()` thay vì `next()`?
Trong thiết kế Iterator, `hasNext()` có nhiệm vụ đảm bảo rằng có phần tử tiếp theo để lấy. Bằng cách thực hiện việc "giải nén" danh sách lồng nhau trong `hasNext()`, chúng ta đảm bảo rằng sau khi `hasNext()` trả về `true`, phần tử ở đỉnh Stack **chắc chắn là một số nguyên**, giúp hàm `next()` thực hiện rất đơn giản.

---

## Chi tiết triển khai (Java)

```java
public class NestedIterator implements Iterator<Integer> {
    private Deque<NestedInteger> stack = new ArrayDeque<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        pushListToStack(nestedList);
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            pushListToStack(stack.pop().getList());
        }
        return !stack.isEmpty();
    }

    private void pushListToStack(List<NestedInteger> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            stack.push(list.get(i));
        }
    }
}
```

---

## Độ phức tạp thuật toán
- **Thời gian (Time Complexity)**:
    - `next()`: `O(1)`.
    - `hasNext()`: Trung bình (Amortized) `O(1)`. Mặc dù có vòng lặp `while`, nhưng mỗi phần tử chỉ được đẩy vào và lấy ra khỏi stack tối đa một vài lần (tùy độ sâu lồng nhau). Tổng thời gian cho `n` phần tử là `O(n)`.
- **Không gian (Space Complexity)**: `O(D)` với `D` là độ sâu tối đa của cấu trúc lồng nhau hoặc `O(N)` trong trường hợp xấu nhất nếu ta coi tất cả phần tử được lưu trong Stack. Tuy nhiên, nó tối ưu hơn việc làm phẳng hoàn toàn vì nó chỉ giữ các phần tử cần thiết trên đường đi hiện tại.

## Ưu điểm của cách tiếp cận này
1. **Lazy Evaluation**: Không cần xử lý toàn bộ dữ liệu ngay từ đầu. Nếu người dùng chỉ muốn lấy 2-3 phần tử đầu tiên, chúng ta không tốn công làm phẳng hàng triệu phần tử phía sau.
2. **Tiết kiệm bộ nhớ**: Chỉ lưu trữ các phần tử cần thiết trong Stack thay vì tạo một danh sách mới chứa toàn bộ các số nguyên đã được làm phẳng.

