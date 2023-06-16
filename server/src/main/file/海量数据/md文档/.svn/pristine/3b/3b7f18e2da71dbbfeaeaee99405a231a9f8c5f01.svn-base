# PG_EVENT_TRIGGER

Vastbase G100 V2.2 Build 12 版本中新增PG_EVENT_TRIGGER系统表，用于维护事件触发器的元信息。

<table>
    <th>名称</th>
    <th>类型</th>
    <th>引用</th>
    <th>描述</th>
    <tr>
    <td>event</td>
    <td>name</td>
    <td>-</td>
    <td>触发器名称（必须唯一）。</td>
    </tr>
    <tr>
    <td>evtevent</td>
    <td>name</td>
        <td>-</td>
        <td>触发器触发事件的标识符。</td>
    </tr>
    <tr>
    	<td>evtowner</td>
        <td>oid</td>
        <td>pg_authid_oid</td>
        <td>事件触发器的拥有者。</td>
    </tr>
    <tr>
    	<td>evtfoid</td>
        <td>oid</td>
        <td>pg_proc_oid</td>
        <td>将被调用的函数。</td>
    </tr>
    <tr>
    	<td>evtenabled</td>
        <td>char</td>
        <td>-</td>
        <td>控制事件触发器触发的会话复制角色模式。
        <li>0：触发器在"origin"和"local"模式触发。</li>
        <li>D：触发器被禁用。</li>
        <li>R：触发器在"replica"模式触发。</li>
        <li>A：触发器总是触发。</li></td>
    </tr>
    <tr>
    	<td>evttags</td>
        <td>text[]</td>
        <td>-</td>
        <td>触发器将触发的命令标签。如果为空，此触发器的触发不受命令标签的限制。</td>
    </tr>
</table>



