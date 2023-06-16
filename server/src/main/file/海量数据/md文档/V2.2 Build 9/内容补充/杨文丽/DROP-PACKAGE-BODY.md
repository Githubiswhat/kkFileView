# DROP PACKAGE BODY

## 功能描述

删除已存在的PACKAGE BODY。

## 注意事项

删除PACKAGE BODY后，PACKAGE内的存储过程及函数会同时失效。

## 语法格式

```
DROP PACKAGE BODY [ IF EXISTS ] package_name;
```

## 参数说明

**package_name**

已存在的包名。

## 示例

1、创建一个CREATE PACKAGE SPECIFICATION。

```
CREATE OR REPLACE PACKAGE emp_bonus IS
var1 int:=1;				--公有变量
var2 int:=2;
PROCEDURE testpro1(var3 int);--公有存储过程，可以被外部调用
END ;
/
```

2、创建一个package body。

```
create or replace package body emp_bonus is
var3 int:=3;
var4 int:=4;
procedure testpro1(var3 int)
is
begin
create table if not exists test1(col1 int);
insert into test1 values(var1);
insert into test1 values(var4);
end;
begin   #实例化开始
var4:=9;
testpro1(var4);
end;
end;
/
```

3、删除package body。

```
drop package body emp_bonus; 
```

## 相关链接

[drop package](drop-package.md)