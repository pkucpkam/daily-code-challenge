# 306. Additive Number - Best Solution

## Tom tat bai toan

Cho chuoi so `num`. Can kiem tra xem co the tach thanh day cong (additive sequence) hay khong:

- Day phai co it nhat 3 so.
- Tu so thu 3 tro di: `a[i] = a[i - 1] + a[i - 2]`.
- Khong duoc co so co leading zero (tru khi chinh no la `0`).

## Y tuong toi uu

Brute force 2 so dau (`first`, `second`), sau do sinh cac so tiep theo bang phep cong chuoi.

Tai sao cong chuoi?

- Do do dai `num` len toi 35, co the vuot qua `long`/`BigInteger` neu xu ly khong can than.
- Cong chuoi truc tiep giup tranh overflow hoan toan.

Quy trinh:

1. Thu moi do dai hop le cho so thu nhat.
2. Thu moi do dai hop le cho so thu hai.
3. Neu mot trong hai so dau co leading zero thi bo qua ngay.
4. Lap:
   - Tinh `sum = first + second` (bang string addition).
   - Kiem tra `num` co bat dau bang `sum` tai vi tri hien tai khong.
   - Neu khong khop -> cap `(first, second)` nay sai.
   - Neu khop -> day cua so: `first = second`, `second = sum`.
5. Neu dung het chuoi thi tim thay day cong hop le.

## Vi sao dung

- Moi day cong deu duoc xac dinh duy nhat boi 2 so dau, nen viec liet ke toan bo cap `(first, second)` la day du.
- Voi moi cap, so tiep theo la duy nhat (`sum = first + second`), nen kiem tra tu trai sang phai la chinh xac.
- Neu tai bat ky buoc nao `sum` khong khop substring tuong ung, cap hien tai chac chan khong tao duoc day cong.
- Neu di den het chuoi, ta co mot phan hoach hop le thoa dinh nghia.

## Do phuc tap

- So cap `(first, second)` toi da: `O(n^2)`.
- Moi cap kiem tra toi da `O(n)` ky tu (tinh ca cong chuoi va so khop).
- Tong: `O(n^3)`, voi `n <= 35` la du nhanh.
- Bo nho phu: `O(n)` cho cac chuoi tam thoi.

## Java code

```java
class Solution {
    public boolean isAdditiveNumber(String num) {
        int n = num.length();

        for (int len1 = 1; len1 <= n / 2; len1++) {
            if (num.charAt(0) == '0' && len1 > 1) {
                break;
            }

            for (int len2 = 1; len1 + len2 < n; len2++) {
                if (num.charAt(len1) == '0' && len2 > 1) {
                    break;
                }

                if (isValidSequence(num, len1, len2)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isValidSequence(String num, int len1, int len2) {
        int start = 0;
        String first = num.substring(start, start + len1);
        start += len1;

        String second = num.substring(start, start + len2);
        start += len2;

        while (start < num.length()) {
            String sum = addStrings(first, second);
            if (!num.startsWith(sum, start)) {
                return false;
            }

            start += sum.length();
            first = second;
            second = sum;
        }

        return true;
    }

    private String addStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int da = i >= 0 ? a.charAt(i) - '0' : 0;
            int db = j >= 0 ? b.charAt(j) - '0' : 0;
            int sum = da + db + carry;

            sb.append((char) ('0' + (sum % 10)));
            carry = sum / 10;
            i--;
            j--;
        }

        return sb.reverse().toString();
    }
}
```
