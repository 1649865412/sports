<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>69码：</label>
		<span class="unit">${stockLog.upccode}</span>
	</p>
	<p>
		<label>款号：</label>
		<span class="unit">${stockLog.materialNumber}</span>
	</p>
	<p>
		<label>数量：</label>
		<span class="unit">${stockLog.num}</span>
	</p>
	<p>
		<label>操作类型(订货1、到货2)：</label>
		<span class="unit">${stockLog.optType}</span>
	</p>
	<p>
		<label>更新人：</label>
		<span class="unit">${stockLog.updatedUser}</span>
	</p>
	<p>
		<label>更新时间：</label>
		<span class="unit">${stockLog.updatedTime}</span>
	</p>
	<p>
		<label>发生时间：</label>
		<span class="unit">${stockLog.optTime}</span>
	</p>
	<p>
		<label>brandId：</label>
		<span class="unit">${stockLog.brandId}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>