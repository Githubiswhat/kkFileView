#####  Synonym词典

**功能描述**

Synonym词典用于定义、识别token的同义词并转化，不支持词组（词组形式的同义词可用Thesaurus词典定义，详细请参见章节“Thesaurus词典”）。

**示例**

- 1、Synonym词典可用于解决语言学相关问题，例如，为避免使单词"Paris"变成"pari"，可在Synonym词典文件中定义一行"Paris paris"，并将该词典放置在预定义的english_stem词典之前。

```
SELECT * FROM ts_debug('english', 'Paris'); 
```

结果返回如下：  

```
alias  |  description  | token |  dictionaries  |  dictionary  | lexemes  
-----------+-----------------+-------+----------------+--------------+--------- 
 asciiword | Word, all ASCII | Paris | {english_stem} | english_stem | {pari} 
(1 row) 
```

2、创建词典，FILEPATH以dicts文件实际目录为准。

```
CREATE TEXT SEARCH DICTIONARY my_synonym ( 
  TEMPLATE = synonym, 
  SYNONYMS = my_synonyms, 
  FILEPATH = 'file:///home/dicts/'  
); 
```

3、修改词典。

```
 ALTER TEXT SEARCH CONFIGURATION english 
  ALTER MAPPING FOR asciiword 
  WITH my_synonym, english_stem; 
```

4、 查询验证。

```
SELECT * FROM ts_debug('english', 'Paris'); 
```

结果显示如下：

```
  alias  |  description  | token |    dictionaries     | dictionary | lexemes  
-----------+-----------------+-------+---------------------------+------------+--------- 
 asciiword | Word, all ASCII | Paris | {my_synonym,english_stem} | my_synonym | {paris} 
(1 row) 
```

5、 查询验证。

```
SELECT * FROM ts_debug('english', 'paris'); 
```

结果显示如下：

```
 alias  |  description  | token |    dictionaries     | dictionary | lexemes  
-----------+-----------------+-------+---------------------------+------------+--------- 
 asciiword | Word, all ASCII | Paris | {my_synonym,english_stem} | my_synonym | {paris} 
(1 row) 
```

6、修改词典。

```
ALTER TEXT SEARCH DICTIONARY my_synonym ( CASESENSITIVE=true); 
```

7、查询验证。

```
SELECT * FROM ts_debug('english', 'Paris'); 
```

结果显示如下：

```
  alias  |  description  | token |    dictionaries     | dictionary | lexemes  
-----------+-----------------+-------+---------------------------+------------+--------- 
 asciiword | Word, all ASCII | Paris | {my_synonym,english_stem} | my_synonym | {paris} 
(1 row) 
```

8、查询验证 。

```
SELECT * FROM ts_debug('english', 'paris'); 
```

结果显示如下：

```
  alias  |  description  | token |    dictionaries     | dictionary | lexemes  
----------+-----------------+-------+---------------------------+------------+--------- 
 asciiword | Word, all ASCII | Paris | {my_synonym,english_stem} | my_synonym | {pari} 
(1 row) 
```

 

其中，同义词词典文件全名为my_synonyms.syn，所在目录为当前连接数据库主节点的/home/dicts/（以实际路径为准）下。关于创建词典的语法和更多参数，请参见章节“ALTER TEXT SEARCH DICTIONARY”。

- 星号（*）可用于词典文件中的同义词结尾，表示该同义词是一个前缀。在to_tsvector()中该星号将被忽略，但在to_tsquery()中会匹配该前缀并对应输出结果（参照章节“处理查询”）。

假设词典文件synonym_sample.syn内容如下：

```
postgres     pgsql 
postgresql    pgsql  
postgre pgsql  
gogle  googl  
indices index*
```

1、创建并使用词典。

```
CREATE TEXT SEARCH DICTIONARY syn ( 
  TEMPLATE = synonym, 
  SYNONYMS = synonym_sample 
); 
```

2、验证结果。

```
SELECT ts_lexize('syn','indices'); 
```

结果返回如下：

```
 ts_lexize  
----------- 
 {index} 
(1 row) 
```

3、创建词典并修改。

```
CREATE TEXT SEARCH CONFIGURATION tst (copy=simple); 
ALTER TEXT SEARCH CONFIGURATION tst ALTER MAPPING FOR asciiword WITH syn; 
```

4、验证结果。

```
SELECT to_tsvector('tst','indices'); 
```

结果返回如下： 

```
to_tsvector  
------------- 
 'index':1 
(1 row) 

 
```

```
 SELECT to_tsquery('tst','indices'); 
```

结果返回如下：

```
 to_tsquery  
------------ 
 'index':* 
(1 row) 
```

```
SELECT 'indexes are very useful'::tsvector; 
```

结果返回如下：

```
      tsvector        
--------------------------------- 
 'are' 'indexes' 'useful' 'very' 
(1 row) 
```

```
SELECT 'indexes are very useful'::tsvector @@ to_tsquery('tst','indices'); 
```

结果返回如下：

```
 ?column?  
---------- 
 t 
(1 row)
```
