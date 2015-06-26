<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<script type="text/javascript">

  function exportButton(){
	  // alert("你好")
       $.pdialog.closeCurrent();
	   $('#${ empty exportFormId ? "ToExcelForm" : exportFormId}').submit(); 
     //  dialogCheck()
  }
  
 
  </script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>导出报表预览</title>
	</head>
	<body  >
		<div class="pageContent" layoutH="10">
			${str}
		<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="exportButton()">
							导出
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
	</body>
</html>
