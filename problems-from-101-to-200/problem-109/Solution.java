class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            m = nums1.length;
            n = nums2.length;
        }

        int imin = 0, imax = m, halfLen = (m + n + 1) / 2;
        while (imin <= imax) {
            int i = (imin + imax) / 2;
            int j = halfLen - i;
            if (i < m && nums2[j - 1] > nums1[i]) {
                // i is too small
                imin = i + 1;
            } else if (i > 0 && nums1[i - 1] > nums2[j]) {
                // i is too big
                imax = i - 1;
            } else {
                // i is perfect
                int maxLeft = 0;
                if (i == 0) maxLeft = nums2[j - 1];
                else if (j == 0) maxLeft = nums1[i - 1];
                else maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);

                if ((m + n) % 2 == 1) return maxLeft;

                int minRight = 0;
                if (i == m) minRight = nums2[j];
                else if (j == n) minRight = nums1[i];
                else minRight = Math.min(nums1[i], nums2[j]);

                return (maxLeft + minRight) / 2.0;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}

// Better solution
// class Solution {
//     public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//         if (nums1 == null || nums2 == null) {
//             return 0;
//         } 

//         // assume nums1 length n and nums2 length m 
//         // if (m + n) odd : (m + n) / 2
//         // else (m + n) / 2 - 1
//         // We just need to find (m + n) / 2 and (m + n) / 2 - 1
//         // each iteration find ith smallest elements by using 2 pointers
//         int r1 = 0, r2 = 0;
//         // var to store final result
//         double prev = 0, cur = 0;
//         int n = nums1.length, m = nums2.length;
//         for (int i = 0; i <= (m + n) / 2; i++) {
//             prev = cur;
//             if (r1 >= nums1.length) {
//                 cur = nums2[r2++];
//             } else if (r2 >= nums2.length) {
//                 cur = nums1[r1++];
//             } else if (nums1[r1] <= nums2[r2]) {
//                 cur = nums1[r1++];
//             } else {
//                 cur = nums2[r2++];
//             }
//         }
//         if ((m + n) % 2 == 0) {
//             return (prev + cur) / 2;
//         }
//         return cur;

//     }
// }