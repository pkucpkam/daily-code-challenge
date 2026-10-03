-- Solution: Filtering odd-numbered IDs and non-boring descriptions, ordered by rating DESC
-- Time Complexity: O(N log N) where N is the number of rows in the Cinema table (due to sorting)
-- Space Complexity: O(N) auxiliary space for sorting and result set

SELECT 
    id, 
    movie, 
    description, 
    rating
FROM Cinema
WHERE id % 2 = 1
  AND description <> 'boring'
ORDER BY rating DESC;

-- Alternative Solution 1 (Using MOD function - standard ANSI SQL):
-- SELECT id, movie, description, rating
-- FROM Cinema
-- WHERE MOD(id, 2) = 1
--   AND description <> 'boring'
-- ORDER BY rating DESC;

-- Alternative Solution 2 (Using Bitwise AND operator - fastest low-level arithmetic):
-- SELECT id, movie, description, rating
-- FROM Cinema
-- WHERE (id & 1) = 1
--   AND description != 'boring'
-- ORDER BY rating DESC;
