# DBMS_XMLPARSER

## 功能描述

dbms_xmlparser包用于构造一个解析xml文档的解析器，并提供一系列接口对解析树的结构和内容进行操作和访问。dbms_xmlparser构造的解析树可以被DOM API访问。该内置包包含以下模块：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#XMLNEWPARSER">NEWPARSER</a></td>
<td>创建解析器。</td>
</tr>
<tr>
<td><a href="#XMLGETDOCUMENT">GETDOCUMENT</a></td>
<td>返回解析器构建的DOM树文档的文档节点，只有文档被解析之后才能调用此函数。</td>
</tr>
<tr>
<td><a href="#XMLPARSEBUFFER">PARSEBUFFER</a></td>
<td>解析存储在指定缓冲区中的xml并存储到解析器中。</td>
</tr>
<tr>
<td><a href="#XMLPARSECLOB">PARSECLOB</a></td>
<td>解析指定clob中的xml并存储到解析器中。</td>
<tr>
<td><a href="#XMLSETBASEDIR">SETBASEDIR</a></td>
<td>设置解析相对路径的文件的目录。</td>
 <tr>
<td><a href="#XMLPARSE">PARSE</a></td>
<td>根据给定的文件名进行解析，返回一个DOM文档或将解析结果保存在解析器中。</td>
<tr>
<td><a href="#XMLFREEPARSER">FREEPARSER</a></td>
<td>释放解析器。</td>
</table>

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- dbms_xmlparser支持同义词 xmlparser ，即调用内置函数时，内置包名称 dbms_xmlparser名可用xmlparser替换。

- 系统已经安装python3。python3安装方法请参见[安装配置python3环境](安装配置python3环境.md)。

- 系统已经创建plpython3u插件，需设置环境变量PYTHONHOME，包括以下内容（重启数据库生效）：

  - PYTHONHOME：python3安装路径。

  - LD_LIBRARY_PATH：python3安装路径下的lib目录。

  - PATH：python3安装路径bin目录。

    使用如下命令创建插件：

    ```sql
    CREATE EXTENSION plpython3u;
    ```

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库中。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

1、创建plpython3u插件（需设置环境变量PYTHONHOME）。

```sql
create extension plpython3u;
```

2、用解析器把XML解析到指定的CLOB对象之中。

```sql
SET SERVEROUTPUT ON;
DECLARE
var clob;
parser DBMS_XMLPARSER.parser;
BEGIN
var := '<?xml version="1.0"?>
<EMPSET>
<!--comment-->
<![CDATA[cdata]]>
<test>testtext</test>
<EMP num1="1" num2="2">
<EMP_NO>7782</EMP_NO>
<ENAME>CLARK</ENAME> 
</EMP>
<EMP num="2"> 
<EMP_NO>7839</EMP_NO>
<ENAME>KING</ENAME>
</EMP>
<EMP num="3"> 
<EMP_NO>7934</EMP_NO>
<ENAME>MILLER</ENAME>
</EMP>
</EMPSET>';
parser := DBMS_XMLPARSER.newParser;
DBMS_XMLPARSER.parseClob(parser,var);
END;
/
```

返回结果为：

```sql
ANONYMOUS BLOCK EXECUTE
```

## 子程序

### NEWPARSER<a id="XMLNEWPARSER"></a>

**语法格式**

```sql
FUNCTION newParser 
RETURN Parser; 
```

### GETDOCUMENT<a id="XMLGETDOCUMENT"></a>

**语法格式**

```sql
FUNCTION GETDOCUMENT(
  p Parser)
RETURN DOMDocument; 
```

**参数说明**

**p**

解析器实例。

### PARSEBUFFER<a id="XMLPARSEBUFFER"></a>

**语法格式**

```sql
PROCEDURE PARSEBUFFER(
  p   Parser,
doc VARCHAR2); 
```

**参数说明**

- **p**

  解析器实例。

- **doc**

  要解析的XML文档缓冲区。

### PARSECLOB<a id="XMLPARSECLOB"></a>

**语法格式**

```sql
PROCEDURE PARSECLOB(
  p   Parser,
doc CLOB);  
```

**参数说明**

- **p**

  解析器实例。

- **doc**

  要解析的XML文档缓冲区。

### SETBASEDIR<a id="XMLSETBASEDIR"></a>

**语法格式**

```sql
PROCEDURE SETBASEDIR(
  p Parser,
  dir VARCHAR2); 
```

**参数说明**

- **p**

  解析器实例。

- **doc**

  在解析器中设置为解析文件的目录，为directory对象名称。


### PARSE<a id="XMLPATSE"></a>

**语法格式**

```sql
FUNCTION PARSE(
  url VARCHAR2)
RETURN DOMDocument; 

PROCEDURE PARSE(
  p Parser,
  url VARCHAR2); 
```

**参数说明**

- **p**

  解析器实例。

- **url**

  要解析的xml文件名或文件的完整路径。

### FREEPARSER<a id="XMLFREEPARSER"></a>

**语法格式**

```sql
PROCEDURE FREEPARSER(
  p Parser; 
```

**参数说明**

**p**

解析器实例。