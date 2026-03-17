# Giải thích bài 247 - Tenth Line

Mục tiêu: in ra đúng dòng thứ 10 của file `file.txt`.

Nếu file có ít hơn 10 dòng thì không in gì (output rỗng).

## Cách 1: Dùng `awk` (gọn và phổ biến)

```bash
awk 'NR==10 { print; exit }' file.txt
```

Ý tưởng:

- `NR` là số thứ tự dòng hiện tại.
- Khi `NR == 10`, in dòng đó.
- `exit` để dừng sớm, không cần đọc phần còn lại của file.

## Cách 2: Dùng `sed`

```bash
sed -n '10p' file.txt
```

Ý tưởng:

- `-n` tắt chế độ in mặc định của `sed`.
- `10p` nghĩa là chỉ in dòng số 10.

## Cách 3: Dùng `tail` + `head`

```bash
tail -n +10 file.txt | head -n 1
```

Ý tưởng:

- `tail -n +10` lấy từ dòng 10 đến hết file.
- `head -n 1` lấy dòng đầu tiên trong phần đó, tức dòng 10 ban đầu.

## Hành vi khi file < 10 dòng

Cả 3 cách trên đều không in gì nếu `file.txt` không có dòng thứ 10.

## Độ phức tạp

- Thời gian: O(n), với `n` là số dòng cần đọc.
- Bộ nhớ phụ: O(1).

`awk` với `exit` thường tối ưu hơn trong thực tế vì có thể dừng ngay khi gặp dòng 10.
