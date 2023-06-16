### NUMTOYMINTERVAL

**功能描述**

NUMTOYMINTERVAL函数将数字n转换为年或者月的间隔。

**语法格式**

```
NUMTOYMINTERVAL(n,'interval_unit')
```

**参数说明**

- n：可以是任何NUMBER值或可以隐式转换为NUMBER值的表达式（n为NULL，输出为空）。

- interval_unit：可以是char、varchar2、nchar或nvarchar2数据类型。interval_unit的值指定n

  的单位，并且必须解析为以下字符串值之一（不区分大小写）：

  - YEAR：取值范围[-2147483648，2147483647]

  - MONTH：取值范围[-178956970.7,178956970.6]

**功能描述**

使用该函数需要具备该函数的访问权限，例如numtoyminterval函数如果位于pg_catalog模式下，用户需要具备以下权限：

- pg_catalog模式的usage权限
- numtoyminterval函数的excute权限

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比的差异，Oracle中interval_unit指定n单位，并且必须解析为以下字符串值之一：DAY，HOUR，MINUTS，SECOND。

**示例**

```
select sysdate,sysdate+numtoyminterval(3,'year') as res from dual;
```

返回结果为：

```
       sysdate       |         res
---------------------+---------------------
 2022-07-14 16:13:19 | 2025-07-14 16:13:19
(1 row)
```
