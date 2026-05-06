# 331. Verify Preorder Serialization of a Binary Tree - Explanation

## Intuition: The "Slots" Concept

To verify if a serialization is valid without actually building the tree, we can use the property of **available slots** in a binary tree.

Imagine you are building the tree step-by-step following the preorder traversal:

1.  **Initial State:** Before you start, you have **1 available slot** where the root of the tree must be placed.
2.  **Processing a Node:** For every node you encounter in the string:
    -   You **must** use up exactly **1 existing slot** to place that node.
    -   If it is a **non-null node** (a number), it will "grow" **2 new slots** (one for its left child and one for its right child).
    -   If it is a **null node** (`#`), it signifies the end of a branch, so it creates **0 new slots**.

## The Algorithm

We maintain a variable `slots` (starting at 1). We split the input string by commas and iterate through each element:

1.  **Decrease `slots` by 1** for the current node.
2.  **Check for validity:** If at any point (before or after decreasing) `slots` becomes negative while we still have nodes to process, the serialization is invalid. 
    *   *Note: If `slots` hits 0, it means the tree is "complete". If there are more nodes after this point, it's invalid.*
3.  **If the node is not `#`**, increment `slots` by **2**.
4.  **Final Check:** After processing all elements, the tree is valid only if `slots` is exactly **0**.

---

### Step-by-Step Example: `"9,3,4,#,#,1,#,#,2,#,6,#,#"`

| Node | Action | Slots Change | Current Slots |
| :--- | :--- | :--- | :--- |
| (Start) | | | 1 |
| `9` | Consume 1, Add 2 | 1 - 1 + 2 | 2 |
| `3` | Consume 1, Add 2 | 2 - 1 + 2 | 3 |
| `4` | Consume 1, Add 2 | 3 - 1 + 2 | 4 |
| `#` | Consume 1, Add 0 | 4 - 1 | 3 |
| `#` | Consume 1, Add 0 | 3 - 1 | 2 |
| `1` | Consume 1, Add 2 | 2 - 1 + 2 | 3 |
| `#` | Consume 1, Add 0 | 3 - 1 | 2 |
| `#` | Consume 1, Add 0 | 2 - 1 | 1 |
| `2` | Consume 1, Add 2 | 1 - 1 + 2 | 2 |
| `#` | Consume 1, Add 0 | 2 - 1 | 1 |
| `6` | Consume 1, Add 2 | 1 - 1 + 2 | 2 |
| `#` | Consume 1, Add 0 | 2 - 1 | 1 |
| `#` | Consume 1, Add 0 | 1 - 1 | 0 |
| **End** | | **Final** | **0 (Valid)** |

---

## Complexity Analysis

-   **Time Complexity:** $O(N)$
    -   We iterate through the input string once. Splitting the string and the loop both take time proportional to the length of the string.
-   **Space Complexity:** $O(N)$
    -   In Java, `preorder.split(",")` creates an array of strings that takes $O(N)$ space.
    -   *Optimization:* We could achieve $O(1)$ space by using a pointer or a `Scanner` to parse the string manually without creating the array.
