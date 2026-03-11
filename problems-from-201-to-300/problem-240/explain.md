# Largest Number

## Yêu cầu bài toán

Cho một mảng số nguyên không âm `nums`, sắp xếp lại các phần tử sao cho khi ghép chúng lại tạo thành số lớn nhất có thể. Trả về kết quả dưới dạng chuỗi.

## Ý tưởng cốt lõi

Không thể so sánh theo giá trị số học thông thường. Thay vào đó, ta so sánh **theo thứ tự ghép chuỗi**:

- Với hai số `a` và `b` (dạng chuỗi), ta so sánh `a + b` với `b + a`.
- Nếu `b + a > a + b` thì `b` nên đứng trước `a`.

Ví dụ: `a = "3"`, `b = "30"`
- `a + b = "330"`, `b + a = "303"`
- `"330" > "303"` → `3` đứng trước `30` → ✅

Sau khi sắp xếp, nối tất cả chuỗi lại là kết quả.

**Trường hợp đặc biệt**: Nếu phần tử đầu tiên sau sắp xếp là `"0"` thì toàn bộ mảng đều là `0` → trả về `"0"`.

## Các bước thực hiện

1. Chuyển mọi số trong `nums` thành chuỗi.
2. Sắp xếp mảng chuỗi với comparator: `(a, b) -> (b + a).compareTo(a + b)`.
3. Nếu phần tử đầu là `"0"`, trả về `"0"`.
4. Nối toàn bộ chuỗi lại và trả về.

## Code

```java
import java.util.Arrays;

class Solution {
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));

        if (strs[0].equals("0")) return "0";

        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }
}
```

## Ví dụ minh hoạ

### Ví dụ 1

```
nums = [10, 2]
Chuỗi: ["10", "2"]
So sánh: "210" > "102" → "2" đứng trước "10"
Kết quả: "210"
```

### Ví dụ 2

```
nums = [3, 30, 34, 5, 9]
Chuỗi sau sắp xếp: ["9", "5", "34", "3", "30"]
Kết quả: "9534330"
```

## Độ phức tạp

- **Thời gian**: $O(n \cdot k \cdot \log n)$ — $n$ là số phần tử, $k$ là độ dài chuỗi trung bình (do mỗi lần so sánh tốn $O(k)$).
- **Không gian**: $O(n)$ — mảng chuỗi trung gian.

- Giả sử `s.score = x`.
- Mọi giá trị điểm phân biệt thuộc tập `{score | score >= x}` đều đứng trước hoặc bằng `x` trong thứ tự giảm dần.
- Nếu có `k` giá trị phân biệt như vậy, thì `x` đứng ở vị trí thứ `k` theo quy tắc `dense rank`.
- Các dòng cùng điểm `x` tạo cùng tập đếm, nên cùng hạng.

## Độ phức tạp

- Thời gian: thường vào khoảng `O(n^2)` với truy vấn tương quan thuần túy.
- Không gian: phụ thuộc bộ tối ưu của hệ quản trị CSDL.

## Ghi chú

- Nếu hệ quản trị hỗ trợ hàm cửa sổ, có thể viết ngắn hơn bằng `DENSE_RANK()`.
