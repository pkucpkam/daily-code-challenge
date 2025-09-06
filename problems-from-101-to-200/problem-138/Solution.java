class Solution {
    public String multiply(String num1, String num2) {
        int n = num1.length(), m = num2.length();
        int[] pos = new int[n + m]; 

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];

                pos[p2] = sum % 10;
                pos[p1] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (!(sb.length() == 0 && p == 0)) sb.append(p);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}

// Better Solution
// class Solution {
//     public String multiply(String num1, String num2) {
//         if ("0".equals(num1) || "0".equals(num2)) {
//             return "0";
//         }
//         int m = num1.length(), n = num2.length();
//         int[] arr = new int[m + n];
//         for (int i = m - 1; i >= 0; --i) {
//             int a = num1.charAt(i) - '0';
//             for (int j = n - 1; j >= 0; --j) {
//                 int b = num2.charAt(j) - '0';
//                 arr[i + j + 1] += a * b;
//             }
//         }
//         for (int i = arr.length - 1; i > 0; --i) {
//             arr[i - 1] += arr[i] / 10;
//             arr[i] %= 10;
//         }
//         int i = arr[0] == 0 ? 1 : 0;
//         StringBuilder ans = new StringBuilder();
//         for (; i < arr.length; ++i) {
//             ans.append(arr[i]);
//         }
//         return ans.toString();
//     }
// }