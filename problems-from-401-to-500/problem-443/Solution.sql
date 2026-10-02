-- Solution: Using MAX() on Subquery with GROUP BY & HAVING COUNT(num) = 1
-- Time Complexity: O(N) where N is the number of rows in the MyNumbers table
-- Space Complexity: O(U) where U is the number of unique numbers in the MyNumbers table

SELECT MAX(num) AS num
FROM (
    SELECT num
    FROM MyNumbers
    GROUP BY num
    HAVING COUNT(num) = 1
) AS single_numbers;

-- Alternative Solution 1 (Scalar Subquery with ORDER BY & LIMIT 1):
-- In MySQL, wrapping in SELECT ( ... ) ensures NULL is returned when the subquery is empty.
--
-- SELECT (
--     SELECT num
--     FROM MyNumbers
--     GROUP BY num
--     HAVING COUNT(num) = 1
--     ORDER BY num DESC
--     LIMIT 1
-- ) AS num;

-- Alternative Solution 2 (Common Table Expression - CTE):
-- WITH SingleNumbers AS (
--     SELECT num
--     FROM MyNumbers
--     GROUP BY num
--     HAVING COUNT(num) = 1
-- )
-- SELECT MAX(num) AS num
-- FROM SingleNumbers;
