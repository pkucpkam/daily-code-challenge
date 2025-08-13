class Solution {
    public int findComplement(int num) {
        if (num == 0) return 1;

        int mask = 0;
        int temp = num;
        while (temp > 0) {
            mask = (mask << 1) | 1;
            temp >>= 1;
        }

        return num ^ mask;
    }
}
