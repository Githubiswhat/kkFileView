##### UNION,CASE和相关构造

SQL UNION构造必须把那些可能不太相似的类型匹配起来成为一个结果集。解析算法分别应用于联合查询的每个输出字段。INTERSECT和EXCEPT构造对不相同的类型使用和UNION相同的算法进行解析。CASE、ARRAY、VALUES、GREATEST和LEAST构造也使用同样的算法匹配它的部件表达式并且选择一个结果数据类型。

**UNION，CASE和相关构造解析**

- 如果所有输入都是相同的类型，并且不是unknown类型，那么解析成这种类型。
- 如果所有输入都是unknown类型则解析成text类型（字符串类型范畴的首选类型）。否则，忽略unknown输入。
- 如果输入不属于同一个类型范畴（unknown类型除外），失败。
- 如果输入类型是同一个类型范畴，则选择该类型范畴的首选类型（例外：union操作会选择第一个分支的类型作为所选类型）。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>    
>
> 系统表pg_type中typcategory表示数据类型范畴， typispreferred表示是否是typcategory分类中的首选类型。

- 把所有输入转换为所选的类型（对于字符串保持原有长度）。如果从给定的输入到所选的类型没有隐式转换则失败。
- 若输入中含json、txid_snapshot、sys_refcursor或几何类型，则不能进行union。

**对于case和coalesce，在TD兼容模式下的处理**

- 如果所有输入都是相同的类型，并且不是unknown类型，那么解析成这种类型。
- 如果所有输入都是unknown类型则解析成text类型。
- 如果输入字符串（包括unknown，unknown当text来处理）和数字类型，那么解析成字符串类型，如果是其他不同的类型范畴，则报错。
- 如果输入类型是同一个类型范畴，则选择该类型的优先级较高的类型。
- 把所有输入转换为所选的类型。如果从给定的输入到所选的类型没有隐式转换则失败。

**对于case，在ORA兼容模式下的处理**

decode(expr, search1, result1, search2, result2, …, defresult)，也即 case expr when search1 then result1 when search2 then result2 else defresult end; 在ORA兼容模式下的处理，将整个表达式最终的返回值类型定为result1的数据类型，或者与result1同类型范畴的更高精度的数据类型。（例如，numeric与int同属数值类型范畴，但numeric比int精度要高，具有更高优先级）

- 将result1的数据类型置为最终的返回值类型preferType，其所属类型范畴为preferCategory。
- 依次考虑result2、result3直至defresult的数据类型。如果其类型范畴也是preferCategory，即与result1具有相同的类型范畴，则判断其精度（优先级）是否高于preferType，如果高于，则将preferType更新为更高精度的数据类型；如果其类型范畴不是preferCategory，则判断其数据类型是否可以隐式转换为preferType，不可以则报错。
- 将最终preferType记录的数据类型作为整个表达式最终的返回值类型；表达式的结果向此类型进行隐式转换。

<div align="left"><img src="image/image2.png" style="zoom:20%")</div>    

- 为了兼容一种特殊情况，即表示了超大数字的字符类型向数值类型转换的情况，例如select decode(1, 2, 2, “53465465676465454657567678676”)，大数超过了bigint、double等的表示范围。所以，当result1的类型范畴为数值类型时，将返回值的类型直接置为numeric，以兼容此种特殊情况。

- 数值类型的优先级排序：numeric>float8>float4>int8>int4>int2>int1
  字符类型的优先级排序：text>varchar=nvarchar2>bpchar>char
  日期类型的优先级排序：timestamptz>timestamp>smalldatetime>date>abstime>timetz>time
  日期跨度类型的优先级排序：interval>tinterval>reltime

**示例**

- Union中的待定类型解析。这里，unknown类型文本'b'将被解析成text类型。


```
SELECT text 'a' AS "text" UNION SELECT 'b'; 
```

结果显示如下：

```
text 
------ 
 a 
 b 
(2 rows)
```

- 简单Union中的类型解析。文本1.2的类型为numeric，而且integer类型的1可以隐含地转换为numeric，因此使用这个类型。


```
SELECT 1.2 AS "numeric" UNION SELECT 1; 
```

结果显示如下：

```
 numeric 
--------- 
    1 
   1.2 
(2 rows)
```

- 转置Union中的类型解析。这里，因为类型real不能被隐含转换成integer，但是integer可以隐含转换成real，那么联合的结果类型将是real。


```
 SELECT 1 AS "real" UNION SELECT CAST('2.2' AS REAL); 
```

 结果显示如下：

```
real 
------ 
  1 
 2.2 
(2 rows)
```

- TD模式下，coalesce参数输入int和varchar类型，那么解析成varchar类型。 


1、在TD模式下，创建TD兼容模式的数据库td_1并切换数据库为td_1。 

```
CREATE DATABASE td_1 dbcompatibility = 'C';	
\c td_1 
```

2、创建表t1。 

```
CREATE TABLE t1(a int, b varchar(10)); 
```

3、查看coalesce参数输入int和varchar类型的查询语句的执行计划。 

```
EXPLAIN VERBOSE select coalesce(a, b) from t1; 
```

当结果显示如下信息，则表示验证完成。

```
                   QUERY PLAN 
--------------------------------------------------------------------------------------- 
 Seq Scan on public.t1 (cost=0.00..24.18 rows=1134 width=42) 
  Output: (COALESCE((a)::varchar, b)
(2 rows) 
```

4、删除测试库

```
\c vastbase
DROP DATABASE td_1;
```

