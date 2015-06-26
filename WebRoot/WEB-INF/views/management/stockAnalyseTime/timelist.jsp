<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style type="text/css">
.fsfbrtl-select {
	width: 60px;
}
</style>


<script type="text/javascript" defer="defer">
function exportEXCEL(exportType){
		if(exportType == 2)
		{	
			var valueText=$('#exportEXCELtime', parent.document).serialize()
			//$('#exportEXCELtime').attr({"action":"${contextPath }/management/suitTimeAnalyse/previewList"});
			
			$.pdialog.open("${contextPath }/management/suitTimeAnalyse/previewList?"+valueText, "stockAnalyseTimePreViewListId","预览",
			{width:850,height:600,max:false,mask:true,resizable:true,drawable:true,fresh:true});
			return ;
		}
		
		//  alert("hello");
		 $('#exportEXCELtime').submit(); 	 		   
		}
		
	//R:日   Z:季   M:月   Y：年
    $(document).ready(function() {		
                var SuitselectTimeType='${suitTime.suitselectTimeType}';
                if(SuitselectTimeType=='R' || SuitselectTimeType == "W")  changeViewR() 
                else if(SuitselectTimeType=='M')	  changeViewM()
                else if(SuitselectTimeType=='Z')	  changeViewZ()
                else if(SuitselectTimeType=='Y')	  changeViewY()   
		 });	
		 
		 
	function getmenu(){ 
		   var check=$("#SuitselectTimeType").val();
			   if(check=="M"){
			        changeViewM()
			   }
		       else if(check=="R") {
			       changeViewR()	  
		       }
		       else if(check=="Z") {
		           changeViewZ()	  
			  }
			  else if (check == "W")
			  {
			  	 changeViewR();
			  }
			  else{
	     		   changeViewY()
	    }
	}
	    function changeViewR(){
		        $("#suitIncludeDayTime").show();
		        $("#suitIncludeQuarter").hide();
		        $("#suitIncludeMonth").hide();
		        $("#suitIncludeYear").hide();
		        
     }	
     	function changeViewM(){
     		    $("#suitIncludeDayTime").hide();
		        $("#suitIncludeQuarter").hide();
		        $("#suitIncludeMonth").show();
		        $("#suitIncludeYear").show();
     }	
     	function changeViewZ(){
     	 		$("#suitIncludeDayTime").hide();
		        $("#suitIncludeQuarter").show();
		        $("#suitIncludeMonth").hide();
		        $("#suitIncludeYear").show();
     }	
     	function changeViewY(){
     	 	    $("#suitIncludeDayTime").hide();
		        $("#suitIncludeQuarter").hide();
		        $("#suitIncludeMonth").hide();
		        $("#suitIncludeYear").show();
     }</script>


<dwz:paginationForm
	action="${contextPath}/management/suitTimeAnalyse/timeAnalyse" page="${page }" reverseOrderDirection="true">
	<input type="hidden" name="suitselectTimeType" value="${suitTime.suitselectTimeType}" />
	<input type="hidden" name="year" value="${suitTime.year}" />
	<input type="hidden" name="firstTime" value="${suitTime.firstTime}" />
	<input type="hidden" name="endTime" value="${suitTime.endTime}" />

	<input type="hidden" name="monthBegin" value="${suitTime.monthBegin}" />
	<input type="hidden" name="monthEnd" value="${suitTime.monthEnd}" />
	<input type="hidden" name="quarterfirstTime" value="${suitTime.quarterfirstTime}" />
	<input type="hidden" name="quarterendTime" value="${suitTime.quarterendTime}" />

	<input type="hidden" name="circle" value="${suitTime.circle}" />
	<input type="hidden" name="quater" value="${suitTime.quater}" />
	<input type="hidden" name="series" value="${suitTime.series}" />
	

</dwz:paginationForm>


<form method="post"
	action="${contextPath}/management/suitTimeAnalyse/timeAnalyse"
	onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="width: 90px;"
						title="统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录">
						统计类型:
					</label>
					<select class="combox fsfbrtl-select" onChange="getmenu()"
						id="SuitselectTimeType" name="suitselectTimeType">
						<option value="R"
							<c:if test="${suitTime.suitselectTimeType=='R'}">selected="selected"</c:if>>
							日
						</option>
						<option value="W"
							<c:if test="${suitTime.suitselectTimeType=='W'}">selected="selected"</c:if>>
							周
						</option>
						<option value="M"
							<c:if test="${suitTime.suitselectTimeType=='M'}">selected="selected"</c:if>>
							月
						</option>
						<option value="Z"
							<c:if test="${suitTime.suitselectTimeType=='Z'}">selected="selected"</c:if>>
							季度
						</option>
						<option value="Y"
							<c:if test="${suitTime.suitselectTimeType=='Y'}">selected="selected"</c:if>>
							年
						</option>
					</select>
				</li>

				
					<label style="width: 80px; margin-left: 0px;">
						销售时间:
					</label>
					<%@include file="../stockAnalyseTime/suitdayView.jsp"%>
				
			</ul>
			
			<ul class="searchContent">
<!--   (0:季节，1:LF/PF，2:系列)-->
	<li>
					<label style="width: 90px; margin-left: 0px">
						季节:
					</label>
					<select class="combox fsfbrtl-select" name="quater">
						<option value="">
							所有
						</option>
						<c:forEach var="Quater" items="${ParamAll[0]}">
						<c:choose>
						<c:when test="${Quater.brandId==param.brandId}">
							<option value="${Quater.name}" ${suitTime.quater==
								Quater.name ? 'selected="selected"':'' }>
								${Quater.name}
							</option>
						  </c:when>
						  	<c:when test="${param.brandId==null}">
						  	<option value="${Quater.name}" ${suitTime.quater==
								Quater.name ? 'selected="selected"':'' }>
								${Quater.name}
							</option>
						   </c:when>
						</c:choose>
						</c:forEach>
					</select>
				</li>
				<li>
					<label style="width: 80px; margin-left: 0px">
						系列
					</label>
					<select class="combox fsfbrtl-select" name="series">
						<option value="">
							所有
						</option>
								<c:forEach var="Series" items="${ParamAll[2] }">
						<c:choose>
						<c:when test="${Series.brandId==param.brandId}">
							<option value="${Series.name}" ${suitTime.series==
								Series.name ? 'selected="selected"':'' }>
								${Series.name}
							</option>
						</c:when>
						<c:when test="${param.brandId==null}">
							<option value="${Series.name}" ${suitTime.series==
								Series.name ? 'selected="selected"':'' }>
								${Series.name}
							</option>
						</c:when>
						</c:choose>
					</c:forEach>
					</select>
				</li>	
				<li>
					<span style="margin-left: 100px; margin-left: 50px;">对比周期：</span>
					<select name="circle" style="margin-right: 30px;">
						<c:forEach var="numList" items="${numList}" varStatus="status">
							<c:if test="${status.index+1<=3}">
								<option value="${status.index+1}" ${suitTime.circle==
									status.index+1 ? 'selected="selected"':'' }>
									${status.index+1}年
								</option>
							</c:if>
						</c:forEach>

					</select>
			</li>

			</ul>
			<div class="subBar">
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
					<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				</ul>
			</div>
			<font style="font-size: 11px">注：默认时间维度范围为：当前时间到往前推30天;统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录;只有一个时间范围以默认时间范围为准</font>


		</div>
	</div>
</form>

<div class="pageContent">
	<div class="panelBar" style="height:30px">
		<form
			action="${contextPath }/management/suitTimeAnalyse/timeAnalyseExcelPOI"
			method="post" id="exportEXCELtime">
				<ul class="toolBar">
				<li>
					<input type="hidden" name="suitselectTimeType"value="${suitTime.suitselectTimeType}" />
					<input type="hidden" name="year" value="${suitTime.year}" />
					<input type="hidden" name="firstTime" value="${suitTime.firstTime}" />
					<input type="hidden" name="endTime" value="${suitTime.endTime}" />
					<input type="hidden" name="monthBegin" value="${suitTime.monthBegin}" />
					<input type="hidden" name="monthEnd" value="${suitTime.monthEnd}" />
					<input type="hidden" name="quarterfirstTime"value="${suitTime.quarterfirstTime}" />
					<input type="hidden" name="quarterendTime"value="${suitTime.quarterendTime}" />
					<input type="hidden" name="circle" value="${suitTime.circle}" />
					<input type="hidden" name="quater" value="${suitTime.quater}" />
					<input type="hidden" name="series" value="${suitTime.series}" />
					
				<a iconClass="page_white_get"   href="javascript:exportEXCEL(1)"  title="导出过滤条件：库存时间范围+界面查询条件;注：最多以销售结束时间为所在年的一年记录;"><span>分析报表导出</span></a>
			</li>
			<li>
			<a iconClass="user_go"   href="javascript:exportEXCEL(2)"  title="导出过滤条件：库存时间范围+界面查询条件;注：最多以销售结束时间为所在年的一年记录;"><span>预览</span></a></li>
			</ul>
		</form>
	</div>	
	<table class="table" layoutH="179" width="100%">
		<thead>
			<tr>	
				<th width="200px">
					产品名称
				</th>
				<th width="80px">
				         类别
				</th>
				<th width="80px" orderField="quater" class="${page.orderField eq 'quater' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">
				         季节
				</th>
				<th width="80px">
				         系列
				</th>
				<th id="title_orderNumber_id" title="最新订货量" orderField="orderNumber" class="${page.orderField eq 'orderNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">
					订货量
				</th>
				<th id="title_arriveNumber_id" title="最新出货量" orderField="arriveNumber" class="${page.orderField eq 'arriveNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}"> 
					出货量
				</th>
				<th id="title_arriveRate_id" title="（销售数量+库存）/订货数量">
					到货率(%)
				</th>
				<th id="title_dynamicRate_id" title="最新销量/最新库存">
					动销率(%)
				</th>
				<th id="title_salesoutRate_id" title="销售数量/到货数量">
					售罄率(%)
				</th>
				<th id="title_stockNumber_id" title="最新库存量" orderField="stockNumber" class="${page.orderField eq 'stockNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc'  : 'desc'}">
					库存量
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${result}">
				<tr target="slt_uid" rel="">
					<td>
						${item.productName!=null?item.productName:"-"}
					</td>	
					<td>
						${item.productType!=null?item.productType:"-"}
					</td>	
					<td>
						${item.quater!=null?item.quater:"-"}
					</td>
					<td>
						${item.series!=null?item.series:"-"}
					</td>
	
					<td align="right">
					<c:if test="${item.orderNumber==null}">
					-
						</c:if>
						<c:if test="${item.orderNumber!=null}">
						<fmt:formatNumber value="${item.orderNumber}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if>
					</td>
			
					<td align="right">
					<c:if test="${item.arriveNumber==null}">
					-
						</c:if>
						<c:if test="${item.arriveNumber!=null}">
						<fmt:formatNumber value="${item.arriveNumber}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if>
					</td>
					<td>
						${item.arriveRate!=null?item.arriveRate:"0"}
					</td>
					<td>
						${item.dynamicRate!=null?item.dynamicRate:"-"}
					</td>
					<td>
						${item.salesoutRate!=null?item.salesoutRate:"-"}
					</td>
					<td  align="right">
					    <c:if test="${item.stockNumber==null}">
					-
						</c:if>
						 <c:if test="${item.stockNumber!=null}">
						<fmt:formatNumber value="${item.stockNumber}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if>
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }" />
</div>
<script type="text/javascript" defer="defer">
$(document).ready(function(){
 setTimeout(function(){
	$("#title_orderNumber_id>div").attr({"title":"最新订货量"});
	$("#title_arriveNumber_id>div").attr({"title":"最新出货量"});
	$("#title_arriveRate_id>div").attr({"title":"（销售数量+库存）/订货数量"});
	$("#title_dynamicRate_id>div").attr({"title":"最新销量/最新库存"});
	$("#title_salesoutRate_id>div").attr({"title":"销售数量/到货数量"});
	$("#title_stockNumber_id>div").attr({"title":"最新库存量"});
	}, 100);
});
</script>
