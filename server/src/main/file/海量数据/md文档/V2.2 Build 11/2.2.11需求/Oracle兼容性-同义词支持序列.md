# Oracle兼容性-同义词支持序列

可合入CREATE SYNONYM语法

## 功能描述

Vastbase支持同义词synonym对序列的创建及使用。

## 语法格式

```sql
CREATE [ OR REPLACE ] SYNONYM synonym_name 
    FOR object_name;
```

## 参数说明

无。

## 注意事项

无。

## 示例

1、创建测试表。

```sql
create table tab_1100628(id int);
```

2、创建序列。

```sql
create sequence sequ1_1100628 increment 1 maxvalue 200 start with 1 owned by tab_1100628.id;
```

3、为序列创建同义词。

```sql
create synonym syn1_1100628 for sequ1_1100628;
```

4、调用同义词。

```sql
create function fun_1100628(n int)return int
as
a1 int;
begin
for i in 1..n loop
insert into tab_1100628 value(syn1_1100628.nextval);
end loop;
select count(*) into a1 from tab_1100628;
return a1;
end;
/
```

4、调用函数。

```sql
select fun_1100628(3);
```

返回结果为：

```sql
fun_1100628
-------------
           3
(1 row)
```

```sql
select * from tab_1100628;
```

返回结果为：

```sql
 id
----
  2
  3
  4
(3 rows)
```

```sql
select syn1_1100628.currval;
```

返回结果为：

```sql
 fun_1100628
-------------
           3
(1 row)
```

