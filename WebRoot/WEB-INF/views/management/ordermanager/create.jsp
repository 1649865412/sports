<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/orderManager/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">	
	<p>
		<label>69码：</label>
		<input type="text" name="upccode" maxlength="100" class="input-medium validate[required] required"/>
	</p>	
	<p>
		<label>款号：</label>
		<input type="text" name="materialNumber" maxlength="100" class="input-medium validate[required] required"/>
	</p>	
	<p>
		<label>平台产品ID：</label>
		<input type="text" name="platformId" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>订货数量：</label>
		<input type="text" name="orderNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>订货金额：</label>
		<input type="text" name="orderAmount" maxlength="11" class="input-medium validate[custom[number]"/>
	</p>	
	<p>
		<label>转仓数量：</label>
		<input type="text" name="transferNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>调货数量：</label>
		<input type="text" name="transferCargoNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>退货数量：</label>
		<input type="text" name="returnNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>预计到货期：</label>
		<input type="text" name="predictArriveTime" datefmt="yyyy-MM-dd" maxlength="50" class="input-medium date" readonly="readonly" style="float:left; width: 144px"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
		<label>样品数量：</label>
		<input type="text" name="sampleNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>出库单号：</label>
		<input type="text" name="storehouseId" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>颜色：</label>
		<input type="text" name="color" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>色号：</label>
		<input type="text" name="colourNumber" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>尺码：</label>
		<input type="text" name="productSize" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>到货数量：</label>
		<input type="text" name="arrivalNumber" maxlength="10" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>大款：</label>
		<input type="text" name="productLarge" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>店铺名称：</label>
		<input type="text" name="shopName" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>客户名称：</label>
		<input type="text" name="customerName" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>波次号：</label>
		<input type="text" name="waveNumber" maxlength="30" class="input-medium"/>
	</p>	
	<p>
		<label>原出库单号：</label>
		<input type="text" name="oldStorehouseId" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" class="input-medium"/>
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