### ROUND(date/timestamp)

**功能描述**
该函数可以对日期进行处理，返回四舍五入的结果

**语法格式**

```
ROUND(date [, fmt ]);
ROUND(timestamp [with[without] time zone] [, fmt ]);
```

**参数说明**

- date：输入date类型数值。
- timestamp：输入timestamp类型数值。
- fmt ：返回日期或时间四舍五入到格式模型fmt指定的单位。

**示例**

1、使用round（date）函数。

```
select ROUND('2020-11-18 16:46:52'::date,'yy');
```

返回结果为：

```
        round
---------------------
 2021-01-01 00:00:00
(1 row)

```

2、使用round（timestamp）函数。

```
select ROUND('2020-11-18 16:46:52+08'::timestamp with time zone,'hh');
```

返回结果为：

```
         round
------------------------
 2020-11-18 17:00:00+08
(1 row)
```