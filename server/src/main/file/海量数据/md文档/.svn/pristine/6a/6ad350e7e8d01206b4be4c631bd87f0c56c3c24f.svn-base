##### START TRANSACTION

**功能描述**

通过START TRANSACTION启动事务。如果声明了隔离级别、读写模式，那么新事务就使用这些特性，类似执行了SET TRANSACTION。

**语法格式**

格式一：START TRANSACTION格式

```
START TRANSACTION
   [ { ISOLATION LEVEL { READ COMMITTED | READ UNCOMMITTED | SERIALIZABLE | REPEATABLE READ  }
   | { READ WRITE | READ ONLY }
   } [, ...] ];
```

格式二：BEGIN格式

```
BEGIN [ WORK | TRANSACTION ]
  [ { ISOLATION LEVEL { READ COMMITTED | READ UNCOMMITTED | SERIALIZABLE | REPEATABLE READ }
  | { READ WRITE | READ ONLY }
  } [, ...] ];
```

**参数说明**

- WORK | TRANSACTIONBEGIN：格式中的可选关键字，没有实际作用。

- ISOLATION LEVEL：指定事务隔离级别，它决定当一个事务中存在其他并发运行事务时它能够看到什么数据。

  > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
  >
  > 在事务中第一个数据修改语句（SELECT、 INSERT、DELETE、UPDATE、FETCH、COPY）执行之后，事务隔离级别就不能再次设置。

  取值范围：

  - READ COMMITTED：读已提交隔离级别，只能读到已经提交的数据，而不会读到未提交的数据。这是缺省值。
  - READ UNCOMMITTED：读未提交隔离级别，也就是说事务所作的修改在未提交前，其他并发事务是可以读到的。
  - REPEATABLE READ：可重复读隔离级别，仅仅看到事务开始之前提交的数据，它不能看到未提交的数据，以及在事务执行期间由其它并发事务提交的修改。
  - SERIALIZABLE：Vastbase目前功能上不支持此隔离级别，等价于REPEATABLE READ。

- READ WRITE | READ ONLY：指定事务访问模式（读/写或者只读）。

**注意事项**

无。

**示例**

1、创建测试表test。

```
CREATE TABLE test(id int);
```

2、以不同方式启动事务

- 以默认方式（START TRANSACTION）启动事务，并在操作后提交。 

```
START TRANSACTION; 
SELECT * FROM test;
END; 
```

- 以默认方式（BEGIN）启动事务，并在操作后提交。 


```
BEGIN; 
SELECT * FROM test; 
END; 
```

- 以隔离级别为READ COMMITTED，读/写方式启动事务，并在操作后提交。 


```
START TRANSACTION ISOLATION LEVEL READ COMMITTED READ WRITE; 
SELECT * FROM test; 
COMMIT;
```

3、删除测试表

```
DROP TABLE test;
```

