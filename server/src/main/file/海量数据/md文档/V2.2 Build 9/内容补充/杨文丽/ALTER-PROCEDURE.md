# ALTER PROCEDURE

## 功能描述

修改一个自定义存储过程的属性。

## 注意事项

只有存储过程的所有者或者被授予了存储过程ALTER权限的用户才能执行ALTER PROCEDURE命令，系统管理员默认拥有该权限。针对所要修改属性的不同，还有以下权限约束：

- 如果存储过程中涉及对临时表相关的操作，则无法使用ALTER PROCEDURE。
- 修改存储过程的所有者或修改存储过程的模式，当前用户必须是该存储过程的所有者或者系统管理员，且该用户是新所有者角色的成员。
- 只有系统管理员和初始化用户可以将procedure的schema修改成public。

## 语法格式

- 修改自定义存储过程的附加参数。

```
ALTER PROCEDURE name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ]
    action [ ... ] [ RESTRICT ];
```

其中自定义存储过程的附加参数action语法为：

```
    [ EXTERNAL ] SECURITY INVOKER | [ EXTERNAL ] SECURITY DEFINER
    SET configuration_parameter { TO | = } { value | DEFAULT }
    SET configuration_parameter FROM CURRENT
    RESET configuration_parameter
    RESET ALL
```

- 修改自定义存储过程的名称。

```
ALTER PROCEDURE name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ]
    RENAME TO new_name;
```

- 修改自定义存储过程的所属者。

```
ALTER PROCEDURE name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ]
    OWNER TO { new_owner | CURRENT_ROLE | CURRENT_USER | SESSION_USER };
```

- 修改自定义存储过程的模式。

```
ALTER PROCEDURE name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ]
    SET SCHEMA new_schema;
```

- 修改自定义存储过程的插件。

```
ALTER PROCEDURE name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ]
    [ NO ] DEPENDS ON EXTENSION extension_name;
```

## 参数说明

- **argmode**

  标识该参数是输入、输出参数。

  取值范围：IN/OUT/INOUT/VARIADIC。

- **argname**

  参数名称。

  取值范围：字符串，符合标识符命名规范。

- **argtype**

  存储过程参数的类型。

- **new_name**

  存储过程的新名称。要修改存储过程的所属模式，必须拥有新模式的CREATE权限。

  取值范围：字符串，符合标识符命名规范。

- **new_owner**

  存储过程的新所有者。要修改存储过程的所有者，新所有者必须拥有该存储过程所属模式的CREATE权限。

  取值范围：已存在的用户角色。

- **new_schema**

  存储过程的新模式。

  取值范围：已存在的模式。

- **extension_name**

  已安装扩展的名称。

## 示例<a name ='example'>

1、定义存储过程为SQL查询。

```
CREATE PROCEDURE func_add_sql(integer, integer) RETURNS integer
    AS 'select $1 + $2;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;
```

2、利用参数名用 PL/pgSQL 自增一个整数。

```
CREATE OR REPLACE PROCEDURE func_increment_plsql(i integer) RETURNS integer AS $$
        BEGIN
                RETURN i + 1;
        END;
$$ LANGUAGE plpgsql;
```

3、返回RECORD类型

```
CREATE OR REPLACE PROCEDURE func_increment_sql(i int, out result_1 bigint, out result_2 bigint)
returns SETOF RECORD
as $$
begin
    result_1 = i + 1;
    result_2 = i * 10;
return next;
end;
$$language plpgsql;
```

4、返回一个包含多个输出参数的记录。

```
CREATE PROCEDURE func_dup_sql(in int, out f1 int, out f2 text)
    AS $$ SELECT $1, CAST($1 AS text) || ' is text' $$
    LANGUAGE SQL;
    
SELECT * FROM func_dup_sql(42);
```

返回结果为：

```
 f1 |     f2
----+------------
 42 | 42 is text
(1 row)
```

5、计算两个整数的和，并返回结果。如果输入为null，则返回null。

```
CREATE PROCEDURE func_add_sql2(num1 integer, num2 integer) RETURN integer
AS
BEGIN 
RETURN num1 + num2;
END;
/
```

6、修改函数func_add_sql2的执行规则为IMMUTABLE，即参数不变时返回相同结果。

```
ALTER PROCEDURE func_add_sql2(INTEGER, INTEGER) IMMUTABLE;
```

7、将函数func_add_sql2的名称修改为add_two_number。

```
 ALTER PROCEDURE func_add_sql2(INTEGER, INTEGER) RENAME TO add_two_number
```

8、将函数add_two_number的属者改为vastbase。

```
ALTER PROCEDURE add_two_number(INTEGER, INTEGER) OWNER TO vastbase;
```

9、删除函数。

```
DROP PROCEDURE add_two_number;
DROP PROCEDURE func_increment_sql;
DROP PROCEDURE func_dup_sql;
DROP PROCEDURE func_increment_plsql;
DROP PROCEDUREfunc_add_sql;
```

## 相关链接

[CREATE PROCEDURE](CREATE-PROCEDURE.md)，[DROP PROCEDURE](DROP-PROCEDURE.md)

