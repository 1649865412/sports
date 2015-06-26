<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
 function  groupCheck(type){
	 var selectNum=$('#secondDiv label').size();
	 if(selectNum==0){
		 alertMsg.error("请至少选择一种元素做分析");
		}
		else if(selectNum>3)
		{
			 alertMsg.error("最多只能选三个分类分析");
		}
		 else{
				 dialog(type)
			 }
	 }

  function dialog(type)
  {	
  			var checked = [];//动态列(sql名)
  			var checkedName= [];//动态列(中文名)
  			var orderlist= [];//动态列顺序		
  			var chkIndex= [];//选 框下标
			$('#secondDiv label').each(function() {
					 checked.push($(this).attr("value"));
					 chkIndex.push($('#secondDiv label').index(this));
			});
			
			
			 $('span[id="groupLableNames"]').each(function() {
					checkedName.push($(this).text());
			});
			// $("#order_list_value_id" , parent.document).val("");
			// $("#group_type_index_id" , parent.document).val("");
			// $("#group_type_id" , parent.document).val("");
			 $("#order_list_value_id" , parent.document).val(orderlist.join());
			 $("#group_type_id" , parent.document).val(checked.join());
			 $("#group_type_index_id" , parent.document).val(chkIndex.join());
			 $.pdialog.closeCurrent();
				
		     // 正常导出
			 if(type==1)
			 {	
				 $('form[name="stockAnalysisFormName"]' , parent.document).attr({"action":"${contextPath}/management/stockAnalysis/exportExcel"});
		         $('form[name="stockAnalysisFormName"]' , parent.document).submit(); 
		     }
		     
		     // 预览
		     else if(type==2)
		     {
		      	var valueText=$("form[name='stockAnalysisFormName']", parent.document).serialize();
	    		var path="${contextPath}/management/stockAnalysis/previewExcel?"+valueText;
	    		$.pdialog.open(path, "previewListId_stock", "预览",{width:850,height:600,max:false,mask:true,resizable:true,drawable:true,fresh:true});
		      }
  }
</script>

<div class="pageContent" style="background-color:#FFFFF0"><br>
    <span style="color: #FF6600;padding-left:20px;margin-top:200px;font-weight: bold" >
		用鼠标拖动下面的要分类的属性</span> 
	<div style="height: 50px; overflow: auto;padding: 5px; margin: 15px;"  class="sortDrag">
		
		<span style="  padding: 5px; margin: 15px;width:30px">
			<label value="productType">种类</lable>
	
		</span>
		<span style="  padding: 5px; margin: 5px;width:30px">
			
			<label value="quater">季节</lable>
			
		</span>
		<span style=" padding: 5px; margin: 5px">
			
			<label value="series">系列</lable>
		
		</span>
		<span style=" padding: 5px; margin: 5px">
					  
			<label value="inlineOr2ndline">Line</lable>
			
		</span>

		<span style=" padding: 5px; margin: 5px">
			<label value="productSex">性别</lable>
			
		</span>
	</div>
	
	<div style="color: #FF6600;padding-left:20px;font-weight: bold;padding-bottom:5px" >
			请将要分类的属性拉到此处，分类级别从左往右</div>
			
	<div   class="sortDrag"  style="height:50px; overflow: auto;border: 2px solid #EAEAEA;width:93%;margin-left:10px;padding:10px"  id="secondDiv">
     	   
	</div>
	
	<br>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="groupCheck(${exportType})">
							确定
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							关闭
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>