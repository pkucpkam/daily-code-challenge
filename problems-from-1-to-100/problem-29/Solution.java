import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>(Collections.nCopies(rowIndex + 1, 1));
        for (int j = 1; j < rowIndex; j++) {
            row.set(j, (int) ((long) row.get(j - 1) * (rowIndex - j + 1) / j));
        }
        return row;
    }
}

