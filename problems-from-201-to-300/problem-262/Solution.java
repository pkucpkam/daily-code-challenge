import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, k, n, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int remainCount, int remainSum,
            List<Integer> path, List<List<Integer>> result) {
        if (remainCount == 0) {
            if (remainSum == 0) {
                result.add(new ArrayList<>(path));
            }
            return;
        }

        int available = 10 - start;
        if (available < remainCount) {
            return;
        }

        int minPossible = arithmeticSum(start, start + remainCount - 1);
        if (minPossible > remainSum) {
            return;
        }

        int maxPossible = arithmeticSum(10 - remainCount, 9);
        if (maxPossible < remainSum) {
            return;
        }

        for (int num = start; num <= 9; num++) {
            if (num > remainSum) {
                break;
            }

            path.add(num);
            backtrack(num + 1, remainCount - 1, remainSum - num, path, result);
            path.remove(path.size() - 1);
        }
    }

    private int arithmeticSum(int left, int right) {
        return (left + right) * (right - left + 1) / 2;
    }
}