#### AUTO_INCREMENT自增列

**功能描述**

auto_increment语法用于生成自增列，一般出现在创建表语句和修改表语句中。

**语法格式**

- 创建表语句

```
create  table  t1  (id  int  primary  key  auto_increment,  b  int);
```

- 修改表语句

```
alter  table  t1  auto_increment  =  ai_val;
```

**参数说明**

- id（自增列）：t1表的id列为自增列，即对该表插入数据时，可不指定id列的值，数据库内部会自动为其分配自增值。

- ai_val：该值大于自增字段最大值时，修改才生效。

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 创建表语句

  - 默认情况下，自增列的初始值为1，增量值为1。
  - 列类型表支持以下三种数据类型：

  | 模式             | 描述                                      |
  | ---------------- | ----------------------------------------- |
  | int2/smallint    | -32768至32767                             |
  | int4/int/integer | -2147483648至2147483647                   |
  | int8/bigint      | -9223372036854775808至9223372036854775807 |

  - 一张表只允许存在一个auto_increment字段。
  - auto_increment字段必须是主键字段或联合主键字段之一。
  - 对表插入数据时，若指定了auto_increment字段的值，且该值大于表中该自增列的最大值时，需要更新自增列的下一个值为当前插入数据值加1。
  - Vastbase G100 V2.2版本仅支持insert单行数据，暂不支持insert多行和insert...select。
  - 对表插入数据时，若指定auto_increment字段为NULL或0，则依旧触发自增机制。
  - 在MySQL兼容模式下，Serial类型字段与auto_increment类型字段特性一致。
  - 自增列可以插入负值或更新负值。
  - 自增列的值被更新后无法回退，即使其所在的事务后续回滚依旧如此。多个session并发插入时应保证可用性，需要慎重考虑插入操作导致自增值更新所带来的后果。

- 修改表语句

  - MySQL兼容模式下一张表只允许存在一个auto_increment字段，数据库会自动找到对应字段，修改其自增属性。
  - 修改语句与插入语句都可能改变下一个自增值，需要加锁。
  - Vastbase G100 V2.2版本仅支持修改普通表和分区表的auto_increment属性。不支持物化视图，复合类型等其他类型的关系。

**兼容性**

完全兼容。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

2、创建带有auto_increment字段的表，指定主键。

```
create table test1(a int primary key auto_increment, b int);
```

3、插入数据进行测试。

```
insert into test1(b) values(1);
insert into test1(b) values(1);
insert into test1 values(0,3);   --自增
insert into test1 values(NULL,4);  --自增
insert into test1 values(10000,5);  --此处改变自增列的下一个值为10001
insert into test1(b) values(6);
insert into test1 values(10,7);  --此处由于10较小，不改变序列下一个值
insert into test1(b) values(8);
```

4、查询结果。

```
select * from test1;
```

结果显示如下：

```
  a  | b 
-------+---
   1 | 1
   2 | 1
   3 | 3
   4 | 4
 10000 | 5
 10001 | 6
  10 | 7
 10002 | 8
(8 rows)
```

#### 