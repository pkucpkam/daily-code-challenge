import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[][] reconstructQueue(int[][] people) {
        // Sort people:
        // 1. By height descending.
        // 2. If height is same, by k ascending.
        Arrays.sort(people, (a, b) -> {
            if (a[0] != b[0]) {
                return b[0] - a[0];
            } else {
                return a[1] - b[1];
            }
        });
        
        List<int[]> result = new ArrayList<>();
        for (int[] p : people) {
            result.add(p[1], p);
        }
        
        return result.toArray(new int[people.length][]);
    }
}