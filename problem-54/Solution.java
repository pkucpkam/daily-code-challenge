import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        if (nums.length == 0) {
            return ranges;
        }

        int start = nums[0];
        for (int i = 1; i <= nums.length; i++) {
            if (i == nums.length || nums[i] != nums[i - 1] + 1) {
                if (start == nums[i - 1]) {
                    ranges.add(String.valueOf(start));
                } else {
                    ranges.add(start + "->" + nums[i - 1]);
                }
                if (i < nums.length) {
                    start = nums[i];
                }
            }
        }
        return ranges;
    }
}

// Better Solution
// class Solution {
//     public List<String> summaryRanges(int[] nums) {
//         int n = nums.length;
//         if(n==0){
//             return new ArrayList<>();
//         }
//         List<String> res = new ArrayList<>();
//         int end = 0;
//         while(end < n){
//             int start = end; 
//             while(end<n-1 && nums[end]+1==nums[end+1]){
//                 end++;
//             }
//             StringBuilder sb = new StringBuilder();
//             sb.append(nums[start]);
//             if(nums[start]!=nums[end]){
//                 sb.append("->");
//                 sb.append(nums[end]);
//             }
//             res.add(sb.toString());
//             end++;
//         }
//         return res;
//     }
// }