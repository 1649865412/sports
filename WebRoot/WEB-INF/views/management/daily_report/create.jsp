<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/dailyReport/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>创建时间：</label>
		<input type="text" name="fileCreateTime" maxlength="25" class="input-medium required validate[required]"/>
	</p>	
	<p>
		<label>文件名称：</label>
		<input type="text" name="fileCreateName" maxlength="255" class="input-medium required validate[required]"/>
	</p>	
	<p>
		<label>默认存放路径：</label>
		<input type="text" name="defaultSavePath" maxlength="1000" class="input-medium"/>
	</p>	
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>所属组织：</label>
		<input type="text" name="brandId" maxlength="19" class="input-medium"/>
	</p>	
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段1：</label>
		<input type="text" name="reserve1" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段2：</label>
		<input type="text" name="reserve2" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段3：</label>
		<input type="text" name="reserve3" maxlength="100" class="input-medium"/>
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