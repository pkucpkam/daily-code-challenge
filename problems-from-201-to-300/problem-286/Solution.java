class NumArray {

    private final int[] nums;
    private final int[] bit;
    private final int n;

    public NumArray(int[] nums) {
        this.n = nums.length;
        this.nums = new int[n];
        this.bit = new int[n + 1];

        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }
    }

    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;

        int i = index + 1;
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    public int sumRange(int left, int right) {
        return prefixSum(right) - prefixSum(left - 1);
    }

    private int prefixSum(int index) {
        int sum = 0;
        int i = index + 1;

        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }

        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */