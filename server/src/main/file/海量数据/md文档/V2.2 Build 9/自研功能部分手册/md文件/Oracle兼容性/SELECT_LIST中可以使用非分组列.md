#### **SELECT_LIST中可以使用非分组列**

**功能描述**

兼容MySQL、MariaDB中select_list可以使用非分组列。支持在select语句的目标列中，可以使用group by和order by中未列出的列。

**语法格式**

```
[ WITH [ RECURSIVE ] with_query [, ...] ] 
SELECT [/*+ plan_hint */] [ ALL | DISTINCT [ ON ( expression [, ...] ) ] ] 
{ * | {expression [ [ AS ] output_name ]} [, ...] } 
[ FROM from_item [, ...] ] 
[ WHERE condition ] 
[ GROUP BY grouping_element [, ...] ] 
[ HAVING condition [, ...] ] 
[ WINDOW {window_name AS ( window_definition )} [, ...] ] 
[ { UNION | INTERSECT | EXCEPT | MINUS } [ ALL | DISTINCT ] select ] 
[ ORDER BY {expression [ [ ASC | DESC | USING operator ] | nlssort_expression_clause ] [ NULLS { FIRST | LAST } ]} [, ...] ] 
[ LIMIT { [offset,] count | ALL } ] 
[ OFFSET start [ ROW | ROWS ] ] 
[ FETCH { FIRST | NEXT } [ count ] { ROW | ROWS } ONLY ] 
[ {FOR { UPDATE | SHARE } [ OF table_name [, ...] ] [ NOWAIT ]} [...] ];
```

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- vastbase_sql_mode='ONLY_FULL_GROUP_BY'（默认值）时，select list中不可以使用非分组列。

- vastbase_sql_mode=''时，select list中可以使用非分组列。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

2、创建测试表student，并插入测试数据。

```
CREATE TABLE student(sid int,name varchar2(20),age int, class varchar2(20));
INSERT INTO student VALUES(1,'陈一',22,'java');
INSERT INTO student VALUES(2,'黄二',23,'python');
INSERT INTO student VALUES(3,'张三',21,'java');
INSERT INTO student VALUES(4,'李四',20,'C#');
INSERT INTO student VALUES(5,'王五',19,'python');
INSERT INTO student VALUES(6,'赵六',21,'java');
INSERT INTO student VALUES(7,'钱七',21,'java');
INSERT INTO student VALUES(8,'孙八',20,'C++');
INSERT INTO student VALUES(9,'杨九',19,'C++');
```

3、设置vastbase_sql_mode并验证。

```
set vastbase_sql_mode='';
SHOW vastbase_sql_mode; 
```

当结果显示如下信息，则表示设置完成。

```
-------------------

(1 row)
```

4、验证结果。

```
SELECT * FROM student GROUP BY class;
```

当结果显示如下信息，则表示验证完成。

```
 sid | name | age | class  
-----+------+-----+--------
   8 | 孙八 |  20 | C++
   4 | 李四 |  20 | C#
   2 | 黄二 |  23 | python
   1 | 陈一 |  22 | java
(4 rows)
```

