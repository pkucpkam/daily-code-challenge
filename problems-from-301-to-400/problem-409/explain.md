# 560. Subarray Sum Equals K

## Intuition

Bài toán yêu cầu đếm số lượng **subarray liên tiếp** có tổng bằng `k`. Brute-force O(N²) kiểm tra mọi cặp (i, j) là cách đơn giản nhưng không tối ưu.

Ý tưởng tốt hơn: dùng **Prefix Sum + HashMap**.

- Gọi `prefixSum[i]` = tổng từ `nums[0]` đến `nums[i]`.
- Tổng của subarray từ `j+1` đến `i` = `prefixSum[i] - prefixSum[j]`.
- Ta cần: `prefixSum[i] - prefixSum[j] == k` → tức là `prefixSum[j] == prefixSum[i] - k`.
- Dùng **HashMap** lưu số lần xuất hiện của từng `prefixSum` → tra cứu O(1).

---

## Key Insights & Algorithmic Strategy

### 1. Prefix Sum Definition

```
prefixSum[i] = nums[0] + nums[1] + ... + nums[i]
```

Tổng subarray `[j+1..i]` = `prefixSum[i] - prefixSum[j]`

### 2. HashMap Trick

Thay vì duyệt mọi cặp (i, j), với mỗi vị trí `i`:
- Tính `prefixSum` hiện tại.
- Tra trong map xem `prefixSum - k` đã xuất hiện bao nhiêu lần → đó là số subarray kết thúc tại `i` có tổng = `k`.
- Cập nhật map với `prefixSum` hiện tại.

### 3. Khởi Tạo Map với `{0: 1}`

```
prefixCount.put(0, 1)
```

Điều này xử lý trường hợp subarray bắt đầu từ index 0 có tổng = `k` (tức `prefixSum - k == 0`).

---

## Code Implementation (Java)

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);

        int count = 0;
        int prefixSum = 0;

        for (int num : nums) {
            prefixSum += num;
            count += prefixCount.getOrDefault(prefixSum - k, 0);
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `nums = [1,1,1], k = 2`

| Bước | `num` | `prefixSum` | `prefixSum - k` | Map trước khi update | `count` |
|:----:|:-----:|:-----------:|:---------------:|:--------------------:|:-------:|
| init | —     | 0           | —               | `{0:1}`              | 0       |
| 1    | 1     | 1           | -1              | `{0:1}`              | 0       |
| 2    | 1     | 2           | 0               | `{0:1, 1:1}`         | **1**   |
| 3    | 1     | 3           | 1               | `{0:1, 1:1, 2:1}`    | **2** ✅ |

### Example 2: `nums = [1,2,3], k = 3`

| Bước | `num` | `prefixSum` | `prefixSum - k` | `count` |
|:----:|:-----:|:-----------:|:---------------:|:-------:|
| init | —     | 0           | —               | 0       |
| 1    | 1     | 1           | -2              | 0       |
| 2    | 2     | 3           | 0               | **1**   |
| 3    | 3     | 6           | 3               | **2** ✅ |

Hai subarray thỏa mãn: `[3]` và `[1,2]`.

---

## Complexity Analysis

| Tiêu Chí | Giá Trị | Ghi Chú |
|:---|:---|:---|
| **Time Complexity** | $\mathcal{O}(N)$ | Duyệt mảng một lần, mỗi thao tác HashMap là O(1) |
| **Space Complexity** | $\mathcal{O}(N)$ | HashMap lưu tối đa N prefix sum khác nhau |

---

## So Sánh Các Cách Tiếp Cận

| Approach | Time | Space | Nhận Xét |
|:---|:---|:---|:---|
| **Brute Force** | $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ | Kiểm tra mọi cặp (i, j) — TLE với N lớn |
| **Prefix Sum Array** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | Tính sẵn prefix sum nhưng vẫn cần 2 vòng lặp |
| **Prefix Sum + HashMap** ✅ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | **Tối ưu** — một lần duyệt, tra cứu O(1) |

> **Lưu ý:** Mảng có thể chứa số âm nên kỹ thuật sliding window không áp dụng được. HashMap là cách tối ưu duy nhất đạt O(N).
