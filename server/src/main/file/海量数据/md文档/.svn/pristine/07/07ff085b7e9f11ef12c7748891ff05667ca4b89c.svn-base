

# INSERT IGNORE

## 功能描述

Vastbase G100在MySQL兼容模式下，支持在INSERT语句中使用IGNORE选项。

用户在执行INSERT语句的时候，如果发生主键或唯一索引冲突，SQL语句是会引发ERROR报错，如果使用IGNORE选项，则忽略在执行SQL语句时发生的可忽略的错误，将Error降级为Warning，且继续语句的执行，不会影响其他数据的操作。能使Error降级的场景有：

1、违反非空约束时：

若执行的SQL语句违反了表的非空约束，使用IGNORE选项可将Error降级为Warning，并根据GUC参数sql_ignore_strategy的值采用以下策略的一种继续执行：

- sql_ignore_strategy为ignore_null时，忽略违反非空约束的行的INSERT操作，并继续执行剩余数据操作。
- sql_ignore_strategy为overwrite_null时，将违反约束的null值覆写为目标类型的默认值，并继续执行剩余数据操作。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> GUC参数sql_ignore_strategy相关信息请参考[sql_ignore_strategy](../../../开发者指南/其它选项.html#sql_ignore_strategy)。
>

2、违反唯一约束时：

若执行的SQL语句违反了表的唯一约束，使用IGNORE选项可将Error降级为Warning，忽略违反约束的行的INSERT操作，并继续执行剩余数据操作。

3、分区表无法匹配到合法分区时：

在对分区表进行INSERT操作时，若某行数据无法匹配到表格的合法分区，使用IGNORE选项可将Error降级为Warning，忽略该行操作，并继续执行剩余数据操作。

4、插入值向目标列类型转换失败时：

 执行INSERT语句时，若发现新值与目标列类型不匹配，使用IGNORE选项可将Error降级为Warning，并根据新值与目标列的具体类型采取以下策略的一种继续执行：

- 当新值类型与列类型同为数值类型时：  

  若新值在列类型的范围内，则直接进行插入；若新值在列类型范围外，则以列类型的最大/最小值替代。

- 当新值类型与列类型同为字符串类型时：

  若新值长度在列类型限定范围内，则以直接进行插入；若新值长度在列类型的限定范围外，则保留列类型长度限制的前n个字符。

- 若遇到新值类型与列类型不可转换时：

    插入列类型的默认值。

例如，如果没有IGNORE，表中现有主键或唯一索引重复将会导致重复键错误，并且语句将中止。使用IGNORE选项，该行将被丢弃并且不会发生错误，忽略的错误会生成警告。

其它关于INSERT的相关内容请参考[INSERT](../../VastbaseG100Ver2.2.10/doc/开发者指南/INSERT.html)。

## 语法格式

```sql
INSERT [/*+ plan_hint */] [IGNORE] INTO table_name ...
```

## 参数说明

- **IGNORE**

  使用该参数，如果发生了主键或唯一索引冲突，则会忽略错误并发出一个WARNING。

- **table_name**

  表名。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- IGNORE支持分区表。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```sql
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建一张测试表，主键id。

```sql
 CREATE TABLE table_name(
  id int NOT NULL,
  name varchar(50) DEFAULT NULL,
  age int DEFAULT NULL,
  PRIMARY KEY (id)
);
```

2、插入一条数据。

```sql
 insert into table_name values(1,'tom', 20);
```

3、插入一条数据，id为1，不加ignore（主键冲突）。

```sql
 insert into table_name values(1,'Bill',21);
```

返回结果如下，报主键冲突的错误：

```sql
ERROR:  duplicate key value violates unique constraint "table_name_pkey"
DETAIL:  Key (id)=(1) already exists.
```

4、加上ignore之后插入数据。

```sql
insert ignore into table_name values(1,'Bill',21);
```

返回结果如下，会有一个WARNING警告：

```sql
WARNING:  duplicate key value violates unique constraint in table "table_name"
INSERT 0 0
```


5、插入多行数据。

```sql
 insert ignore into table_name values(1,'Bill',21),(2,'cim',21);
```

返回结果为：

```sql
WARNING:  duplicate key value violates unique constraint in table "table_name"
INSERT 0 1
```

6、查询表，发现插入的数据(1,'Bill',21)被忽略了，(2,'cim',21)数据成功插入。

```sql
select * from table_name;
```

返回结果为：

```sql
 id | name | age
----+------+-----
  1 | tom  |  20
  2 | cim  |  21
(2 rows)
```



# 补充GUC参数

## sql_ignore_strategy<a id="sql_ignore_strategy"></a>

**参数说明：**在B兼容模式下，该参数可控制ignore_error的hint在违反非空约束时的处理策略。

该参数属于USERSET类型，请参考重设参数[表1](c_0237121562_zh-cn_topic_0059777490_t91a6f212010f4503b24d7943aed6d846)中对应设置方法进行设置。

**取值范围：**枚举类型

- ignore_null：忽略违反非空约束的行的处理。
- overwrite_null：将违反约束的null值覆写为目标类型的默认值。

**默认值：**ignore_null



