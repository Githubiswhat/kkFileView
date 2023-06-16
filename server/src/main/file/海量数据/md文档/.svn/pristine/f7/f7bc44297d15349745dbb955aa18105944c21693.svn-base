#### ALL_TAB_COLS

ALL_TAB_COLS记录了用户所能访问的所有表字段相关的信息。

<table>
<tr>
<th>列名</th>
<th>类型</th>
<th>描述</th>
</tr>
<tr>
<td>owner</td>
<td>name</td>
<td>表、视图或者集群的所有者</td>
</tr>
<tr>
<td>table_name</td>
<td>name</td>
<td>表、视图或集群的名称</td>
</tr>
<tr>
<td>column_name</td>
<td>name</td>
<td>列名</td>
</tr>
<tr>
<td>data_type</td>
<td>text</td>
<td>列的数据类型</td>
</tr>
<tr>
<td> data_type_mod </td>
<td>text</td>
<td>列的数据类型修饰符</td>
</tr>
<tr>
<td> data_type_owner</td>
<td>name</td>
<td>列的数据类型所有者</td>
</tr>
<tr>
<td> data_length</td>
<td>integer</td>
<td>列长度（以字节为单位）</td>
</tr>
<tr>
<td> data_precision</td>
<td>integer</td>
<td>NUMBER数据类型的十进制精度</br>
FLOAT数据类型的二进制精度</br>
所有其他数据类型为NULL</td>
</tr>
<tr>
<td>data_scale</td>
<td>integer</td>
<td>数字中小数点右侧的数字</td>
</tr>
<tr>
<td>nullable</td>
<td>text</td>
<td>指示列是否允许NULL。值是指列上N是否存在NOT NULL约束，或者列是否为a PRIMARY KEY的一部分</td>
</tr>
<tr>
<td>column_id </td>
<td>smallint</td>
<td>创建列的序号</td>
</tr>
<tr>
<td>default_length</td>
<td>text</td>
<td>列的默认长度</td>
</tr>
<tr>
<td>data_default </td>
<td>text</td>
<td>列的默认值</td>
</tr>
<tr>
<td>num_distinct </td>
<td>real</td>
<td>列中的不同值的数量</td>
</tr>
<tr>
<td>low_value </td>
<td>text</td>
<td>列中低的值</td>
</tr>
<tr>
<td>high_value</td>
<td>text</td>
<td>列中高的值</td>
</tr>
<tr>
<td>density</td>
<td>double precision</td>
<td>如果柱状图在COLUMN_NAME上可用，则此列显示值的选择性，该值跨越柱状图中少于2个端点。它不代表跨越2个或多个端点的值的选择性。</td>
</tr>
<tr>
<td>num_nulls</td>
<td>text</td>
<td>列中NULL的数量</td>
</tr>
<tr>
<td>num_buckets</td>
<td>text</td>
<td>列的直方图中的桶的数量</td>
</tr>
<tr>
<td> last_analyzed </td>
<td>text</td>
<td>分析此列的最近的日期</td>
</tr>
<tr>
<td>SAMPLE_SIZE</td>
<td>text</td>
<td>用于分析此列的样本量</td>
</tr>
<tr>
<td>CHARACTER_SET_NAME</td>
<td>text</td>
<td>字符集的名称（CHAR_CS，NCHAR_CS）</td>
</tr>
<tr>
<td>char_col_decl_length</td>
<td>integer</td>
<td>字符类型列的声明长度</td>
</tr>
<tr>
<td> global_stats </td>
<td>text</td>
<td>如果收集统计数据，或者进行增量维护，则GLOBAL_STATS为YES，否则将为NO</td>
</tr>
<tr>
<td>user_stats </td>
<td>text</td>
<td>指示统计信息是否由用户直接输入（YES）或（NO）</td>
</tr>
<tr>
<td> avg_col_len </td>
<td>integer</td>
<td>列的平均长度（以字节为单位）</td>
</tr>
<tr>
<td>CHAR_LENGTH</td>
<td>integer</td>
<td>以字符的形式显示列的长度，使用的数据类型：CHAR、VARCHAR2、NCHAR、NVARCHAR2</td>
</tr>
<tr>
<td> char_used</td>
<td>text</td>
<td>表示列使用字节长度语义（B）或字符长度语义（C），或者数据类型是否不是以下任何一种（NULL）：CHAR、VARCHAR2、NCHAR、NVARCHAR2</td>
</tr>
<tr>
<td>v80_fmt_image</td>
<td>text</td>
<td>指示列数据是否为8.0版图像格式（YES）或否（NO）</td>
</tr>
<tr>
<td>data_upgraded</td>
<td>text</td>
<td>指示列数据是否已升级为最新类型版本格式（是）或未升级（否）</td>
</tr>
<tr>
<td>hidden_column </td>
<td>text</td>
<td>指示列是否是隐藏列，是（YES）或者否（NO）</td>
</tr>
<tr>
<td>virtual_column</td>
<td>text</td>
<td>指示列是否是虚拟列，是（YES）或者否（NO）</td>
</tr>
<tr>
<td>segment_column_id</td>
<td>text</td>
<td>列的序列号</td>
</tr>
<tr>
<td>internal_column_id</td>
<td>smallint</td>
<td>列的内部序列号</td>
</tr>
<tr>
<td>histogram</td>
<td>text</td>
<td>表示直方图存在的类型：NONE、FREQUENCY、TOP-FREQUENCY、HEIGHT BALANCED、HYBRID</td>
</tr>
<tr>
<td>qualified_col_name</td>
<td>name</td>
<td>限定列的名称</td>
</tr>
</table>