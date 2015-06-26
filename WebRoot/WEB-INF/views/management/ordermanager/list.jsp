<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/orderManager/list" page="${page }" reverseOrderDirection="true">
	<input type="hidden" name="search_LIKE_upccode" value="${param.search_LIKE_upccode}"/>
	<input type="hidden" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}"/>
	<input type="hidden" name="search_LIKE_platformId" value="${param.search_LIKE_platformId}"/>
	<input type="hidden" name="search_LIKE_productName" value="${param.search_LIKE_productName}"/>
	<input type="hidden" name="search_EQ_quater" value="${param.search_EQ_quater}"/>
	<input type="hidden" name="search_EQ_productType" value="${param.search_EQ_productType}"/>

</dwz:paginationForm>
<form id="refreshOrderManagerForm" name="refreshOrderManagerForm" method="post" action="${contextPath }/management/orderManager/list" onsubmit="return navTabSearch(this)" />
<form id="exprotErrorForm" method="post" action="${contextPath }/management/orderManager/exprotErrorInfo">
	<input type="hidden" id="exprotErrorInfoId" name="exprotErrorInfoFileName" /> 
</form>
<form method="post" action="${contextPath }/management/orderManager/list" onsubmit="return navTabSearch(this)">
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
				<li>
				<label>平台产品ID：</label>
					<input type="text" name="search_LIKE_platformId" value="${param.search_LIKE_platformId}" alt="支持模糊查询"/>			
				</li>
			</ul>
			<ul class="searchContent" >
			<li>
				<label>产品名称：</label>
					<input type="text" name="search_LIKE_productName" value="${param.search_LIKE_productName}" alt="支持模糊查询"/>			
				</li>
		
				<li>
				<label>季节：</label>
				<select name="search_EQ_quater" class="select">
					<option value="">请选择</option>
					<c:if test="${not empty QUATERS}">
						<c:forEach var="item" items="${QUATERS}">
							<option value="${item.quater}" <c:if test="${item.quater eq  param.search_EQ_quater}"> selected="selected"</c:if>>${item.quater}</option>
						</c:forEach>
					</c:if>
				</select>			
				</li>
				<li>
				<label>类别：</label>	
					<select name="search_EQ_productType" class="select">
					<option value="">请选择</option>
					<c:if test="${not empty PRODUCT_TYPES}">
						<c:forEach var="item" items="${PRODUCT_TYPES}">
							<option value="${item.productType}" <c:if test="${item.productType eq  param.search_EQ_productType}"> selected="selected"</c:if>>${item.productType}</option>
						</c:forEach>
					</c:if>
				</select>
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
			<shiro:hasPermission name="OrderFormInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" max="true" width="530" height="250" rel="OrderFormInfo_view" href="${contextPath }/management/orderManager/view/{slt_uid}"><span>查看订货信息</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="OrderFormInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" max="true" height="250" rel="OrderFormInfo_save" href="${contextPath }/management/orderManager/create"><span>添加订货信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="OrderFormInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" max="true" height="250" rel="OrderFormInfo_edit" href="${contextPath }/management/orderManager/update/{slt_uid}"><span>编辑订货信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="OrderFormInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/orderManager/delete" title="确认要删除选定记录吗?"><span>删除订货信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="OrderFormInfo:upload">
				<li><a iconClass="page_excel" target="dialog" mask="true"  ref="orderManager_upload"  href="${contextPath }/management/orderManager/openUploadPage" ><span>数据导入</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="OrderFormInfo:upload">
				<li><a iconClass="page_white_put" target="dialog" mask="true"  href="${contextPath }/management/orderManager/templateDownload" ><span>模版下载</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="OrderFormInfo:export">
				<li><a iconClass="page_white_get" target="dwzExport" targetType="navTab" href="${contextPath }/management/orderManager/dataExport"  title="确认要导出这些记录吗?"><span>数据导出</span></a>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<!-- <div layoutH="140"  style="overflow-x:scroll;width:100%;height:100%"> -->
	<table class=table layoutH="162" width="1600">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="50">69码</th>
				<th width="50" orderField="materialNumber" class=${page.orderField eq "materialNumber" ? page.orderDirection eq "desc" ? "asc": "desc"  :"asc"} >款号</th>
				<th width="100">平台产品ID</th>
				<th width="300">产品名称</th>
				<th width="50">季节</th>
				<th width="50">类型</th>
				<th width="50" orderField="tagPrice" class=${page.orderField eq "tagPrice" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"} >市场价</th>
				<th width="80" orderField="orderNumber" class=${page.orderField eq "orderNumber" ? page.orderDirection eq "desc" ? "asc": "desc"  : "desc"}>订货数量</th>
				<th width="80" orderField="orderAmount" class=${page.orderField eq "orderAmount" ? page.orderDirection eq "desc" ? "asc": "desc"  : "desc"}>订货金额</th>
				<th width="80" orderField="arrivalNumber" class=${page.orderField eq "arrivalNumber" ? page.orderDirection eq "desc" ? "asc": "desc"  : "desc"}>到货数量</th>
				<th width="100" id="title_currentStockNumber_id" orderField="currentStockNumber" class=${page.orderField eq "currentStockNumber" ? page.orderDirection eq "desc" ? "asc": "desc" :  "desc"} >现有库存</th>
				<th width="100" id="title_availableStockNumber_id" orderField="availableStockNumber" class=${page.orderField eq "availableStockNumber" ? page.orderDirection eq "desc" ? "asc": "desc" :   "desc"} >可用库存</th>
				<th width="100" orderField="availableStockMoney" class=${page.orderField eq "availableStockMoney" ? page.orderDirection eq "desc" ? "asc": "desc"  : "desc"} >可用库存金额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${orderFormInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.upccode}</td>
				<td>${item.materialNumber}</td>
				<td>${item.platformId}</td>
				<td>${item.productName}</td>
				<td>${item.quater}</td>
				<td>${item.productType}</td>
				<td>${item.tagPrice}</td>
				<td>${item.orderNumber}</td>
				<td>${item.orderAmount}</td>
				<td>${item.arrivalNumber}</td>
				<td>${item.currentStockNumber}</td>
				<td>${item.availableStockNumber}</td>
				<td>${item.availableStockMoney}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- </div>-->
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
<script type="text/javascript">
$(document).ready(function(){
 setTimeout(function(){
	$("#title_currentStockNumber_id").attr({"title":"公式：(订货数量-转仓数量-退货数量-调货数量-样品数量)+原库存"});
	$("#title_currentStockNumber_id>div").attr({"title":"公式：(订货数量-转仓数量-退货数量-调货数量-样品数量)+原库存"});
	$("#title_availableStockNumber_id").attr({"title":"公式：(到货数量-转仓数量-退货数量-调货数量-样品数量)+原库存"});
	$("#title_availableStockNumber_id>div").attr({"title":"公式：(到货数量-转仓数量-退货数量-调货数量-样品数量)+原库存"});
	}, 100);
});
</script>