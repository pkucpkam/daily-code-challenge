# 275. H-Index II - Best Solution

## Tóm tắt bài toán

Cho mảng `citations` đã được sắp xếp tăng dần, hãy trả về chỉ số **h-index** của nhà nghiên cứu.

`h-index` là giá trị lớn nhất `h` sao cho có ít nhất `h` bài báo, và mỗi bài trong số đó có số lượt trích dẫn không nhỏ hơn `h`.

Vì mảng đã được sắp xếp, ta có thể dùng **binary search** để đạt độ phức tạp `O(log n)`.

## Ý tưởng chính

Tại vị trí `mid`, số bài báo ở phía bên phải và bao gồm `mid` là `n - mid`.

- Nếu `citations[mid] == n - mid`, thì ta tìm được chính xác đáp án.
- Nếu `citations[mid] < n - mid`, nghĩa là giá trị hiện tại còn quá nhỏ, cần dịch sang phải để tìm chỉ số h lớn hơn.
- Nếu `citations[mid] > n - mid`, nghĩa là có thể còn một đáp án tốt hơn ở bên trái.

Sau khi binary search kết thúc, đáp án là `n - left`.

## Vì sao đúng

Giả sử `h = n - i`.

- Phần tử `citations[i]` đại diện cho bài báo đầu tiên trong nhóm có thể tạo ra một h-index hợp lệ.
- Khi `citations[i] >= n - i`, có ít nhất `n - i` bài báo với số trích dẫn không nhỏ hơn `n - i`.
- Ta cần tìm giá trị nhỏ nhất của `i` thỏa điều kiện này, vì khi đó `n - i` là h-index lớn nhất.

Binary search trên mảng tăng dần giúp tìm vị trí đó trong `O(log n)`.

## Độ phức tạp

- Time: `O(log n)`
- Space: `O(1)`

## Java Code

```java
class Solution {
    public int hIndex(int[] citations) {
        int left = 0;
        int right = citations.length - 1;
        int n = citations.length;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int papersWithAtLeastMid = n - mid;

            if (citations[mid] == papersWithAtLeastMid) {
                return papersWithAtLeastMid;
            }

            if (citations[mid] < papersWithAtLeastMid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return n - left;
    }
}
```
