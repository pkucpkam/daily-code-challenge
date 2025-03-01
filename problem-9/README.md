# Valid Parentheses
## Description

Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.

## Example
Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "()[]{}"
Output: true

Example 3:
Input: s = "(]"
Output: false

Example 4:
Input: s = "([])"
Output: true

Constraints:
1 <= s.length <= 104
s consists of parentheses only '()[]{}'.


## Best Solution 
class Solution {
    public boolean isValid(String s) {

        if( s.length() == 1 )return false;
        int i = -1;
        char[] stack = new char[s.length()];

        for( char ch : s.toCharArray() ){
            if( ch =='(' || ch=='{' || ch=='[' ){
                i = i+1;
                stack[i] = ch;
            }
            else{
                if( i>=0 && ( (stack[i]=='(' && ch==')') || (stack[i]=='{' && ch=='}') || (stack[i]=='[' && ch==']') ) ){
                    i = i-1;
                }
                else{
                    return false;
                }
            }
        }
        return i==-1;
    }
}