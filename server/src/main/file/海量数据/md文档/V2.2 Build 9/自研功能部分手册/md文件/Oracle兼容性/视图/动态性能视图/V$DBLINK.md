#### V$DBLINK

V$DBLINK包含当前会话在使用dblink时所有链接信息，在事务提交或回滚时清除。

| 列名                  | 类型 | 描述                         |
| --------------------- | ---- | ---------------------------- |
| db_link               | text | 数据库链接的名称             |
| owner_id              | text | 数据库链接uid的所有者        |
| logged_on             | text | 数据库链接当前是否已登录     |
| heterogeneous         | text | 数据库链接是否异构           |
| protocol              | text | 数据库链接的通信协议         |
| open_cursors          | text | 数据库链接是否有打开的游标   |
| in_transaction        | text | 数据库链接当前是否在事务中   |
| update_sent           | text | 数据库链接上是否有更新       |
| commit_point_strength | text | 数据库链接上事务的提交点强度 |
