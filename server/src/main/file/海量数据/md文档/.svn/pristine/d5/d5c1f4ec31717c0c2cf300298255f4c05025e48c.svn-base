##### SET TRANSACTION

**功能描述**

为事务设置特性。事务特性包括事务隔离级别、事务访问模式(读/写或者只读)。可以设置当前事务的特性（LOCAL)，也可以设置会话的默认事务特性(SESSION)。

**语法格式**

设置事务的隔离级别、读写模式。

```
{ SET [ LOCAL ] TRANSACTION|SET SESSION CHARACTERISTICS AS TRANSACTION }
  { ISOLATION LEVEL { READ COMMITTED | READ UNCOMMITTED | SERIALIZABLE | REPEATABLE READ  }
  | { READ WRITE | READ ONLY } } [, ...]
```

**参数说明**

- LOCAL：声明该命令只在当前事务中有效。

- SESSION：声明这个命令只对当前会话起作用。

  取值范围：字符串，要符合标识符的命名规范。

- ISOLATION_LEVEL：指定事务隔离级别，该参数决定当一个事务中存在其他并发运行事务时能够看到什么数据。

  > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
  >
  > 在事务中第一个数据修改语句（SELECT、INSERT、DELETE、UPDATE、FETCH、COPY)执行之后，当前事务的隔离级别就不能再次设置。

  取值范围：

  - READ COMMITTED：读已提交隔离级别，只能读到已经提交的数据，而不会读到未提交的数据。这是缺省值。
  - READ UNCOMMITTED：读未提交隔离级别，也就是说事务所作的修改在未提交前，其他并发事务是可以读到的。
  - REPEATABLE READ：可重复读隔离级别，仅仅能看到事务开始之前提交的数据，不能看到未提交的数据，以及在事务执行期间由其它并发事务提交的修改。
  - SERIALIZABLE：Vastbase目前功能上不支持此隔离级别，等价于REPEATABLE READ。

- READ WRITE | READ ONLY：指定事务访问模式（读/写或者只读）。


**注意事项**

设置当前事务特性需要在事务中执行（即执行SET TRANSACTION之前需要执行START TRANSACTION或者BEGIN），否则设置不生效。

**示例**

开启一个事务，设置事务的隔离级别为READ COMMITTED，访问模式为READ ONLY。 

```
START TRANSACTION; 
SET LOCAL TRANSACTION ISOLATION LEVEL READ COMMITTED READ ONLY; 
COMMIT;
```

