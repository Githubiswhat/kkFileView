### SINH，COSH，TANH

**功能描述**

- sinh函数：计算输入参数的双曲正弦值，并返回结果。
- cosh函数：计算输入参数的双曲余弦值，并返回结果。
- tanh函数：计算输入参数的双曲正切值，并返回结果。

**语法格式**

- sinh函数

```
sinh(arg)
```

- cosh函数

```
cosh(arg)
```

- tanh函数

```
tanh(arg)
```

**参数说明**

arg：输入float类型数值。

**示例**

- cosh函数

```
select cosh(1);

       cosh
------------------
 1.54308063481524
(1 row)
```

- sinh函数

```
select sinh(33);

      sinh
-----------------
 107321789892958
(1 row)

```

- tanh函数

```
select tanh(9);

select tanh(9);
       tanh
------------------
 .999999969540041
(1 row)
```
