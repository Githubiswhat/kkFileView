### ROWID

**功能描述**

ROWID是一个伪列，该值表明了该行在Oracle数据库中的详细物理地址，通过ROWID，数据库可以快速定位某行具体数据的位置。

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 子查询中也可以使用rowid。目前具体需求只有一层子查询。对于多层子查询嵌套的时候，在各层中都可以使用rowid不做强制要求。

**兼容性**

完全兼容。

**示例**

1、创建测试表并插入数据。

```
create table t01(id int,name varchar2(32));
insert into t01(id,name) values(1,'a'),(2,'b'),(3,'c'),(4,'d');
```

2、查询ROWID。

```
 select rowid,t.* from (select a.* from t01 a) t;
```

返回结果为：

```
        rowid         | id | name
----------------------+----+------
 h1IAAA==AAAAAA==AQA= |  1 | a
 h1IAAA==AAAAAA==AgA= |  2 | b
 h1IAAA==AAAAAA==AwA= |  3 | c
 h1IAAA==AAAAAA==BAA= |  4 | d
(4 rows)
```

3、子查询外层支持ROWID。

```
select rowid as rowid_2,t.* from (select rowid,a.* from t01 a) t;
```

返回结果为：

```
       rowid_2        |        rowid         | id | name
----------------------+----------------------+----+------
 h1IAAA==AAAAAA==AQA= | h1IAAA==AAAAAA==AQA= |  1 | a
 h1IAAA==AAAAAA==AgA= | h1IAAA==AAAAAA==AgA= |  2 | b
 h1IAAA==AAAAAA==AwA= | h1IAAA==AAAAAA==AwA= |  3 | c
 h1IAAA==AAAAAA==BAA= | h1IAAA==AAAAAA==BAA= |  4 | d
(4 rows)
```
