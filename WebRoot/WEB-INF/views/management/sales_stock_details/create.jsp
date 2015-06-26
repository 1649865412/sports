<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/sales_stock_details/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<input type="text" name="upccode" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>销售时间：</label>
		<input type="text" name="salesTime" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>销量：</label>
		<input type="text" name="salesNumber" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>成交价（均价）：</label>
		<input type="text" name="avgCurrentPrice" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>销售金额：</label>
		<input type="text" name="salesAmount" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>仓库：</label>
		<input type="text" name="storeHouse" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>入库时间：</label>
		<input type="text" name="enteringTime" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>订货量：</label>
		<input type="text" name="orderNumber" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>库存量：</label>
		<input type="text" name="stockNumber" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>到货量：</label>
		<input type="text" name="arriveNumber" maxlength="12" class="input-medium"/>
	</p>	
	<p>
		<label>预计到货期：</label>
		<input type="text" name="predictArriveTime" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>实际到货时间：</label>
		<input type="text" name="factArriveTime" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>所属组织：</label>
		<input type="text" name="brandId" maxlength="10" class="input-medium"/>
	</p>	
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" maxlength="50" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段1：</label>
		<input type="text" name="reserve1" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段2：</label>
		<input type="text" name="reserve2" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>预留字段3：</label>
		<input type="text" name="reserve3" maxlength="255" class="input-medium"/>
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