# 241. Different Ways to Add Parentheses

**Difficulty:** Medium

Given a string expression of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators.

You may return the answer in any order.

The test cases are generated such that:
- Output values fit in a 32-bit integer.
- The number of different results does not exceed 10^4.

## Example 1

Input:
```text
expression = "2-1-1"
```

Output:
```text
[0,2]
```

Explanation:
```text
((2-1)-1) = 0
(2-(1-1)) = 2
```

## Example 2

Input:
```text
expression = "2*3-4*5"
```

Output:
```text
[-34,-14,-10,-10,10]
```

Explanation:
```text
(2*(3-(4*5))) = -34
((2*3)-(4*5)) = -14
((2*(3-4))*5) = -10
(2*((3-4)*5)) = -10
(((2*3)-4)*5) = 10
```

## Constraints

- 1 <= expression.length <= 20
- expression consists of digits and the operator '+', '-', and '*'.
- All integer values in the input expression are in the range [0, 99].
- Integer values in the input expression do not have a leading '-' or '+' sign.