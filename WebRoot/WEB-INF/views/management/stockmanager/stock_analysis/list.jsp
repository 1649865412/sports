<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/stockAnalysis/list" page="${page }" name="stockAnalysisFormName" reverseOrderDirection="true">
	<input type="hidden" name="search_EQ_upccode" value="${param.search_EQ_upccode}"/>
	<input type="hidden" name="search_EQ_materialNumber" value="${param.search_EQ_materialNumber}"/>
	<input type="hidden" name="search_GTE_startSalesTime" value="${param.search_GTE_startSalesTime}"/>
	<input type="hidden" name="search_LTE_endSalesTime" value="${param.search_LTE_endSalesTime}"/>
	<input type="hidden" name="search_EQ_productSex" value="${param.search_EQ_productSex}"/>
	<input type="hidden" name="search_EQ_productBrand" value="${param.search_EQ_productBrand}"/>
	<input type="hidden" name="search_EQ_inlineOr2ndline" value="${param.search_EQ_inlineOr2ndline}"/>
	<input type="hidden" name="search_EQ_search_EQ_series" value="${param.search_EQ_search_EQ_series}"/>
	<input type="hidden" name="futureArriveName" id="futureArriveName_value_id"  value="${futureArriveName}"/>
	<input type="hidden" name="factArriveTime" id="factArriveTime_value_id" value="${factArriveTime}"/>
	<input type="hidden" name="group_type" id="group_type_id"/>
	<input type="hidden" name="order_list_value" id="order_list_value_id"/>
	<input type="hidden" name="group_type_index" id="group_type_index_id"/>
</dwz:paginationForm>
<script type="text/javascript">

	function openDialog(query_type)
	{	
		if(!checkParams())
		{
			var openPath = "${contextPath}/management/stockAnalysis/preGoTogroupTypePage?exportType="+query_type;
			$.pdialog.open(openPath, "", (query_type == 1 ? "导出":"预览"),{max:false,mask:true,resizable:true,drawable:true,fresh:true});
		}
	}
	
	function checkParams()
	{	
		var factArriveTime = $("#factArriveTime_id").val(); 
		try
			{
			if($("input[id='futureArriveName_id']:checked").size()>0)
			{ 
				if(factArriveTime.length <=0)
				{
					 alertMsg.error("到货时间不能为空！");
					 return true;
				}
			}
			else if(factArriveTime.length > 0 && $("input[id='futureArriveName_id']:checked").size()<=0)
			{
				alertMsg.error("请选择需要未来到货！");
				return true;
			}
			
			 $('input[id="futureArriveName_id"]:checked').each(function() {
				$("#futureArriveName_value_id").val($(this).val());
			});	
			$("#factArriveTime_value_id").val(factArriveTime);
		}catch(e)
		{
		
			}
		
		return false;
	}

</script>
<form method="post"  action="${contextPath}/management/stockAnalysis/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>69码：</label>
					<input type="text" name="search_EQ_upccode" value="${param.search_EQ_upccode}" />			
				</li>
				<li>
				<label>款号：</label>
					<input type="text" name="search_EQ_materialNumber" value="${param.search_EQ_materialNumber}" />			
				</li>
				<li>
					<label >销售时间：</label>
					<input type="text" name="search_GTE_startSalesTime"  value="${param.search_GTE_startSalesTime}" class="input-medium date" readonly="readonly" style="width: 80px"/>
					~<input type="text" name="search_LTE_endSalesTime"   value="${param.search_LTE_endSalesTime}" class="input-medium date" readonly="readonly" style="width: 80px"/>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
				 	<label>性别：</label> 
				 	<select name="search_EQ_productSex" class="select">
				 	<option value="">请选择</option>
					<option value="男" <c:if test="${param.search_EQ_productSex eq '男'}">selected="selected"</c:if>>男</option>
					<option value="女" <c:if test="${param.search_EQ_productSex eq '女'}">selected="selected"</c:if>>女</option>
					<option value="中性" <c:if test="${param.search_EQ_productSex eq '中性'}">selected="selected"</c:if>>中性</option>
					</select>
			 	</li>
			 	<li>
					<label>品牌：</label>
					<select name="search_EQ_productBrand" class="select">
					<option value="">请选择</option>
					<c:if test="${not empty PRODUCT_BRANDS}">
						<c:forEach var="item" items="${PRODUCT_BRANDS}">
							<option value="${item.productBrand}" <c:if test="${item.productBrand eq  param.search_EQ_productBrand}"> selected="selected"</c:if>>${item.productBrand}</option>
						</c:forEach>
					</c:if>
					</select>
				</li>
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
			</ul>
			<ul class="searchContent">
				<li>
					<label>Inline/2ndline：</label>
					<select name="search_EQ_inlineOr2ndline" class="select">
					<option value="">请选择</option>
					<c:if test="${not empty INLINE_OR2NDLINES}">
						<c:forEach var="item" items="${INLINE_OR2NDLINES}">
							<option value="${item.inlineOr2ndline}" <c:if test="${item.inlineOr2ndline eq  param.search_EQ_inlineOr2ndline}"> selected="selected"</c:if>>${item.inlineOr2ndline}</option>
						</c:forEach>
					</c:if>
				</select>
				</li>
				<li>
					<input type="checkbox" name="futureArriveName" id="futureArriveName_id"  value="true"  <c:if test="${not empty futureArriveName}">checked="checked"</c:if>/> 需要未来到货 
				</li>
				<li>
					<label>到货时间：</label>
					<input type="text" name="factArriveTime" id="factArriveTime_id"  value="${factArriveTime}" datefmt="yyyy-MM" class="input-medium date" readonly="readonly" style="float:left;width: 80px"/>
					<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
				</li>
			</ul>
			<div class="subBar">
			<span style="float:left;padding-top:10px"><font style="font-size: 11px">注：默认查询、导出、预览时间范围为当前时间往前推一个月，如：2014-08-30~2014-09-30</font></span>
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
			<!--<shiro:hasPermission name="StockInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="StockInfo_view" href="${contextPath }/management/stockInfo/view/{slt_uid}"><span>查看StockInfo</span></a></li>
			</shiro:hasPermission>-->
			<li><a iconClass="accept" href="javascript:" title="选择导出选项"><span>选择导出选项：</span></a></li>
			<li>
			<a iconClass="application" href="javascript:" title="导出过滤条件：根据勾选分类(种类,系列,Line,性别,季节,且分类不为空和Null)"><span><input
							type="radio" name="excelType" value="1" id="radioGroup" checked="checked">库存报表</span></a></li>
		<li>
			<a iconclass="page_white_get"    href="javascript:" onclick="openDialog('1')" title="报表导出"><span>导出</span></a>
		 	
		</li>
		<li>
			<a iconClass="user_go"  href="javascript:" onclick="openDialog('2')" title="报表预览"><span>预览</span></a>
		</li>
		</ul>
	</div>
	
	<table class="table" layoutH="187" width="100%">
		<thead>
			<tr>
				<th>69码</th>
				<th orderField="materialNumber" class="${page.orderField eq 'materialNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">款号</th>
				<th>Inline/2ndline</th>
				<th>LF/PF</th>
				<th>品牌</th>
				<th id="title_lastStockNumber_id" title="最新库存量" orderField="lastStockNumber" class="${page.orderField eq 'lastStockNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">库存量</th>
				<th id="title_lastOrderNumber_id" title="最新订货量" orderField="lastOrderNumber" class="${page.orderField eq 'lastOrderNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">订货量</th>
				<th id="title_lastArriveNumber_id" title="最新到货量" orderField="lastArriveNumber" class="${page.orderField eq 'lastArriveNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">到货量</th>
				<th orderField="totalSalesNumber" class="${page.orderField eq 'totalSalesNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">总销量</th>
				<th orderField="enteringTime" class="${page.orderField eq 'enteringTime' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">库存时间</th>
				<th id="title_arrivalRate_id" title="最近一次到货量/最近一次订货数量 " >到货率(%)</th>
				<th id="title_dynamicSalesRate_id" title="选择的销售周期内总销量/最近一次库存" >动销率(%)</th>
				<th id="title_soldRate_id" title="选择的销售周期内总销售数量/最近一次到货数量">售罄率(%)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${analysisInfos}">
			<c:if test="${not empty item.productUpccode}">
				<tr>
					<td title="${item.productUpccode}">${item.productUpccode}</td>
					<td title="${item.materialNumber}">${item.materialNumber}</td>
					<td title="${item.inlineOr2ndline}">${item.inlineOr2ndline}</td>
					<td title="${item.productLfPf}">${item.productLfPf}</td>
					<td title="${item.productBrand}">${item.productBrand}</td>	
					<td title="${item.lastStockNumber}">${item.lastStockNumber}</td>	
					<td title="${item.lastOrderNumber}">${item.lastOrderNumber}</td>	
					<td title="${item.lastArriveNumber}">${item.lastArriveNumber}</td>	
					<td title="${item.totalSalesNumber}">${item.totalSalesNumber}</td>	
					<td title="${item.enteringTime}">${item.enteringTime}</td>	
					<td title="最近一次到货量/最近一次订货数量 ">${item.arrivalRate}</td>	
					<td title="选择的销售周期内总销量/最近一次库存">${item.dynamicSalesRate}</td>	
					<td title="选择的销售周期内总销售数量/最近一次到货数量">${item.soldRate}</td>	
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>

<script type="text/javascript" defer="defer">
$(document).ready(function(){
 setTimeout(function(){
	$("#title_arrivalRate_id>div").attr({"title":"最近一次到货量/最近一次订货数量"});
	$("#title_dynamicSalesRate_id>div").attr({"title":"选择的销售周期内总销量/最近一次库存"});
	$("#title_soldRate_id>div").attr({"title":"选择的销售周期内总销售数量/最近一次到货数量"});
	$("#title_lastStockNumber_id>div").attr({"title":"最新库存量"});
	$("#title_lastOrderNumber_id>div").attr({"title":"最新订货量"});
	$("#title_lastArriveNumber_id>div").attr({"title":"最新到货量"});
	}, 100);
});
</script>
