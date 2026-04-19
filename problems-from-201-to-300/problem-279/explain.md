# 279. Peeking Iterator - Best Solution

## Tóm tắt bài toán

Ta cần cài đặt một iterator có thêm thao tác `peek()`, tức là nhìn trước phần tử kế tiếp mà không làm dịch con trỏ.

## Ý tưởng chính

Giải pháp tối ưu và đơn giản nhất là giữ sẵn phần tử kế tiếp trong một biến đệm:

- Khi khởi tạo, đọc trước phần tử đầu tiên từ iterator gốc.
- `peek()` chỉ trả về giá trị đang được đệm.
- `next()` trả về giá trị đệm hiện tại, rồi đọc tiếp một phần tử mới từ iterator gốc để nạp lại bộ đệm.
- `hasNext()` chỉ kiểm tra xem còn giá trị đệm hay không.

Cách này đảm bảo mỗi phần tử chỉ bị đọc đúng một lần từ iterator gốc.

## Vì sao đúng

- Tại mọi thời điểm, biến đệm luôn chứa phần tử chưa được trả về tiếp theo.
- `peek()` không thay đổi trạng thái nên luôn trả về đúng phần tử kế tiếp.
- `next()` trả về phần tử kế tiếp hiện tại rồi cập nhật bộ đệm sang phần tử sau đó, đúng với hành vi của iterator.
- `hasNext()` phản ánh chính xác việc còn hay không còn phần tử trong bộ đệm.

## Độ phức tạp

- Time: `O(1)` cho mỗi lời gọi `peek`, `next`, `hasNext`
- Space: `O(1)`

## Java Code

```java
import java.util.Iterator;

class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private Integer nextElement;
    private boolean hasNext;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.hasNext = iterator.hasNext();
        this.nextElement = this.hasNext ? iterator.next() : null;
    }

    public Integer peek() {
        return nextElement;
    }

    @Override
    public Integer next() {
        Integer current = nextElement;
        if (iterator.hasNext()) {
            nextElement = iterator.next();
            hasNext = true;
        } else {
            nextElement = null;
            hasNext = false;
        }
        return current;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }
}
```

## Mở rộng generic

Để hỗ trợ mọi kiểu dữ liệu, chỉ cần đổi `Integer` thành một kiểu generic `T`, ví dụ `class PeekingIterator<T> implements Iterator<T>`. Khi đó logic giữ bộ đệm vẫn giữ nguyên.
