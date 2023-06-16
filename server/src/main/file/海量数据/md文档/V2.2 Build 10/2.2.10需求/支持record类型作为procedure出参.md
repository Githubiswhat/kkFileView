# 支持record类型作为procedure出参

**功能描述**

在Oracle兼容模式下，Vastbase G100支持在package中定义的record类型作为procedure出参。

**语法格式**

```
--创建一个package
create or replace package pkg_themis_xmloperate as

--定义t_tagValue是以int作为数组标号、数组元素是varchar2(30000)的嵌套表类型
type t_tagValue is table of varchar2(30000)index by binary_integer;

--定义类型t_tagclobvalue是以int作为数组标号、数组元素是clob的嵌套表类型
type t_tagclobvalue is table of clob index binary_integer;

--定义一个record类型A，该类型里有两种子类型：xmldomnode,boolean
type t_approve_info is record(xmldomnode int,is_local boolean);

--定义一个存储过程，其中t_approve_info是一个在上面package里定义好的record类型，在这里被用于出参类型
procedure p_get_PD_from_approve_info(
p_stackid in number,
p_stackdepth in number,
P_in_node in xmldom.domnode,
p_out_data out t_approve_info,
p_case_flow_id in number default null,
p_flow_id in number default null);
end;
/
```

**注意事项**

- 仅在Oracle兼容模式下生效。
- 由于测试用例关联其他兼容特性，所以需要确保关联的兼容同时具备才可以正常执行。
- 兼容支持在package中使用type...is...语法，比如is table of、is record。

**操作步骤**

1、自定义record类型和这个存储过程都是在一个package的声明里使用的。

2、定义package。

3、定义record类型。

4、定义存储过程，使用前面定义的record类型作为出参。

**示例**

1、创建数据库，检查兼容性。

```
CREATE DATABASE my_test DBCOMPATIBILITY 'A';
\c my_test
show sql_compatibility;
```

2、创建一个包，定义record类型为int。

```
CREATE OR REPLACE PACKAGE pkg AS
TYPE rec_type IS RECORD(
col_int int,
col_smallint smallint,
col_integer integer,
col_bigint bigint,
col_int2 int2,
col_int4 int4,
col_int8 int8
);

PROCEDURE print_rec_type(
p_col_int in int,
p_col_smallint in smallint,
p_col_integer in integer,
p_col_bigint in bigint,
p_col_int2 in int2,
p_col_int4 in int4,
p_col_int8 in int8,
rec out rec_type);
END;
/
```

3、包中存储过程作为返回参数。

```
CREATE OR REPLACE PACKAGE BODY pkg AS
PROCEDURE print_rec_type(
p_col_int in int,
p_col_smallint in smallint,
p_col_integer in integer,
p_col_bigint in bigint,
p_col_int2 in int2,
p_col_int4 in int4,
p_col_int8 in int8,
rec out rec_type) IS

BEGIN

rec.col_int := p_col_int ;
rec.col_smallint:= p_col_smallint;
rec.col_integer := p_col_integer ;
rec.col_bigint := p_col_bigint ;
rec.col_int2 := p_col_int2 ;
rec.col_int4 := p_col_int4 ;
rec.col_int8 := p_col_int8 ;

END;
END pkg;
/
```

4、PLSQL中调用存储过程。

```
set serveroutput on;

DECLARE
rec_p pkg.rec_type;
begin
pkg.print_rec_type(1,11,11,11,34.5,34.5,123,rec_p);

DBMS_OUTPUT.PUT_LINE(rec_p.col_int );
DBMS_OUTPUT.PUT_LINE(rec_p.col_smallint);
DBMS_OUTPUT.PUT_LINE(rec_p.col_integer );
DBMS_OUTPUT.PUT_LINE(rec_p.col_bigint );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int2 );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int4 );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int8 );
end;
/
```

返回结果为：

```
1
11
11
11
35
35
123
```

