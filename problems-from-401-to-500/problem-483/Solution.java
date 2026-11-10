import java.util.HashMap;
import java.util.Map;

class MapSum {
    private final Map<String, Integer> values = new HashMap<>();
    private final Map<String, Integer> prefixSums = new HashMap<>();

    public MapSum() {
    }

    public void insert(String key, int val) {
        int delta = val - values.getOrDefault(key, 0);
        values.put(key, val);

        for (int i = 1; i <= key.length(); i++) {
            String prefix = key.substring(0, i);
            prefixSums.put(prefix, prefixSums.getOrDefault(prefix, 0) + delta);
        }
    }

    public int sum(String prefix) {
        return prefixSums.getOrDefault(prefix, 0);
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
