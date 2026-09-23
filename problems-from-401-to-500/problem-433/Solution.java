import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String[] findRestaurant(String[] list1, String[] list2) {
        // Đảm bảo list1 luôn là mảng có độ dài nhỏ hơn hoặc bằng list2
        // Giúp tiết kiệm dung lượng bộ nhớ HashMap và giảm số lần tính mã băm (hash)
        if (list1.length > list2.length) {
            return findRestaurant(list2, list1);
        }

        // Khởi tạo HashMap với dung lượng dự tính để tránh rehashing
        Map<String, Integer> map = new HashMap<>(list1.length * 4 / 3 + 1);
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }

        List<String> result = new ArrayList<>();
        int minSum = Integer.MAX_VALUE;

        for (int j = 0; j < list2.length; j++) {
            // Tối ưu cắt tỉa nhánh (Early Termination):
            // Do chỉ số i trong list1 luôn >= 0, nên với mọi phần tử ở vị trí j,
            // tổng chỉ số i + j luôn >= j.
            // Nếu j > minSum thì mọi cặp chung tìm thấy sau vị trí này chắc chắn có tổng > minSum.
            if (j > minSum) {
                break;
            }

            Integer i = map.get(list2[j]);
            if (i != null) {
                int sum = i + j;
                if (sum < minSum) {
                    minSum = sum;
                    result.clear();
                    result.add(list2[j]);
                } else if (sum == minSum) {
                    result.add(list2[j]);
                }
            }
        }

        return result.toArray(new String[result.size()]);
    }
}