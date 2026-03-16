# Ý tưởng tối ưu - Transpose File

Bài này yêu cầu hoán vị ma trận từ file `file.txt`: dòng thành cột, cột thành dòng.

Giải pháp tốt nhất trong shell là dùng `awk` vì:

- `awk` xử lý theo từng dòng, từng cột (`$1`, `$2`, ...), rất đúng bản chất đề.
- Chỉ cần đọc file một lần.
- Code ngắn, rõ ràng, dễ mở rộng.

```bash
awk '{
	for (i = 1; i <= NF; i++) {
		if (NR == 1) {
			col[i] = $i
		} else {
			col[i] = col[i] " " $i
		}
	}
}
END {
	for (i = 1; i <= length(col); i++) {
		print col[i]
	}
}' file.txt
```

## Giải thích từng bước

1. Duyệt từng dòng và từng cột

- `NR` là số thứ tự dòng hiện tại.
- `NF` là số cột của dòng hiện tại.
- Vòng `for (i = 1; i <= NF; i++)` duyệt tất cả cột trong dòng đó.

2. Xây dựng dữ liệu theo cột

- Mảng `col[i]` lưu nội dung của cột thứ `i` sau khi transpose.
- Ở dòng đầu (`NR == 1`), gán trực tiếp: `col[i] = $i`.
- Từ dòng thứ 2 trở đi, nối thêm bằng khoảng trắng: `col[i] = col[i] " " $i`.

3. In kết quả

- Khối `END` chạy sau khi đọc xong file.
- In lần lượt `col[1]`, `col[2]`, ... để ra các dòng đã transpose.

## Ví dụ

Input:

```text
name age
alice 21
ryan 30
```

Quá trình xây `col`:

- Sau dòng 1: `col[1]="name"`, `col[2]="age"`
- Sau dòng 2: `col[1]="name alice"`, `col[2]="age 21"`
- Sau dòng 3: `col[1]="name alice ryan"`, `col[2]="age 21 30"`

Output:

```text
name alice ryan
age 21 30
```

## Độ phức tạp

Gọi `r` là số dòng, `c` là số cột.

- Thời gian: O(r * c)
- Bộ nhớ: O(r * c) cho dữ liệu kết quả trong mảng `col`

## Vì sao đây là best solution

- Dễ hiểu, đúng bản chất transpose theo cột.
- Hiệu năng tốt: quét dữ liệu một lần.
- Thuần Unix shell (`awk`), không cần công cụ phụ hay nhiều pipeline phức tạp.
