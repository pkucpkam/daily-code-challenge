import java.util.*;

class Test {
    public static void main(String[] args) {
        Solution sol = new Solution();

        List<String> logs1 = Arrays.asList("0:start:0", "1:start:2", "1:end:5", "0:end:6");
        System.out.println(Arrays.toString(sol.exclusiveTime(2, logs1)));

        List<String> logs2 = Arrays.asList("0:start:0", "0:start:2", "0:end:5", "0:start:6", "0:end:6", "0:end:7");
        System.out.println(Arrays.toString(sol.exclusiveTime(1, logs2)));

        List<String> logs3 = Arrays.asList("0:start:0", "0:start:2", "0:end:5", "1:start:6", "1:end:6", "0:end:7");
        System.out.println(Arrays.toString(sol.exclusiveTime(2, logs3)));
    }
}
