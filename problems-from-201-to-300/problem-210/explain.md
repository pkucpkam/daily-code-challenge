# Clone Graph

## Yêu cầu bài toán

- Cho một tham chiếu (reference) đến một node `node` của một đồ thị vô hướng liên thông.
- Mỗi node trong đồ thị nhận một thuộc tính giá trị (`val`) và chứa một danh sách các node kề với nó (`neighbors`).
- Nhiệm vụ của bạn là trả về một **Bản sao sâu (Deep copy) / Clone** của toàn bộ đồ thị đó. Nghĩa là bạn phải tự khởi tạo lại các nốt mới (`new Node`) có cấu trúc y hệt bản gốc, không được dùng chung không gian bộ nhớ (reference) với bất kỳ nốt gốc nào.

## Ý tưởng cốt lõi

Vấn đề khó nhất khi copy một đồ thị đang móc nối chằng chịt vào nhau đó là **chu trình lặp (Cycle)**. Đồ thị không giống dạng hình Cây (Tree) đổ tuột từ trên xuống. Tại đồ thị có thể xảy ra trường hợp Nốt A móc với Nốt B, rồi Nốt B móc ngược lại Nốt A. Nếu bạn dùng đệ quy tạo mới một cách mù quáng, bạn sẽ kẹt trong một vòng lặp nhào nặn vô tận.

Để né việc vấp ngã này, chìa khóa là: **Phải cất giữ một "Danh sách từ điển" mapping giữa `Nốt Cũ` và `Nốt Mới tương ứng của nó`**.
- Bất cứ khi nào bạn định copy một nốt cũ, bạn lật danh bạ ra coi "Cái nốt này đã từng được nhân bản bao giờ chưa?". 
- Nếu rồi, đừng rảnh tay đi copy nó lần nữa, chỉ cần lôi cái phiên bản nhân bản gốc dán vào là xong quy trình.

Thuật toán duyệt tìm có thể là DFS (Đệ quy sâu) hoặc **BFS (Duyệt theo cấp bằng Queue)**. Cách phổ biến nhất (và giống trong file code) là dùng cấu trúc BFS.

## Thuật toán (Theo BFS)

1. Nếu `node` đầu vào bằng null, mạnh dạn trả về null.
2. Khởi tạo một cái bản đồ HashMap `map` để ghi chú quá trình Clone (Key: Node gốc -> Value: Node clone).
3. Tạo Queue `q` phục vụ BFS. Bắt đầu với đỉnh khởi nguồn `node`.
4. Ghi danh nốt khởi nguồn vào Map bằng cách tạo cho nó một cái thân xác mới tinh (`Node rootClone = new Node(node.val)`) và đem đặt Key-Value vào `map`. Sau đó bỏ `node` đó vô cái `q`.
5. Đẩy `while` loop chừng nào mảng Queue còn chứa nốt:
   - Rút nốt gốc `curr` trên cùng ra khỏi mảng queue.
   - Quét qua mọi mặt hàng có trong danh sách hàng xóm `curr.neighbors`.
   - Với mỗi cái nốt `nei` hàng xóm hiện lên:
     - Nếu lật `map` lên mà chưa thấy cái `nei` này xuất hiện (chưa được clone): 
       Ngay lập tức, nhào nặn ra cho nó một thân xác clone mới (`new Node`), nhét bản copy vào `map`, và **nhét luôn thằng hàng xóm cũ `nei` kia vào `queue`** (để lát sau mình còn rảnh mò đến hàng xóm của nó nữa!).
     - Bất chấp vừa mới làm ra hay là đồ cũ làm từ vòng trước trên Map, ta lôi cái nốt Clone hiện tại móc nó với nốt Clone hàng xóm bằng dòng gán List: `map.get(curr).neighbors.add(map.get(nei));`.

6. Trả về nốt khởi nguồn clone là xong xuôi!

## Vì sao đúng và an toàn?

Kĩ thuật ghim Hash Map bảo đảm mỗi thân xác gốc chỉ bị tạo ra thân xác Clone **một và duy nhất một lần**. Bất cứ cái râu móc chằng chịt, vòng tròn tự gọi bản thân (Self-loops) nào cũng đều sẽ không kích hoạt quá trình "khởi tạo" nếu Map đã xác thực mã nốt gốc, qua đó chốt chặn hoàn toàn lỗi tràn bộ nhớ / Stack Overflow.

## Độ phức tạp
- Thời gian: `O(V + E)`. Cực kì gọn ghẽ, với $V$ là số nốt trong đồ thị và $E$ là số mối dây (cạnh) kết nối. Thuật toán này viếng thăm mỗi Nốt phụ một lần, chui xuống gán hàng xóm cho từng sợi cạnh chỉ một lần. 
- Không gian phụ: `O(V)`. Chiếm bộ nhớ do ta phải lưu đủ lượng đồ thị vào trong cái HashTable Map và mảng xếp hàng Queue.

## Code (Java)

```java
import java.util.*;

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Queue<Node> q = new LinkedList<>();
        
        Node rootClone = new Node(node.val);
        map.put(node, rootClone);
        q.add(node);
        
        while (!q.isEmpty()) {
            Node curr = q.poll();
            for (Node nei : curr.neighbors) {
                if (!map.containsKey(nei)) {
                    map.put(nei, new Node(nei.val));
                    q.add(nei);
                }
                map.get(curr).neighbors.add(map.get(nei));
            }
        }
        return rootClone;
    }
}
```
