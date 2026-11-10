# Giải thích bài 677 - Map Sum Pairs

## Ý tưởng

Ta lưu hai bảng băm:

- `values`: lưu giá trị hiện tại của từng khóa.
- `prefixSums`: lưu tổng giá trị của tất cả khóa bắt đầu bằng mỗi tiền tố.

Khi chèn khóa `key` với giá trị `val`, chỉ các tiền tố của `key` bị ảnh hưởng. Vì vậy, ta cập nhật lần lượt:

```text
key = "apple"
Các tiền tố: "a", "ap", "app", "appl", "apple"
```

Nếu khóa đã tồn tại, giá trị cũ phải được thay thế. Gọi:

```text
delta = giá trị mới - giá trị cũ
```

Sau đó cộng `delta` vào tổng của mọi tiền tố. Cách này tránh phải duyệt lại toàn bộ các khóa đã lưu.

## Chứng minh tính đúng đắn

### Bổ đề 1

Sau mỗi lần gọi `insert`, `prefixSums[prefix]` bằng tổng giá trị của mọi khóa bắt đầu bằng `prefix`.

**Chứng minh:** Khi thêm hoặc cập nhật `key`, chỉ các tiền tố của `key` có thể thay đổi tổng. Mỗi tiền tố đó được cộng thêm:

```text
giá trị mới - giá trị cũ
```

Do đó tổng của từng tiền tố được cập nhật chính xác. Các tiền tố khác không bị ảnh hưởng. ∎

### Bổ đề 2

`sum(prefix)` trả về đúng tổng các giá trị của những khóa bắt đầu bằng `prefix`.

**Chứng minh:** Theo Bổ đề 1, `prefixSums[prefix]` đã chứa chính xác tổng cần tìm. Nếu tiền tố chưa từng xuất hiện, không có khóa phù hợp nên kết quả là `0`. ∎

### Định lý

Thuật toán luôn trả về kết quả đúng.

**Chứng minh:** `insert` duy trì bất biến trong Bổ đề 1, còn `sum` sử dụng trực tiếp bất biến đó theo Bổ đề 2. Vì vậy mọi truy vấn đều cho kết quả chính xác. ∎

## Độ phức tạp

Gọi `L` là độ dài của khóa:

- `insert`: `O(L)` lần tạo tiền tố.
- `sum`: `O(1)` trung bình.
- Bộ nhớ: `O(T)`, trong đó `T` là tổng số ký tự của các tiền tố khác nhau được lưu.

Đây là cách tối ưu cho bài toán vì mỗi lần `insert` chỉ cần xử lý đúng các tiền tố của khóa.

## Mã nguồn

```java
import java.util.HashMap;
import java.util.Map;

class MapSum {
    private final Map<String, Integer> values = new HashMap<>();
    private final Map<String, Integer> prefixSums = new HashMap<>();

    public MapSum() {
    }

    public void insert(String key, int val) {
        int delta = val - values.getOrDefault(key, 0);
        values.put(key, val);

        for (int i = 1; i <= key.length(); i++) {
            String prefix = key.substring(0, i);
            prefixSums.put(prefix, prefixSums.getOrDefault(prefix, 0) + delta);
        }
    }

    public int sum(String prefix) {
        return prefixSums.getOrDefault(prefix, 0);
    }
}
```
