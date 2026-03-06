# Compare Version Numbers

## Yêu cầu bài toán

- Cho hai chuỗi phiên bản `version1` và `version2`. Hãy so sánh chúng.
- Các phiên bản bao gồm các phần số (revisions) cách nhau bởi dấu chấm `.`.
- Khi so sánh:
  - Bỏ qua các chữ số 0 đứng đầu mỗi phần (ví dụ "01" tương đương "1").
  - So sánh từng phần từ trái sang phải.
  - Nếu một chuỗi có ít phần hơn chuỗi kia, hãy coi các phần thiếu là số 0.
- Kết quả:
  - Nếu `version1 < version2`, trả về `-1`.
  - Nếu `version1 > version2`, trả về `1`.
  - Ngược lại, trả về `0`.

## Ý tưởng cốt lõi

Cách dễ nhất là tách (split) chuỗi phiên bản thành các mảng dựa trên dấu chấm. Sau đó, chúng ta duyệt qua mảng dài nhất và so sánh từng cặp số nguyên tại mỗi vị trí. Nếu một mảng đã hết phần tử, chúng ta mặc định giá trị tại vị trí đó bằng 0.

## Thuật toán

1. Tách chuỗi bằng phương thức `split("\\.")` để có các mảng chuỗi con.
2. Tìm độ dài lớn nhất giữa hai mảng này.
3. Chạy vòng lặp từ 0 đến độ dài lớn nhất:
   - Lấy giá trị nguyên của phần thứ `i` trong `version1`. Nếu `i` vượt quá độ dài mảng, giá trị bằng 0.
   - Lấy giá trị nguyên của phần thứ `i` trong `version2`. Nếu `i` vượt quá độ dài mảng, giá trị bằng 0.
   - So sánh hai giá trị nguyên vừa lấy được:
     - Nếu `v1 > v2`, trả về `1`.
     - Nếu `v1 < v2`, trả về `-1`.
4. Nếu đi hết vòng lặp mà không có sự khác biệt, hai phiên bản bằng nhau, trả về `0`.

## Độ phức tạp
- **Thời gian**: $O(N + M)$ - Với $N, M$ là độ dài của hai chuỗi phiên bản.
- **Không gian**: $O(N + M)$ - Lưu trữ các mảng chuỗi sau khi tách.

## Code (Java)

```java
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        
        int length = Math.max(v1.length, v2.length);
        for (int i = 0; i < length; i++) {
            // Chuyển đổi phần hiện tại thành số nguyên, nếu thiếu thì mặc định là 0
            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            
            if (num1 < num2) return -1;
            if (num1 > num2) return 1;
        }
        
        return 0;
    }
}
```
