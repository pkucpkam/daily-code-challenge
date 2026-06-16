class Solution {
    public int findMaximumXOR(int[] nums) {
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        if (maxNum == 0) return 0;
        
        int maxBits = 31 - Integer.numberOfLeadingZeros(maxNum);
        int maxNodes = nums.length * (maxBits + 1) + 1;
        
        int[] left = new int[maxNodes];
        int[] right = new int[maxNodes]; 
        int nodeCount = 1;
        
        int maxXor = 0;
        for (int num : nums) {
            if (nodeCount > 1) {
                int queryNode = 0;
                int currentXor = 0;
                for (int bit = maxBits; bit >= 0; bit--) {
                    int b = (num >> bit) & 1;
                    if (b == 0) {
                        if (right[queryNode] != 0) { 
                            currentXor |= (1 << bit);
                            queryNode = right[queryNode];
                        } else {
                            queryNode = left[queryNode];
                        }
                    } else {
                        if (left[queryNode] != 0) { 
                            currentXor |= (1 << bit);
                            queryNode = left[queryNode];
                        } else {
                            queryNode = right[queryNode];
                        }
                    }
                }
                maxXor = Math.max(maxXor, currentXor);
            }
            
            int insertNode = 0;
            for (int bit = maxBits; bit >= 0; bit--) {
                int b = (num >> bit) & 1;
                if (b == 0) {
                    if (left[insertNode] == 0) {
                        left[insertNode] = nodeCount++;
                    }
                    insertNode = left[insertNode];
                } else {
                    if (right[insertNode] == 0) {
                        right[insertNode] = nodeCount++;
                    }
                    insertNode = right[insertNode];
                }
            }
        }
        return maxXor;
    }
}