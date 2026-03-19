-- ⭐ BEST SOLUTION: Self-Join (Nhanh & Hiệu quả)
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)
WHERE w1.temperature > w2.temperature;


-- Alternative 1: LAG() Window Function (Modern & Sạch)
SELECT id
FROM (
    SELECT id,
           temperature,
           LAG(temperature) OVER (ORDER BY recordDate) AS prev_temp,
           LAG(recordDate) OVER (ORDER BY recordDate) AS prev_date
    FROM Weather
) AS w
WHERE recordDate = DATE_ADD(prev_date, INTERVAL 1 DAY)
  AND temperature > prev_temp;


-- Alternative 2: DATEDIFF (Đơn giản)
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON DATEDIFF(w1.recordDate, w2.recordDate) = 1
WHERE w1.temperature > w2.temperature;