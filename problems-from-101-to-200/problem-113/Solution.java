class Solution {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            maxArea = Math.max(maxArea, h * w);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}

// Better Solution 
// class Solution {
//     static{
//         for(int i=0;i<60;i++){
//             maxArea(new int[]{0,0});
//         }
//     }
//     public static int maxArea(int[] H) {
//         int l = 0, r = H.length - 1;
//         int maxArea = 0;
//         while (l < r) {
//             int width = r - l;
//             int minH = Math.min(H[l], H[r]);
//             int area = width * minH;
//             maxArea = Math.max(maxArea, area);

//             if (H[l] < H[r]) l++;
//             else r--;
//         }
//         return maxArea;
//     }
// }