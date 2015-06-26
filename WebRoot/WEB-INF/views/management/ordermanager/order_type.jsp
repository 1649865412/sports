<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/views/include.inc.jsp"%>
  <script type="text/javascript" language="javaScript">
 	function uploadSubmit()
 	{	
 	 	var orderTypeValue = $('input[name="orderType"]:checked').val();
 	 	if(undefined == orderTypeValue || orderTypeValue.length <=0)
 	 	{
 	 		alertMsg.error("请先选择类型");
 	 		return ;	
 	 	}
 	 	
 		//$('#orderTypeId' , parent.document).attr({value:orderTypeValue}); 
 		$.pdialog.closeCurrent();
 		$.pdialog.open("${contextPath }/management/orderManager/upload?orderType="+orderTypeValue, "orderManager_upload", "数据导入",{mask:true});
 	}
</script>
<form id="" method="post" action="${contextPath}/management/orderManager/${request_url}" >
	<div class="pageContent">
		<div class="pageFormContent" layoutH="58">
			<p>
			<label>请选择：</label>
			<c:if test="${not empty orderTypes}">
				<c:forEach items="${orderTypes}" var="item" varStatus="index">
					<input type="radio" name="orderType"  value="${item.type}" <c:if test="${index.index+1 eq 1}">checked="checked" </c:if> /> &nbsp;${item.name}&nbsp;&nbsp;&nbsp;&nbsp;		
				</c:forEach>
			</c:if>
			<c:if test="${empty orderTypes}">
					<input type="radio" name="orderType"  value="1" checked="checked"/> &nbsp;订货单&nbsp;&nbsp;&nbsp;&nbsp;		
					<input type="radio" name="orderType"  value="2"/> &nbsp;到货单&nbsp;&nbsp;&nbsp;&nbsp;		
			</c:if>
		</p>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="${isDownload==true?'submit':'button'}" <c:if test="${isDownload==false }">onclick="uploadSubmit();"</c:if>>确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</div>
</form>

