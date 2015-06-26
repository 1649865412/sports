<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/scheduler/job/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>Job 名称：</label>
		<input type="text" name="jobName" maxlength="20" class="input-medium"/>
	</p>	
	<p>
		<label>Job 分组：</label>
		<input type="text" name="jobGroup" maxlength="100" class="input-medium"/>
		<input type="hidden" name="jobStatus" maxlength="100" value="1">
	</p>
	 
	<p>
		<label>执行类：</label>
		<input type="text" name="jobClass" maxlength="100" class="input-medium"/>
	</p>		
	<p>
		<label> Cron表达式：</label>
		<input type="text" name="cronExpression" maxlength="19" class="input-medium"/>
		（必填，Cron表达式(如"0/10 * * ? * * *"，每10秒中执行调试一次)，对使用者要求比较，要会写Cron表达式）
	</p>	
	<p>
		<label>描述：</label>
		<textarea rows="3" cols="20" name="desc">
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