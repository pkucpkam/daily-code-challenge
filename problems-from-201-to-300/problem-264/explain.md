# 223. Rectangle Area - Best Solution

## Mô tả bài toán

Cho 2 hình chữ nhật song song với trục tọa độ:

- Hình A: `(ax1, ay1)` là góc dưới-trái, `(ax2, ay2)` là góc trên-phải.
- Hình B: `(bx1, by1)` là góc dưới-trái, `(bx2, by2)` là góc trên-phải.

Yêu cầu: tính **tổng diện tích phần được phủ** bởi hai hình.

## Ý tưởng cốt lõi

Tổng diện tích phủ = diện tích A + diện tích B - diện tích phần giao nhau (nếu có).

1. Diện tích từng hình:

   - `areaA = (ax2 - ax1) * (ay2 - ay1)`
   - `areaB = (bx2 - bx1) * (by2 - by1)`

2. Tính chiều rộng và chiều cao phần giao nhau:

   - `overlapWidth = min(ax2, bx2) - max(ax1, bx1)`
   - `overlapHeight = min(ay2, by2) - max(ay1, by1)`

3. Nếu không giao nhau thì một trong hai giá trị trên sẽ âm, nên ép về 0:

   - `overlapWidth = max(0, overlapWidth)`
   - `overlapHeight = max(0, overlapHeight)`

4. `overlapArea = overlapWidth * overlapHeight`.

5. Kết quả:

   - `areaA + areaB - overlapArea`

## Minh họa nhanh

Ví dụ 1:

- A: từ `(-3, 0)` đến `(3, 4)` => `areaA = 6 * 4 = 24`
- B: từ `(0, -1)` đến `(9, 2)` => `areaB = 9 * 3 = 27`
- Giao nhau:
  - rộng: `min(3, 9) - max(-3, 0) = 3 - 0 = 3`
  - cao: `min(4, 2) - max(0, -1) = 2 - 0 = 2`
  - `overlapArea = 3 * 2 = 6`
- Kết quả: `24 + 27 - 6 = 45`

## Độ phức tạp

- Thời gian: `O(1)`
- Bộ nhớ: `O(1)`

## Java code

```java
class Solution {
    public int computeArea(int ax1, int ay1, int ax2, int ay2,
                           int bx1, int by1, int bx2, int by2) {
        int areaA = (ax2 - ax1) * (ay2 - ay1);
        int areaB = (bx2 - bx1) * (by2 - by1);

        int overlapWidth = Math.max(0, Math.min(ax2, bx2) - Math.max(ax1, bx1));
        int overlapHeight = Math.max(0, Math.min(ay2, by2) - Math.max(ay1, by1));
        int overlapArea = overlapWidth * overlapHeight;

        return areaA + areaB - overlapArea;
    }
}
```
