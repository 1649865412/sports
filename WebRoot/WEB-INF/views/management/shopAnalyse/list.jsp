<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>

<%@ include file="/WEB-INF/views/include.inc.jsp"%>


<style type="text/css">
.fsfbrtl-select {
	width: 60px;
}
</style>

<script type="text/javascript">
	function check() {
		if ($("input[name='excelType']:checked").size() == 0) {
			alert("请选择导出类型！");
		} else {
			dialogCheck()
		}
	}

	function preview() {
		if ($("input[name='excelType']:checked").size() == 0) {
			alert("请选择预览类型！");
		} else {
			getmenu()
		}
	}

	function dialogCheck() {

		var va = $("input[name='excelType']:checked").val();
		if (va == 3) {
			getSubmit("Month", "toMonthTypeSelectExcel", "monthId",
					"请选择月报报表导出图表类型", 1);
		} else if (va == 5 || va == 7) {
			getSubmit("ShopAnalyse", "toGroupSelectExcel", "ShopAnalyseId",
					"销售属性分类与下拉顺充选择（导出）", 1);
		} else if (va == 1 || va == 2) {
			selectTOP_DaySales_Column(1, va);
		} else if (va == 6) {
			$.pdialog
					.open(
							"${contextPath}/management/shopAnalyse/preMonthlyViewList?type=1",
							"preMonthlyViewListId", "请选择分析字段", {
								max : false,
								mask : true,
								resizable : true,
								drawable : true,
								fresh : true
							});

		} else {
			$('#ToExcelForm').submit();
			$("#CheckGetSelect").val("");
		}
	}

	function getmenu() {
		var valueText = $('#ToExcelForm').serialize()
		var path = "${contextPath}/management/shopAnalyse/previewList?"
				+ valueText
		var va = $("input[name='excelType']:checked").val();

		if (va == 3) {
			getSubmit("Month", "toMonthTypeSelectExcel", "monthId", "请选择分析类型",
					2);
		} else if (va == 5 || va == 7) {
			getSubmit("ShopAnalyse", "toGroupSelectExcel", "ShopAnalyseId",
					"销售属性分类与顺充选择（预览）", 2);
		} else if (va == 1 || va == 2) {
			selectTOP_DaySales_Column(2, va);
		} else if (va == 6) {
			$.pdialog
					.open(
							"${contextPath}/management/shopAnalyse/preMonthlyViewList?type=2",
							"preMonthlyViewListId", "请选择分析字段", {
								max : false,
								mask : true,
								resizable : true,
								drawable : true,
								fresh : true
							});
		} else {
			$.pdialog.open(path, "previewListId", "导出报表预览", {
				width : 910,
				height : 600,
				max : false,
				mask : true,
				resizable : true,
				drawable : true,
				fresh : true
			});
		}
	}

	//type:1导出，2预览
	function getSubmit(flag, url, id, name, type) {
		//	alert("进入方法")
		var CheckGetSelectValue = $("#CheckGetSelect").val();
		url = "${contextPath}/management/shopAnalyse/" + url + "?type=" + type
		if (CheckGetSelectValue != flag) {
			//  alert("进入默认打开")
			$.pdialog.open(url, id, name, {
				width : 350,
				height : 300,
				max : false,
				mask : true,
				resizable : true,
				drawable : true,
				fresh : true
			});
		} else {
			if (type == 1) {
				//  	alert("进入方法type1")
				$('#ToExcelForm').submit();
			} else if (type == 2) {
				//  	 alert("进入方法type2")
				var valueText = $('#ToExcelForm').serialize()
				var path = "${contextPath}/management/shopAnalyse/previewList?"
						+ valueText
				$.pdialog.open(path, "previewListId", "导出报表预览", {
					width : 850,
					height : 600,
					max : false,
					mask : true,
					resizable : true,
					drawable : true,
					fresh : true
				});
			}
			$("#CheckGetSelect").val("")
		}
	}

	//top10与日销售可选列
	function selectTOP_DaySales_Column(type, top0rdayType) {
		var option = "{width:50px,height:20px,max:true,mask:true,mixable:false,minable:false,resizable:false,drawable:false}"
		var path = "${contextPath}/management/shopAnalyse/toColumnSelect?type="
				+ type + "&top0rdayType=" + top0rdayType
		$.pdialog.open(path, "cloumnId" + type,
				"导出列选择__tag_113$51_(自动记录您的最近一次选择，初始为全选)__tag_113$99_", option);
	}
</script>

<dwz:paginationForm action="${contextPath }/management/shopAnalyse/list"
	page="${page }" reverseOrderDirection="true">
	<input type="hidden" name="beginTime"
		value="${shopAnalyseCheckEntity.beginTime}" />
	<input type="hidden" name="endTime"
		value="${shopAnalyseCheckEntity.endTime}" />
	<input type="hidden" name="productPlatformId"
		value="${shopAnalyseCheckEntity.productPlatformId}" />
	<input type="hidden" name="quater"
		value="${shopAnalyseCheckEntity.quater}" />
	<input type="hidden" name="productLfPf"
		value="${shopAnalyseCheckEntity.productLfPf}" />
	<input type="hidden" name="beginPrice"
		value="${shopAnalyseCheckEntity.beginPrice}" />
	<input type="hidden" name="endPrice"
		value="${shopAnalyseCheckEntity.endPrice}" />
	<input type="hidden" name="materialNumber"
		value="${shopAnalyseCheckEntity.materialNumber}" />
</dwz:paginationForm>



<form method="post" action="${contextPath }/management/shopAnalyse/list"
	onsubmit="return validateCustomBase(this)" id="formId">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="width: 100px;">
						销售时间：
					</label>
					<input type="text" name="beginTime" class="date"
						readonly="readonly" style="width: 80px"
						value="${shopAnalyseCheckEntity.beginTime}" />
					~
					<input type="text" name="endTime" class="date" readonly="readonly"
						style="width: 80px" value="${shopAnalyseCheckEntity.endTime}" />
				</li>
				<!--  
				<li>
					<label style="width: 100px;">
						销售结束时间：
					</label>
					<input type="text" name="endTime" class="date"
						readonly="readonly" style="float: left;"
						value="${shopAnalyseCheckEntity.endTime}" />
					<a class="inputDateButton" href="javascript:;" style="float: left;">选择</a>
				</li>
				-->
				<li>
					<label style="width: 100px;">
						平台ID：
					</label>
					<input type="text" name="productPlatformId"
						value="${shopAnalyseCheckEntity.productPlatformId}" title="支持模糊查询"
						alt="支持模糊查询" />
				</li>
				<li>
					<label>
						款号：
					</label>
					<input type="text" name="materialNumber"
						value="${shopAnalyseCheckEntity.materialNumber}" title="支持模糊查询"
						alt="支持模糊查询" / />
				</li>
			</ul>

			<ul class="searchContent">
				<!--   (0:季节，1:LF/PF，2:系列)-->
				<li>
					<label style="width: 100px; margin-left: 0px">
						季节：
					</label>
					<select class="combox fsfbrtl-select" name="quater">
						<option value="">
							所有
						</option>
						<c:forEach var="Quater" items="${ParamAll[0]}">
							<c:choose>
								<c:when test="${Quater.brandId==param.brandId}">
									<option value="${Quater.name}"
										${shopAnalyseCheckEntity.quater==
										Quater.name? 'selected="selected"':'' }>
										d${Quater.name}
									</option>
								</c:when>
								<c:when test="${param.brandId==null}">
									<option value="${Quater.name}"
										${shopAnalyseCheckEntity.quater==
										Quater.name? 'selected="selected"':'' }>
										${Quater.name}
									</option>
								</c:when>
							</c:choose>
						</c:forEach>
					</select>
				</li>
				<li>
					<label style="width: 100px; margin-left: 0px">
						LF/PF：
					</label>
					<select class="combox fsfbrtl-select" name="productLfPf">
						<option value="">
							所有
						</option>
						<c:forEach var="productLfPf" items="${ParamAll[1]}">
							<c:choose>
								<c:when test="${productLfPf.brandId==param.brandId}">
									<option value="${productLfPf.name}"
										${shopAnalyseCheckEntity.productLfPf==
										productLfPf.name ? 'selected="selected"':'' }>
										${productLfPf.name}
									</option>
								</c:when>
								<c:when test="${param.brandId==null}">
									<option value="${productLfPf.name}"
										${shopAnalyseCheckEntity.productLfPf==
										productLfPf.name ? 'selected="selected"':'' }>
										${productLfPf.name}
									</option>
								</c:when>
							</c:choose>
						</c:forEach>
					</select>

				</li>
				<li>
					<label style="width: 100px;">
						价格区间：
					</label>
					<input title="市场价" type="text"
						value="${shopAnalyseCheckEntity.beginPrice}" name="beginPrice"
						class="input-medium  required validate[custom[number]]" value=""
						style="width: 60px" />
					~
					<input title="市场价" type="text"
						value="${shopAnalyseCheckEntity.endPrice}" name="endPrice"
						class="input-medium  required validate[custom[number]]" value=""
						style="width: 60px" />
				</li>
			</ul>
			<div class="subBar">
				<span style="float: left; padding-top: 10px"><font
					style="font-size: 11px">默认查询导出时间范围为当前月初一号到当前时间;若只有一个时间范围，则另一个时间范围向前或向后一个月;增长率等于(本月除以上个月-1)*100</font>
				</span>
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="reset">
									重置
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</form>

<!-- 功能id,1top10,2销售日报，3月报，4月报摘要 5销售分析 6月度统计 -->
<form method="post"
	action="${contextPath}/management/shopAnalyse/toExcel" target="_self"
	method="post" id="ToExcelForm">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a iconClass="accept" href="#" title="选择导出选项"><span>选择导出选项：</span>
				</a>
			</li>
			<li>
				<a iconClass="application" href="#"
					title="导出过滤条件：根据查询条件查询后，按销售金额取排名前10个的记录 （默认时间当前月初到当前时间）"><span><input
							type="radio" name="excelType" value="1">Top10</span> </a>
			</li>
			<li>
				<a iconClass="application_cascade" href="#"
					title="导出过滤条件：根据查询条件+商品销售时间查询销售数据报表（默认当前年当前月初到当前时间）"><span><input
							type="radio" name="excelType" value="2">销售日报 </span> </a>
			</li>
			<!-- 
			<li>
				<a iconClass="application_view_gallery" title="导出过滤条件：月报表字段+销售时间范围（如无时间范围则默认当前年当前月向前推3个月;注:最多以销售结束时间为所在年的一年月报记录)"><span><input
							type="radio" name="excelType" value="3">月报</span> </a>
			</li> -->
			<!-- 	<li>
				<a iconClass="application_view_gallery" title="导出过滤条件：根据一段时间以及其他选项查询出来的销售数据，取这段时间中第一个月销售数据分析并形成报表"><span><input
							type="radio" name="excelType" value="4">月度摘要</span> </a>
			</li> -->
			<li>
				<a iconClass="application_form_magnify" href="#"
					title="导出过滤条件：根据勾选分类(种类,系列,Line,性别,季节,且分类不为空和Null)+价格+时间查询的销售记录导出报表（默认当前年当前月初到当前时间,如果分类只选一个，则过虑条件为查询界面所有）"><span><input
							type="radio" name="excelType" value="5" id="radioGroup">销售分析</span>
				</a>
			</li>
			<li>
				<a iconClass="application_form_magnify" href="#"
					title="导出过滤条件：月报表字段+(如无时间范围则，默认当前年当前月向前推3个月;注：最多以销售结束时间为所在年的一年月报记录)"><span><input
							type="radio" name="excelType" value="6" id="radioGroup">月度统计
				</span> </a>
			</li>
			<li>
				<input type="hidden" name="beginTime"
					value="${shopAnalyseCheckEntity.beginTime}" />
				<input type="hidden" name="endTime"
					value="${shopAnalyseCheckEntity.endTime}" />
				<input type="hidden" name="productPlatformId"
					value="${shopAnalyseCheckEntity.productPlatformId}" />
				<input type="hidden" name="quater"
					value="${shopAnalyseCheckEntity.quater}" />
				<input type="hidden" name="productLfPf"
					value="${shopAnalyseCheckEntity.productLfPf}" />
				<input type="hidden" name="beginPrice"
					value="${shopAnalyseCheckEntity.beginPrice}" />
				<input type="hidden" name="endPrice"
					value="${shopAnalyseCheckEntity.endPrice}" />

				<input type="hidden" name="ShopOptions" value="0" id="ShopOptions" />
				<input type="hidden" name="OrderList" value="0" id="OrderList" />
				<input type="hidden" name="ShopOptionsName" value="0"
					id="ShopOptionsName" />

				<input type="hidden" name="materialNumber"
					value="${shopAnalyseCheckEntity.materialNumber}" />
				<input type="hidden" name=topColumn
					value="${shopAnalyseCheckEntity.topColumn}" id="topColumn" />
				<input type="hidden" name=daySalesColumn
					value="${shopAnalyseCheckEntity.daySalesColumn}"
					id="daySalesColumn" />
				<input type="hidden" name=monthlyFieldName
					value="${shopAnalyseCheckEntity.monthlyFieldName}"
					id="monthlyFieldName" />

				<input type="hidden" name="img" value="-1" id="img" />
				<input type="hidden" name="CheckGetSelect" value="0"
					id="CheckGetSelect" />
				<a iconClass="page_white_get" href="#" onclick=
	check();
title="报表导出"><span>导出</span>
				</a>
				<a iconClass="user_go" href="#" onclick=
	preview();
title="报表预览"><span>预览</span>
				</a>
			</li>
		</ul>
	</div>
</form>
<table class="table" layoutH="162" width="100%">
	<thead>
		<tr>
			<th width="100" orderField="materialNumber"
				class="${page.orderField eq 'materialNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
				款号
			</th>
			<th width="100">
				69码
			</th>
			<th width="90" orderField="salesTime"
				class="${page.orderField eq 'salesTime' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
				销售时间
			</th>

			<th width="100">
				平台产品ID
			</th>
			<th width="70" orderField="tagPrice"
				class="${page.orderField eq 'tagPrice' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
				市场价
			</th>
			<th width="70">
				种类
			</th>
			<th width="50">
				性别
			</th>
			<th width="70">
				LF/PF
			</th>
			<th width="70">
				季节
			</th>
			<th width="70" id="title_salesNumber_id" title="所查询周期的销量总和"
				orderField="salesNumber"
				class="${page.orderField eq 'salesNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
				总销量
			</th>
			<th width="70" id="title_disCount_id"
				title="所查询周期的有销售记录的平均折扣（成交价除以市场价）">
				折扣率(%)
			</th>
			<th width="100" id="title_daysx_id" title="所查询周期的销售金额平均值">
				日均销售金额
			</th>
			<th width="100" id="title_salesSum_id" orderField="salesSum"
				class="${page.orderField eq 'salesSum' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}"
				title="所查询周期的总和">
				销售金额
			</th>
			<th width="80" id="title_outSalesRate_id"
				title="所查询周期的有销售记录的平均售罄率（销售数量/到货数量）">
				售罄率(%)
			</th>

		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${result}">
			<tr target="slt_uid">
				<td>
					${item.materialNumber!=null?item.materialNumber:"-"}
				</td>
				<td>
					${item.upccode!=null?item.upccode:"-"}
				</td>
				<td>
					${item.salesTime!=null?item.salesTime:"-"}
				</td>
				<td>
					${item.productPlatformId!=null?item.productPlatformId:"-"}
				</td>
				<td>
					${item.tagPrice!=null?item.tagPrice:"-"}
				</td>
				<td>
					${item.productType!=null?item.productType:"-"}
				</td>
				<td>
					${item.productSex!=null?item.productSex:"-"}
				</td>
				<td>
					${item.productLfPf!=null?item.productLfPf:"-"}
				</td>
				<td>
					${item.quater!=null?item.quater:"-"}
				</td>
				<td align="right">
					<c:if test="${item.salesNumber==null}">
					-
					</c:if>
					<c:if test="${item.salesNumber!=null}">
						<fmt:formatNumber value="${item.salesNumber}" pattern="###,###.##"
							minFractionDigits="2" />
					</c:if>
				</td>

				<td>
					${item.disCount!=null?item.disCount:"-"}
				</td>
				<td align="right">
					<c:if test="${item.daysx==null}">
					-
					</c:if>
					<c:if test="${item.daysx!=null}">
						<fmt:formatNumber value="${item.daysx}" pattern="###,###.##"
							minFractionDigits="2" />
					</c:if>
				</td>
				<td align="right">
					<c:if test="${item.salesSum==null}">
					-
					</c:if>
					<c:if test="${item.salesSum!=null}">
						<fmt:formatNumber value="${item.salesSum}" pattern="###,###.##"
							minFractionDigits="2" />
					</c:if>
				</td>
				<td>
					${item.outSalesRate!=null?item.outSalesRate:"-"}
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<!-- 分页 -->

</table>

<dwz:pagination page="${page }" />
<script type="text/javascript">
	$(document).ready(function() {
		setTimeout(function() {
			$("#title_salesNumber_id").attr( {
				"title" : "所查询周期的销量总和"
			});
			$("#title_salesNumber_id>div").attr( {
				"title" : "所查询周期的销量总和"
			});
			$("#title_disCount_id").attr( {
				"title" : "所查询周期的有销售记录的平均折扣（成交价除以市场价）"
			});
			$("#title_disCount_id>div").attr( {
				"title" : "所查询周期的有销售记录的平均折扣（成交价除以市场价）"
			});
			$("#title_daysx_id").attr( {
				"title" : "所查询周期的销售金额平均值"
			});
			$("#title_daysx_id>div").attr( {
				"title" : "所查询周期的销售金额平均值"
			});
			$("#title_salesSum_id").attr( {
				"title" : "所查询周期的总和"
			});
			$("#title_salesSum_id>div").attr( {
				"title" : "所查询周期的总和"
			});
			$("#title_outSalesRate_id").attr( {
				"title" : "所查询周期的有销售记录的平均售罄率（销售数量/到货数量）"
			});
			$("#title_outSalesRate_id>div").attr( {
				"title" : "所查询周期的有销售记录的平均售罄率（销售数量/到货数量）"
			});
		}, 100);
	});
</script>

