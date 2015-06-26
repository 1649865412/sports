<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
	 $("#btnMonthlyId").click(function (){
	  	var radioObj = $("input[name='fieldName']:checked");
	  	if(radioObj.length <=0)
	  	{
	  		alertMsg.error("请至少选择一个字段！");
		 	return ;
	  	}
	  	
	  	$("#monthlyFieldName", parent.document).val($.trim(radioObj.val()));
	  	$.pdialog.closeCurrent();
	  	var type = "${type}";
	  	if(type == 1)
	  	{
	  		 $('#ToExcelForm' , parent.document).submit(); 
	  	}
	  	else if(type == 2)
	  	{
	  		 	var valueText=$('#ToExcelForm', parent.document).serialize()
	    		var path="${contextPath}/management/shopAnalyse/previewList?"+valueText
	    		$.pdialog.open(path, "previewListId", "导出报表预览",
				{width:850,height:600,max:false,mask:true,resizable:true,drawable:true,fresh:true});
	  	}
	  });
		 });</script>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		   <div>
		     <c:if test="${not empty fieldsMap}">
			 	<ul style="list-style: none">
		 		  <c:forEach var="item" items="${fieldsMap}" varStatus="index">
		 		 	<li style="display: inline-block; width: 100px;"><input type="radio" name="fieldName" value="${item.key}" <c:if test="${index.index+1 eq 1 }">checked="checked"</c:if>  >${item.value}</li>
		 		  </c:forEach>
			 	</ul>
	 		</c:if>
		   </div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" id="btnMonthlyId">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>