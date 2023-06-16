# JSONB_PATH_QUERY_ARRAY

## 功能描述

JSONB_PATH_QUERY_ARRAY函数利用SQL/JSON Path特性按照特定路径查询JSONB对象，返回查询结果，并将结果封装为数组。

## 语法格式

```sql
JSONB_PATH_QUERY_ARRAY(target jsonb, path jsonpath [, vars jsonb [, silent bool]])
```

## 参数说明

- **target**

  输入的json。

- **jsonpath**

  输入的匹配用的jsonpath。

- **var**

  json对象。它的属性将替换jsonpath里的名字。

  默认为空。

- **silent**

  当为true时，屏蔽strict模式的jsonpath在执行过程中进行结构绑定时的报错。当查询json数据时，jsonpath也许会不符合实际json数据的结构。对一个json对象不存在的成员或json数组不存在的元素的访问会导致结构化错误。在jsonpath的lax模式下，结构化会被屏蔽且转为空 SQL/JSON 序列。在strict模式下，任何结构化报错都会抛出，除非silent被设置为 true。

  默认值：false

## 注意事项

无。

## 示例

**示例1：**调用JSONB_PATH_QUERY_ARRAY函数，将符合条件的数据封装为数组。

```sql
select * from jsonb_path_query_array('{"a":[1,2,3,4,5]}', '$.a[*] ? (@ >= $min && @ <= $max)', '{"min":2, "max":4}');
```

返回结果为：

```sql
 jsonb_path_query_array
------------------------
 [2, 3, 4]
(1 row)
```

**示例2：**插入语句中使用JSONB_PATH_QUERY_ARRAY。

1、创建测试表，并插入数据。

```sql
CREATE TABLE tb_1100956(id text,id1 jsonb);

insert into tb_1100956 values( 'good ', '[{"pred": "yes", "prob": 0.6}, {"pred": "maybe", "prob": 0.4}, {"pred": "another", "prob": 0.7}]');
insert into tb_1100956 values('bad ', '[{"pred": "unexpected", "prob": 0.4}, {"pred": "uncool", "prob": 0.3}]');
```

2、查询表数据。

```sql
select * from tb_1100956 order by 1;
```

返回结果为：

```sql
  id   |                                              id1

-------+---------------------------------------------------------------------------------
 bad   | [{"pred": "unexpected", "prob": .4}, {"pred": "uncool", "prob": .3}]
 good  | [{"pred": "yes", "prob": .6}, {"pred": "maybe", "prob": .4}, {"pred": "another",
"prob": .7}]
(2 rows)
```

3、调用JSONB_PATH_QUERY_ARRAY函数。

```sql
insert into tb_1100956(id) select * from jsonb_path_query_array('{"a":[1,2,3,4,5]}', '$.a[*] ? (@ >= $min && @ <= $max)', '{"min":2, "max":4}');
```

4、查询结果。

```sql
select * from tb_1100956 order by 1;
```

返回结果如下（新增一行数据 [2, 3, 4]）：

```sql
    id     |                                              id1

-----------+-----------------------------------------------------------------------------
 [2, 3, 4] |
 bad       | [{"pred": "unexpected", "prob": .4}, {"pred": "uncool", "prob": .3}]
 good      | [{"pred": "yes", "prob": .6}, {"pred": "maybe", "prob": .4}, {"pred": "anothe
r", "prob": .7}]
(3 rows)
```

5、删除测试表。

```sql
drop table tb_1100956;
```

