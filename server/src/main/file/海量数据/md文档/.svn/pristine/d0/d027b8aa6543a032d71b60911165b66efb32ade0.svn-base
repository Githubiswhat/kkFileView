# 事务ID和快照数据类型txid_snapshot

## 功能描述

Vastbase通过txid_snapshot类型记录事务ID和快照信息。

## 语法格式

txid_snapshot的文本表示为：

```
xmin:xmax:xip_list
```

## 参数解释

<table>
    <tr>
    <th>名称</th>
    <th>描述</th>
    </tr>
    <tr>
    <td>xmin</td>
    <td>仍然活动的最早的事务ID（txid）。所有较早事务将是已经提交可见的，或者是直接回滚。</td>
    </tr>
    <tr>
    <td>xmax</td>
    <td>作为尚未分配的txid。所有大于或等于此txids的都是尚未开始的快照时间，因此不可见。</td></tr>
    <tr>
    <td>xip_list</td>
    <td>当前快照中活动的txids。这个列表只包含在xmin和xmax之间活动的txids；有可能活动的txids高于xmax。介于大于等于xmin、小于xmax，并且不在这个列表中的txid，在这个时间快照已经完成的，因此按照提交状态查看他是可见还是回滚。这个列表不包含子事务的txids。
</td></tr>
</table>

## **示例**

获取当前快照

```
select txid_current_snapshot(); 
```

结果返回如下：

```
 txid_current_snapshot
-----------------------
 25091:25101:
(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> txid_current_snapshot()函数返回类型为txid_snapshot。