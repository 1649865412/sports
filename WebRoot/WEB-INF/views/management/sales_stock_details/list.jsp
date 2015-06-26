<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/salesStockDetails/list" page="${page }" name="salesStockDetailsFormName" reverseOrderDirection="true">
	<input type="hidden" name="search_LIKE_upccode" value="${param.search_LIKE_upccode}"/>
	<input type="hidden" name="search_LTE_salesTime" value="${param.search_LTE_salesTime}"/>
	<input type="hidden" name="search_GTE_salesTime" value="${param.search_GTE_salesTime}"/>
	<input type="hidden" name="exportSelectFieldsName"  id="exportSelectFieldsId"/>
	<input type="hidden" name="search_LIKE_materialNumber"  id="${param.search_LIKE_materialNumber}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/salesStockDetails/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>69码：</label>
					<input type="text" name="search_LIKE_upccode" value="${param.search_LIKE_upccode}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>款号：</label>
					<input type="text" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}"  alt="支持模糊查询"/>			
				</li>
				<li>
					<label>销售时间：</label>
					<input type="text" name="search_GTE_salesTime"   datefmt="yyyy-MM-dd" value="${param.search_GTE_salesTime}" class="input-medium date" readonly="readonly" style="width: 80px;"/>
					<input type="text" name="search_LTE_salesTime"  datefmt="yyyy-MM-dd" value="${param.search_LTE_salesTime}" class="input-medium date" readonly="readonly" style="width: 80px;"/>
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
			<shiro:hasPermission name="SalesStockDetails:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" max="true" width="530" height="250"  rel="SalesStockDetails_view" href="${contextPath }/management/salesStockDetails/view/{slt_uid}"><span>查看销售信息</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="SalesStockDetails:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="SalesStockDetails_save" href="${contextPath }/management/salesStockDetails/create"><span>添加销售信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="SalesStockDetails:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="SalesStockDetails_edit" href="${contextPath }/management/salesStockDetails/update/{slt_uid}"><span>编辑销售信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="SalesStockDetails:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/salesStockDetails/delete" title="确认要删除选定销售信息?"><span>删除销售信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="SalesStockDetails:export">
				<li><a iconClass="page_white_get" target="dialog" mask="true" href="${contextPath }/management/salesStockDetails/preDataExport"  title="请选择需要导出的字段"><span>数据导出</span></a>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="1600">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>69码</th>
				<th width="100px" orderField="materialNumber" class=${page.orderField eq "materialNumber" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>款号</th>
				<th width="100px" orderField="salesTime" class=${page.orderField eq "salesTime" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>销售时间</th>
				<th width="80px" orderField="salesNumber" class=${page.orderField eq "salesNumber" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>销售数量</th>
				<th width="100px" orderField="avgCurrentPrice" class=${page.orderField eq "avgCurrentPrice" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>成交价</th>
				<th width="80px" orderField="salesAmount" class=${page.orderField eq "salesAmount" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>销售金额</th>
				<th width="60px">仓库</th>
				<th width="80px" orderField="enteringTime" class=${page.orderField eq "enteringTime" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>入库时间</th>
				<th width="80px" orderField="orderNumber" class=${page.orderField eq "orderNumber" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>订货量</th>
				<th width="80px" orderField="stockNumber" class=${page.orderField eq "stockNumber" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>库存量</th>
				<th width="80px" orderField="arriveNumber" class=${page.orderField eq "arriveNumber" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>到货量</th>
				<th width="100px" orderField="predictArriveTime" class=${page.orderField eq "predictArriveTime" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>预计到货期</th>
				<th width="120px" orderField="factArriveTime" class=${page.orderField eq "factArriveTime" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>实际到货时间</th>
				<th width="60px">备注</th>
				<th width="200px">数据更新时间</th> 
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${salesStockDetailss}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.upccode}</td>
				<td>${item.materialNumber}</td>
				<td>${item.salesTime}</td>
				<td>${item.salesNumber}</td>
				<td>${item.avgCurrentPrice}</td>
				<td>${item.salesAmount}</td>
				<td>${item.storeHouse}</td>
				<td>${item.enteringTime}</td>
				<td>${item.orderNumber}</td>
				<td>${item.stockNumber}</td>
				<td>${item.arriveNumber}</td>
				<td>${item.predictArriveTime}</td>
				<td>${item.factArriveTime}</td>
				<td>${item.remarks}</td>
				<td>${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>