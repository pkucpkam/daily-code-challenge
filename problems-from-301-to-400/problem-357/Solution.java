class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            int slow = i;
            int fast = i;
            
            // Find if there is a loop
            while (true) {
                int nextSlow = getNext(nums, slow);
                if (nums[slow] * nums[nextSlow] <= 0) {
                    break;
                }
                slow = nextSlow;
                
                int nextFast = getNext(nums, fast);
                if (nums[fast] * nums[nextFast] <= 0) {
                    break;
                }
                int nextFast2 = getNext(nums, nextFast);
                if (nums[nextFast] * nums[nextFast2] <= 0) {
                    break;
                }
                fast = nextFast2;
                
                if (slow == fast) {
                    if (slow == getNext(nums, slow)) {
                        break;
                    }
                    return true;
                }
            }
            
            int curr = i;
            int val = nums[i];
            while (nums[curr] * val > 0) {
                int next = getNext(nums, curr);
                nums[curr] = 0;
                curr = next;
            }
        }
        return false;
    }

    private int getNext(int[] nums, int i) {
        int n = nums.length;
        int next = (i + nums[i]) % n;
        if (next < 0) {
            next += n;
        }
        return next;
    }
}