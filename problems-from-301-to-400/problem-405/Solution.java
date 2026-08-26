class Solution {
    public int nextGreaterElement(int n) {
        char[] digits = String.valueOf(n).toCharArray();
        int len = digits.length;

        // Step 1: Find the first decreasing digit from right to left
        int i = len - 2;
        while (i >= 0 && digits[i] >= digits[i + 1]) {
            i--;
        }

        // If no such digit is found, digits are in non-increasing order; no greater permutation exists
        if (i < 0) {
            return -1;
        }

        // Step 2: Find the smallest digit to the right of 'i' that is strictly greater than digits[i]
        int j = len - 1;
        while (j > i && digits[j] <= digits[i]) {
            j--;
        }

        // Step 3: Swap digits at indices i and j
        swap(digits, i, j);

        // Step 4: Reverse the digits from index i + 1 to the end to get the smallest next permutation
        reverse(digits, i + 1, len - 1);

        // Step 5: Parse the resulting string into long and check for 32-bit integer overflow
        try {
            long val = Long.parseLong(new String(digits));
            return val > Integer.MAX_VALUE ? -1 : (int) val;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }
}