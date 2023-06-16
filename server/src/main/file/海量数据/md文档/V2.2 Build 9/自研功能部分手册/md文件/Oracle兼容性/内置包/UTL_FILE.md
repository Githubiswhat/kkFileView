### UTL_FILE

**功能描述**

UTL_FILE包是用于提供在存储过程或函数中对系统文件进行读写的功能。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td>fopen</td>
<td>打开一个文件用于输入或输出。</td>
</tr>
<tr>
<td>is_open</td>
<td>检查一个文件句柄是否指向一个打开的文件。</td>
</tr>
<tr>
<td>get_line</td>
<td>从一个打开的文件中读取指定的一行文本。</td>
</tr>
<tr>
<td>get_nextline</td>
<td>获得下一行。</td>
</tr>
<tr>
<td>put</td>
<td>将一个字符串写入到文件。</td>
</tr>
<tr>
<td>put_line</td>
<td>在文件中写入一行，在行的末尾写入一个行终结符。
</td>
</tr>
<tr>
<td>new_line</td>
<td>在文件末尾写入一个行终结符。</td>
</tr>
<tr>
<td>putf</td>
<td>格式化输出过程。</td>
</tr>
<tr>
<td>fflush</td>
<td>将文件缓存刷到物理写入。</td>
</tr>
<tr>
<td>fclose</td>
<td>关闭一个文件。
</td>
</tr>
<tr>
<td>fclose_all</td>
<td>关闭所有打开的文件。</td>
</tr>
<tr>
<td>fremove</td>
<td>删除磁盘上的文件。</td>
</tr>
<tr>
<td>frename</td>
<td>重命名一个已经存在的文件。</td>
</tr>
<tr>
<td>fremove</td>
<td>删除磁盘上的文件。</td>
</tr>
<tr>
<td>fcopy</td>
<td>将一个文件中的内容拷贝到另一个文件中。</td>
</tr>
<tr>
<td>fgetattr</td>
<td>获取文件属性。</td>
</tr>
<tr>
<td>tmpdir</td>
<td>获得临时目录路径。</td>
</tr>
</table>


**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：FGETPOS、FOPEN_NCHAR、FSEEK、GET_LINE_NCHAR、GET_ROW、PUT_NCHAR、PUT_LINE_NCHAR、PUT_row。

**示例**

1、创建主备文件，在操作系统中执行如下命令。

```
echo "111aaa
222bbb
333ccc
444ddd
555eee
666fff
777ggg
888hhh
999iii
101010jjj
111111kkk"  > /tmp/test.txt
```

2、连接数据库，创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

3、在数据库下执行。

```
insert into utl_file.utl_file_dir values ('/tmp');
```

4、在数据库下执行文件复制（utl_file.fcopy是Oracle中的一个“静态参数”，可以设置一个或多个路径。）。

```
select utl_file.fcopy('/tmp', 'test.txt', '/tmp', 'test_cp.txt',3,6);

```

5、查看复制的文件,在shell下执行。

```
cat test_cp.txt
```

返回结果如下，则表示文件复制成功：

```
333ccc
444ddd
555eee
666fff
```

7、连接db_oracle数据库，fgetattr查看文件属性。

```
do language plpgsql $$
declare
ex boolean;
flen int;
bsize int;
begin
-- utl_file.fgetattr('/tmp'::text, 'test.txt'::text, ex, flen, bsize);
select * from utl_file.fgetattr('/tmp'::text, 'test.txt'::text) into ex,flen,bsize;
IF ex THEN
raise notice 'File Exists';
ELSE
raise notice 'File Does Not Exist' ;
END IF;
raise notice 'FileLength: %' , flen;
raise notice 'BlockSize: %',bsize;
end;
$$;
```

返回结果为：

```
NOTICE:  File Exists
NOTICE:  FileLength: 0
NOTICE:  BlockSize: 4096
ANONYMOUS BLOCK EXECUTE

```

8、调用存储过程对文件进行读写。

```
do language plpgsql $$
declare
vInHandle utl_file.file_type;
vOutHandle utl_file.file_type;
vNewLine VARCHAR2(250);
begin
vInHandle := utl_file.fopen('/tmp', 'test.txt','R');
LOOP
BEGIN
select * from utl_file.get_line(vInHandle)into vNewLine ;
raise notice '%', vNewLine;
EXCEPTION
WHEN OTHERS THEN
EXIT;
END;
END LOOP;
vOutHandle := utl_file.fopen('/tmp', 'test_cp.txt', 'w');
perform utl_file.put_line(vOutHandle, 'hello lsxy');
utl_file.fflush(vOutHandle);
utl_file.fclose_all();
end;
$$;
```

9、在shell执行如下命令，查看文件内容。

```
cat /tmp/test_cp.txt
```

返回结果为：

```
hello lsxy
```
