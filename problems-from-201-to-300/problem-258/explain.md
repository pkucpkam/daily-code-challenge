# 210. Course Schedule II - Giải thích lời giải tối ưu

## Ý tưởng tốt nhất: Topological Sort (Kahn's Algorithm)

Mỗi cặp `[a, b]` nghĩa là phải học `b` trước rồi mới học `a`.
Xem đây là đồ thị có hướng: `b -> a`.

Nếu tồn tại thứ tự học hợp lệ, đó chính là một **topological order** của đồ thị.
Nếu có chu trình, không thể học hết các môn.

## Cách làm

1. Tạo đồ thị `graph` từ danh sách `prerequisites`.
2. Tính `indegree[x]` = số môn tiên quyết còn thiếu của môn `x`.
3. Đưa tất cả môn có `indegree = 0` vào hàng đợi (có thể học ngay).
4. Lặp khi hàng đợi còn phần tử:
   - Lấy một môn `cur` ra, thêm vào kết quả.
   - Với mỗi môn `next` phụ thuộc vào `cur`, giảm `indegree[next]`.
   - Nếu `indegree[next]` về `0`, đưa `next` vào hàng đợi.
5. Nếu số môn trong kết quả bằng `numCourses` thì trả về kết quả.
6. Ngược lại, có chu trình nên trả về mảng rỗng.

## Vì sao đúng?

- Một môn chỉ được đưa vào kết quả khi mọi tiên quyết của nó đã xử lý xong (`indegree = 0`).
- Mỗi lần xử lý một môn, ta "mở khóa" các môn phụ thuộc nó bằng cách giảm `indegree`.
- Nếu còn môn không bao giờ về `indegree = 0`, các môn đó thuộc (hoặc bị kẹt bởi) chu trình.
- Do đó:
  - Xử lý đủ `numCourses` môn => có thứ tự hợp lệ.
  - Xử lý thiếu => không thể hoàn thành toàn bộ chương trình.

## Độ phức tạp

- Thời gian: `O(V + E)` với `V = numCourses`, `E = prerequisites.length`.
- Bộ nhớ phụ: `O(V + E)` cho đồ thị, indegree và queue.

## Java code

```java
class Solution {
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] indegree = new int[numCourses];
		int[][] graph = new int[numCourses][];
		int[] outCount = new int[numCourses];

		for (int[] edge : prerequisites) {
			outCount[edge[1]]++;
		}

		for (int i = 0; i < numCourses; i++) {
			graph[i] = new int[outCount[i]];
		}

		int[] nextIndex = new int[numCourses];
		for (int[] edge : prerequisites) {
			int course = edge[0];
			int pre = edge[1];
			graph[pre][nextIndex[pre]++] = course;
			indegree[course]++;
		}

		int[] queue = new int[numCourses];
		int head = 0;
		int tail = 0;

		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue[tail++] = i;
			}
		}

		int[] order = new int[numCourses];
		int idx = 0;

		while (head < tail) {
			int cur = queue[head++];
			order[idx++] = cur;

			for (int next : graph[cur]) {
				indegree[next]--;
				if (indegree[next] == 0) {
					queue[tail++] = next;
				}
			}
		}

		return idx == numCourses ? order : new int[0];
	}
}
```

## Ghi chú

- Cách viết trên dùng mảng thuần (`int[][]`, `int[]`) để giảm overhead bộ nhớ so với `ArrayList<List<Integer>>`.
- Nếu bạn ưu tiên code ngắn gọn, có thể dùng `ArrayList` + `ArrayDeque`; độ phức tạp vẫn giữ nguyên.
