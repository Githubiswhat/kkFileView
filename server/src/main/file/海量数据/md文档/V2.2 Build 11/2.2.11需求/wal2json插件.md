# wal2json插件

## 功能描述

wal2jison是逻辑解码插件，使用该插件可以访问INSERT和UPDATE生成的元组，解析WAL中的内容。wal2json插件会在每个事务中生成一个JSON对象。JSON对象总提供了所有新/旧元组，额外选项还可以包括事务时间戳，限定架构，数据类型，事务ID等属性。

wal2json支持的参数列表如下：

| 选项                     | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ |
| include-xids             | 添加xid 信息，默认false                                      |
| include-timestamp        | 添加 timestamp信息，默认false                                |
| include-schemas          | 添加schemas信息，默认true                                    |
| include-types            | 添加type信息，默认true                                       |
| include-typmod           | 添加 typmod 信息，默认true                                   |
| include-type-oids        | 添加 type oids信息，默认false                                |
| include-domain-data-type | 将域名替换成数据类型名，默认false                            |
| include-column-positions | 添加 column position 信息，默认false                         |
| include-origin           | 添加origin信息，默认false                                    |
| include-not-null         | 添加not nul1信息，默认false                                  |
| include-default          | 添加默认值表达式，默认为false                                |
| include-pk               | 添加主键信息，默认为false                                    |
| pretty-print             | 更好的输出ison结构，默认为false                              |
| include-lsn              | 添加nextlsn信息，默认为false                                 |
| write-in-chunks          | 在每次更改后写入，而不是在每次更改集之后写入。只在format-version为1的时候有用，默认为 false |
| filter-origins           | 排除指定origin的更改，默认为空不过滤，多个 origin用逗号分隔  |
| filter-tables            | 排除指定table的更改，默认为空不过滤，多个table用逗号分隔     |
| add_tables               | 仅包含指定table的更改，默认值是所有的表，与filter-tables值具有相同的规则 |
| format-version           | 格式化方式，可选值为1和2，默认为1                            |
| actions                  | 解析的actions，默认是所有的action（insert，update，delete）  |

## 注意事项

- GUC参数wal_level需要设置为logical。
- 设置合适的max_wal_senders和max_replication_slots值。
- 暂不支持truncate和message操作。

## 使用流程

使用逻辑复制槽和解码插件解析数据库中wal日志记录的操作流程如下：

1、修改配置文件相关参数。

2、创建逻辑复制槽。

3、执行SQL语句。

4、解析wal内容。

该流程中涉及的函数请参见：SQL语法参考->函数和操作符->[逻辑复制函数](../开发者指南/逻辑复制函数.md)

## 示例

1、修改相关参数（修改后需重启数据库实例）。

```
ALTER SYSTEM SET wal_level to logical;
ALTER SYSTEM SET max_wal_senders to 10;
ALTER SYSTEM SET max_replication_slots to 10;
```

2、创建逻辑复制槽。

```
select 'init' from pg_create_logical_replication_slot('regression_slot_1098463','wal2json');
```

3、执行SQL语句。

```\
create table tb_1098463(col1 int ,col2 bigint default '1',col3 text default 'a',col4 int ) ;

insert into tb_1098463 values(generate_series(1,3),1,'a');
insert into tb_1098463(col1) values(4);
insert into tb_1098463 values(5,4,'a');
insert into tb_1098463 select * from tb_1098463;
```

4、解析wal内容。

```
select data from pg_logical_slot_peek_changes('regression_slot_1098463',null,null,'actions','insert');
```

返回结果为：

```
                                       data
---------------------------------------------------------------------------------------------------------------------------
 {"change":[]}
 {"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntype
s":["integer","bigint","text","integer"],"columnvalues":[1,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098
463","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnvalues":[2,1,"a
",null]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":
["integer","bigint","text","integer"],"columnvalues":[3,1,"a",null]}]}
 {"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntype
s":["integer","bigint","text","integer"],"columnvalues":[4,1,"a",null]}]}
 {"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntype
s":["integer","bigint","text","integer"],"columnvalues":[5,4,"a",null]}]}
 {"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntype
s":["integer","bigint","text","integer"],"columnvalues":[1,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098
463","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnvalues":[2,1,"a
",null]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":
["integer","bigint","text","integer"],"columnvalues":[3,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098463
","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnvalues":[4,1,"a",n
ull]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":["i
nteger","bigint","text","integer"],"columnvalues":[5,4,"a",null]}]}
(5 rows)

```

5、解析wal内容添加xid（include-xids选项测试）。

```
select data from pg_logical_slot_peek_changes('regression_slot_1098463',null,null,'include-xids','true');
```

返回结果为：

```
                               data
---------------------------------------------------------------------------------------------------------------------------

 {"xid":15504,"change":[]}
 {"xid":15505,"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"]
,"columntypes":["integer","bigint","text","integer"],"columnvalues":[1,1,"a",null]},{"kind":"insert","schema":"public","tab
le":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnval
ues":[2,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"c
olumntypes":["integer","bigint","text","integer"],"columnvalues":[3,1,"a",null]}]}
 {"xid":15506,"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"]
,"columntypes":["integer","bigint","text","integer"],"columnvalues":[4,1,"a",null]}]}
 {"xid":15507,"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"]
,"columntypes":["integer","bigint","text","integer"],"columnvalues":[5,4,"a",null]}]}
 {"xid":15508,"change":[{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"]
,"columntypes":["integer","bigint","text","integer"],"columnvalues":[1,1,"a",null]},{"kind":"insert","schema":"public","tab
le":"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnval
ues":[2,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"c
olumntypes":["integer","bigint","text","integer"],"columnvalues":[3,1,"a",null]},{"kind":"insert","schema":"public","table"
:"tb_1098463","columnnames":["col1","col2","col3","col4"],"columntypes":["integer","bigint","text","integer"],"columnvalues
":[4,1,"a",null]},{"kind":"insert","schema":"public","table":"tb_1098463","columnnames":["col1","col2","col3","col4"],"colu
mntypes":["integer","bigint","text","integer"],"columnvalues":[5,4,"a",null]}]}
(5 rows)
```

6、删除逻辑复制槽。

```
select 'stop' from pg_drop_replication_slot('regression_slot_1098463');
drop table tb_1098463;
```

