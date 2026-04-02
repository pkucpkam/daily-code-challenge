# 216. Combination Sum III - Best Solution

## Ý tưởng cốt lõi

Dùng **quay lui (backtracking)** để chọn dần các số từ `1` đến `9`.

Mỗi bước:

- Chọn một số `num` từ `start` đến `9`.
- Giảm số lượng phần tử còn cần chọn (`remainCount`).
- Giảm tổng còn thiếu (`remainSum`).
- Gọi đệ quy với `start = num + 1` để đảm bảo mỗi số chỉ dùng một lần và tổ hợp luôn tăng dần (không trùng thứ tự).

Khi `remainCount == 0`:

- Nếu `remainSum == 0` thì đây là một đáp án hợp lệ.
- Ngược lại thì loại.

## Cắt nhánh tối ưu

Để giảm số nhánh phải duyệt, ta dùng 3 điều kiện cắt sớm:

1. Không đủ số để chọn:
   - Số lượng số còn lại trong đoạn `[start..9]` là `10 - start`.
   - Nếu nhỏ hơn `remainCount` thì dừng.

2. Tổng nhỏ nhất vẫn quá lớn:
   - Tổng nhỏ nhất khi chọn `remainCount` số bắt đầu từ `start` là:
   - `start + (start+1) + ... + (start+remainCount-1)`.
   - Nếu tổng này `> remainSum` thì dừng.

3. Tổng lớn nhất vẫn quá nhỏ:
   - Tổng lớn nhất khi chọn `remainCount` số là:
   - `(10-remainCount) + ... + 9`.
   - Nếu tổng này `< remainSum` thì dừng.

Nhờ vậy, lời giải chạy rất nhanh trên bộ test.

## Độ phức tạp

- Tối đa có thể xem như duyệt các tổ hợp của 9 số: `O(C(9, k))`.
- Với ràng buộc nhỏ (`1..9`), đây là phương án hiệu quả nhất và dễ kiểm chứng.
- Bộ nhớ phụ: `O(k)` cho ngăn xếp đệ quy và mảng tạm.

## Java code

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, k, n, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int remainCount, int remainSum,
                           List<Integer> path, List<List<Integer>> result) {
        if (remainCount == 0) {
            if (remainSum == 0) {
                result.add(new ArrayList<>(path));
            }
            return;
        }

        int available = 10 - start;
        if (available < remainCount) {
            return;
        }

        int minPossible = arithmeticSum(start, start + remainCount - 1);
        if (minPossible > remainSum) {
            return;
        }

        int maxPossible = arithmeticSum(10 - remainCount, 9);
        if (maxPossible < remainSum) {
            return;
        }

        for (int num = start; num <= 9; num++) {
            if (num > remainSum) {
                break;
            }

            path.add(num);
            backtrack(num + 1, remainCount - 1, remainSum - num, path, result);
            path.remove(path.size() - 1);
        }
    }

    private int arithmeticSum(int left, int right) {
        return (left + right) * (right - left + 1) / 2;
    }
}
```
