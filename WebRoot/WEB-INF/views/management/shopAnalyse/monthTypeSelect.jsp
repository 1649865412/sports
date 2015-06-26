<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script type="text/javascript">

  function dialog(type){
              var imptype = $("input[name='imgType']:checked").val();
           //   alert(imptype);
			  $("#img", parent.document).val(imptype);
			  $("#CheckGetSelect", parent.document).val("Month");
			  $.pdialog.closeCurrent();
	          	if(type==1)
		         $('#ToExcelForm' , parent.document).submit(); 
		     else if(type==2){
		      	var valueText=$('#ToExcelForm', parent.document).serialize()
	    		var path="${contextPath}/management/shopAnalyse/previewList?"+valueText
	    		 $.pdialog.open(path, "previewListId", "导出报表预览",
						{width:850,height:600,max:false,mask:true,resizable:true,drawable:true,fresh:true});
		     }
	          $("#CheckGetSelect", parent.document).val("");
    
  }
</script>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>导出类型选择：</label>
			 <input type="radio" name="imgType" value="0" style="margin-left: 80px" checked="checked">柱状图&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="imgType" value="1">饼状图 </span>
		</p>
		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="dialog(${type})">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>