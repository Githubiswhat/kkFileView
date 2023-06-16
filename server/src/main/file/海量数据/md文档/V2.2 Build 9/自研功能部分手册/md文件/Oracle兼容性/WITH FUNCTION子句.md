### WITH FUNCTION子句

**功能描述**

with function功能实现了再with语句中定义临时函数，在后续的子语句中可以重复使用该函数。

**语法格式**

```
WITH { FUNCTION func_name ( [ [ argmode ] [ argname ] argtype [ { DEFAULT | = } default_expr ] [, ...] ] )
    [ RETURNS rettype
      | RETURNS TABLE ( column_name column_type [, ...] ) ] AS ‘definition’ } [, …]
{ SelectStmt }
```

**参数说明**

- func_name：自定义的函数名称。取值范围：字符串，要符合标识符命令规范。

- argmode：函数参数的模式。取值范围：IN，OUT，INOUT或VARIADIC（用于声明数组类型的参数），缺省值是IN。只有OUT模式的参数后面能跟VARIADIC。并且  OUT和INOUT模式的参数不能用在RETURNS TABLE的函数定义中。

- argname：函数参数的名称。取值范围：字符串，要符合标识符命令规范。

- argtype：函数参数的类型。

- rettype：函数返回值的数据类型。如果存在OUT或IN OUT参数，可以省略RETURNS子句。如果出现了，那么它必须和输出参数隐含的结果类型兼容：如果有多个输出参数，必须是 RECORD， 如果只有一个输出参数，则与其相同。

- column_name：字段名称。

- column_type：字段类型。

- definition：一个定义函数的字符串长，含义取决于语言。它可以是一个内部函数名称、一个指向某个目标文件的路径、一个SQL查询、一个过程语言文本。

**示例**

使用WITH FUNCTION字句调用函数。

```
WITH FUNCTION withfunc (x integer) RETURNS INTEGER AS $$
BEGIN
RETURN x+1;
END
$$,
FUNCTION withfunc2(x INTEGER, y INTEGER) RETURNS INTEGER AS $$
BEGIN
RETURN x+y;
END;
$$,
FUNCTION withfunc3(x TEXT) RETURNS TEXT AS $$
BEGIN
RETURN x || '-test';
END;
$$
SELECT withfunc(1),withfunc2(2,3),withfunc3('4');
```

当结果显示如下，则表示函数调用成功：

```
 withfunc | withfunc2 | withfunc3
----------+-----------+-----------
        2 |         5 | 4-test
(1 row)
```
