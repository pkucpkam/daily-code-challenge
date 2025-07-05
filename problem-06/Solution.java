// Convert to String
// class Solution {
//     public boolean isPalindrome(int x) {
//         String str = x + "";
//         String newStr = "";
//         for (int i = str.length() - 1; i >= 0; i--) {
//             newStr += str.charAt(i);
//         }
//         if (str.equals(newStr)) {
//             return true;
//         }
//         return false;
//     }
// }

// Use math
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversed = 0, original = x;
        while (x > 0) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }

        return original == reversed;
    }
}
