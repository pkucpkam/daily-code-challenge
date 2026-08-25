import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> edgeCounts = new HashMap<>();
        int maxEdges = 0;

        for (List<Integer> row : wall) {
            int edgePosition = 0;
            // Iterate up to the second-to-last brick to avoid counting the rightmost edge of the wall
            for (int i = 0; i < row.size() - 1; i++) {
                edgePosition += row.get(i);
                int count = edgeCounts.getOrDefault(edgePosition, 0) + 1;
                edgeCounts.put(edgePosition, count);
                maxEdges = Math.max(maxEdges, count);
            }
        }

        return wall.size() - maxEdges;
    }
}