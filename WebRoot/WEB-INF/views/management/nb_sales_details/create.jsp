<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/nbSalesDetails/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
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
		<label>平台ID：</label>
		<input type="text" name="platformId" maxlength="100" class="input-medium validate[required] required"/>
	</p>	
	<p>
		<label>销售起止时间：</label>
		<input type="text" name="marketStartTime" maxlength="100" class="input-medium date validate[required] required" style="width: 145px"/>
			<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
		<label>销售结束时间：</label>
		<input type="text" name="marketEndTime" maxlength="100" class="input-medium date validate[required] required" style="width: 145px"/>
			<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
		<label>销售数量：</label>
		<input type="text" name="salesNumber" maxlength="11" class="input-medium  validate[custom[integer] validate[required] required"/>
	</p>	
	<p>
		<label>成交价（均价）：</label>
		<input type="text" name="avgCurrentPrice" maxlength="11" class="input-medium validate[custom[number]"/>
	</p>	
	<p>
		<label>销售金额：</label>
		<input type="text" name="salesAmount" maxlength="11" class="input-medium validate[required] validate[custom[number] required"/>
	</p>	
	<p>
		<label>仓库：</label>
		<input type="text" name="storeHouse" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>入库时间：</label>
		<input type="text" name="enteringTime" maxlength="30" class="input-medium date" style="width: 145px"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
		<label>订货量：</label>
		<input type="text" name="orderNumber" maxlength="11" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>库存量：</label>
		<input type="text" name="stockNumber" maxlength="11" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>到货量：</label>
		<input type="text" name="arriveNumber" maxlength="11" class="input-medium validate[custom[integer]"/>
	</p>	
	<p>
		<label>预计到货期：</label>
		<input type="text" name="predictArriveTime" maxlength="50" class="input-medium"/>
	</p>	
	<p>
		<label>实际到货时间：</label>
		<input type="text" name="factArriveTime" maxlength="50" class="input-medium"/>
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