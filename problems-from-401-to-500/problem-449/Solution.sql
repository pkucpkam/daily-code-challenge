-- Solution 1 (Best & Modern): Window Functions (LEAD & LAG)
-- Time Complexity: O(N) where N is the number of rows (single pass over sorted index)
-- Space Complexity: O(N) auxiliary space for window frame evaluation

SELECT 
    id,
    CASE 
        WHEN id % 2 = 1 THEN COALESCE(LEAD(student) OVER (ORDER BY id), student)
        ELSE LAG(student) OVER (ORDER BY id)
    END AS student
FROM Seat
ORDER BY id ASC;

-- =====================================================================
-- Alternative 1 (Classic ANSI SQL): Modifying `id` with CASE WHEN & Subquery
-- Time Complexity: O(N log N) due to ORDER BY on the calculated id
-- Space Complexity: O(N) auxiliary space for sorting
-- =====================================================================
-- SELECT 
--     CASE 
--         WHEN id % 2 = 1 AND id = (SELECT COUNT(*) FROM Seat) THEN id
--         WHEN id % 2 = 1 THEN id + 1
--         ELSE id - 1
--     END AS id,
--     student
-- FROM Seat
-- ORDER BY id ASC;

-- =====================================================================
-- Alternative 2: Bitwise XOR Trick with CASE WHEN
-- ((id - 1) ^ 1) + 1 swaps odd and even numbers (1 <-> 2, 3 <-> 4, ...)
-- =====================================================================
-- SELECT 
--     CASE 
--         WHEN id % 2 = 1 AND id = (SELECT COUNT(*) FROM Seat) THEN id
--         ELSE (id - 1) ^ 1 + 1
--     END AS id,
--     student
-- FROM Seat
-- ORDER BY id ASC;

-- =====================================================================
-- Alternative 3: Self LEFT JOIN
-- =====================================================================
-- SELECT 
--     s1.id,
--     COALESCE(s2.student, s1.student) AS student
-- FROM Seat s1
-- LEFT JOIN Seat s2 
--     ON (s1.id % 2 = 1 AND s1.id + 1 = s2.id)
--     OR (s1.id % 2 = 0 AND s1.id - 1 = s2.id)
-- ORDER BY s1.id ASC;
