补充oracle兼容性->PL/SQL

# %TYPE

## 功能描述

在 pl/pgsql中，%type 属性用于将常量、变量、集合元素、记录字段或子程序参数声明为与先前声明的变量或列具有相同数据类型，无需知道该类型是什么。在 Vastbase G100中，支持`table_name.column_name%type`和 ·、`view_name.column_name%type`，即支持对表和视图列名类型的引用。

## 语法格式

```sql
table_name.column_name%type
view_name.column_name%type
```

## 参数说明

- **view_name**

    视图名称。

- **table_name**

    表名称。

- **column_name**

    列名。

## 注意事项

- 使用该%type 属性，引用项从被引用项继承以下内容 :

  - 视图/表的数据类型和大小。

  - 约束（除非引用的项目是一列）。
- 引用项不继承引用项的初始值。如果引用项的声明发生更改，则引用项的声明也会相应更改。
- 使用%type 属性，当视图/表结构变化时，能够在不重连会话的情况下感知结构的变化，同时要考虑性能问题。

## 示例

1、创建测试表和视图。

```sql
create table t_vct(id int, col char(3));
create view v_vct as select * from t_vct;
```

2、创建一个函数将%type作为函数的传入参数。

```sql
create or replace function f_vct(in a v_vct.col%type) returns void as $$
begin
raise notice '%',a;
end;
$$ language plpgsql;
```

3、调用函数。

```sql
select f_vct('test');
```

返回结果如下所示：

```sql
 f_vct
-------

(1 row)
```

4、删除视图。

```sql
drop view v_vct;
```

5、修改字段col长度使其大于原长度。

```sql
alter table t_vct modify col char(50);
```

6、重新创建视图。

```sql
create view v_vct as select * from t_vct;
```

7、再次调用函数。

```sql
select f_vct('test');
```

返回结果如下所示：

```sql
 f_vct
-------

(1 row)
```



