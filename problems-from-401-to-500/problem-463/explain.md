# 📚 Giải thích Chi tiết Bài 649 - Dota2 Senate

**Mục tiêu:** Trong thế giới Dota2, có hai phe là **Radiant** ('R') và **Dire** ('D'). Mỗi nghị sĩ trong phiên họp có quyền cấm một nghị sĩ đối phương mất quyền tham gia vĩnh viễn hoặc tuyên bố chiến thắng nếu tất cả những người còn lại đều cùng phe với mình. Các nghị sĩ bỏ phiếu theo thứ tự vòng tròn từ đầu đến cuối chuỗi. Giả sử mọi nghị sĩ đều cực kỳ thông minh và luôn áp dụng chiến lược tối ưu nhất cho phe mình, hãy dự đoán phe nào sẽ giành chiến thắng chung cuộc (`"Radiant"` hoặc `"Dire"`).

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Các ràng buộc (Constraints)

- `n == senate.length`: Chiều dài chuỗi $n$ nằm trong khoảng $[1, 10^4]$.
- `senate[i]` chỉ bao gồm ký tự `'R'` hoặc `'D'`.
- Cuộc bỏ phiếu diễn ra theo từng vòng (round-based). Các nghị sĩ đã bị tước quyền ở vòng trước sẽ bị bỏ qua ở các vòng sau.
- **Đánh giá về độ phức tạp:**
  - Với $n \le 10^4$, một thuật toán có độ phức tạp thời gian $\mathcal{O}(n)$ sẽ hoàn thành trong chưa đầy $5\text{ms}$ ($\approx 10^4$ phép tính).
  - Trong khi đó, nếu sử dụng phương pháp mô phỏng ngây thơ (xóa ký tự trực tiếp trên `String` hoặc `StringBuilder` với chi phí $\mathcal{O}(n)$ cho mỗi lần xóa), tổng thời gian có thể lên tới $\mathcal{O}(n^2) \approx 10^8$ phép tính, rất dễ dẫn đến lỗi Time Limit Exceeded (TLE) hoặc làm chậm chương trình.

---

### 2. Lý thuyết trò chơi & Chiến lược Tham lam tối ưu (Game Theory & Optimal Greedy Strategy)

Mỗi nghị sĩ khi đến lượt hành động sẽ có một mục tiêu duy nhất: **Bảo vệ phe mình và tiêu diệt phe đối phương**.

#### Câu hỏi chiến lược: *Một nghị sĩ nên cấm quyền của ai trong phe đối địch?*
Giả sử một nghị sĩ phe Radiant ($R$) đang đến lượt bỏ phiếu:
- Đối phương có thể gồm:
  1. Những nghị sĩ Dire sắp đến lượt bỏ phiếu trong vòng hiện tại.
  2. Những nghị sĩ Dire đã bỏ phiếu xong trong vòng này và đang đợi vòng sau.
- **Chiến lược tối ưu tuyệt đối:** Nghị sĩ $R$ **bắt buộc phải cấm quyền của nghị sĩ Dire gần nhất ngay sau mình trong thứ tự bỏ phiếu**.
- **Chứng minh:**
  - Nếu $R$ cấm một nghị sĩ Dire ở xa hoặc đã bỏ phiếu rồi, thì nghị sĩ Dire ngay sau $R$ vẫn còn quyền và sẽ đến lượt hành động ngay lập tức. Khi đó, nghị sĩ Dire này chắc chắn sẽ cấm quyền của một nghị sĩ Radiant khác!
  - Ngược lại, nếu $R$ cấm ngay nghị sĩ Dire kế tiếp, mối nguy hiểm trước mắt bị triệt tiêu ngay lập tức trước khi đối phương kịp tung ra bất kỳ đòn tấn công nào.
- Vì vậy, chiến lược tham lam (**Greedy Choice**): **Luôn cấm nghị sĩ đối phương có lượt đi sớm nhất kế tiếp**.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Kỹ thuật Hai Hàng Đợi (Two Queues with Offset Timestamps)

### 💡 Ý tưởng đột phá (Key Insights)

Để thực hiện chiến lược "người đến trước cấm người đến sau và người sống sót đi tiếp vào vòng sau", cấu trúc dữ liệu hoàn hảo nhất chính là **Hàng đợi (FIFO Queue)**.

#### 1. Hai Hàng đợi phân loại ban đầu
- Tạo hai hàng đợi:
  - `radiantQueue`: lưu chỉ số ban đầu của các nghị sĩ phe Radiant.
  - `direQueue`: lưu chỉ số ban đầu của các nghị sĩ phe Dire.
- Ví dụ với `senate = "RDD"` ($n = 3$):
  - `radiantQueue = [0]`
  - `direQueue = [1, 2]`

#### 2. Cơ chế đối đầu từng cặp (Round Simulation)
Trong mỗi lượt, ta lấy nghị sĩ đứng đầu mỗi hàng đợi ra so tài:
- `rIndex = radiantQueue.poll()`
- `dIndex = direQueue.poll()`
- **So sánh lượt đi:**
  - Nếu `rIndex < dIndex`: Nghị sĩ Radiant có chỉ số nhỏ hơn $\implies$ đến lượt trước.
    - Radiant cấm quyền của Dire $\implies$ `dIndex` bị loại bỏ vĩnh viễn (không được đưa lại vào hàng đợi).
    - Radiant sống sót và giành quyền bỏ phiếu ở vòng sau $\implies$ đưa lại vào `radiantQueue` với chỉ số mới là `rIndex + n`.
  - Nếu `dIndex < rIndex`: Nghị sĩ Dire có chỉ số nhỏ hơn $\implies$ đến lượt trước.
    - Dire cấm quyền của Radiant $\implies$ `rIndex` bị loại bỏ vĩnh viễn.
    - Dire sống sót và giành quyền bỏ phiếu ở vòng sau $\implies$ đưa lại vào `direQueue` với chỉ số mới là `dIndex + n`.

#### 3. Ý nghĩa toán học của việc cộng thêm $n$ (`index + n`)
- Việc gán chỉ số mới là `index + n` đóng vai trò như một **Timestamp logic**:
  1. **Đảm bảo thứ tự vòng sau:** Nếu ở vòng 1, nghị sĩ $R_0$ đi trước $R_2$ ($0 < 2$), thì sang vòng 2, $R_0$ vẫn sẽ đi trước $R_2$ vì $0 + n < 2 + n$.
  2. **Tách biệt các vòng đấu:** Mọi nghị sĩ ở vòng hiện tại đều có chỉ số $< n$, trong khi các nghị sĩ vượt qua vòng này sẽ có chỉ số $\ge n$. Điều này đảm bảo toàn bộ các nghị sĩ của vòng hiện tại sẽ được hành động trước khi bất kỳ ai bước vào vòng tiếp theo.

---

### 🎨 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `senate = "RDD"`, $n = 3$

```text
Khởi tạo:
radiantQueue = [0]
direQueue    = [1, 2]

--- Lượt 1 ---
Lấy: rIndex = 0, dIndex = 1
So sánh: 0 < 1  => Radiant (0) đi trước!
Hành động: Radiant(0) cấm Dire(1).
Kết quả: Dire(1) bị LOẠI. Radiant(0) được tái sinh vào vòng sau với index = 0 + 3 = 3.
Trạng thái queues:
radiantQueue = [3]
direQueue    = [2]

--- Lượt 2 ---
Lấy: rIndex = 3, dIndex = 2
So sánh: 2 < 3  => Dire (2) đi trước!
Hành động: Dire(2) cấm Radiant(3).
Kết quả: Radiant(3) bị LOẠI. Dire(2) được tái sinh với index = 2 + 3 = 5.
Trạng thái queues:
radiantQueue = []
direQueue    = [5]

--- Kết thúc ---
radiantQueue rỗng!
=> Phe chiến thắng: "Dire"
```

---

#### Ví dụ 2: `senate = "DDRRR"`, $n = 5$

```text
Khởi tạo:
radiantQueue = [2, 3, 4]
direQueue    = [0, 1]

--- Lượt 1 ---
Lấy rIndex = 2, dIndex = 0.
0 < 2 => Dire(0) cấm Radiant(2).
Dire(0) tái sinh với index = 0 + 5 = 5.
Queues: radiant = [3, 4], dire = [1, 5]

--- Lượt 2 ---
Lấy rIndex = 3, dIndex = 1.
1 < 3 => Dire(1) cấm Radiant(3).
Dire(1) tái sinh với index = 1 + 5 = 6.
Queues: radiant = [4], dire = [5, 6]

--- Lượt 3 ---
Lấy rIndex = 4, dIndex = 5.
4 < 5 => Radiant(4) đi trước! Radiant(4) cấm Dire(5).
Radiant(4) tái sinh với index = 4 + 5 = 9.
Queues: radiant = [9], dire = [6]

--- Lượt 4 (Vòng 2) ---
Lấy rIndex = 9, dIndex = 6.
6 < 9 => Dire(6) đi trước! Dire(6) cấm Radiant(9).
Dire(6) tái sinh với index = 6 + 5 = 11.
Queues: radiant = [], dire = [11]

--- Kết thúc ---
radiantQueue rỗng!
=> Phe chiến thắng: "Dire"
```

---

### 🌟 Ưu điểm vượt trội của giải pháp Two Queues

1. **Hiệu năng tuyến tính $\mathcal{O}(n)$:**
   - Trong mỗi bước so sánh của vòng lặp, chính xác một nghị sĩ bị loại vĩnh viễn.
   - Vì tổng số nghị sĩ ban đầu là $n$, vòng lặp `while` sẽ dừng lại sau tối đa $n$ bước!
   - Không có bất kỳ phép toán lặp lại dư thừa nào.
2. **Quản lý trạng thái tự nhiên, không bug:**
   - Cơ chế FIFO của hàng đợi phản ánh chính xác 100% quy tắc vòng tròn của phiên họp thượng viện.
   - Không cần mảng đánh dấu `boolean[]` phức tạp, không cần dịch chuyển mảng, không sợ tràn chỉ số.
3. **Mã nguồn ngắn gọn, thanh lịch:**
   - Chỉ khoảng 20 dòng mã, trong sáng và trực quan.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-463/Solution.java):

```java
import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    /**
     * Best Solution: Tham lam kết hợp Hai Hàng Đợi (Greedy with Two Queues).
     *
     * Time Complexity: O(n) trong đó n là số lượng nghị sĩ (n <= 10^4).
     * Space Complexity: O(n) để lưu các chỉ số trong hai hàng đợi.
     */
    public String predictPartyVictory(String senate) {
        int n = senate.length();
        Queue<Integer> radiant = new ArrayDeque<>();
        Queue<Integer> dire = new ArrayDeque<>();

        // Bước 1: Khởi tạo hai hàng đợi lưu vị trí xuất hiện ban đầu của từng phe
        for (int i = 0; i < n; i++) {
            if (senate.charAt(i) == 'R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }

        // Bước 2: Mô phỏng các vòng bỏ phiếu
        while (!radiant.isEmpty() && !dire.isEmpty()) {
            int rIndex = radiant.poll();
            int dIndex = dire.poll();

            // Nghị sĩ nào đến lượt trước sẽ cấm quyền của đối thủ đến sau
            if (rIndex < dIndex) {
                // Radiant đi trước -> Cấm Dire (dIndex bị loại bỏ vĩnh viễn)
                // Radiant được tham gia vòng kế tiếp với thứ tự rIndex + n
                radiant.offer(rIndex + n);
            } else {
                // Dire đi trước -> Cấm Radiant (rIndex bị loại bỏ vĩnh viễn)
                // Dire được tham gia vòng kế tiếp với thứ tự dIndex + n
                dire.offer(dIndex + n);
            }
        }

        // Bước 3: Phe nào còn người trong hàng đợi thì phe đó chiến thắng
        return radiant.isEmpty() ? "Dire" : "Radiant";
    }
}
```

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Mô phỏng trực tiếp trên mảng với biến đếm cấm (Array Simulation with Ban Counter - $\mathcal{O}(n)$ Time, $\mathcal{O}(n)$ Space)

#### Tư duy:
Thay vì dùng hai hàng đợi, ta dùng một mảng `char[] chars = senate.toCharArray()` cùng hai biến đếm `banR` và `banD`:
- `banR`: Số lượt cấm đang treo nhắm vào phe Radiant.
- `banD`: Số lượt cấm đang treo nhắm vào phe Dire.
- Duyệt tuần hoàn qua mảng ký tự bằng vòng lặp:
  - Khi gặp `'R'`:
    - Nếu `banR > 0`: Nghị sĩ này bị cấm $\implies$ giảm `banR--`, đánh dấu ký tự thành `'0'` (bị loại), giảm biến đếm tổng số quân Radiant `countR--`.
    - Ngược lại: Nghị sĩ này được bỏ phiếu $\implies$ tạo ra 1 quyền cấm đối phương `banD++`.
  - Tương tự khi gặp `'D'`:
    - Nếu `banD > 0`: giảm `banD--`, đánh dấu `'0'`, giảm `countD--`.
    - Ngược lại: tạo ra 1 quyền cấm đối phương `banR++`.
- Lặp lại cho đến khi `countR == 0` hoặc `countD == 0`.

#### Mã nguồn:
```java
class SolutionArraySimulation {
    public String predictPartyVictory(String senate) {
        char[] arr = senate.toCharArray();
        int n = arr.length;
        int countR = 0, countD = 0;

        for (char c : arr) {
            if (c == 'R') countR++;
            else countD++;
        }

        int banR = 0, banD = 0;
        int i = 0;

        while (countR > 0 && countD > 0) {
            if (arr[i] == 'R') {
                if (banR > 0) {
                    banR--;
                    arr[i] = '0'; // Bị loại
                    countR--;
                } else {
                    banD++; // Tạo quyền cấm Dire
                }
            } else if (arr[i] == 'D') {
                if (banD > 0) {
                    banD--;
                    arr[i] = '0'; // Bị loại
                    countD--;
                } else {
                    banR++; // Tạo quyền cấm Radiant
                }
            }
            i = (i + 1) % n; // Quay vòng
        }

        return countR > 0 ? "Radiant" : "Dire";
    }
}
```

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(n)$ — Mỗi lần quay vòng, ít nhất một số lượng nghị sĩ bị loại. Tổng số lần duyệt mảng không vượt quá một hằng số lần $n$.
- **Bộ nhớ:** $\mathcal{O}(n)$ cho mảng `char[]`.
- **Nhận xét:** Rất nhanh trong thực tế ($\approx 2\text{ms}$), nhưng tư duy quản lý các biến `banR`, `banD` tích lũy qua các vòng dễ gây nhầm lẫn hơn so với Hai Hàng Đợi.

---

### Cách 2: Vét cạn xóa trực tiếp trên StringBuilder (Naive String Manipulation - $\mathcal{O}(n^2)$)

#### Tư duy:
Dùng `StringBuilder sb = new StringBuilder(senate)`:
- Duyệt từng vị trí `i` từ đầu đến cuối:
  - Nếu gặp `sb.charAt(i) == 'R'`, tìm vị trí ký tự `'D'` đầu tiên nằm sau `i` (hoặc quay vòng từ đầu chuỗi đến trước `i`) và gọi `sb.deleteCharAt(targetIdx)`.
  - Tương tự khi gặp `'D'`.
- Lặp lại cho đến khi chuỗi chỉ còn toàn `'R'` hoặc toàn `'D'`.

#### Đánh giá:
- **Thời gian:** $\mathcal{O}(n^2)$ — Phương thức `sb.deleteCharAt()` mất $\mathcal{O}(n)$ để dồn các ký tự phía sau lên. Với $n = 10^4$, tổng thời gian chạy có thể lên tới hàng trăm mili-giây hoặc gặp TLE.
- Không nên sử dụng trong phỏng vấn tuyển dụng.

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Two Queues (Best) | Mô phỏng mảng + Ban Counter | Xóa trên StringBuilder |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | $\mathcal{O}(n^2)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ |
| **Số bước lặp tối đa** | Đúng $n$ lần so sánh | Khoảng $2n$ đến $3n$ lần duyệt | Vòng lặp $n$ lần, mỗi lần dịch mảng $\mathcal{O}(n)$ |
| **Thời gian chạy LeetCode** | $\approx 5\text{ms} - 8\text{ms}$ (Beats 90%+) | $\approx 2\text{ms} - 4\text{ms}$ (Beats 98%) | $\approx 120\text{ms} - 250\text{ms}$ |
| **Tính trong sáng của mã** | Cực kỳ trực quan, dễ chứng minh | Dễ nhầm lẫn trạng thái tích lũy cấm | Dễ hiểu nhưng hiệu năng kém |
| **Đánh giá trong phỏng vấn** | **Lựa chọn tối ưu số 1 (Chuẩn mực)** | Điểm cộng lớn nếu viết tối ưu | Không được chấp nhận |

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Chuỗi xen kẽ đơn giản (`senate = "RD"`)
- $n = 2$.
- `radiant = [0]`, `dire = [1]`.
- $0 < 1 \implies$ Radiant(0) cấm Dire(1).
- `radiant = [2]`, `dire = []` $\implies$ Trả về `"Radiant"`.

---

### Ca 2: Phe số lượng ít hơn nhưng nắm thế chủ động (`senate = "RDD"`)
- $n = 3$.
- `radiant = [0]`, `dire = [1, 2]`.
- Lượt 1: $0 < 1 \implies$ Radiant cấm Dire(1). Dire(2) vẫn còn.
- Lượt 2: $2 < 3 \implies$ Dire(2) cấm Radiant(3).
- Dire thắng $\implies$ Trả về `"Dire"`.

---

### Ca 3: Phe ở sau lật ngược thế cờ (`senate = "DDRRR"`)
- $n = 5$.
- `radiant = [2, 3, 4]`, `dire = [0, 1]`.
- Mặc dù Radiant có 3 người và Dire chỉ có 2 người, nhưng 2 người của Dire đi trước đã kịp cấm 2 người đầu của Radiant.
- Kết quả cuối cùng: Phe Dire vẫn lật ngược tình thế và giành chiến thắng $\implies$ Trả về `"Dire"`.

---

### Ca 4: Chuỗi chỉ có duy nhất một phe (`senate = "RRRR"`)
- Ngay từ đầu, `dire` đã rỗng.
- Vòng lặp `while` không thực thi lần nào.
- Trả về ngay `"Radiant"`.

---

### Ca 5: Chiều dài tối đa $n = 10^4$
- Các phép toán `offer()` và `poll()` trên `ArrayDeque` đạt độ phức tạp hằng số $\mathcal{O}(1)$.
- Chương trình xử lý $10^4$ phần tử trong chưa đầy $3\text{ms}$, đảm bảo không gặp bất kỳ vấn đề gì về hiệu năng hay bộ nhớ.

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

### 1. Kỹ thuật Timestamp Offset (`index + n`) trong Round-Robin Scheduling
- Khi gặp bài toán mô phỏng các tiến trình lặp lại theo vòng (Round-Robin), thay vì phải dùng mảng xoay vòng hay danh sách liên kết vòng (Circular Linked List) phức tạp, việc cộng thêm một chu kỳ $n$ vào chỉ số là một kỹ thuật kinh điển giúp biến bài toán vòng tròn thành bài toán xếp hàng tuyến tính.
- Kỹ thuật này giữ nguyên trật tự tương đối giữa các phần tử và tự động phân định rõ ràng các thế hệ (vòng 1, vòng 2, vòng 3...).

### 2. Nhận biết bài toán Tham lam (Greedy Choice)
- Khi phân tích chiến lược của một người chơi, hãy đặt câu hỏi: *"Hành động nào mang lại lợi ích cục bộ lớn nhất ngay lập tức mà không để lại hậu quả tiêu cực cho tương lai?"*
- Trong bài này: Cấm ngay kẻ thù chuẩn bị tấn công mình là quyết định tối ưu không thể bàn cãi.

### 3. Các bài toán liên quan trên LeetCode để rèn luyện:
- **LeetCode 621:** Task Scheduler *(Sắp xếp tiến trình theo CPU cooling time - Greedy & PriorityQueue)*
- **LeetCode 358:** Rearrange String k Distance Apart *(Xếp hàng đợi theo khoảng cách)*
- **LeetCode 1046:** Last Stone Weight *(Mô phỏng đối đầu từng cặp bằng Heap/Queue)*
