#### SEQUENCE兼容RESTART语法

**功能描述**

SEQUENCE支持restart语法，通过该语法可以重新设置序列的起始值。

**语法格式**

```
ALTER SEQUENCE name RESTART [WITH] number;
```

**参数说明**

- name：序列名称。
- number：序列的起始值。

**注意事项**

无。

**示例**

1、创建一个序列起始值为32。

```
CREATE SEQUENCE sequence_test20 START WITH 32;
```

2、使用nextval返回序列的一个值。

```
SELECT nextval('sequence_test20');
```

返回结果为：

```
 nextval
---------
      32
(1 row)
```

3、修改序列的起始值。 

```
ALTER SEQUENCE sequence_test20 RESTART WITH 22;
```

4、查询修改结果。

```
SELECT nextval('sequence_test20');
```

返回结果为：

```
 nextval
---------
      22
(1 row)
```