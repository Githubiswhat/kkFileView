### PERCENTILE_CONT

**功能描述**

percentile_cont是一个假定连续分布模型的逆分布函数，该函数具有一个百分比比值和一个排序规范，其返回值是在排序规范的给定给定百分比值范围内的内插值。

**语法格式**

```
PERCENTILE_CONT(percentile) 
WITHIN GROUP (ORDER BY expr DESC/ASC)
OVER([PARTITION BY expr_list])
```

**参数说明**

- percentile：数值类型的常量，取值范围为0-1之间(包含0和1)。
- WITHIN GROUP：指定了分组元素的排序方式。
- OVER：语句中指定了分组方式。
- DESC/ASC：asc是指定列按升序排列，desc则是指定列按降序排列。

**示例**

```
SELECT color,percentile_cont(0.5) WITHIN GROUP (ORDER BY value) over(partition by color) from (VALUES('red',1),('red',3),('blue',2),('blue',4)) v(color,value);
```

返回结果如下，则表示支持该函数：

```
color ｜ percrntile_cont
------------------------
blue   | 3
blue   | 3
red    | 2
red    | 2
(4 rows)
```
