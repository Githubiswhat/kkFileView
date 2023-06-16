#### PGroonga插件

**功能描述**

PGroonga是一个全文检索插件，Vastbase G100已经内置了 PGroonga扩展，您只需要创建扩展，即可使用PGroonga支持的语言进行超高速全文检索。

**语法格式**

1、使用pgroonga()创建单字段全文索引。

```
CREATE  INDEX  pgroonga_index  ON  memos  USING  pgroonga(content1）;
```

- 使用&@操作符进行全文检索。

```
SELECT * FROM memos WHERE content &@ '全文搜索关键字';
```

- 使用&@~操作符执行含pgroonga查询语法的全文检索。

```
SELECT * FROM memos WHERE content2 &@~ 'PGroonga OR PostgreSQL';
```

- 使用like操作符（数据库本身的一种查询语法，区分大小写）执行全文检索。

```
SELECT * FROM memos WHERE content2 LIKE '%全文搜索关键字%';
```

- 使用ilike操作符（数据库本身的一种查询语法，不区分大小写）执行全文检索。

```
SELECT * FROM memos WHERE content2 ILIKE '%全文搜索关键字%';
```

2、使用pgroonga()创建多字段全文索引。

```
CREATE INDEX pgroonga_index_1 ON memos USING pgroonga(content1, content2);
```

- 使用&@操作符进行多字段全文检索。

```
SELECT * FROM memos WHERE content1 &@ '全文搜索关键字' and content2 &@ '全文搜索关键字';
```

- 使用like操作符（数据库本身的一种查询语法，区分大小写）执行多字段全文检索。

```
SELECT * FROM memos WHERE content1 like '%全文搜索关键字%' and content2 like '%全文搜索关键字%';
```

- 使用ilike操作符（数据库本身的一种查询语法，不区分大小写）执行多字段全文检索。

```
SELECT * FROM memos WHERE content1 ilike '%全文搜索关键字%' and content2 ilike '%全文搜索关键字%';
```

使用pgroonga_vacuum()函数清理临时数据。

```
select pgroonga_vacuum();
```

 

**示例**

1、创建插件。

修改postgresql.conf中配置enable_pgroonga=true。

```
CREATE EXTENSION pgroonga;
```

2、关闭全表扫描，为了在少量数据下体现索引效果。

```
set enable_seqscan=off;
```

3、创建表，并插入数据。

```
CREATE TABLE memos (
id integer,
content text
);
INSERT INTO memos VALUES (1, 'PostgreSQL is a relational database management system.');
INSERT INTO memos VALUES (2, 'Groonga is a fast full text search engine that supports all languages.');
INSERT INTO memos VALUES (3, 'PGroonga is a PostgreSQL extension that uses Groonga as index.');
INSERT INTO memos VALUES (4, 'There is groonga command.');
```

4、创建全文索引。

```
CREATE INDEX idxA ON memos USING pgroonga (content pgroonga_text_full_text_search_ops_v2);
```

查看执行计划。

```
explain select * from memos where content LIKE '%groonga%';
```

结果显示如下：

```
                        QUERY PLAN                             
-------------------------------------------------------------------
 Index Scan using idxa on memos  (cost=0.00..4.01 rows=1 width=36)
   Index Cond: (content ~~ '%groonga%'::text)
(2 rows)
```

5、查看表中内容。

```
select * from memos where content LIKE '%groonga%';
```

结果显示如下：

```
 id |          content          
----+---------------------------
  4 | There is groonga command.
(1 row)
```

使用ILIKE查询。

```
select * from memos where content ILIKE '%roonga%';
```

结果显示如下：

```
 id | content 
----+---------
(0 rows)
```

5、查询验证。

- 使用&@操作符。

```
SELECT * FROM memos WHERE content &@ 'languages';
```

结果显示如下：

```
id | content
----+------------------------------------------------------------------------
2 | Groonga is a fast full text search engine that supports all languages.
(1 row)
```

- 使用&@~操作符。

```
SELECT * FROM memos WHERE content &@~ 'PGroonga OR PostgreSQL';
```

结果显示如下：

```
id | content
----+----------------------------------------------------------------
3 | PGroonga is a PostgreSQL extension that uses Groonga as index.
1 | PostgreSQL is a relational database management system.
(2 rows)
```

- 使用like操作符。

```
SELECT * FROM memos WHERE content LIKE '%groonga%';
```

结果显示如下：

```
id | content
----+---------------------------
4 | There is groonga command.
(1 row)
```

- 使用ilike操作符。

```
SELECT * FROM memos WHERE content ILIKE '%groonga%';
```

结果显示如下：

```
 id |                                content                                 
----+------------------------------------------------------------------------
  2 | Groonga is a fast full text search engine that supports all languages.
  3 | PGroonga is a PostgreSQL extension that uses Groonga as index.
  4 | There is groonga command.
(3 rows)
```
