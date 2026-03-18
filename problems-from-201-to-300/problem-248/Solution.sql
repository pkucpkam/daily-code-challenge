-- Best Solution: DELETE với JOIN (Hiệu quả nhất)
DELETE p1
FROM Person p1, Person p2
WHERE p1.email = p2.email AND p1.id > p2.id;


delete from Person
where id not in (
    select id
    from (
        select min(id) as id
        from Person
        group by email
    ) temp
);