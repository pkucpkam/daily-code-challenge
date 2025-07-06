import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(i + 1, 1));
            for (int j = 1; j < i; j++) {
                row.set(j, triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
            }
            triangle.add(row);
        }
        return triangle;
    }
}

// Better Solution
// class Solution {
// public List<List<Integer>> generate(int numRows) {
// List<List<Integer>> result = new ArrayList<>();

// for (int i = 0; i < numRows; i++) {
// ArrayList<Integer> row = new ArrayList<>();
// long val = 1;
// row.add((int) val); // First element always 1 (C(i,0))

// for (int j = 1; j <= i; j++) {
// val = val * (i - j + 1) / j;
// row.add((int) val);
// }

// result.add(row);
// }

// return result;
// }
// }