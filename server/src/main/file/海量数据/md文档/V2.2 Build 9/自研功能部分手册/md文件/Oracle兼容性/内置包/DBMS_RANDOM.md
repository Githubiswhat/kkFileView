###  DBMS_RANDOM

**功能描述**
dbms_random是一个PL/SQL包，用于生成随机数和随机字符。该内置包包含以下函数：

<table>
<tr>
<th>
函数
</td>
<th>
描述
</td>
</tr>
<tr>
<td>
<a href="#INITIALIZE">INITIALIZE</a>
</td>
<td>
使用种子值初始化包。
</td>
</tr>
<tr>
<td>
<a href="#NORMAL">NORMAL</a>
</td>
<td>
返回生态分布中随机数。
</td>
</tr>
<tr>
<td>
<a href="#RANDOM">RANDOM</a>
</td>
<td>
生成一个随机数。
</td>
</tr>
<tr>
<td>
<a href="#SEED">SEEd</a>
</td>
<td>
重置种子。
</td>
</tr>
<tr>
<td>
<a href="#STRING">STRING</a>
</td>
<td>
获取随机字符串。
</td>
</tr>
<tr>
<td>
<a href="#TERMINATE">TERMINATE</a>
</td>
<td>
终止包。
</td>
</tr>
<tr>
<td>
<a href="#VALUE">VALUE</a>
</td>
<td>
获取一个大于等于0小于1的随机数，或者得到指定区间的随机数。
</td>
</tr>
</table>


**注意事项**

无。

**兼容性**

完全兼容。

#### INITIALIZE函数<a id="INITIALIZE"></a>

**语法格式**

```
DBMS_RANDOM.INITIALIZE (
   val  IN  BINARY_INTEGER);
```

**参数说明**

val：用于生成随机数的种子号。

**示例**

```
select dbms_random.initialize(123456);
 initialize 
------------

(1 row)
```

#### NORMAL函数<a id="NORMAL"></a>

**语法格式**

```
DBMS_RANDOM.NORMAL
  RETURN NUMBER;
```

**示例**

```
select dbms_random.normal();
     normal     
----------------
 1.560225125239
(1 row)
```

#### RANDOM函数<a id="RANDOM"></a>

**语法格式**

```
DBMS_RANDOM.RANDOM
   RETURN binary_integer;
```

**示例**

```
select dbms_random.random();
   random   
------------
 1356653482
(1 row)
```

#### SEED函数<a id="SEED"></a>

**语法格式**

```
DBMS_RANDOM.SEED (
   val  IN  BINARY_INTEGER);

DBMS_RANDOM.SEED (
   val  IN  VARCHAR2);
```

**参数说明**

val：用于生成随机数的种子号或字符串。

**示例**

```
select dbms_random.seed('asda');
 seed 
------

(1 row)
```

#### STRING函数<a id="STRING"></a>

**语法格式**

```
DBMS_RANDOM.STRING
   opt  IN  CHAR,
   len  IN  NUMBER)
  RETURN VARCHAR2;
```

**参数说明**

- opt：字符串输出格式。
- len：字符串输出长度。

**示例**

```
select dbms_random.string('x',9);
  string   
-----------
 ZTZAXREFR
(1 row)

```

#### TERMINATE函数<a id="TERMINATE"></a>

**语法格式**

```
DBMS_RANDOM.TERMINATE;
```

**示例**

```
select dbms_random.terminate();
 terminate
-----------

(1 row)



```

#### VALUE函数<a id="VALUE"></a>

**语法格式**

```
DBMS_RANDOM.VALUE
  RETURN NUMBER;

DBMS_RANDOM.VALUE(
  low  IN  NUMBER,
  high IN  NUMBER)
RETURN NUMBER;
```

**参数说明**

- low：生成随机数范围内的最小数，生成的数字可能等于low。
- high：生成随机数的最大值，生成的数字将小于high。

**示例**

```
vastbase=# select dbms_random.value();
      value      
-----------------
 .48548461124301
(1 row)


vastbase=#  select dbms_random.value(100,2200);
      value       
------------------
 1328.65713294595
(1 row)

```
