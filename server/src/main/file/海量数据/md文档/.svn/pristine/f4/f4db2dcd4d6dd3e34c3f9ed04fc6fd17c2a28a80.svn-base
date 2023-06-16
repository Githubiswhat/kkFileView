##### UPSERT功能

**功能描述**

UPSERT功能用于往目标表中插入或更新数据。当插入数据因为主键或约束产生冲突时，更新数据，不冲突则插入数据。通过PG风格的upsert语句，指定insert时的冲突键，以及冲突后的处理方式。执行INSERT语句时，支持通过ON CONFLICT conflict_target DO conflict_action语法，指定insert时的冲突检测目标及冲突后的行为。

**语法格式**

- INSERT完整语法


```
[WITH [RECURSIVE] with_query[,..]]
INSERT INTO table_name [(column_name[,…])]
 { DEFAULT VALUES
 | VALUES {({ expression | DEFAULT }[,…])}[,…]
 | query }
[upsert_clause]
[RETURNING { * | {output_expression [ [AS] output_name ]}[,…]}];
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> INSERT语法参数详细说明清参考SQL语法“INSERT”章节

- upsert_clause语法


```
(1) ON DUPLICATE KEY UPDATE {{column_name = {expression | DEFAULT}}[,…] | NOTHING }
(2) ON CONFLICT [conflict_target]DO conflict_action
```

**参数说明**

- conflict_target：

1、(index_params)[where_clause]：根据该语法指定表的字段名或表达式等信息，执行时通过该信息找到对应的唯一索引进行冲突检测。

index_params为索引的参数，与CREATE INDEX时的索引参数一致。

```
{{column_name | (expression)} [COLLATE collation] [opclass] [ASC|DESC] [NULLS{FIRST|LAST}] [,…]
```

但是在upsert语句中，不支持指定ASC/DESC、NULLS FIRST/LAST。

where_clause为条件表达式，暂不支持where_caluse。

2、ON CONSTRAINT name：根据该语法指定表的唯一约束名称，执行时将根据该唯一约束找到对应的唯一索引进行冲突检测，但目前暂不支持该用法。

- conflict_action语法

1、检测到冲突时执行UPDATE。

```
 DO UPDATE SET { column_name= { expression |DEFAULT }}[,...] [where_clause]
```

在SET子句中，可以EXCLUDED来引用要插入的列值，但不支持引用系统列（比如ctid)。例如: UPDATE SET c1=EXCLUDE.c1则表示将c1更新为SQL中 insert部分计划插入的c1字段的值；而UPDATE setc1=EXCLUDE.ctid:text 则不支持。

where_clause为条件表达式，用于指定update的条件，是可选节点，但目前暂不支持指定where_clause。

2、DO NOTHING：检测到冲突时什么也不做。

- with_query：WITH子句允许指定一个或者更多子查询，再insert查询中中可以用名称引用这些子查询，由于Vastbase原upsert不支持with子句，Vastbase G100 V2.2版本兼容pg中upsert也暂不支持。

- schema_name：可选参数，模式名称。

- table_name：一个已有表的名称。

- alias：table_name的别名。当提供了一个别名时，它会完全隐藏掉表的实际名称。当ON CONFLICT DO UPDATE的目标是一个被排除的表时这特别有用，因为那将被当作表示要被插入行的特殊表的名称。

- column_name：名为table_name的表中的一个列的名称。如有必要，列名可以用一个子域名或者数组下标限定（指向一个组合列的某些列中插入会让其他域为空）。

- expression：要赋予给相应列的表达式或者值。

- DEFAULT：相应的列将填充其默认值。标识列将由关联序列生成的新值填充。对于生成的列，允许指定该值，但仅指定根据其生成表达式计算该列的正常行为。

- query：提供要被插入行的查询（SELECT语句）。

- INSERT完output_expression：在每一行被插入或更新后由INSERT命令计算并且返回的表达式。该表达式可以使用table_name指定的表中的任何列。写成*可返回被插入或更新行的所有列。在Vastbase G100 V2.2.5版本中，暂不支持。

- output_name：要用于被返回列的名称。

- ON CONFLICT子句：可选的ON CONFLICT子句为出现违背唯一性或违背约束错误时提供另一种可供选择的动作。对于每一个要插入的行，不管是插入进行下去还是由conflict_target指定的一个对象，都会采取可供选择的conflict_action。ON CONFLICT DO NOTHING简单返回，什么也不做。ON CONFLICT DO UPDATE则会更新与要插入的行冲突的已有行。conflict_target可以执行唯一索引推断。在执行推断时，它由一个或者多个index_column_name列或者index_expression表达式以及一个可选的index_predicate构成。如果推断尝试不成功，则会发生一个错误。ON CONFLICT DO UPDATE保证一个原子的INSERT或者UPDATE结果。在没有无关错误的前提下，这两种结果之一可以得到保证，即使在很高的并发度也能保证。

- conflict_target：通过选择判断目标索引来指定哪些行与ON CONFLICT在其上采取可替代动作的行相冲突。要么执行唯一索引推断，要么显式命名一个约束。对于ON CONFLICT DO NOTHING来说，它对于指定一个conflict_target是可选的。在被省略时，与所有有效约束（以及唯一索引）的冲突都会被处理。对于ON CONFLICT DO UPDATE，必须提供一个conflict_target。

- conflict_action：conflict_action指定一个可替换的ON CONFLICT动作。它可以是DO NOTHING，也可以是一个指定在冲突情况下要被执行的UPDATE动作细节的DO UPDATE子句。ON CONFLICT DO UPDATE中的SET和WHERE子句能够使用该表的名称（或者别名）访问现有的行，并且可以用特殊的excluded表访问要插入的行。注意，不支持引用系统列例如ctid作为更新值，当前版本暂时不支持sub-SELECT做更新。

- index_column_name：一个table_name列的名称。它被用来检测对象是否冲突。

- index_expression：和index_column_name类似，但是被用来判断出现在索引定义中的table_name列（非简单列）上的表达式。

- Collation：强制相应的index_column_name或index_expression使用一种特定的排序规则以便在检测冲突期间能被匹配上。默认为缺省值。排序规则不影响冲突检测，一般不指定。

- opclass：强制相应的index_column_name或index_expression使用特定的操作符类以便在检测冲突期间能被匹配上。通常会被省略。

- index_predicate：用于允许判断部分唯一索引。任何满足该谓词（不一定需要真的是部分索引）的索引都能被推断。

- constraint_name：用名称显式地指定一个判断目标对象约束，本版本中，暂不支持。

- condition：一个能返回boolean值的表达式。仅在这个表达式返回true行才将被更新，不过在采用ON CONFLICT DO UPDATE动作时所有的行都会被锁定。注意condition会被最后计算，即一个冲突被标识为要更新的候选对象之后。

**注意事项**

- ON CONFLICT语法暂不支持在视图、外部表、分区表中使用。

- conflict_target用于指定冲突检测的目标，该目标必须能关联到唯一索引。.目前暂不支持不指定conflict_target的用法。

- 支持 insert的数据类型允许在SQL的insert部分写入。

- 支持update的数据类型允许在upsext_clause中的update子句中更新。
- upsert语句暂不支持以下场景：
  - SQL中包含 upsert子句和 returning子句。
  - SQL中包含upsert子句和 with子句。
  - update set子句中包含子查询。
  - upsert子句的conflict_target为空。
  - upsert子句的conflict_target为ON CONSTRAINT name。
  - upsert子句的conflict_target中包含where子句。
  - upsert子句的conflict_action中包含where子句。
  - upsert子句的conflict_target指定了ASC/DESC 、NULLS FIRSTLAST关键字。
  - upsert语法不支持在分区表、视图、外部表中使用。

**示例**

- ON CONFLICT

1、创建测试表。

```
create table tu1(c1 int unique, c2 int);
```

2、执行如下语句，如c1值不冲突，将执行插入。

```
insert into tu1 values(3,3) on conflict(c1) do update set c2=EXCLUDED.c2;
```

3、查询结果，当结果显示为如下信息，则表示插入成功。

```
select * from tu1;
 c1 | c2 
----+----
  3 |  3
(1 row)
```

4、执行如下语句，如c1值冲突，将执行更新。

```
insert into tu1 values(3,4) on conflict(c1) do update set c2=EXCLUDED.c2;
```

5、查询结果，当结果显示为如下信息，则表示更新成功。

```
select * from tu1;
 c1 | c2 
----+----
  3 |  4
(1 row)
```

6、执行如下语句，如c1值冲突，将什么都不做。

```
insert into tu1 values(3,5) on conflict(c1) do nothing; 
```

7、查询结果，当结果显示为如下信息，则表示没有执行操作。

```
select * from tu1;
 c1 | c2 
----+----
  3 |  4
(1 row)
```

- 使用 ON DUPLICATE KEY UPDATE 根据某一字段值查询数据库中是否有记录，有则更新，没有则插入

1、创建测试数据。

```
CREATE TABLE test01 (col1 INT PRIMARY KEY, col2 INT);
insert into test01 values(1,1);
```

2、根据col1字段值判断数据库中是否有记录'1'，有则更新字段col2为'2'，没有则插入col1为'1'。

```
INSERT INTO test01(col1) VALUES(1) ON DUPLICATE KEY UPDATE col2 = 2;
```

3、验证结果，当结果显示为如下信息，则表示验证成功。

```
SELECT * FROM test01

 col1 | col2 
------+------
    1 |    2
(1 row)
```

