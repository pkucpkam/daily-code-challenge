# Nim Game

## 1. Mô tả bài toán
Bạn và người bạn của mình chơi trò chơi bốc sỏi. Ban đầu có một đống sỏi.
Luật chơi:
- Hai người luân phiên bốc sỏi, bạn là người đi trước.
- Mỗi lượt bốc từ 1 đến 3 viên sỏi.
- Người bốc được viên sỏi cuối cùng là người chiến thắng.
Cho số lượng sỏi `n`, hãy trả về `true` nếu bạn có thể thắng (giả định cả hai đều chơi tối ưu).

## 2. Ý tưởng cốt lõi
- Đây là một bài toán lý thuyết trò chơi kinh điển. 
- Hãy xét các trường hợp nhỏ:
    - `n = 1, 2, 3`: Bạn bốc hết và thắng ngay lập tức.
    - `n = 4`: Bạn bốc 1 -> còn 3 (bạn bốc thắng), bốc 2 -> còn 2 (bạn bốc thắng), bốc 3 -> còn 1 (bạn bốc thắng). Trong mọi trường hợp, sau lượt bốc của bạn, còn lại từ 1 đến 3 viên sỏi, và đối thủ sẽ bốc hết. Vậy bạn **luôn thua** khi `n = 4`.
    - `n = 5, 6, 7`: Bạn có thể bốc sao cho số sỏi còn lại là 4. Vì đối thủ phải đối mặt với đống sỏi 4 viên, họ sẽ thua (như phân tích trên), và bạn thắng.
    - `n = 8`: Dù bạn bốc bao nhiêu (1, 2, hoặc 3), đối thủ cũng sẽ điều chỉnh để đưa đống sỏi về 4 viên cho bạn. Bạn sẽ thua.
- Quy luật rút ra: Bạn sẽ luôn thua nếu số sỏi là bội số của 4 (\(n \% 4 == 0\)).

## 3. Giải thích thuật toán
- Theo phân tích ở trên, điều kiện để bạn dành chiến thắng là số lượng sỏi ban đầu không phải là bội số của 4.
- Chỉ cần một dòng kiểm tra duy nhất: `return n % 4 != 0`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Chỉ một phép toán chia lấy dư.
- **Không gian (Space Complexity)**: \(O(1)\) - Không tốn thêm bộ nhớ.

## 5. Code (Java)
```java
class Solution {
    public boolean canWinNim(int n) {
        // Bạn sẽ thua nếu đối mặt với đống sỏi là bội số của 4
        // Vì đối thủ luôn có thể bốc bù lại để giữ khoảng cách 4 viên
        return n % 4 != 0;
    }
}
```
*(Ghi chú: Bài toán này dạy cho chúng ta về việc tìm kiếm điểm trạng thái thua trong trò chơi đối kháng).*
