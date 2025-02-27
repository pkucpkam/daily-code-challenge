import java.util.HashMap;

public class Solution {
    public int romanToInt(String s) {
        int result = 0;
        int preValue = 0;
        HashMap<Character, Integer> roman = new HashMap<Character, Integer>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);
        for (int i=s.length()-1; i >= 0; i--) {
            int curValue = roman.get(s.charAt(i));
            if (curValue < preValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            preValue = curValue;
        }
        return result;
    }

    // public static void main(String[] args) {
    //     Solution solution = new Solution();
    //     System.out.println(solution.romanToInt("LVIII")); // 3
    //     System.out.println(solution.romanToInt("MCMXCIV")); // 4 
    // }
}
