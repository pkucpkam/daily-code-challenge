import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), new boolean[nums.length], res);
        return res;
    }

    private void backtrack(int[] nums, List<Integer> curr, boolean[] used, List<List<Integer>> res) {
        if (curr.size() == nums.length) {
            res.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            curr.add(nums[i]);
            backtrack(nums, curr, used, res);
            curr.remove(curr.size() - 1);
            used[i] = false;
        }
    }
}

// Better Solution
// class Solution {
//     public List<List<Integer>> permute(int[] nums) {
//         int n = nums.length;
//         List<List<Integer>> ans = new ArrayList<>();
//         solve(0, nums, ans);
//         return ans;
//     }
//     public void solve(int idx, int nums[], List<List<Integer>> ans) {
//         if(idx == nums.length) {
//             List<Integer> permutation = new ArrayList<>();
//             for(int num: nums) permutation.add(num);
//             ans.add(permutation);
//             return;
//         }
//         for(int i=idx; i<nums.length; i++) {
//             swap(nums, idx, i);
//             solve(idx+1, nums, ans);
//             swap(nums, idx, i);
//         }
//     }
//     public void swap(int nums[], int i, int j) {
//         int temp = nums[i];
//         nums[i] = nums[j];
//         nums[j] = temp;
//     }
// }