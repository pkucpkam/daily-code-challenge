# Exclusive Time of Functions

## Idea

This problem is a classic stack-based timing problem.

A single-threaded CPU executes one function at a time, so the active functions form a stack:

- when a function starts, we push its ID onto the stack
- when it ends, we pop it from the stack

We also need to know how much time each function spent running, including all nested calls.

## Key Observation

If we track the previous timestamp, then the elapsed time between two consecutive logs can be added to the function currently on top of the stack.

For a `start` event:

- if the stack is not empty, the current top function keeps running until this new function starts
- so we add `time - prevTime` to that function
- then push the new function and set `prevTime = time`

For an `end` event:

- the current top function is the one finishing
- it ran from `prevTime` to `time`, inclusive of the end timestamp
- so we add `time - prevTime + 1`
- then pop it and set `prevTime = time + 1`

This handles recursive calls and nested execution correctly.

## Correctness

The stack always represents the current execution path. The function on top is the one actively running when the next log arrives.

Between consecutive timestamps, only the top-of-stack function is executing. Therefore, the elapsed interval must be charged to that function.

When a function starts while another is active, that active function is paused, so its runtime increases by the gap before the new start. When a function ends, we account for the ending timestamp as well, so the exact interval is measured correctly.

Because every interval between logs is assigned to the correct active function and each function is processed in stack order, the final array contains the exact exclusive time for every function.

## Complexity

- **Time:** `O(logs.length)`
- **Space:** `O(n)`

## Java Solution

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        int prevTime = 0;

        for (String log : logs) {
            String[] parts = log.split(":");
            int id = Integer.parseInt(parts[0]);
            String state = parts[1];
            int time = Integer.parseInt(parts[2]);

            if ("start".equals(state)) {
                if (!stack.isEmpty()) {
                    result[stack.peek()] += time - prevTime;
                }
                stack.push(id);
                prevTime = time;
            } else {
                result[stack.peek()] += time - prevTime + 1;
                stack.pop();
                prevTime = time + 1;
            }
        }

        return result;
    }
}
```
