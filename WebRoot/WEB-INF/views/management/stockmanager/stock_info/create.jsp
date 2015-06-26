<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/stockInfo/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<input type="text" name="upccode" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>现有库存数量：</label>
		<input type="text" name="currentStockNumber" maxlength="10" class="input-medium"/>
	</p>	
	<p>
		<label>可用库存数据：</label>
		<input type="text" name="availableStockNumber" maxlength="10" class="input-medium"/>
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
		<input type="text" name="updateTime" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>订货单表：</label>
		<input type="text" name="orderId" maxlength="10" class="input-medium"/>
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