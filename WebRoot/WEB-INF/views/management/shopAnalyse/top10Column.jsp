<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>


<script type="text/javascript">
	 $(document).ready(function() {		
 	        var array=new Array();
 	        var valueSelect= $("#topColumn", parent.document).val();
 	        array=valueSelect.split(",");
 	        if(!valueSelect.length==0){
 	        	$('input[name="topColumnSelect"]').each(function() {
 	 	            if(!checkHave($(this).val(),array)){
 	 	            	$(this).removeAttr("checked"); 
 	 	                }
 	 	        });
 	 	        }
 	       
 		 });
	   function checkHave(checkboxvalue,array){
		   var flag=false;
           for(var i=0;i<array.length;i++){
               if(array[i]==checkboxvalue){
                   flag=true;
            	   break; 
                   }
                 }
           return flag;
		   }

	   
  function dialog(type,top0rdayType){
	  		  var checked = [];
	  		  var num= $('input[name="topColumnSelect"]:checked').size();
	  		  if(num==0){
		  		  	 alert("至少择选一列")
		  		  }
		  		  else{
		  		      $('input[name="topColumnSelect"]:checked').each(function() {
				            checked.push($(this).val());
				        });
					  $("#topColumn", parent.document).val(checked);
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
  }</script>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	  <label style="width: 100px;">导出可选列选择：</label>
		   <div>
			 <input type="checkbox" name="topColumnSelect" value="upccode" style="margin-left: 80px" checked="checked" >69码	
			  <input type="checkbox" name="topColumnSelect" value="materialNumber" style="margin-left: 80px" checked="checked" >款号	
			   <input type="checkbox" name="topColumnSelect" value="line" style="margin-left: 80px" checked="checked" >Lnline/2ndline	
			    <input type="checkbox" name="topColumnSelect" value="productLfPf" style="margin-left: 80px" checked="checked" >LF/PF	
			     <input type="checkbox" name="topColumnSelect" value="avgCurrentPrice" style="margin-left: 74px" checked="checked" >销售价	
			      <input type="checkbox" name="topColumnSelect" value="salesSum" style="margin-left: 68px" checked="checked" >销量
			       <input type="checkbox" name="topColumnSelect" value="salesNumber" style="margin-left: 190px" checked="checked" >销售金额	
			        <input type="checkbox" name="topColumnSelect" value="discount" style="margin-left: 58px" checked="checked" >销售占比(%)
			          <input type="checkbox" name="topColumnSelect" value="quater" style="margin-left: 38px" checked="checked" >季节(季节)
			           <input type="checkbox" name="topColumnSelect" value="numIndex" style="margin-left: 190px" checked="checked" >序号 
			 </div>
		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="dialog(${type},${top0rdayType})">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>