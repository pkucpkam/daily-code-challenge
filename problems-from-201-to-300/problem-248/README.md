# 196. Delete Duplicate Emails

**Difficulty:** Easy

## Problem Description

Write a solution to delete all duplicate emails, keeping only one unique email with the smallest id.

> **Note for SQL users:** Write a DELETE statement, not a SELECT.  
> **Note for Pandas users:** Modify Person in place.

## Table Schema

### Person

| Column Name | Type    |
|-------------|---------|
| id          | int     |
| email       | varchar |

**Constraints:**
- `id` is the primary key (column with unique values)
- Emails do not contain uppercase letters

## Requirements

After running your script, the final Person table will be shown. The driver will:
1. Compile and run your code
2. Display the resulting Person table
3. Order of results does not matter

## Example

### Input

```
Person table:
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
```

### Output

```
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
```

**Explanation:** 
`john@example.com` is repeated twice. We keep the row with the smallest ID = 1.