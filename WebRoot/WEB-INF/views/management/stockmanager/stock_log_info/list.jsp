<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/stockLogInfo/list" page="${page }">
		<input type="hidden" name="search_LIKE_upccode" value="${param.search_LIKE_upccode}"/>
			<input type="hidden" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/stockLogInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>69码：</label>
					<input type="text" name="search_LIKE_upccode" value="${param.search_LIKE_upccode}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>款号：</label>
					<input type="text" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}" alt="支持模糊查询"/>			
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<!--<shiro:hasPermission name="StockLogInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="StockLogInfo_view" href="${contextPath }/management/stockLogInfo/view/{slt_uid}"><span>查看StockLogInfo</span></a></li>
			</shiro:hasPermission>-->	
			<shiro:hasPermission name="StockLogInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="StockLogInfo_save" href="${contextPath }/management/stockLogInfo/create"><span>添加StockLogInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="StockLogInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="StockLogInfo_edit" href="${contextPath }/management/stockLogInfo/update/{slt_uid}"><span>编辑StockLogInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="StockLogInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/stockLogInfo/delete" title="确认要删除选定StockLogInfo?"><span>删除StockLogInfo</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>69码</th>
				<th>款号</th>
				<th>现有库存数量</th>
				<th>可用库存数量</th>
				<th>用户名</th>
				<th>用户ID</th>
				<th>IP地址</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${stockLogInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.upccode}</td>
				<td>${item.materialNumber}</td>
				<td>${item.currentStockNumber}</td>
				<td>${item.availableStockNumber}</td>
				<td>${item.userName}</td>
				<td>${item.userId}</td>
				<td>${item.ipAddress}</td>
				<td>${item.remarks}</td>
				<td>${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>