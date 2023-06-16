# %type语法

**功能描述**

包内支持使用%type定义变量的类型，赋值时包体可自动转换。

**语法格式**

```
create or replace package package_name as
variable_name table_name.column_name%type;
```

**参数说明**

- package_name：用户定义的包名称。

- variable_name：用户定义的变量名称。

- table_name.column_name ：%type参考的类型对应的列名（当调用其他表或包时，用xx.xx指定，如语法中的table_name.column_name)。


**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- %type支持vastbase支持的类型。

**示例**

1、创建数据库。

```
CREATE DATABASE db_1087209 DBCOMPATIBILITY 'A';
\c db_1087209
```

2、创建包，%type定义变量var2的类型为字符型。

```
create or replace package pk_1087209 as
var1 varchar(5);
var2 var1%type;
end;
/
```

3、创建包体，赋值类型为数值型。

```
create or replace package body pk_1087209 as
procedure p1 as
begin
var1:='aaa';
var2:=55;
raise info '%', var1;
raise info '%', var2;
end;
end pk_1087209;
/
```

4、调用包体。

```
call pk_1087209.p1();
```

返回结果为：

```
INFO:  aaa
INFO:  55
 p1
----

(1 row)
```

