# 307. Range Sum Query - Mutable - Lời giải tối ưu

## Tóm tắt bài toán

Cần thiết kế cấu trúc dữ liệu hỗ trợ 2 thao tác trên mảng nums:

- update(index, val): cập nhật 1 phần tử.
- sumRange(left, right): tính tổng đoạn [left..right].

Với tối đa 3 * 10^4 thao tác, tính tổng trực tiếp mỗi lần (O(n)) sẽ chậm.

## Ý tưởng tối ưu: Fenwick Tree (Binary Indexed Tree)

Fenwick Tree lưu các tổng trung gian để cả 2 thao tác đều đạt O(log n):

- Point update: lan truyền delta lên các nút phụ trách.
- Prefix sum: đi theo liên kết cha để cộng nhanh.

Khi đó:

sumRange(left, right) = prefixSum(right) - prefixSum(left - 1)

## Vì sao đúng

- Bất biến của Fenwick Tree: mỗi bit[i] lưu tổng của một đoạn kết thúc tại i.
- Khi update, cộng delta vào tất cả nút bao phủ index đó, nên mọi tổng đoạn liên quan vẫn đúng.
- prefixSum(x) cộng các đoạn rời nhau và phân hoạch đúng [0..x], nên kết quả chính xác.
- Tổng đoạn [left..right] bằng hiệu 2 prefix sum chính xác, nên kết quả cuối cùng đúng.

## Độ phức tạp

- Constructor: O(n log n) (xây bằng nhiều lần update).
- update: O(log n).
- sumRange: O(log n).
- Bộ nhớ phụ: O(n).

## Java implementation

```java
class NumArray {

    private final int[] nums;
    private final int[] bit;
    private final int n;

    public NumArray(int[] nums) {
        this.n = nums.length;
        this.nums = new int[n];
        this.bit = new int[n + 1];

        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }
    }

    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;

        int i = index + 1;
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    public int sumRange(int left, int right) {
        return prefixSum(right) - prefixSum(left - 1);
    }

    private int prefixSum(int index) {
        int sum = 0;
        int i = index + 1;

        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }

        return sum;
    }
}
```
