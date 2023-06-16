### 匿名块支持JDBC绑定变量输入输出

**功能描述**

该功能实现了JDBC驱动程序在使用匿名代码块的时候可以绑定变量作为输入输出参数。

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 只有使用配套的JDBC才可以实现该功能。
- 输入类型支持数值/字符串/日期等数据库常用数据类型。
- 内核输出类型只支持text，JDBC端支持ROWID类型输出。
- 不支持自治事务匿名块绑定变量输入输出。

**使用流程**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、在数据库中建表。

```
create table t_rowid(a varchar,b int,c float8,d timestamp,e varchar,f varchar);
```

3、JAVA端正常连接数据库后进行如下操作。

（1）匿名块+绑定变量SQL语句。

```
String sql=
  "begin\r\n"+
  "  insert into t_rowid values(?,?,?,?,?,?) returning rowid into ?;\r\n"+
  "insert into t_rowid values(?) returning rowid into ?;\r\n"+
  "select ? into ?;\r\n""+
  "end";
```

(2)输入输出参数声明并执行。

```
try(CallableStatement pstmt = connection.prepareCall(sql)){
      pstmt.setString(1, "hello");
      pstmt.setInt(2, 666);
      pstmt.setDouble(3, 66.66)
      pstmt.setTimestamp(4, new java.sql.Timestamp(new Date() getTime())); pstmt.setString(5, null); 
      pstmt.setString(6, "0");
      pstmt.registerOutParameter(7,Types.VARCHAR); 
      pstmt.setString(8, "你好");
      pstmt.registerOutParameter(9,Types.VARCHAR); 
      pstmt.setString(10, "hello world");
      pstmt.registerOutParameter(11,Types.VARCHAR);
      pstmt.execute();
      String value =pstrmt.getString(7); 
      System.out.println("value:"+value); 
      String val = pstmt.getString(9); 
      System.out.println("value:"+val); 
      String valu = pstmt.getString(11); 
      System.out.println("value:"+valu)
}
```

3、在数据库中执行结果查询。

```
select * fron t_rowid;
```

返回结果为：

```
  a   |   b   |    c  |          d           |    e | f

------+-------+-------+----------------------+------+---
hello |  666  | 66.66 | 2022-01-06 18:23:31:34|     | 0
你好
(2 rows)
```

4、JAVA端接受的结果。

```
value:0EQAAA==AAAAAA==BAA=
value:0EQAAA==AAAAAA==BQA=
value:hello world
```