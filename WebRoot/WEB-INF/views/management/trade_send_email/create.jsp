<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/tradEmailManager/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadRel);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>邮箱地址：</label>
		<input type="text" name="emailAddress" maxlength="100" alt="请输入您的电子邮箱" class="input-medium required email validate[required]"/>
	</p>	
	<p>
		<label>用户名称：</label>
		<input type="text" name="emailUserName" maxlength="255" alt="请输入您的姓名" class="input-medium"/>
	</p>
	<p>
		<label>联系方式：</label>
		<input type="text" name="phoneNumber" maxlength="50"  alt="请输入您的电话号码" class="input-medium phone"/>
	</p>			
	<p>
		<label>状态：</label>
		<select name="emailSend" class="input-medium">
			<option value="1" selected="selected">发送</option>
			<option value="0">不发送</option>
		</select>
	</p>	
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" class="input-medium"/>
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