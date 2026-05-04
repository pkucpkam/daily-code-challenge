class Solution {
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        if (n <= 1) return;

        // Step 1: Find the median using QuickSelect (O(n) time, O(1) space)
        int median = findKthLargest(nums, (n + 1) / 2);

        // Step 2: 3-way partitioning with virtual indexing
        // Virtual indexing maps:
        // Index 0, 1, 2, 3, 4, 5... to
        // Pos   1, 3, 5, 0, 2, 4...
        int left = 0, i = 0, right = n - 1;

        while (i <= right) {
            if (nums[newIndex(i, n)] > median) {
                swap(nums, newIndex(left++, n), newIndex(i++, n));
            } else if (nums[newIndex(i, n)] < median) {
                swap(nums, newIndex(right--, n), newIndex(i, n));
            } else {
                i++;
            }
        }
    }

    private int newIndex(int i, int n) {
        return (1 + 2 * i) % (n | 1);
    }

    private int findKthLargest(int[] nums, int k) {
        int left = 0, right = nums.length - 1;
        // Target index in a sorted array (ascending) would be n - k
        int target = nums.length - k;
        while (left < right) {
            int pivotIndex = partition(nums, left, right);
            if (pivotIndex == target) {
                return nums[pivotIndex];
            } else if (pivotIndex < target) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }
        return nums[left];
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i++, j);
            }
        }
        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}