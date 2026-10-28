import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int low = 0;
        int high = arr.length - k;

        // Tìm kiếm nhị phân vị trí bắt đầu tối ưu của cửa sổ k phần tử
        while (low < high) {
            int mid = low + (high - low) / 2;

            // So sánh khoảng cách từ x tới arr[mid] và arr[mid + k]
            // Không cần dùng Math.abs() vì arr đã được sắp xếp tăng dần
            if (x - arr[mid] > arr[mid + k] - x) {
                low = mid + 1; // arr[mid + k] tốt hơn -> dịch cửa sổ sang phải
            } else {
                high = mid;    // arr[mid] tốt hơn hoặc bằng -> giữ/dịch cửa sổ sang trái
            }
        }

        // Tạo danh sách kết quả gồm k phần tử từ vị trí low
        List<Integer> result = new ArrayList<>(k);
        for (int i = low; i < low + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }
}