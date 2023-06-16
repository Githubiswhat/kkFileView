# CREATE SEQUENCE...as data_type

**功能描述**

CREATE SEQUENCE是向当前数据库里增加一个新的序列。在PG兼容模式下，可以使用as datatype语法可选项，指定序列的数据类型。

**语法格式**

```
CREATE [TEMPORARY|TEMP] SEQUENCE [IF NOT EXISTS] name [as data_type]
[INCREMENT [BY] increment]
[MINVALUE minvalue|NO MINVALUE][MAXVALUE maxvalue|NO MAXVALUE]
[START[WITH]start][CACHE cache][[NO] CYCLE]
[OWNED BY{tablename.column_name|NONE}]
```

**参数说明**

- TEMPOPARY/TEMP：如果声明了这个修饰词，那么该序列对象只为这个会话创建，并且在会话结束的时候自动删除。

- name：序列的名称。

- data_type：序列的数据类型，Vastbase G100支持的数据类型有smallint、integer和bright，其中bright是默认值。

- increment：指定序列的步长。一个正数将生成一个递增的序列，一个负数将生成一个递减的序列。缺省值为1。

- MINVALUE minvalue | NO MINVALUE| NOMINVALUE：

  执行序列的最小值。如果没有声明minvalue或者声明了NO MINVALUE，则递增序列的缺省值为1，递减序列的缺省值为-2<sup>63</sup>-1。NOMINVALUE等价于NO MINVALUE

- MAXVALUE maxvalue | NO MAXVALUE| NOMAXVALUE：

  执行序列的最大值。如果没有声明maxvalue或者声明了NO MAXVALUE，则递增序列的缺省值为2<sup>63</sup>-1，递减序列的缺省值为-1。NOMAXVALUE等价于NO MAXVALUE

- start

  指定序列的起始值。缺省值：对于递增序列为minvalue，递减序列为maxvalue。

- cache

  为了快速访问，而在内存中预先存储序列号的个数。

  缺省值为1，表示一次只能生成一个值，也就是没有缓存。

  >   <div align="left"><img src="image/image1.png" style="zoom:20%")</div>
  >
  >不建议同时定义cache和maxvalue或minvalue。因为定义cache后不能保证序列的连续性，可能会产生空洞，造成序列号段浪费。

- CYCLE

  用于使序列达到maxvalue或者minvalue后可循环并继续下去。

  如果声明了NO CYCLE，则在序列达到其最大值后任何对nextval的调用都会返回一个错误。

  NOCYCLE的作用等价于NO CYCLE。

  缺省值为NO CYCLE。

  若定义序列为CYCLE，则不能保证序列的唯一性。

- OWNED BY

  将序列和一个表的指定字段进行关联。这样，在删除那个字段或其所在表的时候会自动删除已关联的序列。关联的表和序列的所有者必须是同一个用户，并且在同一个模式中。需要注意的是，通过指定OWNED BY，仅仅是建立了表的对应列和sequence之间关联关系，并不会在插入数据时在该列上产生自增序列。

  缺省值为OWNED BY NONE，表示不存在这样的关联。

  >   <div align="left"><img src="image/image2.png" style="zoom:20%")</div>
  >通过OWNED BY创建的Sequence不建议用于其他表，如果希望多个表共享Sequence，该Sequence不应该从属于特定表。

**注意事项**

- 使用此子句仅在PG兼容模式下有效。
- 用户需要具备创建序列的权限。

**示例**

1、创建数据库，检查兼容性。

```
CREATE DATABASE test_10379 DBCOMPATIBILITY 'PG';
\c test_10379
show sql_compatibility;
```

2、创建一个名为serial1的递增序列，从101开始，并指定data_type类型为smallint。

```
create sequence serial1 as smallint start 101;
```

返回结果为：

```
CREATE SEQUENCE
```

3、查看这个序列。

```
select * from serial1;
```

返回结果为：

```
 sequence_name | last_value | start_value | increment_by | max_value | min_value
 | cache_value | log_cnt | is_cycled | is_called | uuid | type_id
---------------+------------+-------------+--------------+-----------+----------
-+-------------+---------+-----------+-----------+------+---------
 serial1       |        101 |         101 |            1 |     32767 |         1
 |           1 |       0 | f         | f         |    0 |      21
(1 row)
```

