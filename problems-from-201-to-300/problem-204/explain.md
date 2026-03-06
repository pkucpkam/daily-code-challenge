# Word Ladder

## Yêu cầu bài toán

- Cho hai từ `beginWord` và `endWord`, cùng một từ điển `wordList`.
- Một chuỗi biến đổi hợp lệ là chuỗi mà hai từ cạnh nhau chỉ khác nhau 1 chữ cái. Mọi từ mới xuất hiện đều phải nằm trong `wordList`.
- Khác với phiên bản II, bài toán này chỉ yêu cầu bạn trả về **số lượng từ** trong chuỗi biến đổi ngắn nhất (tức là bước chuyển + 1 độ dài cho từ bắt đầu). Nếu không có ngoại lệ hợp trình nào tồn tại trả về 0.

## Ý tưởng cốt lõi

Bài toán yêu cầu đếm số đỉnh trên đường đi ngắn nhất của đồ thị không trọng số, nên ta sử dụng Thuật toán BFS (Tìm kiếm theo chiều rộng). 
Đặc biệt, do tính chất ta đã biết trước điểm đầu `beginWord` và điểm cuối `endWord`, áp dụng biện pháp **Bidirectional BFS (Duyệt chiều rộng hai chiều)** (từ đầu tiến ra, từ cuối xuất kích về trước) sẽ mang lại tốc độ chạy thực tế đột phá, giúp giảm đáng kể số trạng thái trung gian cần xét.

## Thuật toán

1. Ta gom những từ xuất phát vào `beginSet` (bắt đầu là `beginWord`) và những từ ở đích vào `endSet` (bắt đầu là `endWord`). Đồng thời dùng một `visited` để tránh đi lại từ trùng vòng tròn lẩn quẩn.
2. Tại mỗi bước duyệt, ta chủ động ưu tiên xử lý `set` nào có kích thước nhỏ hơn. Nếu `beginSet > endSet`, ta tiến hành tráo 2 Set này cho nhau => Giúp hạn chế không gian tìm kiếm nở rộng trong BFS.
3. Sinh tất cả các trạng thái biến đổi có thể hợp lệ từ `beginSet` (bằng kĩ thuật thay thế 1 kí tự trên từ).
   - Nếu từ vừa tạo ra đã tồn tại trong `endSet`, nghĩa là "hai luồng BFS đã va chạm vào nhau". Đường đi liên hoàn đã hoàn tất, ta lập tức trả về `len + 1`.
   - Nếu nó nằm trong từ điển và chưa thăm qua, ta thêm nó vào `nextLevel` (tương đương cấp tiếp theo) và tiếp tục cho hết vòng lặp. Cập nhật `beginSet = nextLevel`, tăng chi phí `len` đếm.
4. Nếu hai set cạn kiệt (mở rộng mà không đem lại kết quả đụng chạm), ta trả về 0.

## Vì sao đúng?

- BFS nguyên gốc cho khoảng cách ngắn nhất, BFS hai chiều cũng giống vậy nhưng việc chia 2 luồng cắt giảm theo luỹ thừa những nhánh sai, giúp tiết kiệm bộ vi xử lý theo thời gian cực lớn (không gian trạng thái thu hẹp từ $O(K^D)$ xuống $O(K^{D/2})$).
- Cách kiểm tra thông minh tráo Set `beginSet` <-> `endSet` cho phép vòng lặp code chỉ cần mở rộng 1 đầu mà trên thực tế luôn nhắm vào đầu có diện tích phủ nhỏ nhẹ hơn để xử lý nhánh cây.

## Độ phức tạp

- Thời gian: Xấu nhất $O(N \times L^2)$ (cho $N$ từ, mất vòng $L$ cho việc thay các chữ cái, kèm theo kiểm tra chuỗi String). Bidirectional BFS có hằng số thời gian nhỏ hơn đáng kể lần so với thuật BFS truyền thống.
- Không gian: $O(N)$ lưu trữ các bộ nhớ `Set` liên quan đến `visited` hay `Dictionary`.

## Code (Java)

```java
import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord))
            return 0;

        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visited = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);
        visited.add(beginWord);
        int len = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
            }

            Set<String> nextLevel = new HashSet<>();
            for (String word : beginSet) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char old = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) continue;
                        chars[i] = c;
                        String nw = new String(chars);
                        if (endSet.contains(nw))
                            return len + 1;
                        if (dict.contains(nw) && !visited.contains(nw)) {
                            nextLevel.add(nw);
                            visited.add(nw);
                        }
                    }
                    chars[i] = old;
                }
            }
            beginSet = nextLevel;
            len++;
        }

        return 0;
    }
}
```
