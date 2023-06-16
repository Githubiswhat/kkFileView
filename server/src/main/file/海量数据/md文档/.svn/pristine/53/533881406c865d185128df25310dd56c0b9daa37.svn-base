### TO_TIMESTAMP_TZ

**功能描述**

TO_TIMESTAMP_TZ函数用于将字符类型的输入转化成带时区的timestamptz（存储时间戳的数据类型）。

**语法格式**

```
TO_TIMESTAMP_TZ(char  [, fmt [, 'nlsparam' ] ])
```

**参数说明**

- char：接受CHAR、VARCHAR2、NCHAR和NVARCHAR2数据类型的字符串输入。

- fmt：沿用当前数据库时间日期格式。

- nlsparam：仅需支持'NLS_DATE_LANGUAGE=''SIMPLIFIED CHINESE''和"NLS_DATE_LANG

UAGE=ENGLISH"（即中文和英文输入）。

**注意事项**

- 暂不支持DEFAULT return_value ON CONVERSION ERROR可选参数。
- 此函数不会将字符串转换为timestamp with local time zone类型。

**示例**

```
select to_timestamp_tz('2003/12/13 10:13:18', 'YYYY/MM/DD HH:MI:SS');
```

返回结果为：

```
    to_timestamp_tz
------------------------
 2003-12-13 10:13:18+08
(1 row)
```
