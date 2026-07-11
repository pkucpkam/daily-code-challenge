class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) {
            return false;
        }
        if (desiredTotal <= 0) {
            return true;
        }
        byte[] memo = new byte[1 << maxChoosableInteger];
        return dfs(maxChoosableInteger, desiredTotal, 0, memo);
    }

    private boolean dfs(int maxChoosableInteger, int desiredTotal, int state, byte[] memo) {
        if (memo[state] != 0) {
            return memo[state] == 1;
        }
        
        for (int i = 1; i <= maxChoosableInteger; i++) {
            int bit = 1 << (i - 1);
            if ((state & bit) == 0) {
                if (desiredTotal - i <= 0 || !dfs(maxChoosableInteger, desiredTotal - i, state | bit, memo)) {
                    memo[state] = 1;
                    return true;
                }
            }
        }
        
        memo[state] = 2;
        return false;
    }
}