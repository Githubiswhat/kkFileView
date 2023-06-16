### WM_CONCAT

**功能描述**

WM_CONCAT函数可以实现行转列功能，即将查询出的某一列的值使用逗号进行隔开拼接，使之成为一条数据，实现字段合并。

**语法格式**

```
SELECT column, WM_CONCAT(column1) FROM table GROUP BY column;
```

**参数说明**

column1：表中的某一列。

**示例**

1、创建测试表并插入数据。

```
CREATE TABLE shopping (u_id int, goods text, num int);
INSERT INTO shopping VALUES (1, 'Apple', 2), (1, 'Melon', 4), (1, 'Orange', 3);
INSERT INTO shopping VALUES (2, 'Pear', 5);
INSERT INTO shopping VALUES (3, 'Grape', 1), (3, 'Banana', 1);
```

2、使用WM_CONCAT函数1。

```
SELECT u_id, WM_CONCAT(goods) as goods_sum
FROM shopping
GROUP BY u_id;
```

返回结果如下:

```
 u_id |     goods_sum
------+--------------------
    1 | Apple,Melon,Orange
    3 | Grape,Banana
    2 | Pear
(3 rows)
```

3、使用WM_CONCAT函数2。

```
SELECT u_id, WM_CONCAT(goods || '(' || num || ')' ) as goods_sum
FROM shopping
GROUP BY u_id;
```

返回结果如下：

```
 u_id |          goods_sum
------+-----------------------------
    1 | Apple(2),Melon(4),Orange(3)
    3 | Grape(1),Banana(1)
    2 | Pear(5)
(3 rows)
```
