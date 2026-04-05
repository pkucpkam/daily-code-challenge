class Solution {
    public int calculate(String s) {
        int result = 0;
        int lastNumber = 0;
        int currentNumber = 0;
        char operation = '+';

        for (int index = 0; index < s.length(); index++) {
            char currentChar = s.charAt(index);

            if (Character.isDigit(currentChar)) {
                currentNumber = currentNumber * 10 + (currentChar - '0');
            }

            if (!Character.isDigit(currentChar) && currentChar != ' ' || index == s.length() - 1) {
                if (operation == '+') {
                    result += lastNumber;
                    lastNumber = currentNumber;
                } else if (operation == '-') {
                    result += lastNumber;
                    lastNumber = -currentNumber;
                } else if (operation == '*') {
                    lastNumber = lastNumber * currentNumber;
                } else if (operation == '/') {
                    lastNumber = lastNumber / currentNumber;
                }

                operation = currentChar;
                currentNumber = 0;
            }
        }

        return result + lastNumber;
    }
}