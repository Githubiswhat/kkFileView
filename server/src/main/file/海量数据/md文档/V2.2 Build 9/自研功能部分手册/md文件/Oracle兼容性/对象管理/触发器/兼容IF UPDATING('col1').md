### 兼容IF UPDATING('col1')

**功能描述**

允许触发器函数中对单个表字段的修改使用IF UPDATING('col1')进行判断单个表字段的更新。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

1、检查数据库兼容模式DBCOMPATIBILITY为A（即兼容Oracle）。

```
show sql_compatibility;
```

2、创建测试表并插入数据。

```
DROP TABLE IF EXISTS tri_tbl;
CREATE TABLE tri_tbl(id int, name varchar(256),note_pad varchar(256));
insert into tri_tbl values(1,null,'aaa');
insert into tri_tbl values(1,null,'aaa');
```

3、创建函数。

```
CREATE OR REPLACE FUNCTION tri_func() RETURNS TRIGGER AS
$$
DECLARE
n_id int := new.id;
o_id int := old.id;
BEGIN
raise notice 'trigger begin: TG_OP is %',TG_OP;
if updating('name') then
raise notice 'old name:%',old.name;
raise notice 'new name:%',new.name;
end if;
if updating('note_pad') then
raise notice 'old note_pad:%',old.note_pad;
raise notice 'new note_pad:%',new.note_pad;
end if;
return new;
END $$ LANGUAGE 'plpgsql';
```

4、创建触发器。

```
CREATE TRIGGER be_tri
BEFORE INSERT OR DELETE OR UPDATE
ON tri_tbl
FOR EACH ROW
EXECUTE PROCEDURE tri_func();
```

5、调用函数。

```
update tri_tbl set name='bbb',note_pad='666' where id=1;
```

返回结果为：

```
NOTICE: trigger begin: TG_OP is UPDATE
NOTICE: old name:<null>
NOTICE: new name:bbb
NOTICE: old note_pad:aaa
NOTICE: new note_pad:666
UPDATE 1
```
