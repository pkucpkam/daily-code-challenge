# 299. Bulls and Cows - Best Solution

## Tóm tắt bài toán

Cho hai chuỗi số `secret` và `guess` có cùng độ dài:

- `bull` là ký tự khớp cả giá trị lẫn vị trí.
- `cow` là ký tự có trong `secret` nhưng sai vị trí (không tính các ký tự đã là bull).

Trả về chuỗi kết quả dạng `xAyB`.

## Ý tưởng tối ưu (one-pass)

Duyệt một lần từ trái sang phải:

- Nếu `secret[i] == guess[i]` thì tăng `bulls`.
- Nếu khác nhau:
  - Dùng mảng `balance[10]` để theo dõi chênh lệch số lượng từng chữ số giữa phần đã duyệt của `secret` và `guess`.
  - Nếu trước khi cộng, `balance[s] < 0` thì nghĩa là chữ số `s` này từng xuất hiện dư bên `guess`, nên ghép thành 1 cow.
  - Nếu trước khi trừ, `balance[g] > 0` thì nghĩa là chữ số `g` này từng xuất hiện dư bên `secret`, nên ghép thành 1 cow.
  - Sau đó cập nhật `balance[s]++` và `balance[g]--`.

Ý tưởng này tự xử lý đúng cả trường hợp có ký tự lặp.

## Vì sao đúng

- `bulls` chỉ đếm khi đúng vị trí nên luôn chính xác.
- Với các vị trí không phải bull, `balance[d]` biểu diễn số lần chữ số `d` còn dư từ `secret` so với `guess` trong phần đã duyệt.
- Khi gặp một chữ số có thể triệt tiêu phần dư phía đối diện, ta tăng `cows` đúng 1 lần cho mỗi cặp ghép hợp lệ.
- Mỗi ký tự chỉ được ghép tối đa một lần, nên không đếm trùng cow.

## Độ phức tạp

- Time: `O(n)`
- Space: `O(1)` (mảng kích thước cố định 10)

## Java Code

```java
class Solution {
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] balance = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int g = guess.charAt(i) - '0';

            if (s == g) {
                bulls++;
            } else {
                if (balance[s] < 0) {
                    cows++;
                }
                if (balance[g] > 0) {
                    cows++;
                }
                balance[s]++;
                balance[g]--;
            }
        }

        return bulls + "A" + cows + "B";
    }
}
```
