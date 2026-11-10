# 677. Map Sum Pairs

**Difficulty:** Medium 🟡  
**Topics:** Hash Table, Trie, Design  
**Companies:** premium lock icon  

---

## Problem Statement

Design a map that allows you to do the following:

- Map a string key to a given value.
- Return the sum of the values that have a key with a prefix equal to a given string.

Implement the `MapSum` class:

- `MapSum()` Initializes the `MapSum` object.
- `void insert(String key, int val)` Inserts the key-value pair into the map. If the key already exists, the original key-value pair is overridden with the new one.
- `int sum(String prefix)` Returns the sum of all the pairs' values whose key starts with the prefix.

---

## Examples

### Example 1

```text
Input:
["MapSum", "insert", "sum", "insert", "sum"]
[[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]

Output:
[null, null, 3, null, 5]
```

**Explanation:**

```java
MapSum mapSum = new MapSum();
mapSum.insert("apple", 3);
mapSum.sum("ap"); // return 3 (apple = 3)
mapSum.insert("app", 2);
mapSum.sum("ap"); // return 5 (apple + app = 3 + 2 = 5)
```

---

## Constraints

- `1 <= key.length, prefix.length <= 50`
- `key` and `prefix` consist of only lowercase English letters.
- `1 <= val <= 1000`
- At most `50` calls will be made to `insert` and `sum`.
