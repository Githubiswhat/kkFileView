### 定义游标支持INTO关键字

**功能描述**

支持cursor xxx for/is select ... into ...用法。

**语法格式**

```
DECLARE
    CURSOR name FOR SELECT columns INTO ... FROM table_name;
```

**参数据说明**
INTO ... ：可以是声明的变量，数量与SELECT ...一致。

**注意事项**

无。

**示例**

1、创建测试表并插入数据。

```
CREATE TABLE stu_info
(
id INT NOT NULL DEFAULT 0,
name TEXT
);

INSERT INTO stu_info (id,name) VALUES(1,'name01');
INSERT INTO stu_info (id,name) VALUES(2,'name02');
INSERT INTO stu_info (id,name) VALUES(3,'name03');
```

2、定义一个游标。

```
do language plpgsql $$
declare
v_id int;
v_name TEXT;
cursor cur1 for
select id,name into v_id,v_name from stu_info where id>1;
begin
open cur1;
loop
fetch cur1 into v_id,v_name;
if found then
update stu_info set name='test' where id = v_id;
else
exit;
end if;
end loop;
end;
$$;
```

3、查询表中数据。

```
select * from stu_info;
```

返回结果如下，则表示游标定义成功：

```
 id |  name
----+--------
  1 | name01
  2 | test
  3 | test
(3 rows)
```
