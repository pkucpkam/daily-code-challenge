# 373. Find K Pairs with Smallest Sums

**Độ khó:** Medium

## Mô Tả Bài Toán

Cho hai mảng số nguyên `nums1` và `nums2` đã được sắp xếp theo thứ tự không giảm, và một số nguyên `k`.

Định nghĩa một cặp số `(u, v)` bao gồm một phần tử từ mảng thứ nhất và một phần tử từ mảng thứ hai.

Hãy tìm và trả về `k` cặp số `(u1, v1), (u2, v2), ..., (uk, vk)` có tổng giá trị nhỏ nhất.

---

## Ý Tưởng Giải

Do cả hai mảng `nums1` và `nums2` đã được sắp xếp tăng dần, cặp có tổng nhỏ nhất luôn luôn là `(nums1[0], nums2[0])`.

Để tìm `k` cặp có tổng nhỏ nhất một cách hiệu quả mà không cần duyệt qua toàn bộ các cặp có thể có (độ phức tạp sẽ là $O(N \times M)$), chúng ta có thể sử dụng cấu trúc dữ liệu **Min-Heap (Priority Queue)**.

### Chi Tiết Thuật Toán (Sử Dụng Min-Heap)

1. **Định dạng dữ liệu trong Heap:**
   Mỗi phần tử trong Min-Heap sẽ lưu trữ một mảng gồm 3 thông tin: `[tổng_của_cặp, chỉ_số_i_trong_nums1, chỉ_số_j_trong_nums2]`. 
   Heap sẽ tự động sắp xếp tăng dần dựa trên `tổng_của_cặp`.

2. **Khởi tạo Heap:**
   Ta ghép `nums2[0]` với từng phần tử của `nums1` để tạo thành các cặp ban đầu. 
   Vì ta chỉ cần tìm `k` cặp nhỏ nhất, nên ta chỉ cần đẩy tối đa `min(nums1.length, k)` phần tử vào heap:
   Các phần tử ban đầu: `(nums1[0], nums2[0])`, `(nums1[1], nums2[0])`, ..., `(nums1[min(N, k)-1], nums2[0])`.

3. **Tìm k cặp nhỏ nhất:**
   Lặp `k` lần (hoặc cho đến khi heap rỗng):
   * Lấy cặp có tổng nhỏ nhất ra khỏi heap: giả sử đó là cặp ứng với chỉ số `i` (trong `nums1`) và `j` (trong `nums2`).
   * Thêm cặp `[nums1[i], nums2[j]]` vào danh sách kết quả.
   * Nếu phần tử tiếp theo trong mảng `nums2` tồn tại (tức là `j + 1 < nums2.length`), ta đẩy cặp mới `(nums1[i], nums2[j + 1])` vào heap để tiếp tục so sánh.

### Tại sao cách tiếp cận này lại đúng và tối ưu?
* Với mỗi dòng `i`, các cặp tiếp theo có tổng tăng dần là `(nums1[i], nums2[0])`, `(nums1[i], nums2[1])`, `(nums1[i], nums2[2])`,...
* Do đó, sau khi ta lấy ra cặp `(nums1[i], nums2[j])`, ứng cử viên nhỏ nhất tiếp theo của dòng `i` chắc chắn là `(nums1[i], nums2[j + 1])`. Việc đẩy nó vào heap giúp ta so sánh nó với các ứng cử viên nhỏ nhất từ các dòng khác một cách chính xác nhất.

---

## Độ Phức Tạp

* **Thời gian:** $O(k \log(\min(N, k)))$ với $N$ là chiều dài của mảng `nums1`.
  * Khởi tạo heap mất tối đa $O(\min(N, k) \log(\min(N, k)))$.
  * Mỗi vòng lặp lấy ra phần tử nhỏ nhất và thêm phần tử mới mất $O(\log(\min(N, k)))$. Ta lặp tối đa $k$ lần.
  * Vì $k \le 10^4$, thuật toán chạy cực kỳ nhanh và mượt mà.
* **Không gian:** $O(\min(N, k))$ để lưu trữ các trạng thái trong Min-Heap.

---

## Code (Java)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) {
            return result;
        }

        // Min-heap lưu trữ mảng: [tổng, chỉ số trong nums1, chỉ số trong nums2]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        // Khởi tạo heap với min(nums1.length, k) phần tử kết hợp với nums2[0]
        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            minHeap.offer(new int[]{nums1[i] + nums2[0], i, 0});
        }

        // Lấy k cặp có tổng nhỏ nhất
        while (k-- > 0 && !minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int i = curr[1];
            int j = curr[2];

            List<Integer> pair = new ArrayList<>();
            pair.add(nums1[i]);
            pair.add(nums2[j]);
            result.add(pair);

            // Nếu mảng nums2 còn phần tử tiếp theo ở dòng i này, thêm cặp đó vào heap
            if (j + 1 < nums2.length) {
                minHeap.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }

        return result;
    }
}
```

