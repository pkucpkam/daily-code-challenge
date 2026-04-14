class Solution {
    public int[] singleNumber(int[] nums) {
        int xorAll = 0;
        for (int num : nums) {
            xorAll ^= num;
        }

        int rightMostSetBit = xorAll & -xorAll;

        int a = 0;
        int b = 0;
        for (int num : nums) {
            if ((num & rightMostSetBit) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }

        return new int[] { a, b };
    }
}