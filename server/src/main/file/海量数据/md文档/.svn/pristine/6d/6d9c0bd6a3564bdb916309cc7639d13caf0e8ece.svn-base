## VPD功能

**功能描述**

通过添加管理策略功能可针对某个模式schema中的某个对象object（包括table或视图view）添加管理策略。

**语法格式/参数说明**

- 添加VPD策略

```
dbms_rls.add_policy(
        Object_name text, --对象名称
        policy_name text, --策略名称
        policy_function text, --策略函数名称
        object_schema text DEFAULT NULL::text, --对象模式，默认为空
        function_schema text DEFAULT NULL::text, --函数模式，默认为空
        statement_types text DEFAULT 'insert,update,delete,select'::text, --策略作用的语句类型，语句类型使用英文逗号分隔，默认为insert,update,delete,select
        update_check boolean DEFAULT false, --是否启动更新检查，默认为false
        enable boolean DEFAULT true, --是否启用策略
        sec_relevant_cols text DEFAULT NULL:text --敏感列，敏感列使用英文逗号分隔
) RETURN VOID   --返回值为空
```

- 删除VPD策略

```
dbms_rls.drop_policy(
        object_name text, --对象名称
        policy_name text, --策略名称
        object_schema text DEFAULT NULL:text --对象模式，默认为空
) RETURN VOID    --返回值为空
```

- 启动/禁用

```
dbms_rls.enable_policy(
        object_name text, --对象名称
        policy_name text, --策略名称
        enable boolean, --是否启用策略
        object_schema text DEFAULT NULL:text --对象模式，默认为空
) RETURN VOID    --返回值为空
```

**注意事项**

无。

**示例**

1、创建并设置模式为vpd。

```
create schema vpd;
set search_path to vpd;
```

2、创建测试表并插入数据。

```
create table t_vpd (x number);
insert into t_vpd values (1);
insert into t_vpd values (2);
insert into t_vpd values (10001);
insert into t_vpd values (10002);
```

3、创建测试函数。

```
CREATE OR REPLACE FUNCTION vpd.f_limited_query_t(s_schema IN text,s_object IN text)
RETURNS text AS $$
BEGIN
RETURN 'x <= 10000';
END;
$$ LANGUAGE plpgsql;
```

4、添加策略。

```
BEGIN
DBMS_RLS.ADD_POLICY (
object_schema => 'vpd',
object_name => 't_vpd',
policy_name => 'policy_t_vpd_select',
function_schema => 'vpd',
policy_function => 'f_limited_query_t',
statement_types => 'select'
);
END;
/
```

5、启用策略。

```
BEGIN
DBMS_RLS.enable_policy(
object_schema => 'vpd',
object_name => 't_vpd',
policy_name => 'policy_t_vpd_select',
enable => true
);
END;
/
```

6、如下所示：查询会自动带上where条件。

```
explain (COSTS false) select * from t_vpd;
           QUERY PLAN            
---------------------------------
 Seq Scan on t_vpd
   Filter: (x <= 10000::numeric)
(2 rows)
```
