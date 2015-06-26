<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }//management/stockmanager/stock_log/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${stockLog.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<input type="text" name="upccode" maxlength="20" value="${stockLog.upccode}" class="input-medium"/>
	</p>
	<p>
		<label>款号：</label>
		<input type="text" name="materialNumber" maxlength="100" value="${stockLog.materialNumber}" class="input-medium"/>
	</p>
	<p>
		<label>数量：</label>
		<input type="text" name="num" maxlength="19" value="${stockLog.num}" class="input-medium"/>
	</p>
	<p>
		<label>操作类型(订货1、到货2)：</label>
		<input type="text" name="optType" maxlength="20" value="${stockLog.optType}" class="input-medium"/>
	</p>
	<p>
		<label>更新人：</label>
		<input type="text" name="updatedUser" maxlength="100" value="${stockLog.updatedUser}" class="input-medium"/>
	</p>
	<p>
		<label>更新时间：</label>
		<input type="text" name="updatedTime" class="input-medium date validate[required]" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${stockLog.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>发生时间：</label>
		<input type="text" name="optTime" class="input-medium date validate[required]" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${stockLog.optTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>brandId：</label>
		<input type="text" name="brandId" maxlength="19" value="${stockLog.brandId}" class="input-medium"/>
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