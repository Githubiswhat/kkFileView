### 允许INSERT使用oid.colname获取值为空

**功能描述**

兼容Oracle的触发器特性，允许触发器在指定条件下取NULL值。在触发器中，支持使用oid.colname语法获取dml操作之前的旧值，如果之前是insert操作，oid.colname结果为空，如果之前值update和delete操作，oid.colname语法获取的值为操作之前的旧值。


**注意事项**

无。

**示例**

1、创建测试表。

```
CREATE TABLE tri_tbl(id int, name varchar(256));
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
if TG_OP='INSERT' then
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
insert into tri_tbl values(1,'aaaaa');
```

返回结果为：

```
NOTICE:  trigger begin: TG_OP is INSERT
NOTICE:  old id:<NULL>,old name:<NULL>
NOTICE:  new id:1,new name:aaaaa
INSERT 0 1
```