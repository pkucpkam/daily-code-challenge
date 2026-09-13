-- Solution: Window Functions (COUNT OVER) - Modern & Clean
-- Time Complexity: O(N log N) due to partitioning/sorting
-- Space Complexity: O(N) for window buffers

WITH InsuranceStats AS (
    SELECT 
        tiv_2016,
        COUNT(*) OVER(PARTITION BY tiv_2015) AS count_tiv_2015,
        COUNT(*) OVER(PARTITION BY lat, lon) AS count_lat_lon
    FROM Insurance
)
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM InsuranceStats
WHERE count_tiv_2015 > 1 
  AND count_lat_lon = 1;

-- Alternative Solution 1: Subquery with IN & GROUP BY / HAVING (MySQL / PostgreSQL)
-- SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
-- FROM Insurance
-- WHERE tiv_2015 IN (
--     SELECT tiv_2015
--     FROM Insurance
--     GROUP BY tiv_2015
--     HAVING COUNT(*) > 1
-- )
-- AND (lat, lon) IN (
--     SELECT lat, lon
--     FROM Insurance
--     GROUP BY lat, lon
--     HAVING COUNT(*) = 1
-- );

-- Alternative Solution 2: Correlated Subqueries with EXISTS & NOT EXISTS
-- SELECT ROUND(SUM(i.tiv_2016), 2) AS tiv_2016
-- FROM Insurance i
-- WHERE EXISTS (
--     SELECT 1
--     FROM Insurance i2
--     WHERE i2.pid <> i.pid AND i2.tiv_2015 = i.tiv_2015
-- )
-- AND NOT EXISTS (
--     SELECT 1
--     FROM Insurance i3
--     WHERE i3.pid <> i.pid AND i3.lat = i.lat AND i3.lon = i.lon
-- );
