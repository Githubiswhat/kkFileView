# CREATE TRIGGER

放入MySQL兼容性-SQL语法

## 功能描述

创建一个触发器。 触发器将与指定的表或视图关联，并在特定条件下执行指定的函数。在MySQL兼容模式下支持以下特性：

- 支持使用DEFINER关键字。
- 创建时支持使用IF NOT EXISTS判断触发器是否已存在。
- 支持[trigger_order](#trigger_order)中的{FOLLOWS|PRECEDES}来控制触发器的优先触发顺序。
- 支持在trigger_body部分直接通过在“begin...end“之间书写代码块。
- 支持使用LEAVE语法。

## 语法格式

```sql
CREATE [DEFINER = user] [ CONSTRAINT ] TRIGGER [IF NOT EXISTS] name { BEFORE | AFTER | INSTEAD OF } { event [ OR ... ] }
    ON table_name
    [ FROM referenced_table_name ]
    { NOT DEFERRABLE | [ DEFERRABLE ] { INITIALLY IMMEDIATE | INITIALLY DEFERRED } }
    [ FOR [ EACH ] { ROW | STATEMENT } ]
    [ WHEN ( condition ) ]
    [trigger_order]
    trigger_body
```

event部分语法如下：

```sql
INSERT
UPDATE [ OF column_name [, ... ] ]
DELETE
TRUNCATE
```
trigger_order部分语法如下：<a id=trigger_order>

```sql
[ FOLLOWS | PRECEDES ]
```

## 参数说明

-  **CONSTRAINT**

  可选项，指定此参数将创建约束触发器，即触发器作为约束来使用。除了可以使用SET CONSTRAINTS调整触发器触发的时间之外，这与常规触发器相同。 约束触发器必须是AFTER ROW触发器。

-  **name** 

  触发器名称，该名称不能限定模式，因为触发器自动继承其所在表的模式，且同一个表的触发器不能重名。 对于约束触发器，使用[SET CONSTRAINTS](SET-CONSTRAINTS.md)修改触发器行为时也使用此名称。

  取值范围：符合标识符命名规范的字符串，且最大长度不超过63个字符。

-  **BEFORE**

  触发器函数是在触发事件发生前执行。

-  **AFTER**

  触发器函数是在触发事件发生后执行，约束触发器只能指定为AFTER。

-  **INSTEAD OF**

  触发器函数直接替代触发事件。

-  **event**

  启动触发器的事件，取值范围包括：INSERT、UPDATE、DELETE或TRUNCATE，也可以通过OR同时指定多个触发事件。

  对于UPDATE事件类型，可以使用下面语法指定列：

  ```sql
  UPDATE OF column_name1 [, column_name2 ... ]
  ```

  表示当这些列作为UPDATE语句的目标列时，才会启动触发器，但是INSTEAD OF UPDATE类型不支持指定列信息。

-  **table_name**

  需要创建触发器的表名称。

  取值范围：数据库中已经存在的表名称。

- **referenced_table_name**

  约束引用的另一个表的名称。 只能为约束触发器指定，常见于外键约束。

  取值范围：数据库中已经存在的表名称。

-  **DEFERRABLE | NOT DEFERRABLE**

  约束触发器的启动时机，仅作用于约束触发器。这两个关键字设置该约束是否可推迟。

  详细介绍请参见[CREATE TABLE](CREATE-TABLE.md)。

-  **INITIALLY IMMEDIATE** **| INITIALLY DEFERRED**

  如果约束是可推迟的，则这个子句声明检查约束的缺省时间，仅作用于约束触发器。

  详细介绍请参见[CREATE TABLE](CREATE-TABLE.md)。

-  **FOR EACH ROW | FOR EACH STATEMENT**

  触发器的触发频率。

  - FOR EACH ROW是指该触发器是受触发事件影响的每一行触发一次。
  - FOR EACH STATEMENT是指该触发器是每个SQL语句只触发一次。

  未指定时默认值为FOR EACH STATEMENT。约束触发器只能指定为FOR EACH ROW。

-  **condition**

  决定是否实际执行触发器函数的条件表达式。当指定WHEN时，只有在条件返回true时才会调用该函数。

  在FOR EACH ROW触发器中，WHEN条件可以通过分别写入OLD.column_name或NEW.column_name来引用旧行或新行值的列。 当然，INSERT触发器不能引用OLD和DELETE触发器不能引用NEW。

  INSTEAD OF触发器不支持WHEN条件。

  WHEN表达式不能包含子查询。

  对于约束触发器，WHEN条件的评估不会延迟，而是在执行更新操作后立即发生。 如果条件返回值不为true，则触发器不会排队等待延迟执行。

-  **FOLLOWS**

  其他触发器的优先级顺序向后挤压。

-  **PRECEDES** 

  其他触发器的优先级顺序向前挤压。


  > <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
  >
  >  关于触发器种类：
  >
  > - INSTEAD OF的触发器必须标记为FOR EACH ROW，并且只能在视图上定义。
  > - BEFORE和AFTER触发器作用在视图上时，只能标记为FOR EACH STATEMENT。
  > - TRUNCATE类型触发器仅限FOR EACH STATEMENT。

## 注意事项

- 当前仅支持在普通行存表上创建触发器，不支持在列存表、临时表、unloggd表等类型表上创建触发器。
- 如果为同一事件定义了多个相同类型的触发器，默认依据创建时间的顺序触发，并且支持使用trigger order特征中的{FOLLOWS|PRECEDES}来控制触发器的优先触发顺序。
- 触发器常用于多表间数据关联同步场景，对SQL执行性能影响较大，不建议在大数据量同步及对性能要求高的场景中使用。
- 外键操作不会激活触发器。
- 触发器中不允许return语句，因为触发器不能返回值。要立即退出触发器，使用LEAVE语句。
- 触发器缓存不会检测底层对象的元数据何时发生更改。如果触发器使用表，并且自触发器加载到缓存后该表己更改，则触发器使用过时的元数据进行操作。
- MySQL使用ROW模式复制时，主库的语句不会触发从库的触发器，在Vastbase G100中，复制是通过日志流复制实现，与MySQL采用statement复制模式一致，因此触发器会影响从库。

## 示例

1、创建并进入兼容MySQL的库my_test。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY 'B';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------
B
(1 row)
```

2、创建测试数据。

```sql
CREATE TABLE t_follows_trigger(
id int,rep text,insert_time timestamp
);
INSERT INTO t_follows_trigger values(1,'zhangsan',sysdate);
INSERT INTO t_follows_trigger values(2,'lisi',sysdate);

CREATE TABLE t_react(
id int,rep text,insert_time timestamp
);
```

3、创建触发器函数。

```sql
CREATE OR REPLACE FUNCTION tri_insert_func() RETURNS TRIGGER AS
$$
DECLARE
BEGIN
insert into t_react values(4,'before insert',sysdate);
RETURN NEW;
END
$$ LANGUAGE PLPGSQL;
```

4、创建触发器trigger_insert。

```sql
CREATE TRIGGER trigger_insert
BEFORE insert
ON t_follows_trigger 
FOR EACH ROW

EXECUTE PROCEDURE tri_insert_func();
```

5、创建FOLLOWS触发器。

```sql
CREATE definer=vbadmin TRIGGER trigger1_follows
BEFORE insert
ON t_follows_trigger 
FOR EACH ROW
FOLLOWS trigger_insert
BEGIN 
insert into t_react values(5,'after follows1',sysdate);
END;
/
CREATE definer=vbadmin TRIGGER trigger2_follows
BEFORE insert
ON t_follows_trigger 
FOR EACH ROW
FOLLOWS trigger_insert
BEGIN 
insert into t_react values(6,'after follows2',sysdate);
END;
/
CREATE definer=vbadmin TRIGGER trigger3_follows
BEFORE insert
ON t_follows_trigger 
FOR EACH ROW
FOLLOWS trigger_insert
BEGIN 
insert into t_react values(7,'after follows3',sysdate);
END;
/
```

6、执行INSERT操作。

```sql
INSERT INTO t_follows_trigger values(3,'wangwu',sysdate),(3,'wangwu1',sysdate);
```

7、验证结果。

```sql
select id,rep from (select * from t_follows_trigger union all select * from t_react) order by insert_time ;
```

结果返回如下：

```sql
 id |      rep
----+----------------
  1 | zhangsan
  2 | lisi
  3 | wangwu
  3 | wangwu1
  4 | before insert
  7 | after follows3
  6 | after follows2
  5 | after follows1
  4 | before insert
  7 | after follows3
  6 | after follows2
  5 | after follows1
(12 rows)
```

