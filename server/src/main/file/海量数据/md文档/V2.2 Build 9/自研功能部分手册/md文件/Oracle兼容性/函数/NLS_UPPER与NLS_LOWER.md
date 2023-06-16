### NLS_UPPER与NLS_LOWER

**功能描述**

nls_upper函数用于将字符转换为大写，nls_lower函数用于将字符转换为小写。

**语法格式**

nls_upper语法：

```
NLS_UPPER(char [, 'nlsparam' ])
```

nls_lower语法：

```
NLS_LOWER(char [, 'nlsparam' ])
```

**参数说明**

- char：需要转换的字符串，可以是char、varchar2、nchar、nvarchar2和clob。

- nlsparam：可选参数，不区分大小写，用于指定排序的方式，常见的有SCHINESE_RADICAL_M（部首、笔画）排序、SCHINESE_STROKE_M（笔画、部首)排序，SCHINESE_PINYIN_M（拼音）排序。

**注意事项**

使用该函数需要具备该函数的访问权限，例如nls_upper和nls_lower函数如果位于pg_catalog模式下，用户需要具备以下权限：

- pg_catalog模式的usage权限
- sys_extract_utc函数的excute权限

**示例**

- 将字符转化为大写。

```
select nls_upper('asdasd') from dual;
```

返回结果为：

```
 nls_upper
-----------
 ASDASD
(1 row)
```

- 将字符转化为小写。

```
select nls_lower('SADAS') from dual;
```

返回结果为：

```
 nls_lower
-----------
 sadas
(1 row)
```
