#### PRODUCT_COMPONENT_VERSION

兼容Oracle静态数据字典PRODUCT_COMPONENT_VERSION，其中包含组件产品的版本和状态信息。

可以使用如下查询语句进行查询：

```
select * from PRODUCT_COMPONENT_VERSION;
```

<table>
<tr>
<th>列名</th>
<th>类型</th>
<th>描述</th>
</tr>
<tr>
<td>PRODUCT</td>
<td>text</td>
<td>产品名称</td>
</tr>
<tr>
<td>VERSION</td>
<td>text</td>
<td>版本号</td>
</tr>
<tr>
<td>STATUS</td>
<td>text</td>
<td>发布状态</td>
</tr>
</table>