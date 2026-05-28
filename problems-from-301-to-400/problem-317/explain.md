# 385. Mini Parser - Explanation

## Problem Overview

We need to convert a serialized nested list string into a `NestedInteger`.

Each element can be:

* A single integer, like `324`
* A list, like `[123,[456,[789]]]`

The parser must support nested lists, negative numbers, commas, and square brackets.

## Best Approach: Iterative Stack Parser

The best approach is to scan the string once and use a stack to remember parent lists.

There are two important cases:

* If the string does not start with `[`, the whole input is a single integer.
* Otherwise, the input is a nested list and we build it while scanning from left to right.

When we see:

1. `[`: start a new list. If we were already building a list, push the old one onto the stack as the parent.
2. `]`: finish the current list. If there is a parent on the stack, pop it and add the finished list into it.
3. `,`: skip it because commas only separate elements.
4. A digit or `-`: parse the full integer and add it to the current list.

This avoids recursive parsing, so it is safer for inputs with very deep nesting.

## Walkthrough

For:

```text
s = "[123,[456,[789]]]"
```

The parser builds:

```text
[
  123,
  [
    456,
    [
      789
    ]
  ]
]
```

Each time a nested list closes, it is added to its parent list.

## Implementation Details

Single integer input is handled first:

```java
if (s.charAt(0) != '[') {
    return new NestedInteger(Integer.parseInt(s));
}
```

For list input, `current` stores the list currently being built, and `stack` stores its parent lists.

When parsing an integer, continue while the characters are digits. This correctly handles multi-digit and negative values:

```java
int start = i;

if (s.charAt(i) == '-') {
    i++;
}

while (i < s.length() && Character.isDigit(s.charAt(i))) {
    i++;
}

current.add(new NestedInteger(Integer.parseInt(s.substring(start, i))));
```

## Complexity

Let `n` be `s.length()`.

* Time complexity: `O(n)` because each character is processed once.
* Space complexity: `O(d)` for the stack, where `d` is the maximum nesting depth.

The returned `NestedInteger` itself uses `O(n)` space to store the parsed structure.

## Code

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public NestedInteger deserialize(String s) {
        if (s.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(s));
        }

        Deque<NestedInteger> stack = new ArrayDeque<>();
        NestedInteger current = null;

        for (int i = 0; i < s.length();) {
            char c = s.charAt(i);

            if (c == '[') {
                if (current != null) {
                    stack.push(current);
                }

                current = new NestedInteger();
                i++;
            } else if (c == ']') {
                if (!stack.isEmpty()) {
                    NestedInteger completed = current;
                    current = stack.pop();
                    current.add(completed);
                }

                i++;
            } else if (c == ',') {
                i++;
            } else {
                int start = i;

                if (s.charAt(i) == '-') {
                    i++;
                }

                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }

                current.add(new NestedInteger(Integer.parseInt(s.substring(start, i))));
            }
        }

        return current;
    }
}
```
