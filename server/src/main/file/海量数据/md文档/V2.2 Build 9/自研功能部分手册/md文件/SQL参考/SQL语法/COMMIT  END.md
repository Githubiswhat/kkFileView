##### COMMIT | END

**功能描述**

通过COMMIT或者END可完成提交事务的功能，即提交事务的所有操作。

**语法格式**

```
{ COMMIT | END } [ WORK | TRANSACTION ] ;
```

**参数说明**

- COMMIT | END：提交当前事务，让所有当前事务的更改为其他事务可见。

- WORK | TRANSACTION：可选关键字，除了增加可读性没有其他任何作用。

**注意事项**

无。

**示例**

1. 创建表。 

```
CREATE TABLE customer_demographics_t2 
   ( 
     CD_DEMO_SK         INTEGER        NOT NULL, 
     CD_GENDER         CHAR(1), 
     CD_MARITAL_STATUS     CHAR(1), 
     CD_EDUCATION_STATUS    CHAR(20), 
     CD_PURCHASE_ESTIMATE    INTEGER, 
     CD_CREDIT_RATING      CHAR(10), 
     CD_DEP_COUNT        INTEGER, 
     CD_DEP_EMPLOYED_COUNT   INTEGER, 
     CD_DEP_COLLEGE_COUNT    INTEGER 
   ) 
   WITH (ORIENTATION = COLUMN,COMPRESSION=MIDDLE) 
   ; 
```

2. 开启事务。 

```
START TRANSACTION; 
```

3. 插入数据。 

```
INSERT INTO customer_demographics_t2 VALUES(1,'M', 'U', 'DOCTOR DEGREE', 1200, 'GOOD', 1, 0, 0); 
INSERT INTO customer_demographics_t2 VALUES(2,'F', 'U', 'MASTER DEGREE', 300, 'BAD', 1, 0, 0); 
```

4. 提交事务，让所有更改永久化。 

```
COMMIT; 
```

5. 新建连接查询数据。 

```
SELECT * FROM customer_demographics_t2; 
```

当结果显示如下信息时，则表示事务提交成功。

```
cd_demo_sk | cd_gender | cd_marital_status | cd_education_status  |      cd_purchase_estimate | cd_credi
t_rating | cd_dep_count | cd_dep_employed_count |          cd_dep_college_count 
------------+-----------+-------------------+----------------------+----------------------+---------
---------+--------------+-----------------------+----------------------
          1 | M         | U                 | DOCTOR DEGREE        |                    1200 | GOOD    
         |            1 |                     0 |                    0
          2 | F         | U                 | MASTER DEGREE        |                     300 | BAD     
         |            1 |                     0 |                       0
(2 rows)
```

6. 删除表customer_demographics_t2。 

```
DROP TABLE customer_demographics_t2;
```

  