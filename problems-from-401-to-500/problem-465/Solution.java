import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     * Best Solution: Mã hóa Bộ ba Định danh Cây con (Triplet Subtree ID Mapping).
     *
     * Tư tưởng cốt lõi:
     * 1. Nhận diện cấu trúc cây con:
     *    - Một cây con tại `node` được xác định duy nhất bởi bộ 3:
     *      (ID của cây con trái, giá trị node.val, ID của cây con phải).
     *    - Nút rỗng `null` được gán ID mặc định là 0.
     *
     * 2. Tối ưu hóa chuỗi khóa:
     *    - Thay vì tuần tự hóa toàn bộ cây thành chuỗi (gây tốn O(N^2) thời gian và bộ nhớ trong trường hợp cây suy biến),
     *      ta chỉ ghép 3 số nguyên: `leftId + "," + node.val + "," + rightId`.
     *    - Chuỗi khóa này có độ dài tối đa chỉ ~15 ký tự, giúp phép băm (hashing) và so sánh đạt O(1).
     *
     * 3. Gán ID duy nhất & Đếm số lần xuất hiện:
     *    - Sử dụng `tripletToId` để ánh xạ mỗi bộ ba duy nhất sang một số nguyên ID (1, 2, 3,...).
     *    - Sử dụng `idCount` để đếm tần suất xuất hiện của từng ID cấu trúc cây con.
     *    - Khi một ID đạt tần suất chính xác bằng 2 (`count == 2`), ta thêm `node` đó vào danh sách kết quả.
     *      (Điều này đảm bảo mỗi loại cây con trùng lặp chỉ được thêm vào danh sách đúng 1 lần, kể cả khi xuất hiện >= 3 lần).
     *
     * Time Complexity: O(N) - Mỗi node được duyệt đúng 1 lần qua DFS hậu thứ tự (post-order).
     *                  Mỗi thao tác băm và tra cứu bảng băm tốn O(1).
     * Space Complexity: O(N) - Bảng băm lưu tối đa N bộ ba, mảng kết quả và ngăn xếp đệ quy O(H) với H <= N.
     */
    private int idCounter = 1;
    private final Map<String, Integer> tripletToId = new HashMap<>();
    private final Map<Integer, Integer> idCount = new HashMap<>();
    private final List<TreeNode> result = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        tripletToId.clear();
        idCount.clear();
        result.clear();
        idCounter = 1;

        getId(root);

        return result;
    }

    private int getId(TreeNode node) {
        if (node == null) {
            return 0; // ID 0 đại diện cho nút rỗng (null)
        }

        // Duyệt hậu thứ tự (post-order): Tính ID cho cây con trái và phải trước
        int leftId = getId(node.left);
        int rightId = getId(node.right);

        // Tạo khóa định danh bộ ba O(1)
        String tripletKey = leftId + "," + node.val + "," + rightId;

        // Lấy ID hiện có hoặc tạo ID mới nếu cấu trúc này chưa từng xuất hiện
        int id;
        if (tripletToId.containsKey(tripletKey)) {
            id = tripletToId.get(tripletKey);
        } else {
            id = idCounter++;
            tripletToId.put(tripletKey, id);
        }

        // Đếm số lần xuất hiện của ID cây con này
        int count = idCount.getOrDefault(id, 0) + 1;
        idCount.put(id, count);

        // Chỉ thêm vào kết quả khi lần đầu tiên phát hiện trùng lặp (count == 2)
        if (count == 2) {
            result.add(node);
        }

        return id;
    }
}