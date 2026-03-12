# Ý tưởng

Mục tiêu là lấy nhân viên có lương cao nhất trong từng phòng ban.

Một phòng ban có thể có nhiều người cùng mức lương cao nhất, nên kết quả cần trả về tất cả các nhân viên đồng hạng cao nhất.

## Truy vấn

```sql
SELECT d.name AS Department,
			 e.name AS Employee,
			 e.salary AS Salary
FROM Employee e
JOIN Department d
	ON e.departmentId = d.id
JOIN (
	SELECT departmentId,
				 MAX(salary) AS max_sal
	FROM Employee
	GROUP BY departmentId
) m
	ON e.departmentId = m.departmentId
 AND e.salary = m.max_sal;
```

## Giải thích

- Join `Employee` với `Department` để lấy tên phòng ban.
- Bảng tạm `m` tính trước mức lương cao nhất (`MAX(salary)`) cho từng `departmentId`.
- Join `Employee` với `m` theo 2 điều kiện:
	- cùng `departmentId`
	- `e.salary = m.max_sal`
- Nhờ đó chỉ giữ nhân viên có lương cao nhất của từng phòng ban.
- Nếu có nhiều người cùng lương cao nhất trong một phòng ban, tất cả đều được trả về.

## Vì sao đây là better solution

- Mức lương cao nhất mỗi phòng ban được tính một lần trong subquery tổng hợp.
- Truy vấn thường dễ được tối ưu tốt trên dữ liệu lớn hơn cách correlated subquery.
- Logic tách bạch: bước 1 tìm `max salary` theo phòng ban, bước 2 lấy nhân viên khớp mức đó.

## Độ đúng

- Với mỗi phòng ban, `m.max_sal` đúng là mức lương lớn nhất do dùng `MAX(salary)` và `GROUP BY departmentId`.
- Điều kiện `e.salary = m.max_sal` đảm bảo chỉ lấy nhân viên có lương cao nhất.
- Không bỏ sót trường hợp đồng hạng vì mọi bản ghi thỏa điều kiện đều được giữ lại.

## Độ phức tạp

- Trong thực tế, hiệu năng còn phụ thuộc vào optimizer và chỉ mục.
- Chỉ mục hữu ích: `Employee(departmentId, salary)`.
