####  支持default分区

**功能描述**

针对range，list和hash分区，支持定义和使用default分区。创建分区表的时候可以指定创建default分区，对于已经创建的分区表，可以新增和删除default分区。

Range类型的分区表，可以通过指定分区范围值为MAXVALUE来包含所有情况的值。

Hash类型的分区表，由于Hash分区表的特性，必然可以包含所有情况的值。



**语法格式**

- 创建分区表时指定创建default分区，defaultf分区必须定义为最后一个分区，SQL语法如下：

```
CREATE TABLE [IF NOT EXISTS]table_name
...
PARTITION BY [RANGE | LIST| HASH]({column_name|(expression)})
(
  PARTITION partition_name...,
  PARTITION partition_name VALUES(DEFAULT)[TABLESPACE tablespace_name]
)
```

- 新增default分区(已存在default分区的LIST分区不能新增分区)SQL语法如下：

```
ALTER TABLE table_name ADD PARTITION partition_name VALUES(DEFAULT)
[TABLESPACE tablespace_name];
```

- 删除default分区(不能只剩下一个default分区)SQL语法如下：

```
ALTER TABLE partition_name DROP PARTITION partition_name;
```

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 在创建分区表时，不能只定义一个default分区并且default分区必须定义在最后。
- 已经存在default分区的分区表，不能新增分区。
- 删除分区时，不能只剩下一个default分区。
- default分区不能进行SPLIT和MERGE操作。

**示例**

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';     
\c db_oracle
```

2、创建一个分区表。

```
create table t_partition_list4(col number,name varchar2(20))
partition by list(col)(
partition t_list_p1 values(1,3,5,7,9) ,
partition t_list_p2 values(2,4,6,8,10) );
```

2、添加一个默认分区并插入数据。

```
alter table t_partition_list4 add partition t_list_default values(default);
insert into t_partition_list4 values(1,'t_list_p1');
insert into t_partition_list4 values(2,'t_list_p2');
insert into t_partition_list4 values(31,'t_list_default');
```

3、查询分区表中的数据。

- 查询分区表中的数据t_list_p1.

```
select * from t_partition_list4 partition(t_list_p1);
```

返回结果为：

```
 col |   name
-----+-----------
   1 | t_list_p1
(1 row)
```

- 查询分区表中的数据t_list_p2。

```
select * from t_partition_list4 partition(t_list_p2);
```

返回结果为：

```
 col |   name
-----+-----------
   2 | t_list_p2
(1 row)
```

- t_list_default

```
select * from t_partition_list4 partition(t_list_default);
```

返回结果为：

```
 col |      name
-----+----------------
  31 | t_list_default
(1 row)
```
