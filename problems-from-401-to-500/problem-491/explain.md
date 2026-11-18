# 📚 Giải thích Chi tiết Bài 690 - Employee Importance

**Mục tiêu:** Cho danh sách thông tin các nhân viên (gồm mã số duy nhất `id`, giá trị độ quan trọng `importance`, và danh sách mã số cấp dưới trực tiếp `subordinates`) cùng một mã số `id` mục tiêu. Hãy tính **tổng giá trị độ quan trọng** của nhân viên `id` này và toàn bộ cấp dưới trực tiếp lẫn gián tiếp của họ.

---

## 🎯 Phân tích bài toán

### 1. Mô hình hóa bài toán dưới dạng Cây (N-ary Tree)
Đọc kỹ ràng buộc đề bài:
> *"One employee has at most one direct leader and may have several subordinates."*  
> *(Mỗi nhân viên có tối đa một quản lý trực tiếp và có thể có nhiều cấp dưới.)*

Tính chất này đảm bảo:
- Sơ đồ tổ chức là một **rừng các cây tổng quát (N-ary Trees)**, hoàn toàn **không chứa chu trình (acyclic)**.
- Không có bất kỳ nhân viên nào thuộc về hai nhánh khác nhau trong cùng một cây con.
- Do đó, khi duyệt cây từ nút gốc `id` (bằng DFS hoặc BFS), mỗi nhân viên cấp dưới chỉ được đi qua **đúng một lần duy nhất** — ta **không cần dùng mảng/tập hợp `visited`**.

---

### 2. Giải quyết nút thắt tra cứu (Lookup Bottleneck)
- Danh sách đầu vào `employees` không được sắp xếp theo `id`.
- Trường `subordinates` chỉ lưu danh sách các số nguyên `id`, không trỏ trực tiếp tới đối tượng `Employee`.
- Nếu mỗi lần muốn truy cập một cấp dưới ta lại duyệt tuyến tính qua cả danh sách `employees`, tổng thời gian thực thi sẽ lên tới $\mathcal{O}(N^2)$.
- **Giải pháp:** Xây dựng bảng tra cứu `id -> Employee` trong $\mathcal{O}(N)$ ở đầu hàm để mọi thao tác truy cập nhân viên theo `id` sau đó chỉ tốn $\mathcal{O}(1)$.

---

### 3. Ràng buộc & Tối ưu Ánh xạ trực tiếp (Direct Addressing)
- $1 \le employees.length \le 2000$.
- $1 \le employees[i].id \le 2000$ và tất cả `id` đều duy nhất.
- $-100 \le employees[i].importance \le 100$ (lưu ý: độ quan trọng có thể là số âm).
- Vì `id` nằm gọn trong đoạn $[1, 2000]$, ta có thể dùng mảng tĩnh `Employee[] empById = new Employee[2001]` thay cho `HashMap<Integer, Employee>`. Cách này loại bỏ hoàn toàn chi phí tính mã băm (hashing), boxing/unboxing `int -> Integer`, và cấp phát nút `Map.Entry` trên Heap.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Duyệt tuyến tính tìm ID | Cách 2: `HashMap` + DFS / BFS | Cách 3: Mảng ánh xạ `Employee[2001]` + DFS ⭐ |
| :--- | :--- | :--- | :--- |
| **Cấu trúc tra cứu** | Quét lại `List<Employee>` mỗi lần gọi | `HashMap<Integer, Employee>` | Mảng trực tiếp `Employee[2001]` |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ (tốc độ nhanh nhất, $\approx 1-2\text{ ms}$) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(H)$ | $\mathcal{O}(N)$ | $\mathcal{O}(2001 + H)$ |
| **Tính tổng quát** | Kém hiệu năng | **Tổng quát cho mọi miền ID bất kỳ** | **Tối ưu tối đa theo ràng buộc đề bài** |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mảng Ánh Xạ Trực Tiếp + DFS

### 💡 Ý tưởng thuật toán cốt lõi

1. **Bước 1 (Tiền xử lý - Indexing):**
   - Khởi tạo mảng `Employee[] empById = new Employee[2001]`.
   - Duyệt qua từng nhân viên `emp` trong `employees` và gán `empById[emp.id] = emp`.
2. **Bước 2 (Đệ quy tính tổng cây con - DFS):**
   - Với nhân viên có mã `id`, lấy đối tượng `emp = empById[id]`.
   - Khởi tạo `totalImportance = emp.importance`.
   - Với từng mã cấp dưới `subId` trong `emp.subordinates`, cộng dồn `totalImportance += dfs(empById, subId)`.
   - Trả về `totalImportance`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `employees = [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]]`, `id = 1`

```text
          [ID: 1 | Imp: 5]
            /          \
   [ID: 2 | Imp: 3]   [ID: 3 | Imp: 3]

Luồng gọi đệ quy DFS:
1. dfs(1):
   - total = 5
   - Gọi dfs(2):
     + total = 3 (không có cấp dưới) -> trả về 3
   - Gọi dfs(3):
     + total = 3 (không có cấp dưới) -> trả về 3
   - Tổng tại 1: 5 + 3 + 3 = 11

👉 Kết quả cuối cùng: 11.
```

#### Ví dụ 2: `employees = [[1, 2, [5]], [5, -3, []]]`, `id = 5`
- Bắt đầu trực tiếp từ nút `5` (bỏ qua quản lý `1`).
- `dfs(5)` lấy `importance = -3`, danh sách `subordinates` rỗng $\implies$ trả về **`-3`**.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Mảng Ánh Xạ Trực Tiếp + DFS ⭐ (Hiệu năng cao nhất)

```java
import java.util.List;

/*
// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
*/

class Solution {
    public int getImportance(List<Employee> employees, int id) {
        // Ánh xạ trực tiếp id -> Employee với O(1) tuyệt đối (vì 1 <= id <= 2000)
        Employee[] empById = new Employee[2001];
        for (Employee emp : employees) {
            empById[emp.id] = emp;
        }

        return dfs(empById, id);
    }

    private int dfs(Employee[] empById, int id) {
        Employee emp = empById[id];
        int totalImportance = emp.importance;

        // Cộng dồn độ quan trọng của tất cả cấp dưới trực tiếp và gián tiếp
        for (int subId : emp.subordinates) {
            totalImportance += dfs(empById, subId);
        }

        return totalImportance;
    }
}
```

---

### Cách 2 (Tham khảo tổng quát): `HashMap` + BFS (Dùng hàng đợi, không đệ quy)

```java
import java.util.*;

class SolutionBFS {
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee e : employees) {
            map.put(e.id, e);
        }

        int total = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(id);

        while (!queue.isEmpty()) {
            Employee curr = map.get(queue.poll());
            total += curr.importance;
            for (int subId : curr.subordinates) {
                queue.offer(subId);
            }
        }

        return total;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`Employee[] empById = new Employee[2001];`**:
   - Cấp phát mảng tham chiếu có chỉ số từ `0` đến `2000` (vì `1 <= id <= 2000`).
2. **`for (Employee emp : employees) empById[emp.id] = emp;`**:
   - Duyệt toàn bộ danh sách đúng 1 lần để lập chỉ mục, tốn $\mathcal{O}(N)$ thời gian.
3. **`return dfs(empById, id);`**:
   - Khởi chạy quá trình duyệt theo chiều sâu bắt đầu từ nhân viên có mã `id`.
4. **`int totalImportance = emp.importance;`**:
   - Khởi tạo tổng giá trị bằng độ quan trọng của chính nhân viên hiện tại.
5. **`for (int subId : emp.subordinates) totalImportance += dfs(empById, subId);`**:
   - Đệ quy đi xuống từng cấp dưới trực tiếp để lấy tổng độ quan trọng của cả cây con gốc `subId`, rồi cộng dồn vào `totalImportance`.
   - Khi `emp.subordinates` rỗng (nhân viên là nút lá), vòng lặp không chạy và hàm trả về ngay `emp.importance` (đóng vai trò điểm dừng đệ quy tự nhiên).

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N)$**:
  - Với $N$ là tổng số nhân viên (`employees.length`, $N \le 2000$).
  - Bước xây dựng mảng ánh xạ duyệt qua tất cả $N$ nhân viên: $\mathcal{O}(N)$.
  - Bước DFS thăm mỗi nhân viên thuộc cây con của `id` đúng một lần (tối đa $N$ nút và $N - 1$ cạnh): $\mathcal{O}(N)$.
  - Tổng thời gian: $\mathcal{O}(N)$.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N)$** (hoặc chính xác là $\mathcal{O}(M + H)$ với $M = 2001$ là kích thước mảng ánh xạ và $H \le N$ là chiều cao cây đệ quy):
  - Mảng `empById` có kích thước cố định $2001$ tham chiếu ($\approx 8-16\text{ KB}$).
  - Ngăn xếp đệ quy (call stack) sâu tối đa bằng chiều cao cây $H \le 2000$, hoàn toàn an toàn không gây `StackOverflowError`.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Chủ động trao đổi về `HashMap` vs Mảng ánh xạ trực tiếp:**
   - Trong phỏng vấn, nên trình bày ý tưởng dùng `HashMap<Integer, Employee>` trước vì tính tổng quát (hoạt động ngay cả khi ID là số âm, số lớn $10^9$ hay chuỗi UUID).
   - Sau đó, chỉ ra ràng buộc `1 <= id <= 2000` để đề xuất tối ưu sang mảng tĩnh `Employee[2001]` giúp giảm hằng số thời gian và loại bỏ autoboxing. Điều này thể hiện tư duy phân tích ràng buộc rất tốt.
2. **Câu hỏi mở rộng (Follow-up Questions):**
   - *Nếu cơ cấu công ty dạng ma trận (một nhân viên có thể có nhiều quản lý trực tiếp) thì sao?*
     - Lúc này đồ thị là một DAG (đồ thị có hướng không chu trình) hoặc đồ thị tổng quát. Ta bắt buộc phải thêm `Set<Integer> visited` để đánh dấu các nhân viên đã cộng điểm, tránh cộng trùng lặp.
   - *Nếu cây quá sâu (ví dụ $N = 10^6$ dạng đường thẳng) thì DFS đệ quy có vấn đề gì?*
     - Đệ quy có thể gây tràn ngăn xếp (`StackOverflowError`). Giải pháp thay thế là dùng **BFS với `ArrayDeque`** hoặc **DFS lặp (Iterative DFS) với `Deque`**.
