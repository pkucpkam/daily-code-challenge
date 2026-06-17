# Giải thích thuật toán: Reconstruct Original Digits from English

## Phương pháp tiếp cận

Bài toán yêu cầu chúng ta tìm lại các chữ số gốc từ một chuỗi chứa các ký tự tiếng Anh bị xáo trộn. Mấu chốt của bài toán này là nhận ra rằng một số từ tiếng Anh của các chữ số có chứa các **ký tự độc nhất** (chỉ xuất hiện trong từ đó mà không xuất hiện trong các từ khác từ 0 đến 9).

Hãy xem danh sách các từ:
- 0: "zero"
- 1: "one"
- 2: "two"
- 3: "three"
- 4: "four"
- 5: "five"
- 6: "six"
- 7: "seven"
- 8: "eight"
- 9: "nine"

### Bước 1: Tìm các chữ số có ký tự độc nhất
- Chữ số **0** là số duy nhất có ký tự `'z'`.
- Chữ số **2** là số duy nhất có ký tự `'w'`.
- Chữ số **4** là số duy nhất có ký tự `'u'`.
- Chữ số **6** là số duy nhất có ký tự `'x'`.
- Chữ số **8** là số duy nhất có ký tự `'g'`.

Từ số lượng của các ký tự này, chúng ta có thể đếm chính xác số lần xuất hiện của các chữ số 0, 2, 4, 6, 8.

### Bước 2: Tìm các chữ số còn lại bằng phép trừ
Khi đã biết số lượng của 0, 2, 4, 6, 8, chúng ta có thể dùng các ký tự xuất hiện trong 2 từ (trong đó 1 từ đã biết số lượng) để suy ra:
- Ký tự `'h'` có mặt trong "three" (3) và "eight" (8). Do đã biết (8), ta tính được **3**: `count[3] = count['h'] - count[8]`.
- Ký tự `'f'` có mặt trong "five" (5) và "four" (4). Ta tính được **5**: `count[5] = count['f'] - count[4]`.
- Ký tự `'s'` có mặt trong "seven" (7) và "six" (6). Ta tính được **7**: `count[7] = count['s'] - count[6]`.
- Ký tự `'o'` có mặt trong "one" (1), "zero" (0), "two" (2), và "four" (4). Ta tính được **1**: `count[1] = count['o'] - count[0] - count[2] - count[4]`.
- Ký tự `'i'` có mặt trong "nine" (9), "five" (5), "six" (6), và "eight" (8). Ta tính được **9**: `count[9] = count['i'] - count[5] - count[6] - count[8]`.

### Bước 3: Xây dựng chuỗi kết quả
Duyệt từ số 0 đến 9, số nào có số lượng > 0 thì in số đó ra tương ứng với số lần xuất hiện. Bằng cách này, chuỗi sẽ tự động theo thứ tự tăng dần như đề bài yêu cầu.

## Độ phức tạp
- **Thời gian (Time Complexity):** $O(N)$, với $N$ là độ dài của chuỗi `s`. Chúng ta chỉ duyệt qua chuỗi một lần để đếm tần suất các ký tự, sau đó thực hiện vài phép tính với các mảng cố định (kích thước nhỏ, chỉ gồm 26 và 10 phần tử).
- **Không gian bộ nhớ (Space Complexity):** $O(1)$. Không gian lưu trữ bổ sung duy nhất là mảng đếm kích thước 26 (tần suất chữ cái) và mảng kích thước 10 (số lượng các chữ số), có kích thước không đổi không phụ thuộc vào input.
