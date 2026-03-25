# 207. Course Schedule - Best Solution

## Ý tưởng tối ưu

Mô hình bài toán thành **đồ thị có hướng**:

- Mỗi môn học là một đỉnh.
- Cặp `[a, b]` nghĩa là có cạnh `b -> a` (học `b` trước rồi mới học `a`).

Muốn học hết tất cả môn thì đồ thị phải **không có chu trình**.

Giải pháp tối ưu và phổ biến là **Kahn's Algorithm** (Topological Sort bằng BFS):

1. Tính `inDegree[x]` = số môn tiên quyết của môn `x`.
2. Đưa tất cả môn có `inDegree = 0` vào queue (có thể học ngay).
3. Lần lượt lấy môn ra học, giảm `inDegree` của các môn phụ thuộc vào nó.
4. Nếu môn nào giảm về `0`, đưa vào queue.
5. Đếm số môn đã học được.

Nếu đếm được đúng `numCourses` thì học hết được, ngược lại là có chu trình nên không thể.

## Vì sao đây là best solution

- Chạy tuyến tính theo số đỉnh và số cạnh.
- Phù hợp tốt với ràng buộc `numCourses <= 2000`, `prerequisites.length <= 5000`.
- Trực tiếp phát hiện chu trình thông qua số lượng đỉnh xử lý được.

## Code (Java)

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        java.util.List<Integer>[] graph = new java.util.ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new java.util.ArrayList<>();
        }

        int[] inDegree = new int[numCourses];

        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
            inDegree[course]++;
        }

        java.util.ArrayDeque<Integer> queue = new java.util.ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int finished = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            finished++;

            for (int next : graph[current]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return finished == numCourses;
    }
}
```

## Độ phức tạp

- Time: `O(numCourses + prerequisites.length)`
- Space: `O(numCourses + prerequisites.length)`

## Ví dụ ngắn

Với `numCourses = 2`, `prerequisites = [[1,0],[0,1]]`:

- `inDegree[0] = 1`, `inDegree[1] = 1`
- Không có môn nào `inDegree = 0` để bắt đầu.
- Queue rỗng ngay từ đầu, `finished = 0`.

Kết luận: không thể học hết, trả về `false`.



