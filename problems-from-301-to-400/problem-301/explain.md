# Top K Frequent Elements

## Bài toán

Cho mảng số nguyên `nums` và số nguyên `k`, hãy trả về `k` phần tử xuất hiện nhiều nhất trong mảng. Thứ tự của kết quả không quan trọng.

## Cách mình đang dùng: Heap

Trong `Solution.java`, cách làm gồm 2 bước chính:

1. Đếm tần suất xuất hiện của từng số bằng `HashMap<Integer, Integer>`.
2. Duy trì một `PriorityQueue<Integer>` như một min-heap theo tần suất.

Ý tưởng của heap ở đây là chỉ giữ lại đúng `k` phần tử ứng viên tốt nhất.

### Cụ thể trong code

- `fqMap` lưu số lần xuất hiện của từng giá trị.
- `pq` được tạo với comparator:

```java
(a, b) -> fqMap.get(a) - fqMap.get(b)
```

Comparator này biến `PriorityQueue` thành min-heap theo tần suất:

- phần tử có tần suất nhỏ hơn sẽ đứng ở đầu heap
- khi heap vượt quá kích thước `k`, ta gọi `poll()` để loại phần tử có tần suất nhỏ nhất

Kết quả là sau khi duyệt xong toàn bộ các key trong `fqMap`, heap chỉ còn `k` phần tử xuất hiện nhiều nhất.

## Vì sao heap đúng?

Heap giúp ta không cần sắp xếp toàn bộ các phần tử theo tần suất. Thay vào đó:

- mỗi lần thêm một phần tử vào heap, ta chỉ giữ tối đa `k` phần tử
- nếu có phần tử mới tốt hơn phần tử đang tệ nhất trong heap, phần tử tệ nhất sẽ bị loại

Nhờ vậy, đến cuối cùng heap giữ đúng `k` phần tử có tần suất cao nhất.

## Độ phức tạp của cách heap

- Đếm tần suất: `O(n)`
- Duyệt các phần tử khác nhau và thao tác heap: `O(m log k)` với `m` là số lượng phần tử khác nhau
- Tổng thể: `O(n log k)` trong trường hợp xấu thường dùng để mô tả
- Không gian phụ: `O(n)` cho `HashMap`, và `O(k)` cho heap

## Cách khác: Bucket Sort

Vì tần suất lớn nhất của một phần tử không vượt quá `nums.length`, ta có thể dùng bucket sort.

### Ý tưởng bucket

1. Đếm tần suất của từng số bằng `HashMap`.
2. Tạo một mảng `buckets`, trong đó `buckets[f]` chứa tất cả các số xuất hiện đúng `f` lần.
3. Duyệt `buckets` từ tần suất lớn nhất xuống nhỏ nhất.
4. Lấy dần các phần tử cho đến khi đủ `k` phần tử.

### Vì sao bucket hiệu quả?

Ta không cần sort toàn bộ các phần tử. Chỉ cần gom nhóm theo tần suất rồi quét từ cao xuống thấp. Điều này giúp đạt thời gian `O(n)` trong nhiều cách cài đặt chuẩn.

### Độ phức tạp của bucket

- Đếm tần suất: `O(n)`
- Phân phối vào bucket: `O(m)`
- Quét bucket để lấy kết quả: `O(n)`
- Tổng thể: `O(n)`
- Không gian phụ: `O(n)`

## Khi nào chọn cách nào?

- Chọn heap khi muốn code gọn, dễ hiểu, và chỉ cần giữ `k` phần tử tốt nhất.
- Chọn bucket khi muốn tối ưu thời gian hơn nữa theo đúng yêu cầu follow-up.

## Tóm tắt ngắn

- Heap: đếm tần suất rồi giữ một min-heap kích thước `k`
- Bucket: đếm tần suất rồi gom phần tử vào các nhóm theo số lần xuất hiện
- Cả hai đều dùng `HashMap` để đếm trước, nhưng bucket thường nhanh hơn nếu xét độ phức tạp tổng thể
