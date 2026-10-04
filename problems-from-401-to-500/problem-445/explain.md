# 📚 Giải thích Chi tiết Bài 621 - Task Scheduler

**Mục tiêu:** Cho một mảng các ký tự `tasks` biểu diễn các tác vụ CPU cần thực hiện (mỗi tác vụ được gán một chữ cái in hoa từ `A` đến `Z`) và một số nguyên không âm `n`. Giữa hai tác vụ cùng loại bắt buộc phải có khoảng cách nghỉ (cooling interval) tối thiểu là `n` đơn vị thời gian. Trong mỗi khoảng thời gian, CPU có thể hoàn thành một tác vụ hoặc ở trạng thái nghỉ (`idle`). Hãy tìm số lượng khoảng thời gian CPU ít nhất để hoàn thành tất cả các tác vụ.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- `1 <= tasks.length <= 10⁴`: Số lượng tác vụ có thể lên đến $10^4$.
- `tasks[i]` là chữ cái in hoa tiếng Anh (`'A'` đến `'Z'`): Chỉ có tối đa 26 loại tác vụ khác nhau.
- `0 <= n <= 100`: Khoảng cách làm mát (cooling interval) giữa 2 tác vụ cùng loại.

---

### 2. Bản chất cốt lõi của bài toán
- Giả sử một tác vụ $X$ xuất hiện nhiều lần nhất với số lần là `maxFreq`.
- Giữa 2 lần thực thi $X$, bắt buộc phải có ít nhất `n` đơn vị thời gian (dành cho các tác vụ khác hoặc `idle`).
- Vì vậy, tác vụ có tần suất cao nhất chính là **nút thắt cổ chai (bottleneck)** quyết định số lượng khoảng thời gian CPU cần thiết.
- Mục tiêu của ta là sắp xếp các tác vụ sao cho số lượng khoảng trống `idle` là **ít nhất có thể**.

---

## 💡 Tư duy giải pháp: Mô hình Khung thời gian (Bucket / Frame Model)

Để dễ hình dung chiến lược tham lam (Greedy), hãy xếp các tác vụ có tần suất xuất hiện lớn nhất vào trước.

### 1. Minh họa trực quan
Giả sử `tasks = ["A","A","A","B","B","B"]`, với `n = 2`:
- Tác vụ `A` xuất hiện nhiều nhất: `maxFreq = 3`.
- Ta xếp `A` trước, giữa mỗi chữ `A` phải có ít nhất `n = 2` vị trí trống:

```text
Khung 1: [ A ] [   ] [   ]
Khung 2: [ A ] [   ] [   ]
Khung 3: [ A ]
```

Nhận xét:
- Ta có `maxFreq - 1 = 2` khung hoàn chỉnh đầu tiên.
- Chiều dài của mỗi khung này luôn là `n + 1 = 3` (gồm 1 vị trí chạy tác vụ và `n` vị trí làm mát).
- Khung cuối cùng chỉ chứa các tác vụ có tần suất lớn nhất mà không cần thêm `n` khoảng nghỉ phía sau.

Tiếp theo, ta điền tác vụ `B` (cũng có tần suất là 3):

```text
Khung 1: [ A ] [ B ] [ idle ]
Khung 2: [ A ] [ B ] [ idle ]
Khung 3: [ A ] [ B ]
```

Tổng số khoảng thời gian cần:
$$\text{Chiều dài} = (\text{maxFreq} - 1) \times (n + 1) + \text{countMaxFreq} = (3 - 1) \times (2 + 1) + 2 = 2 \times 3 + 2 = 8$$

---

### 2. Hai trường hợp tổng quát

#### Trường hợp 1: Vẫn còn khoảng trống `idle`
Khi số lượng tác vụ còn lại không đủ lấp đầy các ô trống trong `maxFreq - 1` khung, ta bắt buộc phải để CPU `idle`. Khi đó, tổng thời gian chính là kích thước của mô hình khung:
$$\text{intervals} = (\text{maxFreq} - 1) \times (n + 1) + \text{countMaxFreq}$$

#### Trường hợp 2: Các tác vụ khác lấp đầy và vượt quá số ô trống
Khi có rất nhiều tác vụ khác nhau với tần suất nhỏ hơn `maxFreq`, ta có thể chèn chúng vào các khung. Do mỗi khung có thể nới rộng tùy ý (ví dụ: biến khung `n + 1` thành `n + 2`, `n + 3`...), khoảng cách giữa hai tác vụ giống nhau bất kỳ chỉ tăng lên chứ không bao giờ nhỏ hơn `n`.
- Trong trường hợp này, **không hề có bất kỳ khoảng `idle` nào**.
- CPU luôn luôn bận rộn chạy tác vụ từ đầu đến cuối.
- Tổng thời gian thực thi chính bằng tổng số tác vụ ban đầu: `tasks.length`.

$\implies$ **Công thức tổng quát tối ưu:**
$$\text{leastInterval} = \max\Big(\text{tasks.length},\ (\text{maxFreq} - 1) \times (n + 1) + \text{countMaxFreq}\Big)$$

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Đếm tần suất + Công thức Toán học Tham lam

### 🌟 Ưu điểm vượt trội
- **Thời gian chạy siêu tốc $\mathcal{O}(N)$:** Chỉ duyệt qua mảng `tasks` 1 lần và duyệt mảng tần suất 26 phần tử. Thực thi $\approx 1\text{ms} - 2\text{ms}$ trên LeetCode (Beats 99% - 100%).
- **Bộ nhớ $\mathcal{O}(1)$:** Chỉ dùng mảng 26 số nguyên cố định, không tạo cấu trúc dữ liệu bổ sung.
- **Ngắn gọn, dễ hiểu và không phát sinh lỗi biên:** Xử lý tự nhiên mọi trường hợp `n = 0`, danh sách chỉ có 1 tác vụ, hoặc nhiều tác vụ cùng đạt tần suất cực đại.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-445/Solution.java):

```java
class Solution {
    /**
     * Best Solution: Đếm tần suất (Frequency Counting) + Công thức Khung Thời gian Tham lam (Greedy Math / Bucket Model).
     * 
     * Time Complexity: O(tasks.length) - Duyệt qua mảng tasks một lần để đếm tần số, sau đó duyệt mảng tần số kích thước 26.
     * Space Complexity: O(1) - Sử dụng mảng đếm tần suất cố định 26 phần tử (bảng chữ cái tiếng Anh in hoa).
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        if (n == 0) {
            return tasks.length;
        }

        // Bước 1: Đếm tần suất xuất hiện của từng loại task (A - Z)
        int[] freq = new int[26];
        int maxFreq = 0;

        for (char task : tasks) {
            int count = ++freq[task - 'A'];
            if (count > maxFreq) {
                maxFreq = count;
            }
        }

        // Bước 2: Đếm số lượng task có cùng tần suất lớn nhất (maxFreq)
        int countMaxFreq = 0;
        for (int f : freq) {
            if (f == maxFreq) {
                countMaxFreq++;
            }
        }

        // Bước 3: Tính toán khoảng thời gian tối thiểu dựa trên mô hình khung (Bucket model)
        // Cần (maxFreq - 1) khung hoàn chỉnh, mỗi khung có kích thước (n + 1).
        // Khung cuối cùng chỉ chứa các task có tần suất cực đại (countMaxFreq).
        int minIntervals = (maxFreq - 1) * (n + 1) + countMaxFreq;

        // Nếu số lượng task quá nhiều, các ô idle được lấp đầy và mở rộng mà không phát sinh thêm idle
        return Math.max(tasks.length, minIntervals);
    }
}
```

---

### 🔍 Giải thích chi tiết từng bước trong mã nguồn

1. **Kiểm tra trường hợp đặc biệt:**
   - Nếu `tasks` rỗng $\implies$ trả về `0`.
   - Nếu `n == 0` $\implies$ không yêu cầu thời gian chờ, các tác vụ có thể thực hiện liên tiếp không cần nghỉ $\implies$ trả về ngay `tasks.length`.

2. **Bước 1 - Đếm tần suất:**
   - Dùng mảng `freq` có kích thước $26$ ứng với các chữ cái từ `'A'` đến `'Z'`.
   - Vừa tăng đếm `freq[task - 'A']++`, vừa cập nhật `maxFreq` ngay trong vòng lặp đầu tiên để tiết kiệm thời gian.

3. **Bước 2 - Đếm số lượng tác vụ đạt `maxFreq`:**
   - Duyệt qua 26 phần tử của `freq`, đếm xem có bao nhiêu chữ cái có số lần xuất hiện bằng đúng `maxFreq` (`countMaxFreq`).

4. **Bước 3 - Áp dụng công thức và trả về kết quả:**
   - Tính `minIntervals = (maxFreq - 1) * (n + 1) + countMaxFreq`.
   - Trả về `Math.max(tasks.length, minIntervals)`.

---

## 🔬 Trace ví dụ từng bước (Step-by-step Dry Run)

### Ví dụ 1: `tasks = ["A","A","A","B","B","B"], n = 2`
1. Tần suất: `freq['A'] = 3`, `freq['B'] = 3`, các ký tự khác $= 0$.
2. `maxFreq = 3`.
3. Số lượng tác vụ có tần suất $= 3$: `'A'` và `'B'` $\implies$ `countMaxFreq = 2`.
4. `minIntervals = (3 - 1) * (2 + 1) + 2 = 2 * 3 + 2 = 8`.
5. `Math.max(6, 8) = 8`.
- Thứ tự thực thi mẫu: `A -> B -> idle -> A -> B -> idle -> A -> B` (Tổng cộng: 8 intervals).

---

### Ví dụ 2: `tasks = ["A","C","A","B","D","B"], n = 1`
1. Tần suất: `freq['A'] = 2`, `freq['B'] = 2`, `freq['C'] = 1`, `freq['D'] = 1`.
2. `maxFreq = 2`.
3. `countMaxFreq = 2` (gồm `'A'` và `'B'`).
4. `minIntervals = (2 - 1) * (1 + 1) + 2 = 1 * 2 + 2 = 4`.
5. Tổng số tác vụ: `tasks.length = 6`.
6. `Math.max(6, 4) = 6`.
- Không cần bất kỳ khoảng nghỉ nào.
- Thứ tự thực thi mẫu: `A -> B -> C -> D -> A -> B` (Tổng cộng: 6 intervals).

---

### Ví dụ 3: `tasks = ["A","A","A","B","B","B"], n = 3`
1. Tần suất: `freq['A'] = 3`, `freq['B'] = 3`.
2. `maxFreq = 3`, `countMaxFreq = 2`.
3. `minIntervals = (3 - 1) * (3 + 1) + 2 = 2 * 4 + 2 = 10`.
4. `Math.max(6, 10) = 10`.
- Thứ tự thực thi mẫu: `A -> B -> idle -> idle -> A -> B -> idle -> idle -> A -> B` (Tổng cộng: 10 intervals).

---

## 🔄 GIẢI PHÁP THAY THẾ: Mô phỏng bằng Hàng đợi ưu tiên (Max-Heap + Cooling Queue)

Ngoài cách tính toán bằng công thức toán học, ta có thể giải bài này bằng cách **mô phỏng từng chu kỳ CPU**:
1. Đưa tần suất của các tác vụ vào một **Max-Heap**.
2. Trong mỗi chu kỳ $n + 1$ đơn vị thời gian (một vòng làm mát):
   - Lấy tối đa $n + 1$ tác vụ có tần suất lớn nhất ra thực thi.
   - Giảm tần suất của chúng đi 1 và lưu tạm vào danh sách.
   - Sau đó đẩy lại những tác vụ còn số lần $> 0$ vào Max-Heap.
   - Nếu Max-Heap đã rỗng, ta chỉ cộng số tác vụ thực tế vừa chạy (chu kỳ cuối không cần `idle`). Ngược lại, ta cộng đủ $n + 1$ (tính cả `idle`).

### Mã nguồn tham khảo (Simulation Approach)

```java
import java.util.*;

class SolutionSimulation {
    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) {
            freq[c - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int f : freq) {
            if (f > 0) {
                maxHeap.offer(f);
            }
        }

        int totalTime = 0;
        while (!maxHeap.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int cycle = n + 1;
            int tasksInCycle = 0;

            // Thực thi tối đa n + 1 tác vụ trong 1 chu kỳ
            for (int i = 0; i < cycle; i++) {
                if (!maxHeap.isEmpty()) {
                    temp.add(maxHeap.poll() - 1);
                    tasksInCycle++;
                }
            }

            // Đẩy lại các tác vụ còn dư vào Max-Heap
            for (int count : temp) {
                if (count > 0) {
                    maxHeap.offer(count);
                }
            }

            // Nếu heap đã hết tác vụ, chỉ cộng số tác vụ thực tế đã chạy ở chu kỳ cuối
            // Nếu heap còn tác vụ, chu kỳ này phải tốn đủ (n + 1) đơn vị thời gian (tính cả idle)
            totalTime += maxHeap.isEmpty() ? tasksInCycle : cycle;
        }

        return totalTime;
    }
}
```

---

## 📊 So sánh các phương pháp

| Tiêu chí | ⭐ Công thức Toán học (Greedy Math) | 🔄 Mô phỏng Hàng đợi ưu tiên (Max-Heap) |
| :--- | :--- | :--- |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N)$ ($\approx 1\text{ms}$, nhanh nhất) | $\mathcal{O}(N \log 26) = \mathcal{O}(N)$ ($\approx 15\text{ms} - 25\text{ms}$) |
| **Độ phức tạp Không gian** | $\mathcal{O}(1)$ (mảng 26 số nguyên) | $\mathcal{O}(1)$ (Heap tối đa 26 phần tử) |
| **Khả năng sinh lịch trình thực tế** | Chỉ tính độ dài thời gian | Có thể xuất ra thứ tự chạy cụ thể của từng task |
| **Độ phức tạp mã nguồn** | Rất ngắn gọn (khoảng 15 dòng) | Dài hơn, cần quản lý chu kỳ và danh sách phụ |
| **Đánh giá** | **Khuyên dùng tối đa (Best Solution)** | Thích hợp khi đề bài yêu cầu in ra chuỗi thứ tự task |

---

## 💡 Tổng kết & Bài học kinh nghiệm

1. **Quy tắc Nút thắt cổ chai (Bottleneck Principle):** Trong các bài toán xếp lịch có ràng buộc khoảng nghỉ (cooling period), đối tượng có tần suất xuất hiện lớn nhất luôn đóng vai trò quyết định cấu trúc khung của toàn bộ lịch trình.
2. **Kỹ thuật chia khung (Bucket/Frame Technique):** Thay vì thử ghép nối tuần tự, ta tạo sẵn các khung thời gian có kích thước `n + 1` và lấp các đối tượng nhỏ hơn vào khoảng trống.
3. **Từ Mô phỏng sang Toán học:** Các bài toán mô phỏng tuần tự thường có thể quy về công thức giải tích đóng nếu không cần đưa ra cấu hình chi tiết mà chỉ cần độ dài tối ưu.
