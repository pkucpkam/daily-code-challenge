# 310. Minimum Height Trees - Lời giải tối ưu

## Tóm tắt bài toán

Cho một cây vô hướng có `n` đỉnh. Cần tìm tất cả các đỉnh nếu chọn làm gốc thì cây có chiều cao nhỏ nhất.

## Ý tưởng tối ưu: Cắt lá theo từng lớp

Một cây có thể có 1 hoặc 2 đỉnh trung tâm. Nếu ta liên tục loại bỏ các lá của cây, các đỉnh còn lại sẽ dần tiến về trung tâm. Khi số đỉnh còn lại nhỏ hơn hoặc bằng 2, đó chính là đáp án.

Các bước:

1. Xây dựng danh sách kề và mảng bậc của từng đỉnh.
2. Đưa tất cả các lá ban đầu vào hàng đợi.
3. Mỗi vòng, loại bỏ toàn bộ các lá hiện tại.
4. Giảm bậc của các đỉnh kề; đỉnh nào trở thành lá mới thì đưa vào hàng đợi.
5. Khi còn lại không quá 2 đỉnh, các đỉnh trong hàng đợi là các gốc của minimum height trees.

## Vì sao đúng

- Lá luôn là các đỉnh xa trung tâm nhất, nên chúng không thể là gốc tối ưu khi còn các đỉnh khác phía trong.
- Sau khi bỏ đi một lớp lá, bài toán còn lại là cùng một bài toán trên cây nhỏ hơn.
- Quá trình này giống như co cây từ ngoài vào trong cho đến khi chỉ còn 1 hoặc 2 đỉnh trung tâm.
- Cây chỉ có 1 trung tâm khi đường kính chẵn, và 2 trung tâm khi đường kính lẻ; đây chính là các gốc tạo ra chiều cao nhỏ nhất.

## Độ phức tạp

- Thời gian: `O(n)` vì mỗi cạnh và mỗi đỉnh chỉ được xử lý hữu hạn lần.
- Bộ nhớ phụ: `O(n)` cho danh sách kề, bậc đỉnh, và hàng đợi.

## Java implementation

```java
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<>();

        if (n == 1) {
            result.add(0);
            return result;
        }

        List<List<Integer>> graph = new ArrayList<>(n);
        int[] degree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph.get(u).add(v);
            graph.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        int remainingNodes = n;
        while (remainingNodes > 2) {
            int size = queue.size();
            remainingNodes -= size;

            for (int i = 0; i < size; i++) {
                int leaf = queue.poll();

                for (int neighbor : graph.get(leaf)) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 1) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        result.addAll(queue);
        return result;
    }
}
```
