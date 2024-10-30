class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        num_dict = {}
        
        for i, num in enumerate(nums):
            complement = target - num
            if complement in num_dict:
                return [num_dict[complement], i]
            num_dict[num] = i
