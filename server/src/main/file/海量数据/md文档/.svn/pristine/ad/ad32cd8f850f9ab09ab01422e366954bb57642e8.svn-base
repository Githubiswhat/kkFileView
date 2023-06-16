###  ORA_HASH

**功能描述**

该函数用于为给定表达式计算哈希值。

**语法格式**

```
ora_hash（expr[,max_bucket[,seed_value]])
```

**参数说明**

- expr：需要计算hash值的表达式或者列。
- max_bucket：哈希函数返回的最大桶值，范围0到4294967295，默认为4294967295。
- seed_value：为同一组数据生成许多不同的结果，范围0到4294967295，默认为0。当seed_value值不一样时，产生的结果值不一样。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

1、创建测试表并插入数据。

```
drop table test_hash ;
create table test_hash(c1 int,c2 varchar(20));
insert into test_hash values(1,'test1');
insert into test_hash values(2,'test2');
```

2、调用ORA_HASH函数。

```
select ora_hash(c2,12,11) from test_hash;
```

返回结果为：

```
 ora_hash
----------
        5
        6
(2 rows)
```
