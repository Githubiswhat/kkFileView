补充[convert_from](字符处理函数和操作符.md)函数跳转链接。

# 数据类型

Vastbase G100兼容MySQL数据类型如下所示：

<table>
    <tr>
        <th>类型</th>
        <th>描述</th>
        <th>存储空间</th>
    </tr>
    <tr>
        <td>binary</td>
        <td>BINARY类型是固定长度二进制字符串，与char相似，可存储字符。不同的是BINARY存储的是二进制值，BINARY无字符集的概念，对其排序和比较都是按照二进制值进行对比。BINARY（N）中的N指的是字节长度。N取值范围为1~255。</td>
        <td>最大为1GB</td>
    </tr>
    <tr>
        <td>longtext</td>
        <td>变长字符串。</td>
        <td>最大为1GB，但还需要考虑到列描述头信息的大小， 以及列所在元组的大小限制（也小于1GB），因此longtext类型最大小于1GB。</td>
    </tr>
    <tr>
        <td>datetime</td>
        <td>datetime[n]用于设置显示毫秒后面的多少位，n表示获取的位数，取值为1~6。</td>
        <td>8字节</td>
    </tr>
    <tr>
        <td>longblob</td>
        <td>longblob是一种存储二进制数据的数据类型，可以用于存储大型二进制对象（BLOB），例如图像，音频和视频等数据。</td>
        <td>最大为4GB</td>
    </tr>
</table>



### 注意事项

- binary，datetime数据类型仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- datetime[n]数据类型的实现依赖Vastbase现有timestamp数据类型，因此，在一些场景中，例如`\d`元命令中，datetime数据类型会被显示成timestamp类型。
- 当datetime[n]类型指定了小数位之后，如果datetime[n]类型的数据小数位都是0，则在Vastbase数据库中，受到timestamp数据类型的局限，其小数位上的0将不做显示。

## 示例

**前置步骤：**创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

**示例1：**binary类型。

1、创建测试binary类型的表test_mysql_type2并插入测试数据。

```sql
CREATE TABLE test_mysql_type2(c1 binary);
INSERT INTO test_mysql_type2 values('a');
INSERT INTO test_mysql_type2 values('张三');
```

2、验证binary数据类型。

```sql
SELECT * FROM test_mysql_type2;
```

当结果显示如下信息，则表示验证完成。

```sql
    c1
-----------------------
 \x61
 \xe5bca0e4b889
(2 rows)
```

**示例2：**longtext类型。

1、创建测试longtext类型的表test_mysql_type3并插入测试数据。

```sql
CREATE TABLE test_mysql_type3(c1 longtext);
INSERT INTO test_mysql_type3 values('测试abc123');
```

2、验证longtext数据类型。

```sql
SELECT * FROM test_mysql_type3;
```

当结果显示如下信息，则表示验证完成。

```sql
   c1
------------------
 测试abc123
(1 row)
```

**示例3：**datetime类型。

1、创建测试datetime类型的表test_mysql_type4并插入测试数据。

```sql
CREATE TABLE test_mysql_type4(c1 datetime);
INSERT INTO test_mysql_type4 values('2019-03-05 01:53:55.63');
```

2、验证datetime数据类型。

```sql
SELECT * FROM test_mysql_type4;
```

当结果显示如下信息，则表示验证完成。

```sql
     c1
--------------------------
 2019-03-05 01:53:55.63
(1 row)
```

**示例4：**longblob类型。

1、创建测试表并插入数据。

```sql
CREATE TABLE t_longblob
(c1 int primary key auto_increment,c2 longblob default 'test_default，测试默认值');
INSERT INTO t_longblob VALUE(1,'test1');
INSERT INTO t_longblob(c2) VALUES(default);
```

2、查询表中数据。

```sql
select * from t_longblob;
```

返回结果为：

```sql
 c1 |                              c2
----+--------------------------------------------------------------
  1 | 7465737431
  2 | 746573745F64656661756C74EFBC8CE6B58BE8AF95E9BB98E8AEA4E580BC
(2 rows)
```

3、将longblob类型的二进制数据转换为UTF8编码的形式。

```sql
select c1,convert_from(rawsend(c2::raw),'utf8') from t_longblob;
```

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> - convert_from函数用来以数据库编码的方式转换字符串bytea。详细请参考[convert_from](字符处理函数和操作符.md)。
> - rawsend是内部处理函数，用于将raw类型数据转换为bytea。

数据表中的存储的longblob类型的二进制数据，经过上述转换之后查询返回结果为：

```sql
 c1 |       convert_from
----+--------------------------
  1 | test1
  2 | test_default，测试默认值
(2 rows)
```

