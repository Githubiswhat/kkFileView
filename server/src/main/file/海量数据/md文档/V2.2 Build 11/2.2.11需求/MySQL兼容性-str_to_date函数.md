# STR_TO_DATE

## 功能描述

STR_TO_DATE函数将时间格式的字符串，按照所提供的显示格式转换为DATETIME类型的值。

## 语法格式

```sql
STR_TO_DATE(text,format)
```

## 参数说明

- **text**

  字符串格式的时间、日期时间。

- **format**

  解析text表示的日期时间的格式规则，以下表中形式表示。
  
  <table>
      <th>说明符</th>
      <th>说明</th>
      <tr>
      <td>%a</td>
      <td>工作日的缩写名称（Sun..Sat）</td></tr>
      <tr>
      <td>%b</td>
      <td>月份的缩写名称（Jan..Dec）</td></tr>
      <tr>
      <td>%c</td>
      <td>月份，数字形式（0..12）</td></tr>
      <tr>
      <td>%D</td>
      <td>带有英语后缀的该月日期（0th,1st,2nd,3rd,...）</td></tr>
      <tr>
      <td>%d</td>
      <td>该月日期，数字形式（00..31）</td></tr>
      <tr>
      <td>%e</td>
      <td>该月日期，数字形式（0..31）</td></tr>
      <tr>
      <td>%f</td>
      <td>微秒（000000..999999）</td></tr>
      <tr>
      <td>%H</td>
      <td>小时（00..23）</td></tr>
      <tr>
      <td>%h</td>
      <td>小时（01..12）</td></tr>
      <tr>
      <td>%I</td>
      <td>小时（01..12）</td></tr>
      <tr>
      <td>%i</td>
      <td>分钟，数字形式（00..59）</td></tr>
      <tr>
      <td>%j</td>
      <td>一年中的天数（001..366）</td></tr>
      <tr>
      <td>%k</td>
      <td>小时（0..23）</td></tr>
      <tr>
      <td>%l</td>
      <td>小时（1..12）</td></tr>
      <tr>
      <td>%M</td>
      <td>月份名称（January..December）</td></tr>
      <tr>
      <td>%m</td>
      <td>月份，数字形式（00..12）</td></tr>
      <tr>
      <td>%p</td>
      <td>上午（AM）或下午（PM）</td></tr>
      <tr>
      <td>%r</td>
      <td>时间，12小时制（小时hh:分钟mm:秒数ss 后加AM或PM</td></tr>
      <tr>
      <td>%S</td>
      <td>秒（00..59）</td></tr>
      <tr>
      <td>%s</td>
      <td>秒（00..59）</td></tr>
      <tr>
      <td>%T</td>
      <td>时间，24小时制（小时hh:分钟:秒数ss）</td></tr>
      <tr>
      <td>%U</td>
      <td>周（00..53），其中周日为每周的第一天</td></tr>
      <tr>
      <td>%u</td>
      <td>周（00..53），其中周一为每周的第一天</td></tr>
      <tr>
      <td>%V</td>
      <td>周（01..53），其中周日为每周的第一天；和%X同时使用</td></tr>
      <tr>
      <td>%v</td>
      <td>周（01..53），其中周一为每周的第一天；和%x同时使用</td></tr>
      <tr>
      <td>%W</td>
      <td>工作日名称（周日..周六）</td></tr>
      <tr>
      <td>%w</td>
      <td>一周中的每日（0=周日..6=周六）</td></tr>
      <tr>
      <td>%X</td>
      <td>该周的年份，其中周日为每周的第一天，数字形式，4位数；和%V同时使用</td></tr>
      <tr>
      <td>%x</td>
      <td>该周的年份，其中周一为每周的第一天，数字形式，4位数；和%v同时使用</td></tr>
      <tr>
      <td>%Y</td>
      <td>年份，数字形式，4位数</td></tr>
      <tr>
      <td>%y</td>
      <td>年份，数字形式（2位数）</td></tr>
      <tr>
      <td>%%</td>
      <td>'%'文字字符</td></tr>
  </table>		

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 通过使用描述符的组合来与输入的text类型的字符串进行匹配，从而得到一个日期类型的返回值。

## 示例

通过format匹配字符串返回时间类型。

```sql
select str_to_date('28042022595911','%d%m%Y%i%s%h');
```

结果返回如下：

```sql
     str_to_date
---------------------
 2022-04-28 11:59:59
(1 row)
```

