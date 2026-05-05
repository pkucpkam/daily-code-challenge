# Giải Thích Bài Toán: 328. Odd Even Linked List

Mục tiêu của bài toán là nhóm tất cả các nút ở vị trí lẻ (index 1, 3, 5...) lại với nhau, sau đó nối tiếp bằng các nút ở vị trí chẵn (index 2, 4, 6...).

## Phương Pháp: Hai Con Trỏ (O(n) Time, O(1) Space)

Đây là cách tiếp cận tối ưu nhất, sử dụng hai con trỏ để tách danh sách gốc thành hai danh sách con riêng biệt (lẻ và chẵn) sau đó nối chúng lại.

### 1. Khởi tạo
- `odd`: Con trỏ trỏ đến nút đầu tiên (vị trí 1 - lẻ).
- `even`: Con trỏ trỏ đến nút thứ hai (vị trí 2 - chẵn).
- `evenHead`: Lưu lại nút đầu tiên của danh sách chẵn để sau này nối vào cuối danh sách lẻ.

### 2. Duyệt và Tách
Chúng ta sử dụng một vòng lặp để duyệt qua danh sách. Ở mỗi bước:
- Nối nút tiếp theo của `odd` vào nút tiếp theo của `even` (nút lẻ kế tiếp).
- Di chuyển `odd` sang nút vừa nối.
- Nối nút tiếp theo của `even` vào nút tiếp theo của `odd` (nút chẵn kế tiếp).
- Di chuyển `even` sang nút vừa nối.

**Điều kiện dừng:** Khi `even` hoặc `even.next` là `null`, nghĩa là không còn nút nào để xử lý.

### 3. Nối hai danh sách
Sau khi tách xong, chúng ta chỉ cần nối đuôi của danh sách lẻ (`odd.next`) vào đầu của danh sách chẵn (`evenHead`).

## Hình minh họa luồng xử lý
Giả sử danh sách là: `1 -> 2 -> 3 -> 4 -> 5`

- **Bắt đầu:** `odd = 1`, `even = 2`, `evenHead = 2`
- **Bước 1:** 
    - `1.next = 3`, `odd = 3`
    - `2.next = 4`, `even = 4`
    - Danh sách tạm: `1 -> 3`, `2 -> 4 -> 5`
- **Bước 2:**
    - `3.next = 5`, `odd = 5`
    - `4.next = null`, `even = null`
    - Danh sách tạm: `1 -> 3 -> 5`, `2 -> 4 -> null`
- **Kết thúc:** Nối `5.next = evenHead` (là 2)
    - Kết quả: `1 -> 3 -> 5 -> 2 -> 4`

## Phân Tích Độ Phức Tạp
- **Thời gian (Time Complexity):** $O(n)$ vì chúng ta chỉ duyệt qua danh sách một lần duy nhất.
- **Không gian (Space Complexity):** $O(1)$ vì chúng ta chỉ thay đổi các liên kết (`next`) của các nút có sẵn, không tạo thêm cấu trúc dữ liệu mới.

---

## Lưu ý quan trọng
- Luôn kiểm tra điều kiện `head == null` ở đầu hàm.
- Việc duy trì `evenHead` là cực kỳ quan trọng vì nếu không có nó, chúng ta sẽ mất dấu điểm bắt đầu của phần danh sách chẵn sau khi đã thay đổi các liên kết.
