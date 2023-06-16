### DBMS_SQL

**功能描述**

DBMS_SQL主要功能是提供用于执行动态SQL(DML和DDL)的方法。该内置包包含以下函数：

<table >
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#OPEN_CURSOR">OPEN_CURSOR</a></td>
<td>打开一个cursor，并返回该cursor的id。</td>
</tr>
<tr>
<td><a href="#CLOSE_CURSOR">CLOSE_CURSOR</a> </td>
<td>用于关闭一个cursor。</td>
</tr>
<tr>
<td ><a href="#COLUMN_VALUE">COLUMN_VALUE</a> </td>
<td>用于将fetch结果中的指定列保存到返回参数中。</td>
</tr>
<tr>
<td><a href="#DEFINE_COLUMN">DEFINE_COLUMN</a> </td>
<td>用于定义返回的结果中的列结构。</td>
</tr>
<tr>
<td><a href="#EXECUTE">EXECUTE</a></td>
<td>用于执行PARSE函数处理的SQL语句。</td>
</tr>
<tr>
<td><a href="#FETCH_ROWS">FETCH_ROWS</a> </td>
<td>用于载入结果集中的一行结果。</td>
</tr>
<tr>
<td><a href="#PARSE">PARSE</a></td>
<td>解析当前语句的语法。</td>
</tr>
<tr>
<td><a href="#RETURN_RESULT">RETURN_RESULT</a></td>
<td>用于将已执行语句的结果返回到客户端应用程序。</td>
</tr>
</table>

**注意事项**

无。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：BIND_ARRAY、BIND_VARIABLE、BIND_VARIABLE_PKG、COLUMN_VALUE_LONG、DEFINE_ARRAY 、DEFINE_COLUMN_CHAR、DEFINE_COLUMN_LONG、DEFINE_COLUMN_RAW、DEFINE_COLUMN_ROWID、DESCRIBE_COLUMNS、DESCRIBE_COLUMNS2、DESCRIBE_COLUMNS3、EXECUTE_AND_FETCH、GET_NEXT_RESULT、IS_OPEN、LAST_ERROR_POSITION、LAST_ROW_ID、LAST_SQL_FUNCTION_CODE、TO_CURSOR_NUMBER 、TO_REFCURSOR、VARIABLE_VALUE、VARIABLE_VALUE_PKG。

**示例**

1、创建测试表并插入数据。

```
create table dbms_t1(id number,con varchar(20));
insert into dbms_t1 values(1.5,'aaa'),(2.0,'bbb'),(3,'ccc');
```

2、创建存储过程调用函数。

```
CREATE OR REPLACE function dbms_demo1() return text AS
    cursor_name INT;
    rows_processed INT;
            ret INT;
            rec text;
BEGIN
    cursor_name := DBMS_SQL.open_cursor();
            DBMS_SQL.PARSE(cursor_name,'select con from public.dbms_t1;',0);
            ret := DBMS_SQL.EXECUTE(cursor_name);
            loop
            if DBMS_SQL.FETCH_ROWS(cursor_name) > 0 then
                    select DBMS_SQL.COLUMN_VALUE(cursor_name,1,rec) into rec;
            else
                    exit;
            end if;
            end loop;
            DBMS_SQL.CLOSE_CURSOR(cursor_name);
            return rec;
END;
/
```

3、调用存储过程dbms_demo1()。

```
 select dbms_demo1();
```

当结果显示如下，则表示函数调用成功：

```
 dbms_demo1
------------
 ccc
(1 row)

```

4、创建测试表re_t1，re_t2并插入数据。

```
create table re_t1(c1 int,c2 varchar(32));
insert into re_t1 values(1,'111');
insert into re_t1 values(2,'222');
create table re_t2(c1 varchar(32),c2 int);
insert into re_t2 values('111',1);
insert into re_t2 values('222',2);
```

5、使用sys_refcursor类型作为输入。

```
create or replace procedure re_proc() as
rc1 sys_refcursor;
rc2 sys_refcursor;
begin
open rc1 for select * from re_t1;
dbms_sql.return_result(rc1);
open rc2 for select * from re_t2;
dbms_sql.return_result(rc2);
end;
/
```

6、调用存储过程re_proc()。

```
call re_proc();
```

当返回结果如下，则表示函数调用成功：

```
ResultSet #1

 c1 | c2
----+-----
  1 | 111
  2 | 222
(2 rows)

ResultSet #2

 c1  | c2
-----+----
 111 |  1
 222 |  2
(2 rows)

 re_proc
---------

(1 row)

```

7、使用integer类型作为输入参数。

```
create or replace procedure re_proc_int() as
id number;
id1 number:=dbms_sql.open_cursor();
id2 number:=dbms_sql.open_cursor();
begin
dbms_sql.parse(id1,'select * from public.re_t1',0);
id:=dbms_sql.execute(id1);
dbms_sql.return_result(id1);
dbms_sql.parse(id2,'select * from public.re_t2',0);
id:=dbms_sql.execute(id2);
dbms_sql.return_result(id2);
end;
/
```

8、调用存储过程re_proc_int()。

```
call re_proc_int();
```

当结果返回如下，则表示调用成功：

```
ResultSet #1

 c1 | c2
----+-----
  1 | 111
  2 | 222
(2 rows)

ResultSet #2

 c1  | c2
-----+----
 111 |  1
 222 |  2
(2 rows)

 re_proc_int
-------------

(1 row)

```

#### OPEN_CURSOR<a id="OPEN_CURSOR"></a>

**语法格式** 

```
DBMS_SQL.OPEN_CURSOR (
   c    IN OUT INTEGER);
```

**参数说明**
c：需要打开的游标ID号。

#### CLOSE_CURSOR<a id="CLOSE_CURSOR"></a>

**语法格式**

```
DBMS_SQL.CLOSE_CURSOR (
   c    IN OUT INTEGER);

```

**参数说明**

c：需要关闭的游标ID数。

#### COLUMN_VALUE<a id="COLUMN_VALUE"></a>

**语法格式**

```
DBMS_SQL.COLUMN_VALUE (
   c                 IN  INTEGER,
   position          IN  INTEGER,
   value             OUT <datatype> 
 [,column_error      OUT NUMBER] 
 [,actual_length     OUT INTEGER]);

```

**参数说明**

- c：游标ID号。
- position：光标中列的相对位置、语句中的第一列位置为1。
- value：返回指定列的值。
- column_error：返回指定列值的任何错误代码。
- actual_length：指定列的值在截断之前的实际长度

#### DEFINE_COLUMN<a id="DEFINE_COLUMN"></a>

**语法格式**

```
DBMS_SQL.DEFINE_COLUMN (
   c              IN INTEGER,
   position       IN INTEGER,
   column         IN <datatype>);


DBMS_SQL.DEFINE_COLUMN (
   c              IN INTEGER,
   position       IN INTEGER,
   column         IN VARCHAR2 CHARACTER SET ANY_CS,
   column_size    IN INTEGER);
```

**参数说明**

- c：游标ID号。
- position：正在定义的行中列的相对位置，语句中第一列的位置为1。
- colum：列的值
- column_size：列值的最大预期大小（以字节为单位）。

#### EXECUTE<a id="EXECUTE"></a>

**语法格式**

```
DBMS_SQL.EXECUTE (
   c   IN INTEGER)
  RETURN INTEGER;

```

**参数说明**

c：游标ID号。

#### FETCH_ROWS<a id="FETCH_ROWS"></a>

**语法格式**

```
DBMS_SQL.FETCH_ROWS (
   c              IN INTEGER)
  RETURN INTEGER;

```

**参数说明**

c：ID号。

#### PARSE<a id="PARSE"></a>

**语法格式**

```
DBMS_SQL.PARSE (
   c                           IN   INTEGER, 
   statement                   IN   VARCHAR2A,
   lb                          IN   INTEGER, 
   ub                          IN   INTEGER,
   lfflg                       IN   BOOLEAN, 
   language_flag               IN   INTEGER[
 [,edition                     IN   VARCHAR2 DEFAULT NULL],
   apply_crossedition_trigger  IN   VARCHAR2 DEFAULT NULL,
   fire_apply_trigger          IN   BOOLEAN DEFAULT TRUE]
 [,schema                      IN   VARCHAR2 DEFAULT NULL]
 [,container                   IN   VARCHAR2)];

DBMS_SQL.PARSE (
   c                           IN   INTEGER, 
   statement                   IN   VARCHAR2s,
   lb                          IN   INTEGER, 
   ub                          IN   INTEGER,
   lfflg                       IN   BOOLEAN, 
   language_flag               IN   INTEGER[
 [,edition                     IN   VARCHAR2 DEFAULT NULL],
   apply_crossedition_trigger  IN   VARCHAR2 DEFAULT NULL,
   fire_apply_trigger          IN   BOOLEAN DEFAULT TRUE]
 [,schema                      IN   VARCHAR2 DEFAULT NULL]
 [,container                   IN   VARCHAR2)];

```

**参数说明**

- c：要解析的语句的游标ID号。

- statement：要解析的SQL语句。

- lb：语句元素的下限。

- ub：语句中元素的上限。

- lfflg：如果设置为TRUN，则在连接的每个元素之后插入换行符。

- language_flag：指定 SQL 语句的行为。

- edition：指定在以下条件下运行语句的版本：

  - 如果是NULL该语句将在当前版本中运行。

  - 如果指定了有效容器，则通过NULL表示该语句将在目标容器的默认版本中运行。

  - 给定用户和执行语句的版本，用户必须USE对该版本具有特权。

    以下一般条件适用。字符串的内容作为 SQL 标识符处理；如果版本的实际名称中存在特殊字符或小写字符，则必须用双引号将字符串的其余部分括起来，如果不使用双引号，则内容将大写。

- apply_crossedition_trigger：指定要应用于指定 SQL 的正向交叉版本触发器的非限定名称。

- fire_apply_trigger：指示apply_crossedition_trigger的内容本身是要执行的，还是只能作为用于选择其他触发器的指南。

- schema：指定解析不合格对象名称的架构。如果NULL，则当前架构是有效用户的架构。

- container：要在其中运行的目标容器的名称。


#### RETURN_RESULT<a id="RETURN_RESULT"></a>

**语法格式**

```
DBMS_SQL.RETURN_RESULT(
   rc           IN OUT      SYS_REFCURSOR, 
   to_client    IN          BOOLEAN           DEFAULT TRUE);

DBMS_SQL.RETURN_RESULT(
   rc           IN OUT      INTEGER, 
   to_client    IN          BOOLEAN           DEFAULT TRUE);

```

**参数说明**

- rc：语句游标或引用游标。
- to_client：将语句结果返回（或不返回）给客户端。如果是FALSE，则将其返回给直接调用者。