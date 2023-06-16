### 数组支持RECORD类型

**功能描述**

该功能允许在PL/SQL程序中定义RECORD类型数组。

**语法格式**

```
TYPE array_type IS VARRAY(size) OF data_type;
```

**参数说明**

- array_type：要定义的数组类型名。
- VARRAY：表示要定义的数组类型。
- size：取值为正整数，表示可以容纳的成员的最大数量。
- data_type：要创建的数组中成员的类型，现在支持record类型。

**注意事项**

无。

**示例**

在程序块中定义数组类型。

```
declare
TYPE sum_type IS RECORD
(
sum_id varchar2(20),
sum_sum number
);

sum_rec sum_type;
TYPE sum_type_array IS VARRAY(2) of sum_type;
sum_rec_array sum_type_array;

begin
sum_rec.sum_id :='GLG001_CBB_DAY';
sum_rec.sum_sum := 1;
sum_rec_array[1] := sum_rec;

sum_rec.sum_id :='GLG001_CBC_SUM';
sum_rec.sum_sum := 2;
sum_rec_array[2] := sum_rec;

sum_rec := sum_rec_array[1];
raise notice 'sum_rec_array[1]：% %', sum_rec.sum_id, sum_rec.sum_sum;

sum_rec := sum_rec_array[2];
raise notice 'sum_rec_array[2]：% %', sum_rec.sum_id, sum_rec.sum_sum;
end;
/

```

返回结果为：

```
NOTICE:  sum_rec_array[1]：GLG001_CBB_DAY 1
NOTICE:  sum_rec_array[2]：GLG001_CBC_SUM 2
ANONYMOUS BLOCK EXECUTE
```