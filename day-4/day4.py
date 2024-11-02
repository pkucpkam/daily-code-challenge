class Solution(object):
    def lengthOfLongestSubstring(self, s):
        """
        :type s: str
        :rtype: int
        """
        char_map = {}
        start = 0
        max_len = 0

        for end in range(len(s)):
            if s[end] in char_map and char_map[s[end]] >= start:
                start = char_map[s[end]] + 1

            char_map[s[end]] = end
            max_len = max(max_len, end - start + 1)

        return max_len
