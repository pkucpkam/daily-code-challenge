class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) return s;

        StringBuilder[] rows = new StringBuilder[Math.min(numRows, s.length())];
        for (int i = 0; i < rows.length; i++) rows[i] = new StringBuilder();

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows[curRow].append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) result.append(row);
        return result.toString();
    }
}

// Better Solution
// class Solution {
//     public String convert(String s, int numRows) {
//         int length = s.length();
        
//         if (numRows > length || numRows <= 1) {
//             return s;
//         }
        
//         char[] zigZagChars = new char[length];
//         int count = 0;
        
//         int interval = 2 * numRows - 2;
        
//         for (int i = 0; i < numRows; i++) {
//             int step = interval - 2 * i;
//             for (int j = i; j < length; j += interval) {
//                 zigZagChars[count] = s.charAt(j);
//                 count++;
//                 if (step > 0 && step < interval && j + step < length) {
//                     zigZagChars[count] = s.charAt(j + step);
//                     count++;
//                 }                
//             }            
//         }
        
//         return new String(zigZagChars);
//     }
// }