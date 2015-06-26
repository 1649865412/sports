<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>导出报表预览</title>
	
</head>
	<body  >
		<div class="pageContent" layoutH="10">
		<c:if test="${not empty error_message}">${error_message}</c:if>
		<c:if test="${not empty ExcelToHtml}">${ExcelToHtml}</c:if>
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

<script type="text/javascript" defer="defer">
	  function exportButton(){
	       $.pdialog.closeCurrent();
	       $('form[name="dailyReportInfoFormName"]' , parent.document).attr({"action":"${contextPath }/management/dailyReport/downLoad/${id}"});
		   $('form[name="dailyReportInfoFormName"]', parent.document).submit(); 
	  }
	  
	  $(document).ready(function(){
	  	 $("td").removeClass("style_02");
	});
  
 
  </script>
