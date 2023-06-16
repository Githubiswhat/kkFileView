(放入oracle兼容性)

# pljson开源组件兼容

## 功能描述

提供pljson和pljson_list包，处理PL/SQL代码中json格式的数据。

## 语法格式

- 增加pljson和pljson_list包，包中提前定义数据结构：
  - 在pljson包中，定义pljson类型，用于存储json类型数据。
  - 在pljson包中，定义pljson_list类型，用于存储pljson类型的数组。

- pljson包涉及到的函数：

  - 在pljson包中增加pljson函数，函数将字符串的数据类型解析成json类型。

    ```
    function pljson(str varvhar2) return pljson
    ```

  - 增加get_string函数。

    ```
    function get_string(pair_name01 varchar2) return varchar2
    ```

  - 增加get_pljson函数。

    ```
    function get_pljson(pair_name02 varchar2) return pljson
    ```

- pljson_list包中涉及到的函数：

  - 新增pljson_list函数，将json数组格式的字符串赋值给pljson_list类型。

    ```
    function pljson_list(json_str varchar2) return pljson_list
    ```

  - 允许通过以下方式判断pljson_list对象是否为空。

    ```
    if pljson_list is [not] null
    ```

  - 增加count函数，获取pljson_list的数组个数。

    ```
    function count return number
    ```

  - 增加get函数，用于获取pljson_list数组对应下标的pljson数据。

    ```
    function get(position int) return pljson
    ```

  - 增加get_pljson_list函数可以获取pljson中第二层的pljson_list数据。

    ```
    function get_pljson_list(pair_name03 varchar2) return pljson_list
    ```

  - 数据类型pljson和pljson_list，允许是存储过程或函数的输入参数或函数返回类型。

## 参数说明：

- **str**

  输入字符串

  输入格式满足：http://www.json.org/json-zh.html

- **pljson**

  函数返回的数据类型

  如果输入数据格式不满足json格式，输出 JSON Parser exception。

- **pair_name01**

  传入的值，获取json数据中对应的value值。

- **pair_name02**

  传入的值，获取json数据中第二层的json数据。

- **json_str**

  输入的json数组格式的字符串。

- **pljson_list**

  函数返回的数据类型。

- **position**

  输入数组的下标值。

  数组下标从1开始计数。

- **pair_name03**

  传入的值，获取对应value中的pljson_list的数组。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- get_string(key)函数仅支持一层json的数据，如果有多层返回值为空。
- 暂不支持pljson开源组件其他功能。

## 示例

**前置步骤：**创建创建兼容Oracle的库test并进入。

```sql
CREATE DATABASE test DBCOMPATIBILITY 'A';
\c test
```

**示例1**：存储过程输入参数为pljson_list，导入表后查询。

1、配置环境、创建测试表。

```sql
create extension pljson;
set serveroutput on;
create table test_tab(w1 pljson_list);
```

2、创建存储过程。

```sql
create or replace procedure pro(v_col1 in pljson_list) as
v_json_list pljson_list;
begin
v_json_list := pljson_list('[{"type" : "Office", "number" : "909-555-7307"},
{"type" : "Mobile", "number" : "415-555-1234"}]');
insert into test_tab values(v_json_list);
dbms_output.put_line(v_json_list::varchar);
end;
/
```

3、调用存储过程。

```sql
call pro(pljson_list('[{"type" : "Office", "number" : "909-555-7307"},{"type" : "Mobile", "number" : "415-555-1234"}]'));
```

返回结果为：

```sql
("{""(1,,,,,,,,\\""()\\"",\\""(\\""\\""{\\""\\""\\""\\""(3,Office,1,,,,type,1,\\\\\\\\\\\\\\\\\\""\\""\\""\\""()\\\\\\\\\\\\\\\\\\""\\""\\""\\"",\\\\\\\\\\\\\\\\\\""\\""\\""\\""(,)\\\\\\\\\\\\\\\\\\""\\""\\""\\"")\\""\\""\\""\\"",\\""\\""\\""\\""(3,909-555-7307,1,,,,number,2,\\\\\\\\\\\\\\\\\\""\\""\\""\\""()\\\\\\\\\\\\\\\\\\""\\""\\""\\"",\\\\\\\\\\\\\\\\\\""\\""\\""\\""(,)\\\\\\\\\\\\\\\\\\""\\""\\""\\"")\\""\\""\\""\\""}\\""\\"",1)\\"")"",""(1,,,,,,,,\\""()\\"",\\""(\\""\\""{\\""\\""\\""\\""(3,Mobile,1,,,,type,1,\\\\\\\\\\\\\\\\\\""\\""\\""\\""()\\\\\\\\\\\\\\\\\\""\\""\\""\\"",\\\\\\\\\\\\\\\\\\""\\""\\""\\""(,)\\\\\\\\\\\\\\\\\\""\\""\\""\\"")\\""\\""\\""\\"",\\""\\""\\""\\""(3,415-555-1234,1,,,,number,2,\\\\\\\\\\\\\\\\\\""\\""\\""\\""()\\\\\\\\\\\\\\\\\\""\\""\\""\\"",\\\\\\\\\\\\\\\\\\""\\""\\""\\""(,)\\\\\\\\\\\\\\\\\\""\\""\\""\\"")\\""\\""\\""\\""}\\""\\"",1)\\"")""}")
 pro
-----

(1 row)

```

**示例2：**包中存储过程inout参数为pljson，get函数获取指定数值下标的值

1、配置环境，创建测试表。

```sql
create extension pljson;
set serveroutput on;
create table test_tab2(w1 pljson);
```

2、创建存储过程。

```sql
create or replace package test_pkg as
procedure pro1(i_json inout pljson);
end;
/

create or replace package body test_pkg as
procedure pro1(i_json inout pljson) as
begin
insert into test_tab2 values(i_json);
dbms_output.put_line(get(i_json,1)::varchar);
end;
end;
/
```

3、调用存储过程。

```sql
call test_pkg.pro1(pljson('{"type" : "Mobile", "number" : "415-555-1234"}'));
```

结果显示为：

```sql
                json_data         | check_for_duplicate
----------------------------------+---------------------
 {"(3,Mobile,1,,,,type,1,\"()\",\"(,)\")","(3,415-555-1234,1,,,,number,2,\"()\",\"(,)\")"}                          |           1
 
(1 row)
```

