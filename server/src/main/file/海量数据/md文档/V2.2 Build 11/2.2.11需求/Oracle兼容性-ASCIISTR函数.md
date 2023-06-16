# ASCIISTR函数

## 功能描述

ASCIISTR函数用于将输入的字符串转换为ASCII码串。

函数的返回值对属于ASCII码表的字符按照原样显示，其他字符转换为对应的UTF-16编码，再转换为UTF-16编码对

应的ASCII码，以\xxx的形式显示。如果函数的输入为空，返回结果为NULL。

## 语法格式

```sql
ASCIISTR(str text)
```

## 参数说明

**str**

需要转换为ascii码串的字符。

## 注意事项

无。

## 示例

1、查看数据库编码方式。

```sql
show server_encoding;
```

返回结果为：

```sql
 server_encoding
-----------------
 UTF8
(1 row)
```

2、调用函数输入参数为单个中文字符。

```sql
select  asciistr('国') from dual;
```

返回结果为：

```sql
 asciistr
----------
 \56FD
(1 row)
```

