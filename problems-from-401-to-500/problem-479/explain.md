# 📚 Giải thích Chi tiết Bài 672 - Bulb Switcher II

**Mục tiêu:** Cho một căn phòng có $n$ bóng đèn được đánh số từ $1$ đến $n$, ban đầu tất cả các bóng đèn đều đang được **bật (ON)**. Trên tường có $4$ nút bấm với các chức năng đảo trạng thái khác nhau. Bạn được yêu cầu bấm chính xác `presses` lần. Hãy tính số lượng trạng thái khác nhau của các bóng đèn có thể đạt được sau toàn bộ quá trình bấm nút.

---

## 🎯 Phân tích bài toán

### 1. Bản chất của các Thao tác Bấm nút (Commutative & Parity Property)

Bốn nút bấm có chức năng như sau:
- **Nút 1:** Đảo trạng thái tất cả các bóng đèn ($1, 2, 3, 4, \dots$).
- **Nút 2:** Đảo trạng thái các bóng đèn có số chẵn ($2, 4, 6, \dots$).
- **Nút 3:** Đảo trạng thái các bóng đèn có số lẻ ($1, 3, 5, \dots$).
- **Nút 4:** Đảo trạng thái các bóng đèn có nhãn dạng $3k + 1$ ($1, 4, 7, 10, \dots$).

Ta rút ra các tính chất đại số quan trọng:
1. **Tính giao hoán và kết hợp (Commutative & Associative):**
   - Phép đảo trạng thái bản chất là phép **XOR với 1**: $x \oplus 1$.
   - Vì phép XOR có tính giao hoán, thứ tự bấm các nút **không quan trọng**. Bấm nút A rồi nút B cho kết quả giống hệt bấm nút B rồi nút A.
2. **Tính triệt tiêu chẵn lẻ (Self-Inverse):**
   - Bấm cùng một nút 2 lần sẽ tự triệt tiêu ($A \oplus A = 0$), đưa bóng đèn về trạng thái ban đầu.
   - Do đó, số lần bấm của mỗi nút chỉ có ý nghĩa về mặt **tính chẵn/lẻ**:
     - Bấm số lần chẵn ($0, 2, 4, \dots$) $\iff$ Tương đương **0 lần** (nút không hoạt động).
     - Bấm số lần lẻ ($1, 3, 5, \dots$) $\iff$ Tương đương **1 lần** (nút được kích hoạt).

---

### 2. Sự Phụ thuộc Tuyến tính giữa các Nút (Linear Dependence)

Quan sát tác động của Nút 1, Nút 2 và Nút 3:
- Nút 1 tác động lên toàn bộ các số.
- Nút 2 tác động lên các số chẵn.
- Nút 3 tác động lên các số lẻ.
- Mọi số nguyên dương đều hoặc là chẵn, hoặc là lẻ. Do đó:
  $$\text{Nút 2} \oplus \text{Nút 3} = \text{Nút 1}$$
  $$\text{Nút 1} \oplus \text{Nút 2} \oplus \text{Nút 3} = 0$$

> [!IMPORTANT]
> **Hệ quả quan trọng:** Nếu ta bấm đồng thời cả 3 nút (1, 2, 3), mỗi bóng đèn đều bị đảo chính xác 2 lần, tức là **toàn bộ bóng đèn không hề thay đổi trạng thái**!
> Vì vậy, 4 nút bấm không tạo ra $2^4 = 16$ trạng thái độc lập mà chỉ có tối đa $2^3 = 8$ trạng thái khả dĩ.

---

### 3. Tính Chu kỳ của Dãy Bóng đèn (Periodicity & LCM)

Mỗi nút tác động lên các bóng đèn theo chu kỳ:
- Nút 1: Tác động mọi bóng $\implies$ chu kỳ $1$.
- Nút 2: Tác động theo tính chẵn lẻ $\implies$ chu kỳ $2$.
- Nút 3: Tác động theo tính chẵn lẻ $\implies$ chu kỳ $2$.
- Nút 4: Tác động theo số dư chia cho 3 ($3k + 1$) $\implies$ chu kỳ $3$.

Bội chung nhỏ nhất của các chu kỳ là:
$$\text{BCNN}(1, 2, 2, 3) = 6$$

Điều này có nghĩa là bóng đèn thứ $i$ và bóng đèn thứ $i + 6$ luôn chịu tác động của cùng một tập hợp nút bấm:
$$\text{state}(i) = \text{state}(i + 6)$$
Do đó, toàn bộ dãy $n$ bóng đèn chỉ là sự lặp lại của **6 bóng đèn đầu tiên**!

---

### 4. Thu hẹp Không gian từ 6 bóng xuống đúng 3 bóng đầu tiên

Xét tác động của các nút lên 3 bóng đèn đầu tiên:
- **Bóng 1:** Bị tác động bởi Nút 1, Nút 3, Nút 4.
- **Bóng 2:** Bị tác động bởi Nút 1, Nút 2.
- **Bóng 3:** Bị tác động bởi Nút 1, Nút 3.

Hãy chú ý:
- Bóng 1 và Bóng 3 chỉ khác biệt duy nhất ở **Nút 4** (vì Bóng 1 có dạng $3(0)+1$, còn Bóng 3 thì không).
- Bóng 2 phản ánh tác động của Nút 2.
- Do tính phụ thuộc $\text{Nút 1} \oplus \text{Nút 2} \oplus \text{Nút 3} = 0$, việc xác định trạng thái của 3 bóng đèn đầu tiên là **đủ để suy ra trạng thái của tất cả các nút và toàn bộ các bóng đèn còn lại**!

> [!TIP]
> **Định lý thu gọn:** Với mọi $n \ge 3$, số trạng thái phân biệt của toàn bộ $n$ bóng đèn bằng đúng số trạng thái phân biệt của **3 bóng đèn đầu tiên**.
> Ta chỉ cần xét:
> $$n' = \min(n, 3)$$

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Mô phỏng Duyệt BFS / Set | Cách 2: Phân tích Toán học & Bảng Quyết định (Mathematical Reduction ⭐) |
| :--- | :--- | :--- |
| **Ý tưởng** | Rút gọn về 6 bóng đèn, dùng `Set<Integer>` mô phỏng từng lượt bấm nút | Thu gọn $n' = \min(n, 3)$, suy luận số trạng thái hữu hạn bằng giải tích tổ hợp |
| **Độ phức tạp Thời gian** | $\mathcal{O}(presses)$ | $\mathcal{O}(1)$ - Tuyệt đối tối ưu |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(1)$ (chỉ lưu tối đa 8 trạng thái trong Set) | $\mathcal{O}(1)$ - Không cần cấu trúc dữ liệu phụ trợ |
| **Đánh giá** | Dễ code nhưng thừa thãi khi $presses$ lớn | **Mã nguồn cực kỳ ngắn gọn, thể hiện tư duy toán học sâu sắc** |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Phân Tích Toán Học & Bảng Quyết Định O(1)

### 💡 Khảo sát chi tiết từng trường hợp của $(n, presses)$

Gọi $n' = \min(n, 3)$. Ta lần lượt xét các giá trị của $n'$ và `presses`:

#### 1. Khi `presses == 0`
- Chưa bấm lần nào, tất cả các bóng đèn đều giữ nguyên trạng thái ban đầu (`[on, on, ...]`).
- 👉 **Kết quả: 1** (với mọi $n$).

---

#### 2. Khi $n = 1$ (Chỉ có 1 bóng đèn)
- Ban đầu: `[on]`.
- Nút 1, 3, 4 đều đảo bóng 1 $\implies$ chuyển thành `[off]`.
- Nút 2 không tác động lên bóng 1 $\implies$ giữ nguyên `[on]`.
- Với `presses >= 1`, ta luôn có thể tạo ra cả 2 trạng thái `[on]` hoặc `[off]`.
- 👉 **Kết quả: 2**.

---

#### 3. Khi $n = 2$ (Có 2 bóng đèn)
Hai bóng đèn có tối đa $2^2 = 4$ trạng thái có thể có.
- Ban đầu: `[on, on]` (tức `[1, 1]`).
- **Nếu `presses == 1`:**
  - Bấm Nút 1: `[off, off]`
  - Bấm Nút 2: `[on, off]`
  - Bấm Nút 3: `[off, on]`
  - Bấm Nút 4: `[off, on]` (trùng với Nút 3)
  - 👉 Tạo ra đúng **3** trạng thái phân biệt: `[0, 0]`, `[1, 0]`, `[0, 1]`.
- **Nếu `presses >= 2`:**
  - Ta có thể bấm Nút 1 hai lần (hoặc bất kỳ nút nào 2 lần) để trở về trạng thái gốc `[on, on]` (`[1, 1]`).
  - Kết hợp với 3 trạng thái ở trên $\implies$ Đạt được trọn vẹn cả **4** trạng thái: `[1, 1]`, `[0, 0]`, `[1, 0]`, `[0, 1]`.
  - 👉 **Kết quả: 4**.

---

#### 4. Khi $n \ge 3$ (Có từ 3 bóng đèn trở lên)
Ba bóng đèn có tối đa $2^3 = 8$ trạng thái có thể có.
- Ban đầu: `[on, on, on]` (tức `[1, 1, 1]`).

- **Nếu `presses == 1`:**
  - Bấm Nút 1: `[0, 0, 0]`
  - Bấm Nút 2: `[1, 0, 1]`
  - Bấm Nút 3: `[0, 1, 0]`
  - Bấm Nút 4: `[0, 1, 1]`
  - Cả 4 trạng thái trên đều khác nhau.
  - 👉 **Kết quả: 4**.

- **Nếu `presses == 2`:**
  - Ta có thể đạt được:
    - Bấm 1 nút 2 lần: `[1, 1, 1]` (trạng thái gốc).
    - Bấm 2 nút khác nhau:
      - Nút 1 + 2 = Nút 3 $\implies$ `[0, 1, 0]`
      - Nút 1 + 3 = Nút 2 $\implies$ `[1, 0, 1]`
      - Nút 2 + 3 = Nút 1 $\implies$ `[0, 0, 0]`
      - Nút 1 + 4 $\implies$ `[1, 0, 0]`
      - Nút 2 + 4 $\implies$ `[0, 0, 1]`
      - Nút 3 + 4 $\implies$ `[1, 1, 0]`
  - Tổng hợp lại gồm 7 trạng thái:
    `[1, 1, 1]`, `[0, 1, 0]`, `[1, 0, 1]`, `[0, 0, 0]`, `[1, 0, 0]`, `[0, 0, 1]`, `[1, 1, 0]`.
  - Trạng thái duy nhất chưa đạt được là `[0, 1, 1]` (chính là Nút 4 đơn lẻ, đòi hỏi số lần bấm lẻ: 1 hoặc 3).
  - 👉 **Kết quả: 7**.

- **Nếu `presses >= 3`:**
  - Vì $presses \ge 3$, ta có thể đạt được trạng thái `[0, 1, 1]` bằng cách bấm Nút 4 rồi bấm thêm một nút bất kỳ 2 lần để tiêu hao lượt bấm.
  - Kết hợp với 7 trạng thái của $presses = 2$, ta đạt được **toàn bộ 8 trạng thái khả dĩ**.
  - 👉 **Kết quả: 8**.

---

## 🔄 Bảng Tổng Hợp Quyết Định Hoàn Chỉnh (Decision Matrix)

| Quy mô bóng đèn ($n$) | $presses = 0$ | $presses = 1$ | $presses = 2$ | $presses \ge 3$ |
| :---: | :---: | :---: | :---: | :---: |
| **$n = 1$** | **1** | **2** | **2** | **2** |
| **$n = 2$** | **1** | **3** | **4** | **4** |
| **$n \ge 3$** | **1** | **4** | **7** | **8** |

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Giải pháp Tối ưu - Bảng Quyết Định Toán học $\mathcal{O}(1)$ ⭐

```java
class Solution {
    /**
     * Giải pháp tối ưu: Rút gọn toán học O(1)
     */
    public int flipLights(int n, int presses) {
        // Trường hợp 0: Chưa bấm lần nào
        if (presses == 0) {
            return 1;
        }

        // Trường hợp 1: Chỉ có 1 bóng đèn
        if (n == 1) {
            return 2;
        }

        // Trường hợp 2: Có 2 bóng đèn
        if (n == 2) {
            return presses == 1 ? 3 : 4;
        }

        // Trường hợp 3: Có từ 3 bóng đèn trở lên (n >= 3)
        if (presses == 1) {
            return 4;
        }
        if (presses == 2) {
            return 7;
        }
        return 8; // presses >= 3
    }
}
```

---

### Cách 2 (Tham khảo): Mô phỏng Bitmask & BFS trên Chu kỳ 6

Dành cho trường hợp muốn chứng minh tính đúng đắn hoặc mở rộng nếu bài toán thay đổi tập hợp nút bấm:

```java
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class SolutionSimulation {
    public int flipLights(int n, int presses) {
        // Chỉ cần quan sát tối đa 6 bóng đèn do chu kỳ BCNN(2, 3) = 6
        int len = Math.min(n, 6);
        
        // Trạng thái ban đầu: len bóng đèn đều ON (tất cả các bit đều là 1)
        int initial = (1 << len) - 1;

        // Tạo mặt nạ bitmask cho 4 nút bấm đối với len bóng đèn đầu tiên
        int b1 = 0, b2 = 0, b3 = 0, b4 = 0;
        for (int i = 0; i < len; i++) {
            int bulb = i + 1; // Nhãn bóng từ 1 đến len
            b1 |= (1 << i);
            if (bulb % 2 == 0) b2 |= (1 << i);
            if (bulb % 2 == 1) b3 |= (1 << i);
            if ((bulb - 1) % 3 == 0) b4 |= (1 << i);
        }
        int[] buttons = {b1, b2, b3, b4};

        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(initial);

        // Mô phỏng từng lượt bấm
        // Vì trạng thái tuần hoàn sau tối đa 3 lần bấm hiệu dụng, ta có thể giới hạn số bước
        int effectivePresses = Math.min(presses, 3);
        // Nếu presses > 3 và (presses - effectivePresses) lẻ, ta chỉnh lại tính chẵn lẻ
        if (presses > 3 && (presses % 2 != effectivePresses % 2)) {
            effectivePresses = 2; // Giữ nguyên tính chẵn lẻ
        }

        for (int p = 0; p < presses && p < 4; p++) {
            Set<Integer> nextStates = new HashSet<>();
            for (int state : currentStates) {
                for (int btn : buttons) {
                    nextStates.add(state ^ btn);
                }
            }
            currentStates = nextStates;
        }

        return currentStates.size();
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`if (presses == 0) return 1;`**:
   - Nếu số lượt bấm bằng 0, chỉ có đúng một cấu hình khả dĩ duy nhất là tất cả các đèn đều bật.
2. **`if (n == 1) return 2;`**:
   - Khi có 1 đèn và số lần bấm $\ge 1$, ta có thể để đèn tắt (bấm Nút 1) hoặc bật (bấm Nút 2). Số trạng thái luôn là 2.
3. **`if (n == 2) return presses == 1 ? 3 : 4;`**:
   - Khi có 2 đèn, 1 lần bấm chỉ cho ra 3 trạng thái. Từ 2 lần bấm trở lên có thể tạo ra cả 4 trạng thái của 2 bit.
4. **`if (presses == 1) return 4;`**:
   - Với $n \ge 3$, đúng 1 lần bấm tương ứng với 4 nút độc lập, sinh ra đúng 4 trạng thái phân biệt.
5. **`if (presses == 2) return 7;`**:
   - Với 2 lần bấm, ta tạo được trạng thái gốc (`[1, 1, 1]`) và 6 trạng thái từ việc phối hợp 2 nút khác nhau, tổng cộng là 7 trạng thái.
6. **`return 8;`**:
   - Với $presses \ge 3$, toàn bộ $2^3 = 8$ trạng thái của 3 bóng đèn đầu tiên đều có thể đạt được.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- $\mathcal{O}(1)$:
  - Hàm chỉ thực hiện tối đa 4 phép so sánh điều kiện số nguyên (`if-else`).
  - Hoàn toàn không phụ thuộc vào độ lớn của $n$ (ngay cả khi $n = 10^9$) hay $presses$ (ngay cả khi $presses = 10^9$).

### Độ phức tạp Không gian (Space Complexity)
- $\mathcal{O}(1)$:
  - Không khởi tạo thêm mảng, không dùng hàng đợi hay cấu trúc dữ liệu bổ trợ nào.
  - Bộ nhớ sử dụng là hằng số tuyệt đối.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Kỹ thuật "Nén chiều dữ liệu" (Dimensionality Reduction):**
   - Đừng vội vàng giải quyết bài toán bằng cách tạo mảng kích thước $n$ hay mô phỏng trực tiếp khi $n$ lớn.
   - Luôn đặt câu hỏi: *"Các phần tử sau có bị ràng buộc hay lặp lại theo quy luật của các phần tử trước không?"*
   - Tính chu kỳ $\text{BCNN}$ và phép biến đổi đại số tuyến tính trong trường hữu hạn $\mathbb{F}_2$ (XOR) là chìa khóa rút gọn các bài toán chuyển mạch / bóng đèn.

2. **Cách trình bày khi phỏng vấn:**
   - Bước 1: Nêu nhận xét về tính giao hoán và triệt tiêu của phép XOR ($A \oplus A = 0$).
   - Bước 2: Nêu tính chu kỳ 6 từ $\text{BCNN}(2, 3) = 6$.
   - Bước 3: Chứng minh 3 bóng đèn đầu tiên độc lập tuyến tính và quyết định toàn bộ hệ thống.
   - Bước 4: Lập bảng phân tích các trường hợp nhỏ và đưa ra giải pháp $\mathcal{O}(1)$.
   - Phong cách tiếp cận này ghi điểm tuyệt đối trong các buổi phỏng vấn của các tập đoàn công nghệ lớn (Google, Meta).
