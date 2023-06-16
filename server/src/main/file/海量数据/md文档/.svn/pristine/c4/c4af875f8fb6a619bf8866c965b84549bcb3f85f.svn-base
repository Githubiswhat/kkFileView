#### 兼容NO CACHE、NOCACHE、ORDER、NO ORDER、NOORDER关键字

**功能描述**

提供类似Oracle的序列功能、支持创建（create）或修改（alter）时使用：no cache、nocache、order、no order、noorder。

**语法格式**

- CREATE

```
CREATE [ TEMPORARY | TEMP ] SEQUENCE [ IF NOT EXISTS ] name [ INCREMENT [ BY ] increment ]
    [ MINVALUE minvalue | NO MINVALUE ] [ MAXVALUE maxvalue | NO MAXVALUE ]
    [ START [ WITH ] start ] [ CACHE cache ] [ NOCACHE ] [ NO CACHE ] [ [ NO ] CYCLE ]
        [ NOORDER ] [ [ NO ] ORDER ] [ OWNED BY { table_name.column_name | NONE } ]

```

- ALTER

```
ALTER SEQUENCE [ IF EXISTS ] name
    [ AS data_type ]
    [ INCREMENT [ BY ] increment ]
    [ MINVALUE minvalue | NO MINVALUE ] [ MAXVALUE maxvalue | NO MAXVALUE ]
    [ START [ WITH ] start ]
    [ RESTART [ [ WITH ] restart ] ]
    [ CACHE cache ] [ NOCACHE ] [ NO CACHE ] [ [ NO ] CYCLE ]
```

**参数说明**

- no cache、nocache：相当于cache 1（cache：设置缓存的序列值个数）。
- Order：保证序列号按请求顺序产生。如果想以序列号作为timestamp(时间戳)类型的话，可以采用该选项。
- noorder、no order：与order相对，不能保证序列号按请求顺序产生。

**注意事项**

无。

**示例**

1、 创建序列是使用nocache关键字。

```
create sequence SEQ_ID3  
minvalue 1  
maxvalue 99999999  
start with 1  
increment by 1  
nocache
noorder;
```

返回结果如下，则表示序列创建成功：

```
CREATE SEQUENCE
```

2、修改序列。

```
alter sequence SEQ_ID3 order;
```

返回结果如下，则表示修改成功：

```
ALTER SEQUENCE
```