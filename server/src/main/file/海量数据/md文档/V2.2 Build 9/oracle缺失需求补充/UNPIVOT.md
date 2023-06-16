替换oracle兼容性->SQL语法中的PIVOT和UNPIVOT

# UNPIVOT

## 功能描述

UNPIVOT子句用于将指定字段的字段值由列转换为行。

## 语法格式

```sql
SELECT …
FROM …
UNPIVOT[INCLUDE|EXCLUDE NULLS]
(unpivot_clause
unpivot_for_clause
unpivot_in_clause) 
WHERE …
```

## 参数说明

- **INCLUDE|EXCLUDE NULLS**

    用来控制unpivot是否包含null的记录，默认是不包含null的。

- **unpivot_clause**

    行值的列名（即unpivot_in_clause中各列的值）。
  
- **unpivot_for_clause**
    将原有多个列合并到单个列的列名，即把unpivot_in_clause中列名拼接，语法如下：

    ```sql
	  FOR unpivot_column2
	  ```

- **unpivot_in_clause**
  
   原始表的列名，语法如下：
  
   ```sql
   IN ({(column_name,...)|column_name,}...)
   ```
  
## 注意事项

无。

## 示例

1、创建测试表并插入数据。

```sql
create table t(name varchar(40),chinese int,math int);
insert into t values('张三',90,100);
insert into t values('李四',88,99);
```

2、查询表中数据。

```sq;
select * from  t;
```

返回结果为：

```sql
 name | chinese | math
------+---------+------
 张三 |      90 |  100
 李四 |      88 |   99
(2 rows)
```

3、执行如下SQL语句

```sql
select * from t unpivot(score for course in(chinese,math));
```

返回结果为：

```sql
 name | course  | score
------+---------+-------
 张三 | chinese |    90
 张三 | math    |   100
 李四 | chinese |    88
 李四 | math    |    99
(4 rows)
```