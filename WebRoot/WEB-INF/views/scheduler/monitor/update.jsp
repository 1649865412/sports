<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/scheduler/job/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${qrtzJob.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>Job 名称：</label>
		<input type="text" name="jobName" maxlength="20" value="${qrtzJob.jobName }" class="input-medium"/>
	</p>	
	<p>
		<label>Job 分组：</label>
		<input type="text" name="jobGroup" maxlength="100" value="${qrtzJob.jobGroup}" class="input-medium"/>
	</p>
	
	<p>
		<label>状态：</label>
		<select name="jobStatus">
			<option value="0" <c:if test="${qrtzJob.jobStatus == 0}">selected="selected"</c:if> >禁用</option>
			<option value="1" <c:if test="${qrtzJob.jobStatus == 1}">selected="selected"</c:if> >启用</option>
			<option value="2" <c:if test="${qrtzJob.jobStatus == 2}">selected="selected"</c:if> >删除</option>
		</select> 
	</p>
	 
	<p>
		<label>执行类：</label>
		<input type="text" name="jobClass" maxlength="100" value="${qrtzJob.jobClass}" class="input-medium"/>
	</p>		
	<p>
		<label> Cron表达式：</label>
		<input type="text" name="cronExpression" maxlength="19" value="${qrtzJob.cronExpression}"  class="input-medium"/>
		（必填，Cron表达式(如"0/10 * * ? * * *"，每10秒中执行调试一次)，对使用者要求比较，要会写Cron表达式）
	</p>	
	<p>
		<label>描述：</label>
		<textarea rows="3" cols="20" name="desc" >
		 ${qrtzJob.desc} 
		</textarea> 
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