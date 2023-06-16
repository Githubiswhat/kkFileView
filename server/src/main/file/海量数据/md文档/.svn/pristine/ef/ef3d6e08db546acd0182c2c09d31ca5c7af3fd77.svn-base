# DUMP函数

## 功能描述

DUMP函数用于查看表达式在数据库内的数据类型代码、长度（以字节为单位）和表达式的内部表达形式。返回值为一个字符串，返回字符串的格式为：

```sql
Typ=TYPE Len=LEN [CharacterSet=CHARACTERSETJ: xx,yy,zz
```

每个代码对应的数据类型参考如下：

| 数据类型代码                   | 对应的数据类型                                               |
| ------------------------------ | ------------------------------------------------------------ |
| 1（VARCHAR2）                  | text，varchar,nvarchar2                                      |
| 2（number）                    | int1，int2，int4，int8，numeric，float4，float8              |
| 12（DATE）                     | oradate，date                                                |
| 23（RAW）                      | raw                                                          |
| 69（ROWID）                    | rowid                                                        |
| 96（CHAR）                     | char                                                         |
| 180（TIMESTAMP）               | timestamp                                                    |
| 181（TIMESTAMP WITH TIMEZONE） | timestamptz                                                  |
| 189（INTERVAL YEAR TO MONTH）  | Interval，单位：年（YEAR）、月（MONTH）                      |
| 190（INTERVAL DAY TO SECOND）  | Interval，单位：天（DAY）、小时（HOUR）、分钟（MINUTE）和秒（SECOND） |

## 语法格式

```sql
DUMP(expr[, return_fmt [, start_position [, length ] ] ])
```


## 参数说明

- **expr**

  需要被查询的表达式。如果expr为NULL，则函数返回值也为NULL。

- **return_fmt**

  可选项，用于决定返回结果的格式。

  取值范围：8，10，16，17

  - 8：用八进制输出内部表达形式。
  - 10：用十进制输出内部表达形式。
  - 16：用十六进制输出内部表达形式。
  - 17：返回每个字节的字符形式，当且仅当这个字节可以被解释为编译器字符（如ASCII）中可打印的字符。

  默认值：10

  > <div align="left"><img src="image/img3.png" style="zoom:75%")</div>  
  >
  > - 默认情况下，返回值不包含当前所用字符集信息，为了获得expr的字符集信息，可以在合法的return_fmt取值上加上1000以作标识。例如，return_fmt为1008，意味着以八进制形式返回结果。
  > - return_fmt大于 1000显示字符集的功能仅在表达式为字符相关类型，即type=1或96时生效，其他情况均不显示字符集信息。

- **start_position,length**

  可选项，这两个参数共同决定了返回哪一部分的内部表示。

  - start_position允许任何数字类型的输入以及内容仅包含纯数字的字符串，代表从第i个字节开始返回（i=1代表从第一个字节开始返回，下标从1开始），负数代表从后往前数第i个字节。

  - length参数的输入规则与start_position相同，唯一区别是负数被处理为绝对值。
  
  start_position和length为0相当于默认状态，输出所有字节的内部表达。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- DUMP函数暂不支持LONG相关类型以及BLOB、CLOB类型。
- 表达式的字节数不能超过3000个（即Len<3000）。

## 示例

1、创建并切换至兼容模式为Oracle的数据库。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、调用DUMP函数。

```sql
SELECT DUMP('abc', 1016) FROM DUAL;
```

返回结果为：

```sql
                  dump
-----------------------------------------
 Typ=1 Len=3 CharacterSet=UTF8: 61 62 63
(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> 函数返回结果表示，表达式'abc'在数据库内部存储的内容如下：
>
> - 数据类型代码：1
> - 长度：3字节
> - 字符集：UTF8
> - 内部表达形式：61 62 63