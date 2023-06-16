### DBMS_XMLPARSER

**功能描述**

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
<td>获取DOM文档。</td>
</tr>
<tr>
<td><a href="#XMLPARSEBUFFER">PARSEBUFFER</a></td>
<td>解析存储在给定缓冲区中的 DTD</td>
</tr>
<tr>
<td><a href="#XMLPARSECLOB">PARSECLOB</a></td>
<td>等待任何已注册的警报出现。</td>
</table>


**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 系统已经安装python3。

- 创建plpython3u插件，需设置环境变量PYTHONHOME，包括以下内容（重启数据库生效）：

  - PYTHONHOME：python3安装路径。

  - LD_LIBRARY_PATH：增加python3安装路径下的lib目录。

  - PATH：增加python3安装路径bin目录。

**示例**

1、创建plpython3u插件（需设置环境变量PYTHONHOME）。

```
create extension plpython3u;
```

2、用解析器把XML解析到指定的CLOB对象之中。

```
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

#### NEWPARSER<a id="XMLNEWPARSER"></a>

**语法格式**

```
FUNCTION newParser 
RETURN Parser; 
```

#### GETDOCUMENT<a id="XMLGETDOCUMENT"></a>

**语法格式**

```
FUNCTION GETDOCUMENT(
  p Parser)
RETURN DOMDocument; 
```

**参数说明**

- p：解析器实例。

#### PARSEBUFFER<a id="XMLPARSEBUFFER"></a>

**语法格式**

```
PROCEDURE PARSEBUFFER(
  p   Parser,
doc VARCHAR2); 
```

**参数说明**

- p：解析器实例。

- doc：要解析的XML文档缓冲区。

#### PARSECLOB<a id="XMLPARSECLOB"></a>

**语法格式**

```
PROCEDURE PARSECLOB(
  p   Parser,
doc CLOB);  
```

**参数说明**

- p：解析器实例。

- doc：要解析的XML文档缓冲区。