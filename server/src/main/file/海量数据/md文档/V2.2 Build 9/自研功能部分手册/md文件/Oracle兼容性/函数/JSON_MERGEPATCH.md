### JSON_MERGEPATCH

**功能描述**

json_mergepatch函数可查询和更新json文档的特定部分。

**语法格式**

```
 JSON_MERGEPATCH
         (target_expr,patch_expr[returning_clause] )
```

**参数说明**

- target_expr：需要修改的目标表达式（以下简称源）。

- patch_expr：一个json片段，表达要合并到目标表达式中的补丁（以下简称补丁）。

- json_returning_clause：指定操作符的返回类型。默认的返回类型是text。

**注意事项**

- 使用该函数需要具备该函数的访问权限，例如json_mergepatch函数如果位于pg_catalog模式下，用户需要具备以下权限：

  	- pg_catalog模式的usage权限。
  	- json_mergepatch函数的excute权限。

- json的数据类型有：空值null、布尔值false/true、字符串”string”、数组[]和对象{}。数组[]和对象{}称为复杂数据类型，可嵌套使用。

- 补丁必须为数组或者对象，否则会报错。

  - 补丁为数组时： 
    - 源是空值null、布尔值false/true或者字符串”string”时，返回null。
    - 源为数组或者对象时，使用补丁替换源。
  - 补丁为对象时：
    - 源是空值null、布尔值false/true或者字符串”string”时，返回null。
    - 源为数组时，去除补丁中值为null的键值对后再替换源。
    - 源为对象时，按照如下两个对象合并规则合并。

- 两个对象合并规则：

  - 如果补丁不是JSON对象。使用补丁替换源（补丁值为null时，若源中没有对应补丁的键，
    则该键值对不能出现在结果中）。
  - 如果补丁是JSON对象，请执行以下操作:

  (1)如果源不是对象，则将其视为空对象({})。
  (2)迭代补丁对象的(p-field:p-value)成员。

  - 如果补丁成员的p-value为null，则从源中删除相应的成员。
  - 否则，递归：源对应的值和p-value合并的结果，替换相应源字段的值。

- Vastbase处理json数据时会对object的键值对中的键进行排序和去除相同键，因此object中的键值对和输入时的顺序可能会不一样。Vastbase注重合并结果，不保证顺序或者空格的使用上和Oracle完全一致。

- 隐式转化规则新增了text和json/jsonb相互转化外。

**示例**
1、创建测试表并插入数据。

```
create table json_mergepatch_test(Id  bigint,Data json );
INSERT INTO json_mergepatch_test (id,data) values(1,'{"id":1,"first_name":"Iron","last_name":"Man"}');
```

2、查询数据。

```
SELECT JSON_MERGEPATCH(data, '{"last_name":"banana"}') AS data FROM json_mergepatch_test;
```

返回结果为：

```
                          data
--------------------------------------------------------
 {"id": 1, "last_name": "banana", "first_name": "Iron"}
(1 row)
```

3、更新表中的数据。

```
UPDATE json_mergepatch_test a SET a.data = JSON_MERGEPATCH(a.data, '{"last_name":"banana","new_element":"surprise"}');
```

4、查询数据。

```
SELECT data FROM json_mergepatch_test;
```

返回结果为：

```
                                       data
-----------------------------------------------------------------------------------
 {"id": 1, "last_name": "banana", "first_name": "Iron", "new_element": "surprise"}
(1 row)
```
