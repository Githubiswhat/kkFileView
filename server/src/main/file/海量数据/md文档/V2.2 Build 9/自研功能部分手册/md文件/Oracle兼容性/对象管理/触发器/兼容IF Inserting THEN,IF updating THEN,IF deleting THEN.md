### 兼容IF Inserting THEN,IF updating THEN,IF deleting THEN

**功能描述**

该功能允许在触发器函数中使用IF Inserting THEN，IF updating THEN和IF deleting THEN命令来判断增加、修改和删除记录的dml操作。

**注意事项**

无。

**示例**

- **IF Inserting THEN**

1、创建测试表。

```
create table  tri_tbl(id int, name varchar(256));
```

2、创建函数。

```
CREATE OR REPLACE FUNCTION tri_func() RETURNS TRIGGER AS
$$
DECLARE
n_id int := new.id;
o_id int := old.id;
BEGIN
raise notice 'trigger begin: TG_OP is %',TG_OP;
if inserting then
raise notice 'old id:%,old name:%',o_id,old.name;
raise notice 'new id:%,new name:%',n_id,new.name;
end if;
return new;
END $$ LANGUAGE plpgsql;
```

3、创建触发器。

```
CREATE TRIGGER be_tri
BEFORE INSERT OR DELETE OR UPDATE
ON tri_tbl
FOR EACH ROW
EXECUTE PROCEDURE tri_func();
```

4、调用函数。

```
insert into tri_tbl values(1,'aaaaa'),(2,'bbb');
```

返回结果为：

```
NOTICE:  trigger begin: TG_OP is INSERT
NOTICE:  old id:<NULL>,old name:<NULL>
NOTICE:  new id:1,new name:aaaaa
NOTICE:  trigger begin: TG_OP is INSERT
NOTICE:  old id:<NULL>,old name:<NULL>
NOTICE:  new id:2,new name:bbb
INSERT 0 2
```

- **IF updating THEN**

1、创建测试表并插入数据。

```
CREATE TABLE tri_tbl1(id int, name varchar(256));
insert into tri_tbl1 values(1,null),(1,'bbb');
```

2、创建函数。

```
CREATE OR REPLACE FUNCTION tri_func() RETURNS TRIGGER AS
$$
DECLARE
n_id int := new.id;
o_id int := old.id;
BEGIN
raise notice 'trigger begin: TG_OP is %',TG_OP;
if updating then
raise notice 'old id:%,old name:%',o_id,old.name;
raise notice 'new id:%,new name:%',n_id,new.name;
end if;
return new;
END $$ LANGUAGE plpgsql;
```

3、创建触发器。

```
CREATE TRIGGER be_tri
BEFORE INSERT OR DELETE OR UPDATE
ON tri_tbl1
FOR EACH ROW
EXECUTE PROCEDURE tri_func();
```

4、调用函数。

```
update tri_tbl1 set name='aaaaa' where id=1;
```

返回结果为：

```
NOTICE:  old id:1,old name:<NULL>
NOTICE:  new id:1,new name:aaaaa
NOTICE:  trigger begin: TG_OP is UPDATE
NOTICE:  old id:1,old name:bbb
NOTICE:  new id:1,new name:aaaaa
UPDATE 2
```

- **IF deleting THEN**

1、创建测试表并插入数据。

```
CREATE TABLE tri_tb(id int, name varchar(256));
insert into tri_tb values(1,null),(1,'aaa');
```

2、创建函数。

```
CREATE OR REPLACE FUNCTION tri_func1() RETURNS TRIGGER AS
$$
DECLARE
n_id int := new.id;
o_id int := old.id;
BEGIN
raise notice 'trigger begin: TG_OP is %',TG_OP;
if deleting then
raise notice 'old id:%,old name:%',o_id,old.name;
raise notice 'new id:%,new name:%',n_id,new.name;
end if;
return new;
END $$ LANGUAGE plpgsql;
```

3、创建触发器。

```
CREATE TRIGGER be_tri
BEFORE INSERT OR DELETE OR UPDATE
ON tri_tb
FOR EACH ROW
EXECUTE PROCEDURE tri_func1();
```

4、调用函数。

```
delete tri_tb where id=1;
```

返回结果为：

```
NOTICE:  trigger begin: TG_OP is DELETE
NOTICE:  old id:1,old name:<NULL>
NOTICE:  new id:<NULL>,new name:<NULL>
NOTICE:  trigger begin: TG_OP is DELETE
NOTICE:  old id:1,old name:aaa
NOTICE:  new id:<NULL>,new name:<NULL>
DELETE 2
```
