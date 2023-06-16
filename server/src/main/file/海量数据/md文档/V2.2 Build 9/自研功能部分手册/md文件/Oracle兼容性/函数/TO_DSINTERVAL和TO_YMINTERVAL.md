### TO_DSINTERVAL和TO_YMINTERVAL

**功能描述**

TO_DSINTERVAL 函数将时间转换为DAY TO SECOND INTERVAL数据类型，即处理时间时仅包含日期，小时，分钟和秒的部分。

TO_YMINTERVAL函数将其参数转换为YEAR TO MONTH INTERVAL数据类型，即处理时间时仅包含年和月的部分。

**语法格式**

to_dsinterval函数

```
TO_DSINTERVAL ('[+ | -] days hours : minutes : seconds ' [ DEFAULT return_value ON CONVERSION ERROR ]
```

to_yminterval函数

```
TO_YMINTERVAL ( '{ [+|-] years - months } ' [ DEFAULT return_value ON CONVERSION ERROR ])
```

**参数说明**

to_dsinterval函数：

- days：天，整形，范围0到999999999。
- hours：小时，整形，范围0到23。
- minutes：分，整形，范围0到59。
- seconds：秒，整形，范围0到59，Vastbase秒后的小数点支持到6位（Oracle秒后的小数点支持到9位）。
- DEFAULT return_value ON CONVERSION ERROR：可选参数，该子句允许指定此函数在将参数转换为INTERVAL DAY TO SECOND类型时发生错误时返回的值。

 to_yminterval函数：

- years：年，整形，Vastbase取值范围0到178956970（Oracle取值范围0到999999999）。
- months：小时，整形，范围0到11。
- u DEFAULT return_value ON CONVERSION ERROR：可选参数。该子句允许指定此函数在将参数转换为INTERVAL MONTH TO YEAR类型时发生错误时返回的值。

**注意事项**

- to_dsinterval函数将传入字符串转换为INTERVAL数据类型。函数接收两种格式字符串。

  -  SQL格式，兼容SQL标准（ISO/IEC9075）。

     - 不允许输入年和月。
     - 语法元素之间允许任意个空格。
  -  SISO格式，兼容ISO 8601:2004标准。
     -  不允许输入年和月。

     -  语法元素之间不允许有空格。

     -  输入的时间如果出现进位，进位之后的结果超出输入限制是允许的。
- to_yminterval函数将传入字符串转换为INTERVAL数据类型。函数接收两种格式字符串。
  - SQL格式，兼容SQL标准（ISO/IEC9075）。
    - 仅允许出现年和月。
    - 语法元素之间允许任意个空格。
  - ISO格式，兼容ISO 8601:2004标准。
    - 年、月、日、时、分、秒和小数秒，均允许输入，但除年和月以外的内容会被忽略。
    - 秒的小数位支持输入9位，超出后报错（虽然秒会被忽略，但会引发报错）。
    - Vastbase年数范围为0到178956970。如果输入月数大于等于12时，会出现向年份进位的情况，进位之后应保证年数范围依然在0到178956970，否则报错。
    - 语法元素之间不允许有空格。

**示例**

- to_dsinterval函数：

```
select to_dsinterval('1 12 : 34 : 56 ')  from dual;  
```

返回结果为：

```
 to_dsinterval
----------------
 1 day 12:34:56
(1 row)
```

- to_yminterval函数：

```
select to_yminterval('1-1') from dual;
```

返回结果为：

```
 to_yminterval
---------------
 1 year 1 mon
(1 row)
```
