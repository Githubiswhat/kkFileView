###  DBMS_ASSERT

**功能描述**

该内置包用于验证输入值的属性。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#ENQUOTE_LITERAL">ENQUOTE_LITERAL</a></td>
<td>输入字符串满足下述要求时，在字符串首位两端补充单引号：<br />
a. 必须保证除首尾两端的单引号之外，其余单引号必须紧邻成对出现，且成对紧邻出现时不能出现在字符串开头。<br />
b. 若原始字符串两端已有单引号，则本接口不再额外在字符串两端补充单引号。</td>
</tr>
<tr>
<td> <a href="#SCHEMA_NAME">SCHEMA_NAME</a></td>
<td>判断输入字符串是否为有效的模式名，若有效则返回该模式名。</td>
</tr>
<tr>
<td><a href="#SQL_OBJECT_NAME">SQL_OBJECT_NAME</a></td>
<td>判断输入字符串是否为有效的SQL对象名，若有效则返回该对象名。<br />输入的对象名可以是如下情况：<br />a. 若输入的对象名是同义词名，则要求其基本对象必须存在。<br />b. 该函数可识别的 SQL对象包括表，视图，同义词，索引，序列，包，触发器，函数，存储过程。<br />c. 输入内容可带从属关系，支持的从属关系输入方式包括：<br />模式名.表名   <br />模式名.视图名 <br />模式名.索引名  <br />模式名.序列名   <br />模式名.包名   <br />模式名.包名.函数名（SQL语句暂不支持) <br />模式名.包名.存储过程名（SQL语句暂不支持)  <br />模式名.包名.变量名( SQL语句暂不支持)<br />模式名.函数名   <br />模式名.存储过程名  <br />模式名.同义词   <br />模式名.表名.字段名   <br />模式名.视图名.字段名  <br />表名.字段名   <br />视图名.字段名</td>
</tr>
</table>


**注意事项**

无。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：ENQUITE_NAME、NOOP、QUALIFIED、SIMPLE_SQL_NAME。


#### ENQUOTE_LITERAL函数<a id="ENQUOTE_LITERAL"></a>

**语法格式**

```
DBMS_ASSERT.ENQUOTE_LITERAL (
   str            VARCHAR2);
```

**参数说明**

str：要引用的字符串。

**示例**

```
select DBMS_ASSERT.ENQUOTE_LITERAL('abcedf') from dual;
   enquote_literal 
  -----------------
   'abcedf'
  (1 row)
```

####  SCHEMA_NAME函数<a id="SCHEMA_NAME"></a>

**语法格式**

```
DBMS_ASSERT.SCHEMA_NAME (
   str      VARCHAR2 CHARACTER SET ANY_CS)；
```

**参数说明**

str：有效的模式名称。

**示例**

```
create schema testname;
select DBMS_ASSERT.SCHEMA_NAME('testname');
   schema_name 
  -------------
   testname
  (1 row)
```

####  SQL_OBJECT_NAME函数<a id="SQL_OBJECT_NAME"></a>

**语法格式**

```
DBMS_ASSERT.SQL_OBJECT_NAME (
   str      VARCHAR2 CHARACTER SET ANY_CS);
```


**参数说明**

str：有效的模式名称。

**示例**

1、创建函数test_f。

```
create function test_f(int) returns int as $$
  begin
  raise notice 'test_f%',$1;
  return $1;
  end $$ language plpgsql;
```

2、调用SQL_OBJECT_NAME函数。

```
select DBMS_ASSERT.SQL_OBJECT_NAME('test_f');
```

返回结果为：

```
  sql_object_name 
  -----------------
   test_f
  (1 row)
```
