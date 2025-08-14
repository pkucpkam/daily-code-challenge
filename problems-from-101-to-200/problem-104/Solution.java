class Solution {
    public String[] findRelativeRanks(int[] score) {
        int n = score.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        java.util.Arrays.sort(idx, (a, b) -> score[b] - score[a]);
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) res[idx[i]] = "Gold Medal";
            else if (i == 1) res[idx[i]] = "Silver Medal";
            else if (i == 2) res[idx[i]] = "Bronze Medal";
            else res[idx[i]] = String.valueOf(i + 1);
        }
        return res;
    }
}


// Use Queue
// class Solution {
//     public String[] findRelativeRanks(int[] score) {        
//         int n = score.length;
//         String[] res = new String[n];
//         PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->score[b]-score[a]);
//         for(int i=0;i<n;i++){
//             pq.add(i);
//         }
//         int i=1;
//         while(!pq.isEmpty()){
//             int idx = pq.poll();
//             if(i>3){
//                 res[idx] = Integer.toString(i);
//             }else if(i==1){
//                 res[idx] = "Gold Medal";
//             }else if(i==2){
//                 res[idx] = "Silver Medal";
//             }else if(i==3){
//                 res[idx] = "Bronze Medal";
//             }
//             i++;
//         }
//         return res;   
//     }
// }