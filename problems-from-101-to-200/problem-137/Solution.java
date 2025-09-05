import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates); 
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> comb, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(comb));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            if (candidates[i] > target) break; 
            comb.add(candidates[i]);
            backtrack(candidates, target - candidates[i], i + 1, comb, res); 
            comb.remove(comb.size() - 1);
        }
    }
}


// Better Solution
// class Solution {
//     /**
//     target = target - ci 
//     if target == 0 {
//         add to List
//     } 
//     if i >= length 
//     return 
    
//     2,5,2,1,2,2 target = 6
//     1,2,2,2,2,5
//      */
//     public List<List<Integer>> combinationSum2(int[] candidates, int target) {
//         sort(candidates);
//         List<List<Integer>> result = new ArrayList<>();
//         backtrack(candidates, 0, target, result, new ArrayList<>());
//         return result;
//     }

//     private void sort(int[] candidates) {
//         for (int i = 0; i < candidates.length; i++) {
//             for (int j = i+1; j < candidates.length; j++) {
//                 if (candidates[j] < candidates[i]) {
//                     int val = candidates[i];
//                     candidates[i] = candidates[j];
//                     candidates[j] = val;
//                 }
//             }
//         }
//     }


//     private void backtrack(int[] candidates, int start, int target, List<List<Integer>> result, List<Integer> path) {
//         if(target == 0) { 
//             result.add(new ArrayList<>(path));
//             return;
//         } 
//         for(int i = start; i < candidates.length; i++) {
//             if(i > start && candidates[i] == candidates[i - 1]) continue; // keeps fist combination and skips the rest
//             if(candidates[i] > target) break;
//             path.add(candidates[i]);
//             backtrack(candidates, i + 1, target - candidates[i], result, path);
//             path.remove(path.size() - 1);
//         }
//     }
// }