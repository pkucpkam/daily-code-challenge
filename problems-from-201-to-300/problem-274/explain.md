# 260. Single Number III - Best Solution

## Mô tả bài toán

Cho mảng `nums` trong đó:

- Có đúng 2 phần tử xuất hiện đúng 1 lần.
- Tất cả phần tử còn lại xuất hiện đúng 2 lần.

Yêu cầu tìm 2 phần tử xuất hiện 1 lần với:

- Thời gian tuyến tính `O(n)`
- Bộ nhớ phụ hằng số `O(1)`

## Ý tưởng tối ưu: XOR + tách theo bit khác nhau

Gọi hai số cần tìm là `x` và `y`.

1. XOR toàn bộ mảng:

   `xorAll = x ^ y`

   Vì các số xuất hiện 2 lần sẽ triệt tiêu nhau (`a ^ a = 0`), nên kết quả còn lại là XOR của 2 số đơn lẻ.

2. Tìm một bit mà `x` và `y` khác nhau:

   `mask = xorAll & -xorAll`

   Đây là bit 1 thấp nhất trong `xorAll`.

3. Chia mảng thành 2 nhóm theo bit `mask`:

- Nhóm 1: `(num & mask) == 0`
- Nhóm 2: `(num & mask) != 0`

   Vì `x` và `y` khác nhau ở bit này, chúng chắc chắn nằm ở 2 nhóm khác nhau.

4. XOR trong từng nhóm:

   Các cặp trùng nhau vẫn triệt tiêu, mỗi nhóm còn lại đúng 1 số đơn lẻ.

## Chứng minh đúng ngắn gọn

- Sau bước 1, `xorAll = x ^ y` do mọi phần tử xuất hiện 2 lần bị triệt tiêu.
- `mask` chọn một bit mà `x` và `y` khác nhau, nên một số có bit 0 và số còn lại có bit 1 ở vị trí đó.
- Khi chia nhóm theo `mask`, hai số đơn lẻ bị tách ra hai nhóm khác nhau; các số trùng nhau luôn rơi cùng một nhóm và triệt tiêu qua XOR.
- Do đó XOR mỗi nhóm trả về chính xác một số cần tìm.

## Độ phức tạp

- Thời gian: `O(n)`
- Bộ nhớ phụ: `O(1)`

## Java Code

```java
class Solution {
    public int[] singleNumber(int[] nums) {
        int xorAll = 0;
        for (int num : nums) {
            xorAll ^= num;
        }

        int rightMostSetBit = xorAll & -xorAll;

        int a = 0;
        int b = 0;
        for (int num : nums) {
            if ((num & rightMostSetBit) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }

        return new int[] {a, b};
    }
}
```
