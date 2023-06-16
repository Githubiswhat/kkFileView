### ROWNUM

**功能描述**

ROWNUM伪列会在查询时返回行的编号。

**示例**

1、创建临时表并插入数据

```
CREATE TEMPORARY TABLE empsalary (
depname varchar,
empno bigint,
salary int,
enroll_date date
);
INSERT INTO empsalary VALUES
('develop', 10, 5200, '2007-08-01'),
('sales', 1, 5000, '2006-10-01'),
('personnel', 5, 3500, '2007-12-10'),
('sales', 4, 4800, '2007-08-08'),
('personnel', 2, 3900, '2006-12-23'),
('develop', 7, 4200, '2008-01-01'),
('develop', 9, 4500, '2008-01-01'),
('sales', 3, 4800, '2007-08-01'),
('develop', 8, 6000, '2006-10-01'),
('develop', 11, 5200, '2007-08-15');
```

2、执行查询语句。

```
select rownum,depname,empno,salary from empsalary where rownum <3;
```

返回结果为：

```
 rownum | depname | empno | salary
--------+---------+-------+--------
      1 | develop |    10 |   5200
      2 | sales   |     1 |   5000
(2 rows)
```
