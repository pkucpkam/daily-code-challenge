import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); 

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++; 
                } else {
                    right--;
                }
            }
        }
        return res;
    }
}

// Better Solution 
// import java.util.*;

// class Solution {
//     public List<List<Integer>> threeSum(int[] arr) {
//         return new AbstractList<List<Integer>>() {
//             List<List<Integer>> ans;

//             @Override
//             public int size() {
//                 if (ans == null) ans = createList(arr);
//                 return ans.size();
//             }

//             @Override
//             public List<Integer> get(int index) {
//                 if (ans == null) ans = createList(arr);
//                 return ans.get(index);
//             }
//         };
//     }

//     private List<List<Integer>> createList(int[] arr) {
//         List<List<Integer>> ans = new ArrayList<>();
//         if (arr == null || arr.length < 3) return ans;

//         Arrays.sort(arr);
//         int n = arr.length;

//         for (int i = 0; i < n - 2 && arr[i] <= 0; i++) {
//             if (i > 0 && arr[i] == arr[i - 1]) continue;
//             twoSum(ans, arr, i, i + 1, n - 1);         
//         }
//         return ans;
//     }

//     private void twoSum(List<List<Integer>> ans, int[] arr, int i, int left, int right) {
//         int target = -arr[i];
//         while (left < right) {
//             int sum = arr[left] + arr[right];
//             if (sum == target) {
//                 ans.add(Arrays.asList(arr[i], arr[left], arr[right]));
//                 int lv = arr[left], rv = arr[right];
//                 while (left < right && arr[left] == lv) left++;
//                 while (left < right && arr[right] == rv) right--;
//             } else if (sum < target) {
//                 int lv = arr[left];
//                 while (left < right && arr[left] == lv) left++; 
//             } else {
//                 int rv = arr[right];
//                 while (left < right && arr[right] == rv) right--;
//             }
//         }
//     }
// }