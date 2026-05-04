# Giải Thích Bài Toán: 324. Wiggle Sort II

Mục tiêu của bài toán là sắp xếp lại mảng `nums` sao cho thỏa mãn điều kiện: `nums[0] < nums[1] > nums[2] < nums[3]...`.

## Phương Pháp: QuickSelect + Virtual Indexing (O(n) Time, O(1) Space)

Đây là giải pháp tối ưu nhất để giải quyết bài toán này, vượt qua giới hạn của cách tiếp cận sắp xếp thông thường ($O(n \log n)$).

### 1. Tìm Số Trung Vị (Median)
Đầu tiên, chúng ta tìm giá trị trung vị của mảng bằng thuật toán **QuickSelect**.
- Số trung vị chia mảng thành hai phần: các phần tử nhỏ hơn trung vị và các phần tử lớn hơn trung vị.
- Độ phức tạp trung bình của QuickSelect là $O(n)$.

### 2. Chỉ Số Ảo (Virtual Indexing)
Để sắp xếp theo kiểu "wiggle" (dập dềnh), chúng ta cần đưa các phần tử lớn vào các vị trí lẻ (1, 3, 5...) và các phần tử nhỏ vào các vị trí chẵn (0, 2, 4...).
Công thức ánh xạ chỉ số ảo:
`MappedIndex = (1 + 2 * i) % (n | 1)`

Ví dụ với $n=6$:
- `i = 0` -> `MappedIndex = 1`
- `i = 1` -> `MappedIndex = 3`
- `i = 2` -> `MappedIndex = 5`
- `i = 3` -> `MappedIndex = 0`
- `i = 4` -> `MappedIndex = 2`
- `i = 5` -> `MappedIndex = 4`

Cách ánh xạ này giúp chúng ta duyệt qua các vị trí lẻ trước, sau đó mới đến các vị trí chẵn.

### 3. Phân Loại 3 Phần (3-Way Partitioning)
Sử dụng thuật toán **Dutch National Flag** (Cờ Hà Lan) kết hợp với chỉ số ảo để phân loại mảng dựa trên giá trị trung vị (`median`):
- Các phần tử `> median`: Đưa vào các vị trí lẻ đầu tiên.
- Các phần tử `< median`: Đưa vào các vị trí chẵn cuối cùng.
- Các phần tử `= median`: Nằm ở giữa.

### 4. Tại sao cách này hoạt động?
Việc sử dụng số trung vị và sắp xếp các phần tử lớn vào vị trí lẻ, phần tử nhỏ vào vị trí chẵn đảm bảo rằng không có hai phần tử bằng nhau (đặc biệt là các số bằng trung vị) nằm cạnh nhau, thỏa mãn điều kiện nghiêm ngặt `<` và `>`.

## Phân Tích Độ Phức Tạp
- **Thời gian**: $O(n)$ trung bình (do QuickSelect và một vòng lặp phân loại).
- **Không gian**: $O(1)$ (không sử dụng mảng phụ, thực hiện thay đổi trực tiếp trên mảng gốc).

---

## Các Bước Thực Hiện Trong Code
1. `findKthLargest`: Tìm giá trị trung vị.
2. `newIndex`: Hàm ánh xạ chỉ số thực sang chỉ số ảo.
3. `while` loop: Thực hiện swap dựa trên so sánh với `median` thông qua `newIndex`.
