<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/dailyReport/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${dailyReportInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>创建时间：</label>
		<input type="text" name="fileCreateTime" maxlength="25" value="${dailyReportInfo.fileCreateTime}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>文件名称：</label>
		<input type="text" name="fileCreateName" maxlength="255" value="${dailyReportInfo.fileCreateName}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>默认存放路径：</label>
		<input type="text" name="defaultSavePath" maxlength="1000" value="${dailyReportInfo.defaultSavePath}" class="input-medium"/>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" value="${dailyReportInfo.remarks}" class="input-medium"/>
	</p>
	<p>
		<label>所属组织：</label>
		<input type="text" name="brandId" maxlength="19" value="${dailyReportInfo.brandId}" class="input-medium"/>
	</p>
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" maxlength="25" value="${dailyReportInfo.updateTime}" class="input-medium"/>
	</p>
	<p>
		<label>预留字段1：</label>
		<input type="text" name="reserve1" maxlength="100" value="${dailyReportInfo.reserve1}" class="input-medium"/>
	</p>
	<p>
		<label>预留字段2：</label>
		<input type="text" name="reserve2" maxlength="100" value="${dailyReportInfo.reserve2}" class="input-medium"/>
	</p>
	<p>
		<label>预留字段3：</label>
		<input type="text" name="reserve3" maxlength="100" value="${dailyReportInfo.reserve3}" class="input-medium"/>
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