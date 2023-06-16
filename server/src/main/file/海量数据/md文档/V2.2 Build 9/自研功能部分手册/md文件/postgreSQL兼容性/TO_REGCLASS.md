#### TO_REGCLASS函数

**功能描述**

to_regclass函数，用以判断表对象是否存在。

**语法格式**

```
to_regclass ( text )
```

**参数说明**

text：支持所有的文本字符、字符数组（字符串）类型，函数入参必须保障需要支持的数据类型有：

| 名称                              | 描述                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| CHAR(n)、CHARACTER(n)、 NCHAR(n)  | 定长字符串，不足补空格。n是指字节长度，如不带精度n，默认精度为1。 |
| VARCHAR(n) 、CHARACTER VARYING(n) | 变长字符串。n是指字节长度。                                  |
| VARCHAR2(n)                       | 变长字符串。是VARCHAR(n)类型的别名。n是指字节长度。          |
| NVARCHAR2(n)                      | 变长字符串。n是指字节长度。                                  |
| CLOB                              | 文本大对象。是TEXT类型的别名。                               |
| TEXT                              | 变长字符串。                                                 |
| VARCHAR                           | 变长字符串。                                                 |
| CHAR                              | 单字节内部类型                                               |
| NAME                              | 用于对象名的内部类型。                                       |

注：其他的数据类型，只要能完成向text类型的隐式转换，在Vastbase中就是合法的。

**注意事项**

- 函数输入：类型是text，格式是[[catalogname.]schemaname.]relname；根据search_path顺序查找，并且查找的是relation。

- 函数返回：类型是regclass。

- 输入输出遵循现有的隐式类型转化规则，隐式类型转化可以在pg_cast系统表中查看。

- 具备该函数的访问权限，例如to_regclass函数如果位于pg_catalog模式下，用户需要具备以下权限：

  - pg_catalog模式的usage权限。

  - to_regclass函数的excute权限。


**示例**

1、创建测试表。

```
create table table_exit(id int);
```

2、验证函数。

```
select  to_regclass('table_exit');
```

结果返回如下：

```
  to_regclass
-------------
  table_exit
(1  row)
```

