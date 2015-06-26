<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>


<script type="text/javascript">
 function  groupCheck(type){
	 var selectNum=$('#secondDiv label').size();
	 if(selectNum==0){
		 alert("请至少选择一种元素做分析");
		 }else if(selectNum>3){
			 alert("最多只能选三个分类分析");
			 }
		 else{
			 dialog(type)
			 }
	 }

  function dialog(type){
  				var checked = [];//动态列(sql名)
  				var orderlist= [];//动态列顺序		
  				var array=["product_type","quater", "series", "inline_or2ndline", "product_sex"]//此处要根据后台顺序而定	
  			  				
  				$('#secondDiv label').each(function() {
				           //   alert($(this).attr("value"))
					            checked.push($(this).attr("value"));
					        });
			   
  			  orderlist=getorderList(checked,array);
			  $("#CheckGetSelect", parent.document).val("ShopAnalyse");
			  
		    //	alert("order:"+orderlist.join());  
			//  alert("ShopOptions:"+checked.join());  

			  
			  $("#ShopOptions" , parent.document).val(checked.join());//动态列(sql名)
			  $("#OrderList" , parent.document).val(orderlist.join());//动态列顺序

			  
			   if($('#secondDiv label').size()>0){
			    $("input[name='excelType']:checked", parent.document).val(5);
			   }
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
	          $("input[name='excelType']:checked", parent.document).val(5)
  }
</script>



<!--  /**
	 * 销售分析分类 如要增加新分类，加这里根据前台顺序加上对应数据库字段即可
	 * 种类，季节，系列，一线还是二线，性别
	 */
	public static String GROUP_NAME_CHECK[] = new String[] { "product_type",
			"quater", "series", "inline_or2ndline", "product_sex" };-->


<div class="pageContent" style="background-color:#FFFFF0"><br>
    <span style="color: #FF6600;padding-left:20px;margin-top:200px;font-weight: bold" >
		用鼠标拖动下面的要分类的属性</span> 
	<div style="height: 50px; overflow: auto;padding: 5px; margin: 15px;"  class="sortDrag">
		
		<span style="  padding: 5px; margin: 15px;width:30px">
			<label value="product_type">种类</lable>
	
		</span>
		<span style="  padding: 5px; margin: 5px;width:30px">
			
			<label value="quater">季节</lable>
			
		</span>
		<span style=" padding: 5px; margin: 5px">
			
			<label value="series">系列</lable>
		
		</span>
		<span style=" padding: 5px; margin: 5px">
					  
					<label value="inline_or2ndline">Line</lable>
			
		</span>

		<span style=" padding: 5px; margin: 5px">
			<label value="product_sex">性别</lable>
			
		</span>
	</div>
	
	<div style="color: #FF6600;padding-left:20px;font-weight: bold;padding-bottom:5px" >
			请将要分类的属性拉到此处，分类级别从左往右</div>
			
	<div   class="sortDrag"  style="height:50px; overflow: auto;border: 2px solid #EAEAEA;width:285px;margin-left:10px;padding:10px"  id="secondDiv">
     	   
	</div>
	
	<br>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="groupCheck(${type})">
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


<script type="text/javascript">
 //判断在不在所选分类数组里
	function checkArrayHave(array,value){
	   var  flag=false;
	 //  alert("array:"+array.join()+"--value--"+value);
	   for(var j=0;j<array.length;j++){
	        if(value==array[j]){
	            flag=true;
	            break;
	            }
		   }
	   return flag;
	}

	

	
	//生成 所选排序索引数组(总个数，不分类个数)
	 function   getIndexArray(end,begin){
			var IndexArray = [];
			for(var i=end;i>begin;i--){
				IndexArray.push(i);
				}

			 return IndexArray;
		 }
	 

	 //返回数组索引
		function getIndex(array,value){
		   var  num=0;
		//	alert("========"+num);
		   for(var j=0;j<array.length;j++){
		        if(value==array[j]){
		        	num=j;
		            break;
		            }
			   }
		 //  alert("index"+num);
		   return num;
		}
		
 //所选分类，全部分类
 function getorderList(checked,array){
	 var orderlist= [];//动态列顺序		;
	 var  IndexArray=[];
	   IndexArray= getIndexArray(array.length,(array.length-checked.length))//所选分类索引
	    //alert("======="+IndexArray.join())
          for(var i=0;i<array.length;i++){
                        if(checkArrayHave(checked,array[i])){
	                        //    alert(array[i]+"有");
	                            var count=getIndex(checked,array[i]);
	                        	orderlist[i]= IndexArray[count];
                            }else{
                          //  	alert(array[i]+"无");
                            	orderlist[i]=1;  
                                }
              }
           //  alert("orderlist:"+orderlist.join());
             return orderlist;
	 }
 </script>