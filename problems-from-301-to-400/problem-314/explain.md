# Giải thích chi tiết: 380. Insert Delete GetRandom O(1)

**Độ khó:** Medium (Trung bình)

---

## 1. Phân Tích Bài Toán & Ý Tưởng Cốt Lõi

Yêu cầu cốt lõi của bài toán là thiết kế một cấu trúc dữ liệu hỗ trợ 3 thao tác: `insert`, `remove`, và `getRandom` đều có độ phức tạp thời gian trung bình là **$O(1)$**.

Hãy cùng phân tích tại sao các cấu trúc dữ liệu đơn lẻ quen thuộc không thể đáp ứng đồng thời cả 3 yêu cầu trên:

*   **Mảng động (ArrayList / Vector):**
    *   `insert(val)`: Thêm vào cuối mảng tốn $O(1)$ amortized (trung bình).
    *   `getRandom()`: Chọn ngẫu nhiên một chỉ số từ `0` đến `size - 1` tốn $O(1)$.
    *   `remove(val)`: **Thất bại!** Để xóa một phần tử bất kỳ theo giá trị, chúng ta cần tìm kiếm nó trong mảng (mất $O(N)$) rồi dịch chuyển các phần tử phía sau để lấp đầy khoảng trống (mất $O(N)$).
*   **Bảng băm (HashSet / HashMap):**
    *   `insert(val)`: Thêm vào bảng băm tốn $O(1)$.
    *   `remove(val)`: Xóa khỏi bảng băm tốn $O(1)$.
    *   `getRandom()`: **Thất bại!** Các cấu trúc bảng băm không hỗ trợ truy xuất phần tử theo chỉ mục (index) tuyến tính liên tục. Do đó, ta không thể chọn ngẫu nhiên một phần tử với xác suất đồng đều trong $O(1)$ được.

### Giải pháp kết hợp: HashMap + ArrayList

Để có được thế mạnh của cả hai, ta kết hợp chúng lại:
1.  **Một danh sách liên kết / Mảng động (`ArrayList`):** Lưu giá trị thực tế của các phần tử. Giúp thao tác truy xuất ngẫu nhiên `getRandom` diễn ra trong $O(1)$ bằng cách sinh chỉ số ngẫu nhiên.
2.  **Một bảng băm (`HashMap`):** Lưu trữ ánh xạ từ **`Giá trị -> Chỉ số của nó trong ArrayList`**. Giúp thao tác tìm kiếm vị trí của một phần tử để xóa diễn ra trong $O(1)$.

---

## 2. Chi Tiết Thuật Toán

### A. Thao tác `insert(val)`
1.  Kiểm tra xem `val` đã tồn tại trong `HashMap` chưa. Nếu đã tồn tại, trả về `false`.
2.  Nếu chưa tồn tại:
    *   Lưu ánh xạ `val -> list.size()` vào `HashMap` (chỉ số của nó sẽ là vị trí cuối cùng trong danh sách).
    *   Thêm `val` vào cuối `ArrayList`.
    *   Trả về `true`.

### B. Thao tác `remove(val)` (Kỹ thuật "Swap with Last Element")
Xóa một phần tử ở giữa mảng sẽ mất $O(N)$ do phải dịch chuyển các phần tử. Nhưng **xóa phần tử ở cuối mảng luôn chỉ tốn $O(1)$**. 
Do đó, chúng ta sẽ làm như sau:
1.  Kiểm tra xem `val` có trong `HashMap` không. Nếu không có, trả về `false`.
2.  Lấy chỉ số của `val` cần xóa trong mảng từ bảng băm, gọi là `index`.
3.  Lấy giá trị của phần tử cuối cùng hiện tại trong mảng, gọi là `lastVal`.
4.  **Hoán đổi vị trí:**
    *   Đè giá trị `lastVal` lên vị trí `index` (vị trí cũ của `val`).
    *   Cập nhật lại chỉ số mới của `lastVal` trong `HashMap` thành `index`.
5.  **Xóa phần tử cuối cùng:**
    *   Xóa phần tử ở cuối mảng `ArrayList` (bằng phương thức `list.remove(list.size() - 1)` - tốn $O(1)$).
    *   Xóa `val` khỏi `HashMap`.
6.  Trả về `true`.

> [!NOTE]
> Kỹ thuật này hoạt động hoàn toàn chính xác ngay cả khi phần tử cần xóa chính là phần tử cuối cùng của mảng. Việc ghi đè và cập nhật chỉ số của phần tử cuối cùng vẫn hợp lệ trước khi chúng ta thực hiện thao tác xóa thực sự ở cuối mảng và xóa khóa đó trong HashMap.

### C. Thao tác `getRandom()`
1.  Sử dụng bộ sinh số ngẫu nhiên `Random` để sinh một số nguyên ngẫu nhiên `i` trong khoảng `[0, list.size() - 1]`.
2.  Trả về phần tử tại `list.get(i)` trong $O(1)$.

---

## 3. Dry Run Minh Họa (Chạy Từng Bước)

Giả sử ta thực hiện chuỗi thao tác sau:

| Thao tác | Trạng thái ArrayList | Trạng thái HashMap | Giải thích chi tiết |
| :--- | :--- | :--- | :--- |
| **Khởi tạo** | `[]` | `{}` | Khởi tạo mảng và bảng băm rỗng. |
| `insert(1)` | `[1]` | `{1: 0}` | `1` chưa tồn tại. Thêm `1` vào cuối mảng (chỉ số 0), đưa `{1: 0}` vào map. Trả về `true`. |
| `remove(2)` | `[1]` | `{1: 0}` | `2` không có trong map. Trả về `false`. |
| `insert(2)` | `[1, 2]` | `{1: 0, 2: 1}` | `2` chưa có. Thêm `2` vào cuối mảng (chỉ số 1), đưa `{2: 1}` vào map. Trả về `true`. |
| `getRandom()` | `[1, 2]` | `{1: 0, 2: 1}` | Chọn ngẫu nhiên index `0` hoặc `1`. Trả về `1` hoặc `2` với xác suất 50%. |
| `remove(1)` | `[2]` | `{2: 0}` | `1` có index là `0`. Lấy phần tử cuối là `2`. Ghi đè `2` vào vị trí của `1` (ArrayList trở thành `[2, 2]`). Cập nhật vị trí `2` trong map thành `0`. Xóa phần tử cuối (ArrayList còn `[2]`), xóa `1` khỏi map. Trả về `true`. |
| `insert(2)` | `[2]` | `{2: 0}` | `2` đã tồn tại trong map. Trả về `false`. |
| `getRandom()` | `[2]` | `{2: 0}` | Chỉ số duy nhất là `0`. Luôn trả về `2` với xác suất 100%. |

---

## 4. Mã Nguồn Java Chi Tiết

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class RandomizedSet {
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random rand;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        rand = new Random();
    }
    
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        // Thêm giá trị vào bảng băm kèm theo index trong ArrayList
        map.put(val, list.size());
        // Thêm vào cuối ArrayList (tốn O(1))
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        
        // Lấy vị trí của val cần xóa
        int index = map.get(val);
        // Lấy giá trị của phần tử nằm cuối danh sách
        int lastVal = list.get(list.size() - 1);
        
        // Đưa phần tử cuối danh sách vào vị trí của phần tử cần xóa
        list.set(index, lastVal);
        // Cập nhật lại chỉ số mới của lastVal trong HashMap
        map.put(lastVal, index);
        
        // Xóa phần tử ở cuối danh sách (tốn O(1) vì không phải dịch chuyển)
        list.remove(list.size() - 1);
        // Xóa val khỏi HashMap
        map.remove(val);
        
        return true;
    }
    
    public int getRandom() {
        // Sinh chỉ số ngẫu nhiên từ 0 đến list.size() - 1
        int randomIndex = rand.nextInt(list.size());
        // Trả về phần tử tương ứng
        return list.get(randomIndex);
    }
}
```

---

## 5. Phân Tích Độ Phức Tạp

*   **Độ phức tạp thời gian (Time Complexity):**
    *   `insert`: **$O(1)$** trung bình. Thao tác `containsKey` và `put` trên HashMap tốn $O(1)$. Thao tác `add` vào cuối ArrayList tốn $O(1)$ amortized.
    *   `remove`: **$O(1)$** trung bình. Việc tra cứu chỉ mục từ map tốn $O(1)$. Nhờ kỹ thuật "Swap with Last", ta ghi đè vị trí chỉ tốn $O(1)$, và xóa phần tử ở cuối ArrayList cũng tốn $O(1)$.
    *   `getRandom`: **$O(1)$**. Sinh chỉ số ngẫu nhiên bằng `rand.nextInt()` tốn $O(1)$ và truy xuất từ ArrayList theo chỉ mục tốn $O(1)$.
*   **Độ phức tạp không gian (Space Complexity):** **$O(N)$**, với $N$ là số lượng phần tử duy nhất được lưu trữ tại một thời điểm trong `RandomizedSet`. Chúng ta cần duy trì `ArrayList` chứa tối đa $N$ phần tử và `HashMap` lưu tối đa $N$ khóa-giá trị.