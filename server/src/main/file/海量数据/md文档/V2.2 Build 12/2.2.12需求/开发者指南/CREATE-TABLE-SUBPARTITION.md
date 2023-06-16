# CREATE TABLE SUBPARTITION<a name="ZH-CN_TOPIC_0000001198046401"></a>

## 功能描述<a name="zh-cn_topic_0283136653_zh-cn_topic_0237122119_section1163224811518"></a>

创建二级分区表。分区表是把逻辑上的一张表根据某种方案分成几张物理块进行存储，这张逻辑上的表称之为分区表，物理块称之为分区。分区表是一张逻辑表，不存储数据，数据实际是存储在分区上的。对于二级分区表，顶层节点表和一级分区都是逻辑表，不存储数据，只有二级分区（叶子节点）存储数据。

二级分区功能可参见管理员指南：[支持两级分区](../管理员指南/支持两级分区.md)。二级分区表的分区方案是由两个一级分区的分区方案组合而来的，一级分区的分区方案详见[CREATE TABLE PARTITION](CREATE-TABLE-PARTITION.md)。

常见的二级分区表组合方案有Range-Range分区、Range-List分区、Range-Hash分区、List-Range分区、List-List分区、List-Hash分区、Hash-Range分区、Hash-List分区、Hash-Hash分区等。目前二级分区仅支持行存表。

## 语法格式<a name="section11556125664117"></a>

```sql
CREATE TABLE [ IF NOT EXISTS ] subpartition_table_name
( 
{ column_name data_type [ COLLATE collation ] [ column_constraint [ ... ] ]
| table_constraint
| LIKE source_table [ like_option [...] ] }[, ... ]
)
[ WITH ( {storage_parameter = value} [, ... ] ) ]
[ COMPRESS | NOCOMPRESS ]
[ TABLESPACE tablespace_name ]

PARTITION BY { 
        {RANGE (partition_key) [ INTERVAL ('interval_expr') [ STORE IN (tablespace_name [, ... ] ) ] ] ( partition_less_than_item [, ... ] )} |
        {RANGE (partition_key) [ INTERVAL ('interval_expr') [ STORE IN (tablespace_name [, ... ] ) ] ] ( partition_start_end_item [, ... ] )} |
        {LIST | HASH (partition_key) (PARTITION partition_name [VALUES (list_values_clause)] opt_table_space )}
    } 
        
(
  PARTITION partition_name1 [ VALUES LESS THAN (val1) | VALUES (val1[, …]) ] [ TABLESPACE tablespace ]
  (
       { SUBPARTITION subpartition_name1 [ VALUES LESS THAN (val1_1) | VALUES (val1_1[, …])]  [ TABLESPACE tablespace ] } [, ...]
  )[, ...]
)
[ { ENABLE | DISABLE } ROW MOVEMENT ];
```

-   列约束column_constraint：


<br />

```sql
[ CONSTRAINT constraint_name ]
    { NOT NULL |
      NULL | 
      CHECK ( expression ) | 
      DEFAULT default_e xpr | 
      GENERATED ALWAYS AS ( generation_expr ) STORED |
      UNIQUE index_parameters | 
      PRIMARY KEY index_parameters |
      REFERENCES reftable [ ( refcolumn ) ] [ MATCH FULL | MATCH PARTIAL | MATCH SIMPLE ]
            [ ON DELETE action ] [ ON UPDATE action ] }
[ DEFERRABLE | NOT DEFERRABLE | INITIALLY DEFERRED | INITIALLY IMMEDIATE ]
```

-   表约束table_constraint：

<br />

```sql
[ CONSTRAINT constraint_name ]
    { CHECK ( expression ) | 
      UNIQUE ( column_name [, ... ] ) index_parameters | 
      PRIMARY KEY ( column_name [, ... ] ) index_parameters |
      FOREIGN KEY ( column_name [, ... ] ) REFERENCES reftable [ ( refcolumn [, ... ] ) ]
      [ MATCH FULL | MATCH PARTIAL | MATCH SIMPLE ] [ ON DELETE action ] [ ON UPDATE action ] }
[ DEFERRABLE | NOT DEFERRABLE | INITIALLY DEFERRED | INITIALLY IMMEDIATE ]
```


-   like选项like_option：

<br />

```sql
{ INCLUDING | EXCLUDING } { DEFAULTS | GENERATED | CONSTRAINTS | INDEXES | STORAGE | COMMENTS | RELOPTIONS| ALL }
```


-   索引存储参数index_parameters：

<br />

```sql
[ WITH ( {storage_parameter = value} [, ... ] ) ]
[ USING INDEX TABLESPACE tablespace_name ]
```


## 参数说明<a name="section7923313718"></a>

- **IF NOT EXISTS**

    如果已经存在相同名称的表，不会抛出一个错误，而会发出一个通知，告知表关系已存在。

- **subpartition_table_name**

    二级分区表的名称。

    取值范围：字符串，要符合标识符的命名规范。

- **column_name**

    新表中要创建的字段名。

    取值范围：字符串，要符合标识符的命名规范。

- **data_type**

    字段的数据类型。

- **COLLATE  collation**

    COLLATE子句指定列的排序规则（该列必须是可排列的数据类型）。如果没有指定，则使用默认的排序规则。排序规则可以使用`select * from pg_collation;`命令从pg_collation系统表中查询，默认的排序规则为查询结果中以default开始的行。

- **CONSTRAINT constraint_name**

    列约束或表约束的名称。可选的约束子句用于声明约束，新行或者更新的行必须满足这些约束才能成功插入或更新。
	定义约束有两种方法：

  - 列约束：作为一个列定义的一部分，仅影响该列。
  - 表约束：不和某个列绑在一起，可以作用于多个列。

- **LIKE source_table [ like_option ... ]**

    二级分区表暂不支持该功能。

-   **WITH ( storage_parameter [= value] [, ... ] )**

    这个子句为表或索引指定一个可选的存储参数。参数的详细描述如下所示：

    -   FILLFACTOR

        一个表的填充因子（fillfactor）是一个介于10和100之间的百分数。100（完全填充）是默认值。如果指定了较小的填充因子，INSERT操作仅按照填充因子指定的百分率填充表页。每个页上的剩余空间将用于在该页上更新行，这就使得UPDATE有机会在同一页上放置同一条记录的新版本，这比把新版本放置在其他页上更有效。对于一个从不更新的表将填充因子设为100是最佳选择，但是对于频繁更新的表，选择较小的填充因子则更加合适。该参数对于列存表没有意义。

        取值范围：10~100

    - ORIENTATION

      决定了表的数据的存储方式。

      > <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
      > orientation不支持修改。
      
      取值范围：
      
      -   COLUMN：表的数据将以列式存储。
      -   ROW（缺省值）：表的数据将以行式存储。
    
    - COMPRESSION
      -   列存表的有效值为LOW/MIDDLE/HIGH/YES/NO，压缩级别依次升高，默认值为LOW。
      -   行存表不支持压缩。
    
    - MAX_BATCHROW
    
      指定了在数据加载过程中一个存储单元可以容纳记录的最大数目。该参数只对列存表有效。
    
      取值范围：10000~60000，默认60000。
    
    - PARTIAL_CLUSTER_ROWS
    
      指定了在数据加载过程中进行将局部聚簇存储的记录数目。该参数只对列存表有效。
    
      取值范围：大于等于MAX_BATCHROW，建议取值为MAX_BATCHROW的整数倍数。
    
    - DELTAROW_THRESHOLD
    
      预留参数。该参数只对列存表有效。
    
      取值范围：0～9999


-   **COMPRESS / NOCOMPRESS**

    创建一个新表时，需要在创建表语句中指定关键字COMPRESS，这样，当对该表进行批量插入时就会触发压缩特性。该特性会在页范围内扫描所有元组数据，生成字典、压缩元组数据并进行存储。指定关键字NOCOMPRESS则不对表进行压缩。行存表不支持压缩。

    缺省值为NOCOMPRESS，即不对元组数据进行压缩。

-   **TABLESPACE tablespace_name**

    指定新表将要在tablespace_name表空间内创建。如果没有声明，将使用默认表空间。

-   **PARTITION BY {RANGE | LIST | HASH} (partition_key)**
    
    -   对于partition_key，分区策略的分区键仅支持1列。
    -   分区键支持的数据类型和一级分区表约束保持一致。
    
- **PARTITION BY RANGE(partition_key)**

  创建范围分区。partition_key为分区键的名称。

  - 对于从句是VALUES LESS THAN的语法格式：


  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > 对于从句是VALUE LESS THAN的语法格式，范围分区策略的分区键最多支持4列。

  该情形下，分区键支持的数据类型为：SMALLINT、INTEGER、BIGINT、DECIMAL、NUMERIC、REAL、DOUBLE PRECISION、CHARACTER VARYING(n)、VARCHAR(n)、CHARACTER(n)、CHAR(n)、CHARACTER、CHAR、TEXT、NVARCHAR、NVARCHAR2、NAME、TIMESTAMP[(p)] [WITHOUT TIME ZONE]、TIMESTAMP[(p)] [WITH TIME ZONE]、DATE。

  - 对于从句是START END的语法格式：


  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > 对于从句是START END的语法格式，范围分区策略的分区键仅支持1列。

  该情形下，分区键支持的数据类型为：SMALLINT、INTEGER、BIGINT、DECIMAL、NUMERIC、REAL、DOUBLE PRECISION、TIMESTAMP[(p)] [WITHOUT TIME ZONE]、TIMESTAMP[(p)] [WITH TIME ZONE]、DATE。

  - 对于指定了INTERVAL子句的语法格式：


  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > 对于指定了INTERVAL子句的语法格式，范围分区策略的分区键仅支持1列。

  该情形下，分区键支持的数据类型为：TIMESTAMP[(p)] [WITHOUT TIME ZONE]、TIMESTAMP[(p)] [WITH TIME ZONE]、DATE。

- **PARTITION partition_name VALUES LESS THAN ( { partition_value | MAXVALUE } )**

  指定各分区的信息。partition_name为范围分区的名称。partition_value为范围分区的上边界，取值依赖于partition_key的类型。MAXVALUE表示分区的上边界，它通常用于设置最后一个范围分区的上边界。

  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > - 每个分区都需要指定一个上边界。
  > - 分区上边界的类型应当和分区键的类型一致。
  > - 分区列表是按照分区上边界升序排列的，值较小的分区位于值较大的分区之前。

- **PARTITION partition_name {START (partition_value) END (partition_value) EVERY (interval_value)} | {START (partition_value) END (partition_value|MAXVALUE)} | {START(partition_value)} | {END (partition_value | MAXVALUE)**}

  指定各分区的信息，各参数意义如下：

  - partition_name：范围分区的名称或名称前缀，除以下情形外（假定其中的partition_name是p1），均为分区的名称。
    - 若该定义是START+END+EVERY从句，则语义上定义的分区的名称依次为p1_1, p1_2, …。例如对于定义“PARTITION p1 START(1) END(4) EVERY(1)”，则生成的分区是：[1, 2), [2, 3) 和 [3, 4)，名称依次为p1_1, p1_2和p1_3，即此处的p1是名称前缀。
    - 若该定义是第一个分区定义，且该定义有START值，则范围（MINVALUE, START）将自动作为第一个实际分区，其名称为p1_0，然后该定义语义描述的分区名称依次为p1_1, p1_2, …。例如对于完整定义“PARTITION p1 START(1), PARTITION p2 START(2)”，则生成的分区是：(MINVALUE, 1), [1, 2) 和 [2, MAXVALUE)，其名称依次为p1_0, p1_1和p2，即此处p1是名称前缀，p2是分区名称。这里MINVALUE表示最小值。
  - partition_value：范围分区的端点值（起始或终点），取值依赖于partition_key的类型，不可是MAXVALUE。
  - interval_value：对[START，END) 表示的范围进行切分，interval_value是指定切分后每个分区的宽度，不可是MAXVALUE；如果（END-START）值不能整除以EVERY值，则仅最后一个分区的宽度小于EVERY值。
  - MAXVALUE：表示最大值，它通常用于设置最后一个范围分区的上边界。

  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > 1、在创建分区表若第一个分区定义含START值，则范围（MINVALUE，START）将自动作为实际的第一个分区。
  >
  > 2、START END语法需要遵循以下限制：
  >
  > - 每个partition_start_end_item中的START值（如果有的话，下同）必须小于其END值。
  > - 相邻的两个partition_start_end_item，第一个的END值必须等于第二个的START值；
  > - 每个partition_start_end_item中的EVERY值必须是正向递增的，且必须小于（END-START）值；
  > - 每个分区包含起始值，不包含终点值，即形如：[起始值，终点值)，起始值是MINVALUE时则不包含；
  > - 一个partition_start_end_item创建的每个分区所属的TABLESPACE一样；
  > - partition_name作为分区名称前缀时，其长度不要超过57字节，超过时自动截断；
  > - 在创建、修改分区表时请注意分区表的分区总数不可超过最大限制（1048575）；
  >
  > 3、 在创建分区表时START END与LESS THAN语法不可混合使用。
  >
  > 4、 即使创建分区表时使用START END语法，备份（vb_dump）出的SQL语句也是VALUES LESS THAN语法格式。

- **INTERVAL ('interval_expr') [ STORE IN (tablespace_name [, … ] ) ]**

  间隔分区定义信息。

  - interval_expr：自动创建分区的间隔，例如：1 day、1 month。
  - STORE IN (tablespace_name [, … ] )：指定存放自动创建分区的表空间列表，如果有指定，则自动创建的分区从表空间列表中循环选择使用，否则使用分区表默认的表空间。

  > <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
  >
  > 列存表不支持间隔分区。

- **PARTITION BY LIST(partition_key)**

  创建列表分区。partition_key为分区键的名称。

  - 对于partition_key，列表分区策略的分区键仅支持1列。
  - 对于从句是VALUES (list_values_clause)的语法格式，list_values_clause中包含了对应分区存在的键值，每个分区的键值数量不超过127个。

  分区键支持的数据类型为：INT1、INT2、INT4、INT8、NUMERIC、VARCHAR(n)、CHAR、BPCHAR、NVARCHAR、NVARCHAR2、TIMESTAMP[(p)] [WITHOUT TIME ZONE]、TIMESTAMP[(p)] [WITH TIME ZONE]、DATE。分区个数不能超过 1048575 个。

- **PARTITION BY HASH(partition_key)**

  创建哈希分区。partition_key为分区键的名称。

  对于partition_key，哈希分区策略的分区键仅支持1列。

  分区键支持的数据类型为：INT1、INT2、INT4、INT8、NUMERIC、VARCHAR(n)、CHAR、BPCHAR、TEXT、NVARCHAR、NVARCHAR2、TIMESTAMP[(p)] [WITHOUT TIME ZONE]、TIMESTAMP[(p)] [WITH TIME ZONE]、DATE。分区个数不能超过1048575 个。


-   **{ ENABLE | DISABLE } ROW MOVEMENT**

    行迁移开关。

    如果进行UPDATE操作时，更新了元组在分区键上的值，造成了该元组所在分区发生变化，就会根据该开关给出报错信息，或者进行元组在分区间的转移。

    取值范围：

    -   ENABLE（缺省值）：行迁移开关打开。
    -   DISABLE：行迁移开关关闭。

-   **NOT NULL**

    字段值不允许为NULL。ENABLE用于语法兼容，可省略。

-   **NULL**

    字段值允许NULL ，这是缺省。

    这个子句只是为和非标准SQL数据库兼容。不建议使用。

-   **CHECK (condition) [ NO INHERIT ]**

    CHECK约束声明一个布尔表达式，每次要插入的新行或者要更新的行的新值必须使表达式结果为真或未知才能成功，否则会抛出一个异常并且不会修改数据库。

    声明为字段约束的检查约束应该只引用该字段的数值，而在表约束里出现的表达式可以引用多个字段。

    用NO INHERIT标记的约束将不会传递到子表中去。

    ENABLE用于语法兼容，可省略。

-   **DEFAULT default_expr**

    DEFAULT子句给字段指定缺省值。该数值可以是任何不含变量的表达式(不允许使用子查询和对本表中的其他字段的交叉引用)。缺省表达式的数据类型必须和字段类型匹配。

    缺省表达式将被用于任何未声明该字段数值的插入操作。如果没有指定缺省值则缺省值为NULL 。

-   **GENERATED ALWAYS AS ( generation_expr ) STORED**

    该子句将字段创建为生成列，生成列的值在写入（插入或更新）数据时由generation_expr计算得到，STORED表示像普通列一样存储生成列的值。

    > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
    >
    >-   生成表达式不能以任何方式引用当前行以外的其他数据。生成表达式不能引用其他生成列，不能引用系统列。生成表达式不能返回结果集，不能使用子查询，不能使用聚集函数，不能使用窗口函数。生成表达式调用的函数只能是不可变（IMMUTABLE）函数。
    >
    >-   不能为生成列指定默认值。
    >
    >-   生成列不能作为分区键的一部分。
    >
    >-   生成列不能和ON UPDATE约束字句的CASCADE、SET NULL、SET DEFAULT动作同时指定。生成列不能和ON DELETE约束字句的SET NULL、SET DEFAULT动作同时指定。
    >
    >-   修改和删除生成列的方法和普通列相同。删除生成列依赖的普通列，生成列被自动删除。不能改变生成列所依赖的列的类型。
    >
    >-   生成列不能被直接写入。在INSERT或UPDATE命令中, 不能为生成列指定值, 但是可以指定关键字DEFAULT。
    >
    >-   生成列的权限控制和普通列一样。
    > 
    >
    >-   不能为生成列指定默认值。
    >
    >-   生成列不能作为分区键的一部分。
    >
    >-   生成列不能和ON UPDATE约束字句的CASCADE、SET NULL、SET DEFAULT动作同时指定。生成列不能和ON DELETE约束字句的SET NULL、SET DEFAULT动作同时指定。
    >
    >-   修改和删除生成列的方法和普通列相同。删除生成列依赖的普通列，生成列被自动删除。不能改变生成列所依赖的列的类型。
    >
    >-   生成列不能被直接写入。在INSERT或UPDATE命令中, 不能为生成列指定值, 但是可以指定关键字DEFAULT。
    >
    >-   生成列的权限控制和普通列一样。
    >
    >-   列存表、内存表MOT不支持生成列。外表中仅postgres_fdw支持生成列。

-   **UNIQUE index_parameters**

    **UNIQUE ( column_name [, ... ] ) index_parameters**

    UNIQUE约束表示表里的一个字段或多个字段的组合必须在全表范围内唯一。

    对于唯一约束，NULL被认为是互不相等的。

-   **PRIMARY KEY index_parameters**

    **PRIMARY KEY ( column_name [, ... ] ) index_parameters**

    主键约束声明表中的一个或者多个字段只能包含唯一的非NULL值。

    一个表只能声明一个主键。

-   **DEFERRABLE | NOT DEFERRABLE**

    这两个关键字设置该约束是否可推迟。一个不可推迟的约束将在每条命令之后马上检查。可推迟约束可以推迟到事务结尾使用SET CONSTRAINTS命令检查。缺省是NOT DEFERRABLE。目前，UNIQUE约束、主键约束、外键约束可以接受这个子句。所有其他约束类型都是不可推迟的。

-   **INITIALLY IMMEDIATE | INITIALLY DEFERRED**

    如果约束是可推迟的，则这个子句声明检查约束的缺省时间。

    -   如果约束是INITIALLY IMMEDIATE（缺省），则在每条语句执行之后就立即检查它；
    -   如果约束是INITIALLY DEFERRED ，则只有在事务结尾才检查它。

    约束检查的时间可以用SET CONSTRAINTS命令修改。

-   **USING INDEX TABLESPACE tablespace_name**

    为UNIQUE或PRIMARY KEY约束相关的索引声明一个表空间。如果没有提供这个子句，这个索引将在default_tablespace中创建，如果default_tablespace为空，将使用数据库的缺省表空间。

## 注意事项<a name="zh-cn_topic_0283136653_zh-cn_topic_0237122119_zh-cn_topic_0059777586_s0bb17f15d73a4d978ef028b2686e0f7a"></a>

-   二级分区表有两个分区键，每个分区键只能支持1列。
-   唯一约束和主键约束的约束键包含所有分区键将为约束创建LOCAL索引，否则创建GLOBAL索引。
-   二级分区表的二级分区（叶子节点）个数不能超过1048575个，一级分区无限制，但一级分区下面至少有一个二级分区。
-   二级分区表只支持行存，不支持列存、段页式、hashbucket。
-   不支持Upsert、Merge into。
-   指定分区查询时，如`select * from tablename partition/subpartition (partition_name);`，关键字partition和subpartition注意不要写错。如果写错，查询不会报错，这时查询会变为对表起别名进行查询。
-   不支持对二级分区 subpartition for (values)查询。如`select * from tablename subpartition for (values);`。
-   对于二级分区表PARTITION FOR (values)语法，values只能是常量。
-   对于分区表PARTITION/SUBPARTITION FOR (values)语法，values在需要数据类型转换时，建议使用强制类型转换，以防隐式类型转换结果与预期不符。
-   目前Hash分区是按倒序排列的，即通过哈希和取余计算后得到的分区下标与创建顺序相反，同样EXPLAIN计划显示的Selected Partitions的序号排序也与创建顺序相反。
-   不支持密态数据库、账本数据库和行级访问控制。
-   指定分区语句目前不能走全局索引扫描。

## 示例<a name="section3608124119220"></a>

**示例1：**创建list_list组合类型的二级分区表并进行truncate（清空）和drop（删除）分区的操作。

1、创建表list_list（包含一级分区和二级分区）。

```sql
CREATE TABLE list_list
    (
        month_code VARCHAR2 ( 30 ) NOT NULL ,
        dept_code  VARCHAR2 ( 30 ) NOT NULL ,
        user_no    VARCHAR2 ( 30 ) NOT NULL ,
        sales_amt  int
    )
    PARTITION BY LIST (month_code) SUBPARTITION BY LIST (dept_code)
    (
      PARTITION p_201901 VALUES ( '201902' )
      (
        SUBPARTITION p_201901_a VALUES ( '1' ),
        SUBPARTITION p_201901_b VALUES ( default )
      ),
      PARTITION p_201902 VALUES ( '201903' )
      (
        SUBPARTITION p_201902_a VALUES ( '1' ),
        SUBPARTITION p_201902_b VALUES ( '2' )
      )
    );
```

2、插入测试数据。

```sql
insert into list_list values('201902', '1', '1', 1);
insert into list_list values('201902', '2', '1', 1);
insert into list_list values('201902', '1', '1', 1);
insert into list_list values('201903', '2', '1', 1);
insert into list_list values('201903', '1', '1', 1);
insert into list_list values('201903', '2', '1', 1);
```

3、查看表。

```sql
select * from list_list;
```

返回结果为：

```sql
 month_code | dept_code | user_no | sales_amt
------------+-----------+---------+-----------
 201902     | 1         | 1       |         1
 201902     | 1         | 1       |         1
 201902     | 2         | 1       |         1
 201903     | 1         | 1       |         1
 201903     | 2         | 1       |         1
 201903     | 2         | 1       |         1
 (6 rows)
```

4、查看其中的一个分区p_201901。

```sql
select * from list_list partition (p_201901);
```

返回结果为：

```sql
 month_code | dept_code | user_no | sales_amt
------------+-----------+---------+-----------
 201902     | 1         | 1       |         1
 201902     | 1         | 1       |         1
 201902     | 2         | 1       |         1
(3 rows)
```

5、清空其中的一个分区p_201901。

```sql
alter table list_list truncate partition p_201901;
```

6、再次查看这个分区。

```sql
select * from list_list partition (p_201901);
```

返回结果为空，表示该分区已经被清空。

```sql
 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
(0 rows)
```

7、删除一级分区p_201901。

```sql
alter table list_list drop partition p_201901;
```

8、查看表list_list。

```sql
select * from list_list;
```

返回结果如下，分区p_201901已被删除。

```sql
 month_code | dept_code | user_no | sales_amt
------------+-----------+---------+-----------
 201903     | 1         | 1       |         1
 201903     | 2         | 1       |         1
 201903     | 2         | 1       |         1
(3 rows)
```

**示例2：**创建一个list_range分区组合的分区表，并给分区表新增一级分区和二级分区。

1、创建表list_range。（一级分区为list分区，二级分区为range分区。）

```sql
create table list_range
(      id number not null, 
       partition_key   int, 
       subpartition_key   int,      
       col2   varchar2(10)  
)  
partition by list(partition_key)
subpartition by range(subpartition_key)
(  
    partition t_partition_01 values (100) 
    (subpartition sub_1_1 values less than (10),
     subpartition sub_1_2 values less than (20)
     ),
    partition t_partition_02 values (200)
    (subpartition sub_2_1 values less than (10),
     subpartition sub_2_2 values less than (20)
     )
);
```

2、新增分区。

```sql
alter table list_range add partition t_partition_03 values (300)
( subpartition sub_3_1 values less than (10),
 subpartition sub_3_2 values less than (20)
);
```

3、在系统表[pg_partition](PG_PARTITION.md)中查询分区信息。

```sql
\x  --启用列式方式显示结果
select * from pg_partition where relname='t_partition_03';
```

返回结果如下，查看到其中parentid为19029。

```sql
-[ RECORD 1 ]------+----------------------------------------------------------------
relname            | list_range
parttype           | r
parentid           | 19029
rangenum           | 0
intervalnum        | 0
partstrategy       | l
relfilenode        | 0
reltablespace      | 0
relpages           | 0
reltuples          | 0
relallvisible      | 0
reltoastrelid      | 0
reltoastidxid      | 0
indextblid         | 0
indisusable        | t
reldeltarelid      | 0
reldeltaidx        | 0
relcudescrelid     | 0
relcudescidx       | 0
relfrozenxid       | 0
intspnum           |
partkey            | 2
intervaltablespace |
interval           |
boundaries         |
transit            |
reloptions         | {orientation=row,compression=no,fillfactor=80,wait_clean_gpi=n}
relfrozenxid64     | 0
relminmxid         | 0
```

4、根据上一步查询到的parentid，查询对应分区信息。

```sql
select * from pg_partition where parentid='19029';
```

返回结果如下，一级分区和二级分区添加成功。

```sql
-[ RECORD 1 ]------+----------------------------------------------------------------
relname            | t_partition_03
parttype           | p
parentid           | 19029
rangenum           | 0
intervalnum        | 0
partstrategy       | l
relfilenode        | 0
reltablespace      | 0
relpages           | 0
reltuples          | 0
relallvisible      | 0
reltoastrelid      | 0
reltoastidxid      | 0
indextblid         | 0
indisusable        | t
reldeltarelid      | 0
reldeltaidx        | 0
relcudescrelid     | 0
relcudescidx       | 0
relfrozenxid       | 18570
intspnum           |
partkey            | 3
intervaltablespace |
interval           |
boundaries         | {300}
transit            |
reloptions         | {orientation=row,compression=no,fillfactor=80}
relfrozenxid64     | 18570
relminmxid         | 2
-[ RECORD 2 ]------+----------------------------------------------------------------
relname            | list_range
parttype           | r
parentid           | 19029
rangenum           | 0
intervalnum        | 0
partstrategy       | l
relfilenode        | 0
reltablespace      | 0
relpages           | 0
reltuples          | 0
relallvisible      | 0
reltoastrelid      | 0
reltoastidxid      | 0
indextblid         | 0
indisusable        | t
reldeltarelid      | 0
reldeltaidx        | 0
relcudescrelid     | 0
relcudescidx       | 0
relfrozenxid       | 0
intspnum           |
partkey            | 2
intervaltablespace |
interval           |
boundaries         |
transit            |
reloptions         | {orientation=row,compression=no,fillfactor=80,wait_clean_gpi=n}
relfrozenxid64     | 0
relminmxid         | 0
-[ RECORD 3 ]------+----------------------------------------------------------------
relname            | t_partition_01
parttype           | p
parentid           | 19029
rangenum           | 0
intervalnum        | 0
partstrategy       | l
relfilenode        | 0
reltablespace      | 0
relpages           | 0
reltuples          | 0
relallvisible      | 0
reltoastrelid      | 0
reltoastidxid      | 0
indextblid         | 0
indisusable        | t
reldeltarelid      | 0
reldeltaidx        | 0
relcudescrelid     | 0
relcudescidx       | 0
relfrozenxid       | 18566
intspnum           |
partkey            | 3
intervaltablespace |
interval           |
boundaries         | {100}
transit            |
reloptions         | {orientation=row,compression=no,fillfactor=80}
relfrozenxid64     | 18566
relminmxid         | 2
-[ RECORD 4 ]------+----------------------------------------------------------------
relname            | t_partition_02
parttype           | p
parentid           | 19029
rangenum           | 0
intervalnum        | 0
partstrategy       | l
relfilenode        | 0
reltablespace      | 0
relpages           | 0
reltuples          | 0
relallvisible      | 0
reltoastrelid      | 0
reltoastidxid      | 0
indextblid         | 0
indisusable        | t
reldeltarelid      | 0
reldeltaidx        | 0
relcudescrelid     | 0
relcudescidx       | 0
relfrozenxid       | 18566
intspnum           |
partkey            | 3
intervaltablespace |
interval           |
boundaries         | {200}
transit            |
reloptions         | {orientation=row,compression=no,fillfactor=80}
relfrozenxid64     | 18566
relminmxid         | 2
```

**示例3：**对二级分区表进行split（分区切割）操作。

1、创建分区表。

```sql
CREATE TABLE list_list
(
    month_code VARCHAR2 ( 30 ) NOT NULL ,
    dept_code  VARCHAR2 ( 30 ) NOT NULL ,
    user_no    VARCHAR2 ( 30 ) NOT NULL ,
    sales_amt  int
)
PARTITION BY LIST (month_code) SUBPARTITION BY LIST (dept_code)
(
  PARTITION p_201901 VALUES ( '201902' )
  (
    SUBPARTITION p_201901_a VALUES ( '1' ),
    SUBPARTITION p_201901_b VALUES ( default )
  ),
  PARTITION p_201902 VALUES ( '201903' )
  (
    SUBPARTITION p_201902_a VALUES ( '1' ),
    SUBPARTITION p_201902_b VALUES ( default )
  )
);
```

2、插入测试数据。

```sql
insert into list_list values('201902', '1', '1', 1);
insert into list_list values('201902', '2', '1', 1);
insert into list_list values('201902', '1', '1', 1);
insert into list_list values('201903', '2', '1', 1);
insert into list_list values('201903', '1', '1', 1);
insert into list_list values('201903', '2', '1', 1);
```

3、查看表list_list。

```sql
select * from list_list;
```

返回结果如下：

```sql
 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
 201903     | 2         | 1       |         1
 201903     | 2         | 1       |         1
 201903     | 1         | 1       |         1
 201902     | 2         | 1       |         1
 201902     | 1         | 1       |         1
 201902     | 1         | 1       |         1
(6 rows)
```

4、查看二级分区p_201901_a。

```sql
select * from list_list subpartition (p_201901_a);
```

返回结果为：

```sql
 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
 201902     | 1         | 1       |         1
 201902     | 1         | 1       |         1
(2 rows)
```

5、查看二级分区p_201901_b。

```sql
select * from list_list subpartition (p_201901_b);
```

返回结果为：

```sql
 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
 201902     | 2         | 1       |         1
(1 row)
```

6、将分区p_201901_b分割为p_201901_b和p_201901_c。

```sql
alter table list_list split subpartition p_201901_b values (2) into
(
 subpartition p_201901_b,
 subpartition p_201901_c
);
```

7、查看分区p_201901_a、p_201901_b、p_201901_c。

```sql
select * from list_list subpartition (p_201901_a);
select * from list_list subpartition (p_201901_b);
select * from list_list subpartition (p_201901_c);
```

返回结果为：

```sql
 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
 201902     | 1         | 1       |         1
 201902     | 1         | 1       |         1
(2 rows)

 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
 201902     | 2         | 1       |         1
(1 row)

 month_code | dept_code | user_no | sales_amt 
------------+-----------+---------+-----------
(0 rows)
```

