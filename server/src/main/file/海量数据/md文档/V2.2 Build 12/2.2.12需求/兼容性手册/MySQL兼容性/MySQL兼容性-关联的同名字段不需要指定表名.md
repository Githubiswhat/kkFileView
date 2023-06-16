补充注意事项中参数的跳转链接

# 关联的同名字段不需要指定表名

## 功能描述

Vastbase G100在MySQL兼容模式下，支持多表关联时同名字段不需要指定表名。当存在多表关联的时，对于同名字段，group by和order by 后可以省略掉表名，表名默认跟目标列中出现的表名保持一致。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 使用前需将参数"vastbase_sql_mode"设置为空。

## 示例

1、创建兼容模式为MySQL的库db_mysql，并进入。

```sql
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

2、设置"vastbase_sql_mode"为空。

```sql
set vastbase_sql_mode='';
```

3、创建测试表，两张表中存在同名字段"customernumber"。

```sql
create table customers(
    customernumber integer NOT NULL,
    customername varchar(50) NOT NULL,
    contactlastname varchar(50) NOT NULL,
    contactfirstname varchar(50) NOT NULL,
    addressline1 varchar(50) NOT NULL,
    addressline2 varchar(50),
    city varchar(50) NOT NULL,
    state varchar(50),
    postalcode varchar(15),
    country varchar(50) NOT NULL,
    salesrepemployeenumber integer,
    creditlimit numeric(10,2))
    WITH (orientation=row,compression=no,fillfactor=80);

CREATE TABLE orders(                   
	ordernumber integer(32) NOT NULL,  
	orderdate date NOT NULL,           
	requireddate date NOT NULL,        
	shippeddate date,                  
	status varchar(15) NOT NULL,       
	comments text,                     
	customernumber integer(32) NOT NULL
);
```

4、插入测试数据。

```sql
insert into customers values('103','Atelier graphique', 'Schmitt','Carine','54,rue Royale',NULL,'Nantes',NULL,'44000','France','1370','21000');
insert into customers values('112','Signal Gift Stores','king','Jean','8489 Strong St.',NULL,'Las Vegas',NULL,'83030','USA','1166','71800');
insert into customers values('114','Australian Collectors','Co.','Ferguson Peter','636 St Kilda Road','Level 3','Melbourne','Victoria','3004','Australia','1611','117300');

insert into orders values('10100','2013-01-06','2013-01-13','2013-01-10','Shipped',NULL,'103');
insert into orders values('10102','2013-01-10','2013-01-18','2013-01-14','shipped',NULL,'112');
insert into orders values('10101','2013-01-09','2013-01-18','2013-01-11','shipped','check on availability.','114');
```

5、使用group by、having、order by不指定表名查询。

```sql
SELECT
customers.customerNumber,  
customerName,
orderDate,
LEAD(orderDate,1) OVER (
PARTITION BY customerNumber
ORDER BY orderDate ) nextOrderDate
FROM
orders
INNER JOIN customers USING (customerNumber)
group by customerNumber,orderDate
having customerNumber
order by customerNumber;
```

结果返回如下：

```sql
 customerNumber |     customerName      | orderDate  | nextOrderDate
----------------+-----------------------+------------+---------------
            103 | Atelier graphique     | 2013-01-06 |
            112 | Signal Gift Stores    | 2013-01-09 |
            114 | Australian Collectors | 2013-01-10 |
(3 rows)
```





