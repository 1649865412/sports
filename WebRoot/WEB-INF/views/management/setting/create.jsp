<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<script type="text/javascript">
		function check(value){	
		    var value=value.split("_")[1];
			$("#branidLabel").html(value);
			$("#brandIdNum").val(value);		 		   
		}</script>
		
		
<div class="pageContent">
<form method="post" action="${contextPath }//management/setting/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58"><%--
	
		<label>品牌ID：</label>
		<input type="text" name="brandId" maxlength="10" value="${generalSetting.brandId}" class="input-medium"/>
	  <label id="branidLabel"></label>
	      --%><input type="hidden" value="${defaultValue}" name="brandId" id="brandIdNum"/>
		
	<p>
		<label>品牌名称：</label>
		<!--  <input type="text" name="brandName" maxlength="255" class="input-medium"/>-->
		<select  name="brandName"  onchange="check(this.value);">
		<c:if test="${fn:length(brandListId)==0}">
		<c:forEach var="all" items="${allBrandList}">
			<option value="${all.brandName}_${all.id}">${all.brandName}</option>
			</c:forEach>
		</c:if>
		
		<c:if test="${fn:length(brandListId)!=0}">
		<c:forEach var="brand" items="${brandListId}">
		  <c:forEach var="all" items="${allBrandList}">
			<c:if test="${all.id==brand}">
			<option value="${all.brandName}_${all.id}">${all.brandName}</option>
		</c:if>
			</c:forEach>
		</c:forEach>
		</c:if>
		
		</select>
	</p>	
	<p>
		<label>到货提醒：</label>
		<input type="text" name="arrivalDay" maxlength="10" class="input-medium  required validate[custom[number]]"/>
	</p>	
	<p>
		<label>安全销售天数：</label>
		<input type="text" name="secritySell" maxlength="10" class="input-medium  required validate[custom[number]]"/>
	</p>	
	<p>
		<label>库存周转天数：</label>
		<input type="text" name="turnoverDay" maxlength="10" class="input-medium  required validate[custom[number]]"/>
	</p>	
	<%--<p>
		<label>更新时间：</label>
		<input type="text" name="updatedTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
		<label>更新人：</label>
		<input type="text" name="updatedUser" maxlength="10" class="input-medium"/>
	</p>	
	<p>
		<label>用户角色：</label>
		<input type="text" name="roleId" maxlength="10" class="input-medium"/>
	</p>	
	--%></div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>