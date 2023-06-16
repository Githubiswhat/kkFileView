# MySQL兼容性-SQL中支持双引号包裹字符值

## 功能描述

在MySQL兼容模式下，可以通过vastbase_sql_mode参数中的ANSI_QUOTES来控制双引号表示的效果。当启用ANSI_QUOTES时，双引号引用的内容被解释为标识符；当没有启用ANSI_QUOTES时，双引号引用内容被解释为字符串。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- vastbase_sql_mode默认值为“ONLY_FULL_GROUP_BY,ANSI_QUOTES”，包含“ANSI_QUOTES”。

## 示例

1、启用ansi_quotes。

```sql
set vastbase_sql_mode='ANSI_QUOTES';
```

2、查看当前vastbase_sql_mode。

```sql
show vastbase_sql_mode;
```

结果返回如下：

```sql
 vastbase_sql_mode
-------------------
 ANSI_QUOTES
(1 row)
```

3、创建测试表"qtest"

```sql
CREATE TABLE "qtest"(id int);
```

4、表名加双引号插入数据。

```sql
insert into "qtest" values(1);
```

结果返回如下，插入成功。

```sql
INSERT 0 1
```

5、不加双引号插入数据。

```sql
insert into qtest values(2);
```

结果返回如下，插入成功。

```sql
INSERT 0 1
```

6、表名及插入数据加双引号插入数据。

```sql
insert into "qtest" values("3");
```

结果返回如下，插入失败。

```sql
ERROR:  column "3" does not exist
LINE 1: insert into "qtest" values("3");
```

