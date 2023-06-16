# ltree插件

## 功能描述

Itree可以实现一种数据类型用于表示存储在一个层次树状结构中的数据的标签，并提供了在标签树中搜索的扩展功能。

### 数据类型

ltree存储一个标签路径，标签长度必须少于256字节，标签路径长度小于65KB.

一个标签路径是由点号分隔的零个或更多个标签的序列，例如：

```sql
CREATE TABLE test (path ltree); 
INSERT INTO test VALUES ('Top');
INSERT INTO test VALUES ('Top.Science');
INSERT INTO test VALUES ('Top.Science.Astronomy');
INSERT INTO test VALUES ('Top.Science.Astronomy.Astrophysics');
INSERT INTO test VALUES ('Top.Science.Astronomy.Cosmology');
```

lquery表示一个用于匹配ltree值的类正则表达式。

一个简单词可以匹配一个路径中的标签，星号可以匹配零个或多个标签，例如：

| 模式     | 描述                                |
| -------- | ----------------------------------- |
| foo      | 正好匹配标签路径foo                 |
| \*.foo.* | 匹配任何包含标签foo的路径           |
| *.foo    | 匹配任何最后一个标签是foo的标签路径 |
| *{n}     | 匹配正好n个标签                     |
| *{n, }   | 匹配至少n个标签                     |
| *{n,m}   | 匹配至少n个标签但是最多m个标签      |
| *{,m}    | 匹配至多m个标签                     |
| foo{n,m} | 匹配至少n个但是最多m个foo标签       |
| foo{,}   | 匹配任意数量的foo标签               |

在lquery中，可以在一个非星号标签的末尾用如下的修饰符：

| 修饰符 | 描述                     |
| ------ | ------------------------ |
| @      | 不区分大小写匹配         |
| *      | 匹配带此前缀的任何标签   |
| %      | 匹配开头以下划线分隔的词 |

除此之外，还可以使用 |(OR)、! (NOT)修饰符修饰。

Itxtquery表示一种用于匹配Itree值的类全文搜索的模式。

ltxtquery匹配词时不考虑它们在标签路径的位置，并且Itxtquery允许符号之间存在空白而ltree和lquery不允许。

除此之外ltextquery也可以在末尾带有修饰符@、 *、%，修饰符的意义和lquery中有相同的含义。词还可以使用&(And)、| (OR)、! (NOT)以及圆括号组合。例如：

```sql
CREATE TABLE test (path ltree);
INSERT INTO test VALUES ('Top.Science');
SELECT path~'Science'::lquery from test;	--返回f
SELECT path@'Science'::ltxtquery from test; --返回t
```

### 操作符

Itree 类型除支持普通的比较操作符=、<>、<、>、<=、>=外，还支持下表的特殊操作符：
<table>
 <tr>
<th>操作符</th>
<th>返回值</th>
<th>描述</th>
</tr>
<tr>
<td>ltree@>ltree</td>
<td>boolean</td>
<td>判断左参数是不是右参数的祖先</td>
</tr>
<tr>
<td>ltree<@ ltree</td>
<td>boolean</td>
<td>判断左参数是不是右参数的后代</td>
</tr>
<tr>
<td>ltree~lquery</td>
<td>boolean</td>
<td rowspan=2>判断ltree是否匹配lquery</td>
</tr>
<tr>
<td>lquery~ ltree</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree? lquery[]</td>
<td>boolean</td>
<td rowspan=2>判断数组中是否存在与ltree匹配的lquery</td>
</tr>
<tr>
<td>lquery[]? ltree</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree @ ltxtquery</td>
<td>boolean</td>
<td rowspan=2>判断ltree是否匹配ltxtquery</td>
</tr>
<tr>
<td>ltxtquery @ ltree</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree || ltree</td>
<td>ltree</td>
<td>串接ltree 路径</td>
</tr>
<tr>
<td>ltree || text</td>
<td>ltree</td>
<td rowspan=2>将text文本转换成ltree并且串接</td>
</tr>
<tr>
<td>text || ltree</td>
<td>ltree</td>
</tr>
<tr>
<td>ltree[] @>ltree</td>
<td>boolean</td>
<td >判断数组中是否包含右参数ltree的一个祖先</td>
</tr>
<tr>
<td>ltree <@ ltree[]</td>
<td>boolean</td>
<td >判断数组中是否包含左参数ltree的一个祖先</td>
</tr>
<tr>
<td>ltree[]<@ ltree</td>
<td>boolean</td>
<td >判断数组是否包含右参数ltree的一个后代</td>
</tr>
<tr>
<td>ltree@> ltree[]</td>
<td>boolean</td>
<td >判断数组是否包含左参数ltree的一个后代</td>
</tr>
<tr>
<td>ltree[]~ lquery</td>
<td>boolean</td>
<td rowspan=2>判断数组是否包含匹配lquery的路径</td>
</tr>
<tr>
<td>lquery~ltree[]</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree[] ? lquery[]</td>
<td>boolean</td>
<td rowspan=2>判断ltree数组是否包含匹配任意lquery的路径</td>
</tr>
<tr>
<td>lquery[]? ltree[]</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree[] @ ltxtquery</td>
<td>boolean</td>
<td rowspan=2>判断数组是否包含匹配ltxtquery的路径</td>
</tr>
<tr>
<td>ltxtquery @ltree[]</td>
<td>boolean</td>
</tr>
<tr>
<td>ltree[]?@ >ltree</td>
<td>ltree</td>
<td >返回数组中第一个是右参数祖先的项，不存在返回NULL</td>
</tr>
<tr>
<td>ltree[]?<@ ltree</td>
<td>ltree</td>
<td >返回数组中第一个是左参数祖先的项，不存在返回NULL</td>
</tr>
<tr>
<td>ltree[]?~lquery</td>
<td>ltree</td>
<td rowspan=2>返回数组中第一个匹配lquery的项，不存在返回NULL</td>
</tr>
<tr>
<td>ltree[]?@ ltxtquery</td>
<td>ltree</td>
</tr>
</table>



### 函数

| 函数                                | 描述                                                         |
| ----------------------------------- | ------------------------------------------------------------ |
| subltree(ltree,int start,int end)   | 返回从位置start到位置end-1（从0开始计）的子路径。            |
| subpath(ltree,int offset,int len)   | 返回从位置offset开始长度为len的子路径。<br>如果offset为负，则子路径从距离路径终点的远端开始。<br>如果len为负，则从路径的尾部开始丢弃len个标签。 |
| subpath(ltree,int offset)           | 返回从位置offset开始一直延伸到路径末尾的子路径。<br/>如果offset为负，则子路径从距离路径终点的远端开始。 |
| nlevel(ltree)                       | 返回路径中的标签数量。                                       |
| index(a ltree ,b ltree )            | a中第一次出现b的位置，没有找到则返回-1，从0开始。            |
| index(a ltree ,b ltree,offset int ) | a中第一次出现b的位置，搜索从offset开始。offset为负，则从尾部-offset个标签开始。 |
| text2ltree(text)                    | 将text转换成ltree。                                          |
| ltree2text(text)                    | 将ltree转换成text。                                          |
| lca(ltree,ltree,...)                | 最长公共路径的前缀，最多支持8个参数。                        |
| lca(ltree[])                        | 最长公共路径的前缀。                                         |

### 索引

- ltree数据类型支持B-树索引：<、<=、=、>=、>
- ltree数据类型支持GIST索引：<、<=、=、>=、>、@>、<@、@、~、?
- ltree[]数据类型支持GIST索引：ltree[]<@ltree、ltree@>ltree[]、@、~、?

### 注意事项

在PG数据库中ltree的gist索引在使用时可以加选项带参数，比如：

```sql
create index tstidx on treetest using gist (t gist_ltree _ops(siglen=2025))
```

Vastbase不支持该功能，在Vastbase数据库中使用方法如下：

```sql
create index tstidx on ltreetest using gist (t gist_ltree_ops);
```


## 使用方法

创建Itree插件。

```sql
create extension ltree;
```

删除Itree插件。

```sql
drop extension ltree;
```

## 示例

**示例1：**ltree数据类型的列支持增删改查。

1、创建Itree插件。

```sql
create extension ltree;
```

2、创建包含数据类型ltree的表。

```sql
create table tb_1101588(id int, path ltree);
```

3、向表中插入数据。

```sql
insert into tb_1101588 values(1, 'GangTai.NanGeShou.ZhouJieLun.QiLiXiang');
insert into tb_1101588 values(2, 'GangTai.NanGeShou.ZhouJieLun.QingTian');
insert into tb_1101588 values(3, 'NeiDi.NanGeShou');
insert into tb_1101588 values(4, 'NeiDi.NvGeShou.ZhangLiangYing');
insert into tb_1101588 values(5, 'NeiDi');
```
4、查询表中得数据。

```sql
select * from tb_1101588;
```

返回结果为：

```sql
 id |                  path
----+----------------------------------------
  1 | GangTai.NanGeShou.ZhouJieLun.QiLiXiang
  2 | GangTai.NanGeShou.ZhouJieLun.QingTian
  3 | NeiDi.NanGeShou
  4 | NeiDi.NvGeShou.ZhangLiangYing
  5 | NeiDi
(5 rows)
```

5、更新表中数据。

```sql
update tb_1101588 set path = 'GangTai.NanGeShou.LinJunJie' where path ~ '*.QingTian';
```

查询数据：

```sql
select * from tb_1101588;
```

返回结果为：

```sql
 id |                  path
----+----------------------------------------
  1 | GangTai.NanGeShou.ZhouJieLun.QiLiXiang
  3 | NeiDi.NanGeShou
  4 | NeiDi.NvGeShou.ZhangLiangYing
  5 | NeiDi
  2 | GangTai.NanGeShou.LinJunJie
(5 rows)
```

6、删除表中数据。

```sql
delete from tb_1101588 where path ~ '*.QiLiXiang';
```

5、查询表中数据。

```sql
select * from tb_1101588;
```

返回结果为：

```sql
 id |             path
----+-------------------------------
  3 | NeiDi.NanGeShou
  4 | NeiDi.NvGeShou.ZhangLiangYing
  5 | NeiDi
  2 | GangTai.NanGeShou.LinJunJie
(4 rows)
```

6、删除表。

```sql
drop table tb_1101588;
```

**示例2：**数据类型lquery匹配ltree值的类正则表达式（foo正好匹配标签路径foo）。

1、创建表测试表。

```sql
create table tb_1101393(id int, path ltree);
```

2、向表中插入数据。

```sql
insert into tb_1101393 values(1, 'GangTai.NanGeShou');
insert into tb_1101393 values(2, 'GangTai.NvGeShou');
```

3、匹配标签路径。

```sql
select path from tb_1101393 where path ~ 'GangTai.NvGeShou';
```

返回结果为：

```sql
       path
------------------
 GangTai.NvGeShou
(1 row)
```

4、删除表。

```sql
drop table tb_1101393;
```

**示例3：**数据类型lquery匹配ltree值的类正则表达式（*.foo.\*匹配任何包含标签foo的标签路径）。

1、创建测试表。

```sql
create table tb_1101394(id int, path ltree);
```

2、向表中插入数据。

```sql
insert into tb_1101394 values(1, 'GangTai.NanGeShou.ZhouJieLun.QiLiXiang');
insert into tb_1101394 values(2, 'GangTai.NvGeShou.CaiYiLin.RiBuLuo.Test');
insert into tb_1101394 values(3, 'GangTai.NvGeShou.CaiYiLin.GuaiMeiDe');
```

3、匹配标签路径。

```sql
select path from tb_1101394 where path ~ '*.CaiYiLin.*';
```

返回结果为：

```sql
                  path
----------------------------------------
 GangTai.NvGeShou.CaiYiLin.RiBuLuo.Test
 GangTai.NvGeShou.CaiYiLin.GuaiMeiDe
(2 rows)
```

4、删除表。

```sql
drop table tb_1101394;
```

**示例4：**操作符ltree[] ~ lquery和lquery ~ ltree[]（判断数组是否包含匹配lquery的路径）。

```
select '{"NeiDi", "GangTai"}'::ltree[] ~ 'NeiDi*';
```

返回结果为：

```sql
 ?column?
----------
 t
(1 row)
```

**示例5：**调用函数index(a ltree ,b ltree )返回a中第一次出现b的位置，没有找到则返回-1，从0开始。

1、创建表中数据类型为ltree。

```sql
create table tb_1101506(id int, path ltree);
```

2、向表中插入数据。

```sql
insert into tb_1101506 values(1, 'GangTai.NanGeShou.ZhouJieLun.QiLiXiang');
insert into tb_1101506 values(2, 'GangTai.NanGeShou.ZhouJieLun.QingTian');
insert into tb_1101506 values(3, 'NeiDi.NanGeShou');
insert into tb_1101506 values(4, 'NeiDi.NvGeShou.ZhangLiangYing');
insert into tb_1101506 values(5, 'NeiDi');
```

3、调用index函数。

```sql
select index(path, 'NanGeShou'::ltree) from tb_1101506;
```

返回结果为：

```sql
 index
-------
     1
     1
     1
    -1
    -1
(5 rows)
```

4、删除表。

```sql
drop table tb_1101506;
```

**示例6：**调用函数lca(ltree[])计算数组中的路径的最长公共祖先。

```sql
select lca(array['1.2.3'::ltree, '1.2.3.4']);
```

返回结果为：

```sql
 lca
-----
 1.2
(1 row)
```

**示例7：**ltree上的 B-树索引

1、创建表中数据类型为ltree。

```sql
create table tb_1101523(id int, path ltree);
```

2、向表中插入数据。

```sql
insert into tb_1101523 values(1, 'GangTai.NanGeShou.ZhouJieLun.QiLiXiang');
insert into tb_1101523 values(2, 'GangTai.NanGeShou.ZhouJieLun.QingTian');
insert into tb_1101523 values(3, 'NeiDi.NanGeShou');
insert into tb_1101523 values(4, 'NeiDi.NvGeShou.ZhangLiangYing');
insert into tb_1101523 values(5, 'NeiDi');
```

3、创建索引。

```sql
create index idx_1101523 on tb_1101523(path);
```

4、验证操作符（<、<=、=、>=、>）。

```sql
select path from tb_1101523 where path < 'NeiDi.NvGeShou';
```

返回结果分别为：

```sql
                  path
----------------------------------------
 GangTai.NanGeShou.ZhouJieLun.QiLiXiang
 GangTai.NanGeShou.ZhouJieLun.QingTian
 NeiDi.NanGeShou
 NeiDi
(4 rows)
```

5、验证操作符（<=）。

```sql
select path from tb_1101523 where path <= 'NeiDi.NvGeShou';
```

返回结果为：

```sql
                  path
----------------------------------------
 GangTai.NanGeShou.ZhouJieLun.QiLiXiang
 GangTai.NanGeShou.ZhouJieLun.QingTian
 NeiDi.NanGeShou
 NeiDi
(4 rows)
```

6、验证操作符（=）。

```sql
select path from tb_1101523 where path = 'NeiDi';
```

返回结果为：

```sql
 path
-------
 NeiDi
(1 row)
```

7、验证操作符（>=）。

```sql
select path from tb_1101523 where path >= 'NeiDi.NvGeShou';
```

返回结果为：

```sql
             path
-------------------------------
 NeiDi.NvGeShou.ZhangLiangYing
(1 row)
```

8、验证操作符（>）。

```sql
select path from tb_1101523 where path > 'NeiDi.NvGeShou';
```

返回结果为：

```sql
             path
-------------------------------
 NeiDi.NvGeShou.ZhangLiangYing
(1 row)
```

9、关闭顺序扫描，验证查询时走索引扫描。

```sql
set enable_seqscan =off;
set enable_bitmapscan =off;
explain select path from tb_1101523 where path < 'NeiDi.NvGeShou';
```

返回结果为：

```sql
                                     QUERY PLAN
------------------------------------------------------------------------------------
 Index Only Scan using idx_1101523 on tb_1101523  (cost=0.00..8.27 rows=1 width=32)
   Index Cond: (path < 'NeiDi.NvGeShou'::ltree)
(2 rows)
```

