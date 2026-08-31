# 561. Array Partition

## Intuition

Bài toán yêu cầu chia mảng `2n` phần tử thành `n` cặp sao cho tổng `min` của mỗi cặp là **lớn nhất**.

**Nhận xét chìa khóa:** Trong mỗi cặp `(a, b)`, ta chỉ lấy được `min(a, b)` — tức là phần tử nhỏ hơn. Phần tử lớn hơn bị "lãng phí". Để tối ưu, ta cần **giảm thiểu tổng lượng bị lãng phí**.

Cách tốt nhất: **Sắp xếp mảng**, rồi ghép cặp các phần tử **liền kề nhau**. Khi đó, mỗi cặp chênh lệch nhỏ nhất có thể, tối đa hóa `min`.

---

## Key Insights & Algorithmic Strategy

### 1. Tại sao Sort + lấy chỉ số chẵn?

Sau khi sort mảng tăng dần: `[a0, a1, a2, a3, ..., a_{2n-1}]`

Ghép cặp tối ưu là: `(a0, a1), (a2, a3), ..., (a_{2n-2}, a_{2n-1})`

→ `min` của mỗi cặp chính là phần tử tại **chỉ số chẵn** `(0, 2, 4, ...)`.

→ Kết quả = `a[0] + a[2] + a[4] + ...`

### 2. Chứng minh trực quan

Giả sử mảng đã sort: `[1, 2, 3, 4]`

| Cách ghép cặp | Tổng min |
|:---|:---:|
| `(1,2), (3,4)` ✅ | `1 + 3 = 4` |
| `(1,3), (2,4)` | `1 + 2 = 3` |
| `(1,4), (2,3)` | `1 + 2 = 3` |

→ Ghép liền kề sau khi sort luôn cho kết quả **lớn nhất**.

### 3. Tại sao ghép xa nhau lại tệ hơn?

Khi ghép `(a0, a_{2n-1})` — cặp có khoảng cách xa nhất — ta "lãng phí" `a_{2n-1}` (phần tử lớn nhất) mà không thu được gì. Sort + ghép liền kề đảm bảo mỗi phần tử bị lãng phí là nhỏ nhất có thể.

---

## Code Implementation (Java)

```java
import java.util.Arrays;

class Solution {
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);           // O(N log N)
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];          // lấy phần tử tại chỉ số chẵn
        }
        return sum;
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `nums = [1, 4, 3, 2]`

**Sau khi sort:** `[1, 2, 3, 4]`

| Chỉ số | Giá trị | Lấy vào sum? |
|:------:|:-------:|:------------:|
| 0      | 1       | ✅ `sum = 1` |
| 1      | 2       | ❌           |
| 2      | 3       | ✅ `sum = 4` |
| 3      | 4       | ❌           |

**Kết quả: `4`** ✅

---

### Example 2: `nums = [6, 2, 6, 5, 1, 2]`

**Sau khi sort:** `[1, 2, 2, 5, 6, 6]`

| Chỉ số | Giá trị | Lấy vào sum?  |
|:------:|:-------:|:-------------:|
| 0      | 1       | ✅ `sum = 1`  |
| 1      | 2       | ❌            |
| 2      | 2       | ✅ `sum = 3`  |
| 3      | 5       | ❌            |
| 4      | 6       | ✅ `sum = 9`  |
| 5      | 6       | ❌            |

**Kết quả: `9`** ✅

---

## Complexity Analysis

| Tiêu Chí | Giá Trị | Ghi Chú |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(N \log N)$ | Dominated bởi bước sort |
| **Space Complexity** | $\mathcal{O}(1)$ | Chỉ dùng biến đếm, không cần thêm bộ nhớ |

---

## So Sánh Các Cách Tiếp Cận

| Approach | Time | Space | Nhận Xét |
|:---|:---|:---|:---|
| **Brute Force** | $\mathcal{O}(N! )$ | $\mathcal{O}(1)$ | Thử mọi cách ghép — không thực tế |
| **Greedy + Sort** ✅ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(1)$ | **Tối ưu** — đơn giản và nhanh |
| **Counting Sort** | $\mathcal{O}(N + R)$ | $\mathcal{O}(R)$ | Dùng được vì giá trị nằm trong `[-10⁴, 10⁴]` |

> **Ghi chú:** Counting Sort (R = 2×10⁴) có thể đạt O(N) time nhưng cần O(R) space. Với bài này, Greedy + `Arrays.sort()` là cách viết **tối giản và đủ nhanh** nhất trong thực tế.
