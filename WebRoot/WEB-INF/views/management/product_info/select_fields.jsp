<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
  <style type="text/css">
	p{width:150px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;
</style>
<script type="text/javascript">
$(document).ready(function(){
  $("#checkAll").click(function(){
                $('input[name="fieldName"]').attr({"checked":true}); 
            });
  $("#checkNotAll").click(function(){
                $('input[name="fieldName"]').attr({"checked":false}); 
            });
});

function exportData()
{	
	try
	{

		 var selectNum=$('input[name="fieldName"]:checked').size();
		 if(selectNum <= 0)
		 {
		 	alertMsg.error("请至少选择一个字段！");
		 	return ;
		 }
		 
		 var fieldNames = [];
		 var ids = [];
		 var parentFormObj = $("form[name=${submitFormName}]", parent.document);
		 $('input[name="fieldName"]:checked').each(function() 
		 {
				fieldNames.push($(this).val());
		 });
		 
		 parentFormObj.parent().find("input[name='ids']:checked").each(function() 
		 {
				ids.push($(this).val());
		 });
		
		parentFormObj.find("#${inputId}").val(fieldNames.join());
		//$("#${inputId}" , parent.document).val(fieldNames.join());
		var idInput = '"<input type="hidden" name="search_IN_id" value="'+(ids.join())+'" style="display:none;"/>"';
		parentFormObj.find("input[name='search_IN_id']").remove();
		parentFormObj.append(idInput);
		parentFormObj.attr({"action":"${contextPath}${exportURL}"});
		$.pdialog.closeCurrent();
		parentFormObj.submit();
	}catch(e)
	{
		$.error = e;
	}
}
</script>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		 <!-- <label style="width: 100px;">导出可选列选择：</label> -->
	 	<c:if test="${not empty fieldsMap}">
		 <ul style="list-style: none">
	 		<c:forEach var="item" items="${fieldsMap}" varStatus="index">
	 		 	<li style="display: inline-block; width: 100px;"><input type="checkbox" name="fieldName" value="${item.key}" checked="checked" >${item.value}</li>
	 		</c:forEach>
	 	</ul>
	 	</c:if>
	 	
	</div>
	<div class="formBar">
		<ul>	
			<li><div class="button"><div class="buttonContent"><button type="button" id="checkAll">全选</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" id="checkNotAll">全不选</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="exportData()">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>