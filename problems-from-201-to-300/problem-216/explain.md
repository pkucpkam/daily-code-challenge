# Word Break II

## Yêu cầu bài toán

- Cho một chuỗi `s` và một danh sách từ vựng `wordDict`.
- Chèn các khoảng trắng vào chuỗi `s` để tạo thành một câu sao cho mỗi từ trong câu đều thuộc `wordDict`.
- Nhiệm vụ: Trả về **tất cả** các câu có thể tạo thành theo cách này.

## Ý tưởng cốt lõi

Bài toán yêu cầu liệt kê tất cả các kết quả khả thi, vì vậy chúng ta sử dụng phương pháp **Quay lui (Backtracking)** kết hợp với **Đệ quy**.
Tuy nhiên, nếu chỉ dùng đệ quy thuần túy, chúng ta sẽ phải tính toán lại nhiều lần cho cùng một chuỗi con, dẫn đến độ phức tạp cực lớn. Để tối ưu, chúng ta sử dụng **Lưu trữ kết quả trung gian (Memoization)**.

Ý tưởng: Với một chuỗi bắt đầu từ vị trí `index`, chúng ta thử cắt các đoạn con có độ dài khác nhau. Nếu đoạn con đó nằm trong từ điển, chúng ta tiếp tục đệ quy tìm các cách phân tách cho phần còn lại của chuỗi.

## Thuật toán

1. Sử dụng một `HashSet` để lưu trữ từ điển giúp tra cứu nhanh.
2. Sử dụng một `HashMap<Integer, List<String>> memo` để lưu lại kết quả đã tìm được cho các chỉ số (index) trong chuỗi `s`.
3. Hàm đệ quy `backtrack(start)`:
   - Nếu `start` đã có trong `memo`, trả về kết quả đó.
   - Nếu `start` bằng độ dài chuỗi, trả về danh sách chứa chuỗi rỗng (điểm dừng).
   - Duyệt `end` từ `start + 1` đến hết chuỗi:
     - Lấy từ `word = s.substring(start, end)`.
     - Nếu `word` nằm trong từ điển:
       - Đệ quy lấy danh sách các câu từ vị trí `end`.
       - Với mỗi câu `sub`, nối `word` vào trước và thêm vào kết quả hiện tại.
   - Lưu kết quả vào `memo` và trả về.

## Độ phức tạp
- **Thời gian**: Trong trường hợp tệ nhất có thể lên tới $O(2^N)$ câu trả lời. Nhờ Memoization, chúng ta tránh được việc tính toán lặp lại, nhưng vẫn phải duyệt qua các cách kết hợp từ.
- **Không gian**: $O(N \cdot 2^N)$ để lưu trữ các câu trong bộ nhớ đệm `memo`.

## Code (Java)

```java
import java.util.*;

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>(); // Lưu trữ kết quả trung gian
        return backtrack(s, 0, dict, memo);
    }
    
    private List<String> backtrack(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                List<String> sublist = backtrack(s, end, dict, memo);
                for (String sub : sublist) {
                    // Ghép từ hiện tại với phần câu phía sau
                    result.add(word + (sub.isEmpty() ? "" : " " + sub));
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
}
```
