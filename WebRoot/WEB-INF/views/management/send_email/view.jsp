<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>邮箱地址：</label>
		<span class="unit">${sendEmailInfo.emailAddress}</span>
	</p>
	<p>
		<label>用户名称：</label>
		<span class="unit">${sendEmailInfo.emailUserName}</span>
	</p>
	<p>
		<label>联系方式：</label>
		<span class="unit">${sendEmailInfo.phoneNumber}</span>
	</p>
	<p>
		<label>状态：</label>
		<span class="unit">${sendEmailInfo.emailSend eq 1 ? '发送':'不发送'}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${sendEmailInfo.remarks}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${sendEmailInfo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>