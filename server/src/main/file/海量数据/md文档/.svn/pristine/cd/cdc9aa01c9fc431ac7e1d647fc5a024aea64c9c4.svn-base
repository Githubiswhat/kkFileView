# **支持type自带构造器使用及new构造器语法**

**功能描述**

Vastbase支持使用Oracle中的type类型自带的构造器函数创建和使用，以及通过使用new构造器函数来创建新的集合对象。

**语法格式**

```
collection_name collection_type [:=collection_type(...)];
```

```
collection_name collection_type [:=new collection_type(...)];
```

**参数说明**

- collection_name：集合变量的名字。

- collection_type：有两层含义，一层它代表着一个先前以及声明的集合类型的名字，另一层它代表和该类型同名的构造函数。


**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

**示例1：** 用type自带构造器和new构造器初始化并赋值变量：

1、创建Oracle兼容模式下的数据库并创建type。

```
create database db1 dbcompatibility 'A';
\c db1
create type type1 as (i int, j varchar(20));
```

2、用type自带构造器和new构造器初始化并赋值变量。

```
declare
        n1 type1 := type1();
        n2 type1 := type1(1,'n1');
        n3 type1 := new type1();
        n4 type1 := new type1(1, 'n1');
begin
        raise notice '%' ,n1;
        raise notice '%' ,n2;
        raise notice '%' ,n3;
        raise notice '%' ,n4;
end;
/
```

执行结果：

```
NOTICE:  (,)
NOTICE:  (1,n1)
NOTICE:  (,)
NOTICE:  (1,n1)
ANONYMOUS BLOCK EXECUTE
```

**示例2：** 在声明类型时直接初始化并赋值变量 。

1、创建Oracle兼容模式下的数据库。

```
create database db1 dbcompatibility 'A';
\c db1
set serveroutput on;
```

2、在声明类型时直接用type自带构造器初始化并赋值变量。

```
DECLARE 
TYPE nest_loc_type IS TABLE OF VARCHAR2( 13 ) NOT NULL;
nest_loc_tab nest_loc_type := nest_loc_type( 'NEW YORK', 'DALLAS', 'CHICAGO' );  -->在声明时直接初始化并赋值：
BEGIN 
FOR i IN 1 .. nest_loc_tab.COUNT 
LOOP 
DBMS_OUTPUT.put_line( 'nest_loc_tab(' || i || ') value is ' || nest_loc_tab( i ) ); 
END LOOP; 
END;
/
```

结果展示为：

```
nest_loc_tab(1) value is NEW YORK
nest_loc_tab(2) value is DALLAS
nest_loc_tab(3) value is CHICAGO
ANONYMOUS BLOCK EXECUTE
```

3、在声明类型时直接用new构造器初始化并赋值变量。

```
DECLARE
TYPE nest_loc_type IS TABLE OF VARCHAR2( 13 ) NOT NULL;
nest_loc_tab nest_loc_type :=new  nest_loc_type( 'NEW YORK', 'DALLAS', 'CHICAGO' ); -->在声明时使用new直接初始化并赋值
BEGIN
FOR i IN 1 .. nest_loc_tab.COUNT 
LOOP
DBMS_OUTPUT.put_line( 'nest_loc_tab(' || i || ') value is ' || nest_loc_tab( i ) );
END LOOP;
END;
/
```

结果展示为：

```
nest_loc_tab(1) value is NEW YORK
nest_loc_tab(2) value is DALLAS
nest_loc_tab(3) value is CHICAGO
ANONYMOUS BLOCK EXECUTE
```

