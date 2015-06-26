<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>导出报表预览</title>
	<script type="text/javascript">
	  function exportStockTimePreviewData(){
	       $.pdialog.closeCurrent();
		   $('#exportEXCELtime' , parent.document).submit(); 
	  }
  
 
  </script>
</head>
	<body  >
		<div class="pageContent" layoutH="10">
			${str}
		<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="exportStockTimePreviewData()">
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
