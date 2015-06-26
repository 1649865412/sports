<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/emailManager/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadRel);">
	<input type="hidden" name="id" value="${sendEmailInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>邮箱地址：</label>
		<input type="text" name="emailAddress" maxlength="100" value="${sendEmailInfo.emailAddress}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>用户名称：</label>
		<input type="text" name="emailUserName" maxlength="255" value="${sendEmailInfo.emailUserName}" class="input-medium"/>
	</p>
	<p>
		<label>联系方式：</label>
		<input type="text" name="phoneNumber" maxlength="50" value="${sendEmailInfo.phoneNumber}" class="input-medium"/>
	</p>	
	<p>
		<label>状态：</label>
		<select name="emailSend" class="input-medium">
			<option value="1" <c:if test="${sendEmailInfo.emailSend eq 1 }"> selected="selected"</c:if>>发送</option>
			<option value="0" <c:if test="${sendEmailInfo.emailSend eq 0 }"> selected="selected"</c:if>>不发送</option>
		</select>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" value="${sendEmailInfo.remarks}" class="input-medium"/>
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