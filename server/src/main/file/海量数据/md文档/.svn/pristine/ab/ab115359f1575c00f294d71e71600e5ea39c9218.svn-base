###  DECODE

**功能描述**

decode函数将输入数值与函数中的对应参数相比较，若与其中一个参数相等则根据输入值返回一个对应的结果。

函数的参数列表是由输入值，若干用于比较的数值及其对应结果值和默认值组成的。假如输入值未能与任何一个比较数值匹配成功，则函数返回默认值。

**语法格式**

```
DECODE(expr, search1, result1 [, search2, result2 ]...  [, default ])
```

**参数说明**

- expr：可以是数值类型，也可以是字符类型。

- 若expr=search1，则输出result1，expr=search1，输出result1，expr=search2，输出result2……若expr不等于所列出的所有value，则输出为default。

**示例**

调用函数。

- 输入expr=search1，返回result1。

```
select decode(1,1,'2004-05-07 13:23:44',0,4,5);
```

当结果显示为如下信息，则表示验证成功

```
        case         
---------------------
 2004-05-07 13:23:44
(1 row)
```

- 输入expr=search2返回result2。

```
select decode(0,1,'2004-05-07 13:23:44',0,4,5);
```

当结果显示为如下信息，则表示验证成功

```
 case 
------
 4
(1 row)
```
