### Q’转义字符语法

**功能描述**

Vastbase G100实现Oracle中的Q’转义字符，该转义字符开启Oracle中的alternative quoting（引号替换）机制。

**语法格式**

```
Q|q 'quote_delimiter c  quote_delimiter'
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> 开启alternative quoting（引号替换）机制后，内部c的所有字符，包括单引号（'）、双引号（"）等均被作为字符处理。

**参数说明**

- Q or q ：表示将使用引号替代机制。这种机制允许为文本字符串使用广泛的分隔符。

- 最外面的''是两个单引号，分别位于左quote_delimiter和右quote_delimiter的前面和后面。

- c ：是用户字符集的任何成员。可以在由 c 字符组成的文本中包含引号（"）。还可以包括quote_delimiter，只要它不是紧跟在单引号之后。

- quote_delimiter：是除空格、制表符和回车之外的任何单字节或多字节字符。quote_delimiter可以是单引号。但是，如果quote_delimiter出现在文本本身中，请确保它后面不紧跟单引号。
  如果左quote_delimiter是 [, {, <, 或者 (，则右quote_delimiter必须是相应的], }, >, 或者 )。在所有其他情况下，左和右quote_delimiter必须是相同的字符。

**注意事项**

无。

**示例**

示例一：

```
select q'asdffwwda';
```

返回结果如下：

```
?column? 
----------
sdffwwd
(1 row)
```

示例二：

```
select q'[sfeew[]we]';
```

返回结果为：

```
?column?  
-----------
sfeew[]we
(1 row)
```

示例三：

1、创建测试表并插入数据。

```
create table qq_test(name char(20));
insert into qq_test values(q'd er'd 'wwd');
```

2、查询表中的数据。

```
select * from qq_test;
```

返回结果为：

```
      name         
----------------------
er'd 'ww           
(1 row)
```
