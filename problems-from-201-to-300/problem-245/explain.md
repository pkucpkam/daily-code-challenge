# Ý tưởng tối ưu - Word Frequency

Bài này yêu cầu viết bash script để đếm số lần xuất hiện của từng từ trong file words.txt và in theo thứ tự giảm dần theo tần suất.

Giải pháp tốt nhất là dùng pipeline Unix ngắn gọn:

```bash
tr -s '[:space:]' '\n' < words.txt | sort | uniq -c | sort -rn | awk '{print $2, $1}'
```

## Giải thích từng bước

1. Chuẩn hóa dữ liệu đầu vào

- `tr -s '[:space:]' '\n' < words.txt`
- Mọi khoảng trắng (space, tab, xuống dòng) được gom lại và đổi thành xuống dòng.
- Sau bước này, mỗi dòng tương ứng một từ.

2. Gom các từ giống nhau

- `sort`
- Cần sắp xếp trước để `uniq -c` đếm chính xác theo cụm liên tiếp.

3. Đếm tần suất

- `uniq -c`
- Tạo output dạng: `count word`.

4. Sắp xếp theo tần suất giảm dần

- `sort -rn`
- `-n` là so sánh số, `-r` là giảm dần.

5. Đổi format kết quả

- `awk '{print $2, $1}'`
- Từ `count word` thành `word count` đúng yêu cầu đề.

## Ví dụ

Input:

```text
the day is sunny the the
the sunny is is
```

Sau pipeline, output:

```text
the 4
is 3
sunny 2
day 1
```

## Độ phức tạp

Gọi tổng số từ là n.

- Thời gian: O(n log n), do hai lần sắp xếp.
- Bộ nhớ: phụ thuộc vào triển khai của công cụ hệ thống (đặc biệt là sort), thường dùng bộ nhớ tạm theo dữ liệu.

## Vì sao đây là best solution

- Đúng tinh thần bài: shell + one-liner Unix pipes.
- Ngắn, mạnh, dễ đọc với người quen command line.
- Tận dụng công cụ chuẩn hệ Unix thay vì tự viết xử lý dài dòng.
