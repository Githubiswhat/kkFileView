# ALTER TABLE PARTITION<a name="ZH-CN_TOPIC_0289900688"></a>

## 功能描述<a name="zh-cn_topic_0283137443_zh-cn_topic_0237122077_zh-cn_topic_0059778761_s4954d450a2e8434aa3abac355bac38e6"></a>

修改表分区，包括增删分区、切割分区、合成分区、交换分区以及修改分区属性等。

## 语法格式<a name="zh-cn_topic_0283137443_zh-cn_topic_0237122077_zh-cn_topic_0059778761_s77ad09af007d4883a3bc70cc8a945481"></a>

修改表分区主语法如下：

```sql
ALTER TABLE [ IF EXISTS ] { table_name  [*] | ONLY table_name | ONLY ( table_name  )}
    action [, ... ];
```

其中action统指如下分区维护子语法。当存在多个分区维护子句时，保证了分区的连续性，无论这些子句的排序如何，Vastbase总会先执行DROP PARTITION再执行ADD PARTITION操作，最后顺序执行其它分区维护操作。

```sql
move_clause  |
exchange_clause  |
row_clause  |
merge_clause  |
modify_clause  |
split_clause  |
add_clause  |
drop_clause  |
truncate_clause
```

- move_clause子语法用于移动分区到新的表空间。

  ```sql
  MOVE PARTITION { partion_name | FOR ( partition_value [, ...] ) } TABLESPACE tablespacename
  ```

- exchange_clause子语法用于把普通表的数据迁移到指定的分区。

  ```sql
   EXCHANGE PARTITION|SUBPARTITION { ( partition_name ) | FOR ( partition_value [, ...] ) } 
   WITH TABLE {[ ONLY ] ordinary_table_name | ordinary_table_name * | ONLY ( ordinary_table_name )}  
   [ { WITH | WITHOUT } VALIDATION ] [ VERBOSE ]
  ```

   进行交换的普通表和分区必须满足如下条件：

  -   普通表和分区的列数目相同，对应列的信息严格一致，包括：列名、列的数据类型、列约束、列的Collation信息、列的存储参数、列的压缩信息等。
  -   普通表和分区的表压缩信息严格一致。
  -   普通表和分区的索引个数相同，且对应索引的信息严格一致。
  -   普通表和分区的表约束个数相同，且对应表约束的信息严格一致。
  -   普通表不可以是临时表，分区表只能是范围分区表，列表分区表，哈希分区表。
  -   普通表和分区表上不可以有动态数据脱敏，行访问控制约束。
  -   列表分区表，哈希分区表不能是列存储。
  - List/Hash/Range类型分区表支持exchange_clause。

    ><div align="left"><img src="image/img2.png" style="zoom:75%")</div>   
    >
    >-   完成交换后，普通表和分区的数据被置换，同时普通表和分区的表空间信息被置换。此时，普通表和分区的统计信息变得不可靠，需要对普通表和分区重新执行analyze。
    >
    >-   由于非分区键不能建立本地唯一索引，只能建立全局唯一索引，所以如果普通表含有唯一索引时，会导致不能交换数据。
    >-   支持增删列之后的表进行分区交换。

- row_clause子语法用于设置分区表的行迁移开关。

  ```sql
  { ENABLE | DISABLE } ROW MOVEMENT
  ```

- merge_clause子语法用于把多个分区合并成一个分区。

  ```sql
  MERGE PARTITIONS { partition_name } [, ...] INTO PARTITION partition_name [ TABLESPACE tablespacename ] [ UPDATE GLOBAL INDEX ]
  ```

-   modify_clause子语法用于设置分区索引是否可用。
    
    ```sql
    MODIFY PARTITION partition_name { UNUSABLE LOCAL INDEXES | REBUILD UNUSABLE LOCAL INDEXES }
    ```
    
- split_clause子语法用于把一个分区切割成多个分区。

  ```sql
  SPLIT PARTITION { partition_name | FOR ( partition_value [, ...] ) } { split_point_clause | no_split_point_clause } [ UPDATE GLOBAL INDEX ]
  ```

- 指定切割点split\_point\_clause的语法为。

  ```sql
   AT ( partition_value ) INTO ( PARTITION partition_name [ TABLESPACE tablespacename ] , PARTITION partition_name [ TABLESPACE tablespacename ] )
  ```


>  <div align="left"><img src="image/img2.png" style="zoom:75%")</div>   
>
>-   列存分区表不支持切割分区。
>
>-  切割点的大小要位于正在被切割的分区的分区键范围内，指定切割点的方式只能把一个分区切割成两个新分区。
>

- 不指定切割点no_split_point_clause的语法为：

  ```sql
  INTO { ( partition_less_than_item [, ...] ) | ( partition_start_end_item [, ...] ) }
  ```


> <div align="left"><img src="image/img2.png" style="zoom:75%")</div>   


  >-   不指定切割点的方式，partition\_less\_than\_item指定的第一个新分区的分区键要大于正在被切割的分区的前一个分区（如果存在的话）的分区键，partition\_less\_than\_item指定的最后一个分区的分区键要等于正在被切割的分区的分区键大小。

  >-   不指定切割点的方式，partition\_start\_end\_item指定的第一个新分区的起始点（如果存在的话）必须等于正在被切割的分区的前一个分区（如果存在的话）的分区键，partition\_start\_end\_item指定的最后一个分区的终止点（如果存在的话）必须等于正在被切割的分区的分区键。

  >-   partition_less_than_item支持的分区键个数最多为4，而partition_start_end_item仅支持1个分区键，其支持的数据类型参见[PARTITION BY RANGE(partition_key)](CREATE-TABLE-PARTITION.md#PARTITION-BY-RANGE)。

> - 在同一语句中partition\_less\_than\_item和partition\_start\_end\_item两者不可同时使用；不同split语句之间没有限制。

  - 分区项partition_less_than_item的语法为：

    ```sql
    PARTITION partition_name VALUES LESS THAN ( { partition_value | MAXVALUE }  [, ...] ) [ TABLESPACE tablespacename ]
    ```

- 分区项partition_start_end_item的语法为，其约束参见[START END语法描述](CREATE-TABLE-PARTITION.md#start-end语法限制)。

  ```sql
  PARTITION partition_name {{START(partition_value) END (partition_value) EVERY (interval_value)} |{START(partition_value) END ({partition_value | MAXVALUE})} |{START(partition_value)} |{END({partition_value |MAXVALUE})}} [TABLESPACE tablespace_name]
  ```

- add_clause子语法用于为指定的分区表添加一个或多个分区。

  ```sql
  ADD PARTITION ( partition_col1_name = partition_col1_value [, partition_col2_name = partition_col2_value ] [, ...] )
      [ LOCATION 'location1' ]
      [ PARTITION (partition_colA_name = partition_colA_value [, partition_colB_name = partition_colB_value ] [, ...] ) ]
      [ LOCATION 'location2' ]
      
  ADD {partition_less_than_item | partition_start_end_item| partition_list_item }
  ```

- 分区项partition_list_item的语法如下。

  ```sql
  PARTITION partition_name VALUES (list_values_clause) [ TABLESPACE tablespacename ]
  ```


><div align="left"><img src="image/img2.png" style="zoom:75%")</div>   
>
>-   partition_list_item仅支持的1个分区键，其支持的数据类型参见[PARTITION BY LIST(partition_key)](CREATE-TABLE-PARTITION.md#partition-by-list)。
>-   间隔/哈希分区表不支持添加分区。

-   drop_clause子语法用于删除分区表中的指定分区。

><div align="left"><img src="image/img2.png" style="zoom:75%")</div>   
>
>哈希分区表不支持删除分区。


- truncate_clause子语法用于清空分区表中的指定分区。

  ```sql
  TRUNCATE PARTITION  { partition_name | FOR (  partition_value [, ...] )  } [ UPDATE GLOBAL INDEX ]
  ```

- 修改表分区名称的语法。

  ```
  ALTER TABLE [ IF EXISTS ] { table_name [*] | ONLY table_name | ONLY ( table_name  )}
  RENAME PARTITION { partion_name | FOR ( partition_value [, ...] ) } TO partition_new_name;
  ```

  


## 参数说明<a name="zh-cn_topic_0283137443_zh-cn_topic_0237122077_zh-cn_topic_0059778761_sff7a5cc103ab41709c6f7249e8d47808"></a>

-   **table_name**

    分区表名。

    取值范围：已存在的分区表名。

-   **partition_name**

    分区名。

    取值范围：已存在的分区名。

-   **tablespacename**

    指定分区要移动到哪个表空间。

    取值范围：已存在的表空间名。

-   **partition_value**

    分区键值。

    通过PARTITION FOR ( partition_value [, ...] )子句指定的这一组值，可以唯一确定一个分区。

    取值范围：需要进行重命名的分区的分区键的取值范围。

-   **UNUSABLE LOCAL INDEXES**

    设置该分区上的所有索引不可用。

-   **REBUILD UNUSABLE LOCAL INDEXES**

    重建该分区上的所有索引。

-   **ENABLE/DISABLE ROW MOVEMET**

    行迁移开关。

    如果进行UPDATE操作时，更新了元组在分区键上的值，造成了该元组所在分区发生变化，就会根据该开关给出报错信息，或者进行元组在分区间的转移。

    取值范围：

    -   ENABLE：打开行迁移开关。
    -   DISABLE：关闭行迁移开关。

    默认是打开状态。

-   **ordinary_table_name**

    进行迁移的普通表的名称。

    取值范围：已存在的普通表名。

-   **{ WITH | WITHOUT } VALIDATION**

    在进行数据迁移时，是否检查普通表中的数据满足指定分区的分区键范围。

    取值范围：

    -   WITH：对于普通表中的数据要检查是否满足分区的分区键范围，如果有数据不满足，则报错。
    -   WITHOUT：对于普通表中的数据不检查是否满足分区的分区键范围。

    默认是WITH状态。

    由于检查比较耗时，特别是当数据量很大的情况下更甚。所以在保证当前普通表中的数据满足分区的分区键范围时，可以加上WITHOUT来指明不进行检查。

-   **VERBOSE**

    在VALIDATION是WITH状态时，如果检查出普通表有不满足要交换分区的分区键范围的数据，那么把这些数据插入到正确的分区，如果路由不到任何分区，再报错。

    ><div align="left"><img src="image/img2.png" style="zoom:75%")</div>   
    >只有在VALIDATION是WITH状态时，才可以指定VERBOSE。

-   **partition_new_name**

    分区的新名称。

    取值范围：字符串，要符合标识符的命名规范。

## 注意事项<a name="zh-cn_topic_0283137443_zh-cn_topic_0237122077_zh-cn_topic_0059778761_s5b88399280d4435fbb63e27378589a97"></a>

-   添加分区的表空间不能是PG_GLOBAL。
-   添加分区的名称不能与该分区表已有分区的名称相同。
-   添加分区的分区键值要和分区表的分区键的类型一致。
-   若添加RANGE分区，添加分区键值要大于分区表中最后一个范围分区的上边界。
-   若添加LIST分区，添加分区键值不能与现有分区键值重复。
-   不支持添加HASH分区。
-   如果目标分区表中已有分区数达到了最大值1048575，则不能继续添加分区。
-   当分区表只有一个分区时，不能删除该分区。
-   选择分区使用PARTITION FOR()，括号里指定值个数应该与定义分区时使用的列个数相同，并且一一对应。
-   Value分区表不支持相应的Alter Partition操作。
-   列存分区表不支持切割分区。
-   间隔分区表不支持添加分区。
-   哈希分区表不支持切割分区，不支持合成分区，不支持添加和删除分区。
-   列表分区表不支持切割分区，不支持合成分区。
-   只有分区表的所有者或者被授予了分区表ALTER权限的用户有权限执行ALTER TABLE PARTITION命令，系统管理员默认拥有此权限。

## 示例<a name="zh-cn_topic_0283137443_zh-cn_topic_0237122077_zh-cn_topic_0059778761_s50d0d11ee3074db6911f91d1d9e31fbd"></a>

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
>
> 此处仅展示exchange_clause子语法的示例，其余更多示例请参考CREATE TABLE PARTITION的[示例](CREATE-TABLE-PARTITION.md#zh-cn_topic_0283136653_zh-cn_topic_0237122119_zh-cn_topic_0059777586_s43dd49de892344bf89e6f56f17404842)。

**示例1：**分区交换。

1、创建分区表。

```sql
create table t_range_list
( id number,
partition_key int,
subpartition_key int,
col2 varchar2(10) )
partition by range(partition_key)
subpartition by list(subpartition_key)(
partition p1 values less than (100)
(subpartition sub_1_1 values (10),
subpartition sub_1_2 values (20)),
partition p2 values less than(200)
(subpartition sub_2_1 values (10),
subpartition sub_2_2 values (20)),
partition p3 values less than (300)
(subpartition sub_3_1 values (10),
subpartition sub_3_2 values (20)));
```

2、插入测试数据。

```sql
INSERT INTO t_range_list VALUES(1,50,10,'sub_1_1');
INSERT INTO t_range_list VALUES(2,150,20,'sub_2_2');
INSERT INTO t_range_list VALUES(3,250,10,'sub_3_1');
```

3、创建用于交换的普通表。

```sql
create table t_exchange
( id number,
partition_key int,
subpartition_key int,
col2 varchar2(10) );
```

4、为普通表插入数据。

```sql
INSERT INTO t_exchange VALUES(1,40,10,'sub_1_1');
```

5、将分区sub_1_1的数据与普通表的数据进行交换。

```sql
ALTER TABLE  t_range_list exchange subpartition sub_1_1 with table t_exchange;
```

6、查看交换后的普通表数据。

```sql
select * from t_exchange;
```

返回结果为：

```sql
 id | partition_key | subpartition_key |  col2
----+---------------+------------------+---------
  1 |            50 |               10 | sub_1_1
(1 row)
```

7、查看交换后分区表的数据。

```sql
select * from t_range_list;
```

返回结果为：

```sql
 id | partition_key | subpartition_key |  col2
----+---------------+------------------+---------
  1 |            40 |               10 | sub_1_1
  2 |           150 |               20 | sub_2_2
  3 |           250 |               10 | sub_3_1
(3 rows)
```

**示例2：**进行增删列操作之后的分区交换。

1、创建range分区表。

```sql
CREATE TABLE e (
id INT NOT NULL,
fname VARCHAR(30), 
lname VARCHAR(30) )
PARTITION BY RANGE (id)
( PARTITION p0 VALUES LESS THAN (50),
PARTITION p1 VALUES LESS THAN (100),
PARTITION p2 VALUES LESS THAN (150),
PARTITION p3 VALUES LESS THAN (MAXVALUE) );
```

2、向分区表中插入测试数据。

```sql
INSERT INTO e VALUES (1669, 'Jim', 'Smith'), (337, 'Mary', 'Jones'), (16, 'Frank', 'White'), (2005, 'Linda', 'Black');
```

3、创建普通表，比步骤1中创建的分区表少一列lname。

```sql
CREATE TABLE e2 (
id INT NOT NULL,
fname VARCHAR(30));
```

4、为普通表新增一列lname，但数据类型和分区表中的lname列不同。

```sql
alter table e2 add column lname text;
```

5、交换分区。

```sql
alter table e exchange partition p3 with table e2;
```

返回结果如下，交换失败（同名列lname在普通表和分区表中数据类型不同）。

```
ERROR:  column type or size mismatch in ALTER TABLE EXCHANGE PARTITION
```

6、删除普通表中的列lname。

```sql
alter table e2 drop lname;
```

7、为普通表再次新增列lname，数据类型与分区表中的列lname相同。

```sql
alter table e2 add column lname VARCHAR(30);
```

8、执行分区交换，交换成功。

```sql
alter table e exchange partition p3 with table e2;
```

9、查看此时的分区表e和普通表e2的数据。

```sql
select * from e;
select * from e2;
```

返回结果为：

```sql
 id | fname | lname
----+-------+-------
 16 | Frank | White
(1 row)

  id  | fname | lname
------+-------+-------
 1669 | Jim   | Smith
  337 | Mary  | Jones
 2005 | Linda | Black
(3 rows)
```

