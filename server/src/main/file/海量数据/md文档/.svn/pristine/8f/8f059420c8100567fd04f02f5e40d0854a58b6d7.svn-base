# security label

## 功能描述

定义或更改应用于对象的安全标签。

## 注意事项

无

## 语法格式

```
SECURITY LABEL [ FOR vastbase ] ON
{
  TABLE object_name |
  FUNCTION function_name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ] |
  MATERIALIZED VIEW object_name |
  PROCEDURE procedure_name [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ] |
  ROLE object_name |
  SEQUENCE object_name |
  TRIGGER object_name OF table_name |
  VIEW object_name |
  COLUMN table_name.column.name
} IS 'label' | NULL;
```

## 参数说明

- **object_name**

  对象名称。

- **function_name**

  函数名称。

- **argmode**

  函数参数的模式。

  取值范围：IN，OUT，INOUT或VARIADIC（用于声明数组类型的参数），缺省值是IN。只有OUT模式的参数后面能跟VARIADIC。并且 OUT和INOUT模式的参数不能用在RETURNS TABLE的函数定义中。

- **argname**

  函数参数的名称。

  取值范围：字符串，要符合标识符命令规范。

- **argtype**

  函数参数的类型。

- **procedure_name**

  存储过程名称。

## 示例

更改一个表的安全标签。

```
SECURITY LABEL FOR vastbase ON TABLE mytable IS 'system_u:object_r:sepgsql_table_t:s0';
```

## 相关链接

无