### INSTRB

**功能描述**

INSTRB函数用于搜索一个字符串在指定的字符串中出现的位置，起始搜索位置和出现的次数（第几次）可以通过参数来指定。

当起始搜索位置为负数时，表示从指定字符串的尾部往回开始搜索。返回值为字符串在被搜索的字符串中出现的位置，该位置是按字节数来计算。如果未搜索到字符串，则返回0。

**语法格式**

```
instrb(C1,C2[,I[,J]]);
```

**参数说明**

- C1：varchar2类型，被搜索的字符串。
- C2：varchar2类型，指定的字符串。
- I：integer类型，起始搜索位置，默认为1。
- J：integer类型，第J次出现的位置，默认为1。

**注意事项**

无。

**示例**

示例一：默认编码为utf8，调用instrb函数。

```
select instrb('CORPORATE FLOOR','OR');
```

返回结果为：

```
instrb               
-------
  2
(1 row)
```

示例二：

```
select instrb('CORPORATE FLOOR','OR',5,2);
```

返回结果为：

```
instrb               
-------
  14
(1 row)
```

示例三：

```
select instrb('CORPORATE FLOOR','OR',-3,2);
```

返回结果为：

```
instrb               
-------
  2
(1 row)
```

示例四：

```
select instrb('测试CORPORATE FLOOR','OR');
```

utf8编码格式下一个中文为三个字节，故返回结果为:

```
instrb               
-------
  8
(1 row)
```
