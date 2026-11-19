import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        // Min-Heap duy trì k phần tử có tần suất cao nhất
        // Phần tử "yếu nhất" trong top k sẽ nằm ở đỉnh heap để bị loại bỏ khi vượt quá kích thước k:
        // 1. Tần suất nhỏ hơn nằm ở đỉnh heap (loại trước).
        // 2. Nếu cùng tần suất, từ có thứ tự từ điển LỚN hơn nằm ở đỉnh heap (loại trước).
        PriorityQueue<String> minHeap = new PriorityQueue<>((w1, w2) -> {
            int freq1 = freqMap.get(w1);
            int freq2 = freqMap.get(w2);
            if (freq1 != freq2) {
                return Integer.compare(freq1, freq2);
            }
            return w2.compareTo(w1);
        });

        for (String word : freqMap.keySet()) {
            minHeap.offer(word);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        List<String> result = new ArrayList<>(k);
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }

        // Đảo ngược danh sách vì minHeap lấy ra theo thứ tự tăng dần
        Collections.reverse(result);
        return result;
    }
}
