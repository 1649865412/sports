<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/orderManager/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${orderFormInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<input type="text" name="upccode" maxlength="100" value="${orderFormInfo.upccode}" class="input-medium readonly validate[required] required" readonly="readonly"/>
	</p>
	<p>
		<label>款号：</label>
		<input type="text" name="materialNumber" maxlength="100" value="${orderFormInfo.materialNumber}" class="input-medium validate[required] required readonly" readonly="readonly"/>
	</p>
	<p>
		<label>平台产品ID：</label>
		<input type="text" name="platformId" maxlength="100" value="${orderFormInfo.platformId}" class="input-medium"/>
	</p>
	<p>
		<label>订货数量：</label>
		<input type="text" name="orderNumber" maxlength="10" value="${orderFormInfo.orderNumber}" class="input-medium validate[custom[integer]"/>
	</p>
	<p>
		<label>订货金额：</label>
		<input type="text" name="orderAmount" maxlength="11" value="${orderFormInfo.orderAmount}" class="input-medium validate[custom[number]"/>
	</p>
	<p>
		<label>转仓数量：</label>
		<input type="text" name="transferNumber" maxlength="10" value="${orderFormInfo.transferNumber}" class="input-medium validate[custom[integer]" />
	</p>
	<p>
		<label>调货数量：</label>
		<input type="text" name="transferCargoNumber" maxlength="10"  value="${orderFormInfo.transferCargoNumber}" class="input-medium validate[custom[integer]"/>
	</p>
	<p>
		<label>退货数量：</label>
		<input type="text" name="returnNumber" maxlength="10" value="${orderFormInfo.returnNumber}" class="input-medium validate[custom[integer]"/>
	</p>
	<p>
		<label>预计到货期：</label>
		<input type="text" name="predictArriveTime" maxlength="50" value="${orderFormInfo.predictArriveTime}" class="input-medium date" readonly="readonly" style="float:left;width: 144px"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>样品数量：</label>
		<input type="text" name="sampleNumber" maxlength="10" value="${orderFormInfo.sampleNumber}" class="input-medium validate[custom[integer]"/>
	</p>
	<p>
		<label>出库单号：</label>
		<input type="text" name="storehouseId" maxlength="100" value="${orderFormInfo.storehouseId}" class="input-medium"/>
	</p>
	<p>
		<label>颜色：</label>
		<input type="text" name="color" maxlength="25" value="${orderFormInfo.color}" class="input-medium"/>
	</p>
	<p>
		<label>色号：</label>
		<input type="text" name="colourNumber" maxlength="25" value="${orderFormInfo.colourNumber}" class="input-medium"/>
	</p>
	<p>
		<label>尺码：</label>
		<input type="text" name="productSize" maxlength="25" value="${orderFormInfo.productSize}" class="input-medium"/>
	</p>
	<p>
		<label>到货数量：</label>
		<input type="text" name="arrivalNumber" maxlength="10" value="${orderFormInfo.arrivalNumber}" class="input-medium validate[custom[integer]"/>
	</p>
	<p>
		<label>大款：</label>
		<input type="text" name="productLarge" maxlength="25" value="${orderFormInfo.productLarge}" class="input-medium"/>
	</p>
	<p>
		<label>店铺名称：</label>
		<input type="text" name="shopName" maxlength="255" value="${orderFormInfo.shopName}" class="input-medium"/>
	</p>
	<p>
		<label>客户名称：</label>
		<input type="text" name="customerName" maxlength="100" value="${orderFormInfo.customerName}" class="input-medium"/>
	</p>
	<p>
		<label>波次号：</label>
		<input type="text" name="waveNumber" maxlength="30" value="${orderFormInfo.waveNumber}" class="input-medium"/>
	</p>
	<p>
		<label>原出库单号：</label>
		<input type="text" name="oldStorehouseId" maxlength="100" value="${orderFormInfo.oldStorehouseId}" class="input-medium"/>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remarks" maxlength="255" value="${orderFormInfo.remarks}" class="input-medium"/>
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