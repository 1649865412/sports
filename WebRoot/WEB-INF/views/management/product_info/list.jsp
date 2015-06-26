<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/productInfo/list" page="${page }" reverseOrderDirection="true" name="productInfoFormName">
	<input type="hidden" name="search_LIKE_productUpccode" value="${param.search_LIKE_productUpccode}"/>
	<input type="hidden" name="search_GTE_tagPrice" value="${param.search_GTE_tagPrice}"/>
	<input type="hidden" name="search_LTE_tagPrice" value="${param.search_LTE_tagPric}"/>
	<input type="hidden" name="search_EQ_productSex" value="${param.search_EQ_productSex}"/>
	<input type="hidden" name="search_EQ_series" value="${param.search_EQ_series}"/>
	<input type="hidden" name="search_EQ_productType" value="${param.search_EQ_productType}"/>
	<input type="hidden" name="search_LIKE_productName" value="${param.search_LIKE_productName}"/>
	<input type="hidden" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}"/>
	<input type="hidden" name="exportSelectFieldsName" id="exportSelectFieldsId"/>
</dwz:paginationForm>
<form action="${contextPath }/management/productInfo/list" name="refreshProductInfoForm" id="refreshProductInfoForm" method="post" onsubmit="return navTabSearch(this)"/>  
<form id="exprotErrorForm" method="post" action="${contextPath }/management/productInfo/exprotErrorInfo">
	<input type="hidden" id="exprotErrorInfoId" name="exprotErrorInfoFileName" /> 
</form>
<form method="post" action="${contextPath }/management/productInfo/list" onsubmit="return validateCustomBase(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>69码</label>
					<input type="text" name="search_LIKE_productUpccode" value="${param.search_LIKE_productUpccode}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>款号：</label>
					<input type="text" name="search_LIKE_materialNumber" value="${param.search_LIKE_materialNumber}" alt="支持模糊查询"/>			
				</li>
				<li>
					<label>吊牌价：</label> 
					<input type="text" name="search_GTE_tagPrice" class="input-medium validate[custom[number]]" value="${param.search_GTE_tagPrice}" style="width: 80px"/>~
					<input type="text" name="search_LTE_tagPrice" class="input-medium validate[custom[number]]"   value="${param.search_LTE_tagPric}" style="width: 80px" />
				</li>	
			
			</ul>
			<ul class="searchContent">
				<li>
					<label>系列：</label>
					<select name="search_EQ_series" class="select">
					<option value="">请选择</option>
					<c:if test="${not empty SERIES}">
						<c:forEach var="item" items="${SERIES}">
							<option value="${item.series}" <c:if test="${item.series eq  param.search_EQ_series}"> selected="selected"</c:if>>${item.series}</option>
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
				<li>
					<label>产品名称：</label>
					<input type="text" name="search_LIKE_productName" value="${param.search_LIKE_productName}" alt="支持模糊查询"/>			
				</li>
			</ul>
			<ul class="searchContent">
					<li>
				 	<label>性别：</label> 
				 	<select name="search_EQ_productSex" class="select input-medium">
				 	<option value="">请选择</option>
					<option value="男" <c:if test="${param.search_EQ_productSex eq '男'}">selected="selected"</c:if>>男</option>
					<option value="女" <c:if test="${param.search_EQ_productSex eq '女'}">selected="selected"</c:if>>女</option>
					<option value="中性" <c:if test="${param.search_EQ_productSex eq '中性'}">selected="selected"</c:if>>中性</option>
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
			<shiro:hasPermission name="ProductInfo:view">
				<li><a iconClass="magnifier" target="dialog" max="true" mask="true" width="530" height="250" rel="ProductInfo_view" href="${contextPath }/management/productInfo/view/{slt_uid}"><span>查看产品信息</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="ProductInfo:save">
				<li><a class="add" target="dialog" mask="true" max="true" width="530" height="250" rel="ProductInfo_save" href="${contextPath }/management/productInfo/create"><span>添加产品信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProductInfo:edit">
				<li><a class="edit" target="dialog" mask="true" max="true" width="530" height="250" rel="ProductInfo_edit" href="${contextPath }/management/productInfo/update/{slt_uid}"><span>编辑产品信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProductInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/productInfo/delete" title="确认要删除选定?"><span>删除产品信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProductInfo:upload">
				<li><a iconClass="page_excel" target="dialog" mask="true"  ref="ProductInfo_upload"  href="${contextPath }/management/productInfo/upload?requestUrl=parseData" ><span>数据导入</span></a></li>
				<input type="hidden"  target="request_url" ref="parseData"/> 
			</shiro:hasPermission>
			<shiro:hasPermission name="ProductInfo:upload">
				<li><a iconClass="page_white_put"  href="${contextPath }/ExcelModel/product/productinfo_model.xlsx" ><span>模版下载</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ProductInfo:export">
				<li><a iconClass="page_white_get" target="dialog" mask="true"  href="${contextPath }/management/productInfo/preDataExport"  title="请选择需要导出的字段"><span>数据导出</span></a>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="187" width="1600"> 
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="200">产品名称</th>
				<th width="80">69码</th>
				<th width="120" orderField="materialNumber" class=${page.orderField eq "materialNumber" ? page.orderDirection eq "desc" ? "asc": "desc"  :"asc"} >款号</th>
				<th width="120">平台ID</th>
				<th width="90">品牌</th>
				<th width="80"  orderField="tagPrice" class=${page.orderField eq "tagPrice" ? page.orderDirection eq "desc" ? "asc": "desc" : "desc"}>吊牌价</th>
				<th width="80">上市月份</th>
				<th width="120">上架日期</th>
				<th width="80">产地</th>
				<th width="80">性别</th>
				<th width="80">季节</th>
				<th width="80">系列</th>
				<th width="80">类别</th>
				<th width="80">图片</th>
				<th width="80">Inline/2ndline</th>
				<th width="80">材质</th>
				<th width="80">商品定位</th>
				<th width="80">功能</th>
				<th width="80">颜色</th>
				<th width="80">尺码</th>
				<th width="80">属性</th>
				<th width="80">鞋底</th>
				<th width="80">色号</th>
				<th width="80">系统编号</th>
				<th width="80">重量</th>
				<th width="80">LF/PF</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${productInfos}">
			<tr target="slt_uid" rel="${item.id}" style="height: 40px">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.productName}</td>
				<td>${item.productUpccode}</td>
				<td>${item.materialNumber}</td>
				<td>${item.productPlatformId}</td>
				<td>${item.productBrand}</td>
				<td>${item.tagPrice}</td>
				<td>${item.onMarketMonth}</td>
				<td>${item.onSalesTime}</td>
				<td>${item.productPlace}</td>
				<td>${item.productSex}</td>
				<td>${item.quater}</td>
				<td>${item.series}</td>
				<td>${item.productType}</td>
				<td id="showImageId" name="showImageName"><c:if test="${ not empty item.image }"><img  alt="图片不存在" src="${contextPath}${item.image}" style="width: 80px;height: 40px; " /></c:if>
				<c:if test="${ empty item.image }">&nbsp;</c:if></td>
				<td>${item.inlineOr2ndline}</td>
				<td>${item.materialQuality}</td>
				<td>${item.productPosition}</td>
				<td>${item.productFunction}</td>
				<td>${item.productColor}</td>
				<td>${item.productSize}</td>
				<td>${item.productAttribute}</td>
				<td>${item.shoesBottom}</td>
				<td>${item.colorId}</td>
				<td>${item.systemId}</td>
				<td>${item.productWeight}</td>
				<td>${item.productLfPf}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>

<script type="text/javascript" defer="defer">
$(document).ready(function(){
	setTimeout(function(){
		$("td[name='showImageName']").each(function(){	
		 	var tdHtml = $(this).find("div").html();
			$(this).find("div").remove();
			$(this).html(tdHtml);
		 });
		 
		//$("#showImageId div").remove(); 
		$("#showImageId img").css({"width":$("#showImageId").width()});
	}, 100);
});
</script>