<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/security/brand/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	 	
	<p>
		<label>品牌名称：</label>
		<input type="text" name="brandName" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>扩展系统：</label>
		<input type="text" name="extendType" maxlength="255" class="input-medium"/>
	</p> 
	<p>
		<label>扩展ID：</label>
		<input type="text" name="extendId" maxlength="20" class="input-medium"/>
	</p> 
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>