import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    private final Map<String, List<Integer>> memo = new HashMap<>();

    public List<Integer> diffWaysToCompute(String expression) {
        return compute(expression);
    }

    private List<Integer> compute(String expr) {
        if (memo.containsKey(expr)) {
            return memo.get(expr);
        }

        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> leftValues = compute(expr.substring(0, i));
                List<Integer> rightValues = compute(expr.substring(i + 1));

                for (int left : leftValues) {
                    for (int right : rightValues) {
                        if (ch == '+') {
                            results.add(left + right);
                        } else if (ch == '-') {
                            results.add(left - right);
                        } else {
                            results.add(left * right);
                        }
                    }
                }
            }
        }

        if (results.isEmpty()) {
            results.add(Integer.parseInt(expr));
        }

        memo.put(expr, results);
        return results;
    }
}