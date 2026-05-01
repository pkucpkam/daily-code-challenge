# Giải Thích - 318. Tích Lớn Nhất Độ Dài Từ

## Phân Tích Bài Toán

Mục tiêu là tìm tích lớn nhất của độ dài hai từ mà không chia sẻ bất kỳ ký tự chung nào.

### Kỹ Thuật Chính
Hai từ không chia sẻ ký tự chung nếu tập hợp ký tự trong word[i] và tập hợp ký tự trong word[j] không giao nhau.

## Cách Tiếp Cận: Bit Manipulation + Brute Force

Chúng ta sử dụng **bit masks** để biểu diễn tập hợp ký tự của mỗi từ. Một bit mask là một số nguyên trong đó mỗi trong 26 bit biểu diễn xem một chữ cái (a-z) có xuất hiện trong từ hay không.

### Tại Sao Sử Dụng Bit Masks?
- **So Sánh Hiệu Quả**: Kiểm tra xem hai từ có ký tự chung trở thành một phép toán bitwise AND đơn giản.
- **Tra Cứu O(1)**: Sau khi xử lý trước, so sánh hai từ mất thời gian hằng số.

### Thuật Toán

1. **Tạo Bit Masks**: Với mỗi từ, tạo một bitmask trong đó:
   - Bit 0 biểu diễn 'a', Bit 1 biểu diễn 'b', ..., Bit 25 biểu diễn 'z'
   - Nếu ký tự 'c' xuất hiện trong từ, đặt bit (c - 'a') thành 1

2. **Kiểm Tra Tất Cả Các Cặp**: Với mỗi cặp từ (i, j):
   - Thực hiện bitwise AND trên masks của chúng: `masks[i] & masks[j]`
   - Nếu kết quả bằng 0, các từ không có ký tự chung
   - Tính tích: `words[i].length() * words[j].length()`
   - Cập nhật tích lớn nhất

3. **Trả Về Kết Quả**: Trả về tích lớn nhất được tìm thấy

### Ví Dụ Minh Họa

**Đầu Vào:** `["abcw","baz","foo","bar","xtfn","abcdef"]`

| Từ      | Mask (binary)           | Thập Phân |
|---------|------------------------|---------|
| "abcw"  | 00...001111 (a,b,c,w)  | 1855    |
| "baz"   | 00...1000011 (a,b,z)   | 67      |
| "foo"   | 00...1000010000 (f,o)  | 2048    |
| "bar"   | 00...001011 (a,b,r)    | 11      |
| "xtfn"  | 0...(t,n,f,x)          | 11136   |
| "abcdef"| 00...0111111 (a-f)     | 63      |

**Kiểm Tra Các Cặp:**
- "abcw" (1855) & "xtfn" (11136) = 0 ✓ → Tích = 4 × 4 = **16**
- "baz" (67) & "foo" (2048) = 0 ✓ → Tích = 3 × 3 = 9
- Các cặp khác có ký tự chung

**Kết Quả:** 16

## Phân Tích Độ Phức Tạp

- **Độ Phức Tạp Thời Gian**: 
  - Tạo masks: $O(n \times m)$, trong đó $n$ là số từ và $m$ là độ dài trung bình
  - Kiểm tra các cặp: $O(n^2)$
  - Tổng: $O(n \times m + n^2)$

- **Độ Phức Tạp Không Gian**: $O(n)$ cho việc lưu trữ các bitmasks

## Hiện Thực Mã

```java
class Solution {
    public int maxProduct(String[] words) {
        int n = words.length;
        // Tạo một bitmask cho mỗi từ
        int[] masks = new int[n];
        
        for (int i = 0; i < n; i++) {
            int mask = 0;
            for (char c : words[i].toCharArray()) {
                // Đặt bit cho ký tự c (0-25 cho a-z)
                mask |= (1 << (c - 'a'));
            }
            masks[i] = mask;
        }
        
        int maxProduct = 0;
        
        // Kiểm tra tất cả các cặp từ
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Nếu masks không giao nhau (không có ký tự chung)
                if ((masks[i] & masks[j]) == 0) {
                    maxProduct = Math.max(maxProduct, words[i].length() * words[j].length());
                }
            }
        }
        
        return maxProduct;
    }
}
```

## Những Điểm Chính

1. **Bit masks** là mạnh mẽ để biểu diễn các tập hợp ký tự trong một số nguyên duy nhất
2. **Bitwise AND** (`&`) kiểm tra hiệu quả các phần tử chung giữa hai tập hợp
3. Giải pháp này tránh tạo HashSet/HashMaps cho mỗi từ, làm cho nó hiệu quả hơn
4. Kiểm tra cặp brute force $O(n^2)$ là tối ưu vì chúng ta cần kiểm tra tất cả các cặp trong trường hợp xấu nhất
