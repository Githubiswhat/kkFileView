###### INDEX-ADVISOR索引推荐

**单query索引推荐**

单query索引推荐功能支持用户在数据库中直接进行操作，本功能基于查询语句的语义信息和数据库的统计信息，对用户输入的单条查询语句生成推荐的索引。本功能涉及的函数接口如下。

| 函数名          | 参数          | 功能                           |
| :-------------- | :------------ | :----------------------------- |
| gs_index_advise | SQL语句字符串 | 针对单条查询语句生成推荐索引。 |

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> 仅支持单条SELECT类型的语句，不支持其他类型的SQL语句。
>
> 暂不支持列存表、段页式表、普通视图、物化视图、全局临时表以及密态数据库。

**单query索引示例**

- 使用上述函数，获取针对该query生成的推荐索引，推荐结果由索引的表名和列名组成。

```
select "table", "column" from gs_index_advise('SELECT c_discount from bmsql_customer where c_w_id = 10');
```

```
     table      |  column  
----------------+----------
 bmsql_customer | c_w_id
(1 row)
```

上述结果表明：应当在 bmsql_customer 的 c_w_id 列上创建索引，例如可以通过下述SQL语句创建索引：

```
CREATE INDEX idx on bmsql_customer(c_w_id);
```

- 某些SQL语句，也可能被推荐创建联合索引，例如：

```
select "table", "column" from gs_index_advise('select name, age, sex from t1 where age >= 18 and age < 35 and sex = ''f'';');
```

```
 table | column
-------+------------
 t1    | age, sex
(1 row)
```

上述语句表明应该在表 t1 上创建一个联合索引 (age, sex)， 则可以通过下述命令创建：

```
CREATE INDEX idx1 on t1(age, sex);
```

- 针对分区表可推荐具体索引类型，例如：

```
select "table", "column", "indextype" from gs_index_advise('select name, age, sex from range_table where age = 20;');
```

```
 table | column | indextype
-------+--------+-----------
 t1    | age    | global
(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> 系统函数gs_index_advise()的参数是文本型，如果参数中存在如单引号（'）等特殊字符，可以使用单引号（'）进行转义，可参考上述示例。

**虚拟索引**

虚拟索引功能支持用户在数据库中直接进行操作，将模拟真实索引的建立，避免真实索引创建所需的时间和空间开销，用户基于虚拟索引，可通过优化器评估该索引对指定查询语句的代价影响。

涉及的系统函数接口如下表所示：

| 函数名               | 参数                 | 功能                             |
| :------------------- | :------------------- | :------------------------------- |
| hypopg_create_index  | 创建索引语句的字符串 | 创建虚拟索引。                   |
| hypopg_display_index | 无                   | 显示所有创建的虚拟索引信息。     |
| hypopg_drop_index    | 索引的oid            | 删除指定的虚拟索引。             |
| hypopg_reset_index   | 无                   | 清除所有虚拟索引。               |
| hypopg_estimate_size | 索引的oid            | 估计指定索引创建所需的空间大小。 |

涉及的GUC参数如下：

| 参数名            | 功能                 | 默认值 |
| :---------------- | :------------------- | :----- |
| enable_hypo_index | 是否开启虚拟索引功能 | off    |

**示例**

1、创建测试表并插入数据。

```
CREATE TABLE bmsql_customer (c_w_id int,name varchar,c_discount int);
INSERT INTO bmsql_customer values(1,a,11);
INSERT INTO bmsql_customer values(2,b,12);
INSERT INTO bmsql_customer values(3,c,13);
```

使用函数hypopg_create_index创建虚拟索引。例如：

```
select * from hypopg_create_index('create index on bmsql_customer(c_w_id)');
```

```
 indexrelid |             indexname              
------------+------------------------------------
      24040 | <24040>btree_bmsql_customer_c_w_id
(1 row)
```

2、开启GUC参数enable_hypo_index，该参数控制数据库的优化器进行EXPLAIN时是否考虑创建的虚拟索引。通过对特定的查询语句执行explain，用户可根据优化器给出的执行计划评估该索引是否能够提升该查询语句的执行效率。

开启GUC参数前，执行EXPLAIN + 查询语句：

```
explain SELECT c_discount from bmsql_customer where c_w_id = 3;
```

```
                              QUERY PLAN                              
----------------------------------------------------------------------
 Seq Scan on bmsql_customer  (cost=0.00..52963.06 rows=31224 width=4)
   Filter: (c_w_id = 3)
(2 rows)
```

开启GUC参数后，执行EXPLAIN + 查询语句：

```
set enable_hypo_index = on;
explain SELECT c_discount from bmsql_customer where c_w_id = 3;
```

```
                                                    QUERY PLAN                                                    
------------------------------------------------------------------------------------------------------------------
 [Bypass]
 Index Scan using <329726>btree_bmsql_customer_c_w_id on bmsql_customer  (cost=0.00..39678.69 rows=31224 width=4)
   Index Cond: (c_w_id = 3)
(3 rows)
```

通过对比两个执行计划可以观察到，该索引预计会降低指定查询语句的执行代价，用户可考虑创建对应的真实索引。

3、使用函数hypopg_display_index展示所有创建过的虚拟索引（可选）。例如：

```
select * from hypopg_display_index();
```

```
         indexname              | indexrelid |     table      |  column  

------------------------------------+------------+----------------+----------
 <24040>btree_bmsql_customer_c_w_id |      24040 | bmsql_customer | (c_w_id)
 (2 rows)
```

4、使用函数hypopg_estimate_size估计虚拟索引创建所需的空间大小（单位：字节）（可选）。例如：

```
 select * from hypopg_estimate_size(329730);
```

```
 hypopg_estimate_size 
----------------------
             15687680
(1 row)
```

5、删除虚拟索引。

使用函数hypopg_drop_index删除指定oid的虚拟索引。例如：

```
select * from hypopg_drop_index(329726);
```

```
 hypopg_drop_index 
-------------------
 t
(1 row)
```

使用函数hypopg_reset_index一次性清除所有创建的虚拟索引。例如：

```
select * from hypopg_reset_index();
```

```
 hypopg_reset_index 
--------------------
    
(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> - 执行EXPLAIN ANALYZE不会涉及虚拟索引功能。
> - 创建的虚拟索引是数据库实例级别的，各个会话（session）之间可共享设置，关闭会话后虚拟索引仍可存在，但是重启数据库后将被清空。
> - 本功能暂不支持视图、物化视图、列存表。

**WORKLOAD级别索引推荐**

对于workload级别的索引推荐，用户可通过运行数据库外的脚本使用此功能，本功能将包含有多条DML语句的workload作为输入，最终生成一批可对整体workload的执行表现进行优化的索引。同时，本功能提供从日志中抽取业务数据SQL流水的功能。

**前提条件**

- 数据库状态正常、客户端能够正常连接。
- 当前执行用户下安装有gsql工具，该工具路径已被加入到PATH环境变量中。
- 若使用本功能提供的业务数据抽取功能，需提前将要收集的节点的GUC参数按如下设置：
  - log_min_duration_statement = 0
  - log_statement= 'all'

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> 业务数据抽取完毕建议将上述GUC参数复原，否则容易导致日志文件膨胀。

**业务数据抽取脚本使用步骤**

1、按前提条件中要求设置相关GUC参数。<a id='步骤1'></a>

2、执行根据日志抽取SQL语句的功能，命令如下：

```
gs_dbmind component extract_log [l LOG_DIRECTORY] [f OUTPUT_FILE] [p LOG_LINE_PREFIX] [-d DATABASE] [-U USERNAME][--start_time] [--sql_amount] [--statement] [--json] [--max_reserved_period] [--max_template_num]
```

其中的输入参数依次为：

- LOG_DIRECTORY：pg_log的存放目录。
- OUTPUT_PATH：输出SQL流水文件文件的保存路径，即抽取出的业务数据存放的文件路径。
- LOG_LINE_PREFIX：指定每条日志信息的前缀格式。
- DATABASE：（可选）数据库名称，不指定默认所有数据库。
- USERNAME：（可选）用户名称，不指定默认所有用户。
- start_time：（可选）日志收集的开始时间，不指定默认所有文件。
- sql_amount：（可选）收集SQL数量的最大值，不指定默认收集所有SQL。
- statement：（可选）表示收集pg_log日志中statement标识开头的SQL，不指定默认不收集。
- json：（可选）指定收集日志的文件存储格式为SQL归一化后的json，不指定默认格式每条SQL占一行。
- max_reserved_period：（可选）指定json模式下，增量收集日志中保留的模板的最大的更新时长，不指定默认都保留，单位/天。
- max_template_num：（可选）指定json模式下保留的最大模板数量，不指定默认都保留。

使用示例：

```
gs_dbmind component extract_log $GAUSSLOG/pg_log/dn_6001 sql_log.txt '%m %c %d %p %a %x %n %e' -d postgres -U omm --start_time '2021-07-06 00:00:00' --statement
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> 若指定-d/-U参数，日志打印每条日志信息的前缀格式需包含%d、%u，若需要抽取事务，必须指定%p，详见log_line_prefix参数。max_template_num参数设置建议不超5000条，避免workload索引推荐执行时间过长。

3、将[1](#步骤1)中设置的GUC参数还原为设置前的值。

**索引推荐脚本使用步骤**

1、准备好包含有多条DML语句的文件作为输入的workload，文件中每条语句占据一行。用户可从数据库的离线日志中获得历史的业务语句。

2、运行本功能，命令如下：

```
gs_dbmind component index_advisor [p PORT] [d DATABASE] [f FILE] [--h HOST] [-U USERNAME] [-W PASSWORD][--schema SCHEMA]
[--max_index_num MAX_INDEX_NUM][--max_index_storage MAX_INDEX_STORAGE] [--multi_iter_mode] [--multi_node]  [--json] [--driver] [--show_detail]
```

其中的输入参数依次为：

- PORT：连接数据库的端口号。
- DATABASE：连接数据库的名字。
- FILE：包含workload语句的文件路径。
- HOST：（可选）连接数据库的主机号。
- USERNAME：（可选）连接数据库的用户名。
- PASSWORD：（可选）连接数据库用户的密码。
- SCHEMA：模式名称。
- MAX_INDEX_NUM：（可选）最大的索引推荐数目。
- MAX_INDEX_STORAGE：（可选）最大的索引集合空间大小。
- multi_node：（可选）指定当前是否为分布式数据库实例。
- multi_iter_mode：（可选）算法模式，可通过是否设置该参数来切换算法。
- json：（可选）指定workload语句的文件路径格式为SQL归一化后的json，默认格式每条SQL占一行。
- driver：（可选）指定是否使用python驱动器连接数据库，默认gsql连接。
- show_detail：（可选）是否显示当前推荐索引集合的详细优化信息。

例如：

```
gs_dbmind component index_advisor 6001 postgres tpcc_log.txt --schema public --max_index_num 10 --multi_iter_mode
```

推荐结果为一批索引，以多个创建索引语句的格式显示在屏幕上，结果示例。

```
create index ind0 on public.bmsql_stock(s_i_id,s_w_id);
create index ind1 on public.bmsql_customer(c_w_id,c_id,c_d_id);
create index ind2 on public.bmsql_order_line(ol_w_id,ol_o_id,ol_d_id);
create index ind3 on public.bmsql_item(i_id);
create index ind4 on public.bmsql_oorder(o_w_id,o_id,o_d_id);
create index ind5 on public.bmsql_new_order(no_w_id,no_d_id,no_o_id);
create index ind6 on public.bmsql_customer(c_w_id,c_d_id,c_last,c_first);
create index ind7 on public.bmsql_new_order(no_w_id);
create index ind8 on public.bmsql_oorder(o_w_id,o_c_id,o_d_id);
create index ind9 on public.bmsql_district(d_w_id);
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> multi_node参数需严格按照当前数据库架构进行指定，否则推荐结果不全，甚至导致无推荐结果。