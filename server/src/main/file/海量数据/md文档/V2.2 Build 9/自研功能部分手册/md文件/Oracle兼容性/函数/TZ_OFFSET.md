### TZ_OFFSET

**功能描述**

TZ_OFFSET函数用于返回与其输入参数相关的时区偏移量。

**语法格式**

```
select TZ_OFFSET ( { 'time_zone_name'| '{ + | - } hh : mi'| SESSIONTIMEZONE | DBTIMEZONE } )
```

**参数说明**

- time_zone_name：时区名，如'PRC'。
- {+|-} hh:mi：时区相对UTC的偏移，hh范围：0-15；mi范围：0-59。
- SESSIONTIMEZONE：返回当前会话时区。
- DBTIMEZONE：返回数据库时区。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

- 查询PRC时区的偏移量

```
select tz_offset('PRC');
```

返回结果为：

```
 tz_offset
-----------
 +08:00
(1 row)
```

- 查询GMT时区的偏移量

```
select tz_offset('GMT');
```

返回结果为：

```
 tz_offset
-----------
 +00:00
(1 row)
```


- 查询America/Lima所在时区的时区偏移量

```
select tz_offset('America/Lima');
```

返回结果为：

```
 tz_offset
-----------
 -05:00
(1 row)
```

