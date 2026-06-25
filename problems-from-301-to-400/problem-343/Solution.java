import java.util.TreeMap;
import java.util.Map;

class Solution {
    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[] result = new int[n];
        
        // Store the start time and its original index in a TreeMap
        TreeMap<Integer, Integer> startIndices = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            startIndices.put(intervals[i][0], i);
        }
        
        // For each interval, find the smallest start time >= its end time
        for (int i = 0; i < n; i++) {
            Map.Entry<Integer, Integer> entry = startIndices.ceilingEntry(intervals[i][1]);
            result[i] = (entry != null) ? entry.getValue() : -1;
        }
        
        return result;
    }
}