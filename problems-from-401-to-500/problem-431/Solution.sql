-- Solution: GROUP BY & HAVING COUNT(student) >= 5
-- Time Complexity: O(N) where N is the number of rows in the Courses table
-- Space Complexity: O(K) where K is the number of distinct classes

SELECT class
FROM Courses
GROUP BY class
HAVING COUNT(student) >= 5;

-- Alternative Solution 1 (Defensive against duplicate enrollments: COUNT(DISTINCT student)):
-- In cases where (student, class) is not guaranteed to be a primary key,
-- COUNT(DISTINCT student) prevents counting the same student multiple times.
--
-- SELECT class
-- FROM Courses
-- GROUP BY class
-- HAVING COUNT(DISTINCT student) >= 5;

-- Alternative Solution 2 (Subquery approach):
-- SELECT class
-- FROM (
--     SELECT class, COUNT(student) AS student_count
--     FROM Courses
--     GROUP BY class
-- ) AS temp
-- WHERE student_count >= 5;
