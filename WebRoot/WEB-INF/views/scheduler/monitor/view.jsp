<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	
	<p>
		<label>Job 名称：</label>
		<span class="unit">${qrtzJob.jobName}</span> 
	</p>	
	<p>
		<label>Job 分组：</label>
		<span class="unit">${qrtzJob.jobGroup}</span>  
	</p>
	
	<p>
		<label>状态：</label>
		<span class="unit">
		<c:if test="${qrtzJob.jobStatus == 0}">
			禁用
		</c:if>
		<c:if test="${qrtzJob.jobStatus == 1}">
			启用
		</c:if>
		<c:if test="${qrtzJob.jobStatus == 2}">
			删除
		</c:if>
	  </span>   
	</p>
	 
	<p>
		<label>执行类：</label>
		<span class="unit">${qrtzJob.jobClass}</span>  
	</p>		
	<p>
		<label> Cron表达式：</label>
		<span class="unit">${qrtzJob.cronExpression}</span>  
	</p>	
	<p>
		<label>描述：</label>
		<span class="unit">${qrtzJob.desc}</span>  
	 
	</p>	 
	 
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>