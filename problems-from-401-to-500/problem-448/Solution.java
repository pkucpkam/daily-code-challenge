import java.util.List;

class Solution {
    /**
     * Best Solution: Kỹ thuật Tham lam duyệt 1 lần (Single-Pass Greedy)
     * 
     * Time Complexity: O(m) - với m là số lượng mảng, mỗi mảng chỉ truy xuất phần tử đầu và cuối O(1).
     * Space Complexity: O(1) - chỉ dùng một vài biến phụ trợ để theo dõi min và max.
     */
    public int maxDistance(List<List<Integer>> arrays) {
        // Khởi tạo minVal và maxVal từ mảng đầu tiên
        List<Integer> firstArray = arrays.get(0);
        int minVal = firstArray.get(0);
        int maxVal = firstArray.get(firstArray.size() - 1);
        int maxDist = 0;

        int m = arrays.size();
        // Duyệt qua các mảng còn lại từ vị trí 1 đến m - 1
        for (int i = 1; i < m; i++) {
            List<Integer> currentArray = arrays.get(i);
            int currentMin = currentArray.get(0);
            int currentMax = currentArray.get(currentArray.size() - 1);

            // Cập nhật khoảng cách lớn nhất có thể giữa mảng hiện tại và các mảng trước đó
            // Đảm bảo 2 phần tử luôn thuộc 2 mảng khác nhau vì minVal và maxVal được tính từ các mảng [0..i-1]
            maxDist = Math.max(maxDist, Math.max(currentMax - minVal, maxVal - currentMin));

            // Cập nhật minVal và maxVal toàn cục cho các bước tiếp theo
            minVal = Math.min(minVal, currentMin);
            maxVal = Math.max(maxVal, currentMax);
        }

        return maxDist;
    }
}