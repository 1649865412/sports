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
function exportEXCEL(){
		 // alert("hello");
		  $('#exportEXCEL').submit(); 	 		   
		}

//R:日   Z:季   M:月   Y：年
$(document).ready(function() {		
	          //  alert('${shopAnalyseTimeCheckEntity.selectTimeType}')
                if(${shopAnalyseTimeCheckEntity.selectTimeType=='R'})        changeViewR() 
                else if(${shopAnalyseTimeCheckEntity.selectTimeType=='M'})	  changeViewM()
                else if(${shopAnalyseTimeCheckEntity.selectTimeType=='Z'})	  changeViewZ()
                else if(${shopAnalyseTimeCheckEntity.selectTimeType=='Y'})	  changeViewY()   
		 });
		 	
	function getmenu(){ 
		   var check=$("#selectTimeType").val();
		  // alert(check);	
		   if(check=="M"){
			        changeViewM()
			   }
		       else if(check=="R") {
			       changeViewR()	  
		       }
		       else if(check=="Z") {
		           changeViewZ()	  
			  }else{
	     		   changeViewY()
	    }
	}
		function changeViewR(){
		        $("#includeDayTime").show();
		        $("#includeQuarter").hide();
		        $("#includeMonth").hide();
		        $("#includeYear").hide();
     }	
     	function changeViewM(){
     		    $("#includeDayTime").hide();
		        $("#includeQuarter").hide();
		        $("#includeMonth").show();
		        $("#includeYear").show();
     }	
     	function changeViewZ(){
     	 		$("#includeDayTime").hide();
		        $("#includeQuarter").show();
		        $("#includeMonth").hide();
		        $("#includeYear").show();
     }	
     	function changeViewY(){
     	 	    $("#includeDayTime").hide();
		        $("#includeQuarter").hide();
		        $("#includeMonth").hide();
		        $("#includeYear").show();
     } 
		 function check(type){
			  getSubmit("toGroupSelectExcel?type="+type,"ShopAnalyseId","时间纵向分析分类导出与下拉顺充选择");
			 }
		 
		 function getSubmit(url,id,name){
			// alert("hello")
		    //  var  option="{width:350,height:300,max:true,mask:true,mixable:false,minable:false,resizable:false,drawable:false}"
		      url="${contextPath}/management/shopTimeAnalyse/"+url
			  $.pdialog.open(url, id, name,{width:350,height:300,max:false,mask:true,resizable:true,drawable:true,fresh:true});  
}</script>


<dwz:paginationForm
	action="${contextPath }/management/shopTimeAnalyse/timeAnalyse"
	page="${page}" reverseOrderDirection="true">
	<input type="hidden" name="selectTimeType"
		value="${shopAnalyseTimeCheckEntity.selectTimeType}" />
	<input type="hidden" name="year"
		value="${shopAnalyseTimeCheckEntity.year}" />
	<input type="hidden" name="firstTime"
		value="${shopAnalyseTimeCheckEntity.firstTime}" />
	<input type="hidden" name="endTime"
		value="${shopAnalyseTimeCheckEntity.endTime}" />
	<input type="hidden" name="monthBegin"
		value="${shopAnalyseTimeCheckEntity.monthBegin}" />
	<input type="hidden" name="monthEnd"
		value="${shopAnalyseTimeCheckEntity.monthEnd}" />
	<input type="hidden" name="quarterfirstTime"
		value="${shopAnalyseTimeCheckEntity.quarterfirstTime}" />
	<input type="hidden" name="quarterendTime"
		value="${shopAnalyseTimeCheckEntity.quarterendTime}" />
	<input type="hidden" name="circle"
		value="${shopAnalyseTimeCheckEntity.circle}" />
	<input type="hidden" name="series"
		value="${shopAnalyseTimeCheckEntity.series}" />
	<input type="hidden" name="quater"
		value="${shopAnalyseTimeCheckEntity.quater}" />
</dwz:paginationForm>


<form method="post"
	action="${contextPath}/management/shopTimeAnalyse/timeAnalyse"
	onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="width: 90px;"
						title="统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录,超过一年的，以结束时间为准的所在当前年为查询范围">
						统计类型:
					</label>
					<select class="combox fsfbrtl-select" onChange="getmenu()"
						id="selectTimeType" name="selectTimeType"
						title="统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录,超过一年的，以结束时间为准的所在当前年为查询范围">
						<option value="R"
							<c:if test="${shopAnalyseTimeCheckEntity.selectTimeType=='R'}">selected="selected"</c:if>>
							日
						</option>
						<option value="M"
							<c:if test="${shopAnalyseTimeCheckEntity.selectTimeType=='M'}">selected="selected"</c:if>>
							月
						</option>
						<option value="Z"
							<c:if test="${shopAnalyseTimeCheckEntity.selectTimeType=='Z'}">selected="selected"</c:if>>
							季度
						</option>
						<option value="Y"
							<c:if test="${shopAnalyseTimeCheckEntity.selectTimeType=='Y'}">selected="selected"</c:if>>
							年
						</option>
					</select>
				</li>
				<label style="width: 80px; margin-left: 0px;">
					查询销售时间:
				</label>
				<%@include file="../shopAnalyse/shopdayView.jsp"%>

			</ul>

			<!--   (0:季节，1:LF/PF，2:系列)-->
			<ul class="searchContent">

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
									<option value="${Quater.name}"
										${shopAnalyseTimeCheckEntity.quater==
										Quater.name ? 'selected="selected"':'' }>
										${Quater.name}
									</option>
								</c:when>
								<c:when test="${param.brandId==null}">
									<option value="${Quater.name}"
										${shopAnalyseTimeCheckEntity.quater==
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
									<option value="${Series.name}"
										${shopAnalyseTimeCheckEntity.series==
										Series.name ? 'selected="selected"':'' }>
										${Series.name}
									</option>
								</c:when>
								<c:when test="${param.brandId==null}">
									<option value="${Series.name}"
										${shopAnalyseTimeCheckEntity.series==
										Series.name ? 'selected="selected"':'' }>
										${Series.name}
									</option>
								</c:when>
							</c:choose>
						</c:forEach>
					</select>
				</li>
				<li>
					<label style="margin-left: 100px; margin-left: 50px;">
						对比周期：
					</label>
					<select name="circle" style="margin-right: 30px;">
						<c:forEach var="numList" items="${numList}" varStatus="status">
							<c:if test="${status.index+1<=2}">
								<option value="${status.index+1}"
									${shopAnalyseTimeCheckEntity.circle==
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

			<font style="font-size: 11px">注：默认时间维度范围为：
				当前时间到往前推30天;统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录;如果只有一个时间范围则以默认时间范围为准,增长率等于（本年除以去年-1）*100</font>
		</div>
	</div>
</form>



<div class="pageContent">
	<div class="panelBar">
		<form
			action="${contextPath }/management/shopTimeAnalyse/timeAnalyseExcelPOI"
			method="post" id="exportEXCEL">
			<ul class="toolBar">
				<li>
					<input type="hidden" name="selectTimeType"
						value="${shopAnalyseTimeCheckEntity.selectTimeType}" />
					<input type="hidden" name="year"
						value="${shopAnalyseTimeCheckEntity.year}" />
					<input type="hidden" name="firstTime"
						value="${shopAnalyseTimeCheckEntity.firstTime}" />
					<input type="hidden" name="endTime"
						value="${shopAnalyseTimeCheckEntity.endTime}" />
					<input type="hidden" name="monthBegin"
						value="${shopAnalyseTimeCheckEntity.monthBegin}" />
					<input type="hidden" name="monthEnd"
						value="${shopAnalyseTimeCheckEntity.monthEnd}" />
					<input type="hidden" name="quarterfirstTime"
						value="${shopAnalyseTimeCheckEntity.quarterfirstTime}" />
					<input type="hidden" name="quarterendTime"
						value="${shopAnalyseTimeCheckEntity.quarterendTime}" />
					<input type="hidden" name="circle"
						value="${shopAnalyseTimeCheckEntity.circle}" />
					<input type="hidden" name="category"
						value="${shopAnalyseTimeCheckEntity.category}" />
					<input type="hidden" name="series"
						value="${shopAnalyseTimeCheckEntity.series}" />
					<input type="hidden" name="quater"
						value="${shopAnalyseTimeCheckEntity.quater}" />
					<input type="hidden" name="shopOptionsNameTime" value="0"
						id="shopOptionsNameTime" />
					<input type="hidden" name="orderListTime" value="0"
						id="OrderListTime" />

					<!--  <a iconClass="page_white_get"   href="javascript:exportEXCEL()"  title="导出过滤条件：平台+销售时间范围+界面查询条件"><span>分析报表导出</span></a>-->

					<a iconClass="page_white_get" href="#" onclick="check(1)"><span
						title="导出过滤条件：根据勾选分类(种类,系列,Line,性别,季节,且分类不为空和Null)导出报表,报表图为所选的一级分类+时间范围曲线图
			,注：最多以销售结束时间为所在年的一年记录">分类报表导出</span>
					</a>


				</li>
				<li>
					<a iconClass="user_go" href="#" onclick="check(2)" title="报表预览"><span>预览</span>
					</a>
				</li>
			</ul>
		</form>
	</div>

	<table class="table" layoutH="174" width="100%">
		<thead>
			<tr>
				<th width="100">
					69码
				</th>
				<th width="80" orderField="materialNumber"
					class="${page.orderField eq 'materialNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
					款号
				</th>

				<th width="100" orderField="quater"
					class="${page.orderField eq 'quater' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
					季节
				</th>
				<th width="50">
					性别
				</th>
				<th width="80" orderField="tagPrice"
					class="${page.orderField eq 'tagPrice' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
					市场价
				</th>
				<th width="200">
					系列
				</th>
				<th width="200">
					品牌
				</th>
				<th width="150" id="title_salesNumber_id" title="所查询周期的销量总和"
					orderField="salesNumber"
					class="${page.orderField eq 'salesNumber' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
					销售数量
				</th>
				<th width="100" id="title_disCount_id"
					title="所查询周期的有销售记录的平均折扣（成交价除以市场价）" orderField="disCount"
					class="${page.orderField eq 'disCount' ? page.orderDirection eq 'desc' ? 'asc':'desc' : 'desc'}">
					折扣率(%)
				</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${shopTimeAnaylseResultList}">
				<tr target="slt_uid">
					<td>
						${item.upccode!=null?item.upccode:"-"}
					</td>
					<td>
						${item.materialNumber!=null?item.materialNumber:"-"}
					</td>

					<td>
						${item.quater!=null?item.quater:"-"}
					</td>
					<td>
						${item.productSex!=null?item.productSex:"-"}
					</td>
					<td>
						${item.tagPrice!=null?item.tagPrice:"-"}
					</td>
					<td>
						${item.series!=null?item.series:"-"}
					</td>
					<td>
						${item.productBrand!=null?item.productBrand:"-"}
					</td>
					<td align="right">
						<c:if test="${item.salesNumber==null}">
					-
					</c:if>
						<c:if test="${item.salesNumber!=null}">
							<fmt:formatNumber value="${item.salesNumber}"
								pattern="###,###.##" minFractionDigits="2" />
						</c:if>
					</td>
					<td>
						${item.disCount!=null?item.disCount:"-"}
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }" />
</div>


<script type="text/javascript">
$(document).ready(function(){
 setTimeout(function(){
	$("#title_salesNumber_id").attr({"title":"所查询周期的销量总和"});
	$("#title_salesNumber_id>div").attr({"title":"所查询周期的销量总和"});
	$("#title_disCount_id").attr({"title":"所查询周期的有销售记录的平均折扣（成交价除以市场价）"});
	$("#title_disCount_id>div").attr({"title":"所查询周期的有销售记录的平均折扣（成交价除以市场价）"});
	}, 100);
});
</script>