#### 创建存储过程和函数时支持DEFINER

**功能描述**

定义存储过程、函数时支持语法DEFINER = user指定属主。

**前提条件**

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

**语法格式**

- 存储过程

```
CREATE
[DEFINER = user]
PROCEDURE sp_name() 
routine_body
```

- 函数

```
CREATE
[DEFINER = user]
FUNCTION sp_name()
RETURNS type
routine_body
```

**参数说明**

- DEFINER = user：当创建存储过程、函数时指定SECURITY DEFINER、AUTHID DEFINER、AUTHID DEFINER或者SECURITY DEFINER时声明该函数将以DEFINER指定的用户的权限执行。

- user后面可以跟@'主机名'或者@'ip'，但并不校验主机名或者ip的合法性。

- sp_name() ：自定义的存储过程或者函数名称。

- routine_body：过程体代码块。


**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 用户如果是有“系统管理员”角色，可以定义存储过程、函数的DEFINER 属性为任意用户。
- 非“系统管理员”创建存储过程、函数的时候，DEFINER属性只能定义为自己。
- 创建存储过程、函数definer指定的用户不存在时，则会报错。
- 存储过程的名称sp_name()与过程体的代码之间必须使用“as”或“is”。
- 存储过程的代码主块中，必须包裹在begin...end；的代码块中。
- SQL SECURITY 属性为DEFINER时，数据库中必须存在DEFINER指定的定义者用户，并且该定义者用户拥有对应的操作权限及引用的相关对象的权限，执行者以该用户的权限去执行存储过程、函数。
- CREATE OR REPLACE DEFINER = use创建存储过程、函数过程时，则会报错。
- 当使用CREATE DEFINER = user FUNCTION/PROCEDURE指定了所属用户时，后来又使用CREATE OR REPLACE进行替换函数存储过程、函数时，仍保留创建函数存储过程、函数时DEFINER指定的用户作为其属主。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

2、创建普通用户definer1。

```
CREATE USER definer1 IDENTIFIED BY 'bigdata@123';
```

3、普通用户definer1创建函数指定定义者，创建成功，并执行函数成功。

```
CREATE DEFINER=definer1  function func() returns void
AS  $$
BEGIN
  raise info 'definer ';
END;
$$ LANGUAGE plpgsql  SECURITY definer;
```

6、给普通用户definer1赋予该函数的执行权限，普通用户definer1下执行该函数，执行成功。

```
grant EXECUTE on procedure func() to definer1;
\c - definer1
```

```
select func();
```

结果显示如下：

```
INFO:  definer 
CONTEXT:  referenced column: func

 func 
-------------
(1 row)
```

