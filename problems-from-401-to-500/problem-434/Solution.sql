-- Solution: UNION ALL & GROUP BY & ORDER BY LIMIT 1
-- Time Complexity: O(N log N) due to sorting (or O(N) with top-1 heap)
-- Space Complexity: O(N) to store temporary unioned table and aggregation hash table

SELECT id, COUNT(*) AS num
FROM (
    SELECT requester_id AS id FROM RequestAccepted
    UNION ALL
    SELECT accepter_id AS id FROM RequestAccepted
) AS all_friends
GROUP BY id
ORDER BY num DESC
LIMIT 1;

-- =====================================================================
-- Alternative 1: CTE (Common Table Expression) version
-- =====================================================================
-- WITH AllFriends AS (
--     SELECT requester_id AS id FROM RequestAccepted
--     UNION ALL
--     SELECT accepter_id AS id FROM RequestAccepted
-- )
-- SELECT id, COUNT(*) AS num
-- FROM AllFriends
-- GROUP BY id
-- ORDER BY num DESC
-- LIMIT 1;

-- =====================================================================
-- Solution for Follow-up: Handle multiple people having the same most friends
-- Using DENSE_RANK() window function
-- =====================================================================
-- WITH FriendCounts AS (
--     SELECT id, COUNT(*) AS num
--     FROM (
--         SELECT requester_id AS id FROM RequestAccepted
--         UNION ALL
--         SELECT accepter_id AS id FROM RequestAccepted
--     ) AS all_friends
--     GROUP BY id
-- ),
-- RankedFriends AS (
--     SELECT id, num, DENSE_RANK() OVER (ORDER BY num DESC) AS rnk
--     FROM FriendCounts
-- )
-- SELECT id, num
-- FROM RankedFriends
-- WHERE rnk = 1;
