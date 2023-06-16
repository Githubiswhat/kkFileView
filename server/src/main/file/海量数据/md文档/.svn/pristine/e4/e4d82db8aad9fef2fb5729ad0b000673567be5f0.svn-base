# MySQL兼容性-支持set语句赋值

## 功能描述

MySQL兼容模式下，Vastbase G100支持使用set语句对用户变量进行赋值，支持使用@variable声明变量。

## 语法格式

```sql
SET @var_name:=expr[,@var_name:=expr]...
SET @var_name=expr[,@var_name:=expr]...
```

## 参数说明

- **var_name：**自定义变量名。只能由数字、字母、下划线（_）、点（.）、$组成。如果使用单引号、双引号等引用时，则可以使用其它字符（如'var_name'、"var_name"）。
- **expr：**支持可直接或间接转为整型、浮点型、字符串、位串和NULL的表达式。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 仅当GUC参数enable_set_variable_b_format=on时才能使用此功能。

  - 修改全局参数的命令为：（修改此参数后需要执行`vb_ctl reload`命令重新加载配置文件使其生效。

    ```
    echo "enable_set_variable_b_format=on" >> $PGDATA/postgresql.conf
    ```

  - 修改当前会话参数的语法为：

    ```
    set enable_set_variable_b_format=on;
    ```

- 使用[prepare from](PREPARE FROM.md)为SQL语句命名时，用户自定义变量存储的字符串仅支持select、insert、update、delete、merge语法，且必须是单条语句。

- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。

## 示例

- 示例1：查询使用set赋值变量的详细信息。

```sql
\h set
```

返回结果为：

```sql
Command:     SET
Description: change a run-time parameter
Syntax:
SET [ LOCAL | SESSION ]
    { {config_parameter { { TO | = } { value | DEFAULT } | FROM CURRENT }}};
SET [ SESSION | LOCAL ] TIME ZONE { timezone | LOCAL | DEFAULT };
SET [ SESSION | LOCAL ] NAMES encoding_name;
SET [ SESSION | LOCAL ]
    {CURRENT_SCHEMA { TO | = } { schema | DEFAULT }
    | SCHEMA 'schema'};
SET [ SESSION | LOCAL ] XML OPTION { DOCUMENT | CONTENT };
SET @var_name := expr [, @var_name := expr] ...
SET @var_name = expr [, @var_name = expr] ...
NOTICE: '@var_name' is only avaiable in CENTRALIZED mode and B-format database, and enable_set_variable_b_format = on.
```

- 示例2：使用set将变量赋值为数值类型。

1、创建并切换至兼容模式为B的数据库下。

```sql
create database db_1102053 dbcompatibility 'B';
\c db_1102053
```

2、查看GUC参数enable_set_variable_b_format的状态是否为on。

```sql
show enable_set_variable_b_format; 
```

返回结果为：

```
 enable_set_variable_b_format
------------------------------
 on
(1 row)
```

3、使用set对变量进行赋值。

```sql
set @VAR1_1102053=33::int2;
set @var2_1102053:=1111::int4;
set @var5_1102053 := @var6_1102053 := @$var7_1102053:=12345678::int8;
set @var8_1102053= @var9_1102053:= @var10$_1102053:=0.1234::numeric;
set @'#v3_1102053':=12345::number(5);
set @"^v4_1102053":=0.123::number(3,3);
set @`!!!v11_1102053`:=-999.9::number(4,1);
```

4、查看变量。

```sql
select @VAR1_1102053,@var2_1102053,@var5_1102053,@var6_1102053,@$var7_1102053,@var8_1102053,@var9_1102053,@var10$_1102053,@'#v3_1102053',@"^v4_1102053",@`!!!v11_1102053`; 
```

返回结果为：

```sql
 @var1_1102053 | @var2_1102053 | @var5_1102053 | @var6_1102053 | @$var7_1102053 | @var8_1102053 | @var9_1102053 | @var10$_1102053 | @#v3_1102053 | @^v4_1102053 | @!!!v11_1102053
---------------+---------------+---------------+---------------+----------------+---------------+---------------+--
---------------+--------------+--------------+-----------------
            33 |          1111 |      12345678 |      12345678 |       12345678 |         .1234 |         .1234 |
         .1234 |        12345 |         .123 |          -999.9
(1 row)
```

- **示例3：**使用set语法创建prepare语句。

1、创建并切换至兼容模式为MySQL的数据库下。

```sql
create database db_1102078 dbcompatibility 'B';
\c db_1102078
```

2、查看GUC参数enable_set_variable_b_format的状态是否为on。

```sql
show enable_set_variable_b_format; 
```

返回结果为：

```
 enable_set_variable_b_format
------------------------------
 on
(1 row)
```

3、创建测试表。

```
create table tb1(id int);
```

4、使用set语法将两个自定义变量赋值为字符串，内容为SQL语句。

```
set @sql1:='insert into tb1 values(5)';
set @sql2:='select * from tb1';
```

5、为步骤4中的两条SQL语句命名。

```
PREPARE stmt1 from @sql1;
PREPARE stmt2 from @sql2;
```

6、执行SQL语句stmt1、stmt2。

```
EXECUTE stmt1;
EXECUTE stmt2;
```

返回结果为：

```
INSERT 0 1      --stmt1

 id             --stmt2
----
  5
(1 row)
```

