## ANALYZE和ANALYSE

**功能描述**

ANALYZE和ANALYSE用于收集与数据库中普通表内容相关的统计信息，统计结果存储在系统表PG_STATISTIC下。执行计划生成器会使用这些统计数据，以确定最有效的执行计划。

如果没有指定参数，ANALYZE会分析当前数据库中的每个表和分区表。同时也可以通过指定table_name、column和partition_name参数把分析限定在特定的表、列或分区表中。

ANALYZE VERIFY和ANALYSE VERIFY用于检测数据库中普通表（行存表、列存表）的数据文件是否损坏。

**注意事项**

- ANALYZE非临时表不能在一个匿名块、事务块、函数或存储过程内被执行。支持存储过程中ANALYZE临时表，不支持统计信息回滚操作。

- ANALYZE VERIFY 操作处理的大多为异常场景检测需要使用RELEASE版本。

- ANALYZE VERIFY 场景不触发远程读，因此远程读参数不生效。对于关键系统表出现错误被系统检测出页面损坏时，将直接报错不再继续检测。

**语法格式**

- 收集表的统计信息。

```
{ ANALYZE | ANALYSE } [ VERBOSE ] 
  [ table_name [ ( column_name [, ...] ) ] ];
```

- 收集分区表的统计信息。

```
{ ANALYZE | ANALYSE } [ VERBOSE ] 
[ table_name [ ( column_name [, ...] ) ] ] 
PARTITION ( patrition_name ) ;
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> 普通分区表目前支持针对某个分区的统计信息的语法，在功能上不支持针对某个分区的统计信息收集。

- 收集多列统计信息。 

```
{ANALYZE | ANALYSE} [ VERBOSE ] 
  table_name (( column_1_name, column_2_name [, ...] ));
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> （1）收集多列统计信息时，请设置GUC参数default_statistics_target为负数，  以使用百分比采样方式。	
>
> （2）每组多列统计信息最多支持32列。
>
> （3）不支持收集多列统计信息的表：系统表。 

- 检测当前库的数据文件。

```
{ANALYZE | ANALYSE} VERIFY {FAST|COMPLETE};
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> （1）Fast模式校验时，需要对校验的表有并发的DML操作，会导致校验过程中有误报的问题，因为当前Fast模式是直接从磁盘上读取，并发有其他线程修改文件时，会导致获取的数据不准确，建议离线操作。
>
> （2）支持对全库进行操作，由于涉及的表较多，建议以重定向保存结果，命令如下所示：
>
> ```
> vsql -d database -p port -f "verify.sql"> verify_warning.txt  2>&1
> ```
>
> （3）对外提示NOTICE只核对外可见的表，内部表的检测会包含在它所依赖的外部表，不对外显示和呈现。
>
> （4）此命令的处理可容错ERROR级别的处理。由于debug版本的Assert可能会导致core无法继续执行命令，建议在release模式下操作。
>
> （5）对于全库操作时，当关键系统表出现损坏则直接报错，不再继续执行。
>
> （6）不支持临时表和unlog表。

- 检测表和索引的数据文件。

```
{ANALYZE | ANALYSE} VERIFY {FAST|COMPLETE} table_name|index_name [CASCADE];
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> （1）支持对普通表的操作和索引表的操作，但不支持对索引表index使用CASCADE操作。原因是由于CASCADE模式用于处理主表的所有索引表，当单独对索引表进行检测时，无需使用CASCADE模式。
>
> （2）不支持临时表和unlog表。
>
> （3）对于主表的检测会同步检测主表的内部表，例如toast表、cudesc表等。
>
> （4）当提示索引表损坏时，建议使用reindex命令进行重建索引操作。

- 检测表分区的数据文件。

```
{ANALYZE | ANALYSE} VERIFY {FAST|COMPLETE} table_name PARTITION {(patrition_name)}[CASCADE];
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> （1）支持对表的单独分区进行检测操作，但不支持对索引表index使用CASCADE操作。
>
> （2）不支持临时表和unlog表。
>
> （3） 全局分区索引适配支持ANALYZE VERIFY FAST。

**参数说明**

- VERBOSE：启用显示进度信息，如果指定了VERBOSE，ANALYZE发出进度信息，表明目前正在处理的表。各种有关表的统计信息也会打印出来。

- FAST|COMPLETE：对于行存表，FAST模式下主要对于行存表的CRC和page header进行校验，如果校验失败则会告警; 而COMPLETE模式下，则主要对行存表的指针、tuple进行解析校验。 对于列存表，FAST模式下主要对于列存表的CRC和magic进行校验，如果校验失败则会告警; 而COMPLETE模式下，则主要对列存表的CU进行解析校验。

- table_name：需要分析的特定表的表名（可能会带模式名），如果省略，将对数据库中的所有表（非外部表）进行分析。对于ANALYZE收集统计信息，目前仅支持行存表、列存表。

  取值范围：已有的表名。

- index_name需要分析的特定索引表的表名（可能会带模式名）。

  取值范围：已有的表名。

- CASCADE：CASCADE模式下会对当前表的所有索引进行检测处理。

- partition_name：如果table为分区表，在关键字PARTITION后面指定分区名partition_name表示分析该分区表的统计信息。目前语法上支持分区表做ANALYZE，但功能实现上暂不支持对指定分区统计信息的分析。

  取值范围：表的某一个分区名。

**示例**

1、创建测试表。

```
CREATE TABLE customer_info 
( 
WR_RETURNED_DATE_SK    INTEGER            , 
WR_RETURNED_TIME_SK    INTEGER            , 
WR_ITEM_SK         INTEGER        NOT NULL, 
WR_REFUNDED_CUSTOMER_SK  INTEGER 
) ;
```

2、创建分区表。

```
CREATE TABLE customer_par 
( 
WR_RETURNED_DATE_SK    INTEGER            , 
WR_RETURNED_TIME_SK    INTEGER            , 
WR_ITEM_SK         INTEGER        NOT NULL, 
WR_REFUNDED_CUSTOMER_SK  INTEGER 
) 
PARTITION BY RANGE(WR_RETURNED_DATE_SK) 
( 
PARTITION P1 VALUES LESS THAN(2452275), 
PARTITION P2 VALUES LESS THAN(2452640), 
PARTITION P3 VALUES LESS THAN(2453000), 
PARTITION P4 VALUES LESS THAN(MAXVALUE) 
) 
ENABLE ROW MOVEMENT;
```

3、使用ANALYZE语句更新统计信息。

```
ANALYZE customer_info;
```

4、使用ANALYZE VERBOSE语句更新统计信息，并输出表的相关信息。

```
ANALYZE VERBOSE customer_info; 
```

返回结果为：

```
INFO:  analyzing "public.customer_info"(node1 pid=336454)
INFO:  ANALYZE INFO : "customer_info": scanned 0 of 0 pages, containing 0 live rows and 0 dead rows; 0 rows in sample, 0 estimated total rows(node1 pid=336454)
ANALYZE
```

5、删除表。

```
DROP TABLE customer_info; 
DROP TABLE customer_par;
```

