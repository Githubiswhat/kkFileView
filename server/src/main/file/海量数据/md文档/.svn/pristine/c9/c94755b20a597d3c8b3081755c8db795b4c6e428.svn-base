# 支持UUID类型到字符串类型的隐式转换

## 功能描述

Vastbase G100支持[uuid类型](UUID类型.md)到字符串类型的隐式转换。

## 注意事项

UUID是一个小写十六进制数字的序列，由分字符分成几组，标准的UUID输出格式为：一组8位数字+三组4位数字+一组12位数字，总共32个数字代表128位。在Vastbae G100的不同兼容模式下，UUID的输出格式并不一定包含分字符“-”，详情参考：[uuid类型](UUID类型.md)。

## 示例

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> 以下示例均为默认数据库vastbase（A兼容）下的执行结果。

**示例1：**支持uuid到各字符串类型的隐式转换。

1、创建测试表。

```sql
create table test(
c11 CHAR(100) ,
c12 CHARACTER(50),
c13 NCHAR(40) ,
c14 VARCHAR(50) ,
c15 CHARACTER VARYING(50) ,
c16 VARCHAR2(40) ,
c17 NVARCHAR(50) ,
c18 NVARCHAR2(50) ,
c19 TEXT );
```

2、插入测试数据。

```sql
insert into test values(uuid(),uuid(),uuid(),uuid(),uuid(),uuid(),uuid(),uuid(),uuid());
```

3、查看结果：

```sql
\x --列式展示查询结果
select * from test;
```

返回结果为：

```sql
-[ RECORD 1 ]---------------------------------------------------------------------------------------------
c11 | D81D59355A1A7A28B1D58B76563E7841                        
c12 | 42A84A679C6D93291009213A63FC2F5D
c13 | 13567C0FC4A11571327DBB350E0AB105
c14 | 0B57767E69891E769016246A3D64A508
c15 | 5AD5800221AF6A6471AEE53425818C12
c16 | C26B9D6A65616E5B222EFC1EFC033867
c17 | 6B942921915A4003D3E38735C6B95A09
c18 | 43FA7C2808A7DF520E0BAB654C51F17C
c19 | 0F37DB65758B1607D422DD762C601128
```

**示例2：**UUID与其他函数一起使用，存在隐式转换。

1、使用substr函数返回指定位置之后的字符串内容。

```sql
select substr(uuid(),1);
```

返回结果为：

```sql
              substr
----------------------------------
 733A0A562317040D109A3E2693C12027
(1 row)
```

2、使用contact函数连接字符串文本。

```sql
select concat('1',uuid());
```

返回结果为：

```sql
              concat
-----------------------------------
 105E4B8086C9E8131D6249D69E826FA53
(1 row)
```

