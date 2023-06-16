#### V$TABLESPACE

V$TABLESPACE包含当前实例表空间的信息。

| 列名                        | 类型   | 描述                               |
| --------------------------- | ------ | ---------------------------------- |
| ts#                         | bigint | 表空间编号                         |
| name                        | name   | 表空间名称                         |
| included_in_database_backup | text   | 该表空间是否可以包含在备份集       |
| bigfile                     | text   | 是否开启大文件                     |
| flashback_on                | text   | 是否参与到flashback database操作中 |
| encrypt_in_backup           | text   | 该表空间是否为加密                 |
