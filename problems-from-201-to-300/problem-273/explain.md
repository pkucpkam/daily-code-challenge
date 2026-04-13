# 241. Different Ways to Add Parentheses - Best Solution

## Mô tả bài toán

Cho chuỗi biểu thức gồm số và các toán tử `+`, `-`, `*`.

Yêu cầu: trả về tất cả kết quả có thể nhận được khi đặt ngoặc theo mọi cách hợp lệ.

## Ý tưởng tối ưu: Chia để trị + Ghi nhớ (DFS + Memoization)

Nếu tại vị trí `i` là toán tử, ta tách biểu thức thành:

- Vế trái: `expr[0..i-1]`
- Vế phải: `expr[i+1..end]`

Sau đó:

- Tính mọi kết quả có thể của vế trái.
- Tính mọi kết quả có thể của vế phải.
- Ghép cặp từng kết quả trái/phải bằng toán tử ở `i`.

Làm việc này cho mọi toán tử trong chuỗi.

### Base case

Nếu một đoạn không chứa toán tử nào, nó chỉ là một số duy nhất, trả về danh sách gồm 1 phần tử đó.

### Vì sao cần memoization

Cùng một biểu thức con (ví dụ `"3-4*5"`) có thể xuất hiện nhiều lần khi tách ở các vị trí khác nhau.
Nếu không cache, ta sẽ tính lặp lại rất nhiều.

Dùng `Map<String, List<Integer>> memo` để lưu kết quả của từng biểu thức con, giúp giảm thời gian đáng kể.

## Tính đúng

- Mọi cách đặt ngoặc hợp lệ đều tương ứng với việc chọn một toán tử làm "gốc" để tách trái/phải, rồi đệ quy tiếp trên hai phía.
- Thuật toán duyệt hết tất cả vị trí toán tử, nên không bỏ sót cách đặt ngoặc nào.
- Với mỗi phép tách, thuật toán ghép mọi kết quả trái với mọi kết quả phải, nên bao phủ đầy đủ mọi giá trị có thể tạo ra.

Do đó danh sách trả về là đầy đủ và đúng.

## Độ phức tạp

Gọi `n` là số toán tử.

- Số cách đặt ngoặc tăng theo Catalan, nên số kết quả có thể là cấp số mũ theo `n`.
- Memoization giúp mỗi biểu thức con chỉ tính một lần.

Thực tế thường biểu diễn là:

- Thời gian: xấp xỉ `O(number_of_subproblems + total_combinations)`
- Bộ nhớ: `O(number_of_subproblems + total_results_stored)`

## Java Code (Tối ưu cho LeetCode)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    private final Map<String, List<Integer>> memo = new HashMap<>();

    public List<Integer> diffWaysToCompute(String expression) {
        return compute(expression);
    }

    private List<Integer> compute(String expr) {
        if (memo.containsKey(expr)) {
            return memo.get(expr);
        }

        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> leftValues = compute(expr.substring(0, i));
                List<Integer> rightValues = compute(expr.substring(i + 1));

                for (int left : leftValues) {
                    for (int right : rightValues) {
                        if (ch == '+') {
                            results.add(left + right);
                        } else if (ch == '-') {
                            results.add(left - right);
                        } else {
                            results.add(left * right);
                        }
                    }
                }
            }
        }

        if (results.isEmpty()) {
            results.add(Integer.parseInt(expr));
        }

        memo.put(expr, results);
        return results;
    }
}
```
