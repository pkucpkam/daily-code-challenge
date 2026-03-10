SELECT
    s.score,
    (
        SELECT COUNT(DISTINCT s2.score)
        FROM Scores s2
        WHERE s2.score >= s.score
    ) AS `rank`
FROM Scores s
ORDER BY s.score DESC;


Select 
    score,
    DENSE_RANK() OVER(ORDER BY SCORE DESC) AS 'rank'
FROM Scores;