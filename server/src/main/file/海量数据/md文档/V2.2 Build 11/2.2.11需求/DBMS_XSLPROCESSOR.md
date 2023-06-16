# DBMS_XSLPROCESSOR

## 功能描述

DBMS_XSLPROCESSOR内置包提供了一个界面来管理XML文档的内容和结构。该内置包支持以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#XMLVALUEOF">VALUEOF</a></td>
<td>该函数可以根据给定的匹配模式串检索第一个节点的值。</td>
</tr>
</table>

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 系统已经安装python3。python3安装方法请参见[安装配置python3环境](安装配置python3环境.md)。

- 系统已经创建plpython3u插件，需设置环境变量PYTHONHOME，包括以下内容（重启数据库生效）：

  - PYTHONHOME：python3安装路径。

  - LD_LIBRARY_PATH：python3安装路径下的lib目录。

  - PATH：python3安装路径bin目录。

    使用如下命令创建插件：

    ```sql
    CREATE EXTENSION plpython3u;
    ```

## 子程序

### VALUEOF<a id="XMLVALUEOF"></a>

**功能描述**

该函数可以根据给定的匹配模式串检索第一个节点的值。

**语法格式**

```sql
DBMS_XSLPROCESSOR.VALUEOF(
  n           IN    DBMS_XMLDOM.DOMNODE,
  pattern     IN    VARCHAR2,
  val         OUT   VARCHAR2,
  namespace   IN    VARCHAR2 := NULL);
```

**参数说明**

- **n**

  被检索的节点。

- **pattern** 

  用于匹配的模式。

- **val**

  节点值。

- **namespace**

  命名空间。

**示例**

**前置步骤：**

1、系统已经安装python3。

2、创建文件夹并进入。

```shell
mkdir /tmp/xmltest
cd /tmp/xmltest
```

3、创建xml文件。

```shell
vi book.xml
```

文件内容如下：

```text
<?xml version="1.0" encoding="utf-8" ?>
<!--this is a test about xml.-->
<booklist type="science and engineering">
	<book category="math">
		<title>learning math</title>			
		<author>Lisa</author>					
		<pageNumber>561</pageNumber>
	</book>
	<book category="Python">
		<title>learning Python</title>
		<author>Susan</author>					
		<pageNumber>600</pageNumber>
	</book>
	<book category="C++">
		<title>learning C++</title>				
		<author>Mikes</author>					
		<pageNumber>562</pageNumber>
	</book>
</booklist>
```

4、登录数据库创建并切换至兼容模式为Oracle的库。

```sql
create database db_oracle dbcompatibility ‘A’；
\c db_Oracle
```

5、创建plpython3u插件。

```sql
CREATE EXTENSION plpython3u;
```

6、创建directory。

```sql
create directory test_dir as '/tmp/xmltest';
```

7、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

8、调用函数dbms_xslprocessor.valueof，入参节点为null。

```sql
DECLARE
parser dbms_xmlparser.parser;
doc dbms_xmldom.DOMDocument;
docnode dbms_xmldom.DOMnode;
nodelist DBMS_XMLDOM.DOMNodelist;
len int;
node dbms_xmldom.DOMnode;
title varchar2(100);
author varchar2(100);
begin
parser :=dbms_xmlparser.newParser;
dbms_xmlparser.setbasedir(parser,'test_dir');
dbms_xmlparser.parse(parser,'test_dir'||'/'||'book.xml');
--dbms_xmlparser.parse(parser,'book.xml');
doc :=dbms_xmlparser.getDocument(parser);
docnode :=dbms_xmldom.makenode(xmldom.getDocumentElement(doc));
nodelist :=DBMS_XMLDOM.getElementsByTagName(doc,'book');
len :=dbms_xmldom.getLength(nodelist);
for i in 0..len-1 loop
node :=dbms_xmldom.item(nodelist,i);
dbms_xslprocessor.valueof(node,'title/text()',title);
dbms_xslprocessor.valueof(node,'author/text()',author);
dbms_output.put_line('title:'||title||','||'author:'||author);
end loop;
dbms_xmlparser.freeparser(parser);
dbms_xmldom.freeDocument(doc);
end;
/
```

返回结果为：

```sql
title:learning math , author:Lisa
title:learning Python , author:Susan
title:learning C++,author:Mike
ANONYMOUS BLOCK EXECUTE
```



