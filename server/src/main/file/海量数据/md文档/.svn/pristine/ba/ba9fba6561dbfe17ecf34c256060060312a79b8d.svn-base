# Xmltype模块

**术语定义**

Xml一般指可扩展标记语言。可扩展标记语言,标准通用标记语言的子集,简称xml。是一种用于标记电子文件使其具有结构性的标记语言。在电子信息中,标记指计算机所能理解的符号,通过此种标记,计算机之间可以处理包含各种信息的文章。

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 使用xml相关功能,在初始化的时候,需要在数据库安装时候使用编译选项\--with-libxml。

## Xmltype类型

Xmltype 是系统定义的类型，用于存储和管理XML数据，并提供了很多的functions，用来保存、检索和操作xml文档和管理节点。

用户建表时可以指定字段类型为Xmltype或者在函数中定义Xmltype类型的变量，此后用户可以使用Xmltype相关的函数进行xml类型数据对象的初始化或者从xml类型字段内容的提取。

**语法格式**

针对表定义Xmltype字段:

```sql
colname xmltyle;
```

针对函数或者匿名块：

```sql
declare 
data Xmltype;
begin
null;
end;
/
```

### 示例（xmltype类型化的增删改查）

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表并插入数据。

```sql
create table test_xmltype_1087931(i int, d xmltype);
insert into test_xmltype_1087931 values(1,'abc');
insert into test_xmltype_1087931 values(2,'<test>abc</test>');
insert into test_xmltype_1087931 values(3,'abc \153\154\155 \052\251\124');
```

3、查询数据。

```sql
select * from test_xmltype_1087931;
```

返回结果为：

```sql
 i |               d
---+-------------------------------
 1 | abc
 2 | <test>abc</test>
 3 | abc \153\154\155 \052\251\124
(3 rows)
```

4、修改数据。

```sql
update test_xmltype_1087931 set d='abc11' where i=1;
select * from test_xmltype_1087931;
```

返回结果为：

```sql
 i |               d
---+-------------------------------
 2 | <test>abc</test>
 3 | abc \153\154\155 \052\251\124
 1 | abc11
(3 rows)
```

5、修改数据。

```sql
update test_xmltype_1087931 set d=' <foo>abc</foo><bar>123</bar>' where i=3;
select * from test_xmltype_1087931;
```

返回结果为：

```sql
 i |               d
---+-------------------------------
 2 | <test>abc</test>
 1 | abc11
 3 |  <foo>abc</foo><bar>123</bar>
(3 rows)
```

6、删除数据。

```sql
delete from test_xmltype_1087931 where i=1;
select * from test_xmltype_1087931;
```

返回结果为：

```sql
 i |               d
---+-------------------------------
 2 | <test>abc</test>
 3 |  <foo>abc</foo><bar>123</bar>
(2 rows)
```

## 数据初始化

Xmltype类型提供相应的函数对表字段或者xml类型变量进行初始化。

#### Createxml

**功能描述**

用于创建和返回 Xmltype 实例的函数,从字符串创建 Xmltype 实例,返回生成的Xmltype对象。

**语法格式**

```sql
 FUNCTION createXML(
   xmlData IN varchar2)
RETURN Xmltype
```

#### 使用createxml和xmltype函数初始化xml数据

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```sql
drop table if exists test_xmltype_1087938;
create table test_xmltype_1087938(
ID int,
name varchar2(256),
data xmltype
);
```

3、使用正确格式的xml文档。

```sql
Insert INTO test_xmltype_1087938
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind2=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;

Insert INTO test_xmltype_1087938
VALUES (2,'test xml doc',xmltype('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
</record>
</collection>')) ;
```

4、查看表数据。

```sql
select *from test_xmltype_1087938;
```

返回结果为：

```sql
 id |     name     |                   data
----+--------------+-------------------------------------------
  1 | test xml doc | <collection xmlns="">                    +
    |              | <record>                                 +
    |              | <leader>-----nam0-22-----^^^450-</leader>+
    |              | <datafield tag="200" ind1="1" ind2=" ">  +
    |              | <subfield code="a">抗震救灾</subfield>   +
    |              | <subfield code="f">奥运会</subfield>     +
    |              | </datafield>                             +
    |              | <datafield tag="209" ind1=" " ind2=" ">  +
    |              | <subfield code="a">经济学</subfield>     +
    |              | <subfield code="b">计算机</subfield>     +
    |              | <subfield code="c">10001</subfield>      +
    |              | <subfield code="d">2005-07-09</subfield> +
    |              | </datafield>                             +
    |              | <datafield tag="610" ind1="0" ind2=" ">  +
    |              | <subfield code="a">计算机</subfield>     +
    |              | <subfield code="a">笔记本</subfield>     +
    |              | </datafield>                             +
    |              | </record>                                +
    |              | </collection
  2 | test xml doc | <collection xmlns="">                    +
    |              | <record>                                 +
    |              | <leader>-----nam0-22-----^^^450-</leader>+
    |              | <datafield tag="209" ind1=" " ind2=" ">  +
    |              | <subfield code="a">经济学</subfield>     +
    |              | <subfield code="b">计算机</subfield>     +
    |              | <subfield code="c">10001</subfield>      +
    |              | <subfield code="d">2005-07-09</subfield> +
    |              | </datafield>                             +
    |              | </record>                                +
    |              | </collection>
(2 rows)
```

## Xml数据的查询检索

相关函数列表如下：

| 函数                              | 描述                                                 |
| --------------------------------- | ---------------------------------------------------- |
| [Extract](#Extract)               | 对xml对象中多个满足条件的标签进行提取。              |
| [XmlSequence](#XmlSequence)       | 将多个Xmltype类型返回成一个自顶级节点往下的xml数组。 |
| [ExtractValue](#[ExtractValue)    | Extractvalue用于提取满足条件标签的值。               |
| [ExistsNode](#ExistsNode)         | 检查节点是否存在。                                   |
| [GetStringVal](#GetStringVal)     | 以字符串形式返回xml示例的值。                        |
| [APPENDCHILDXML](#APPENDCHILDXML) | 用于将用户指定的节点信息追加到目标xml文档中。        |

### Extract<a id="Extract"></a>

**功能描述**

Xmltype提供函数extract便于对xml对象中多个满足条件的标签进行提取, 返回的是满足条件的字标签类型。

**语法格式**

```sql
extract(xml_obj Xmltype ,xml_path varchar2)
```

其中xml_path格式如下：

```
tagname{[|@attname=”attvalue”]}{/tagname{[|@attname=”attvalue”]}{ /tagname{[|@attname=”attvalue”]}}...}
```

例如：

- 前文中的xml文本中标签collection下有标签record,record下有标签datafield, datafield下有标签subfield. 依次查找 所有的subfield ,其形式为：

```sql
 '/collection/record/datafield/subfield'
```

- 针对每一层标签,可以根据@attname=”attvalue”筛选,只查找满足条件的标签。如只找出datafield标签满足条件 tag=“209” 且 subfield 满足条件 code=“a” 的所有 subfield,使用如下格式：

```sql
 '/collection/record/datafield[@tag="209"]/subfield[@code="a"]'
```

**参数说明**

- **xml_obj**

  xml对象,如xml类型字段或变量。

- **xml_path**

  xml内容搜索标签的先后顺序。

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```sql
drop table if exists test_xmltype_1087939;
create table test_xmltype_1087939(
ID int,
name varchar2(257),
data xmltype
);
```

3、插入数据。

```sql
insert INTO test_xmltype_1087939
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind2=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;
```

4、使用exact提取标签。

```sql
select extract(x.data,'/collection/record/datafield/subfield') xmlseq from test_xmltype_1087939 x;
```

返回结果为：

```
                          xmlseq
-----------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------
 {"<subfield code=\"a\">抗震救灾</subfield>","<subfield code=\"f\">奥运会</subfield>","<subfield code=\"a\">经济学</subfield>","<subfield
 code=\"b\">计算机</subfield>","<subfield code=\"c\">10001</subfield>","<subfield code=\"d\">2005-07-09</subfield>","<subfield code=\"a\"
>计算机</subfield>","<subfield code=\"a\">笔记本</subfield>"}
(1 row)
select extract(x.data,'/collection/record/datafield') xmlseq from test_xmltype_1087939 x;
```

返回结果为：

```
                          xmlseq
--------------------------------------------------------------
 {"<datafield tag=\"200\" ind1=\"1\" ind2=\" \">             +
 <subfield code=\"a\">抗震救灾</subfield>                    +
 <subfield code=\"f\">奥运会</subfield>                      +
 </datafield>","<datafield tag=\"209\" ind1=\" \" ind2=\" \">+
 <subfield code=\"a\">经济学</subfield>                      +
 <subfield code=\"b\">计算机</subfield>                      +
 <subfield code=\"c\">10001</subfield>                       +
 <subfield code=\"d\">2005-07-09</subfield>                  +
 </datafield>","<datafield tag=\"610\" ind1=\"0\" ind2=\" \">+
 <subfield code=\"a\">计算机</subfield>                      +
 <subfield code=\"a\">笔记本</subfield>                      +
 </datafield>"}
(1 row)
```

### XmlSequence<a id="XmlSequence"></a>

**功能描述**

Etract提取的是多个标签, 而 xmlsequence能够将多个Xmltype类型返回成一个自顶级节点往下的xml数组。

**语法格式**

```
Xmlsequence(&xmldoc);
```

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```
show sql_compatibility;
drop table if exists test_xmltype_1087941;
create table test_xmltype_1087941(
ID int,
name varchar2(259),
data xmltype
);
```

3、插入数据。

```
Insert INTO test_xmltype_1087941
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind2=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;
```

4、使用XmlSequence将提取的数据转为数组并通过unnest函数转为表。

```sql
select unnest(XMLSequence(extract(x.data,'/collection/record/datafield/subfield'))) xmlseq from test_xmltype_1087941 x;
```

返回结果为：

```sql
             xmlseq
------------------------------------------
 <subfield code="a">抗震救灾</subfield>
 <subfield code="f">奥运会</subfield>
 <subfield code="a">经济学</subfield>
 <subfield code="b">计算机</subfield>
 <subfield code="c">10001</subfield>
 <subfield code="d">2005-07-09</subfield>
 <subfield code="a">计算机</subfield>
 <subfield code="a">笔记本</subfield>
(8 rows)
```

### ExtractValue<a id="ExtractValue"></a>

**功能描述**

Extract提取函数用于提取xml对象满足条件的标签,而Extractvalue用于提取满足条件标签的值。

**语法格式**

```sql
extract(xml_obj ,'&xml_path')
```

其中 xml_path 格式格式如下:

```sql
tagname{[|@attname=”attvalue”]}{/tagname{[|@attname=”attvalue”]}{ /tagname{[|@attname=”attvalue”]}}...}
```

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```sql
drop table if exists test_xmltype_1087942;
create table test_xmltype_1087942(
ID int,
name varchar2(260),
data xmltype
);
```

3、插入数据。

```sql
Insert INTO test_xmltype_1087942
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind3=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;
```

4、使用ExactValue函数提取满足标签的返回单个值。

```sql
select extractvalue(x.data,'/collection/record/leader') as A from test_xmltype_1087942 x;
```

返回结果为：

```sql
          a
--------------------------
 -----nam0-22-----^^^450-
(1 row)
select extractvalue(x.data,'/collection/record/datafield[@tag="209"]/subfield[@code="a"]') as A from test_xmltype_1087942 x;
```

返回结果为：

```sql
 a
--------
 经济学
(1 row)
```

### ExistsNode<a id="ExistsNode"></a>

**功能描述**

成员函数。 检查节点是否存在。 如果 XPath 字符串为 NULL 或文档为空，则返回值 0，否则返回 1。

**语法格式**

```sql
FUNCTION existsNode(
   Xml_obj  Xmltype,
   xpath IN varchar2)
RETURN number deterministic;
```

其中xpath格式参考[extract](#Extract)。

**参数说明**

- **xml_obj**

  xml对象,如xml类型字段或变量。

- **xpath**

  xml内容搜索标签的先后顺序。

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```sql
drop table if exists test_xmltype_1087946;
create table test_xmltype_1087946(
ID int,
name varchar2(264),
data xmltype
);
```

3、插入数据。

```sql
Insert INTO test_xmltype_1087946
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind3=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;
```

4、使用ExistsNode函数检查节点是否存在(对于xml文档内有的数据，返回1；对于没有的数据返回0)。

```sql
select existsnode(x.data,'/collection/record/datafield[@tag="209"]/subfield[@code="a"]') as a from test_xmltype_1087946 x;
```

返回结果为1：

```sql
 a
---
 1
(1 row)
select existsnode(x.data,'/collection/record/datafield[@tag="209"]/subfield[@code="f"]') as a from test_xmltype_1087946 x;
```

返回结果为0：

```sql
 a
---
 0
(1 row)
```

### GetStringVal<a id="GetStringVal"></a>

**功能描述**

成员函数。 以字符串形式返回xml示例的值。如果 XML 文档大于 VARCHAR2 的最大大小（4000），则在运行时会引发错误。

**语法格式**

```sql
FUNCTION getStringVal(xml_obj Xmltype
)
RETURN varchar2
```

**参数说明**

- **xml_obj**

  xml对象,如xml类型字段或变量。

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```sql
drop table if exists test_xmltype_1087944;
create table test_xmltype_1087944(
ID int,
name varchar2(262),
data xmltype
);
```

3、插入数据。

```sql
Insert INTO test_xmltype_1087944
VALUES (1,'test xml doc',xmlType.createXML('<?xml version="1.0" encoding="UTF-8" ?>
<collection xmlns="">
<record>
<leader>-----nam0-22-----^^^450-</leader>
<datafield tag="200" ind1="1" ind2=" ">
<subfield code="a">抗震救灾</subfield>
<subfield code="f">奥运会</subfield>
</datafield>
<datafield tag="209" ind1=" " ind2=" ">
<subfield code="a">经济学</subfield>
<subfield code="b">计算机</subfield>
<subfield code="c">10001</subfield>
<subfield code="d">2005-07-09</subfield>
</datafield>
<datafield tag="610" ind1="0" ind3=" ">
<subfield code="a">计算机</subfield>
<subfield code="a">笔记本</subfield>
</datafield>
</record>
</collection>')) ;
```

4、查看数据。

```sql
select * from test_xmltype_1087944;
```

返回结果为：

```sql
id |     name     |                   data
----+--------------+-------------------------------------------
  1 | test xml doc | <collection xmlns="">                    +
    |              | <record>                                 +
    |              | <leader>-----nam0-22-----^^^450-</leader>+
    |              | <datafield tag="200" ind1="1" ind2=" ">  +
    |              | <subfield code="a">抗震救灾</subfield>   +
    |              | <subfield code="f">奥运会</subfield>     +
    |              | </datafield>                             +
    |              | <datafield tag="209" ind1=" " ind2=" ">  +
    |              | <subfield code="a">经济学</subfield>     +
    |              | <subfield code="b">计算机</subfield>     +
    |              | <subfield code="c">10001</subfield>      +
    |              | <subfield code="d">2005-07-09</subfield> +
    |              | </datafield>                             +
    |              | <datafield tag="610" ind1="0" ind3=" ">  +
    |              | <subfield code="a">计算机</subfield>     +
    |              | <subfield code="a">笔记本</subfield>     +
    |              | </datafield>                             +
    |              | </record>                                +
    |              | </collection>
(1 row)
```

5、使用GetStringVal函数以字符串形式返回xml示例的值。

```sql
select getStringVal(extract(x.data,'/collection/record/datafield/subfield')) a from test_xmltype_1087944 x;
```

返回结果为：

```sql
                   a
-------------------------------------------
 <subfield code="a">抗震救灾</subfield>   +
 <subfield code="f">奥运会</subfield>     +
 <subfield code="a">经济学</subfield>     +
 <subfield code="b">计算机</subfield>     +
 <subfield code="c">10001</subfield>      +
 <subfield code="d">2005-07-09</subfield> +
 <subfield code="a">计算机</subfield>     +
 <subfield code="a">笔记本</subfield>
(1 row)
select getStringVal(extract(x.data,'/collection/record/datafield')) a from test_xmltype_1087944 x;
```

返回结果为：

```sql
                  a
------------------------------------------
 <datafield tag="200" ind1="1" ind2=" "> +
 <subfield code="a">抗震救灾</subfield>  +
 <subfield code="f">奥运会</subfield>    +
 </datafield>                            +
 <datafield tag="209" ind1=" " ind2=" "> +
 <subfield code="a">经济学</subfield>    +
 <subfield code="b">计算机</subfield>    +
 <subfield code="c">10001</subfield>     +
 <subfield code="d">2005-07-09</subfield>+
 </datafield>                            +
 <datafield tag="610" ind1="0" ind3=" "> +
 <subfield code="a">计算机</subfield>    +
 <subfield code="a">笔记本</subfield>    +
 </datafield>
(1 row)
```

### APPENDCHILDXML<a id="APPENDCHILDXML"></a>

**功能描述**

该函数用于将用户指定的节点信息追加到目标xml文档中，追加位置为xpath表达式指向的节点的子节点，返回追加了新节点的xmltype实例。

**语法格式**

```sql
FUNCTION appendchildxml(
xml_obj xmltype
xpath_expr varchar
value_expr xmltype
namespace varchar2 default NULL
)
return xmltype;
```

**参数说明**

- **xml_obj**

  xmltype实例。

- **xpath_expr**

  xpath表达式。

- **value_expr**

  待追加的xmltype节点。

- **namespace**

  xpath_expr的命名空间，默认为空。

**示例**

1、创建并切换至兼容模式为Oracle的数据库。

```sql
create database db_oracle dbcompatibility 'A';
\c db_oracle
```

2、创建测试表。

```sql
create table test_xml(id int,name xml);
```

3、创建存储过程。

```sql
create or replace procedure p3
is
vx_base xmltype := xmltype('<item></item>');
vx_node1 xmltype;
vx_node2 xmltype;
vs_node xmltype;
begin
select appendchildxml(vx_base,'/item',xmltype('<id>1</id>')) into vx_node1;
select appendchildxml(vx_base,'/item',xmltype('<id>2</id>')) into vx_node2;
select appendchildxml(vx_base,'/item',xmltype('<id>2</id>')) into vs_node;
dbms_output.put_line('vx_node1 is:'||vx_node1||',vx_node2 is:'||vx_node2||',vs_node is:'||vs_node);
insert into test_xml(name) values (vx_node1);
insert into test_xml(name) values (vx_node2);
insert into test_xml(name) values (vs_node);
end;
/
```

5、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

4、调用存储过程。

```sql
call p3();
```

返回结果为（调用成功，返回结果中依次打印vx_node1、vx_node2、vs_node的值）：

```sql
vx_node1 is:<item>
  <id>1</id>
</item>
,vx_node2 is:<item>
  <id>2</id>
</item>
,vs_node is:<item>
  <id>2</id>
</item>

 p3
----

(1 row)
```

5、查看表数据。

```sql
select * from test_xml;
```

返回结果为：

```sql
id |     name
----+--------------
    | <item>      +
    |   <id>1</id>+
    | </item>     +
    |
    | <item>      +
    |   <id>2</id>+
    | </item>     +
    |
    | <item>      +
    |   <id>2</id>+
    | </item>     +
    |
(3 rows)
```

